package com.emp.exception;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EmployeeNameNotFoudException.class)
	public ResponseEntity<ErrorResponse> handleEmployeeNotfound(EmployeeNameNotFoudException ex){
		ErrorResponse error=new ErrorResponse();
		error.setMsg(ex.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());  
       String timestamp = ts.toString();
		error.setTimestamp(timestamp);
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SupervisorNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSupervisorNotfound(SupervisorNotFoundException ex){
		ErrorResponse error=new ErrorResponse();
		error.setMsg(ex.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());  
       String timestamp = ts.toString();
		error.setTimestamp(timestamp);
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}

}
