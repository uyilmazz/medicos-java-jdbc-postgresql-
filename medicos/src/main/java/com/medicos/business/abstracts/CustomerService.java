package com.medicos.business.abstracts;

import java.sql.SQLException;

import com.medicos.core.result.DataResult;
import com.medicos.dto.CustomerLoginDto;
import com.medicos.entity.Customer;

public interface CustomerService extends BaseService<Customer>{
	public DataResult<Customer> login(CustomerLoginDto customerLoginDto) throws SQLException;
}
