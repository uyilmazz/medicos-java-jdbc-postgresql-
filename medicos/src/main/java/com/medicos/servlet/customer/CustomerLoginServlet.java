package com.medicos.servlet.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.CustomerService;
import com.medicos.business.concretes.CustomerManager;
import com.medicos.business.messages.ResultMessages;
import com.medicos.business.xml.dto.CustomerLoginDtoXml;
import com.medicos.business.xml.entity.CustomerXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.Result;
import com.medicos.dto.CustomerLoginDto;
import com.medicos.entity.Customer;
import com.medicos.repository.CustomerRepository;

@WebServlet("/api/customers/login")
public class CustomerLoginServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Document document = XmlHelper.parse(request.getInputStream());
			Document responseDocument;
			if(document != null) {
				CustomerLoginDto customerLoginDto = CustomerLoginDtoXml.parse(document);
				CustomerService customerService = new CustomerManager(new CustomerRepository());
				DataResult<Customer> result = customerService.login(customerLoginDto);
				if(result.isSuccess()) {
					responseDocument = CustomerXml.format(result.getData());
				}else {
					responseDocument = XmlHelper.resultDocument(response, result, 400);
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
