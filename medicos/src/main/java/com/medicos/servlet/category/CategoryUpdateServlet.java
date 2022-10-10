package com.medicos.servlet.category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.CategoryService;
import com.medicos.business.concretes.CategoryManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.CategoryXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.Result;
import com.medicos.entity.Category;
import com.medicos.repository.CategoryRepository;

@WebServlet("/api/categories/update")
public class CategoryUpdateServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Document document = XmlHelper.parse(request.getInputStream());
			Document responseDocument;
			if(document != null) {
				Category category = CategoryXml.parse(document);
				CategoryService categoryService = new CategoryManager(new CategoryRepository());
				Result result = categoryService.update(category);
				if(result.isSuccess()) {
					responseDocument = XmlHelper.resultDocument(response, result, 200);
				}else {
					responseDocument = XmlHelper.resultDocument(response, result, 400);
				}		
			}else {
				Result result = new Result(false,ResultMessages.RequestParameterError);
				responseDocument = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(responseDocument, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
