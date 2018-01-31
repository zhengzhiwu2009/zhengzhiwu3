package com.whaty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.whaty.platform.database.oracle.dbpool;

public class WebcastServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String kString = request.getParameter("k");
		String uid = request.getParameter("uid");
		String sql = "SELECT 1 FROM LIVE_SEQ_STU WHERE K_PARAM = '" + kString + "' AND ID = '" + uid + "'";
		String adminSql = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE ID = '" + kString + "'";
		int c = 0;
		dbpool db = new dbpool();
		try {
			c = db.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			out.write("fail");
		}
		if (c != 0) {
			out.write("pass");
		} else {
			try {
				c = db.executeUpdate(adminSql);
			} catch (Exception e) {
				e.printStackTrace();
				out.write("fail");
			}
			if (c != 0)
				out.write("pass");
			else
				out.write("fail");
		}
		out.flush();
		out.close();
		db = null;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String kString = request.getParameter("k");
		String uid = request.getParameter("uid");
		String sql = "SELECT 1 FROM LIVE_SEQ_STU WHERE K_PARAM = '" + kString + "' AND ID = '" + uid + "'";
		String adminSql = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE ID = '" + kString + "'";
		int c = 0;
		dbpool db = new dbpool();
		try {
			c = db.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			out.write("fail");
		}
		if (c != 0) {
			out.write("pass");
		} else {
			try {
				c = db.executeUpdate(adminSql);
			} catch (Exception e) {
				e.printStackTrace();
				out.write("fail");
			}
			if (c != 0)
				out.write("pass");
			else
				out.write("fail");
		}
		out.flush();
		out.close();
		db = null;
	}
}
