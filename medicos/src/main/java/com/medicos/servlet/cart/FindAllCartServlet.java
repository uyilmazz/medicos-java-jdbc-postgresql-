package com.medicos.servlet.cart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.xml.CartXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Cart;
import com.medicos.entity.CartItem;

@WebServlet("/api/carts")
public class FindAllCartServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Cart cart = new Cart(3,2350,2);		
			CartItem cartItem = new CartItem(5, 1200, 2, 2400, 3, 4);
			CartItem cartItem2 = new CartItem(8, 200, 3, 600, 7, 15);
			List<CartItem> cartItemList = new ArrayList<>();
			cartItemList.add(cartItem);
			cartItemList.add(cartItem2);
			cart.setCartItems(cartItemList);
			
			Document document = CartXml.format(cart);
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
