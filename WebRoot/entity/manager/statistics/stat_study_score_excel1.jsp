<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%response.setHeader("Content-Disposition", "attachment;filename=stat_study_score_excel1.xls"); %>
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
<%@page import="org.dom4j.Element"%>

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
	String sql_batch = "";
	if((batch_id==null||batch_id.equals("")||batch_id.equals("null"))&&stuRegNo!=null&&!stuRegNo.equals(""))
	{
		sql_batch = "select fk_batch_id from pe_bzz_student where reg_no='"+stuRegNo+"'";
		rs = db.executeQuery(sql_batch);
		if(rs!=null&&rs.next())
		{
			batch_id = rs.getString("fk_batch_id");
		}
		db.close(rs);
	}
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
		String sql_stu = "";
		
		if(!sql_ent.equals("") && !us.getRoleId().equals("3"))
		{
			sql_con+=" and ent2.id in "+sql_ent;
			sql_con1=" and stu.fk_enterprise_id in "+sql_ent;
			sql_stu+=" and bs.fk_enterprise_id in "+sql_ent;
		}
		//System.out.println("sqlent:"+sql_ent);
		if(!enterprise_id.equals(""))
		{
			if(erji_id.equals("")||erji_id.equals("#"))
			{
				sql_en = " and (ent2.id = '" + enterprise_id + "' or ent2.fk_parent_id ='"+enterprise_id+"')";
			}
			else
			{
				sql_en =" and ent2.id = '"+erji_id+"'";
				sql_stu+=" and bs.fk_enterprise_id = '"+erji_id+"'";
			}
		}
		if(!batch_id.equals(""))
		{
			sql_en +="and ocrs.FK_BATCH_ID= '"+batch_id+"'";
			sql_stu+=" and bs.fk_batch_id = '" + batch_id + "'";
		}
		if(stuRegNo != null && !stuRegNo.equals("")) {
			sql_en += " and stu.reg_no like '%" + stuRegNo.trim() + "%'";
			sql_stu+=" and bs.reg_no like '%" + stuRegNo.trim() + "%'";
		}
		
		
	 	/*sql = "select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from ("
				+"select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type  \n"
				+ "from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n"
				+ "where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n"
				+ sql_con+ sql_con1 + sql_en + "\n"
				+" union "
				+"select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type  \n"
				+ "from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su \n"
				+ "where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n"
				+ sql_con + sql_con1 + sql_en + "\n"
				+") order by reg_no,flag_course_type,to_number(seq) ";*/
