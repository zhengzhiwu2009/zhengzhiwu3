package com.whaty.platform.entity.web.action.basic;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PrSignUpOrderCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PrSignUpOrderCourseAction extends MyBaseAction<PrSignUpOrderCourse>{	
	
	private String signUpId;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		this.getGridConfig().addColumn(this.getText("课程编号"),"peBzzTchCourse.code",true,false,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("课程名称"),"peBzzTchCourse.name",true,false,true,"TextField",false,100);
		this.getGridConfig().addColumn(this.getText("课程性质"),"peBzzTchCourse.enumConstByFlagCourseType.name",true,false,true,"TextField",false,100);
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PrSignUpOrderCourse.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/prSignUpOrderCourse";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrSignUpOrderCourse.class);
		dc.createCriteria("peBzzTchCourse","peBzzTchCourse");
		dc.createCriteria("peBzzTchCourse.enumConstByFlagCourseType","peBzzTchCourse.enumConstByFlagCourseType");
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
		String[] orderCourseIds = this.getIds().split(",");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PrSignUpOrderCourse.class);
		detachedCriteria.createCriteria("peSignUpOrder", "peSignUpOrder");
		detachedCriteria.add(Restrictions.eq("id", orderCourseIds[0]));
		List orderCourseList = this.getGeneralService().getList(detachedCriteria);
		if(orderCourseList.size() > 0){
			PrSignUpOrderCourse orderCourse = (PrSignUpOrderCourse)orderCourseList.get(0);
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc.createCriteria("peSignUpOrder", "peSignUpOrder");
			dc.add(Restrictions.eq("peSignUpOrder.id", orderCourse.getPeSignUpOrder().getId()));
			List orderList = this.getGeneralService().getList(dc);
			if(orderList.size() > 0){
				throw new EntityException("该报名单已经生成了订单，不能再删除选课课程.");
			}
		}
	}

}
