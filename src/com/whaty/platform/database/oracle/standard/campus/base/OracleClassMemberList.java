package com.whaty.platform.database.oracle.standard.campus.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.ClassMember;
import com.whaty.platform.campus.base.ClassMemberList;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.CampusLog;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleClassMemberList implements ClassMemberList {

	String CLASS_MEMBERLIST = "select id,name,status,login_id,password,member_type,phone,mobilephone,email,apply_date,check_date,checker,link_id,class_id,role from " +
								"(select id,name,status," +
								"login_id,password,member_type,phone,mobilephone,email,to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date," +
								"to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date" +
								",checker,link_id,class_id,role from campus_class_member) ";
	
	public List getClassMembers(Page page, List searchProperty, List orderProperty) throws PlatformException
	{
		dbpool db = new dbpool();
		List list = new ArrayList();
		MyResultSet rs = null;
		String sql = CLASS_MEMBERLIST;
		sql = sql + Conditions.convertToCondition(searchProperty, orderProperty);
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap propMap = new HashMap();
				ClassMember member = new OracleClassMember();
				String memberType = rs.getString("member_type");
				String linkId = rs.getString("link_id");
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setStatus(rs.getString("status"));
				member.setLoginId(rs.getString("login_id"));
				member.setPassword(rs.getString("password"));
				member.setMemberType(memberType);
				member.setPhone(rs.getString("phone"));
				member.setMobilephone(rs.getString("mobilephone"));
				member.setEmail(rs.getString("email"));
				member.setCheckDate(rs.getString("check_date"));
				member.setApplyDate(rs.getString("apply_date"));
				member.setChecker(rs.getString("checker"));
				member.setLinkId(linkId);
				member.setRole(rs.getString("role"));
				if (memberType.equals(ClassMember.STUDENT_MEMBER)) {
					OracleStudent student = new OracleStudent(linkId);
					propMap.put("name",student.getName());
					propMap.put("mobilephone",student.getNormalInfo().getMobilePhones());
					propMap.put("phone",student.getNormalInfo().getPhones());
					propMap.put("workplace",student.getNormalInfo().getWorkplace());
					propMap.put("homeaddress",student.getNormalInfo().getHome_address().getAddress());
					propMap.put("postcode",student.getNormalInfo().getHome_address().getPostalcode());
				}
				if (memberType.equals(ClassMember.TEACHER_MEMBER)) {
					OracleTeacher teacher = new OracleTeacher(linkId);
					propMap.put("name",teacher.getName());
					propMap.put("mobilephone",teacher.getNormalInfo().getMobilePhones());
					propMap.put("phone",teacher.getNormalInfo().getPhones());
					propMap.put("workplace",teacher.getNormalInfo().getWorkplace());
					propMap.put("homeaddress",teacher.getNormalInfo().getHome_address().getAddress());
					propMap.put("postcode",teacher.getNormalInfo().getHome_address().getPostalcode());
				}
				if (memberType.equals(ClassMember.MANAGER_MEMBER)) {
					OracleManager manager = null;
					try {
						manager = new OracleManager(linkId);
					} catch (PlatformException e) {
						
					}
					propMap.put("name",manager.getName());
					propMap.put("mobilephone",manager.getMobilePhone());
					propMap.put("phone",manager.getMobilePhone());
					propMap.put("workplace","");
					propMap.put("homeaddress","");
					propMap.put("postcode","");
				}
				if (memberType.equals(ClassMember.SITEMANAGER_MEMBER)) {
					OracleSiteManager manager = null;
					manager = new OracleSiteManager(linkId);
					propMap.put("name",manager.getName());
					propMap.put("mobilephone",manager.getMobilePhone());
					propMap.put("phone",manager.getMobilePhone());
					propMap.put("workplace","");
					propMap.put("homeaddress","");
					propMap.put("postcode","");
				}
				if (memberType.equals(ClassMember.SITETEACHER_MEMBER)) {
					OracleSiteTeacher teacher = null;
					teacher = new OracleSiteTeacher(linkId);
					propMap.put("name",teacher.getName());
					propMap.put("mobilephone",teacher.getNormalInfo().getMobilePhones());
					propMap.put("phone",teacher.getNormalInfo().getPhones());
					propMap.put("workplace",teacher.getNormalInfo().getWorkplace());
					propMap.put("homeaddress",teacher.getNormalInfo().getHome_address().getAddress());
					propMap.put("postcode",teacher.getNormalInfo().getHome_address().getPostalcode());
				}
				member.setPropMap(propMap);
				OracleClass cla = new OracleClass(rs.getString("class_id"));
				member.setClasses(cla);
				list.add(member);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("getClassMembers(Page page, List searchProperty, List orderProperty) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
	
	public int getClassMembersNum(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = CLASS_MEMBERLIST;
		sql = sql + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}
	public List getClassMembers(String classId,String memberType,String linkId) throws PlatformException
	{
		dbpool db = new dbpool();
		List list = new ArrayList();
		StrManage strManage = StrManageFactory.creat();
		MyResultSet rs = null;
		String sql = "";
		String conditions = "";
		try {
			if(!strManage.fixNull(classId).equals(""))
				conditions += " and class_id='"+classId+"'";
			if(!strManage.fixNull(memberType).equals(""))
				conditions += " and member_type='"+memberType+"'";
			if(!strManage.fixNull(linkId).equals(""))
				conditions += " and link_id='"+linkId+"'";
			sql = "select id,name,status,login_id,password,member_type,phone,mobilephone,email,apply_date,check_date,checker,link_id,class_id,role from " +
								"(select id,name,status," +
								"login_id,password,member_type,phone,mobilephone,email,to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date," +
								"to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date" +
								",checker,link_id,class_id,role from campus_class_member) where 1=1 "+conditions;
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				ClassMember member = new OracleClassMember();
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
				OracleClass cla = new OracleClass(rs.getString("class_id"));
				member.setClasses(cla);
				list.add(member);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("getClassMembers(String classId,String memberType,String linkId) error" + sql);
		}
		catch(Exception e)
		{}
		finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
}
