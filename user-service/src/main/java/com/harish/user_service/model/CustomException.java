package com.harish.user_service.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CustomException {
	
	private String message;
}
