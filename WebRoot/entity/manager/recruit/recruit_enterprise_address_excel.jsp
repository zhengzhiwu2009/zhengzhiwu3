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
		String entName = request.getParameter("_s_peEnterprise_name");//上级企业名
		String name = request.getParameter("_s_name");
		String code = request.getParameter("_s_code");
		
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
		
		dbpool db = new dbpool();
		MyResultSet rs = null;
		
		String sql = " select rs10.ent_id as id, rs10.ent_name as name, rs10.receiver_sheng, rs10.receiver_shi, rs10.receiver_address, rs10.receiver_zipcode, rs10.receiver_username, rs10.receiver_phone, rs10.receiver_mobile, rs10.bmnum, rs10.zcnum, rs10.zknum, rs10.mailnum , rs10.bz from \n" 
				+ "   (select rs8.*, nvl(rs9.mail_num,0) as mailnum   \n"
				+ "    from   \n"
				+ "        (select rs6.*, nvl(rs7.zknum,0) as zknum \n"  
				+ "         from   \n"       
				+ "            (select rs2.*, nvl(rs3.zcnum,0) as zcnum \n"  
				+ "             from   \n"              
				+ "                 (select a.*, nvl(a1.bmnum,0) as bmnum \n"  
				+ "                  from   \n"
				+ "                  ( select ent.id as ent_id, ent.code as code,ent.name as ent_name, ent.receiver_sheng, ent.receiver_shi, ent.receiver_address, ent.receiver_zipcode,ent.receiver_username, ent.receiver_phone,ent.receiver_mobile,ent.bz \n"
				+ "                    from  Pe_Enterprise ent  ,Pe_Enterprise pent\n"
				+ "                    where ent.fk_parent_id is not null and ent.fk_parent_id=pent.id "
                + sqlCon +"\n" 
				+ "                   ) a   \n"
				+ "                   left outer join \n" 
				+ "                   (select recruit.fk_enterprise_id as ent_id, count(recruit.id) as bmnum \n" 
				+ "                    from pe_bzz_recruit recruit inner join pe_bzz_batch b5 on recruit.fk_batch_id = b5.id \n" 
				+ "                    where b5.recruit_selected='1' \n" 
				+ "                    group by recruit.fk_enterprise_id) a1 \n" 
				+ "                    on a.ent_id = a1.ent_id \n"
				+ "                 ) rs2 \n"  
				+ "                left outer join \n"  
				+ "                ( select stu.fk_enterprise_id as stu_ent_id, count(stu.reg_no) as zcnum \n"  
				+ "                  from pe_bzz_student stu inner join  pe_bzz_batch batch on stu.fk_batch_id = batch.id \n"  
				+ "                  where batch.recruit_selected='1' \n"  
				+ "                  group by stu.fk_enterprise_id) rs3 \n"  
				+ "                on rs2.ent_id=rs3.stu_ent_id \n"  
				+ "            ) rs6 \n"   
				+ "          left outer join \n"  
				+ "            (select zk_info.fk_enterprise_id as zk_ent_id, sum(zk_info.received_person_num) as zknum \n"  
				+ "             from pe_bzz_zk_info zk_info inner join pe_bzz_batch batch on zk_info.fk_batch_id = batch.id   \n"
				+ "             where batch.recruit_selected='1' \n"  
				+ "             group by zk_info.fk_enterprise_id) rs7 \n"  
				+ "          on rs6.ent_id=rs7.zk_ent_id) rs8 \n"   
				+ "       left outer join    \n"
				+ "         (select mail_info.fk_enterprise_id as mail_ent_id, sum(mail_info.num) as mail_num \n"  
				+ "          from pe_bzz_mail_info mail_info , pe_bzz_batch b2   \n"
				+ "          where b2.id=mail_info.FK_BATCH_ID and b2.recruit_selected='1' \n" 
				+ "          group by mail_info.fk_enterprise_id) rs9 \n"  
				+ "       on rs8.ent_id = rs9.mail_ent_id) rs10  order by rs10.code";
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
  <td colspan=11 height=38 class=xl28 width=1295 style='height:28.5pt;
  width:973pt'>二级企业邮寄信息表</td>
 </tr>
 <tr height=35 style='mso-height-source:userset;height:26.25pt'>
  <td height=35 class=xl25 style='height:26.25pt;border-top:none'>企业名称</td>
  <td class=xl25 style='border-top:none;border-left:none'>企业地址</td>
  <td class=xl25 style='border-top:none;border-left:none'>邮编</td>
  <td class=xl25 style='border-top:none;border-left:none'>接收人姓名</td>
  <td class=xl26 width=118 style='border-top:none;border-left:none;width:89pt'>接收人手机号</td>
  <td class=xl25 style='border-top:none;border-left:none'>接收人固定电话</td>
  <td class=xl25 style='border-top:none;border-left:none'>报名人数</td>
  <td class=xl25 style='border-top:none;border-left:none'>注册人数</td>
  <td class=xl25 style='border-top:none;border-left:none'>制卡人数</td>
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
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("bmnum"))%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("zcnum"))%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fixnull(rs.getString("zknum"))%></td>
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

