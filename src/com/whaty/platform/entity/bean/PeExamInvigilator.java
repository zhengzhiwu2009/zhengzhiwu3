package com.whaty.platform.entity.bean;

/**
 * PeExamInvigilator generated by MyEclipse Persistence Tools
 */

public class PeExamInvigilator extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private EnumConst enumConst;

	private PeSite peSite;

	private String name;

	private String flagBak;

	// Constructors

	/** default constructor */
	public PeExamInvigilator() {
	}

	/** minimal constructor */
	public PeExamInvigilator(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeExamInvigilator(EnumConst enumConst, PeSite peSite, String name,
			String flagBak) {
		this.enumConst = enumConst;
		this.peSite = peSite;
		this.name = name;
		this.flagBak = flagBak;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConst() {
		return this.enumConst;
	}

	public void setEnumConst(EnumConst enumConst) {
		this.enumConst = enumConst;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

}