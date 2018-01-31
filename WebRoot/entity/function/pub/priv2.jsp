<%@ page import = "java.util.*,com.whaty.platform.interaction.*,com.whaty.platform.test.*"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>

<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
	InteractionUserPriv interactionUserPriv = (InteractionUserPriv)session.getAttribute("interactionUserPriv");
	String userType = null;
	User user = null;
	UserSession us = (UserSession)session.getAttribute("user_session");
	if(us != null) {
		userType = (String)session.getAttribute("userType");
		if("teacher".equalsIgnoreCase(userType)){
		   	user = (User)session.getAttribute("teacher_eduplatform_user");
		   	if(user!=null){
		   	}else{
		   	  user = (User)session.getAttribute("eduplatform_user");
		   	}
		}else {
		  	user = (User)session.getAttribute("student_eduplatform_user");
		  	if(user!=null){
		  	
		   	}else{
		   	  user = (User)session.getAttribute("eduplatform_user");
		   	}
		}
		if(user == null||user.getId()==null||user.getId().equals("null")||user.getId().equals("")) {
			PlatformFactory factory=PlatformFactory.getInstance();
			PlatformManage platformManage=factory.createPlatformManage();
			EntityUser euser=platformManage.getEntityUser(us.getId(),"student");
			request.getSession().removeAttribute("eduplatform_user");
			request.getSession().removeAttribute("eduplatform_priv");
			request.getSession().setAttribute("eduplatform_user",euser);
			System.err.println(euser);
	  	  	StudentPriv studentPriv=factory.getStudentPriv(us.getId());
	  	  	request.getSession().setAttribute("eduplatform_priv",studentPriv);
			user = (User)session.getAttribute("student_eduplatform_user");
		  	if(user!=null){
		   	}else{
		   	  user = (User)session.getAttribute("eduplatform_user");
		   	}
		}
		if(user!=null && (user.getName()==null || "".equals(user.getName()))){
			user.setName("未知");
		}

	} else {
%>	
	<script language="javascript">
		window.top.alert("登录超时，为了您的账户安全，请重新登录。");
		window.top.location = "/";
	</script>
<%
		return ;
	}
	
%>
