package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import com.medicos.entity.Order;

public class OrderRepository extends BaseRepository<Order>{

	public List<Order> findAll() throws SQLException{
		String sql = "Select * from orders";
		return super.findAll(sql);
	}
	
	public Order findById(long id) throws SQLException {
		String sql = "Select * from orders where id = ?";
		return super.findById(sql, id);
	}
	
	public Order findByCustomerId(long customerId) throws SQLException {
		String sql = "Select * from orders where customer_id = ?";
		return super.findById(sql, customerId);
	}
	
	public boolean add(Order order) throws SQLException {
		connect();
		String sql = "Insert into orders(address_line_1,address_line_2,total_amount,order_date,customer_id) values(?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, order.getAddressLine1());
		statement.setString(2, order.getAddressLine2());
		statement.setDouble(3, order.getTotalAmount());
		statement.setTimestamp(4, order.getOrderDate());
		statement.setDouble(5, order.getCustomerId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Order order) throws SQLException {
		connect();
		String sql = "Update orders set address_line_1 = ?, address_line_2 = ?, total_amount = ?, order_date = ?, customer_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, order.getAddressLine1());
		statement.setString(2, order.getAddressLine2());
		statement.setDouble(3, order.getTotalAmount());
		statement.setTimestamp(4, order.getOrderDate());
		statement.setDouble(5, order.getCustomerId());
		statement.setLong(6, order.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(long id) throws SQLException {
		String sql = "Delete from order_items where id = ?";
		return super.remove(sql, id);
	}
	
	@Override
	protected Order parse(ResultSet resultSet) throws SQLException {
		Order order = null;
		if(resultSet.next()) {
			long id = resultSet.getLong("id");
			String addressLine1 = resultSet.getString("address_line_1");
			String addressLine2 = resultSet.getString("address_line_2");
			double totalAmount = resultSet.getDouble("total_amount");
			Timestamp orderDate = resultSet.getTimestamp("order_date");
			long customerId = resultSet.getLong("customer_id");
			order = new Order(id,addressLine1,addressLine2,totalAmount,orderDate,customerId);
		}
		return order;
	}

}
