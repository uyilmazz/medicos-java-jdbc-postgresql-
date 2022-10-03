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
import com.medicos.entity.Appointment;

public class AppointmentXml {
	public static Document format(Appointment appointment) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("appointment");
		Element appointmentElement = document.getDocumentElement();
		appointmentElement.setAttribute("id", Long.toString(appointment.getId()));
		formatHelper(document, appointmentElement, appointment);
		return document;
	}
	
	public static Document formatAll(List<Appointment> appointmentList) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("appointments");
		Element appointmentsElement = document.getDocumentElement();
		for (int i = 0; i < appointmentList.size(); i++) {
			Appointment appointment = appointmentList.get(i);
			XmlHelper.addSingleElement(document, appointmentsElement, "appointment", null, "id", Long.toString(appointment.getId()));
			Element appointmentElement = (Element) appointmentsElement.getElementsByTagName("appointment").item(i);
			formatHelper(document, appointmentElement, appointment);
		}
		return document;
	}
	
	public static Appointment parse(Document document) throws ParseException {
		Element appointmentElement = document.getDocumentElement();
		return parseHelper(appointmentElement);
	}
	
	public static List<Appointment> parseAll(Document document) throws ParseException{
		List<Appointment> appointmentList = new ArrayList<>();
		Element appointmentsElement = document.getDocumentElement();
		NodeList appointmentNodeList = appointmentsElement.getElementsByTagName("appointment");
		for (int i = 0; i < appointmentNodeList.getLength(); i++) {
			Element appointmentElement = (Element) appointmentNodeList.item(i);
			Appointment appointment = parseHelper(appointmentElement);
			appointmentList.add(appointment);
		}
		return appointmentList;
	}
	
	public static Appointment parseHelper(Element appointmentElement) throws ParseException {
		long appointmentId = appointmentElement.getAttribute("id") != "" ? Long.parseLong(appointmentElement.getAttribute("id")) : 0;
		Timestamp appointmenDate = XmlHelper.getSingleElementText(appointmentElement, "appointmenDate", new Timestamp(0));
		boolean isSelected = XmlHelper.getSingleElementText(appointmentElement, "isSelected", false);
		long userId = (long) XmlHelper.getSingleElementText(appointmentElement, "userId", 0);
		long doctorId = (long) XmlHelper.getSingleElementText(appointmentElement, "doctorId", 0);
		Appointment appointment = new Appointment(appointmentId,appointmenDate,isSelected,userId,doctorId);
		return appointment;
	}
	
	public static void formatHelper(Document document,Element appointmentElement,Appointment appointment) {
		XmlHelper.addSingleElementTimestamp(document, appointmentElement, "appointmenDate", appointment.getAppointmentDate(), null, null);
		XmlHelper.addSingleElement(document, appointmentElement, "isSelected", appointment.isSelected(), null, null);
		XmlHelper.addSingleElement(document, appointmentElement, "userId", appointment.getUserId(), null, null);
		XmlHelper.addSingleElement(document, appointmentElement, "doctorId", appointment.getDoctorId(), null, null);
	}
}
