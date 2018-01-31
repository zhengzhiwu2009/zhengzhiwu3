package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeEnterpriseManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeEnterpriseManager extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {

	// Fields

	private String id;
	private SsoUser ssoUser;
	private PeEnterprise peEnterprise;
	private EnumConst enumConstByGender;
	private EnumConst enumConstByFlagIsvalid;
	private String loginId;
	private String name;
	private String position;
	private String phone;
	private String mobilePhone;
	private String email;
	private String bz;
	private String confirmManagerId;
	private Date confirmDate;
	
	private PeEnterprise peSite;	//管理员所在的企业
	
	private EnumConst enumConstByFlaggoodManag;//是否是优秀管理员： 否、优秀、最佳
	
	private PePriRole pePriRole;
	private Set peBulletins = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeEnterpriseManager() {
	}

	/** full constructor */
	public PeEnterpriseManager(SsoUser ssoUser, PeEnterprise peEnterprise,
			EnumConst enumConstByGender, EnumConst enumConstByFlagIsvalid,
			String loginId, String name, String position, String phone,
			String mobilePhone, String email, String bz,PePriRole pePriRole,
			String confirmManagerId, Date confirmDate, Set peBulletins) {
		this.ssoUser = ssoUser;
		this.peEnterprise = peEnterprise;
		this.enumConstByGender = enumConstByGender;
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.loginId = loginId;
		this.name = name;
		this.position = position;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.bz = bz;
		this.confirmManagerId = confirmManagerId;
		this.confirmDate = confirmDate;
		this.peBulletins = peBulletins;
		this.pePriRole=pePriRole;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public PeEnterprise getPeEnterprise() {
		return this.peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public EnumConst getEnumConstByGender() {
		return this.enumConstByGender;
	}

	public void setEnumConstByGender(EnumConst enumConstByGender) {
		this.enumConstByGender = enumConstByGender;
	}

	public EnumConst getEnumConstByFlagIsvalid() {
		return this.enumConstByFlagIsvalid;
	}

	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getConfirmManagerId() {
		return this.confirmManagerId;
	}

	public void setConfirmManagerId(String confirmManagerId) {
		this.confirmManagerId = confirmManagerId;
	}

	public Date getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Set getPeBulletins() {
		return this.peBulletins;
	}

	public void setPeBulletins(Set peBulletins) {
		this.peBulletins = peBulletins;
	}

	public PePriRole getPePriRole() {
		if(this.pePriRole!=null)
			return pePriRole;
		if(ssoUser!=null)
			return this.ssoUser.getPePriRole();
		return null;
	}

	public void setPePriRole(PePriRole pePriRole) {
		this.pePriRole = pePriRole;
	}

	public EnumConst getEnumConstByFlaggoodManag() {
		return enumConstByFlaggoodManag;
	}

	public void setEnumConstByFlaggoodManag(EnumConst enumConstByFlaggoodManag) {
		this.enumConstByFlaggoodManag = enumConstByFlaggoodManag;
	}

	public PeEnterprise getPeSite() {
		return peSite;
	}

	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}

}