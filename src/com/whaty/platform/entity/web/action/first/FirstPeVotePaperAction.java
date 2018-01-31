package com.whaty.platform.entity.web.action.first;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.bean.PrVoteSuggest;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.information.vote.PeVotePaperAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
/**
 * 首页访问调查问卷
 * @author 李冰
 *
 */
public class FirstPeVotePaperAction extends PeVotePaperAction { 
	private boolean success;//是否提交成功
	List<PrVoteSuggest> prVoteSuggestList;//建议 
	PeVotePaper peVotePaper;
	private String togo;
	private String opencourseId;
	

	/**
	 * 调查问卷列表
	 * @return
	 */
	public String toVoteList() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		DetachedCriteria dcEnumConstByFlagIsvalid = dc
			.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria enumConstByFlagQualificationsType = dc
			.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		enumConstByFlagQualificationsType.add(Restrictions.or(Restrictions.eq("code", "0"),Restrictions.eq("code", "1")));
		dc.addOrder(Order.desc("foundDate"));
		try {
			this.setPeVotePaperList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "voteList";
	}
	
	private String getFristVote() throws EntityException{
		DetachedCriteria firstdc = DetachedCriteria.forClass(PeVotePaper.class);
		DetachedCriteria dcEnumConstByFlagIsvalid = firstdc
		.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
	DetachedCriteria enumConstByFlagQT = firstdc
		.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType");
	dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
	enumConstByFlagQT.add(Restrictions.or(Restrictions.eq("code", "0"),Restrictions.eq("code", "1")));
	firstdc.addOrder(Order.desc("foundDate"));
	
		List<PeVotePaper> list = this.getGeneralService().getList(firstdc);
		if(list.size()>0){
			this.setPeVotePaper(list.get(0));
		}
		return "firstvote";
	}
	
	
	/**
	 * 网站前台进入投票页面
	 * @return
	 */
	public String toVote() {
		if("".equals(this.getBean().getId())||null==this.getBean().getId()||"null".equals(this.getBean().getId())){
			this.togo="back";
			return "msg";
		}
		try {
			this.setPeVotePaper((PeVotePaper)this.getGeneralService().getById(PeVotePaper.class,this.getBean().getId()));
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.getPeVotePaper()==null){
			this.togo="back";
			return "msg";
		}
		this.toSetVotePaperQuestion();
		if(this.getPeVotePaper()==null){
			this.togo="back";
			return "msg";
		}
		this.setCanVote(1);
		this.setPastDue(0);
		//检查用户是否可以投票
		this.checkCanVote();
		


		/**
		 * 判断时间是否在投票日期内
		 */
		Date now = new Date();
		if (now.before(this.getPeVotePaper().getStartDate())) {
			this.setPastDue(1);
		} else if (!Const.compareDate(now, this.getPeVotePaper().getEndDate())){
			this.setPastDue(2);
		}
		
		return "vote";
	}
	
	
	/**
	 * 投票处理页面
	 * @return
	 */
	public String vote() {
		
		if(this.formToken != null && !"".equals(this.formToken)) {
			if(!this.validateToken(this.formToken)) {
				return "tokenError";
			}
		}
		try {
			//增加空判断
			if(this.getPeVotePaper() == null || this.getPeVotePaper().getId() == null){
				this.setMsg("数据错误！");
				return "result";
			}
			PeVotePaper votePaper = (PeVotePaper)this.getGeneralService().getById(PeVotePaper.class,this.getPeVotePaper().getId());
			if(votePaper == null){
				this.setMsg("数据错误！");
				return "result";				
			}
			
			this.setPeVotePaper(votePaper);
			this.setCanVote(1);
			//为了防止用户反复投票，还要检查一次是否可以投票
			/**
			 * 如果调查问卷限制IP，则检查用户的IP是否已经投票
			 */
			if (this.getPeVotePaper().getEnumConstByFlagLimitDiffip()!=null &&
					this.getPeVotePaper().getEnumConstByFlagLimitDiffip().getCode()!=null &&
					"1".equals(this.getPeVotePaper().getEnumConstByFlagLimitDiffip().getCode())) {
				//得到用户的IP
				HttpServletRequest request = ServletActionContext. getRequest();
				String ip = request.getRemoteAddr();
				
				DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
				DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
				dcPeVotePaper.add(Restrictions.eq("id", this.getPeVotePaper().getId()));
				dc.add(Restrictions.eq("ip", ip));
				try {
					List list = this.getGeneralService().getList(dc);
					//如果IP已经存在则设置为不能投票
					if (list.size()>0) {
						this.setCanVote(0);
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
			
			
			if (this.getCanVote()==1) {
				/**
				 * 如果调查问卷限制session，则检查用户session是否已经投票
				 */
				if (this.getPeVotePaper().getEnumConstByFlagLimitDiffsession().getCode().equals("1")) {
					//得到用户session
					HttpSession session = ServletActionContext. getRequest().getSession();
					String sessionId = session.getId();
					
					DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
					DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
					dcPeVotePaper.add(Restrictions.eq("id", this.getPeVotePaper().getId()));
					dc.add(Restrictions.eq("userSession", sessionId));
					
					try {
						List list = this.getGeneralService().getList(dc);
						//如果session已经存在则设置为不能投票
						if (list.size()>0) {
							this.setCanVote(0);
						}
					} catch (EntityException e) {
						e.printStackTrace();
					}
				}
			}
				
			if (this.getCanVote()==0) {
				this.setMsg("您已经做过本调查问卷！");
				return "result";
			}
			
			//得到用户的IP
			HttpServletRequest request = ServletActionContext. getRequest();
			String ip = request.getRemoteAddr();
			//得到用户session
			HttpSession session = ServletActionContext. getRequest().getSession();
			String sessionId = session.getId();
			
			Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//Lee 添加时分秒
		   String dateString = formatter.format(currentTime);
		   Date currentTime_2 = formatter.parse(dateString);
		   java.sql.Date d2 = new java.sql.Date(currentTime_2.getTime());
		   UserSession userSession = (UserSession) ActionContext.getContext()
			.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			SsoUser ssoUser = userSession.getSsoUser();
			
			//设置投票记录表
			PrVoteRecord prVoteRecord = new PrVoteRecord();
			prVoteRecord.setIp(ip);
			prVoteRecord.setPeVotePaper(votePaper);
			prVoteRecord.setUserSession(sessionId);
			prVoteRecord.setVoteDate(d2);
			prVoteRecord.setSsoId(ssoUser.getId());
			this.getGeneralService().save(prVoteRecord);
			
			//Lee start 2014年9月23日15:15:49 免考课程填写完满意度填充获得学时时间方法换到学习完成后自动填充获得学时时间
			//如果该课程不考试，则更新该学员的学习完成时间     将此功能关闭 并放入index.jsp中执行
//			EnumConst ec_isNoExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
			
//			PeBzzTchCourse currCourse = (PeBzzTchCourse)this.getGeneralService().getById(PeBzzTchCourse.class, votePaper.getId());
			
//			DetachedCriteria dcCourse = DetachedCriteria.forClass(PeBzzTchCourse.class);
//			dcCourse.createCriteria("enumConstByFlagIsExam","enumConstByFlagIsExam");
//			dcCourse.add(Restrictions.eq("id", votePaper.getCourseId()));
//			List currCourseList = this.getGeneralService().getList(dcCourse);
			
//			if(currCourseList!=null && currCourseList.size()>0){
//				PeBzzTchCourse currCourse = (PeBzzTchCourse)currCourseList.get(0);
//				if(currCourse.getEnumConstByFlagIsExam().getId().equals(ec_isNoExam.getId())){
//					DetachedCriteria dcStudent = DetachedCriteria.forClass(PeBzzStudent.class);
//					dcStudent.createCriteria("ssoUser","ssoUser");
//					dcStudent.add(Restrictions.eq("ssoUser.id", ssoUser.getId()));
//					List listStudent = this.getGeneralService().getList(dcStudent);
//					PeBzzStudent peBzzStudent = (PeBzzStudent)listStudent.get(0);
//					String sqlUpdate = " update PR_BZZ_TCH_STU_ELECTIVE pbtse set pbtse.COMPLETED_TIME = sysdate where FK_STU_ID = '"+peBzzStudent.getId()+"' and FK_TCH_OPENCOURSE_ID = '"+this.getOpencourseId()+"' ";
//					this.getGeneralService().executeBySQL(sqlUpdate);
//				}
//			}
			//Lee end
			//设置建议表
			if (votePaper.getEnumConstByFlagCanSuggest().getCode().equals("1")) {
				if (this.getSuggest()!=null && this.getSuggest().trim().length()>0) {
					PrVoteSuggest prVoteSuggest = new PrVoteSuggest();
					prVoteSuggest.setIp(ip);
					prVoteSuggest.setNote(this.getSuggest());
					prVoteSuggest.setPeVotePaper(votePaper);
					prVoteSuggest.setFoundDate(d2);
					prVoteSuggest.setEnumConstByFlagCheck(
							this.getMyListService().getEnumConstByNamespaceCode("FlagCheck", "0"));
					this.getGeneralService().save(prVoteSuggest);
				}
			}
			//用于拼接选项
			String vote = "";
			//保存多选题
			if (this.getCheckboxQuestion()!=null && this.getCheckboxQuestion().trim().length()>0) {
				vote += this.getCheckboxQuestion();
			}
			//保存单选题
			String readioQuestion = this.readioQuestion();
			if (readioQuestion.length()>0) {
				if (vote.length()>0) {
					vote = vote + "," + readioQuestion;
				}else{
					vote +=readioQuestion;
				}
			}
			//保存用户主观题答案
			String subQStr="";
			String noteQuestion=this.getQuestionNoteValue();
			if (this.getQuestionNoteValue()!=null && this.getQuestionNoteValue().trim().length()>0) {
				subQStr += this.getQuestionNoteValue();
			}
			if(subQStr.length()>0){
				this.getPeVotePaperService().saveOrUpdateSubVoteResult(subQStr.trim());
			}
			if (vote.length()>0) {
				//记录投票结果
				this.getPeVotePaperService().saveVoteNumber(vote);
				//记录用户选择题选择记录
				this.getPeVotePaperService().saveOrUpdateUserVoteResult(vote);
			}
			this.readioQuestion();
			this.setMsg("提交成功，感谢您的参与！");
			this.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("操作失败！");
		}
		return "result";
	}
	
	/**
	 * 转向查看结果页面
	 * @return
	 */
	public String voteResult() {
		this.toSetVotePaperQuestion();
//		this.maxResult();
		String voteCount = toGetPrVoteRecordCount();
		ServletActionContext.getRequest().setAttribute("voteCount", voteCount);
		return "voteResult";
	}
	/**
	 * 计算每项投票的百分比
	 * @return
	 */
	public String votePercent(long item, long total) {
		double d=(double)item/total*100;
		
		return new DecimalFormat("#.00").format(d);
	}
	/**
	 * 转向查看建议页面
	 * @return
	 */
	public String toSuggest() {
		this.toSetVotePaperQuestion();
		DetachedCriteria dc = DetachedCriteria.forClass( PrVoteSuggest.class);
		DetachedCriteria dcPeVotePaper=dc.createCriteria("peVotePaper", "peVotePaper");
		dcPeVotePaper.add(Restrictions.eq("id", this.getPeVotePaper().getId()));
		dc.createCriteria("enumConstByFlagCheck", "enumConstByFlagCheck")
			.add(Restrictions.eq("code", "1"));
		dc.addOrder(Order.desc("foundDate"));
		try {
			this.setPrVoteSuggestList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "suggest";
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/first/firstPeVotePaper";
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<PrVoteSuggest> getPrVoteSuggestList() {
		return prVoteSuggestList;
	}
	public void setPrVoteSuggestList(List<PrVoteSuggest> prVoteSuggestList) {
		this.prVoteSuggestList = prVoteSuggestList;
	}

	public PeVotePaper getPeVotePaper() {
		return peVotePaper;
	}

	public void setPeVotePaper(PeVotePaper peVotePaper) {
		this.peVotePaper = peVotePaper;
	}



	public String getTogo() {
		return togo;
	}

	public void setTogo(String togo) {
		this.togo = togo;
	}

	public String getOpencourseId() {
		return opencourseId;
	}

	public void setOpencourseId(String opencourseId) {
		this.opencourseId = opencourseId;
	}
	
	
}
