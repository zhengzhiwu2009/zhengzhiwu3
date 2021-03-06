package com.whaty.platform.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegStudent extends AbstractBean implements Serializable {

	private String id;
	private SsoUser ssoUser;
	private PeBzzBatch peBzzBatch; // 所在批次
	private PeEnterprise peEnterprise; // 所在企业
	private String name; // 姓名
	private String regNo; // 学号
	private String gender; // 性别
	private String folk; // 民族
	private String education; // 学历
	private String age; // 年龄
	private String position; // 职务
	private String title; // 职称
	private String department; // 部门
	private String address; // 地址
	private String zipcode; // 邮编
	private String phone; // 办公电话
	private String mobilePhone; // 移动电话
	private String email; // 电子邮件
	private String trueName; // 真实姓名
	private Date birthdayDate;//出生日期
	private Date studyEndDate;//延长学习截止时间
	
	private PeEnterprise peSite;	//管理员所在的企业
	private String jobNumber;	//学员工号
	
	private String cardType;	//证件类型
	private String cardNo;	//身份证号
	private String groups;	//分组
	private String checkState;//审核状态
	private String photoConfirm;//照片确认状态
	private String isGood;//照片确认状态
	//private String studentType;	//分组FlagStudentType
	
	
	private Set studentBatch  = new HashSet(0);
	private Set periodStudent = new HashSet(0);
	private Set recordStudent = new HashSet(0);
	
	public Set getRecordStudent() {
		return recordStudent;
	}

	public void setRecordStudent(Set recordStudent) {
		this.recordStudent = recordStudent;
	}

	public Set getPeriodStudent() {
		return periodStudent;
	}

	public void setPeriodStudent(Set periodStudent) {
		this.periodStudent = periodStudent;
	}

	public Date getStudyEndDate() {
		return studyEndDate;
	}

	public void setStudyEndDate(Date studyEndDate) {
		this.studyEndDate = studyEndDate;
	}

	private Set prBzzTchStuElectives = new HashSet(0);
	
	private String exportState;
	private String registPeople;
	private Date registDate;

	private PeBzzRecruit peBzzRecruit;
	private String exportPeople;
	private Date exportDate;
	
	private String subEntName;   //合并企业时，记录原来所在企业名称
	
	private EnumConst enumConstByFlagRankState; // 注册状态：已注册/未注册
	
	private String photo;
	
	private EnumConst enumConstByFlagStudentType;//注册学员类型是否是个人，默认是：否
	
	//private EnumConst enumConstByFlagIsGoodStu;//是否是优秀学员，默认是：否
	
	private Date photoConfirmDate;//照片确认时间
	
	private String photoUnconfirmReason;//管理员取消照片确认为未确认，并填写原因

	private String pickUser;	//领用人，针对体验账号来记录是谁领用了这个账号
	
	// Constructors

	/** default constructor */
	public RegStudent() {
	}

	/** minimal constructor */
	public RegStudent(String regNo) {
		this.regNo = regNo;
	}

	/** full constructor */
	public RegStudent(SsoUser ssoUser, PeBzzBatch peBzzBatch,
			PeEnterprise peEnterprise, String name, String regNo,String cardType,String cardNo,String groups,
			String gender, String folk, String education, String age,
			String position, String title, String department, String address,
			String zipcode, String phone, String mobilePhone, String email,
			String trueName, Set prBzzTchStuElectives,Date birthdayDate,PeBzzRecruit peBzzRecruit,
			Set recordStudent) {
		this.ssoUser = ssoUser;
		this.peBzzBatch = peBzzBatch;
		this.peEnterprise = peEnterprise;
		this.name = name;
		this.regNo = regNo;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.groups = groups;
		this.gender = gender;
		this.folk = folk;
		this.education = education;
		this.age = age;
		this.position = position;
		this.title = title;
		this.department = department;
		this.address = address;
		this.zipcode = zipcode;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.trueName = trueName;
		this.prBzzTchStuElectives = prBzzTchStuElectives;
		this.birthdayDate =birthdayDate;
		this.peBzzRecruit=peBzzRecruit;
		this.recordStudent = recordStudent;
	}
	
	public List epAjax(){
		List enterpriseList = new ArrayList();
		
		return enterpriseList;
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

	public PeBzzBatch getPeBzzBatch() {
		return this.peBzzBatch;
	}

	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public PeEnterprise getPeEnterprise() {
		return this.peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegNo() {
		return this.regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFolk() {
		return this.folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Set getPrBzzTchStuElectives() {
		return this.prBzzTchStuElectives;
	}

	public void setPrBzzTchStuElectives(Set prBzzTchStuElectives) {
		this.prBzzTchStuElectives = prBzzTchStuElectives;
	}

	public Date getBirthdayDate() {
		return birthdayDate;
	}

	public void setBirthdayDate(Date birthdayDate) {
		this.birthdayDate = birthdayDate;
	}

	public String getRegistPeople() {
		return registPeople;
	}

	public void setRegistPeople(String registPeople) {
		this.registPeople = registPeople;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getExportState() {
		return exportState;
	}

	public void setExportState(String exportState) {
		this.exportState = exportState;
	}

	public PeBzzRecruit getPeBzzRecruit() {
		return peBzzRecruit;
	}

	public void setPeBzzRecruit(PeBzzRecruit peBzzRecruit) {
		this.peBzzRecruit = peBzzRecruit;
	}
	public String getExportPeople() {
		return exportPeople;
	}

	public void setExportPeople(String exportPeople) {
		this.exportPeople = exportPeople;
	}

	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	public String getSubEntName() {
		return subEntName;
	}

	public void setSubEntName(String subEntName) {
		this.subEntName = subEntName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public EnumConst getEnumConstByFlagRankState() {
		return enumConstByFlagRankState;
	}

	public void setEnumConstByFlagRankState(EnumConst enumConstByFlagRankState) {
		this.enumConstByFlagRankState = enumConstByFlagRankState;
	}

	public Date getPhotoConfirmDate() {
		return photoConfirmDate;
	}

	public void setPhotoConfirmDate(Date photoConfirmDate) {
		this.photoConfirmDate = photoConfirmDate;
	}

	public String getPhotoUnconfirmReason() {
		return photoUnconfirmReason;
	}

	public void setPhotoUnconfirmReason(String photoUnconfirmReason) {
		this.photoUnconfirmReason = photoUnconfirmReason;
	}

	public PeEnterprise getPeSite() {
		return peSite;
	}

	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}

	public String getPickUser() {
		return pickUser;
	}

	public void setPickUser(String pickUser) {
		this.pickUser = pickUser;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public Set getStudentBatch() {
		return studentBatch;
	}

	public void setStudentBatch(Set studentBatch) {
		this.studentBatch = studentBatch;
	}

	public EnumConst getEnumConstByFlagStudentType() {
		return enumConstByFlagStudentType;
	}

	public void setEnumConstByFlagStudentType(EnumConst enumConstByFlagStudentType) {
		this.enumConstByFlagStudentType = enumConstByFlagStudentType;
	}

	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public String getPhotoConfirm() {
		return photoConfirm;
	}

	public void setPhotoConfirm(String photoConfirm) {
		this.photoConfirm = photoConfirm;
	}

	public String getIsGood() {
		return isGood;
	}

	public void setIsGood(String isGood) {
		this.isGood = isGood;
	}


}
