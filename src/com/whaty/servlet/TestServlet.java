package com.whaty.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;

public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String k = request.getParameter("k");
		String sql = " SELECT PBS.REG_NO, PBS.TRUE_NAME, PBS.EMAIL, PE.NAME, PBS.PHONE, EC.NAME ZIGE FROM PE_BZZ_STUDENT PBS INNER JOIN PE_ENTERPRISE PE ON PBS.FK_ENTERPRISE_ID = PE.ID LEFT JOIN ENUM_CONST EC ON PBS.ZIGE = EC.ID WHERE PBS.PHONE = '"
				+ k + "' ";
		dbpool db = new dbpool();
		JSONObject json = new JSONObject();
		try {
			MyResultSet rs = db.executeQuery(sql);
			if (rs != null) {
				while (rs.next()) {
					json.put("账号", rs.getString("REG_NO"));
					json.put("姓名", rs.getString("TRUE_NAME"));
					json.put("邮箱", rs.getString("EMAIL"));
					json.put("机构名称", rs.getString("NAME"));
					json.put("电话", rs.getString("PHONE"));
					json.put("资格类型", rs.getString("ZIGE"));
				}
				response.getOutputStream().write(
						json.toString().getBytes("UTF-8"));
				response.setContentType("text/json; charset=UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db = null;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String k = request.getParameter("k");
		String sql = " SELECT PBS.REG_NO, PBS.TRUE_NAME, PBS.EMAIL, PE.NAME, PBS.PHONE, EC.NAME ZIGE FROM PE_BZZ_STUDENT PBS INNER JOIN PE_ENTERPRISE PE ON PBS.FK_ENTERPRISE_ID = PE.ID LEFT JOIN ENUM_CONST EC ON PBS.ZIGE = EC.ID WHERE PBS.PHONE = '"
				+ k + "' ";
		dbpool db = new dbpool();
		JSONObject json = new JSONObject();
		try {
			MyResultSet rs = db.executeQuery(sql);
			if (rs != null) {
				while (rs.next()) {
					json.put("账号", rs.getString("REG_NO"));
					json.put("姓名", rs.getString("TRUE_NAME"));
					json.put("邮箱", rs.getString("EMAIL"));
					json.put("机构名称", rs.getString("NAME"));
					json.put("电话", rs.getString("PHONE"));
					json.put("资格类型", rs.getString("ZIGE"));
				}
				response.getOutputStream().write(
						json.toString().getBytes("UTF-8"));
				response.setContentType("text/json; charset=UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db = null;
	}
}
