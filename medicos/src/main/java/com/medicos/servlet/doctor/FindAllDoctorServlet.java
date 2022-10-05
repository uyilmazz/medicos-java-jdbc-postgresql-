package com.medicos.servlet.doctor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.xml.DoctorXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Department;
import com.medicos.entity.Doctor;

@WebServlet("/api/doctors")
public class FindAllDoctorServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Doctor doctor = new Doctor(5, "Doctor 1 first name","doctor 1 last name","Doctor 1 email","Doctor 1 password","Doctor 1 about","doctor 1 imageUrl",18,500);
			Department department = new Department(2,"Department 1","Department 1 imageUrl");
			doctor.setDepartment(department);
			Document document = DoctorXml.format(doctor);
			
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
