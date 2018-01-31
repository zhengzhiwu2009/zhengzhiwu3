<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import="com.sun.org.apache.bcel.internal.generic.NEW"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=exam_student.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}
	
	String getCurDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		return sdf.format(new Date());
	}	
%>
<%
		String batchName = request.getParameter("_s_peBzzExamBatch_name");//所在考试批次
		
		//String regNo = request.getParameter("_s_peBzzStudent_regNo");//系统编号
		
		//String name = request.getParameter("_s_peBzzStudent.trueName");//姓名
		
		String student_batch = request.getParameter("_s_peBzzStudent_peBzzBatch_name");//学期
		
		String enterprise = request.getParameter("_s_peBzzStudent_peEnterprise_name");//二级企业名称
		
		//String site = request.getParameter("_s_peBzzExamBatchSite_peBzzExamSite_name");//考点
		
		//String status = request.getParameter("_s_enumConstByFlagExamScoreStatus_name");//审核状态
		
		
		String sqlCon = "";
		String sqlCont = "";
		String sqlent = "";
		String sqlbat = "";
		String sqlstu = "";
		
		if(batchName != null && !batchName.trim().equals("")) {
			sqlCon += " and bat.name like '%" + batchName.trim() + "%' ";
			sqlCont = " and t.fk_batch_id in(select batch_id from pe_bzz_exambatch bat where 1=1 "+sqlCon+") ";
			sqlbat = " and stu.fk_batch_id in(select batch_id from pe_bzz_exambatch bat where 1=1 "+sqlCon+")";
		}
		if(student_batch != null && !student_batch.trim().equals(""))
		{
			sqlstu += " and pbb.name like '%"+student_batch+"%'";
			
		}
		/*if(enterprise != null && !enterprise.trim().equals(""))
		{
			sqlent = sqlent+" and ent2.name like like '%"+enterprise+"%'";
		}*/
		
		
        int total_xy = 0;
        int total_bm = 0;
        int total_bmtg = 0;
        int total_hk = 0;
        int total_hktg = 0;
        
		dbpool db = new dbpool();
		MyResultSet rs,rs_num = null;
		
		String sql = 
			"select distinct ent.id, ent.name\n" +
			"  from pe_enterprise ent\n" + 
			" where  ent.fk_parent_id is null";
		//System.out.println(sql);
		
 %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="exam_stat.files/filelist.xml">
