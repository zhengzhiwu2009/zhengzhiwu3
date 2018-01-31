package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.SatisfactionPaperquestion;
import com.whaty.platform.entity.bean.SatisfactionQuestionInfo;
import com.whaty.platform.entity.bean.SatisfactionSurveyPaperInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class AddQuestionToSatisfyAction extends MyBaseAction<SatisfactionPaperquestion>{

	private String id;
	private String flag;
	
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
		this.getGridConfig().setTitle("满意度调查题目列表");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("题目名称"), "title");
		if("add".equals(flag)) {
			this.getGridConfig().addMenuFunction(this.getText("添加到问卷中"), "add");
		} else {
			this.getGridConfig().addMenuFunction(this.getText("从问卷中删除"), "del");
		}
		
		this.getGridConfig().addMenuScript(this.getText("返回"),"{history.back()}");
		ServletActionContext.getRequest().getSession().setAttribute("id", id);
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = SatisfactionPaperquestion.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/teaching/addQuestionToSatisfy";
	}
	public DetachedCriteria initDetachedCriteria() {
		String id = ActionContext.getContext().getSession().get("id").toString().trim();
//		System.out.println("*****************************"+id);
		DetachedCriteria criteria = DetachedCriteria.forClass(SatisfactionPaperquestion.class);
		criteria.setProjection(Projections.distinct(Property.forName("satisfactionQuestionInfo.id")));
		criteria.add(Restrictions.eq("satisfactionSurveyPaperInfo.id", id));
		criteria.createCriteria("satisfactionSurveyPaperInfo", "satisfactionSurveyPaperInfo");
		criteria.createCriteria("satisfactionQuestionInfo", "satisfactionQuestionInfo");
		
		DetachedCriteria question = DetachedCriteria.forClass(SatisfactionQuestionInfo.class);
		if(flag.equals("add")) {
			question.add(Subqueries.propertyNotIn("id", criteria));
		} else {
			question.add(Subqueries.propertyIn("id", criteria));
		}
		return question;
	}
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		String id = ActionContext.getContext().getSession().get("id").toString().trim();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = this.getIds().split(",");
			DetachedCriteria paper = DetachedCriteria.forClass(SatisfactionSurveyPaperInfo.class);
			paper.add(Restrictions.eq("id", id));
			try {
				if(action.equals("add")) {
					List<SatisfactionSurveyPaperInfo> paperList = this.getGeneralService().getList(paper);
					DetachedCriteria question = DetachedCriteria.forClass(SatisfactionQuestionInfo.class);
					question.add(Restrictions.in("id", ids));
					List<SatisfactionQuestionInfo> questionList = this.getGeneralService().getList(question);
					Iterator<SatisfactionQuestionInfo> it = questionList.iterator();
					while(it.hasNext()) {
						SatisfactionPaperquestion pq = new SatisfactionPaperquestion();
						pq.setSatisfactionQuestionInfo(it.next());
						pq.setSatisfactionSurveyPaperInfo(paperList.get(0));
						this.getGeneralService().save(pq);
					}
				} else if(action.equals("del")) {
					DetachedCriteria paperQuestion = DetachedCriteria.forClass(SatisfactionPaperquestion.class);
					paperQuestion.add(Restrictions.eq("satisfactionSurveyPaperInfo.id", id));
					paperQuestion.add(Restrictions.in("satisfactionQuestionInfo.id", ids));
					List<SatisfactionPaperquestion> list = this.getGeneralService().getList(paperQuestion);
					List listIds = new ArrayList();
					for(int i=0; i<list.size(); i++) {
						listIds.add(list.get(i).getId());
					}
					this.getGeneralService().deleteByIds(listIds);
				}
				
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
			map.put("success", "true");
			map.put("info", ids.length + "条记录操作成功");
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}
}