/*
高媛编写

sql="select aa.id,aa.stu_name,aa.stu_reg_no,aa.mobile_phone,aa.course_name,aa.percent,aa.pname,aa.ename,bb.score,bb.group_id as zcgroup_id,aa.panduan_percent as panduan_percent,aa.group_id as panduangroup_id,cc.danxuan_percent,cc.group_id as danxuangroup_id,dd.duoxuan_percent,dd.group_id as duoxuangroup_id,ee.type as wenda_type,ee.group_id as wendagroup_id from (\n" +
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as panduan_percent,b.group_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+ 
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+  
	") ) a,\n" + 
	"(select ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"       (select testpaper_id  ,percent from  test_homeworkpaper_history where type='PANDUAN') hi,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"       (select testpaper_id  ,percent,user_id,t_user_id from  test_homeworkpaper_history where type='PANDUAN') hi,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id  ) aa,\n" + 
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.score,b.group_id,b.paper_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select cc.group_id ,t.id as course_id ,t.name,cc.paper_id as paper_id from  pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,(select cp.paper_id,cp.testcourse_id,ci.group_id from onlinetest_course_paper cp,\n" + 
	"       onlinetest_course_info  ci,test_testpaper_info ti where ci.id = cp.testcourse_id and ti.id=cp.paper_id) cc where  cc.group_id(+) = t.id and bt.fk_course_id(+)=t.id and bt.fk_batch_id='ff8080812253f04f0122540a58000004' ) b,\n" + 
	"(select substr(hi.user_id,2,32) as user_id, hi.score,ti.group_id ,t.id as course_id ,t.name from test_testpaper_history hi, test_testpaper_info ti,pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,onlinetest_course_paper cp,onlinetest_course_info ci\n" + 
	" where   ti.id=cp.paper_id and ci.id=cp.testcourse_id and hi.testpaper_id(+) = ti.id and ci.group_id(+)=t.id and t.id=ti.group_id(+) and bt.fk_course_id=t.id and bt.fk_batch_id='ff8080812253f04f0122540a58000004' --and hi.user_id like '(52cce2fd24654f770124655133d20016%'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	")bb,\n" + 
	"(select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as danxuan_percent,b.group_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"       (select testpaper_id  ,percent from  test_homeworkpaper_history where type='DANXUAN') hi,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"       (select testpaper_id  ,percent,user_id,t_user_id from  test_homeworkpaper_history where type='DANXUAN') hi,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id) cc,\n" + 
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as duoxuan_percent,b.group_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"       (select testpaper_id  ,percent from  test_homeworkpaper_history where type='DUOXUAN') hi,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"       (select testpaper_id  ,percent,user_id,t_user_id from  test_homeworkpaper_history where type='DUOXUAN') hi,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") dd,\n" + 
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as wenda_percent,b.group_id,b.type from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent,hi.type\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"       (select testpaper_id  ,percent,type from  test_homeworkpaper_history where type='WENDA') hi,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"       (select testpaper_id  ,percent,user_id,t_user_id from  test_homeworkpaper_history where type='WENDA') hi,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") ee\n" + 
	" where aa.course_id=bb.course_id and aa.id=bb.id\n" + 
	" and cc.course_id=aa.course_id and cc.id=aa.id and dd.course_id=aa.course_id and dd.id=aa.id\n" + 
	" and ee.course_id=aa.course_id and ee.id=aa.id order by aa.stu_reg_no,aa.flag_course_type,to_number(aa.seq)\n" ;
*/

	//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
		String sql_training="training_course_student tcs,";
		if(batch_id!=null && batch_id.equals("ff8080812824ae6f012824b0a89e0008"))
		{
		sql_training="("+
			"select course_id,percent,learn_status,student_id from training_course_student s,pe_bzz_student bs where (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821') \n" + 
			" and bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' "+
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
			") tcs,";
			}
		//sql_training="training_course_student tcs,";

