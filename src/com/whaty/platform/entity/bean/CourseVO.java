package com.whaty.platform.entity.bean;

import java.io.Serializable;

public class CourseVO implements Serializable{
	
	private String id;
	private String name;
	private String courseType;
	private String courseCategory;
	private String itemType;
	private String isValid;
	private String canJoinBatch;
	private String code;
	
	public String getId(){
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getCourseCategory() {
		return courseCategory;
	}
	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getCanJoinBatch() {
		return canJoinBatch;
	}
	public void setCanJoinBatch(String canJoinBatch) {
		this.canJoinBatch = canJoinBatch;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
