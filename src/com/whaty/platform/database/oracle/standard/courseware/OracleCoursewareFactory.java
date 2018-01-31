package com.whaty.platform.database.oracle.standard.courseware;

import com.whaty.platform.courseware.CoursewareFactory;
import com.whaty.platform.courseware.CoursewareManage;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.courseware.FileManagerPriv;


public class OracleCoursewareFactory extends CoursewareFactory{

	public CoursewareManage creatCoursewareManage(CoursewareManagerPriv amanagerpriv) {
		return new OracleCoursewareManage(amanagerpriv);
	}

	public CoursewareManagerPriv getCoursewareManagerPriv(String id) {
		return new OracleCoursewareManagerPriv(id);
	}

	public FileManagerPriv getFileManagerPriv() {
		// TODO Auto-generated method stub
		return new OracleFileManagerPriv();
	}

	public FileManagerPriv getFileManagerPriv(String id) {
		// TODO Auto-generated method stub
		return new OracleFileManagerPriv(id);
	}

}
