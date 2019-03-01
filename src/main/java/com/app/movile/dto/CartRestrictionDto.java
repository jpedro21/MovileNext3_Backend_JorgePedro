package com.app.movile.dto;

import java.math.BigDecimal;

public class CartRestrictionDto {
	
	private BigDecimal minimumCartPrice;

	public BigDecimal getMinimumCartPrice() {
		return minimumCartPrice;
	}

	public void setMinimumCartPrice(BigDecimal minimumCartPrice) {
		this.minimumCartPrice = minimumCartPrice;
	}
}
