package com.harish.user_service.controller;

import com.harish.user_service.exception.UserExistException;
import com.harish.user_service.model.AuthRequest;
import com.harish.user_service.model.AuthResponse;
import com.harish.user_service.model.User;
import com.harish.user_service.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

	@Autowired
    private UserService userService;

    @PostMapping("/login")
    public AuthResponse userLogin(@RequestBody AuthRequest authRequest) {
    	log.info("Logging in user request");
        return userService.checkCredentials(authRequest);
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestBody User user ) throws UserExistException {
    	log.info("Registering user: {}", user.getUsername());
    	return userService.register(user);
    }
    
    @GetMapping("/validate")
    public String validateToken(@RequestHeader(name = "Authorization") String authToken) {
    	log.debug("Validating token: {}",authToken);
    	String token = authToken.startsWith("Bearer ")?authToken.substring(7):authToken;
    	if(userService.validateToken(token)) 
    		return "Token is valid!";
    	else
    		return "Token is invalid!";
    }
}
