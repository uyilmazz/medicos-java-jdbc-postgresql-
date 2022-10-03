package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.OrderItem;

public class OrderItemXml {
	public static Document format(OrderItem orderItem) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("orderItem");
		Element orderItemElement = document.getDocumentElement();
		orderItemElement.setAttribute("id", Long.toString(orderItem.getId()));
		formatHelper(document, orderItemElement, orderItem);
		return document;
	}
	
	public static Document formatAll(List<OrderItem> orderItems) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("orderItems");
		Element root = document.getDocumentElement();
		for (int i = 0; i < orderItems.size(); i++) {
			OrderItem orderItem = orderItems.get(i);
			XmlHelper.addSingleElement(document, root, "orderItem", null, "id", Long.toString(orderItem.getId()));
			Element orderItemElement = (Element) root.getElementsByTagName("orderItem").item(i);
			formatHelper(document, orderItemElement, orderItem);
		}
		return document;
	}
	
	public static OrderItem parse(Document document) {
		Element orderItemElement = document.getDocumentElement();
		return parseHelper(orderItemElement);
	}
	
	public static List<OrderItem> parseAll(Document document){
		List<OrderItem> orderItemList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList orderItemNodeList = root.getElementsByTagName("orderItem");
		for (int i = 0; i < orderItemNodeList.getLength(); i++) {
			Element orderItemElement = (Element) orderItemNodeList.item(i);
			OrderItem orderItem = parseHelper(orderItemElement);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}
	
	public static OrderItem parseHelper(Element orderItemElement) {
		long orderItemId = orderItemElement.getAttribute("id") != "" ? Long.parseLong(orderItemElement.getAttribute("id")) : 0 ;
		String orderItemName = XmlHelper.getSingleElementText(orderItemElement, "name", "");
		String orderItemImageUrl = XmlHelper.getSingleElementText(orderItemElement, "imageUrl", "");
		double orderItemSalesPrice = XmlHelper.getSingleElementText(orderItemElement, "salesPrice", 0);
		int orderItemQuantity  = (int) XmlHelper.getSingleElementText(orderItemElement, "quantity", 0);
		double orderItemlineAmount = XmlHelper.getSingleElementText(orderItemElement, "lineAmount", 0);
		long orderId = (long) XmlHelper.getSingleElementText(orderItemElement, "orderId", 0);
		OrderItem orderItem = new OrderItem(orderItemId,orderItemName,orderItemImageUrl,orderItemSalesPrice,orderItemQuantity,orderItemlineAmount,orderId);
		return orderItem;
	}
	
	public static void formatHelper(Document document, Element orderItemElement,OrderItem orderItem) {
		XmlHelper.addSingleElement(document, orderItemElement, "name", orderItem.getName(), null, null);
		XmlHelper.addSingleElement(document, orderItemElement, "imageUrl", orderItem.getImageUrl(), null, null);
		XmlHelper.addSingleElement(document, orderItemElement, "salesPrice", orderItem.getSalesPrice(), null, null);
		XmlHelper.addSingleElement(document, orderItemElement, "quantity", orderItem.getQuantity(), null, null);
		XmlHelper.addSingleElement(document, orderItemElement, "lineAmount", orderItem.getLineAmount(), null, null);
		XmlHelper.addSingleElement(document, orderItemElement, "orderId", orderItem.getOrderId(), null, null);
	}
}
