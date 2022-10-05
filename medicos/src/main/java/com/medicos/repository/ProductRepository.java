package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.medicos.entity.Category;
import com.medicos.entity.Product;

public class ProductRepository extends BaseRepository<Product>{

	private final String SELECT_STRING = "select p.*,c.\"name\" as category_name,c.image_url as category_image_url from products p join categories c on p.category_id = c.id  ";
	
	public List<Product> findAll() throws SQLException{
		return super.findAll(SELECT_STRING);
	}
	
	public List<Product> findAllByCategoryId(int categoryId) throws SQLException{
		String condition = String.format("where category_id = %s", categoryId);
		String sql = String.format("%s %s", SELECT_STRING,condition);
		return super.findAll(sql);
	}
	
	public Product findById(long id) throws SQLException {
		String sql = String.format("%s %s", SELECT_STRING,"where id = ?");
		return super.findById(sql, id);
	}
	
	public Product findByName(String name) throws SQLException {
		String sql = String.format("%s %s", SELECT_STRING,"where name = ?");
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		Product product = parse(resultSet); 
		disconnect();
		return product;
	}
	
	public boolean add(Product product) throws SQLException {
		connect();
		String sql = "Insert into products(name,description,how_to_use,sales_price,image_url,category_id) values(?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, product.getName());
		statement.setString(2, product.getDescription());
		statement.setString(3, product.getHowToUse());
		statement.setDouble(4, product.getSalesPrice());
		statement.setString(5, product.getImageUrl());
		statement.setInt(6, product.getCategory().getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Product product) throws SQLException {
		connect();
		String sql = "Update products set name = ?, description = ?, how_to_use = ?, sales_price = ?, image_url = ?,category_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, product.getName());
		statement.setString(2, product.getDescription());
		statement.setString(3, product.getHowToUse());
		statement.setDouble(4, product.getSalesPrice());
		statement.setString(5, product.getImageUrl());
		statement.setInt(6, product.getCategory().getId());
		statement.setLong(7, product.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(long id) throws SQLException {
		String sql = "Delete from products where id = ?";
		return super.remove(sql, id);
	}
	
	@Override
	protected Product parse(ResultSet resultSet) throws SQLException {
		Product product = null;
		if(resultSet.next()) {
			long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			String description = resultSet.getString("description");
			String howToUse = resultSet.getString("how_to_use");
			double salesPrice = resultSet.getDouble("sales_price");
			String imageUrl = resultSet.getString("image_url");
			product = new Product(id,name,description,howToUse,salesPrice,imageUrl);
			
			int categoryId = resultSet.getInt("category_id");
			String categoryName = resultSet.getString("category_name");
			String categoryImageUrl = resultSet.getString("category_image_url");
			Category category = new Category(categoryId,categoryName,categoryImageUrl);
			product.setCategory(category);
		}
		return product;
	}

}
