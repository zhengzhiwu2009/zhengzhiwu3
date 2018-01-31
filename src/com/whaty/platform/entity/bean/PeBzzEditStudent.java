package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PeBzzEditStudent extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable{

	private String id;
	private PeBzzStudent peBzzStudent;
	private String oldRegNo; // 原学号
	private String oldTrueName; // 原真实姓名
	private String oldGender; // 原性别
	private Date   oldBirthdayDate;//原出生日期
	private String oldFolk; // 原民族
	private String oldEducation; // 原学历	
	private PeBzzBatch oldPeBzzBatch; // 原所在批次
	private String oldAddress; // 原地址
	private String oldPhone; // 原办公电话
	private String oldMobilePhone; // 原移动电话
	private PeEnterprise oldPeEnterprise; // 原所在企业
	private String oldEmail; // 原电子邮件
	private String oldPhoto; // 原相片
	//private EnumConst enumConstByFlagEditStudent; // 注册状态：已注册/未注册
	private String newRegNo; // 新学号
	private String newTrueName; // 新真实姓名
	private String newGender; // 新性别
	private Date   newBirthdayDate;//新出生日期
	private String newFolk; // 新民族
	private String newEducation; // 新学历	
	private PeBzzBatch newPeBzzBatch; // 新所在批次
	private String newAddress; // 新地址
	private String newPhone; // 新办公电话
	private String newMobilePhone; // 新移动电话
	private PeEnterprise newPeEnterprise; // 新所在企业
	private String newEmail; // 新电子邮件
	private String newPhoto; // 新相片
	private Date alterDate;  //修改时间
	private Date createDate; //创建时间
	private String oldCardNo;//原证件号
	private String oldZjlx;  //原证件类型
	private String oldCardType;//原国籍
	private String newCardNo;//新证件号
	private String newZjlx;//新证件类型
	private String newCardType;//新国籍
	private String status;//迁移来的状态 01 增加 02 修改 03删除
	private String enumConstByFlagEditCheck;//审核状态
	private String note;//备注
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}
	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}
	public String getOldRegNo() {
		return oldRegNo;
	}
	public void setOldRegNo(String oldRegNo) {
		this.oldRegNo = oldRegNo;
	}
	public String getOldTrueName() {
		return oldTrueName;
	}
	public void setOldTrueName(String oldTrueName) {
		this.oldTrueName = oldTrueName;
	}
	public String getOldGender() {
		return oldGender;
	}
	public void setOldGender(String oldGender) {
		this.oldGender = oldGender;
	}
	public Date getOldBirthdayDate() {
		return oldBirthdayDate;
	}
	public void setOldBirthdayDate(Date oldBirthdayDate) {
		this.oldBirthdayDate = oldBirthdayDate;
	}
	public String getOldFolk() {
		return oldFolk;
	}
	public void setOldFolk(String oldFolk) {
		this.oldFolk = oldFolk;
	}
	public String getOldEducation() {
		return oldEducation;
	}
	public void setOldEducation(String oldEducation) {
		this.oldEducation = oldEducation;
	}
	public PeBzzBatch getOldPeBzzBatch() {
		return oldPeBzzBatch;
	}
	public void setOldPeBzzBatch(PeBzzBatch oldPeBzzBatch) {
		this.oldPeBzzBatch = oldPeBzzBatch;
	}
	public String getOldAddress() {
		return oldAddress;
	}
	public void setOldAddress(String oldAddress) {
		this.oldAddress = oldAddress;
	}
	public String getOldPhone() {
		return oldPhone;
	}
	public void setOldPhone(String oldPhone) {
		this.oldPhone = oldPhone;
	}
	public String getOldMobilePhone() {
		return oldMobilePhone;
	}
	public void setOldMobilePhone(String oldMobilePhone) {
		this.oldMobilePhone = oldMobilePhone;
	}
	public Date getAlterDate() {
		return alterDate;
	}
	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}
	public PeEnterprise getOldPeEnterprise() {
		return oldPeEnterprise;
	}
	public void setOldPeEnterprise(PeEnterprise oldPeEnterprise) {
		this.oldPeEnterprise = oldPeEnterprise;
	}
	public String getOldEmail() {
		return oldEmail;
	}
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
	public String getOldPhoto() {
		return oldPhoto;
	}
	public void setOldPhoto(String oldPhoto) {
		this.oldPhoto = oldPhoto;
	}
	public String getNewRegNo() {
		return newRegNo;
	}
	public void setNewRegNo(String newRegNo) {
		this.newRegNo = newRegNo;
	}
	public String getNewTrueName() {
		return newTrueName;
	}
	public void setNewTrueName(String newTrueName) {
		this.newTrueName = newTrueName;
	}
	public String getNewGender() {
		return newGender;
	}
	public void setNewGender(String newGender) {
		this.newGender = newGender;
	}
	public Date getNewBirthdayDate() {
		return newBirthdayDate;
	}
	public void setNewBirthdayDate(Date newBirthdayDate) {
		this.newBirthdayDate = newBirthdayDate;
	}
	public String getNewFolk() {
		return newFolk;
	}
	public void setNewFolk(String newFolk) {
		this.newFolk = newFolk;
	}
	public String getNewEducation() {
		return newEducation;
	}
	public void setNewEducation(String newEducation) {
		this.newEducation = newEducation;
	}
	public PeBzzBatch getNewPeBzzBatch() {
		return newPeBzzBatch;
	}
	public void setNewPeBzzBatch(PeBzzBatch newPeBzzBatch) {
		this.newPeBzzBatch = newPeBzzBatch;
	}
	public String getNewAddress() {
		return newAddress;
	}
	public void setNewAddress(String newAddress) {
		this.newAddress = newAddress;
	}
	public String getNewPhone() {
		return newPhone;
	}
	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}
	public String getNewMobilePhone() {
		return newMobilePhone;
	}
	public void setNewMobilePhone(String newMobilePhone) {
		this.newMobilePhone = newMobilePhone;
	}
	public PeEnterprise getNewPeEnterprise() {
		return newPeEnterprise;
	}
	public void setNewPeEnterprise(PeEnterprise newPeEnterprise) {
		this.newPeEnterprise = newPeEnterprise;
	}
	public String getNewEmail() {
		return newEmail;
	}
	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
	public String getNewPhoto() {
		return newPhoto;
	}
	public void setNewPhoto(String newPhoto) {
		this.newPhoto = newPhoto;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getOldCardNo() {
		return oldCardNo;
	}
	public void setOldCardNo(String oldCardNo) {
		this.oldCardNo = oldCardNo;
	}
	public String getOldZjlx() {
		return oldZjlx;
	}
	public void setOldZjlx(String oldZjlx) {
		this.oldZjlx = oldZjlx;
	}
	public String getOldCardType() {
		return oldCardType;
	}
	public void setOldCardType(String oldCardType) {
		this.oldCardType = oldCardType;
	}
	public String getNewCardNo() {
		return newCardNo;
	}
	public void setNewCardNo(String newCardNo) {
		this.newCardNo = newCardNo;
	}
	public String getNewZjlx() {
		return newZjlx;
	}
	public void setNewZjlx(String newZjlx) {
		this.newZjlx = newZjlx;
	}
	public String getNewCardType() {
		return newCardType;
	}
	public void setNewCardType(String newCardType) {
		this.newCardType = newCardType;
	}
	public String getEnumConstByFlagEditCheck() {
		return enumConstByFlagEditCheck;
	}
	public void setEnumConstByFlagEditCheck(String enumConstByFlagEditCheck) {
		this.enumConstByFlagEditCheck = enumConstByFlagEditCheck;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
