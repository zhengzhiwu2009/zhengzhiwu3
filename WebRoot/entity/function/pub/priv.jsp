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
	
	String contextPath = request.getContextPath();
	
	InteractionUserPriv interactionUserPriv = (InteractionUserPriv)session.getAttribute("interactionUserPriv");
	OpenCourse openCourse = (OpenCourse)session.getAttribute("openCourse");
	
	String teachclass_id = "";
	String courseId = (String)session.getAttribute("courseId");
	String userType = null;
	User user = null;
	User teacher = null;
	User student = null;
	
	UserSession us = (UserSession)session.getAttribute("user_session");
	
	if(interactionUserPriv != null && us != null) {
		openCourse = (OpenCourse)session.getAttribute("openCourse");
//System.out.println(openCourse.getId() + "+++++++++++");
		teachclass_id = interactionUserPriv.getTeachclassId();
		//teachclass_id=(String)session.getAttribute("openId");
		userType = (String)session.getAttribute("userType");
		
		teacher = null;
		student = null;
		User user1=null;
		
		if("teacher".equalsIgnoreCase(userType)){
		   
		   	user = (User)session.getAttribute("teacher_eduplatform_user");
		   	if(user!=null){
		   	}else{
		   	  user = (User)session.getAttribute("eduplatform_user");
		   	}
		   		
			teacher = user;
		}else {
		
		  	user = (User)session.getAttribute("student_eduplatform_user");
		  	if(user!=null){
		   	}else{
		   	  user = (User)session.getAttribute("eduplatform_user");
		   	}
			student = user;
					
		}
		
		if(user == null||user.getId()==null||user.getId().equals("null")||user.getId().equals("")) {

			//System.out.println("us_id:"+us.getLoginId());
			//System.out.println("---------------------session -user: " + user.toString());
			//System.out.println("---------------------session -user.getId(): " + user.getId());
			//System.out.println("---------------------session -user.getLoginID(): " + user.getLoginId());
			//System.out.println("---------------------session -user.getName(): " + user.getName());
			//System.out.println("____00000euser: "+session.getAttribute("00000euser"));
			//System.out.println("____00000 "+session.getAttribute("000000"));
			

			PlatformFactory factory=PlatformFactory.getInstance();
			PlatformManage platformManage=factory.createPlatformManage();
		//System.err.println(us.getId());
			EntityUser euser=platformManage.getEntityUser(us.getId(),"student");
			request.getSession().removeAttribute("eduplatform_user");
			request.getSession().removeAttribute("eduplatform_priv");
			request.getSession().setAttribute("eduplatform_user",euser);
			System.err.println(euser);
	  	  	StudentPriv studentPriv=factory.getStudentPriv(us.getId());
	  	  	request.getSession().setAttribute("eduplatform_priv",studentPriv);
	  	  	
	  	  	//System.out.println("---------------------session -user2.getId(): " + user.getId());
					  	//System.out.println("_____________student_eduplatform_user: "+user1);
					  	//System.out.println("_____________userType: "+userType);
			%>	
				<!-- script language="javascript">
					window.top.alert("登录超时，为了您的账户安全，请重新登录。");
					window.top.location = "/";
				</script -->
			<%
			
			user = (User)session.getAttribute("student_eduplatform_user");
		  	if(user!=null){
		   	}else{
		   	  user = (User)session.getAttribute("eduplatform_user");
		   	}
			student = user;
				
		}
		if(user!=null && (user.getName()==null || "".equals(user.getName()))){
			user.setName("未知");
		}

	} else {
	//System.out.println("---------------------session -usersession: " + us.toString());
	//System.out.println("---------------------session -interactionUserPriv: " + interactionUserPriv.toString());
%>	
	<script language="javascript">
		window.top.alert("登录超时，为了您的账户安全，请重新登录。");
		window.top.location = "/";
	</script>
<%
	return ;
	}
	
%>
