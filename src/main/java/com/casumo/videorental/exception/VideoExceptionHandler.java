package com.casumo.videorental.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VideoExceptionHandler {

	@ExceptionHandler(VideoServiceCustomException.class)
	public ResponseEntity<ErrorMessage> handleVideoCopyException(VideoServiceCustomException exception){
		return new ResponseEntity<>(ErrorMessage.builder()
						.errorCode(exception.getErrorCode())
						.errorMassage(exception.getMessage())
						.build(), exception.getStatus());
	}
}
