package com.app.movile.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Shipping {
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	//TODO: Criar outros atributos pertinentes ao Shipping
	private BigDecimal vlrShipping;
		
	public BigDecimal getVlrShipping() {
		return vlrShipping;
	}

	public void setVlrShipping(BigDecimal vlrShipping) {
		this.vlrShipping = vlrShipping;
	}
}
