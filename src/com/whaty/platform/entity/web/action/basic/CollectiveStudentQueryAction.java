package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class CollectiveStudentQueryAction extends MyBaseAction {
	private String flag = "";
	private String id;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		if (flag.equals("viewDetail")) {
			this.getGridConfig().setTitle(this.getText("课程详细"));
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"),
					"prBzzTchOpencourse.peBzzTchCourse.id", false);
			this.getGridConfig().addColumn(this.getText("课程名"),
					"prBzzTchOpencourse.peBzzTchCourse.name", true);
			this.getGridConfig().addColumn(this.getText("课程编号"),
					"prBzzTchOpencourse.peBzzTchCourse.code", true);
			this.getGridConfig().addColumn(this.getText("课时"),
					"prBzzTchOpencourse.peBzzTchCourse.time", true);
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
		} else {
			this.getGridConfig().setTitle(this.getText("学员查询"));
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"),
					"id", false);
			this.getGridConfig().addColumn(this.getText("学员姓名"),
					"trueName", true);
			this.getGridConfig().addColumn(this.getText("系统编号"),
					"regNo", true);
			this.getGridConfig().addColumn(this.getText("身份证号"),
					"cardNo", true);
			this
					.getGridConfig()
					.addRenderFunction(
							this.getText("查看课程"),
							"<a href=\"/entity/basic/collectiveStudentQuery_viewDetail.action?id=${value}&flag=viewDetail\") >查看</a>",
							"id");
		}

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/collectiveStudentQuery";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrBzzTchStuElective.class);
		if (flag.equals("viewDetail")) {
			dc.createCriteria("peBzzStudent", "peBzzStudent");
			dc.createCriteria("prBzzTchOpencourse", "op");
			dc.createCriteria("op.peBzzTchCourse", "peBzzTchCourse");
			dc.add(Restrictions.eq("peBzzStudent.id", this.id));
		} else {
			dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			dc.createCriteria("peBzzStudent", "peBzzStudent");
			try {
				List<PrBzzTchStuElective> list = this.getGeneralService().getList(dc);
				
				List ids = new ArrayList();
				for(PrBzzTchStuElective p : list) {
					PeBzzStudent s = p.getPeBzzStudent();
					if(!ids.contains(s.getId())) {
						ids.add(s.getId());
					}
				}
				dc = DetachedCriteria.forClass(PeBzzStudent.class);
				if(ids.size()>0){
					dc.add(Restrictions.in("id", ids.toArray(new String[ids.size()])));					
				}else{
					dc.add(Restrictions.eq("id", "0"));	
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dc;
	}

	public String viewDetail() {
		System.out
				.println("*****************************student.id=" + this.id);
		return "grid";
	}

}
