package com.medicos.business.concretes;

import java.util.List;

import com.medicos.business.abstracts.AddressService;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.Result;
import com.medicos.entity.Address;
import com.medicos.repository.AddressRepository;

public class AddressManager implements AddressService{

	private AddressRepository addressRepository;
	
	public AddressManager(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@Override
	public DataResult<List<Address>> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<Address> getById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result add(Address entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result update(Address entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result delete(Address entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
