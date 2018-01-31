<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=studentExcellence_list_excel.xls"); %>
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
		if (str == null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}%>
<%
	String search = fixnull(request.getParameter("search"));
	String search1 = fixnull(request.getParameter("search1"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String c_search = fixnull(request.getParameter("c_search"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
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
	
	
		String sql_en="";
		//if(!search.equals(""))
			//sql_con=" and b.id='"+search+"'";
	
		if(!enterprise_id.equals(""))
		{   
			sql_en="and e.fk_parent_id = '"+enterprise_id+"'";
			if(erji_id.equals(""))
			{
						
			}
			else
			{
				sql_en =" and e.id = '"+erji_id+"'";
			}
		}
		String enterprise="";
		String tmp_sql="select name from pe_enterprise p where p.fk_parent_id is null and p.id='"+enterprise_id+"'";
		rs=db.executeQuery(tmp_sql);
		if(rs!=null && rs.next())
		{
			enterprise=fixnull(rs.getString("name"));
		}
		db.close(rs);
		
		String batch_name="";
		String bat_sql="select t.name from pe_bzz_batch t where  t.id='"+batch_id+"'";
		rs=db.executeQuery(bat_sql);
		if(rs!=null && rs.next())
		{
			batch_name=fixnull(rs.getString("name"));
		}
		db.close(rs);
		//System.out.println("sql_en:"+sql_en);
		String sql_t = "";
if((!enterprise_id.equals(""))||(!batch_id.equals(""))){			
	 if(us.getRoleId().equals("3"))
	   {
		   sql_t =  
	    	   "--总人数\n" +
	    	   "select s.reg_no        as reg_no,\n" + 
	    	   "       s.true_name     as name,\n" + 
	    	   "       e.name          as enterprise_name,\n" + 
	    	   "       nvl(e1.name,'无')          as enterprise1_name,\n" + 
	    	   "       s.gender        as gender,\n" + 
	    	   "       pbe.total_grade as grade,\n" + 
	    	   "       s.mobile_phone  as phone,nvl(forum.articlenum,0) as forumnum , \n" + 
	    	   "       k2.name as is_goodstu , k3.name as submit_status \n"+
	    	   "  from pe_bzz_student s, pe_enterprise e, pe_enterprise e1 , sso_user u, pe_bzz_examscore pbe,whatyforum.whatyforum_userinfo forum ,pe_bzz_goodstu k1 , enum_const k2 , enum_const k3  \n" + 
	    	   " where s.fk_enterprise_id = e.id\n" + 
	    	   "   and u.id = s.fk_sso_user_id\n" + 
	    	   "   and u.flag_isvalid = '2'\n" + 
	    	   "   and pbe.total_grade = '优秀'\n" + 
	    	   "   and s.is_goodstu = k2.id and s.id = k1.fk_studentid(+) and k1.submit_status = k3.id(+) \n"+
	    	   "   and pbe.student_id = s.id and e.fk_parent_id = e1.id(+)  \n" + 
	    	   "   and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
	    	   "   and s.fk_batch_id = '"+batch_id+"' and forum.username(+) = u.login_id \n" + 
	    	  sql_en+" order by e1.id , e.id , s.reg_no";
	   }
	   else
	   {
		   sql_t =  
	    	   "--总人数\n" +
	    	   "select s.reg_no        as reg_no,\n" + 
	    	   "       s.true_name     as name,\n" + 
	    	   "       e.name          as enterprise_name,\n" + 
	    	   "       nvl(e1.name,'无')          as enterprise1_name,\n" + 
	    	   "       s.gender        as gender,\n" + 
	    	   "       pbe.total_grade as grade,\n" + 
	    	   "       s.mobile_phone  as phone,nvl(forum.articlenum,0) as forumnum ,\n" + 
	    	   "       k2.name as is_goodstu , k3.name as submit_status \n"+
	    	   "  from pe_bzz_student s, pe_enterprise e, pe_enterprise e1 ,  sso_user u, pe_bzz_examscore pbe,whatyforum.whatyforum_userinfo forum , pe_bzz_goodstu k1 , enum_const k2 , enum_const k3 \n" + 
	    	   " where s.fk_enterprise_id = e.id\n" + 
	    	   "   and u.id = s.fk_sso_user_id\n" + 
	    	   "   and u.flag_isvalid = '2'\n" + 
	    	   "   and pbe.total_grade = '优秀' \n" + 
	    	   "   and s.is_goodstu = k2.id and s.id = k1.fk_studentid(+) and k1.submit_status = k3.id(+) \n"+
	    	   "   and pbe.student_id = s.id and e.fk_parent_id = e1.id(+) \n" + 
	    	   "   and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
	    	   "   and s.fk_batch_id = '"+batch_id+"' and forum.username(+) = u.login_id  and s.fk_enterprise_id in \n" + 
	    	 sql_ent+ sql_en+" order by e1.id , e.id ,  s.reg_no";
	   }
        //System.out.println(sql_t);
}
	// return;
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="student.files/filelist.xml">
<link rel=Edit-Time-Data href="student.files/editdata.mso">
<link rel=OLE-Object-Data href="student.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2011-06-21T10:53:44Z</o:LastSaved>
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
	font-size:14.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-size:11.0pt;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=997 style='border-collapse:
 collapse;table-layout:fixed;width:750pt'>
 <col width=54 style='mso-width-source:userset;mso-width-alt:1728;width:41pt'>
 <col width=133 style='mso-width-source:userset;mso-width-alt:4256;width:100pt'>
 <col width=97 style='mso-width-source:userset;mso-width-alt:3104;width:73pt'>
 <col width=61 style='mso-width-source:userset;mso-width-alt:1952;width:46pt'>
 <col width=155 style='mso-width-source:userset;mso-width-alt:4960;width:116pt'>
 <col width=154 style='mso-width-source:userset;mso-width-alt:4928;width:116pt'>
 <col width=93 style='mso-width-source:userset;mso-width-alt:2976;width:70pt'>
 <col width=116 style='mso-width-source:userset;mso-width-alt:3712;width:87pt'>
 <col width=134 style='mso-width-source:userset;mso-width-alt:4288;width:101pt'>
 <tr height=36 style='mso-height-source:userset;height:27.0pt'>
  <td colspan=10 height=36 class=xl24 width=997 style='height:27.0pt;width:750pt'><%=batch_name%><%=enterprise%>优秀学员列表（此信息仅供参考）</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl25 style='height:14.25pt;border-top:none'>序号</td>
  <td class=xl25 style='border-top:none;border-left:none'>系统编号</td>
  <td class=xl25 style='border-top:none;border-left:none'>姓名</td>
  <td class=xl25 style='border-top:none;border-left:none'>性别</td>
  <td class=xl25 style='border-top:none;border-left:none'>是否是优秀学员</td>
  <td class=xl25 style='border-top:none;border-left:none'>所在集团</td>
  <td class=xl25 style='border-top:none;border-left:none'>所在企业</td>
  <td class=xl25 style='border-top:none;border-left:none'>移动电话</td>
  <td class=xl25 style='border-top:none;border-left:none'>综合成绩</td>
  <td class=xl25 style='border-top:none;border-left:none'>论坛交流次数</td>
 </tr>
 <%
 
    rs = db.executeQuery(sql_t);
	int a = 0;
	while(rs!=null&&rs.next())
	{
		a++;
		String reg_no = fixnull(rs.getString("reg_no"));
		String stuname = fixnull(rs.getString("name"));
		String gender = fixnull(rs.getString("gender"));
		String enterprise_name = fixnull(rs.getString("enterprise_name"));
		String enterprise1_name = fixnull(rs.getString("enterprise1_name"));
		String phone = fixnull(rs.getString("phone"));
		String grade = fixnull(rs.getString("grade"));
		String forumnum = fixnull(rs.getString("forumnum"));
		
		String is_goodstu = fixnull(rs.getString("is_goodstu"));
		String submit_status = fixnull(rs.getString("submit_status"));
		if(!"".equals(submit_status)){
			is_goodstu = is_goodstu + "("+submit_status+")";
		}
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'><%=a%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=reg_no%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=stuname%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=gender%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=is_goodstu%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=enterprise1_name%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=enterprise_name%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=phone%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=grade%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=forumnum%></td>
 </tr>
 <%}
 db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=54 style='width:41pt'></td>
  <td width=133 style='width:100pt'></td>
  <td width=97 style='width:73pt'></td>
  <td width=61 style='width:46pt'></td>
  <td width=155 style='width:116pt'></td>
  <td width=154 style='width:116pt'></td>
  <td width=93 style='width:70pt'></td>
  <td width=116 style='width:87pt'></td>
  <td width=134 style='width:101pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

