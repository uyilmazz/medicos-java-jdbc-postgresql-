package com.medicos.business.abstracts;

import java.sql.SQLException;
import java.util.List;

import com.medicos.core.result.*;

public interface BaseService <T> {

	public DataResult<List<T>> getAll() throws SQLException;
	public DataResult<T> getById(long id) throws SQLException;
	public Result add(T entity) throws SQLException;
	public Result update(T entity) throws SQLException;
	public Result delete(long id) throws SQLException;
}
