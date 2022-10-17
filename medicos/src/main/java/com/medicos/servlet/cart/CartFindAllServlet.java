package com.medicos.servlet.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.CartService;
import com.medicos.business.concretes.CartManager;
import com.medicos.business.xml.entity.CartXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.entity.Cart;
import com.medicos.repository.CartRepository;

@WebServlet("/api/carts")
public class CartFindAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CartService cartService = new CartManager(new CartRepository());
			DataResult<List<Cart>> result = cartService.getAll();
			Document document = CartXml.formatAll(result.getData());
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
