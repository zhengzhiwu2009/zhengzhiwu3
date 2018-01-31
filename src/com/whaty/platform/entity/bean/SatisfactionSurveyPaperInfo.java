package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SatisfactionSurveyPaperInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SatisfactionSurveyPaperInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagIsvalid;
	private String title;
	private String creatuser;
	private Date creatdate;
	private String note;
	private String time;
	private String type;
	private Set satisfactionPaperquestions = new HashSet(0);

	// Constructors

	/** default constructor */
	public SatisfactionSurveyPaperInfo() {
	}

	/** minimal constructor */
	public SatisfactionSurveyPaperInfo(String title) {
		this.title = title;
	}

	/** full constructor */
	public SatisfactionSurveyPaperInfo(EnumConst enumConstByFlagIsvalid, String title,
			String creatuser, Date creatdate, String note, String time,
			String type, Set satisfactionPaperquestions) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.title = title;
		this.creatuser = creatuser;
		this.creatdate = creatdate;
		this.note = note;
		this.time = time;
		this.type = type;
		this.satisfactionPaperquestions = satisfactionPaperquestions;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagIsvalid() {
		return this.enumConstByFlagIsvalid;
	}

	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreatuser() {
		return this.creatuser;
	}

	public void setCreatuser(String creatuser) {
		this.creatuser = creatuser;
	}

	public Date getCreatdate() {
		return this.creatdate;
	}

	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set getSatisfactionPaperquestions() {
		return this.satisfactionPaperquestions;
	}

	public void setSatisfactionPaperquestions(Set satisfactionPaperquestions) {
		this.satisfactionPaperquestions = satisfactionPaperquestions;
	}

}