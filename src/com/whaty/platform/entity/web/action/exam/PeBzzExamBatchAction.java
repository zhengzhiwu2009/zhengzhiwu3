package com.whaty.platform.entity.web.action.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeInfoNewsType;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeBzzExamBatchAction extends MyBaseAction<PeBzzExamBatch>{
	
	private PeBzzExamBatch peBzzExamBatch;
	
	private String exam_explain;
	
	private String exam_notice;
	
	private String late_explain;
	
	private String answer_explain;
	
	private String id;
	
	private String stats;
	
	private String tag="";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	@Override
	public void initGrid() {
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getUserLoginType().equals("3")&& !this.getTag().equals("make")){
			this.getGridConfig().setCapability(true, true, true);
			this.getGridConfig().addMenuFunction("设置为当前考试批次", "batchSelected", "1");
			this.getGridConfig().setTitle("考试批次");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("名称"), "name");
			this.getGridConfig().addColumn(this.getText("报名开始时间"), "startEntryDate",false);
			this.getGridConfig().addColumn(this.getText("报名结束时间"), "endEntryDate",false);
			this.getGridConfig().addColumn(this.getText("申请缓考开始时间"), "startDelayDate",false);
			this.getGridConfig().addColumn(this.getText("申请缓考结束时间"), "endDelayDate",false);
			this.getGridConfig().addColumn(this.getText("补考申请开始时间"),"againStartTime",false,true,true,"Date",false,50);
			this.getGridConfig().addColumn(this.getText("补考申请结束时间"),"againEndTime",false,true,true,"Date",false,50);
			this.getGridConfig().addColumn(this.getText("考试开始时间"), "startExamDate",false);
			this.getGridConfig().addColumn(this.getText("考试结束时间"), "ednExamDate",false);
			this.getGridConfig().addColumn(this.getText("所在学期"),"peBzzBatch.name",true,true,true,"TextField",false,50);
			//this.getGridConfig().addColumn(this.getText("报名条件"),"condition",false,true,false,"TextField");
			this.getGridConfig().addColumn(this.getText("状态"),"enumConstByFlagExamBatch.name",true,false,true,"TextField");
			this.getGridConfig().addColumn(this.getText("基础课完成进度（%）"), "time", false, true, true, Const.timeScale_for_extjs);
			this.getGridConfig().addColumn(this.getText("作业是否必须完成"),"enumConstByFlagExamConditionHomework.name",false,true,true,"TextField");
			this.getGridConfig().addColumn(this.getText("自测是否必须完成"),"enumConstByFlagExamConditionTest.name",false,true,true,"TextField");
			this.getGridConfig().addColumn(this.getText("课程评估是否必须完成"),"enumConstByFlagExamConditionEvaluate.name",false,true,true,"TextField");
		
			this.getGridConfig().addRenderFunction(this.getText("考试说明"),
					"<a href=\"peBzzExamBatch_updateExamExplain.action?bean.id=${value}\">添加/修改</a>",
					"id");
			this.getGridConfig().addRenderFunction(this.getText("考试通知"),
					"<a href=\"peBzzExamBatch_updateExamNotice.action?bean.id=${value}\">添加/修改</a>",
					"id");
			this.getGridConfig().addRenderFunction(this.getText("缓考说明"),
					"<a href=\"peBzzExamBatch_updateLateExplain.action?bean.id=${value}\">添加/修改</a>",
					"id");
			this.getGridConfig().addRenderFunction(this.getText("答疑公告"),
					"<a href=\"peBzzExamBatch_updateAnswerExplain.action?bean.id=${value}\">添加/修改</a>",
					"id");
			this.getGridConfig().addRenderFunction(this.getText("操作"), "<a href=\"peBzzExamScore_Confirm.action?batchid=${value}&status=1\">批量审核</a>|" +
					"<a href=\"peBzzExamScore_Confirm.action?batchid=${value}&status=0\">取消审核</a>","id");
		}
		else if(us.getUserLoginType().equals("3")&& this.getTag().equals("make"))
		{
			this.getGridConfig().addMenuFunction(this.getText("生成自测成绩"),"/entity/exam/peBzzExamBatch_makeTestScore.action?",true, false);
			this.getGridConfig().addMenuFunction(this.getText("生成总评成绩"),"/entity/exam/peBzzExamBatch_makeTotalScore.action?",true, false);
			this.getGridConfig().addMenuFunction(this.getText("生成考核等级"),"/entity/exam/peBzzExamBatch_makeTotalGrade.action?",true, false);
			this.getGridConfig().addMenuFunction(this.getText("考试成绩发布状态修改"),"/entity/exam/peBzzExamBatch_releaseExamScore.action?",true, false);
			this.getGridConfig().addMenuFunction(this.getText("自测成绩发布状态修改"),"/entity/exam/peBzzExamBatch_releaseTestScore.action?",true, false);
			this.getGridConfig().addMenuFunction(this.getText("总评成绩发布状态修改"),"/entity/exam/peBzzExamBatch_releaseTotalScore.action?",true, false);
			this.getGridConfig().addMenuFunction(this.getText("考核等级发布状态修改"),"/entity/exam/peBzzExamBatch_releaseTotalGrade.action?",true, false);
			
			
			this.getGridConfig().setCapability(false,false,true);
			
			this.getGridConfig().setTitle("考试批次");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("名称"), "name");
			this.getGridConfig().addColumn(this.getText("所在学期"),"peBzzBatch.name",true,true,true,"TextField",false,50);
			this.getGridConfig().addColumn(this.getText("状态"),"enumConstByFlagExamBatch.name",true,false,true,"TextField");
			this.getGridConfig().addColumn(this.getText("考试成绩所占比例（%）"), "examScale", false, true, true, Const.credit_for_extjs);
			this.getGridConfig().addColumn(this.getText("自测成绩所占比例（%）"), "testScale", false, true, true, Const.credit_for_extjs);
			
			
		}
		if(!us.getUserLoginType().equals("3")&& !this.getTag().equals("make"))
		{
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().setTitle("考试批次");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("名称"), "name");
			this.getGridConfig().addColumn(this.getText("报名开始时间"), "startEntryDate",false);
			this.getGridConfig().addColumn(this.getText("报名结束时间"), "endEntryDate",false);
			this.getGridConfig().addColumn(this.getText("申请缓考开始时间"), "startDelayDate",false);
			this.getGridConfig().addColumn(this.getText("申请缓考结束时间"), "endDelayDate",false);
			this.getGridConfig().addColumn(this.getText("补考申请开始时间"),"againStartTime",false,true,true,"Date",false,50);
			this.getGridConfig().addColumn(this.getText("补考申请结束时间"),"againEndTime",false,true,true,"Date",false,50);
			this.getGridConfig().addColumn(this.getText("考试开始时间"), "startExamDate",false);
			this.getGridConfig().addColumn(this.getText("考试结束时间"), "ednExamDate",false);
			this.getGridConfig().addColumn(this.getText("所在学期"),"peBzzBatch.name",true,true,true,"TextField",false,50);
			//this.getGridConfig().addColumn(this.getText("报名条件"),"condition",false,true,false,"TextField");
			this.getGridConfig().addColumn(this.getText("状态"),"enumConstByFlagExamBatch.name",true,false,true,"TextField");
			this.getGridConfig().addColumn(this.getText("基础课完成进度（%）"), "time", false, true, true, Const.timeScale_for_extjs);
			this.getGridConfig().addColumn(this.getText("作业是否必须完成"),"enumConstByFlagExamConditionHomework.name",false,true,true,"TextField");
			this.getGridConfig().addColumn(this.getText("自测是否必须完成"),"enumConstByFlagExamConditionTest.name",false,true,true,"TextField");
			this.getGridConfig().addColumn(this.getText("课程评估是否必须完成"),"enumConstByFlagExamConditionEvaluate.name",false,true,true,"TextField");
			
			this.getGridConfig().addRenderFunction(this.getText("考试说明"),
					"<a href=\"peBzzExamBatch_updateExamExplain.action?bean.id=${value}\">查看</a>",
					"id");
			this.getGridConfig().addRenderFunction(this.getText("考试通知"),
					"<a href=\"peBzzExamBatch_updateExamNotice.action?bean.id=${value}\">查看</a>",
					"id");
			this.getGridConfig().addRenderFunction(this.getText("答疑公告"),
					"<a href=\"peBzzExamBatch_updateAnswerExplain.action?bean.id=${value}\">查看</a>",
					"id");
		}
		
		
		
	}

	public void setEntityClass() {
		this.entityClass = PeBzzExamBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/exam/peBzzExamBatch";
	}

	public void setBean(PeBzzExamBatch instance) {
		super.superSetBean(instance);
	}

	public PeBzzExamBatch getBean() {
		return super.superGetBean();
	}
	
	//MenuFunction  方法
	//生成自测成绩
	public String makeTestScore() {
		
		//该批次报名学生信息(所有审核通过学生)
		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		DetachedCriteria prdc = score.createCriteria("peBzzExamBatch","peBzzExamBatch");
		prdc.add(Restrictions.eq("id", this.getIds().split(",")[0]));
		score.createCriteria("enumConstByFlagExamScoreStatus", "enumConstByFlagExamScoreStatus", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamScoreStatus.id", "402880a9aaadc115061dadfcf26b0003"));
		List<PeBzzExamScore> studentList = null;
		try {
			studentList = this.getGeneralService().getList(score);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (PeBzzExamScore peBzzExamScore : studentList) {
           String sql="";
           //针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
           String sql_training=" training_course_student tcs,";
           if(peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId()!=null && (peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId().equals("ff8080812253f04f0122540a58000004")||peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId().equals("ff8080812824ae6f012824b0a89e0008")))
        	  {
        	   sql_training="("+
				"select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s ,pe_bzz_student bs,pr_bzz_tch_opencourse pbto where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"+peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId()+"'\n"+
				" and (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821')" +
				" and pbto.id=s.course_id and pbto.flag_course_type='402880f32200c249012200c780c40001' and pbto.fk_batch_id=bs.fk_batch_id "+
				"union\n" + 
				"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id," +
				"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
				"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
				"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
				"select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"+peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId()+"' and s.course_id='ff8080812910e7e601291150ddc70419' )a,\n" + 
				"(select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s ,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"+peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId()+"' and s.course_id='ff8080812bf5c39a012bf6a1bab80820')b\n" + 
				"where a.student_id=b.student_id\n" + 
				"union\n" + 
				"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id," +
				"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
				"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
				"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
				"select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s ,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"+peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId()+"' and s.course_id='ff8080812910e7e601291150ddc70413' )a,\n" + 
				"(select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"+peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId()+"' and s.course_id='ff8080812bf5c39a012bf6a1baba0821')b\n" + 
				"where a.student_id=b.student_id"+
				") tcs, ";
        	   
        	   sql="select to_char(avg(score),990.9) as score\n" +
        	   "  from (select b.id, nvl(a.score, 0) as score\n" + 
        	   "          from (select tti.id, tth.score\n" + 
        	   "                  from test_testpaper_info    tti,\n" + 
        	   "                       test_testpaper_history tth,\n" + 
        	   "                       pe_bzz_student         ps,\n" + 
        	   "                       pr_bzz_tch_opencourse  co,\n" + 
        	   "                       onlinetest_course_info oci,\n" + 
        	   "                       onlinetest_course_paper ocp,\n" + 
        	   "                       pe_bzz_examscore       pbes\n" + 
        	   "                 where pbes.student_id = ps.id\n" + 
        	   "                   and tti.group_id = co.fk_course_id\n" + 
        	   "                   and oci.group_id = co.fk_course_id and ocp.testcourse_id=oci.id and ocp.paper_id=tti.id\n" + 
        	   "                   and tth.t_user_id = pbes.student_id \n" + 
        	   "                   and ps.fk_batch_id = co.fk_batch_id\n" + 
        	   "                   and co.flag_course_type =\n" + 
        	   "                       '402880f32200c249012200c780c40001'\n" + 
        	   "                   and pbes.id = '"+peBzzExamScore.getId()+"'\n" + 
        	   "                   and tth.testpaper_id = tti.id) a,\n" + 
        	   "               (select tti.id\n" + 
        	   "                  from "+sql_training+" test_testpaper_info    tti,\n" + 
        	   "                       pe_bzz_student         ps,\n" + 
        	   "                       pr_bzz_tch_opencourse  co,\n" + 
        	   "                       onlinetest_course_info oci,\n" + 
        	   "                       onlinetest_course_paper ocp,\n" + 
        	   "                       pe_bzz_examscore       pbes\n" + 
        	   "                 where tcs.course_id = co.id \n" +
        	   "				   and tcs.student_id = ps.fk_sso_user_id \n" +
        	   "				   and pbes.student_id = ps.id\n" + 
        	   "                   and tti.group_id = co.fk_course_id\n" + 
        	   "                   and oci.group_id = co.fk_course_id and ocp.testcourse_id=oci.id and ocp.paper_id=tti.id\n" + 
        	   "                   and ps.fk_batch_id = co.fk_batch_id\n" + 
        	   "                   and co.flag_course_type =\n" + 
        	   "                       '402880f32200c249012200c780c40001'\n" + 
        	   "                   and pbes.id = '"+peBzzExamScore.getId()+"' and oci.fk_batch_id is null) b\n" + 
        	   "         where a.id(+) = b.id)";
        	  }
           else{
        	   sql="select to_char(avg(score),990.9) as score\n" +
        	   "  from (select b.id, nvl(a.score, 0) as score\n" + 
        	   "          from (select tti.id, tth.score\n" + 
        	   "                  from test_testpaper_info    tti,\n" + 
        	   "                       test_testpaper_history tth,\n" + 
        	   "                       pe_bzz_student         ps,\n" + 
        	   "                       pr_bzz_tch_opencourse  co,\n" + 
        	   "                       onlinetest_course_info oci,\n" + 
        	   "                       onlinetest_course_paper ocp,\n" + 
        	   "                       pe_bzz_examscore       pbes\n" + 
        	   "                 where pbes.student_id = ps.id\n" + 
        	   "                   and tti.group_id = co.fk_course_id\n" + 
        	   "                   and oci.group_id = co.fk_course_id and ocp.testcourse_id=oci.id and ocp.paper_id=tti.id\n" + 
        	   "                   and tth.t_user_id = pbes.student_id \n" + 
        	   "                   and ps.fk_batch_id = co.fk_batch_id\n" + 
        	   "                   and co.flag_course_type =\n" + 
        	   "                       '402880f32200c249012200c780c40001'\n" + 
        	   "                   and pbes.id = '"+peBzzExamScore.getId()+"'\n" + 
        	   "                   and tth.testpaper_id = tti.id) a,\n" + 
        	   "               (select tti.id\n" + 
        	   "                  from test_testpaper_info    tti,\n" + 
        	   "                       pe_bzz_student         ps,\n" + 
        	   "                       pr_bzz_tch_opencourse  co,\n" + 
        	   "                       onlinetest_course_info oci,\n" + 
        	   "                       onlinetest_course_paper ocp,\n" + 
        	   "                       pe_bzz_examscore       pbes\n" + 
        	   "                 where pbes.student_id = ps.id\n" + 
        	   "                   and tti.group_id = co.fk_course_id\n" + 
        	   "                   and oci.group_id = co.fk_course_id and ocp.testcourse_id=oci.id and ocp.paper_id=tti.id\n" + 
        	   "                   and ps.fk_batch_id = co.fk_batch_id\n" + 
        	   "                   and co.flag_course_type =\n" + 
        	   "                       '402880f32200c249012200c780c40001'\n" + 
        	   "                   and pbes.id = '"+peBzzExamScore.getId()+"' and oci.fk_batch_id =ps.fk_batch_id) b\n" + 
        	   "         where a.id(+) = b.id)";
           }
           
             List scoreList=null;
	  		try {
	  			scoreList=this.getGeneralService().getBySQL(sql);
			} catch (EntityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String update_sql="update pe_bzz_examscore t set t.test_score='"+scoreList.get(0)+"' where t.id='"+peBzzExamScore.getId()+"'";
			try {
				this.getGeneralService().executeBySQL(update_sql);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
				
		}

		return "make_ok";
	}
	//生成总评成绩
	public String makeTotalScore() throws EntityException {
		
		//该考试批次信息
		DetachedCriteria examBatch = DetachedCriteria.forClass(PeBzzExamBatch.class);
		examBatch.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
		examBatch.createCriteria("enumConstByFlagExamConditionHomework", "enumConstByFlagExamConditionHomework");
		examBatch.createCriteria("enumConstByFlagExamConditionTest", "enumConstByFlagExamConditionTest");
		examBatch.createCriteria("enumConstByFlagExamConditionEvaluate", "enumConstByFlagExamConditionEvaluate");
		examBatch.add(Restrictions.eq("id", this.getIds().split(",")[0]));
		PeBzzExamBatch peExamBatch = null;
		try {
			peExamBatch = this.getGeneralService().getExamBatch(examBatch);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Long exam_scale = peExamBatch.getExamScale();
		Long test_scale = peExamBatch.getTestScale();
		
		//该批次报名学生信息(所有审核通过学生)
		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		DetachedCriteria prdc = score.createCriteria("peBzzExamBatch","peBzzExamBatch");
		prdc.add(Restrictions.eq("id", this.getIds().split(",")[0]));
		score.createCriteria("enumConstByFlagExamScoreStatus", "enumConstByFlagExamScoreStatus", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamScoreStatus.id", "402880a9aaadc115061dadfcf26b0003"));
		List<PeBzzExamScore> studentList = null;
		studentList = this.getGeneralService().getList(score);
		
		for (PeBzzExamScore peBzzExamScore : studentList) {
			String exam_score = peBzzExamScore.getScore();
			String test_score = peBzzExamScore.getTest_score();
			if(exam_score == null || exam_score.equals("null") || exam_score.equals(""))
			{
				exam_score="0";
			}
			if(test_score == null || test_score.equals("null") || test_score.equals(""))
			{
				test_score="0";
			}
			Double total_score=(Double.parseDouble(exam_score)*exam_scale+Double.parseDouble(test_score)*test_scale)/100;
			String totalScore = Double.toString(total_score);
			String update_sql="update pe_bzz_examscore t set t.total_score=replace(to_char("+totalScore+",'999'),' ','') where t.id='"+peBzzExamScore.getId()+"'";
			try {
				this.getGeneralService().executeBySQL(update_sql);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
				
		}
		return "make_ok";
	}
	//生成考核等级
	public String makeTotalGrade() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)) {
			stats="nostats";
			return "addExam";
		}
		String sql1 = 
			"update pe_bzz_examscore s\n" +
			"   set s.total_grade = '不合格'\n" + 
			" where s.id in (select t.id\n" + 
			"                  from pe_bzz_examscore t\n" + 
			"                 where t.exambatch_id = '"+this.getIds().split(",")[0]+"'\n" + 
			"                   and t.status = '402880a9aaadc115061dadfcf26b0003'\n" + 
			"                   and nvl(to_number(t.total_score), 0) < 60)";
        
		String sql2 = 
			"update pe_bzz_examscore s\n" +
			"   set s.total_grade = '合格'\n" + 
			" where s.id in (select t.id\n" + 
			"                  from pe_bzz_examscore t\n" + 
			"                 where t.exambatch_id = '"+this.getIds().split(",")[0]+"'\n" + 
			"                   and t.status = '402880a9aaadc115061dadfcf26b0003'\n" + 
			"                   and nvl(to_number(t.total_score), 0) >= 60 and nvl(to_number(t.total_score), 0) < 75)";
		
		String sql3 = 
			"update pe_bzz_examscore s\n" +
			"   set s.total_grade = '良好'\n" + 
			" where s.id in (select t.id\n" + 
			"                  from pe_bzz_examscore t\n" + 
			"                 where t.exambatch_id = '"+this.getIds().split(",")[0]+"'\n" + 
			"                   and t.status = '402880a9aaadc115061dadfcf26b0003'\n" + 
			"                   and nvl(to_number(t.total_score), 0) >= 75 and nvl(to_number(t.total_score), 0) < 90)";
		
		String sql4 = 
			"update pe_bzz_examscore s\n" +
			"   set s.total_grade = '优秀'\n" + 
			" where s.id in (select t.id\n" + 
			"                  from pe_bzz_examscore t\n" + 
			"                 where t.exambatch_id = '"+this.getIds().split(",")[0]+"'\n" + 
			"                   and t.status = '402880a9aaadc115061dadfcf26b0003'\n" + 
			"                   and nvl(to_number(t.total_score), 0) >= 90)";
		
		List<String> list = null;
		try {
			this.getGeneralService().executeBySQL(sql1);
			this.getGeneralService().executeBySQL(sql2);
			this.getGeneralService().executeBySQL(sql3);
			this.getGeneralService().executeBySQL(sql4);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		return "make_ok";
	}
	//发布考试成绩
	public String releaseExamScore() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)) {
			stats="nostats";
			return "addExam";
		}
		String flag = "";
		//该批次报名学生信息(所有审核通过学生)
		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		DetachedCriteria prdc = score.createCriteria("peBzzExamBatch","peBzzExamBatch");
		prdc.add(Restrictions.eq("id", this.getIds().split(",")[0]));
		score.createCriteria("enumConstByFlagExamScoreStatus", "enumConstByFlagExamScoreStatus", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamScoreStatus.id", "402880a9aaadc115061dadfcf26b0003"));
		score.createCriteria("enumConstByFlagExamScoreRelease", "enumConstByFlagExamScoreRelease", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamScoreRelease.id", "4028809c2d625bcf011d66fd0dda1006"));//审核通过的学生其考试成绩已经发布状态
		
		List<PeBzzExamScore> studentList = null;
		try {
			studentList = this.getGeneralService().getList(score);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(studentList.size()>0)
		{
			flag="0";
		}
		else
		{
			flag="1";
		}
		
		String sql = 
		"select es.id as id\n" +
		"  from pe_bzz_examscore es\n" + 
		" where es.exambatch_id = '"+this.getIds().split(",")[0]+"'\n" + 
		"   and es.status = '402880a9aaadc115061dadfcf26b0003'";
		
		String updatesql = "update pe_bzz_examscore t\n" +
		"   set t.examscore_release = (select id\n" + 
		"                     from enum_const c\n" + 
		"                    where c.code = '"+flag+"'\n" + 
		"                      and c.namespace = 'FlagExamScoreRelease')\n" + 
		" where t.id in ("+sql+")";
		try {
			this.getGeneralService().executeBySQL(updatesql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return "release";
	}	
	
	//发布自测成绩
	public String  releaseTestScore() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)) {
			stats="nostats";
			return "addExam";
		}
		String flag = "";
		//该批次报名学生信息(所有审核通过学生)
		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		DetachedCriteria prdc = score.createCriteria("peBzzExamBatch","peBzzExamBatch");
		prdc.add(Restrictions.eq("id", this.getIds().split(",")[0]));
		score.createCriteria("enumConstByFlagExamScoreStatus", "enumConstByFlagExamScoreStatus", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamScoreStatus.id", "402880a9aaadc115061dadfcf26b0003"));
		score.createCriteria("enumConstByFlagExamTestScoreRelease", "enumConstByFlagExamTestScoreRelease", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamTestScoreRelease.id", "4028809c2d625bcf011d66fd0dda3006"));//审核通过的学生其自测成绩已经发布状态
		
		List<PeBzzExamScore> studentList = null;
		try {
			studentList = this.getGeneralService().getList(score);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(studentList.size()>0)
		{
			flag="0";
		}
		else
		{
			flag="1";
		}
		
		String sql = 
		"select es.id as id\n" +
		"  from pe_bzz_examscore es\n" + 
		" where es.exambatch_id = '"+this.getIds().split(",")[0]+"'\n" + 
		"   and es.status = '402880a9aaadc115061dadfcf26b0003'";
		
		String updatesql = "update pe_bzz_examscore t\n" +
		"   set t.testscore_release = (select id\n" + 
		"                     from enum_const c\n" + 
		"                    where c.code = '"+flag+"'\n" + 
		"                      and c.namespace = 'FlagExamTestScoreRelease')\n" + 
		" where t.id in ("+sql+")";
		try {
			this.getGeneralService().executeBySQL(updatesql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		return "release";
	}
	//发布总评成绩
	public String  releaseTotalScore() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)) {
			stats="nostats";
			return "addExam";
		}
		String flag = "";
		//该批次报名学生信息(所有审核通过学生)
		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		DetachedCriteria prdc = score.createCriteria("peBzzExamBatch","peBzzExamBatch");
		prdc.add(Restrictions.eq("id", this.getIds().split(",")[0]));
		score.createCriteria("enumConstByFlagExamScoreStatus", "enumConstByFlagExamScoreStatus", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamScoreStatus.id", "402880a9aaadc115061dadfcf26b0003"));
		score.createCriteria("enumConstByFlagExamTotalScoreRelease", "enumConstByFlagExamTotalScoreRelease", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamTotalScoreRelease.id", "4028809c2d625bcf011d66fd0dda5006"));//审核通过的学生其总评成绩已经发布状态
		
		List<PeBzzExamScore> studentList = null;
		try {
			studentList = this.getGeneralService().getList(score);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(studentList.size()>0)
		{
			flag="0";
		}
		else
		{
			flag="1";
		}
		String sql = 
		"select es.id as id\n" +
		"  from pe_bzz_examscore es\n" + 
		" where es.exambatch_id = '"+this.getIds().split(",")[0]+"'\n" + 
		"   and es.status = '402880a9aaadc115061dadfcf26b0003'";
		
		String updatesql = "update pe_bzz_examscore t\n" +
		"   set t.totalscore_release = (select id\n" + 
		"                     from enum_const c\n" + 
		"                    where c.code = '"+flag+"'\n" + 
		"                      and c.namespace = 'FlagExamTotalScoreRelease')\n" + 
		" where t.id in ("+sql+")";
		try {
			this.getGeneralService().executeBySQL(updatesql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		return "release";
	}	
	//发布考核等级
	public String releaseTotalGrade() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)) {
			stats="nostats";
			return "addExam";
		}
		String flag = "";
		//该批次报名学生信息(所有审核通过学生)
		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		DetachedCriteria prdc = score.createCriteria("peBzzExamBatch","peBzzExamBatch");
		prdc.add(Restrictions.eq("id", this.getIds().split(",")[0]));
		score.createCriteria("enumConstByFlagExamScoreStatus", "enumConstByFlagExamScoreStatus", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamScoreStatus.id", "402880a9aaadc115061dadfcf26b0003"));
		score.createCriteria("enumConstByFlagExamTotalGradeRelease", "enumConstByFlagExamTotalGradeRelease", DetachedCriteria.LEFT_JOIN);
		score.add(Restrictions.eq("enumConstByFlagExamTotalGradeRelease.id", "4028809c2d625bcf011d66fd0dda7006"));//审核通过的学生其考核等级已经发布状态
		
		List<PeBzzExamScore> studentList = null;
		try {
			studentList = this.getGeneralService().getList(score);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(studentList.size()>0)
		{
			flag="0";
		}
		else
		{
			flag="1";
		}
		String sql = 
		"select es.id as id\n" +
		"  from pe_bzz_examscore es\n" + 
		" where es.exambatch_id = '"+this.getIds().split(",")[0]+"'\n" + 
		"   and es.status = '402880a9aaadc115061dadfcf26b0003'";
		
		String updatesql = "update pe_bzz_examscore t\n" +
		"   set t.totalgrade_release = (select id\n" + 
		"                     from enum_const c\n" + 
		"                    where c.code = '"+flag+"'\n" + 
		"                      and c.namespace = 'FlagExamTotalGradeRelease')\n" + 
		" where t.id in ("+sql+")";
		try {
			this.getGeneralService().executeBySQL(updatesql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		return "release";
	}	
	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		if (this.getIds() == null || this.getIds().split(",").length > 1) {
			map.put("success", false);
			map.put("info", this.getText("只能操作一条记录!"));
			return map;
		}
//		if (this.getColumn().equals("batchSelected")) {
//			DetachedCriteria detachedCriteria = DetachedCriteria
//					.forClass(PeBzzExamBatch.class);
//			detachedCriteria.add(Restrictions.or(
//					Restrictions.eq("id", this.getIds().split(",")[0]),
//					Restrictions.eq("enumConstByFlagExamBatch.id", "402880a91dadc115011dadfcf26b00aa")));
//			
//			List<PeBzzExamBatch> list = null;
//			try {
//				list = this.getGeneralService().getList(detachedCriteria);
//			} catch (EntityException e1) {
//				e1.printStackTrace();
//			}
//
//			for (PeBzzExamBatch peBzzBatch : list) {
//				if (peBzzBatch.getId().equals(this.getIds().split(",")[0])) {
//					if (peBzzBatch.getEnumConstByFlagExamBatch().getCode() == null
//							|| peBzzBatch.getEnumConstByFlagExamBatch().getCode().equals("0")) {
//						EnumConst enumConstByFlagExamBatch = this.getMyListService().getEnumConstByNamespaceCode("FlagExamBatch", "1");
//						peBzzBatch.setEnumConstByFlagExamBatch(enumConstByFlagExamBatch);
//						try {
//							this.getGeneralService().save(peBzzBatch);
//						} catch (EntityException e) {
//							e.printStackTrace();
//						}
//					}
//				} else {
//					if (peBzzBatch.getEnumConstByFlagExamBatch().getCode().equals("1")) {
//						EnumConst enumConstByFlagExamBatch = this.getMyListService().getEnumConstByNamespaceCode("FlagExamBatch", "0");
//						peBzzBatch.setEnumConstByFlagExamBatch(enumConstByFlagExamBatch);
//						try {
//							this.getGeneralService().save(peBzzBatch);
//						} catch (EntityException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//			map.put("success", true);
//			map.put("info", this.getText("状态已经成功改变"));
//
//			return map;
//		}
		if (this.getColumn().equals("batchSelected")) {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(PeBzzExamBatch.class);
			detachedCriteria.add(Restrictions.eq("id", this.getIds().split(",")[0]));
			List<PeBzzExamBatch> list = null;
			try {
				list = this.getGeneralService().getList(detachedCriteria);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
			boolean bool = false;
			
			if(list.size()!=0){
				try {
					this.getGeneralService().executeBySQL("update pe_bzz_exambatch set selected='402880a91dadc115011dadfcf26b00aa' where id ='"+this.getIds().split(",")[0]+"'");
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					this.getGeneralService().executeBySQL("update pe_bzz_exambatch set selected='402880a91dadc115011dadfcf26b00bb' where id not in('"+this.getIds().split(",")[0]+"')");
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
			map.put("success", true);
			map.put("info", this.getText("状态已经成功改变"));

			return map;
		}
		
	
	public String make(){
		 return "make";
	}
	//add校验
	
	public void checkBeforeAdd() throws EntityException {
		EnumConst enumConstByFlagExamBatch = this.getMyListService().getEnumConstByNamespaceCode("FlagExamBatch", "0");
		this.getBean().setEnumConstByFlagExamBatch(enumConstByFlagExamBatch);
		if (this.getBean().getStartEntryDate().after(this.getBean().getEndEntryDate())) {
			throw new EntityException("报名开始时间不能晚于结束时间");
		}
		if (this.getBean().getStartDelayDate().after(this.getBean().getEndDelayDate())) {
			throw new EntityException("缓考申请开始时间不能晚于结束时间");
		}
		if (this.getBean().getStartExamDate().after(this.getBean().getEdnExamDate())) {
			throw new EntityException("考试开始时间不能晚于结束时间");
		}
		if (this.getBean().getEndEntryDate().after(this.getBean().getStartExamDate())) {
			throw new EntityException("报名结束时间不能晚于考试时间");
		}
		//if (this.getBean().getStartEntryDate().after(this.getBean().getStartDelayDate())) {
			//throw new EntityException("报名开始时间不能晚于缓考申请时间");
		//}
		//if (this.getBean().getEndDelayDate().after(this.getBean().getEndEntryDate())) {
			//throw new EntityException("报名结束时间不能晚于缓考结束时间");
		//}
	}
	
	//update校验
	
	public void checkBeforeUpdate() throws EntityException {
		/*if (this.getBean().getStartEntryDate().after(this.getBean().getEndEntryDate())) {
			throw new EntityException("报名开始时间不能晚于结束时间");
		}
		if (this.getBean().getStartDelayDate().after(this.getBean().getEndDelayDate())) {
			throw new EntityException("缓考申请开始时间不能晚于结束时间");
		}
		if (this.getBean().getStartExamDate().after(this.getBean().getEdnExamDate())) {
			throw new EntityException("考试开始时间不能晚于结束时间");
		}
		if (this.getBean().getEndEntryDate().after(this.getBean().getStartExamDate())) {
			throw new EntityException("报名结束时间不能晚于考试时间");
		}
		//if (this.getBean().getStartEntryDate().after(this.getBean().getStartDelayDate())) {
			//throw new EntityException("报名开始时间不能晚于缓考申请时间");
		//}
		//if (this.getBean().getEndDelayDate().after(this.getBean().getEndEntryDate())) {
			//throw new EntityException("报名结束时间不能晚于缓考结束时间");
		//}*/
		if(this.getBean().getStartEntryDate()!=null)//考试批次列表
		{
			if (this.getBean().getStartEntryDate().after(this.getBean().getEndEntryDate())) {
				throw new EntityException("报名开始时间不能晚于结束时间");
			}
			if (this.getBean().getStartDelayDate().after(this.getBean().getEndDelayDate())) {
				throw new EntityException("缓考申请开始时间不能晚于结束时间");
			}
			if (this.getBean().getStartExamDate().after(this.getBean().getEdnExamDate())) {
				throw new EntityException("考试开始时间不能晚于结束时间");
			}
			if (this.getBean().getEndEntryDate().after(this.getBean().getStartExamDate())) {
				throw new EntityException("报名结束时间不能晚于考试时间");
			}
		}
		else//考试生成成绩页面
		{
			if((this.getBean().getExamScale()+this.getBean().getTestScale())>100)
			{
				throw new EntityException("设置比例总和不能超过100%");
			}
		}
	}
	
	//懒加载
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamBatch.class);
		dc.createCriteria("peBzzBatch", "peBzzBatch", DetachedCriteria.INNER_JOIN);
		dc.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagExamConditionHomework", "enumConstByFlagExamConditionHomework", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagExamConditionTest", "enumConstByFlagExamConditionTest", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagExamConditionEvaluate", "enumConstByFlagExamConditionEvaluate", DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	
	public String updateExamExplain(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)) {
			stats="nostats";
			return "addExam";
		}
		try {
			DetachedCriteria infodc = DetachedCriteria.forClass(PeBzzExamBatch.class);
			infodc.add(Restrictions.eq("id", this.getBean().getId()));
			this.setPeBzzExamBatch((PeBzzExamBatch)this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(userLoginType.equals("3"))
		{
			return "modifyExamExplain";
		}
		else
		{
			return "lookExamExplain";
		}
		
	}
	
	public String modifyExamExplain(){
		
		if(!this.validateToken(this.getFormToken())) {
			stats="formInvildate";
			return "addExam";
		}
		try {
			    PeBzzExamBatch peBzzExamBatch =(PeBzzExamBatch)this.getGeneralService().getById(id);
				peBzzExamBatch.setExam_explain(this.getExam_explain());
				this.getGeneralService().save(peBzzExamBatch);
				stats="modifyok";
			
		} catch (EntityException e) {
			stats="modify";
		}
		return "addExam";
	}
	
	public String updateExamNotice(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)) {
			stats="nostats";
			return "addExam";
		}
		try {
			DetachedCriteria infodc = DetachedCriteria.forClass(PeBzzExamBatch.class);
			infodc.add(Restrictions.eq("id", this.getBean().getId()));
			this.setPeBzzExamBatch((PeBzzExamBatch)this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(userLoginType.equals("3"))
		{
			return "modifyExamNotice";
		}
		else
		{
			return "lookExamNotice";
		}
		
	}
	
	public String modifyExamNotice(){
		
		if(!this.validateToken(this.getFormToken())) {
			stats="formInvildate";
			return "addExam";
		}
		try {
			    PeBzzExamBatch peBzzExamBatch =(PeBzzExamBatch)this.getGeneralService().getById(id);
				peBzzExamBatch.setExam_notice(exam_notice);
				this.getGeneralService().save(peBzzExamBatch);
				stats="modifyok";
			
		} catch (EntityException e) {
			stats="modify";
		}
		return "addExam";
	}
	
	public String updateLateExplain(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)) {
			stats="nostats";
			return "addExam";
		}
		try {
			DetachedCriteria infodc = DetachedCriteria.forClass(PeBzzExamBatch.class);
			infodc.add(Restrictions.eq("id", this.getBean().getId()));
			this.setPeBzzExamBatch((PeBzzExamBatch)this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(userLoginType.equals("3"))
		{
			return "modifyLateExplain";
		}
		else
		{
			return "lookLateExplain";
		}
		
	}
	
	//答疑公告
	public String updateAnswerExplain(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)) {
			stats="nostats";
			return "addExam";
		}
		try {
			DetachedCriteria infodc = DetachedCriteria.forClass(PeBzzExamBatch.class);
			infodc.add(Restrictions.eq("id", this.getBean().getId()));
			this.setPeBzzExamBatch((PeBzzExamBatch)this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(userLoginType.equals("3"))
		{
			return "modifyAnswerExplain";
		}
		else
		{
			return "lookAnswerExplain";
		}
		
	}
	
	public String modifyLateExplain(){
		
		if(!this.validateToken(this.getFormToken())) {
			stats="formInvildate";
			return "addExam";
		}
		try {
			    PeBzzExamBatch peBzzExamBatch =(PeBzzExamBatch)this.getGeneralService().getById(id);
				peBzzExamBatch.setLate_explain(late_explain);
				this.getGeneralService().save(peBzzExamBatch);
				stats="modifyok";
			
		} catch (EntityException e) {
			stats="modify";
		}
		return "addExam";
	}
    
	public String modifyAnswerExplain(){
		
		if(!this.validateToken(this.getFormToken())) {
			stats="formInvildate";
			return "addExam";
		}
		try {
			    PeBzzExamBatch peBzzExamBatch =(PeBzzExamBatch)this.getGeneralService().getById(id);
				peBzzExamBatch.setAnswer_explain(answer_explain);
				this.getGeneralService().save(peBzzExamBatch);
				stats="modifyok";
			
		} catch (EntityException e) {
			stats="modify";
		}
		return "addExam";
	}
	public PeBzzExamBatch getPeBzzExamBatch() {
		return peBzzExamBatch;
	}

	public void setPeBzzExamBatch(PeBzzExamBatch peBzzExamBatch) {
		this.peBzzExamBatch = peBzzExamBatch;
	}

	public String getExam_explain() {
		return exam_explain;
	}

	public void setExam_explain(String exam_explain) {
		this.exam_explain = exam_explain;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExam_notice() {
		return exam_notice;
	}

	public void setExam_notice(String exam_notice) {
		this.exam_notice = exam_notice;
	}

	public String getLate_explain() {
		return late_explain;
	}

	public void setLate_explain(String late_explain) {
		this.late_explain = late_explain;
	}

	public String getAnswer_explain() {
		return answer_explain;
	}

	public void setAnswer_explain(String answer_explain) {
		this.answer_explain = answer_explain;
	}

}
