package com.grubmenow.service.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Http Servlet to handle API Request 
 *
 */
public class GenericAPIServlet extends HttpServlet {
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	private static Map<String, IService> serviceRegistry = new HashMap<>();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String apiName = getAPIName(req);

		IService service = serviceRegistry.get(apiName);

		StringWriter sw = new StringWriter();
		IOUtils.copy(req.getInputStream(), sw);

		System.out.println(sw.toString());

		Object serviceRequest = objectMapper.readValue(sw.toString(), service.getRequestClass());
		Object serviceResponse = service.executeService(serviceRequest);

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		String jsonObject = objectMapper.writeValueAsString(serviceResponse);
		out.print(jsonObject);
		out.flush();
		out.close();
	}
	
	private String getAPIName (HttpServletRequest req) {
		return StringUtils.substringAfterLast(req.getRequestURI(), "/");
	}
	
	@Override 
	public void init() {
		serviceRegistry.put("searchFoodItems", new SearchFoodItemService());
		serviceRegistry.put("getDetailPageResults", new GetDetailPageResultsService());
	}
}