package com.medicos.servlet.cartItem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.medicos.business.xml.CartItemXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.CartItem;

@WebServlet("/api/cartItems")
public class FindAllCartItemServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			CartItem cartItem = new CartItem(5, 1200, 2, 2400, 3, 4);
			Document document = CartItemXml.format(cartItem);
			
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
