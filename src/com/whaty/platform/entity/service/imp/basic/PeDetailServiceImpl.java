package com.whaty.platform.entity.service.imp.basic;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeDetailService;

public class PeDetailServiceImpl implements PeDetailService{

private GeneralDao generalDao;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
	public void deleteByIds(List idList) {
		// TODO Auto-generated method stub
		this.getGeneralDao().deleteByIds(PrBzzTchStuElective.class, idList);
	}
	
	public AbstractBean save(AbstractBean bean)throws EntityException{
		
		try {
			bean = this.generalDao.save(bean);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return bean;
	}

	//@Override
	public List saveStuBatch(List electivelist, List<StudentBatch> stuBatchList)
			throws EntityException {
		for (int i = 0; i < stuBatchList.size(); i++) {
			stuBatchList.set(i,(StudentBatch)this.save(stuBatchList.get(i)));
		}
		PrBzzTchStuElective prBzzTchStuElective;
		for (int i = 0; i < electivelist.size(); i++) {
			prBzzTchStuElective = (PrBzzTchStuElective)electivelist.get(i);
			this.save((TrainingCourseStudent)prBzzTchStuElective.getTrainingCourseStudent());
			electivelist.set(i, save((PrBzzTchStuElective)prBzzTchStuElective));
		}
		return stuBatchList;
	}
	
	//@Override
	public List saveStuBatchBack(List<PrBzzTchStuElectiveBack> electiveBacklist, List<StudentBatch> stuBatchList)
			throws EntityException {
		for (int i = 0; i < stuBatchList.size(); i++) {
			stuBatchList.set(i,(StudentBatch)this.save(stuBatchList.get(i)));
		}
		if (electiveBacklist != null) {
			for (PrBzzTchStuElectiveBack p : electiveBacklist) {
				//PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
				//eleBack.setPeBzzOrderInfo(peBzzOrderInfo);
//				eleBack.setPeBzzStudent(p.getPeBzzStudent());
//				eleBack.setPrBzzTchOpencourse(p.getPrBzzTchOpencourse());
				this.generalDao.save(p);
			}
		}
		return stuBatchList;
	}

	//@Override
	public int deleteStuBatch(List stuBatchIdList, List electiveIdList)
			throws EntityException {
		int i = 0;
		if(electiveIdList !=null && electiveIdList.size() >0){
			DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
			dc.createAlias("trainingCourseStudent", "trainingCourseStudent");
			dc.add(Restrictions.in("id", electiveIdList));
			dc.setProjection(Projections.distinct(Projections.property("trainingCourseStudent.id")));
			List tcsIdList = this.getGeneralDao().getList(dc);
			this.getGeneralDao().deleteByIds(PrBzzTchStuElective.class, electiveIdList);
			if(tcsIdList !=null && tcsIdList.size() >0){
				this.getGeneralDao().deleteByIds(TrainingCourseStudent.class, tcsIdList);
			}
		}
		i = this.getGeneralDao().deleteByIds(StudentBatch.class, stuBatchIdList);
		return i;
	}
	
	//@Override
	public int deleteStuBatchBack(List stuBatchIdList, List electiveIdList)
			throws EntityException {
		int i = 0;
		if(electiveIdList !=null && electiveIdList.size() >0){
			DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
//			dc.createAlias("trainingCourseStudent", "trainingCourseStudent");
			dc.add(Restrictions.in("id", electiveIdList));
//			dc.setProjection(Projections.distinct(Projections.property("trainingCourseStudent.id")));
//			List tcsIdList = this.getGeneralDao().getList(dc);
			this.getGeneralDao().deleteByIds(PrBzzTchStuElectiveBack.class, electiveIdList);
//			if(tcsIdList !=null && tcsIdList.size() >0){
//				this.getGeneralDao().deleteByIds(TrainingCourseStudent.class, tcsIdList);
//			}
		}
		i = this.getGeneralDao().deleteByIds(StudentBatch.class, stuBatchIdList);
		return i;
	}
}
