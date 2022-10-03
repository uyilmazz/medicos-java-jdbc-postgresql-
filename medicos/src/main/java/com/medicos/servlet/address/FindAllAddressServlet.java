package com.medicos.servlet.address;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.xml.AddressXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.entity.Address;
import com.medicos.entity.Province;

@WebServlet("/api/addresses")
public class FindAllAddressServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Address address = new Address(4,"address line 1","address line 2",8);
			Province province = new Province(1,"Adana");
			address.setProvince(province);
			Document document = AddressXml.format(address);
			
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
