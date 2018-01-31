package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 *	单个选课选择学生
 * @author sunjicheng
 *
 */
public class PrBzzTchSelectStudentAction extends MyBaseAction<PeBzzStudent> {

	private String batchId;
	private String opTag;//操作标志，选课/删除选课
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle("课程列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("性别"), "gender");
		this.getGridConfig().addColumn(this.getText("出生日期"), "birthdayDate");
		this.getGridConfig().addColumn(this.getText("民族"), "folk");
		this.getGridConfig().addColumn(this.getText("学历"), "education");
		this.getGridConfig().addColumn(this.getText("职务"), "position");
		this.getGridConfig().addColumn(this.getText("职称"), "title");
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilePhone");
		this.getGridConfig().addColumn(this.getText("电子邮件"), "email");
		this.getGridConfig().addColumn(this.getText("所在企业"),
				"peEnterprise.name");
		this.getGridConfig().addColumn(this.getText("所在集团"),
				"peEnterprise.peEnterprise.name");

		// this.getGridConfig().addColumn(this.getText("评估结束时间"),"peBzzBatch.id",false,false,false,
		// "");

		
		if(this.getOpTag()!= null && this.getOpTag().equals("delete")) {
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
			this.getGridConfig().addMenuFunction(this.getText("下一步"),
					"/entity/teaching/prBzzTchSelectCourses_selectCourseForDel.action?opTag=delete",
					false, false);
			
		} else {
		this.getGridConfig().addMenuScript(this.getText("上一步"), "{history.go(-2)}");
		this.getGridConfig().addMenuFunction(this.getText("下一步"),
				"/entity/teaching/prBzzTchSelectCourses_startSingleSelectCourses.action",
				false, false);
		}
		this.getGridConfig()
		.addMenuScript(this.getText("取消"),
				"{window.navigate('/entity/teaching/prBzzTchOpenCourse.action');}");
	//	this.getGridConfig().addMenuScript(this.getText("取消"),
	//	"{window.navigate('/entity/teaching/prBzzTchOpenCourse.action');}");
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		if(this.getBatchId()==null || this.getBatchId().equals("")) {
			batchId = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("PrBzzTchBatchId");
		} else {
			ServletActionContext.getRequest().getSession().setAttribute("PrBzzTchBatchId", batchId);
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("peEnterprise","peEnterprise");
		dc.createCriteria("peEnterprise.peEnterprise","peEnterprise.peEnterprise",DetachedCriteria.LEFT_JOIN);


		//当删除选课时，查询该学期下有那些学生有选课记录
		if(this.getOpTag()!= null && this.getOpTag().equals("delete")) {
			DetachedCriteria tdc = DetachedCriteria.forClass(TrainingCourseStudent.class);
			tdc.createCriteria("ssoUser");
			tdc.createCriteria("prBzzTchOpencourse","prBzzTchOpencourse");
			tdc.createCriteria("prBzzTchOpencourse.peBzzBatch" ,"ppb");
//			tdc.setProjection(Property.forName("ssoUser.id"));
			tdc.setProjection(Projections.distinct(Projections.property("ssoUser.id")));

			tdc.add(Restrictions.eq("ppb.id", batchId));
			List ssoIdList = null;
			try {
				ssoIdList = (List) this.getGeneralService().getList(tdc);
				
				if(ssoIdList != null && ssoIdList.size()!= 0) {
					int start = 0;
					int end = 999;
					Criterion condition = Restrictions.or(Expression.sql("1=2"), Expression.sql("1=2"));
					
					//当Sql in子句的集合长度大于1000时，会出错，所以当集合长度大于1000时，要分割成子集，分别用in连接，多个in之间用or连接
					while(true) {
						List subList = null;
						Criterion tempcond = null;
						if(ssoIdList.size() >= end) {
							subList = ssoIdList.subList(start, end);
							tempcond = Restrictions.in("ssoUser.id", subList);
							condition = Restrictions.or(condition, tempcond);
						} else {
							
							subList = ssoIdList.subList(start, ssoIdList.size());
							tempcond = Restrictions.in("ssoUser.id", subList);
							condition = Restrictions.or(condition, tempcond);
							break;
						}
						
						start = end;
						end += 999;
					}
					dc.add(condition);
					
					
				} else {
					dc.add(Expression.sql("1=2"));
				}
				
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		dc.add(Restrictions.eq("peBzzBatch.id", batchId));

		return dc;
	}
	
	@Override
	public String execute() {
		ServletActionContext.getRequest().getSession().setAttribute(
				"PrBzzTchBatchId", this.getBatchId());
		
		ServletActionContext.getRequest()
		.getSession().setAttribute("PrBzzTchCourseIds", this.getIds());
		return super.execute();
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prBzzTchSelectStudent";
	}

	public String getOpTag() {
		return opTag;
	}

	public void setOpTag(String opTag) {
		this.opTag = opTag;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
}
