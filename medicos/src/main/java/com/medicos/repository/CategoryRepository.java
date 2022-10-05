package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.medicos.entity.Category;

public class CategoryRepository extends BaseRepository<Category>{

	public List<Category> findAll() throws SQLException{
		String sql = "Select * from categories";
		return super.findAll(sql);
	}
	
	public Category findById(int id) throws SQLException {
		String sql = "Select * from categories where id = ?";
		return super.findById(sql, id);
	}
	
	public Category findByName(String categoryName) throws SQLException {
		Category category = null;
		String sql = "Select * from categories where name = ?";
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, categoryName);
		ResultSet resultSet = statement.executeQuery();
		category = parse(resultSet);
		disconnect();
		return category;
	}
	
	public boolean add(Category category) throws SQLException {
		connect();
		String sql = "Insert into categories(name,image_url) values(?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, category.getName());
		statement.setString(2, category.getImageUrl());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Category category) throws SQLException {
		connect();
		String sql = "Update categories set name = ? , image_url = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, category.getName());
		statement.setString(2, category.getImageUrl());
		statement.setInt(3, category.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(int id) throws SQLException {
		String sql = "Delete from categories where id = ?";
		return super.remove(sql, id);
	}
	
	@Override
	protected Category parse(ResultSet resultSet) throws SQLException {
		Category category = null;
		if(resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			String imageUrl = resultSet.getString("image_url");
			category = new Category(id,name,imageUrl);
		}
		return category;
	}

}
