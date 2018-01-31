<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.util.Cookie.*"%>
<%@ page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../../student/pub/priv.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
		String second = request.getParameter("times");
		String seconds = studentOperationManage.getElectiveTimes(courseId,user.getId());
		
		seconds = Integer.toString(Integer.parseInt(second==null? "0":second)+Integer.parseInt(seconds==null?"0":seconds));
		if(request.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY_BAK) == null) {
			studentOperationManage.setElectiveTimes(courseId,user.getId(),seconds);
		}
//		studentOperationManage.setElectiveTimes(courseId,user.getId(),seconds);
%>
<script>
 window.close();
</script>