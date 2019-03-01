package com.app.movile.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cart {
	
	@Id
	@Column
	private Long id;
	@OneToMany(cascade=CascadeType.ALL)
	private List<ItemCart> items;
	@Column
	private BigDecimal discount;
	@OneToOne(cascade=CascadeType.ALL)
	private Shipping shipping;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ItemCart> getItems() {
		return items;
	}

	public void setItems(List<ItemCart> items) {
		this.items = items;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		if(discount.compareTo(new BigDecimal(0)) < 0) {
			throw new IllegalArgumentException("O disconto deve ser maior que zero.");
		}
		
		this.discount = discount;
	}
	
	public BigDecimal getCartPrice() {
		BigDecimal sum = new BigDecimal(0);
		
		for(ItemCart item : items) {
			sum = sum.add(item.getProduct().getPrice());
		}
		
		return sum;
	}
	
	public boolean hasProducts() {
		return items.size() > 0;
	}
}
