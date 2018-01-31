package com.whaty.platform.entity.web.action.information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class StudentStudyStatusAction extends MyBaseAction {

	private String opt;
	private String batch;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		String title = "";
		if("noLogin".equals(opt)) {
			title = "未登录学员列表";
		} else if("lssFFPc".equals(opt)) {
			title = "学习进度低于25%学员列表";
		} else if("lssFGPcc".equals(opt)) {
			title = "学习进度在25%-50%的学员列表";
		} else if("lssFGPccc".equals(opt)) {
			title = "学习进度在50%-80%的学员列表";
		} else if("grtFPc".equals(opt)) {
			title = "学习进度在80%-100%的学员列表";
		} else if("completeJc".equals(opt)) {
			title = "已完成基础课程学习学员列表";
		} else if("noPhoto".equals(opt)) {
			title = "未上传照片学员列表";
		}
		this.getGridConfig().setTitle(this.getText(title));

		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		String priEntIds = "";
    	String priSql = "";
		
    	if(!us.getUserLoginType().equals("3")) {
	    	for(PeEnterprise pe : us.getPriEnterprises()) {
				priEntIds += "'"+pe.getId()+"',";
			}
			if(priEntIds.endsWith(",")) {
				priEntIds = priEntIds.substring(0, priEntIds.length()-1);
			}
			
			priSql = " where id in ("+priEntIds+")";
		}

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "stu_name", true);
		this.getGridConfig().addColumn(this.getText("学号"), "reg_no", true);
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobile_phone", true);
		this.getGridConfig().addColumn(this.getText("固定电话"), "phone", true);
		
		ColumnConfig cc = new ColumnConfig(this.getText("所在企业"), "enterprise_name");
		cc.setComboSQL("select name as id,name from pe_enterprise" + priSql);
		cc.setType("ComboBox");
		this.getGridConfig().addColumn(cc);
		this.getGridConfig().addColumn(this.getText("基础课程完成学时"), "xs", false);
		this.getGridConfig().addColumn(this.getText("作业完成情况"), "hwk", false);
		this.getGridConfig().addColumn(this.getText("自测完成情况"), "ts", false);
		this.getGridConfig().addColumn(this.getText("课程评估情况"), "ass", false);
		this.getGridConfig().addRenderFunction(this.getText("查看详细"), "<a href='/entity/manager/information/studentstudystatus_detail.jsp?student_id=${value}' target='_blank'>查看</a>", "id");
		
		this
		.getGridConfig()
		.addMenuScript(this.getText("返回"),
				"{window.location='/entity/information/peBulletinView_firstInfo.action?batch="+this.getBatch()+"'}");

	}

	public DetachedCriteria initDetachedCriteria() {

		DetachedCriteria criteria = DetachedCriteria.forClass(PeBulletin.class);
		return criteria;
	}
	
	public void setEntityClass() {
		this.entityClass = PeBulletin.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/information/studentStudyStatus";

	}


	public PeBulletin getBean() {
		return (PeBulletin) super.superGetBean();
	}

	public void setBean(PeBulletin bean) {
		super.superSetBean(bean);
	}

	public String abstractList() {
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it = map1.entrySet().iterator();
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		String priEntIds = "";
    	String priSql = "";
    	String priSql1 = "";
		
    	if(!us.getUserLoginType().equals("3")) {
	    	for(PeEnterprise pe : us.getPriEnterprises()) {
				priEntIds += "'"+pe.getId()+"',";
			}
			if(priEntIds.endsWith(",")) {
				priEntIds = priEntIds.substring(0, priEntIds.length()-1);
			}
			
			priSql = " and ps.erji_id in ("+priEntIds+")";
			priSql1 = " and ps.fk_enterprise_id in ("+priEntIds+")";
		}
		
		int k = 0;
		int n = 0;
		String tempsql = "";
		String sql_con="";
		
		tempsql="select student_id as id ,name as name,reg_no,mobile_phone,phone,erji_name as enterprise_name,(to_char(finished_ctime,'990.9')||'/'||total_ctime) as xs,"+
		" (finished_hw||'/'||total_hw ) as hwk,(finished_test||'/'||total_test) as ts,(finished_assess||'/'||total_assess) as ass "+
		" from stat_study_summary ps where ps.batch_id='"+this.getBatch()+"'"+priSql;
		if("noLogin".equals(opt)) {
			tempsql = "select ps.id,ps.true_name as name,ps.reg_no,ps.mobile_phone,ps.phone,e.name as enterprise_name,'' as xs,'' as hw,'' as ts,'' as ass  from pe_bzz_student ps,sso_user u,pe_enterprise e where ps.fk_sso_user_id=u.id \n"
				+ "and  (u.login_num=0 or u.login_num is null)" + " and ps.fk_batch_id='"+this.getBatch()+"' \n"
				+" and u.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and e.id=ps.fk_enterprise_id"+priSql1;
			
		} else if("lssFFPc".equals(opt)) {
			sql_con=" and percent<25 ";
			tempsql+=sql_con;
		} else if("lssFGPcc".equals(opt)) {
			sql_con=" and percent<50 and percent>=25 ";
			tempsql+=sql_con;
		} else if("lssFGPccc".equals(opt)) {
			sql_con=" and percent<80 and percent>=50 ";
			tempsql+=sql_con;
		} else if("grtFPc".equals(opt)) {
			sql_con=" and percent<100 and percent>=80 ";
			tempsql+=sql_con;
		} else if("completeJc".equals(opt)) {
			sql_con=" and percent=100 ";
			tempsql+=sql_con;
		} else if("noPhoto".equals(opt)) {
			sql_con=" and photo is null  ";
			tempsql+=sql_con;
			tempsql=tempsql+" union "+
					"select ps.id,ps.true_name as name,ps.reg_no,ps.mobile_phone,ps.phone,e.name as enterprise_name,'' as xs,'' as hw,'' as ts,'' as ass  from pe_bzz_student ps,sso_user u,pe_enterprise e where ps.fk_sso_user_id=u.id \n"
				+ "and  (u.login_num=0 or u.login_num is null) and ps.photo is null " + " and ps.fk_batch_id='"+this.getBatch()+"' \n"
				+" and u.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and e.id=ps.fk_enterprise_id"+priSql1;
		}
		//System.out.println("tempsql:"+tempsql);
		
		StringBuffer buffer = new StringBuffer(tempsql);
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
			if (name.startsWith("search__")) {
				n++;
				String value = ((String[]) entry.getValue())[0];
				if (value == "" || "".equals(value)) {
					k++;
				} else {
					name = name.substring(8);
					
					buffer.append(" and " + name + " like '%" + value
								+ "%'");
					
				}
			}
		}
		
		buffer.append("  order by ").append(this.getSort()).append("  ").append(this.getDir());
		

		String js = null;
		if (k - n == 0 ? true : false) {
			js = super.abstractList();
		} else {
			initGrid();
			Page page = null;
			String sql = buffer.toString();
			try {
				page = this.getGeneralService().getByPageSQL(sql,
						Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
				List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(),
						this.getGridConfig().getListColumnConfig());
				Map map = new HashMap();
				if (page != null) {
					map.put("totalCount", page.getTotalCount());
					map.put("models", jsonObjects);
				}
				this.setJsonString(JsonUtil.toJSONString(map));
				JsonUtil.setDateformat("yyyy-MM-dd");
				js = this.json();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return js;
	}

	public Page list() {
		Page page = null;
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "";
		String priEntIds = "";
    	String priSql = "";
    	String priSql1 = "";
		
    	if(!userSession.getUserLoginType().equals("3")) {
	    	for(PeEnterprise pe : userSession.getPriEnterprises()) {
				priEntIds += "'"+pe.getId()+"',";
			}
			if(priEntIds.endsWith(",")) {
				priEntIds = priEntIds.substring(0, priEntIds.length()-1);
			}
			
			priSql = " and ps.erji_id in ("+priEntIds+")";
			priSql1 = " and ps.fk_enterprise_id in ("+priEntIds+")";
		}
    	
    	String sql_con="";
		
    	//需要再增加字段batch_id,photo,login_num
    	sql="select student_id as id ,name as stu_name,reg_no,mobile_phone,phone,erji_name as enterprise_name,(to_char(finished_ctime,'990.9')||'/'||total_ctime) as xs,"+
    		" (finished_hw||'/'||total_hw ) as hwk,(finished_test||'/'||total_test) as ts,(finished_assess||'/'||total_assess) as ass "+
    		" from stat_study_summary ps where ps.batch_id='"+this.getBatch()+"'"+priSql;
    	if("noLogin".equals(opt)) {
    		sql = "select ps.id,ps.true_name as name,ps.reg_no,ps.mobile_phone,ps.phone,e.name as enterprise_name,'' as xs,'' as hw,'' as ts,'' as ass  from pe_bzz_student ps,sso_user u,pe_enterprise e where ps.fk_sso_user_id=u.id \n"
				+ "and  (u.login_num=0 or u.login_num is null)" + " and ps.fk_batch_id='"+this.getBatch()+"' \n"
				+" and u.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and e.id=ps.fk_enterprise_id"+priSql1;
    		
		} else if("lssFFPc".equals(opt)) {
			sql_con=" and percent<25 ";
			sql+=sql_con;
		} else if("lssFGPcc".equals(opt)) {
			sql_con=" and percent<50 and percent>=25 ";
			sql+=sql_con;
		} else if("lssFGPccc".equals(opt)) {
			sql_con=" and percent<80 and percent>=50 ";
			sql+=sql_con;
		} else if("grtFPc".equals(opt)) {
			sql_con=" and percent<100 and percent>=80 ";
			sql+=sql_con;
		} else if("completeJc".equals(opt)) {
			sql_con=" and percent=100 ";
			sql+=sql_con;
		} else if("noPhoto".equals(opt)) {
			sql_con=" and photo is null  ";
			sql+=sql_con;
			sql=sql+" union "+
					"select ps.id,ps.true_name as name,ps.reg_no,ps.mobile_phone,ps.phone,e.name as enterprise_name,'' as xs,'' as hw,'' as ts,'' as ass  from pe_bzz_student ps,sso_user u,pe_enterprise e where ps.fk_sso_user_id=u.id \n"
				+ "and  (u.login_num=0 or u.login_num is null) and ps.photo is null " + " and ps.fk_batch_id='"+this.getBatch()+"' \n"
				+" and u.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and e.id=ps.fk_enterprise_id"+priSql1;
		}
		
//		System.out.println(sql);
		try {
			StringBuffer sb = new StringBuffer(sql);
			this.setSqlCondition(sb);
			
			page = this.getGeneralService().getByPageSQL(sb.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	public Page list2() {
		Page page = null;
//		UserSession userSession = (UserSession) ActionContext.getContext()
//				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "";
		String priEntIds = "";
    	String priSql = "";
		
//    	if(!userSession.getUserLoginType().equals("3")) {
//	    	for(PeEnterprise pe : userSession.getPriEnterprises()) {
//				priEntIds += "'"+pe.getId()+"',";
//			}
//			if(priEntIds.endsWith(",")) {
//				priEntIds = priEntIds.substring(0, priEntIds.length()-1);
//			}
//			
//			priSql = " and ps.fk_enterprise_id in ("+priEntIds+")";
//		}
//		
    	
    	
    	//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
		String sql_training=" training_course_student tcs ";
		if(this.getBatch().equals("ff8080812824ae6f012824b0a89e0008")){
			sql_training="("+
			"select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id not in\n" +
			"('ff8080812910e7e601291150ddc70419','ff8080812bf5c39a012bf6a1bab80820','ff8080812910e7e601291150ddc70413','ff8080812bf5c39a012bf6a1baba0821')\n" + 
			"union\n" + 
			"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
			"select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812910e7e601291150ddc70419' )a,\n" + 
			"(select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812bf5c39a012bf6a1bab80820')b\n" + 
			"where a.student_id=b.student_id\n" + 
			"union\n" + 
			"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
			"select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812910e7e601291150ddc70413' )a,\n" + 
			"(select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812bf5c39a012bf6a1baba0821')b\n" + 
			"where a.student_id=b.student_id"+
			") tcs ";
		}
		String studentSql = "select stu_id,sso_id from (select stu_id,sso_id, nvl(sum(mtime), 0) / nvl(sum(ttime), 0) as percent\n" + 
		"          from (select ps.id as stu_id,u.id as sso_id,btc.time * (tcs.percent / 100) as mtime,btc.time as ttime\n" + 
		"                  from "+sql_training+",pr_bzz_tch_opencourse bto,pe_bzz_tch_course btc,pe_bzz_student ps,sso_user u,pe_enterprise pe\n" + 
		"                 where tcs.course_id = bto.id and bto.fk_course_id = btc.id and bto.flag_course_type = '402880f32200c249012200c780c40001' and tcs.student_id = ps.fk_sso_user_id\n" + 
		"                 and ps.fk_sso_user_id = u.id and ps.fk_batch_id='"+this.getBatch()+"'" + priSql + " and u.flag_isvalid = '2' and u.login_num > 0 and ps.fk_enterprise_id=pe.id\n" + 
		"                 and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') group by stu_id,sso_id) where percent < 0.25" ;
		
		
		
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(studentSql);
		StringBuilder sb = new StringBuilder(",'");
		StringBuilder sbsso = new StringBuilder(",'");
		int i = 0;
		List<String> sl = new ArrayList();
		List<String> slsso = new ArrayList();
		try {
			while (rs!=null&&rs.next()) {
				i++;
				if(i%800==0) {
					sb.append("'");
					sbsso.append("'");
					String ids = sb.substring(1);
					String ssoIds = sbsso.substring(1);
					sl.add(ids);
					slsso.add(ssoIds);
					sb = new StringBuilder(",'");
					sbsso = new StringBuilder(",'");
				} else {
					sb.append(rs.getString("stu_id")+"','");
					sbsso.append(rs.getString("sso_id")+"','");
				}
				
			}
		} catch(Exception e) {e.printStackTrace();} finally{
			db.close(rs);
		}
		sb.append("'");
		sbsso.append("'");
		
		String ids = sb.substring(1);
		String ssoIds = sbsso.substring(1);
		sl.add(ids);
		slsso.add(ssoIds);
		
		String sql_training2=" training_course_student ";
		if(this.getBatch().equals("ff8080812824ae6f012824b0a89e0008")){
			sql_training2="("+
			"select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821') and ("+getsqlid("s.student_id",slsso)+")" + 
			"union\n" + 
			"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
			"select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812910e7e601291150ddc70419' and ("+getsqlid("s.student_id",slsso)+"))a,\n" + 
			"(select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812bf5c39a012bf6a1bab80820'  and ("+getsqlid("s.student_id",slsso)+"))b\n" + 
			"where a.student_id=b.student_id \n" + 
			"union\n" + 
			"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
			"select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812910e7e601291150ddc70413'  and ("+getsqlid("s.student_id",slsso)+"))a,\n" + 
			"(select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812bf5c39a012bf6a1baba0821' and ("+getsqlid("s.student_id",slsso)+"))b\n" + 
			"where a.student_id=b.student_id"+
			")  ";
		}
		
		String sql_hk="";
		String sql_test="";
		 if(this.getBatch().equals("ff8080812253f04f0122540a58000004")||this.getBatch().equals("ff8080812824ae6f012824b0a89e0008")){
			 sql_hk = " and thi.batch_id is null ";
			 sql_test = " and ci.fk_batch_id is null ";
			}
		else{
				sql_hk = " and thi.batch_id='"+this.getBatch()+"'";
				sql_test = " and ci.fk_batch_id='"+this.getBatch()+"' ";
			}
		
		 String timesql =  "select \n" +
		 "       ts.student_id as sso_id,\n" + 
		 "       sum((ts.percent * tc.time / 100)) || '/' || sum(tc.time) as time\n" + 
		 "  from "+sql_training2+" ts,\n" + 
		 "       pe_bzz_tch_course       tc,\n" + 
		 "       pr_bzz_tch_opencourse   pto\n" + 
		 " where ts.course_id = pto.id\n" + 
		 "   and pto.fk_course_id = tc.id\n" + 
		 "   and ("+getsqlid("ts.student_id",slsso)+")\n" + 
		 " group by ts.student_id";
		//TODO 
		 String hwsql = 
			 "select sso_id, stu_id,  sum(nvl2(b.id, 1, 0))||'/'||sum(1) as hw "+
			 "  from (select thi.id             as hm_id,                            "+
			 "               pbs.fk_sso_user_id as sso_id,                           "+
			 "               pbs.id             as stu_id                            "+
			 "          from test_homeworkpaper_info thi,                            "+
			 "               "+sql_training2+" tcs,                            "+
			 "               pe_bzz_student          pbs,                            "+
			 "               pr_bzz_tch_opencourse   pbto                            "+
			 "         where tcs.course_id = pbto.id                                 "+
			 "           and pbto.fk_course_id = thi.group_id                        "+
			 "           and ("+getsqlid("pbs.id",sl)+")                                          "+
			 "           and tcs.student_id = pbs.fk_sso_user_id) a,                            "+
			 "       (select testpaper_id, t_user_id, id                             "+
			 "          from test_homeworkpaper_history                              "+
			 "         where ("+getsqlid("t_user_id",sl)+")) b                                    "+
			 " where a.hm_id = b.testpaper_id(+)                                     "+
			 "   and a.stu_id = b.t_user_id(+)	group by sso_id, stu_id									";
		 //TODO 
		 String tpsql = 
			 "select sso_id, stu_id, sum(nvl2(b.id, 1, 0))||'/'||sum(1) as tp \n" +
			 "  from (select thi.id             as hm_id,\n" + 
			 "               pbs.fk_sso_user_id as sso_id,\n" + 
			 "               pbs.id             as stu_id\n" + 
			 "          from test_testpaper_info     thi,\n" + 
			 "               onlinetest_course_info  ci,\n" + 
			 "               onlinetest_course_paper cp,\n" + 
			 "               "+sql_training2+" tcs,\n" + 
			 "               pe_bzz_student          pbs,\n" + 
			 "               pr_bzz_tch_opencourse   pbto\n" + 
			 "         where tcs.course_id = pbto.id\n" + 
			 "           and pbto.fk_course_id = thi.group_id\n" + 
			 "           and ("+getsqlid("pbs.id",sl)+")\n" + 
			 "           and tcs.student_id = pbs.fk_sso_user_id\n" + 
			 "           and thi.id = cp.paper_id\n" + 
			 "           and cp.testcourse_id = ci.id\n" + 
			 "           and ci.group_id = pbto.fk_course_id) a,\n" + 
			 "       test_testpaper_history b\n" + 
			 " where a.hm_id = b.testpaper_id(+)\n" + 
			 "   and a.stu_id = b.t_user_id(+)\n" + 
			 " group by sso_id, stu_id";
		//TODO 
		String asssql = 
			"select a.sso_id, sum(done) || '/' || sum(1) as assess\n" +
			"  from (select distinct a.student_id as sso_id,\n" + 
			"                        a.course_id,\n" + 
			"                        nvl2(b.fk_course_id, 1, 0) as done\n" + 
			"          from "+sql_training2+" a, pe_bzz_assess b\n" + 
			"         where a.course_id = b.fk_course_id(+)\n" + 
			"           and a.student_id = b.fk_student_id(+)\n" + 
			"           and  ("+getsqlid("a.student_id",slsso)+")) a\n" + 
			" group by a.sso_id";
		String stusql = "select a.fk_sso_user_id as sso_id,a.name,a.reg_no,a.mobile_phone,a.phone,b.name as ename from pe_bzz_student a,pe_enterprise b where a.fk_enterprise_id = b.id and ("+getsqlid("a.fk_sso_user_id",slsso)+")";
		String allsql = "select e.sso_id,e.name,e.reg_no,e.mobile_phone,e.phone,e.ename,a.time,b.hw,c.tp,d.assess from ("+timesql+") a, ("+hwsql+") b, ("+tpsql+") c,("+asssql+") d,("+stusql+") e where a.sso_id=b.sso_id and b.sso_id=c.sso_id and c.sso_id=d.sso_id and e.sso_id=d.sso_id";
//		System.out.println(timesql);
//		System.out.println("-----------------------------------");
//		System.out.println(hwsql);
//		System.out.println("-----------------------------------");
//		System.out.println(tpsql);
//		System.out.println("-----------------------------------");
//		System.out.println(asssql);
//		System.out.println("-----------------------------------");
//		System.out.println(stusql);
//		System.out.println("-----------------------------------");
//		System.out.println(allsql);
		try {
			StringBuffer sb1 = new StringBuffer(sql);
			this.setSqlCondition(sb1);
			
			page = this.getGeneralService().getByPageSQL(sb1.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public static void main(String[] args) {
		StudentStudyStatusAction s = new StudentStudyStatusAction();
		s.setBatch("ff8080812824ae6f012824b0a89e0008");
		s.list2();
	}
	
	public String getsqlid (String cname,List<String> ids) {
		String sql = cname + " in ('')";
		for(String id : ids) {
			sql +=  " or " + cname + " in ("+id+")";
		}
		return sql;
	}
}
