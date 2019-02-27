package com.app.movile.coupon;

import com.app.movile.entity.Cart;

public interface Action {
	
	public Cart applyAction(Cart cart);
	public Cart removeAction(Cart cart);
}
