package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeResource;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class MyResourceAction extends MyBaseAction<PeResource> {
	UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

	@Override
	public void initGrid() {
		Set capabilitySet = us.getUserPriority().keySet();
		this.getGridConfig().setCapability(false, false, false);
		if(!"3".equals(us.getUserLoginType())) {
			this.getGridConfig().setTitle("我的资料");
		} else {
			this.getGridConfig().setTitle("待审核资料");
		}
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		
		this.getGridConfig().addColumn(this.getText("资料名称"), "name",
				true, true, true, "TextField", false, 200, "");
		
		ColumnConfig columnConfigFlagResourceType = new ColumnConfig(this.getText("资料分类"), "enumConstByFlagResourceType.name", true, true, true, "TextField", false, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagDocumentType'";
		columnConfigFlagResourceType.setComboSQL(sql1);
		this.getGridConfig().addColumn(columnConfigFlagResourceType); 
		
		ColumnConfig columnConfigResourceTag = new ColumnConfig(this.getText("资料标签"), "enumConstByFlagResourceTag.name", true, true, false, "TextField", true, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagResourceTag'";
		columnConfigResourceTag.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigResourceTag);
		
		this.getGridConfig().addColumn(this.getText("颁布/发布时间"), "replydate",
				false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("颁布/发布单位"),"fabuunit" ,true, true,true,"TextField",false,100,"");
		
		ColumnConfig columnConfigIsOpen = new ColumnConfig(this.getText("是否公开"), "enumConstByFlagIsOpen.name", true, true, true, "TextField", true, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid' and a.code <> '2'";
		columnConfigIsOpen.setComboSQL(sql3);
		this.getGridConfig().addColumn(columnConfigIsOpen);
		
		ColumnConfig columnConfigIsAudit = new ColumnConfig(this.getText("是否审核通过"), "enumConstByFlagIsAudit.name", true, false, true, "TextField", true, 100, "");
		String sql4 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid' and a.code <> '2'";
		columnConfigIsAudit.setComboSQL(sql4);
		this.getGridConfig().addColumn(columnConfigIsAudit);
		
		this.getGridConfig().addColumn(this.getText("浏览次数"),"views" ,false, false, false,"TextField",false,100,"");
		
		if("3".equals(us.getUserLoginType())) {
			ColumnConfig columnConfigInformationSource = new ColumnConfig(this.getText("资料来源"), "enumConstByInformationResource.name", true, false, true, "TextField", true, 100, "");
			String sql5 = "select a.id ,a.name from enum_const a where a.namespace='InformationResource'";
			columnConfigInformationSource.setComboSQL(sql5);
			this.getGridConfig().addColumn(columnConfigInformationSource);
			
			this.getGridConfig().addColumn(this.getText("上传人"),"creater" ,true, false, true, "TextField", false, 200, "");
		}
		
		if(!"3".equals(us.getUserLoginType())) {
			this
			.getGridConfig()
			.addRenderScript(
					this.getText("查看"),
					"{return '<a target=blank href=\"/cms/doumentDetail.htm?myId='+${value}+'&uid=" + us.getId() + "\" >查看</a>';}",
					"id");
		} else {
			this
			.getGridConfig()
			.addRenderScript(
					this.getText("查看"),
					"{return '<a target=blank href=\"/cms/doumentDetail.htm?myId='+${value}+'&uid=3\" >查看</a>';}",
					"id");
		}
		
		if (capabilitySet.contains(this.servletPath + "_apply.action")) {
			this.getGridConfig().addMenuFunction(this.getText("审核通过"), "FlagIsAudit.true");
		}
		
		if(!"3".equals(us.getUserLoginType())) {
			this.getGridConfig().addMenuFunction(this.getText("删除"), "del");
		}
		Map<String, String> defaultValueMap = new HashMap<String, String>();
		this.setDefaultValueMap(defaultValueMap);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeResource.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/myResource";
	}
	
	public void setBean(PeResource instance) {
		super.superSetBean(instance);

	}

	public PeResource getBean() {
		return super.superGetBean();
	}
	
	
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (select zl.id,zl.name,  e5.name resourceType, zl.resourcetags, nvl(to_char(zl.replydate, 'yyyy-MM-dd') ,'-')replydate ,zl.Fabuunit, e1.name isOpen, e2.name isAudit, zl.views, ");
			if("3".equals(us.getUserLoginType())) {
				sqlBuffer.append(" EC8.NAME INFORMATIONSOURCE, ");
			}
			sqlBuffer.append(" SU.LOGIN_ID creater, zl.flag_top from PE_RESOURCE zl " +
							" inner join enum_const e5 on zl.flag_resourceType = e5.id join enum_const e1 on zl.flag_isopen = e1.id join enum_const e2 on zl.flag_isaudit = e2.id join sso_user su on zl.creater = su.id ");
			if("3".equals(us.getUserLoginType())) {
				sqlBuffer.append(" JOIN ENUM_CONST EC6 ON ZL.FLAG_ISAUDIT = EC6.ID AND EC6.CODE = '0' JOIN ENUM_CONST EC7 ON ZL.FLAG_ISOPEN = EC7.ID AND EC7.CODE = '1' ");
				sqlBuffer.append(" JOIN ENUM_CONST EC8 ON ZL.INFORMATION_RESOURCE = EC8.ID ");
			}
			if("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {
				sqlBuffer.append(" where zl.creater = '" + us.getSsoUser().getId() + "' ");
				sqlBuffer.append(" UNION select zl.id,zl.name,  e5.name resourceType, zl.resourcetags, nvl(to_char(zl.replydate, 'yyyy-MM-dd') ,'-')replydate ,zl.Fabuunit, e3.name isOpen, e4.name isAudit, zl.views, su.login_id creater, zl.flag_top from PE_RESOURCE zl inner join enum_const e1 on zl.flag_isvalid=e1.id  and e1.code='1' ");
				sqlBuffer.append(" inner join enum_const e2 on zl.flag_offline = e2.id and e2.code = '0' inner join enum_const e5 on zl.flag_resourceType = e5.id ");
				sqlBuffer.append(" left join enum_const e3 on zl.flag_isopen = e3.id left join enum_const e4 on e4.id = zl.flag_isaudit join sso_user su on zl.creater = su.id ");
				sqlBuffer.append(" where zl.id in (SELECT RESOURCEID FROM SC_RESOURCE SR WHERE SR.USERID = '" + us.getId() + "') ");
			}
			sqlBuffer.append(" ) WHERE 1 = 1 ");
			/* 拼接查询条件 */
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1
						&& name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name
							.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				if (name.equals("name")) {
					name = "name";
				}
				/* 资料分类 */
				if (name.equals("enumConstByFlagResourceType.name")) {
					name = "resourceType";
				}
				/* 资料标签 */
				if (name.equals("enumConstByFlagResourceTag.name")) {
					name = "resourceTags";
				}
				/* 是否公开 */
				if (name.equals("enumConstByFlagIsOpen.name")) {
					name = "isopen";
				}
				/* 是否审核通过 */
				if (name.equals("enumConstByFlagIsAudit.name")) {
					name = "isaudit";
				}
				/* 资料来源 */
				if (name.equals("enumConstByInformationResource.name")) {
					name = "INFORMATIONSOURCE";
				}
				if("name".equals(name)){
					sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
					
				}else{			
					if(!name.equals("kbianhao")&&!name.equals("kname") || "resourceTags".equals(name)){
						sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");	
					} 
					
				}

			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 资料分类 */
				if (temp.equals("enumConstByFlagResourceType.name")) {
					temp = "resourceType";
				}
				/* 资料标签 */
				if (temp.equals("enumConstByFlagResourceTag.name")) {
					temp = "resourceTags";
				}
				/* 资料来源 */
				if (temp.equals("enumConstByInformationResource.name")) {
					temp = "INFORMATIONSOURCE";
				}
				if (temp.equals("id")) {
					temp = "flag_top";
				}

				if (this.getDir() != null
						&& this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " asc,replydate desc ");

				} else {
					sqlBuffer.append(" order by " + temp + " desc ");
				}

			} else {
				sqlBuffer.append(" order by flag_top asc,replydate desc");
			}
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
	
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
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
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeResource.class);
				if (action.equals("FlagIsAudit.true")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<PeResource> peResourceList = new ArrayList<PeResource>();
					peResourceList = this.getGeneralService().getList(tempdc);
					try {
						checkBeforeUpdateColumn(idList);
					} catch (EntityException e1) {
						map.put("success", "false");
						map.put("info", e1.getMessage());
						return map;
					}
					Iterator<PeResource> iterator = peResourceList.iterator();
					while (iterator.hasNext()) {
						PeResource peResource = iterator.next();
						if("1".equals(peResource.getEnumConstByFlagIsOpen().getCode()) && (peResource.getReplyDate() == null || "".equals(peResource.getReplyDate()))) {
							Date reDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));;
							peResource.setReplyDate(reDate);
						}
						if (!"0".equals(peResource.getEnumConstByFlagIsAudit().getCode())) {
							map.clear();
							map.put("success", "false");
							map.put("info", "操作失败,资料只有未审核通过状态才能审核通过");
							return map;
						}
						peResource.setEnumConstByFlagIsAudit(enumConst);
						this.getGeneralService().save(peResource);
					}
					msg = "资料审核通过";
					map.put("success", "true");
					map.put("info", "共有" + ids.length + "条资料" + msg + "操作成功，该资料已跳转至【资料库管理】列表中，请在资料库管理中继续完成资料发布等操作。");
				}
				
				if (action.equals("del")) {
					tempdc.add(Restrictions.in("id", ids));
					List<PeResource> peResourceList = new ArrayList<PeResource>();
					peResourceList = this.getGeneralService().getList(tempdc);
					try {
						checkBeforeUpdateColumn(idList);
					} catch (EntityException e1) {
						map.put("success", "false");
						map.put("info", e1.getMessage());
						return map;
					}
					Iterator<PeResource> iterator = peResourceList.iterator();
					while (iterator.hasNext()) {
						PeResource peResource = iterator.next();
						String sql = "";
						if(us.getSsoUser().getId().equals(peResource.getCreater())) {
							sql = "DELETE FROM PE_RESOURCE PR WHERE PR.ID = '" + peResource.getId() + "'";
						} else {
							sql = "DELETE FROM SC_RESOURCE SR WHERE SR.USERID = '" + us.getSsoUser().getId() + "' AND SR.RESOURCEID = '" + peResource.getId() + "'";
						}
						this.getGeneralService().executeBySQL(sql);
					}
					msg = "删除成功!";
					map.put("success", "true");
					map.put("info", "共有" + ids.length + "条资料" + msg + "操作成功!");
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
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	@Override
	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		String sql = "";
		List list = null;
		for(int i = 0; i < idList.size(); i++) {
			sql = "SELECT 1 FROM PE_RESOURCE PR WHERE PR.ID = '" + idList.get(i) + "'";
			list = this.getGeneralService().getBySQL(sql);
			if(list == null || list.size() == 0) {
				throw new EntityException("资料不存在或已删除，请确认后再执行操作！");
			}
		}
	}

	
	
	
}
