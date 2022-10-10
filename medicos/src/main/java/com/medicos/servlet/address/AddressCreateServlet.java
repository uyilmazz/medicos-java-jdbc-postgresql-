package com.medicos.servlet.address;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.medicos.business.abstracts.AddressService;
import com.medicos.business.concretes.AddressManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.AddressXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.Result;
import com.medicos.entity.Address;
import com.medicos.repository.AddressRepository;

@WebServlet("/api/address/create")
public class AddressCreateServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Document document = XmlHelper.parse(request.getInputStream());
			Document responseDocument;
			if(document != null) {
				Address address = AddressXml.parse(document);
				AddressService addressService = new AddressManager(new AddressRepository());
				Result addresses = addressService.add(address);
				if(addresses.isSuccess()) {
					responseDocument = XmlHelper.resultDocument(response, addresses, 200);
				}else {
					responseDocument = XmlHelper.resultDocument(response, addresses, 400);
				}		
			}else {
				Result result = new Result(false,ResultMessages.RequestParameterError);
				responseDocument = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(responseDocument, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
