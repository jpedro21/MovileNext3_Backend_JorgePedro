package com.app.movile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.movile.coupon.Coupon;
import com.app.movile.coupon.CouponImpl;

public interface CouponRepository extends JpaRepository<Coupon, String> {
	
	public CouponImpl findByCode(String code);
}
