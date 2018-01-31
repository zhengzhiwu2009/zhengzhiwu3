package com.whaty.platform.sso.service;

import java.util.List;

import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.web.servlet.UserSession;

public interface SsoUserService extends GeneralService<SsoUser>{
	/**
	 * 根据登陆类型以及用户信息检测登陆账号是否可用
	 * @param ssoUser
	 * @param loginType
	 * @author shangbingcang
	 * @throws SsoException
	 */
	public boolean CheckSsoUserByType(SsoUser ssoUser, String loginType) throws SsoException;
	
	/**
	 * 根据用户id取得相应信息
	 * @param loginId
	 * @return SsoUser
	 * @author shangbingcang
	 * @throws EntityException
	 */
	public SsoUser getByLoginId(String loginId) throws EntityException;
	
	/**
	 * 根据登陆类型，以及用户信息取得相应的用户的userSession信息
	 * @param ssoUser
	 * @param loginType
	 * @return UserSession
	 * @author shangbingcang
	 * @throws SsoException
	 */
	public UserSession getUserSession(SsoUser ssoUser, String loginType) throws SsoException;
	
	/**
	 * 首页取回密码功能验证输入是否正确
	 * @param ssoUser
	 * @param loginType
	 * @param trueName 姓名
	 * @param cardId身份证号
	 * @author shangbingcang
	 */
	public boolean checkTrue(SsoUser ssoUser, String loginType ,String trueName ,String cardId);
	
	/**
	 * 首页取回密码功能设置新密码
	 * @param passwd
	 * @return SsoUser
	 * @author shangbingcang
	 */
	public void saveNewPassword(SsoUser ssoUser,String passwd);
    
	/**
	 * 根据用户登陆id取得信息采集人信息
	 * @param loginId
	 * @return PeSitemanager
	 * @author shangbingcang
	 */
	public PeSitemanager getPeSitemanager(String loginId);
	
	/**
	 * 根据用户登陆id取得机构管理员信息
	 * @param loginId
	 * @return PeEnterpriseManager
	 * @author shangbingcang
	 */
	public PeEnterpriseManager getEnterprisemanager(String loginId);
	
	/**
	 * 根据用户登陆id取得学生信息
	 * @param login_id
	 * @return PeBzzStudent
	 * @author shangbingcang
	 */
	public PeBzzStudent getBzzStudent(String login_id);
	
	/**
	 * 根据用户名，手机号，机构管理员id取得招生库学生信息
	 * @param username
	 * @param mobile
	 * @param batch_id
	 * @param enterprise_id
	 * @author shangbingcang
	 * @return PeBzzRecruit
	 */
	public PeBzzRecruit getBzzRecruitStudent(String username,String mobile,String batch_id,String enterprise_id);
	
	/**
	 * 根据用户名，手机号取得招生库学生信息list数据集合
	 * @param username
	 * @param mobile
	 * @author shangbingcang
	 * @return List
	 */
	public List<PeBzzRecruit> getBzzRecruitStudent(String username,String mobile);
	
//	public PeBzzRecruit getBzzRecruitStudent(String username,String mobile);
	
	/**
	 * 根据招生库学生信息取得相应的学生信息
	 * @param peBzzRecruit
	 * @author shangbingcang
	 * @return PeBzzStudent
	 */
	public PeBzzStudent getBzzStudent(PeBzzRecruit peBzzRecruit);
}
