package com.whaty.platform.entity.web.action.first;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 生成下拉列表功能
 * 
 * @author 李冰
 * 
 */
public class GenerateComboAction extends MyBaseAction { 
	private String requestsiteid; 

	private String requestedutypeid; // 用于级联显示，层次id

	public String getRequestsiteid() {
		return requestsiteid;
	}

	public void setRequestsiteid(String requestsiteid) {
		this.requestsiteid = requestsiteid;
	}

	public String getRequestedutypeid() {
		return requestedutypeid;
	}

	public void setRequestedutypeid(String requestedutypeid) {
		this.requestedutypeid = requestedutypeid;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}

	/**
	 * 学习中心列表
	 * 
	 * @return
	 */
	public String getRecsites() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSite.class);
		try {
			List<PeSite> siteList = this.getGeneralService().getList(dc);
			Map map = new HashMap();
			map.put("sites", siteList);
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return "json";
	}

	/**
	 * 层次列表，与学习中心级联
	 * 
	 * @return
	 */
	public String getRecEdutypes() {
		List<PeEdutype> edutypeList = new ArrayList();

		DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria
				.forClass(PrRecPlanMajorSite.class);
		DetachedCriteria dcPeSite = dcPrRecPlanMajorSite
				.createCriteria("peSite","peSite");
		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype","prRecPlanMajorEdutype");
		DetachedCriteria dcPeEdutype = dcPrRecPlanMajorEdutype
				.createCriteria("peEdutype","peEdutype");
		DetachedCriteria dcPeRecruitplan = dcPrRecPlanMajorEdutype
				.createCriteria("peRecruitplan","peRecruitplan");
		dcPeSite.add(Restrictions.eq("id", this.getRequestsiteid()));
		 dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));
		List<PrRecPlanMajorSite> prRecPlanMajorSiteList = new ArrayList();
		try {
			prRecPlanMajorSiteList = this.getGeneralService().getList(
					dcPrRecPlanMajorSite);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (prRecPlanMajorSiteList.size() == 0) {
			PeEdutype noresult = new PeEdutype();
			noresult.setName("暂无层次可选");
			noresult.setId("0");
			edutypeList.add(noresult);
		} else {
			for (int i = 0; i < prRecPlanMajorSiteList.size(); i++) {
				if (i == 0) {
					edutypeList.add(prRecPlanMajorSiteList.get(i)
							.getPrRecPlanMajorEdutype().getPeEdutype());
				} else {
					// 消除名字相同的层次
					boolean flagequal = false;
					for (int j = 0; j < edutypeList.size(); j++) {
						if (edutypeList.get(j).getName().equals(
								prRecPlanMajorSiteList.get(i)
										.getPrRecPlanMajorEdutype()
										.getPeEdutype().getName())) {
							flagequal = true;
						}
					}
					if (!flagequal) {
						edutypeList.add(prRecPlanMajorSiteList.get(i)
								.getPrRecPlanMajorEdutype().getPeEdutype());
					}
				}

			}
		}

		Map map = new HashMap();
		map.put("edutypes", edutypeList);
		this.setJsonString(JsonUtil.toJSONString(map));
		return "json";
	}

	/**
	 * 生成专业列表，与学习中心和层次级联
	 * 
	 * @return
	 */
	public String getRecMajors() {
		List<PeMajor> majorList = new ArrayList();

		DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria
				.forClass(PrRecPlanMajorSite.class);

		DetachedCriteria dcPeSite = dcPrRecPlanMajorSite.createCriteria(
				"peSite", "peSite");

		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");

		DetachedCriteria dcPeMajor = dcPrRecPlanMajorEdutype.createCriteria(
				"peMajor", "peMajor");

		DetachedCriteria dcPeEdutype = dcPrRecPlanMajorEdutype.createCriteria(
				"peEdutype", "peEdutype");

		DetachedCriteria dcPeRecruitplan = dcPrRecPlanMajorEdutype
				.createCriteria("peRecruitplan", "peRecruitplan");

		dcPeSite.add(Restrictions.eq("id", this.getRequestsiteid()));

		dcPeEdutype.add(Restrictions.eq("id", this.getRequestedutypeid()));

		 dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));
		List<PrRecPlanMajorSite> prRecPlanMajorSiteList = new ArrayList();

		try {
			prRecPlanMajorSiteList = this.getGeneralService().getList(
					dcPrRecPlanMajorSite);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (prRecPlanMajorSiteList.size() == 0) {
			PeMajor noresult = new PeMajor();
			noresult.setName("暂无专业可选");
			noresult.setId("0");
			majorList.add(noresult);
		} else {
			for (int i = 0; i < prRecPlanMajorSiteList.size(); i++) {
				majorList.add(prRecPlanMajorSiteList.get(i)
						.getPrRecPlanMajorEdutype().getPeMajor());
			}
		}

		Map map = new HashMap();
		map.put("majors", majorList);
		this.setJsonString(JsonUtil.toJSONString(map));
		return "json";
	}

}
