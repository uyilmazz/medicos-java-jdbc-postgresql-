package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;
import com.medicos.business.abstracts.OrderItemService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.ErrorDataResult;
import com.medicos.core.result.ErrorResult;
import com.medicos.core.result.Result;
import com.medicos.core.result.SuccessDataResult;
import com.medicos.core.result.SuccessResult;
import com.medicos.entity.OrderItem;
import com.medicos.repository.OrderItemRepository;

public class OrderItemManager implements OrderItemService{
	
	private OrderItemRepository orderItemRepository;
	
	public OrderItemManager(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public DataResult<List<OrderItem>> getAll() throws SQLException {
		return new SuccessDataResult<List<OrderItem>>(orderItemRepository.findAll());
	}
	
	@Override
	public DataResult<List<OrderItem>> getByOrderId(long orderId) throws SQLException {
		return new SuccessDataResult<List<OrderItem>>(orderItemRepository.findByOrderId(orderId));
	}

	@Override
	public DataResult<OrderItem> getById(long id) throws SQLException {
		OrderItem orderItem = orderItemRepository.findById(id);
		if(orderItem == null) {
			return new ErrorDataResult<OrderItem>(ResultMessages.OrderItemNotFound);
		}
		return new SuccessDataResult<OrderItem>(orderItem);
	}

	@Override
	public Result add(OrderItem entity) throws SQLException {
		boolean result = orderItemRepository.add(entity);
		return result ? new SuccessResult(ResultMessages.OrderItemAdded) : new ErrorResult(ResultMessages.OrderItemCouldNotAdded);
	}

	@Override
	public Result update(OrderItem entity) throws SQLException {
		OrderItem orderItem = orderItemRepository.findById(entity.getId());
		if(orderItem == null) {
			return new ErrorResult(ResultMessages.OrderItemNotFound);
		}
		boolean updatedResult = orderItemRepository.update(orderItem);
		return updatedResult ? new SuccessResult(ResultMessages.OrderItemUpdated) : new ErrorResult(ResultMessages.OrderItemCouldNotUpdated);
	}

	@Override
	public Result delete(long id) throws SQLException {
		if(!isExist(id)) {
			return new ErrorResult(ResultMessages.OrderItemNotFound);
		}
		boolean deletedResult = orderItemRepository.remove(id);
		return deletedResult ? new SuccessResult(ResultMessages.OrderItemDeleted) : new ErrorResult(ResultMessages.OrderItemCouldNotDeleted);
	}
	
	private boolean isExist(long id) throws SQLException {
		return orderItemRepository.findById(id) != null ? true : false;
	}
}
