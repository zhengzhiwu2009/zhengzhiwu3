package com.whaty.platform.entity.service.imp.basic;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBackHistory;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveHistory;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.bean.TrainingCourseStudentHistory;
import com.whaty.platform.entity.dao.EnumConstDAO;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.RefoundManageService;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class RefoundManageServiceImpl extends GeneralServiceImp implements RefoundManageService{

//	/**
//	 * 支付退款
//	 */
//	@Override
//	public void deleteEleCourse(PeBzzRefundInfo peBzzRefundInfo) {
//		// TODO Auto-generated method stub
//		PeBzzOrderInfo peBzzOrderInfo = peBzzRefundInfo.getPeBzzOrderInfo();
//		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("ClassHourRate", "0");
//		//选课支付退费
//		String sql = " delete from pr_bzz_tch_stu_elective pbtse where pbtse.fk_order_id = '" + peBzzOrderInfo.getId() + "'";
//		this.getGeneralDao().executeBySQL(sql);
///*		DetachedCriteria refundEle = DetachedCriteria.forClass(PrBzzTchStuElective.class);
//		refundEle.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
//		List eleList = this.getGeneralDao().getDetachList(refundEle);
//		Iterator<PrBzzTchStuElective> it = eleList.iterator();
//		while(it.hasNext()) {
//			PrBzzTchStuElective ele = it.next();
//			this.getGeneralDao().delete(ele);
//		}*/
//		SsoUser user = peBzzOrderInfo.getSsoUser();
//		double num = Double.parseDouble(peBzzOrderInfo.getAmount())/Double.parseDouble(ec.getName());
//		//user.setSumAmount(Double.parseDouble(user.getSumAmount())+num+"");
//		user.setAmount(Double.parseDouble(user.getAmount())-num+"");
//		
//		this.getGeneralDao().save(user);
//		EnumConst ec2 = this.getEnumConstDao().getByNamespaceCode("FlagRefundState", "1");
//		peBzzRefundInfo.setEnumConstByFlagRefundState(ec2);
//		this.getGeneralDao().save(peBzzRefundInfo);
//		EnumConst ec3 = this.getEnumConstDao().getByNamespaceCode("FlagOrderState", "1");
//		peBzzOrderInfo.setEnumConstByFlagOrderState(ec3);
//		peBzzOrderInfo.setEnumConstByFlagRefundState(ec2);
//		peBzzOrderInfo.setRefundDate(new Date());
//		this.getGeneralDao().save(peBzzOrderInfo);
//		
//		
//	}
	/**
	 * 拒绝退费操作
	 */
	@Override
	public void refuseRefundApply(PeBzzRefundInfo peBzzRefundInfo) {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec = this.getEnumConstDao().getByNamespaceCode("FlagRefundState", "2");
		String logs = peBzzRefundInfo.getOperationLogs();
		if(logs == null) {
			logs = "";
		}
		StringBuffer buf = new StringBuffer(logs);
		buf.append("|"+us.getLoginId()+"/"+us.getUserName()+"执行了拒绝退费操作\n");
		peBzzRefundInfo.setOperationLogs(buf.toString());
		peBzzRefundInfo.setEnumConstByFlagRefundState(ec);
		this.getGeneralDao().save(peBzzRefundInfo);
		PeBzzOrderInfo peBzzOrderInfo = peBzzRefundInfo.getPeBzzOrderInfo();
		peBzzOrderInfo.setEnumConstByFlagRefundState(ec);
		this.getGeneralDao().save(peBzzOrderInfo);
	}
//	/**
//	 * 在线支付退款
//	 */
//	public boolean saveOnlineRefund(PeBzzRefundInfo peBzzRefundInfo) {
//		try {
//			PeBzzOrderInfo peBzzOrderInfo = peBzzRefundInfo.getPeBzzOrderInfo();
//			//选课支付退费
//			DetachedCriteria refundEle = DetachedCriteria.forClass(PrBzzTchStuElective.class);
//			refundEle.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
//			List eleList = this.getGeneralDao().getDetachList(refundEle);
//			Iterator<PrBzzTchStuElective> it = eleList.iterator();
//			while(it.hasNext()) {
//				PrBzzTchStuElective ele = it.next();
//				this.getGeneralDao().delete(ele);
//			}
//			EnumConst ec2 = this.getEnumConstDao().getByNamespaceCode("FlagRefundState", "1");
//			peBzzRefundInfo.setEnumConstByFlagRefundState(ec2);
//			this.getGeneralDao().save(peBzzRefundInfo);
//			EnumConst ec3 = this.getEnumConstDao().getByNamespaceCode("FlagOrderState", "1");
//			peBzzOrderInfo.setEnumConstByFlagOrderState(ec3);
//			peBzzOrderInfo.setEnumConstByFlagRefundState(ec2);
//			peBzzOrderInfo.setRefundDate(new Date());
//			this.getGeneralDao().save(peBzzOrderInfo);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//			return false;
//		}
//		return true;
//	}

	
//	/**
//	 * 备份退费会删除的数据
//	 * @param peBzzRefundInfo
//	 */
//	public void backupRefundData(PeBzzRefundInfo peBzzRefundInfo) throws EntityException{
//		try{
//			DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
//				dc.createCriteria("ssoUser");
//				dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
//				dc.createCriteria("prBzzTchOpencourse.peBzzTchCourse","peBzzTchCourse");
//				dc.createCriteria("peBzzStudent");
//				dc.createCriteria("enumConstByFlagElectivePayStatus");
//				dc.createCriteria("peBzzOrderInfo", DetachedCriteria.LEFT_JOIN);
//				dc.createCriteria("trainingCourseStudent");
//				dc.add(Restrictions.eq("peBzzOrderInfo.id", peBzzRefundInfo.getPeBzzOrderInfo().getId()));
//				
//			DetachedCriteria backDc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
//				backDc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
//				backDc.createCriteria("peBzzOrderInfo", DetachedCriteria.LEFT_JOIN);
//				backDc.add(Restrictions.eq("peBzzOrderInfo.id", peBzzRefundInfo.getPeBzzOrderInfo().getId()));
//				
//			List<PrBzzTchStuElective> eleList = this.getGeneralDao().getList(dc);
//			List<PrBzzTchStuElectiveBack> eleBackList = this.getGeneralDao().getList(backDc);
//
//			this.transferElectiveHistory(eleList,eleBackList);
//					
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new EntityException("退费操作失败！");
//		}
//	}
}
