package com.alpidi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alpidi.model.Etsy;
import com.alpidi.model.EtsyTokenDetails;
import com.alpidi.model.Listing;
import com.alpidi.model.ListingShopImg;
import com.alpidi.model.Response;
import com.alpidi.model.Shops;
import com.alpidi.model.User;
import com.alpidi.model.VectorFiles;
import com.alpidi.model._Error;
import com.alpidi.payload.response.UploadFileResponse;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.repository.ListingRepository;
import com.alpidi.repository.ShopRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.repository.VectorfilesRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.FileStorageService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class EtsyListingController {

    private static final Logger logger = LoggerFactory.getLogger(EtsyListingController.class);
    
    @Autowired
	EtsyRepository etsyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	ListingRepository listingRepository;
    @Autowired
	VectorfilesRepository vectorfilesRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
	RestTemplate restTemplate;

	@Value("${alpidi.app.etsy_token_url}")
	private String etsy_token_url;
	@Value("${alpidi.app.etsy_parent_url}")
	private String esty_parent_url;
	
	private String client_id = "jytjbhw4mv47rgjwv4yg9gxn";
    
    @RequestMapping(value = "/etsy/getShopSections", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getShopSections(@RequestParam("shopid") String shopid) {			
			try {
				String url = esty_parent_url + "shops/"+shopid+"/sections";
				
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

					var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
					Response resp = new Response(404,
						"Some internal error occured, while generate new esty refresh token!", null, null, error, null,
						null,null);
					return new ResponseEntity<Response>(resp, ex.getStatusCode());
				}
			} catch (Exception e) {
				Response resp = new Response(505, "Some internal error occured!", null, null, null, null, null,null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
    
    @RequestMapping(value = "/etsy/getListingInventory", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getListingInventory(@RequestParam("authtoken") String authtoken,@RequestParam("listingid") String listingid) {
		if(jwtUtils.validateJwtToken(authtoken))
		{
			try {
				var email = jwtUtils.getUserNameFromJwtToken(authtoken);
				Optional<User> userdata= userRepository.findByEmail(email);
				Optional<Etsy> etsyData = etsyRepository.findByuserid(userdata.get().getId());

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
					
					String url = esty_parent_url + "listings/"+listingid+"/inventory";		
					HttpHeaders headers = new HttpHeaders();
					headers.add("charset", "utf-8");
					headers.add("x-api-key", client_id);
					headers.add("Authorization", "Bearer " + newaccess_token);
					HttpEntity<String> entity = new HttpEntity<String>(headers);
					
					ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
						 
					return ResponseEntity.ok(response.getBody());
					
				} catch (HttpClientErrorException ex) {
					var error = new Gson().fromJson(ex.getResponseBodyAsString(), _Error.class);
					Response resp = new Response(1002,
						"Some internal error occured, while generate new esty refresh token!", null, null, error, null,
						null,null);
					return new ResponseEntity<Response>(resp, ex.getStatusCode());
				}
			} catch (Exception e) {
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

   

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("listingid") String listingid,@RequestParam("shopid") String shopid) {
        String fileName = fileStorageService.storeFile(file, listingid, shopid);

        String fileDownoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/auth/downloadFile/")
                .path("/" + shopid + "/" + listingid + "/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName,fileDownoadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadVectorFiles")
    public List<UploadFileResponse> uploadVectorFiles(@RequestParam("files") MultipartFile[] files,@RequestParam("listingid") String listingid,@RequestParam("shopid") String shopid) {
    	ArrayList<String> filename = new ArrayList<String>();
        for(int i=0;i<files.length;i++)
        {
        	filename.add(files[i].getOriginalFilename());
        }        

        if(vectorfilesRepository.existsBylistingid(listingid)!=true)
        {
        	VectorFiles vertorfiles=new VectorFiles(shopid,listingid,filename);
            vectorfilesRepository.save(vertorfiles);
        }
        
    	return Arrays.asList(files).stream().map(file -> uploadFile(file, listingid, shopid)).collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{listingid}/{shopid}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String listingid,@PathVariable String shopid,@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName, listingid, shopid);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}

