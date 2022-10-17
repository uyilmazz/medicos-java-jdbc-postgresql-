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
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.entity.AddressXml;
import com.medicos.core.helper.ParseHelper;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.Result;
import com.medicos.entity.Address;
import com.medicos.repository.AddressRepository;

@WebServlet("/api/addresses/findById")
public class AddressFindByIdServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("id"));
			Document document;
			if(isLong) {
				long id = Long.parseLong(request.getParameter("id"));
				AddressService addressService = new AddressManager(new AddressRepository());
				DataResult<Address> addresses = addressService.getById(id);
				if(addresses.isSuccess()) {
					document = AddressXml.format(addresses.getData());
				}else {
					document = XmlHelper.resultDocument(response, addresses, 400);
				}		
			}else {
				Result result = new Result(false,ResultMessages.RequestParameterError);
				document = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
