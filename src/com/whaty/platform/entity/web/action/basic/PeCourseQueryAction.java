package com.whaty.platform.entity.web.action.basic;

import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeCourseQueryAction extends MyBaseAction{

	@Override
	public void initGrid() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setTitle("全部课程");
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);	
		this.getGridConfig().addColumn(this.getText("课程编号"), "courseCode");
		this.getGridConfig().addColumn(this.getText("课程名称"), "courseName");
		this.getGridConfig().addColumn(this.getText("发布时间"), "assounceDate");
		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher");
		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);
		this.getGridConfig().addColumn(this.getText("学时"), "times",false);
		this.getGridConfig().addColumn(this.getText("价格"), "price",false);
		this.getGridConfig().addColumn(this.getText("课程时长(分钟)"), "course_Len", false, true, true , Const.number_for_extjs);
		ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, true, true, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnConfigCategory.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory);
		
		ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, true, true, "TextField", false, 100, "");
		String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sql9);
		this.getGridConfig().addColumn(columnContentProperty);
		this.getGridConfig().addColumn(this.getText("评分"), "score",false);
		//this.getGridConfig().addColumn(this.getText("建议学习人群"), "personnel");
		ColumnConfig ccFlagSuggest = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, false, false, "TextField", false, 100, "");
		String sqlFlagSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		ccFlagSuggest.setComboSQL(sqlFlagSuggest);
		this.getGridConfig().addColumn(ccFlagSuggest);
		
		ColumnConfig columnConfigIsExam = new ColumnConfig(this.getText("是否考试"), "enumConstByFlagIsExam.name", true, true, true, "TextField", false, 100, "");
		String sqlx = "select a.id ,a.name from enum_const a where a.namespace='FlagIsExam'";
		columnConfigIsExam.setComboSQL(sqlx);
		this.getGridConfig().addColumn(columnConfigIsExam);
		
		ColumnConfig columnConfigIsZX = new ColumnConfig(this.getText("专题/系列"), "enumConstByFlagZX.name", true, false, false, "TextField", false, 100, "");
		String sqlzx = "SELECT t.ID id ,t.NAME name FROM RECOMMEND_SERIES t";
		columnConfigIsZX.setComboSQL(sqlzx);
		this.getGridConfig().addColumn(columnConfigIsZX);
		this.getGridConfig().addColumn(this.getText("课件首页"), "indexFile", false, false, false, "");
		this.getGridConfig().addRenderScript(this.getText("课程信息"), "{return '<a href=\"/entity/teaching/peBzzCourseZixuanManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}", "id");
		//SELECT t.ID id ,t.NAME name FROM RECOMMEND_SERIES t
		//this.getGridConfig().addMenuFunction(this.getText("开启"), "FlagIsvalid.true");
		//this.getGridConfig().addMenuFunction(this.getText("关闭"), "FlagIsvalid.false");
		//this.getGridConfig().addMenuFunction(this.getText("初始化日志配置"), "info.true");
	}
	public Page list() {
		Page page = null;
		String sql="";
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from ( ");
		sb.append(" SELECT PBTC.id as id ,                                                                        ") ; 
		sb.append(" 	   pbtc.CODE as courseCode ,                                                                       ");    
		sb.append("        PBTC.NAME as courseName,                                                                        ") ;      
		sb.append("        PBTC.ANNOUNCE_DATE as assounceDate,                                                               ") ;      
		sb.append("        PBTC.TEACHER as teacher ,                                                                     ") ;      
		sb.append("        EC1.NAME FLAGCOURSETYPE ,                                                                 ") ;      
		sb.append("        PBTC.TIME as times,                                                                        ") ;      
		sb.append("        PBTC.PRICE as price,                                                                       ") ; 
		sb.append("        PBTC.COURSE_LEN,                                                                                          ");
		sb.append("        EC2.NAME FLAGCOURSECATEGORY  ,                                                                 ") ;      
		sb.append("        EC3.NAME FLAGCONTENTPROPERTY,                                                                 ") ;      
		sb.append("        NVL(DECODE(PBTC_SCORE, 0, NULL, PBTC_SCORE), '-') SCORE,                          ") ;      
		sb.append("        WM_CONCAT(EC5.NAME) FS,                                                           ") ;      
		sb.append("        EC4.NAME FlagIsExam  , '' ，" +
				"                  C.INDEXFILE                                           ") ;      
		sb.append("   FROM PE_BZZ_TCH_COURSE PBTC                                                            ") ;      
		sb.append("  INNER JOIN ENUM_CONST EC1                                                               ") ;      
		sb.append("     ON PBTC.FLAG_COURSETYPE = EC1.ID                                                     ") ;      
		sb.append("  INNER JOIN ENUM_CONST EC2                                                               ") ;      
		sb.append("     ON PBTC.FLAG_COURSECATEGORY = EC2.ID                                                 ") ;      
		sb.append("  INNER JOIN ENUM_CONST EC3                                                               ") ;      
		sb.append("     ON PBTC.FLAG_CONTENT_PROPERTY = EC3.ID                                               ") ;      
		sb.append("  INNER JOIN ENUM_CONST EC4                                                               ") ;      
		sb.append("     ON PBTC.FLAG_IS_EXAM = EC4.ID                                                        ") ;    
		sb.append(" LEFT JOIN SCORM_SCO_LAUNCH C ON PBTC.CODE = C.COURSE_ID");
		sb.append("   LEFT JOIN PE_BZZ_TCH_COURSE_SUGGEST B                                                  ") ;      
		sb.append("     ON PBTC.ID = B.FK_COURSE_ID                                                          ") ;      
		sb.append("    AND TABLE_NAME = 'PE_BZZ_TCH_COURSE'                                                  ") ;      
		sb.append("   LEFT JOIN ENUM_CONST EC5                                                               ") ;      
		sb.append("     ON B.FK_ENUM_CONST_ID = EC5.ID                                                       ") ;      
		sb.append("   LEFT JOIN (SELECT DECODE(SUM(A5), 0, '', ROUND(SUM(A5 * A6) / SUM(A5), 1)) PBTC_SCORE, ") ;      
		sb.append("                     RSC.A1                                                               ") ;      
		sb.append("                FROM REPORT_VOTE_TOTAL RSC                                                ") ;      
		sb.append("               WHERE RSC.A5 IS NOT NULL                                                   ") ;      
		sb.append("                 AND RSC.A6 IS NOT NULL                                                   ") ;      
		sb.append("                 AND EXISTS                                                               ") ;      
		sb.append("               (SELECT ID                                                                 ") ;      
		sb.append("                        FROM PE_VOTE_PAPER PVP                                            ") ;      
		sb.append("                       WHERE FID = ID                                                     ") ;      
		sb.append("                         AND PVP.TITLE LIKE '满意度调查问卷（201%')                       ") ;      
		sb.append("               GROUP BY RSC.A1) PBTCSCORE                                                 ") ;      
		sb.append("     ON PBTC.CODE = PBTCSCORE.A1                                                          ") ;      
		sb.append("  WHERE PBTC.FLAG_ISVALID = '2'                                                ") ;      //已发布   
		sb.append("    AND PBTC.FLAG_OFFLINE = '22'                                                  ") ;   //未下线    
		sb.append("    AND PBTC.FLAG_COURSEAREA = 'Coursearea1'                                 ") ;      //公共课程区 
		sb.append("  GROUP BY pbtc.id ,PBTC.CODE,                                                                     ") ;      
		sb.append("           PBTC.NAME,                                                                     ") ;      
		sb.append("           PBTC.ANNOUNCE_DATE,                                                            ") ;      
		sb.append("           PBTC.TEACHER,                                                                  ") ;      
		sb.append("           EC1.NAME,                                                                      ") ;      
		sb.append("           PBTC.TIME,                                                                     ") ;      
		sb.append("           PBTC.PRICE,                                                                    ") ; 
		sb.append("           PBTC.course_len ,                                                                    ") ; 
		sb.append("           EC2.NAME,                                                                      ") ;      
		sb.append("           EC3.NAME,                                                                      ") ;      
		sb.append("           PBTC_SCORE,                                                                    ") ;      
		sb.append("           C.INDEXFILE,                                                                    ") ;      
		sb.append("           EC4.NAME     )                         where 1 =1                                					");        

		
		//StringBuffer searchSql = new StringBuffer();
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
				name = "FLAGCOURSECATEGORY";
			}
			/* 大纲分类 */
			if (name.equals("enumConstByFlagCourseItemType.name")) {
				name = "FlagCourseItemType";
			}
			/* 内容属性分类 */
			if (name.equals("enumConstByFlagContentProperty.name")) {
				name = "FLAGCONTENTPROPERTY";
			}
			/* 是否有效-发布 */
			if (name.equals("enumConstByFlagIsvalid.name")) {
				name = "FlagIsvalid";
			}
			/* 下线 */
			if (name.equals("enumConstByFlagOffline.name")) {
				name = "FlagOffline";
			}
			/* 是否考试 */
			if (name.equals("enumConstByFlagIsExam.name")) {
				name = "FlagIsExam";
			}
			/* 课程收费状态 */
			if (name.equals("enumConstByFlagIsFree.name")) {
				name = "FlagIsFree";
			}
			if(name.equals("assounceDate")){
				name = "to_char(assounceDate,'yyyy-MM-dd') ";
			}
			if(name.equals("enumConstByFlagZX.name")){
				sb.append(" AND ID IN (SELECT r.Pk_Course_Id FROM RECOMMEND_COURSE R INNER JOIN RECOMMEND_SERIES S ON r.PK_SERIES_ID =s.ID AND  s.NAME = '"+ value +"') ");
			}
			if (!name.equals("enumConstByFlagSuggest.name")&& !name.equals("enumConstByFlagZX.name")) {
				// 课程性质、业务分类、大纲分类、内容属性分类用=其他用like
				if ("FlagCourseType".equals(name) || "FlagCourseCategory".equals(name) || "FlagCourseItemType".equals(name) || "FlagContentProperty".equals(name)) {
					sb.append(" and UPPER(" + name + ") = UPPER('" + value + "')");
				} else {
					sb.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
				}
			}
			/* 建议学习人群 */
			if (name.equals("enumConstByFlagSuggest.name")) {
				sb.append(" AND INSTR(FS, '" + value + "', 1, 1) > 0 ");
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
			/* 是否有效-发布 */
			if (temp.equals("enumConstByFlagIsvalid.name")) {
				temp = "FlagIsvalid";
			}
			/* 下线 */
			if (temp.equals("enumConstByFlagOffline.name")) {
				temp = "FlagOffline";
			}
			/* 是否考试 */
			if (temp.equals("enumConstByFlagIsExam.name")) {
				temp = "FlagIsExam";
			}
			/* 课程收费状态 */
			if (temp.equals("enumConstByFlagIsFree.name")) {
				temp = "FlagIsFree";
			}
			if (temp.equals("id")) {
				temp = "assounceDate";
			}
			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
				if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
					sb.append(" order by to_number(" + temp + ") desc ");
				} else {
					if (!temp.equals("enumConstByFlagSuggest.name"))
						sb.append(" order by " + temp + " desc ");
				}
			} else {
				if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
					sb.append(" order by to_number(" + temp + ") asc ");
				} else {
					if (!temp.equals("enumConstByFlagSuggest.name"))
						sb.append(" order by " + temp + " asc ");
				}
			}
		}
		
		try {
			page = this.getGeneralService().getByPageSQL(sb.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/basic/peCourseQuery";
		
	}
}
