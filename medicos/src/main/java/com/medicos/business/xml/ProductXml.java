package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Category;
import com.medicos.entity.Product;

public class ProductXml {
	
	public static Document format(Product product) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("product");
		Element root = document.getDocumentElement();
		root.setAttribute("id", Long.toString(product.getId()));
		formatHelper(document, root, product);
		return document;
	}
	
	public static Document formatAll(List<Product> productList) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("products");
		Element root = document.getDocumentElement();
		for (int i = 0; i < productList.size(); i++) {
			Product product = productList.get(i);
			XmlHelper.addSingleElement(document, root, "product", false, "id", Long.toString(product.getId()));
			Element productElement = (Element) document.getElementsByTagName("product").item(i);
			formatHelper(document, productElement, product);
		}
		return document;
	}
	
	public static Product parse(Document document) {
		Element productElement = document.getDocumentElement();
		return parseHelper(productElement);
	}
	
	public static List<Product> parseAll(Document document){
		List<Product> productList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList productNodeList = root.getElementsByTagName("product");
		for (int i = 0; i < productNodeList.getLength(); i++) {
			Element productElement = (Element) productNodeList.item(i);
			Product product  = parseHelper(productElement);
			productList.add(product);
		}
		return productList;
	}
	
	private static void formatHelper(Document document,Element productElement,Product product) {
		XmlHelper.addSingleElement(document, productElement, "name", product.getName(), null, null);
		XmlHelper.addSingleElement(document, productElement, "description", product.getDescription(), null, null);
		XmlHelper.addSingleElement(document, productElement, "howToUse", product.getHowToUse(), null, null);
		XmlHelper.addSingleElement(document, productElement, "salesPrice", product.getSalesPrice(), null, null);
		XmlHelper.addSingleElement(document, productElement, "imageUrl", product.getImageUrl(), null, null);
		XmlHelper.addSingleElement(document, productElement, "category", null, "id", Long.toString(product.getCategory().getId()));
		
		Element categoryElement = (Element)document.getElementsByTagName("category").item(0);
		XmlHelper.addSingleElement(document, categoryElement, "name", product.getCategory().getName(), null, null);
		XmlHelper.addSingleElement(document, categoryElement, "imageUrl", product.getCategory().getImageUrl(), null, null);
	}
	
	private static Product parseHelper(Element productElement) {
		long productId = productElement.getAttribute("id") != "" ? Long.parseLong(productElement.getAttribute("id")) : 0;
		String productName = XmlHelper.getSingleElementText(productElement, "name", "");
		String productDescription = XmlHelper.getSingleElementText(productElement, "description", "");
		String productHowToUse = XmlHelper.getSingleElementText(productElement, "howToUse", "");
		double productSalesPrice = XmlHelper.getSingleElementText(productElement, "salesPrice",0);
		String productImageUrl = XmlHelper.getSingleElementText(productElement, "imageUrl", "");
		Product product = new Product(productId,productName,productDescription,productHowToUse,productSalesPrice,productImageUrl);
		
		Element categoryElement = (Element) productElement.getElementsByTagName("category").item(0);
		int categoryId = categoryElement.getAttribute("id") != "" ? Integer.parseInt(categoryElement.getAttribute("id")) : 0;
		String categoryName = XmlHelper.getSingleElementText(categoryElement, "name", "");
		String categoryImageUrl = XmlHelper.getSingleElementText(categoryElement, "imageUrl", "");
		Category category = new Category(categoryId,categoryName,categoryImageUrl);
		product.setCategory(category);
		return product;
	}
}





