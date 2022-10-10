package com.medicos.business.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.Result;

public class ResultXml {
	public static Document format(Result result) throws Exception {
		Document document = XmlHelper.createDocument("result");
		Element root = document.getDocumentElement();
		XmlHelper.addSingleElement(document, root, "message", result.getMessage(), null, null);
		XmlHelper.addSingleElement(document, root, "isSuccess", result.isSuccess(), null, null);
		return document;
	}
	
	public static Result parse(Document document) {
		Element root = document.getDocumentElement();
		String message = XmlHelper.getSingleElementText(root, "message", "");
		boolean isSuccess = Boolean.parseBoolean(XmlHelper.getSingleElementText(root, "isSuccess", "true")) ;
		Result result = new Result(isSuccess,message);
		return result;
	}
}
