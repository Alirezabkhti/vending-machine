package com.vub.assessment.vendingmachine.presentation.exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = IllegalStateException.class)
	protected ResponseEntity<Object> handle(IllegalStateException ex) {
		return  ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
			new ExceptionDTO(ex.getMessage())
		);
	}

	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<Object> handle(Exception ex) {
		return  ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
				new ExceptionDTO("something went wrong!")
		);
	}
}
