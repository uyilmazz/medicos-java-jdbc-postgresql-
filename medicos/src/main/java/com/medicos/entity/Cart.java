package com.medicos.entity;

import java.util.List;

public class Cart {
	private long id;
	private double totalAmount;
	private long customerId;
	private List<CartItem> cartItems;
	
	public Cart() {
		
	}
	
	public Cart(long id,double totalAmount,long customerId) {
		this.id = id;
		this.totalAmount = totalAmount;
		this.customerId = customerId;
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

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
}
