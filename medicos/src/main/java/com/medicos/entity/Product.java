package com.medicos.entity;

public class Product {
	private long id;
	private String name;
	private String description;
	private String howToUse;
	private double salesPrice;
	private String imageUrl;
	private Category category;
	
	public Product() {
		
	}

	public Product(long id, String name, String description, String howToUse, double salesPrice, String imageUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.howToUse = howToUse;
		this.salesPrice = salesPrice;
		this.imageUrl = imageUrl;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHowToUse() {
		return howToUse;
	}

	public void setHowToUse(String howToUse) {
		this.howToUse = howToUse;
	}

	public double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
