package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzStudent generated by MyEclipse - Hibernate Tools
 */

public class PeBzzCertificate extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	
	private PeBzzStudent peBzzStudent;	//学生
	
	private String certificate;//证书号
	
	private Date printDate;//打印时间
	
	private PeEnterprise peSite;	//管理员所在的企业
	private PeBzzExamBatch peBzzExamBatch;	//证书所在考试批次

	// Constructors

	/** default constructor */
	public PeBzzCertificate() {
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


	public String getCertificate() {
		return certificate;
	}


	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}


	public Date getPrintDate() {
		return printDate;
	}


	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}


	public PeEnterprise getPeSite() {
		return peSite;
	}


	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}


	public PeBzzExamBatch getPeBzzExamBatch() {
		return peBzzExamBatch;
	}


	public void setPeBzzExamBatch(PeBzzExamBatch peBzzExamBatch) {
		this.peBzzExamBatch = peBzzExamBatch;
	}


	// Property accessors
}