package com.whaty.platform.entity.web.action.basic;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PrSignUpOrderCourse;
import com.whaty.platform.entity.bean.PrSignUpOrderStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrSignUpOrderStudentAction extends MyBaseAction<PrSignUpOrderStudent>{
	
	private String signUpId;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		this.getGridConfig().addColumn(this.getText("系统编号"),"peBzzStudent.regNo",true,false,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,false,true,"TextField",false,100);
		this.getGridConfig().addColumn(this.getText("身份证号"),"peBzzStudent.cardNo",true,false,true,"TextField",false,100);
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PrSignUpOrderStudent.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/prSignUpOrderStudent";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrSignUpOrderStudent.class);
		dc.createCriteria("peBzzStudent","peBzzStudent");
		dc.createCriteria("peSignUpOrder","peSignUpOrder");
		dc.add(Restrictions.eq("peSignUpOrder.id",this.getSignUpId()));
		return dc;
	}

	public String getSignUpId() {
		return signUpId;
	}

	public void setSignUpId(String signUpId) {
		this.signUpId = signUpId;
	}
	
	@Override 
	public void checkBeforeDelete(List idList) throws EntityException{
		String[] orderStudentIds = this.getIds().split(",");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PrSignUpOrderStudent.class);
		detachedCriteria.createCriteria("peSignUpOrder", "peSignUpOrder");
		detachedCriteria.add(Restrictions.eq("id", orderStudentIds[0]));
		List orderCourseList = this.getGeneralService().getList(detachedCriteria);
		if(orderCourseList.size() > 0){
			PrSignUpOrderStudent orderStudent = (PrSignUpOrderStudent)orderCourseList.get(0);
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc.createCriteria("peSignUpOrder", "peSignUpOrder");
			dc.add(Restrictions.eq("peSignUpOrder.id", orderStudent.getPeSignUpOrder().getId()));
			List orderList = this.getGeneralService().getList(dc);
			if(orderList.size() > 0){
				throw new EntityException("该报名单已经生成了订单，不能再删除选课课程.");
			}
		}
	}

}
