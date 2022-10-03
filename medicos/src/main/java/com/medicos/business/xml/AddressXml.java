package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Address;
import com.medicos.entity.Province;

public class AddressXml {

	public static Document format(Address address) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("address");
		Element addressElement = document.getDocumentElement();
		addressElement.setAttribute("id", Long.toString(address.getId()));
		formatHelper(document, addressElement, address);
		return document;
	}
	
	public static Document formatAll(List<Address> addresses) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("addresses");
		Element addressesElement = document.getDocumentElement();
		for (int i = 0; i < addresses.size(); i++) {
			Address address = addresses.get(i);
			XmlHelper.addSingleElement(document, addressesElement, "address", null, "id",Long.toString(address.getId()));
			Element addressElement = (Element) addressesElement.getElementsByTagName("address").item(i);
			addressElement.setAttribute("id", Long.toString(address.getId()));
			formatHelper(document, addressElement, address);
		}
		return document;
	}
	
	public static Address parse(Document document) {
		Element addressElement = document.getDocumentElement();
		return parseHelper(addressElement);
	}
	
	public static List<Address> parseAll(Document document){
		List<Address> addressList = new ArrayList<>();
		Element addressesElement = document.getDocumentElement();
		NodeList addressNodeList = addressesElement.getElementsByTagName("address");
		for (int i = 0; i < addressNodeList.getLength(); i++) {
			Element addressElement = (Element) addressNodeList.item(i);
			Address address = parseHelper(addressElement);
			addressList.add(address);
		}
		return addressList;
	}
	
	public static Address parseHelper(Element addressElement) {
		long addressId = addressElement.getAttribute("id") != "" ? Long.parseLong(addressElement.getAttribute("id")) : 0;
		String addressLine1 = XmlHelper.getSingleElementText(addressElement, "addressLine1", "");
		String addressLine2 = XmlHelper.getSingleElementText(addressElement, "addressLine2", "");
		long userId = (long) XmlHelper.getSingleElementText(addressElement, "userId", 0);
		Address address = new Address(addressId,addressLine1,addressLine2,userId);
		
		Element provinceElement = (Element) addressElement.getElementsByTagName("province").item(0);
		Province province = ProvinceXml.parseHelper(provinceElement);
		address.setProvince(province);
		return address;
	}
	
	public static void formatHelper(Document document,Element addressElement,Address address) {
		XmlHelper.addSingleElement(document, addressElement, "addressLine1", address.getAddressLine1(), null, null);
		XmlHelper.addSingleElement(document, addressElement, "addressLine2", address.getAddressLine2(), null, null);
		XmlHelper.addSingleElement(document, addressElement, "userId", address.getUserId(), null, null);
		XmlHelper.addSingleElement(document, addressElement, "province", null, "id", Integer.toString(address.getProvince().getId()));
		
		Element provinceElement = (Element) addressElement.getElementsByTagName("province").item(0);
		ProvinceXml.formatHelper(document, provinceElement, address.getProvince());
	}
}
