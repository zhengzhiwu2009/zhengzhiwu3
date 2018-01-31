package com.whaty.platform.sso.web.action;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Base64Utils;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.web.action.EntityBaseAction;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.service.SsoUserService;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.RedundanceData;
import com.whaty.servlet.SessionListener;
import com.whaty.util.Base64Util;
import com.whaty.util.SessionCounter;
import com.whaty.util.SingleLogin;

public class SsoLogin extends EntityBaseAction {

	private String passwd;

	private String loginId;

	private String loginType;

	private String authCode;

	private String authCode0;

	private String loginErrMessage;

	private SsoUserService ssoUserService;

	private PeSitemanager peSitemanager;

	private PeEnterpriseManager peEnterprisemanager;

	private PeBzzStudent peBzzStudent;

	private PeBzzRecruit peBzzRecruit;

	// 下列自段用于取回密码
	private String trueName;
	private String cardId;

	// 下列字段用于验证报名信息
	private String stuName;
	private String stuMobile;
	private String stuEnterpriseId;
	private String stuBatchId;
	
	//判断是否从报名课程登录
	private String courseId;

	// 用于onlineCousre登陆的缓存
	private static Map<String, UserSession> userInfoMap;

	public final static String REMEMBER_ME_PASSWORD = "REMEMBER_ME_PASSWORD";
	public final static String REMEMBER_ME_LOGINID = "REMEMBER_ME_LOGINID";

