package com.whaty.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class CleanSessionTableServlet extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		SessionCounter.cleanOnlineUserTable();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		SessionCounter.cleanOnlineUserTable();
//		System.out.println("table clean d ");
	}

}
