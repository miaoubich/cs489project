package com.casumo.videorental.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VideoCopyExceptionHandler {

	@ExceptionHandler(VideoCopyServiceCustomException.class)
	public ResponseEntity<ErrorMessage> handleVideoCopyException(VideoCopyServiceCustomException exception){
		return new ResponseEntity<>(ErrorMessage.builder()
						.errorCode(exception.getErrorCode())
						.errorMassage(exception.getMessage())
						.build(), exception.getStatus());
	}
}
