/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.user;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.training.user.TrainingUserList;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.TrainingLog;

/**
 * @author chenjian
 *
 */
public class OracleTrainingUserList implements TrainingUserList {

	private String STUDENTSQL="select id,name,nick_name,email,status,mobilephone,reg_date from training_student ";
	
	private String CLASSMANAGERSQL="select id,name,nick_name,email,note, mobilephone from " +
			"training_chief ";
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.training.user.TrainingUserList#searchTrainingStudents(com.whaty.platform.util.Page, java.util.List, java.util.List)
	 */
	public List searchTrainingClassManager(Page page, List serachProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = CLASSMANAGERSQL + Conditions.convertToCondition(serachProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList chiefs = null;
		try {
			db = new dbpool();
			chiefs = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			
			while (rs != null && rs.next()) {
				OracleTrainingClassManager chief=new OracleTrainingClassManager();
				chief.setId(rs.getString("id"));
				chief.setName(rs.getString("name"));
				chief.setNickName(rs.getString("nick_name"));
				chief.setEmail(rs.getString("email"));
				chief.setNote(rs.getString("note"));
				chief.setMobilePhone(rs.getString("mobilephone"));
				chiefs.add(chief);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchTrainingStudents with "+e.getLocalizedMessage());
		} finally {
			db.close(rs);
			db = null;
			return chiefs;
		}		
	}
	
	public int getTrainingClassManagerNum(List serachProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = CLASSMANAGERSQL + Conditions.convertToCondition(serachProperty, orderProperty);
		
		return db.countselect(sql);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.user.TrainingUserList#searchTrainingClassManager(com.whaty.platform.util.Page, java.util.List, java.util.List)
	 */
	public List searchTrainingStudents(Page page, List serachProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = STUDENTSQL + Conditions.convertToCondition(serachProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList students = null;
		try {
			db = new dbpool();
			students = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTrainingStudent student=new OracleTrainingStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setNickName(rs.getString("nick_name"));
				student.setEmail(rs.getString("email"));
				student.setMobilePhone(rs.getString("mobilephone"));
				student.setRegDate(rs.getString("reg_date"));
				if(rs.getString("status").equals("1"))
					student.setActive(true);
				else
					student.setActive(false);
				students.add(student);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchTrainingStudents with "+e.getLocalizedMessage());
		} finally {
			db.close(rs);
			db = null;
			return students;
		}
	}
	
	public int getTrainingStudentsNum(List serachProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = STUDENTSQL + Conditions.convertToCondition(serachProperty, orderProperty);
		
		return db.countselect(sql);
	}

}
