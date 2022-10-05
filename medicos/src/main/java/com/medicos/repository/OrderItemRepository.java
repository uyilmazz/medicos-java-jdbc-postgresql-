package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.medicos.entity.OrderItem;

public class OrderItemRepository extends BaseRepository<OrderItem>{

	public List<OrderItem> findAll() throws SQLException{
		String sql = "Select * from order_items";
		return super.findAll(sql);
	}
	
	public List<OrderItem> findByOrderId(long orderId) throws SQLException{
		connect();
		String sql = "Select * from order_items where order_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, orderId);
		ResultSet resultSet = statement.executeQuery();
		List<OrderItem> orderItemList = parseList(resultSet);
		disconnect();
		return orderItemList;
	}
	
	public OrderItem findById(long id) throws SQLException {
		String sql = "Select * from order_items where id = ?";
		return super.findById(sql, id);
	}
	
	public boolean add(OrderItem orderItem) throws SQLException {
		connect();
		String sql = "Insert into order_items(name,image_url,sales_price,quantity,line_amount,order_id) values(?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, orderItem.getName());
		statement.setString(2, orderItem.getImageUrl());
		statement.setDouble(3, orderItem.getSalesPrice());
		statement.setInt(4, orderItem.getQuantity());
		statement.setDouble(5, orderItem.getLineAmount());
		statement.setLong(6, orderItem.getOrderId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(OrderItem orderItem) throws SQLException {
		connect();
		String sql = "Update order_items set name = ?, image_url = ?, sales_price = ?, quantity = ?, line_amount = ?,order_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, orderItem.getName());
		statement.setString(2, orderItem.getImageUrl());
		statement.setDouble(3, orderItem.getSalesPrice());
		statement.setInt(4, orderItem.getQuantity());
		statement.setDouble(5, orderItem.getLineAmount());
		statement.setLong(6, orderItem.getOrderId());
		statement.setLong(7, orderItem.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(long id) throws SQLException {
		String sql = "Delete from order_items where id = ?";
		return super.remove(sql, id);
	}
	
	@Override
	protected OrderItem parse(ResultSet resultSet) throws SQLException {
		OrderItem orderItem = null;
		if(resultSet.next()) {
			long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			String imageUrl = resultSet.getString("image_url");
			double salesPrice = resultSet.getDouble("sales_price");
			int quantity = resultSet.getInt("quantity");
			double lineAmount = resultSet.getDouble("line_amount");
			long orderId = resultSet.getLong("order_id");
			orderItem = new OrderItem(id,name,imageUrl,salesPrice,quantity,lineAmount,orderId);
		}
		return orderItem;
	}

}
