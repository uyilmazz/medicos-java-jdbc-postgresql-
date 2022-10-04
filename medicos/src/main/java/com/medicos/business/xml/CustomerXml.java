package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Customer;

public class CustomerXml {
	
	public static Document format(Customer customer) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("customer");
		Element customerElement = document.getDocumentElement();
		customerElement.setAttribute("id", Long.toString(customer.getId()));
		formatHelper(document, customerElement, customer);
		return document;
	}
	
	public static Document formatAll(List<Customer> customers) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("customers");
		Element customersElement = document.getDocumentElement();
		for (int i = 0; i < customers.size(); i++) {
			Customer customer = customers.get(i);
			XmlHelper.addSingleElement(document, customersElement, "customer", null, "id", Long.toString(customer.getId()));
			Element customerElement = (Element) customersElement.getElementsByTagName("customer").item(i);
			formatHelper(document, customerElement, customer);
		}
		return document;
	}
	
	public static Customer parse(Document document) {
		Element customerElement = document.getDocumentElement();
		return parseHelper(customerElement);
	}
	
	public static List<Customer> parseAll(Document document){
		List<Customer> customerList = new ArrayList<>();
		Element customersElement = document.getDocumentElement();
		NodeList customerNodeList = customersElement.getElementsByTagName("user");
		for (int i = 0; i < customerNodeList.getLength(); i++) {
			Element customerElement = (Element) customerNodeList.item(i);
			Customer user = parseHelper(customerElement);
			customerList.add(user);
		}
		return customerList;
	}
	
	public static Customer parseHelper(Element customerElement) {
		long customerId = Long.parseLong(customerElement.getAttribute("id"));
		String customerName = XmlHelper.getSingleElementText(customerElement, "name", "");
		String customerEmail = XmlHelper.getSingleElementText(customerElement, "email", "");
		String customerPassword = XmlHelper.getSingleElementText(customerElement, "password", "");
		String customerImageUrl = XmlHelper.getSingleElementText(customerElement, "imageUrl", "");
		Customer customer = new Customer(customerId,customerName,customerEmail,customerPassword,customerImageUrl);
		return customer;
	}
	
	public static void formatHelper(Document document,Element customerElement,Customer customer) {
		XmlHelper.addSingleElement(document, customerElement, "name", customer.getName(), null, null);
		XmlHelper.addSingleElement(document, customerElement, "email", customer.getEmail(), null, null);
		XmlHelper.addSingleElement(document, customerElement, "password", customer.getPassword(), null, null);
		XmlHelper.addSingleElement(document, customerElement, "imageUrl", customer.getImageUrl(), null, null);
	}
}
