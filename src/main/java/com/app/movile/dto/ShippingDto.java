package com.app.movile.dto;

import java.math.BigDecimal;

public class ShippingDto {
	
	//TODO: Criar outros atributos pertinentes ao Shipping
	private BigDecimal vlrShipping;

	public BigDecimal getVlrShipping() {
		return vlrShipping;
	}

	public void setVlrShipping(BigDecimal vlrShipping) {
		this.vlrShipping = vlrShipping;
	}
}
