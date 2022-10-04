package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.medicos.entity.CartItem;

public class CartItemRepository extends BaseRepository<CartItem>{

	public List<CartItem> findAll() throws SQLException{
		String sql = "Select * from cart_items";
		return super.findAll(sql);
	}
	
	public List<CartItem> findByCartId(long cartId) throws SQLException{
		connect();
		String sql = "Select * from cart_items where cart_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, cartId);
		ResultSet resultSet = statement.executeQuery();
		List<CartItem> cartItemList = parseList(resultSet);
		disconnect();
		return cartItemList;
	}
	
	public CartItem findById(long id) throws SQLException {
		String sql = "Select * from cart_items where id = ?";
		return super.findById(sql, id);
	}
	
	public boolean add(CartItem cartItem) throws SQLException {
		connect();
		String sql = "Insert into cart_items(sales_price,quantity,line_amount,product_id,cart_id) values(?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDouble(1, cartItem.getSalesPrice());
		statement.setInt(2, cartItem.getQuantity());
		statement.setDouble(3, cartItem.getLineAmount());
		statement.setLong(4, cartItem.getProductId());
		statement.setLong(5, cartItem.getCartId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(CartItem cartItem) throws SQLException {
		connect();
		String sql = "Update cart_items set sales_price = ? , quantity = ? ,line_amount = ?,product_id = ?, cart_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDouble(1, cartItem.getSalesPrice());
		statement.setInt(2, cartItem.getQuantity());
		statement.setDouble(3, cartItem.getLineAmount());
		statement.setLong(4, cartItem.getProductId());
		statement.setLong(5, cartItem.getCartId());
		statement.setLong(6, cartItem.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(long id) throws SQLException {
		String sql = "Delete from cart_items where id = ?";
		return super.remove(sql, id);
	}
	
	
	@Override
	protected CartItem parse(ResultSet resultSet) throws SQLException {
		long cartItemId = resultSet.getLong("id");
		double salesPrice = resultSet.getDouble("sales_price");
		int quantity = resultSet.getInt("quantity");
		double lineAmount = resultSet.getDouble("line_amount");
		long productId = resultSet.getLong("product_id");
		long cartId = resultSet.getLong("cart_id");
		CartItem cartItem = new CartItem(cartItemId,salesPrice,quantity,lineAmount,productId,cartId);
		return cartItem;
	}

}
