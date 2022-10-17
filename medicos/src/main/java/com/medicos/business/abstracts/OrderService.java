package com.medicos.business.abstracts;

import java.sql.SQLException;
import java.util.List;

import com.medicos.core.result.DataResult;
import com.medicos.entity.Order;

public interface OrderService extends BaseService<Order>{
	public DataResult<List<Order>> getByCustomerId(long customerId) throws SQLException;
}
