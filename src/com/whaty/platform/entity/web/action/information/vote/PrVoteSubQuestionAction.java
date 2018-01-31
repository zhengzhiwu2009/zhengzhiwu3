package com.whaty.platform.entity.web.action.information.vote;

import java.util.List;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteSubQuestion;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrVoteSubQuestionAction extends MyBaseAction{
	String peVotePaper_id;// 调查问卷ID
	List<EnumConst> questionTypeList;//问题类型
	String question;//所选择的问题类型
	private String status;
	private String str;
	public void setBean(PrVoteSubQuestion instance) {
		super.superSetBean(instance);

	}

	public String getPeVotePaper_id() {
		return peVotePaper_id;
	}

	public void setPeVotePaper_id(String peVotePaper_id) {
		this.peVotePaper_id = peVotePaper_id;
	}

	public List<EnumConst> getQuestionTypeList() {
		return questionTypeList;
	}

	public void setQuestionTypeList(List<EnumConst> questionTypeList) {
		this.questionTypeList = questionTypeList;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public PrVoteSubQuestion getBean() {
		return (PrVoteSubQuestion) super.superGetBean();
	}
	/**
	 * 添加题目操作
	 * @return
	 */
	public String addQuestion() {
		try {
			
		//	this.getBean().setPeVotePaper((PeVotePaper)this.getMyListService().getById(PeVotePaper.class, this.getPeVotePaper_id()));
		//	this.getBean().setEnumConstByFlagQuestionType(
		//			this.getMyListService().getEnumConstByNamespaceCode("FlagQuestionType", this.getQuestion()));
			this.getGeneralService().save(this.getBean());
			if("".equals(this.str)||null==this.str){
			 str = "/entity/information/peVotePaper_toVoteQuestion.action?bean.id="+this.getPeVotePaper_id();
			}
			this.setTogo(str);
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
		this.entityClass = PrVoteSubQuestion.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/information/prVoteSubQuestion";
	}

}
