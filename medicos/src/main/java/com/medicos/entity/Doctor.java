package com.medicos.entity;

public class Doctor {
	private long id;
	private String name;
	private String about;
	private String imageUrl;
	private int experienceMonth;
	private int patienceCount;
	private Department department;
	
	public Doctor() {
		
	}

	public Doctor(long id, String name, String about, String imageUrl, int experienceMonth, int patienceCount) {
		this.id = id;
		this.name = name;
		this.about = about;
		this.imageUrl = imageUrl;
		this.experienceMonth = experienceMonth;
		this.patienceCount = patienceCount;
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

	public int getExperienceMonth() {
		return experienceMonth;
	}

	public void setExperienceMonth(int experienceMonth) {
		this.experienceMonth = experienceMonth;
	}

	public int getPatienceCount() {
		return patienceCount;
	}

	public void setPatienceCount(int patienceCount) {
		this.patienceCount = patienceCount;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
