package com.medicos.entity;

import java.util.List;

public class Cart {
	private long id;
	private double totalAmount;
	private long userId;
	private List<CartItem> cartItems;
	
	public Cart() {
		
	}
	
	public Cart(long id,double totalAmount,long userId) {
		this.id = id;
		this.totalAmount = totalAmount;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
}
