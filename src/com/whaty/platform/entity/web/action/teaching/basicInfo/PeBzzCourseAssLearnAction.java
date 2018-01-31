package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzCourseAssLearnAction extends MyBaseAction<PeBzzTchCourse> {

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle("协会内训课程学习");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true, true, true, Const.coursecode_for_extjs);

		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, true, true, "TextField", false, 200, "");

		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, false, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);

		ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, false, true, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnConfigCategory.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory);

		ColumnConfig columnCourseItemType = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true, false, true, "TextField", false, 100, "");
		String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemType.setComboSQL(sql7);
		this.getGridConfig().addColumn(columnCourseItemType);

		ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, false, true, "TextField", false, 100, "");
		String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sql9);
		this.getGridConfig().addColumn(columnContentProperty);
		
		ColumnConfig columnCourseArea = new ColumnConfig(this.getText("课程来源"), "enumConstByFlagCourseArea.name", true, false, true, "TextField", false, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagCoursearea' and a.code <> '0'";
		columnCourseArea.setComboSQL(sql1);
		this.getGridConfig().addColumn(columnCourseArea);

		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher", true, true, true, "TextField", true, 25);

		ColumnConfig columnLearnStatus = new ColumnConfig(this.getText("学习状态"), "enumConstByFlagLearnStatus.name", true, false, true, "TextField", false, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='LearnStatus'";
		columnLearnStatus.setComboSQL(sql3);
		this.getGridConfig().addColumn(columnLearnStatus);
		
		ColumnConfig columnExamStatus = new ColumnConfig(this.getText("考试结果"), "enumConstByFlagExamStatus.name", true, false, true, "TextField", false, 100, "");
		String sql4 = "select a.id ,a.name from enum_const a where a.namespace='ExamStatus'";
		columnExamStatus.setComboSQL(sql4);
		this.getGridConfig().addColumn(columnExamStatus);
		
		this.getGridConfig().addColumn(this.getText("完成后是否能继续访问"), "visitafterpass", false, false, false, "");
		
		this.getGridConfig().addColumn(this.getText("是否考试"), "isExam", false, false, false, "");
		
		this.getGridConfig().addRenderScript(this.getText("课程信息"), 
			"{if(record.data['enumConstByFlagCourseArea.name'] == '协会内训课程'){return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCourseAssManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';} " +
			"else if(record.data['enumConstByFlagCourseArea.name'] == '监管机构内训课程'){ return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCourseRegManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';} else if(record.data['enumConstByFlagCourseArea.name'] == '监管内训课程'){ return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCourseRegManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}" +
			"else if(record.data['enumConstByFlagCourseArea.name'] == '公司内训课程'){ return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCourseInternal_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}" +
			"else if(record.data['enumConstByFlagCourseArea.name'] == '公共区课程'){ return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCoursePubManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}" +
			"else if(record.data['enumConstByFlagCourseArea.name'] == '专项课程'){ return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/peBzzCourseBatManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}}",
			"id");
		
		if (capabilitySet.contains("/entity/teaching/peAssCourseDetail_learnCourseDetail.action")) {
			this.getGridConfig().addRenderFunction(this.getText("学习详情"),
					"<center><a href=\"/entity/teaching/peAssCourseDetail_learnCourseDetail.action?courseId=${value}&type=1\" target='_blank'>学习详情</a></center>", "id");
		}

		if (capabilitySet.contains("/entity/teaching/assinteraction_InteractionStuManage.action")) {
			this.getGridConfig().addRenderScript(this.getText("进入学习"),
					"{if(!(record.data['isExam'] == '1' && record.data['enumConstByFlagExamStatus.name'] == '已通过考试' && record.data['visitafterpass'] == '0') && !(record.data['isExam'] == '0' && record.data['enumConstByFlagLearnStatus.name'] == '已完成学习' && record.data['visitafterpass'] == '0')){return '&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/entity/teaching/assinteraction_InteractionStuManage.action?course_id='+record.data['id']+'\" target=\"_blank\">进入学习</a>';} else return '&nbsp;&nbsp;&nbsp;&nbsp;已完成'}", "id");
		}

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzTchCourse.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peBzzCourseAssLearn";
		
	}
	
	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}
	
	
	/**
	 * 课程管理列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("select * from ( ");
			sqlBuffer.append("select distinct pbtc.id,pbtc.code,pbtc.name,nvl(ec1.name,'-') FlagCourseType,");
			sqlBuffer.append("                nvl(ec2.name,'-') FlagCourseCategory,nvl(ec3.name,'-') FlagCourseItemType,nvl(ec4.name,'-') FlagContentProperty,ec5.name FlagCourseArea,pbtc.teacher, ");
			sqlBuffer.append("                case when tcs.learn_status = 'COMPLETED' then '已完成学习' ");
			sqlBuffer.append("                  when tcs.learn_status = 'INCOMPLETE' then '正在学习' ");
			sqlBuffer.append("                  else '未开始学习' end FlagLearnStatus, ");
			sqlBuffer.append("                case when ec6.code = '1' and pbtue.ispass = '1' then '已通过考试' ");
			sqlBuffer.append("                  when ec6.code = '0' then '不考试' ");
			sqlBuffer.append("                  else '未通过考试' ");
			sqlBuffer.append("                end FlagExamStatus, ec9.code visitafterpass,ec10.code flag_is_exam, pbtc.create_date as createDate ");
			sqlBuffer.append("  from pe_bzz_tch_course pbtc ");
			sqlBuffer.append("  left join enum_const ec1 on pbtc.flag_coursetype = ec1.id ");
			sqlBuffer.append("  left join enum_const ec2 on pbtc.flag_coursecategory = ec2.id ");
			sqlBuffer.append("  left join enum_const ec3 on pbtc.flag_course_item_type = ec3.id ");
			sqlBuffer.append("  left join enum_const ec4 on pbtc.flag_content_property = ec4.id ");
			sqlBuffer.append("  join enum_const ec5 on pbtc.flag_coursearea = ec5.id ");
			sqlBuffer.append("  join enum_const ec9 on pbtc.flag_isvisitafterpass = ec9.id ");
			sqlBuffer.append("  join enum_const ec10 on pbtc.flag_is_exam = ec10.id ");
			sqlBuffer.append("  left join (select distinct pbto.fk_course_id,tcs.learn_status ");
			sqlBuffer.append("               from pr_bzz_tch_opencourse pbto ");
			sqlBuffer.append("				 join pr_bzz_tch_user_elective pbtue on pbtue.fk_tch_opencourse_id = pbto.id ");
			sqlBuffer.append("               join training_course_student tcs on pbtue.fk_training_id = tcs.id ");
			sqlBuffer.append("               join sso_user su on tcs.student_id = su.id ");
			sqlBuffer.append("             where su.id ='"+ us.getSsoUser().getId() + "') tcs on tcs.fk_course_id = pbtc.id");
			sqlBuffer.append("  left join (select pbto.fk_course_id, pbtue.ispass ");
			sqlBuffer.append("               from pr_bzz_tch_opencourse pbto ");
			sqlBuffer.append("               join pr_bzz_tch_user_elective pbtue on pbto.id = pbtue.fk_tch_opencourse_id ");
			sqlBuffer.append("               join sso_user su on pbtue.fk_user_id = su.id ");
			sqlBuffer.append("              where su.id = '" + us.getSsoUser().getId() + "') ");
			sqlBuffer.append("                          pbtue on pbtue.fk_course_id = pbtc.id ");
			sqlBuffer.append("                          join enum_const ec6 on pbtc.flag_is_exam = ec6.id ");
			sqlBuffer.append("  join enum_const ec7 on pbtc.flag_isvalid = ec7.id ");
			sqlBuffer.append("  join enum_const ec8 on pbtc.flag_offline = ec8.id ");
			sqlBuffer.append(" where ec7.code = '1' and ec8.code = '0' and ec5.code in ('1','2','3','4','5') ");
			sqlBuffer.append(") where 1=1 "); 
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
				/* 课程性质 */
				if (name.equals("enumConstByFlagCourseType.name")) {
					name = "FlagCourseType";
				}
				/* 业务分类 */
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					name = "FlagCourseCategory";
				}
				/* 大纲分类 */
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					name = "FlagCourseItemType";
				}
				/* 内容属性分类 */
				if (name.equals("enumConstByFlagContentProperty.name")) {
					name = "FlagContentProperty";
				}
				/* 课程来源 */
				if(name.equals("enumConstByFlagCourseArea.name")) {
					name = "FlagCourseArea";
				}
				/* 学习状态 */
				if(name.equals("enumConstByFlagLearnStatus.name")) {
					name = "FlagLearnStatus";
				}
				/* 考试结果 */
				if(name.equals("enumConstByFlagExamStatus.name")) {
					name = "FlagExamStatus";
				}
				if (!name.equals("FlagCourseCategory")) {
					sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
				} else {
					sqlBuffer.append(" and UPPER(" + name + ") = UPPER('" + value + "')");
				}
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 课程性质 */
				if (temp.equals("enumConstByFlagCourseType.name")) {
					temp = "FlagCourseType";
				}
				/* 业务分类 */
				if (temp.equals("enumConstByFlagCourseCategory.name")) {
					temp = "FlagCourseCategory";
				}
				/* 大纲分类 */
				if (temp.equals("enumConstByFlagCourseItemType.name")) {
					temp = "FlagCourseItemType";
				}
				/* 内容属性分类 */
				if (temp.equals("enumConstByFlagContentProperty.name")) {
					temp = "FlagContentProperty";
				}
				/* 课程来源 */
				if(temp.equals("enumConstByFlagCourseArea.name")) {
					temp = "FlagCourseArea";
				}
				/* 学习状态 */
				if(temp.equals("enumConstByFlagLearnStatus")) {
					temp = "FlagLearnStatus";
				}
				/* 考试结果 */
				if(temp.equals("enumConstByFlagExamStatus.name")) {
					temp = "FlagExamStatus";
				}
				if (temp.equals("id")) {
					temp = "createDate";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") desc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " desc ");
					}
				} else {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") asc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by createDate desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
}
