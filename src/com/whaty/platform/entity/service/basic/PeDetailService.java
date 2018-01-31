package com.whaty.platform.entity.service.basic;

import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;

public interface PeDetailService {

	public void deleteByIds(List idList);
	
	public AbstractBean save(AbstractBean bean) throws EntityException;
	
	public List saveStuBatch(List electivelist,List<StudentBatch> stuBatchList) throws EntityException;
	
	public List saveStuBatchBack(List<PrBzzTchStuElectiveBack> electivelist,List<StudentBatch> stuBatchList) throws EntityException;
	
	public int deleteStuBatch(List stuBatchIdList,List electiveIdList)throws EntityException;
	
	public int deleteStuBatchBack(List stuBatchIdList,List electiveIdList)throws EntityException;
}
