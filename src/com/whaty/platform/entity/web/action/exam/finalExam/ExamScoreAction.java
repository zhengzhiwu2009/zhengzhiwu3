package com.whaty.platform.entity.web.action.exam.finalExam;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzParty;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.finalExam.PrExamBookingService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
/**
 * 期末考试成绩单个、批量录入
 * @author zqf
 *
 */
public class ExamScoreAction extends MyBaseAction<PeBzzParty> {
	private File upload;
	private PrExamBookingService prExamBookingService;
	private String flag;
	
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 上传处理功能
	 */
	public String batchexe() {
		int count = 0;
		try {
			count = this.getPrExamBookingService().saveUploadScore(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "msg";
	}
	
	public PrExamBookingService getPrExamBookingService() {
		return prExamBookingService;
	}

	public void setPrExamBookingService(PrExamBookingService prExamBookingService) {
		this.prExamBookingService = prExamBookingService;
	}

	@Override
	public void initGrid() {
		
		
		
		if("examLib".equalsIgnoreCase(flag)){
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().setTitle(this.getText("在线考试题库管理"));
		}else{
			this.getGridConfig().setCapability(true, true, true);
			this.getGridConfig().setTitle(this.getText("在线考试管理"));
		}
		
		
		
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("考试名称"),"name",true,true,true,"");
		this.getGridConfig().addRenderFunction(this.getText("试题数量"), "20","id");
		if("examLib".equalsIgnoreCase(flag)){
			
			this.getGridConfig().addRenderFunction(this.getText("管理题库"), "<a href=\"/sso/bzzinteraction_InteractionExamtkManage.action?course_id=ff80808127a9ad060127ace6c4bb0019&teacher_id=teacher1\" target=\"_blank\">管理题库</a>","id");			
		}else{
			this.getGridConfig().addColumn(this.getText("开始时间"), "beginDate");
			this.getGridConfig().addColumn(this.getText("结束时间"), "endDate");
			
			this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagPartyState.name",true,true,true,"");
			this.getGridConfig().addMenuFunction( this.getText("设为有效"),"FlagIsvalid.true");
			this.getGridConfig().addMenuFunction(this.getText("设为无效"),"FlagIsvalid.false");
			
			this.getGridConfig().addRenderFunction(this.getText("查看学员"), "<a href=\"#?${value}\">查看学员</a>","id");
			this.getGridConfig().addRenderFunction(this.getText("添加学员"), "<a href=\"#?${value}\">添加学员</a>","id");
			
			this.getGridConfig().addRenderFunction(this.getText("查看试卷"), "<a href=\"#?${value}\">查看试卷</a>","id");
			this.getGridConfig().addRenderFunction(this.getText("添加试卷"), "<a href=\"#?${value}\">添加试卷</a>","id");
		}
		
		
		
		
		
		/*this.getGridConfig().addColumn(this.getText("学号"),"prTchStuElective.peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学生姓名"),"prTchStuElective.peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("考试课程"),"prTchStuElective.prTchProgramCourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"prTchStuElective.peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"prTchStuElective.peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"prTchStuElective.peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"prTchStuElective.peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("考试成绩"),"scoreExam",true,true,true,Const.score_for_extjs);
//		this.getGridConfig().addColumn(this.getText("考试成绩状态"),"enumConstByFlagScoreStatus.name",true,true,true,"TextField",true,50);
		ColumnConfig column = new ColumnConfig(this.getText("考试成绩状态"),"enumConstByFlagScoreStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4'");
		this.getGridConfig().addColumn(column);*/
		
		
	}
	
	
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzParty.class);		
			if(action.equals("FlagIsvalid.true"))
			{
				EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
				
				tempdc.add(Restrictions.in("id", ids));
				List<PeBzzParty> sslist = new ArrayList<PeBzzParty>();
				sslist =this.getGeneralService().getList(tempdc);
				Iterator<PeBzzParty> iterator = sslist.iterator();
				while(iterator.hasNext()){
					PeBzzParty peBzzParty = iterator.next();
					peBzzParty.setEnumConstByFlagPartyState(enumConst);
					this.getGeneralService().save(peBzzParty);
				}
			}
			
			if(action.equals("FlagIsvalid.false"))
			{
				EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
				
				tempdc.add(Restrictions.in("id", ids));
				List<PeBzzParty> sslist = new ArrayList<PeBzzParty>();
				sslist =this.getGeneralService().getList(tempdc);
				Iterator<PeBzzParty> iterator = sslist.iterator();
				while(iterator.hasNext()){
					PeBzzParty peBzzParty = iterator.next();
					peBzzParty.setEnumConstByFlagPartyState(enumConst);
					this.getGeneralService().update(peBzzParty);
				}
			}
			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
				
		}else{
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}
	

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzParty.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/examScore";
	}
	public void setBean(PeBzzParty instance){
		this.superSetBean(instance);
	}
	
	public PeBzzParty getBean(){
		return (PeBzzParty)this.superGetBean();
	}
	/*public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzParty.class);
		dc.createCriteria("peSemester", "peSemester");
		DetachedCriteria dcPrTchStuElective = dc.createCriteria("prTchStuElective", "prTchStuElective");
		dcPrTchStuElective.createCriteria("peStudent", "peStudent")
				.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
				.createAlias("peSite", "peSite")
				.createAlias("peEdutype", "peEdutype")
				.createAlias("peMajor", "peMajor")
				.createAlias("peGrade", "peGrade");
		dcPrTchStuElective.createCriteria("prTchProgramCourse", "prTchProgramCourse")
						.createCriteria("peTchCourse", "peTchCourse");
		dc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}*/
	//重写父类的update方法，以满足在更新预约表中成绩的时候，动态的修改选课表中的成绩
	/*public Map update() {
		
		Map map = new HashMap();
		
		PrExamBooking instance = new PrExamBooking();
		
		try {
			instance = (PrExamBooking)this.getGeneralService().getById(this.getBean().getId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.superSetBean((PrExamBooking)setSubIds(instance,this.getBean()));
		try {
			checkBeforeUpdate();
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		

		try {
			this.getPrExamBookingService().save_Score(this.getBean());
			map.put("success", "true");
			map.put("info", "更新成功");
			
		} catch (Exception e) {
			map.clear();
			map.put("success", "false");
			map.put("info", "更新失败");
			
		}

		return map;
	}
*/
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

}
