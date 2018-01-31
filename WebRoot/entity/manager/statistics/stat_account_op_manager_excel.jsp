<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=stat_account_op_manager.xls"); %>

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
%>

<%
	
	dbpool db = new dbpool();
	MyResultSet rs = null;

    String sql = "select e.name,e.login_id, r.name as rname,e.phone,e.mobile_phone,e.email, e.bz \n"
			+ "   from pe_enterprise_manager e, sso_user s, pe_pri_role r,pe_enterprise pe \n"
			+ "  where s.id = e.fk_sso_user_id \n"
			+ "    and e.flag_isvalid = '2' \n"
			+ "    and r.id = s.fk_role_id and s.login_num is not null  \n"
			+ "    and e.id in (select b.fk_enterprisemanager_id from pe_bulletin b ) \n"
			+ "    and e.fk_enterprise_id = pe.id \n"
			+ "  order by s.fk_role_id"; 
		
        //System.out.println(sql);
        
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
  <o:LastSaved>2010-05-04T06:13:25Z</o:LastSaved>
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
	text-align:center;
	vertical-align:middle;}
.xl25
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;
	vertical-align:middle;}
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
       <x:ActiveRow>5</x:ActiveRow>
       <x:ActiveCol>11</x:ActiveCol>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=1172 style='border-collapse:
 collapse;table-layout:fixed;width:879pt'>
 <col width=72 style='width:54pt'>
 <col width=171 style='mso-width-source:userset;mso-width-alt:5472;width:128pt'>
 <col width=108 style='mso-width-source:userset;mso-width-alt:3456;width:81pt'>
 <col width=165 style='mso-width-source:userset;mso-width-alt:5280;width:124pt'>
 <col width=108 style='mso-width-source:userset;mso-width-alt:3456;width:81pt'>
 <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>
 <col width=171 style='mso-width-source:userset;mso-width-alt:5472;width:128pt'>
 <col width=268 style='mso-width-source:userset;mso-width-alt:8576;width:201pt'>
 <tr height=45 style='mso-height-source:userset;height:33.75pt'>
  <td colspan=8 height=45 class=xl25 width=1172 style='height:33.75pt;
  width:879pt'>有操作管理员名单</td>
 </tr>
 <tr height=21 style='mso-height-source:userset;height:15.75pt'>
  <td colspan=8 height=21 class=xl26 style='height:15.75pt'>导出时间：<font
  class="font7"><%=getCurDate()%></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl27 style='height:14.25pt'>序号</td>
  <td class=xl27 style='border-left:none'>管理员姓名</td>
  <td class=xl27 style='border-left:none'>登陆账号</td>
  <td class=xl27 style='border-left:none'>角色名称</td>
  <td class=xl27 style='border-left:none'>固定电话</td>
  <td class=xl27 style='border-left:none'>移动电话</td>
  <td class=xl27 style='border-left:none'>电子邮件</td>
  <td class=xl27 style='border-left:none'>备注</td>
 </tr>
 <%
 	int index = 1;
 	while(rs.next()) {
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl28 style='height:14.25pt;border-top:none'><%=index%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("name"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("login_id"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("rname"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("phone"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("mobile_phone"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("email"))%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(rs.getString("bz"))%></td>
 </tr>
 <%
 		index++;
 	}
 	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=171 style='width:128pt'></td>
  <td width=108 style='width:81pt'></td>
  <td width=165 style='width:124pt'></td>
  <td width=108 style='width:81pt'></td>
  <td width=109 style='width:82pt'></td>
  <td width=171 style='width:128pt'></td>
  <td width=268 style='width:201pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

