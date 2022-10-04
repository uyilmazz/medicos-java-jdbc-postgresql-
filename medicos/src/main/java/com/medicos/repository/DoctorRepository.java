package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.medicos.entity.Department;
import com.medicos.entity.Doctor;

public class DoctorRepository extends BaseRepository<Doctor>{
	private final String SELECT_STRING = "select d.*,dep.\\\"name\\\" as department_name,dep.image_url as department_image_url from doctors d join departments dep on d.department_id = dep.id";
	
	public List<Doctor> findAll() throws SQLException{
		return super.findAll(SELECT_STRING);
	}
	
	public Doctor findById(long id) throws SQLException {
		String sql = String.format("%s %s",SELECT_STRING,"where id = ?");
		return super.findById(sql, id);
	}
	
	public boolean add(Doctor doctor) throws SQLException {
		connect();
		String sql = "Insert into doctors(name,about,image_url,experience_month,patience_count,department_id) values(?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, doctor.getName());
		statement.setString(2, doctor.getAbout());
		statement.setString(3, doctor.getImageUrl());
		statement.setInt(4, doctor.getExperienceMonth());
		statement.setInt(5, doctor.getPatienceCount());
		statement.setInt(6, doctor.getDepartment().getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Doctor doctor) throws SQLException {
		connect();
		String sql = "Update doctors set name = ?, about = ?, image_url = ?, experience_month = ?, patience_count = ?,department_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, doctor.getName());
		statement.setString(2, doctor.getAbout());
		statement.setString(3, doctor.getImageUrl());
		statement.setInt(4, doctor.getExperienceMonth());
		statement.setInt(5, doctor.getPatienceCount());
		statement.setInt(6, doctor.getDepartment().getId());
		statement.setLong(7, doctor.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(int id) throws SQLException {
		String sql = "Delete from doctors where id = ?";
		return super.remove(sql, id);
	}
	

	@Override
	protected Doctor parse(ResultSet resultSet) throws SQLException {
		Doctor doctor = null;
		if(resultSet.next()) {
			long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			String about = resultSet.getString("about");
			String imageUrl = resultSet.getString("image_url");
			int experienceMonth = resultSet.getInt("experience_month");
			int patienceCount = resultSet.getInt("patience_count");
			doctor = new Doctor(id,name,about,imageUrl,experienceMonth,patienceCount);
			
			int departmentId = resultSet.getInt("departmentId");
			String departmentName = resultSet.getString("department_name");
			String departmentImageUrl = resultSet.getString("department_image_url");
			Department department = new Department(departmentId,departmentName,departmentImageUrl);
			doctor.setDepartment(department);
		}
		return doctor;
	}
}
