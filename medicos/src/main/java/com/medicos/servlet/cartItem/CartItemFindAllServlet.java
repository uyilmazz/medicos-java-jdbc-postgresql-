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
import com.medicos.business.xml.entity.CartItemXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.entity.CartItem;
import com.medicos.repository.CartItemRepository;

@WebServlet("/api/cartItems")
public class CartItemFindAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CartItemService cartItemService = new CartItemManager(new CartItemRepository());
			DataResult<List<CartItem>> result = cartItemService.getAll();
			Document document = CartItemXml.formatAll(result.getData());
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
