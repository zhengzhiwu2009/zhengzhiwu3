package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PePriRole generated by MyEclipse Persistence Tools
 */

public class PePriRole extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;

	private EnumConst enumConstByFlagBak;

	private EnumConst enumConstByFlagRoleType;

	private String name;
	
	private String roledesc;

	private Set prPriRoles = new HashSet(0);

	private Set ssoUsers = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePriRole() {
	}

	/** minimal constructor */
	public PePriRole(String name) {
		this.name = name;
	}

	/** full constructor */
	public PePriRole(EnumConst enumConstByFlagBak,
			EnumConst enumConstByFlagRoleType, String name,String roledesc, Set prPriRoles,
			Set ssoUsers) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.enumConstByFlagRoleType = enumConstByFlagRoleType;
		this.name = name;
		this.roledesc = roledesc;
		this.prPriRoles = prPriRoles;
		this.ssoUsers = ssoUsers;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public EnumConst getEnumConstByFlagRoleType() {
		return this.enumConstByFlagRoleType;
	}

	public void setEnumConstByFlagRoleType(EnumConst enumConstByFlagRoleType) {
		this.enumConstByFlagRoleType = enumConstByFlagRoleType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getPrPriRoles() {
		return this.prPriRoles;
	}

	public void setPrPriRoles(Set prPriRoles) {
		this.prPriRoles = prPriRoles;
	}

	public Set getSsoUsers() {
		return this.ssoUsers;
	}

	public void setSsoUsers(Set ssoUsers) {
		this.ssoUsers = ssoUsers;
	}

	public String getRoledesc() {
		return roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

}