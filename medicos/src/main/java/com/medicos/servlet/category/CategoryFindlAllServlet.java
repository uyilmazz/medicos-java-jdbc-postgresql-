package com.medicos.servlet.category;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.CategoryService;
import com.medicos.business.concretes.CategoryManager;
import com.medicos.business.xml.CategoryXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.entity.Category;
import com.medicos.repository.CategoryRepository;

@WebServlet("/api/categories")
public class CategoryFindlAllServlet  extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategoryService categoryService = new CategoryManager(new CategoryRepository());
			DataResult<List<Category>> result = categoryService.getAll();
			Document document = CategoryXml.formatAll(result.getData());
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
