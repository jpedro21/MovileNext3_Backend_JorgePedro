package com.app.movile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.movile.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {
	
	public Cart findOneById(Long idCart);
}
