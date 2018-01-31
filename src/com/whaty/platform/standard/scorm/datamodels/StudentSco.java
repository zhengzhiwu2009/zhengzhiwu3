package com.whaty.platform.standard.scorm.datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生学习记录
 * @author Huze
 */
public class StudentSco {
	
	private String id;
	private String sid;//sco id
	private String title;//sco title
	private String duration;//online time
	private String status;//complete status
	private String score;//score
	private String href;//sco link
	private int level;
	List<StudentSco> item = new ArrayList<StudentSco>();//children
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public List<StudentSco> getItem() {
		return item;
	}
	public void setItem(List<StudentSco> item) {
		this.item = item;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
