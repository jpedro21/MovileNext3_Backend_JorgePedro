package com.app.movile.coupon;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.joda.time.LocalDate;

import com.app.movile.entity.Cart;
import com.app.movile.exception.BusinessException;

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
	
	public CouponImpl(String code, LocalDate iniDate, LocalDate endDate, String text) {
		
		if(iniDate == null || endDate == null) {
			throw new IllegalArgumentException("As datas de início e fim são obrigatórias.");
		}
		
		if(this.iniDate.compareTo(endDate) > 0) {
			throw new IllegalArgumentException("A data inicial não pode ser menor que a final.");
		}
		
		if(this.endDate.compareTo(iniDate) < 0) {
			throw new IllegalArgumentException("A data final não pode ser menor que a data inicial.");
		}
		
		this.endDate = endDate;
		this.iniDate = iniDate;
		this.text = text;
	}
	
	/**
	 * O Action deve ser setado em tempo de execução
	 */
	private Action action;

	/**
	 * A Restrição deve ser setado em tempo de execução
	 * Devem ser adicionadas as regras mais triviais em primeiro
	 * para contribuir com a performance
	 */
	private List<Restriction> restrictions;
	
	public void setCode(String code) {
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

	@Override
	public Cart processCoupon(Cart cart) {
		if(validate(cart)) {
			return action.applyAction(cart);
		} else {
			return cart;
		}
	}
	
	@Override
	public Cart removeCoupon(Cart cart) {
		return action.removeAction(cart);
	}

	private boolean validate(Cart cart) {
		
		if(!cart.hasProducts()) {
			throw new BusinessException("Não existe item na sacola.");
		}
		
		for(Restriction r : restrictions) {
			if(!r.validate(cart)) return false;
		}
		return true;
	}
}
