<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=enterprise_address_excel.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}
	
	String fixShengShi(String sheng, String shi) {
		
		if(sheng == null || sheng.equals("null") || sheng.equals(""))
			sheng = "";
		if(sheng.equals("null省")) {
			sheng = "";
		}
		
		if(shi == null || shi.equals("null") || shi.equals(""))
			shi = "";
		if(shi.equals("null市")) {
			shi = "";
		} else if(shi.endsWith("市市")) {
			shi = shi.substring(0,shi.length()-1);
		}
		//北京，天津，上海，重庆
		if(sheng.equals("北京省")) {
			sheng = "";
			shi = "北京市";
		} else if(sheng.equals("天津省")) {
			sheng = "";
			shi = "天津市";
		} else if(sheng.equals("上海省")) {
			sheng = "";
			shi = "上海市";
		} else if(sheng.equals("重庆省")) {
			sheng = "";
			shi = "重庆市";
		}
		return sheng + " " + shi;
		
	}
	
	
%>
<%
        dbpool db = new dbpool();
        MyResultSet rs = null;
        MyResultSet rs_bat = null;

		String entName = request.getParameter("_s_peEnterprise_name");//上级企业名
		String name = request.getParameter("_s_name");
		String code = request.getParameter("_s_code");
		String exambatch_name = fixnull(request.getParameter("_s_peBzzExamBatch_name"));
		
		String sql_batch="";
		if(exambatch_name.equals("")||exambatch_name==null)//没有选择批次
		{
			sql_batch="select id from pe_bzz_exambatch t where t.selected='402880a91dadc115011dadfcf26b00aa'";

		}
		else
		{
			sql_batch="select id from pe_bzz_exambatch t where t.name ='"+exambatch_name+"'";
		}
		rs_bat = db.executeQuery(sql_batch);
		String exambatch_id="";
		if(rs_bat!=null && rs_bat.next())
		{
			exambatch_id=fixnull(rs_bat.getString("id"));
		}
		db.close(rs_bat);
		
      //System.out.println(entName+","+name+","+code);
		
		String sqlCon = "";
		if(entName != null && !entName.trim().equals("")) {
			sqlCon += " and pent.name='"+entName.trim()+"' ";
		}
		
		if(name != null && !name.trim().equals("")) {
			sqlCon += " and ent.name like '%" + name.trim() + "%' ";
		}
		
		if(code != null && !code.trim().equals("")) {
			sqlCon += " and ent.code like '%" + code.trim() + "%' ";
		}
		
		String sql=

			"--合sql\n" +
			"select rs10.ent_id as id,\n" + 
			"       rs10.ent_name as name,\n" + 
			"       rs10.receiver_sheng,\n" + 
			"       rs10.receiver_shi,\n" + 
			"       rs10.receiver_address,\n" + 
			"       rs10.receiver_zipcode,\n" + 
			"       rs10.receiver_username,\n" + 
			"       rs10.receiver_phone,\n" + 
			"       rs10.receiver_mobile,\n" + 
			"       rs10.xxnum,\n" + 
			"       rs10.zsnum,\n" + 
			"       rs10.mailnum,\n" + 
			"       rs10.bz,\n" + 
			"       rs10.exambatch_id\n" + 
			"  from (select rs6.*, nvl(rs4.mail_num, 0) as mailnum\n" + 
			"          from (select rs2.*, nvl(rs3.zsnum, 0) as zsnum\n" + 
			"                  from (select a.*,\n" + 
			"                               a1.exambatch_id,\n" + 
			"                               a1.exambatch_name,\n" + 
			"                               nvl(a1.xxnum, 0) as xxnum\n" + 
			"                          from (select ent.id as ent_id,\n" + 
			"                                       ent.code as code,\n" + 
			"                                       ent.name as ent_name,\n" + 
			"                                       ent.receiver_sheng,\n" + 
			"                                       ent.receiver_shi,\n" + 
			"                                       ent.receiver_address,\n" + 
			"                                       ent.receiver_zipcode,\n" + 
			"                                       ent.receiver_username,\n" + 
			"                                       ent.receiver_phone,\n" + 
			"                                       ent.receiver_mobile,\n" + 
			"                                       ent.bz\n" + 
			"                                  from Pe_Enterprise ent, Pe_Enterprise pent\n" + 
			"                                 where ent.fk_parent_id is not null\n" + 
			"                                   and ent.fk_parent_id = pent.id \n"+
			sqlCon+
			"                           ) a\n" + 
			"                          left outer join ( --学习人数\n" + 
			"                                          select student.fk_enterprise_id as ent_id,\n" + 
			"                                                  count(student.id) as xxnum,\n" + 
			"                                                  exambatch.id as exambatch_id,\n" + 
			"                                                  exambatch.name as exambatch_name\n" + 
			"                                            from pe_bzz_student   student,\n" + 
			"                                                  pe_bzz_batch     b5,\n" + 
			"                                                  pe_bzz_exambatch exambatch,\n" + 
			"                                                  sso_user         sso\n" + 
			"                                           where b5.id = exambatch.batch_id\n" + 
			"                                             and student.fk_batch_id = b5.id\n" + 
			"                                             and student.fk_sso_user_id =\n" + 
			"                                                 sso.id\n" + 
			"                                             and student.flag_rank_state =\n" + 
			"                                                 '402880f827f5b99b0127f5bdadc70006'\n" + 
			"                                             and sso.flag_isvalid = '2'\n" + 
			"                                           group by student.fk_enterprise_id,\n" + 
			"                                                     exambatch.id,\n" + 
			"                                                     exambatch.name) a1 on a.ent_id =\n" + 
			"                                                                           a1.ent_id) rs2\n" + 
			"                  left outer join ( --证书人数\n" + 
			"                                  select stu.fk_enterprise_id as stu_ent_id,\n" + 
			"                                          count(certificate.id) as zsnum,\n" + 
			"                                          exambatch.id as exambatch_id,\n" + 
			"                                          exambatch.name as exambatch_name\n" + 
			"                                    from pe_bzz_exambatch   exambatch,\n" + 
			"                                          pe_bzz_batch       b5,\n" + 
			"                                          pe_bzz_student     stu,\n" + 
			"                                          pe_bzz_certificate certificate\n" + 
			"                                   where certificate.student_id = stu.id\n" + 
			"                                     and exambatch.batch_id = b5.id\n" + 
			"                                     and b5.id = stu.fk_batch_id\n" + 
			"                                   group by stu.fk_enterprise_id,\n" + 
			"                                             exambatch.id,\n" + 
			"                                             exambatch.name) rs3 on rs2.ent_id =\n" + 
			"                                                                    rs3.stu_ent_id\n" + 
			"                                                                and rs2.exambatch_id =\n" + 
			"                                                                    rs3.exambatch_id) rs6\n" + 
			"          left outer join ( --邮寄人数\n" + 
			"                          select mail_info.fk_enterprise_id as mail_ent_id,\n" + 
			"                                  sum(mail_info.num) as mail_num,\n" + 
			"                                  exambatch.id as exambatch_id,\n" + 
			"                                  exambatch.name as exambatch_name\n" + 
			"                            from pe_bzz_certificate_mail_info mail_info,\n" + 
			"                                  pe_bzz_exambatch             exambatch\n" + 
			"                           where exambatch.id = mail_info.FK_EXAM_BATCH_ID\n" + 
			"                           group by mail_info.fk_enterprise_id,\n" + 
			"                                     exambatch.id,\n" + 
			"                                     exambatch.name) rs4 on rs6.ent_id =\n" + 
			"                                                            rs4.mail_ent_id\n" + 
			"                                                        and rs6.exambatch_id =\n" + 
			"                                                            rs4.exambatch_id) rs10\n" + 
			" where exambatch_id = '"+exambatch_id+"'\n" + 
			" order by rs10.code";


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
<link rel=File-List href="邮寄导出表格.files/filelist.xml">
<link rel=Edit-Time-Data href="邮寄导出表格.files/editdata.mso">
<link rel=OLE-Object-Data href="邮寄导出表格.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>微软用户</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-04-29T06:13:44Z</o:Created>
  <o:LastSaved>2010-05-04T01:54:39Z</o:LastSaved>
  <o:Company>Whaty</o:Company>
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
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl27
	{mso-style-parent:style0;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl28
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
      <x:HorizontalResolution>600</x:HorizontalResolution>
      <x:VerticalResolution>0</x:VerticalResolution>
     </x:Print>
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
  <x:WindowHeight>10695</x:WindowHeight>
  <x:WindowWidth>21435</x:WindowWidth>
  <x:WindowTopX>0</x:WindowTopX>
  <x:WindowTopY>105</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1295 style='border-collapse:
 collapse;table-layout:fixed;width:973pt'>
 <col width=264 style='mso-width-source:userset;mso-width-alt:8448;width:198pt'>
 <col width=199 style='mso-width-source:userset;mso-width-alt:6368;width:149pt'>
 <col width=86 style='mso-width-source:userset;mso-width-alt:2752;width:65pt'>
 <col width=89 style='mso-width-source:userset;mso-width-alt:2848;width:67pt'>
 <col width=118 style='mso-width-source:userset;mso-width-alt:3776;width:89pt'>
 <col width=137 style='mso-width-source:userset;mso-width-alt:4384;width:103pt'>
 <col width=72 span=4 style='width:54pt'>
 <col width=114 style='mso-width-source:userset;mso-width-alt:3648;width:86pt'>
 <tr height=38 style='mso-height-source:userset;height:28.5pt'>
  <td colspan=10 height=38 class=xl28 width=1295 style='height:28.5pt;
  width:973pt'>二级企业邮寄信息表</td>
 </tr>
 <tr height=35 style='mso-height-source:userset;height:26.25pt'>
  <td height=35 class=xl25 style='height:26.25pt;border-top:none'>企业名称</td>
  <td class=xl25 style='border-top:none;border-left:none'>企业地址</td>
  <td class=xl25 style='border-top:none;border-left:none'>邮编</td>
  <td class=xl25 style='border-top:none;border-left:none'>接收人姓名</td>
  <td class=xl26 width=118 style='border-top:none;border-left:none;width:89pt'>接收人手机号</td>
  <td class=xl25 style='border-top:none;border-left:none'>接收人固定电话</td>
  <td class=xl25 style='border-top:none;border-left:none'>学习人数</td>
  <td class=xl25 style='border-top:none;border-left:none'>证书人数</td>
  <td class=xl25 style='border-top:none;border-left:none'>邮寄人数</td>
  <td class=xl25 style='border-top:none;border-left:none'>备注</td>
 </tr>
 <%
 	while(rs.next()) {
 %>
 <tr height=51 style='mso-height-source:userset;height:38.25pt'>
  <td height=51 class=xl27 width=264 style='height:38.25pt;border-top:none;
  width:198pt'><%=fixnull(rs.getString("name"))%></td>
  <td class=xl27 width=199 style='border-top:none;border-left:none;width:149pt'><%=fixShengShi(rs.getString("receiver_sheng") + "省",rs.getString("receiver_shi") + "市")%> <%=fixnull(rs.getString("receiver_address"))%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("receiver_zipcode"))%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("receiver_username"))%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("receiver_mobile"))%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("receiver_phone"))%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("xxnum"))%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("zsnum"))%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("mailnum"))%></td>
  <td class=xl27 width=114 style='border-top:none;border-left:none;width:86pt'><%=fixnull(rs.getString("bz"))%></td>
 </tr>
 <%	}
 	db.close(rs);
 	%>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=264 style='width:198pt'></td>
  <td width=199 style='width:149pt'></td>
  <td width=86 style='width:65pt'></td>
  <td width=89 style='width:67pt'></td>
  <td width=118 style='width:89pt'></td>
  <td width=137 style='width:103pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=114 style='width:86pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

