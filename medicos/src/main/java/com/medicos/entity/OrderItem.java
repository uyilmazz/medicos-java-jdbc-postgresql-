package com.medicos.entity;

public class OrderItem {
	private long id;
	private String name;
	private String imageUrl;
	private double salesPrice;
	private int quantity;
	private double lineAmount;
	private long orderId;
	
	public OrderItem() {
		
	}

	public OrderItem(long id, String name, String imageUrl, double salesPrice, int quantity, double lineAmount,
			long orderId) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.salesPrice = salesPrice;
		this.quantity = quantity;
		this.lineAmount = lineAmount;
		this.orderId = orderId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
}
