package com.app.movile.business;

import java.util.Arrays;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.movile.coupon.CartBehaviorRestriction;
import com.app.movile.coupon.Coupon;
import com.app.movile.coupon.CouponImpl;
import com.app.movile.coupon.FixedPriceBehaviorAction;
import com.app.movile.dto.ActionDto;
import com.app.movile.dto.CartDto;
import com.app.movile.dto.CouponDto;
import com.app.movile.dto.FixedPriceActionDto;
import com.app.movile.dto.RestrictionDto;
import com.app.movile.entity.Cart;
import com.app.movile.repository.CartBehaviorRestrictionRepository;
import com.app.movile.repository.CouponRepository;
import com.app.movile.repository.FixedPriceBehaviorActionRepository;

@Component
public class CouponBusiness {
	
	@Autowired
	private CouponRepository couponRepo;
	
	@Autowired
	private CartBehaviorRestrictionRepository cartRestrRepo;
	
	@Autowired
	private FixedPriceBehaviorActionRepository fixedPriceActRepo;
	
	/**
	 * 
	 * Caso acontece alguma exceção do cadastro do cupom, restrição 
	 * ou ação toda a transação deverá ser revertida 
	 */
	
	//TODO: Possivelmente este metodo ficará gigante, tentar melhorar; 
	@Transactional(propagation=Propagation.MANDATORY)
	public void create(CouponDto couponDto) {
		
		/**
		 * 
		 * Salva cupom. O cupom deve ser salvo antes da restrição
		 * e ação.
		 */
		Coupon coupon = new CouponImpl(couponDto.getCode(), 
									   couponDto.getIniDate(), 
									   couponDto.getEndDate(),
									   couponDto.getText());
		couponRepo.save(coupon);
		
		/**
		 * 
		 * Salva restrição do cupom
		 */
		
		RestrictionDto restrictions = couponDto.getRestrictions();
		if(restrictions != null) {
			if(restrictions.getCartRestriction() != null) {
				CartBehaviorRestriction cartRestr = new CartBehaviorRestriction(coupon.getCode(), 
																				restrictions.getCartRestriction().getCartPrice());
				cartRestrRepo.save(cartRestr);
			}
		}
		
		/**
		 * 
		 * Salva ação do cupom
		 */
		
		ActionDto action = couponDto.getAction();
		if(action.getFixedPriceAction() != null) {
			FixedPriceBehaviorAction fixedPriceAct = new FixedPriceBehaviorAction(couponDto.getCode(), 
																				  action.getFixedPriceAction().getDiscount());
			fixedPriceActRepo.save(fixedPriceAct);
		}
	}
	
	public CartDto applyCoupon(String code, CartDto cartDto) {
		
		Coupon coupon = couponRepo.findByCode(code);
		coupon.setRestrictions(Arrays.asList(cartRestrRepo.findByCouponCode(code)));
		coupon.setAction(fixedPriceActRepo.findByCouponCode(code));
		
		ModelMapper mapper = new ModelMapper();
		Cart cart = coupon.processCoupon(mapper.map(cartDto, Cart.class));
		return mapper.map(cart, CartDto.class);
	}
	
}
