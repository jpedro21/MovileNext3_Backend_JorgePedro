package com.app.movile.coupon;

import com.app.movile.entity.Cart;

public interface Restriction {
	
	public boolean validate(Cart cart); 
}
