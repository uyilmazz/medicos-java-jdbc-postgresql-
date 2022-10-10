package com.medicos.business.abstracts;

import java.sql.SQLException;
import java.util.List;

import com.medicos.core.result.DataResult;
import com.medicos.entity.Product;

public interface ProductService extends BaseService<Product>{
	public DataResult<List<Product>> getByCategoryId(long categoryId) throws SQLException;
}
