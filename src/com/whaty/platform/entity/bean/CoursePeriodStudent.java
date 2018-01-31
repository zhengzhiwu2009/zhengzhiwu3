package com.whaty.platform.entity.bean;

public class CoursePeriodStudent extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable {

	
	private String id;
	private PeBzzStudent peBzzStudent;
	private PeElectiveCoursePeriod peElectiveCoursePeriod;
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
	public PeElectiveCoursePeriod getPeElectiveCoursePeriod() {
		return peElectiveCoursePeriod;
	}
	public void setPeElectiveCoursePeriod(
			PeElectiveCoursePeriod peElectiveCoursePeriod) {
		this.peElectiveCoursePeriod = peElectiveCoursePeriod;
	}
	


}
