package com.harish.user_service.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.harish.user_service.model.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(exception = InvalidUserCredentials.class)
	ResponseEntity<Object> handleException(InvalidUserCredentials e, WebRequest request) {
		CustomException customException = new CustomException();
		customException.setMessage(e.getMessage());
        return super.handleExceptionInternal(e, customException, 
          new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}
	
	@ExceptionHandler(exception = UserExistException.class)
	ResponseEntity<Object> handleException(UserExistException e, WebRequest request) {
		CustomException customException = new CustomException();
		customException.setMessage(e.getMessage());
        return super.handleExceptionInternal(e, customException, 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
