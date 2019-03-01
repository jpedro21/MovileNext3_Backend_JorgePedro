package com.app.movile.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.movile.dto.ErrorDto;
import com.app.movile.exception.BusinessException;
import com.app.movile.exception.CouponBusinessException;
import com.app.movile.util.ErrorDtoBuilder;


@RestControllerAdvice
public class ResourceHandler {
	
	@Autowired
	private ErrorDtoBuilder builder;
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<List<ErrorDto>> handleContaNaoEncontrada(IllegalArgumentException e) {
		return defaultResponseEntity(e, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CouponBusinessException.class)
	public ResponseEntity<List<ErrorDto>> handleContaCamposRequiridos(CouponBusinessException e) {
		return defaultResponseEntity(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<List<ErrorDto>> handleContaParametroInvalido(BusinessException e) {
		return defaultResponseEntity(e, HttpStatus.BAD_REQUEST);
	}
	
	private ResponseEntity<List<ErrorDto>> defaultResponseEntity(Exception e, HttpStatus httpStatus) {
		List<ErrorDto> erro = builder.constructErrorDto(e);
		return ResponseEntity.status(httpStatus).body(erro);
	}
}
