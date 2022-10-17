package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import com.medicos.entity.CartItem;
import com.medicos.entity.Order;

public class OrderRepository extends BaseRepository<Order> {

	public List<Order> findAll() throws SQLException {
		String sql = "Select * from orders";
		return super.findAll(sql);
	}

	public Order findById(long id) throws SQLException {
		String sql = "Select * from orders where id = ?";
		return super.findById(sql, id);
	}

	public List<Order> findByCustomerId(long customerId) throws SQLException {
		String sql = "Select * from orders where customer_id = ?";
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, customerId);
		ResultSet resultSet = statement.executeQuery();
		List<Order> orders = parseList(resultSet);
		disconnect();
		return orders;
	}

	public boolean add(Order order) throws SQLException {
		try {
			CartItemRepository cartItemRepository = new CartItemRepository();
			connect();
			connection.setAutoCommit(false);
			
			// Find Cart
			long cartId = 0;
			double cartTotalAmount = 0;
			String cartSql = "Select * from carts where customer_id = ?";
			PreparedStatement cartFindStatement = connection.prepareStatement(cartSql);
			cartFindStatement.setLong(1, order.getCustomerId());
			ResultSet resultSetCart = cartFindStatement.executeQuery();
			if (resultSetCart.next()) {
				cartId = resultSetCart.getLong("id");
				cartTotalAmount = resultSetCart.getDouble("total_amount"); 
			}
			
			// Create Order
			long orderId = 0;
			String createOrderSql = "Insert into orders(address_line_1,address_line_2,total_amount,order_date,customer_id) values(?,?,?,?,?)";
			PreparedStatement createOrderStatement = connection.prepareStatement(createOrderSql, Statement.RETURN_GENERATED_KEYS);
			createOrderStatement.setString(1, order.getAddressLine1());
			createOrderStatement.setString(2, order.getAddressLine2());
			createOrderStatement.setDouble(3, cartTotalAmount);
			createOrderStatement.setTimestamp(4, order.getOrderDate());
			createOrderStatement.setDouble(5, order.getCustomerId());
			createOrderStatement.executeUpdate();
			ResultSet resultSet = createOrderStatement.getGeneratedKeys();
			if (resultSet.next()) {
				orderId = resultSet.getLong(1);
			}

			// Find CartItems
			String cartItemsSql = "select ci.*,p.\"name\" as product_name,p.description as product_description,"
					+ "p.how_to_use as product_how_to_use,p.sales_price as product_sales_price,"
					+ "p.image_url as product_image_url,c.id as category_id,c.\"name\" as category_name,c.image_url as category_image_url"
					+ " from cart_items ci join products p on ci.product_id = p.id join categories c on p.category_id = c.id where ci.cart_id = ?";
			PreparedStatement cartItemFindStatement = connection.prepareStatement(cartItemsSql);
			cartItemFindStatement.setLong(1, cartId);
			ResultSet resultSetCartITem = cartItemFindStatement.executeQuery();
			List<CartItem> cartItemList = cartItemRepository.parseList(resultSetCartITem);
			
			// Create OrderItems -> Bulk
			String createOrderItemSql = "Insert into order_items(name,image_url,sales_price,quantity,line_amount,order_id) values(?,?,?,?,?,?)";
			PreparedStatement createOrderItemStatement = connection.prepareStatement(createOrderItemSql);
			for(CartItem cartItem : cartItemList) {
				createOrderItemStatement.setString(1, cartItem.getProduct().getName());
				createOrderItemStatement.setString(2, cartItem.getProduct().getImageUrl());
				createOrderItemStatement.setDouble(3, cartItem.getProduct().getSalesPrice());
				createOrderItemStatement.setInt(4, cartItem.getQuantity());
				createOrderItemStatement.setDouble(5, cartItem.getLineAmount());
				createOrderItemStatement.setLong(6, orderId);
				createOrderItemStatement.addBatch();
			}
			
			// Clear Cart
			String clearCartSql = "Update carts set total_amount = 0 where id = ?";
			PreparedStatement clearCartStatement = connection.prepareStatement(clearCartSql);
			clearCartStatement.setLong(1,cartId);
			clearCartStatement.executeUpdate();
			
			// Delete CartItems
			String deleteCartItemSql = "Delete from cart_items where cart_id = ?";
			PreparedStatement deleteCartItemStatement = connection.prepareStatement(deleteCartItemSql);
			deleteCartItemStatement.setLong(1, cartId);
			deleteCartItemStatement.executeUpdate();
			
			createOrderItemStatement.executeBatch();
			connection.commit();
			disconnect();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
		String sql = "Delete from orders where id = ?";
		return super.remove(sql, id);
	}

	@Override
	protected Order parse(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String addressLine1 = resultSet.getString("address_line_1");
		String addressLine2 = resultSet.getString("address_line_2");
		double totalAmount = resultSet.getDouble("total_amount");
		Timestamp orderDate = resultSet.getTimestamp("order_date");
		long customerId = resultSet.getLong("customer_id");
		return new Order(id, addressLine1, addressLine2, totalAmount, orderDate, customerId);
	}

}
