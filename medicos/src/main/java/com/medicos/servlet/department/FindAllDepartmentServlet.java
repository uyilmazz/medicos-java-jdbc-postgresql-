package com.medicos.servlet.department;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.medicos.business.xml.DepartmentXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Department;

@WebServlet("/api/departments")
public class FindAllDepartmentServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Department department = new Department(2,"department 1","department 1 imageUrl");
			Document document = DepartmentXml.format(department);
			
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
