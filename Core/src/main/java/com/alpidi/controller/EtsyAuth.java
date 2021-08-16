package com.alpidi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import org.springframework.util.*;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import com.alpidi.controller.EtsyAuth;
import com.alpidi.model.Etsy;
import com.alpidi.model.EtsyTokenDetails;
import com.alpidi.model.Response;
import com.alpidi.model.User;
import com.alpidi.model._Error;

import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class EtsyAuth {
	@Autowired
	EtsyRepository etsyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	JwtUtils jwtUtils;

	private static final Logger log = LoggerFactory.getLogger(EtsyAuth.class);

	@Value("${alpidi.app.etsy_token_url}")
	private String etsy_token_url;
	
	@Value("${alpidi.app.etsy_parent_url}")
	private String esty_parent_url;
	
	private String client_id = "jytjbhw4mv47rgjwv4yg9gxn";
	private String client_secret = "ya4rno838q";
	private String redirect_uri = "http://localhost:4200/account/shopowner/connect";
	
	@PostMapping("etsy/getTokendetails")
	public ResponseEntity<?> TokenDetails(@RequestBody String token) {
		
		if(jwtUtils.validateJwtToken(token))
		{
			var email = jwtUtils.getUserNameFromJwtToken(token);
			Optional<User> userdata= userRepository.findByEmail(email);
			Optional<Etsy> etsyData = etsyRepository.findByuserid(userdata.get().getId());

			return ResponseEntity.ok(etsyData);
		}
		else
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Authentication is failed!"));
		}
	}
	
	@RequestMapping(value = "/etsy/generatecodechallenge", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> generateCodeChallenge() {
		try {
			SecureRandom sr = new SecureRandom();
			byte[] code = new byte[32];
			sr.nextBytes(code);
			String code_verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);

			byte[] bytes = code_verifier.getBytes("US-ASCII");
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes, 0, bytes.length);
			byte[] digest = md.digest();
			String code_challenge = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(digest);

			Response auth_resp = new Response(200, "success", null, null, null, code_challenge, code_verifier,null);
			return new ResponseEntity<Response>(auth_resp, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			Response auth_resp = new Response(500, "Some internal error occured!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(auth_resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/etsy/getauthcode", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> getAuthCode(@Nullable @RequestParam("code") String code,
		@Nullable @RequestParam("state") String state, @Nullable @RequestParam("error") String error,
		@Nullable @RequestParam("error_description") String error_description) {
		try {
			if ((error != null) && (code == null)) {
				Response auth_resp = new Response(1000, "Authentication Failed!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(auth_resp, HttpStatus.FORBIDDEN);
			}

			if ((state != null) && (state.equals("alpidietsyapp"))) {
				Response auth_resp = new Response(1000, "success", code, null, null, null, null,null);
				return new ResponseEntity<Response>(auth_resp, HttpStatus.OK);
			} else {
				Response auth_resp = new Response(1000, "Invalid Esty App", null, null, null, null, null,null);
				return new ResponseEntity<Response>(auth_resp, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			Response auth_resp = new Response(1001, "Some internal error occured!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(auth_resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/etsy/getaccesstoken", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> getAccessToken(@RequestParam("code") String code,
		@RequestParam("code_verifier") String code_verifier,@RequestParam("authtoken") String authtoken) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));

			// setting up the request body with APPLICATION_FORM_URLENCODED
			MultiValueMap<String, String> reqMap = new LinkedMultiValueMap<>();
			reqMap.add("grant_type", "authorization_code");
			reqMap.add("client_id", client_id);
			reqMap.add("redirect_uri", redirect_uri);
			reqMap.add("code", code);
			reqMap.add("code_verifier", code_verifier);
			HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(reqMap, headers);

			try {
				if(jwtUtils.validateJwtToken(authtoken))
				{
					ResponseEntity<String> response = restTemplate.exchange(etsy_token_url, HttpMethod.POST, formEntity, String.class);
					String responseBody = response.getBody();
					Gson gson = new Gson();
					var token = gson.fromJson(responseBody, EtsyTokenDetails.class);		
					
					String str = token.getAccess_token();
					String[] arrSplit = str.split("\\.");
					var  etsyuserid =arrSplit[0];
					
					Date currentDate = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(currentDate);
					cal.add(Calendar.HOUR, +1);
					Date oneHour = cal.getTime();

					var email = jwtUtils.getUserNameFromJwtToken(authtoken);
					Optional<User> userdata= userRepository.findByEmail(email);
					
					Etsy etsy  = new Etsy(token.getAccess_token(),token.getToken_type(),token.getRefresh_token(),oneHour,userdata.get().getId(),etsyuserid);
					System.out.println(etsyRepository.existsByetsyuserid(etsyuserid));
					if(etsyRepository.existsByetsyuserid(etsyuserid)==false)
					{	
						etsyRepository.save(etsy);
					}
					
					Response resp = new Response(200, "success", null, token, null, null, null,null);
					return new ResponseEntity<Response>(resp, HttpStatus.OK);
				}
				else
				{
					Response resp = new Response(500, "User Authentication is failed!", null, null, null, null, null,null);
					return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
			} catch (HttpClientErrorException ex) {
				log.error(EtsyAuth.class.getName(), ex.getMessage());
				var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
				Response resp = new Response(1002, "Some internal error occured, while generate new esty token!", null,
					null, error, null, null,null);
				return new ResponseEntity<Response>(resp, ex.getStatusCode());
			}
		} catch (Exception e) {
			log.error(EtsyAuth.class.getName(), e.getMessage());
			Response resp = new Response(500, "Some internal error occured!", null, null, null, null, null,null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/etsy/getrefreshtoken", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response> getRefreshToken(@RequestParam("refresh_token") String refresh_token,@RequestParam("authtoken") String authtoken) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));

			// setting up the request body with APPLICATION_FORM_URLENCODED
			MultiValueMap<String, String> reqMap = new LinkedMultiValueMap<>();
			reqMap.add("grant_type", "refresh_token");
			reqMap.add("client_id", client_id);
			reqMap.add("refresh_token", refresh_token);
			HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(reqMap, headers);

			try {
				if(jwtUtils.validateJwtToken(authtoken))
				{
					ResponseEntity<String> response = restTemplate.exchange(etsy_token_url, HttpMethod.POST, formEntity, String.class);
					String responseBody = response.getBody();
					Gson gson = new Gson();
					var token = gson.fromJson(responseBody, EtsyTokenDetails.class);
					String str = token.getAccess_token();
					String[] arrSplit = str.split("\\.");
					var  etsyuserid =arrSplit[0];
					

					var email = jwtUtils.getUserNameFromJwtToken(authtoken);
					Optional<User> userdata= userRepository.findByEmail(email);
					
					Optional<Etsy> etsyData = etsyRepository.findByuserid(userdata.get().getId());
					
						Date currentDate = new Date();
						Calendar cal = Calendar.getInstance();
						cal.setTime(currentDate);
						cal.add(Calendar.HOUR, +1);
						Date oneHour = cal.getTime();
						
						Etsy _etsy = etsyData.get();
						_etsy.setAccesstoken(token.getAccess_token());
						_etsy.setRefreshtoken(token.getRefresh_token());
						_etsy.setExpirein(oneHour);
						_etsy.setEtsyuserid(etsyuserid);
						if(etsyRepository.existsByetsyuserid(etsyuserid)==false)
						{
							etsyRepository.save(_etsy);
						}

					Response resp = new Response(1000, "success", null, token, null, null, null,null);
					return new ResponseEntity<Response>(resp, response.getStatusCode());
				}
				else
				{
					Response resp = new Response(1001, "User authentication fail!", null, null, null, null, null,null);
					return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				}
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

	@RequestMapping(value = "/etsy/getuser", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getuser(@RequestParam("authtoken") String authtoken) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {

				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				Optional<Etsy> etsyData = etsyRepository.findByuserid(userdata.get().getId());
				
				String url = esty_parent_url+"users/"+etsyData.get().getEtsyuserid();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
				headers.add("charset", "utf-8");
				headers.add("x-api-key", client_id);
				System.out.println(etsyData.get().getRefreshtoken());

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
				
				System.out.println("Newtoken : "+newaccess_token);

				headers.add("Authorization", "Bearer " + newaccess_token);

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
				
				HttpEntity<String> entity = new HttpEntity<String>(headers);
				try {
					ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
					String responseBody = response.getBody();
					return ResponseEntity.ok(responseBody);
				} catch (HttpClientErrorException ex) {
					
					log.error(EtsyAuth.class.getName(), ex.getMessage());
					var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
					System.out.println(error.getError());
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


}
