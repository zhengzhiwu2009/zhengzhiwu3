package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PePriCategory generated by MyEclipse Persistence Tools
 */

public class PePriCategory extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;

	private PePriCategory pePriCategory;

	private String name;

	private String code;

	private String path;

	private String flagLeftMenu;

	private Set pePriorities = new HashSet(0);

	private Set pePriCategories = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePriCategory() {
	}

	/** minimal constructor */
	public PePriCategory(String name) {
		this.name = name;
	}

	/** full constructor */
	public PePriCategory(PePriCategory pePriCategory, String name, String code,
			String path, String flagLeftMenu, Set pePriorities,
			Set pePriCategories) {
		this.pePriCategory = pePriCategory;
		this.name = name;
		this.code = code;
		this.path = path;
		this.flagLeftMenu = flagLeftMenu;
		this.pePriorities = pePriorities;
		this.pePriCategories = pePriCategories;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PePriCategory getPePriCategory() {
		return this.pePriCategory;
	}

	public void setPePriCategory(PePriCategory pePriCategory) {
		this.pePriCategory = pePriCategory;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFlagLeftMenu() {
		return this.flagLeftMenu;
	}

	public void setFlagLeftMenu(String flagLeftMenu) {
		this.flagLeftMenu = flagLeftMenu;
	}

	public Set getPePriorities() {
		return this.pePriorities;
	}

	public void setPePriorities(Set pePriorities) {
		this.pePriorities = pePriorities;
	}

	public Set getPePriCategories() {
		return this.pePriCategories;
	}

	public void setPePriCategories(Set pePriCategories) {
		this.pePriCategories = pePriCategories;
	}

}