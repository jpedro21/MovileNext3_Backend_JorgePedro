package com.app.movile.coupon;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.app.movile.entity.Cart;

@Entity
public class FixedPriceBehaviorAction extends AbstractCouponBehavior implements Action {
	
	public FixedPriceBehaviorAction(String couponCode, BigDecimal discount) {
		super(couponCode);
		this.discount = discount;
	}

	@Column
	private BigDecimal discount;
	
	@Override
	public Cart applyAction(Cart cart) {
		cart.setDiscount(discount);
		return cart;
	}

	@Override
	public Cart removeAction(Cart cart) {
		cart.setDiscount(new BigDecimal(0));
		return cart;
	}
}
