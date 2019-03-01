package com.app.movile.coupon;

import java.util.List;

import org.joda.time.LocalDate;

import com.app.movile.entity.Cart;
import com.app.movile.repository.CartRepository;

public interface Coupon {
	
	public String getCode();
	public LocalDate getIniDate();
	public LocalDate getEndDate();
	public String getText();
	public void addRestriction(Restriction restriction);
	public void setRestrictions(List<Restriction> restrictions);
	public void setAction(Action action);
	public void processCoupon(Cart cart, CartRepository cartRepo);
	public void removeCoupon(Cart cart, CartRepository cartRepo);
}
