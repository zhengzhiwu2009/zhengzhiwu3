<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_study_excel.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.text.SimpleDateFormat"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import = "com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@page import = "com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.standard.scorm.operation.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.test.history.HomeworkPaperHistory"%>
<%@page import="com.whaty.platform.interaction.*"%>
<%@page import="com.whaty.platform.test.TestManage"%>
<%@page import="com.whaty.platform.test.question.PaperQuestion"%>
<%@page import="com.whaty.platform.test.question.TestQuestionType"%>
<%@page import="com.whaty.platform.util.XMLParserUtil"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.whaty.platform.database.oracle.standard.interaction.OracleInteractionUserPriv"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.DocumentHelper"%>
<%@page import="org.dom4j.Element,java.text.*"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}
	
	String getPercent(Double num) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(num) + "%";
	} 
	
	String getFormatNum(Double num) {
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(num);
	}
	
	String getCurDate() 
	{
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
  		return dateformat1.format(new Date());
	}
%>
<%
	
	String sql = "";
	dbpool db = new dbpool();
	MyResultSet rs =null;
	MyResultSet rs1 =null;
 %>
 <%
	String batch_id = fixnull(request.getParameter("batch_id"));
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String erji_id = fixnull(request.getParameter("erji_id"));
	String stuRegNo = fixnull(request.getParameter("stuRegNo"));
	
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList = us.getPriEnterprises();
	String sql_ent = "";
	String sql_entbk = "";
	for (int i = 0; i < entList.size(); i++) {
		PeEnterprise e = (PeEnterprise) entList.get(i);
		sql_ent += "'" + e.getId() + "',";
	}
	sql_entbk = sql_ent;
	if (!sql_ent.equals("")) {
		sql_ent = "(" + sql_ent.substring(0, sql_ent.length() - 1)+")";
	}
//	System.out.println("priEnt: " + sql_ent);


	String priTag = "2";  //0:总站管理员；1：一级管理员;2：二级管理员
	//判断是一级企业管理员，还是二级企业管理员
	
	if (us.getRoleId().equals("3")) { //表示总站管理员
	
		priTag = "0";
	} else if(us.getRoleId().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006")) {
		priTag = "1";
	} else {
		priTag = "2";
	}
	//System.out.println("priEnt: " + sql_ent);	
		
 	String sql_en = "";	
	String sql_con = "";
	String sql_con1 = "";
	
	if(!sql_ent.equals("") && !us.getRoleId().equals("3"))
	{
		sql_con+=" and b.id in "+sql_ent;
		//sql_con1=" and a.fk_enterprise_id in "+sql_ent;
	}
	
	if(!enterprise_id.equals(""))
	{
		if(erji_id.equals("")||erji_id.equals("#"))
		{
			sql_en = " and (b.id = '" + enterprise_id + "' or b.fk_parent_id ='"+enterprise_id+"')";
		}
		else
			sql_en =" and b.id = '"+erji_id+"'";
	}
	if(!batch_id.equals(""))
	{
		sql_en +="and a.FK_BATCH_ID= '"+batch_id+"'";
	}
	if(stuRegNo != null && !stuRegNo.equals("")) {
		sql_en += " and a.reg_no like '%" + stuRegNo.trim() + "%'";
	}
	
	//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
	String sql_training="training_course_student tcs,";
	if(batch_id!=null && batch_id.equals("ff8080812824ae6f012824b0a89e0008"))
	{
		sql_training="("+
			"select course_id,percent,learn_status,student_id from training_course_student s,pe_bzz_student bs where (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821') \n" + 
			" and bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' "+
			"union\n" + 
			"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
			"select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812910e7e601291150ddc70419' )a,\n" + 
			"(select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812bf5c39a012bf6a1bab80820')b\n" + 
			"where a.student_id=b.student_id\n" + 
			"union\n" + 
			"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
			"select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812910e7e601291150ddc70413' )a,\n" + 
			"(select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812bf5c39a012bf6a1baba0821')b\n" + 
			"where a.student_id=b.student_id"+
			") tcs,";
			}
		//	System.out.println("sql_training:"+sql_training);
								
	sql = "select a.id, a.true_name as name, a.reg_no as reg_no, a.gender, a.age, to_char(a.birthday,'yyyy-mm-dd') as birth,a.title,a.fk_sso_user_id as sso_id, \n"
			+ " b.name as ent_name,c.name as b_name ,scc.jctime,scc.jcpercent,scc.tctime,scc.tcpercent  \n"
			+ " from pe_bzz_student a, pe_enterprise b, pe_bzz_batch c,sso_user su ,  \n"
			+ "      (select a.id,a.jctime,a.jcpercent,b.tctime,b.tcpercent from  \n"
			+ "(select tcs.student_id as id,sum(tcs.percent/100 * btc.time) as jctime, sum(tcs.percent/100 * btc.time)/sum(btc.time)*100 as jcpercent from "+sql_training+" pr_bzz_tch_opencourse bto,pe_bzz_tch_course btc \n" 
			+ "       where tcs.course_id=bto.id and bto.fk_course_id=btc.id and bto.flag_course_type='402880f32200c249012200c780c40001' \n"
			+ "       group by tcs.student_id ) a, \n"
			+ "(select tcs.student_id as id,sum(tcs.percent/100 * btc.time) as tctime, sum(tcs.percent/100 * btc.time)/sum(btc.time)*100 as tcpercent from "+sql_training+" pr_bzz_tch_opencourse bto,pe_bzz_tch_course btc \n" 
			+ "       where tcs.course_id=bto.id and bto.fk_course_id=btc.id and bto.flag_course_type='402880f32200c249012200c7f8b30002' \n"
			+ "       group by tcs.student_id) b \n"
			+ "where a.id = b.id) scc  \n"
			+ " where c.id(+) = a.fk_batch_id and su.id=scc.id \n" 
			+ "   and b.id(+) = a.fk_enterprise_id  and su.id=a.fk_sso_user_id and su.flag_isvalid='2' and a.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n" 
			+ sql_con + " "
			+ sql_en + " "
			+ sql_con1
			+ "  order by reg_no";
		
   // System.out.println(sql);
   //out.println(sql);
 %>
 


