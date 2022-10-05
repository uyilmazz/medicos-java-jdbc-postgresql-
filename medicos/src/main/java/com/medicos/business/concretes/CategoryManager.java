package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;
import com.medicos.business.abstracts.CategoryService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.*;
import com.medicos.entity.Category;
import com.medicos.repository.CategoryRepository;

public class CategoryManager implements CategoryService{

	private final String entityName = "Category";
	private CategoryRepository categoryRepository;
	
	public CategoryManager(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public DataResult<List<Category>> getAll() throws SQLException {
		return new ErrorDataResult<List<Category>>(categoryRepository.findAll());
	}

	@Override
	public DataResult<Category> getById(long id) throws SQLException {
		Category category = categoryRepository.findById((int)id);
		if(category == null) {
			return new ErrorDataResult<Category>(ResultMessages.notAddedMessage(entityName));
		}
		return new SuccessDataResult<Category>(category);
	}
	
	@Override
	public Result add(Category entity) throws SQLException {
		Category category = categoryRepository.findByName(entity.getName());
		if(category != null) {
			return new ErrorResult(ResultMessages.alreadyExistMessage(entityName));
		}
		boolean addedResult = categoryRepository.add(entity);
		return addedResult ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Category entity) throws SQLException {
		if(!isExistCategory(entity.getId())) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean updatedResult = categoryRepository.update(entity);
		return updatedResult ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		if(!isExistCategory((int) id)) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean deletedResult = categoryRepository.remove((int) id);
		return deletedResult ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}
	
	private boolean isExistCategory(int id) throws SQLException {
		return categoryRepository.findById(id) != null ? true : false;
	}
}
