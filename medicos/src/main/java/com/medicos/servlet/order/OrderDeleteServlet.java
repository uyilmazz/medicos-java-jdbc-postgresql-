package com.medicos.servlet.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.OrderService;
import com.medicos.business.concretes.OrderManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.helper.ParseHelper;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.Result;
import com.medicos.repository.OrderRepository;

@WebServlet("/api/orders/delete")
public class OrderDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("id"));
			Document document;
			if(isLong) {
				long id = Long.parseLong(request.getParameter("id"));
				OrderService orderService = new OrderManager(new OrderRepository());
				Result result = orderService.delete(id);
				if(result.isSuccess()) {
					document = XmlHelper.resultDocument(response, result, 200);
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