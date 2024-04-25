package com.casumo.videorental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casumo.videorental.model.User;
import com.casumo.videorental.security.JwtService;
import com.casumo.videorental.security.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/video-rental-store/api/v1/auth")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;

	@PostMapping("/register-user")
	public ResponseEntity<String> registerUser(@RequestBody User user){
		log.info("Before register new USER!");
		String newUser = userService.addUser(user);
		log.info("After register new USER!");
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user){
		Authentication auth = authenticationManager.authenticate(
								new UsernamePasswordAuthenticationToken(
										user.getUsername(), user.getPassword())); 
		if(auth.isAuthenticated()) {
			String jwtToken = jwtService.generateToken(user.getUsername());
			return new ResponseEntity<>(jwtToken, HttpStatus.OK);
		}
		else
			throw new UsernameNotFoundException("Invalid User or Password!");
	}
}
