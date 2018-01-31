package com.whaty.platform.entity.service.recruit;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;

public interface PeBzzRecruitbatchService {
	
	public int Bacthsave(File file,String id)throws EntityException, MessageException;

	public PeBzzRecruit save(PeBzzRecruit bean) throws EntityException;

	public int deleteByIds(List idList)throws EntityException;
	
	public int BacthBeforesave(File file,PeBzzBatch bzzBatch)throws EntityException, MessageException;
}
