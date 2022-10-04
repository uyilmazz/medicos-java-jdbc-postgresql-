package com.medicos.entity;

import java.sql.Timestamp;

public class Appointment {
	private long id;
	private Timestamp appointmentDate;
	private boolean isSelected;
	private long customerId;
	private long doctorId;
	
	public Appointment() {
		
	}

	public Appointment(long id, Timestamp appointmentDate, boolean isSelected, long customerId, long doctorId) {
		this.id = id;
		this.appointmentDate = appointmentDate;
		this.isSelected = isSelected;
		this.customerId = customerId;
		this.doctorId = doctorId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Timestamp appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
}
