package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.medicos.entity.Department;
import com.medicos.entity.Doctor;

public class DoctorRepository extends BaseRepository<Doctor> {
	private final String SELECT_STRING = "select d.*,dep.\"name\" as department_name,dep.image_url as department_image_url from doctors d join departments dep on d.department_id = dep.id";

	public List<Doctor> findAll() throws SQLException {
		return super.findAll(SELECT_STRING);
	}

	public Doctor findById(long id) throws SQLException {
		String sql = String.format("%s %s", SELECT_STRING, "where d.id = ?");
		return super.findById(sql, id);
	}

	public Doctor findByEmail(String email) throws SQLException {
		Doctor doctor = null;
		String sql = String.format("%s %s", SELECT_STRING, "where d.email = ?");
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			doctor = parse(resultSet);
		}
		disconnect();
		return doctor;
	}

	public boolean add(Doctor doctor) throws SQLException {
		connect();
		String sql = "Insert into doctors(first_name,last_name,email,password,about,image_url,experience_month,patience_count,department_id) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, doctor.getFirstName());
		statement.setString(2, doctor.getLastName());
		statement.setString(3, doctor.getEmail());
		statement.setString(4, doctor.getPassword());
		statement.setString(5, doctor.getAbout());
		statement.setString(6, doctor.getImageUrl());
		statement.setInt(7, doctor.getExperienceMonth());
		statement.setInt(8, doctor.getPatienceCount());
		statement.setInt(9, doctor.getDepartment().getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}

	public boolean update(Doctor doctor) throws SQLException {
		connect();
		String sql = "Update doctors set first_name = ?,last_name = ?, email = ?, password = ?, about = ?, image_url = ?, experience_month = ?, patience_count = ?,department_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, doctor.getFirstName());
		statement.setString(2, doctor.getLastName());
		statement.setString(3, doctor.getEmail());
		statement.setString(4, doctor.getPassword());
		statement.setString(5, doctor.getAbout());
		statement.setString(6, doctor.getImageUrl());
		statement.setInt(7, doctor.getExperienceMonth());
		statement.setInt(8, doctor.getPatienceCount());
		statement.setInt(9, doctor.getDepartment().getId());
		statement.setLong(10, doctor.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}

	public boolean remove(long id) throws SQLException {
		String sql = "Delete from doctors where id = ?";
		return super.remove(sql, id);
	}

	@Override
	protected Doctor parse(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		String email = resultSet.getString("email");
		String password = resultSet.getString("password");
		String about = resultSet.getString("about");
		String imageUrl = resultSet.getString("image_url");
		int experienceMonth = resultSet.getInt("experience_month");
		int patienceCount = resultSet.getInt("patience_count");
		Doctor doctor = new Doctor(id, firstName, lastName, email, password, about, imageUrl, experienceMonth,
				patienceCount);

		int departmentId = resultSet.getInt("department_id");
		String departmentName = resultSet.getString("department_name");
		String departmentImageUrl = resultSet.getString("department_image_url");
		Department department = new Department(departmentId, departmentName, departmentImageUrl);
		doctor.setDepartment(department);
		return doctor;
	}
}
