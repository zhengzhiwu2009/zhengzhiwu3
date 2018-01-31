<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_course_total_excel.xls"); %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.text.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.*"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%!
	//判断字符串为空的话，赋值为""  
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
	
	String getCurDate() 
	{
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
  		return dateformat1.format(new Date());
	}
	
	String getFormatDate(Date d) 
	{
		if(d == null) {
			return null;
		}
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日");
  		return dateformat1.format(d);
	}
	
	String getpPeEnt(PeBzzStudent stu)
	{
		if(stu.getPeEnterprise().getPeEnterprises() != null) 
			return stu.getPeEnterprise().getPeEnterprise().getName();
		else 
			return null;
	}
	
	private String getNPGSql(String courseId, String batchId, String tc) {
		String sql_con1 = "";
		String sql_con2 = "";
		String sql_con3 = "";

		if (courseId != null) {
			sql_con1 = "         and tcs.course_id = '" + courseId + "' \n";
			sql_con2 = "         and ass.fk_course_id='" + courseId + "' \n";
		}
		if (tc != null) {
			sql_con3 = "    and bs.fk_enterprise_id in " + tc + " \n ";
		}

		String sql = "select distinct * from ("
				+ "select bs.id as id, bs.true_name as trueName, bs.reg_no as regNo, bs.gender, bs.folk, bs.education, bs.birthday as birthdayDate, \n"
				+ "       bs.position,bs.title,bs.department, bs.phone,bs.mobile_phone as mobilePhone, bs.email,  \n"
				+ "       tc.name as cname, e2.name as ent2Name, e1.name as ent1Name \n"
				+ "from pe_bzz_student bs,  pe_bzz_tch_course tc, \n"
				+ "     pe_enterprise e2 left outer join pe_enterprise e1 on e2.fk_parent_id=e1.id, ( \n"
				+ "   select distinct tcs.student_id,tcs.course_id,oc.fk_course_id \n"
				+ "   from training_course_student tcs,pr_bzz_tch_opencourse oc \n"
				+ "   where tcs.course_id=oc.id and tcs.percent>50 \n"
				+ sql_con1
				+ "         and oc.fk_batch_id='"
				+ batchId
				+ "' \n"
				+ "   minus \n"
				+ "   select distinct ass.fk_student_id, ass.fk_course_id ,oc.fk_course_id \n"
				+ "   from pe_bzz_assess ass ,pr_bzz_tch_opencourse oc, pe_bzz_tch_course tc \n"
				+ "   where ass.fk_course_id=oc.id and oc.fk_course_id=tc.id \n"
				+ sql_con2
				+ "         and oc.fk_batch_id='"
				+ batchId
				+ "' \n"
				+ "      ) c \n"
				+ "where c.fk_course_id=tc.id and bs.fk_sso_user_id=c.student_id and bs.fk_enterprise_id=e2.id \n"
				+ sql_con3 + " order by tc.code)";

		return sql;
	}
%>

