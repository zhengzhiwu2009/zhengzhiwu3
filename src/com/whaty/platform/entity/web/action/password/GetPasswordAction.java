package com.whaty.platform.entity.web.action.password;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.util.SendMailUtil;
//import com.whaty.framework.ssh.exception.GeneralException;
//import com.whaty.ucenter.user.bean.UserDto;
//import com.whaty.ucenter.user.bean.UserException;
//import com.whaty.ucenter.user.webservice.UserWebService;
import com.whaty.platform.entity.web.util.UUIDGenerator;
import com.whaty.platform.util.Const;


public class GetPasswordAction extends MyBaseAction{
	private String email;
	private String errorEmailMessage;
	private String message;
	private String validateCode;
	private String errorValidateCodeMessage;
	private String cardId;
	private String verifystr;
	private String password;
	private String repassword;
	private String ssoId;
	private String loginId;
	private String errorLoginIdMessage;
	
	//用户信息
	private PeBzzStudent peBzzStudent;	//学员信息
	private PeManager peManager;	//总管理员
	private PeEnterpriseManager peEnterpriseManager;	//企业管理员
	
//	private UserWebService userWebService; //TODO:请考虑用spring注入
	private GeneralService generalService;
	
	
	/**
	 * 找回密码页面
	 * @return
	 */
	public String identify() {
		return "identify";
	}

	/**
	 * 验证邮箱是否可用
	 * @return
	 */
	public String validateEmail() {
		String suc = "0";
		if (isCasUserEmail()) {
			suc = "1";
		}
		return ajax(suc, "text/html");
	}

