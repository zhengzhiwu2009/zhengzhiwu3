package com.whaty.platform.entity.bean;

/**
 * PrExamPatrolSetting generated by MyEclipse Persistence Tools
 */

public class PrExamPatrolSetting extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;

	private PeExamPatrol peExamPatrol;

	private PeSemester peSemester;

	private PeSite peSite;

	// Constructors

	/** default constructor */
	public PrExamPatrolSetting() {
	}

	/** full constructor */
	public PrExamPatrolSetting(PeExamPatrol peExamPatrol,
			PeSemester peSemester, PeSite peSite) {
		this.peExamPatrol = peExamPatrol;
		this.peSemester = peSemester;
		this.peSite = peSite;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeExamPatrol getPeExamPatrol() {
		return this.peExamPatrol;
	}

	public void setPeExamPatrol(PeExamPatrol peExamPatrol) {
		this.peExamPatrol = peExamPatrol;
	}

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

}