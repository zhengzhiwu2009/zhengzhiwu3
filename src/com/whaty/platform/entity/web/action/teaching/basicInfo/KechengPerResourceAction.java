package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;



import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class KechengPerResourceAction extends MyBaseAction {


	private int totalCount; // 总条数
	private int startIndex = 0; // 开始数
	private int pageSize = 10; // 页面显示数
	private Page page;
	private String id;
	private String typeflag;

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if ("addkecheng".equals(this.typeflag)) {

			this.getGridConfig().setTitle("选择课程");
			this.getGridConfig()
					.setCapability(false, false, false, true, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code",
					true, false, true, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("课程名称"), "name",
					true, false, true, "TextField", false, 100);
			

			ColumnConfig xkstatus = new ColumnConfig(this.getText("业务分类"),
					"ComboBox_ZIGE_NAME", true, false, true, "TextField",
					false, 100, "");
			String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
			xkstatus.setComboSQL(sql7);
			this.getGridConfig().addColumn(xkstatus);
			if (us.getUserLoginType().equals("3")) {
				
				ColumnConfig columnConfigIsvalid = new ColumnConfig(this.getText("是否发布"),
						"ComboBox_FlagIsvalid_NAME", true, false, true, "TextField",
						false, 100, "");
				String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid'";
				columnConfigIsvalid.setComboSQL(sql3);
				this.getGridConfig().addColumn(columnConfigIsvalid);
				
				ColumnConfig columnConfigOffline = new ColumnConfig(this.getText("是否下线"),
						"ComboBox_FlagOffline_NAME", true, false, true, "TextField",
						false, 100, "");
				String sql33 = "select a.id ,a.name from enum_const a where a.namespace='FlagOffline'";
				columnConfigOffline.setComboSQL(sql3);
				this.getGridConfig().addColumn(columnConfigOffline);
				
				this.getGridConfig().addMenuFunction(this.getText("添加至课程"),
						"addResourceToCourse");
			}
			this.getGridConfig().addMenuScript(this.getText("返回"),
					"{window.location='/entity/teaching/peResource.action'}");
		} else if ("viewkecheng".equals(this.typeflag)) {
			this.getGridConfig().setTitle("查看课程");
			this.getGridConfig()
					.setCapability(false, false, false, true, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code",
					true, false, true, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("课程名称"), "name",
					true, false, true, "TextField", false, 100);

			ColumnConfig xkstatus = new ColumnConfig(this.getText("业务分类"),
					"ComboBox_ZIGE_NAME", true, false, true, "TextField",
					false, 100, "");
			String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
			xkstatus.setComboSQL(sql7);
			this.getGridConfig().addColumn(xkstatus);
			if (us.getUserLoginType().equals("3")) {
				
				ColumnConfig columnConfigIsvalid = new ColumnConfig(this.getText("是否发布"),
						"ComboBox_FlagIsvalid_NAME", true, false, true, "TextField",
						false, 100, "");
				String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid'";
				columnConfigIsvalid.setComboSQL(sql3);
				this.getGridConfig().addColumn(columnConfigIsvalid);
				
				ColumnConfig columnConfigOffline = new ColumnConfig(this.getText("是否下线"),
						"ComboBox_FlagOffline_NAME", true, false, true, "TextField",
						false, 100, "");
				String sql33 = "select a.id ,a.name from enum_const a where a.namespace='FlagOffline'";
				columnConfigOffline.setComboSQL(sql3);
				this.getGridConfig().addColumn(columnConfigOffline);	
				
			this.getGridConfig().addMenuFunction(this.getText("删除课程"),
					"delCourse_"+this.getId());
			}
			if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuScript(this.getText("返回"),
					"{window.location='/entity/teaching/peResource.action'}");
			}else{
				this.getGridConfig().addMenuScript(this.getText("返回"),
				"{window.location='/entity/teaching/peResourcejiti.action'}");
			}
		}
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/kechengPerResource";
	}

	/**
	 * 查看课程 addkecheng为查看添加课程  viewkecheng 查看课程
	 */
	public Page list() {
		String sql = "";
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sqlBuffer = new StringBuffer();
		if ("addkecheng".equals(this.typeflag)) {
			sqlBuffer.append("SELECT * FROM (select pbtc.id,pbtc.code,pbtc.name,ec1.name as ComboBox_ZIGE_NAME ,ec2.name as ComboBox_FlagIsvalid_NAME ,ec3.name as  ComboBox_FlagOffline_NAME   from pe_bzz_tch_course pbtc left join enum_const ec1 on pbtc.flag_coursecategory=ec1.id ");
			sqlBuffer.append( " inner join enum_const ec2 on pbtc.flag_isvalid=ec2.id   inner join enum_const ec3 on pbtc.flag_offline=ec3.id " );
			sqlBuffer.append(" where pbtc.FLAG_COURSEAREA <> 'Coursearea0' ORDER BY PBTC.CREATE_DATE DESC ) WHERE 1=1 ");
		} else if ("viewkecheng".equals(this.typeflag)) {
			 if(us.getUserLoginType().equals("3"))	{
				 sql=" ,ec2.name as ComboBox_FlagIsvalid_NAME ,ec3.name as  ComboBox_FlagOffline_NAME ";
			 }
			sqlBuffer.append("SELECT * FROM (select pbtc.id,pbtc.code,pbtc.name,ec1.name as ComboBox_ZIGE_NAME "+sql+"  from pe_bzz_tch_course pbtc left join enum_const ec1 on pbtc.flag_coursecategory=ec1.id " );
			 if(us.getUserLoginType().equals("3"))	{
				 sqlBuffer.append( " inner join enum_const ec2 on pbtc.flag_isvalid=ec2.id   inner join enum_const ec3 on pbtc.flag_offline=ec3.id " );
			 }else {
				 sqlBuffer.append( " inner join enum_const ec2 on pbtc.flag_isvalid=ec2.id and ec2.code='1'  inner join enum_const ec3 on pbtc.flag_offline=ec3.id and ec3.code='0' " );
			 }	
			 sqlBuffer.append(" where pbtc.id  in (select ii.teachclass_id from interaction_teachclass_info ii inner join PE_RESOURCE zl on zl.id=ii.fk_ziliao where zl.id='"+this.getId()+"')  and pbtc.FLAG_COURSEAREA <> 'Coursearea0'  ) where 1=1");
		}
		try {
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					context.setParameters(params);
				}
			}
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				if (name.equals("ComboBox_ZIGE_NAME")) {
					name = "ComboBox_ZIGE_NAME";
				}
				if (name.equals("ComboBox_FlagIsvalid_NAME")) {
					name = "ComboBox_FlagIsvalid_NAME";
				}
				if (name.equals("ComboBox_FlagOffline_NAME")) {
					name = "ComboBox_FlagOffline_NAME";
				}
				if("ComboBox_ZIGE_NAME".equals(name)|| "ComboBox_FlagIsvalid_NAME".equals(name) || "ComboBox_FlagOffline_NAME".equals(name)){
					sqlBuffer.append(" and UPPER(" + name + ") = UPPER('" + value + "')");
					
				}else{			
						sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");	
					
				}
				
			} while (true);
			
			this.setSqlCondition(sqlBuffer);
			this.page = this.getGeneralService().getByPageSQL(
					sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}


	public String getTypeflag() {
		return typeflag;
	}

	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}

	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				if (action.startsWith("addResourceToCourse")) {
//					String zids=action.substring(action.indexOf("_")+1);
					String[] resourceIds = (String[]) ServletActionContext.getRequest().getSession().getAttribute("resourceIds");
					for(int i = 0; i < resourceIds.length; i++) {
						for (Object course_id : idList) {
							String del_sql = "DELETE FROM INTERACTION_TEACHCLASS_INFO ITI WHERE ITI.FK_ZILIAO = '" + resourceIds[i] + "' AND ITI.TEACHCLASS_ID = '" + course_id + "'";
							this.getGeneralService().executeBySQL(del_sql);
							String sqlString = "insert into interaction_teachclass_info (id,teachclass_id,type,fk_ziliao) values (s_interaction_teachclass_id.nextval,'"+course_id+"','KCZL','"
									+ resourceIds[i] + "')";
							this.getGeneralService()
									.executeBySQL(sqlString);
						}
					}
						
					map.clear();
					map.put("success", "true");
					map.put("info", msg + "操作成功");
					// this.setTogo("");
					return map;
				}
				if (action.startsWith("delCourse_")) {
					String zids=action.substring(action.indexOf("_")+1);
					
						for (Object course_id : idList) {
								String sqlString="delete  from interaction_teachclass_info ii where ii.teachclass_id='"+course_id+"' and ii.fk_ziliao='"+zids+"'";
								this.getGeneralService()
										.executeBySQL(sqlString);
					}
					map.clear();
					map.put("success", "true");
					map.put("info", msg + "操作成功");
					// this.setTogo("");
					return map;
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", msg + "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少选择一门课程");
			return map;
		}
		return map;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
