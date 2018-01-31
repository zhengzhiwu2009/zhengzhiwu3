package com.whaty.platform.database.oracle.standard.campus.base;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.AssociationList;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.CampusLog;

public class OracleAssociationList implements AssociationList {

	private String CLASSLIST = "select id,title,status,apply_date,check_date,note,forum_id,manager_id,creater_type,manager_name from "
			+ "(select id,title,status,"
			+ "to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date,to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date"
			+ ",note,forum_id,manager_id,creater_type,manager_name from campus_association_info) ";

	public List getAssociationes(Page page, List searchProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		List list = new ArrayList();
		MyResultSet rs = null;
		String sql = CLASSLIST
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleAssociation cla = new OracleAssociation();
				cla.setId(rs.getString("id"));
				cla.setTitle(rs.getString("title"));
				cla.setStatus(rs.getString("status"));
				cla.setApplyDate(rs.getString("apply_date"));
				cla.setCheckDate(rs.getString("check_date"));
				cla.setNote(rs.getString("note"));
				cla.setManagerId(rs.getString("manager_id"));
				cla.setCreaterType(rs.getString("creater_type"));
				cla.setManagerName(rs.getString("manager_name"));
				// List members = new
				// OracleAssociationMemberList().getAssociationMembers(rs.getString("id"),null,null);
				// cla.setMembers(members);
				list.add(cla);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("getAssociationes(Page page, List searchProperty, List orderProperty) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getAssociationesNum(List searchProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = CLASSLIST;
		sql = sql + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}
}