	private String rememberMe;// 是否记住密码
	private String passwdInput;// 是否是输入的密码
	private String logintype;// 判断是从哪登录的

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public SsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(SsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public String execute() {
		return this.login();
	}

	/**
	 * 协会管理员、集体管理员和学生登录校验
	 * 
	 * @return
	 * @author linjie
	 */
	public String login() {
		// 验证码校验
		if (!this.isTrueAuthImg()) {
			logger.error(this.getText("login.error.errorauthocode", this.getAuthCode()));
			this.loginErrMessage = "验证码错误";
			return "back";

		}
		SsoUser ssoUser = null;
		try {
			this.setLoginId(StringUtils.trim(this.getLoginId()));
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		if (ssoUser == null) {
			logger.error(this.getText("login.error.onuser", this.getLoginId()));
			// this.loginErrMessage = "没有这个用户";
			// this.loginErrMessage = "您的登陆信息有误，登陆失败。";
			this.loginErrMessage = "用户名错误，登陆失败。";
			return "back";

		}
		peBzzStudent = getSsoUserService().getBzzStudent(ssoUser.getLoginId());
		if (!"".equals(this.getLoginType()) && this.getLoginType() != null && "0".equals(this.getLoginType())) {// 集体
			if (null != ssoUser.getPePriRole() && "3".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())) {
				this.loginErrMessage = "请从协会用户处登录";
				return "back";
			} else if (null != ssoUser.getPePriRole()
					&& "0".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())
					&& (peBzzStudent != null && (peBzzStudent.getPeEnterprise().getPeEnterprise() == null && !"V".equals(peBzzStudent.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode())) || (peBzzStudent.getPeEnterprise().getPeEnterprise() != null && !"V".equals(peBzzStudent
							.getPeEnterprise().getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode())))) {
				this.loginErrMessage = "请从个人用户处登录";
				return "back";
			} else if (("0".equals(ssoUser.getPePriRole().getId())
					&& ((peBzzStudent.getPeEnterprise().getPeEnterprise() == null 
							&& "V".equals(peBzzStudent.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode())) 
							|| (peBzzStudent.getPeEnterprise().getPeEnterprise() != null 
									&& "V".equals(peBzzStudent.getPeEnterprise().getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode())))) 
							|| "5".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())
							|| "6".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())) {
				this.loginErrMessage = "请从监管用户处登录";
				return "back";
			}
		} else if (!"".equals(this.getLoginType()) && this.getLoginType() != null && "1".equals(this.getLoginType())) {// 协会
			if (("0".equals(ssoUser.getPePriRole().getId()) && ((peBzzStudent.getPeEnterprise().getPeEnterprise() == null 
					&& "V".equals(peBzzStudent.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode())) 
					|| (peBzzStudent.getPeEnterprise().getPeEnterprise() != null 
							&& "V".equals(peBzzStudent.getPeEnterprise().getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode()))))
					|| "5".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())
					|| "6".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())) {
				this.loginErrMessage = "请从监管用户处登录";
				return "back";
			} else if (null == ssoUser.getPePriRole() || !"3".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())) {
				this.loginErrMessage = "请从首页登录";
				return "back";
			}
		} else if (!"".equals(this.getLoginType()) && this.getLoginType() != null && "2".equals(this.getLoginType())) {// 个人
				if ("24".indexOf(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode()) != -1) {
					this.loginErrMessage = "请从集体用户处登录";
					return "back";
				} else if ("3".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())) {
					this.loginErrMessage = "请从协会用户处登录";
					return "back";
				} else if ((peBzzStudent != null && ((peBzzStudent.getPeEnterprise() != null && peBzzStudent.getPeEnterprise().getPeEnterprise() == null 
						&& "V".equals(peBzzStudent.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode())) 
						|| (peBzzStudent.getPeEnterprise() != null && peBzzStudent.getPeEnterprise().getPeEnterprise() != null 
								&& "V".equals(peBzzStudent.getPeEnterprise().getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode()))))
						|| "5".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode()) 
						|| "6".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())) {
					this.loginErrMessage = "请从监管用户处登录";
					return "back";
				}else if(peBzzStudent.getPeEnterprise() == null){
					this.loginErrMessage = "用户身份错误，请联系管理员。";
					return "back";
				}
		} else if (!"".equals(this.getLoginType()) && this.getLoginType() != null && "3".equals(this.getLoginType())) {// 监管
			if ("24".indexOf(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode()) != -1) {
				this.loginErrMessage = "请从集体用户处登录";
				return "back";
			} else if ("3".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())) {
				this.loginErrMessage = "请从协会用户处登录";
				return "back";
			} else if ("0".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())
					&& ((peBzzStudent.getPeEnterprise().getPeEnterprise() == null && !"V".equals(peBzzStudent.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode())) || (peBzzStudent.getPeEnterprise().getPeEnterprise() != null && !"V".equals(peBzzStudent.getPeEnterprise().getPeEnterprise()
							.getEnumConstByFlagEnterpriseType().getCode())))) {
				if("1".equals(ssoUser.getEnumConstByFlagIsvalid().getCode()))
					this.loginErrMessage = "请从个人用户处登录";
				else
					this.loginErrMessage = "您的账号处于无效，无法登陆";
				return "back";
			}
		} else {
			if (null == ssoUser.getPePriRole() || "123".indexOf(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode()) == -1) {
				this.loginErrMessage = "请从个人用户处登录";
				return "back";
			}
		}

		// -------------------------------------------------------------------------
		ActionContext ctx = ActionContext.getContext();

		HttpServletRequest request = null;
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		if (null != this.passwd && !"".equals(this.passwd))
			this.passwd = Base64Util.getFromBASE64(this.passwd);

		// -------------------------------------------------------------------------
		if ("3".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())) {
			this.logintype = "3";
		}

		/** **********************验证码置前********************************* */

		if (!MyUtil.md5(this.getPasswd()).equals(ssoUser.getPasswordBk())) {
			logger.error(this.getText("login.error.errorpassword", this.getLoginId() + ",passwd:" + this.getPasswd()));
			// this.loginErrMessage = "密码错误";
			if (null != ssoUser.getPePriRole() && "0".equals(ssoUser.getPePriRole().getEnumConstByFlagRoleType().getCode())) {

				this.loginErrMessage = "密码错误,请注意区分密码大小写及0（数字零）与O（大写字母）,1(数字1)与I(大写字母)的区分。密码错误请找机构负责人核实";
			} else
				this.loginErrMessage = "密码错误,请注意区分密码大小写及0（数字零）与O（大写字母）,1(数字1)与I(大写字母)的区分。";
			return "back";

		} else {// sso验证通过

			boolean passFlag = false;
			try {
				// passFlag = getSsoUserService().CheckSsoUserByType(ssoUser, getLoginType()); //验证用户身份是否正确
				for (int i = 0; i < 7; i++) {
					passFlag = getSsoUserService().CheckSsoUserByType(ssoUser, i + ""); // 验证用户身份是否正确
					if (passFlag) {
						this.setLoginType(i + "");
						break;
					}
				}
				if ("2".equals(this.getLoginType()) || "4".equals(this.getLoginType()) || "5".equals(this.getLoginType()) || "6".equals(this.getLoginType())) {
					peEnterprisemanager = getSsoUserService().getEnterprisemanager(ssoUser.getLoginId());
					request.getSession().setAttribute("peEnterprisemanager", peEnterprisemanager.getPeEnterprise().getCode());
					request.getSession().setAttribute("peEnterpriseName", peEnterprisemanager.getPeEnterprise().getName());
				}
			} catch (SsoException e) {
				logger.error(e);
				this.loginErrMessage = e.getMessage();
				return "back";
			}

			if (!passFlag) {// 验证失败
				logger.error(this.getText("login.error.erroridentity", this.getLoginId()));
				this.loginErrMessage = "用户身份错误";
				return "back";
			}

			Date lastDate = null;
			try {
				// 设置用户属性
				lastDate = ssoUser.getLastLoginDate();
				ssoUser.setLastLoginDate(new Date());
				ssoUser.setLastLoginIp(request.getRemoteAddr());
				long num = ssoUser.getLoginNum() == null ? 0 : ssoUser.getLoginNum();
				ssoUser.setLoginNum((++num));
				ssoUser = getSsoUserService().save(ssoUser);
			} catch (Exception e) {
				logger.error(e);
				this.loginErrMessage = "用户信息设置失败";
				return "back";
			}

			// 验证通过；存储用户session;
			UserSession userSession = null;
			try {
				// ssoUser.setLastLoginDate(lastDate);
				userSession = getSsoUserService().getUserSession(ssoUser, getLoginType());
				request.getSession().setAttribute("Logintype", getLoginType());
				request.getSession().setAttribute("roleid", userSession.getRoleId());
			} catch (SsoException e) {
				this.loginErrMessage = "用户信息设置失败";
				return "back";
			}
			// if (ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY_BAK) !=
			// null)
			// ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY_BAK);
			// ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY,
			// userSession);

			if (ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY) != null)
				ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY);
			ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession);
			/*
			 * 服务器端监听用户
			 */
			HttpSession sess = request.getSession();
			// sess.setAttribute("userLoginIdId", userSession);
			SessionCounter.Createsession(sess, request.getRemoteAddr());
			sess.setAttribute("sessionListener", new SessionListener());
			if (this.getLoginType().equals("0")) {
				//peBzzStudent = getSsoUserService().getBzzStudent(ssoUser.getLoginId());
				request.getSession().setAttribute("enterpriseName", peBzzStudent.getPeEnterprise().getName());
				userSession.setUserName(peBzzStudent.getTrueName());
				String entype = null;
				if(null != peBzzStudent.getPeEnterprise()){//有机构
					if(null != peBzzStudent.getPeEnterprise().getPeEnterprises()){//一级机构
						if(null != peBzzStudent.getPeEnterprise().getEnumConstByFlagEnterpriseType()){//有机构类别
							entype = peBzzStudent.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode();
						}
					}else{//二级机构
						String sql = "SELECT 1 FROM PE_ENTERPRISE PE2 INNER JOIN PE_ENTERPRISE PE ON PE2.FK_PARENT_ID = PE.ID "
							        + " INNER JOIN ENUM_CONST EC ON PE.ENTYPE = EC.ID"
									+ " WHERE PE2.ID = '" + peBzzStudent.getPeEnterprise().getId() + "' AND EC.CODE = 'V'";
						try{
							List list = getSsoUserService().getBySQL(sql);
							if(null != list && list.size() > 0)//监管机构类别
								entype = "V";
						}catch(Exception e){
							System.out.println("ERROR-学员：" + peBzzStudent.getRegNo() + "机构查询错误！");
						}
					}
				}
				request.getSession().setAttribute("enterpriseType",entype );
				try {
					PlatformFactory factory = PlatformFactory.getInstance();
					// PlatformManage
					// platformManage=factory.createPlatformManage();
					// EntityUser
					// euser=platformManage.getEntityUser(ssoUser.getId(),"student");
					OracleStudent os = new OracleStudent();
					HumanNormalInfo normalInfo = new HumanNormalInfo();
					StudentEduInfo eduInfo = new StudentEduInfo();
					HumanPlatformInfo platformInfo = new HumanPlatformInfo();
					RedundanceData redundance = new RedundanceData();
					os.setId(peBzzStudent.getId());
					os.setName(peBzzStudent.getTrueName());
					normalInfo.setTxt_Reg_No(peBzzStudent.getRegNo()); // 学生学号
					os.setNormalInfo(normalInfo);
					eduInfo.setReg_no(peBzzStudent.getRegNo());
					os.setStudentInfo(eduInfo);
					os.setPlatformInfo(platformInfo);
					os.setRedundace(redundance);
					EntityUser euser = os;
					request.getSession().removeAttribute("eduplatform_user");
					request.getSession().removeAttribute("eduplatform_priv");
					request.getSession().setAttribute("eduplatform_user", euser);
					StudentPriv studentPriv = factory.getStudentPriv(ssoUser.getId());
					request.getSession().setAttribute("eduplatform_priv", studentPriv);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (peBzzStudent != null && (peBzzStudent.getTrueName() == null || peBzzStudent.getTrueName().equals(""))) {
					return "nochecklogin";
				}
			}

			/*
			 * if(ssoUser.getLoginNum()==1&&this.getLoginType().equals("0")){
			 * request.getSession().setAttribute("loginid",ssoUser.getLoginId());
			 * return "firstlogin"; }
			 */
			request.getSession().setAttribute("courseId", courseId);
			SingleLogin singleLogin =new SingleLogin();
			try {
				singleLogin.saveLoginSess(this.getLoginId(),userSession.getRoleId());
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "success";

			// //根据不同类型登陆
			// if(SsoConstant.SSO_MANAGER.equals(getLoginType())){
			// return "manager";
			// }else if(SsoConstant.SSO_SITEMANAGER.equals(getLoginType())){
			// return "sitemanager";
			// }else if(SsoConstant.SSO_TEACHER.equals(getLoginType())){
			// return "teacher";
			// }else if(SsoConstant.SSO_STUDENT.equals(getLoginType())){
			// return "student";
			// }else{
			// return "student";
			// }
		}
	}

	/**
	 * 判断验证码是否正确
	 * 
	 * @return
	 * @author linjie
	 */
	public boolean isTrueAuthImg() {
		ActionContext ctx = ActionContext.getContext();
		boolean isValivd = true;
		String code = "";
		if (!"".equals(this.getLoginType()) && this.getLoginType() != null && ("2".equals(this.getLoginType()) || "3".equals(this.getLoginType()))) {
			code = (String) ctx.getSession().get("authCode0");
			if (!StringUtils.defaultString(this.getAuthCode0()).equals(code)) {
				isValivd = false;
			}
		} else {
			code = (String) ctx.getSession().get("authCode");
			if (!StringUtils.defaultString(this.getAuthCode()).equals(code)) {
				isValivd = false;
			}
		}
		return isValivd;
	}

	public String returnIndex() {
		return "success";
	}

	/**
	 * 暂未用到
	 * 
	 * @return
	 * @author linjie
	 */
	public String staticLogin() {
		UserSession userSession = null;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		if (userInfoMap == null) {
			userInfoMap = initUserMap();
		}

		if ((userSession = userInfoMap.get(this.getLoginId())) != null) {
			if (userSession.getSsoUser() != null && userSession.getSsoUser().getPassword().equals(this.getPasswd())) {
				ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession);
				request.getSession().setAttribute("Logintype", userSession.getUserLoginType());
				request.getSession().setAttribute("roleid", userSession.getRoleId());
				// System.out.println("catch a person");
				return "success";
			} else {
				// System.out.println(userSession.getSsoUser().getPassword());
				// System.out.println(this.getPasswd());
				this.loginErrMessage = "密码错误,请注意区分密码大小写及0（数字零）与O（大写字母）,1(数字1)与I(大写字母)的区分。密码错误请找机构负责人核实。";
				return "LoginError";
			}

		}

		SsoUser ssoUser = null;
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (ssoUser == null) {
			logger.error(this.getText("login.error.onuser", this.getLoginId()));
			// this.loginErrMessage = "没有这个用户";
			this.loginErrMessage = "您的登陆信息有误，登陆失败。";
			return "LoginError";
		}
		if (!this.getPasswd().equals(ssoUser.getPassword())) {
			logger.error(this.getText("login.error.errorpassword", this.getPasswd()));
			// this.loginErrMessage = "密码错误";
			this.loginErrMessage = "密码错误,请注意区分密码大小写及0（数字零）与O（大写字母）,1(数字1)与I(大写字母)的区分。";
			return "LoginError";
		}

		{// sso验证通过

			boolean passFlag = false;
			try {
				// passFlag = getSsoUserService().CheckSsoUserByType(ssoUser,
				// getLoginType()); //验证用户身份是否正确
				for (int i = 0; i < 4; i++) {
					passFlag = getSsoUserService().CheckSsoUserByType(ssoUser, i + ""); // 验证用户身份是否正确
					if (passFlag) {
						this.setLoginType(i + "");
						break;
					}
				}
				if (this.getLoginType().equals("2")) {
					// peSitemanager =
					// getSsoUserService().getPeSitemanager(ssoUser.getLoginId());
					// request.getSession().setAttribute("peSitemanager",
					// peSitemanager.getPeSite().getId());

					peEnterprisemanager = getSsoUserService().getEnterprisemanager(ssoUser.getLoginId());
					request.getSession().setAttribute("peEnterprisemanager", peEnterprisemanager.getPeEnterprise().getCode());
				}
			} catch (SsoException e) {
				logger.error(e);
				this.loginErrMessage = e.getMessage();
				return "LoginError";
			}

			if (!passFlag) {// 验证失败
				logger.error(this.getText("login.error.erroridentity", this.getLoginId()));
				this.loginErrMessage = "用户身份错误";
				return "LoginError";
			}

			Date lastDate = null;
			try {
				// 设置用户属性
				lastDate = ssoUser.getLastLoginDate();
				ssoUser.setLastLoginDate(new Date());
				ssoUser.setLastLoginIp(request.getRemoteAddr());
				long num = ssoUser.getLoginNum() == null ? 0 : ssoUser.getLoginNum();
				ssoUser.setLoginNum((++num));
				ssoUser = getSsoUserService().save(ssoUser);
			} catch (Exception e) {
				logger.error(e);
				this.loginErrMessage = "用户信息设置失败";
				return "LoginError";
			}

			try {
				// ssoUser.setLastLoginDate(lastDate);
				userSession = getSsoUserService().getUserSession(ssoUser, getLoginType());
				request.getSession().setAttribute("Logintype", getLoginType());
				request.getSession().setAttribute("roleid", userSession.getRoleId());
			} catch (SsoException e) {
				this.loginErrMessage = "用户信息设置失败";
				return "LoginError";
			}
			if (ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY_BAK) != null)
				ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY_BAK);
			ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession);

			/*
			 * 服务器端监听用户
			 */
			HttpSession sess = request.getSession();
			// sess.setAttribute("userLoginIdId", userSession);
			SessionCounter.Createsession(sess, request.getRemoteAddr());

			if (this.getLoginType().equals("0")) {
				peBzzStudent = getSsoUserService().getBzzStudent(ssoUser.getLoginId());
				// System.out.println("true_name:>>>>>>>>>>>>>>>"+peBzzStudent.getTrueName());
				if (peBzzStudent != null && (peBzzStudent.getTrueName() == null || peBzzStudent.getTrueName().equals(""))) {
					return "nochecklogin";
				}

			}

			if (ssoUser.getLoginNum() == 1 && this.getLoginType().equals("0")) {
				request.getSession().setAttribute("loginid", ssoUser.getLoginId());
				return "firstlogin";
			}

			userInfoMap.put(ssoUser.getLoginId(), userSession);
			return "success";
		}
	}

	/**
	 * 模拟登录
	 * 
	 * @return
	 */
	public String simulate() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);

		UserSession userSession = null;
		userSession = (UserSession) ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession == null) {
			this.loginErrMessage = "登录超时，请重新登录！";
			return "information";
		}
		if (!userSession.getUserLoginType().equals(SsoConstant.SSO_MANAGER)) {
			this.loginErrMessage = "模拟登录一次只能模拟登录一个人的，请注销或关闭刚模拟登录的窗口。";
			return "information";
		}

		// 将session中的用户信息转存
		ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY_BAK, userSession);

		// 开始登录
		SsoUser ssoUser = null;
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			logger.error(e1);
			this.loginErrMessage = e1.getMessage();
			return "information";
		}

		// 验证通过；存储用户session;
		UserSession userSession2 = null;
		try {
			userSession2 = getSsoUserService().getUserSession(ssoUser, SsoConstant.SSO_STUDENT);
		} catch (SsoException e) {
			this.loginErrMessage = "用户信息设置失败";
			return "information";
		}
		ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession2);

		return "success";
	}

	/**
	 * 注销退出时执行的方法，清除session
	 * 
	 * @return
	 * @author linjie
	 */
	public String close() {
		/*
		 * 服务器端监听用户
		 */
		String str = "stuexit";
		HttpSession session = ServletActionContext.getRequest().getSession();
		// SessionCounter.destroySession(session);
		ActionContext ctx = ActionContext.getContext();
		try {
			UserSession us = (UserSession) ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			if (null != us && "3".equals(us.getUserLoginType())) {
				str = "exit";
			}
			if (this.getLoginErrMessage() != null && this.getLoginErrMessage().equals("clear")) {
				ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY_BAK);
				ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY);
				ctx.getSession().clear();
			} else {
				UserSession userSession2 = (UserSession) ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY_BAK);
				// 将session中的用户信息转存
				if (userSession2 != null) {
					ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY);
					ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession2);
				} else {
					ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY_BAK);
					ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY);
				}
			}
			session.invalidate();
		} catch (Exception e) {
			System.out.println("登陆已超时,无法执行注销操作!");
			return str;
		}
		return str;

	}

	/**
	 * 首页取回密码功能
	 * 
	 * @return
	 */
	public String toPassword() {
		SsoUser ssoUser = null;
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (ssoUser == null) {
			this.loginErrMessage = "没有这个用户!";
			return "pwd";
		}
		boolean passFlag = false;
		try {
			passFlag = getSsoUserService().CheckSsoUserByType(ssoUser, getLoginType()); // 验证用户身份是否正确
		} catch (SsoException e) {
			passFlag = true;
		}

		if (!passFlag) {// 验证失败
			this.loginErrMessage = "用户分类错误!";
			return "pwd";
		}
		if (this.getSsoUserService().checkTrue(ssoUser, this.getLoginType(), this.getTrueName(), this.getCardId())) {
			this.loginErrMessage = "true";
		} else {
			this.loginErrMessage = "姓名或者身份证号错误！";
		}
		return "pwd";
	}

	public String newPassword() {
		SsoUser ssoUser = null;
		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		this.getSsoUserService().saveNewPassword(ssoUser, this.getPasswd());
		this.setLoginErrMessage("success");
		return "pwd";
	}

	/**
	 * Action 用于在线课堂的登陆(未用)
	 * 
	 * @return
	 */
	public String onlineCourseLogin() {

		UserSession userSession = null;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		if (userInfoMap == null) {
			userInfoMap = initUserMap();
		}

		if ((userSession = userInfoMap.get(this.getLoginId())) != null) {
			if (userSession.getSsoUser() != null && userSession.getSsoUser().getPassword().equals(this.getPasswd())) {
				ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession);
				return "ToOnlineCourse";
			} else {
				// System.out.println(userSession.getSsoUser().getPassword());
				// System.out.println(this.getPasswd());
				this.loginErrMessage = "密码错误,请注意区分密码大小写及0（数字零）与O（大写字母）,1(数字1)与I(大写字母)的区分。";
				return "OnlineCourseLogin";
			}

		}
		SsoUser ssoUser = null;

		try {
			ssoUser = this.getSsoUserService().getByLoginId(this.getLoginId());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (ssoUser == null) {
			logger.error(this.getText("login.error.onuser", this.getLoginId()));
			// this.loginErrMessage = "没有这个用户";
			this.loginErrMessage = "账号错误,请检查账号是否使用学员卡上的系统编号登录，及账号位数是否正确。";
			return "OnlineCourseLogin";
		}
		if (!this.getPasswd().equals(ssoUser.getPassword())) {
			logger.error(this.getText("login.error.errorpassword", this.getPasswd()));
			// this.loginErrMessage = "密码错误";
			this.loginErrMessage = "密码错误,请注意区分密码大小写及0（数字零）与O（大写字母）,1(数字1)与I(大写字母)的区分。";
			return "OnlineCourseLogin";
		}

		Date lastDate = null;
		try {
			// 设置用户属性
			lastDate = ssoUser.getLastLoginDate();
			ssoUser.setLastLoginDate(new Date());
			ssoUser.setLastLoginIp(request.getRemoteAddr());
			long num = ssoUser.getLoginNum() == null ? 0 : ssoUser.getLoginNum();
			ssoUser.setLoginNum((++num));
			ssoUser = getSsoUserService().save(ssoUser);
		} catch (Exception e) {
			logger.error(e);
			this.loginErrMessage = "用户信息设置失败";
			return "OnlineCourseLogin";
		}

		// session处理

		try {
			// ssoUser.setLastLoginDate(lastDate);
			userSession = getSsoUserService().getUserSession(ssoUser, getLoginType());
			request.getSession().setAttribute("Logintype", getLoginType());
			request.getSession().setAttribute("roleid", userSession.getRoleId());
		} catch (SsoException e) {
			this.loginErrMessage = "用户信息设置失败";
			return "OnlineCourseLogin";
		}
		if (ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY_BAK) != null)
			ctx.getSession().remove(SsoConstant.SSO_USER_SESSION_KEY_BAK);
		ctx.getSession().put(SsoConstant.SSO_USER_SESSION_KEY, userSession);

		userInfoMap.put(this.getLoginId(), userSession);
		return "ToOnlineCourse";
	}

	/**
	 * Action 用于初始化userMap
	 * 
	 * @return
	 * @author linjie
	 */
	private Map initUserMap() {
		Calendar start = Calendar.getInstance();
		Map map = new HashMap<String, UserSession>();

		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.createCriteria("pePriRole", "pePriRole");
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.add(Restrictions.eq("pePriRole.id", "0"));
		dc.add(Restrictions.eq("enumConstByFlagIsvalid.id", "2"));
		try {
			List<SsoUser> list = this.getSsoUserService().getList(dc);

			for (SsoUser mySsoUser : list) {

				UserSession userSession = new UserSession();
				// session处理
				userSession.setId(mySsoUser.getId());
				userSession.setLoginId(mySsoUser.getLoginId());
				userSession.setRoleId(mySsoUser.getPePriRole().getId());
				userSession.setSsoUser(mySsoUser);
				userSession.setUserLoginType("0");

				map.put(mySsoUser.getLoginId(), userSession);
			}
			Calendar end = Calendar.getInstance();
			System.out.println("initUserInfoMap: select " + list.size() + " records in " + ((end.getTimeInMillis() - start.getTimeInMillis()) / 1000.0) + " sec.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * Action 用于初始化userInfoMap
	 * 
	 * @return
	 * @author linjie
	 */
	public String initUserMapOnline() {
		ActionContext ctx = ActionContext.getContext();

		if (ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY) != null)
			this.userInfoMap = this.initUserMap();
		return "OnlineCourseLogin";
	}

	/**
	 * Action 用于清除userInfoMap
	 * 
	 * @return
	 * @author linjie
	 */
	public String destroyUserMapOnline() {
		ActionContext ctx = ActionContext.getContext();

		if (ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY) != null) {
			this.userInfoMap.clear();
			this.userInfoMap = null;
		}
		return "OnlineCourseLogin";
	}

	public String getLoginErrMessage() {
		return loginErrMessage;
	}

	public void setLoginErrMessage(String loginErrMessage) {
		this.loginErrMessage = loginErrMessage;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public PeSitemanager getPeSitemanager() {
		return peSitemanager;
	}

	public void setPeSitemanager(PeSitemanager peSitemanager) {
		this.peSitemanager = peSitemanager;
	}

	public PeEnterpriseManager getPeEnterprisemanager() {
		return peEnterprisemanager;
	}

	public void setPeEnterprisemanager(PeEnterpriseManager peEnterprisemanager) {
		this.peEnterprisemanager = peEnterprisemanager;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public String getStuEnterpriseId() {
		return stuEnterpriseId;
	}

	public void setStuEnterpriseId(String stuEnterpriseId) {
		this.stuEnterpriseId = stuEnterpriseId;
	}

	public String getStuMobile() {
		return stuMobile;
	}

	public void setStuMobile(String stuMobile) {
		this.stuMobile = stuMobile;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	/**
	 * action 当用户首次登陆时，核对报名信息与登陆的学员信息是否一致
	 * 
	 * @return
	 * @author linjie
	 */
	public String checkRecruitInfo() {

		List<PeBzzRecruit> recruitList = this.getSsoUserService().getBzzRecruitStudent(this.getStuName(), this.getStuMobile());
		int existInfos = 0;
		int errorInfos = 0;
		if (recruitList != null && recruitList.size() != 0) {
			for (PeBzzRecruit recruit : recruitList) {
				if (this.getSsoUserService().getBzzStudent(recruit) != null) {
					existInfos++;
					// return "info_exist";
				} else {
					UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
					String loginId = userSession.getLoginId();

					PeBzzStudent stu = this.getSsoUserService().getBzzStudent(loginId);

					String stuEntId = stu.getPeEnterprise().getId();
					String recruitEntId = recruit.getPeEnterprise().getId();

					if (stuEntId.equals(recruitEntId)) {
						this.setPeBzzRecruit(recruit);
						return "info_ok";
					} else {
						errorInfos++;
						// return "info_error";
					}
				}
			}

		} else
			return "info_error";

		if (existInfos == recruitList.size()) {
			return "info_exist";
		} else if (errorInfos != 0) {
			return "info_error";
		} else {
			return "info_error";
		}
	}

	public String getStuBatchId() {
		return stuBatchId;
	}

	public void setStuBatchId(String stuBatchId) {
		this.stuBatchId = stuBatchId;
	}

	public PeBzzRecruit getPeBzzRecruit() {
		return peBzzRecruit;
	}

	public void setPeBzzRecruit(PeBzzRecruit peBzzRecruit) {
		this.peBzzRecruit = peBzzRecruit;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getPasswdInput() {
		return passwdInput;
	}

	public void setPasswdInput(String passwdInput) {
		this.passwdInput = passwdInput;
	}

	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public String getAuthCode0() {
		return authCode0;
	}

	public void setAuthCode0(String authCode0) {
		this.authCode0 = authCode0;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
