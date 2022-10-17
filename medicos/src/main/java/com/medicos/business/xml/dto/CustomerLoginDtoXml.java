package com.medicos.business.xml.dto;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.medicos.core.helper.XmlHelper;
import com.medicos.dto.CustomerLoginDto;


public class CustomerLoginDtoXml {
	public static Document format(CustomerLoginDto customerLoginDto) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("customerLoginDto");
		Element customerLoginDtoElement = document.getDocumentElement();
		formatHelper(document, customerLoginDtoElement, customerLoginDto);
		return document;
	}
		
	public static CustomerLoginDto parse(Document document) {
		Element customerLoginDtoElement = document.getDocumentElement();
		return parseHelper(customerLoginDtoElement);
	}	
	
	public static CustomerLoginDto parseHelper(Element customerLoginDtoElement) {
		String customerLoginDtoEmail = XmlHelper.getSingleElementText(customerLoginDtoElement, "email", "");
		String customerLoginDtoPassword = XmlHelper.getSingleElementText(customerLoginDtoElement, "password", "");
		CustomerLoginDto customerLoginDto = new CustomerLoginDto(customerLoginDtoEmail,customerLoginDtoPassword);
		return customerLoginDto;
	}
	
	public static void formatHelper(Document document,Element customerLoginDtoElement,CustomerLoginDto customerLoginDto) {
		XmlHelper.addSingleElement(document, customerLoginDtoElement, "email", customerLoginDto.getEmail(), null, null);
		XmlHelper.addSingleElement(document, customerLoginDtoElement, "password", customerLoginDto.getPassword(), null, null);
	}
}
