package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.medicos.business.abstracts.ProvinceService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.ErrorDataResult;
import com.medicos.core.result.ErrorResult;
import com.medicos.core.result.Result;
import com.medicos.core.result.SuccessDataResult;
import com.medicos.core.result.SuccessResult;
import com.medicos.entity.Province;
import com.medicos.repository.ProvinceRepository;

public class ProvinceManager implements ProvinceService{
	
	private final String entityName = "Province";
	private ProvinceRepository provinceRepository;
	
	public ProvinceManager(ProvinceRepository provinceRepository) {
		this.provinceRepository = provinceRepository;
	}
	
	@Override
	public DataResult<List<Province>> getAll() throws SQLException {
		return new SuccessDataResult<List<Province>>(provinceRepository.findAll());
	}

	@Override
	public DataResult<Province> getById(long id) throws SQLException {
		Province province = provinceRepository.findById((int) id);
		if(province == null) {
			return new ErrorDataResult<Province>(ResultMessages.notFoundMessage(entityName));
		}
		return new SuccessDataResult<Province>(province);
	}
	
	@Override
	public Result add(Province entity) throws SQLException {
		boolean result = provinceRepository.add(entity);
		return result ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Province entity) throws SQLException {
		if(!isExist(entity.getId())) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean result = provinceRepository.update(entity);
		return result ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		if(!isExist(id)) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean result = provinceRepository.remove((int) id);
		return result ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}
	
	private boolean isExist(long id) throws SQLException {
		Province province = provinceRepository.findById((int) id);
		return province != null ? true : false;
	}
}
