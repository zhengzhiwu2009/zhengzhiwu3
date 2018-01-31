<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=stat_account_nologin.xls"); %>

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
	
	String getDate(Date d) 
	{
		if(d == null) {
			return "";
		}
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日");
  		return dateformat1.format(d);
	}
%>

<%
	
	String batch_id=fixnull(request.getParameter("batch_id"));
	String sql_con = "";
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	String batchName = "";
	
	String sql = "";
	if(batch_id != null && !batch_id.equals("")) {
		sql_con = " and s.fk_batch_id='" + batch_id + "' \n";
		
		sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+batch_id+"'";
		rs = db.executeQuery(sql);
		
		if(rs.next()) {
			batchName = rs.getString("bname");
		}
		db.close(rs);
	}

    sql = "select s.true_name as name, s.gender,s.folk,s.education,s.birthday,s.phone,s.mobile_phone,s.email, \n"
		+ "       pe.name as ename, pe2.name as pname  ,u.login_num \n"
		+ "from pe_bzz_student s, sso_user u, pe_enterprise pe,pe_enterprise pe2 \n" 
		+ "where s.fk_enterprise_id = pe.id and pe.fk_parent_id=pe2.id \n"  
		+ sql_con
		+ " and s.fk_sso_user_id=u.id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
		+ " and (u.login_num is null or u.login_num=0 or u.last_login_ip is null) \n"
		+ "order by pe2.name,pe.name  ";
		
        System.out.println(sql);
        
    rs = db.executeQuery(sql);
			
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="新建%20Microsoft%20Excel%20工作表.files/filelist.xml">
<link rel=Edit-Time-Data href="新建%20Microsoft%20Excel%20工作表.files/editdata.mso">
<link rel=OLE-Object-Data href="新建%20Microsoft%20Excel%20工作表.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2010-05-04T07:08:07Z</o:LastSaved>
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
	text-align:center;}
.xl25
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;}
.xl26
	{mso-style-parent:style0;
	text-align:right;}
.xl27
	{mso-style-parent:style0;
	text-align:center;
	border:.5pt solid windowtext;}
.xl28
	{mso-style-parent:style0;
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
       <x:RangeSelection>$A$1:$K$1</x:RangeSelection>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=1429 style='border-collapse:
 collapse;table-layout:fixed;width:1074pt'>
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
  <td colspan=11 height=42 class=xl25 width=1429 style='height:31.5pt;
  width:1074pt'><%=batchName%> 未登录学员名单</td>
 </tr>
 <tr height=22 style='mso-height-source:userset;height:16.5pt'>
  <td colspan=11 height=22 class=xl26 style='height:16.5pt'>导出时间：<font
  class="font7"><%=getCurDate()%></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl27 style='height:14.25pt'>序号</td>
  <td class=xl27 style='border-left:none'>姓名</td>
  <td class=xl27 style='border-left:none'>性别</td>
  <td class=xl27 style='border-left:none'>民族</td>
  <td class=xl27 style='border-left:none'>学历</td>
  <td class=xl27 style='border-left:none'>出生日期</td>
  <td class=xl27 style='border-left:none'>固定电话</td>
  <td class=xl27 style='border-left:none'>移动电话</td>
  <td class=xl27 style='border-left:none'>电子邮件</td>
  <td class=xl27 style='border-left:none'>所在企业</td>
  <td class=xl27 style='border-left:none'>所在集团</td>
 </tr>
 <%
 	int index = 1;
 	while(rs.next()) {
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl28 style='height:14.25pt;border-top:none'><%=index%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("name"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("gender"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("folk"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("education"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(getDate(rs.getDate("birthday")))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("phone"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("mobile_phone"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("email"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("ename"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("pname"))%></td>
 </tr>
 <%
 		index++;
 	}
 	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'><br/></td>
  <td width=106 style='width:80pt'><br/></td>
  <td width=65 style='width:49pt'><br/></td>
  <td width=84 style='width:63pt'><br/></td>
  <td width=90 style='width:68pt'><br/></td>
  <td width=129 style='width:97pt'><br/></td>
  <td width=122 style='width:92pt'><br/></td>
  <td width=113 style='width:85pt'><br/></td>
  <td width=177 style='width:133pt'><br/></td>
  <td width=224 style='width:168pt'><br/></td>
  <td width=247 style='width:185pt'>=<br/></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

