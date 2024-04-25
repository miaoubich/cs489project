//package com.casumo.videorental.exception;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class HandleRentalException extends ResponseEntityExceptionHandler{
//
//	@ExceptionHandler(RentalServiceCustomException.class)
//	public ResponseEntity<ErrorMessage> handlePatientException(RentalServiceCustomException exception){
//		return new ResponseEntity<>(ErrorMessage.builder()
//								.errorCode(exception.getErrorCode())
//								.errorMassage(exception.getMessage())
//								.build(), exception.getStatus());
//	}
//}
