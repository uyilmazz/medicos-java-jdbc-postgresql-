package com.medicos.entity;

public class CartItem {
	private long id;
	private double salesPrice;
	private int quantity;
	private double lineAmount;
	private long userId;
	private long cartId;
	
	public CartItem() {
		
	}

	public CartItem(long id, double salesPrice, int quantity, double lineAmount, long userId, long cartId) {
		this.id = id;
		this.salesPrice = salesPrice;
		this.quantity = quantity;
		this.lineAmount = lineAmount;
		this.userId = userId;
		this.cartId = cartId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(double lineAmount) {
		this.lineAmount = lineAmount;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
}
