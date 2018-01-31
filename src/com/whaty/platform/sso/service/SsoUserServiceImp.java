package com.whaty.platform.sso.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PePriority;
import com.whaty.platform.entity.bean.PrPriRole;
import com.whaty.platform.entity.bean.PrPriManagerEdutype;
import com.whaty.platform.entity.bean.PrPriManagerGrade;
import com.whaty.platform.entity.bean.PrPriManagerMajor;
import com.whaty.platform.entity.bean.PrPriManagerSite;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzPriManagerEnterprise;
import com.whaty.platform.entity.bean.PeEnterprise;

public class SsoUserServiceImp extends GeneralServiceImp<SsoUser> implements SsoUserService{
	
	/**
	 * 根据登陆类型以及用户信息检测登陆账号是否可用
	 * @param ssoUser
	 * @param loginType
	 * @author cailei
	 * @throws SsoException
	 */
	public boolean CheckSsoUserByType(SsoUser ssoUser, String loginType) throws SsoException {
		boolean pass = false;
		try{
			if(SsoConstant.SSO_MANAGER.equals(loginType)){//验证管理员身份
				PeManager peManager = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					peManager = (PeManager)managers.get(0);
					if(SsoConstant.SSO_USER_IS_VALID.equals(peManager.getEnumConstByFlagIsvalid().getCode())){
						pass = true;
					}else{
						throw new SsoException("您的账号处于无效，不能登陆");
					}
				}
			}
			if(SsoConstant.SSO_SITEMANAGER.equals(loginType)){//验证分站管理员身份
//				PeSitemanager peSiteManager = null;
//				DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class);
//				dc.createCriteria("ssoUser", "ssoUser");
//				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
//				List managers = this.getGeneralDao().getList(dc);
//				if(managers !=null && !managers.isEmpty()) {
//					peSiteManager = (PeSitemanager)managers.get(0);
//					if(SsoConstant.SSO_USER_IS_VALID.equals(peSiteManager.getEnumConstByFlagIsvalid().getCode())){
//						pass = true;
//					}else{
//						throw new SsoException("您的账号处于无效，不能登陆");
//					}
//				}
				if(!"4".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode()) 
						&& !"5".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode()) 
						&& !"6".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())){
					
					PeEnterpriseManager peEnterpriseManager = null;
					DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
					dc.createCriteria("ssoUser", "ssoUser");
					dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
					List managers = this.getGeneralDao().getList(dc);
					if(managers !=null && !managers.isEmpty()) {
						peEnterpriseManager = (PeEnterpriseManager)managers.get(0);
						if((peEnterpriseManager.getEnumConstByFlagIsvalid()!=null)&&SsoConstant.SSO_USER_IS_VALID.equals(peEnterpriseManager.getEnumConstByFlagIsvalid().getCode())){
							pass = true;
						}else{
							throw new SsoException("您的账号还没有被审核通过，不能登陆");
						}
					}
				}
			}
			if(SsoConstant.SSO_TEACHER.equals(loginType)){//验证管理员身份
				PeTeacher peTeacher = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeTeacher.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				List teachers = this.getGeneralDao().getList(dc);
				if(teachers !=null && !teachers.isEmpty()) {
					peTeacher = (PeTeacher)teachers.get(0);
//					System.out.println(peTeacher.getEnumConstByFlagActive().getCode());
					if(SsoConstant.SSO_USER_IS_VALID.equals(peTeacher.getEnumConstByFlagActive().getCode())){
						pass = true;
					}else{
						throw new SsoException("您的账号处于无效，不能登陆");
					}
				}
			}
			if(SsoConstant.SSO_STUDENT.equals(loginType)){//验证学生身份
				PeBzzStudent peStudent = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					peStudent = (PeBzzStudent)managers.get(0);
					if(SsoConstant.SSO_USER_IS_VALID.equals(peStudent.getSsoUser().getEnumConstByFlagIsvalid().getCode())){
						pass = true;
					}else{
						throw new SsoException("您的账号处于无效，不能登陆");
					}
				}
			}
			if(SsoConstant.SSO_SECONDSITEMANAGER.equals(loginType)){//验证二级管理员身份
				
				if(!"5".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode()) 
						&& !"6".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())){
					PeEnterpriseManager peEnterpriseManager = null;
					DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
					dc.createCriteria("ssoUser", "ssoUser");
					dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
					List managers = this.getGeneralDao().getList(dc);
					if(managers !=null && !managers.isEmpty()) {
						peEnterpriseManager = (PeEnterpriseManager)managers.get(0);
						if((peEnterpriseManager.getEnumConstByFlagIsvalid()!=null)&&SsoConstant.SSO_USER_IS_VALID.equals(peEnterpriseManager.getEnumConstByFlagIsvalid().getCode())){
							pass = true;
						}else{
							throw new SsoException("您的账号还没有被审核通过，不能登陆");
						}
					}
				}
			}
			
			if(SsoConstant.SSO_REGMANAGER.equals(loginType)){//验证一级监管管理员身份
				
				if(!"6".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())){
					PeEnterpriseManager peEnterpriseManager = null;
					DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
					dc.createCriteria("ssoUser", "ssoUser");
					dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
					List managers = this.getGeneralDao().getList(dc);
					if(managers !=null && !managers.isEmpty()) {
						peEnterpriseManager = (PeEnterpriseManager)managers.get(0);
						if((peEnterpriseManager.getEnumConstByFlagIsvalid()!=null)&&SsoConstant.SSO_USER_IS_VALID.equals(peEnterpriseManager.getEnumConstByFlagIsvalid().getCode())){
							pass = true;
						}else{
							throw new SsoException("您的账号还没有被审核通过，不能登陆");
						}
					}
				}
			}
			
			if(SsoConstant.SSO_SECONDREGMANAGER.equals(loginType)){//验证一级监管管理员身份
				PeEnterpriseManager peEnterpriseManager = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					peEnterpriseManager = (PeEnterpriseManager)managers.get(0);
					if((peEnterpriseManager.getEnumConstByFlagIsvalid()!=null)&&SsoConstant.SSO_USER_IS_VALID.equals(peEnterpriseManager.getEnumConstByFlagIsvalid().getCode())){
						pass = true;
					}else{
						throw new SsoException("您的账号还没有被审核通过，不能登陆");
					}
				}
			}
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		
		return pass;
	}
    
	/**
	 * 首页取回密码功能验证输入是否正确
	 * @param ssoUser
	 * @param loginType
	 * @param trueName 姓名
	 * @param cardId身份证号
	 * @author cailei
	 */
	public boolean checkTrue(SsoUser ssoUser, String loginType ,String trueName ,String cardId){
		boolean pass = false;
			if(SsoConstant.SSO_MANAGER.equals(loginType)){//验证管理员身份
				PeManager peManager = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				dc.add(Restrictions.eq("trueName", trueName));
				dc.add(Restrictions.eq("idCard", cardId));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					pass = true;
				}
			}
			if(SsoConstant.SSO_SITEMANAGER.equals(loginType)){//验证分站管理员身份
				PeSitemanager peSiteManager = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				dc.add(Restrictions.eq("trueName", trueName));
				dc.add(Restrictions.eq("idCard", cardId));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					pass = true;
				}
			}if(SsoConstant.SSO_TEACHER.equals(loginType)){//验证教师身份
				PeTeacher peTeacher = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeTeacher.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				dc.add(Restrictions.eq("trueName", trueName));
				dc.add(Restrictions.eq("idCard", cardId));
				List teachers = this.getGeneralDao().getList(dc);
				if(teachers !=null && !teachers.isEmpty()) {
					pass = true;
				}
			}if(SsoConstant.SSO_STUDENT.equals(loginType)){//验证学生身份
				PeStudent peStudent = null;
				DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser.loginId", ssoUser.getLoginId()));
				DetachedCriteria dcInfo = dc.createCriteria("prStudentInfo", "prStudentInfo");
				dc.add(Restrictions.eq("trueName", trueName));
				dcInfo.add(Restrictions.eq("cardNo", cardId));
				List managers = this.getGeneralDao().getList(dc);
				if(managers !=null && !managers.isEmpty()) {
					pass = true;
				}
			}

		
		return pass;
		
	}
	
	/**
	 * 首页取回密码功能设置新密码
	 * @param passwd
	 * @return SsoUser
	 * @author cailei
	 */
	public void saveNewPassword(SsoUser ssoUser,String passwd){
		ssoUser.setPassword(passwd);
		this.getGeneralDao().save(ssoUser);
	}
	
	/**
	 * 根据用户id取得相应信息
	 * @param loginId
	 * @return SsoUser
	 * @author cailei
	 * @throws EntityException
	 */
	public SsoUser getByLoginId(String loginId) throws EntityException{
		final String getByLoginId = "from SsoUser u where upper(u.loginId) = upper('"+loginId+"') or lower(u.loginId) = lower('"+loginId+"')";
		List<SsoUser> ssoUsers = this.getGeneralDao().getByHQL(getByLoginId);
		if(ssoUsers !=null && !ssoUsers.isEmpty()){
			return ssoUsers.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据登陆类型，以及用户信息取得相应的用户的userSession信息
	 * @param ssoUser
	 * @param loginType
	 * @return UserSession
	 * @author shangbingcang
	 * @throws SsoException
	 */
	public UserSession getUserSession(SsoUser user, String loginType) throws SsoException{
		UserSession us = new UserSession();
		us.setSsoUser(user);
		us.setId(user.getId());
		us.setLoginId(user.getLoginId());
		us.setRoleId(user.getPePriRole() != null ? user.getPePriRole().getId() : null);
		us.setUserLoginType(loginType);
		
		//设置权限 
		Map userPriorityMap = new HashMap();
		if(user.getPePriRole()!= null){
			userPriorityMap = getUserPriority(user);
		}else{
			throw new SsoException("没有为用户设置角色!");
		}
		us.setUserPriority(userPriorityMap);
		
		//设置权限范围;
		DetachedCriteria dcSite = DetachedCriteria.forClass(PrPriManagerSite.class)
										.createAlias("ssoUser", "ssoUser")
										.createAlias("peSite", "peSite");
		dcSite.add(Restrictions.eq("ssoUser.id", user.getId()));
		List priMananerSiteList = getGeneralDao().getList(dcSite);
		List siteList = new ArrayList();
		if(priMananerSiteList != null && !priMananerSiteList.isEmpty()){
			Iterator it = priMananerSiteList.iterator();
			while(it.hasNext()){
				PrPriManagerSite prsite = (PrPriManagerSite)it.next();
				siteList.add(prsite.getPeSite());
			}
			us.setPriSites(siteList); 
		}
		
		/**
		 * 当修改机构或者是二级机构是此方法无效，改为用下面的那个
		 */
//		//	设置企业管理员权限范围;
//		DetachedCriteria dcEnterprise = DetachedCriteria.forClass(PrBzzPriManagerEnterprise.class)
//										.createAlias("ssoUser", "ssoUser")
//										.createAlias("peEnterprise", "peEnterprise");
//		dcEnterprise.add(Restrictions.eq("ssoUser.id", user.getId()));
//		List priMananerEnterpriseList = getGeneralDao().getList(dcEnterprise);
//		List enterpriseList = new ArrayList();
//		if(priMananerEnterpriseList != null && !priMananerEnterpriseList.isEmpty()){
//			Iterator it = priMananerEnterpriseList.iterator();
//			while(it.hasNext()){
//				PrBzzPriManagerEnterprise prenterprise = (PrBzzPriManagerEnterprise)it.next();
//				enterpriseList.add(prenterprise.getPeEnterprise());
//			}
//			us.setPriEnterprises(enterpriseList); 
//		}
		
		//	设置企业管理员权限范围,联合管理员表取机构
		DetachedCriteria dcEnterprise = DetachedCriteria.forClass(PeEnterpriseManager.class)
										.createAlias("ssoUser", "ssoUser")
										.createAlias("peEnterprise", "peEnterprise");
		dcEnterprise.add(Restrictions.eq("ssoUser.id", user.getId()));
		List priMananerEnterpriseList = getGeneralDao().getList(dcEnterprise);
		List enterpriseList = new ArrayList();
		if(priMananerEnterpriseList != null && !priMananerEnterpriseList.isEmpty()){
			Iterator it = priMananerEnterpriseList.iterator();
			while(it.hasNext()){
				PeEnterpriseManager prenterprise = (PeEnterpriseManager)it.next();
				enterpriseList.add(prenterprise.getPeEnterprise());
				/**
				 * 20130114添加，向session中添加管理员姓名，如果姓名为空，则用系统编号代替
				 */
				if(prenterprise.getName()!=null && !"".equals(prenterprise.getName())) {
					us.setUserName(prenterprise.getName());
				} else {
					us.setUserName(user.getLoginId());
				}
			}
			us.setPriEnterprises(enterpriseList); 
			
		}
		
		
		
		DetachedCriteria dcEdutype = DetachedCriteria.forClass(PrPriManagerEdutype.class)
											.createAlias("ssoUser", "ssoUser")
											.createAlias("peEdutype", "peEdutype");
		dcEdutype.add(Restrictions.eq("ssoUser.id", user.getId()));
		
		List priManagerEdutypeList = getGeneralDao().getList(dcEdutype);
		List eduList = new ArrayList();
		if(priManagerEdutypeList != null && !priManagerEdutypeList.isEmpty()){
			Iterator it = priManagerEdutypeList.iterator();
			while(it.hasNext()){
				PrPriManagerEdutype predu = (PrPriManagerEdutype)it.next();
				eduList.add(predu.getPeEdutype());
			}
			us.setPriEdutype(eduList);
		}
		
		DetachedCriteria dcMajor = DetachedCriteria.forClass(PrPriManagerMajor.class)
										.createAlias("ssoUser", "ssoUser")
										.createAlias("peMajor", "peMajor");
		dcMajor.add(Restrictions.eq("ssoUser.id", user.getId()));
		List priManagerMajorList = getGeneralDao().getList(dcMajor);
		List majorList = new ArrayList();
		if(priManagerMajorList != null && !priManagerMajorList.isEmpty()){
			Iterator it = priManagerMajorList.iterator();
			while(it.hasNext()){
				PrPriManagerMajor prMajor = (PrPriManagerMajor)it.next();
				majorList.add(prMajor.getPeMajor());
			}
			us.setPriMajor(majorList);
		}
		
		
		DetachedCriteria dcGrade = DetachedCriteria.forClass(PrPriManagerGrade.class)
											.createAlias("ssoUser", "ssoUser")
											.createAlias("peGrade", "peGrade");
		dcGrade.add(Restrictions.eq("ssoUser.id", user.getId()));
		List priManagerGradeList = getGeneralDao().getList(dcGrade);
		List gradeList = new ArrayList();
		if(priManagerGradeList != null && !priManagerGradeList.isEmpty()){
			Iterator it = priManagerGradeList.iterator();
			while(it.hasNext()){
				PrPriManagerGrade prGrade = (PrPriManagerGrade)it.next();
				gradeList.add(prGrade.getPeGrade());
			}
			us.setPriGrade(gradeList);
		}
		/**
		 * 20130114添加，用于协会管理员姓名初始化到session中
		 */
		if("3".equals(loginType)) {
			DetachedCriteria pmDc = DetachedCriteria.forClass(PeManager.class).createAlias("ssoUser", "ssoUser");
			pmDc.add(Restrictions.eq("ssoUser.id", user.getId()));
			List pmList = this.getGeneralDao().getList(pmDc);
			if(pmList.size()>0) {
				PeManager pm = (PeManager)pmList.get(0);
				us.setUserName(pm.getTrueName());
			} else {
				us.setUserName(user.getLoginId());
			}
		}
		return us;
	}
	
	/**
	 * 获得用户权限
	 * @param user
	 * @return Map
	 * @author cailei
	 */
	
	public Map<String, PePriority> getUserPriority(SsoUser user){
		return getUserPriority(user.getPePriRole().getId());
	}
	
	/**
	 * 根据角色id获得用户权限
	 * @param roleId
	 * @return Map
	 * @author cailei
	 */
	
	public Map<String, PePriority> getUserPriority(String roleId){
		Map<String, PePriority> userPriority = new HashMap<String, PePriority>();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrPriRole.class);
		dc.createAlias("pePriRole", "pePriRole");
		dc.createAlias("pePriority", "pePriority");
		dc.add(Restrictions.eq("pePriRole.id", roleId)).add(Restrictions.eq("flagIsvalid", "1"));
		List<PrPriRole> list = this.getGeneralDao().getList(dc);
		
		if(list != null && !list.isEmpty()){ //权限不为空则取出权限放入map中;
			
			Iterator<PrPriRole> it = list.iterator();
			while(it.hasNext()){
				PrPriRole prRole = it.next();
				PePriority priority = prRole.getPePriority();
				userPriority.put(priority.getNamespace()+"/"+priority.getAction()+"_"+priority.getMethod()+".action", priority);
				userPriority.put(priority.getNamespace()+"/"+priority.getAction()+"_execute.action", priority);
				if("abstractList".equals(priority.getMethod())) {
					userPriority.put(priority.getNamespace()+"/"+priority.getAction()+"_abstractExcel.action", priority);
				}
			}
		}
		return userPriority;
	}

	/**
	 * 根据用户登陆id取得信息采集人信息
	 * @param loginId
	 * @return PeSitemanager
	 * @author cailei
	 */
	public PeSitemanager getPeSitemanager(String loginId) {
		
		return this.getGeneralDao().getPeSitemanager(loginId);
	}

	/**
	 * 根据用户登陆id取得机构管理员信息
	 * @param loginId
	 * @return PeEnterpriseManager
	 * @author cailei
	 */
	public PeEnterpriseManager getEnterprisemanager(String loginId) {
		// TODO Auto-generated method stub
		return this.getGeneralDao().getEnterprisemanager(loginId);
	}
	
	/**
	 * 根据用户登陆id取得学生信息
	 * @param login_id
	 * @return PeBzzStudent
	 * @author cailei
	 */
	public PeBzzStudent getBzzStudent(String loginId) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.add(Restrictions.eq("regNo", loginId));
		return this.getGeneralDao().getStudentInfo(dc);
	}
	
	/**
	 * 根据用户名，手机号，机构管理员id取得招生库学生信息
	 * @param username
	 * @param mobile
	 * @param batch_id
	 * @param enterprise_id
	 * @author cailei
	 * @return PeBzzRecruit
	 */
	public PeBzzRecruit getBzzRecruitStudent(String username,String mobile,String batch_id,String enterprise_id)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRecruit.class);
		dc.add(Restrictions.eq("name", username));
		dc.add(Restrictions.eq("mobilePhone", mobile));
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.add(Restrictions.or(Restrictions.eq("peEnterprise.peEnterprise.id", enterprise_id),Restrictions.eq("peEnterprise.id", enterprise_id)));
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.add(Restrictions.eq("peBzzBatch.id", batch_id));
		return this.getGeneralDao().getBzzRecruitStudent(dc);
	}
	
	/**
	 * 根据用户名，手机号取得招生库学生信息list数据集合
	 * @param username
	 * @param mobile
	 * @author cailei
	 * @return List
	 */
	public List<PeBzzRecruit> getBzzRecruitStudent(String username,String mobile)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRecruit.class);
		dc.add(Restrictions.eq("name", username));
		dc.add(Restrictions.eq("mobilePhone", mobile));
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		return this.getGeneralDao().getList(dc);
	}

	/**
	 * 根据招生库学生信息取得相应的学生信息
	 * @param peBzzRecruit
	 * @author cailei
	 * @return PeBzzStudent
	 */
	public PeBzzStudent getBzzStudent(PeBzzRecruit peBzzRecruit) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("peBzzRecruit","peBzzRecruit");
		dc.add(Restrictions.eq("peBzzRecruit.id", peBzzRecruit.getId()));
		return this.getGeneralDao().getStudentInfo(dc);
	}
}
