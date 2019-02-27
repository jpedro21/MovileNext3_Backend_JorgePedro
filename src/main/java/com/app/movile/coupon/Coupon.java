package com.app.movile.coupon;

import java.util.List;

import org.joda.time.LocalDate;

import com.app.movile.entity.Cart;

public interface Coupon {
	
	public String getCode();
	public LocalDate getIniDate();
	public LocalDate getEndDate();
	public String getText();
	public void addRestriction(Restriction restriction);
	public void setRestrictions(List<Restriction> restrictions);
	public void setAction(Action action);
	public Cart processCoupon(Cart cart);
	public Cart removeCoupon(Cart cart);
}
