package com.alpidi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.alpidi.model.Etsy;
import com.alpidi.model.EtsyTokenDetails;
import com.alpidi.model.Listing;
import com.alpidi.model.ListingPrice;
import com.alpidi.model.ListingResult;
import com.alpidi.model.ListingShopImg;
import com.alpidi.model.Response;
import com.alpidi.model.ShopImage;
import com.alpidi.model.Shops;
import com.alpidi.model.User;
import com.alpidi.model._Error;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class EtsyShop {
	@Autowired
	EtsyRepository etsyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	ListingRepository listingRepository;
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	JwtUtils jwtUtils;

	private static final Logger log = LoggerFactory.getLogger(EtsyShop.class);
	
	@Value("${alpidi.app.etsy_token_url}")
	private String etsy_token_url;
	@Value("${alpidi.app.etsy_parent_url}")
	private String esty_parent_url;
	
	private String client_id = "jytjbhw4mv47rgjwv4yg9gxn";

	@RequestMapping(value = "/etsy/getShopByOwnerUserId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getShopByOwnerUserId(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			var email = jwtUtils.getUserNameFromJwtToken(authtoken);
			Optional<User> userdata= userRepository.findByEmail(email);
			Optional<Etsy> etsyData = etsyRepository.findByuserid(userdata.get().getId());
			
			try {
				String url = esty_parent_url + "users/"+etsyData.get().getEtsyuserid()+"/shops";
				System.out.println(url);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
				headers.add("charset", "utf-8");
				headers.add("x-api-key", client_id);	

				HttpEntity<String> entity = new HttpEntity<String>(headers);

				try {
					ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
					String responseBody = response.getBody();

					Gson gson = new Gson();
					var result = gson.fromJson(responseBody, Shops.class);
					
					
					 JSONObject js = new JSONObject();
					 js.put("shop_id",result.getshopid());
					 js.put("shop_name",result.getshopname());
					 var res = gson.toJson(js); 
					 System.out.println(js);
					
					return ResponseEntity.ok(response.getBody());
				} catch (HttpClientErrorException ex) {
					log.error(EtsyAuth.class.getName(), ex.getMessage());
					var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
					Response resp = new Response(404,
						"Some internal error occured, while generate new esty refresh token!", null, null, error, null,
						null,null);
					return new ResponseEntity<Response>(resp, ex.getStatusCode());
				}
			} catch (Exception e) {
				log.error(EtsyAuth.class.getName(), e.getMessage());
				Response resp = new Response(505, "Some internal error occured!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@RequestMapping(value = "/etsy/getshopbyuser", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getShopByUser(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{

			var email = jwtUtils.getUserNameFromJwtToken(authtoken);
			Optional<User> userdata= userRepository.findByEmail(email);
			Optional<Etsy> etsyData = etsyRepository.findByuserid(userdata.get().getId());
			
			try {
				String url = esty_parent_url + "users/"+etsyData.get().getEtsyuserid()+"/shops";
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
				headers.add("charset", "utf-8");
				headers.add("x-api-key", client_id);	

				HttpEntity<String> entity = new HttpEntity<String>(headers);

				try {
					ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
					String responseBody = response.getBody();
					Gson gson = new Gson();
					var result = gson.fromJson(responseBody, Shops.class);
					
					var etsyuserid=result.getuser_id();
					System.out.println(etsyuserid);
					if (etsyRepository.existsByetsyuserid(etsyuserid)) {
						System.out.println("Etsy Auth Done.");
						System.out.println(shopRepository.existsByetsyuserid(etsyuserid));
						if (shopRepository.existsByetsyuserid(etsyuserid)) 
						{
							System.out.println("Duplicate shop");
							
							return ResponseEntity.badRequest().body(new MessageResponse("Duplicate Shop!"));
						}
						else
						{
							System.out.println("New entry");
							Shops shop= new Shops(result.getshopid(),result.getshopname(),null,etsyuserid,result.getcurrencycode(),result.getlisting_active_count(),result.getlogin_name(),result.getaccepts_custom_requests(),result.geturl(),result.getimage_url(),result.getnum_favorers(),result.getpolicy_welcome(),result.getpolicy_payment(),result.getpolicy_shipping(),result.getpolicy_refunds(),result.getpolicy_additional(),result.getpolicy_seller_info(),result.getpolicy_update_date(),result.getpolicy_has_private_receipt_info());
							shopRepository.save(shop);
							
							return ResponseEntity.ok(response.getBody());
						}
					}
					else
					{
						Shops shop= new Shops(result.getshopid(),result.getshopname(),null,etsyuserid,result.getcurrencycode(),result.getlisting_active_count(),result.getlogin_name(),result.getaccepts_custom_requests(),result.geturl(),result.getimage_url(),result.getnum_favorers(),result.getpolicy_welcome(),result.getpolicy_payment(),result.getpolicy_shipping(),result.getpolicy_refunds(),result.getpolicy_additional(),result.getpolicy_seller_info(),result.getpolicy_update_date(),result.getpolicy_has_private_receipt_info());
						shopRepository.save(shop);
						
						return ResponseEntity.ok(response.getBody());
					}
					
					//Response resp = new Response(1000, "success", null, null, null, null, null,response.getBody());
					//return new ResponseEntity<Response>(resp, response.getStatusCode());
				} catch (HttpClientErrorException ex) {
					log.error(EtsyAuth.class.getName(), ex.getMessage());
					var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
					Response resp = new Response(404,
						"Some internal error occured, while generate new esty refresh token!", null, null, error, null,
						null,null);
					return new ResponseEntity<Response>(resp, ex.getStatusCode());
				}
			} catch (Exception e) {
				log.error(EtsyAuth.class.getName(), e.getMessage());
				Response resp = new Response(505, "Some internal error occured!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@RequestMapping(value = "/etsy/findshop", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> findShop(@RequestParam("shop_name") String shop_name) {
		try {
			String url = esty_parent_url+"shops?shop_name="+shop_name ;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
			headers.add("charset", "utf-8");
			headers.add("x-api-key", client_id);

			HttpEntity<String> entity = new HttpEntity<String>(headers);
			try {
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
				String responseBody = response.getBody();

				return ResponseEntity.ok(responseBody);

				//Response resp = new Response(1000, "success", null, null, null, null, null,null);
				//return new ResponseEntity<Response>(resp, response.getStatusCode());
			} catch (HttpClientErrorException ex) {
				System.out.println(ex.getMessage());
				log.error(EtsyAuth.class.getName(), ex.getMessage());
				var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
				Response resp = new Response(1002,
					"Some internal error occured, while generate new esty refresh token!", null, null, error, null,null,null);
				return new ResponseEntity<Response>(resp, ex.getStatusCode());
			}
		} catch (Exception e) {
			log.error(EtsyAuth.class.getName(), e.getMessage());
			Response resp = new Response(1001, "Some internal error occured!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/etsy/getshoplisting", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getshoplisting(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				Optional<Etsy> etsyData = etsyRepository.findByuserid(userdata.get().getId());
				Optional<Shops> shopData = shopRepository.findByetsyuserid(etsyData.get().getEtsyuserid());

				HttpHeaders refreshtokenheaders = new HttpHeaders();
				refreshtokenheaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				refreshtokenheaders.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
				MultiValueMap<String, String> reqMap = new LinkedMultiValueMap<>();
				reqMap.add("grant_type", "refresh_token");
				reqMap.add("client_id", client_id);
				reqMap.add("refresh_token", etsyData.get().getRefreshtoken());
				HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(reqMap, refreshtokenheaders);

				ResponseEntity<String> refresh_response = restTemplate.exchange(etsy_token_url, HttpMethod.POST, formEntity, String.class);
				String refresh_responseBody = refresh_response.getBody();
				Gson gson = new Gson();
				var token = gson.fromJson(refresh_responseBody, EtsyTokenDetails.class);
				String newaccess_token = token.getAccess_token();

				Date currentDate = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(currentDate);
				cal.add(Calendar.HOUR, +1);
				Date oneHour = cal.getTime();

				Etsy _etsy = etsyData.get();
				_etsy.setAccesstoken(token.getAccess_token());
				_etsy.setRefreshtoken(token.getRefresh_token());
				_etsy.setExpirein(oneHour);

				etsyRepository.save(_etsy);

				try {
					
					String url = esty_parent_url + "shops/"+shopData.get().getshopid()+"/listings";		
					HttpHeaders headers = new HttpHeaders();
					headers.add("charset", "utf-8");
					headers.add("x-api-key", client_id);
					headers.add("Authorization", "Bearer " + newaccess_token);
					HttpEntity<String> entity = new HttpEntity<String>(headers);
					
					ResponseEntity<Listing> response = restTemplate.exchange(url, HttpMethod.GET, entity, Listing.class);
					Listing listing = response.getBody();
					
					 for(int i=0; i<listing.getCount(); i++){  
						var listin_id=listing.getResults().get(i).getListing_id();
						
							String imgurl = esty_parent_url+"shops/"+shopData.get().getshopid()+"/listings/"+listin_id+"/images" ;
							HttpHeaders imageheaders = new HttpHeaders();
							imageheaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
							imageheaders.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
							imageheaders.add("charset", "utf-8");
							imageheaders.add("x-api-key", client_id);
		
							HttpEntity<String> img_entity = new HttpEntity<String>(imageheaders);
							
							ResponseEntity<ListingShopImg> img_response = restTemplate.exchange(imgurl, HttpMethod.GET, img_entity, ListingShopImg.class);
							ListingShopImg img = img_response.getBody();
							
							listing.getResults().get(i).setimg75(img.getResults().get(0).getUrl_75x75());
					 }  					 
					return ResponseEntity.ok(listing);
					
				} catch (HttpClientErrorException ex) {
					
					log.error(EtsyAuth.class.getName(), ex.getMessage());
					var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
					Response resp = new Response(1002,
						"Some internal error occured, while generate new esty refresh token!", null, null, error, null,
						null,null);
					return new ResponseEntity<Response>(resp, ex.getStatusCode());
				}
			} catch (Exception e) {
				log.error(EtsyAuth.class.getName(), e.getMessage());
				Response resp = new Response(1001, "Some internal error occured!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
			Response resp = new Response(404, "User Authentication Fail!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/etsy/getListingDetails", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getListingDetails(@RequestParam("listingid") String listingid) {			
		try {
			String url = esty_parent_url + "listings/"+listingid;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
			headers.add("charset", "utf-8");
			headers.add("x-api-key", client_id);	

			HttpEntity<String> entity = new HttpEntity<String>(headers);

			try {
				ResponseEntity<ListingResult> response = restTemplate.exchange(url, HttpMethod.GET, entity, ListingResult.class);
				ListingResult listingresult = response.getBody();
				
				String imgurl = esty_parent_url+"shops/"+listingresult.getShop_id()+"/listings/"+listingresult.getListing_id()+"/images" ;
				HttpHeaders imageheaders = new HttpHeaders();
				imageheaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				imageheaders.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
				imageheaders.add("charset", "utf-8");
				imageheaders.add("x-api-key", client_id);

				HttpEntity<String> img_entity = new HttpEntity<String>(imageheaders);
				
				ResponseEntity<ListingShopImg> img_response = restTemplate.exchange(imgurl, HttpMethod.GET, img_entity, ListingShopImg.class);
				ListingShopImg img = img_response.getBody();
				listingresult.setimg75(img.getResults().get(0).getUrl_170x135());
				listingresult.setImages(img.getResults());
			
				return ResponseEntity.ok(listingresult);
			} catch (HttpClientErrorException ex) {
				log.error(EtsyAuth.class.getName(), ex.getMessage());
				var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
				Response resp = new Response(404,
					"Some internal error occured, while generate new esty refresh token!", null, null, error, null,
					null,null);
				return new ResponseEntity<Response>(resp, ex.getStatusCode());
			}
		} catch (Exception e) {
			log.error(EtsyAuth.class.getName(), e.getMessage());
			Response resp = new Response(505, "Some internal error occured!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/etsy/getShoplistingImage", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getShoplistingImage(@RequestParam("shopid") String shopid,@RequestParam("listingid") String listingid) {			
			try {
				String url = esty_parent_url + "shops/"+shopid+"/listings/"+listingid+"/images";
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
				headers.add("charset", "utf-8");
				headers.add("x-api-key", client_id);	

				HttpEntity<String> entity = new HttpEntity<String>(headers);

				try {
					ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
										
					return ResponseEntity.ok(response.getBody());
				} catch (HttpClientErrorException ex) {
					log.error(EtsyAuth.class.getName(), ex.getMessage());
					var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
					Response resp = new Response(404,
						"Some internal error occured, while generate new esty refresh token!", null, null, error, null,
						null,null);
					return new ResponseEntity<Response>(resp, ex.getStatusCode());
				}
			} catch (Exception e) {
				log.error(EtsyAuth.class.getName(), e.getMessage());
				Response resp = new Response(505, "Some internal error occured!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
}
