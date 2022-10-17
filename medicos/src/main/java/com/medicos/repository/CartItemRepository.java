package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.medicos.entity.CartItem;
import com.medicos.entity.Category;
import com.medicos.entity.Product;

public class CartItemRepository extends BaseRepository<CartItem> {
	private final String SELECT_STRING = "select ci.*,p.\"name\" as product_name,p.description as product_description,"
			+ "p.how_to_use as product_how_to_use,p.sales_price as product_sales_price,"
			+ "p.image_url as product_image_url,c.id as category_id,c.\"name\" as category_name,c.image_url as category_image_url"
			+ " from cart_items ci join products p on ci.product_id = p.id join categories c on p.category_id = c.id ";

	public List<CartItem> findAll() throws SQLException {
		return super.findAll(SELECT_STRING);
	}

	public List<CartItem> findByCartId(long cartId) throws SQLException {
		String sql = String.format("%s %s", SELECT_STRING,"where ci.cart_id = ?");
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, cartId);
		ResultSet resultSet = statement.executeQuery();
		List<CartItem> cartItemList = parseList(resultSet);
		disconnect();
		return cartItemList;
	}

	public CartItem findById(long id) throws SQLException {
		String sql = String.format("%s %s", SELECT_STRING,"where ci.id = ?");
		return super.findById(sql, id);
	}

	public boolean add(CartItem cartItem) throws SQLException {
		connect();
		connection.setAutoCommit(false);
		double totalAmount = 0;
		// Find Cart
		String cartSql = "Select * from carts where id = ?";
		PreparedStatement cartFindStatement = connection.prepareStatement(cartSql);
		cartFindStatement.setLong(1, cartItem.getCartId());
		ResultSet resultSetCart = cartFindStatement.executeQuery();
		if (resultSetCart.next()) {
			totalAmount = resultSetCart.getDouble("total_amount");
		}

		// Create CartItem
		String sql = "Insert into cart_items(quantity,line_amount,product_id,cart_id) values(?,?,?,?)";
		PreparedStatement cartItemCreateStatement = connection.prepareStatement(sql);
		cartItemCreateStatement.setInt(1, cartItem.getQuantity());
		cartItemCreateStatement.setDouble(2, cartItem.getLineAmount());
		cartItemCreateStatement.setLong(3, cartItem.getProduct().getId());
		cartItemCreateStatement.setLong(4, cartItem.getCartId());
		int affectedCreatedCartItem = cartItemCreateStatement.executeUpdate();

		// Update Cart
		totalAmount += cartItem.getLineAmount();
		String updateCartSql = "Update carts set total_amount = ? where id = ?";
		PreparedStatement cartUpdateStatement = connection.prepareStatement(updateCartSql);
		cartUpdateStatement.setDouble(1, totalAmount);
		cartUpdateStatement.setLong(2, cartItem.getCartId());
		int affectedUpdatedCart = cartUpdateStatement.executeUpdate();

		connection.commit();
		disconnect();
		if (!(affectedCreatedCartItem > 0 && affectedUpdatedCart > 0)) {
			return false;
		}
		return true;
	}

	public boolean update(CartItem cartItem) throws SQLException {	
		connect();
		connection.setAutoCommit(false);
		double totalAmount = 0;
		double line_amount = 0;
		
		// Find Cart
		String cartSql = "Select total_amount from carts where id = ?";
		PreparedStatement cartFindStatement = connection.prepareStatement(cartSql);
		cartFindStatement.setLong(1, cartItem.getCartId());
		ResultSet resultSetCart = cartFindStatement.executeQuery();
		if (resultSetCart.next()) {
			totalAmount = resultSetCart.getDouble("total_amount");
		}
		
		// Find CartItem
		String cartItemSql = "Select line_amount from cart_items where id = ?";
		PreparedStatement cartItemFindStatement = connection.prepareStatement(cartItemSql);
		cartItemFindStatement.setLong(1, cartItem.getId());
		ResultSet resutlSetCartItem = cartItemFindStatement.executeQuery();
		if (resutlSetCartItem.next()) {
			line_amount = resutlSetCartItem.getDouble("line_amount");
		}
		// old cartItem line amount subtract
		totalAmount -= line_amount;

		// Update CartItem
		String sql = "Update cart_items set quantity = ? ,line_amount = ?,product_id = ?, cart_id = ? where id = ?";
		PreparedStatement cartItemUpdateStatement = connection.prepareStatement(sql);
		cartItemUpdateStatement.setInt(1, cartItem.getQuantity());
		cartItemUpdateStatement.setDouble(2, cartItem.getLineAmount());
		cartItemUpdateStatement.setLong(3, cartItem.getProduct().getId());
		cartItemUpdateStatement.setLong(4, cartItem.getCartId());
		cartItemUpdateStatement.setLong(5, cartItem.getId());
		int affectedUpdatedCartItem = cartItemUpdateStatement.executeUpdate();

		// Update Cart -- new line amount add
		totalAmount += cartItem.getLineAmount();
		String updateCartSql = "Update carts set total_amount = ? where id = ?";
		PreparedStatement cartUpdateStatement = connection.prepareStatement(updateCartSql);
		cartUpdateStatement.setDouble(1, totalAmount);
		cartUpdateStatement.setLong(2, cartItem.getCartId());
		int affectedUpdatedCart = cartUpdateStatement.executeUpdate();

		connection.commit();
		disconnect();
		if (!(affectedUpdatedCartItem > 0 && affectedUpdatedCart > 0)) {
			return false;
		}
		return true;
	}

	public boolean remove(CartItem cartItem) throws SQLException {
		connect();
		connection.setAutoCommit(false);
		double totalAmount = 0;
		// Find Cart
		String cartSql = "Select * from carts where id = ?";
		PreparedStatement cartFindStatement = connection.prepareStatement(cartSql);
		cartFindStatement.setLong(1, cartItem.getCartId());
		ResultSet resultSetCart = cartFindStatement.executeQuery();
		if (resultSetCart.next()) {
			totalAmount = resultSetCart.getDouble("total_amount");
		}

		// Remove CartItem
		String sql = "Delete from cart_items where id = ?";
		PreparedStatement cartItemDeleteStatement = connection.prepareStatement(sql);
		cartItemDeleteStatement.setLong(1, cartItem.getId());
		int affectedDeleteCartItem = cartItemDeleteStatement.executeUpdate();

		// Update Cart
		totalAmount -= cartItem.getLineAmount();
		String updateCartSql = "Update carts set total_amount = ? where id = ?";
		PreparedStatement cartUpdateStatement = connection.prepareStatement(updateCartSql);
		cartUpdateStatement.setDouble(1, totalAmount);
		cartUpdateStatement.setLong(2, cartItem.getCartId());
		int affectedUpdatedCart = cartUpdateStatement.executeUpdate();

		connection.commit();
		disconnect();

		if (affectedDeleteCartItem > 0 && affectedUpdatedCart > 0) {
			return true;
		}
		return false;
	}

	@Override
	protected CartItem parse(ResultSet resultSet) throws SQLException {
		long cartItemId = resultSet.getLong("id");
		int quantity = resultSet.getInt("quantity");
		double lineAmount = resultSet.getDouble("line_amount");
		long cartId = resultSet.getLong("cart_id");
		CartItem cartItem = new CartItem(cartItemId, quantity, lineAmount, cartId);
		
		long productId = resultSet.getLong("product_id");
		String productName = resultSet.getString("product_name");
		String productDescription = resultSet.getString("product_description");
		String productHowToUse = resultSet.getString("product_how_to_use");
		double productSalesPrice = resultSet.getDouble("product_sales_price");
		String productImageUrl = resultSet.getString("product_image_url");
		Product product = new Product(productId,productName,productDescription,productHowToUse,productSalesPrice,productImageUrl);
		
		int categoryId = resultSet.getInt("category_id");
		String categoryName = resultSet.getString("category_name");
		String categoryImageUrl = resultSet.getString("category_image_url");
		Category category = new Category(categoryId,categoryName,categoryImageUrl);
		
		product.setCategory(category);
		cartItem.setProduct(product);
		return cartItem;
	}

}
