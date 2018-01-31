package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PeSignUpOrder extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable{
	
	private String id;
	private String orderSequence;
	private String note;
	private Date addDate;
	private PeEnterpriseManager peEnterpriseManager;
	private EnumConst enumConstByFlagElectivePayStatus;
	private Set orderDetailsSet = new HashSet(0);
	private Set prSignUpStudentSet = new HashSet(0);
	private Set prSignUpCourseSet = new HashSet(0);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(String orderSequence) {
		this.orderSequence = orderSequence;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	public PeEnterpriseManager getPeEnterpriseManager() {
		return peEnterpriseManager;
	}
	public void setPeEnterpriseManager(PeEnterpriseManager peEnterpriseManager) {
		this.peEnterpriseManager = peEnterpriseManager;
	}
	public EnumConst getEnumConstByFlagElectivePayStatus() {
		return enumConstByFlagElectivePayStatus;
	}
	public void setEnumConstByFlagElectivePayStatus(
			EnumConst enumConstByFlagElectivePayStatus) {
		this.enumConstByFlagElectivePayStatus = enumConstByFlagElectivePayStatus;
	}
	public Set getOrderDetailsSet() {
		return orderDetailsSet;
	}
	public void setOrderDetailsSet(Set orderDetailsSet) {
		this.orderDetailsSet = orderDetailsSet;
	}
	public Set getPrSignUpStudentSet() {
		return prSignUpStudentSet;
	}
	public void setPrSignUpStudentSet(Set prSignUpStudentSet) {
		this.prSignUpStudentSet = prSignUpStudentSet;
	}
	public Set getPrSignUpCourseSet() {
		return prSignUpCourseSet;
	}
	public void setPrSignUpCourseSet(Set prSignUpCourseSet) {
		this.prSignUpCourseSet = prSignUpCourseSet;
	}
	
}
