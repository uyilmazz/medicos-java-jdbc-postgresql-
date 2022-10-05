package com.medicos.entity;

public class Doctor {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String about;
	private String imageUrl;
	private int experienceMonth;
	private int patienceCount;
	private Department department;
	
	public Doctor() {
		
	}

	public Doctor(long id, String firstName, String lastName, String email,String password,String about, String imageUrl, int experienceMonth,
			int patienceCount) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
