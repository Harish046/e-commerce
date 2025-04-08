package com.harish.user_service.service;

import com.harish.user_service.exception.InvalidUserCredentials;
import com.harish.user_service.exception.UserExistException;
import com.harish.user_service.model.AuthRequest;
import com.harish.user_service.model.AuthResponse;
import com.harish.user_service.model.User;
import com.harish.user_service.repository.UserRepository;
import com.harish.user_service.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
    private UserRepository userRepository;
    
	@Autowired
    private PasswordEncoder passwordEncoder;
    
	@Autowired
    private JwtUtil jwtUtil;

    public String register(User user) throws UserExistException {
    	
    	if(userRepository.findByUsername(user.getUsername()).isPresent()) {
    		throw new UserExistException("User already exist");
    	}
    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	userRepository.save(user);
    	
        return "User registered successfully";
    }



	public AuthResponse checkCredentials(AuthRequest authRequest) {
		User user = userRepository.findByUsername(authRequest.getUsername())
				.orElseThrow(() ->new InvalidUserCredentials("Invalid credentials"));
		if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
			throw new InvalidUserCredentials("Invalid credentials");
		}
		
		String token = jwtUtil.generateToken(authRequest.getUsername());
		return new AuthResponse(token);
	}
	
	public boolean validateToken(String token) {
		return jwtUtil.validateToken(token);
	}
    
    
}
