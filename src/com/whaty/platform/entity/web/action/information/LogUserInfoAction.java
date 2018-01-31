package com.whaty.platform.entity.web.action.information;

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
import com.whaty.platform.entity.bean.LogUserInfo;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.bean.PrVoteSuggest;
import com.whaty.platform.entity.bean.SatisfactionPaperquestion;
import com.whaty.platform.entity.bean.SatisfactionSurveyPaperInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
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
public class LogUserInfoAction extends MyBaseAction<LogUserInfo> {

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("日志审计");
		this.getGridConfig().setCapability(true, true, true);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("用户"), "name");
		this.getGridConfig().addColumn(this.getText("QXID"), "qxid");
		this.getGridConfig().addColumn(this.getText("操作功能"), "operatetype");
		this.getGridConfig().addColumn(this.getText("操作状态"), "operatestatus");
		this.getGridConfig().addColumn(this.getText("操作权限"), "opkey");
		this.getGridConfig().addColumn(this.getText("IP地址"), "opip");
		this.getGridConfig().addColumn(this.getText("操作时间"), "operatetime");
		

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/logUserInfo";
		
	}
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		ActionContext context = ActionContext.getContext();
		try{
			sqlBuffer.append("   ");
			sqlBuffer.append("   ");
			sqlBuffer.append("   ");
			sqlBuffer.append("   ");
			sqlBuffer.append("   ");
			sqlBuffer.append("   ");
			sqlBuffer.append("   ");
			sqlBuffer.append("   ");
			sqlBuffer.append("   ");
			sqlBuffer.append("   ");
			
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
	 
	return page;
	}

}
