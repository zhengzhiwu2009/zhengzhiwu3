package com.whaty.products.webtrn.password;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.omg.CORBA.UserException;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.util.UUIDGenerator;
import com.whaty.platform.sms.SmsSendThread;
import com.whaty.util.EncryptUtil;

public class GetPasswordAction extends MyBaseAction {

	private static final int MAX_VERIFY_COUNT = 3;
	
	private SsoUser user;
	private String email;
	private String safeEmail;
	private String verifyType;
	private String messageCode;
	private String errorEmailMessage;
	private String message;
	private String validateCode;
	private String errorValidateCodeMessage;
	private String cardId;
	private String verifystr;
	private String password;
	private String repassword;
	private String ssoId;
	private String cellphone;
	private String safeCellPhone;
	private String idcard;
	private String loginId;
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SsoUser getUser() {
		return user;
	}

	public void setUser(SsoUser user) {
		this.user = user;
	}

	public String password() {
		return "password";
	}

	public String editpw() {

		String deletesql = "DELETE t.* from verifystr t where id in ( (select dt.id from (SELECT id,HOUR(timediff(v.create_date,NOW())) as d from verifystr v ) dt where d <0 or d>24))";
		this.getGeneralService().getGeneralDao().executeBySQL(deletesql);
		String querySql = "select t.user_id from verifystr t where id = '" + verifystr + "'";
		try {
			List<String> ls = this.getGeneralService().getBySQL(querySql);
			if (ls.size() == 0) {
				return "index";
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "editpw";

	}

	public String subpw() {

		String deletesql = "DELETE t.* from verifystr t where id in ( (select dt.id from (SELECT id,HOUR(timediff(v.create_date,NOW())) as d from verifystr v ) dt where d <0 or d>24))";
		this.getGeneralService().getGeneralDao().executeBySQL(deletesql);
		String querySql = "select t.user_id from verifystr t where id = '" + verifystr + "'";
		try {
			List<String> ls = this.getGeneralService().getBySQL(querySql);
			if (ls.size() == 0) {
				this.setMessage("设置失败！");
				return "editpw";
			}
			String hql = "from SsoUser user where id='" + ls.get(0) + "'";
			List list = new ArrayList();
			try {
				list = this.getGeneralService().getByHQL(hql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//查询与页面输入相同的用户

			//获取此用户的信息
			user = (SsoUser) list.get(0);
			//user.setPassword(password);
			user.setPassword(EncryptUtil.md5(password));//保存加密后的密码
			this.getGeneralService().save(user);
			String del = "DELETE t.* from verifystr t where id = '" + verifystr + "'";
			this.getGeneralService().getGeneralDao().executeBySQL(del);
		} catch (EntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "finished";

	}

	public String validateInfo() {
		String trurLoginId = null;
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("security");
		if (StringUtils.isBlank(validateCode) || !validateCode.equalsIgnoreCase(code)) {
			this.setErrorValidateCodeMessage("验证码输入不正确");
			return "infoinput";
		}
		String ssoUserId = null;
		if(!StringUtils.isBlank(this.getLoginId())&&this.getLoginId().trim().length()>0) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class).createAlias("ssoUser", "ssoUser");
			dc.add(Restrictions.or(Restrictions.eq("ssoUser.loginId", this.getLoginId()),Restrictions.eq("ssoUser.alias", this.getLoginId())));
			if(!StringUtils.isBlank(this.getName())&&this.getName().trim().length()>0) {
				dc.add(Restrictions.eq("trueName", this.getName()));
			}
			if(!StringUtils.isBlank(this.getEmail())&&this.getEmail().trim().length()>0) {
				dc.add(Restrictions.eq("email", this.getEmail()));
			}
			if(!StringUtils.isBlank(this.getCellphone())&&this.getCellphone().trim().length()>0) {
				dc.add(Restrictions.eq("mobile", this.getCellphone()));
			}
			if(!StringUtils.isBlank(this.getIdcard())&&this.getIdcard().trim().length()>0) {
				dc.add(Restrictions.eq("cardNo", this.getIdcard()));
			}
			try {
				List<PeBzzStudent> list = this.getGeneralService().getList(dc);
				if(list!=null&&list.size()==1) {
					PeBzzStudent pt = list.get(0);
					ssoUserId = pt.getSsoUser().getId();
					System.out.println(pt.getSsoUser().getLoginId());
					trurLoginId= pt.getSsoUser().getLoginId();
				} else if(list.size()==0) {
					this.setMessage("找不到匹配的用户");
					return "infoinput";
				} else {
					this.setMessage("找到多个匹配用户，请输入更详细的用户信息");
					return "infoinput";
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else {
			this.setMessage("用户名和姓名都不能为空");
			return "infoinput";
		}
		String uuid = UUIDGenerator.getUUID();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		String sql = "insert into verifystr(id,create_date,login_id,isvalid) values('" + uuid + "','" + sdf.format(dt) + "','" + trurLoginId + "','1')";
		this.getGeneralService().getGeneralDao().executeBySQL(sql);
		this.setVerifystr(uuid);
		return "resetpassword";
	}
	
	public String getpassSubmit() {
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("security");
		if (!validateCode.equals(code)) {
			this.setMessage("验证码不正确");
			return "passport";
		}
		String name = user.getLoginId();
		String hql = "from SsoUser user where user.loginId='" + name + "' ";
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getByHQL(hql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		//查询与页面输入相同的用户

		//获取此用户的信息
		user = (SsoUser) list.get(0);
		String tempCardId = null;

		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class).add(Restrictions.eq("ssoUser.id", user.getId()));
		try {
			List<PeBzzStudent> plist = this.getGeneralService().getList(dc);
			if (plist.size() == 0) {
				dc = DetachedCriteria.forClass(PeManager.class).add(Restrictions.eq("ssoUser.id", user.getId()));
				List<PeManager> plist2 = this.getGeneralService().getList(dc);
				if (plist2.size() > 0) {
					email = plist2.get(0).getEmail();
					tempCardId = plist2.get(0).getIdCard();
				} else {
					this.setMessage("用户信息没有找到！");
					return "passport";
				}
			} else {
				email = plist.get(0).getEmail();
				tempCardId = plist.get(0).getCardNo();
			}

			if (tempCardId == null) {
				this.setMessage("用户" + user.getLoginId() + "身份证号信息没有找到！");
				return "passport";
			}
			if (!cardId.equals(tempCardId)) {
				this.setMessage("身份证号不正确！");
				return "passport";
			}
			if (email == null) {
				this.setMessage("邮箱信息不存在！");
				return "passport";
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		try {
			this.getGeneralService().save(user);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String uuid = UUIDGenerator.getUUID();
		HttpServletRequest request = ServletActionContext.getRequest();
		Date dt = new Date();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		SendMailUtil sendMail = new SendMailUtil();
		String content = "亲爱的" + user.getLoginId() + ":<div>您好！</div><div> 您在" + sdf.format(dt) + "提交找回密码请求，请点击下面的链接修改密码。</div><div>为了保障您帐号的安全性，该链接有效期为24小时，并且在您验证过一次才失效！</div><div><a href='"
				+ basePath + "password/getPsw_editpw.action?verifystr=" + uuid + "' >" + basePath + "password/getPsw_editpw.action?verifystr=" + uuid + "</a></div>(如果您无法点击这个链接，请将此链接复制到浏览器地址栏后访问)";
		sendMail.send(email, content);
		String sql = "insert into verifystr(id,create_date,user_id) values('" + uuid + "','" + sdf.format(dt) + "','" + user.getId() + "')";
		this.getGeneralService().getGeneralDao().executeBySQL(sql);

		return SUCCESS;
	}

	public String getpass() {
		String domain = ServletActionContext.getRequest().getServerName();
		String name = user.getLoginId();
		String hql = "from SsoUser user where user.loginId='" + name + "'";
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getByHQL(hql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		//查询与页面输入相同的用户

		if (list == null || list.size() == 0) {
			this.setMessage("用户名不正确！");
			return "input";
		}
		return "passport";

	}

	/**
	 * 找回密码页面
	 * @return
	 */
	public String identify() {
		return "identify";
	}

	/**
	 * 找回密码页面
	 * @return
	 */
	public String input() {
		return "infoinput";
	}
	
	/**
	 * 根据用户账号绑定信息的情况，展现用户找回密码的方式。
	 */
	public String getFindPswType(){
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("security");
		if (StringUtils.isBlank(validateCode) || !validateCode.equalsIgnoreCase(code)) {
			this.setErrorValidateCodeMessage("验证码输入不正确");
			return "findPassword";
		}
		try {
//			this.setCellphone(userDto.getMobile());
			this.setEmail("huze@whaty.com");
		} catch (Exception e) {
			this.setErrorEmailMessage("用户不存在");
			e.printStackTrace();
			return "findPassword";
		}
		return "getFindPswType";
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
		UserDto userDto = new UserDto();
		userDto.setUsername(loginId);
		userDto.setEmail(email);
		doSendEmail(userDto);
		return "sendEmail";
	}
	
	/**
	 * 重新发送邮件
	 * @return
	 */
	public String resendEmail() {
		String sql = "select id,create_date,login_id,isvalid,verify_count from verifystr t where t.id = '" + verifystr + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0) {
				Object[] obj = (Object[]) list.get(0);
				int verifyCount = (null==obj[4]?0:Integer.valueOf(obj[4].toString()));
				if(verifyCount > MAX_VERIFY_COUNT){
					return  "refuseOperate";
				}
				String loginId = obj[2].toString();
				UserDto userDto = new UserDto();
				doSendEmail(userDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "resendEmail";
		}
		return "resendEmail";
	}
	
	/**
	 * 发送邮件
	 */
	public void doSendEmail(UserDto userDto){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		String uuid = UUIDGenerator.getUUID();
		verifystr = uuid;
		String sql = "insert into verifystr(id,create_date,login_id,isvalid,verify_type,verify_count) values('" + verifystr + "','" + sdf.format(dt) + "','" + userDto.getUsername() + "','1','0','0')";
		this.getGeneralService().getGeneralDao().executeBySQL(sql);
		
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
		List siteList = this.getGeneralService().getGeneralDao().getBySQL(siteSql);
		if (siteList != null && siteList.size() > 0) {
			siteName = ((Object[]) siteList.get(0))[0].toString();
		}
		email = userDto.getEmail();
		
		
		SendEmailThread sendEmailThread = new SendEmailThread(siteName, email , "找回密码邮件（系统自动邮件,请勿答复）", content);
		sendEmailThread.start();
		//sendMail.send(siteName, email , "找回密码邮件（系统自动邮件,请勿答复）", content);
	}
	
	
	public String sendEmailSuccess() {
		safeEmail = safeEmail(email);
		return "sendEmailSuccess";
	}
	
	public String resendEmailSuccess() {
		
		return "resendEmailSuccess";
	}
	
	/**
	 * 发送手机短信
	 * @return
	 */
	public String sendShortMessage() {
		UserDto userDto = new UserDto();
		userDto.setUsername(loginId);
		return "sendShortMessage";
	}
	

	
	public Date getMessageSendDate(){
		Date date = (Date) ServletActionContext.getRequest().getSession().getAttribute("messageSendDate");
		if(null != date){
			return date;
		}
		return new Date();
	}
	
	public String sendShortMessageSuccess() {
		safeCellPhone = safeCellphone(cellphone);
		return "sendShortMessageSuccess";
	}
	
	public String resendShortMessageSuccess() {
		safeCellPhone = safeCellphone(cellphone);
		return "sendShortMessageSuccess";
	}
	
	/**
	 * 生成中间四位隐藏的手机号码
	 * @param cellphone 手机号码
	 * @return 中间四位隐藏的手机号码
	 */
	private String safeCellphone(String cellphone){
		String phone = "";
		if(null != cellphone && cellphone.length()>10){
			phone = cellphone.substring(0,3)+"****"+cellphone.substring(7,11);
		}
		return phone;
	}
	
	/**
	 * 获取安全邮箱
	 * @param email 邮箱
	 * @return 安全邮箱
	 */
	private String safeEmail(String email){
		try{
			String em = "";
			if(null != email){
				int atIndex = email.indexOf("@");
				em = em + getAsterisk(atIndex) + email.substring(atIndex,atIndex+2);
				int dotIndex = email.lastIndexOf(".");
				String beforeDot = email.substring(atIndex+1,dotIndex-2);
				em = em + getAsterisk(beforeDot.length())+email.substring(dotIndex-1, email.length());
			}
			return em;
		}catch(Exception e){
			return "";
		}
		
	}
	
	/**
	 * 获取*字符串，count代表多少个
	 * @param count 多少个
	 * @return *字符串
	 */
	private static String getAsterisk(int count){
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i < count; i++) {
			sb.append("*");
		}
		return sb.toString();
	}
	
	/**
	 * 重设密码
	 * @return
	 */
	public String resetpassword() {
		safeCellPhone = safeCellphone(cellphone);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		if (StringUtils.isNotBlank(verifystr)) {
			String sql = "select id,create_date,login_id,isvalid,verify_info from verifystr t where t.id = '" + verifystr + "'";
			try { 
				List list = this.getGeneralService().getBySQL(sql);
				if (list != null && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					Date createDate = (Date) obj[1];
					String isvalid = obj[3].toString();
					String verifyInfo = (String)obj[4];
					if("1".equals(isvalid)){
						Date endDate;
						if("1".equals(verifyType)){
							//判断短信验证码是否正确
							if(null == messageCode || !messageCode.equals(verifyInfo)){
								this.setErrorEmailMessage("验证码错误");
								return "messageCodeError";
							}
							endDate = DateUtils.addMinutes(createDate, 30);
						}else{
							endDate = DateUtils.addDays(createDate, 1);
						}
						if(endDate.after(now)){
							return "resetpassword";
						}
					}
					return "invalid";
				} else {
					return "invalid";
				}
			} catch (EntityException e) {
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

		/*if (password.length() < 6 && password.length() > 16) {
			this.setMessage("2");
			return "resetpassword";
		}*/

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
				if (list != null && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					Date createDate = (Date) obj[1];
					String logiId = (String)obj[2];
					//this.setSsoId(obj[2].toString());
					String isvalid = obj[3].toString();
					Date endDate = DateUtils.addDays(createDate, 1);

					if (endDate.after(now) && "1".equals(isvalid)) {
						//SsoUser user = (SsoUser) this.getGeneralService().getById(SsoUser.class, obj[2].toString());
						//user.setPassword(EncryptUtil.md5(password));
						try {
							//this.getGeneralService().save(user);
							System.out.println(logiId);
//							userWebService.modifyWbtrnPassword(logiId, password);
							//UserDto UserDto  = userWebService.getUserByUsername(user.getLoginId(), EncryptUtil.md5("1111"));
							//UserDto UserDto = userWebService.getUserByUsernameOnly(user.getLoginId());
							//System.out.println("UserDto:" + UserDto.getEmail());
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
			} catch (EntityException e) {
				e.printStackTrace();
				return "invalid";
			}
		} else {
			return "invalid";
		}
	}

	public String showsuccess() {
		ServletActionContext.getRequest().getSession().removeAttribute("messageSendDate");
		ServletActionContext.getRequest().getSession().removeAttribute("messageCode");
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

	public boolean isCasUserEmail() {
		boolean flag = false;
		if (StringUtils.isNotBlank(email)) {
		}
		return flag;
	}

	@Override
	public void initGrid() {

	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/password/getPswAction";

	}

	public String genRandomNum(int pwd_len) {
		//35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; //生成的随机数
		int count = 0; //生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			//生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
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

	public String getErrorEmailMessage() {
		return errorEmailMessage;
	}

	public void setErrorEmailMessage(String errorEmailMessage) {
		this.errorEmailMessage = errorEmailMessage;
	}

	public String getErrorValidateCodeMessage() {
		return errorValidateCodeMessage;
	}

	public void setErrorValidateCodeMessage(String errorValidateCodeMessage) {
		this.errorValidateCodeMessage = errorValidateCodeMessage;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
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

	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getSafeEmail() {
		return safeEmail;
	}

	public void setSafeEmail(String safeEmail) {
		this.safeEmail = safeEmail;
	}

	public String getSafeCellPhone() {
		return safeCellPhone;
	}

	public void setSafeCellPhone(String safeCellPhone) {
		this.safeCellPhone = safeCellPhone;
	}
	
}
class UserDto {
	private String username;
	private String email;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
