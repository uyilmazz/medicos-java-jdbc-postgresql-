package com.medicos.core.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHelper {
	
	private static DocumentBuilderFactory documentBuilderFactory;
	public static DocumentBuilderFactory getDocumentBuilderFactory() {
		if(documentBuilderFactory == null) {
			documentBuilderFactory =  DocumentBuilderFactory.newInstance();
		}
		return documentBuilderFactory;
	}
	
	private static TransformerFactory transformerFactory;
	public static TransformerFactory getTransformerFactory() {
		if(transformerFactory == null) {
			transformerFactory = TransformerFactory.newInstance();
		}
		return transformerFactory;
	}
	
	public static Document createDocument(String root) throws ParserConfigurationException {
		DocumentBuilder documentBuilder = getDocumentBuilderFactory().newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement(root);
		document.appendChild(element);
		return document;
	}
	
	public static Document parse(InputStream in) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder documentBuilder = getDocumentBuilderFactory().newDocumentBuilder();
		Document document = documentBuilder.parse(in);
		return document;
	}
	
	public static void dump(Document document, OutputStream out) throws TransformerException, IOException {
		Transformer transformer = getTransformerFactory().newTransformer();
		DOMSource data = new DOMSource(document);
		StreamResult result = new StreamResult(out);
		transformer.transform(data, result);
		out.close();
	}
	
	public static String getSingleElementText(Element parent,String tag,String defaultValue) {
		NodeList elementList = parent.getElementsByTagName(tag);
		if(elementList.getLength() == 0) {
			return defaultValue;
		}
		return elementList.item(0).getTextContent();
	}
	
	public static double getSingleElementText(Element parent,String tag,double defaultValue) {
		String content = getSingleElementText(parent, tag, Double.toString(defaultValue));
		return Double.parseDouble(content);
	}
	
	public static boolean getSingleElementText(Element parent,String tag,Boolean defaultValue) {
		String content = getSingleElementText(parent, tag, Boolean.toString(defaultValue));
		return Boolean.parseBoolean(content);
	}
	
	public static Timestamp getSingleElementText(Element parent,String tag,Timestamp defaultValue) throws ParseException {
		String content = getSingleElementText(parent, tag,defaultValue.toString());
		System.out.println(defaultValue);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    Date parsedDate = dateFormat.parse(content);
	    Timestamp timeStamp = new Timestamp(parsedDate.getTime());
	    return timeStamp;
	}
	
	public static void addSingleElement(Document document,Element parent,String tag,String content,String attribute,String attributeValue) {
		Element element = document.createElement(tag);
		if (content != null) {
			element.setTextContent(content);
		}

		if (attribute != null) {
			element.setAttribute(attribute, attributeValue);
		}

		if (parent == null) {
			document.appendChild(element);
		} else {
			parent.appendChild(element);
		}
	}
	
	
	public static void addSingleElement(Document document,Element parent,String tag,double content,String attribute,String attributeValue) {
		addSingleElement(document, parent, tag, Double.toString(content), attribute, attributeValue);
	}
	
	public static void addSingleElement(Document document,Element parent,String tag,boolean content,String attribute,String attributeValue) {
		addSingleElement(document, parent, tag, Boolean.toString(content), attribute, attributeValue);
	}
	
	public static void addSingleElementTimestamp(Document document,Element parent,String tag,Timestamp content,String attribute,String attributeValue) {
		addSingleElement(document, parent, tag, content != null ? content.toString() : null, attribute, attributeValue);
	}
}
