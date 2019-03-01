package com.app.movile.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
	
	private List<ItemCartDto> items;
	private BigDecimal discount;
	private ShippingDto shipping;
	
	public List<ItemCartDto> getItems() {
		return items;
	}
	public void setItems(List<ItemCartDto> items) {
		this.items = items;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public ShippingDto getShipping() {
		return shipping;
	}
	public void setShipping(ShippingDto shipping) {
		this.shipping = shipping;
	}
	
	public void setDiscount(BigDecimal discount) {
		if(discount.compareTo(new BigDecimal(0)) < 0) {
			throw new IllegalArgumentException("O disconto deve ser maior que zero.");
		}
		
		this.discount = discount;
	}
	
	public BigDecimal getCartPrice() {
		BigDecimal sum = new BigDecimal(0);
		
		for(ItemCartDto item : items) {
			sum.add(item.getProduct().getPrice());
		}
		
		return sum;
	}
	
	public boolean hasProducts() {
		return items.size() > 0;
	}
}
