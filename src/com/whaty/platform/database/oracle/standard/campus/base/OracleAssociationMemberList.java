package com.whaty.platform.database.oracle.standard.campus.base;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.AssociationMember;
import com.whaty.platform.campus.base.AssociationMemberList;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.CampusLog;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleAssociationMemberList implements AssociationMemberList {

	String CLASS_MEMBERLIST = "select id,name,status,login_id,password,member_type,phone,mobilephone,email,apply_date,check_date,checker,link_id,association_id,role from "
			+ "(select id,name,status,"
			+ "login_id,password,member_type,phone,mobilephone,email,to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date,"
			+ "to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date"
			+ ",checker,link_id,association_id,role from campus_association_member) ";

	public List getAssociationMembers(Page page, List searchProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		List list = new ArrayList();
		MyResultSet rs = null;
		String sql = CLASS_MEMBERLIST;
		sql = sql
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
				AssociationMember member = new OracleAssociationMember();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setStatus(rs.getString("status"));
				member.setLoginId(rs.getString("login_id"));
				member.setPassword(rs.getString("password"));
				member.setMemberType(rs.getString("member_type"));
				member.setPhone(rs.getString("phone"));
				member.setMobilephone(rs.getString("mobilephone"));
				member.setEmail(rs.getString("email"));
				member.setCheckDate(rs.getString("check_date"));
				member.setApplyDate(rs.getString("apply_date"));
				member.setChecker(rs.getString("checker"));
				member.setLinkId(rs.getString("link_id"));
				member.setRole(rs.getString("role"));
				OracleAssociation cla = new OracleAssociation(rs
						.getString("association_id"));
				member.setAssociation(cla);
				list.add(member);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("getAssociationMembers(Page page, List searchProperty, List orderProperty) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getAssociationMembersNum(List searchProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = CLASS_MEMBERLIST;
		sql = sql + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getAssociationMembers(String associationId, String memberType,
			String linkId) throws PlatformException {
		dbpool db = new dbpool();
		List list = new ArrayList();
		StrManage strManage = StrManageFactory.creat();
		MyResultSet rs = null;
		String sql = "";
		String conditions = "";
		try {
			if (!strManage.fixNull(associationId).equals(""))
				conditions += " and association_id='" + associationId + "'";
			if (!strManage.fixNull(memberType).equals(""))
				conditions += " and member_type='" + memberType + "'";
			if (!strManage.fixNull(linkId).equals(""))
				conditions += " and link_id='" + linkId + "'";
			sql = "select id,name,status,login_id,password,member_type,phone,mobilephone,email,apply_date,check_date,checker,link_id,association_id,role from "
					+ "(select id,name,status,"
					+ "login_id,password,member_type,phone,mobilephone,email,to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date,"
					+ "to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date"
					+ ",checker,link_id,association_id,role from campus_association_member) where 1=1 "
					+ conditions;
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				AssociationMember member = new OracleAssociationMember();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setStatus(rs.getString("status"));
				member.setLoginId(rs.getString("login_id"));
				member.setPassword(rs.getString("password"));
				member.setMemberType(rs.getString("member_type"));
				member.setPhone(rs.getString("phone"));
				member.setMobilephone(rs.getString("mobilephone"));
				member.setEmail(rs.getString("email"));
				member.setCheckDate(rs.getString("check_date"));
				member.setApplyDate(rs.getString("apply_date"));
				member.setChecker(rs.getString("checker"));
				member.setLinkId(rs.getString("link_id"));
				member.setRole(rs.getString("role"));
				OracleAssociation cla = new OracleAssociation(rs
						.getString("association_id"));
				member.setAssociation(cla);
				list.add(member);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("getAssociationMembers(Page page, List searchProperty, List orderProperty) error"
							+ sql);
		} catch (Exception e) {
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
}
