package com.medicos.servlet.cart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.CartService;
import com.medicos.business.concretes.CartManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.entity.CartXml;
import com.medicos.core.helper.ParseHelper;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.Result;
import com.medicos.entity.Cart;
import com.medicos.repository.CartRepository;

@WebServlet("/api/carts/findByCustomerId")
public class CartFindByCustomerIdServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("customerId"));
			Document document;
			if(isLong) {
				long id = Long.parseLong(request.getParameter("customerId"));
				CartService cartService = new CartManager(new CartRepository());
				DataResult<Cart> result = cartService.getByCustomerId(id);
				if(result.isSuccess()) {
					document = CartXml.format(result.getData());
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
