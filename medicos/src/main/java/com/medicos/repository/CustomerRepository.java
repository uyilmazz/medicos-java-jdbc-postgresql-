package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.medicos.entity.Customer;

public class CustomerRepository extends BaseRepository<Customer>{

	public List<Customer> findAll() throws SQLException{
		String sql = "Select * from customers";
		return super.findAll(sql);
	}
	
	public Customer findById(long id) throws SQLException {
		String sql = "Select * from customers where id = ?";
		return super.findById(sql, id);
	}
	
	public Customer findByEmail(String email) throws SQLException {
		String sql = "Select * from customers where email = ?";
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();
		Customer customer = parse(resultSet);
		disconnect();
		return customer;
	}
	
	public boolean add(Customer customer) throws SQLException {
		connect();
		String sql = "Insert into customers(first_name,last_name,email,password,image_url) values(?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, customer.getFirstName());
		statement.setString(2, customer.getLastName());
		statement.setString(3, customer.getEmail());
		statement.setString(4, customer.getPassword());
		statement.setString(5, customer.getImageUrl());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Customer customer) throws SQLException {
		connect();
		String sql = "Update customers set first_name = ?,last_name, email = ?, password = ?, image_url = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, customer.getFirstName());
		statement.setString(2, customer.getLastName());
		statement.setString(3, customer.getEmail());
		statement.setString(4, customer.getPassword());
		statement.setString(5, customer.getImageUrl());
		statement.setLong(6, customer.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(long id) throws SQLException {
		String sql = "Delete from customers where id = ?";
		return super.remove(sql, id);
	}
	
	@Override
	protected Customer parse(ResultSet resultSet) throws SQLException {
		Customer customer = null;
		if(resultSet.next()) {
			long id = resultSet.getLong("id");
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			String password = resultSet.getString("password");
			String imageUrl = resultSet.getString("image_url");
			customer = new Customer(id,firstName,lastName,email,password,imageUrl);
		}
		return customer;
	}
}
