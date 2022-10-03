package com.medicos.servlet.province;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.medicos.business.xml.ProvinceXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Province;

@WebServlet("/api/provinces")
public class FindAllProvinceServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Province province = new Province(6,"province 1 name");
			Document document = ProvinceXml.format(province);
			
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
