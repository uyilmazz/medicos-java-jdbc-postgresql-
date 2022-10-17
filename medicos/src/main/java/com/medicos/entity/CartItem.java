package com.medicos.entity;

public class CartItem {
	private long id;
	private int quantity;
	private double lineAmount;
	private Product product;
	private long cartId;
	
	public CartItem() {
	}

	public CartItem(long id, int quantity, double lineAmount , long cartId) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.lineAmount = lineAmount;
		this.cartId = cartId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
}
