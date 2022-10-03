package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Category;

public class CategoryXml {
	public static Document format(Category category) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("category");
		Element categoryElement = document.getDocumentElement();
		categoryElement.setAttribute("id", Integer.toString(category.getId()));
		formatHelper(document, categoryElement, category);
		return document;
	}
	
	public static Document formatAll(List<Category> categoryList) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("categories");
		Element categoriesElement = document.getDocumentElement();
		for (int i = 0; i < categoryList.size(); i++) {
			Category category = categoryList.get(i);
			XmlHelper.addSingleElement(document, categoriesElement, "category", false, "id", Integer.toString(category.getId()));
			Element categoryElement = (Element) categoriesElement.getElementsByTagName("category").item(i);
			formatHelper(document, categoryElement, category);
		}
		return document;
	}
	
	public static Category parse(Document document) {
		Element categoryElement = document.getDocumentElement();
		return parseHelper(categoryElement);
	}
	
	public static List<Category> parseAll(Document document){
		List<Category> categoryList = new ArrayList<>();	
		Element categoriesElement = document.getDocumentElement();
		NodeList categoryNodeList = categoriesElement.getElementsByTagName("category");
		for (int i = 0; i < categoryNodeList.getLength(); i++) {
			Element categoryElement = (Element) categoryNodeList.item(i);
			Category category = parseHelper(categoryElement);
			categoryList.add(category);
		}
		return categoryList;
	}
	
	private static void formatHelper(Document document,Element categoryElement,Category category) {
		XmlHelper.addSingleElement(document, categoryElement, "name", category.getName(), null, null);
		XmlHelper.addSingleElement(document, categoryElement, "imageUrl", category.getImageUrl(), null, null);
	}
	
	private static Category parseHelper(Element categoryElement) {
		int categoryId = categoryElement.getAttribute("id") != "" ? Integer.parseInt(categoryElement.getAttribute("id")) : 0;
		String categoryName = XmlHelper.getSingleElementText(categoryElement, "name", "");
		String categoryImageUrl = XmlHelper.getSingleElementText(categoryElement, "imageUrl", "");
		Category category = new Category(categoryId,categoryName,categoryImageUrl);
		return category;
	}

}
