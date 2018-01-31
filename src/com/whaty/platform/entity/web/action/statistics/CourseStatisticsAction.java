package com.whaty.platform.entity.web.action.statistics;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class CourseStatisticsAction extends MyBaseAction{

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("课程统计");
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true);
		this.getGridConfig().addColumn(this.getText("选课人数"), "amount",false);
		//this.getGridConfig().addColumn(this.getText("查看详细"), "opid", false, false, false, "TextField", false, 30, false, "");
		this.getGridConfig().addRenderFunction(this.getText("查看详细"), "<a href=\"/entity/statistics/electiveCourseInfo.action?opid=${value}\" target=\"_blank\"><font color=#0000ff>查看详细信息<font></a>", "id");
	}
    
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/statistics/courseStatistics";
		
	}
	@Override
	public Page list() {
		Page page=null;
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from (select pbto.id, pbtc.name,pbtse.amount                              ");
		sql.append("   from PE_BZZ_TCH_COURSE pbtc,				  ");
		sql.append("        pr_bzz_tch_opencourse pbto,				  ");
		sql.append("        (select pbtse.fk_tch_opencourse_id as opencourse_id,  ");
		sql.append("                count(pbtse.fk_stu_id) as amount		  ");
		sql.append("           from pr_bzz_tch_stu_elective pbtse		  ");
		sql.append("          group by pbtse.fk_tch_opencourse_id) pbtse	  ");
		sql.append("  where pbtc.id = pbto.fk_course_id				  ");
		sql.append("    and pbtse.opencourse_id = pbto.id	) where 1=1		  ");
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

}