	/**
	 * 发送邮件
	 * @return
	 */
	public String sendEmail() {
		
		//校验登录账号是否正确
		if (StringUtils.isBlank(loginId)) {
			this.setErrorLoginIdMessage("请输入登录账号");
			return "identify";
		}
		
		//检验邮箱格式是否正确
		if (StringUtils.isBlank(email)) {
			this.setErrorEmailMessage("请输入正确的注册邮箱");
			return "identify";
		}
		
		//检验验证码是否正确
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String code = (String) ctx.getSession().get("authCode");
		if (!this.getValidateCode().equals(code)) {
			this.errorValidateCodeMessage = "验证码错误";
			return "identify";
		}
		
		boolean isExistUser = this.isExistUserByLoginId(loginId);
		if(isExistUser){	//登录账号存在
			
			String userEmail = "";
			String passEmail = "";
			if(null != this.peBzzStudent && !"".equals(this.peBzzStudent)){
				userEmail = peBzzStudent.getEmail();
				if("是".equals(peBzzStudent.getEnumConstByIsEmployee().getName()) 
						&& peBzzStudent.getPassEmail() != null 
						&& !"".equals(peBzzStudent.getPassEmail())) {
					passEmail = peBzzStudent.getPassEmail();
				}
			}else if(null != this.peManager && !"".equals(this.peManager)){
				userEmail = this.peManager.getEmail();
			}else if(null != this.peEnterpriseManager && !"".equals(this.peEnterpriseManager)){
				userEmail = this.peEnterpriseManager.getEmail();
			}
			if(this.email.equals(userEmail) || this.email.equals(passEmail)){	//校验邮箱
				doSendEmail();
			}else{
				this.setErrorEmailMessage("该邮箱未在平台注册");
				return "identify";
			}
		}else{	//登录账号不存在
			this.setErrorLoginIdMessage("您输入的账号不存在");
			return "identify";
		}
		/*boolean isExistEmail = this.isExistEmail(email);
		if(isExistEmail){
			doSendEmail();
		}else{
			this.setErrorEmailMessage("该邮箱未在平台注册");
			return "identify";
		}*/
		return "sendEmail";
	}
	
	
	/**
	 * 重发发送邮件
	 * @return
	 */
	public String resendEmail() {
		String sql = "select id,create_date,login_id,isvalid,user_id from verifystr t where t.id = '" + verifystr + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql); 
//			List list = new ArrayList(); //假数据，请执行上面的SQL
			if (list != null && list.size() > 0) {
				Object[] obj = (Object[]) list.get(0);
				String loginId = obj[2].toString();
				String userId = obj[4].toString();
				boolean isExistUser = this.isExistUser(userId);
				if(isExistUser){
					doSendEmail();
				}else{
					this.setErrorEmailMessage("该邮箱未在平台注册");
					return "invalid";
				}
			} else {
				return "invalid";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "invalid";
		}
		return "resendEmail";
	}
	
	/**
	 * 发送邮件
	 */
	public void doSendEmail(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		String uuid = UUIDGenerator.getUUID();
		verifystr = uuid;
		String username = "";
		String email = "";
		String userId = "";
		String loginId = "";
		if(null != this.peBzzStudent && !"".equals(this.peBzzStudent)){	//为学员
			username = this.peBzzStudent.getTrueName();
			if("是".equals(peBzzStudent.getEnumConstByIsEmployee().getName()) 
					&& peBzzStudent.getPassEmail() != null && !"".equals(peBzzStudent.getPassEmail()) 
					&& this.email.equals(peBzzStudent.getPassEmail())) {
				email = peBzzStudent.getPassEmail();
			} else {
				email = peBzzStudent.getEmail();
			}
			userId = this.peBzzStudent.getId();
			loginId = this.peBzzStudent.getRegNo();
		}else if(null != this.peManager && !"".equals(this.peManager)){	//为总站管理员
			username = this.peManager.getName();
			email = this.peManager.getEmail();
			userId = this.peManager.getId();
			loginId = this.peManager.getLoginId();
		}else if(null != this.peEnterpriseManager && !"".equals(this.peEnterpriseManager)){
			username = this.peEnterpriseManager.getName();
			email = this.peEnterpriseManager.getEmail();
			userId = this.peEnterpriseManager.getId();
			loginId = this.peEnterpriseManager.getLoginId();
		}
		String sql = "insert into verifystr(id,create_date,login_id,isvalid,user_id) values('" + verifystr + "',to_date('" + sdf.format(dt) + "','yyyy-mm-dd hh24:mi:ss'),'" + loginId + "','1','"+userId+"')";
		try {
			this.getGeneralService().executeBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String endDate = sdf.format(DateUtils.addDays(dt, 1));
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		
		StringBuffer sb = new StringBuffer("");
		sb.append("<p>亲爱的用户" + username + "：您好！</p>");
		sb.append("<div>");
		sb.append("<p>	   您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了一个新的密码。</p>");
		sb.append("<p>假如这不是您本人所申请, 请不用理会这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。</p>");
		sb.append("<p>要使用新的密码, 请使用以下链接启用密码。</p>");
		sb.append("<a href='" + basePath + "password/getPsw_resetpassword.action?verifystr=" + verifystr + "' target='_blank'>");
		sb.append(basePath + "password/getPsw_resetpassword.action?verifystr=" + verifystr + "</a><br/>");
		sb.append("(如果无法点击该URL链接地址，请将它复制并粘帖到浏览器的地址输入框，然后单击回车即可。该链接使用后将立即失效。)");
		sb.append("<div style='font-weight:bold;color:red;'>注意:请您在收到邮件24小时内(" + endDate + "前)使用，否则该链接将会失效。</div>");
		sb.append("</div>");
		String content = sb.toString();
		/*String domain = ServletActionContext.getRequest().getServerName();
		String siteSql = "select t.name,t.TITLE,t.WEB_DOMAIN from pe_site t where t.WEB_DOMAIN = '" + domain + "'";
		String siteName = "";
		
		//TODO:请执行这个操作
		List siteList = this.getGeneralService().getGeneralDao().getBySQL(siteSql);
		if (siteList != null && siteList.size() > 0) {
			siteName = ((Object[]) siteList.get(0))[0].toString();
		}*/
//		email = userDto.getEmail();
		String siteName = "中国证券业协会远程培训平台";
		
		SendEmailThread sendEmailThread = new SendEmailThread(siteName, email , "找回密码邮件（系统自动邮件,请勿答复）", content);
		sendEmailThread.start();
	}
	/**
	 * 发送邮件
	 */
	/*public void doSendEmail(UserDto userDto){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		String uuid = UUIDGenerator.getUUID();
		verifystr = uuid;
		String sql = "insert into verifystr(id,create_date,login_id,isvalid) values('" + verifystr + "','" + sdf.format(dt) + "','" + userDto.getUsername() + "','1')";
		
		//this.getGeneralService().getGeneralDao().executeBySQL(sql);  //TODO: 请执行这个操作
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String endDate = sdf.format(DateUtils.addDays(dt, 1));
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		
		StringBuffer sb = new StringBuffer("");
		sb.append("<p>亲爱的用户" + userDto.getUsername() + "：您好！</p>");
		sb.append("<div>");
		sb.append("<p>	   您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了一个新的密码。</p>");
		sb.append("<p>假如这不是您本人所申请, 请不用理会这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。</p>");
		sb.append("<p>要使用新的密码, 请使用以下链接启用密码。</p>");
		sb.append("<a href='" + basePath + "password/getPsw_resetpassword.action?verifystr=" + verifystr + "' target='_blank'>");
		sb.append(basePath + "password/getPsw_resetpassword.action?verifystr=" + verifystr + "</a><br/>");
		sb.append("(如果无法点击该URL链接地址，请将它复制并粘帖到浏览器的地址输入框，然后单击回车即可。该链接使用后将立即失效。)");
		sb.append("<div style='font-weight:bold;color:red;'>注意:请您在收到邮件24小时内(" + endDate + "前)使用，否则该链接将会失效。</div>");
		sb.append("</div>");
		String content = sb.toString();
		String domain = ServletActionContext.getRequest().getServerName();
		String siteSql = "select t.name,t.TITLE,t.WEB_DOMAIN from pe_site t where t.WEB_DOMAIN = '" + domain + "'";
		String siteName = "";
		
		//TODO:请执行这个操作
		List siteList = this.getGeneralService().getGeneralDao().getBySQL(siteSql);
		if (siteList != null && siteList.size() > 0) {
			siteName = ((Object[]) siteList.get(0))[0].toString();
		}
		email = userDto.getEmail();
		
		
		SendEmailThread sendEmailThread = new SendEmailThread(siteName, email , "找回密码邮件（系统自动邮件,请勿答复）", content);
		sendEmailThread.start();
	}*/
	
	
	public String sendEmailSuccess() {
		return "sendEmailSuccess";
	}
	
	public String resendEmailSuccess() {
		return "resendEmailSuccess";
	}

	/**
	 * 重设密码
	 * @return
	 */
	public String resetpassword() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		if (StringUtils.isNotBlank(verifystr)) {
			String sql = "select id,create_date,login_id,isvalid from verifystr t where t.id = '" + verifystr + "'";
			try {
				List list = this.getGeneralService().getBySQL(sql); //TODO:请执行这个操作
//				List list = new ArrayList();
				if (list != null && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					Date createDate = (Date) obj[1];
					String isvalid = obj[3].toString();
					Date endDate = DateUtils.addDays(createDate, 1);

					if (endDate.after(now) && "1".equals(isvalid)) {
						return "resetpassword";
					} else {
						return "invalid";
					}
				} else {
					return "invalid";
				}
			//} catch (EntityException e) {
			} catch (Exception e) {
				e.printStackTrace();
				return "invalid";
			}
		} else {
			return "invalid";
		}
	}

	/**
	 * 设置密码
	 * @return
	 */
	public String modifypassword() {

		if (StringUtils.isBlank(password)) {
			this.setMessage("0");
			return "resetpassword";
		}

		Pattern p = Pattern.compile("^[A-Za-z0-9_-]+$");
		Matcher m = p.matcher(password);
		if (!m.matches()) {
			this.setMessage("1");
			return "resetpassword";
		}

		if (password.length() < 6 || password.length() > 16) {
			this.setMessage("2");
			return "resetpassword";
		}

		if (StringUtils.isBlank(repassword)) {
			this.setMessage("3");
			return "resetpassword";
		}

		if (!password.equals(repassword)) {
			this.setMessage("4");
			return "resetpassword";
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		if (StringUtils.isNotBlank(verifystr)) {
			String sql = "select id,create_date,login_id,isvalid from verifystr t where t.id = '" + verifystr + "'";
			try {
				List list = this.getGeneralService().getBySQL(sql);
//				List list = new ArrayList();
				if (list != null && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					Date createDate = (Date) obj[1];
					String loginId = (String)obj[2];
					//this.setSsoId(obj[2].toString());
					String isvalid = obj[3].toString();
					Date endDate = DateUtils.addDays(createDate, 1);

					if (endDate.after(now) && "1".equals(isvalid)) {
						try {
							String sql_sso_user = "update sso_user set password='此字段弃用',password_bk='"+MyUtil.md5(password)+"',password_md5='"+MyUtil.md5(Const.defaultPwd)+"' where login_id='"+loginId+"'";
							this.getGeneralService().executeBySQL(sql_sso_user);
						} catch (Exception e) {
							e.printStackTrace();
							return "modifyError";
						}
						this.getGeneralService().executeBySQL("update verifystr set isvalid = '0' where id = '" + verifystr + "'");
						return "modifysuccess";
					} else {
						return "invalid";
					}
				} else {
					return "invalid";
				}
			//} catch (EntityException e) {
			} catch (Exception e) {
				e.printStackTrace();
				return "invalid";
			}
		} else {
			return "invalid";
		}
	}

	public String showsuccess() {
		return "showsuccess";
	}

	// AJAX输出
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/**
	 * 验证是否是有效邮箱
	 * @return
	 */
	public boolean isCasUserEmail() {
		boolean flag = false;
		if (StringUtils.isNotBlank(email)) {
			try {
				//flag = userWebService.verifyEmail(email); //TODO:  去CAS或者本地数据库验证邮箱是否被使用了
				DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class).createAlias("ssoUser", "ssoUser")
					.add(Restrictions.eq("ssoUser.loginId", StringUtils.defaultIfBlank(this.getLoginId(), "")))
					.add(Restrictions.eq("email", email));
				System.out.println("this.getLoginId():"+this.getLoginId());
				System.out.println("email:"+email);
				List list = this.getGeneralService().getList(dc);
				if(list!=null&&list.size()>0) {
					flag = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}

	
	//*********setter and getter********************//
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getErrorEmailMessage() {
		return errorEmailMessage;
	}

	public void setErrorEmailMessage(String errorEmailMessage) {
		this.errorEmailMessage = errorEmailMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getErrorValidateCodeMessage() {
		return errorValidateCodeMessage;
	}

	public void setErrorValidateCodeMessage(String errorValidateCodeMessage) {
		this.errorValidateCodeMessage = errorValidateCodeMessage;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getVerifystr() {
		return verifystr;
	}

	public void setVerifystr(String verifystr) {
		this.verifystr = verifystr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}
	
	
	/**
	 * 为了减少用户等待发邮件的时间
	 * @author Administrator
	 *
	 */
	class SendEmailThread extends Thread{
		
		private String siteName;
		private String toEmail;
		private String title;
		private String content;
		
		public SendEmailThread(String siteName, String email, String title, String content) {
			this.siteName = siteName;
			this.toEmail = email;
			this.title = title;
			this.content = content;
		}

		@Override
		public void run() {
			SendMailUtil sendMail = new SendMailUtil();
			sendMail.send(siteName, toEmail , title, content);
		}
		
	}


	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public PeManager getPeManager() {
		return peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	public PeEnterpriseManager getPeEnterpriseManager() {
		return peEnterpriseManager;
	}

	public void setPeEnterpriseManager(PeEnterpriseManager peEnterpriseManager) {
		this.peEnterpriseManager = peEnterpriseManager;
	}
	
	/**
	 * 校验邮箱地址是否存在，如果存在的话一并获取用户的信息
	 * @param email
	 * @return
	 */
	public boolean isExistEmail(String email){
		boolean result = false;
		DetachedCriteria dc = null;
		List list = new ArrayList();
		try {
			//判断是否为学员
			dc = DetachedCriteria.forClass(PeBzzStudent.class);
			dc.add(Restrictions.eq("email", email));
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				this.peBzzStudent = (PeBzzStudent) list.get(0);
				result = true;
				return result;
			}
			//判断是否为总站管理员
			dc = DetachedCriteria.forClass(PeManager.class);
			dc.add(Restrictions.eq("email", email));
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				this.peManager = (PeManager) list.get(0);
				result = true;
				return result;
			}
			//判断是否为分站管理员
			dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.add(Restrictions.eq("email", email));
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				this.peEnterpriseManager = (PeEnterpriseManager) list.get(0);
				result = true;
				return result;
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据id获取用户信息
	 * @param userId
	 * @return
	 */
	public boolean isExistUser(String userId){
		boolean result = false;
		DetachedCriteria dc = null;
		List list = new ArrayList();
		try {
			//判断是否为学员
			dc = DetachedCriteria.forClass(PeBzzStudent.class);
			dc.add(Restrictions.eq("id", userId));
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				this.peBzzStudent = (PeBzzStudent) list.get(0);
				result = true;
				return result;
			}
			//判断是否为总站管理员
			dc = DetachedCriteria.forClass(PeManager.class);
			dc.add(Restrictions.eq("id", userId));
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				this.peManager = (PeManager) list.get(0);
				result = true;
				return result;
			}
			//判断是否为分站管理员
			dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.add(Restrictions.eq("id", userId));
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				this.peEnterpriseManager = (PeEnterpriseManager) list.get(0);
				result = true;
				return result;
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean isExistUserByLoginId(String loginId){
		boolean result = false;
		DetachedCriteria dc = null;
		List list = new ArrayList();
		try {
			//判断是否为学员
			dc = DetachedCriteria.forClass(PeBzzStudent.class);
			dc.add(Restrictions.eq("regNo", loginId));
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				this.peBzzStudent = (PeBzzStudent) list.get(0);
				result = true;
				return result;
			}
			//判断是否为总站管理员
			dc = DetachedCriteria.forClass(PeManager.class);
			dc.add(Restrictions.eq("loginId", loginId));
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				this.peManager = (PeManager) list.get(0);
				result = true;
				return result;
			}
			//判断是否为分站管理员
			dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.add(Restrictions.eq("loginId", loginId));
			list = this.getGeneralService().getList(dc);
			if(null != list && list.size() > 0){
				this.peEnterpriseManager = (PeEnterpriseManager) list.get(0);
				result = true;
				return result;
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getErrorLoginIdMessage() {
		return errorLoginIdMessage;
	}

	public void setErrorLoginIdMessage(String errorLoginIdMessage) {
		this.errorLoginIdMessage = errorLoginIdMessage;
	}
}
