<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%response.setHeader("Content-Disposition", "attachment;filename=stat_study_progress_score_excel.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.text.SimpleDateFormat"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import = "com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@page import = "com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.standard.scorm.operation.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.test.history.HomeworkPaperHistory"%>
<%@page import="com.whaty.platform.interaction.*"%>
<%@page import="com.whaty.platform.test.TestManage"%>
<%@page import="com.whaty.platform.test.question.PaperQuestion"%>
<%@page import="com.whaty.platform.test.question.TestQuestionType"%>
<%@page import="com.whaty.platform.util.XMLParserUtil"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.whaty.platform.database.oracle.standard.interaction.OracleInteractionUserPriv"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.DocumentHelper"%>
<%@page import="org.dom4j.Element,java.text.*"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
	
	private String percent(String one,String two){
		/*Double on = Double.valueOf(one);
		Double tw = Double.valueOf(two);
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumIntegerDigits(2);
		nf.setMaximumFractionDigits(2);
		Double double1 = on/tw;
		String percent =nf.format(double1);*/
		int a=Integer.parseInt(one);
		int b=Integer.parseInt(two);
		double p=(double)((a*10000)/b)/100;
		return new Double(p).toString()+"%";
	}
	
	private String getPercent(double count)
    {  
        DecimalFormat df = new DecimalFormat("##0.00");//使用系统默认的格式  

        return df.format(count);
     }
	
	String getCurDate() 
	{
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
  		return dateformat1.format(new Date());
	}
%>
<%
	String batch_id = fixnull(request.getParameter("batch_id"));
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String erji_id = fixnull(request.getParameter("erji_id"));
	String stuRegNo = fixnull(request.getParameter("stuRegNo"));
	
	
	TrainingFactory factory=TrainingFactory.getInstance();
	UserSession usersession = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	TrainingStudentPriv includePriv=new OracleTrainingStudentPriv();
	includePriv.setStudentId(usersession.getId());
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionUserPriv interactionUserPriv = new OracleInteractionUserPriv();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	
	TrainingStudentOperationManage stuManage=factory.creatTrainingUserOperationManage(includePriv);
	/*ScormStudentManage scormStudentManage=stuManage.getScormStudentManage();
	UserCourseData userCourseData=new UserCourseData();*/
	
	int scores = 0;    
	
	String sql = "";
	dbpool db = new dbpool();
	MyResultSet rs =null;
	MyResultSet rs1 =null;
 %>
 <%
	
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
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
	
        String sql_con="";
		String sql_con1="";
		String sql_en = "";	
		
		if(!sql_ent.equals("") && !us.getRoleId().equals("3"))
		{
			sql_con+=" and ent2.id in "+sql_ent;
			sql_con1=" and stu.fk_enterprise_id in "+sql_ent;
		}
		//System.out.println("sqlent:"+sql_ent);
		String titleEntName = "";
		
		if(!enterprise_id.equals(""))
		{
			if(erji_id.equals(""))
			{
				sql_en = " and (ent2.id = '" + enterprise_id + "' or ent2.fk_parent_id ='"+enterprise_id+"')";
			}
			else
				sql_en =" and ent2.id = '"+erji_id+"'";
			
			sql = "select ent2.name from pe_enterprise ent2 where 1=1" + sql_en;
			rs = db.executeQuery(sql);
			
			if(rs.next()) {
				titleEntName = rs.getString("name");
			}
			db.close(rs);
		}
		
		String titleBatchName = "";
		if(!batch_id.equals(""))
		{
			sql_en +="and ocrs.FK_BATCH_ID= '"+batch_id+"'";
			sql = "select batch.name from pe_bzz_batch batch where batch.id='" + batch_id + "'";
			rs = db.executeQuery(sql);
			System.out.println(sql);
			if(rs.next()) {
				titleBatchName = rs.getString("name");
			}
			db.close(rs);
		}
		if(stuRegNo != null && !stuRegNo.equals("")) {
			sql_en += " and stu.reg_no like '%" + stuRegNo.trim() + "%'";
		}
	//System.out.println("priEnt: " + sql_ent);	
		
	 	sql = "select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id ,seq,flag_course_type from ("
				+"select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type  \n"
				+ "from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su \n"
				+ "where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
				+ sql_con+ sql_con1 + sql_en + "\n"
				+" union "
				+"select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type  \n"
				+ "from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su \n"
				+ "where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
				+ sql_con + sql_con1 + sql_en + "\n"
				+") order by reg_no,flag_course_type,to_number(seq) ";
		
		String sql2 = "select a.id, count(a.course_id) as num from ( \n"
				+ sql +") a \n"
				+ "group by a.id";
		Map<String,String> coursePerStu = new HashMap<String, String>();
		rs = db.executeQuery(sql2);
		while(rs.next()) {
			String sid = rs.getString("id");
			String num = rs.getString("num");
			coursePerStu.put(sid, num);
		}
		db.close(rs);
		
		
		
    //System.out.println(sql);
   //out.println(sql);
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
  <o:LastSaved>2010-04-30T03:55:39Z</o:LastSaved>
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
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;}
.font9
	{color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;}
