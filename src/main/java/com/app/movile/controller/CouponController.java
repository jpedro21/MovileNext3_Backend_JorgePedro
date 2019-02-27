package com.app.movile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.movile.dto.CouponDto;
import com.app.movile.service.CouponService;

@RestController
@RequestMapping("/coupon")
public class CouponController {
	
	@Autowired
	private CouponService service;
	
	@PostMapping("/create")
	public RequestEntity<Void> create(@RequestBody CouponDto coupon) {
		
		service.create(coupon);
	
		return null;
	}
}
