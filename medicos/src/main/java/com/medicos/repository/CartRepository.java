package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.medicos.entity.Cart;
import com.medicos.entity.CartItem;

public class CartRepository extends BaseRepository<Cart>{

	public List<Cart> findAll() throws SQLException{
		String sql = "Select * from carts";
		return super.findAll(sql);
	}
	
	public Cart findById(long id) throws SQLException {
		String sql = "Select * from carts where id = ?";
		return super.findById(sql, id);
	}
	
	public Cart findByCustomerId(long customerId) throws SQLException {
		String sql = "Select * from carts where customer_id = ?";
		return super.findById(sql, customerId);
	}
	
	public boolean add(Cart cart) throws SQLException {
		connect();
		String sql = "Insert into carts(total_amount,customer_id) values(?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDouble(1, cart.getTotalAmount());
		statement.setLong(2, cart.getCustomerId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Cart cart) throws SQLException {
		connect();
		String sql = "Update carts set total_amount = ? , customer_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDouble(1, cart.getTotalAmount());
		statement.setLong(2, cart.getCustomerId());
		statement.setLong(3, cart.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(long id) throws SQLException {
		String sql = "Delete from carts where id = ?";
		return super.remove(sql, id);
	}
	
	@Override
	protected Cart parse(ResultSet resultSet) throws SQLException {
		Cart cart = null;
		if(resultSet.next()) {
			long cartId = resultSet.getLong("cart_id");
			double total_amount = resultSet.getDouble("total_amount");
			long userId = resultSet.getLong("user_id");
			CartItemRepository cartItemRepository = new CartItemRepository();
			List<CartItem> cartItemList = cartItemRepository.findByCartId(cartId);
			cart = new Cart(cartId,total_amount,userId);
			cart.setCartItems(cartItemList);
		}
		return cart;
	}

}
