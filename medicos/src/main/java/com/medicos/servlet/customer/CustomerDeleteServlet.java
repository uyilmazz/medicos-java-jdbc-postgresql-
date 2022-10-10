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
import com.medicos.core.helper.ParseHelper;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.Result;
import com.medicos.repository.CustomerRepository;

@WebServlet("/api/customers/delete")
public class CustomerDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("id"));
			Document document;
			if(isLong) {
				long id = Long.parseLong(request.getParameter("id"));
				CustomerService customerService = new CustomerManager(new CustomerRepository());
				Result result = customerService.delete(id);
				if(result.isSuccess()) {
					document = XmlHelper.resultDocument(response, result, 200);
				}else {
					document = XmlHelper.resultDocument(response, result, 400);
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
