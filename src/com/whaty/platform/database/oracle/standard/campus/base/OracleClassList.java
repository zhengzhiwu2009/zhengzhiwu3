package com.whaty.platform.database.oracle.standard.campus.base;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.ClassList;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.interaction.forum.OracleForumList;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.sso.SsoUser;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.CampusLog;

public class OracleClassList implements ClassList {

	private String CLASSLIST = "select id,title,status,apply_date,check_date,note,forum_id,manager_id,creater_type,manager_name from "
			+ "(select id,title,status,"
			+ "to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date,to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date"
			+ ",note,forum_id,manager_id,creater_type,manager_name from campus_class_info) ";

	private String CLASSFORUMLIST = "select rownum,id,title,status,apply_date,check_date,note,forum_id,manager_id"
			+ ",manager_name,creater_type, name, content, create_date, num, course_id, isbrowse,isspeak,group_type,owners "
			+ "from (select a.id,a.title,a.status,to_char(a.apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date"
			+ ",to_char(a.check_date,'yyyy-mm-dd hh24:mi:ss') as check_date,a.note,a.forum_id,a.manager_id,a.manager_name,a.creater_type"
			+ ", b.name, b.content, to_char(b.create_date,'yyyy-mm-dd') as create_date, b.num, b.course_id, b.isbrowse, b.isspeak,b.group_type,b.owners from"
			+ " campus_class_info a,interaction_forumlist_info b where a.forum_id=b.id)";

	public List getClasses(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		List list = new ArrayList();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = CLASSLIST
					+ Conditions.convertToCondition(searchProperty,
							orderProperty);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleClass cla = new OracleClass();
				cla.setId(rs.getString("id"));
				cla.setTitle(rs.getString("title"));
				cla.setStatus(rs.getString("status"));
				cla.setApplyDate(rs.getString("apply_date"));
				cla.setCheckDate(rs.getString("check_date"));
				cla.setNote(rs.getString("note"));
				cla.setManagerId(rs.getString("manager_id"));
				cla.setCreaterType(rs.getString("creater_type"));
				cla.setForumId(rs.getString("forum_id"));
				cla.setManagerName(rs.getString("manager_name"));
				// List members = new
				// OracleClassMemberList().getClassMembers(rs.getString("id"),null,null);
				// cla.setMembers(members);
				list.add(cla);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("getClasses(Page page, List searchProperty, List orderProperty) error! sql="+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getClassesNum(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = CLASSLIST
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getClassForums(Page page, List searchProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		List list = new ArrayList();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = CLASSFORUMLIST
					+ Conditions.convertToCondition(searchProperty,
							orderProperty);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleClass cla = new OracleClass();
				cla.setId(rs.getString("id"));
				cla.setTitle(rs.getString("title"));
				cla.setStatus(rs.getString("status"));
				cla.setApplyDate(rs.getString("apply_date"));
				cla.setCheckDate(rs.getString("check_date"));
				cla.setNote(rs.getString("note"));
				cla.setManagerId(rs.getString("manager_id"));
				cla.setManagerName(rs.getString("manager_name"));
				cla.setCreaterType(rs.getString("creater_type"));
				cla.setForumId(rs.getString("forum_id"));

				OracleForumList forumList = new OracleForumList();
				forumList.setId(rs.getString("forum_id"));
				forumList.setName(rs.getString("name"));
				forumList.setContent(rs.getString("content"));
				forumList.setDate(rs.getString("create_date"));
				forumList.setCourseId(rs.getString("course_id"));
//				forumList.setGroupType(rs.getString("group_type"));
				String owners = rs.getString("owners");
				if(owners == null || owners.equalsIgnoreCase("null"))
					owners = "";
//				forumList.setOwnerStr(owners);
				ArrayList list1 = new ArrayList();
				if(!owners.equals(""))
				{
					String temp[]=owners.split(",");
					for(int i=0;i<temp.length;i++)
					{
						ArrayList tempList = new ArrayList();
						try {
							if(OracleSsoUser.isExist(temp[i]) > 0) 
							{
								OracleSsoUser ssoUser = new OracleSsoUser();
								SsoUser user = ssoUser.getSsoLoginUser(temp[i]);
								tempList.add(user.getId());
								tempList.add(user.getName());
								tempList.add(user.getLoginId());
								list1.add(tempList);
							}
						} catch (PlatformException e) {
							continue;
						}
					}
				}
//				forumList.setOwners(list1);
//				forumList.setClasses(cla);
				// List members = new
				// OracleClassMemberList().getClassMembers(rs.getString("id"),null,null);
				// cla.setMembers(members);
				list.add(forumList);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("getClasses(Page page, List searchProperty, List orderProperty) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getClassForumesNum(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = CLASSFORUMLIST
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}
}
