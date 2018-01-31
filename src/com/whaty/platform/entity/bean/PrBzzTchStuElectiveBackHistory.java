package com.whaty.platform.entity.bean;

/**
 * 网银支付未支付时订单产生时的选课记录，存在这张表中
 * @author Administrator
 *
 */
public class PrBzzTchStuElectiveBackHistory extends
com.whaty.platform.entity.bean.AbstractBean implements
java.io.Serializable {


		// Fields

		private String id;
		private SsoUser ssoUser;
		private PrBzzTchOpencourse prBzzTchOpencourse;
		private PeBzzStudent peBzzStudent;
		private PeBzzOrderInfo peBzzOrderInfo;
		private PeElectiveCoursePeriod peElectiveCoursePeriod;

		// Constructors

		/** default constructor */
		public PrBzzTchStuElectiveBackHistory() {
		}


		/** full constructor */
		public PrBzzTchStuElectiveBackHistory(
				SsoUser ssoUser, 
				PrBzzTchOpencourse prBzzTchOpencourse, 
				PeBzzStudent peBzzStudent,
				PeElectiveCoursePeriod peElectiveCoursePeriod) {
			
			
			this.ssoUser = ssoUser;
			this.prBzzTchOpencourse = prBzzTchOpencourse;
			this.peBzzStudent = peBzzStudent;
			this.peElectiveCoursePeriod = peElectiveCoursePeriod;
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

		
		public PrBzzTchOpencourse getPrBzzTchOpencourse() {
			return this.prBzzTchOpencourse;
		}

		public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
			this.prBzzTchOpencourse = prBzzTchOpencourse;
		}

		public PeBzzStudent getPeBzzStudent() {
			return this.peBzzStudent;
		}

		public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
			this.peBzzStudent = peBzzStudent;
		}



		public PeBzzOrderInfo getPeBzzOrderInfo() {
			return peBzzOrderInfo;
		}

		public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
			this.peBzzOrderInfo = peBzzOrderInfo;
		}

		public PeElectiveCoursePeriod getPeElectiveCoursePeriod() {
			return peElectiveCoursePeriod;
		}

		public void setPeElectiveCoursePeriod(
				PeElectiveCoursePeriod peElectiveCoursePeriod) {
			this.peElectiveCoursePeriod = peElectiveCoursePeriod;
		}

}
