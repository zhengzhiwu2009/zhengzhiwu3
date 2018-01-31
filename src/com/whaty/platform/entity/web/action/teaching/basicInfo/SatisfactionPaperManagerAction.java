package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.bean.PrVoteSuggest;
import com.whaty.platform.entity.bean.SatisfactionPaperquestion;
import com.whaty.platform.entity.bean.SatisfactionSurveyPaperInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.XMLParserUtil;

/**
 * @param
 * @version 创建时间：2009-6-21 上午10:40:06
 * @return
 * @throws PlatformException
 *             类说明
 */
public class SatisfactionPaperManagerAction extends MyBaseAction<PeVotePaper> {

	private String tempFlag;
	private SatisfactionSurveyPaperInfo satisfactionSurveyPaperInfo;
	private PeVotePaper peVotePaper;
	private List<PrVoteQuestion> prVoteQuestionList;
	long voteNumber; // 参加投票的人数
	private String canupdate;

	public String getCanupdate() {
		return canupdate;
	}

	public void setCanupdate(String canupdate) {
		this.canupdate = canupdate;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("满意度调查列表");
		this.getGridConfig().setCapability(true, true, true);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("调查名称"), "title");

		this.getGridConfig().addColumn(this.getText("添加时间"), "startDate", false, false, true, "Date", false, 50);
		ColumnConfig columnConfigIsvalid = new ColumnConfig(this.getText("是否可用"), "enumConstByFlagIsvalid.name", true, false, true, "TextField", false, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid'  and a.id<>'3x' ";// 不显示待审核
																												// lzh
		columnConfigIsvalid.setComboSQL(sql3);
		this.getGridConfig().addColumn(columnConfigIsvalid);
		// this.getGridConfig().addColumn(this.getText("创建人"),
		// "creatuser",true,false,true,"");
		// this.getGridConfig().addRenderFunction(this.getText("问题总数"),
		// "30","id");
		// this.getGridConfig().addColumn(this.getText("添加时间起始"),
		// "startStartDate", true, false, false, "");
		// this.getGridConfig().addColumn(this.getText("添加时间结束"),
		// "startEndDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("添加时间"), "startStartDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("至"), "startEndDate", true, false, false, "");

		this.getGridConfig().addRenderScript(
				this.getText("管理题目"),
				"{if(record.data['enumConstByFlagIsvalid.name'] =='否')"
						+ "{return '<a href=\"/entity/teaching/satisfactionPaperManager_toVoteQuestion.action?bean.id='+record.data['id']+'\">添加</a>';}else {return '添加';}}", "id");

		// this.getGridConfig().addRenderFunction(this.getText("管理题目"), "<a
		// href=\"/entity/teaching/satisfactionPaperManager_toVoteQuestion.action?bean.id=${value}\">添加</a>","id");
		// this.getGridConfig().addRenderFunction(this.getText("查看题目"), "<a
		// href=\"/entity/teaching/addQuestionToSatisfy.action?id=${value}&flag=del\">查看</a>","id");

		// this.getGridConfig().addColumn(this.getText("是否可用"),
		// "enumConstByFlagIsvalid.name",true, false, true, "");

		this.getGridConfig().addRenderFunction(this.getText("预览"), "<a href=\"/entity/teaching/satisfactionPaperManager_viewDetail.action?bean.id=${value}\"  target='_blank'>预览</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("课程列表"), "<a href=\"/entity/teaching/courseDetailView.action?id=${value}\" >课程列表</a>", "id");
		this.getGridConfig().addRenderScript(
				this.getText("自定义回答"),
				"{ return '<a href=\"/entity/teaching/satisfactionPaperManager_toVoteQuestionAnswer.action?bean.id='+record.data['id']+'\">自定义回答</a>'}", "id");		this.getGridConfig().addMenuFunction(this.getText("设为可用"), "2");
		this.getGridConfig().addMenuFunction(this.getText("设为不可用"), "3");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeVotePaper.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/satisfactionPaperManager";
	}

	public void setBean(PeVotePaper instance) {
		super.superSetBean(instance);

	}

	public PeVotePaper getBean() {
		return super.superGetBean();
	}

