package com.app.movile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.movile.business.CouponBusiness;
import com.app.movile.dto.CouponDto;

@Service
public class CouponService {
	
	@Autowired
	private CouponBusiness business;
	
	public void create(CouponDto coupon) {
		business.create(coupon);
	}

	public void apply(String couponCode, Long idCart) {
		business.applyCoupon(couponCode, idCart);
	}
}
