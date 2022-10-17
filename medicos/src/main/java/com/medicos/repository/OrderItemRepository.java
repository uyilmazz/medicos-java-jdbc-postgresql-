package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.medicos.entity.OrderItem;

public class OrderItemRepository extends BaseRepository<OrderItem> {

	public List<OrderItem> findAll() throws SQLException {
		String sql = "Select * from order_items";
		return super.findAll(sql);
	}

	public List<OrderItem> findByOrderId(long orderId) throws SQLException {
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
		connection.setAutoCommit(false);
		double totalAmount = 0;
		
		// Find Order
		String orderSql = "Select * from orders where id = ?";
		PreparedStatement orderFindStatement = connection.prepareStatement(orderSql);
		orderFindStatement.setLong(1, orderItem.getOrderId());
		ResultSet resultSetOrder = orderFindStatement.executeQuery();
		if (resultSetOrder.next()) {
			totalAmount = resultSetOrder.getDouble("total_amount");
		}

		// Create OrderItem
		String sql = "Insert into order_items(name,image_url,sales_price,quantity,line_amount,order_id) values(?,?,?,?,?,?)";
		PreparedStatement orderItemCreateStatement = connection.prepareStatement(sql);
		orderItemCreateStatement.setString(1, orderItem.getName());
		orderItemCreateStatement.setString(2, orderItem.getImageUrl());
		orderItemCreateStatement.setDouble(3, orderItem.getSalesPrice());
		orderItemCreateStatement.setInt(4, orderItem.getQuantity());
		orderItemCreateStatement.setDouble(5, orderItem.getLineAmount());
		orderItemCreateStatement.setLong(6, orderItem.getOrderId());
		int affectedCreatedCartItem = orderItemCreateStatement.executeUpdate();

		// Update Order
		totalAmount += orderItem.getLineAmount();
		String updateOrderSql = "Update orders set total_amount = ? where id = ?";
		PreparedStatement orderUpdateStatement = connection.prepareStatement(updateOrderSql);
		orderUpdateStatement.setDouble(1, totalAmount);
		orderUpdateStatement.setLong(2, orderItem.getOrderId());
		int affectedOrderUpdated = orderUpdateStatement.executeUpdate();

		connection.commit();
		disconnect();
		if (!(affectedCreatedCartItem > 0 && affectedOrderUpdated > 0)) {
			return false;
		}
		return true;
	}

	public boolean update(OrderItem orderItem) throws SQLException {
		connect();
		connection.setAutoCommit(false);
		double totalAmount = 0;
		double line_amount = 0;
		
		// Find Order
		String orderSql = "Select total_amount from orders where id = ?";
		PreparedStatement orderFindStatement = connection.prepareStatement(orderSql);
		orderFindStatement.setLong(1, orderItem.getOrderId());
		ResultSet resultSetOrder = orderFindStatement.executeQuery();
		if (resultSetOrder.next()) {
			totalAmount = resultSetOrder.getDouble("total_amount");
		}
		
		// Find OrderItem
		String orderItemSql = "Select line_amount from order_items where id = ?";
		PreparedStatement orderItemFindStatement = connection.prepareStatement(orderItemSql);
		orderItemFindStatement.setLong(1, orderItem.getId());
		ResultSet resutlSetOrderItem = orderItemFindStatement.executeQuery();
		if (resutlSetOrderItem.next()) {
			line_amount = resutlSetOrderItem.getDouble("line_amount");
		}
		// old orderItem line amount subtract
		totalAmount -= line_amount;

		// Update OrderItem
		String sql = "Update order_items set name = ?, image_url = ?, sales_price = ?, quantity = ?, line_amount = ?,order_id = ? where id = ?";
		PreparedStatement orderItemUpdateStatement = connection.prepareStatement(sql);
		orderItemUpdateStatement.setString(1, orderItem.getName());
		orderItemUpdateStatement.setString(2, orderItem.getImageUrl());
		orderItemUpdateStatement.setDouble(3, orderItem.getSalesPrice());
		orderItemUpdateStatement.setInt(4, orderItem.getQuantity());
		orderItemUpdateStatement.setDouble(5, orderItem.getLineAmount());
		orderItemUpdateStatement.setLong(6, orderItem.getOrderId());
		orderItemUpdateStatement.setLong(7, orderItem.getId());
		int affectedUpdatedCartItem = orderItemUpdateStatement.executeUpdate();

		// Update Order -- new orderItem line amount add
		totalAmount += orderItem.getLineAmount();
		String updateOrderSql = "Update orders set total_amount = ? where id = ?";
		PreparedStatement orderUpdateStatement = connection.prepareStatement(updateOrderSql);
		orderUpdateStatement.setDouble(1, totalAmount);
		orderUpdateStatement.setLong(2, orderItem.getOrderId());
		int affectedUpdatedOrder = orderUpdateStatement.executeUpdate();

		connection.commit();
		disconnect();
		if (!(affectedUpdatedCartItem > 0 && affectedUpdatedOrder > 0)) {
			return false;
		}
		return true;
	}

	public boolean remove(OrderItem orderItem) throws SQLException {
		connect();
		connection.setAutoCommit(false);
		double totalAmount = 0;
		
		// Find Order
		String orderSql = "Select total_amount from orders where id = ?";
		PreparedStatement orderFindStatement = connection.prepareStatement(orderSql);
		orderFindStatement.setLong(1, orderItem.getOrderId());
		ResultSet resultSetOrder = orderFindStatement.executeQuery();
		if (resultSetOrder.next()) {
			totalAmount = resultSetOrder.getDouble("total_amount");
		}

		// Remove OrderItem
		String sql = "Delete from order_items where id = ?";
		PreparedStatement orderItemDeleteStatement = connection.prepareStatement(sql);
		orderItemDeleteStatement.setLong(1, orderItem.getId());
		int affectedDeleteOrderItem = orderItemDeleteStatement.executeUpdate();

		// Update Order
		totalAmount -= orderItem.getLineAmount();
		String updateOrderSql = "Update orders set total_amount = ? where id = ?";
		PreparedStatement orderUpdateStatement = connection.prepareStatement(updateOrderSql);
		orderUpdateStatement.setDouble(1, totalAmount);
		orderUpdateStatement.setLong(2, orderItem.getOrderId());
		int affectedUpdatedOrder = orderUpdateStatement.executeUpdate();

		connection.commit();
		disconnect();

		if (affectedDeleteOrderItem > 0 && affectedUpdatedOrder > 0) {
			return true;
		}
		return false;
	}

	@Override
	protected OrderItem parse(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String name = resultSet.getString("name");
		String imageUrl = resultSet.getString("image_url");
		double salesPrice = resultSet.getDouble("sales_price");
		int quantity = resultSet.getInt("quantity");
		double lineAmount = resultSet.getDouble("line_amount");
		long orderId = resultSet.getLong("order_id");
		return new OrderItem(id, name, imageUrl, salesPrice, quantity, lineAmount, orderId);
	}

}
