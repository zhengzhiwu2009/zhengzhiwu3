package com.whaty.platform.entity.web.action.information.vote;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteSubQuestion;
import com.whaty.platform.entity.bean.PrVoteUserQuestion;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 调查问卷 问题管理
 * 
 * @author 李冰
 * 
 */
public class PrVoteQuestionAction extends MyBaseAction {
	String peVotePaper_id;// 调查问卷ID
	List<EnumConst> questionTypeList;// 问题类型
	String question;// 所选择的问题类型
	private String status;
	private String str;
	private Long odr;// questionOrder
	private List prVoteSubQuestionList;

	/**
	 * 转向添加问题页面
	 * 
	 * @return
	 */
	public String toAddQuestion() {
		this.setPeVotePaper_id(this.getBean().getPeVotePaper().getId());
		this.flagQuestionTypes();
		return "addQuestion";
	}

	/**
	 * 取得问题类型
	 */
	public void flagQuestionTypes() {
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagQuestionType"));

		try {
			List<EnumConst> questionTypeList = this.getGeneralService()
					.getList(dc);
			this.setQuestionTypeList(questionTypeList);
		} catch (EntityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 添加题目操作
	 * 
	 * @return
	 */
	public String addQuestion() {
		try {
			this.getBean().setPeVotePaper(
					(PeVotePaper) this.getMyListService().getById(
							PeVotePaper.class, this.getPeVotePaper_id()));
			this.getBean().setEnumConstByFlagQuestionType(
					this.getMyListService().getEnumConstByNamespaceCode(
							"FlagQuestionType", this.getQuestion()));
			Long querstionOrder = this.getBean().getQuestionOrder();
			List<PrVoteQuestion> list = new ArrayList<PrVoteQuestion>();
			DetachedCriteria questions = DetachedCriteria
					.forClass(PrVoteQuestion.class);
			DetachedCriteria paper = questions.createCriteria("peVotePaper",
					"peVotePaper");
			paper.add(Restrictions.eq("peVotePaper.id", this
					.getPeVotePaper_id()));
			list = this.getGeneralService().getList(questions);
			int max =0;
			for (int i = 0; i < list.size(); i++) {
				PrVoteQuestion prVq = list.get(i);
				if(prVq.getQuestionOrder() !=null){
					if(prVq.getQuestionOrder()>max){
						max =new Long(prVq.getQuestionOrder()).intValue();//取出当前试卷的最大题目数
					}
					if (prVq.getQuestionOrder() >= querstionOrder) {
						prVq.setQuestionOrder(prVq.getQuestionOrder() + 1);
						this.getGeneralService().save(prVq);
					}
				}else{
					this.getGeneralService().save(prVq);
				}
			}
			if(querstionOrder >max){
				this.getBean().setQuestionOrder((long)max+1);
			}
			this.getGeneralService().save(this.getBean());

			if ("".equals(this.str) || null == this.str) {
				str = "/entity/information/peVotePaper_toVoteQuestion.action?bean.id="
						+ this.getPeVotePaper_id();
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

	/**
	 * 删除题目操作
	 * 
	 * @return
	 */
	public String delQuestion() {

		List list = new ArrayList();
		list.add(this.getBean().getId());
		//Long querstionOrder = this.odr;
		Long querstionOrder = this.getBean().getQuestionOrder();
		try {
			this.getGeneralService().deleteByIds(list);
			List<PrVoteQuestion> qList = new ArrayList<PrVoteQuestion>();
			DetachedCriteria questions = DetachedCriteria
					.forClass(PrVoteQuestion.class);
			DetachedCriteria paper = questions.createCriteria("peVotePaper",
					"peVotePaper");
			paper.add(Restrictions.eq("peVotePaper.id", this.getBean()
					.getPeVotePaper().getId()));
			qList = this.getGeneralService().getList(questions);
			for (int i = 0; i < qList.size(); i++) {
				PrVoteQuestion prVq = qList.get(i);
				if(prVq.getQuestionOrder() !=null && querstionOrder !=null){
					if (prVq.getQuestionOrder() >= querstionOrder) {
						prVq.setQuestionOrder(prVq.getQuestionOrder() - 1);
						this.getGeneralService().save(prVq);
					}
				}else{
					this.getGeneralService().save(prVq);
				}
			}

			if ("".equals(this.str) || null == this.str) {
				str = "/entity/information/peVotePaper_toVoteQuestion.action?bean.id="
						+ this.getBean().getPeVotePaper().getId();
			}
			setTogo(str);
			setMsg("删除成功");
		} catch (EntityException e) {
			e.printStackTrace();
			setMsg("删除失败");
			setTogo("back");
		}

		return "msg";
	}

	/**
	 * 转向修改题目页面
	 * 
	 * @return
	 */
	public String toEditQuestion() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String quesType =request.getParameter("quesType");
		
		this.flagQuestionTypes();
		try {
			this.setBean((PrVoteQuestion) this.getGeneralService().getById(
					this.getBean().getId()));

		} catch (EntityException e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("操作失败！");
		}
		request.setAttribute("quesType", quesType);
		return "editQuestion";
	}

	/**
	 * 修改题目操作
	 * 
	 * @return
	 */
	public String editQuestion() {
		try {
			this.getBean().setEnumConstByFlagQuestionType(
					this.getMyListService().getEnumConstByNamespaceCode(
							"FlagQuestionType", this.getQuestion()));
			PrVoteQuestion prVoteQuestion = (PrVoteQuestion) this
					.getGeneralService().getById(this.getBean().getId());
			this.getBean().setCreateDate(null);
			this.superSetBean((PrVoteQuestion) setSubIds(prVoteQuestion, this
					.getBean()));
			this.getGeneralService().save(prVoteQuestion);
			if ("".equals(this.str) || null == this.str) {
				str = "/entity/information/peVotePaper_toVoteQuestion.action?bean.id="
						+ this.getBean().getPeVotePaper().getId();
			} else {
				str += this.getBean().getPeVotePaper().getId();
			}
			setTogo(str);
			setMsg("修改成功");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("操作失败！");
		}
		return "msg";
	}

	// 满意度调查设置跳转页
	public String courseVote() {
		if ("".equals(this.getStatus()) || null == this.getStatus()) {
			this.setTogo("back");
			this.setMsg("跳转页面有错");
			return "msg";
		}

		if ("edit".equals(this.getStatus())) {
			str = "/entity/teaching/satisfactionPaperManager_toVoteQuestion.action?bean.id=";
			return editQuestion();
		} else if ("del".equals(this.getStatus())) {
			str = "/entity/teaching/satisfactionPaperManager_toVoteQuestion.action?bean.id="
					+ this.getBean().getPeVotePaper().getId();
			return delQuestion();
		} else if ("add".equals(this.getStatus())) {
			str = "/entity/teaching/satisfactionPaperManager_toVoteQuestion.action?bean.id="
					+ this.getPeVotePaper_id();
			return addQuestion();
		} else {
			this.setTogo("back");
			this.setMsg("跳转页面有错");
			return "msg";
		}
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrVoteQuestion.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/prVoteQuestion";

	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteQuestion.class);
		dc.createAlias("peVotePaper", "peVotePaper");
		return dc;
	}

	public void setBean(PrVoteQuestion instance) {
		super.superSetBean(instance);

	}

	public PrVoteQuestion getBean() {
		return (PrVoteQuestion) super.superGetBean();
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

	public Long getOdr() {
		return odr;
	}

	public void setOdr(Long odr) {
		this.odr = odr;
	}
	public String answerDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List voteSubList =new ArrayList ();
		try {
			this.setBean((PrVoteQuestion) this.getGeneralService().getById(
					this.getBean().getId()));
			voteSubList= this.getGeneralService().getBySQL(" select id,item_content from pr_vote_sub_question where fk_vote_question_id = '"+this.getBean().getId()+"' or FK_VOTE_QUESTION_ID IN (SELECT q.ID FROM PR_VOTE_QUESTION Q WHERE q.Fk_Parent_Id = '"+this.getBean().getId()+"') and iscustom ='1' order by create_date desc ");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("操作失败！");
		}
		request.setAttribute("voteSubList", voteSubList);
		return "answerDetail";
	}

	public List getPrVoteSubQuestionList() {
		return prVoteSubQuestionList;
	}

	public void setPrVoteSubQuestionList(List prVoteSubQuestionList) {
		this.prVoteSubQuestionList = prVoteSubQuestionList;
	}
}
