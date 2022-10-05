package com.medicos.business.abstracts;

import java.sql.SQLException;

import com.medicos.core.result.DataResult;
import com.medicos.entity.Order;

public interface OrderService extends BaseService<Order>{
	public DataResult<Order> getByCustomerId(long customerId) throws SQLException;
}