/*
朱高建根据高媛编写的sql改写如下：
*/
if(batch_id.equals("ff8080812253f04f0122540a58000004")||batch_id.equals("52cce2fd2471ddc30124764980580131")||batch_id.equals("ff8080812824ae6f012824b0a89e0008"))
{
	sql="select aa.id,aa.stu_name,aa.stu_reg_no,aa.mobile_phone,aa.course_name,aa.percent,aa.pname,aa.ename,bb.score,bb.group_id as zcgroup_id,aa.panduan_percent as panduan_percent,aa.group_id as panduangroup_id,cc.danxuan_percent,cc.group_id as danxuangroup_id,dd.duoxuan_percent,dd.group_id as duoxuangroup_id,ee.type as wenda_type,ee.group_id as wendagroup_id,ff.type as fenxi_type,ff.fenxi_percent as fenxi_percent,ff.group_id as fenxigroup_id \n" +
	"from (select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as panduan_percent,b.group_id \n" + 
	"from (select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+ 
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+  
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN'  and ti.batch_id is null and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"') b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='PANDUAN') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'PANDUAN' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN'  and ti.batch_id is null and ti.status='1' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id  ) aa,\n" + 
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.score,b.group_id,b.paper_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select cc.group_id ,t.id as course_id ,t.name,cc.paper_id as paper_id from  pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,(select cp.paper_id,cp.testcourse_id,ci.group_id from onlinetest_course_paper cp,\n" + 
	"       onlinetest_course_info  ci,test_testpaper_info ti where ci.id = cp.testcourse_id and ti.id=cp.paper_id  and ci.fk_batch_id is null and ti.status='1' and ci.status='1' ) cc where  cc.group_id(+) = t.id and bt.fk_course_id(+)=t.id and bt.fk_batch_id='"+batch_id+"' ) b,\n" + 
	"(select substr(hi.user_id,2,32) as user_id, hi.score,ti.group_id ,t.id as course_id ,t.name \n" +
	"		from test_testpaper_history hi, test_testpaper_info ti,pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,onlinetest_course_paper cp,onlinetest_course_info ci,pe_bzz_student bs,sso_user su \n" + 
	" where   ti.id=cp.paper_id  and ci.fk_batch_id is null and ci.status='1' and ti.status='1' and ci.id=cp.testcourse_id and hi.testpaper_id(+) = ti.id and ci.group_id(+)=t.id and t.id=ti.group_id(+) and bt.fk_course_id=t.id and bt.fk_batch_id='"+batch_id+"' and hi.t_user_id = bs.id and bs.fk_sso_user_id=su.id "+sql_stu+"\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	")bb,\n" + 
	
	
	
	"(select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as danxuan_percent,b.group_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN'  and ti.batch_id is null and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='DANXUAN') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'DANXUAN' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN'  and ti.batch_id is null and ti.status='1'  )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id) cc,\n" + 
	
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as duoxuan_percent,b.group_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN'  and ti.batch_id is null and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='DUOXUAN') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'DUOXUAN' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN'  and ti.batch_id is null and ti.status='1' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") dd,\n" + 
	
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as wenda_percent,b.group_id,b.type from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name,'WENDA' as type \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA'  and ti.batch_id is null and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='WENDA') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'WENDA' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA'  and ti.batch_id is null  and ti.status='1' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") ee,\n" + 
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as fenxi_percent,b.group_id,b.type from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name,'YUEDU' as type \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='YUEDU'  and ti.batch_id is null and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='WENDA') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'YUEDU' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='YUEDU'  and ti.batch_id is null and ti.status='1'  )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") ff \n" + 
	
	
	
	" where aa.course_id=bb.course_id and aa.id=bb.id\n" + 
	" and cc.course_id=aa.course_id and cc.id=aa.id and dd.course_id=aa.course_id and dd.id=aa.id\n" + 
	" and ee.course_id=aa.course_id and ee.id=aa.id and aa.id=ff.id and aa.course_id=ff.course_id order by aa.stu_reg_no,aa.flag_course_type,to_number(aa.seq)\n" ;
}
else
{
	sql="select aa.id,aa.stu_name,aa.stu_reg_no,aa.mobile_phone,aa.course_name,aa.percent,aa.pname,aa.ename,bb.score,bb.group_id as zcgroup_id,aa.panduan_percent as panduan_percent,aa.group_id as panduangroup_id,cc.danxuan_percent,cc.group_id as danxuangroup_id,dd.duoxuan_percent,dd.group_id as duoxuangroup_id,ee.type as wenda_type,ee.group_id as wendagroup_id,ff.type as fenxi_type,ff.fenxi_percent as fenxi_percent,ff.group_id as fenxigroup_id \n" +
	"from (select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as panduan_percent,b.group_id \n" + 
	"from (select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+ 
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+  
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN'  and ti.batch_id='"+batch_id+"' and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"') b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='PANDUAN') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'PANDUAN' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN'  and ti.batch_id='"+batch_id+"' and ti.status='1' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id  ) aa,\n" + 
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.score,b.group_id,b.paper_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select cc.group_id ,t.id as course_id ,t.name,cc.paper_id as paper_id from  pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,(select cp.paper_id,cp.testcourse_id,ci.group_id from onlinetest_course_paper cp,\n" + 
	"       onlinetest_course_info  ci,test_testpaper_info ti where ci.id = cp.testcourse_id and ti.id=cp.paper_id  and ci.fk_batch_id='"+batch_id+"' and ti.status='1' and ci.status='1' ) cc where  cc.group_id(+) = t.id and bt.fk_course_id(+)=t.id and bt.fk_batch_id='"+batch_id+"' ) b,\n" + 
	"(select substr(hi.user_id,2,32) as user_id, hi.score,ti.group_id ,t.id as course_id ,t.name \n" +
	"		from test_testpaper_history hi, test_testpaper_info ti,pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,onlinetest_course_paper cp,onlinetest_course_info ci,pe_bzz_student bs,sso_user su \n" + 
	" where   ti.id=cp.paper_id  and ci.fk_batch_id='"+batch_id+"' and ci.status='1' and ti.status='1' and ci.id=cp.testcourse_id and hi.testpaper_id(+) = ti.id and ci.group_id(+)=t.id and t.id=ti.group_id(+) and bt.fk_course_id=t.id and bt.fk_batch_id='"+batch_id+"' and hi.t_user_id = bs.id and bs.fk_sso_user_id=su.id "+sql_stu+"\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	")bb,\n" + 
	
	
	
	"(select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as danxuan_percent,b.group_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN'  and ti.batch_id='"+batch_id+"' and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='DANXUAN') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'DANXUAN' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN'  and ti.batch_id='"+batch_id+"' and ti.status='1'  )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id) cc,\n" + 
	
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as duoxuan_percent,b.group_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN'  and ti.batch_id='"+batch_id+"' and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='DUOXUAN') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'DUOXUAN' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN'  and ti.batch_id='"+batch_id+"' and ti.status='1' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") dd,\n" + 
	
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as wenda_percent,b.group_id,b.type from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name,'WENDA' as type \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA'  and ti.batch_id='"+batch_id+"' and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='WENDA') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'WENDA' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA'  and ti.batch_id='"+batch_id+"'  and ti.status='1' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") ee,\n" + 
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as fenxi_percent,b.group_id,b.type from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name,'YUEDU' as type \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='YUEDU'  and ti.batch_id='"+batch_id+"' and ti.status='1' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='WENDA') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'YUEDU' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='YUEDU'  and ti.batch_id='"+batch_id+"' and ti.status='1'  )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") ff \n" + 
	
	
	
	" where aa.course_id=bb.course_id and aa.id=bb.id\n" + 
	" and cc.course_id=aa.course_id and cc.id=aa.id and dd.course_id=aa.course_id and dd.id=aa.id\n" + 
	" and ee.course_id=aa.course_id and ee.id=aa.id and aa.id=ff.id and aa.course_id=ff.course_id order by aa.stu_reg_no,aa.flag_course_type,to_number(aa.seq)\n" ;
}
/*
sql="select aa.id,aa.stu_name,aa.stu_reg_no,aa.mobile_phone,aa.course_name,aa.percent,aa.pname,aa.ename,bb.score,bb.group_id as zcgroup_id,aa.panduan_percent as panduan_percent,aa.group_id as panduangroup_id,cc.danxuan_percent,cc.group_id as danxuangroup_id,dd.duoxuan_percent,dd.group_id as duoxuangroup_id,ee.type as wenda_type,ee.group_id as wendagroup_id \n" +
	"from (select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as panduan_percent,b.group_id \n" + 
	"from (select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+ 
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+  
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='PANDUAN') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'PANDUAN' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id  ) aa,\n" + 
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.score,b.group_id,b.paper_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select cc.group_id ,t.id as course_id ,t.name,cc.paper_id as paper_id from  pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,(select cp.paper_id,cp.testcourse_id,ci.group_id from onlinetest_course_paper cp,\n" + 
	"       onlinetest_course_info  ci,test_testpaper_info ti where ci.id = cp.testcourse_id and ti.id=cp.paper_id) cc where  cc.group_id(+) = t.id and bt.fk_course_id(+)=t.id and bt.fk_batch_id='"+batch_id+"' ) b,\n" + 
	"(select substr(hi.user_id,2,32) as user_id, hi.score,ti.group_id ,t.id as course_id ,t.name \n" +
	"		from test_testpaper_history hi, test_testpaper_info ti,pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,onlinetest_course_paper cp,onlinetest_course_info ci,pe_bzz_student bs,sso_user su \n" + 
	" where   ti.id=cp.paper_id and ci.id=cp.testcourse_id and hi.testpaper_id(+) = ti.id and ci.group_id(+)=t.id and t.id=ti.group_id(+) and bt.fk_course_id=t.id and bt.fk_batch_id='"+batch_id+"' and hi.t_user_id = bs.id and bs.fk_sso_user_id=su.id "+sql_stu+"\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	")bb,\n" + 
	
	
	
	"(select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as danxuan_percent,b.group_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='DANXUAN') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'DANXUAN' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id) cc,\n" + 
	
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as duoxuan_percent,b.group_id from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='DUOXUAN') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'DUOXUAN' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") dd,\n" + 
	
	
	
	"(\n" + 
	"select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as wenda_percent,b.group_id,b.type from (\n" + 
	"select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su\n" + 
	"where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	" union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type\n" + 
	"from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su\n" + 
	"where   tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
	sql_con+ sql_con1 + sql_en + "\n"+   
	") ) a,\n" + 
	"(select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name,'WENDA' as type \n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA' )ti\n" + 
	" where\n" + 
	"    ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"' ) b,\n" + 
	"(select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent\n" + 
	"  from pe_bzz_tch_course          t,\n" + 
	"       pr_bzz_tch_opencourse      bt,\n" + 
	//"       (select testpaper_id  ,percent,user_id from  test_homeworkpaper_history where type='WENDA') hi,\n" + 
	"       (select testpaper_id, percent,user_id,t_user_id from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su \n" + 
    "            where thh.type = 'WENDA' and bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id " + sql_stu+"\n" + 
    "            and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,\n" +
	"      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA' )ti\n" + 
	" where\n" + 
	"    hi.testpaper_id(+) = ti.id\n" + 
	"   and ti.group_id(+) = t.id\n" + 
	"   and bt.fk_course_id(+) = t.id\n" + 
	"   and bt.fk_batch_id = '"+batch_id+"'\n" + 
	" ) c\n" + 
	"where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id\n" + 
	") ee\n" + 
	
	
	
	" where aa.course_id=bb.course_id and aa.id=bb.id\n" + 
	" and cc.course_id=aa.course_id and cc.id=aa.id and dd.course_id=aa.course_id and dd.id=aa.id\n" + 
	" and ee.course_id=aa.course_id and ee.id=aa.id order by aa.stu_reg_no,aa.flag_course_type,to_number(aa.seq)\n" ;
   //	System.out.println("sql:"+sql);
  */
  
  /**
  select aa.id,aa.stu_name,aa.stu_reg_no,aa.mobile_phone,aa.course_name,aa.percent,aa.pname,aa.ename,bb.score,bb.group_id as zcgroup_id,aa.panduan_percent as panduan_percent,aa.group_id as panduangroup_id,cc.danxuan_percent,cc.group_id as danxuangroup_id,dd.duoxuan_percent,dd.group_id as duoxuangroup_id,ee.type as wenda_type,ee.group_id as wendagroup_id 
       from (select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as panduan_percent,b.group_id 
                    from (select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  
                                 from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su
                                              where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008'
                                 union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su
                                              where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008')) a,
                         (select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name 
                                 from pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,
                                      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN' )ti
                                 where ti.group_id(+) = t.id and bt.fk_course_id(+) = t.id and bt.fk_batch_id = 'ff8080812824ae6f012824b0a89e0008' ) b,
                         (select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent
                                 from pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,
                                      (select testpaper_id, percent,user_id,t_user_id
                  from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su
                 where thh.type = 'PANDUAN' and 
                 bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id
                 and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and bs.fk_enterprise_id in('ff80808128f2740d0128f28caad501c5')
                 and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,
                                      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='PANDUAN' )ti
                                 where hi.testpaper_id(+) = ti.id and ti.group_id(+) = t.id and bt.fk_course_id(+) = t.id and bt.fk_batch_id = 'ff8080812824ae6f012824b0a89e0008' ) c
                    where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id) aa,--59.718
            (select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.score,b.group_id,b.paper_id 
                    from (select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  
                                 from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su
                                              where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008'
                                 union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su
                                              where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'  and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008')) a,
                         (select cc.group_id ,t.id as course_id ,t.name,cc.paper_id as paper_id 
                                 from  pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,
                                       (select cp.paper_id,cp.testcourse_id,ci.group_id from onlinetest_course_paper cp,onlinetest_course_info  ci,test_testpaper_info ti where ci.id = cp.testcourse_id and ti.id=cp.paper_id) cc 
                                 where  cc.group_id(+) = t.id and bt.fk_course_id(+)=t.id and bt.fk_batch_id='ff8080812253f04f0122540a58000004' ) b,
                         (select substr(hi.user_id,2,32) as user_id, hi.score,ti.group_id ,t.id as course_id ,t.name 
                                 from test_testpaper_history hi, test_testpaper_info ti,pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,onlinetest_course_paper cp,onlinetest_course_info ci,pe_bzz_student bs,sso_user su
                                 where ti.id=cp.paper_id and ci.id=cp.testcourse_id and hi.testpaper_id(+) = ti.id and ci.group_id(+)=t.id and t.id=ti.group_id(+) and bt.fk_course_id=t.id and bt.fk_batch_id='ff8080812253f04f0122540a58000004'
                                 and hi.user_id like '('||bs.id||'%' and bs.fk_sso_user_id=su.id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and bs.fk_enterprise_id in('ff80808128f2740d0128f28caad501c5')
                 and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') c
                    where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id)bb, --13.782
            (select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as danxuan_percent,b.group_id 
                    from (select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type 
                                 from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su
                                              where  tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008'
                                 union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su
                                              where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008')) a,
                         (select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name
                                 from pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,
                                      (select distinct ti.id as id,ti.group_id from test_homeworkpaper_info ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN' )ti
                                 where ti.group_id(+) = t.id and bt.fk_course_id(+) = t.id and bt.fk_batch_id = 'ff8080812824ae6f012824b0a89e0008') b,
                         (select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent
                                 from pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,
                                      (select testpaper_id, percent,user_id,t_user_id
                  from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su
                 where thh.type = 'DANXUAN' and 
                 bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id
                 and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and bs.fk_enterprise_id in('ff80808128f2740d0128f28caad501c5')
                 and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,
                                      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DANXUAN' )ti
                                 where hi.testpaper_id(+) = ti.id and ti.group_id(+) = t.id and bt.fk_course_id(+) = t.id and bt.fk_batch_id = 'ff8080812824ae6f012824b0a89e0008') c
                    where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id) cc,--27.297
            (select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as duoxuan_percent,b.group_id 
                    from (select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type  
                                 from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su
                                              where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008'
                                 union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su
                                              where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008')) a,
                         (select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name
                                 from pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,
                                      (select distinct ti.id as id,ti.group_id from test_homeworkpaper_info ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN' )ti
                                 where ti.group_id(+) = t.id and bt.fk_course_id(+) = t.id and bt.fk_batch_id = 'ff8080812824ae6f012824b0a89e0008') b,
                         (select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent
                                 from pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,
                                      (select testpaper_id, percent,user_id,t_user_id
                  from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su
                 where thh.type = 'DUOXUAN' and 
                 bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id
                 and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and bs.fk_enterprise_id in('ff80808128f2740d0128f28caad501c5')
                 and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,
                                      (select distinct ti.id as id,ti.group_id from test_homeworkpaper_info ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='DUOXUAN' )ti
                                 where hi.testpaper_id(+) = ti.id and ti.group_id(+) = t.id and bt.fk_course_id(+) = t.id and bt.fk_batch_id = 'ff8080812824ae6f012824b0a89e0008') c
                    where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id) dd,--17.938
            (select a.id,a.stu_name,a.stu_reg_no,a.mobile_phone,a.course_name,a.percent,a.pname,a.ename,a.course_id,a.seq,a.flag_course_type,c.percent as wenda_percent,b.group_id,b.type 
                    from (select id,name as stu_name,reg_no as stu_reg_no,mobile_phone,cname as course_name,percent,pname,ename,course_id,seq,flag_course_type 
                                 from (select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1, sso_user su
                                              where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008'
                                 union select stu.id,crs.id as course_id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type
                                              from training_course_student tcs, pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2, sso_user su
                                              where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and ent2.id in ('ff80808128f2740d0128f28caad501c5') and stu.fk_enterprise_id in ('ff80808128f2740d0128f28caad501c5') and ent2.id = 'ff80808128f2740d0128f28caad501c5'and ocrs.FK_BATCH_ID= 'ff8080812824ae6f012824b0a89e0008')) a,
                         (select ti.group_id, ti.id as testpaper_id,t.id as course_id, t.name,'WENDA' as type
                                 from pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,
                                      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA' )ti
                                 where ti.group_id(+) = t.id and bt.fk_course_id(+) = t.id and bt.fk_batch_id = 'ff8080812824ae6f012824b0a89e0008') b,
                         (select hi.t_user_id as user_id, ti.group_id, hi.testpaper_id,t.id as course_id, t.name,hi.percent
                                 from pe_bzz_tch_course t,pr_bzz_tch_opencourse bt,
                                      (select testpaper_id, percent,user_id,t_user_id
                  from test_homeworkpaper_history thh,pe_bzz_student bs,sso_user su
                 where thh.type = 'WENDA' and 
                 bs.fk_sso_user_id=su.id and thh.t_user_id=bs.id
                 and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and bs.fk_enterprise_id in('ff80808128f2740d0128f28caad501c5')
                 and su.flag_isvalid = '2'and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') hi,
                                      (select distinct ti.id as id,ti.group_id from  test_homeworkpaper_info    ti,test_paperquestion_info pi where ti.id=pi.testpaper_id and pi.type='WENDA' )ti
                                 where hi.testpaper_id(+) = ti.id and ti.group_id(+) = t.id and bt.fk_course_id(+) = t.id and bt.fk_batch_id = 'ff8080812824ae6f012824b0a89e0008') c
                    where  c.course_id(+)=a.course_id and  c.user_id(+)=a.id and b.course_id=a.course_id) ee--9.578
       where aa.course_id=bb.course_id and aa.id=bb.id and cc.course_id=aa.course_id and cc.id=aa.id and dd.course_id=aa.course_id and dd.id=aa.id and ee.course_id=aa.course_id and ee.id=aa.id 
       order by aa.stu_reg_no,aa.flag_course_type,to_number(aa.seq)
  */
  
 %>
 
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="Book1.files/filelist.xml">
<link rel=Edit-Time-Data href="Book1.files/editdata.mso">
<link rel=OLE-Object-Data href="Book1.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>China User</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-04-27T06:44:52Z</o:Created>
  <o:LastSaved>2010-04-27T06:46:02Z</o:LastSaved>
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
	font-size:18.0pt;
	font-weight:700;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;}
