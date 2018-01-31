package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzTeachingLiveroom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzTeachingLiveroom extends AbstractBean implements java.io.Serializable {

	// Fields

	private String id;
	private SsoUser ssoUser;
	private String name;
	private Integer roomId;
	private Date beginDate;
	private Date endDate;
	private String note;
	private Date dataDate;
	private String isOpen;

	// Constructors

	/** default constructor */
	public PeBzzTeachingLiveroom() {
	}

	/** full constructor */
	public PeBzzTeachingLiveroom(SsoUser ssoUser, String name, Integer roomId,
			Date beginDate, Date endDate, String note, Date dataDate,String isOpen) {
		this.ssoUser = ssoUser;
		this.name = name;
		this.roomId = roomId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.note = note;
		this.dataDate = dataDate;
		this.isOpen=isOpen;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

}