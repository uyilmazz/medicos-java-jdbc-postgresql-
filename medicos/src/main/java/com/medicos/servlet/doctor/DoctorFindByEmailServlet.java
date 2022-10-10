package com.medicos.servlet.doctor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.DoctorService;
import com.medicos.business.concretes.DoctorManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.DoctorXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.Result;
import com.medicos.entity.Doctor;
import com.medicos.repository.DoctorRepository;

@WebServlet("/api/doctors/findByEmail")
public class DoctorFindByEmailServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			Document document;
			if(email != null) {
				DoctorService doctorService = new DoctorManager(new DoctorRepository());
				DataResult<Doctor> result = doctorService.getByEmail(email);
				if(result.isSuccess()) {
					document = DoctorXml.format(result.getData());
				}else {
					document = XmlHelper.resultDocument(response, result, 400);
				}		
			}else {
				Result result = new Result(false,ResultMessages.RequestParameterError);
				document = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
