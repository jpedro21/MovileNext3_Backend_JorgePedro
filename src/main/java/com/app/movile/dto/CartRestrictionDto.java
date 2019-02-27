package com.app.movile.dto;

import java.math.BigDecimal;

public class CartRestrictionDto {
	
	private BigDecimal cartPrice;

	public BigDecimal getCartPrice() {
		return cartPrice;
	}

	public void setCartPrice(BigDecimal cartPrice) {
		this.cartPrice = cartPrice;
	}
}
