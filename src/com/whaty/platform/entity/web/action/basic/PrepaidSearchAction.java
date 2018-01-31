package com.whaty.platform.entity.web.action.basic;

import java.util.List;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrepaidSearchAction extends MyBaseAction<PeEnterpriseManager> {
	private List<PeBzzStudent> stuList;
	private EnumConst enumConst;
	public List<PeBzzStudent> getStuList() {
		return stuList;
	}
	public void setStuList(List<PeBzzStudent> stuList) {
		this.stuList = stuList;
	}
	public EnumConst getEnumConst() {
		return enumConst;
	}
	public void setEnumConst(EnumConst enumConst) {
		this.enumConst = enumConst;
	}

	/**
	 * 初始化列表
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("预付费账户查询");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("机构代码"), "code", true,false, true, "");
		this.getGridConfig().addColumn(this.getText("单位名称"), "name",true,false, true, "");
		this.getGridConfig().addColumn(this.getText("账户总额"),"sumAmount",false,false,false,"sumAmount");
		this.getGridConfig().addColumn(this.getText("已使用"), "samount",false, false, false, "amount");
		this.getGridConfig().addColumn(this.getText("账户剩余学时数"), "balance",true, false, true, "balance");
		this.getGridConfig().addColumn(this.getText("用户名"),"loginId",false,false,false,"loginId");
		//this.getGridConfig().addRenderScript(this.getText("充值记录"),"{return '<a href=\"/entity/basic/buyClassHourRecord.action?userId='+record.data['loginId']+'\">充值记录</a>';}","");
		//Lee 原版
		//this.getGridConfig().addRenderScript(this.getText("消费记录"),"{return '<a href=\"/entity/basic/checkClassHourRecord.action?flag=old&userId='+record.data['loginId']+'\">消费记录</a>';}","");
		//Lee  
		//Lee 2013年12月27日
		this.getGridConfig().addRenderScript(this.getText("充值记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/checkClassHourRecord.action?forg=c&flag=old&userId='+record.data['loginId']+'\">充值记录</a>';}","");
		this.getGridConfig().addRenderScript(this.getText("分配记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/checkClassHourRecord.action?forg=y&flag=old&userId='+record.data['loginId']+'\">分配记录</a>';}","");
		this.getGridConfig().addRenderScript(this.getText("剥离记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/checkClassHourRecord.action?forg=b&flag=old&userId='+record.data['loginId']+'\">剥离记录</a>';}","");
		this.getGridConfig().addRenderScript(this.getText("购买课程记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/checkClassHourRecord.action?forg=d&flag=old&userId='+record.data['code']+'\">购买课程记录</a>';}","");
		this.getGridConfig().addRenderScript(this.getText("退费记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/checkClassHourRecord.action?forg=e&flag=old&userId='+record.data['code']+'\">退费记录</a>';}","");
		this.getGridConfig().addRenderScript(this.getText("预付费账户变动记录"),"{return '<a target=\"_blank\" href=\"/entity/basic/yuFuFeeRecord.action?userId='+record.data['loginId']+'\">预付费账户变动记录</a>';}","");
	}
	     
	/**
	 * 重写框架方法：数据库查询（带sql条件）
	 * @author dgh
	 * @return
	 */
	@Override
	public Page list(){
		Page page = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from ( select pem.id as id,pe.code as code ,pe.name as name,su.SUM_AMOUNT as sumamount,\n su.amount as amount,(nvl(su.SUM_AMOUNT, 0) - nvl(su.amount, 0)) as balance \n,su.login_id as loginid from  pe_enterprise_manager pem,");
			sql.append(" pe_enterprise pe,sso_user su \n where pem.fk_sso_user_id = su.id and pe.id = pem.fk_enterprise_id order by code ) where 1 = 1 ");
			this.setSqlCondition(sql);
			page = getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	public PeEnterpriseManager getBean() {
		return (PeEnterpriseManager) super.superGetBean();
	}

	public void setBean(PeEnterpriseManager enterpriseManager) {
		super.superSetBean(enterpriseManager);
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeEnterpriseManager.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/prepaidSearch";
	}
	
	public String viewPurchaseHistory(){
		
		return "viewPurchaseHistory";
	}
	
	public String viewAssignHistory(){
		return "viewAssignHistory";
	}
	
	   
}
