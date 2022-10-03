package com.medicos.servlet.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.medicos.business.xml.UserXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.User;

@WebServlet("/api/users")
public class FindAllUserServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			User user = new User(5,"user 1 name","user 1 email","user 1 password","user 1 imageURl");
			Document document = UserXml.format(user);
			
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}