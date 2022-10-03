package com.medicos.business.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Province;

public class ProvinceXml {
	public static Document format(Province province) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("province");
		Element provinceElement = document.getDocumentElement();
		provinceElement.setAttribute("id", Integer.toString(province.getId()));
		formatHelper(document, provinceElement, province);
		return document;
	}
	
	public static Document formatAll(List<Province> provinceList) throws ParserConfigurationException {
		Document document = XmlHelper.createDocument("provinces");
		Element root = document.getDocumentElement();
		for (int i = 0; i < provinceList.size(); i++) {
			Province province = provinceList.get(i);
			XmlHelper.addSingleElement(document, root, "province", null, "id", Integer.toString(province.getId()));
			Element provinceElement = (Element) root.getElementsByTagName("province").item(i);
			formatHelper(document, provinceElement, province);
		}
		return document;
	}
	
	public static Province parse(Document document) {
		Element provinceElement = document.getDocumentElement();
		return parseHelper(provinceElement);
	}
	
	public static List<Province> parseAll(Document document){
		List<Province> provinceList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList provinceNodeList = root.getElementsByTagName("province");
		for (int i = 0; i < provinceNodeList.getLength(); i++) {
			Element provinceElement = (Element) provinceNodeList.item(i);
			Province province = parseHelper(provinceElement);
			provinceList.add(province);
		}
		return provinceList;
	}
	
	public static Province parseHelper(Element provinceElement) {
		int provinceId = provinceElement.getAttribute("id") != "" ? Integer.parseInt(provinceElement.getAttribute("id")) : 0 ;
		String provinceName = XmlHelper.getSingleElementText(provinceElement, "name", "");
		Province province = new Province(provinceId,provinceName);
		return province;
	}
	
	public static void formatHelper(Document document,Element provinceElement,Province province) {
		XmlHelper.addSingleElement(document, provinceElement, "name", province.getName(), null, null);
	}
}
