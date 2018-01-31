<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	int chk = 0;
	String err_msg = "";
	String uid = "";
	String opid = "";
	dbpool db = new dbpool();
	try{	
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		uid = us.getSsoUser().getId();
		opid = request.getParameter("opencourseId");
		StringBuffer chkRepSqlBuffer = new StringBuffer();
		chkRepSqlBuffer.append(" SELECT 1 ");
		chkRepSqlBuffer.append("   FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE, ");
		chkRepSqlBuffer.append("        TRAINING_COURSE_STUDENT TCS, ");
		chkRepSqlBuffer.append("        PR_BZZ_TCH_OPENCOURSE   PBTO, ");
		chkRepSqlBuffer.append("        PE_BZZ_TCH_COURSE       PBTC ");
		chkRepSqlBuffer.append("  WHERE PBTSE.FK_TRAINING_ID = TCS.ID ");
		chkRepSqlBuffer.append("    AND TCS.COURSE_ID = PBTO.ID ");
		chkRepSqlBuffer.append("    AND PBTO.FK_COURSE_ID = PBTC.ID ");
		chkRepSqlBuffer.append("    AND TCS.STUDENT_ID = '" + uid + "' AND ");
		chkRepSqlBuffer.append("  PBTO.ID = '" + opid + "' ");
		chkRepSqlBuffer.append("    AND (TCS.GET_DATE + PBTC.STUDYDATES <= SYSDATE OR ");
		chkRepSqlBuffer.append("        (PBTSE.EXAM_TIMES IS NOT NULL AND ");
		chkRepSqlBuffer.append("        PBTSE.EXAM_TIMES >= PBTC.EXAMTIMES_ALLOW AND PBTSE.ISPASS = '0')) ");
	
		chk = db.executeUpdate(chkRepSqlBuffer.toString());
	}catch(Exception e){
		System.out.println("验证重新购买失败|" + uid + "|" + opid);
		chk = 0;
	}finally{
		db = null;
	}
	
	if (chk == 0) {
		err_msg = "验证失败！";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>提示信息</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<form name="print" >
			<div id="main_content">
			   <div class="content_title">提示信息</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<div class="" align="center"><%=err_msg %></div>
			   		</tr>
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">
								<input type=button value="关闭" onclick="window.close();" />
							</div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>
	<%
}else{
	response.sendRedirect("/entity/workspaceStudent/studentWorkspace_incompleteCoursePayment.action?opencourseId="+opid);
}
%>