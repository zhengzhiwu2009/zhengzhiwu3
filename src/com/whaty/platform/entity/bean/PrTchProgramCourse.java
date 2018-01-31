package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrTchProgramCourse generated by MyEclipse Persistence Tools
 */

public class PrTchProgramCourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private PeTchProgramGroup peTchProgramGroup;

	private EnumConst enumConstByFlagBak;

	private EnumConst enumConstByFlagIsMainCourse;

	private PeTchCourse peTchCourse;

	private Double credit;

	private Long unit;

	private Set prTchStuElectives = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrTchProgramCourse() {
	}

	/** full constructor */
	public PrTchProgramCourse(PeTchProgramGroup peTchProgramGroup,
			EnumConst enumConstByFlagBak,
			EnumConst enumConstByFlagIsMainCourse, PeTchCourse peTchCourse,
			Double credit, Long unit, Set prTchStuElectives) {
		this.peTchProgramGroup = peTchProgramGroup;
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.enumConstByFlagIsMainCourse = enumConstByFlagIsMainCourse;
		this.peTchCourse = peTchCourse;
		this.credit = credit;
		this.unit = unit;
		this.prTchStuElectives = prTchStuElectives;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTchProgramGroup getPeTchProgramGroup() {
		return this.peTchProgramGroup;
	}

	public void setPeTchProgramGroup(PeTchProgramGroup peTchProgramGroup) {
		this.peTchProgramGroup = peTchProgramGroup;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public EnumConst getEnumConstByFlagIsMainCourse() {
		return this.enumConstByFlagIsMainCourse;
	}

	public void setEnumConstByFlagIsMainCourse(
			EnumConst enumConstByFlagIsMainCourse) {
		this.enumConstByFlagIsMainCourse = enumConstByFlagIsMainCourse;
	}

	public PeTchCourse getPeTchCourse() {
		return this.peTchCourse;
	}

	public void setPeTchCourse(PeTchCourse peTchCourse) {
		this.peTchCourse = peTchCourse;
	}

	public Double getCredit() {
		return this.credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Long getUnit() {
		return this.unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
	}

	public Set getPrTchStuElectives() {
		return this.prTchStuElectives;
	}

	public void setPrTchStuElectives(Set prTchStuElectives) {
		this.prTchStuElectives = prTchStuElectives;
	}

}