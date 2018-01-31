<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=stat_xueli.xls"); %>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
	
	private String getPercent(String num, double count)
    {
        if(count == 0) {
           return "0.0";
        }
        
        if(num == null) {
        	num = "0";
        }
        
        double res = (Integer.parseInt(num) / count) * 100;
        
        DecimalFormat df = new DecimalFormat("##0.0");//使用系统默认的格式  

        return df.format(res);
     }
%>

<%
	 
	String batchName="";
	String chuzhong = "";
	String gaozhong = "";
	String zhigao = "";
	String zhongzhuan = "";
	String jixiao = "";
	String dazhuan = "";
	String daben = "";
	String shuoshi = "";
	String boshi  = "";
	
	double count = 0;
	
	UserSession us = (UserSession)session.getAttribute("user_session");
	   		
	String d=fixnull(request.getParameter("d"));
	   String batch_id="";
	   if(d.indexOf("|")>0)
	   		batch_id=fixnull(d.substring(0,d.indexOf("|")));
	   		
	List entList=us.getPriEnterprises();
	String sql_ent="";
	String sql_entbk="";
	for(int i=0;i<entList.size();i++)
	{
		PeEnterprise e=(PeEnterprise)entList.get(i);
		sql_ent+="'"+e.getId()+"',";
	}
	sql_entbk=sql_ent;
	if(!sql_ent.equals(""))
	{
		sql_ent="("+sql_ent.substring(0,sql_ent.length()-1)+")";
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	if(us.getRoleId().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006"))  //表示为一级主管和一级辅导员
	{
		String t_sql="select id from pe_enterprise where fk_parent_id in "+sql_ent;
		rs=db.executeQuery(t_sql);
		while(rs!=null && rs.next())
		{
			sql_entbk+="'"+fixnull(rs.getString("id"))+"',";
		}
		db.close(rs);
		sql_ent="("+sql_entbk.substring(0,sql_entbk.length()-1)+")";
	}
				
		String sql_con="";
		if(!sql_ent.equals(""))
		sql_con+=" and e.id in "+sql_ent;	
		
		if(!batch_id.equals(""))
			sql_con+=" and b.id='"+batch_id+"'";

		String sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+batch_id+"'";
		rs = db.executeQuery(sql);
		
		if(rs.next()) {
			batchName = rs.getString("bname");
		}
		db.close(rs);

       sql = "select a.chuzhong, b.gaozhong,c.zhigao,d.zhongzhuan,e.jixiao,f.dazhuan,g.daben,h.shuoshi,i.boshi "+
          "from (select count(s.education) as chuzhong "+
            "from pe_bzz_batch b, (select s.education,s.fk_batch_id,s.fk_enterprise_id from pe_bzz_student s,sso_user u where u.id=s.fk_sso_user_id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') s, pe_enterprise e  "+
           "where s.education = '初中'"+
             "and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) a, "+
         "(select count(s.education) as gaozhong "+
            "from pe_bzz_batch b, (select s.education,s.fk_batch_id,s.fk_enterprise_id from pe_bzz_student s,sso_user u where u.id=s.fk_sso_user_id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') s, pe_enterprise e "+
          "where s.education = '高中' "+
             "and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) b, "+
          "(select count(s.education) as zhigao "+
            "from pe_bzz_batch b, (select s.education,s.fk_batch_id,s.fk_enterprise_id from pe_bzz_student s,sso_user u where u.id=s.fk_sso_user_id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') s, pe_enterprise e "+
          "where s.education = '职高' "+
             "and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) c, "+
          "(select count(s.education) as zhongzhuan "+
            "from pe_bzz_batch b, (select s.education,s.fk_batch_id,s.fk_enterprise_id from pe_bzz_student s,sso_user u where u.id=s.fk_sso_user_id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') s, pe_enterprise e "+
          "where s.education = '中专' "+
            " and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) d,"+
          "(select count(s.education) as jixiao "+
            "from pe_bzz_batch b, (select s.education,s.fk_batch_id,s.fk_enterprise_id from pe_bzz_student s,sso_user u where u.id=s.fk_sso_user_id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') s, pe_enterprise e "+
         " where s.education = '技校' "+
             "and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" )e, "+
          "(select count(s.education) as dazhuan "+
            "from pe_bzz_batch b, (select s.education,s.fk_batch_id,s.fk_enterprise_id from pe_bzz_student s,sso_user u where u.id=s.fk_sso_user_id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') s, pe_enterprise e "+
         " where s.education = '大专' "+
            " and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) f, "+
          "(select count(s.education) as daben "+
           " from pe_bzz_batch b, (select s.education,s.fk_batch_id,s.fk_enterprise_id from pe_bzz_student s,sso_user u where u.id=s.fk_sso_user_id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') s, pe_enterprise e "+
        "  where s.education = '本科' "+
            " and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) g,"+
        "(select count(s.education) as shuoshi "+
           " from pe_bzz_batch b, (select s.education,s.fk_batch_id,s.fk_enterprise_id from pe_bzz_student s,sso_user u where u.id=s.fk_sso_user_id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') s, pe_enterprise e "+
        "  where s.education = '硕士' "+
            " and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) h, "+
        "(select count(s.education) as boshi "+
           " from pe_bzz_batch b, (select s.education,s.fk_batch_id,s.fk_enterprise_id from pe_bzz_student s,sso_user u where u.id=s.fk_sso_user_id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') s, pe_enterprise e "+
        "  where s.education = '博士' "+
            " and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) i ";
        //    System.out.println(sql);
        rs = db.executeQuery(sql);
		if(rs!=null&&rs.next())
		{
            chuzhong = fixnull(rs.getString("chuzhong"));
			gaozhong = fixnull(rs.getString("gaozhong"));
			zhigao = fixnull(rs.getString("zhigao"));
			zhongzhuan = fixnull(rs.getString("zhongzhuan"));
			jixiao = fixnull(rs.getString("jixiao"));
			dazhuan = fixnull(rs.getString("dazhuan"));
			daben = fixnull(rs.getString("daben"));
			shuoshi = fixnull(rs.getString("shuoshi"));
			boshi  = fixnull(rs.getString("boshi"));
		}
		db.close(rs);
		count=Integer.parseInt(chuzhong) +
			Integer.parseInt(gaozhong) +
			Integer.parseInt(zhigao) +
			Integer.parseInt(zhongzhuan) +
			Integer.parseInt(jixiao) +
			Integer.parseInt(dazhuan) +
			Integer.parseInt(daben) +
			Integer.parseInt(shuoshi) +
			Integer.parseInt(boshi);
			
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="11.files/filelist.xml">
<link rel=Edit-Time-Data href="11.files/editdata.mso">
<link rel=OLE-Object-Data href="11.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>China User</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-04-26T05:31:31Z</o:Created>
  <o:LastSaved>2010-04-26T08:22:01Z</o:LastSaved>
  <o:Company>Micosoft</o:Company>
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
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>11</x:ActiveRow>
       <x:ActiveCol>3</x:ActiveCol>
       <x:RangeSelection>$A$1:$D$12</x:RangeSelection>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=288 style='border-collapse:
 collapse;table-layout:fixed;width:216pt'>
 <col width=72 span=4 style='width:54pt'>
 <tr height=29 style='mso-height-source:userset;height:21.75pt'>
  <td colspan=3 height=29 class=xl25 width=288 style='height:21.75pt;
  width:216pt'>2009测试学期<span style='mso-spacerun:yes'>&nbsp;&nbsp;
  </span>学历分布情况</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl24 style='border-top:none;border-left:none'>人数</td>
  <td class=xl24 style='border-top:none;border-left:none'>比例</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>初中</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=chuzhong%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(chuzhong,count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>高中</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=gaozhong%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(gaozhong,count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>职高</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=zhigao%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(zhigao,count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>中专</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=zhongzhuan%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(zhongzhuan,count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>技校</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=jixiao%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(jixiao,count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>大专</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=dazhuan%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(dazhuan,count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>本科</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=daben%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(daben,count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>硕士</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=shuoshi%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(shuoshi,count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>博士</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=boshi%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(boshi,count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>总计</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=String.valueOf((int)count)%></td>
  <td class=xl24 style='border-top:none;border-left:none'>　</td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

