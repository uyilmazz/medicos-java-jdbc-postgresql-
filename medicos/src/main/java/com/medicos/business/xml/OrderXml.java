package com.medicos.business.xml;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Order;

public class OrderXml {
	public static Document format(Order order) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("order");
		Element orderElement = document.getDocumentElement();
		orderElement.setAttribute("id", Long.toString(order.getId()));
		formatHelper(document, orderElement, order);
		return document;
	}
	
	public static Document formatAll(List<Order> orders) throws ParserConfigurationException{
		Document document = XmlHelper.createDocument("orders");
		Element ordersElement = document.getDocumentElement();
		for (int i = 0; i < orders.size(); i++) {
			Order order  = orders.get(i);
			XmlHelper.addSingleElement(document, ordersElement, "order", null, "id", Long.toString(order.getId()));
			Element orderElement = (Element) ordersElement.getElementsByTagName("order").item(i);
			formatHelper(document, orderElement, order);
		}
		return document;
	}
	
	public static Order parse(Document document) throws ParseException {
		Element orderElement = document.getDocumentElement();
		return parseHelper(orderElement);
	}
	
	public static List<Order> parseAll(Document document) throws ParseException{
		List<Order> orderList = new ArrayList<>();
		Element ordersElement = document.getDocumentElement();
		NodeList orderNodeList = ordersElement.getElementsByTagName("order");
		for (int i = 0; i < orderNodeList.getLength(); i++) {
			Element orderElement = (Element) orderNodeList.item(i);
			Order order = parseHelper(orderElement);
			orderList.add(order);
		}
		return orderList;
	}
	
	private static Order parseHelper(Element orderElement) throws ParseException {
		long orderId = orderElement.getAttribute("id") != "" ? Long.parseLong(orderElement.getAttribute("id")) : 0 ;
		double totalAmount = XmlHelper.getSingleElementText(orderElement, "totalAmount", 0);
		String addressLine1 = XmlHelper.getSingleElementText(orderElement, "addressLine1", "");
		String addressLine2 = XmlHelper.getSingleElementText(orderElement, "addressLine2", "");
		long userId = (long) XmlHelper.getSingleElementText(orderElement, "customerId", 0);
		Timestamp orderdate = XmlHelper.getSingleElementText(orderElement, "orderDate", new Timestamp(0));
		Order order = new Order(orderId,addressLine1,addressLine2,totalAmount,orderdate,userId);
		return order;
		
	}
	
	private static void formatHelper(Document document,Element orderElement,Order order) {
		XmlHelper.addSingleElement(document, orderElement, "totalAmount", order.getTotalAmount(), null, null);
		XmlHelper.addSingleElement(document, orderElement, "addressLine1", order.getAddressLine1(), null, null);
		XmlHelper.addSingleElement(document, orderElement, "addressLine2", order.getAddressLine2(), null, null);
		XmlHelper.addSingleElement(document, orderElement, "customerId", order.getCustomerId(), null, null);
		XmlHelper.addSingleElementTimestamp(document, orderElement, "orderDate", order.getOrderDate(), null, null);
	}
}