	/**
	 * 重写框架方法：添加数据
	 * 
	 * @author linjie
	 * @return
	 */
	public Map add() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getBean().setCreateUser(us.getLoginId());
		this.getBean().setStartDate(new Date());
		// 因为课程满意度没有时间限制 所以设置成无限大的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		String str = "9999-12-31";
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getBean().setEndDate(date);
		EnumConst e = new EnumConst();
		// e.setId("3");
		// 初始设置成未启用
		// this.getBean().setEnumConstByFlagIsvalid(e);
		this.getBean().setType("course");
		e = new EnumConst();
		e.setId("402880a91e61bac6011e61bcbcd60001");
		// 是否可以添加建议 满意度调查不能所以设成 否
		this.getBean().setEnumConstByFlagCanSuggest(e);
		e = new EnumConst();
		e.setId("402880a91e61bac6011e61c006b20007");
		// 是否可以查看建议 满意度调查不能所以设成 否
		this.getBean().setEnumConstByFlagViewSuggest(e);
		e = new EnumConst();
		e.setId("402880a91e61bac6011e61be18ad0003");
		this.getBean().setEnumConstByFlagLimitDiffip(e);
		e = new EnumConst();
		e.setId("402880a91e61bac6011e61bf60020006");
		this.getBean().setEnumConstByFlagLimitDiffsession(e);
		e = new EnumConst();
		e.setId("402880a91e5ec11a011e5ec319950001");
		this.getBean().setEnumConstByFlagQualificationsType(e);
		e = new EnumConst();
		e.setId("3");
		this.getBean().setEnumConstByFlagIsvalid(e);

