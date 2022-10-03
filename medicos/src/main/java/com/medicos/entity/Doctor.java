package com.medicos.entity;

public class Doctor {
	private long id;
	private String name;
	private String about;
	private String imageUrl;
	private double experience;
	private int patience;
	private Department department;
	
	public Doctor() {
		
	}

	public Doctor(long id, String name, String about, String imageUrl, double experience, int patience) {
		this.id = id;
		this.name = name;
		this.about = about;
		this.imageUrl = imageUrl;
		this.experience = experience;
		this.patience = patience;
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

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}

	public int getPatience() {
		return patience;
	}

	public void setPatience(int patience) {
		this.patience = patience;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
