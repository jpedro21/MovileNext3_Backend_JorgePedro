package com.app.movile.business;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.movile.coupon.CartBehaviorRestriction;
import com.app.movile.coupon.CouponImpl;
import com.app.movile.coupon.FixedPriceBehaviorAction;
import com.app.movile.dto.ActionDto;
import com.app.movile.dto.CouponDto;
import com.app.movile.dto.RestrictionDto;
import com.app.movile.entity.Cart;
import com.app.movile.entity.ItemCart;
import com.app.movile.entity.Product;
import com.app.movile.entity.Shipping;
import com.app.movile.repository.CartBehaviorRestrictionRepository;
import com.app.movile.repository.CartRepository;
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
	
	@Autowired
	private CartRepository cartRepo; 
	
	/*
	 * 
	 * Caso acontece alguma exceção do cadastro do cupom, restrição 
	 * ou ação toda a transação deverá ser revertida 
	 */
	
	//TODO: Possivelmente este metodo ficará gigante, tentar melhorar; 
	@Transactional
	public void create(CouponDto couponDto) {
		
		/**
		 * 
		 * Salva cupom. O cupom deve ser salvo antes da restrição
		 * e ação.
		 */
		CouponImpl coupon = new CouponImpl(couponDto.getCode(), 
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
				CartBehaviorRestriction cartRestr = new CartBehaviorRestriction(coupon, 
																				restrictions.getCartRestriction().getMinimumCartPrice());
				cartRestrRepo.save(cartRestr);
			}
		}
		
		/**
		 * 
		 * Salva ação do cupom
		 */
		
		ActionDto action = couponDto.getAction();
		if(action.getFixedPriceAction() != null) {
			FixedPriceBehaviorAction fixedPriceAct = new FixedPriceBehaviorAction(coupon, 
																				  action.getFixedPriceAction().getDiscount());
			fixedPriceActRepo.save(fixedPriceAct);
		}
	}
	
	public void applyCoupon(String code, Long idCart) {
		
		CouponImpl coupon = couponRepo.findByCode(code);
		
		if(coupon == null) {
			throw new IllegalArgumentException("Cupom não encontrado.");
		}
		
		coupon.setRestrictions(Arrays.asList(cartRestrRepo.findByCouponCode(code)));
		coupon.setAction(fixedPriceActRepo.findByCouponCode(code));

		Cart cart = cartRepo.findOneById(idCart);
		coupon.processCoupon(cart, cartRepo);
	}
	
}
