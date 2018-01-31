package com.whaty.platform.entity.web.action.statistics;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ElectiveCourseInfoAction extends MyBaseAction{
    private String opid;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("选课详细信息");
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学号"), "reg_no", true);
		this.getGridConfig().addColumn(this.getText("姓名"), "true_name", true);
		this.getGridConfig().addColumn(this.getText("机构代码"), "code", true);
		this.getGridConfig().addColumn(this.getText("机构名称"), "pename", true);
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/statistics/electiveCourseInfo";
		
	}
	
	@Override
	public Page list() {
		Page page=null;
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from (select pbs.id,pbs.reg_no, pbs.true_name, pe.code, pe.name as pename  ");
		sql.append("   from pr_bzz_tch_opencourse pbto,				   ");
		sql.append("        pr_bzz_tch_stu_elective   pbtse,				   ");
		sql.append("        pe_bzz_student        pbs,				   ");
		sql.append("        PE_ENTERPRISE         pe				   ");
		sql.append("  where pbto.id =pbtse.fk_tch_opencourse_id					   ");
		sql.append("    and pbs.id = pbtse.fk_stu_id				   ");
		sql.append("    and pbs.fk_enterprise_id = pe.id			   ");
		sql.append("    and pbto.id='"+this.getOpid()+"') where 1=1");
        this.setSqlCondition(sql);
        try {
			page=this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	public String getOpid() {
		return opid;
	}

	public void setOpid(String opid) {
		this.opid = opid;
	}
  
}