<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="aaaa.files/filelist.xml">
<link rel=Edit-Time-Data href="aaaa.files/editdata.mso">
<link rel=OLE-Object-Data href="aaaa.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>China User</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-07-12T09:05:01Z</o:Created>
  <o:LastSaved>2010-07-13T09:55:25Z</o:LastSaved>
  <o:Company>11</o:Company>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
tr
	{mso-height-source:auto;
	mso-ruby-visibility:none;}
col
	{mso-width-source:auto;
	mso-ruby-visibility:none;}
br
	{mso-data-placement:same-cell;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:常规;
	mso-style-id:0;}
td
	{mso-style-parent:style0;
	padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl26
	{mso-style-parent:style0;
	text-align:right;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl27
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;}
.xl28
	{mso-style-parent:style0;
	border:.5pt solid windowtext;}
.xl29
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl30
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl31
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	border:.5pt solid windowtext;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;
	display:none;}
-->
</style>
<!--[if gte mso 9]><xml>
 <x:ExcelWorkbook>
  <x:ExcelWorksheets>
   <x:ExcelWorksheet>
    <x:Name>Sheet1</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:Print>
      <x:ValidPrinterInfo/>
      <x:PaperSizeIndex>9</x:PaperSizeIndex>
      <x:HorizontalResolution>300</x:HorizontalResolution>
      <x:VerticalResolution>300</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:RangeSelection>$A$1:$I$1</x:RangeSelection>
      </x:Pane>
     </x:Panes>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet2</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>11145</x:WindowHeight>
  <x:WindowWidth>21435</x:WindowWidth>
  <x:WindowTopX>0</x:WindowTopX>
  <x:WindowTopY>75</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1189 style='border-collapse:
 collapse;table-layout:fixed;width:892pt'>
 <col width=72 style='width:54pt'>
 <col width=110 style='mso-width-source:userset;mso-width-alt:3520;width:83pt'>
 <col width=111 span=2 style='mso-width-source:userset;mso-width-alt:3552;
 width:83pt'>
 <col width=305 style='mso-width-source:userset;mso-width-alt:9760;width:229pt'>
 <col width=111 style='mso-width-source:userset;mso-width-alt:3552;width:83pt'>
 <col width=129 style='mso-width-source:userset;mso-width-alt:4128;width:97pt'>
 <col width=111 style='mso-width-source:userset;mso-width-alt:3552;width:83pt'>
 <col width=129 style='mso-width-source:userset;mso-width-alt:4128;width:97pt'>
 <tr height=30 style='height:22.5pt'>
  <td colspan=9 height=30 class=xl27 width=1189 style='height:22.5pt;
  width:892pt'>学习记录跟踪报表</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=9 height=19 class=xl26 style='height:14.25pt'>导出时间：<%=getCurDate()%></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl29 style='height:14.25pt'>编号</td>
  <td class=xl30>姓名</td>
  <td class=xl30>学号</td>
  <td class=xl30>性别</td>
  <td class=xl30>所在企业</td>
  <td class=xl30>基础课学时数</td>
  <td class=xl30>基础课学习进度</td>
  <td class=xl30>提升课学时数</td>
  <td class=xl30>提升课学习进度</td>
 </tr>
 <%
 	rs = db.executeQuery(sql);
 	long count = 1L;
 	while(rs.next()) {
 		String name = fixnull(rs.getString("name"));
 		String reg_no = fixnull(rs.getString("reg_no"));
		String gender = fixnull(rs.getString("gender"));
		String age = fixnull(rs.getString("age"));
		String birth = fixnull(rs.getString("birth"));
		String title = fixnull(rs.getString("title"));
		String ent_name = fixnull(rs.getString("ent_name"));
		String b_name = fixnull(rs.getString("b_name"));
		String sso_id = fixnull(rs.getString("sso_id"));
		Double jcTime = rs.getDouble("jctime");
		Double jcPercent = rs.getDouble("jcpercent");
		Double tcTime = rs.getDouble("tctime");
		Double tcPercent = rs.getDouble("tcpercent");
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'><%=count%></td>
  <td class=xl25><%=name%></td>
  <td class=xl25><%=reg_no%></td>
  <td class=xl25><%=gender%></td>
  <td class=xl25><%=ent_name%></td>
  <td class=xl25 x:num><%=this.getFormatNum(jcTime)%></td>
  <td class=xl25><%=this.getPercent(jcPercent)%></td>
  <td class=xl25 x:num><%=this.getFormatNum(tcTime)%></td>
  <td class=xl25><%=this.getPercent(tcPercent)%></td>
 </tr>
   <%
  		count++;
  	}
  	db.close(rs);
  %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=110 style='width:83pt'></td>
  <td width=111 style='width:83pt'></td>
  <td width=111 style='width:83pt'></td>
  <td width=305 style='width:229pt'></td>
  <td width=111 style='width:83pt'></td>
  <td width=129 style='width:97pt'></td>
  <td width=111 style='width:83pt'></td>
  <td width=129 style='width:97pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>