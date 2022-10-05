package com.medicos.business.messages;

public class ResultMessages {
	
	public static String ErrorMessage = "An error occured!";
	
	public static String notFoundMessage(String entityName) {
		return String.format("%s not found!", entityName);
	}
	
	public static String alreadyExistMessage(String entityName) {
		return String.format("%s is already exists!", entityName);
	}
	
	public static String addedMessage(String entityName) {
		return String.format("%s is added.", entityName);
	}
	
	public static String notAddedMessage(String entityName) {
		return String.format("%s could not be added.", entityName);
	}
	
	public static String updatedMessage(String entityName) {
		return String.format("%s is updated.", entityName);
	}
	
	public static String notUpdatedMessage(String entityName) {
		return String.format("%s could not be updated!", entityName);
	}
	
	public static String deletedMessage(String entityName) {
		return String.format("%s is deleted.", entityName);
	}
	
	public static String notDeletedMessage(String entityName) {
		return String.format("%s could not be deleted!", entityName);
	}
	
	// CartItem Messages
	public static final String CartItemNotFound = "Product not found in cart!";
	public static final String CartItemAdded = "Product is added to cart.";
	public static final String CartItemCouldNotAdded = "Product could not be added to your cart.";
	public static final String CartItemUpdated = "Your product in the cart has been updated.";
	public static final String CartItemCouldNotUpdated = "Your product in the cart could not be updated!";
	public static final String CartItemDeleted = "Your product in the cart has been deleted.";
	public static final String CartItemCouldNotDeleted = "Your product in the cart could not be deleted!";
	
	// OrderItem Messages
	public static final String OrderItemNotFound = "Product not found in order!";
	public static final String OrderItemAdded = "Product is added to order.";
	public static final String OrderItemCouldNotAdded = "Product could not be added to your order.";
	public static final String OrderItemUpdated = "Your product in the order has been updated.";
	public static final String OrderItemCouldNotUpdated = "Your product in the order could not be updated!";
	public static final String OrderItemDeleted = "Your product in the order has been deleted.";
	public static final String OrderItemCouldNotDeleted = "Your product in the order could not be deleted!";
	
	public static final String EmailAlreadyExist = "Email address already exists!";
}