.xl25
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;}
.xl26
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:right;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl27
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl28
	{mso-style-parent:style0;
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
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>3</x:ActiveRow>
       <x:ActiveCol>10</x:ActiveCol>
       <x:RangeSelection>$A$1:$K$4</x:RangeSelection>
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
  <x:WindowWidth>18195</x:WindowWidth>
  <x:WindowTopX>120</x:WindowTopX>
  <x:WindowTopY>75</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1645 style='border-collapse:
 collapse;table-layout:fixed;width:1235pt'>
 <col width=72 style='width:54pt'>
 <col width=172 style='mso-width-source:userset;mso-width-alt:5504;width:129pt'>
 <col width=118 style='mso-width-source:userset;mso-width-alt:3776;width:89pt'>
 <col width=112 style='mso-width-source:userset;mso-width-alt:3584;width:84pt'>
 <col width=214 style='mso-width-source:userset;mso-width-alt:6848;width:161pt'>
 <col width=147 style='mso-width-source:userset;mso-width-alt:4704;width:110pt'>
 <col width=129 span=2 style='mso-width-source:userset;mso-width-alt:4128;
 width:97pt'>
 <col width=76 style='mso-width-source:userset;mso-width-alt:2432;width:57pt'>
 <col width=219 style='mso-width-source:userset;mso-width-alt:7008;width:164pt'>
 <col width=257 style='mso-width-source:userset;mso-width-alt:8224;width:193pt'>
 <tr height=30 style='height:22.5pt'>
  <td colspan=12 height=30 class=xl24 width=1645 style='height:22.5pt;
  width:1235pt'>作业自测状况报表</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=11 height=19 class=xl26 style='height:14.25pt'>导出时间:<font
  class="font7"><%=getCurDate()%><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl27 style='height:14.25pt;border-top:none'>序号</td>
  <td class=xl27 style='border-top:none;border-left:none'>姓名</td>
  <td class=xl27 style='border-top:none;border-left:none'>学号</td>
  <td class=xl27 style='border-top:none;border-left:none'>移动电话</td>
  <td class=xl27 style='border-top:none;border-left:none'>课程名称</td>
  <td class=xl27 style='border-top:none;border-left:none'>作业选择题正确率</td>
  <td class=xl27 style='border-top:none;border-left:none'>作业判断正确率</td>
  <td class=xl27 style='border-top:none;border-left:none'>作业问答题状态</td>
  <td class=xl27 style='border-top:none;border-left:none'>作业案例分析题状态</td>
  <td class=xl27 style='border-top:none;border-left:none'>自测分数</td>
  <td class=xl27 style='border-top:none;border-left:none'>所在集团</td>
  <td class=xl27 style='border-top:none;border-left:none'>所在企业</td>
 </tr>
 <%
				rs=db.executeQuery(sql);
				int a = 0,index=1;
				while(rs!=null&&rs.next())
				{
					
					a++;
					String t_id=fixnull(rs.getString("id"));
					//String course_id = fixnull(rs.getString("course_id"));
					String course_name = fixnull(rs.getString("course_name"));
					//String course_type = fixnull(rs.getString("course_type"));
					
					String fieldStuName=fixnull(rs.getString("stu_name"));
					String fieldRegNo=fixnull(rs.getString("stu_reg_no"));
					String fieldMobile=fixnull(rs.getString("mobile_phone"));
					String fieldCName=course_name;
					String fieldPName=fixnull(rs.getString("pname"));
					String fieldEName=fixnull(rs.getString("ename"));
					
					String score=fixnull(rs.getString("score"));
					String zcgroup_id=fixnull(rs.getString("zcgroup_id"));
					String pd_percent=fixnull(rs.getString("panduan_percent"));
					String pdgroup_id=fixnull(rs.getString("panduangroup_id"));
					String danxuan_percent=fixnull(rs.getString("danxuan_percent"));
					String danxuangroup_id=fixnull(rs.getString("danxuangroup_id"));
					String duoxuan_percent=fixnull(rs.getString("duoxuan_percent"));
					String duoxuangroup_id=fixnull(rs.getString("duoxuangroup_id"));
					String wenda_type=fixnull(rs.getString("wenda_type"));
					String wendagroup_id=fixnull(rs.getString("wendagroup_id"));
					
					String fenxi_percent=fixnull(rs.getString("fenxi_percent"));
					String fenxigroup_id=fixnull(rs.getString("fenxigroup_id"));
					
					String zcscore=score;
					if(score.equals("")&&!zcgroup_id.equals("") )
					{
						zcscore="未做";
					}
					else if(zcgroup_id.equals(""))
					{
						zcscore="无自测";
					}
					
					if(pd_percent.equals("")&& !pdgroup_id.equals(""))
					{
						pd_percent="未做";
					}
					else if(pdgroup_id.equals(""))
					{
						pd_percent="无判断";
					}
					//由于《班组现场管理》这门课的单选和多选都在同一份作业中，所以将单选的情况设置成与多选的情况一样
					/*if(course_name.equals("班组现场管理")){
						danxuan_percent=duoxuan_percent;
						danxuangroup_id=duoxuangroup_id;
					}*/
					if(danxuan_percent.equals("")&& !danxuangroup_id.equals(""))
					{
						danxuan_percent="单选未做";
					}
					else if(danxuangroup_id.equals(""))
					{
						danxuan_percent="无单选";
					}
					else if(!danxuan_percent.equals(""))
					{
						danxuan_percent="(单选)"+danxuan_percent;
					}
					
					if(duoxuan_percent.equals("")&& !duoxuangroup_id.equals(""))
					{
						duoxuan_percent="多选未做";
					}
					else if(duoxuangroup_id.equals(""))
					{
						duoxuan_percent="无多选";
					}
					else if(!duoxuan_percent.equals(""))
					{
						duoxuan_percent="(多选)"+duoxuan_percent;
					}
					
					String wenda_status="已做";
					if(wenda_type.equals("")&& !wendagroup_id.equals(""))
					{
						wenda_status="未做";
					}
					else if(wendagroup_id.equals(""))
					{
						wenda_status="无问答";
					}
					if(fenxi_percent.equals("")&& !fenxigroup_id.equals(""))
					{
						fenxi_percent="未做";
					}
					else if(fenxigroup_id.equals(""))
					{
						fenxi_percent="无案例分析";
					}
%>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl28 style='height:14.25pt;border-top:none'><%=index%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fieldStuName%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fieldRegNo%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fieldMobile%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fieldCName%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=danxuan_percent%>&nbsp;&nbsp;<%=duoxuan_percent%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=pd_percent%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=wenda_status%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fenxi_percent%></td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=zcscore%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fieldPName%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fieldEName%></td>
 </tr>
   <%
           		index++;
           	}
           	db.close(rs);
            %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=172 style='width:129pt'></td>
  <td width=118 style='width:89pt'></td>
  <td width=112 style='width:84pt'></td>
  <td width=214 style='width:161pt'></td>
  <td width=147 style='width:110pt'></td>
  <td width=129 style='width:97pt'></td>
  <td width=129 style='width:97pt'></td>
  <td width=76 style='width:57pt'></td>
  <td width=219 style='width:164pt'></td>
  <td width=257 style='width:193pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>


