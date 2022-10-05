package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.medicos.business.abstracts.ProductService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.*;
import com.medicos.entity.Product;
import com.medicos.repository.ProductRepository;

public class ProductManager implements ProductService{

	private final String entityName = "Product";
	private ProductRepository productRepository;
	
	public ProductManager(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public DataResult<List<Product>> getAll() throws SQLException {
		return new SuccessDataResult<List<Product>>(productRepository.findAll());
	}

	@Override
	public DataResult<Product> getById(long id) throws SQLException {
		Product product = productRepository.findById(id);
		if(product == null) {
			return new ErrorDataResult<Product>(ResultMessages.notFoundMessage(entityName));
		}
		return new SuccessDataResult<Product>(product);
	}
	

	@Override
	public Result add(Product entity) throws SQLException {
		Product product = productRepository.findByName(entity.getName());
		if(product != null) {
			return new ErrorResult(ResultMessages.alreadyExistMessage(entityName));
		}
		boolean result = productRepository.add(entity);
		return result ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Product entity) throws SQLException {
		if(!isExist(entity.getId())) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean result = productRepository.update(entity);
		return result ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		if(!isExist(id)) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean result = productRepository.remove(id);
		return result ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}
	
	private boolean isExist(long id) throws SQLException {
		Product product = productRepository.findById(id);
		return product != null ? true : false;
	}
}
