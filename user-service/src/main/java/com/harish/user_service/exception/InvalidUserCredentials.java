package com.harish.user_service.exception;

public class InvalidUserCredentials extends RuntimeException {
	

	public InvalidUserCredentials(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
	
	

}
