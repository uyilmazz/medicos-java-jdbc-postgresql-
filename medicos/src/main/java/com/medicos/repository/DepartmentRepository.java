package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.medicos.entity.Department;

public class DepartmentRepository extends BaseRepository<Department>{

	public List<Department> findAll() throws SQLException{
		String sql = "Select * from departments";
		return super.findAll(sql);
	}
	
	public Department findById(int id) throws SQLException {
		String sql = "Select * from departments where id = ?";
		return super.findById(sql, id);
	}
	
	public Department findByName(String departmentName) throws SQLException {
		Department department = null;
		String sql = "Select * from departments where name = ?";
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, departmentName);
		ResultSet resultSet = statement.executeQuery();
		department = parse(resultSet);
		disconnect();
		return department;
	}
	
	public boolean add(Department department) throws SQLException {
		connect();
		String sql = "Insert into departments(name,image_url) values(?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, department.getName());
		statement.setString(2, department.getImageUrl());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Department department) throws SQLException {
		connect();
		String sql = "Update departments set name = ? , image_url = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, department.getName());
		statement.setString(2, department.getImageUrl());
		statement.setInt(3, department.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(int id) throws SQLException {
		String sql = "Delete from departments where id = ?";
		return super.remove(sql, id);
	}
	
	@Override
	protected Department parse(ResultSet resultSet) throws SQLException {
		Department department = null;
		if(resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			String imageUrl = resultSet.getString("image_url");
			department = new Department(id,name,imageUrl);
		}
		return department;
	}
}