<link rel=Edit-Time-Data href="exam_stat.files/editdata.mso">
<link rel=OLE-Object-Data href="exam_stat.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2010-11-15T06:54:17Z</o:LastSaved>
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
	color:red;
	font-size:16.0pt;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl26
	{mso-style-parent:style0;
	font-size:10.0pt;
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=866 style='border-collapse:
 collapse;table-layout:fixed;width:650pt'>
 <col width=225 style='mso-width-source:userset;mso-width-alt:7200;width:169pt'>
 <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>
 <col width=140 style='mso-width-source:userset;mso-width-alt:4480;width:105pt'>
 <col width=149 style='mso-width-source:userset;mso-width-alt:4768;width:112pt'>
 <col width=96 style='mso-width-source:userset;mso-width-alt:3072;width:72pt'>
 <col width=147 style='mso-width-source:userset;mso-width-alt:4704;width:110pt'>
 <tr height=62 style='mso-height-source:userset;height:46.5pt'>
 <%if(batchName!=null && !batchName.equals(null) && !batchName.equals("")){%>
  <td colspan=6 height=62 class=xl24 width=866 style='height:46.5pt;width:650pt'>考试统计表<font color='black' size='2'>《报名批次：<%=batchName%>》</font></td>
  <%}
  else {%>
  <td colspan=6 height=62 class=xl24 width=866 style='height:46.5pt;width:650pt'>考试统计表<font color='black' size='2'>《全部批次》</font></td>
  <%}%>
 </tr>
 <tr height=37 style='mso-height-source:userset;height:27.75pt'>
  <td height=37 class=xl25 width=225 style='height:27.75pt;border-top:none;
  width:169pt'>一级企业名称</td>
  <td class=xl25 width=109 style='border-top:none;border-left:none;width:82pt'>企业学员人数</td>
  <td class=xl25 width=140 style='border-top:none;border-left:none;width:105pt'>考试报名人数</td>
  <td class=xl25 width=149 style='border-top:none;border-left:none;width:112pt'>考试审核通过人数</td>
  <td class=xl25 width=96 style='border-top:none;border-left:none;width:72pt'>缓考人数</td>
  <td class=xl25 width=147 style='border-top:none;border-left:none;width:110pt'>缓考审核通过人数</td>
 </tr>
 <%
 	rs = db.executeQuery(sql);
 	while(rs!=null && rs.next())
 	{
 		String enterprise_name = fixnull(rs.getString("name"));
 		String enterprise_id = fixnull(rs.getString("id"));
 		String sql_num = 
		"select a.xynum, b.bmnum, c.bmtgnum, d.hknum, e.hktgnum\n" +
		"  from (select sum(xynum) as xynum\n" + 
		"          from (select count(t.id) as xynum\n" + 
		"                  from pe_bzz_student t,pe_bzz_batch pbb, \n" + 
		"                       pe_enterprise  ent1,\n" + 
		"                       pe_enterprise  ent2,sso_user sso \n" + 
		"                 where t.fk_enterprise_id = ent2.id and t.fk_batch_id = pbb.id and t.fk_enterprise_id = ent2.id\n" + 
		"                   and ent1.id = '"+enterprise_id+"'\n" +sqlstu+ 
		"                   and ent2.fk_parent_id = ent1.id "+sqlCont+" and t.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and t.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
		"                union\n" + 
		"                select count(t.id) as xynum\n" + 
		"                  from pe_bzz_student t,pe_bzz_batch pbb, pe_enterprise ent,sso_user sso \n" + 
		"                 where t.fk_enterprise_id = ent.id and t.fk_batch_id = pbb.id \n" + 
		"                   and ent.id = '"+enterprise_id+"'\n" + sqlstu+
		"                   and ent.fk_parent_id is null "+sqlCont+" and t.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and t.flag_rank_state='402880f827f5b99b0127f5bdadc70006')) a,\n" + 
		"       (select sum(bmnum) as bmnum\n" + 
		"          from (select count(sco.id) as bmnum\n" + 
		"                  from pe_bzz_examscore sco,\n" + 
		"                       pe_enterprise    ent1,\n" + 
		"                       pe_enterprise    ent2,\n" + 
		"                       pe_bzz_student   stu,pe_bzz_batch pbb,\n" + 
		"                       pe_bzz_exambatch bat,sso_user sso \n" + 
		"                 where sco.student_id = stu.id and stu.fk_batch_id = pbb.id and stu.fk_enterprise_id = ent2.id \n" + 
		"                   and stu.fk_enterprise_id = ent2.id and bat.batch_id  = pbb.id \n" + 
		"                   and sco.exambatch_id = bat.id\n" + 
		"                   and ent1.id = '"+enterprise_id+"'\n" + sqlstu+
		"                   and ent2.fk_parent_id = ent1.id and stu.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + sqlbat+
		"                union\n" + 
		"                select count(sco.id) as bmnum\n" + 
		"                  from pe_bzz_examscore sco,\n" + 
		"                       pe_enterprise    ent,\n" + 
		"                       pe_bzz_student   stu,pe_bzz_batch pbb,\n" + 
		"                       pe_bzz_exambatch bat,sso_user sso \n" + 
		"                 where sco.student_id = stu.id and stu.fk_batch_id = pbb.id \n" + 
		"                   and stu.fk_enterprise_id = ent.id and bat.batch_id  = pbb.id \n" + 
		"                   and sco.exambatch_id = bat.id\n" + sqlbat+sqlstu+
		"                   and ent.id = '"+enterprise_id+"'\n" + 
		"                   and ent.fk_parent_id is null and stu.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006')) b,\n" + 
		"       (select sum(bmtgnum) as bmtgnum\n" + 
		"          from (select count(sco.id) as bmtgnum\n" + 
		"                  from pe_bzz_examscore sco,\n" + 
		"                       pe_enterprise    ent1,\n" + 
		"                       pe_enterprise    ent2,\n" + 
		"                       pe_bzz_student   stu,pe_bzz_batch pbb,\n" + 
		"                       pe_bzz_exambatch bat,sso_user sso \n" + 
		"                 where sco.student_id = stu.id and stu.fk_batch_id = pbb.id and stu.fk_enterprise_id = ent2.id \n" + 
		"                   and stu.fk_enterprise_id = ent2.id and bat.batch_id  = pbb.id \n" + 
		"                   and sco.exambatch_id = bat.id\n" + sqlbat+sqlstu+
		"                   and ent1.id = '"+enterprise_id+"'\n" + 
		"                   and ent2.fk_parent_id = ent1.id\n" + 
		"                   and sco.status = '402880a9aaadc115061dadfcf26b0003' and stu.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
		"                union\n" + 
		"                select count(sco.id) as bmtgnum\n" + 
		"                  from pe_bzz_examscore sco,\n" + 
		"                       pe_enterprise    ent,\n" + 
		"                       pe_bzz_student   stu,pe_bzz_batch pbb,\n" + 
		"                       pe_bzz_exambatch bat,sso_user sso \n" + 
		"                 where sco.student_id = stu.id and stu.fk_batch_id = pbb.id \n" + 
		"                   and stu.fk_enterprise_id = ent.id and bat.batch_id  = pbb.id \n" + 
		"                   and sco.exambatch_id = bat.id\n" + sqlbat+sqlstu+
		"                   and ent.id = '"+enterprise_id+"'\n" + 
		"                   and sco.status = '402880a9aaadc115061dadfcf26b0003' and stu.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
		"                   and ent.fk_parent_id is null)) c,\n" + 
		"       (select sum(hknum) as hknum\n" + 
		"          from (select count(sco.id) as hknum\n" + 
		"                  from pe_bzz_examlate  sco,\n" + 
		"                       pe_enterprise    ent1,\n" + 
		"                       pe_enterprise    ent2,\n" + 
		"                       pe_bzz_student   stu,pe_bzz_batch pbb,\n" + 
		"                       pe_bzz_exambatch bat,sso_user sso \n" + 
		"                 where sco.student_id = stu.id and stu.fk_batch_id = pbb.id and stu.fk_enterprise_id = ent2.id \n" + 
		"                   and stu.fk_enterprise_id = ent2.id and bat.batch_id  = pbb.id \n" + 
		"                   and sco.exambatch_id = bat.id\n" + sqlbat+sqlstu+
		"                   and ent1.id = '"+enterprise_id+"'\n" + 
		"                   and ent2.fk_parent_id = ent1.id and stu.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
		"                union\n" + 
		"                select count(sco.id) as hknum\n" + 
		"                  from pe_bzz_examlate  sco,\n" + 
		"                       pe_enterprise    ent,\n" + 
		"                       pe_bzz_student   stu,pe_bzz_batch pbb,\n" + 
		"                       pe_bzz_exambatch bat,sso_user sso \n" + 
		"                 where sco.student_id = stu.id and stu.fk_batch_id = pbb.id \n" + 
		"                   and stu.fk_enterprise_id = ent.id and bat.batch_id  = pbb.id \n" + 
		"                   and sco.exambatch_id = bat.id\n" + sqlbat+sqlstu+
		"                   and ent.id = '"+enterprise_id+"'\n" + 
		"                   and ent.fk_parent_id is null and stu.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006')) d,\n" + 
		"       (select sum(hktgnum) as hktgnum\n" + 
		"          from (select count(sco.id) as hktgnum\n" + 
		"                  from pe_bzz_examlate  sco,\n" + 
		"                       pe_enterprise    ent1,\n" + 
		"                       pe_enterprise    ent2,\n" + 
		"                       pe_bzz_student   stu,pe_bzz_batch pbb,\n" + 
		"                       pe_bzz_exambatch bat,sso_user sso \n" + 
		"                 where sco.student_id = stu.id and stu.fk_batch_id = pbb.id and stu.fk_enterprise_id = ent2.id \n" + 
		"                   and stu.fk_enterprise_id = ent2.id and bat.batch_id  = pbb.id \n" + 
		"                   and sco.exambatch_id = bat.id\n" + sqlbat+sqlstu+
		"                   and ent1.id = '"+enterprise_id+"'\n" + 
		"                   and ent2.fk_parent_id = ent1.id\n" + 
		"                   and sco.status = 'ccb2880a91dadc115011dadfcf26b0002' and stu.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
		"                union\n" + 
		"                select count(sco.id) as hktgnum\n" + 
		"                  from pe_bzz_examlate  sco,\n" + 
		"                       pe_enterprise    ent,\n" + 
		"                       pe_bzz_student   stu,pe_bzz_batch pbb,\n" + 
		"                       pe_bzz_exambatch bat,sso_user sso \n" + 
		"                 where sco.student_id = stu.id and stu.fk_batch_id = pbb.id \n" + 
		"                   and stu.fk_enterprise_id = ent.id and bat.batch_id  = pbb.id \n" + 
		"                   and sco.exambatch_id = bat.id\n" + sqlbat+sqlstu+
		"                   and ent.id = '"+enterprise_id+"'\n" + 
		"                   and sco.status = 'ccb2880a91dadc115011dadfcf26b0002' and stu.fk_sso_user_id = sso.id and sso.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
		"                   and ent.fk_parent_id is null)) e";
		rs_num =db.executeQuery(sql_num); 
		while(rs_num!=null && rs_num.next())
		{
 		   String xynum = fixnull(rs_num.getString("xynum"));
 		   String bmnum = fixnull(rs_num.getString("bmnum"));
 		   String bmtgnum = fixnull(rs_num.getString("bmtgnum"));
 		   String hknum = fixnull(rs_num.getString("hknum"));
 		   String hktgnum = fixnull(rs_num.getString("hktgnum"));
 		   
 		   total_xy = total_xy + Integer.parseInt(xynum); 
 		   total_bm = total_bm + Integer.parseInt(bmnum);
 		   total_bmtg = total_bmtg + Integer.parseInt(bmtgnum); 
 		   total_hk = total_hk + Integer.parseInt(hknum); 
 		   total_hktg = total_hktg + Integer.parseInt(hktgnum);  
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'><%=enterprise_name%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=xynum%>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=bmnum %>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=bmtgnum%>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=hknum%>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=hktgnum%>　</td>
 </tr>
 
 <%
 }db.close(rs_num);
 }
 db.close(rs);
 %>
<tr height=37 style='mso-height-source:userset;height:27.75pt'>
  <td height=37 class=xl25 width=225 style='height:27.75pt;border-top:none;
  width:169pt'>总计</td>
  <td class=xl25 width=109 style='border-top:none;border-left:none;width:82pt'><%=total_xy %></td>
  <td class=xl25 width=140 style='border-top:none;border-left:none;width:105pt'><%=total_bm %></td>
  <td class=xl25 width=149 style='border-top:none;border-left:none;width:112pt'><%=total_bmtg %></td>
  <td class=xl25 width=96 style='border-top:none;border-left:none;width:72pt'><%=total_hk %></td>
  <td class=xl25 width=147 style='border-top:none;border-left:none;width:110pt'><%=total_hktg %></td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=225 style='width:169pt'><br/></td>
  <td width=109 style='width:82pt'><br/></td>
  <td width=140 style='width:105pt'><br/></td>
  <td width=149 style='width:112pt'><br/></td>
  <td width=96 style='width:72pt'><br/></td>
  <td width=147 style='width:110pt'><br/></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
 