package com.app.movile;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.movile.coupon.CartBehaviorRestriction;
import com.app.movile.coupon.CouponImpl;
import com.app.movile.coupon.FixedPriceBehaviorAction;
import com.app.movile.dto.ActionDto;
import com.app.movile.dto.CartRestrictionDto;
import com.app.movile.dto.CouponDto;
import com.app.movile.dto.FixedPriceActionDto;
import com.app.movile.dto.RestrictionDto;
import com.app.movile.entity.Cart;
import com.app.movile.entity.ItemCart;
import com.app.movile.entity.Product;
import com.app.movile.entity.Shipping;
import com.app.movile.repository.CartBehaviorRestrictionRepository;
import com.app.movile.repository.CartRepository;
import com.app.movile.repository.CouponRepository;
import com.app.movile.repository.FixedPriceBehaviorActionRepository;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovileNext3BackendJorgePedroApplicationTests {
	
	@Autowired
	private CouponRepository couponRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private FixedPriceBehaviorActionRepository fixedPriceRepo;
	
	@Autowired
	private CartBehaviorRestrictionRepository cartRestrRepo;
	
	@LocalServerPort
	private int port;
	
	@After
	public void cleanUp() {
		fixedPriceRepo.deleteAll();
		cartRestrRepo.deleteAll();
		cartRepo.deleteAll();
		couponRepo.deleteAll();
		
	}
	
	@Test
	public void createCouponTest() {
		
		given()
			.port(port)
			.contentType(ContentType.JSON)
			.body(createCouponDto())
			.when()
				.post("coupon/create")
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void applyCouponValidDate() {
		Cart cart = createCart();
		cartRepo.save(cart);
		
		CouponImpl coupon = createCoupon();
		couponRepo.save(coupon);
		
		FixedPriceBehaviorAction action = new FixedPriceBehaviorAction(coupon, new BigDecimal(10));
		fixedPriceRepo.save(action);
		
		given()
			.port(port)
			.contentType(ContentType.JSON)
			.body(createCouponDto())
			.pathParams("couponCode", "CUPOM10", "idCart", "1")
			.when()
				.post("coupon/apply/{couponCode}/{idCart}")
			.then()
				.statusCode(HttpStatus.CREATED.value());
		
		cart = cartRepo.findOneById(cart.getId());
		
		assertTrue(new BigDecimal(10).compareTo(cart.getDiscount()) == 0);
	}
	
	@Test
	public void applyNonExistsCoupon() {
		Cart cart = createCart();
		cartRepo.save(cart);
		
		given()
			.port(port)
			.contentType(ContentType.JSON)
			.body(createCouponDto())
			.pathParams("couponCode", "CUPOM10", "idCart", "1")
			.when()
				.post("coupon/apply/{couponCode}/{idCart}")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void applyCouponInvalidDate() {
		Cart cart = createCart();
		cartRepo.save(cart);
		
		CouponImpl coupon = createCoupon();
		coupon.setIniDate(new LocalDate(2018, 12, 1));
		coupon.setEndDate(new LocalDate(2018, 12, 1));
		couponRepo.save(coupon);
		
		FixedPriceBehaviorAction action = new FixedPriceBehaviorAction(coupon, new BigDecimal(10));
		fixedPriceRepo.save(action);
		
		given()
			.port(port)
			.contentType(ContentType.JSON)
			.body(createCouponDto())
			.pathParams("couponCode", "CUPOM10", "idCart", "1")
			.when()
				.post("coupon/apply/{couponCode}/{idCart}")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void applyCouponCartPriceValidRestriction() {
		Cart cart = createCart();
		cartRepo.save(cart);
		
		CouponImpl coupon = createCoupon();
		couponRepo.save(coupon);
		
		FixedPriceBehaviorAction action = new FixedPriceBehaviorAction(coupon, new BigDecimal(10));
		fixedPriceRepo.save(action);
		
		CartBehaviorRestriction restriction = new CartBehaviorRestriction(coupon, new BigDecimal(100));
		cartRestrRepo.save(restriction);
		
		given()
			.port(port)
			.contentType(ContentType.JSON)
			.body(createCouponDto())
			.pathParams("couponCode", "CUPOM10", "idCart", "1")
			.when()
				.post("coupon/apply/{couponCode}/{idCart}")
			.then()
				.statusCode(HttpStatus.CREATED.value());
		
		cart = cartRepo.findOneById(cart.getId());
		
		assertTrue(new BigDecimal(10).compareTo(cart.getDiscount()) == 0);
	}
	
	@Test
	public void applyCouponCartPriceInvalidRestriction() {
		Cart cart = createCart();
		cartRepo.save(cart);
		
		CouponImpl coupon = createCoupon();
		couponRepo.save(coupon);
		
		FixedPriceBehaviorAction action = new FixedPriceBehaviorAction(coupon, new BigDecimal(10));
		fixedPriceRepo.save(action);
		
		CartBehaviorRestriction restriction = new CartBehaviorRestriction(coupon, new BigDecimal(500));
		cartRestrRepo.save(restriction);
		
		given()
			.port(port)
			.contentType(ContentType.JSON)
			.body(createCouponDto())
			.pathParams("couponCode", "CUPOM10", "idCart", "1")
			.when()
				.post("coupon/apply/{couponCode}/{idCart}")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
		
	}
	
	private CouponDto createCouponDto() {
		
		CartRestrictionDto cartRestriction = new CartRestrictionDto();
		cartRestriction.setMinimumCartPrice(new BigDecimal(100));
		
		FixedPriceActionDto fixedPriceAction = new FixedPriceActionDto();
		fixedPriceAction.setDiscount(new BigDecimal(10));
				
		RestrictionDto restriction = new RestrictionDto();
		restriction.setCartRestriction(cartRestriction);
		
		ActionDto action = new ActionDto();
		action.setFixedPriceAction(fixedPriceAction);
		
		CouponDto dto = new CouponDto();
		dto.setCode("CUPOM10");
		dto.setIniDate(LocalDate.now());
		dto.setEndDate(LocalDate.now());
		dto.setRestrictions(restriction);
		dto.setAction(action);
		
		return dto;
	}
	
	private Cart createCart() {
		
		//Criando cart
		Cart cart = new Cart();
		cart.setId(1L);
		cart.setDiscount(new BigDecimal(0));
		
		//Criando item
		ItemCart item = new ItemCart();
		item.setQtd(1);
		
		//Criando produto
		Product product = new Product();
		product.setName("PRODUCT 1");
		product.setPrice(new BigDecimal(100));
		
		//Criando shipping
		Shipping shipping = new Shipping();
		shipping.setVlrShipping(new BigDecimal(5));
		
		//Vinculando produto ao item
		item.setProduct(product);
		
		//Vinculando item e shipping ao cart
		cart.setItems(Arrays.asList(item));
		cart.setShipping(shipping);
		
		return cart;
	}
	
	public CouponImpl createCoupon() {
		return new CouponImpl("CUPOM10", LocalDate.now(), LocalDate.now(), "Ganhe R$10,00 de desconto");
	}
}

