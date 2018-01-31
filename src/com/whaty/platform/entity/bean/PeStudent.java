package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeStudent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeStudent extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByScoreUniteEnglishB;
	private EnumConst enumConstByScoreUniteEnglishC;
	private EnumConst enumConstByScoreUniteComputer;
	private EnumConst enumConstByDegreeEnglishType;
	private PrStudentInfo prStudentInfo;
	private EnumConst enumConstByFlagStudentStatus;
	private EnumConst enumConstByFlagMajorType;
	private PeSite peSite;
	private PeMajor peMajor;
	private PeRecStudent peRecStudent;
	private EnumConst enumConstByScoreUniteEnglishA;
	private EnumConst enumConstByFlagAdvanced;
	private PeFeeLevel peFeeLevel;
	private SsoUser ssoUser;
	private EnumConst enumConstByScoreUniteShuxue;
	private EnumConst enumConstByScoreUniteYuwen;
	private PeEdutype peEdutype;
	private EnumConst enumConstByFlagDisobey;
	private PeGrade peGrade;
	private String name;
	private String trueName;
	private String regNo;
	private String kaoshenghao;
	private Date recruitDate;
	private Double feeBalance;
	private Double feeInactive;
	private String disobeyNote;
	private Double scoreDegreeEnglish;
	private String graduationCertificateNo;
	private Date graduationDate;
	private String leaveDate;
	private String degreeCertificateNo;
	private Date degreeDate;
	private Set prFeeDetails = new HashSet(0);
	private Set systemApplies = new HashSet(0);
	private Set prTchStuElectives = new HashSet(0);
	private Set prStudentHistories = new HashSet(0);
	private Set prStuChangeSchools = new HashSet(0);
	private Set prExamStuMaincourses = new HashSet(0);
	private Set prStuChangeEdutypes = new HashSet(0);
	private Set prStuChangeSites = new HashSet(0);
	private Set prStuChangeMajors = new HashSet(0);
	private Set prTchStuPapers = new HashSet(0);
	private Set homeworkHistories = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeStudent() {
	}

	/** minimal constructor */
	public PeStudent(String name, String trueName) {
		this.name = name;
		this.trueName = trueName;
	}

	/** full constructor */
	public PeStudent(EnumConst enumConstByScoreUniteEnglishB,
			EnumConst enumConstByScoreUniteEnglishC,
			EnumConst enumConstByScoreUniteComputer,
			EnumConst enumConstByDegreeEnglishType,
			PrStudentInfo prStudentInfo,
			EnumConst enumConstByFlagStudentStatus,
			EnumConst enumConstByFlagMajorType, PeSite peSite, PeMajor peMajor,
			PeRecStudent peRecStudent, EnumConst enumConstByScoreUniteEnglishA,
			EnumConst enumConstByFlagAdvanced, PeFeeLevel peFeeLevel,
			SsoUser ssoUser, EnumConst enumConstByScoreUniteShuxue,
			EnumConst enumConstByScoreUniteYuwen, PeEdutype peEdutype,
			EnumConst enumConstByFlagDisobey, PeGrade peGrade, String name,
			String trueName, String regNo, String kaoshenghao,
			Date recruitDate, Double feeBalance, Double feeInactive,
			String disobeyNote, Double scoreDegreeEnglish,
			String graduationCertificateNo, Date graduationDate,
			String leaveDate, String degreeCertificateNo, Date degreeDate,
			Set prFeeDetails, Set systemApplies, Set prTchStuElectives,
			Set prStudentHistories, Set prStuChangeSchools,
			Set prExamStuMaincourses, Set prStuChangeEdutypes,
			Set prStuChangeSites, Set prStuChangeMajors, Set prTchStuPapers,
			Set homeworkHistories) {
		this.enumConstByScoreUniteEnglishB = enumConstByScoreUniteEnglishB;
		this.enumConstByScoreUniteEnglishC = enumConstByScoreUniteEnglishC;
		this.enumConstByScoreUniteComputer = enumConstByScoreUniteComputer;
		this.enumConstByDegreeEnglishType = enumConstByDegreeEnglishType;
		this.prStudentInfo = prStudentInfo;
		this.enumConstByFlagStudentStatus = enumConstByFlagStudentStatus;
		this.enumConstByFlagMajorType = enumConstByFlagMajorType;
		this.peSite = peSite;
		this.peMajor = peMajor;
		this.peRecStudent = peRecStudent;
		this.enumConstByScoreUniteEnglishA = enumConstByScoreUniteEnglishA;
		this.enumConstByFlagAdvanced = enumConstByFlagAdvanced;
		this.peFeeLevel = peFeeLevel;
		this.ssoUser = ssoUser;
		this.enumConstByScoreUniteShuxue = enumConstByScoreUniteShuxue;
		this.enumConstByScoreUniteYuwen = enumConstByScoreUniteYuwen;
		this.peEdutype = peEdutype;
		this.enumConstByFlagDisobey = enumConstByFlagDisobey;
		this.peGrade = peGrade;
		this.name = name;
		this.trueName = trueName;
		this.regNo = regNo;
		this.kaoshenghao = kaoshenghao;
		this.recruitDate = recruitDate;
		this.feeBalance = feeBalance;
		this.feeInactive = feeInactive;
		this.disobeyNote = disobeyNote;
		this.scoreDegreeEnglish = scoreDegreeEnglish;
		this.graduationCertificateNo = graduationCertificateNo;
		this.graduationDate = graduationDate;
		this.leaveDate = leaveDate;
		this.degreeCertificateNo = degreeCertificateNo;
		this.degreeDate = degreeDate;
		this.prFeeDetails = prFeeDetails;
		this.systemApplies = systemApplies;
		this.prTchStuElectives = prTchStuElectives;
		this.prStudentHistories = prStudentHistories;
		this.prStuChangeSchools = prStuChangeSchools;
		this.prExamStuMaincourses = prExamStuMaincourses;
		this.prStuChangeEdutypes = prStuChangeEdutypes;
		this.prStuChangeSites = prStuChangeSites;
		this.prStuChangeMajors = prStuChangeMajors;
		this.prTchStuPapers = prTchStuPapers;
		this.homeworkHistories = homeworkHistories;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByScoreUniteEnglishB() {
		return this.enumConstByScoreUniteEnglishB;
	}

	public void setEnumConstByScoreUniteEnglishB(
			EnumConst enumConstByScoreUniteEnglishB) {
		this.enumConstByScoreUniteEnglishB = enumConstByScoreUniteEnglishB;
	}

	public EnumConst getEnumConstByScoreUniteEnglishC() {
		return this.enumConstByScoreUniteEnglishC;
	}

	public void setEnumConstByScoreUniteEnglishC(
			EnumConst enumConstByScoreUniteEnglishC) {
		this.enumConstByScoreUniteEnglishC = enumConstByScoreUniteEnglishC;
	}

	public EnumConst getEnumConstByScoreUniteComputer() {
		return this.enumConstByScoreUniteComputer;
	}

	public void setEnumConstByScoreUniteComputer(
			EnumConst enumConstByScoreUniteComputer) {
		this.enumConstByScoreUniteComputer = enumConstByScoreUniteComputer;
	}

	public EnumConst getEnumConstByDegreeEnglishType() {
		return this.enumConstByDegreeEnglishType;
	}

	public void setEnumConstByDegreeEnglishType(
			EnumConst enumConstByDegreeEnglishType) {
		this.enumConstByDegreeEnglishType = enumConstByDegreeEnglishType;
	}

	public PrStudentInfo getPrStudentInfo() {
		return this.prStudentInfo;
	}

	public void setPrStudentInfo(PrStudentInfo prStudentInfo) {
		this.prStudentInfo = prStudentInfo;
	}

	public EnumConst getEnumConstByFlagStudentStatus() {
		return this.enumConstByFlagStudentStatus;
	}

	public void setEnumConstByFlagStudentStatus(
			EnumConst enumConstByFlagStudentStatus) {
		this.enumConstByFlagStudentStatus = enumConstByFlagStudentStatus;
	}

	public EnumConst getEnumConstByFlagMajorType() {
		return this.enumConstByFlagMajorType;
	}

	public void setEnumConstByFlagMajorType(EnumConst enumConstByFlagMajorType) {
		this.enumConstByFlagMajorType = enumConstByFlagMajorType;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public PeMajor getPeMajor() {
		return this.peMajor;
	}

	public void setPeMajor(PeMajor peMajor) {
		this.peMajor = peMajor;
	}

	public PeRecStudent getPeRecStudent() {
		return this.peRecStudent;
	}

	public void setPeRecStudent(PeRecStudent peRecStudent) {
		this.peRecStudent = peRecStudent;
	}

	public EnumConst getEnumConstByScoreUniteEnglishA() {
		return this.enumConstByScoreUniteEnglishA;
	}

	public void setEnumConstByScoreUniteEnglishA(
			EnumConst enumConstByScoreUniteEnglishA) {
		this.enumConstByScoreUniteEnglishA = enumConstByScoreUniteEnglishA;
	}

	public EnumConst getEnumConstByFlagAdvanced() {
		return this.enumConstByFlagAdvanced;
	}

	public void setEnumConstByFlagAdvanced(EnumConst enumConstByFlagAdvanced) {
		this.enumConstByFlagAdvanced = enumConstByFlagAdvanced;
	}

	public PeFeeLevel getPeFeeLevel() {
		return this.peFeeLevel;
	}

	public void setPeFeeLevel(PeFeeLevel peFeeLevel) {
		this.peFeeLevel = peFeeLevel;
	}

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public EnumConst getEnumConstByScoreUniteShuxue() {
		return this.enumConstByScoreUniteShuxue;
	}

	public void setEnumConstByScoreUniteShuxue(
			EnumConst enumConstByScoreUniteShuxue) {
		this.enumConstByScoreUniteShuxue = enumConstByScoreUniteShuxue;
	}

	public EnumConst getEnumConstByScoreUniteYuwen() {
		return this.enumConstByScoreUniteYuwen;
	}

	public void setEnumConstByScoreUniteYuwen(
			EnumConst enumConstByScoreUniteYuwen) {
		this.enumConstByScoreUniteYuwen = enumConstByScoreUniteYuwen;
	}

	public PeEdutype getPeEdutype() {
		return this.peEdutype;
	}

	public void setPeEdutype(PeEdutype peEdutype) {
		this.peEdutype = peEdutype;
	}

	public EnumConst getEnumConstByFlagDisobey() {
		return this.enumConstByFlagDisobey;
	}

	public void setEnumConstByFlagDisobey(EnumConst enumConstByFlagDisobey) {
		this.enumConstByFlagDisobey = enumConstByFlagDisobey;
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

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getRegNo() {
		return this.regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getKaoshenghao() {
		return this.kaoshenghao;
	}

	public void setKaoshenghao(String kaoshenghao) {
		this.kaoshenghao = kaoshenghao;
	}

	public Date getRecruitDate() {
		return this.recruitDate;
	}

	public void setRecruitDate(Date recruitDate) {
		this.recruitDate = recruitDate;
	}

	public Double getFeeBalance() {
		return this.feeBalance;
	}

	public void setFeeBalance(Double feeBalance) {
		this.feeBalance = feeBalance;
	}

	public Double getFeeInactive() {
		return this.feeInactive;
	}

	public void setFeeInactive(Double feeInactive) {
		this.feeInactive = feeInactive;
	}

	public String getDisobeyNote() {
		return this.disobeyNote;
	}

	public void setDisobeyNote(String disobeyNote) {
		this.disobeyNote = disobeyNote;
	}

	public Double getScoreDegreeEnglish() {
		return this.scoreDegreeEnglish;
	}

	public void setScoreDegreeEnglish(Double scoreDegreeEnglish) {
		this.scoreDegreeEnglish = scoreDegreeEnglish;
	}

	public String getGraduationCertificateNo() {
		return this.graduationCertificateNo;
	}

	public void setGraduationCertificateNo(String graduationCertificateNo) {
		this.graduationCertificateNo = graduationCertificateNo;
	}

	public Date getGraduationDate() {
		return this.graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getLeaveDate() {
		return this.leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getDegreeCertificateNo() {
		return this.degreeCertificateNo;
	}

	public void setDegreeCertificateNo(String degreeCertificateNo) {
		this.degreeCertificateNo = degreeCertificateNo;
	}

	public Date getDegreeDate() {
		return this.degreeDate;
	}

	public void setDegreeDate(Date degreeDate) {
		this.degreeDate = degreeDate;
	}

	public Set getPrFeeDetails() {
		return this.prFeeDetails;
	}

	public void setPrFeeDetails(Set prFeeDetails) {
		this.prFeeDetails = prFeeDetails;
	}

	public Set getSystemApplies() {
		return this.systemApplies;
	}

	public void setSystemApplies(Set systemApplies) {
		this.systemApplies = systemApplies;
	}

	public Set getPrTchStuElectives() {
		return this.prTchStuElectives;
	}

	public void setPrTchStuElectives(Set prTchStuElectives) {
		this.prTchStuElectives = prTchStuElectives;
	}

	public Set getPrStudentHistories() {
		return this.prStudentHistories;
	}

	public void setPrStudentHistories(Set prStudentHistories) {
		this.prStudentHistories = prStudentHistories;
	}

	public Set getPrStuChangeSchools() {
		return this.prStuChangeSchools;
	}

	public void setPrStuChangeSchools(Set prStuChangeSchools) {
		this.prStuChangeSchools = prStuChangeSchools;
	}

	public Set getPrExamStuMaincourses() {
		return this.prExamStuMaincourses;
	}

	public void setPrExamStuMaincourses(Set prExamStuMaincourses) {
		this.prExamStuMaincourses = prExamStuMaincourses;
	}

	public Set getPrStuChangeEdutypes() {
		return this.prStuChangeEdutypes;
	}

	public void setPrStuChangeEdutypes(Set prStuChangeEdutypes) {
		this.prStuChangeEdutypes = prStuChangeEdutypes;
	}

	public Set getPrStuChangeSites() {
		return this.prStuChangeSites;
	}

	public void setPrStuChangeSites(Set prStuChangeSites) {
		this.prStuChangeSites = prStuChangeSites;
	}

	public Set getPrStuChangeMajors() {
		return this.prStuChangeMajors;
	}

	public void setPrStuChangeMajors(Set prStuChangeMajors) {
		this.prStuChangeMajors = prStuChangeMajors;
	}

	public Set getPrTchStuPapers() {
		return this.prTchStuPapers;
	}

	public void setPrTchStuPapers(Set prTchStuPapers) {
		this.prTchStuPapers = prTchStuPapers;
	}

	public Set getHomeworkHistories() {
		return this.homeworkHistories;
	}

	public void setHomeworkHistories(Set homeworkHistories) {
		this.homeworkHistories = homeworkHistories;
	}

}