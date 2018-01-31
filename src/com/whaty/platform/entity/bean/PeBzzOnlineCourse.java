package com.whaty.platform.entity.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PeBzzOnlineCourse extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {

	private String id;	
	private String code; //编号
	private String subject; //讲座主题	
	
	private Date startDate; //开始日期
	private Date endDate;  //结束日期
	
	private Date courseDate; //日期（从开始日期中获取）
	private String startTime;  //开始时间（从开始日期中获取）
	private String endTime;	  //结束时间（从结束日期中获取）
	
	private String emcee; //主持人
	private String flashImage; //浮动图片地址
	private String indexUrl; //课件首页链接地址
	private String bz;
	
	private Integer precedeDays;  //浮动图片提前天数
	
	private PeEnterprise peSite;	//管理员所在的企业
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getEmcee() {
		return emcee;
	}

	public void setEmcee(String emcee) {
		this.emcee = emcee;
	}

	public String getFlashImage() {
		return flashImage;
	}

	public void setFlashImage(String flashImage) {
		this.flashImage = flashImage;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCourseDate() {
		if(this.courseDate == null) {
			this.courseDate = this.getStartDate();
		}
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		if(this.startDate == null) {
			this.startDate = new Date();
		}
		
		if(courseDate == null) {
			this.courseDate = courseDate;
			return ;
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(this.startDate);
		
		String[] temp = this.getDateDate(courseDate).split("-");
		int yyyy = Integer.parseInt(temp[0]);
		int MM = Integer.parseInt(temp[1]);
		int dd = Integer.parseInt(temp[2]);
		c.set(Calendar.YEAR, yyyy);
		c.set(Calendar.MONTH, MM-1);
		c.set(Calendar.DAY_OF_MONTH, dd);
		
		this.startDate = c.getTime();
		
		if(this.endDate == null) {
			this.endDate = this.startDate;
		}
		
		c = Calendar.getInstance();
		c.setTime(this.endDate);
		
		c.set(Calendar.YEAR, yyyy);
		c.set(Calendar.MONTH, MM-1);
		c.set(Calendar.DAY_OF_MONTH, dd);
		
		this.endDate = c.getTime();
		
		this.courseDate = courseDate;
	}
	
	public String getStartTime() {
		if(this.startTime == null) {
			this.startTime = this.getDateTime(this.getStartDate());
		}
		return startTime;
	}

	public void setStartTime(String startTime) {
		if(this.startDate == null) {
			this.startDate = new Date();
		}
		
		if(startTime == null) {
			this.startTime = startTime;
			return ;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(this.startDate);
		
		String[] temp = startTime.split(":");
		int hh = Integer.parseInt(temp[0]);
		int mm = Integer.parseInt(temp[1]);
		int ss = 0;
		c.set(Calendar.HOUR_OF_DAY, hh);
		c.set(Calendar.MINUTE, mm);
		c.set(Calendar.SECOND, ss);
		
		this.startTime = startTime;
		this.startDate = c.getTime();
	}

	public String getEndTime() {
		if(this.endTime == null) {
			this.endTime = this.getDateTime(this.getEndDate());
		}
		return endTime;
	}

	public void setEndTime(String endTime) {
		if(this.endDate == null) {
			this.endDate = new Date();
		}
		
		if(endTime == null) {
			this.endTime = endTime;
			return ;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(this.endDate);
		
		String[] temp = endTime.split(":");
		int hh = Integer.parseInt(temp[0]);
		int mm = Integer.parseInt(temp[1]);
		int ss = 0;
		c.set(Calendar.HOUR_OF_DAY, hh);
		c.set(Calendar.MINUTE, mm);
		c.set(Calendar.SECOND, ss);
		
		this.endTime = endTime;
		this.endDate = c.getTime();
	}
	
	private String getDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		if(date == null) {
			return null;
		}
		return sdf.format(date);
	}
	
	private String getDateDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(date == null) {
			return null;
		}
		return sdf.format(date);
	}
	
	public String formateCourseDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.getCourseDate());
	}
	
	public boolean itsCurrentCourse() {
		Date now = new Date();
		if(this.startDate != null && this.endDate != null) {
			if(this.startDate.before(now) && this.endDate.after(now)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void setCurrentCourse(boolean currentCourse){
	}

	public Integer getPrecedeDays() {
		return precedeDays;
	}

	public void setPrecedeDays(Integer precedeDays) {
		this.precedeDays = precedeDays;
	}

	public PeEnterprise getPeSite() {
		return peSite;
	}

	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}

}
