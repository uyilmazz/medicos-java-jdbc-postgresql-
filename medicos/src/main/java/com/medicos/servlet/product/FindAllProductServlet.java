package com.medicos.servlet.product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.medicos.business.xml.ProductXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Category;
import com.medicos.entity.Product;

@WebServlet("/api/products")
public class FindAllProductServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Product product = new Product(0, "product 1", "product 1 description","product 1 howtouse", 2220, "product 1 imageUrl");
			Category category = new Category(1,"category 1","category 1 imageUrl");
			product.setCategory(category);
			Document document = ProductXml.format(product);
			
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