		return super.add();
	}

	/**
	 * 重写框架方法--列更新
	 * 
	 * @author linjie
	 * @return
	 */
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		String idsStr = "";
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			List list = new ArrayList();
			for (int j = 0; j < ids.length; j++) {
				list.add(ids[j]);
				idsStr += "'" + ids[j] + "',";
			}
			idsStr = idsStr.substring(0, idsStr.length() - 1);
			/**
			 * 添加设为有效时校验
			 */
			String sql =

			"select nvl(count(distinct pvp.id),0)\n" + "  from pe_vote_paper pvp\n" + " inner join pr_vote_question pvq on pvq.fk_vote_paper_id = pvp.id\n" + " where pvp.id in (" + idsStr + ")";

			try {
				List pvpList = this.getGeneralService().getBySQL(sql);
				int count = Integer.parseInt(pvpList.get(0).toString());
				if (count != ids.length) {
					map.put("success", "false");
					map.put("info", "调查问卷没有试题，不能发布");
					return map;
				}
			} catch (EntityException e1) {
				// TODO Auto-generated catch block
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}

			try {

				int i = 0;
				if (!"".equals(this.getColumn()) && null != this.getColumn()) {
					i = this.getGeneralService().updateColumnByIds(list, "enumConstByFlagIsvalid.id", this.getColumn());
				}
				map.put("success", "true");
				map.put("info", i + "条记录操作成功");
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	/**
	 * 重写框架方法：删除数据
	 * 
	 * @author linjie
	 * @return
	 */
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				DetachedCriteria criteria = DetachedCriteria.forClass(PrVoteQuestion.class);
				criteria.createCriteria("peVotePaper", "peVotePaper");
				criteria.add(Restrictions.in("peVotePaper.id", ids));

				DetachedCriteria criteria1 = DetachedCriteria.forClass(PrVoteSuggest.class);
				criteria1.createCriteria("peVotePaper", "peVotePaper");
				criteria1.add(Restrictions.in("peVotePaper.id", ids));

				DetachedCriteria criteria2 = DetachedCriteria.forClass(PrVoteRecord.class);
				criteria2.createCriteria("peVotePaper", "peVotePaper");
				criteria2.add(Restrictions.in("peVotePaper.id", ids));

				try {
					List<PrVoteQuestion> plist = this.getGeneralService().getList(criteria);
					List<PrVoteSuggest> vslist = this.getGeneralService().getList(criteria1);
					List<PrVoteRecord> vrlist = this.getGeneralService().getList(criteria1);
					if (plist.size() > 0 || vslist.size() > 0 || vrlist.size() > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "该满意度问卷下已经添加题目,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try {
					// this.getGeneralService().deleteByIds(idList);
					map.put("success", "true");
					map.put("info", ids.length + "条满意度问卷记录删除成功");
					UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
					List<PeEnterprise> priList = us.getPriEnterprises();

					List siteList = new ArrayList();

					for (int i = 0; i < priList.size(); i++) {
						siteList.add(priList.get(i).getId());
					}

					int i = this.getGeneralService().deleteByIds(this.getEntityClass(), siteList, idList);
					if (0 == i) {
						map.clear();
						map.put("success", "false");
						map.put("info", "对不起，您没有删除的权限");
					}
				} catch (RuntimeException e) {
					return this.checkForeignKey(e);
				} catch (Exception e1) {
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

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		dc.add(Restrictions.eq("type", "course"));
		dc.add(Restrictions.isNull("courseId"));
		dc.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType");
		dc.addOrder(Order.desc("startDate"));
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					if (params.get("search__startStartDate") != null) {
						String[] startDate = (String[]) params.get("search__startStartDate");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__startStartDate");
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date sDate = format.parse(tempDate + " 00:00:00 ");
							// Date sDate = format.parse(tempDate);
							dc.add(Restrictions.ge("startDate", sDate));
						}
					}
					if (params.get("search__startEndDate") != null) {
						String[] startDate = (String[]) params.get("search__startEndDate");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__startEndDate");
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date sDate = format.parse(tempDate + " 23:59:59 ");
							dc.add(Restrictions.le("startDate", sDate));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}

	/**
	 * 获得问卷信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String paperInfo() {
		ServletActionContext.getRequest().getSession().setAttribute("paperInfo", this.getBean());

		return "paperInfo";
	}

	/**
	 * 查看调查问卷
	 * 
	 * @return
	 */
	public String showInfo() {
		this.toSetVotePaperQuestion();
		this.maxResult();

		// 设置参加投票的人数
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
		DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
		dcPeVotePaper.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			List list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				this.setVoteNumber(list.size());
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "detail";
	}

	/**
	 * 最大投票数
	 * 
	 * @author linjie
	 * @return
	 */
	protected void maxResult() {
		for (PrVoteQuestion prVoteQuestion : this.prVoteQuestionList) {
			long result = 0L;
			if (prVoteQuestion.getItemResult1() != null && prVoteQuestion.getItemResult1() > result) {
				result = prVoteQuestion.getItemResult1();
			}
			if (prVoteQuestion.getItemResult2() != null && prVoteQuestion.getItemResult2() > result) {
				result = prVoteQuestion.getItemResult2();
			}
			if (prVoteQuestion.getItemResult3() != null && prVoteQuestion.getItemResult3() > result) {
				result = prVoteQuestion.getItemResult3();
			}
			if (prVoteQuestion.getItemResult4() != null && prVoteQuestion.getItemResult4() > result) {
				result = prVoteQuestion.getItemResult4();
			}
			if (prVoteQuestion.getItemResult5() != null && prVoteQuestion.getItemResult5() > result) {
				result = prVoteQuestion.getItemResult5();
			}
			if (prVoteQuestion.getItemResult6() != null && prVoteQuestion.getItemResult6() > result) {
				result = prVoteQuestion.getItemResult6();
			}
			if (prVoteQuestion.getItemResult7() != null && prVoteQuestion.getItemResult7() > result) {
				result = prVoteQuestion.getItemResult7();
			}
			if (prVoteQuestion.getItemResult8() != null && prVoteQuestion.getItemResult8() > result) {
				result = prVoteQuestion.getItemResult8();
			}
			if (prVoteQuestion.getItemResult9() != null && prVoteQuestion.getItemResult9() > result) {
				result = prVoteQuestion.getItemResult9();
			}
			if (prVoteQuestion.getItemResult10() != null && prVoteQuestion.getItemResult10() > result) {
				result = prVoteQuestion.getItemResult10();
			}
			if (prVoteQuestion.getItemResult11() != null && prVoteQuestion.getItemResult11() > result) {
				result = prVoteQuestion.getItemResult11();
			}
			if (prVoteQuestion.getItemResult12() != null && prVoteQuestion.getItemResult12() > result) {
				result = prVoteQuestion.getItemResult12();
			}
			if (prVoteQuestion.getItemResult13() != null && prVoteQuestion.getItemResult13() > result) {
				result = prVoteQuestion.getItemResult13();
			}
			if (prVoteQuestion.getItemResult14() != null && prVoteQuestion.getItemResult14() > result) {
				result = prVoteQuestion.getItemResult14();
			}
			if (prVoteQuestion.getItemResult15() != null && prVoteQuestion.getItemResult15() > result) {
				result = prVoteQuestion.getItemResult15();
			}
			prVoteQuestion.setItemNum(result);
		}
	}

	/**
	 * 获得问题集合
	 * 
	 * @author linjie
	 * @return
	 */
	private Map parseQuestionList(List<SatisfactionPaperquestion> list) {
		Map map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			String questionCore = list.get(i).getSatisfactionQuestionInfo().getQuestioncore();
			List coreList = XMLParserUtil.parserSingleMultiNoAn(questionCore);
			map.put(i, coreList);
		}
		return map;

	}

	/**
	 * 根据ID设置peVotePaper 和prVoteQuestionList
	 */
	protected void toSetVotePaperQuestion() {
		try {
			String flag_valid = "";
			this.setCanupdate("1");
			// 取得调查问卷
			try {
				this.setPeVotePaper((PeVotePaper) this.getGeneralService().getById(PeVotePaper.class, this.getBean().getId()));
			} catch (Exception e) {
				if(this.getBean() == null || this.getBean().getId() == null){
					System.out.println("取得调查问卷失败：参数不全！|"+e.getMessage());
				}else{
					System.out.println("取得调查问卷失败：" + this.getBean() + "-" + this.getBean().getId()+"|"+e.getMessage());
				}
			}

			if (this.getPeVotePaper() != null) {

				flag_valid = this.getPeVotePaper().getEnumConstByFlagIsvalid().getName();
			}
			// 取得调查问卷
			this.setPeVotePaper((PeVotePaper) this.getGeneralService().getById(this.getBean().getId()));

			List<PrVoteQuestion> prVoteQuestionList = new ArrayList<PrVoteQuestion>();
			DetachedCriteria dc = DetachedCriteria.forClass(PrVoteQuestion.class);
			DetachedCriteria dcPrVoteQuestion = dc.createCriteria("peVotePaper", "peVotePaper");
			dcPrVoteQuestion.add(Restrictions.eq("id", this.getBean().getId()));
			dc.addOrder(Order.asc("questionOrder"));
			// 取得问卷包含的问题
			prVoteQuestionList = this.getGeneralService().getList(dc);

			this.setPrVoteQuestionList(prVoteQuestionList);
			String sql_record = "    select pr.fk_vote_paper_id,pr.vote_date ,vp.title,ec.code,ec.name" + "	  from pr_vote_record  pr ,pe_vote_paper vp ,enum_const ec "
					+ "	  where  pr.fk_vote_paper_id=vp.id and ec.id=vp.flag_isvalid  " + " 	  and pr.fk_vote_paper_id='" + this.getBean().getId() + "'";
			List list_record = this.getGeneralService().getBySQL(sql_record);
			if ((list_record != null && list_record.size() > 0) || (flag_valid != null && flag_valid.equals("是"))) {
				this.setCanupdate("0");
			}

		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转向问题管理页面
	 * 
	 * @return
	 */
	public String toVoteQuestion() {
		this.toSetVotePaperQuestion();
		return "voteQuestion";
	}
	/**转向问题管理页面，可查看自定义回答*/
	public String toVoteQuestionAnswer() {
		this.toSetVotePaperQuestion();
		return "voteQuestionAnswer";
	}

	/**
	 * 查看调查问卷
	 * 
	 * @return
	 */
	public String viewDetail() {
		this.toSetVotePaperQuestion();
		return "detail";
	}

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public SatisfactionSurveyPaperInfo getSatisfactionSurveyPaperInfo() {
		return satisfactionSurveyPaperInfo;
	}

	public void setSatisfactionSurveyPaperInfo(SatisfactionSurveyPaperInfo satisfactionSurveyPaperInfo) {
		this.satisfactionSurveyPaperInfo = satisfactionSurveyPaperInfo;
	}

	public PeVotePaper getPeVotePaper() {
		return peVotePaper;
	}

	public void setPeVotePaper(PeVotePaper peVotePaper) {
		this.peVotePaper = peVotePaper;
	}

	public List getPrVoteQuestionList() {
		return prVoteQuestionList;
	}

	public void setPrVoteQuestionList(List prVoteQuestionList) {
		this.prVoteQuestionList = prVoteQuestionList;
	}

	public long getVoteNumber() {
		return voteNumber;
	}

	public void setVoteNumber(long voteNumber) {
		this.voteNumber = voteNumber;
	}

}
