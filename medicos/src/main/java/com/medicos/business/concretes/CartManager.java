package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.medicos.business.abstracts.CartService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.*;
import com.medicos.entity.Cart;
import com.medicos.repository.CartRepository;

public class CartManager implements CartService{

	private final String entityName = "Cart";
	private CartRepository cartRepository;
	
	public CartManager(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	@Override
	public DataResult<List<Cart>> getAll() throws SQLException {
		return new SuccessDataResult<List<Cart>>(cartRepository.findAll());
	}

	@Override
	public DataResult<Cart> getById(long id) throws SQLException {
		Cart cart = cartRepository.findById(id);
		if(cart == null) {
			return new ErrorDataResult<Cart>(ResultMessages.notFoundMessage(entityName));
		}
		return new SuccessDataResult<Cart>(cart);
	}
	
	@Override
	public DataResult<Cart> getByCustomerId(long customerId) throws SQLException {
		Cart cart = cartRepository.findByCustomerId(customerId);
		if(cart == null) {
			return new ErrorDataResult<Cart>(ResultMessages.notFoundMessage(entityName));
		}
		return new SuccessDataResult<Cart>(cart);
	}

	@Override
	public Result add(Cart entity) throws SQLException {
		boolean result = false;
		if(entity.getCustomerId() <= 0) {
			result = cartRepository.add(entity);
		}else {
			Cart cart = cartRepository.findByCustomerId(entity.getCustomerId());
			if(cart != null) {
				return new ErrorResult(ResultMessages.alreadyExistMessage(entityName));
			}
			result = cartRepository.add(entity);
		}
		return result ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Cart entity) throws SQLException {
		if(!isExist(entity.getId())) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean result = cartRepository.update(entity);
		return result ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		if(!isExist(id)) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean result = cartRepository.remove(id);
		return result ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}
	
	private boolean isExist(long id) throws SQLException {
		Cart cart = cartRepository.findById(id);
		return cart != null ? true : false;
	}
}
