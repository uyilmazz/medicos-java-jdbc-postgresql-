package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.medicos.business.abstracts.DoctorService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.*;
import com.medicos.entity.Doctor;
import com.medicos.repository.DoctorRepository;

public class DoctorManager implements DoctorService{

	private final String entityName = "Doctor";
	private DoctorRepository doctorRepository;
	
	public DoctorManager(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}
	
	@Override
	public DataResult<List<Doctor>> getAll() throws SQLException {
		return new ErrorDataResult<List<Doctor>>(doctorRepository.findAll());
	}

	@Override
	public DataResult<Doctor> getById(long id) throws SQLException {
		Doctor doctor = doctorRepository.findById(id);
		if(doctor == null) {
			return new ErrorDataResult<Doctor>(ResultMessages.notAddedMessage(entityName));
		}
		return new SuccessDataResult<Doctor>(doctor);
	}
	
	@Override
	public DataResult<Doctor> getByEmail(String email) throws SQLException {
		Doctor doctor = doctorRepository.findByEmail(email);
		if(doctor == null) {
			return new ErrorDataResult<Doctor>(ResultMessages.notAddedMessage(entityName));
		}
		return new SuccessDataResult<Doctor>(doctor);
	}
	
	@Override
	public Result add(Doctor entity) throws SQLException {
		Doctor doctor = doctorRepository.findByEmail(entity.getEmail());
		if(doctor != null) {
			return new ErrorResult(ResultMessages.alreadyExistMessage(entityName));
		}
		boolean addedResult = doctorRepository.add(entity);
		return addedResult ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Doctor entity) throws SQLException {
		if(!isExist(entity.getId())) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean updatedResult = doctorRepository.update(entity);
		return updatedResult ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		if(!isExist((int) id)) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean deletedResult = doctorRepository.remove(id);
		return deletedResult ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}
	
	private boolean isExist(long id) throws SQLException {
		return doctorRepository.findById(id) != null ? true : false;
	}
}
