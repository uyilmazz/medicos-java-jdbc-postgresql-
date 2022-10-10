package com.medicos.servlet.province;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.medicos.business.abstracts.ProvinceService;
import com.medicos.business.concretes.ProvinceManager;
import com.medicos.business.xml.ProvinceXml;
import com.medicos.core.helper.XmlHelper;
import com.medicos.core.result.DataResult;
import com.medicos.entity.Province;
import com.medicos.repository.ProvinceRepository;

@WebServlet("/api/provinces")
public class ProvinceFindAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProvinceService provinceService = new ProvinceManager(new ProvinceRepository());
			DataResult<List<Province>> result = provinceService.getAll();
			Document document = ProvinceXml.formatAll(result.getData());
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}
