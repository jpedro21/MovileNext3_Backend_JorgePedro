package com.app.movile.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.app.movile.dto.ErrorDto;

@Component
public class ErrorDtoBuilder {
	
	public List<ErrorDto> constructErrorDto(Exception exception) {
		
		return Arrays.asList(new ErrorDto(exception.getMessage(), 
					 System.currentTimeMillis()));
	}
}
