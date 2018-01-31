package com.whaty.platform.entity.web.action.information.vote;

import java.util.List;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PrVoteSubQuestion;
import com.whaty.platform.entity.bean.PrVoteUserQuestion;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrVoteUserQuestionAction extends MyBaseAction{
	public void setBean(PrVoteUserQuestion instance) {
		super.superSetBean(instance);

	}
	public PrVoteUserQuestion getBean() {
		return (PrVoteUserQuestion) super.superGetBean();
	}
	/**
	 * 添加题目操作
	 * @return
	 */
	public String addUserQuestion() {
		try {
			this.getGeneralService().save(this.getBean());
			this.setMsg("添加成功！");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("添加失败！");
		}
		return "msg";
	}
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PrVoteUserQuestion.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/information/prVoteUserQuestion";
	}
}
