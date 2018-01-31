package com.whaty.platform.database.oracle.standard.courseware.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.whaty.platform.courseware.basic.CoursewareList;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleDep;
import com.whaty.platform.entity.basic.Dep;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserAddLog;

public class OracleCoursewareList implements CoursewareList {

//	String SQLCOURSEWARE = "select ID,NAME,AUTHOR,PUBLISHER,NOTE,FOUNDER_ID,FOUND_DATE,COURSEWARE_TYPE,"
//			+ "LINK,ACTIVE,courseware_dir,OPENCOURSEDEPID from PE_TCH_COURSEWARE";
	
	String SQLCOURSEWARE = "select ID,NAME,AUTHOR,PUBLISHER,NOTE,FOUNDER_ID,FOUND_DATE,COURSEWARE_TYPE,LINK,ACTIVE,courseware_dir,OPENCOURSEDEPID,depname from (select ci.ID as id,ci.NAME as NAME,ci.AUTHOR as AUTHOR,ci.PUBLISHER as PUBLISHER,ci.NOTE as NOTE,ci.FOUNDER_ID as FOUNDER_ID,ci.FOUND_DATE as FOUND_DATE,ci.COURSEWARE_TYPE as COURSEWARE_TYPE, ci.LINK as LINK,ci.ACTIVE as ACTIVE,ci.courseware_dir as courseware_dir,ci.OPENCOURSEDEPID as OPENCOURSEDEPID,edi.name as depname" +
			" from PE_TCH_COURSEWARE ci,entity_dep_info edi where ci.opencoursedepid = edi.id(+))";

	String SQLTEMPLATE = "select id,name,found_date,founder_id,image_filename,active,note from courseware_template";

	public List searchWhatyCoursewareTemplate(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = null;
		ArrayList templateList = null;
		MyResultSet rs = null;
		String sql = SQLTEMPLATE
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		try {
			db = new dbpool();
			templateList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleWhatyCoursewareTemplate template = new OracleWhatyCoursewareTemplate();
				template.setId(rs.getString("id"));
				template.setName(rs.getString("name"));
				template.setFounderDate(rs.getString("found_date"));
				template.setFounderId(rs.getString("founder_id"));
				template.setImageFileName(rs.getString("image_filename"));
				if (rs.getInt("active") == 1)
					template.setActive(true);
				else
					template.setActive(false);
				template.setNote(rs.getString("note"));
				templateList.add(template);

			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return templateList;
	}

	public List searchCourseware(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLCOURSEWARE
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList coursewares = null;
		try {
			coursewares = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleCourseware courseware = new OracleCourseware();
				courseware.setId(rs.getString("id"));
				courseware.setName(rs.getString("name"));
				courseware.setAuthor(rs.getString("author"));
				if (rs.getInt("active") != 1)
					courseware.setActive(false);
				else
					courseware.setActive(true);
				courseware.setFoundDate(rs.getString("found_date"));
				courseware.setFounderId(rs.getString("founder_id"));
				courseware.setCoursewareType(rs.getString("courseware_type"));
				courseware.setCoursewareDirId(rs.getString("courseware_dir"));
				
				Dep dep = new OracleDep();
			    dep.setId(rs.getString("OPENCOURSEDEPID"));
			    dep.setName(rs.getString("depname"));
				courseware.setDep(dep);
				coursewares.add(courseware);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return coursewares;
	}

	public int searchCoursewareNum(List searchList) {
		dbpool db = new dbpool();
		String sql = SQLCOURSEWARE
				+ Conditions.convertToCondition(searchList, null);
		return db.countselect(sql);
	}

	public int addTeachClassCware(String teachClassId, String[] coursewareIds,
			String[] pageCoursewareIds, String active) {

		dbpool db = new dbpool();
		String sql = "";
		Hashtable ht = new Hashtable();
		if (coursewareIds == null)
			coursewareIds = new String[0];
		if (pageCoursewareIds == null)
			pageCoursewareIds = new String[0];
		sql = "delete from PR_TCH_OPENCOURSE_COURSEWARE where teachclass_id='"
				+ teachClassId + "' and courseware_id in (";
		String ids = "";
		for (int i = 0; i < pageCoursewareIds.length; i++) {
			ids += "'" + pageCoursewareIds[i] + "',";
		}
		if (!"".equals(ids))
			ids = ids.substring(0, ids.length() - 1);
		sql += ids + ")";
		ht.put(new Integer(1), sql);

		int num = 0;
		for (int i = 0; i < coursewareIds.length; i++) {
			if (coursewareIds[i] != null && !coursewareIds[i].trim().equals("")) {
				sql = "insert into PR_TCH_OPENCOURSE_COURSEWARE (id,courseware_id,teachclass_id) values "
						+ "(to_char(s_teachclass_cware_id.nextval),'"
						+ coursewareIds[i]
						+ "','" + teachClassId + "')";
				ht.put(new Integer(num++ + 2), sql);
			}
		}
		int i = db.executeUpdateBatch(ht);
		UserAddLog.setDebug("OracleCoursewareList.addTeachClassCware(String teachClassId, String[] coursewareIds,String[] pageCoursewareIds, String active) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
