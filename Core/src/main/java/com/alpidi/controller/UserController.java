package com.alpidi.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpidi.model.ERole;
import com.alpidi.model.Role;
import com.alpidi.model.User;
import com.alpidi.payload.request.ForgetPasswordRequest;
import com.alpidi.payload.request.SignupRequest;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.UserRepository;
import com.alpidi.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@PostMapping("/getuserdetailsfromAuthtoken")
	public ResponseEntity<?> userinfo(@Valid@RequestBody User User) {
		if(jwtUtils.validateJwtToken(User.getAuthtoken()))
		{
			var email = jwtUtils.getUserNameFromJwtToken(User.getAuthtoken());
			Optional<User> userData = userRepository.findByEmail(email);
			return ResponseEntity.ok(userData);
		}
		else
		{
			return ResponseEntity.badRequest().body(new MessageResponse("User Authentication is failed!"));
		}
	}
	
	void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("menil4gvm@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);

    }
	
	@GetMapping("/getalluser")
	public List < User > getalluser() {
		List<User> userData = userRepository.findByRoleNotAndIsdeleted("admin",false);		
	
		return userData;
	}
	
	
	@GetMapping("/users")
    public List < User > getUsers() {
        return this.userRepository.findAll();
    }

	@PostMapping("/deleteuser")
	public ResponseEntity<?> deleteTutorial(@RequestParam("id") String id) {
	  try {
		  //userRepository.deleteById(id);
		  System.out.println(id);
		  Optional<User> userData = userRepository.findById(id);

		  User _user = userData.get();
		  _user.setIsdelete(true);
		  userRepository.save(_user);
		    
		  return ResponseEntity.badRequest().body(new MessageResponse("User Deleted."));
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
	  Optional<User> userData = userRepository.findById(id);

	  if (userData.isPresent()) {
	    return new ResponseEntity<>(userData.get(), HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}
	
	@PostMapping("/forgetpassword")
	public ResponseEntity<?> forgetpassword(@Valid @RequestBody ForgetPasswordRequest ForgetPasswordRequest) {
		

		if (userRepository.existsByEmail(ForgetPasswordRequest.getEmail())) {
			sendEmail();
			return ResponseEntity.badRequest().body(new MessageResponse("Email exist, Email send."));
		}
		else
		{
			return ResponseEntity.ok(new MessageResponse("Email does not exist!"));
		}
	}
	
	@PostMapping("/resetpassword/{id}")
	public ResponseEntity<User> updateTutorial(@PathVariable("id") String id, @RequestBody User user) {
	  Optional<User> userData = userRepository.findById(id);

	    User _user = userData.get();
	    _user.setPassword(encoder.encode(user.getPassword()));
	    return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
	}

}
