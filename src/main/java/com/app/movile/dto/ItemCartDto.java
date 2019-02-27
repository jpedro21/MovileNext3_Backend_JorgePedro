package com.app.movile.dto;

public class ItemCartDto {
	
	private Integer qtd;
	private ProductDto product;
	
	public Integer getQtd() {
		return qtd;
	}
	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
	public ProductDto getProduct() {
		return product;
	}
	public void setProduct(ProductDto product) {
		this.product = product;
	}
}
