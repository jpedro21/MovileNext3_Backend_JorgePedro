package com.app.movile.coupon;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.joda.time.LocalDate;

import com.app.movile.entity.Cart;
import com.app.movile.exception.BusinessException;
import com.app.movile.exception.CouponBusinessException;
import com.app.movile.repository.CartRepository;

@Entity
public class CouponImpl implements Coupon {
	
	@Id
	@Column
	private String code;
	@Column
	private LocalDate iniDate;
	@Column
	private LocalDate endDate;
	@Column
	private String text;
	
	public CouponImpl() {
		
	}
	
	public CouponImpl(String code, LocalDate iniDate, LocalDate endDate, String text) {
		setCode(code);
		setIniDate(iniDate);
		setEndDate(endDate);
		this.text = text;
	}
	
	/*
	 * O Action deve ser setado em tempo de execução
	 */
	@Transient
	private Action action;

	/*
	 * A Restrição deve ser setado em tempo de execução
	 * Devem ser adicionadas as regras mais triviais em primeiro
	 * para contribuir com a performance
	 */
	@Transient
	private List<Restriction> restrictions;
	
	public void setCode(String code) {
		
		if(code == null) {
			throw new IllegalArgumentException("O código do cupom é uma informação obrigatória.");
		}
				
		this.code = code;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public void setIniDate(LocalDate iniDate) {
		if(this.iniDate != null && iniDate.compareTo(endDate) > 0) {
			throw new IllegalArgumentException("A data inicial não pode ser menor que a final.");
		}
		
		this.iniDate = iniDate;
	}
	 
	public void setEndDate(LocalDate endDate) {
		if(this.endDate != null && endDate.compareTo(iniDate) < 0) {
			throw new IllegalArgumentException("A data final não pode ser menor que a data inicial.");
		}
		
		this.endDate = endDate;
	}
	
	@Override
	public void addRestriction(Restriction couponRtn) {
		this.restrictions.add(couponRtn);
	}

	@Override
	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
		
	}

	@Override
	public void setAction(Action action) {
		this.action = action;
	}
	
	public String getCode() {
		return code;
	}

	public LocalDate getIniDate() {
		return iniDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getText() {
		return text;
	}
	
	/*
	 * Toda a ação altera alguma informação do carrinho de compras
	 * portanto devemos passar um repositorio injetado para que 
	 * possamos manipularmos estas informações.
	 */
	@Override
	public void processCoupon(Cart cart, CartRepository cartRepo) {
		
		/*
		 * Não deve existir um cupom sem ação
		 */
		if(action == null) {
			throw new CouponBusinessException("Erro ao processar coupon");
		}
		
		if(validate(cart)) {
			action.applyAction(cart, cartRepo);
		}
	}
	
	@Override
	public void removeCoupon(Cart cart, CartRepository cartRepo) {
		action.removeAction(cart, cartRepo);
	}

	private boolean validate(Cart cart) {
		
		if(iniDate.compareTo(LocalDate.now()) > 0 || endDate.compareTo(LocalDate.now()) < 0) {
			throw new CouponBusinessException("Cupom fora da data de vigência");
		}
		
		if(!cart.hasProducts()) {
			throw new CouponBusinessException("Não existe item na sacola.");
		}
		
		for(Restriction r : restrictions) {
			if(r != null && !r.validate(cart)) return false;
		}
		return true;
	}
}
