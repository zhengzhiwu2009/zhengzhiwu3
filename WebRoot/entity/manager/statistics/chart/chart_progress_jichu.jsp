<%@ page language="java" import="java.util.*"  contentType="text/xml;charset=gbk"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<jsp:directive.page import="java.io.PrintWriter"/>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<%!
	//�ж��ַ���Ϊ�յĻ�����ֵΪ""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
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
    if(dou>=50&&dou<60){
	num=6;
}
    if(dou>=60&&dou<70){
	num=7;
}
    if(dou>=70&&dou<=80){
	num=8;
}
    return num;
}

%>
<%  UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
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
	if(us.getRoleId().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006"))  //��ʾΪһ�����ܺ�һ������Ա
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
				    sql_bath =" and ps.fk_batch_id  ='"+bath+"'   ";
				}
				
		//���2010����ѧԱ����ҵ������������ҵ�����������£������鰲ȫ�����Ͱ��鰲ȫ�������£���ѡȡ���ȸߵ�һ�ţ���֤�����ι�16��
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
	
	   String sql=" select ps.id, sum(ce.time*(ts.percent/100)) as stime  from sso_user su ,"+sql_training+
	       "pe_bzz_student ps ,pr_bzz_tch_opencourse co ,pe_bzz_tch_course ce , pe_enterprise pe   "+ 
	       " where ps.fk_sso_user_id = su.id and  su.id = ts.student_id  and  pe.id = ps.fk_enterprise_id"+ 
	       " and co.id = ts.course_id and co.fk_course_id = ce.id and  ps.fk_batch_id = co.fk_batch_id and su.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' "
	       +sql_bath+"  "+sql_con+"  "+sql_con1+" and co.flag_course_type='402880f32200c249012200c780c40001'  group by ps.id ";
	       //System.out.println(sql);
 rs = db.executeQuery(sql);
 	int total_10 = 0;
	int total_20 = 0;
	int total_30 = 0;
	int total_40 = 0;
	int total_50 = 0;
	int total_60 = 0;
	int total_70 = 0;
	int total_80 = 0;
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
		}
		}
		db.close(rs);
		StringBuffer sqltemp=new StringBuffer();
        String str ="<chart palette='2'  xAxisName='ѧʱ' yAxisName='Number' showValues='0' decimals='0' formatNumberScale='0'>"+
                    "<set label='0~10' value='"+total_10+"'/>"+
                    "<set label='10~20' value='"+total_20+"' />"+
                    "<set label='20~30' value='"+total_30+"' />"+
                    "<set label='30~40' value='"+total_40+"' />"+
                    "<set label='40~50' value='"+total_50+"' />"+
                    "<set label='50~60' value='"+total_60+"' />"+
                    "<set label='60~70' value='"+total_70+"' />"+
                    "<set label='70~80' value='"+total_80+"' /></chart>"; 

        
		sqltemp.append(str);
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader("Expires",0);
		PrintWriter w = response.getWriter();
		w.write(sqltemp.toString());
		w.close();
	;
%>
	