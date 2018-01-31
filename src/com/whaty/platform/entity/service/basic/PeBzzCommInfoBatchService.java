package com.whaty.platform.entity.service.basic;

import java.io.File;

import com.whaty.platform.entity.bean.PeBzzCommInfo;
import com.whaty.platform.entity.exception.EntityException;

public interface PeBzzCommInfoBatchService {

	public int Bacthsave(File filetest) throws EntityException;

	public PeBzzCommInfo save(PeBzzCommInfo bean);
	
}
