package com.alpidi.controller;


import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpidi.model.User;
import com.alpidi.payload.request.LoginRequest;
import com.alpidi.payload.request.SignupRequest;
import com.alpidi.payload.response.JwtResponse;
import com.alpidi.payload.response.MessageResponse;
import com.alpidi.repository.RoleRepository;
import com.alpidi.repository.UserRepository;
import com.alpidi.repository.EtsyRepository;
import com.alpidi.security.jwt.JwtUtils;
import com.alpidi.security.sevices.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	EtsyRepository etsyRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) 
	{
		try {
		Optional<User> userData = userRepository.findByEmail(loginRequest.getEmail());
		if(userData.get().getProvider()=="GOOGLE")
		{
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), ""));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			
			String jwt = jwtUtils.generateJwtToken(authentication);
			
			return ResponseEntity.ok(
					new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(), userDetails.getEmail()));
		}
		else
		{
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
						
			String jwt = jwtUtils.generateJwtToken(authentication);
			
			return ResponseEntity.ok(
					new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(), userDetails.getEmail()));
		}


		}
		catch(Exception ex) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email or Password is incorrect!"));
		}
	}
	
	@PostMapping("/me")
	public ResponseEntity<?> userinfo(@Valid@RequestBody String token) {
		if(jwtUtils.validateJwtToken(token))
		{
			Optional<User> userData = userRepository.findByEmail(jwtUtils.getUserNameFromJwtToken(token));

			return ResponseEntity.ok(userData);
		}
		else
		{
			return ResponseEntity.badRequest().body(new MessageResponse("User Authentication is failed!"));
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		System.out.println(signUpRequest.getProvider());
		var provider=signUpRequest.getProvider();
		if(provider==null || provider.indexOf("GOOGLE") <= -1)
		{	
			System.out.println("Not Google");
			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
			}
			User user = new User(signUpRequest.getFirstname(),signUpRequest.getLastname(), signUpRequest.getEmail(),
					encoder.encode(signUpRequest.getPassword()),signUpRequest.getRole(),signUpRequest.getPhoto(),signUpRequest.getProvider(),signUpRequest.getAuthtoken(),signUpRequest.getSocialid());

			
			userRepository.save(user);
			
			try {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
								
				String jwt = jwtUtils.generateJwtToken(authentication);
				
				return ResponseEntity.ok(
						new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(), userDetails.getEmail()));
				}
				catch(Exception ex) {
					return ResponseEntity.badRequest().body(new MessageResponse("Problem in register user!"));
				}
		}
		else
		{
			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
								
				String jwt = jwtUtils.generateJwtToken(authentication);
				
				return ResponseEntity.ok(
						new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(), userDetails.getEmail()));
			}
			else
			{
				User user = new User(signUpRequest.getFirstname(),signUpRequest.getLastname(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()),signUpRequest.getRole(),signUpRequest.getPhoto(),signUpRequest.getProvider(),signUpRequest.getAuthtoken(),signUpRequest.getSocialid());

				userRepository.save(user);
				
				try {
					Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

					SecurityContextHolder.getContext().setAuthentication(authentication);

					UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
					
					String jwt = jwtUtils.generateJwtToken(authentication);
					
					return ResponseEntity.ok(
							new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(), userDetails.getEmail()));
					}
					catch(Exception ex) {
						return ResponseEntity.badRequest().body(new MessageResponse("Problem in register user!"));
					}
			}
			
		}
			
			
		

	}
}
