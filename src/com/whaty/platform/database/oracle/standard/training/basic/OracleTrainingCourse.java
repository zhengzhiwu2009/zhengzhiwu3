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
import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.training.basic.StudentCourseStatus;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.basic.TrainingCourseType;
import com.whaty.platform.training.basic.TrainingCourseware;
import com.whaty.platform.training.basic.TrainingCoursewareType;
import com.whaty.platform.training.basic.courseware.AiccTrainingCourseware;
import com.whaty.platform.training.basic.courseware.Scorm12TrainingCourseware;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.TrainingLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingCourse extends TrainingCourse {
	
	public OracleTrainingCourse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleTrainingCourse(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			String sql = "select t.id,t.name,t.code,c.id as courseware_id,c.code as courseware_code from pe_bzz_tch_course t,pe_bzz_tch_courseware c where t.id = c.fk_course_id  and t.id='"
					+ id + "'";
			rs = db.executeQuery(sql);
			TrainingLog.setDebug(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setCoursewareType(TrainingCoursewareType.SCORM12);
				this.setCoursewareId(rs.getString("courseware_id"));
				this.setCoursewareCode(rs.getString("courseware_code"));
			//	this.setStatus(rs.getString("status"));
			//	TrainingCourseType courseType = new OracleTrainingCourseType();
			//	courseType.setId(rs.getString("course_typeid"));
			//	courseType.setName(rs.getString("coursetype_name"));
			//	this.setTrainingCourseType(courseType);
//				this.setCoursewareId(rs.getString("courseware_id"));
//				this.setCoursewareType(rs.getString("courseware_type"));
//				this.setNote(rs.getString("note"));
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into training_course(id,name,course_typeid,status,note) "
				+ " values('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getTrainingCourseType().getId()
				+ "'"
				+ ",'"
				+ this.getStatus() + "','" + this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTrainingCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update training_course set name='" + this.getName()
				+ "'," + "course_typeid='"
				+ this.getTrainingCourseType().getId() + "'," + "status='"
				+ this.getStatus() + "',note='" + this.getNote() + "',"
				+ "courseware_id='" + this.getCoursewareId() + "'	,"
				+ "courseware_type='" + this.getCoursewareType()
				+ "'  where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTrainingCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from training_course where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTrainingCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#getTraininigCourseware()
	 */
	public TrainingCourseware getTrainingCourseware() throws PlatformException {
		if (this.getCoursewareType() == null)
			return null;
		if (this.getCoursewareType().equals(TrainingCoursewareType.AICC)) {
			try {
				return new AiccTrainingCourseware(this.getCoursewareId());
			} catch (AiccException e) {
				throw new PlatformException("aicc error!");
			}
		} else if (this.getCoursewareType().equals(
				TrainingCoursewareType.SCORM12)) {
			try {
				return new Scorm12TrainingCourseware(this.getCoursewareCode());
			} catch (Exception e) {
				throw new PlatformException("scorm12 error!");
			}
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#getIsActive()
	 */
	public boolean getIsActive() throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		try {
			return strManage.checkStatus(this.getStatus(), 1);
		} catch (WhatyUtilException e) {
			throw new PlatformException("error in TrainingCourse.getIsActive");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#setActive(boolean)
	 */
	public void setActive(boolean isActive) throws PlatformException {
		if (this.getStatus() == null)
			this.setStatus("00000000");
		StrManage strManage = StrManageFactory.creat();
		try {
			this.setStatus(strManage
					.updateStatus(this.getStatus(), 1, isActive));
		} catch (WhatyUtilException e) {
			throw new PlatformException("error in TrainingCourse.setActive");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#getIsPubView()
	 */
	public boolean getIsPubView() throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		try {
			return strManage.checkStatus(this.getStatus(), 2);
		} catch (WhatyUtilException e) {
			throw new PlatformException("error in TrainingCourse.getIsActive");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#setPubView(boolean)
	 */
	public void setPubView(boolean isPubView) throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		try {
			this.setStatus(strManage.updateStatus(this.getStatus(), 2,
					isPubView));
		} catch (WhatyUtilException e) {
			throw new PlatformException("error in TrainingCourse.setActive");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#getIsPubSelect()
	 */
	public boolean getIsPubSelect() throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		try {
			return strManage.checkStatus(this.getStatus(), 3);
		} catch (WhatyUtilException e) {
			throw new PlatformException("error in TrainingCourse.getIsActive");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#setPubSelect(boolean)
	 */
	public void setPubSelect(boolean isPubSelect) throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		try {
			this.setStatus(strManage.updateStatus(this.getStatus(), 3,
					isPubSelect));
		} catch (WhatyUtilException e) {
			throw new PlatformException("error in TrainingCourse.setActive");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#getSelectedStudents()
	 */
	public List getSelectedStudents() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.SELECTED);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		return basicList.searchCourseStudents(null, searchproperty, null, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#getSelectedStudentsNum()
	 */
	public int getSelectedStudentsNum() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.SELECTED);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		return basicList.searchCourseStudentsNum(searchproperty, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#getUndercheckStudents()
	 */
	public List getUndercheckStudents() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.UNDERCHECK);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		return basicList.searchCourseStudents(null, searchproperty, null, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingCourse#getUndercheckStudentsNum()
	 */
	public int getUndercheckStudentsNum() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		SearchProperty search = new SearchProperty("studentcourse_status",
				StudentCourseStatus.UNDERCHECK);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		return basicList.searchCourseStudentsNum(searchproperty, this);
	}

}
