package com.whaty.platform.entity.bean;

/**
 * PrPriManagerSite generated by MyEclipse Persistence Tools
 */

public class PrPriManagerSite extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private SsoUser ssoUser;

	private PeSite peSite;

	// Constructors

	/** default constructor */
	public PrPriManagerSite() {
	}

	/** full constructor */
	public PrPriManagerSite(SsoUser ssoUser, PeSite peSite) {
		this.ssoUser = ssoUser;
		this.peSite = peSite;
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

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

}