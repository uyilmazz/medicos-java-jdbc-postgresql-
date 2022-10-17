package com.medicos.servlet.orderItem;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.OrderItemService;
import com.medicos.business.concretes.OrderItemManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.entity.OrderItemXml;
import com.medicos.core.helper.ParseHelper;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.Result;
import com.medicos.entity.OrderItem;
import com.medicos.repository.OrderItemRepository;

@WebServlet("/api/orderItems/findByOrderId")
public class OrderItemFindByOrderIdServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isInteger(request.getParameter("orderId"));
			Document document;
			if(isLong) {
				long id = Long.parseLong(request.getParameter("orderId"));
				OrderItemService orderItemService = new OrderItemManager(new OrderItemRepository());
				DataResult<List<OrderItem>> result = orderItemService.getByOrderId(id);
				if(result.isSuccess()) {
					document = OrderItemXml.formatAll(result.getData());
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
