package com.app.movile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.movile.coupon.FixedPriceBehaviorAction;

public interface FixedPriceBehaviorActionRepository extends JpaRepository<FixedPriceBehaviorAction, String> {
	
	public FixedPriceBehaviorAction findByCouponCode(String code);
}
