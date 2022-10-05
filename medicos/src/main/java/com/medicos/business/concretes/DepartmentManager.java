package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;
import com.medicos.business.abstracts.DepartmentService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.*;
import com.medicos.entity.Department;
import com.medicos.repository.DepartmentRepository;

public class DepartmentManager implements DepartmentService{

	private final String entityName = "Department";
	private DepartmentRepository departmentRepository;
	
	public DepartmentManager(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}
	
	@Override
	public DataResult<List<Department>> getAll() throws SQLException {
		return new ErrorDataResult<List<Department>>(departmentRepository.findAll());
	}

	@Override
	public DataResult<Department> getById(long id) throws SQLException {
		Department department = departmentRepository.findById((int)id);
		if(department == null) {
			return new ErrorDataResult<Department>(ResultMessages.notAddedMessage(entityName));
		}
		return new SuccessDataResult<Department>(department);
	}
	
	@Override
	public Result add(Department entity) throws SQLException {
		Department department = departmentRepository.findByName(entity.getName());
		if(department != null) {
			return new ErrorResult(ResultMessages.alreadyExistMessage(entityName));
		}
		boolean addedResult = departmentRepository.add(entity);
		return addedResult ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Department entity) throws SQLException {
		if(!isExistDepartment(entity.getId())) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean updatedResult = departmentRepository.update(entity);
		return updatedResult ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		if(!isExistDepartment((int) id)) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean deletedResult = departmentRepository.remove((int) id);
		return deletedResult ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}
	
	private boolean isExistDepartment(int id) throws SQLException {
		return departmentRepository.findById(id) != null ? true : false;
	}
}
