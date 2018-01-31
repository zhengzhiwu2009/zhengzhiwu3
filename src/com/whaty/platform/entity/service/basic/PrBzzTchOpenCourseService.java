package com.whaty.platform.entity.service.basic;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;

public interface PrBzzTchOpenCourseService {

	public void deleteByIds(List idList);

	public PeBzzBatch getPeBzzBatch(DetachedCriteria criteria);
	
	public boolean saveElectiveList(List<PeBzzStudent> peBzzSdentList,PeBzzTchCourse peBzzTchCourse,PeBzzBatch peBzzBatch);
	
	public void saveElectiveList(List<PrBzzTchOpencourse> opencourseList,List<PrBzzTchStuElective> electiveList);

	public void saveElectiveListBack(List<PrBzzTchOpencourse> opencourseList,List<PrBzzTchStuElective> electiveList);
	
	public void deleteElectiveByIds(List ids);
	
	public int deleteByElectiveList(List<PrBzzTchStuElective> electiveList);

	public int deleteOpencourseByIds(List idList);

	public void deleteElectiveBackByIds(List electiveIdList);

}
