package com.whaty.platform.entity.bean;

public class PrSignUpOrderCourse extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable{
	
	private String id;
	private PeSignUpOrder peSignUpOrder;
	private PeBzzTchCourse peBzzTchCourse;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PeSignUpOrder getPeSignUpOrder() {
		return peSignUpOrder;
	}
	public void setPeSignUpOrder(PeSignUpOrder peSignUpOrder) {
		this.peSignUpOrder = peSignUpOrder;
	}
	public PeBzzTchCourse getPeBzzTchCourse() {
		return peBzzTchCourse;
	}
	public void setPeBzzTchCourse(PeBzzTchCourse peBzzTchCourse) {
		this.peBzzTchCourse = peBzzTchCourse;
	}
	
}
