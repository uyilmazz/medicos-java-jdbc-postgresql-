package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Department;
import com.medicos.entity.Doctor;

public class DoctorXml {
	
	public static Document format(Doctor doctor) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("doctor");
		Element doctorElement = document.getDocumentElement();
		doctorElement.setAttribute("id", Long.toString(doctor.getId()));
		formatHelper(document, doctorElement, doctor);
		return document;
	}
	
	public static Document formatAll(List<Doctor> doctorList) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("doctors");
		Element doctorsElement = document.getDocumentElement();
		for (int i = 0; i < doctorList.size(); i++) {
			Doctor doctor = doctorList.get(i);
			XmlHelper.addSingleElement(document, doctorsElement, "doctor", null, "id", Long.toString(doctor.getId()));
			Element doctorElement = (Element) doctorsElement.getElementsByTagName("doctor").item(i);
			formatHelper(document, doctorElement, doctor);
		}
		return document;
	}
	
	public static Doctor parse(Document document) {
		Element doctorElement = document.getDocumentElement();
		return parseHelper(doctorElement);
	}
	
	public static List<Doctor> parseAll(Document document){
		List<Doctor> doctorList = new ArrayList<>();
		Element doctorsElement = document.getDocumentElement();
		NodeList doctorNodeList = doctorsElement.getElementsByTagName("doctor");
		for (int i = 0; i < doctorNodeList.getLength(); i++) {
			Element doctorElement = (Element) doctorNodeList.item(i);
			Doctor doctor = parseHelper(doctorElement);
			doctorList.add(doctor);
		}
		return doctorList;
	}
	
	public static Doctor parseHelper(Element doctorElement) {
		long doctorId = doctorElement.getAttribute("id") != "" ? Long.parseLong(doctorElement.getAttribute("id")) : 0 ;
		String doctorFirstName = XmlHelper.getSingleElementText(doctorElement, "firstName", "");
		String doctorLastName = XmlHelper.getSingleElementText(doctorElement, "lastName", "");
		String doctorEmail = XmlHelper.getSingleElementText(doctorElement, "email", "");
		String doctorPassword = XmlHelper.getSingleElementText(doctorElement, "password", "");
		String doctorAbout = XmlHelper.getSingleElementText(doctorElement, "about", "");
		String doctorImageUrl = XmlHelper.getSingleElementText(doctorElement, "imageUrl", "");
		int doctorExperienceMonth = (int) XmlHelper.getSingleElementText(doctorElement, "experienceMonth", 0);
		int doctorPatienceCount = (int) XmlHelper.getSingleElementText(doctorElement, "patienceCount", 0);
		Doctor doctor = new Doctor(doctorId,doctorFirstName,doctorLastName,doctorEmail,doctorPassword,doctorAbout,doctorImageUrl,doctorExperienceMonth,doctorPatienceCount);
		
		Element departmentElement = (Element) doctorElement.getElementsByTagName("department").item(0);
		Department department = DepartmentXml.parseHelper(departmentElement);
		doctor.setDepartment(department);
		return doctor;
	}

	public static void formatHelper(Document document,Element doctorElement,Doctor doctor) {
		XmlHelper.addSingleElement(document, doctorElement, "firstName", doctor.getFirstName(), null, null);
		XmlHelper.addSingleElement(document, doctorElement, "lastName", doctor.getLastName(), null, null);
		XmlHelper.addSingleElement(document, doctorElement, "email", doctor.getEmail(), null, null);
		XmlHelper.addSingleElement(document, doctorElement, "password", doctor.getPassword(), null, null);
		XmlHelper.addSingleElement(document, doctorElement, "about", doctor.getAbout(), null, null);
		XmlHelper.addSingleElement(document, doctorElement, "imageUrl", doctor.getImageUrl(), null, null);
		XmlHelper.addSingleElement(document, doctorElement, "experienceMonth", doctor.getExperienceMonth(), null, null);
		XmlHelper.addSingleElement(document, doctorElement, "patienceCount", doctor.getPatienceCount(), null, null);
		XmlHelper.addSingleElement(document, doctorElement, "department",null, "id", Integer.toString(doctor.getDepartment().getId()));
		
		Element departmentElement = (Element) doctorElement.getElementsByTagName("department").item(0);
		DepartmentXml.formatHelper(document, departmentElement, doctor.getDepartment());
	}
}
