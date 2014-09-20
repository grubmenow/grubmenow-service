package com.grubmenow.service.dashboard.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;

public class AbstractDashboadServlet extends HttpServlet {

	@SneakyThrows
    public void forwardTo(HttpServletRequest request, HttpServletResponse response, String page) {
    	getServletContext().getRequestDispatcher(page).forward(request, response);
    }
    
}
