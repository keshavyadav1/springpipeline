package com.nagarro.advice;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.nagarro.exception.CustomException;
import com.nagarro.model.ErrorDto;

@RestControllerAdvice
public class ExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
	public ErrorDto sizeExceptionHandler(CustomException cx) {
		ErrorDto er= new ErrorDto();
		er.setMessage(cx.getMessage());
		er.setCode(400);
		er.setTimestamp(dateandtime());
		return er;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ErrorDto allExceptionHandler(Exception ex) {
		ErrorDto er= new ErrorDto();
		er.setMessage(ex.getMessage());
		er.setCode(500);
		er.setTimestamp(dateandtime());
		return er;
		
	}
	
	private String dateandtime() {
		// TODO Auto-generated method stub
		GregorianCalendar calendar =new GregorianCalendar();
		SimpleDateFormat format=new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
		format.setCalendar(calendar);
		return format.format(calendar.getTime());
	}
}
