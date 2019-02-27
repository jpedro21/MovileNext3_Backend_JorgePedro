package com.app.movile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.movile.coupon.CartBehaviorRestriction;

public interface CartBehaviorRestrictionRepository extends JpaRepository<CartBehaviorRestriction, String> {

	public CartBehaviorRestriction findByCouponCode(String code);

}