.font10
	{color:black;
	font-size:11.0pt;
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
.style17
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
	mso-style-name:"常规 2";}
.style18
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
	mso-style-name:"常规 3";}
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
.xl26
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style18;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl28
	{mso-style-parent:style18;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl29
	{mso-style-parent:style0;
	border:.5pt solid windowtext;}
.xl30
	{mso-style-parent:style17;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl31
	{mso-style-parent:style18;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;}
.xl32
	{mso-style-parent:style0;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;}
.xl33
	{mso-style-parent:style0;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl34
	{mso-style-parent:style18;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl35
	{mso-style-parent:style0;
	color:black;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;
	vertical-align:middle;}
.xl36
	{mso-style-parent:style0;}
.xl37
	{mso-style-parent:style0;
	color:black;
	font-size:11.0pt;
	text-align:right;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl38
	{mso-style-parent:style0;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl39
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;}
.xl40
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl41
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl42
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl43
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	white-space:normal;}
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
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>2</x:ActiveRow>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=1145 style='border-collapse:
 collapse;table-layout:fixed;width:859pt'>
 <col width=99 style='mso-width-source:userset;mso-width-alt:3168;width:74pt'>
 <col width=113 style='mso-width-source:userset;mso-width-alt:3616;width:85pt'>
 <col width=116 style='mso-width-source:userset;mso-width-alt:3712;width:87pt'>
 <col width=263 style='mso-width-source:userset;mso-width-alt:8416;width:197pt'>
 <col width=95 style='mso-width-source:userset;mso-width-alt:3040;width:71pt'>
 <col width=100 style='mso-width-source:userset;mso-width-alt:3200;width:75pt'>
 <col width=99 style='mso-width-source:userset;mso-width-alt:3168;width:74pt'>
 <col width=97 style='mso-width-source:userset;mso-width-alt:3104;width:73pt'>
 <col width=77 style='mso-width-source:userset;mso-width-alt:2464;width:58pt'>
 <col width=86 style='mso-width-source:userset;mso-width-alt:2752;width:65pt'>
 <tr height=42 style='mso-height-source:userset;height:31.5pt'>
  <td colspan=10 height=42 class=xl35 width=1145 style='height:31.5pt;
  width:859pt'><%=titleEntName%> <%=titleBatchName%> <%=stuRegNo%> 作业自测状况及学习进度综合报表<ruby><font class="font7"><rt
  class=font7></rt></font></ruby></td>
 </tr>
 <tr height=24 style='mso-height-source:userset;height:18.0pt'>
  <td colspan=10 height=24 class=xl37 style='height:18.0pt'>导出时间:
  <font class="font9"><%=getCurDate()%></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td rowspan=2 height=38 class=xl39 style='border-bottom:.5pt solid black;
  height:28.5pt;border-top:none'>姓名</td>
  <td rowspan=2 class=xl39 style='border-bottom:.5pt solid black;border-top:
  none'>学号</td>
  <td rowspan=2 class=xl39 style='border-bottom:.5pt solid black;border-top:
  none'>手机</td>
  <td rowspan=2 class=xl39 style='border-bottom:.5pt solid black;border-top:
  none'>课程</td>
  <td rowspan=2 class=xl39 style='border-bottom:.5pt solid black;border-top:
  none'>学习进度</td>
  <td colspan=3 class=xl40 style='border-right:.5pt solid black;border-left:
  none'>作业完成情况</td>
  <td rowspan=2 class=xl39 style='border-bottom:.5pt solid black;border-top:
  none'>自测情况</td>
  <td rowspan=2 class=xl43 width=86 style='border-bottom:.5pt solid black;
  border-top:none;width:65pt'>单位名称</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none;border-left:
  none'>判断</td>
  <td class=xl26 style='border-top:none;border-left:none'>选择</td>
  <td class=xl26 style='border-top:none;border-left:none'>问答</td>
 </tr>
 <%
				rs=db.executeQuery(sql);
				int a = 0,index=1;
				String pStuRegNo = "";
				while(rs!=null&&rs.next())
				{
					a++;
					String t_id=fixnull(rs.getString("id"));
					String course_id = fixnull(rs.getString("course_id"));
					String course_name = fixnull(rs.getString("course_name"));
					//String course_type = fixnull(rs.getString("course_type"));
					
					String fieldStuName=fixnull(rs.getString("stu_name"));
					String fieldRegNo=fixnull(rs.getString("stu_reg_no"));
					String fieldMobile=fixnull(rs.getString("mobile_phone"));
					String fieldCName=course_name;
					String fieldPName=fixnull(rs.getString("pname"));
					String fieldEName=fixnull(rs.getString("ename"));
					
					double jdPercent = rs.getDouble("percent");
					
					String curStuRegNo = fieldRegNo;
					
					//System.out.println("index: " + index);
					//System.out.println("fieldCName: " + fieldCName);
					//System.out.println("pRN: " + pStuRegNo);
					//System.out.println("cRN: " + curStuRegNo);
					
					String zcsql = " select ty.score as score,ty.id as tyid,ti.id as tiid ,count(ty.id) as num from test_testpaper_history ty "
						+ " inner join test_testpaper_info ti on ty.testpaper_id = ti.id "
						+ " and ti.group_id ='"+course_id+"' and ty.t_user_id = '" + t_id + "' group by ty.score,ty.id,ti.id";
					
//System.out.println("zcsql:" + zcsql);
					MyResultSet zcrs = db.executeQuery(zcsql);
					String zcscore = "";
					String zcid ="";
					String zice_link="";
					int num =0;
					while(zcrs!=null&zcrs.next()){
						 zcid = zcrs.getString("tyid");
						zcscore = fixnull(zcrs.getString("score"));
						//num = zcrs.getInt("num");
						
						num++;
					}
					db.close(zcrs);
					
					String pjsql = " select hy.id as id ,hi.id as hid, hy.test_result as test_result from test_homeworkpaper_history hy "
						+ " inner join test_homeworkpaper_info hi on hy.testpaper_id = hi.id "
						+ " and hi.group_id ='"+course_id+"'   and hy.t_user_id = '" + t_id + "'";
//System.out.println("pjsql:" + pjsql);
						rs1 =db.executeQuery(pjsql);
						String etype="";
						String  stanAnswer = "";
						String  userAnswer = "";
						int pcount =0;
						int dcount =0;
						int pzong = 0;
						int dzong = 0;
						int mcount =0;
						int mzong =0;
						
						String pppercent = "无判断";
						String ddpercent = "无单选";
						String mmpercent = "无多选";
						String stats = "无问答";
						String tiSql = "select distinct pi.type as type from test_homeworkpaper_info hi,test_paperquestion_info pi \n"
								+ "where pi.testpaper_id=hi.id and hi.group_id='" + course_id + "'";
						MyResultSet tiRs = db.executeQuery(tiSql);
						while(tiRs.next()) {
							String typeTi = tiRs.getString("type");
							if(typeTi.equals(TestQuestionType.PANDUAN)) {
								pppercent = "未做";
							} else if(typeTi.equals(TestQuestionType.DANXUAN)) {
								ddpercent = "单选未做";
							} else if(typeTi.equals(TestQuestionType.DUOXUAN)) {
								mmpercent = "多选未做";
							} else if(typeTi.equals(TestQuestionType.WENDA)) {
								stats = "未做";
							}
						}
						db.close(tiRs);
						
						String ppaperid ="";
						String dpaperid ="";
						String wpaperid ="";
						String mpaperid ="";
						Map map = new HashMap();
						int k = 0;
						while(rs1!=null&&rs1.next()){
							String temp = rs1.getString("test_result");
							String hid =rs1.getString("hid");
							
							//System.err.print("hid: "+hid);
							//System.err.println(" ; id: " + rs1.getString("id"));
										
							Document doc = DocumentHelper.parseText(temp);
							String totalScore = doc.selectSingleNode("/answers/totalscore").getText();
							String totalnote = doc.selectSingleNode("/answers/totalnote").getText();
							List itemlist = doc.selectNodes("/answers/item");
							for(Iterator it = itemlist.iterator();it.hasNext();){
								Element answer = (Element)it.next();
								Element typeEle = answer.element("type");
								etype = typeEle.getTextTrim();
								//判断题
								if(etype.equalsIgnoreCase(TestQuestionType.PANDUAN)){
									Element uAnswerEle = answer.element("uanswer");
									if(uAnswerEle.getTextTrim().equals("正确")){
										userAnswer ="1";
									}else if(uAnswerEle.getTextTrim().equals("错误")){
										userAnswer ="0";
									}else{
										userAnswer ="未做";
									}
									Element sAnswerEle = answer.element("sanswer");
									stanAnswer = sAnswerEle.getTextTrim();
									if(userAnswer.equals(stanAnswer)){
										pcount++;
									}
									pzong++;
									ppaperid=hid;
								}
								
								
								//多选题
								if(etype.equalsIgnoreCase(TestQuestionType.DUOXUAN)){
									Element uAnswerEle = answer.element("uanswer");
									userAnswer =uAnswerEle.getTextTrim().toUpperCase();
									Element sAnswerEle = answer.element("sanswer");
									stanAnswer = sAnswerEle.getTextTrim();
									if(userAnswer.equals(stanAnswer)){
										mcount++;
									}
									mzong++;
									mpaperid=hid;
								}
								
								//单选题
								if(etype.equalsIgnoreCase(TestQuestionType.DANXUAN)){
									Element uAnswerEle = answer.element("uanswer");
									userAnswer =uAnswerEle.getTextTrim().toUpperCase();
									Element sAnswerEle = answer.element("sanswer");
									stanAnswer = sAnswerEle.getTextTrim();
									if(userAnswer.equals(stanAnswer)){
										dcount++;
									}
									dzong++;
									dpaperid=hid;
								}
								
								//问答题
								if(etype.equalsIgnoreCase(TestQuestionType.WENDA)){
									Element uAnswerEle = answer.element("uanswer");
									userAnswer =uAnswerEle.getTextTrim();
									if(userAnswer!=""||userAnswer.length()>0){
										stats="已做";
									}
									Element sAnswerEle = answer.element("sanswer");
									stanAnswer = sAnswerEle.getTextTrim();
									wpaperid =hid;
								}
								
							}
							k++;
						}
						db.close(rs1);
						String ten = pcount+"";
						String den = dcount+"";
						String men = mcount+"";
					//	System.out.println("panduantotal:"+pzong+",panduancount:"+ten+",danxuantotal:"+dzong+",danxuancount:"+den+",multitotla:"+mzong+",multicount:"+men);
						if(pzong!=0){
							String ppzong = pzong+"";
							pppercent = this.percent(ten,ppzong);
						}
						if(dzong!=0){
							String ddzong = dzong+"";
							ddpercent = this.percent(den,ddzong);
						}
						if(mzong!=0){
							String mmzong = mzong+"";
							mmpercent = this.percent(men,mmzong);
						}
						
%>
<%if(!pStuRegNo.equals(curStuRegNo)) {
	pStuRegNo = curStuRegNo;
	String rowSpan = coursePerStu.get(t_id);
//	System.out.println(rowSpan);
//	System.out.println("1--" + fieldCName + "--" + curStuRegNo);
%>
<tr height=19 style='height:14.25pt'>
  <td rowspan=<%=rowSpan%> height=304 class=xl31 style='border-bottom:.5pt solid black;
  height:228.0pt;border-top:none'><%=fieldStuName%></td>
  <td rowspan=<%=rowSpan%>  class=xl31 style='border-bottom:.5pt solid black;border-top:
  none'><%=fieldRegNo%></td>
  <td rowspan=<%=rowSpan%>  class=xl31 style='border-bottom:.5pt solid black;border-top:
  none'><%=fieldMobile%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=fieldCName%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=getPercent(jdPercent)%> %</td>
  <td class=xl29 style='border-top:none;border-left:none'><%if(!pppercent.equals("未做")  && !pppercent.equals("无判断")){%><%=pppercent%><%}else{%><%=pppercent%><%} %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%if(mzong!=0){if(!mmpercent.equals("多选未做") && !mmpercent.equals("无多选")){%>(多选)<%=mmpercent%><%}else{%><%=mmpercent%>
              <%}} if(dzong!=0){if(!ddpercent.equals("单选未做") && !ddpercent.equals("无单选")){%>(单选)<%=ddpercent%><%}else{%><%=ddpercent%><%}} %>
              <%if(mzong==0 && dzong==0){%>未做<%} %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%if(!stats.equals("未做") && !stats.equals("无问答")){%><%=stats%><%}else{%><%=stats%><%} %></td>
  <td class=xl29 style='border-top:none;border-left:none'><%if(num>0){%><%=zcscore%><%}else{%>未做<%} %></td>
  <td rowspan=<%=rowSpan%>  class=xl34 width=86 style='border-bottom:.5pt solid black;
  border-top:none;width:65pt'><%=fieldEName%></td>
 </tr>



  
  
<% } else {
//System.out.println("2--" + fieldCName + "--" + curStuRegNo);%>
	
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl27 style='height:14.25pt;border-top:none;border-left:
  none'><%=fieldCName%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=getPercent(jdPercent)%> %</td>
  <td class=xl29 style='border-top:none;border-left:none'><%if(!pppercent.equals("未做")  && !pppercent.equals("无判断")){%><%=pppercent%><%}else{%><%=pppercent%><%} %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%if(mzong!=0){if(!mmpercent.equals("多选未做") && !mmpercent.equals("无多选")){%>(多选)<%=mmpercent%><%}else{%><%=mmpercent%>
              <%}} if(dzong!=0){if(!ddpercent.equals("单选未做") && !ddpercent.equals("无单选")){%>(单选)<%=ddpercent%><%}else{%><%=ddpercent%><%}} %>
              <%if(mzong==0 && dzong==0){%>未做<%} %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%if(!stats.equals("未做") && !stats.equals("无问答")){%><%=stats%><%}else{%><%=stats%><%} %></td>
  <td class=xl29 style='border-top:none;border-left:none' x:num><%if(num>0){%><%=zcscore%><%}else{%>未做<%} %></td>
 </tr>
<%}%>
   <%
           		index++;
           	}
           	db.close(rs);
            %>
 
 
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=99 style='width:74pt'><br/></td>
  <td width=113 style='width:85pt'><br/></td>
  <td width=116 style='width:87pt'><br/></td>
  <td width=263 style='width:197pt'><br/></td>
  <td width=95 style='width:71pt'><br/></td>
  <td width=100 style='width:75pt'><br/></td>
  <td width=99 style='width:74<br/>pt'></td>
  <td width=97 style='widt<br/>h:73pt'></td>
  <td width=77 style='<br/>width:58pt'></td>
  <td width=86 styl<br/>e='width:65pt'>}</td>
 </tr>
 <![endif]>
</table>

</body>

</html>
 