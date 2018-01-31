package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.math.BigDecimal;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PeCourseManagerAction extends MyBaseAction<PeBzzTchCourse> {

	private static final long serialVersionUID = 9078813946660029710L;
	private String msg;
	private File upload;
	private PeTchCourseService peTchCourseService;

	public PeTchCourseService getPeTchCourseService() {
		return peTchCourseService;
	}

	public void setPeTchCourseService(PeTchCourseService peTchCourseService) {
		this.peTchCourseService = peTchCourseService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid");
		return dc;
	}
	
	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);
		
	}
	
	public PeBzzTchCourse getBean(){
		return super.superGetBean();
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
//		BigDecimal b1 = new BigDecimal(this.getBean().getScoreHomeworkRate().toString());
//		BigDecimal b2 = new BigDecimal(this.getBean().getScoreUsualRate().toString());
//		BigDecimal b3 = new BigDecimal(this.getBean().getScoreExamRate().toString());
//		if (b1.add(b2).add(b3).doubleValue() != 1) {
//			throw new EntityException("三种比例之和应该等于 1 。");
//		}
	}
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		this.checkBeforeUpdate();
	}

	public String batch(){
		return "batch";
	}
	public String uploadCourse(){
		int count = 0;
		try{
			count = this.getPeTchCourseService().save_uploadCourse(this.getUpload());
		}catch(Exception e){
			this.setMsg(e.getMessage());
			return "uploadCourse_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "uploadCourse_result";
	}
	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("课程题库列表"));

		/*this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("课程代码"), "code");
		this.getGridConfig().addColumn(this.getText("考试成绩比例"),"scoreExamRate", false, true, true,Const.scale_for_extjs);
		this.getGridConfig().addColumn(this.getText("平时成绩比例"),"scoreUsualRate", false, true, true,Const.scale_for_extjs);
		this.getGridConfig().addColumn(this.getText("作业成绩比例"),"scoreHomeworkRate", false, true, true,Const.scale_for_extjs);
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("课程简介"), "note", false, true, true, "TextArea", true, 1000);
		this.getGridConfig().addRenderFunction(this.getText("设置课程教师"), "<a href=\"prTchCourseTeacher.action?bean.peTchCourse.id=${value}\"  target='_self'>设置课程教师</a>","id");
		this.getGridConfig().addRenderFunction(this.getText("查看历史教材"), "<a href=\"courseBookHis.action?ids=${value}\"  target='_blank'>查看历史教材</a>","id");
		this.getGridConfig().addRenderFunction(this.getText("查看历史课件"), "<a href=\"courseCoursewareHis.action?ids=${value}\"  target='_blank'>查看历史课件</a>","id");*/
		
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "code",false,true,true,Const.coursecode_for_extjs);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("课程性质"),"enumConstByFlagCourseType.name",true,false,false,"");
		this.getGridConfig().addColumn(this.getText("课程类别"), "enumConstByFlagCourseCategory.name",true,false,false,"");
		this.getGridConfig().addColumn(this.getText("主讲人"),"teacher",true,false,false,"");
		this.getGridConfig().addColumn(this.getText("学时数"),"time",true, false,false,"");
		this.getGridConfig().addColumn(this.getText("发布状态"), "enumConstByFlagIsvalid.name",true);
//		this.getGridConfig().addColumn(this.getText("课后测验题"), "time",false);
//		this.getGridConfig().addColumn(this.getText("随堂练习题"), "time",false);
		
		this.getGridConfig().addRenderFunction(this.getText("课后测验题"), "<a href=\"/sso/bzzinteraction_InteractiontkManage.action?course_id=${value}&teacher_id=teacher1\"  target='_blank'>24</a>","id");
		this.getGridConfig().addRenderFunction(this.getText("随堂练习题"), "<a href=\"/sso/bzzinteraction_InteractiontkManage.action?course_id=${value}&teacher_id=teacher1\"  target='_blank'>30</a>","id");

		
		this.getGridConfig().addRenderFunction(this.getText("题库管理"), "<a href=\"/sso/bzzinteraction_InteractiontkManage.action?course_id=${value}&teacher_id=teacher1\"  target='_blank'>题库管理</a>","id");

	}


	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzTchCourse.class;
		
	}


	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peCourseManager";
		
	}
}
