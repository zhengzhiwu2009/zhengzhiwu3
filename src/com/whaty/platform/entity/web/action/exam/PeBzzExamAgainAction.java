package com.whaty.platform.entity.web.action.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzExamAgain;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBzzExamAgainAction extends MyBaseAction<PeBzzExamAgain> {
	private String id;
	private String note;
	
	private String ids;
	private String cost;	//补考费用
	private String msg;
	private String studentList;	//学生
	
	private String status;	//状态

	@Override
	public void initGrid() {
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		if(us.getUserLoginType().equals("3")){
			this.getGridConfig().setCapability(false, true, false);
		}else{
			this.getGridConfig().setCapability(false, false, false);
		}
	
		
		this.getGridConfig().setTitle("补考信息");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,false,true,"TextField");
		this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,true,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("移动电话"),"peBzzStudent.mobilePhone",true,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("学期"),"peBzzStudent.peBzzBatch.name",true,false,true,"TextField",false,50);
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),"peBzzStudent.peEnterprise.name");
		c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null");
		this.getGridConfig().addColumn(c_name1);
		this.getGridConfig().addColumn(this.getText("考试批次"),"peBzzExamBatch.name",true,true,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate",false);
		this.getGridConfig().addColumn(this.getText("初审时间"), "firstDate",false,false,false,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("审核时间"), "finalDate",false);
		this.getGridConfig().addColumn(this.getText("费用(元)"), "cost",false);
		this.getGridConfig().addColumn(this.getText("状态"),"enumConstByFlagExamAgainStatus.name",true,false,true,"TextField");
		this
		.getGridConfig()
		.addRenderScript(
				this.getText("操作"),
				"{return '<a href=/entity/manager/exam/reject_examAgain.jsp?id='+record.data['id']+'&type=enterprise>驳回</a>';}",
				"");
//		this
//				.getGridConfig()
//				.addRenderScript(
//						this.getText("学员人数"),
//						"{return '<a href=peDetail.action?id='+record.data['id']+'&type=allnum><font color=#0000ff ><u>'+record.data['stunum']+'</u></font></a>';}",
//						"");
		if(us.getUserLoginType().equals("3"))	
		{
			this.getGridConfig().addMenuFunction("审核", "checked", "1");
			this.getGridConfig().addMenuFunction("取消审核", "nochecked", "1");
//			this.getGridConfig().addMenuScript("添加费用js", "{window.open('/entity/exam/peBzzExamAgain_addCost.action')}");
//			this.getGridConfig().addMenuFunction("添加补考费用", "/entity/exam/peBzzExamAgain_addCost.action", "id", false, false);
		}
		
	}

	
	public String reject(){
		DetachedCriteria detachedCriteria = DetachedCriteria
		.forClass(PeBzzExamAgain.class);
		detachedCriteria.add(Restrictions.eq("id", id));
		try{
			List examAgainList=this.getGeneralService().getList(detachedCriteria);
			if(examAgainList.size()!=0){
				PeBzzExamAgain examAgain = (PeBzzExamAgain)examAgainList.get(0);
				examAgain.setNote(note);
				examAgain.setEnumConstByFlagExamAgainStatus(enumConstByFlagExamAgainStatus());
				this.getGeneralService().save(examAgain);
			}
			this.status = "examAgainReject";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "reject";
	}
	private EnumConst enumConstByFlagExamAgainStatus() {
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagExamAgainStatus"));
		dc.add(Restrictions.eq("code", "3"));
		EnumConst ec = null;
		try {
			ec = (EnumConst) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ec;

	}

	
	
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzExamAgain.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/exam/peBzzExamAgain";
	}

	public void setBean(PeBzzExamAgain instance) {
		super.superSetBean(instance);
	}

	public PeBzzExamAgain getBean() {
		return super.superGetBean();
	}
	
//MenuFunction  方法
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	@Override
	public Map updateColumn() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
//		if (this.getIds() == null || this.getIds().split(",").length > 1) {
//			map.put("success", false);
//			map.put("info", this.getText("只能操作一条记录!"));
//			return map;
//		}
		if (this.getColumn().equals("checked")) {
			for(int i=0;i<this.getIds().split(",").length;i++){
				DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(PeBzzExamAgain.class);
					detachedCriteria.add(Restrictions.eq("id", this.getIds().split(",")[i]));
					List<PeBzzExamAgain> list = null;
					try {
						list = this.getGeneralService().getList(detachedCriteria);
					} catch (EntityException e1) {
						e1.printStackTrace();
					}
		
					for (PeBzzExamAgain peBzzBatch : list) {
						if (peBzzBatch.getEnumConstByFlagExamAgainStatus().getCode().equals("3")){
							map.put("success", true);
							map.put("info", this.getText("当前补考申请已经被驳回,操作失败!"));
							return map;
						}
						if (peBzzBatch.getEnumConstByFlagExamAgainStatus().getCode().equals("2")) {
							map.put("success", true);
							map.put("info", this.getText("当前已经是审核状态"));
							return map;
					}
						if (peBzzBatch.getEnumConstByFlagExamAgainStatus().getCode().equals("0")&&us.getUserLoginType().equals("3")) {
								Date final_date = new Date(); 
								EnumConst enumConstByFlagExamAgainStatus = this.getMyListService().getEnumConstByNamespaceCode("FlagExamAgainStatus", "2");
								peBzzBatch.setEnumConstByFlagExamAgainStatus(enumConstByFlagExamAgainStatus);
								peBzzBatch.setFinalDate(final_date);
								DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamScore.class);
								try {
									this.getGeneralService().save(peBzzBatch);
								} catch (EntityException e) {
									e.printStackTrace();
								}
						}
					}
			}
			map.put("success", true);
			map.put("info", this.getText("状态已经成功改变"));
			return map;
		}
		if(this.getColumn().equals("nochecked")){
			for(int i=0;i<this.getIds().split(",").length;i++){
					DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(PeBzzExamAgain.class);
					detachedCriteria.add(Restrictions.eq("id", this.getIds().split(",")[i]));
					List<PeBzzExamAgain> list = null;
					try {
						list = this.getGeneralService().getList(detachedCriteria);
					} catch (EntityException e1) {
						e1.printStackTrace();
					}
					for (PeBzzExamAgain peBzzBatch : list) {
						if (peBzzBatch.getEnumConstByFlagExamAgainStatus().getCode().equals("3")){
							map.put("success", true);
							map.put("info", this.getText("当前补考申请已经被驳回,操作失败!"));
							return map;
						}
						if(peBzzBatch.getEnumConstByFlagExamAgainStatus().getCode().equals("0")){
							map.put("success", true);
							map.put("info", this.getText("当前已经是未审核状态"));
							return map;	
						} 
						if (peBzzBatch.getEnumConstByFlagExamAgainStatus().getCode().equals("2")) {
								Date finalDate = null;
								EnumConst enumConstByFlagExamAgainStatus = this.getMyListService().getEnumConstByNamespaceCode("FlagExamAgainStatus", "0");
								peBzzBatch.setEnumConstByFlagExamAgainStatus(enumConstByFlagExamAgainStatus);
								peBzzBatch.setFinalDate(finalDate);
								try {
									this.getGeneralService().save(peBzzBatch);
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
	
//add校验
	
	public void checkBeforeAdd() throws EntityException {
		EnumConst getEnumConstByFalgExamLateStatus = this.getMyListService().getEnumConstByNamespaceCode("FlagExamAgainStatus", "2");
		this.getBean().setEnumConstByFlagExamAgainStatus(getEnumConstByFalgExamLateStatus);
	}
	
	//update校验
	
	public void checkBeforeUpdate() throws EntityException {
		
	}
	
	//懒加载
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamAgain.class);
		dc.createCriteria("peBzzExamBatch", "peBzzExamBatch", DetachedCriteria.INNER_JOIN);
		dc.createCriteria("enumConstByFlagExamAgainStatus", "enumConstByFlagExamAgainStatus", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria pebzz =dc.createCriteria("peBzzStudent","peBzzStudent", DetachedCriteria.INNER_JOIN);
		pebzz.createAlias("peBzzBatch", "peBzzBatch",DetachedCriteria.LEFT_JOIN);
		pebzz.createAlias("peEnterprise", "peEnterprise",DetachedCriteria.LEFT_JOIN);
		
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
	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamAgain.class);
		dc.createCriteria("enumConstByFlagExamAgainStatus","enumConstByFlagExamAgainStatus");
		dc.add(Restrictions.in("id", idList));
		dc.add(Restrictions.eq("enumConstByFlagExamAgainStatus.namespace", "FlagExamAgainStatus"));
		dc.add(Restrictions.or(Restrictions.eq("enumConstByFlagExamAgainStatus.code", "2"), Restrictions.eq("enumConstByFlagExamAgainStatus.code", "3")));
		List list = this.getGeneralService().getList(dc);
		
		if(list != null && list.size()!=0) {
			throw new EntityException("该学员补考申请已经审核通过或被驳回，不能删除!");
		}
	}
	
	public String addCost() {
		
		this.ids = this.getIds();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamAgain.class);
		dc.createCriteria("peBzzStudent", "peBzzStudent", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.in("id", ids.split(",")));
		List<PeBzzExamAgain> list = null;
		try {
			list = this.getGeneralService().getList(dc);
			StringBuffer buf = new StringBuffer();
			if(list != null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					buf.append(list.get(i).getPeBzzStudent().getTrueName());
					if(i < list.size() -1){
						buf.append("、");
					}
				}
			}
			this.setStudentList(buf.toString());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "addCost";
	}

	public String saceCost(){
		List<PeBzzExamAgain> list = new ArrayList<PeBzzExamAgain>();
		String[] idList = this.getIds().split(",");
		for(int i = 0; i < idList.length; i++){
			PeBzzExamAgain bean = null;
			try {
				bean = this.getGeneralService().getById(idList[i]);
				bean.setCost(this.getCost());
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(bean);
		}
		int count = 0;
		try{
//			count = this.getPeTchCourseService().save_uploadCourse(this.getUpload());
			list = this.getGeneralService().saveList(list);
			this.setMsg("操作成功！\r\n共为" + list.size() + "位学员设置补考费用！");
		}catch(Exception e){
			this.setMsg(e.getMessage());
		}
		return "saveCostSucc";
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getStudentList() {
		return studentList;
	}


	public void setStudentList(String studentList) {
		this.studentList = studentList;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
}
