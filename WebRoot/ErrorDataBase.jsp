<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.sql.*"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.DocumentHelper"%>
<%@page import="org.dom4j.Element"%>

<%
	String start = "0";
	String end = "0";
	if(request.getParameter("start")!=null&&!request.getParameter("start").equals("null")&&!request.getParameter("start").equals(""))
	{
		start = request.getParameter("start");
	}
	if(request.getParameter("end")!=null&&!request.getParameter("end").equals("null")&&!request.getParameter("end").equals(""))
	{
		end = request.getParameter("end");
	}
	out.print("开始："+start+"  结束："+end+"<br/>");
	dbpool db = new dbpool();
		
	String sql = "select a.*,b.test_result as test_result,b.test_date from ( \n"
		+ "  select distinct hy.id,thi.group_id,tpi.type \n"
		+ "  from test_homeworkpaper_history hy,test_homeworkpaper_info thi,test_paperquestion_info tpi \n"
		+ "  where hy.testpaper_id=thi.id and tpi.testpaper_id=thi.id and hy.errordata='0' and hy.id>="+start+" and hy.id<="+end+" \n"
		+ ") a,test_homeworkpaper_history b \n"
		+ "where a.id=b.id";
		
  MyResultSet rs1 = db.executeQuery(sql);
   
    long count = 0l;
 	while (rs1!=null&&rs1.next()) {
 	    String result = rs1.getString("test_result");
 		String hyid = rs1.getString("id");
 		String type = rs1.getString("type");
 		String group_id = rs1.getString("group_id");
 		String sqlup="";
 		try{
 		Document doc = DocumentHelper.parseText(result);
 		List itemlist = doc.selectNodes("/answers/item");
 		Iterator it = itemlist.iterator(); 
		if (it.hasNext()) {
			Element answer = (Element) it.next();
			Element typeEle = answer.element("type");
			String eType = typeEle.getTextTrim();
			if(!type.toUpperCase().equals(eType.toUpperCase())) {
				if(eType.toUpperCase().equals("PANDUAN"))
				{
					sqlup="update test_homeworkpaper_history set errordata='3',testpaper_id=(select id from test_homeworkpaper_info where group_id='"+group_id+"' and title like '%（判断）%') where id='"+hyid+"'";
				}else if(eType.toUpperCase().equals("WENDA"))
				{
					sqlup="update test_homeworkpaper_history set errordata='3',testpaper_id=(select id from test_homeworkpaper_info where group_id='"+group_id+"' and title like '%（问答）%') where id='"+hyid+"'";
				}
				else if(eType.toUpperCase().equals("DANXUAN"))
				{
					sqlup="update test_homeworkpaper_history set errordata='3',testpaper_id=(select id from test_homeworkpaper_info where group_id='"+group_id+"' and title like '%（选择）%') where id='"+hyid+"'";
				}
				else if(eType.toUpperCase().equals("DUOXUAN"))
				{
					sqlup="update test_homeworkpaper_history set errordata='3',testpaper_id=(select id from test_homeworkpaper_info where group_id='"+group_id+"' and title like '%多项选择%') where id='"+hyid+"'";
				}
				/*if(db.executeUpdate(sqlup)>0)
				{
					count++;
				}*/
				//out.print("错误数据id="+hyid+"<br/>");
				//count++;
 			}
 		}
 		}
 		catch(Exception e)
 		{
 			sqlup="update test_homeworkpaper_history set errordata='2' where id='"+hyid+"'";
 			db.executeUpdate(sqlup);
 			out.print("无法解析id="+hyid+"<br/>");
 			count++;
 		}
 	} 
 	db.close(rs1);
 %>
共<%=count %>人