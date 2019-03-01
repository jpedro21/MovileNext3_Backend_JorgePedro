package com.app.movile.coupon;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.movile.entity.Cart;
import com.app.movile.repository.CartRepository;

@Entity
@Component
public class FixedPriceBehaviorAction extends AbstractCouponBehavior implements Action {
	
	public FixedPriceBehaviorAction() {
		
	}
	
	public FixedPriceBehaviorAction(CouponImpl coupon, BigDecimal discount) {
		super(coupon);
		this.discount = discount;
	}

	@Column
	private BigDecimal discount;
	
	@Override
	public void applyAction(Cart cart, CartRepository cartRepo) {
		cart.setDiscount(discount);
		cartRepo.save(cart);
	}

	@Override
	public void removeAction(Cart cart, CartRepository cartRepo) {
		cart.setDiscount(new BigDecimal(0));
		cartRepo.save(cart);
	}
}
