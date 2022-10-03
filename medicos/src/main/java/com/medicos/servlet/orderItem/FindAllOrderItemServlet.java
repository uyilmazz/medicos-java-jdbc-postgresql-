package com.medicos.servlet.orderItem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.xml.OrderItemXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.OrderItem;

@WebServlet("/api/orderItems")
public class FindAllOrderItemServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			OrderItem orderItem = new OrderItem(2,"orderItem 1 name","order 1 imageUrl",2300,3,6900,8);
			Document document = OrderItemXml.format(orderItem);
			
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
