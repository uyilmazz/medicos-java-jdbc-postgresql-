package com.medicos.business.xml.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Department;

public class DepartmentXml {

	public static Document format(Department department) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("department");
		Element departmentElement = document.getDocumentElement();
		departmentElement.setAttribute("id", Integer.toString(department.getId()));
		formatHelper(document, departmentElement, department);
		return document;
	}
	
	public static Document formatAll(List<Department> departmentList) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("departments");
		Element root = document.getDocumentElement();
		for (int i = 0; i < departmentList.size(); i++) {
			Department department = departmentList.get(i);
			XmlHelper.addSingleElement(document, root, "department", null, "id", Integer.toString(department.getId()));
			Element departmentElement = (Element) root.getElementsByTagName("department").item(i);
			formatHelper(document, departmentElement, department);
		}
		return document;
	}
	
	public static Department parse(Document document) {
		Element departmentElement = document.getDocumentElement();
		return parseHelper(departmentElement);
	}
	
	public static List<Department> parseAll(Document document){
		List<Department> departmentList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList departmentNodeList = root.getElementsByTagName("department");
		for (int i = 0; i < departmentNodeList.getLength(); i++) {
			Element departmentElement = (Element) departmentNodeList.item(i);
			Department department = parseHelper(departmentElement);
			departmentList.add(department);
		}
		return departmentList;
	}
	
	public static Department parseHelper(Element departmentElement) {
		int departmentId = departmentElement.getAttribute("id") != "" ? Integer.parseInt(departmentElement.getAttribute("id")) : 0 ;
		String departmentName = XmlHelper.getSingleElementText(departmentElement, "name", "");
		String departmentImageUrl = XmlHelper.getSingleElementText(departmentElement, "imageUrl", "");
		Department department = new Department(departmentId,departmentName,departmentImageUrl);
		return department;
	}
	
	public static void formatHelper(Document document,Element departmentElement,Department department) {
		XmlHelper.addSingleElement(document, departmentElement, "name", department.getName(), null, null);
		XmlHelper.addSingleElement(document, departmentElement, "imageUrl", department.getImageUrl(), null, null);
	}
}
