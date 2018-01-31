<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中国证券业协会</title>
</head>
<body>
<%
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	int count =	interactionManage.addTeachClass(request);
	//同步讲师简介
	//String course_id = request.getParameter("teachclass_id");
	//String sql = "select teacher from pe_bzz_tch_course pbtc where pbtc.id = '" + course_id + "'";
	//dbpool db = new dbpool();
	//MyResultSet mrs = db.execuQuery(sql);
	//if (mrs.next()) {
		//String sql1 = "select sg_tti_id from sg_train_terchaer_info stti where stti.sg_tti_name = '" + mrs.getString("teacher") + "'";
		//MyResultSet mrs1 = db.execuQuery(sql1);
		//if(mrs1.next()) {
			//String sql2 = "update sg_train_terchaer_info set sg_tti_discription = '" + request.getParameter("body") + "' where sg_tti_id = '" + mrs1.getString("sg_tti_id") + "'";
			//db.executeUpdate(sql2);
		//}
		//db.close(mrs1);
	//}
	//db.close(mrs);
	if(count>0)
	{
 %>
 <script>
 	alert("添加成功！");
 	window.location="courseitemnote_list.jsp";
 </script>
 <%
	 }
	 else
	 {
  %>
  <script>
 	alert("添加失败！");
 	history.back();
 </script>
  <%
  	}
   %>
</body>
</html>