package com.whaty.platform.entity.bean;

public class PrSignUpOrderDetail extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable{
	
	private String id;
	private PeBzzStudent peBzzStudent;
	private PeBzzTchCourse peBzzTchCourse;
	private PeSignUpOrder peSignUpOrder;
	
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
	public PeBzzTchCourse getPeBzzTchCourse() {
		return peBzzTchCourse;
	}
	public void setPeBzzTchCourse(PeBzzTchCourse peBzzTchCourse) {
		this.peBzzTchCourse = peBzzTchCourse;
	}
	public PeSignUpOrder getPeSignUpOrder() {
		return peSignUpOrder;
	}
	public void setPeSignUpOrder(PeSignUpOrder peSignUpOrder) {
		this.peSignUpOrder = peSignUpOrder;
	}
	
}
