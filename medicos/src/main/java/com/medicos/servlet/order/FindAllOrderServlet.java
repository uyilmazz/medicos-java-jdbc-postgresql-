package com.medicos.servlet.order;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.medicos.business.xml.OrderXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Order;

@WebServlet("/api/orders")
public class FindAllOrderServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Date date = new Date();
			Timestamp timeStamp = new Timestamp(Long.parseLong("0"));
			Order order = new Order(2,"order 1 addressline 1","order 1 addressline 2",2300,timeStamp,3);
			Document document = OrderXml.format(order);
			
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
