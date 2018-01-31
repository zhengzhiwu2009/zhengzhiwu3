package com.whaty.platform.database.oracle.standard.campus.base;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.AssociationMember;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.util.log.CampusLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleAssociationMember extends AssociationMember {

	public OracleAssociationMember() {
		super();
	}

	public OracleAssociationMember(String linkId, String memberType,
			String associationId) {
		dbpool db = new dbpool();
		try {
			if (memberType.equals(AssociationMember.STUDENT_MEMBER)) {
				OracleStudent student = new OracleStudent(linkId);
				this.setName(student.getName());
				this.setLinkId(student.getId());
				this.setEmail(student.getEmail());
				this.setPhone(student.getNormalInfo().getPhones());
				this.setMobilephone(student.getNormalInfo().getMobilePhones());
				this.setMemberType(this.STUDENT_MEMBER);
				this.setAssociation(new OracleAssociation(associationId));
				this.setLoginId(student.getStudentInfo().getReg_no());
				this.setPassword(student.getPassword());
				this.setRole("0");
				this.setStatus("0");
			}
			if (memberType.equals(this.TEACHER_MEMBER)) {
				OracleTeacher teacher = new OracleTeacher(linkId);
				this.setName(teacher.getName());
				this.setLinkId(teacher.getId());
				this.setEmail(teacher.getEmail());
				this.setPhone(teacher.getNormalInfo().getPhones());
				this.setMobilephone(teacher.getNormalInfo().getMobilePhones());
				this.setMemberType(this.TEACHER_MEMBER);
				this.setAssociation(new OracleAssociation(associationId));
				this.setLoginId(teacher.getTeacherInfo().getGh());
				this.setPassword(teacher.getPassword());
				this.setRole("0");
				this.setStatus("0");
			}
			if (memberType.equals(this.MANAGER_MEMBER)) {
				OracleManager manager = null;
				try {
					manager = new OracleManager(linkId);
				} catch (PlatformException e) {
					
				}
				this.setName(manager.getName());
				this.setLinkId(manager.getId());
				this.setEmail(manager.getEmail());
				this.setPhone("");
				this.setMobilephone(manager.getMobilePhone());
				this.setMemberType(this.MANAGER_MEMBER);
				this.setAssociation(new OracleAssociation(associationId));
				this.setLoginId(manager.getLoginId());
				this.setPassword(manager.getPassword());
				this.setRole("0");
				this.setStatus("0");
			}
			if (memberType.equals(this.SITEMANAGER_MEMBER)) {
				OracleSiteManager manager = null;
				manager = new OracleSiteManager(linkId);
				this.setName(manager.getName());
				this.setLinkId(manager.getId());
				this.setEmail(manager.getEmail());
				this.setPhone("");
				this.setMobilephone(manager.getMobilePhone());
				this.setMemberType(this.SITEMANAGER_MEMBER);
				this.setAssociation(new OracleAssociation(associationId));
				this.setLoginId(manager.getLoginId());
				this.setPassword(manager.getPassword());
				this.setRole("0");
				this.setStatus("0");
			}
			if (memberType.equals(this.SITETEACHER_MEMBER)) {
				OracleSiteTeacher teacher = null;
				teacher = new OracleSiteTeacher(linkId);
				this.setName(teacher.getName());
				this.setLinkId(teacher.getId());
				this.setEmail(teacher.getEmail());
				this.setPhone(teacher.getNormalInfo().getPhones());
				this.setMobilephone(teacher.getNormalInfo().getMobilePhones());
				this.setMemberType(this.SITETEACHER_MEMBER);
				this.setAssociation(new OracleAssociation(associationId));
				this.setLoginId(teacher.getLoginId());
				this.setPassword(teacher.getPassword());
				this.setRole("0");
				this.setStatus("0");
			}
		} catch (Exception e) {
			
		}
	}

	public OracleAssociationMember(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,status,login_id,password,member_type,phone,mobilephone,email,apply_date,check_date,checker,link_id,association_id,role from "
				+ "(select id,name,status,"
				+ "login_id,password,member_type,phone,mobilephone,email,to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date,"
				+ "to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date"
				+ ",checker,link_id,association_id,role from campus_association_member) where id = '"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setStatus(rs.getString("status"));
				this.setLoginId(rs.getString("login_id"));
				this.setPassword(rs.getString("password"));
				this.setMemberType(rs.getString("member_type"));
				this.setPhone(rs.getString("phone"));
				this.setMobilephone(rs.getString("mobilephone"));
				this.setEmail(rs.getString("email"));
				this.setCheckDate(rs.getString("check_date"));
				this.setApplyDate(rs.getString("apply_date"));
				this.setChecker(rs.getString("checker"));
				this.setLinkId(rs.getString("link_id"));
				this.setRole(rs.getString("role"));
				OracleAssociation cla = new OracleAssociation(rs
						.getString("association_id"));
				this.setAssociation(cla);
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("OracleAssociation(String id) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		if (isExist() > 0) {
			throw new PlatformException("ǰųԱ" + this.getName() + "Ѿڣ");
		} else {
			dbpool db = new dbpool();
			String sql = "";
			sql = "insert into campus_association_member (id,name,status,login_id,password,member_type,phone,mobilephone,email,apply_date,checker,association_id,link_id,role) "
					+ "values(to_char(s_campus_association_member_id.nextval),'"
					+ this.getName()
					+ "','"
					+ this.getStatus()
					+ "','"
					+ this.getLoginId()
					+ "','"
					+ this.getPassword()
					+ "','"
					+ this.getMemberType()
					+ "','"
					+ this.getPhone()
					+ "','"
					+ this.getMobilephone()
					+ "','"
					+ this.getEmail()
					+ "',sysdate"
					+ ",'"
					+ this.getChecker()
					+ "','"
					+ this.getAssociation().getId()
					+ "','"
					+ this.getLinkId()
					+ "','" + this.getRole() + "')";
			int i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleAssociationMember.add() SQL=" + sql + " COUNT="+ i +" DATE=" + new Date());
			return i;
		}
	}

	public int update() throws PlatformException {
		if (isExist() > 1) {
			throw new PlatformException("ǰųԱ" + this.getName() + "Ѿڣ");
		} else {
			dbpool db = new dbpool();
			String sql = "";
			sql = "update campus_association_member " + "set name='"
					+ this.getName() + "',email='" + this.getEmail()
					+ "',phone='" + this.getPhone() + "',mobilephone='"
					+ this.getMobilephone() + "' where id='" + this.getId()
					+ "'";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleAssociationMember.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		}
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from campus_association_member where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleAssociationMember.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int confirm() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_association_member set status='1',check_date=sysdate where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAssociationMember.confirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int unConfirm() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_association_member set status='2',check_date=sysdate where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAssociationMember.unConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int assignManager() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_association_member set role='1' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAssociationMember.assignManager() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int unAssignManager() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update campus_association_member set role='0' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAssociationMember.unAssignManager() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public AssociationMember getAssociationMember(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		OracleAssociationMember member = new OracleAssociationMember();
		String sql = "";
		sql = "select id,name,status,login_id,password,member_type,phone,mobilephone,email,apply_date,check_date,checker,link_id,association_id,role from "
				+ "(select id,name,status,"
				+ "login_id,password,member_type,phone,mobilephone,email,to_char(apply_date,'yyyy-mm-dd hh24:mi:ss') as apply_date,"
				+ "to_char(check_date,'yyyy-mm-dd hh24:mi:ss') as check_date"
				+ ",checker,link_id,association_id,role from campus_association_member) where id = '"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
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
			}
		} catch (java.sql.SQLException e) {
			CampusLog.setError("OracleAssociation(String id) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return member;
	}

	/** жųԱǷڣ0򲻴ڣ0 */
	private int isExist() {
		dbpool db = new dbpool();
		String sql = "select id from campus_association_member where link_id='"
				+ this.getLinkId() + "' and association_id='"
				+ this.getAssociation().getId() + "'";
		int i = db.countselect(sql);
		return i;

	}
}