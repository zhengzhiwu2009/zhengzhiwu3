package com.whaty.platform.entity.bean;

import java.util.Date;

public class TrainingCourseStudentHistory extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {

	// Fields    

    private String id;
    private SsoUser ssoUser;
    private PrBzzTchOpencourse prBzzTchOpencourse;
    private String status;
    private Date getDate;
    private Double percent;
    private String learnStatus;
    private Long score;
  
    private Date completeDate;    //新增学习完成时间


   // Constructors

   public Date getCompleteDate() {
		return completeDate;
	}


	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}


	/** default constructor */
   public TrainingCourseStudentHistory() {
   }

   
   /** full constructor */
   public TrainingCourseStudentHistory(SsoUser ssoUser, PrBzzTchOpencourse prBzzTchOpencourse, String status, 
   			Date getDate, Double percent, String learnStatus, Long score) {
       this.ssoUser = ssoUser;
       this.prBzzTchOpencourse = prBzzTchOpencourse;
       this.status = status;
       this.getDate = getDate;
       this.percent = percent;
       this.learnStatus = learnStatus;
       this.score = score;
      
   }

  
   // Property accessors

   public String getId() {
       return this.id;
   }
   
   public void setId(String id) {
       this.id = id;
   }

   public PrBzzTchOpencourse getPrBzzTchOpencourse() {
       return this.prBzzTchOpencourse;
   }
   
   public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
       this.prBzzTchOpencourse = prBzzTchOpencourse;
   }

   public String getStatus() {
       return this.status;
   }
   
   public void setStatus(String status) {
       this.status = status;
   }

   public Date getGetDate() {
       return this.getDate;
   }
   
   public void setGetDate(Date getDate) {
       this.getDate = getDate;
   }

   public Double getPercent() {
       return this.percent;
   }
   
   public void setPercent(Double percent) {
       this.percent = percent;
   }

   public String getLearnStatus() {
       return this.learnStatus;
   }
   
   public void setLearnStatus(String learnStatus) {
       this.learnStatus = learnStatus;
   }

   public Long getScore() {
       return this.score;
   }
   
   public void setScore(Long score) {
       this.score = score;
   }
  

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

}
