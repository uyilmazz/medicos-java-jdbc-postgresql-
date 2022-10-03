package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Cart;
import com.medicos.entity.CartItem;

public class CartXml {
	
	public static Document format(Cart cart) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("cart");
		Element cartElement = document.getDocumentElement();
		cartElement.setAttribute("id", Long.toString(cart.getId()));
		formatHelper(document, cartElement, cart);
		return document;
	}
	
	public static Document formatAll(List<Cart> carts) throws ParserConfigurationException{
		Document document = XmlHelper.createDocument("carts");
		Element cartsElement = document.getDocumentElement();
		for (int i = 0; i < carts.size(); i++) {
			Cart cart  = carts.get(i);
			XmlHelper.addSingleElement(document, cartsElement, "cart", null, "id", Long.toString(cart.getId()));
			Element cartElement = (Element) cartsElement.getElementsByTagName("cart").item(i);
			formatHelper(document, cartElement, cart);
		}
		return document;
	}
	
	public static Cart parse(Document document) {
		Element cartElement = document.getDocumentElement();
		return parseHelper(cartElement);
	}
	
	public static List<Cart> parseAll(Document document){
		List<Cart> cartList = new ArrayList<>();
		Element cartsElement = document.getDocumentElement();
		NodeList cartsNodeList = cartsElement.getElementsByTagName("cart");
		for (int i = 0; i < cartsNodeList.getLength(); i++) {
			Element cartElement = (Element) cartsNodeList.item(i);
			Cart cart = parseHelper(cartElement);
			cartList.add(cart);
		}
		return cartList;
	}
	
	private static Cart parseHelper(Element cartElement) {
		long cartId = cartElement.getAttribute("id") != "" ? Long.parseLong(cartElement.getAttribute("id")) : 0 ;
		double totalAmount = XmlHelper.getSingleElementText(cartElement, "totalAmount", 0);
		long userId = (long) XmlHelper.getSingleElementText(cartElement, "userId", 0);
		Cart cart = new Cart(cartId,totalAmount,userId);
		
		List<CartItem> cartItemList = new ArrayList<>();
		Element cartItemsElement = (Element) cartElement.getElementsByTagName("cartItems").item(0);
		NodeList cartItemsNodeList = cartItemsElement.getElementsByTagName("cartItem");
		for (int i = 0; i < cartItemsNodeList.getLength(); i++) {
			Element cartItemElement = (Element) cartItemsNodeList.item(i);
			CartItem cartItem = CartItemXml.parseHelper(cartItemElement);
			cartItemList.add(cartItem);
		}
		cart.setCartItems(cartItemList);
		return cart;
	}
	
	private static void formatHelper(Document document,Element cartElement,Cart cart) {
		XmlHelper.addSingleElement(document, cartElement, "totalAmount", cart.getTotalAmount(), null, null);
		XmlHelper.addSingleElement(document, cartElement, "userId", cart.getUserId(), null, null);
		XmlHelper.addSingleElement(document, cartElement, "cartItems", null, null, null);
		Element cartItemsElement = (Element) cartElement.getElementsByTagName("cartItems").item(0);
		for (int i = 0; i < cart.getCartItems().size(); i++) {
			CartItem cartItem = cart.getCartItems().get(i);
			XmlHelper.addSingleElement(document, cartItemsElement, "cartItem", null, "id", Long.toString(cartItem.getId()));
			Element cartItemElement = (Element) cartItemsElement.getElementsByTagName("cartItem").item(i);
			CartItemXml.formatHelper(document, cartItemElement, cartItem);
		}
	}
}
