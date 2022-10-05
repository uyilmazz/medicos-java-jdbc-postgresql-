package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.medicos.business.abstracts.CustomerService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.ErrorDataResult;
import com.medicos.core.result.ErrorResult;
import com.medicos.core.result.Result;
import com.medicos.core.result.SuccessDataResult;
import com.medicos.core.result.SuccessResult;
import com.medicos.entity.Customer;
import com.medicos.repository.CustomerRepository;

public class CustomerManager implements CustomerService{

	private final String entityName = "Customer";
	private CustomerRepository customerRepository;
	
	public CustomerManager(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	public DataResult<List<Customer>> getAll() throws SQLException {
		return new SuccessDataResult<List<Customer>>(customerRepository.findAll());
	}

	@Override
	public DataResult<Customer> getById(long id) throws SQLException {
		Customer customer = customerRepository.findById((int) id);
		if(customer == null) {
			return new ErrorDataResult<Customer>(ResultMessages.notFoundMessage(entityName));
		}
		return new SuccessDataResult<Customer>(customer);
	}
	
	@Override
	public Result add(Customer entity) throws SQLException {
		Customer customer = customerRepository.findByEmail(entity.getEmail());
		if(customer != null) {
			return new ErrorResult(ResultMessages.alreadyExistMessage(entityName));
		}
		boolean result = customerRepository.add(entity);
		return result ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Customer entity) throws SQLException {
		if(!isExist(entity.getId())) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		if(customerRepository.findByEmail(entity.getEmail()) != null) {
			return new ErrorResult(ResultMessages.EmailAlreadyExist);
		}
		boolean result = customerRepository.update(entity);
		return result ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		if(!isExist(id)) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean result = customerRepository.remove(id);
		return result ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}
	
	private boolean isExist(long id) throws SQLException {
		Customer customer = customerRepository.findById(id);
		return customer != null ? true : false;
	}
}
