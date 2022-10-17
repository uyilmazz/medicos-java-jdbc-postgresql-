package com.medicos.servlet.appointment;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.medicos.business.abstracts.AppointmentService;
import com.medicos.business.concretes.AppointmentManager;
import com.medicos.business.xml.entity.AppointmentXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.entity.Appointment;
import com.medicos.repository.AppointmentRepository;

@WebServlet("/api/appointments")
public class AppointmentFindAllServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			AppointmentService appointmentService = new AppointmentManager(new AppointmentRepository());
			DataResult<List<Appointment>> appointment = appointmentService.getAll();
			Document document = AppointmentXml.formatAll(appointment.getData());
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
