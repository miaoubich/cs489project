package com.casumo.videorental.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandler {

	@ExceptionHandler(CustomerServiceCustomException.class)
	public ResponseEntity<ErrorMessage> handlePatientException(CustomerServiceCustomException exception){
		return new ResponseEntity<>(ErrorMessage.builder()
								.errorCode(exception.getErrorCode())
								.errorMassage(exception.getMessage())
								.build(), exception.getStatus());
	}
}
