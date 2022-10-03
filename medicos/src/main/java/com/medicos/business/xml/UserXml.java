package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.User;

public class UserXml {
	
	public static Document format(User user) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("user");
		Element userElement = document.getDocumentElement();
		userElement.setAttribute("id", Long.toString(user.getId()));
		formatHelper(document, userElement, user);
		return document;
	}
	
	public static Document formatAll(List<User> users) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("users");
		Element usersElement = document.getDocumentElement();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			XmlHelper.addSingleElement(document, usersElement, "user", null, "id", Long.toString(user.getId()));
			Element userElement = (Element) usersElement.getElementsByTagName("user").item(i);
			formatHelper(document, userElement, user);;
		}
		return document;
	}
	
	public static User parse(Document document) {
		Element userElement = document.getDocumentElement();
		return parseHelper(userElement);
	}
	
	public static List<User> parseAll(Document document){
		List<User> userList = new ArrayList<>();
		Element usersElement = document.getDocumentElement();
		NodeList userNodeList = usersElement.getElementsByTagName("user");
		for (int i = 0; i < userNodeList.getLength(); i++) {
			Element userElement = (Element) userNodeList.item(i);
			User user = parseHelper(userElement);
			userList.add(user);
		}
		return userList;
	}
	
	public static User parseHelper(Element userElement) {
		long userId = Long.parseLong(userElement.getAttribute("id"));
		String userName = XmlHelper.getSingleElementText(userElement, "name", "");
		String userEmail = XmlHelper.getSingleElementText(userElement, "email", "");
		String userPassword = XmlHelper.getSingleElementText(userElement, "password", "");
		String userImageUrl = XmlHelper.getSingleElementText(userElement, "imageUrl", "");
		User user = new User(userId,userName,userEmail,userPassword,userImageUrl);
		return user;
	}
	
	public static void formatHelper(Document document,Element userElement,User user) {
		XmlHelper.addSingleElement(document, userElement, "name", user.getName(), null, null);
		XmlHelper.addSingleElement(document, userElement, "email", user.getEmail(), null, null);
		XmlHelper.addSingleElement(document, userElement, "password", user.getPassword(), null, null);
		XmlHelper.addSingleElement(document, userElement, "imageUrl", user.getImageUrl(), null, null);
	}
}
