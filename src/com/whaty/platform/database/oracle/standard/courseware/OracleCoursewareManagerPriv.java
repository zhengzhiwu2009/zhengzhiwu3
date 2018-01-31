package com.whaty.platform.database.oracle.standard.courseware;

import java.io.Serializable;

import com.whaty.platform.courseware.CoursewareManagerPriv;

public class OracleCoursewareManagerPriv extends CoursewareManagerPriv implements Serializable{

	public OracleCoursewareManagerPriv(String id) {
		super();
		this.messageId = id ;
		this.addWhatyCoursewareTemplate=1;
		this.buildNormalHttpCourseware=1;
		this.buildWhatyOnlineCourseware=1;
		this.buildWhatyUploadCourseware=1;
		this.deleteWhatyCoursewareTemplate=1;
		this.getWhatyCoursewareTemplate=1;
		this.updateWhatyCoursewareTemplate=1;
		this.getCourseware=1;
		this.addChapterPage=1;
		this.addOnlineCwInfo=1;
		this.deleteOnlineCwInfo=1;
		this.getOnlineCwInfo=1;
		this.getOnlineCwRootDir=1;
		this.updateNormalHttpLink=1;
		this.updateUploadEnterFile=1;
		this.filemanage=0;
	}
	
	

}
