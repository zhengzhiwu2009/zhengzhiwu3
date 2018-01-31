<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="jxl.*,java.text.*,java.io.*,java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.Exception.PlatformException"%>
<%@ page import="java.awt.List" %>
<%@page import = "com.whaty.platform.database.oracle.dbpool"%>
<%@page import = "com.whaty.platform.database.oracle.MyResultSet"%>
<html>
<body>

<%!
Vector recursion(String root, Vector<File> vecFile) {
	  File file = new File(root);
	  File[] subFile = file.listFiles();
	  for (int i = 0; i < subFile.length; i++) {
	   if (subFile[i].isDirectory()) {
	    recursion(subFile[i].getAbsolutePath(), vecFile);
	   } else {
	    vecFile.add(subFile[i]);
	   }
	  }
	  return vecFile;
	 }
%>

<%
try
{
	dbpool db = new dbpool();
	int count = 0;
	Vector<File> vecFile = new Vector<File>();
	vecFile = recursion("C:\\Documents and Settings\\Administrator\\桌面\\生源省", vecFile);
	for (File file : vecFile) {
		   //System.out.println(file.getName() + "  " + new FileInputStream(file).available());
		   Workbook w = Workbook.getWorkbook(file);
		   Sheet sheet = w.getSheet(0);
		   int columns = sheet.getColumns();
		   int rows = sheet.getRows();
		   for (int i = 1 ;i < rows ;i ++)
			{
			   String code = sheet.getCell(1,i).getContents().trim();
			   String sheng = sheet.getCell(3,i).getContents().trim();
			   String shi = sheet.getCell(4,i).getContents().trim();
			   String sql = "update pe_enterprise e set e.info_sheng='"+sheng+"',e.info_shi='"+shi+"' where e.code='"+code+"'";
			   //System.out.println(sql);
			   if(db.executeUpdate(sql)>0){
				   count++;
			   }else{
				   System.out.println(sql);
			   }
			}
	}
System.out.println("共更新成功"+count+"条");
}
	catch(Exception e){
%>
<script language=javascript>
		alert("上载的不是一个标准化的Excel文件，请先下载模版，然后按模版上传");
		history.back();
</script>
<%
	}
%>
</body>
</html>