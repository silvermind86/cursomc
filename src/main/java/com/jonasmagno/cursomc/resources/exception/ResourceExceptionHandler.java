package com.jonasmagno.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jonasmagno.cursomc.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request){
		
		 StandardError se = new StandardError(
			 HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(se);
	}
}
