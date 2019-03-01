package com.app.movile.coupon;

import com.app.movile.entity.Cart;
import com.app.movile.repository.CartRepository;

public interface Action {
	
	/*
	 * Toda a ação altera alguma informação do carrinho de compras
	 * portanto devemos passar um repositorio injetado para que 
	 * possamos manipularmos estas informações.
	 */
	public void applyAction(Cart cart, CartRepository cartRepo);
	public void removeAction(Cart cart, CartRepository cartRepo);
}
