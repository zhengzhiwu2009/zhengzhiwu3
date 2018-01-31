package com.whaty.platform.entity.web.action.information.vote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteSuggest;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 调查问卷建议管理
 * @author 李冰
 *
 */
public class PrVoteSuggestAction extends MyBaseAction {
	
	/**
	 * 查看建议的详情
	 * @return
	 */
	public String viewDetail() {
		try {
			this.setBean((PrVoteSuggest)this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("操作失败！");
			return "msg";
		}
		return "view";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("建议列表"));
		this.getGridConfig().setCapability(false, true, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		//this.getGridConfig().addColumn(this.getText("内容"), "note");
		this.getGridConfig().addColumn(this.getText("是否通过审核"), "enumConstByFlagCheck.name");	
		this.getGridConfig().addColumn(this.getText("发表时间"), "foundDate");
		this.getGridConfig().addColumn(this.getText("IP地址"), "ip");
		this.getGridConfig().addRenderFunction(this.getText("查看详情"),
				"<a href=\"prVoteSuggest_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>",
				"id");
		this.getGridConfig().addMenuFunction(this.getText("审核通过"),
				"enumConstByFlagCheck.id" ,
				this.getMyListService().getEnumConstByNamespaceCode("FlagCheck", "1").getId());	
		this.getGridConfig().addMenuFunction(this.getText("审核不通过"),
				"enumConstByFlagCheck.id" ,
				this.getMyListService().getEnumConstByNamespaceCode("FlagCheck", "0").getId());
		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{window.history.back()}");
	}

	
	@Override
	public void setEntityClass() {
		this.entityClass =  PrVoteSuggest.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/prVoteSuggest";

	}
	
	public DetachedCriteria initDetachedCriteria() {
		//JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteSuggest.class);
		dc.createAlias("peVotePaper", "peVotePaper");
		dc.createCriteria("enumConstByFlagCheck", "enumConstByFlagCheck");
		return dc;
	}
	
	public void setBean( PrVoteSuggest instance) {
		super.superSetBean(instance);

	}

	public  PrVoteSuggest getBean() {
		return ( PrVoteSuggest) super.superGetBean();
	}
}
