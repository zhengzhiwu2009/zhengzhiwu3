package com.whaty.platform.entity.bean;

public class StudentBatch extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	/**
	 * 主键
	 */
	private String id;
	
	private PeBzzStudent peStudent;
	
	private PeBzzBatch peBzzBatch;
	
	private PeBzzOrderInfo peBzzOrderInfo;
	
	private EnumConst enumConstByFlagBatchPayState;
	
	public StudentBatch(){
	}
	
	public StudentBatch(String id,PeBzzStudent peStudent,PeBzzBatch peBzzBatch,PeBzzOrderInfo peBzzOrderInfo,EnumConst enumConstByFlagBatchPayState){
		this.id = id;
		this.peBzzBatch = peBzzBatch;
		this.peStudent = peStudent;
		this.peBzzOrderInfo = peBzzOrderInfo;
		this.enumConstByFlagBatchPayState = enumConstByFlagBatchPayState;
	}
	
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	

	public PeBzzStudent getPeStudent() {
		return peStudent;
	}



	public void setPeStudent(PeBzzStudent peStudent) {
		this.peStudent = peStudent;
	}



	public PeBzzBatch getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagBatchPayState() {
		return enumConstByFlagBatchPayState;
	}

	public void setEnumConstByFlagBatchPayState(EnumConst enumConstByFlagBatchPayState) {
		this.enumConstByFlagBatchPayState = enumConstByFlagBatchPayState;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

}
