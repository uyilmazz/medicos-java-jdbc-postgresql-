package com.medicos.business.abstracts;

import java.sql.SQLException;

import com.medicos.core.result.DataResult;
import com.medicos.entity.Cart;

public interface CartService extends BaseService<Cart>{
	
	public DataResult<Cart> getByCustomerId(long customerId) throws SQLException;
}
