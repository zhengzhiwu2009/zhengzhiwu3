package com.whaty.platform.entity.web.action.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamBatchSite;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzExamSite;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzExamSiteAction extends MyBaseAction<PeBzzExamSite>{

	@Override
	public void initGrid() {
		//this.getGridConfig().setCapability(true, true, true);
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getUserLoginType().equals("3")){
			this.getGridConfig().setCapability(true, true, true);
			this.getGridConfig().addMenuFunction(this.getText("设置到当前考试批次下"),"toBatch");
		}else{
			this.getGridConfig().setCapability(false,false,false);
		}
		this.getGridConfig().setTitle("考试地点");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name",true, true, true, "TextArea", false, 500);
		this.getGridConfig().addColumn(this.getText("省份"), "sheng");
		this.getGridConfig().addColumn(this.getText("城市"), "shi");
		this.getGridConfig().addColumn(this.getText("考点容量"),"capacity", false, true, true,Const.number_for_extjs);
		this.getGridConfig().addColumn(this.getText("详细地址"), "address",false, true, true, "TextArea", true, 200);
		this.getGridConfig().addColumn(this.getText("备注"),"note", false, true, true, "TextArea", true, 100);
		
		

	}

	public void setEntityClass() {
		this.entityClass = PeBzzExamSite.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/exam/peBzzExamSite";
	}

	public void setBean(PeBzzExamSite instance) {
		super.superSetBean(instance);
	}

	public PeBzzExamSite getBean() {
		return super.superGetBean();
	}
	//MenuFunction  方法
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria examBatch = DetachedCriteria.forClass(PeBzzExamBatch.class);
				examBatch.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
				examBatch.add(Restrictions.eq("enumConstByFlagExamBatch.id", "402880a91dadc115011dadfcf26b00aa"));
				PeBzzExamBatch peExamBatch = null;
				peExamBatch = this.getGeneralService().getExamBatch(examBatch);
			
			if(action.equals("toBatch"))
			{
				for(int i=0;i<ids.length;i++)
				{
					DetachedCriteria site = DetachedCriteria.forClass(PeBzzExamSite.class);
					site.add(Restrictions.eq("id", ids[i]));
					PeBzzExamSite examSite = null;
					examSite = (PeBzzExamSite) this.getGeneralService().getExamSite(site);
					
					peExamBatch = this.getGeneralService().getExamBatch(examBatch);
					PeBzzExamBatchSite batch_site = new PeBzzExamBatchSite();
					batch_site.setPeBzzExamBatch(peExamBatch);
					batch_site.setPeBzzExamSite(examSite);
					batch_site = (PeBzzExamBatchSite) this.getGeneralService().savePeBzzExamBatchSite(batch_site);
				}
			}
			
			map.put("success", "true");
			map.put("info", ids.length + "条记录操作成功");
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

	//add校验
	
	public void checkBeforeAdd() throws EntityException {
		
	}
	
	//update校验
	
	public void checkBeforeUpdate() throws EntityException {
		
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamSite.class);
		return dc;
	}
	
	@Override
	public Map delete() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				try{
					checkBeforeDelete(idList);
				}catch(EntityException e){
					map.put("success", "false");
					map.put("info", e.getMessage());
					return map;
				}
				map.put("success", "true");
				map.put("info", "删除成功");
				try{
					StringBuffer buffer = new StringBuffer();
					buffer.append("delete from pe_bzz_exambatch_site pbes where pbes.site_id in (");
					int length = idList.size();
					for(int k = 0; k < length; k++){
						buffer.append("'").append(idList.get(k)).append("'");
						if(k < length - 1){
							buffer.append(", ");
						}
					}
					buffer.append(")");
					this.getGeneralService().executeBySQL(buffer.toString());	//强制删除站点信息，即使设置为当前考试批次下也可以删除
					int i = this.getGeneralService().deleteByIds(idList);
					if(0 == i){
						map.clear();
						map.put("success", "false");
						map.put("info", "对不起，您的没有删除的权限");
					}
				}catch(RuntimeException e){
					return this.checkForeignKey(e);
				}catch(Exception e1){
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		// TODO Auto-generated method stub
		DetachedCriteria dc = null;
		dc  = DetachedCriteria.forClass(PeBzzExamScore.class);
		dc.createCriteria("peBzzExamBatchSite", "peBzzExamBatchSite", DetachedCriteria.INNER_JOIN).createCriteria("peBzzExamSite", "peBzzExamSite", DetachedCriteria.INNER_JOIN);
		dc.add(Restrictions.in("peBzzExamBatchSite.peBzzExamSite.id", idList));
		List list = new ArrayList();
		list = this.getGeneralService().getList(dc);
		if(null != list && list.size() > 0){
			throw new EntityException("已经有学员在该站点下报名，不能删除!");
		}
	}
	
	//懒加载

}
