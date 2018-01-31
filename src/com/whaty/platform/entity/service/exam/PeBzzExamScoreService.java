package com.whaty.platform.entity.service.exam;


import java.io.File;

import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;

public interface PeBzzExamScoreService {
	
	public int Bacthsave(File file,String id)throws EntityException, MessageException;
	
	public int Bacthsave(File file,String id,String score_type)throws EntityException, MessageException;
	
	public int BacthExamsave(File file,String id)throws EntityException, MessageException;
	
}
