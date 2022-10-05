package com.medicos.business.abstracts;

import java.sql.SQLException;
import java.util.List;
import com.medicos.core.result.DataResult;
import com.medicos.entity.OrderItem;

public interface OrderItemService extends BaseService<OrderItem>{
	public DataResult<List<OrderItem>> getByOrderId(long orderId) throws SQLException;
}
