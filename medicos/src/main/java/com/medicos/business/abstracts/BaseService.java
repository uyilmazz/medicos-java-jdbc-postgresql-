package com.medicos.business.abstracts;

import java.util.List;

import com.medicos.core.result.*;

public interface BaseService <T> {

	public DataResult<List<T>> getAll();
	public DataResult<T> getById();
	public Result add(T entity);
	public Result update(T entity);
	public Result delete(T entity);
}
