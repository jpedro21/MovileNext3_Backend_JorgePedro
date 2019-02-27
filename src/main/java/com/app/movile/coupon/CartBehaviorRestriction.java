package com.app.movile.coupon;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.app.movile.entity.Cart;
import com.app.movile.exception.CouponBusinessException;

@Entity
public class CartBehaviorRestriction extends AbstractCouponBehavior implements Restriction {
	
	@Column
	private BigDecimal minimumCartPrice;
	
	public CartBehaviorRestriction() {
		
	}
	
	public CartBehaviorRestriction(String coupon, BigDecimal cartPrice) {
		super(coupon);

		if(cartPrice == null) {
			throw new IllegalArgumentException("O valor desta restrição não pode ser nula.");
		}
		
		this.minimumCartPrice = cartPrice;
	}
	
	public BigDecimal getMinimumCartPrice() {
		return minimumCartPrice;
	}

	@Override
	public boolean validate(Cart cart) {
		if(cart.getCartPrice().compareTo(minimumCartPrice) >= 0 ) {
			return true;
		} else {
			throw new CouponBusinessException("O valor mínimo para esta promoção é de: R$" + minimumCartPrice.toString());
		}
	}
}
