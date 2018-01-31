package com.whaty.platform.entity.service.imp.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.service.basic.PrBzzTchOpenCourseService;

public class PrBzzTchOpenCourseServiceImp implements PrBzzTchOpenCourseService {

	private GeneralDao generalDao;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public void deleteByIds(List idList) {
		this.generalDao.deleteByIds(idList);
	}

	public PeBzzBatch getPeBzzBatch(DetachedCriteria criteria) {
		return generalDao.getPeBzzBatch(criteria);
	}
	
	
	
	public boolean saveElectiveList(List<PeBzzStudent> peBzzSdentList,
								 PeBzzTchCourse peBzzTchCourse,
								 PeBzzBatch peBzzBatch) {
		try {

				Iterator<PeBzzStudent> iterator = peBzzSdentList.iterator();
				while(iterator.hasNext()) {
					PrBzzTchStuElective stuEle = new PrBzzTchStuElective();
					DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
					dc.add(Restrictions.eq("peBzzBatch.id", peBzzBatch.getId()));
					dc.add(Restrictions.eq("peBzzTchCourse.id", peBzzTchCourse.getId()));
					
					List<PrBzzTchOpencourse> list = this.getGeneralDao().getDetachList(dc);
					
					stuEle.setPrBzzTchOpencourse(list.get(0));
					stuEle.setPeBzzStudent(iterator.next());
					stuEle.setElectiveDate(new Date());
					this.generalDao.save(stuEle);
					
				}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public void deleteElectiveByIds(List ids) {
		this.getGeneralDao().deleteByIds(PrBzzTchStuElective.class, ids);
	}
	
	public void deleteElectiveBackByIds(List ids) {
		this.getGeneralDao().deleteByIds(PrBzzTchStuElectiveBack.class, ids);
	}

	//@Override
	public int deleteByElectiveList(List<PrBzzTchStuElective> electiveList) {
		int i = 0;
//		List opencourseIdList = new ArrayList();
		List tcsIdList = new ArrayList();
		List electiveIdList = new ArrayList();
		for (int j = 0; j < electiveList.size(); j++) {
			tcsIdList.add(electiveList.get(j).getTrainingCourseStudent().getId());
//			if(!opencourseIdList.contains(electiveList.get(j).getPrBzzTchOpencourse().getId())){
//				opencourseIdList.add(electiveList.get(j).getPrBzzTchOpencourse().getId());
//			}
			electiveIdList.add(electiveList.get(j).getId());
		}
			this.getGeneralDao().deleteByIds(PrBzzTchStuElective.class, electiveIdList);
			if(tcsIdList !=null && tcsIdList.size() >0){
				i = this.getGeneralDao().deleteByIds(TrainingCourseStudent.class, tcsIdList);
			}
//			i = this.getGeneralDao().deleteByIds(PrBzzTchOpencourse.class, opencourseIdList);
		return i;
	}

	//@Override
	public int deleteOpencourseByIds(List idList) {
		DetachedCriteria dcEletive = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dcEletive.createCriteria("prBzzTchOpencourse","prBzzTchOpencourse");
		dcEletive.createCriteria("trainingCourseStudent","trainingCourseStudent");
		dcEletive.add(Restrictions.in("prBzzTchOpencourse.id", idList));
		
		List<PrBzzTchStuElective> electiveList = this.getGeneralDao().getList(dcEletive);
		if(electiveList != null && electiveList.size() > 0){
			this.deleteByElectiveList(electiveList);
		}
		return this.getGeneralDao().deleteByIds(PrBzzTchOpencourse.class, idList);
	}

	//@Override
	public void saveElectiveList(List<PrBzzTchOpencourse> opencourseList,List<PrBzzTchStuElective> electiveList) {
		
		if(electiveList !=null && electiveList.size() > 0){
		
			for (PrBzzTchStuElective prBzzTchStuElective : electiveList) {
				this.getGeneralDao().save(prBzzTchStuElective.getPrBzzTchOpencourse());
				this.getGeneralDao().save(prBzzTchStuElective.getTrainingCourseStudent());
				this.getGeneralDao().save(prBzzTchStuElective);
			}
		}else{
			for (PrBzzTchOpencourse prBzzTchOpencourse : opencourseList) {
				this.getGeneralDao().save(prBzzTchOpencourse);
			}
		}
	}
	
	//@Override
	public void saveElectiveListBack(List<PrBzzTchOpencourse> opencourseList,List<PrBzzTchStuElective> electiveList) {
		
		if(electiveList !=null && electiveList.size() > 0){
		
			for (PrBzzTchStuElective prBzzTchStuElective : electiveList) {
				PrBzzTchOpencourse prBzzTchOpencourse = (PrBzzTchOpencourse) this.getGeneralDao().save(prBzzTchStuElective.getPrBzzTchOpencourse());
				prBzzTchStuElective = prBzzTchStuElective;
				PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
				eleBack.setPeBzzStudent(prBzzTchStuElective.getPeBzzStudent());
				eleBack.setPrBzzTchOpencourse(prBzzTchOpencourse);
				eleBack.setPeElectiveCoursePeriod(prBzzTchStuElective.getPeElectiveCoursePeriod());
				//this.save((T)prBzzTchStuElective.getTrainingCourseStudent());
				this.generalDao.save(eleBack);
			}
		}else{
			for (PrBzzTchOpencourse prBzzTchOpencourse : opencourseList) {
				this.getGeneralDao().save(prBzzTchOpencourse);
			}
		}
	}

}
