/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.training.basic.TrainingCourseType;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.log.TrainingLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingCourseType extends TrainingCourseType {

	public OracleTrainingCourseType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleTrainingCourseType(String typeId) {
		if (typeId != null && typeId.equalsIgnoreCase("root")) {
			this.setId("root");
			this.setName("root");
		} else {

			dbpool db = new dbpool();
			MyResultSet rs = null;
			try {
				String sql = "select a.id,a.name,a.status,a.parent_id,b.name as parent_name,"
						+ "a.note from training_course_type a,training_course_type b  "
						+ "where a.parent_id=b.id(+) and a.id='" + typeId + "'";
				TrainingLog.setDebug(sql);
				rs = db.executeQuery(sql);
				if (rs != null && rs.next()) {
					this.setId(rs.getString("id"));
					this.setName(rs.getString("name"));
					if (rs.getInt("status") == 1)
						this.setActive(true);
					else
						this.setActive(false);
					TrainingCourseType parentType = new OracleTrainingCourseType();
					parentType.setId(rs.getString("parent_id"));
					if (rs.getString("parent_id").equalsIgnoreCase("root"))
						parentType.setName("root");
					else
						parentType.setName(rs.getString("parent_name"));
					this.setParentType(parentType);
					this.setNote(rs.getString("note"));
				}
			} catch (SQLException e) {

			} finally {
				db.close(rs);
				db = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourseType#getChildType(java.util.List,
	 *      java.util.List)
	 */
	public List getChildType(List searchproperty, List orderproperty)
			throws PlatformException {
		String sql = "select id,name,status,parent_id,parent_name,note from "
				+ "(select a.id,a.name,a.status,a.parent_id,b.name as parent_name,"
				+ "a.note from training_course_type a,training_course_type b   "
				+ "where a.parent_id='"
				+ this.getId()
				+ "' and a.parent_id=b.id(+)) "
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty);
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List childTypeList = new ArrayList();
		try {
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				TrainingCourseType courseType = new OracleTrainingCourseType();
				courseType.setId(rs.getString("id"));
				courseType.setName(rs.getString("name"));
				if (rs.getInt("status") == 1)
					courseType.setActive(true);
				else
					courseType.setActive(false);
				TrainingCourseType parentType = new OracleTrainingCourseType();
				parentType.setId(rs.getString("parent_id"));
				parentType.setName(rs.getString("parent_name"));
				courseType.setParentType(parentType);
				courseType.setNote(rs.getString("note"));
				childTypeList.add(courseType);
			}
		} catch (SQLException e) {
			TrainingLog.setDebug(e.toString() + "error in " + sql);
		} finally {
			db.close(rs);
			db = null;
			return childTypeList;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourseType#isRoot()
	 */
	public boolean isRoot() throws PlatformException {
		if (this.getId().equals("root"))
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourseType#moveTo(com.whaty.platform.training.basic.TrainingCourseType)
	 */
	public void moveTo(TrainingCourseType parentType) throws PlatformException {
		this.setParentType(parentType);
		this.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourseType#getParent()
	 */
	public TrainingCourseType getParent() throws PlatformException {
		return new OracleTrainingCourseType(this.getParentType().getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourseType#addChildType(java.util.List)
	 */
	public void addChildType(List childTypeList) throws PlatformException {
		List sqlgroup = new ArrayList();
		for (int i = 0; i < childTypeList.size(); i++) {
			TrainingCourseType courseType = (TrainingCourseType) childTypeList
					.get(i);
			int status = 0;
			if (courseType.getIsActive())
				status = 1;
			String sql = "insert into training_course_type(name,status,parent_id,note) "
					+ " values(to_char(s_training_id.nextval),'"
					+ courseType.getName()
					+ "',"
					+ status
					+ ",'"
					+ this.getId() + "','" + courseType.getNote() + "')";
			UserAddLog.setDebug("OracleTrainingCourseType.addChildType(List ChildTypeList) SQL=" + sql + " DATE=" + new Date());
			sqlgroup.add(sql);
		}
		dbpool db = new dbpool();
		db.executeUpdateBatch(sqlgroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourseType#removeChildType(java.util.List)
	 */
	public void removeChildType(List childTypeList) throws PlatformException {
		String deleteIds = "";
		for (int i = 0; i < childTypeList.size(); i++) {
			TrainingCourseType courseType = (TrainingCourseType) childTypeList
					.get(i);
			deleteIds = deleteIds + "'" + courseType.getId() + "',";
		}
		if (deleteIds.length() > 3) {
			deleteIds = deleteIds.substring(0, deleteIds.length() - 1);
			String sql = "delete from training_course_type where parent_id='"
					+ this.getParentType().getId() + "' and id in(" + deleteIds
					+ ")";
			dbpool db = new dbpool();
			
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleTrainingCourseType.removeChildType(List ChildTypeList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		int status = 0;
		if (this.getIsActive())
			status = 1;
		String sql = "insert into training_course_type(id,name,status,parent_id,note) "
				+ " values(to_char(s_training_id.nextval),'"
				+ this.getName()
				+ "',"
				+ status
				+ ",'"
				+ this.getParentType().getId()
				+ "','"
				+ this.getNote() + "')";
		int i = db.executeUpdate(sql) ;
		UserAddLog.setDebug("OracleTrainingCourseType.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() throws PlatformException {
		dbpool db = new dbpool();
		int status = 0;
		if (this.getIsActive())
			status = 1;
		String sql = "update training_course_type set name='" + this.getName()
				+ "',status=" + status + ",parent_id='"
				+ this.getParentType().getId() + "'," + "note='"
				+ this.getNote() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTrainingCourseType.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from  training_course_type  where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTrainingCourseType.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourseType#getTypeTreeCanMove()
	 */
	public List getTypeTreeCanMove() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id,name,status,parent_id,alevel from "
				+ "(select id,name,status,parent_id,level as alevel from "
				+ "(select id,name,status,parent_id from training_course_type) "
				+ "start with parent_id='root' connect by prior id = parent_id) "
				+ " where id in "
				+ "(select id from training_course_type minus "
				+ "select id from training_course_type  "
				+ "start with parent_id='" + this.getId() + "' "
				+ "connect by prior id = parent_id) ";
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList courseTypeTree = null;
		try {
			db = new dbpool();
			courseTypeTree = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				TrainingCourseType courseType = new OracleTrainingCourseType();
				courseType.setId(rs.getString("id"));
				courseType.setName(rs.getString("name"));
				courseType.setLevel(rs.getInt("alevel"));
				TrainingCourseType parentType = new OracleTrainingCourseType();
				parentType.setId(rs.getString("parent_id"));
				courseType.setParentType(parentType);
				if (rs.getInt("status") == 1)
					courseType.setActive(true);
				else
					courseType.setActive(false);
				if (courseType.getId() != null
						&& !courseType.getId().equals(this.getId()))
					courseTypeTree.add(courseType);
			}
		} catch (Exception e) {
			throw new PlatformException(
					"error in CourseType.getTypeTreeCanMove");
		} finally {
			db.close(rs);
			db = null;
			return courseTypeTree;
		}

	}

}
