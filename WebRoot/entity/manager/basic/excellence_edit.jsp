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
<%@page import="com.whaty.platform.entity.service.imp.basic.PeBzzGoodMagServiceimp"%>
<%@page import="com.whaty.platform.entity.bean.PeEnterpriseManager"%>
<%@page import="com.whaty.platform.entity.bean.PeBzzGoodMag"%>
<%@page import="com.whaty.platform.entity.service.basic.PeBzzGoodMagService"%>
<%@page import="com.whaty.platform.sso.service.admin.PeEnterprisemanagerServiceImp"%>
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
	
	String[] mag_id = request.getParameterValues("mag_id");
	String type = fixnull(request.getParameter("type"));
	
	//  batch_id  enterprise_id  erji_id  pageInt
	String batch_id = fixnull(request.getParameter("batch_id"));
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String erji_id = fixnull(request.getParameter("erji_id"));
	String pageInt = fixnull(request.getParameter("pageInt"));
	
	List MagList = new ArrayList();
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
  	
  	GeneralService generalService = (GeneralService)wac.getBean("generalService");
  	PeBzzGoodMagService peBzzGoodMagService = (PeBzzGoodMagService)wac.getBean("peBzzGoodMagService");
  	
  	DetachedCriteria tempdc = DetachedCriteria.forClass(PeEnterpriseManager.class);

	tempdc.add(Restrictions.in("id", mag_id));
  	MagList = generalService.getList(tempdc);
	
	if(mag_id == null){
	%>
	<script>
		alert("请至少选择一名管理员信息！");
		window.history.back();
	</script>
	<%	
		return ;
	}
	
	List<PeEnterpriseManager> updateStu_list = new ArrayList<PeEnterpriseManager>();
	int old_count = 0;
	int update_count = 0;
	int current_num = 0;
	String messStr = "";
	
	PeEnterpriseManager mag_temp = new PeEnterpriseManager();
	PeBzzGoodMag goodMag_temp = new PeBzzGoodMag();
	
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
		tempdc.add(Restrictions.eq("namespace", "FlaggoodManag"));
		tempdc.add(Restrictions.eq("code", "001"));
		EnumConst flagIsGoodMag = (EnumConst)generalService.getList(tempdc).get(0);
		
		Date currentDate = new Date();
		
		for(int i=0;i<MagList.size();i++){
		
			mag_temp = (PeEnterpriseManager)MagList.get(i);
			tempdc = DetachedCriteria.forClass(PeBzzGoodMag.class);
			tempdc.add(Restrictions.eq("peEnterpriseManager", mag_temp));
		    if( generalService.getList(tempdc).size() > 0 ){
		    	goodMag_temp = (PeBzzGoodMag)generalService.getList(tempdc).get(0);
		    	if( "002".equals(goodMag_temp.getEnumConstByFlaggoodState().getCode())){
		    		old_count ++;
		    	}else{
		    		updateStu_list.add(Integer.valueOf(current_num),mag_temp);
		    		current_num ++;
		    	}
		    	
		    	
		    }else{
		    	goodMag_temp = new PeBzzGoodMag();
				goodMag_temp.setPeEnterpriseManager(mag_temp);
				goodMag_temp.setWork_space(mag_temp.getPeEnterprise().getName());
				goodMag_temp.setEnumConstByFlaggoodState(submit_status);
				goodMag_temp.setPeManager(peManager);
				goodMag_temp.setRegard_num(0);
				goodMag_temp.setRecord_date(currentDate);
								
				peBzzGoodMagService.Save(goodMag_temp);
				
		    	updateStu_list.add(Integer.valueOf(current_num),mag_temp);
		    	current_num ++;
		    }
			
	    }
	    
	    if(updateStu_list.size() > 0){
	    	for(int i=0;i<updateStu_list.size();i++){
	    		peBzzGoodMagService.updateFlaggoodMag(updateStu_list.get(i),flagIsGoodMag);
	    		update_count ++;
	    	}
		}else{
			update_count = 0;
		}
		
		messStr = "";
		if(update_count < 1){
			messStr = "选中学生的优秀学员审核已通过，故不可执行设为优秀操作";
		}else{
			if(old_count < 1){
				messStr = String.valueOf(update_count)+"条记录操作成功";
			}else{
				messStr = String.valueOf(update_count)+"条记录操作成功，"+String.valueOf(old_count)+"名学生的优秀学员审核已通过，故不可执行设为优秀操作";
			}
		}
	
		%>
		<script>
			alert("<%=messStr %>");
			window.navigate("excellence_list.jsp?batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&erji_id=<%=erji_id%>&pageInt=<%=pageInt%>");
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
		
		tempdc = DetachedCriteria.forClass(EnumConst.class);
		tempdc.add(Restrictions.eq("namespace", "FlaggoodManag"));
		tempdc.add(Restrictions.eq("code", "000"));
		EnumConst flagIsGoodMag = (EnumConst)generalService.getList(tempdc).get(0);
		
		for(int i=0;i<MagList.size();i++){
			mag_temp = (PeEnterpriseManager)MagList.get(i);
			tempdc = DetachedCriteria.forClass(PeBzzGoodMag.class);
			tempdc.add(Restrictions.eq("peEnterpriseManager", mag_temp));
		    if( generalService.getList(tempdc).size() > 0 ){
		    	goodMag_temp = (PeBzzGoodMag)generalService.getList(tempdc).get(0);
		    	if("002".equals(goodMag_temp.getEnumConstByFlaggoodState().getCode())){
		    		old_count ++;
		    	}else{
		    		generalService.delete(goodMag_temp);
		    		updateStu_list.add(Integer.valueOf(current_num),mag_temp);
		    		current_num ++;
		    	}
		    }else{
		    	updateStu_list.add(Integer.valueOf(current_num),mag_temp);
		    	current_num ++;
		    }
		}
	
		if(updateStu_list.size() > 0){
			for(int i=0;i<updateStu_list.size();i++){
	    		peBzzGoodMagService.updateFlaggoodMag(updateStu_list.get(i),flagIsGoodMag);
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
			window.navigate("excellence_list.jsp?batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&erji_id=<%=erji_id%>&pageInt=<%=pageInt%>");
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