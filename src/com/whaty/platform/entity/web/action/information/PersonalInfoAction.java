package com.whaty.platform.entity.web.action.information;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.imp.information.PersonalInfoService;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PersonalInfoAction extends MyBaseAction{
	
	private PeManager peManager;
	
	private PeEnterpriseManager peEnterpriseManager;
	private String phone;
	private String mobilePhone;
	private String email;
	private String id;
	private String trueName;
	private String zhiCheng;
	private String gender;
	private String message;
	private String address;
	
	private String oldPwd;
	private String num;
	private String pwd;
	private GeneralService generalService;
	private EnumConstService enumConstService;
	
	private PersonalInfoService personalInfoService;
	public void initGrid() {
	}

    @Override
    public void setEntityClass() {
	this.entityClass = PeBulletin.class;
    }

    @Override
    public void setServletPath() {
	this.servletPath = "/entity/information/peBulletinView";
    }
    
	public PersonalInfoService getPersonalInfoService() {
		return personalInfoService;
	}

	public void setPersonalInfoService(PersonalInfoService personalInfoService) {
		this.personalInfoService = personalInfoService;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
	
	/**
	 * 查看账户信息
	 * @author linjie
	 * @return
	 */
	public String viewInfo() {
		setInfo();
		return "info_detail";
	}
	
	/**
	 * 修改账户信息页面
	 * @author linjie
	 * @return
	 */
	public String turnToEditInfo() {
		setInfo();
			
		return "edit_manager_Info";
	}
	
	/**
	 * 修改账户信息
	 * @author linjie
	 * @return
	 */
	public String editInfo() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String loginType = userSession.getUserLoginType();
		DetachedCriteria tempdc = null;
		try {
			if(loginType.equals("3")){
				tempdc= DetachedCriteria.forClass(PeManager.class);
			}else{
				tempdc= DetachedCriteria.forClass(PeEnterpriseManager.class);
				tempdc.createAlias("peEnterprise", "peEnterprise");
			}
			tempdc.add(Restrictions.eq("id", id));
			if(loginType.equals("3")){
				PeManager peManager =(PeManager)this.generalService.getList(tempdc).get(0);
				peManager.setEmail(email);
				peManager.setMobilePhone(mobilePhone);
				peManager.setPhone(phone);
				peManager.setZhiCheng(zhiCheng);
				peManager.setTrueName(trueName);
				peManager.setEnumConstByGender(this.getEnumConstService().getByNamespaceCode("Gender", this.gender));
				this.getGeneralService().updatePeManager(peManager);
			}else{
				PeEnterpriseManager enterpriseManager =(PeEnterpriseManager)this.generalService.getList(tempdc).get(0);
				PeEnterprise peEnterprise = enterpriseManager.getPeEnterprise();
				enterpriseManager.setEmail(email);
				enterpriseManager.setMobilePhone(mobilePhone);
				enterpriseManager.setPhone(phone);
				//enterpriseManager.setPosition(zhiCheng);
				enterpriseManager.setName(trueName);
				enterpriseManager.setEnumConstByGender(this.getEnumConstService().getByNamespaceCode("Gender", this.gender));
				peEnterprise.setNum(num);
				peEnterprise.setFzrDepart(zhiCheng);
				peEnterprise.setAddress(address);
				enterpriseManager.setPeEnterprise(peEnterprise);
				try {
					//this.generalService.save(enterpriseManager);
					this.personalInfoService.updatePersonalInfo(enterpriseManager);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.message="editOK";
		return "info_view";
	}
	
	/**
	 * 查询账户信息
	 * @author linjie
	 */
	private void setInfo() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String loginType = userSession.getUserLoginType();
		DetachedCriteria infodc = null;
		try {
			if(loginType.equals("3")){
				infodc = DetachedCriteria.forClass(PeManager.class);
			}else{
				infodc = DetachedCriteria.forClass(PeEnterpriseManager.class);
				infodc.createCriteria("peEnterprise","peEnterprise");
			}			
			infodc.createCriteria("enumConstByGender", "enumConstByGender", DetachedCriteria.LEFT_JOIN);
			infodc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
			if(loginType.equals("3")){
				this.setPeManager((PeManager)this.generalService.getList(infodc).get(0));
			}else{
				this.setPeEnterpriseManager((PeEnterpriseManager)this.generalService.getList(infodc).get(0));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	public String editPwd() {
		Map map = new HashMap();
		if(this.getOldPwd() == null){
			return "pwd";
		}
		SsoUser ssoUser = ((UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY)).getSsoUser();
		
		//if((!MyUtil.md5(this.getOldPwd()).equals(ssoUser.getPasswordMd5())) && (!this.getOldPwd().equals(ssoUser.getPassword()))){
		if(!MyUtil.md5(this.getOldPwd()).equals(ssoUser.getPasswordBk())){//md5验证
			this.message = "0";
		}else{
			ssoUser.setPasswordBk(MyUtil.md5(this.getPwd()));
			ssoUser.setPasswordMd5(MyUtil.md5(this.getPwd()));
			//ssoUser.setPassword(this.getPwd());
			try {
				this.getGeneralService().save(ssoUser);
				//this.message = "密码设置成功";
				this.message="1";
			} catch (EntityException e) {
				e.printStackTrace();
				//this.message = "密码设置失败";
				this.message="2";
			}
		}
		return "pwd";
	}
	
	/**
	 * 修改密码
	 * @author linjie
	 * @return
	 */
	public String showPwd(){
		
		return "pwd";
	}
	
	public String getPhone() {
		return phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getZhiCheng() {
		return zhiCheng;
	}

	public void setZhiCheng(String zhiCheng) {
		this.zhiCheng = zhiCheng;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	
}