<%
	//List<PeBzzStudent> stuList = (List<PeBzzStudent>)request.getAttribute("stuResultList");	
	//Map userCourseMap = (Map)request.getAttribute("userCourseMap");
	
	
	String title = (String)request.getAttribute("excelName");
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String course_id = (String)request.getAttribute("cid");
	String batch_id = (String)request.getAttribute("bid");
	
	UserSession us = (UserSession) request
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		String tc = "";
		if (!us.getRoleId().equals("3")) {
			List<PeEnterprise> priList = us.getPriEnterprises();

			for (PeEnterprise e : priList) {

				tc += "'" + e.getId() + "',";
			}

			// System.out.println("----------tc: " + tc);
		}
		if (tc.length() == 0) {
			tc = "'noPriEnterprise'";
		}
		tc = "(" + tc.substring(0, tc.length() - 1) + ")";
	
	String sql = this.getNPGSql(course_id,batch_id,tc);
	
	//System.out.println(sql);
	rs = db.executeQuery(sql);
	
	response.setHeader("Content-Disposition", "attachment;filename=export_opencourse_stu.xls"); 
	
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="aaaad.files/filelist.xml">
<link rel=Edit-Time-Data href="aaaad.files/editdata.mso">
<link rel=OLE-Object-Data href="aaaad.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2010-05-19T07:13:31Z</o:LastSaved>
  <o:Version>11.9999</o:Version>
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
.font0
	{color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font7
	{color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
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
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style0;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl28
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;
	vertical-align:middle;}
.xl29
	{mso-style-parent:style0;
	text-align:right;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl30
	{mso-style-parent:style0;
	border:.5pt solid windowtext;}
.xl31
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:middle;
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
      <x:HorizontalResolution>200</x:HorizontalResolution>
      <x:VerticalResolution>200</x:VerticalResolution>
     </x:Print>
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>11</x:ActiveRow>
       <x:ActiveCol>5</x:ActiveCol>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=1655 style='border-collapse:
 collapse;table-layout:fixed;width:1244pt'>
 <col width=226 style='mso-width-source:userset;mso-width-alt:7232;width:170pt'>
 <col width=72 style='width:54pt'>
 <col width=106 style='mso-width-source:userset;mso-width-alt:3392;width:80pt'>
 <col width=65 style='mso-width-source:userset;mso-width-alt:2080;width:49pt'>
 <col width=84 style='mso-width-source:userset;mso-width-alt:2688;width:63pt'>
 <col width=90 style='mso-width-source:userset;mso-width-alt:2880;width:68pt'>
 <col width=129 style='mso-width-source:userset;mso-width-alt:4128;width:97pt'>
 <col width=122 style='mso-width-source:userset;mso-width-alt:3904;width:92pt'>
 <col width=113 style='mso-width-source:userset;mso-width-alt:3616;width:85pt'>
 <col width=177 style='mso-width-source:userset;mso-width-alt:5664;width:133pt'>
 <col width=224 style='mso-width-source:userset;mso-width-alt:7168;width:168pt'>
 <col width=247 style='mso-width-source:userset;mso-width-alt:7904;width:185pt'>
 <col width=102 style='mso-width-source:userset;mso-width-alt:3264;width:77pt'>
 <tr height=42 style='mso-height-source:userset;height:31.5pt'>
  <td colspan=12 class=xl28 width=1429 style='width:1074pt'><%=title%></td>
 </tr>
 <tr height=22 style='mso-height-source:userset;height:16.5pt'>
  <td colspan=12 class=xl29>导出时间：<font class="font7"><%=getCurDate()%></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl31 style='height:14.25pt'>课程名称</td>
  <td class=xl24 style='border-left:none'>学号</td>
  <td class=xl25>姓名</td>
  <td class=xl25>性别</td>
  <td class=xl25>民族</td>
  <td class=xl25>学历</td>
  <td class=xl25>出生日期</td>
  <td class=xl25>固定电话</td>
  <td class=xl25>移动电话</td>
  <td class=xl25>电子邮件</td>
  <td class=xl25>所在企业</td>
  <td class=xl25>所在集团</td>
 </tr>
 <%
 	Map<String,List> map = new HashMap<String, List>();
 	while(rs.next()) {
 		
		PeBzzStudent s = new PeBzzStudent();
		s.setRegNo(fixnull(rs.getString("regNo")));
		s.setTrueName(fixnull(rs.getString("trueName")));
		s.setGender(fixnull(rs.getString("gender")));
		s.setFolk(fixnull(rs.getString("folk")));
		s.setEducation(fixnull(rs.getString("education")));
		s.setBirthdayDate(rs.getDate("birthdayDate"));
		s.setPhone(fixnull(rs.getString("phone")));
		s.setMobilePhone(fixnull(rs.getString("mobilePhone")));
		s.setEmail(fixnull(rs.getString("email")));
		PeEnterprise e1 = new PeEnterprise();
		e1.setName(fixnull(rs.getString("ent1Name")));
		PeEnterprise e2 = new PeEnterprise();
		e2.setName(fixnull(rs.getString("ent2Name")));
		e2.setPeEnterprise(e1);
		s.setPeEnterprise(e2);
 		
 		String cname = fixnull(rs.getString("cname"));
 		
 		
 		if(map.get(cname) != null) {
 			
 			map.get(cname).add(s);
 		} else {
 			List list = new ArrayList();
 			list.add(s);
 			map.put(cname, list);
 		
 		}
 	}
 	db.close(rs);
 	
 	Set<String> keys = map.keySet();
 	
 	for(String key : keys) {
 		List<PeBzzStudent> list = map.get(key);
 		int i = 0;
 		for(PeBzzStudent stu : list) {
 			if(i==0) {
 		%>
  <tr height=19 style='height:14.25pt'>
  <td rowspan=<%=list.size()%> height=38 class=xl31 style='height:28.5pt;border-top:none'> <%=key%></td>
  <td class=xl26 style='border-left:none'><%=stu.getRegNo()%></td>
  <td class=xl27><%=stu.getTrueName()%></td>
  <td class=xl27><%=stu.getGender()%></td>
  <td class=xl27><%=stu.getFolk()%></td>
  <td class=xl27><%=stu.getEducation()%></td>
  <td class=xl27><%=fixnull(this.getFormatDate(stu.getBirthdayDate()))%></td>
  <td class=xl27><%=stu.getPhone()%></td>
  <td class=xl27><%=stu.getMobilePhone()%></td>
  <td class=xl27><%=stu.getEmail()%></td>
  <td class=xl27><%=stu.getPeEnterprise().getName()%></td>
  <td class=xl27><%=stu.getPeEnterprise().getPeEnterprise().getName()%></td>
 </tr>
  		<%	} else {
  		%>
 <tr height=19 style='height:14.25pt'>
  <td class=xl26 style='border-left:none'><%=stu.getRegNo()%></td>
  <td class=xl27><%=stu.getTrueName()%></td>
  <td class=xl27><%=stu.getGender()%></td>
  <td class=xl27><%=stu.getFolk()%></td>
  <td class=xl27><%=stu.getEducation()%></td>
  <td class=xl27><%=fixnull(this.getFormatDate(stu.getBirthdayDate()))%></td>
  <td class=xl27><%=stu.getPhone()%></td>
  <td class=xl27><%=stu.getMobilePhone()%></td>
  <td class=xl27><%=stu.getEmail()%></td>
  <td class=xl27><%=stu.getPeEnterprise().getName()%></td>
  <td class=xl27><%=stu.getPeEnterprise().getPeEnterprise().getName()%></td>
 </tr>
  		<%
  		}
  		i++;
  	}
 }

 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=226 style='width:170pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=106 style='width:80pt'></td>
  <td width=65 style='width:49pt'></td>
  <td width=84 style='width:63pt'></td>
  <td width=90 style='width:68pt'></td>
  <td width=129 style='width:97pt'></td>
  <td width=122 style='width:92pt'></td>
  <td width=113 style='width:85pt'></td>
  <td width=177 style='width:133pt'></td>
  <td width=224 style='width:168pt'></td>
  <td width=247 style='width:185pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
