package com.app.movile.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.movile.dto.CouponDto;
import com.app.movile.service.CouponService;

@RestController
@RequestMapping("/coupon")
public class CouponController {
	
	@Autowired
	private CouponService service;
	
	@PostMapping("/create")
	public ResponseEntity<Void> create(@RequestBody CouponDto coupon) {
		
		service.create(coupon);
		
		return ResponseEntity.created(null).build();
	}
	
	@PostMapping("/apply/{couponCode}/{idCart}")
	public ResponseEntity<Void>apply(@PathVariable String couponCode, @PathVariable Long idCart) {
		
		service.apply(couponCode, idCart);
		
		return ResponseEntity.created(null).build();
	}
}
