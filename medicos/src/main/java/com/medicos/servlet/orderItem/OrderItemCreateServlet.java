package com.medicos.servlet.orderItem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.OrderItemService;
import com.medicos.business.concretes.OrderItemManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.OrderItemXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.Result;
import com.medicos.entity.OrderItem;
import com.medicos.repository.OrderItemRepository;

@WebServlet("/api/orderItems/create")
public class OrderItemCreateServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Document document = XmlHelper.parse(request.getInputStream());
			Document responseDocument;
			if(document != null) {
				OrderItem orderItem = OrderItemXml.parse(document);
				OrderItemService orderItemService = new OrderItemManager(new OrderItemRepository());
				Result result = orderItemService.add(orderItem);
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
