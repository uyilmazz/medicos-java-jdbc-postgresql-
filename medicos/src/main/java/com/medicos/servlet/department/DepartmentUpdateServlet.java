package com.medicos.servlet.department;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.DepartmentService;
import com.medicos.business.concretes.DepartmentManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.DepartmentXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.Result;
import com.medicos.entity.Department;
import com.medicos.repository.DepartmentRepository;

@WebServlet("/api/departments/update")
public class DepartmentUpdateServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Document document = XmlHelper.parse(request.getInputStream());
			Document responseDocument;
			if(document != null) {
				Department department = DepartmentXml.parse(document);
				DepartmentService departmentService = new DepartmentManager(new DepartmentRepository());
				Result result = departmentService.update(department);
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
