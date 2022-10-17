package com.medicos.servlet.address;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.medicos.business.abstracts.AddressService;
import com.medicos.business.concretes.AddressManager;
import com.medicos.business.xml.entity.AddressXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.entity.Address;
import com.medicos.repository.AddressRepository;

@WebServlet("/api/addresses")
public class AddressFindAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			AddressService addressService = new AddressManager(new AddressRepository());
			DataResult<List<Address>> addresses = addressService.getAll();
			Document document = AddressXml.formatAll(addresses.getData());
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
