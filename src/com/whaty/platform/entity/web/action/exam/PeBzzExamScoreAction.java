package com.whaty.platform.entity.web.action.exam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.bcel.internal.generic.DCMPG;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzEditStudent;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamLate;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzExamSite;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;
import com.whaty.platform.entity.service.exam.PeBzzExamScoreService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeBzzExamScoreAction extends MyBaseAction<PeBzzExamScore>{
	
	private List<PeBzzExamBatch> peBzzExamBatch;
	
	private File upload; // 文件
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置
	private String batchid ;
	private String score_type;
	private String status;
	private int confirm_count;
	private String confirm_status;
	private List peBzzExamScores;
	
	private PeBzzExamScoreService peBzzExamScoreService;
	
	private String tag = "0";
	
	@Override
	public void initGrid() {
		this.setCanProjections(true);
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getUserLoginType().equals("3")&& !this.getTag().equals("$updatetag")&& !this.getTag().equals("search")){
			this.getGridConfig().setCapability(false, true, true);
			this.getGridConfig().addMenuFunction("审核", "confirm", "1");
			this.getGridConfig().addMenuFunction("取消审核", "unconfirm", "1");
			//this.getGridConfig().addMenuScript(this.getText("导出报名学员"),
					//"{" + "var searchData = s_formPanel.getForm().getValues(true);" +
					//"window.location='/entity/manager/exam/exam_student_excel.jsp?'+searchData;}");
			this.getGridConfig().addMenuScript(this.getText("导出报名统计"),
					"{" + "var searchData = s_formPanel.getForm().getValues(true);" +
					"window.location='/entity/manager/exam/exam_stat_excel.jsp?'+searchData;}");
			
			this.getGridConfig().addMenuScript(this.getText("导入考试信息"),
					"{"+"window.location='peBzzExamScore_examBatchUpload.action?'}");
			
			/*this.getGridConfig().addMenuFunction(this.getText("选择打印准考证"),
					"/entity/exam/peBzzExamScore_examBatchPrint.action?",
					false, false);
			
			this.getGridConfig().addMenuScript(this.getText("打印当前批次下全部准考证"),
					"{" + "window.open('/entity/manager/exam/exam_testcard_batch_print.jsp?')}");*/
			
		}
		if(!this.getTag().equals("$updatetag")&& !this.getTag().equals("search"))
		{
			this.getGridConfig().addMenuScript(this.getText("打印说明"),
					"{"+"window.open('/entity/manager/exam/testcard_print_explain.jsp?')}");
			//由于还未安排考场及座号，打印准考证操作暂时关闭2011-11-12mhy
			this.getGridConfig().addMenuFunction(this.getText("打印准考证"),
					"/entity/exam/peBzzExamScore_examBatchPrint.action?",
					false, false);
			
			this.getGridConfig().addMenuScript(this.getText("打印当前批次下全部准考证"),
					"{" + "window.open('/entity/manager/exam/exam_testcard_batch_print.jsp?')}");
			
			
		}
		if(us.getUserLoginType().equals("3")&& this.getTag().equals("$updatetag"))
		{
			this.getGridConfig().setCapability(false,false,true);
		}
		else 
		{
			this.getGridConfig().setCapability(false,false,false);
		}
		//this.getGridConfig().setCapability(true, true, true);
		
		if(this.getTag().equals("$updatetag"))
		{
			this.getGridConfig().setTitle("成绩信息");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("手机号"),"peBzzStudent.mobilePhone",true,
					false, true, "");
			//this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,false,true,"TextField",true,100);
			//this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.name",false,true,false,"TextField",false,100);
			this.getGridConfig().addColumn(this.getText("学期"),"peBzzStudent.peBzzBatch.name",true,
					false, true, "");
			ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),"peBzzStudent.peEnterprise.name");
			c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null");
			this.getGridConfig().addColumn(c_name1);
			this.getGridConfig().addColumn(this.getText("所在考试批次"),"peBzzExamBatch.name",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("考点"), "peBzzExamBatchSite.peBzzExamSite.name", false,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("审核状态"),"enumConstByFlagExamScoreStatus.name",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("考试状态"),"enumConstByFlagExamStatus.name",true,
					true, true, "");		
			//this.getGridConfig().addColumn(this.getText("考点"),"peBzzExamBatchSite.peBzzExamSite.name",true,false,true,"TextField",false,50);
			//this.getGridConfig().addColumn(this.getText("考场"),"room_id",true,false,true,"TextField");
			this.getGridConfig().addColumn(this.getText("主观题成绩"),"subScore",false,true,true, Const.score_for_extjs);
			
			this.getGridConfig().addColumn(this.getText("客观题成绩"),"objScore",false,true,true,Const.score_for_extjs);
			
			this.getGridConfig().addColumn(this.getText("总成绩"),"score",false,true,true,Const.score_for_extjs);
			
			this.getGridConfig().addRenderFunction(this.getText("操作"), "<a href=\"peBzzExamScore_togetherScore.action?bean.id=${value}\">同步成绩</a>","id");
		}
		else if(us.getUserLoginType().equals("3")&&this.getTag().equals("search"))
		{
			this.getGridConfig().setTitle("成绩信息");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			//this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,false,true,"TextField");
			this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("手机号"),"peBzzStudent.mobilePhone",true,
					false, true, "");
			ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),"peBzzStudent.peEnterprise.name");
			c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null");
			this.getGridConfig().addColumn(c_name1);
			//this.getGridConfig().addColumn(this.getText("所在考试批次"),"peBzzExamBatch.name",true,true,true,"TextField",false,50);
			this.getGridConfig().addColumn(this.getText("所在考试批次"),"peBzzExamBatch.name",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("审核状态"),"enumConstByFlagExamScoreStatus.name",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("考试状态"),"enumConstByFlagExamStatus.name",true,
					false, true, "");			
			this.getGridConfig().addColumn(this.getText("考试成绩"),"score",false,true,true,"TextField");
			
			this.getGridConfig().addColumn(this.getText("考试成绩是否发布"),"enumConstByFlagExamScoreRelease.name",false,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("自测成绩"),"test_score",false,true,true,"TextField");
			
			this.getGridConfig().addColumn(this.getText("自测成绩是否发布"),"enumConstByFlagExamTestScoreRelease.name",false,
					false, true, "");
			
			this.getGridConfig().addColumn(this.getText("总评成绩"),"total_score",false,true,true,"TextField");
			
			this.getGridConfig().addColumn(this.getText("总评成绩是否发布"),"enumConstByFlagExamTotalScoreRelease.name",false,
					false, true, "");
			
			this.getGridConfig().addColumn(this.getText("总评等级"),"total_grade",false,true,true,"TextField");
			
			this.getGridConfig().addColumn(this.getText("总评等级是否发布"),"enumConstByFlagExamTotalGradeRelease.name",false,
					false, true, "");
			
		}
		else 
		{
			this.getGridConfig().setTitle("报名信息");
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			//this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,false,true,"TextField");
			this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("性别"),"peBzzStudent.gender",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("移动电话"),"peBzzStudent.mobilePhone",true,
					false, true, "");
			//this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,false,true,"TextField",true,100);
			//this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.name",false,true,false,"TextField",false,100);
			this.getGridConfig().addColumn(this.getText("学期"),"peBzzStudent.peBzzBatch.name",true,
					false, true, "");
			//this.getGridConfig().addColumn(this.getText("学期"),"peBzzStudent.peBzzBatch.name",true,true,true,"TextField",false,50);
			ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),"peBzzStudent.peEnterprise.name");
			c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null");
			this.getGridConfig().addColumn(c_name1);
			//this.getGridConfig().addColumn(this.getText("所在考试批次"),"peBzzExamBatch.name",true,true,true,"TextField",false,50);
			this.getGridConfig().addColumn(this.getText("所在考试批次"),"peBzzExamBatch.name",true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("考点"), "peBzzExamBatchSite.peBzzExamSite.name", true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("考试类型"), "enumConstByFlagExamType.name", true,
					false, true, "");
			//this.getGridConfig().addColumn(this.getText("考点"),"peBzzExamBatchSite.peBzzExamSite.name",true,true,true,"TextField",false,500);
			//this.getGridConfig().addColumn(this.getText("审核状态"),"enumConstByFlagExamScoreStatus.name",true,true,true,"TextField");
			this.getGridConfig().addColumn(this.getText("审核状态"),"enumConstByFlagExamScoreStatus.name",true,
					false, true, "");
		}
		
		/*if(this.getTag().equals("$updatetag"))
		{
			this.getGridConfig().addColumn(this.getText("成绩"),"score",false,true,true,"TextField");
			this
			.getGridConfig()
			.addRenderScript(
					this.getText("操作"),
					"{return '<a href=/entity/manager/exam/reject_examlate.jsp?id='+record.data['id']+'&type=enterprise>录入|修改</a>';}",
					"");
		}*/
		
		
	}

	public void setEntityClass() {
		this.entityClass = PeBzzExamScore.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/exam/peBzzExamScore";
	}

	public void setBean(PeBzzExamScore instance) {
		super.superSetBean(instance);
	}

	public PeBzzExamScore getBean() {
		return super.superGetBean();
	}
	
	//MenuFunction  方法
	

	
	//add校验
	
	public void checkBeforeAdd() throws EntityException {
		
	}
	
	//update校验
	
	public void checkBeforeUpdate() throws EntityException {
		
	}
	public String togetherScore() {
		
		//该学生报名信息
		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		DetachedCriteria prdc = score.createCriteria("peBzzExamBatch","peBzzExamBatch");
		score.add(Restrictions.eq("id", this.getBean().getId()));
		PeBzzExamScore peBzzScore = null;
		try {
			peBzzScore = this.getGeneralService().getExamScore(score);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//该考试批次信息
		DetachedCriteria examBatch = DetachedCriteria.forClass(PeBzzExamBatch.class);
		examBatch.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
		examBatch.add(Restrictions.eq("id", peBzzScore.getPeBzzExamBatch().getId()));
		PeBzzExamBatch peExamBatch = null;
		try {
			peExamBatch = this.getGeneralService().getExamBatch(examBatch);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Long exam_scale = peExamBatch.getExamScale();
		Long test_scale = peExamBatch.getTestScale();
		
		//生成总评成绩
		String exam_score = peBzzScore.getScore();
		String test_score = peBzzScore.getTest_score();
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
		String update_sql="update pe_bzz_examscore t set t.total_score=replace(to_char("+totalScore+",'999'),' ','') where t.id='"+ this.getBean().getId()+"'";
		try {
			this.getGeneralService().executeBySQL(update_sql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		//生成总评等级
		String totalGrade="";
		if(Double.parseDouble(totalScore)<60)
		{
			totalGrade="不合格";
		}
		else if(Double.parseDouble(totalScore)>=60 && Double.parseDouble(totalScore)<75)
		{
			totalGrade="合格";
		}
		else if(Double.parseDouble(totalScore)>=75 && Double.parseDouble(totalScore)<90)
		{
			totalGrade="良好";
		}
		else if(Double.parseDouble(totalScore)>=90)
		{
			totalGrade="优秀";
		}
		String update="update pe_bzz_examscore t set t.total_grade='"+totalGrade+"' where t.id='"+ this.getBean().getId()+"'";
		try {
			this.getGeneralService().executeBySQL(update);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
				

		return "togetherScore";
	}
	//懒加载
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamScore.class);
		//dc.createCriteria("peBzzStudent", "peBzzStudent", DetachedCriteria.INNER_JOIN);
		dc.createCriteria("peBzzExamBatch", "peBzzExamBatch", DetachedCriteria.INNER_JOIN);
		DetachedCriteria batchSite = dc.createCriteria("peBzzExamBatchSite", "peBzzExamBatchSite", DetachedCriteria.INNER_JOIN);
		batchSite.createCriteria("peBzzExamSite", "peBzzExamSite", DetachedCriteria.INNER_JOIN);
		dc.createCriteria("enumConstByFlagExamScoreStatus", "enumConstByFlagExamScoreStatus", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagExamStatus", "enumConstByFlagExamStatus", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagExamScoreRelease", "enumConstByFlagExamScoreRelease", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagExamTotalScoreRelease", "enumConstByFlagExamTotalScoreRelease", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagExamTestScoreRelease", "enumConstByFlagExamTestScoreRelease", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagExamTotalGradeRelease", "enumConstByFlagExamTotalGradeRelease", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagExamType", "enumConstByFlagExamType", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria pebzz =dc.createCriteria("peBzzStudent","peBzzStudent", DetachedCriteria.INNER_JOIN);
		pebzz.createCriteria("peBzzBatch", "peBzzBatch", DetachedCriteria.INNER_JOIN);
		pebzz.createCriteria("peEnterprise", "peEnterprise", DetachedCriteria.INNER_JOIN);
		
		DetachedCriteria dct = DetachedCriteria.forClass(PeBzzStudent.class);
		dct.createAlias("peEnterprise", "peEnterprise",DetachedCriteria.LEFT_JOIN);
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		if(!us.getUserLoginType().equals("3")) {
			List<String> priEntIds = new ArrayList<String>();
			for(PeEnterprise pe : us.getPriEnterprises()) {
				priEntIds.add(pe.getId());
			}
			if(priEntIds.size() != 0) {
				dc.add(Restrictions.in("peBzzStudent.peEnterprise.id", priEntIds));
			} else {
				dc.add(Expression.eq("2", "1"));
			}
		}
		return dc;
	}
	
	public String up(){
		return "up";
	}
	
	public String updatetag(){
			 return "tag";
	}
	
	public String search(){
		 return "search";
	}
	
	public String unBaoMing(){
		 return "unBaoMing";
	}
	
	public String ups(){
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		if(userLoginType.equals("3")||userLoginType.equals("2")){
			DetachedCriteria examBatch = DetachedCriteria.forClass(PeBzzExamBatch.class);
			examBatch.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
			examBatch.createCriteria("peBzzBatch", "peBzzBatch");
			try {
				this.setPeBzzExamBatch(this.getGeneralService().getList(examBatch));
				} catch (EntityException e) {
					e.printStackTrace();
			}
			return "ups";
		}else{
			this.setMsg("您的权限不够，无法进行此项操作!!!!");
		}
		return "msg";
		}
	
	public String examBatchUpload(){
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		if(userLoginType.equals("3")||userLoginType.equals("2")){
			DetachedCriteria examBatch = DetachedCriteria.forClass(PeBzzExamBatch.class);
			examBatch.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
			examBatch.createCriteria("peBzzBatch", "peBzzBatch");
			try {
				this.setPeBzzExamBatch(this.getGeneralService().getList(examBatch));
				} catch (EntityException e) {
					e.printStackTrace();
			}
			return "examBatchUpload";
		}else{
			this.setMsg("您的权限不够，无法进行此项操作!!!!");
		}
		return "msg";
		}
	
	public String look(){
		 return "look";
	}

	public List<PeBzzExamBatch> getPeBzzExamBatch() {
		return peBzzExamBatch;
	}

	public void setPeBzzExamBatch(List<PeBzzExamBatch> peBzzExamBatch) {
		this.peBzzExamBatch = peBzzExamBatch;
	}
	
	public String uploadExcel(){
		
		int count =0;
		try {
			FileInputStream fis = new FileInputStream(getUpload());
			File file = new File(getSavePath().replaceAll("\\\\", "/"));
			 if (!file.exists()) {
				    file.mkdirs();
				   }
			 	String filepath = getSavePath().replaceAll("\\\\", "/") + "/" + getUploadFileName();
			 FileOutputStream fos = new FileOutputStream(getSavePath().replaceAll("\\\\", "/") + "/" + getUploadFileName());
			  int i = 0;
			   byte[] buf = new byte[1024];
			   while ((i = fis.read(buf)) != -1) {
			    fos.write(buf, 0, i);
			   }
			   File filetest = new File(filepath);
			   //System.out.println("标记111"+this.peBzzExamScoreService);
			   //count = this.getPeBzzExamScoreService().Bacthsave(filetest, batchid);
			   count = this.getPeBzzExamScoreService().Bacthsave(filetest, batchid);
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
			return "uploadScore_result";
		} catch (MessageException e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "uploadScore_result";
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "uploadScore_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "uploadScore_result";
	}
	
	public String uploadExamBatchExcel(){
		
		int count =0;
		try {
			FileInputStream fis = new FileInputStream(getUpload());
			File file = new File(getSavePath().replaceAll("\\\\", "/"));
			 if (!file.exists()) {
				    file.mkdirs();
				   }
			 	String filepath = getSavePath().replaceAll("\\\\", "/") + "/" + getUploadFileName();
			 FileOutputStream fos = new FileOutputStream(getSavePath().replaceAll("\\\\", "/") + "/" + getUploadFileName());
			  int i = 0;
			   byte[] buf = new byte[1024];
			   while ((i = fis.read(buf)) != -1) {
			    fos.write(buf, 0, i);
			   }
			   File filetest = new File(filepath);
			   //System.out.println("标记111"+this.peBzzExamScoreService);
			   count = this.getPeBzzExamScoreService().BacthExamsave(filetest, batchid);
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
			return "uploadScore_result";
		} catch (MessageException e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "uploadScore_result";
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "uploadScore_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "uploadScore_result";
	}
    
	public String examBatchPrint()
	{	
		List<PeBzzExamScore> list = new ArrayList <PeBzzExamScore>(); 
		Map session =  ActionContext.getContext().getSession();
		for(int i=0;i<this.getIds().split(",").length;i++){
			DetachedCriteria detachedCriteria = DetachedCriteria
			.forClass(PeBzzExamScore.class);
			detachedCriteria.createCriteria("peBzzExamBatch", "peBzzExamBatch", DetachedCriteria.INNER_JOIN);
			DetachedCriteria student = detachedCriteria.createCriteria("peBzzStudent", "peBzzStudent", DetachedCriteria.INNER_JOIN);
			student.createCriteria("peEnterprise", "peEnterprise");
			DetachedCriteria batchs = detachedCriteria.createCriteria("peBzzExamBatchSite", "peBzzExamBatchSite", DetachedCriteria.INNER_JOIN);
			batchs.createCriteria("peBzzExamSite", "peBzzExamSite", DetachedCriteria.INNER_JOIN);
			detachedCriteria.createCriteria("enumConstByFlagExamScoreStatus", "enumConstByFlagExamScoreStatus", DetachedCriteria.LEFT_JOIN);
			detachedCriteria.add(Restrictions.eq("enumConstByFlagExamScoreStatus.id", "402880a9aaadc115061dadfcf26b0003"));
			detachedCriteria.add(Restrictions.eq("id", this.getIds().split(",")[i]));
			PeBzzExamScore peBzzExamScore = null;
			try {
				peBzzExamScore = this.getGeneralService().getExamScore(detachedCriteria);
				if(peBzzExamScore!=null)
				{
					list.add(peBzzExamScore);
				}
				
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		}
		this.setPeBzzExamScores(list);
		session.put("peBzzExamScores", peBzzExamScores);
		return "examBatchPrint";
	}
	@Override
	public Map updateColumn() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();

		if (this.getColumn().equals("confirm")) {
			
			for(int i=0;i<this.getIds().split(",").length;i++){
				DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(PeBzzExamScore.class);
					detachedCriteria.add(Restrictions.eq("id", this.getIds().split(",")[i]));
					List<PeBzzExamScore> list = null;
					try {
						list = this.getGeneralService().getList(detachedCriteria);
					} catch (EntityException e1) {
						e1.printStackTrace();
					}
		
					for (PeBzzExamScore peBzzExamScore : list) {
						if (peBzzExamScore.getEnumConstByFlagExamScoreStatus().getCode().equals("0")&&us.getUserLoginType().equals("3")) {
							//该考试批次信息
							DetachedCriteria examBatch = DetachedCriteria.forClass(PeBzzExamBatch.class);
							examBatch.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
							examBatch.createCriteria("enumConstByFlagExamConditionHomework", "enumConstByFlagExamConditionHomework");
							examBatch.createCriteria("enumConstByFlagExamConditionTest", "enumConstByFlagExamConditionTest");
							examBatch.createCriteria("enumConstByFlagExamConditionEvaluate", "enumConstByFlagExamConditionEvaluate");
							examBatch.add(Restrictions.eq("id", peBzzExamScore.getPeBzzExamBatch().getId()));
							PeBzzExamBatch peExamBatch = null;
							try {
								peExamBatch = this.getGeneralService().getExamBatch(examBatch);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							//该考试批次报名条件
							Long time = peExamBatch.getTime();
							String homework = peExamBatch.getEnumConstByFlagExamConditionHomework().getName();
							String test = peExamBatch.getEnumConstByFlagExamConditionTest().getName();
							String evaluate = peExamBatch.getEnumConstByFlagExamConditionEvaluate().getName();
							
							String timeflag="1";
							String homeworkflag="1";
							String testflag="1";
							String evaluateflag="1";
							
							String sqlevaluate;
							String sqlhomework;
							String sqltest;
							String sqlpaper;
							
							//该生的选课情况（基础课程）
							/*DetachedCriteria jccomdc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
							DetachedCriteria opendc = jccomdc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse", DetachedCriteria.INNER_JOIN);
							opendc.createCriteria("peBzzTchCourse", "peBzzTchCourse", DetachedCriteria.INNER_JOIN);
							DetachedCriteria pbsdc =jccomdc.createCriteria("peBzzStudent","peBzzStudent", DetachedCriteria.INNER_JOIN);
							opendc.createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType", DetachedCriteria.INNER_JOIN);
							jccomdc.add(Restrictions.eq("peBzzStudent.id", peBzzExamScore.getPeBzzStudent().getId()));
							jccomdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "基础课程"));
							List electiveList = new ArrayList();//选课列表
							try {
								electiveList = this.getGeneralService().getList(jccomdc);
							} catch (EntityException e) {
								e.printStackTrace();
							}
							List<PrBzzTchStuElective> elvlist = (List<PrBzzTchStuElective>)electiveList;*/
							
							String sql_training=" training_course_student ts,";
							if(peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId().equals("ff8080812824ae6f012824b0a89e0008")){
								sql_training="("+
								"select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s ,pe_bzz_student bs,pr_bzz_tch_opencourse pbto where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"+peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId()+"'\n"+
								"and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821')" +
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
								") ts, ";
							}
							
							String sql_hw="";
							String sql_test="";
							 if(peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId().equals("ff8080812253f04f0122540a58000004")||peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId().equals("ff8080812824ae6f012824b0a89e0008")){
								 sql_hw = " and thi.batch_id is null ";
								 sql_test = " and oci.fk_batch_id is null ";
								}
							else{
								sql_hw = " and thi.batch_id='"+peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId()+"'";
								sql_test = " and oci.fk_batch_id='"+peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId()+"' ";
							}
					  		String sqlcourse=
					  			"select s.true_name,\n" +
					  			"       s.reg_no,\n" + 
					  			"       s.mobile_phone,\n" + 
					  			"       s.phone,\n" + 
					  			"       e.name as ename,\n" + 
					  			"       per\n" + 
					  			"  from (select ps.id,\n" + 
					  			"               (nvl(sum(ce.time * (ts.percent / 100)), 0) / sum(ce.time)) * 100 as per\n" + 
					  			"          from sso_user                su,\n" + 
					  			sql_training + 
					  			"               pe_bzz_student          ps,\n" + 
					  			"               pr_bzz_tch_opencourse   co,\n" + 
					  			"               pe_bzz_tch_course       ce,\n" + 
					  			"               pe_enterprise           pe\n" + 
					  			"         where ps.fk_sso_user_id = su.id\n" + 
					  			"           and su.id = ts.student_id\n" + 
					  			"           and pe.id = ps.fk_enterprise_id\n" + 
					  			"           and co.id = ts.course_id\n" + 
					  			"           and co.fk_course_id = ce.id\n" + 
					  			"           and ps.fk_batch_id = co.fk_batch_id\n" + 
					  			"           and su.flag_isvalid = '2'\n" + 
					  			"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
					  			"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
					  			"           and su.login_num > 0\n" + 
					  			"           and ps.id='"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + 
					  			"         group by ps.id) a,\n" + 
					  			"       pe_bzz_student s,\n" + 
					  			"       pe_enterprise e\n" + 
					  			" where s.id = a.id\n" + 
					  			"   and e.id = s.fk_enterprise_id  and per<"+time+"  \n" + 
					  			" order by e.name, s.reg_no";
					  		List courseList=null;
					  		try {
								courseList=this.getGeneralService().getBySQL(sqlcourse);
							} catch (EntityException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(courseList==null||courseList.size()>0)
							{
								timeflag="0";
								//System.out.println("courseList.size()="+courseList.size());
								//System.out.println("timeflag="+timeflag);
							}
							if(homework.equals("是"))
							{
							sqlhomework=
								"select t_id from\n" +
								"(select a.id,a.testpaper_id,b.testpaper_id as t_id from\n" + 
								"(select ps.id,thh.testpaper_id\n" + 
								"          from sso_user                su,\n" + 
								"               test_homeworkpaper_history thh,\n" + 
								"               test_homeworkpaper_info thi,\n" + 
								"               pe_bzz_student          ps,\n" + 
								"               pr_bzz_tch_opencourse   co,\n" + 
								"               pe_bzz_tch_course       ce,\n" + 
								"               pe_enterprise           pe\n" + 
								"         where ps.fk_sso_user_id = su.id\n" + 
								"           and thi.group_id=ce.id and thh.testpaper_id=thi.id and thh.t_user_id=ps.id\n" + 
								"           and pe.id = ps.fk_enterprise_id\n" + 
								"           and co.fk_course_id = ce.id\n" + 
								"           and ps.fk_batch_id = co.fk_batch_id\n" + 
								"           and su.flag_isvalid = '2'\n" + 
								"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
								"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
								"           and su.login_num > 0\n" +
								//"			and ts.course_id=co.id and ts.student_id=su.id "+
								"           and ps.id='"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + sql_hw+
								"           ) a,\n" + 
								"(select ps.id,thi.id as testpaper_id\n" + 
								"          from sso_user                su,\n" + 
								"               test_homeworkpaper_info thi,\n" + 
								"               pe_bzz_student          ps,\n" + 
								"               pr_bzz_tch_opencourse   co,\n" + 
								"               pe_bzz_tch_course       ce,\n" + 
								"               pe_enterprise           pe\n" + 
								"         where ps.fk_sso_user_id = su.id\n" + 
								"           and thi.group_id=ce.id\n" + 
								"           and pe.id = ps.fk_enterprise_id\n" + 
								"           and co.fk_course_id = ce.id\n" + 
								"           and ps.fk_batch_id = co.fk_batch_id\n" + 
								"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
								"           and su.login_num > 0\n" + 
							//	"			and ts.course_id=co.id and ts.student_id=su.id "+
								"           and ps.id='"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + sql_hw+
								"           ) b where a.id(+)=b.id and a.testpaper_id(+)=b.testpaper_id\n" + 
								"           ) where id is null";
							List homeworkList=null;
					  		try {
					  			homeworkList=this.getGeneralService().getBySQL(sqlhomework);
							} catch (EntityException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(homeworkList==null||homeworkList.size()>0)
							{
								homeworkflag="0";
								//System.out.println("homeworkflag="+homeworkflag);
							}
							}
							/*
							for (PrBzzTchStuElective ele : elvlist)
					  		{
					  			//判断是否满足报名条件1：基础课程必须完成百分之几
					  			//System.out.println("percent="+ele.getTrainingCourseStudent().getPercent());
								if(ele.getTrainingCourseStudent().getPercent() < time)
								{
									timeflag="0";
									//System.out.println("timeflag="+timeflag);
									break;
								}
					  		}*/
					  	    //判断是否满足报名条件2：作业是否必须完成
							
							
							/*if(homework.equals("是"))
							{
								for (PrBzzTchStuElective ele : elvlist)
						  		{
									sqlpaper = 
										"select id\n" +
										"  from (select id, group_id, status\n" + 
										"          from (select id, group_id, status from test_homeworkpaper_info)\n" + 
										"         where status = '1'\n" + 
										"           and group_id = '"+ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId()+"')";
									List paperList=null;
									try {
										paperList=this.getGeneralService().getBySQL(sqlpaper);
									}catch (EntityException e) {
										e.printStackTrace();
									}
									Iterator it=paperList.iterator();
									String paperId="";
									//System.out.println("paperList.size()="+paperList.size());
									for(int j=0;j<paperList.size();j++){
										//java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
										//paperId = entry.getKey().toString();
										
										paperId = paperList.get(j).toString();
										sqlhomework=
											"select id, user_id, testpaper_id\n" +
											"  from (select a.id,\n" + 
											"               a.user_id,a.t_user_id,\n" + 
											"               testpaper_id,\n" + 
											"               b.group_id\n" + 
											"          from test_homeworkpaper_history a,\n" + 
											"               test_homeworkpaper_info    b,\n" + 
											"               pe_bzz_student             c\n" + 
											"         where a.testpaper_id = b.id\n" + 
											"           and a.user_id = ('(' || c.id || ')' || c.true_name)\n" + 
											"         order by a.ischeck desc, test_date desc)\n" + 
											" where t_user_id = '"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + 
											"   and testpaper_id = '"+paperId+"'\n" + 
											"   and group_id = '"+ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId()+"'";
										List homeworkList=null;
										try {
											homeworkList=this.getGeneralService().getBySQL(sqlhomework);
										}catch (EntityException e) {
											e.printStackTrace();
										}
										if(homeworkList !=null)
										{
											homeworkflag="0";
											//System.out.println("homeworkflag="+homeworkflag);
											break;
										}
									}
							    }
				  		    }*/
							//判断是否满足报名条件3：在线自测是否必须完成
							if(test.equals("是"))
							{
								if(peBzzExamScore.getPeBzzStudent().getPeBzzBatch().getId().equals("ff8080812824ae6f012824b0a89e0008")){
									sqltest= 
										"select id,cnum,tnum from\n" +
										"(select nvl(a.id,1) as id,nvl(a.cnum,0) as cnum,b.tnum from\n" + 
										"(select ps.id,count( distinct tth.testpaper_id) as cnum\n" + 
										"          from sso_user                su,\n" + 
										"               test_testpaper_history tth,\n" + 
										"               test_testpaper_info tti,\n" + 
										"               pe_bzz_student          ps,\n" + 
										"               pr_bzz_tch_opencourse   co,\n" + 
										"               pe_bzz_tch_course       ce,\n" + 
										"               pe_enterprise           pe\n" + 
										"         where ps.fk_sso_user_id = su.id and tth.score>=60\n" + 
										"           and tti.group_id=ce.id and tth.testpaper_id=tti.id and tth.t_user_id = ps.id\n" + 
										"           and pe.id = ps.fk_enterprise_id\n" + 
										"           and co.fk_course_id = ce.id\n" + 
										"           and ps.fk_batch_id = co.fk_batch_id\n" + 
										"           and su.flag_isvalid = '2'\n" + 
										"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
										"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
										"           and su.login_num > 0\n" + 
									//	"			and ts.course_id=co.id and ts.student_id=su.id "+
										"           and ps.id='"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + 
										"           group by ps.id\n" + 
										"           ) a,\n" + 
										"(select ps.id,count(distinct oci.id) as tnum\n" + 
										"          from sso_user                su,\n" + 
										"               test_testpaper_info tti,\n" + 
										"               pe_bzz_student          ps,\n" + 
										"               pr_bzz_tch_opencourse   co,\n" + 
										"               onlinetest_course_info  oci,\n" + 
										"               pe_bzz_tch_course       ce,\n" + 
										"               pe_enterprise           pe\n" + 
										"         where ps.fk_sso_user_id = su.id\n" + 
										"           and tti.group_id=ce.id\n" + 
										"           and oci.group_id=ce.id\n" + 
										"           and pe.id = ps.fk_enterprise_id\n" + 
										"           and co.fk_course_id = ce.id\n" + 
										"           and ps.fk_batch_id = co.fk_batch_id\n" + 
										"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
										"           and su.login_num > 0\n" + 
										//"			and ts.course_id=co.id and ts.student_id=su.id "+
										"           and ps.id='"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + sql_test+
										"           group by ps.id\n" + 
										"           ) b where a.id(+)=b.id\n" + 
										"           ) where cnum<(tnum-2)";
								}
								else{
									sqltest= 
										"select id,cnum,tnum from\n" +
										"(select nvl(a.id,1) as id,nvl(a.cnum,0) as cnum,b.tnum from\n" + 
										"(select ps.id,count( distinct tth.testpaper_id) as cnum\n" + 
										"          from sso_user                su,\n" + 
										"               test_testpaper_history tth,\n" + 
										"               test_testpaper_info tti,\n" + 
										"               pe_bzz_student          ps,\n" + 
										"               pr_bzz_tch_opencourse   co,\n" + 
										"               pe_bzz_tch_course       ce,\n" + 
										"               pe_enterprise           pe\n" + 
										"         where ps.fk_sso_user_id = su.id and tth.score>=60\n" + 
										"           and tti.group_id=ce.id and tth.testpaper_id=tti.id and tth.t_user_id = ps.id\n" + 
										"           and pe.id = ps.fk_enterprise_id\n" + 
										"           and co.fk_course_id = ce.id\n" + 
										"           and ps.fk_batch_id = co.fk_batch_id\n" + 
										"           and su.flag_isvalid = '2'\n" + 
										"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
										"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
										"           and su.login_num > 0\n" + 
									//	"			and ts.course_id=co.id and ts.student_id=su.id "+
										"           and ps.id='"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + 
										"           group by ps.id\n" + 
										"           ) a,\n" + 
										"(select ps.id,count(distinct oci.id) as tnum\n" + 
										"          from sso_user                su,\n" + 
										"               test_testpaper_info tti,\n" + 
										"               pe_bzz_student          ps,\n" + 
										"               pr_bzz_tch_opencourse   co,\n" + 
										"               onlinetest_course_info  oci,\n" + 
										"               pe_bzz_tch_course       ce,\n" + 
										"               pe_enterprise           pe\n" + 
										"         where ps.fk_sso_user_id = su.id\n" + 
										"           and tti.group_id=ce.id\n" + 
										"           and oci.group_id=ce.id\n" + 
										"           and pe.id = ps.fk_enterprise_id\n" + 
										"           and co.fk_course_id = ce.id\n" + 
										"           and ps.fk_batch_id = co.fk_batch_id\n" + 
										"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
										"           and su.login_num > 0\n" + 
										//"			and ts.course_id=co.id and ts.student_id=su.id "+
										"           and ps.id='"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + sql_test+
										"           group by ps.id\n" + 
										"           ) b where a.id(+)=b.id\n" + 
										"           ) where cnum<>tnum";
								}
								List testList=null;
								try {
									testList=this.getGeneralService().getBySQL(sqltest);
						  		}catch (EntityException e) {
									e.printStackTrace();
								}
								if(testList==null||testList.size()>0)
								{
									testflag="0";
									//System.out.println("testflag="+testflag);
								}
							}
							
							/*
							if(test.equals("是"))
							{
								for (PrBzzTchStuElective ele : elvlist)
						  		{
									sqltest = 
										"select maxscore\n" +
										"  from (select nvl(max(to_number(score)), 0) as maxscore\n" + 
										"          from (select nvl(ty.score, 0) as score\n" + 
										"                  from test_testpaper_history ty\n" + 
										"                 inner join test_testpaper_info ti on ty.testpaper_id = ti.id\n" + 
										"                                                  and ty.user_id like\n" + 
										"                                                      '%"+peBzzExamScore.getPeBzzStudent().getId()+"%'\n" + 
										"                                                  and ti.group_id =\n" + 
										"                                                      '"+ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId()+"'))\n" + 
										" where maxscore <60";
					
									List testList=null;
									try {
										testList=this.getGeneralService().getBySQL(sqltest);
							  		}catch (EntityException e) {
										e.printStackTrace();
									}
									if(testList !=null)
									{
										testflag="0";
										//System.out.println("testflag="+testflag);
										break;
									}
							    }
				  		    }*/
							//判断是否满足报名条件4：课程评估是否必须完成
							if(evaluate.equals("是"))
							{
								sqlevaluate=
									"select id,cnum,tnum from\n" +
									"(select nvl(a.id,1) as id,nvl(a.cnum,0) as cnum,b.tnum from\n" + 
									"(select ps.id,count( distinct pba.fk_course_id) as cnum\n" + 
									"          from sso_user                su,\n" + 
									"               pe_bzz_assess pba,\n" + 
									"               pe_bzz_student          ps,\n" + 
									"               pr_bzz_tch_opencourse   co,\n" + 
									"               pe_bzz_tch_course       ce,\n" + 
									"               pe_enterprise           pe\n" + 
									"         where ps.fk_sso_user_id = su.id\n" + 
									"           and pba.fk_student_id=su.id and pba.fk_course_id=co.id\n" + 
									"           and pe.id = ps.fk_enterprise_id\n" + 
									"           and co.fk_course_id = ce.id\n" + 
									"           and ps.fk_batch_id = co.fk_batch_id\n" + 
									"           and su.flag_isvalid = '2'\n" + 
									"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
									"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
									"           and su.login_num > 0\n" + 
									"           and ps.id='"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + 
									"           group by ps.id\n" + 
									"           ) a,\n" + 
									"(select ps.id,count(distinct co.id) as tnum\n" + 
									"          from sso_user                su,\n" + 
									"               pe_bzz_student          ps,\n" + 
									"               pr_bzz_tch_opencourse   co,\n" + 
									"               pe_bzz_tch_course       ce,\n" + 
									"               pe_enterprise           pe\n" + 
									"         where ps.fk_sso_user_id = su.id\n" + 
									"           and pe.id = ps.fk_enterprise_id\n" + 
									"           and co.fk_course_id = ce.id\n" + 
									"           and ps.fk_batch_id = co.fk_batch_id\n" + 
									"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
									"           and su.login_num > 0\n" + 
									"           and ps.id='"+peBzzExamScore.getPeBzzStudent().getId()+"'\n" + 
									"           group by ps.id\n" + 
									"           ) b where a.id(+)=b.id\n" + 
									"           ) where cnum<>tnum";
								List evaluateList=null;
								try {
									evaluateList=this.getGeneralService().getBySQL(sqlevaluate);
								}catch (EntityException e) {
									e.printStackTrace();
								}
								if(evaluateList==null||evaluateList.size()>0)
								{
									evaluateflag="0";
									//System.out.println("evaluateflag="+evaluateflag);
								}
							}
							
							/*
							if(evaluate.equals("是"))
							{
								for (PrBzzTchStuElective ele : elvlist)
						  		{
								
									sqlevaluate = "select * from pe_bzz_assess a,pe_bzz_student stu \n"
										+ "where  a.fk_student_id=stu.id and stu.id = '"+peBzzExamScore.getPeBzzStudent().getId()+"' \n"
										+ "      and a.fk_course_id='" + ele.getPrBzzTchOpencourse().getId() + "'";
									List evaluateList=null;
									try {
										evaluateList=this.getGeneralService().getBySQL(sqlevaluate);
									}catch (EntityException e) {
										e.printStackTrace();
									}
									if(evaluateList.size()==0)
									{
										evaluateflag="0";
										//System.out.println("evaluateflag="+evaluateflag);
										break;
									}
								
							    }
				  		    }*/
							if(timeflag.equals("0")||homeworkflag.equals("0")
									||testflag.equals("0")||evaluateflag.equals("0"))
							{
								
							}
							else
							{
								EnumConst getEnumConstByFlagExamScoreStatus = this.getMyListService().getEnumConstByNamespaceCode("FlagExamScoreStatus", "1");
								peBzzExamScore.setEnumConstByFlagExamScoreStatus(getEnumConstByFlagExamScoreStatus);
								DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamScore.class);
								try {
									this.getGeneralService().save(peBzzExamScore);
								} catch (EntityException e) {
									e.printStackTrace();
								}
							}
					     }
			      }
			}
			map.put("success", true);
			map.put("info", this.getText("状态已经成功改变"));

			return map;
		}
		if(this.getColumn().equals("unconfirm")){
			for(int i=0;i<this.getIds().split(",").length;i++){
					DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(PeBzzExamScore.class);
					detachedCriteria.add(Restrictions.eq("id", this.getIds().split(",")[i]));
					List<PeBzzExamScore> list = null;
					try {
						list = this.getGeneralService().getList(detachedCriteria);
					} catch (EntityException e1) {
						e1.printStackTrace();
					}
					for (PeBzzExamScore peBzzExamScore : list) {
						if (peBzzExamScore.getEnumConstByFlagExamScoreStatus().getCode().equals("1")&&us.getUserLoginType().equals("3")) {
								EnumConst getEnumConstByFlagExamScoreStatus = this.getMyListService().getEnumConstByNamespaceCode("FlagExamScoreStatus", "0");
								peBzzExamScore.setEnumConstByFlagExamScoreStatus(getEnumConstByFlagExamScoreStatus);
								try {
									this.getGeneralService().save(peBzzExamScore);
								} catch (EntityException e) {
									e.printStackTrace();
								}
							
						}
					}
			}
			map.put("success", true);
			map.put("info", this.getText("状态已经成功改变"));

		}
		return map;
	}
	/**
	 * action 批量审核
	 * 
	 * @return
	 */
	public String Confirm() {
		try {
			if (status != null && status.equals("1")) {
				
				//该考试批次信息
				DetachedCriteria examBatch = DetachedCriteria.forClass(PeBzzExamBatch.class);
				examBatch.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
				examBatch.createCriteria("enumConstByFlagExamConditionHomework", "enumConstByFlagExamConditionHomework");
				examBatch.createCriteria("enumConstByFlagExamConditionTest", "enumConstByFlagExamConditionTest");
				examBatch.createCriteria("enumConstByFlagExamConditionEvaluate", "enumConstByFlagExamConditionEvaluate");
				examBatch.add(Restrictions.eq("id", batchid));
				PeBzzExamBatch peExamBatch = null;
				try {
					peExamBatch = this.getGeneralService().getExamBatch(examBatch);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//该考试批次报名条件
				Long time = peExamBatch.getTime();
				String homework = peExamBatch.getEnumConstByFlagExamConditionHomework().getName();
				String test = peExamBatch.getEnumConstByFlagExamConditionTest().getName();
				String evaluate = peExamBatch.getEnumConstByFlagExamConditionEvaluate().getName();
				
				String timesql=
					"(select s.id\n" +
					"  from (select ps.id,\n" + 
					"               (nvl(sum(ce.time * (ts.percent / 100)), 0) / sum(ce.time)) * 100 as per\n" + 
					"          from sso_user                su,\n" + 
					"               training_course_student ts,\n" + 
					"               pe_bzz_examscore be,\n" + 
					"               pe_bzz_student          ps,\n" + 
					"               pr_bzz_tch_opencourse   co,\n" + 
					"               pe_bzz_tch_course       ce,\n" + 
					"               pe_enterprise           pe\n" + 
					"         where ps.fk_sso_user_id = su.id and be.student_id=ps.id and be.exambatch_id='"+batchid+"' and be.status='402880a9aaadc115062dadfcf26b0003'\n" + 
					"           and su.id = ts.student_id\n" + 
					"           and pe.id = ps.fk_enterprise_id\n" + 
					"           and co.id = ts.course_id\n" + 
					"           and co.fk_course_id = ce.id\n" + 
					"           and ps.fk_batch_id = co.fk_batch_id\n" + 
					"           and su.flag_isvalid = '2'\n" + 
					"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
					"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
					"           and su.login_num > 0\n" + 
					"         group by ps.id) a,\n" + 
					"       pe_bzz_student s,\n" + 
					"       pe_enterprise e\n" + 
					" where s.id = a.id\n" + 
					"   and e.id = s.fk_enterprise_id  and per>="+time+"\n" + 
					" order by e.name, s.reg_no) a";
				String homeworksql = 
					"(select a.id from\n" +
					"(select ps.id,pbe.exambatch_id,count(thh.testpaper_id) as tnum\n" + 
					"          from sso_user                su,\n" + 
					"               test_homeworkpaper_history thh,\n" + 
					"               test_homeworkpaper_info thi,\n" + 
					"               pe_bzz_student          ps,\n" + 
					"               pr_bzz_tch_opencourse   co,\n" + 
					"               pe_bzz_tch_course       ce,\n" + 
					"               pe_bzz_examscore pbe,\n" + 
					"               pe_enterprise           pe\n" + 
					"         where ps.fk_sso_user_id = su.id\n" + 
					"           and thi.group_id=ce.id and thh.testpaper_id=thi.id and thh.t_user_id=ps.id and pbe.student_id=ps.id and pbe.status='402880a9aaadc115062dadfcf26b0003'\n" + 
					"           and pbe.exambatch_id='"+batchid+"'\n" + 
					"           and pe.id = ps.fk_enterprise_id\n" + 
					"           and co.fk_course_id = ce.id\n" + 
					"           and ps.fk_batch_id = co.fk_batch_id\n" + 
					"           and su.flag_isvalid = '2'\n" + 
					"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
					"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
					"           and su.login_num > 0 group by ps.id,pbe.exambatch_id\n" + 
					"           ) a,\n" + 
					"(select pbe.id,count(thi.id) as cnum\n" + 
					"          from test_homeworkpaper_info thi,\n" + 
					"               pr_bzz_tch_opencourse   co,\n" + 
					"               pe_bzz_batch pbb,\n" + 
					"               pe_bzz_exambatch pbe,\n" + 
					"               pe_bzz_tch_course       ce\n" + 
					"         where pbb.id=pbe.batch_id and pbe.id='"+batchid+"'\n" + 
					"           and thi.group_id=ce.id\n" + 
					"           and co.fk_course_id = ce.id\n" + 
					"           and pbb.id = co.fk_batch_id\n" + 
					"           and co.flag_course_type = '402880f32200c249012200c780c40001'  group by pbe.id\n" + 
					"           ) b where a.exambatch_id=b.id and a.tnum=b.cnum) b";
				String testsql=
					"(select id from\n" +
					"(select a.id,nvl(a.cnum,0) as cnum,b.tnum from\n" + 
					"(select ps.id,pbes.exambatch_id,count( distinct tth.testpaper_id) as cnum\n" + 
					"          from sso_user                su,\n" + 
					"               test_testpaper_history tth,\n" + 
					"               test_testpaper_info tti,\n" + 
					"               pe_bzz_student          ps,\n" + 
					"               pr_bzz_tch_opencourse   co,\n" + 
					"               pe_bzz_examscore pbes,\n" + 
					"               pe_bzz_tch_course       ce,\n" + 
					"               pe_enterprise           pe\n" + 
					"         where ps.fk_sso_user_id = su.id and tth.score>=60 and pbes.student_id=ps.id and pbes.exambatch_id='"+batchid+"' and pbes.status='402880a9aaadc115062dadfcf26b0003'\n" + 
					"           and tti.group_id=ce.id and tth.testpaper_id=tti.id and tth.t_user_id = ps.id \n" + 
					"           and pe.id = ps.fk_enterprise_id\n" + 
					"           and co.fk_course_id = ce.id\n" + 
					"           and ps.fk_batch_id = co.fk_batch_id\n" + 
					"           and su.flag_isvalid = '2'\n" + 
					"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
					"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
					"           and su.login_num > 0\n" + 
					"           group by ps.id,pbes.exambatch_id\n" + 
					"           ) a,\n" + 
					"(select pbe.id,count(distinct oci.id) as tnum\n" + 
					"          from test_testpaper_info tti,\n" + 
					"               pr_bzz_tch_opencourse   co,\n" + 
					"               onlinetest_course_info  oci,\n" + 
					"               pe_bzz_tch_course       ce,\n" + 
					"               pe_bzz_batch pbb,\n" + 
					"               pe_bzz_exambatch pbe\n" + 
					"         where pbb.id=pbe.batch_id and pbe.id='"+batchid+"'  and pbb.id=co.fk_batch_id\n" + 
					"           and tti.group_id=ce.id\n" + 
					"           and oci.group_id=ce.id\n" + 
					"           and co.fk_course_id = ce.id\n" + 
					"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
					"           group by pbe.id\n" + 
					"           ) b where a.exambatch_id=b.id\n" + 
					"           ) where cnum=tnum) c";
				String evaluatesql = 
					"(select id from\n" +
					"(select a.id,nvl(a.cnum,0) as cnum,b.tnum from\n" + 
					"(select ps.id,pbes.exambatch_id,count( distinct pba.fk_course_id) as cnum\n" + 
					"          from sso_user                su,\n" + 
					"               pe_bzz_assess pba,\n" + 
					"               pe_bzz_student          ps,\n" + 
					"               pr_bzz_tch_opencourse   co,\n" + 
					"               pe_bzz_tch_course       ce,\n" + 
					"               pe_bzz_examscore pbes,\n" + 
					"               pe_enterprise           pe\n" + 
					"         where ps.fk_sso_user_id = su.id and pbes.student_id=ps.id and pbes.exambatch_id='"+batchid+"' and pbes.status='402880a9aaadc115062dadfcf26b0003'\n" + 
					"           and pba.fk_student_id=su.id and pba.fk_course_id=co.id\n" + 
					"           and pe.id = ps.fk_enterprise_id\n" + 
					"           and co.fk_course_id = ce.id\n" + 
					"           and ps.fk_batch_id = co.fk_batch_id\n" + 
					"           and su.flag_isvalid = '2'\n" + 
					"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
					"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
					"           and su.login_num > 0\n" + 
					"           group by ps.id,pbes.exambatch_id\n" + 
					"           ) a,\n" + 
					"(select pbe.id,count(distinct co.id) as tnum\n" + 
					"          from pr_bzz_tch_opencourse   co,\n" + 
					"               pe_bzz_tch_course       ce,\n" + 
					"               pe_bzz_batch pbb,\n" + 
					"               pe_bzz_exambatch pbe\n" + 
					"         where pbb.id=pbe.batch_id and pbe.id='"+batchid+"'  and pbb.id=co.fk_batch_id\n" + 
					"           and co.fk_course_id = ce.id\n" + 
					"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
					"           group by pbe.id\n" + 
					"           ) b where a.exambatch_id=b.id\n" + 
					"           ) where cnum=tnum) d";
				String sql = "select a.id from "+timesql;
				String condition = "";
				if(homework.equals("是"))
				{
					sql += ","+homeworksql;
					condition += " and a.id=b.id ";
				}
				if(test.equals("是"))
				{
					sql += ","+testsql;
					condition += " and a.id=c.id ";
				}
				if(evaluate.equals("是"))
				{
					sql += ","+evaluatesql;
					condition += " and a.id=d.id ";
				}
				sql = sql +" where 1=1 "+condition;
				String updatesql = "update pe_bzz_examscore t\n" +
				"   set t.status = (select id\n" + 
				"                     from enum_const c\n" + 
				"                    where c.code = '1'\n" + 
				"                      and c.namespace = 'FlagExamScoreStatus')\n" + 
				" where t.student_id in ("+sql+")";
				//System.out.println("updatesql="+updatesql);
				this.getGeneralService().executeBySQL(updatesql);


				
				
				
				//该批次报名学生信息
				/*DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
				DetachedCriteria prdc = score.createCriteria("peBzzExamBatch","peBzzExamBatch");
				prdc.add(Restrictions.eq("id", batchid));
				score.setProjection(Property.forName("peBzzStudent.id"));
				score.add(Restrictions.eq("status", "402880a9aaadc115062dadfcf26b0003"));
				List<String> studentList = this.getGeneralService().getList(score);
				
				if(studentList.size()>0)
				{//一个一个学生来查看其是否满足报名条件
					for (String id : studentList) {
						
						String timeflag="1";
						String homeworkflag="1";
						String testflag="1";
						String evaluateflag="1";
						
						String sqlevaluate;
						String sqlhomework;
						String sqltest;
						String sqlpaper;
						
						//该生的选课情况（基础课程）
						DetachedCriteria jccomdc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
						DetachedCriteria opendc = jccomdc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse", DetachedCriteria.INNER_JOIN);
						opendc.createCriteria("peBzzTchCourse", "peBzzTchCourse", DetachedCriteria.INNER_JOIN);
						DetachedCriteria pbsdc =jccomdc.createCriteria("peBzzStudent","peBzzStudent", DetachedCriteria.INNER_JOIN);
						opendc.createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType", DetachedCriteria.INNER_JOIN);
						jccomdc.add(Restrictions.eq("peBzzStudent.id", id));
						jccomdc.add(Restrictions.eq("enumConstByFlagCourseType.name", "基础课程"));
						List electiveList = new ArrayList();//选课列表
						try {
							electiveList = this.getGeneralService().getList(jccomdc);
						} catch (EntityException e) {
							e.printStackTrace();
						}
						List<PrBzzTchStuElective> list = (List<PrBzzTchStuElective>)electiveList;
				  		for (PrBzzTchStuElective ele : list)
				  		{
				  			//判断是否满足报名条件1：基础课程必须完成百分之几
							if(ele.getTrainingCourseStudent().getPercent() < time)
							{
								timeflag="0";
								break;
							}
				  		}
				  	    //判断是否满足报名条件2：作业是否必须完成
						if(homework.equals("是"))
						{
							for (PrBzzTchStuElective ele : list)
					  		{
								sqlpaper = 
									"select id\n" +
									"  from (select id, group_id, status\n" + 
									"          from (select id, group_id, status from test_homeworkpaper_info)\n" + 
									"         where status = '1'\n" + 
									"           and group_id = '"+ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId()+"')";
								List paperList=null;
								paperList=this.getGeneralService().getBySQL(sqlpaper);
								Iterator it=paperList.iterator();
								String paperId="";
								for(int i=0;i<paperList.size();i++){
									
									//java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
									//paperId = entry.getKey().toString();
									
									paperId = paperList.get(i).toString();
									sqlhomework=
										"select id, user_id, testpaper_id\n" +
										"  from (select a.id,\n" + 
										"               a.user_id,a.t_user_id,\n" + 
										"               testpaper_id,\n" + 
										"               b.group_id\n" + 
										"          from test_homeworkpaper_history a,\n" + 
										"               test_homeworkpaper_info    b,\n" + 
										"               pe_bzz_student             c\n" + 
										"         where a.testpaper_id = b.id\n" + 
										"           and a.user_id = ('(' || c.id || ')' || c.true_name)\n" + 
										"         order by a.ischeck desc, test_date desc)\n" + 
										" where t_user_id = '"+id+"'\n" + 
										"   and testpaper_id = '"+paperId+"'\n" + 
										"   and group_id = '"+ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId()+"'";
									List homeworkList=null;
									homeworkList=this.getGeneralService().getBySQL(sqlhomework);
									if(homeworkList !=null)
									{
										homeworkflag="0";
										break;
									}
								}
						    }
			  		    }
						//判断是否满足报名条件3：在线自测是否必须完成
						if(test.equals("是"))
						{
							for (PrBzzTchStuElective ele : list)
					  		{
								sqltest = 
									"select maxscore\n" +
									"  from (select nvl(max(to_number(score)), 0) as maxscore\n" + 
									"          from (select nvl(ty.score, 0) as score\n" + 
									"                  from test_testpaper_history ty\n" + 
									"                 inner join test_testpaper_info ti on ty.testpaper_id = ti.id\n" + 
									"                                                  and ty.user_id like\n" + 
									"                                                      '%"+id+"%'\n" + 
									"                                                  and ti.group_id =\n" + 
									"                                                      '"+ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getId()+"'))\n" + 
									" where maxscore <60";
				
								List testList=null;
								testList=this.getGeneralService().getBySQL(sqltest);
								if(testList !=null)
								{
									evaluateflag="0";
									break;
								}
						    }
			  		    }
						//判断是否满足报名条件4：课程评估是否必须完成
						if(evaluate.equals("是"))
						{
							for (PrBzzTchStuElective ele : list)
					  		{
							
								sqlevaluate = "select * from pe_bzz_assess a,pe_bzz_student stu \n"
									+ "where  a.fk_student_id=stu.id and stu.id = '"+id+"' \n"
									+ "      and a.fk_course_id='" + ele.getPrBzzTchOpencourse().getId() + "'";
								List evaluateList=null;
								evaluateList=this.getGeneralService().getBySQL(sqlevaluate);
								if(evaluateList.size()==0)
								{
									evaluateflag="0";
									break;
								}
							
						    }
			  		    }
						if(timeflag.equals("0")||homeworkflag.equals("0")
								||testflag.equals("0")||evaluateflag.equals("0")){
						}
						else
						{
							String update = 
								"update pe_bzz_examscore t\n" +
								"   set t.status = (select id\n" + 
								"                     from enum_const c\n" + 
								"                    where c.code = '1'\n" + 
								"                      and c.namespace = 'FlagExamScoreStatus')\n" + 
								" where t.student_id='"+id+"'";
							this.getGeneralService().executeBySQL(update);
							
						}
					}
				}
				else
				{
					return "no_student";
				}
				*/
			} 
			if (status != null && status.equals("0")) {
				//DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
				//DetachedCriteria prdc = score.createCriteria("peBzzExamBatch","peBzzExamBatch");
				//prdc.add(Restrictions.eq("id", batchid));
				//score.setProjection(Property.forName("id"));
				//List<String> studentList = this.getGeneralService().getList(score);
				//if(studentList.size()>0)
				//{
						String update = 
							"update pe_bzz_examscore t\n" +
							"   set t.status = (select id\n" + 
							"                     from enum_const c\n" + 
							"                    where c.code = '0'\n" + 
							"                      and c.namespace = 'FlagExamScoreStatus')\n" + 
							" where t.exambatch_id='"+batchid+"'";
						this.getGeneralService().executeBySQL(update);
				//}
				//else
				//{
				//	return "no_student";
				//}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	return "confirm_status";
	}
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath("/incoming/Excel");
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public PeBzzExamScoreService getPeBzzExamScoreService() {
		return peBzzExamScoreService;
	}

	public void setPeBzzExamScoreService(PeBzzExamScoreService peBzzExamScoreService) {
		this.peBzzExamScoreService = peBzzExamScoreService;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getConfirm_count() {
		return confirm_count;
	}

	public void setConfirm_count(int confirm_count) {
		this.confirm_count = confirm_count;
	}

	public String getConfirm_status() {
		return confirm_status;
	}

	public void setConfirm_status(String confirm_status) {
		this.confirm_status = confirm_status;
	}

	public List getPeBzzExamScores() {
		return peBzzExamScores;
	}

	public void setPeBzzExamScores(List peBzzExamScores) {
		this.peBzzExamScores = peBzzExamScores;
	}

	public String getScore_type() {
		return score_type;
	}

	public void setScore_type(String score_type) {
		this.score_type = score_type;
	}

}
