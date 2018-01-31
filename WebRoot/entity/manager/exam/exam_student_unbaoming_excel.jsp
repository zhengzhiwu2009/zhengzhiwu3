<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=exam_unbaoming_student_excel.xls"); %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}%>
<%
	String search = fixnull(request.getParameter("search"));
	String search1 = fixnull(request.getParameter("search1"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String c_search = fixnull(request.getParameter("c_search"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
	String studentName = fixnull(request.getParameter("studentName"));
	String enterprise_id = fixnull(request
			.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	//String name = fixnull(request.getParameter("name"));
	String name = fixnull(java.net.URLDecoder.decode(fixnull(request
			.getParameter("name")), "UTF-8"));
	String bSub=fixnull(request.getParameter("bSub"));
	String erji_id=fixnull(request.getParameter("erji_id"));
	
	UserSession us = (UserSession) session
			.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList = us.getPriEnterprises();
	String sql_ent = "";
	String sql_entbk = "";
	for (int i = 0; i < entList.size(); i++) {
		PeEnterprise e = (PeEnterprise) entList.get(i);
		sql_ent += "'" + e.getId() + "',";
	}
	sql_entbk = sql_ent;
	if (sql_ent!=null && !sql_ent.equals("")) {
		sql_ent = "(" + sql_ent.substring(0, sql_ent.length() - 1)
				+ ")";
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	
	String sql_con = "";
	String sql_en = "";
	String sql_con1 = "";
	String sql_s="";

	if(!enterprise_id.equals(""))
	{
		if(erji_id.equals(""))
		{
			sql_en = " and (e.id = '" + enterprise_id + "' or e.fk_parent_id ='"+enterprise_id+"')";
		}
		else
			sql_en =" and e.id = '"+erji_id+"'";
	}
	if (!sql_ent.equals("") && !us.getRoleId().equals("3")) {
		sql_con += "  and e.id in " + sql_ent;
		sql_con1 = "  and s.fk_enterprise_id in " + sql_ent;
	}
	if (!batch_id.equals("")) {
		sql_en += "and b.id = '" + batch_id + "'";
		sql_s=" and s.fk_batch_id='"+batch_id+"'";
	}
		
		

	String sql_t = 
	"select e.name         as enterprise_name,\n" +
			"       s.id           as student_id,\n" + 
			"       s.true_name    as student_name,\n" + 
			"       s.reg_no       as student_reg_no,\n" + 
			"       s.mobile_phone as student_phone,\n" + 
			"       s.email        as student_email,\n" + 
			"       b.name         as batch_name\n" + 
			"  from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e, sso_user su\n" + 
			" where b.id = s.fk_batch_id\n" + 
			"   and s.fk_enterprise_id = e.id\n" + 
			"   and s.fk_sso_user_id = su.id\n" + 
			"   and su.flag_isvalid = '2'\n" + 
			"   and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006' \n" + 
			"   "+sql_en+" "+sql_con+" "+sql_con1+" and s.reg_no like '%"+search+"%' and s.true_name like  '%"+studentName+"%' \n"+
			"minus\n" + 
			"select e.name         as enterprise_name,\n" + 
			"       s.id           as student_id,\n" + 
			"       s.true_name    as student_name,\n" + 
			"       s.reg_no       as student_reg_no,\n" + 
			"       s.mobile_phone as student_phone,\n" + 
			"       s.email        as student_email,\n" + 
			"       b.name         as batch_name\n" + 
			"  from pe_bzz_batch     b,\n" + 
			"       pe_bzz_student   s,\n" + 
			"       pe_enterprise    e,\n" + 
			"       sso_user         su,\n" + 
			"       pe_bzz_examscore es,\n" + 
			"       pe_bzz_examlate  el\n" + 
			" where b.id = s.fk_batch_id\n" + 
			"   and s.fk_enterprise_id = e.id\n" + 
			"   and s.fk_sso_user_id = su.id\n" + 
			"   and su.flag_isvalid = '2'\n" + 
			"   and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"   and (es.student_id = s.id or el.student_id = s.id) \n" +
			"   "+sql_en+" "+sql_con+" "+sql_con1+" and s.reg_no like '%"+search+"%' and s.true_name like  '%"+studentName+"%' ";
	// System.out.println(sql_t);
	// return;
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="student_exam_unbaoming.files/filelist.xml">
<link rel=Edit-Time-Data href="student_exam_unbaoming.files/editdata.mso">
<link rel=OLE-Object-Data href="student_exam_unbaoming.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2011-01-18T09:37:32Z</o:LastSaved>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
 <o:OfficeDocumentSettings>
  <o:RemovePersonalInformation/>
 </o:OfficeDocumentSettings>
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
	vertical-align:bottom;
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
	vertical-align:bottom;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:left;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-size:16.0pt;
	font-weight:700;
	mso-number-format:"\@";
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
      <x:HorizontalResolution>600</x:HorizontalResolution>
      <x:VerticalResolution>0</x:VerticalResolution>
     </x:Print>
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>2</x:ActiveRow>
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
     <x:CodeName>Sheet2</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:CodeName>Sheet3</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>4530</x:WindowHeight>
  <x:WindowWidth>8505</x:WindowWidth>
  <x:WindowTopX>480</x:WindowTopX>
  <x:WindowTopY>120</x:WindowTopY>
  <x:AcceptLabelsInFormulas/>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1131 style='border-collapse:
 collapse;table-layout:fixed;width:848pt'>
 <col width=39 style='mso-width-source:userset;mso-width-alt:1248;width:29pt'>
 <col width=124 style='mso-width-source:userset;mso-width-alt:3968;width:93pt'>
 <col width=96 style='mso-width-source:userset;mso-width-alt:3072;width:72pt'>
 <col width=208 style='mso-width-source:userset;mso-width-alt:6656;width:156pt'>
 <col width=260 style='mso-width-source:userset;mso-width-alt:8320;width:195pt'>
 <col width=175 style='mso-width-source:userset;mso-width-alt:5600;width:131pt'>
 <col width=229 style='mso-width-source:userset;mso-width-alt:7328;width:172pt'>
 <tr height=52 style='mso-height-source:userset;height:39.0pt'>
  <td colspan=7 height=52 class=xl26 width=1131 style='height:39.0pt;
  width:848pt'>考试未报名学员信息表</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>序号</td>
  <td class=xl24 style='border-top:none;border-left:none'>系统编号</td>
  <td class=xl24 style='border-top:none;border-left:none'>姓名</td>
  <td class=xl24 style='border-top:none;border-left:none'>所在学期</td>
  <td class=xl24 style='border-top:none;border-left:none'>所在企业</td>
  <td class=xl24 style='border-top:none;border-left:none'>移动电话</td>
  <td class=xl24 style='border-top:none;border-left:none'>电子邮件</td>
 </tr>
 <%
	 rs = db.executeQuery(sql_t);
	 int i = 0;
	 while(rs!=null&&rs.next())
	 {
		 i++;
		 String enterprise_name = fixnull(rs.getString("enterprise_name"));
		 String student_id = fixnull(rs.getString("student_id"));
		 String student_name = fixnull(rs.getString("student_name"));
		 String student_phone = fixnull(rs.getString("student_phone"));
		 String student_reg_no = fixnull(rs.getString("student_reg_no"));
		 String student_email = fixnull(rs.getString("student_email"));
		 String batch_name = fixnull(rs.getString("batch_name"));
 
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl25 style='height:14.25pt;border-top:none'><%=i %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=student_reg_no %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=student_name %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=batch_name %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=enterprise_name %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=student_phone %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=student_email %>　</td>
 </tr>
 <%}
	 db.close(rs);%>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=39 style='width:29pt'></td>
  <td width=124 style='width:93pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=208 style='width:156pt'></td>
  <td width=260 style='width:195pt'></td>
  <td width=175 style='width:131pt'></td>
  <td width=229 style='width:172pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
