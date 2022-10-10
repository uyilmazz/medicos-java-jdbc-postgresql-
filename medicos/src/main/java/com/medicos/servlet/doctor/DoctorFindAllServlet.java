package com.medicos.servlet.doctor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.DoctorService;
import com.medicos.business.concretes.DoctorManager;
import com.medicos.business.xml.DoctorXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.entity.Doctor;
import com.medicos.repository.DoctorRepository;

@WebServlet("/api/doctors")
public class DoctorFindAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			DoctorService doctorService = new DoctorManager(new DoctorRepository());
			DataResult<List<Doctor>> result = doctorService.getAll();
			Document document = DoctorXml.formatAll(result.getData());
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
