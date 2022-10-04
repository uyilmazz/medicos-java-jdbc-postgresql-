package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.CartItem;

public class CartItemXml {
	
	public static Document format(CartItem cartItem) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("cartItem");
		Element cartItemElement = document.getDocumentElement();
		cartItemElement.setAttribute("id", Long.toString(cartItem.getId()));
		formatHelper(document, cartItemElement, cartItem);
		return document;
	}
	
	public static Document formatAll(List<CartItem> cartItems) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("cartItems");
		Element root = document.getDocumentElement();
		for (int i = 0; i < cartItems.size(); i++) {
			CartItem cartItem = cartItems.get(i);
			XmlHelper.addSingleElement(document, root, "cartItem", null, "id", Long.toString(cartItem.getId()));
			Element cartItemElement = (Element) root.getElementsByTagName("cartItem").item(i);
			formatHelper(document, cartItemElement, cartItem);
		}
		return document;
	}
	
	public static CartItem parse(Document document) {
		Element cartItemElement = document.getDocumentElement();
		return parseHelper(cartItemElement);
	}
	
	public static List<CartItem> parseAll(Document document){
		List<CartItem> cartItemList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList cartItemNodeList = root.getElementsByTagName("cartItem");
		for (int i = 0; i < cartItemNodeList.getLength(); i++) {
			Element cartItemElement = (Element) cartItemNodeList.item(i);
			CartItem cartItem = parseHelper(cartItemElement);
			cartItemList.add(cartItem);
		}
		return cartItemList;
	}
	
	public static CartItem parseHelper(Element cartItemElement) {
		long id = cartItemElement.getAttribute("id") != "" ? Long.parseLong(cartItemElement.getAttribute("id")) : 0 ;
		double salesPrice = XmlHelper.getSingleElementText(cartItemElement, "salesPrice", 0);
		int quantity  = (int) XmlHelper.getSingleElementText(cartItemElement, "quantity", 0);
		double lineAmount = XmlHelper.getSingleElementText(cartItemElement, "lineAmount", 0);
		long productId = (long) XmlHelper.getSingleElementText(cartItemElement, "productId", 0);
		long cartId = (long) XmlHelper.getSingleElementText(cartItemElement, "cartId", 0);
		CartItem cartItem = new CartItem(id,salesPrice,quantity,lineAmount,productId,cartId);
		return cartItem;
	}
	
	public static void formatHelper(Document document, Element cartItemElement,CartItem cartItem) {
		XmlHelper.addSingleElement(document, cartItemElement, "salesPrice", cartItem.getSalesPrice(), null, null);
		XmlHelper.addSingleElement(document, cartItemElement, "quantity", cartItem.getQuantity(), null, null);
		XmlHelper.addSingleElement(document, cartItemElement, "lineAmount", cartItem.getLineAmount(), null, null);
		XmlHelper.addSingleElement(document, cartItemElement, "productId", cartItem.getProductId(), null, null);
		XmlHelper.addSingleElement(document, cartItemElement, "cartId", cartItem.getCartId(), null, null);
	}
}
