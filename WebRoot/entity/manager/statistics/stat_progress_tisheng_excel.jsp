<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=stat_progress_tisheng.xls"); %>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
	
	private String getPercent(int num, double count)
    {
        if(count == 0) {
           return "0.0";
        }
        
        
        double res = (num / count) * 100;
        
        DecimalFormat df = new DecimalFormat("##0.0");//使用系统默认的格式  

        return df.format(res);
     }
     
    private int CheckNum(Double dou){
    int num = 0;
    if(dou>=0&&dou<10){
		num=1;
    }
    if(dou>=10&&dou<20){
	num=2;
}
    if(dou>=20&&dou<30){
	num=3;
}
    if(dou>=30&&dou<40){
	num=4;
}
    if(dou>=40&&dou<50){
	num=5;
}
    if(dou>=500&&dou<60){
	num=6;
}
    if(dou>=60&&dou<70){
	num=7;
}
    if(dou>=70&&dou<80){
	num=8;
}
    if(dou>=80&&dou<90){
	num=9;
}
    if(dou>=90&&dou<100){
	num=10;
}
    return num;
}
     
%>

<%
	String batchName = "";
	double count = 0;
	UserSession us = (UserSession)session.getAttribute("user_session");
	List entList=us.getPriEnterprises();
	
	String btsr = fixnull(request.getParameter("d"));
	String bath= "";
	if(btsr!=""){
		bath = btsr.substring(0,btsr.indexOf("|"));
	}
	
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
				String sql_con1="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!sql_ent.equals(""))
				{
					sql_con+=" and pe.id in "+sql_ent;
					sql_con1=" and ps.fk_enterprise_id in "+sql_ent;
				}
				String sql_bath = "";
				if(bath!=""){
				    sql_bath =" and ps.fk_batch_id ='"+bath+"'   ";
				}
					
					
				String sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+bath+"'";
		rs = db.executeQuery(sql);
		
		if(rs.next()) {
			batchName = rs.getString("bname");
		}
		db.close(rs);	
		
		//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
		String sql_training="("+
			"select course_id,percent,learn_status,student_id from training_course_student s where s.course_id not in\n" +
			"('ff8080812910e7e601291150ddc70419','ff8080812bf5c39a012bf6a1bab80820','ff8080812910e7e601291150ddc70413','ff8080812bf5c39a012bf6a1baba0821')\n" + 
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
			") ts,";
		//sql_training="training_course_student ts,";
		
	   sql= " select ps.id, sum(ce.time*(ts.percent/100)) as stime  from sso_user su ,"+sql_training +
	       "pe_bzz_student ps ,pr_bzz_tch_opencourse co ,pe_bzz_tch_course ce , pe_enterprise pe"+ 
	       " where ps.fk_sso_user_id = su.id and  su.id = ts.student_id  and  pe.id = ps.fk_enterprise_id"+ 
	       " and co.id = ts.course_id and co.fk_course_id = ce.id and  ps.fk_batch_id = co.fk_batch_id  and su.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' "
	       +sql_bath+"  "+sql_con+"    "+sql_con1+" and co.flag_course_type='402880f32200c249012200c7f8b30002'   group by ps.id";
	   
			   
 rs = db.executeQuery(sql);
	int total_10 = 0;
	int total_20 = 0;
	int total_30 = 0;
	int total_40 = 0;
	int total_50 = 0;
	int total_60 = 0;
	int total_70 = 0;
	int total_80 = 0;
	int total_90 = 0;
	int total_100 = 0;
		while(rs!=null&&rs.next())
		{
		    Double dou = rs.getDouble("stime");
			int num = this.CheckNum(dou);
			switch (this.CheckNum(dou)){
			case 1:
			    total_10  = total_10+1;
				break;
			case 2:
			    total_20  = total_20+1;
				break;
			case 3:
			    total_30  = total_30+1;
				break;
			case 4:
			    total_40  = total_40+1;
				break;
			case 5:
			    total_50  = total_50+1;
				break;
			case 6:
			    total_60  = total_60+1;
				break;
			case 7:
			    total_70  = total_70+1;
				break;
			case 8:
			    total_80  = total_80+1;
				break;
			case 9:
			    total_90  = total_90+1;
				break;
			case 10:
			    total_100  = total_100+1;
				break;
		}
		}
		db.close(rs);
		
		count = total_10 +
			total_20 +
			total_30 +
			total_40 +
			total_50 +
			total_60 +
			total_70 +
			total_80 +
			total_90 +
			total_100;

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
  <o:LastSaved>2010-04-26T11:09:44Z</o:LastSaved>
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
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl26
	{mso-style-parent:style0;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style0;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl28
	{mso-style-parent:style0;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl29
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
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>5</x:ActiveRow>
       <x:ActiveCol>9</x:ActiveCol>
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
  <td colspan=3 height=29 class=xl26 width=216 style='border-right:.5pt solid black;
  height:21.75pt;width:162pt'>test<span style='mso-spacerun:yes'>  
  </span>提升课程学习进度统计情况</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl24 style='border-top:none;border-left:none'>课时</td>
  <td class=xl24 style='border-top:none;border-left:none'>比例</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>0--10</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_10%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_10, count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>10--20</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_20%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_20, count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>20--30</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_30%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_30, count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>30--40</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_40%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_40, count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>40--50</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_50%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_50, count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>50--60</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_60%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_60, count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>60--70</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_70%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_70, count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>70--80</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_80%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_80, count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>80--90</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_90%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_90, count)%>&nbsp;%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>90--100</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=total_100%></td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=getPercent(total_100, count)%>&nbsp;%</td>
 </tr>
 
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>总计</td>
  <td class=xl24 style='border-top:none;border-left:none' x:num><%=(int)count%></td>
  <td class=xl24 style='border-top:none;border-left:none'>　</td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

