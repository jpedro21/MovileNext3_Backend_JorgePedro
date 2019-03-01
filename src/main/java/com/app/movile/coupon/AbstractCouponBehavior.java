package com.app.movile.coupon;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class AbstractCouponBehavior {
	
	@Id
	@Column
	protected String couponCode;
	
	@OneToOne
	@JoinColumn
	@MapsId
	protected CouponImpl coupon;
	
	public AbstractCouponBehavior() {
		
	}
	
	public AbstractCouponBehavior(CouponImpl coupon) {
		this.couponCode = coupon.getCode();
		this.coupon = coupon;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public Coupon getCoupon() {
		return coupon;
	}

}
