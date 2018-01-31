package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

public class PeBzzExamSite extends AbstractBean implements java.io.Serializable{

	private String id;					//考试地点ID
	private String name;				//考试地点名称
	private String sheng;				//考生地点省份
	private String note;				//备注
	private String shi;                 //考点所在市
	private String address;             //考点详细地址
	private Long capacity;              //考点容量
//	private Set peBzzExamStudents = new HashSet(0); //报名学生
	
	private PeEnterprise peSite;	//管理员所在的企业
	

	@Override
	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSheng() {
		return sheng;
	}


	public void setSheng(String sheng) {
		this.sheng = sheng;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


//	public Set getPeBzzExamStudents() {
//		return peBzzExamStudents;
//	}
//
//
//	public void setPeBzzExamStudents(Set peBzzExamStudents) {
//		this.peBzzExamStudents = peBzzExamStudents;
//	}


	public void setId(String id) {
		this.id = id;
	}


	public String getShi() {
		return shi;
	}


	public void setShi(String shi) {
		this.shi = shi;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Long getCapacity() {
		return capacity;
	}


	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}


	public PeEnterprise getPeSite() {
		return peSite;
	}


	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}
	
}
