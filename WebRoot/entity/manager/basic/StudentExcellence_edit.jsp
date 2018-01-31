<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="org.hibernate.criterion.DetachedCriteria"%>
<%@page import="com.whaty.platform.entity.bean.PeBzzGoodStu"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.whaty.platform.entity.service.GeneralService"%>
<%@page import="org.hibernate.criterion.Restrictions"%>
<%@page import="com.whaty.platform.entity.bean.PeBzzStudent"%>
<%@page import="com.whaty.platform.entity.service.basic.PeBzzGoodStuService"%>
<%@page import="com.whaty.platform.entity.bean.PeManager"%>
<%@page import="com.whaty.platform.entity.bean.EnumConst"%>
<%@page import="com.whaty.platform.entity.service.basic.PeBzzstudentbacthService"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}
%>
<%
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	String[] stu_id = request.getParameterValues("stu_id");
	String type = fixnull(request.getParameter("type"));
	
	//  batch_id  enterprise_id  erji_id  pageInt
	String batch_id = fixnull(request.getParameter("batch_id"));
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String erji_id = fixnull(request.getParameter("erji_id"));
	String pageInt = fixnull(request.getParameter("pageInt"));
	
	List StuList = new ArrayList();
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
  	
  	GeneralService generalService = (GeneralService)wac.getBean("generalService");
  	PeBzzGoodStuService peBzzGoodStuService = (PeBzzGoodStuService)wac.getBean("peBzzGoodStuService");
  	PeBzzstudentbacthService peBzzstudentbacthService = (PeBzzstudentbacthService)wac.getBean("peBzzstudentbacthService");
	
  	DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzStudent.class);

	tempdc.add(Restrictions.in("id", stu_id));
  	StuList = generalService.getList(tempdc);
	
	if(stu_id == null){
	%>
	<script>
		alert("请至少选择一名学员信息！");
		window.history.back();
	</script>
	<%	
		return ;
	}
	
	List<PeBzzStudent> updateStu_list = new ArrayList<PeBzzStudent>();
	int old_count = 0;
	int update_count = 0;
	int current_num = 0;
	String messStr = "";
	
	PeBzzStudent stu_temp = new PeBzzStudent();
	PeBzzGoodStu goodStu_temp = new PeBzzGoodStu();
	
	if("yeas".equals(type)){//设为优秀
		old_count = 0;
		update_count = 0;
		current_num = 0;
		messStr = "";
		
		tempdc = DetachedCriteria.forClass(PeManager.class);
		tempdc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		PeManager peManager = (PeManager)generalService.getList(tempdc).get(0);
			
		tempdc = DetachedCriteria.forClass(EnumConst.class);
		tempdc.add(Restrictions.eq("namespace", "FlaggoodState"));
		tempdc.add(Restrictions.eq("code", "000"));
		EnumConst submit_status = (EnumConst)generalService.getList(tempdc).get(0);
		
		tempdc = DetachedCriteria.forClass(EnumConst.class);
		tempdc.add(Restrictions.eq("namespace", "FlagIsGoodStu"));
		tempdc.add(Restrictions.eq("code", "1"));
		EnumConst flagIsGoodStu = (EnumConst)generalService.getList(tempdc).get(0);
		
		Date currentDate = new Date();
		
		for(int i=0;i<StuList.size();i++){
		
			stu_temp = (PeBzzStudent)StuList.get(i);
			tempdc = DetachedCriteria.forClass(PeBzzGoodStu.class);
			tempdc.add(Restrictions.eq("peBzzStudent", stu_temp));
		    if( generalService.getList(tempdc).size() > 0 ){
		    	old_count ++;
		    }else{
		    	goodStu_temp = new PeBzzGoodStu(); 
				goodStu_temp.setPeBzzStudent(stu_temp);
				goodStu_temp.setWork_space(stu_temp.getPeEnterprise().getName());
				goodStu_temp.setPeManager(peManager);
				goodStu_temp.setEnumConstByFlaggoodState(submit_status);
				goodStu_temp.setRegard_num(0);
				goodStu_temp.setRecord_date(currentDate);
				
				peBzzGoodStuService.Save(goodStu_temp);
				
		    	updateStu_list.add(Integer.valueOf(current_num),stu_temp);
		    	current_num ++;
		    }
			
	    }
	    
	    if(updateStu_list.size() > 0){
	    	for(int i=0;i<updateStu_list.size();i++){
	    		peBzzstudentbacthService.updateFlaggoodStu(updateStu_list.get(i),flagIsGoodStu);
	    		update_count ++;
	    	}
		}else{
			update_count = 0;
		}
		
		messStr = "";
		if(update_count > 0 || old_count > 0){
			messStr = String.valueOf(update_count)+"条记录操作成功";
		}else{
			messStr = "操作失败";
		}
	
		%>
		<script>
			alert("<%=messStr %>");
			window.navigate("StudentExcellence_list.jsp?batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&erji_id=<%=erji_id%>&pageInt=<%=pageInt%>");
		</script>
		<%
	
	}else if("no".equals(type)){//取消优秀
		old_count = 0;
		update_count = 0;
		current_num = 0;
		messStr = "";
		
		tempdc = DetachedCriteria.forClass(EnumConst.class);
		tempdc.add(Restrictions.eq("namespace", "FlagIsGoodStu"));
		tempdc.add(Restrictions.eq("code", "0"));
		EnumConst flagIsGoodStu = (EnumConst)generalService.getList(tempdc).get(0);
		
		for(int i=0;i<StuList.size();i++){
			stu_temp = (PeBzzStudent)StuList.get(i);
			tempdc = DetachedCriteria.forClass(PeBzzGoodStu.class);
			tempdc.add(Restrictions.eq("peBzzStudent", stu_temp));
		    if( generalService.getList(tempdc).size() > 0 ){
		    	goodStu_temp = (PeBzzGoodStu)generalService.getList(tempdc).get(0);
		    	if("002".equals(goodStu_temp.getEnumConstByFlaggoodState().getCode())){
		    		old_count ++;
		    	}else{
		    		peBzzGoodStuService.del(goodStu_temp);
		    		updateStu_list.add(Integer.valueOf(current_num),stu_temp);
		    		current_num ++;
		    	}
		    }else{
		    	updateStu_list.add(Integer.valueOf(current_num),stu_temp);
		    	current_num ++;
		    }
		}
	
		if(updateStu_list.size() > 0){
			for(int i=0;i<updateStu_list.size();i++){
	    		peBzzstudentbacthService.updateFlaggoodStu(updateStu_list.get(i),flagIsGoodStu);
	    		update_count ++;
	    	}
		}else{
			update_count = 0;
		}
	
		messStr = "";
		if(update_count < 1){
			messStr = "选中学生的优秀学员审核已通过，故不可执行取消优秀操作";
		}else{
			if(old_count < 1){
				messStr = String.valueOf(update_count)+"条记录操作成功";
			}else{
				messStr = String.valueOf(update_count)+"条记录操作成功，"+String.valueOf(old_count)+"名学生的优秀学员审核已通过，故不可执行取消优秀操作";
			}
		}
	
		%>
		<script>
			alert("<%=messStr %>");
			window.navigate("StudentExcellence_list.jsp?batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&erji_id=<%=erji_id%>&pageInt=<%=pageInt%>");
		</script>
		<%
	
	}
	
	
	
	
	
	
	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tableType_1</title>
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
</body>
</html>