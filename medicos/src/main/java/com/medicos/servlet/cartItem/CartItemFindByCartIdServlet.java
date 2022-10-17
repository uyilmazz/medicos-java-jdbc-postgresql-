package com.medicos.servlet.cartItem;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.CartItemService;
import com.medicos.business.concretes.CartItemManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.entity.CartItemXml;
import com.medicos.core.helper.ParseHelper;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.Result;
import com.medicos.entity.CartItem;
import com.medicos.repository.CartItemRepository;

@WebServlet("/api/cartItems/findByCartId")
public class CartItemFindByCartIdServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("cartId"));
			Document document;
			if(isLong) {
				long id = Long.parseLong(request.getParameter("cartId"));
				CartItemService cartItemService = new CartItemManager(new CartItemRepository());
				DataResult<List<CartItem>> result = cartItemService.getByCartId(id);
				if(result.isSuccess()) {
					document = CartItemXml.formatAll(result.getData());
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
