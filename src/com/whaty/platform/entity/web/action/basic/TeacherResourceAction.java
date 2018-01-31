package com.whaty.platform.entity.web.action.basic;

import java.io.Reader;
import java.sql.Clob;
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

import com.whaty.platform.database.oracle.standard.interaction.OracleInteractionTeachClass;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.TeacherInfo;
import com.whaty.platform.entity.bean.TeachingField;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.TeacherResourceService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class TeacherResourceAction extends MyBaseAction<TeacherInfo> {
	private String id;
	private TeacherResourceService teacherResourceService;
	private TeacherInfo teacherInfo;
	private List<TeachingField> fieldList = null;
	private String name;
	private String organization;
	private String department;
	private String code;
	private String mobile;
	private String email;
	private String description;
	private String fields;
	private String fieldIds;
	private String existFlag = "false";
	private String teacherId;
	private String msg;
	private List log_list;
	private String logIds;
	private Object[] oldTeacherInfo;
	private Object[] newTeacherInfo;
	private int count = 0;
	private String courseId;
	private String togo;
	public TeacherResourceService getTeacherResourceService() {
		return teacherResourceService;
	}

	public void setTeacherResourceService(
			TeacherResourceService teacherResourceService) {
		this.teacherResourceService = teacherResourceService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TeacherInfo getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(TeacherInfo teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

	public List<TeachingField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<TeachingField> fieldList) {
		this.fieldList = fieldList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getExistFlag() {
		return existFlag;
	}

	public void setExistFlag(String existFlag) {
		this.existFlag = existFlag;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getFieldIds() {
		return fieldIds;
	}

	public void setFieldIds(String fieldIds) {
		this.fieldIds = fieldIds;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List getLog_list() {
		return log_list;
	}

	public void setLog_list(List log_list) {
		this.log_list = log_list;
	}

	public String getLogIds() {
		return logIds;
	}

	public void setLogIds(String logIds) {
		this.logIds = logIds;
	}

	public Object[] getOldTeacherInfo() {
		return oldTeacherInfo;
	}

	public void setOldTeacherInfo(Object[] oldTeacherInfo) {
		this.oldTeacherInfo = oldTeacherInfo;
	}

	public Object[] getNewTeacherInfo() {
		return newTeacherInfo;
	}

	public void setNewTeacherInfo(Object[] newTeacherInfo) {
		this.newTeacherInfo = newTeacherInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTogo() {
		return togo;
	}

	public void setTogo(String toGo) {
		this.togo = toGo;
	}

	/**
	 * 初始化列表
	 * 
	 * @author Lee
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = false;
		boolean canUpdate = false;
		boolean canDelete = false;
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);

		this.getGridConfig().setTitle("远程培训师资库管理");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("讲师姓名"), "sg_tti_name", true, true, true, "TextField", false, 200);

		this.getGridConfig().addColumn(this.getText("所在机构"), "sg_tti_organization_name", true, true, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("工作部门"), "sg_tti_department_name", false, true, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("证件号码"), "sg_tti_code", true, true, true, "");

		this.getGridConfig().addColumn(this.getText("联系电话"), "sg_tti_mobile", true, true, true, "TextField", true, 25);
		
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "sg_tti_email", false, true, true, "TextField", true, 25);
		
		this.getGridConfig().addColumn(this.getText("授课数量"), "course_count", false, false, true, "TextField", true, 25);
		
		this.getGridConfig().addColumn(this.getText("修改人"), "opt_user", false, false, true, "TextField", true, 25);
		
		this.getGridConfig().addColumn(this.getText("修改时间"), "opt_time", false, false, true, "TextField", true, 25);
		
		if (capabilitySet.contains(this.servletPath + "_showCourse.action")) {
			this.getGridConfig().addRenderScript(this.getText("查看课程及其评价"),
					"{return '<a href=\"/entity/basic/teacherShowCourse.action?teacherId='+${value}+'\"  target=\"_blank\">查看课程及其评价</a>';}", "id");
		}
		
		if (capabilitySet.contains(this.servletPath + "_teacherInfo.action")) {
			this.getGridConfig().addRenderScript(this.getText("查看详情"),
					"{return '<a href=\"/entity/basic/teacherResource_showTeacherInfo.action?id='+${value}+'\"  target=\"_blank\">查看详情</a>';}", "id");
		}
		
		if (capabilitySet.contains(this.servletPath + "_update.action")) {
			this.getGridConfig().addRenderScript(this.getText("修改"),
					"{return '<a href=\"/entity/basic/teacherResource_toUpdate.action?id='+${value}+'\" >修改</a>';}", "id");
		}
		
		if(capabilitySet.contains(this.servletPath + "_operationInfo.action")) {
			this.getGridConfig().addRenderScript(this.getText("操作记录"),
					"{return '<a href=\"/entity/basic/teacherResource_operationInfo.action?id='+${value}+'\"  target=\"_blank\">操作记录</a>';}", "id");
		}

		Map<String, String> defaultValueMap = new HashMap<String, String>();
		defaultValueMap.put("passRole", "80");
		defaultValueMap.put("examTimesAllow", "5");
		defaultValueMap.put("studyDates", "90");
		this.setDefaultValueMap(defaultValueMap);
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = TeacherInfo.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/teacherResource";
	}

	public void setBean(TeacherInfo instance) {
		super.superSetBean(instance);

	}

	public TeacherInfo getBean() {
		return super.superGetBean();
	}
	
	/**
	 * 讲师库列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT stti.sg_tti_id, stti.sg_tti_name, stti.sg_tti_organization_name, ");
			sqlBuffer.append(" stti.sg_tti_department_name, stti.sg_tti_code, stti.sg_tti_mobile, stti.sg_tti_email,  ");
			sqlBuffer.append(" (select count(pbtc.id) course_count from pe_bzz_tch_course pbtc where pbtc.teacher is not null ");
			sqlBuffer.append(" and pbtc.announce_date <> pbtc.create_date and pbtc.teacher_id = stti.sg_tti_id) course_count,");
			sqlBuffer.append(" (select sl.opt_user from (select sttil.sg_tti_id,sttil.opt_user from sg_train_terchaer_info_log sttil where sttil.log_id is not null order by sttil.opt_time desc) sl where sl.sg_tti_id = stti.sg_tti_id and rownum <= 1) opt_user,");
			sqlBuffer.append(" (select sl.opt_time from (select sttil.sg_tti_id,sttil.opt_time from sg_train_terchaer_info_log sttil where sttil.log_id is not null order by sttil.opt_time desc) sl where sl.sg_tti_id = stti.sg_tti_id and rownum <= 1) opt_time,");
			sqlBuffer.append(" stti.add_time ");
			sqlBuffer.append(" from sg_train_terchaer_info stti where stti.sg_tti_is_hzph = '0' order by stti.add_time desc ");
			sqlBuffer.append(" ) WHERE 1 = 1 ");
			/* 拼接查询条件 */
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
				sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("id")) {
					temp = "add_time";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");
				} else {
					sqlBuffer.append(" order by " + temp + " asc ");
				}
			} else {
				sqlBuffer.append(" order by add_time desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	public String toUpdate() {
		String hql = "from TeacherInfo where id = '" + id + "'";
		try {
			teacherInfo = (TeacherInfo) this.getGeneralService().getByHQL(hql).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(teacherInfo.getSg_tti_code() != null && !"".equals(teacherInfo.getSg_tti_code())) {
			String field_hql = "from TeachingField";
			try {
				fieldList = this.getGeneralService().getByHQL(field_hql);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			return "checkCode";
		} else {
			return "toUpdate";
		}
	}
	
	/**
	 * 根据身份证信息验证讲师信息是否存在
	 * @return
	 */
	public String checkCode() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String hql = "from TeacherInfo where id = '" + id + "'";
		String field_hql = "from TeachingField";
		try {
			fieldList = this.getGeneralService().getByHQL(field_hql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		String existHql = " from TeacherInfo where sg_tti_code = '" + code + "'";
		try {
			List<TeacherInfo> list = this.getGeneralService().getByHQL(existHql);
			if(list != null && list.size() > 0) {
				existFlag = "true";
				teacherInfo = (TeacherInfo) list.get(0);
				teacherId = id;
			} else {
				teacherInfo = (TeacherInfo) this.getGeneralService().getByHQL(hql).get(0);
				if(code != null && !"".equals(code)) {
					String sql = "";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String modifyTime = sdf.format(new Date());
					/* 记录日志 */
					try {
						sql = "insert into SG_TRAIN_TERCHAER_INFO_LOG(SG_TTI_ID,SG_TFC_ID,SCO_ID," +
								"CTCE_ID,ECO_ID,SG_TTI_NAME,SG_TTI_CODE,SG_TTI_ORGANIZATION_NAME,SG_TTI_DEPARTMENT_NAME," +
								"SG_TTI_POSITION,SG_TTI_MOBILE,SG_TTI_PHONE,SG_TTI_GUOJI,SG_TTI_EMAIL,SG_TTI_LEVEL," +
								"SG_TTI_PHOTO_PATH,TFC_NAME,SG_TTI_DATA_TYPE,SG_BIRTHDAY,STEC_ID,SG_PRO,SG_PRO_NAME," +
								"ADD_TIME,SG_TTI_DISCRIPTION,SG_TTI_IS_COMPLETE,SG_TTI_IS_HZPH,SG_TTI_IS_CYRY,SG_CY_VALID,LOG_ID," +
								"OPT_USER,OPT_TIME,OPT_IP,OPT_TYPE) values('" + 
								teacherInfo.getId() + "','" + 
								teacherInfo.getSg_tfc_id() + "','" +
								teacherInfo.getSco_id() + "','" + 
								teacherInfo.getCtce_id() + "','" +
								teacherInfo.getEco_id() + "','" +
								teacherInfo.getSg_tti_name() + "','" +
								teacherInfo.getSg_tti_code() + "','" +
								teacherInfo.getSg_tti_organization_name() + "','" +
								teacherInfo.getSg_tti_department_name() + "','" +
								teacherInfo.getSg_tti_position() + "','" +
								teacherInfo.getSg_tti_mobile() + "','" +
								teacherInfo.getSg_tti_phone() + "','" +
								teacherInfo.getSg_tti_guoji() + "','" +
								teacherInfo.getSg_tti_email() + "','" +
								teacherInfo.getSg_tti_level() + "','" +
								teacherInfo.getSg_tti_photo_path() + "','" +
								teacherInfo.getTfc_name() + "','" +
								teacherInfo.getSg_tti_data_type() + "','" +
								teacherInfo.getSg_birthday() + "','" +
								teacherInfo.getStec_id() + "','" +
								teacherInfo.getSg_pro() + "','" +
								teacherInfo.getSg_pro_name() + "','" +
								teacherInfo.getAdd_time() + "','" +
								teacherInfo.getSg_tti_description() + "','" +
								teacherInfo.getSg_tti_is_complete() + "','" +
								teacherInfo.getSg_tti_is_hzph() + "','" +
								teacherInfo.getSg_tti_is_cyry() + "','0'," +
								"sys_guid(), '" + us.getSsoUser().getLoginId() +
								"','" + modifyTime + "','" + 
								this.getIpAddr() + "','2')";
					} catch (Exception e) {
						e.printStackTrace();
					}
					this.getGeneralService().executeBySQL(sql);
					teacherInfo.setSg_tti_code(code);
					teacherInfo.setSg_tti_is_complete("0");
				}
			}
				
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "checkCode";
	}
	
	public String modify() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String hql = "from TeacherInfo where id = '" + id + "'";
		try {
			teacherInfo = (TeacherInfo) this.getGeneralService().getByHQL(hql).get(0);
			String sql = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String modifyTime = sdf.format(new Date());
			/* 记录日志 */
			try {
				sql = "insert into SG_TRAIN_TERCHAER_INFO_LOG(SG_TTI_ID,SG_TFC_ID,SCO_ID," +
						"CTCE_ID,ECO_ID,SG_TTI_NAME,SG_TTI_CODE,SG_TTI_ORGANIZATION_NAME,SG_TTI_DEPARTMENT_NAME," +
						"SG_TTI_POSITION,SG_TTI_MOBILE,SG_TTI_PHONE,SG_TTI_GUOJI,SG_TTI_EMAIL,SG_TTI_LEVEL," +
						"SG_TTI_PHOTO_PATH,TFC_NAME,SG_TTI_DATA_TYPE,SG_BIRTHDAY,STEC_ID,SG_PRO,SG_PRO_NAME," +
						"ADD_TIME,SG_TTI_DISCRIPTION,SG_TTI_IS_COMPLETE,SG_TTI_IS_HZPH,SG_TTI_IS_CYRY,SG_CY_VALID,LOG_ID," +
						"OPT_USER,OPT_TIME,OPT_IP,OPT_TYPE) values('" + 
						teacherInfo.getId() + "','" + 
						teacherInfo.getSg_tfc_id() + "','" +
						teacherInfo.getSco_id() + "','" + 
						teacherInfo.getCtce_id() + "','" +
						teacherInfo.getEco_id() + "','" +
						teacherInfo.getSg_tti_name() + "','" +
						teacherInfo.getSg_tti_code() + "','" +
						teacherInfo.getSg_tti_organization_name() + "','" +
						teacherInfo.getSg_tti_department_name() + "','" +
						teacherInfo.getSg_tti_position() + "','" +
						teacherInfo.getSg_tti_mobile() + "','" +
						teacherInfo.getSg_tti_phone() + "','" +
						teacherInfo.getSg_tti_guoji() + "','" +
						teacherInfo.getSg_tti_email() + "','" +
						teacherInfo.getSg_tti_level() + "','" +
						teacherInfo.getSg_tti_photo_path() + "','" +
						teacherInfo.getTfc_name() + "','" +
						teacherInfo.getSg_tti_data_type() + "','" +
						teacherInfo.getSg_birthday() + "','" +
						teacherInfo.getStec_id() + "','" +
						teacherInfo.getSg_pro() + "','" +
						teacherInfo.getSg_pro_name() + "','" +
						teacherInfo.getAdd_time() + "','" +
						teacherInfo.getSg_tti_description() + "','" +
						teacherInfo.getSg_tti_is_complete() + "','" +
						teacherInfo.getSg_tti_is_hzph() + "','" +
						teacherInfo.getSg_tti_is_cyry() + "','0'," +
						"sys_guid(), '" + us.getSsoUser().getLoginId() +
						"','" + modifyTime + "','" + 
						this.getIpAddr() + "','2')";
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.getGeneralService().executeBySQL(sql);
			//修改讲师简介
			String update_teacher_slq = "update sg_train_terchaer_info set sg_tti_name = '" + name + "'," +
					"sg_tti_organization_name = '" + organization + "'," +
					"sg_tti_department_name = '" + department + "'," +
					"sg_tti_mobile = '" + mobile + "'," +
					"sg_tti_email = '" + email + "'," +
					"sg_tti_discription = '" + description + "'," +
					"sg_tfc_id = '" + fieldIds + "'," +
					"tfc_name = '" + fields + "' where sg_tti_id = '" + teacherInfo.getId() + "'";
			this.getGeneralService().executeBySQL(update_teacher_slq);
			msg = "修改成功!";
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public String showTeacherInfo() {
		if(id.indexOf("-") != -1) {
			String[] ids = id.split("-");
			id = ids[0];
		}
		String hql = "from TeacherInfo where id = '" + id + "'";
		String sql = "SELECT ID, SG_TTI_NAME, SG_TTI_ORGANIZATION_NAME, SG_TTI_DEPARTMENT_NAME, SG_TTI_CODE, SG_TTI_MOBILE, SG_TTI_EMAIL, SG_TTI_DISCRIPTION, TFC_NAME FROM TEACHER_MERGE_INFO WHERE SG_TTI_ID = '" + id + "'";
		try {
			List list = this.getGeneralService().getByHQL(hql);
			if(list != null && list.size() > 0) {
				teacherInfo = (TeacherInfo) list.get(0);
			} else {
				list = this.getGeneralService().getBySQL(sql);
				if(list != null && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					String data = "";
					Reader inStream = null;
					Clob clob = (Clob) obj[7];
					if (clob != null && !"".equals(clob)) {
						inStream = clob.getCharacterStream();
						char[] c = new char[(int) clob.length()];
						inStream.read(c);
						data = new String(c);
					}
					teacherInfo = new TeacherInfo();
					teacherInfo.setId((String)obj[0]);
					teacherInfo.setSg_tti_name((String)obj[1]);
					teacherInfo.setSg_tti_organization_name((String)obj[2]);
					teacherInfo.setSg_tti_department_name((String)obj[3]);
					teacherInfo.setSg_tti_code((String)obj[4]);
					teacherInfo.setSg_tti_mobile((String)obj[5]);
					teacherInfo.setSg_tti_email((String)obj[6]);
					teacherInfo.setSg_tti_description(data);
					teacherInfo.setTfc_name((String)obj[8]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showTeacherInfo";
	}
	
	/**
	 * lwq 2014-11-30 合并数据
	 * @return
	 */
	public String merge() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String hql = "from TeacherInfo where id = '" + id + "'";
		String course_sql = "select pbtc.id from pe_bzz_tch_course pbtc where pbtc.teacher_id = '" + teacherId + "'";
		String deleteHql = "delete from TeacherInfo where id='" + teacherId + "'";
		String mergeTeacherSql = "INSERT INTO TEACHER_MERGE_INFO SELECT SYS_GUID(), SG_TTI_ID, SG_TFC_ID, SG_TTI_NAME, SG_TTI_CODE, SG_TTI_ORGANIZATION_NAME," +
				"SG_TTI_DEPARTMENT_NAME, SG_TTI_MOBILE, SG_TTI_EMAIL, TFC_NAME, SG_TTI_DATA_TYPE, ADD_TIME, SG_TTI_DISCRIPTION, SG_TTI_IS_COMPLETE, SG_TTI_IS_HZPH," +
				"SG_TTI_IS_CYRY, '" + id + "' FROM SG_TRAIN_TERCHAER_INFO STTI WHERE STTI.SG_TTI_ID = '" + teacherId + "'";
		TeacherInfo teacher = null;
		try {
			this.getGeneralService().executeBySQL(mergeTeacherSql);//备份被删除的讲师数据
			teacher = (TeacherInfo) this.getGeneralService().getByHQL(hql).get(0);
			/* 记录日志 */
			String sql = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String modifyTime = sdf.format(new Date());
			try {
				sql = "insert into SG_TRAIN_TERCHAER_INFO_LOG(SG_TTI_ID,SG_TFC_ID,SCO_ID," +
						"CTCE_ID,ECO_ID,SG_TTI_NAME,SG_TTI_CODE,SG_TTI_ORGANIZATION_NAME,SG_TTI_DEPARTMENT_NAME," +
						"SG_TTI_POSITION,SG_TTI_MOBILE,SG_TTI_PHONE,SG_TTI_GUOJI,SG_TTI_EMAIL,SG_TTI_LEVEL," +
						"SG_TTI_PHOTO_PATH,TFC_NAME,SG_TTI_DATA_TYPE,SG_BIRTHDAY,STEC_ID,SG_PRO,SG_PRO_NAME," +
						"ADD_TIME,SG_TTI_DISCRIPTION,SG_TTI_IS_COMPLETE,SG_TTI_IS_HZPH,SG_TTI_IS_CYRY,SG_CY_VALID,LOG_ID," +
						"OPT_USER,OPT_TIME,OPT_IP,OPT_TYPE) values('" + 
						teacher.getId() + "','" + 
						teacher.getSg_tfc_id() + "','" +
						teacher.getSco_id() + "','" + 
						teacher.getCtce_id() + "','" +
						teacher.getEco_id() + "','" +
						teacher.getSg_tti_name() + "','" +
						teacher.getSg_tti_code() + "','" +
						teacher.getSg_tti_organization_name() + "','" +
						teacher.getSg_tti_department_name() + "','" +
						teacher.getSg_tti_position() + "','" +
						teacher.getSg_tti_mobile() + "','" +
						teacher.getSg_tti_phone() + "','" +
						teacher.getSg_tti_guoji() + "','" +
						teacher.getSg_tti_email() + "','" +
						teacher.getSg_tti_level() + "','" +
						teacher.getSg_tti_photo_path() + "','" +
						teacher.getTfc_name() + "','" +
						teacher.getSg_tti_data_type() + "','" +
						teacher.getSg_birthday() + "','" +
						teacher.getStec_id() + "','" +
						teacher.getSg_pro() + "','" +
						teacher.getSg_pro_name() + "','" +
						teacher.getAdd_time() + "','" +
						teacher.getSg_tti_description() + "','" +
						teacher.getSg_tti_is_complete() + "','" +
						teacher.getSg_tti_is_hzph() + "','" +
						teacher.getSg_tti_is_cyry() + "','0'," +
						"sys_guid(), '" + us.getSsoUser().getLoginId() +
						"','" + modifyTime + "','" + 
						this.getIpAddr() + "','2')";
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.getGeneralService().executeBySQL(sql);
			String updateTeacherSql = "update sg_train_terchaer_info stti set stti.sg_tti_name='" + name + "'," +
					"stti.sg_tti_organization_name='" + organization + "'," +
					"stti.sg_tti_department_name='" + department + "',stti.sg_tti_mobile='" + mobile + "',stti.sg_tti_email='" + email + "'," +
					"stti.sg_tti_discription='" + description + "',stti.sg_tfc_id='" + fieldIds + "'," +
					"stti.tfc_name='" + fields + "',stti.sg_tti_is_hzph='0'";
			if(teacher != null && teacher.getSg_tti_code() != null && !"".equals(teacher.getSg_tti_code())){
				updateTeacherSql += ",stti.sg_tti_is_complete='0'";
			}
			updateTeacherSql += " where stti.sg_tti_id='" + id + "'";
			this.getGeneralService().executeBySQL(updateTeacherSql);
			
			List courseList = this.getGeneralService().getBySQL(course_sql);
			//修改课程讲师
			String updateSql = "update pe_bzz_tch_course pbtc set pbtc.teacher_id = '" + id + "',pbtc.teacher = '" + name + "' where pbtc.id in('";
			if(courseList != null && courseList.size() > 0) {
				for(int i = 0; i < courseList.size(); i++) {
					updateSql += courseList.get(i) + "','";
				}
				updateSql += "')";
				this.getGeneralService().executeBySQL(updateSql);
			}
			
			//修改讲师简介
			
			this.getGeneralService().executeByHQL(deleteHql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		msg = "合并数据成功!";
		return "success";
	}
	
	/**
	 * lwq 查看操作日志 2014-12-1
	 * @return
	 */
	public String operationInfo() {
		String sql = "select sttil.log_id,decode(sttil.sg_tti_name,'null','',sttil.sg_tti_name), " +
				" decode(sttil.sg_tti_organization_name,'null','',sttil.sg_tti_organization_name), " +
				" decode(sttil.sg_tti_department_name,'null','',sttil.sg_tti_department_name), " +
				" decode(sttil.sg_tti_code,'null','',sttil.sg_tti_code),decode(sttil.sg_tti_mobile,'null','',sttil.sg_tti_mobile), " +
				" decode(sttil.sg_tti_email,'null','',sttil.sg_tti_email),sttil.opt_user,sttil.opt_ip,sttil.opt_time, " +
				" case when sttil.opt_type = '1' then '新增' " +
				" when sttil.opt_type = '2' then '修改' else '删除' end opt_type " +
				" from sg_train_terchaer_info_log sttil where sttil.sg_tti_id = '" + id + "' and sttil.log_id is not null order by sttil.opt_time desc";
		try {
			log_list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "log_list";
	}
	
	/**
	 * lwq 2014-12-02 日志对比
	 * @return
	 */
	public String doCompare() {
		String[] ids = logIds.split(",");
		count = ids.length;
		String oldSql = "select decode(sttil.sg_tti_name,'null','',sttil.sg_tti_name), decode(sttil.sg_tti_organization_name,'null','',sttil.sg_tti_organization_name), " +
				" decode(sttil.sg_tti_department_name,'null','',sttil.sg_tti_department_name), " +
				" decode(sttil.sg_tti_code,'null','',sttil.sg_tti_code), decode(sttil.sg_tti_mobile,'null','',sttil.sg_tti_mobile), " +
				" decode(sttil.sg_tti_email,'null','',sttil.sg_tti_email),to_char(sttil.sg_tti_discription), " +
				" decode(sttil.tfc_name,'null','',sttil.tfc_name), sttil.opt_user, sttil.opt_ip, " +
				" sttil.opt_time, case when sttil.opt_type = '1' then '新增' when sttil.opt_type = '2' then '修改' else '删除' end opt_type " +
				" from sg_train_terchaer_info_log sttil where sttil.log_id in ('";
		String newSql = "";
		for(int i = 0; i < ids.length; i++) {
			oldSql += ids[i] + "','";
		}
		oldSql += "') order by sttil.opt_time";
		try {
			List list = this.getGeneralService().getBySQL(oldSql);
			Object[] obj = (Object[]) list.get(0);
			oldTeacherInfo = obj;
			if(ids.length == 2) {
				obj = null;
				obj = (Object[]) list.get(1);
				newTeacherInfo = obj;
			} else {
				String sql = "select sttil.sg_tti_id from sg_train_terchaer_info_log sttil where sttil.log_id='" + ids[0] + "'";
				id = (String) this.getGeneralService().getBySQL(sql).get(0);
				newSql = "from TeacherInfo where id='" + id + "'";
				teacherInfo = (TeacherInfo) this.getGeneralService().getByHQL(newSql).get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toCompare";
	}
	
	/**
	 * lwq 2016-01-13 讲师简介
	 * @return
	 */
	public String showTeacherNote() {
		String course_sql = "SELECT PBTC.TEACHER from PE_BZZ_TCH_COURSE PBTC WHERE PBTC.ID = '" + courseId + "'";
		try {
			String teacher_name = (String) this.getGeneralService().getBySQL(course_sql).get(0);
			// 讲师校验
			List list = null;
			if (teacher_name != null && !"".equals(teacher_name)) {
				String note_sql = "select t.content  as content from interaction_teachclass_info t where t.teachclass_id = '" + courseId
						+ "' and t.type = 'JSJJ'";
				String note = "";
				List note_list;
				
					note_list = this.getGeneralService().getBySQL(note_sql);
				
				if (note_list.size() > 0) {
					note = (String) note_list.get(0);
				}
				String hql = "from TeacherInfo where sg_tti_name = '" + teacher_name + "'";
				list = this.getGeneralService().getByHQL(hql);
				if (list != null && list.size() > 0) {
					teacherInfo = (TeacherInfo) list.get(0);
					if (teacherInfo.getSg_tti_is_hzph() == null || "".equals(teacherInfo.getSg_tti_is_hzph()) || "1".equals(teacherInfo.getSg_tti_is_hzph())) {
						String update_teacher = "update sg_train_terchaer_info set sg_tti_is_hzph = '0' where sg_tti_id = '" + teacherInfo.getId() + "'";
						this.getGeneralService().executeBySQL(update_teacher);
					}
				} else {
					teacherInfo = new TeacherInfo();
					teacherInfo.setSg_tti_name(teacher_name);
					teacherInfo.setSg_tti_description(note);
					teacherInfo.setSg_tti_data_type("2");
					teacherInfo.setSg_tti_is_complete("1");
					teacherInfo.setSg_tti_is_hzph("0");
					teacherInfo.setSg_tti_is_cyry("1");
					teacherInfo.setAdd_time(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
//					String insert_sql = "insert into sg_train_terchaer_info(sg_tti_id,sg_tti_name,sg_tti_discription,sg_tti_data_type,"
//							+ "sg_tti_is_complete,sg_tti_is_hzph,sg_tti_is_cyry,add_time) values (sys_guid(),'" + teacher_name + "','" + note
//							+ "','2','1','0','1',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
//					this.getGeneralService().executeBySQL(insert_sql);
					this.getGeneralService().save(teacherInfo);
					list = this.getGeneralService().getByHQL(hql);
					teacherInfo = null;
					teacherInfo = (TeacherInfo) list.get(0);
				}
				String update_sql = "UPDATE PE_BZZ_TCH_COURSE SET TEACHER_ID = '" + teacherInfo.getId() + "' WHERE ID = '" + courseId + "'";
				this.getGeneralService().executeBySQL(update_sql);
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return "show_note";
	}
	
	
	public String toAddNote() {
		try {
			teacherInfo = this.getGeneralService().getById(id);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toAddNote";
	}
	
	public String addNote() {
		try {
			teacherInfo = this.getGeneralService().getById(id);
			teacherInfo.setSg_tti_description(description);
			String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd").format(new Date());
			teacherInfo.setAdd_time(time);
			this.getGeneralService().update(teacherInfo);
			//原表中增加记录
			String fileLink = "";
			if (teacherInfo.getSg_tti_description().length() > 0 && teacherInfo.getSg_tti_description().toLowerCase().indexOf("</a>") != -1) {
				int startIdx = teacherInfo.getSg_tti_description().toLowerCase().indexOf("<");
				int endIdx = teacherInfo.getSg_tti_description().toLowerCase().lastIndexOf(">");
				//截取多个上传附件路径并以","分隔
				fileLink = teacherInfo.getSg_tti_description().substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
			}
			String sql = "insert into interaction_teachclass_info (id,teachclass_id,title,content,type,file_link) values (s_interaction_teachclass_id.nextval,'" + courseId + "','"
				+ teacherInfo.getSg_tti_name() + "','" + teacherInfo.getSg_tti_description() + "','JSJJ','" + fileLink + "')";
			this.getGeneralService().executeBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		msg = "添加成功!";
		togo = "/entity/basic/teacherResource_showTeacherNote.action?courseId=" + courseId;
		return "m_msg";
	}
	
	/**
	 * lwq 2016-01-13 修改讲师简介
	 * @return
	 */
	public String toEditNote() {
		try {
			teacherInfo = this.getGeneralService().getById(id);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toEditNote";
	}
	
	public String editNote() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			String modifyTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd").format(new Date());
			teacherInfo = this.getGeneralService().getById(id);
			
			String sql = "insert into SG_TRAIN_TERCHAER_INFO_LOG(SG_TTI_ID,SG_TFC_ID,SCO_ID," +
			"CTCE_ID,ECO_ID,SG_TTI_NAME,SG_TTI_CODE,SG_TTI_ORGANIZATION_NAME,SG_TTI_DEPARTMENT_NAME," +
			"SG_TTI_POSITION,SG_TTI_MOBILE,SG_TTI_PHONE,SG_TTI_GUOJI,SG_TTI_EMAIL,SG_TTI_LEVEL," +
			"SG_TTI_PHOTO_PATH,TFC_NAME,SG_TTI_DATA_TYPE,SG_BIRTHDAY,STEC_ID,SG_PRO,SG_PRO_NAME," +
			"ADD_TIME,SG_TTI_DISCRIPTION,SG_TTI_IS_COMPLETE,SG_TTI_IS_HZPH,SG_TTI_IS_CYRY,SG_CY_VALID,LOG_ID," +
			"OPT_USER,OPT_TIME,OPT_IP,OPT_TYPE) values('" + 
			teacherInfo.getId() + "','" + 
			teacherInfo.getSg_tfc_id() + "','" +
			teacherInfo.getSco_id() + "','" + 
			teacherInfo.getCtce_id() + "','" +
			teacherInfo.getEco_id() + "','" +
			teacherInfo.getSg_tti_name() + "','" +
			teacherInfo.getSg_tti_code() + "','" +
			teacherInfo.getSg_tti_organization_name() + "','" +
			teacherInfo.getSg_tti_department_name() + "','" +
			teacherInfo.getSg_tti_position() + "','" +
			teacherInfo.getSg_tti_mobile() + "','" +
			teacherInfo.getSg_tti_phone() + "','" +
			teacherInfo.getSg_tti_guoji() + "','" +
			teacherInfo.getSg_tti_email() + "','" +
			teacherInfo.getSg_tti_level() + "','" +
			teacherInfo.getSg_tti_photo_path() + "','" +
			teacherInfo.getTfc_name() + "','" +
			teacherInfo.getSg_tti_data_type() + "','" +
			teacherInfo.getSg_birthday() + "','" +
			teacherInfo.getStec_id() + "','" +
			teacherInfo.getSg_pro() + "','" +
			teacherInfo.getSg_pro_name() + "','" +
			teacherInfo.getAdd_time() + "','" +
			teacherInfo.getSg_tti_description() + "','" +
			teacherInfo.getSg_tti_is_complete() + "','" +
			teacherInfo.getSg_tti_is_hzph() + "','" +
			teacherInfo.getSg_tti_is_cyry() + "','0'," +
			"sys_guid(), '" + us.getSsoUser().getLoginId() +
			"','" + modifyTime + "','" + 
			this.getIpAddr() + "','2')";
			this.getGeneralService().executeBySQL(sql);
			
			teacherInfo.setSg_tti_description(description);
			this.getGeneralService().update(teacherInfo);
		} catch (EntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		msg = "修改成功!";
		togo = "/entity/basic/teacherResource_showTeacherNote.action?courseId=" + courseId;
		return "m_msg";
	}
	
	public String deleNote() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			String modifyTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd").format(new Date());
			teacherInfo = this.getGeneralService().getById(id);
			String sql = "insert into SG_TRAIN_TERCHAER_INFO_LOG(SG_TTI_ID,SG_TFC_ID,SCO_ID," +
			"CTCE_ID,ECO_ID,SG_TTI_NAME,SG_TTI_CODE,SG_TTI_ORGANIZATION_NAME,SG_TTI_DEPARTMENT_NAME," +
			"SG_TTI_POSITION,SG_TTI_MOBILE,SG_TTI_PHONE,SG_TTI_GUOJI,SG_TTI_EMAIL,SG_TTI_LEVEL," +
			"SG_TTI_PHOTO_PATH,TFC_NAME,SG_TTI_DATA_TYPE,SG_BIRTHDAY,STEC_ID,SG_PRO,SG_PRO_NAME," +
			"ADD_TIME,SG_TTI_DISCRIPTION,SG_TTI_IS_COMPLETE,SG_TTI_IS_HZPH,SG_TTI_IS_CYRY,SG_CY_VALID,LOG_ID," +
			"OPT_USER,OPT_TIME,OPT_IP,OPT_TYPE) values('" + 
			teacherInfo.getId() + "','" + 
			teacherInfo.getSg_tfc_id() + "','" +
			teacherInfo.getSco_id() + "','" + 
			teacherInfo.getCtce_id() + "','" +
			teacherInfo.getEco_id() + "','" +
			teacherInfo.getSg_tti_name() + "','" +
			teacherInfo.getSg_tti_code() + "','" +
			teacherInfo.getSg_tti_organization_name() + "','" +
			teacherInfo.getSg_tti_department_name() + "','" +
			teacherInfo.getSg_tti_position() + "','" +
			teacherInfo.getSg_tti_mobile() + "','" +
			teacherInfo.getSg_tti_phone() + "','" +
			teacherInfo.getSg_tti_guoji() + "','" +
			teacherInfo.getSg_tti_email() + "','" +
			teacherInfo.getSg_tti_level() + "','" +
			teacherInfo.getSg_tti_photo_path() + "','" +
			teacherInfo.getTfc_name() + "','" +
			teacherInfo.getSg_tti_data_type() + "','" +
			teacherInfo.getSg_birthday() + "','" +
			teacherInfo.getStec_id() + "','" +
			teacherInfo.getSg_pro() + "','" +
			teacherInfo.getSg_pro_name() + "','" +
			teacherInfo.getAdd_time() + "','" +
			teacherInfo.getSg_tti_description() + "','" +
			teacherInfo.getSg_tti_is_complete() + "','" +
			teacherInfo.getSg_tti_is_hzph() + "','" +
			teacherInfo.getSg_tti_is_cyry() + "','0'," +
			"sys_guid(), '" + us.getSsoUser().getLoginId() +
			"','" + modifyTime + "','" + 
			this.getIpAddr() + "','2')";
			this.getGeneralService().executeBySQL(sql);
			
			teacherInfo.setSg_tti_description("");
			this.getGeneralService().update(teacherInfo);
		} catch (EntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		msg = "删除成功!";
		togo = "/entity/basic/teacherResource_showTeacherNote.action?courseId=" + courseId;
		return "m_msg";
	}
	
}
