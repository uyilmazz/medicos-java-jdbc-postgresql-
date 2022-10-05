package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;
import com.medicos.business.abstracts.CartItemService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.*;
import com.medicos.entity.CartItem;
import com.medicos.repository.CartItemRepository;

public class CartItemManager implements CartItemService{

	private CartItemRepository cartItemRepository;
	
	public CartItemManager(CartItemRepository cartItemRepository) {
		this.cartItemRepository = cartItemRepository;
	}
	
	@Override
	public DataResult<List<CartItem>> getAll() throws SQLException {
		return new SuccessDataResult<List<CartItem>>(cartItemRepository.findAll());
	}
	
	@Override
	public DataResult<List<CartItem>> getByCartId(long cartId) throws SQLException {
		return new SuccessDataResult<List<CartItem>>(cartItemRepository.findByCartId(cartId));
	}

	@Override
	public DataResult<CartItem> getById(long id) throws SQLException {
		CartItem cartItem = cartItemRepository.findById(id);
		if(cartItem == null) {
			return new ErrorDataResult<CartItem>(ResultMessages.CartItemNotFound);
		}
		return new SuccessDataResult<CartItem>(cartItem);
	}

	@Override
	public Result add(CartItem entity) throws SQLException {
		CartItem cartItem = cartItemRepository.findById(entity.getId());
		boolean result = false;
		if(cartItem == null) {
			result = cartItemRepository.add(cartItem);
		}else {
			int dbQuantity = cartItem.getQuantity();
			dbQuantity += entity.getQuantity();
			cartItem.setQuantity(dbQuantity);
			result = cartItemRepository.update(cartItem);
		}
		return result ? new SuccessResult(ResultMessages.CartItemAdded) : new ErrorResult(ResultMessages.CartItemCouldNotAdded);
	}

	@Override
	public Result update(CartItem entity) throws SQLException {
		CartItem cartItem = cartItemRepository.findById(entity.getId());
		if(cartItem == null) {
			return new ErrorResult(ResultMessages.CartItemNotFound);
		}
		boolean updatedResult = cartItemRepository.update(cartItem);
		return updatedResult ? new SuccessResult(ResultMessages.CartItemUpdated) : new ErrorResult(ResultMessages.CartItemCouldNotUpdated);
	}

	@Override
	public Result delete(long id) throws SQLException {
		CartItem cartItem = cartItemRepository.findById(id);
		if(cartItem == null) {
			return new ErrorResult(ResultMessages.CartItemNotFound);
		}
		boolean deletedResult = cartItemRepository.remove(id);
		return deletedResult ? new SuccessResult(ResultMessages.CartItemDeleted) : new ErrorResult(ResultMessages.CartItemCouldNotDeleted);
	}
}
