package com.whaty.util;

import com.whaty.platform.entity.bean.PeBzzOnlineCourse;

public class OnlineCourseCounter {
	private static long activeSessions = 0;
	
	public static synchronized void increaseOnlineCourseSessions() {
		activeSessions ++;
	}
	
	public static synchronized void decreaseOnlineCourseSessions() {
		if(activeSessions >= 1)
			activeSessions --;
	}
	
	public static long getActiveSessions() {
		return activeSessions;
	}

	public static void setActiveSessions(long activeSessions) {
		OnlineCourseCounter.activeSessions = activeSessions;
	}

	
	
}
