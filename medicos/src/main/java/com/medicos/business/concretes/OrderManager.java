package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.medicos.business.abstracts.OrderService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.*;
import com.medicos.entity.Order;
import com.medicos.repository.OrderRepository;

public class OrderManager implements OrderService{
	
	private final String entityName = "Order";
	private OrderRepository orderRepository;
	
	public OrderManager(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public DataResult<List<Order>> getAll() throws SQLException {
		return new SuccessDataResult<List<Order>>(orderRepository.findAll());
	}

	@Override
	public DataResult<Order> getById(long id) throws SQLException {
		Order cart = orderRepository.findById(id);
		if(cart == null) {
			return new ErrorDataResult<Order>(ResultMessages.notFoundMessage(entityName));
		}
		return new SuccessDataResult<Order>(cart);
	}
	
	@Override
	public DataResult<List<Order>> getByCustomerId(long customerId) throws SQLException {
		return new SuccessDataResult<List<Order>>(orderRepository.findByCustomerId(customerId));
	}

	@Override
	public Result add(Order entity) throws SQLException {
		boolean result = orderRepository.add(entity);
		return result ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Order entity) throws SQLException {
		if(!isExist(entity.getId())) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean result = orderRepository.update(entity);
		return result ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		if(!isExist(id)) {
			return new ErrorResult(ResultMessages.notFoundMessage(entityName));
		}
		boolean result = orderRepository.remove(id);
		return result ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}
	
	private boolean isExist(long id) throws SQLException {
		Order order = orderRepository.findById(id);
		return order != null ? true : false;
	}
}
