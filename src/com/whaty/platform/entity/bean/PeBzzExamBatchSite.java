package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

public class PeBzzExamBatchSite extends AbstractBean implements java.io.Serializable{

	private String id; //关系ID
	private PeBzzExamBatch peBzzExamBatch;//考试批次ID
	private PeBzzExamSite peBzzExamSite;//考点ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PeBzzExamBatch getPeBzzExamBatch() {
		return peBzzExamBatch;
	}
	public void setPeBzzExamBatch(PeBzzExamBatch peBzzExamBatch) {
		this.peBzzExamBatch = peBzzExamBatch;
	}
	public PeBzzExamSite getPeBzzExamSite() {
		return peBzzExamSite;
	}
	public void setPeBzzExamSite(PeBzzExamSite peBzzExamSite) {
		this.peBzzExamSite = peBzzExamSite;
	}
	
}
