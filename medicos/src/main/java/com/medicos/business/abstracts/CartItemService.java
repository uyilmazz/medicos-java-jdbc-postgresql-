package com.medicos.business.abstracts;

import java.sql.SQLException;
import java.util.List;

import com.medicos.core.result.DataResult;
import com.medicos.entity.CartItem;

public interface CartItemService extends BaseService<CartItem>{
	
	public DataResult<List<CartItem>> getByCartId(long cartId) throws SQLException;
}
