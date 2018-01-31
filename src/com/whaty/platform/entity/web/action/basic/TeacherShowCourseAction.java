package com.whaty.platform.entity.web.action.basic;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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

public class TeacherShowCourseAction extends MyBaseAction<PeBzzTchCourse> {
	private String teacherId;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
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

		this.getGridConfig().setTitle("查看评价");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true, true, true, "TextField", false, 200);

		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, true, true, "TextField", true, 25);

		ColumnConfig columnCourseArea = new ColumnConfig(this.getText("课程来源"), "enumConstByFlagCourseArea.name", true, false, true, "TextField", false, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagCoursearea'";
		columnCourseArea.setComboSQL(sql1);
		this.getGridConfig().addColumn(columnCourseArea);

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
		
		this.getGridConfig().addColumn(this.getText("学时数"), "time", false, false, true, "TextField", true, 25);
		
//		this.getGridConfig().addColumn(this.getText("投票总人次"), "writ_time", false, false, true, "TextField", true, 25);
		
		this.getGridConfig().addColumn(this.getText("总平均分"), "ave_score", false, false, true, "TextField", true, 25);
		
		Map<String, String> defaultValueMap = new HashMap<String, String>();
		defaultValueMap.put("passRole", "80");
		defaultValueMap.put("examTimesAllow", "5");
		defaultValueMap.put("studyDates", "90");
		this.setDefaultValueMap(defaultValueMap);
	}
	
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/teacherShowCourse";
	}

	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}
	
	/**
	 * 授课课程列表
	 * 
	 * @author Lee
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT pbtc.id, pbtc.code, pbtc.name, ec.name flagCourseArea, ");
			sqlBuffer.append(" ec1.name flagCourseCategory, ec2.name flagCourseItemType, ec3.name flagContentProperty, pbtc.time, ");
			sqlBuffer.append(" ROUND(nvl(nvl((SELECT sum(DECODE(nvl((NVL(ITEM_RESULT1, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT2, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT3, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT4, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT5, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT6, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT7, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT8, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT9, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT10, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT11, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT12, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT13, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT14, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT15, 0)) ");
			sqlBuffer.append(" , 0),0,0,(NVL(PVQ.ITEM_RESULT1, 0) * NVL(PVQ.ITEM_SCORE1, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT2, 0) * NVL(PVQ.ITEM_SCORE2, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT3, 0) * NVL(PVQ.ITEM_SCORE3, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT4, 0) * NVL(PVQ.ITEM_SCORE4, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT5, 0) * NVL(PVQ.ITEM_SCORE5, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT6, 0) * NVL(PVQ.ITEM_SCORE6, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT7, 0) * NVL(PVQ.ITEM_SCORE7, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT8, 0) * NVL(PVQ.ITEM_SCORE8, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT9, 0) * NVL(PVQ.ITEM_SCORE9, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT10, 0) * NVL(PVQ.ITEM_SCORE10, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT11, 0) * NVL(PVQ.ITEM_SCORE11, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT12, 0) * NVL(PVQ.ITEM_SCORE12, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT13, 0) * NVL(PVQ.ITEM_SCORE13, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT14, 0) * NVL(PVQ.ITEM_SCORE14, 0) + ");
			sqlBuffer.append(" NVL(PVQ.ITEM_RESULT15, 0) * NVL(PVQ.ITEM_SCORE15, 0)) / ");
			sqlBuffer.append(" (NVL(ITEM_RESULT1, 0) + NVL(ITEM_RESULT2, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT3, 0) + NVL(ITEM_RESULT4, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT5, 0) + NVL(ITEM_RESULT6, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT7, 0) + NVL(ITEM_RESULT8, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT9, 0) + NVL(ITEM_RESULT10, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT11, 0) + NVL(ITEM_RESULT12, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT13, 0) + NVL(ITEM_RESULT14, 0) + ");
			sqlBuffer.append(" NVL(ITEM_RESULT15, 0))) * pvq.weight / 1000 ) ave_score ");
			sqlBuffer.append(" FROM PR_VOTE_QUESTION PVQ ");
			sqlBuffer.append(" JOIN PE_VOTE_PAPER PVP ON PVP.ID = PVQ.FK_VOTE_PAPER_ID ");
//			sqlBuffer.append(" left join (SELECT COUNT(*) SUMSCORE, FK_VOTE_PAPER_ID ID ");
//			sqlBuffer.append(" FROM PR_VOTE_RECORD ");
//			sqlBuffer.append(" GROUP BY FK_VOTE_PAPER_ID) B on PVQ.FK_VOTE_PAPER_ID = B.ID ");
//			sqlBuffer.append(" left join pr_vote_sub_question pvsq on pvsq.fk_vote_question_id = pvq.id ");
			sqlBuffer.append(" WHERE PVQ.WEIGHT IS NOT NULL ");
			sqlBuffer.append(" AND FLAG_QUESTION_TYPE in ('402880a91e635aa0011e636032100002', ");
			sqlBuffer.append(" '402880a91e635aa0011e635fcd2c0001') ");
			sqlBuffer.append(" and PVP.id = pbtc.satisfaction_id AND PVP.COURSEID = PBTC.ID ");
			sqlBuffer.append(" group by pvq.fk_vote_paper_id),0) + ");
			sqlBuffer.append(" nvl((select sum(nvl(pvsq.item_content,0) * pvq.weight / 1000) / b.sumscore a_score ");
			sqlBuffer.append(" from PR_VOTE_QUESTION PVQ left join (SELECT COUNT(*) SUMSCORE,FK_VOTE_PAPER_ID ID ");
			sqlBuffer.append(" FROM PR_VOTE_RECORD GROUP BY FK_VOTE_PAPER_ID) B on PVQ.FK_VOTE_PAPER_ID = B.ID ");
			sqlBuffer.append(" JOIN PE_VOTE_PAPER PVP ON PVP.ID = PVQ.FK_VOTE_PAPER_ID ");
			sqlBuffer.append(" left join pr_vote_sub_question pvsq on pvsq.fk_vote_question_id = pvq.id ");
			sqlBuffer.append(" WHERE PVQ.WEIGHT IS NOT NULL ");
			sqlBuffer.append(" AND FLAG_QUESTION_TYPE = '402880a91e635aa011e635fcd2c0001' and PVP.id = pbtc.satisfaction_id AND PVP.COURSEID = PBTC.ID ");
			sqlBuffer.append(" AND PVSQ.ISCUSTOM = '0' ");
			sqlBuffer.append(" group by pvq.fk_vote_paper_id,b.sumscore),0) ");
			sqlBuffer.append(" ,0), 1) avg_score, ");
			sqlBuffer.append(" pbtc.create_date  ");
			sqlBuffer.append(" from pe_bzz_tch_course pbtc ");
			sqlBuffer.append(" join enum_const ec on pbtc.flag_coursearea = ec.id ");
			sqlBuffer.append(" left join enum_const ec1 on pbtc.flag_coursecategory = ec1.id ");
			sqlBuffer.append(" left join enum_const ec2 on pbtc.flag_course_item_type = ec2.id ");
			sqlBuffer.append(" left join enum_const ec3 on pbtc.flag_content_property = ec3.id ");
			sqlBuffer.append(" where pbtc.teacher_id='" + teacherId + "' ");
			sqlBuffer.append(" and pbtc.teacher is not null and pbtc.announce_date <> pbtc.create_date ");
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
				/* 课程来源 */
				if (name.equals("enumConstByFlagCourseArea.name")) {
					name = "FlagCourseArea";
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
				sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 课程来源 */
				if (temp.equals("enumConstByFlagCourseArea.name")) {
					temp = "FlagCourseArea";
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
				if (temp.equals("id")) {
					temp = "create_date";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");
				} else {
					sqlBuffer.append(" order by " + temp + " asc ");
				}
			} else {
				sqlBuffer.append(" order by create_date desc");
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
