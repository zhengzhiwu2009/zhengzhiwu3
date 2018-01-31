package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeTchProgram entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTchProgram extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagUniteA;
	private EnumConst enumConstByFlagUniteB;
	private PeEdutype peEdutype;
	private EnumConst enumConstByFlagMajorType;
	private EnumConst enumConstByFlagDegreeCandisobey;
	private PeMajor peMajor;
	private PeGrade peGrade;
	private String name;
	private Long graduateMinCredit;
	private Double degreeAvgScore;
	private Double degreePaperScore;
	private Long paperMinCreditHour;
	private Long paperMinSemeser;
	private Long maxElective;
	private Double minSemester;
	private Double maxSemester;
	private String note;
	private Set peTchProgramGroups = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTchProgram() {
	}

	/** minimal constructor */
	public PeTchProgram(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeTchProgram(EnumConst enumConstByFlagUniteA,
			EnumConst enumConstByFlagUniteB, PeEdutype peEdutype,
			EnumConst enumConstByFlagMajorType,
			EnumConst enumConstByFlagDegreeCandisobey, PeMajor peMajor,
			PeGrade peGrade, String name, Long graduateMinCredit,
			Double degreeAvgScore, Double degreePaperScore,
			Long paperMinCreditHour, Long paperMinSemeser, Long maxElective,
			Double minSemester, Double maxSemester, String note,
			Set peTchProgramGroups) {
		this.enumConstByFlagUniteA = enumConstByFlagUniteA;
		this.enumConstByFlagUniteB = enumConstByFlagUniteB;
		this.peEdutype = peEdutype;
		this.enumConstByFlagMajorType = enumConstByFlagMajorType;
		this.enumConstByFlagDegreeCandisobey = enumConstByFlagDegreeCandisobey;
		this.peMajor = peMajor;
		this.peGrade = peGrade;
		this.name = name;
		this.graduateMinCredit = graduateMinCredit;
		this.degreeAvgScore = degreeAvgScore;
		this.degreePaperScore = degreePaperScore;
		this.paperMinCreditHour = paperMinCreditHour;
		this.paperMinSemeser = paperMinSemeser;
		this.maxElective = maxElective;
		this.minSemester = minSemester;
		this.maxSemester = maxSemester;
		this.note = note;
		this.peTchProgramGroups = peTchProgramGroups;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagUniteA() {
		return this.enumConstByFlagUniteA;
	}

	public void setEnumConstByFlagUniteA(EnumConst enumConstByFlagUniteA) {
		this.enumConstByFlagUniteA = enumConstByFlagUniteA;
	}

	public EnumConst getEnumConstByFlagUniteB() {
		return this.enumConstByFlagUniteB;
	}

	public void setEnumConstByFlagUniteB(EnumConst enumConstByFlagUniteB) {
		this.enumConstByFlagUniteB = enumConstByFlagUniteB;
	}

	public PeEdutype getPeEdutype() {
		return this.peEdutype;
	}

	public void setPeEdutype(PeEdutype peEdutype) {
		this.peEdutype = peEdutype;
	}

	public EnumConst getEnumConstByFlagMajorType() {
		return this.enumConstByFlagMajorType;
	}

	public void setEnumConstByFlagMajorType(EnumConst enumConstByFlagMajorType) {
		this.enumConstByFlagMajorType = enumConstByFlagMajorType;
	}

	public EnumConst getEnumConstByFlagDegreeCandisobey() {
		return this.enumConstByFlagDegreeCandisobey;
	}

	public void setEnumConstByFlagDegreeCandisobey(
			EnumConst enumConstByFlagDegreeCandisobey) {
		this.enumConstByFlagDegreeCandisobey = enumConstByFlagDegreeCandisobey;
	}

	public PeMajor getPeMajor() {
		return this.peMajor;
	}

	public void setPeMajor(PeMajor peMajor) {
		this.peMajor = peMajor;
	}

	public PeGrade getPeGrade() {
		return this.peGrade;
	}

	public void setPeGrade(PeGrade peGrade) {
		this.peGrade = peGrade;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGraduateMinCredit() {
		return this.graduateMinCredit;
	}

	public void setGraduateMinCredit(Long graduateMinCredit) {
		this.graduateMinCredit = graduateMinCredit;
	}

	public Double getDegreeAvgScore() {
		return this.degreeAvgScore;
	}

	public void setDegreeAvgScore(Double degreeAvgScore) {
		this.degreeAvgScore = degreeAvgScore;
	}

	public Double getDegreePaperScore() {
		return this.degreePaperScore;
	}

	public void setDegreePaperScore(Double degreePaperScore) {
		this.degreePaperScore = degreePaperScore;
	}

	public Long getPaperMinCreditHour() {
		return this.paperMinCreditHour;
	}

	public void setPaperMinCreditHour(Long paperMinCreditHour) {
		this.paperMinCreditHour = paperMinCreditHour;
	}

	public Long getPaperMinSemeser() {
		return this.paperMinSemeser;
	}

	public void setPaperMinSemeser(Long paperMinSemeser) {
		this.paperMinSemeser = paperMinSemeser;
	}

	public Long getMaxElective() {
		return this.maxElective;
	}

	public void setMaxElective(Long maxElective) {
		this.maxElective = maxElective;
	}

	public Double getMinSemester() {
		return this.minSemester;
	}

	public void setMinSemester(Double minSemester) {
		this.minSemester = minSemester;
	}

	public Double getMaxSemester() {
		return this.maxSemester;
	}

	public void setMaxSemester(Double maxSemester) {
		this.maxSemester = maxSemester;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeTchProgramGroups() {
		return this.peTchProgramGroups;
	}

	public void setPeTchProgramGroups(Set peTchProgramGroups) {
		this.peTchProgramGroups = peTchProgramGroups;
	}

}