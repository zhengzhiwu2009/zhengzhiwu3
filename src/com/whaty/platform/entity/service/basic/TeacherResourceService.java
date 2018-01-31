package com.whaty.platform.entity.service.basic;

import com.whaty.platform.entity.bean.TeacherInfo;
import com.whaty.platform.entity.exception.EntityException;

public interface TeacherResourceService {
	public TeacherInfo add(TeacherInfo teacherInfo) throws EntityException;
}
