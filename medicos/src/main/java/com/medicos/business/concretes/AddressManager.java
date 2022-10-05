package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.medicos.business.abstracts.AddressService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.*;
import com.medicos.entity.Address;
import com.medicos.repository.AddressRepository;

public class AddressManager implements AddressService{

	private final String entityName = "Address";
	private AddressRepository addressRepository;
	
	public AddressManager(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@Override
	public DataResult<List<Address>> getAll() throws SQLException {
		return new ErrorDataResult<List<Address>>(addressRepository.findAll());
	}

	@Override
	public DataResult<Address> getById(long id) throws SQLException {
		Address address = addressRepository.findById(id);
		if(address == null) {
			return new ErrorDataResult<Address>(ResultMessages.notAddedMessage(entityName));
		}
		return new SuccessDataResult<Address>(address);
	}
	
	@Override
	public Result add(Address entity) throws SQLException {
		boolean addedResult = addressRepository.add(entity);
		return addedResult ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Address entity) throws SQLException {
		DataResult<Address> addressResult = getById(entity.getId());
		if(!addressResult.isSuccess()) {
			return new ErrorResult(addressResult.getMessage());
		}
		boolean updatedResult = addressRepository.update(entity);
		return updatedResult ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		DataResult<Address> addressResult = getById(id);
		if(!addressResult.isSuccess()) {
			return new ErrorResult(addressResult.getMessage());
		}
		boolean deletedResult = addressRepository.remove(id);
		return deletedResult ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}
}
