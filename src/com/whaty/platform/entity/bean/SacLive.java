package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * 在线直播实体类
 * 
 * @author Lee 2014年6月4日09:32:25
 * 
 */
public class SacLive extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {
	// 唯一标识ID
	private String id;
	// 课程编号
	private String code;
	// 课程名称
	private String name;
	// 课程性质
	private EnumConst enumConstByFlagCourseType;
	// 业务分类
	private EnumConst enumConstByFlagCourseCategory;
	// 大纲分类
	private EnumConst enumConstByFlagCourseItemType;
	// 内容属性分类
	private EnumConst enumConstByFlagContentProperty;
	// 是否设置了建议学习人群
	private EnumConst enumConstByFlagSuggestSet;
	// 金额
	private String price;
	// 报名开始时间
	private Date ssDate;
	// 报名结束时间
	private Date seDate;
	// 预计学时数
	private String yjTime;
	// 累计时长(分钟)
	private String liveLen;
	// 实际学时数
	private String sjTime;
	// 预计直播开始时间
	private Date ysDatetime;
	// 预计直播结束时间
	private Date yeDatetime;
	// 直播链接
	private String urllink;
	// 课程简介
	private String courseNote;
	// 主讲人简介
	private String teaNote;
	// 主讲人
	private String teacher;
	// 通过规则
	private EnumConst enumConstByFlagPassrole;
	// 学习建议
	private String suggestion;
	// 课程图片地址
	private String photoLink;
	// 图片状态 ENUM_CONST(码表)外键
	private EnumConst enumConstByFlagImg;
	// 主讲人照片
	private String teaImg;
	// 主讲人照片状态 ENUM_CONST(码表)外键
	private EnumConst enumConstByFlagTeaimg;
	// 发布状态 ENUM_CONST(码表)外键
	private EnumConst enumConstByFlagAnnounce;
	// 下线状态 ENUM_CONST(码表)外键
	private EnumConst enumConstByFlagOffline;
	// 满意度ID
	private String satisfactionId;
	// 创建时间
	private Date createDate;
	// 编辑时间
	private Date editDate;
	// 主讲人描述
	private String teaDesc;
	// 操作日志
	private String operationLogs;
	// (同意)发布日期
	private Date announceDate;
	// 满意度是否必填 ENUM_CONST(码表)外键
	private EnumConst enumConstByFlagSatisfaction;
	// 创建人
	private String creater;
	// 编辑人
	private String editor;
	// 直播ID(可在直播链接中截取)
	private String liveId;
	// 是否采集过数据 ENUM_CONST(码表)外键
	private EnumConst enumConstByFlagLiveData;
	// 是否分配完学时 ENUM_CONST(码表)外键
	private EnumConst enumConstByFlagLiveTime;
	// 直播密码
	private String liveStuPwd;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EnumConst getEnumConstByFlagCourseType() {
		return enumConstByFlagCourseType;
	}

	public void setEnumConstByFlagCourseType(EnumConst enumConstByFlagCourseType) {
		this.enumConstByFlagCourseType = enumConstByFlagCourseType;
	}

	public EnumConst getEnumConstByFlagCourseCategory() {
		return enumConstByFlagCourseCategory;
	}

	public void setEnumConstByFlagCourseCategory(
			EnumConst enumConstByFlagCourseCategory) {
		this.enumConstByFlagCourseCategory = enumConstByFlagCourseCategory;
	}

	public EnumConst getEnumConstByFlagCourseItemType() {
		return enumConstByFlagCourseItemType;
	}

	public void setEnumConstByFlagCourseItemType(
			EnumConst enumConstByFlagCourseItemType) {
		this.enumConstByFlagCourseItemType = enumConstByFlagCourseItemType;
	}

	public EnumConst getEnumConstByFlagContentProperty() {
		return enumConstByFlagContentProperty;
	}

	public void setEnumConstByFlagContentProperty(
			EnumConst enumConstByFlagContentProperty) {
		this.enumConstByFlagContentProperty = enumConstByFlagContentProperty;
	}

	public EnumConst getEnumConstByFlagSuggestSet() {
		return enumConstByFlagSuggestSet;
	}

	public void setEnumConstByFlagSuggestSet(
			EnumConst enumConstByFlagFlagSuggestSet) {
		this.enumConstByFlagSuggestSet = enumConstByFlagFlagSuggestSet;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getSsDate() {
		return ssDate;
	}

	public void setSsDate(Date ssDate) {
		this.ssDate = ssDate;
	}

	public Date getSeDate() {
		return seDate;
	}

	public void setSeDate(Date seDate) {
		this.seDate = seDate;
	}

	public String getYjTime() {
		return yjTime;
	}

	public void setYjTime(String yjTime) {
		this.yjTime = yjTime;
	}

	public String getLiveLen() {
		return liveLen;
	}

	public void setLiveLen(String liveLen) {
		this.liveLen = liveLen;
	}

	public String getSjTime() {
		return sjTime;
	}

	public void setSjTime(String sjTime) {
		this.sjTime = sjTime;
	}

	public Date getYsDatetime() {
		return ysDatetime;
	}

	public void setYsDatetime(Date ysDatetime) {
		this.ysDatetime = ysDatetime;
	}

	public Date getYeDatetime() {
		return yeDatetime;
	}

	public void setYeDatetime(Date yeDatetime) {
		this.yeDatetime = yeDatetime;
	}

	public String getUrllink() {
		return urllink;
	}

	public void setUrllink(String urllink) {
		this.urllink = urllink;
	}

	public String getCourseNote() {
		return courseNote;
	}

	public void setCourseNote(String courseNote) {
		this.courseNote = courseNote;
	}

	public String getTeaNote() {
		return teaNote;
	}

	public void setTeaNote(String teaNote) {
		this.teaNote = teaNote;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public EnumConst getEnumConstByFlagPassrole() {
		return enumConstByFlagPassrole;
	}

	public void setEnumConstByFlagPassrole(EnumConst enumConstByFlagPassrole) {
		this.enumConstByFlagPassrole = enumConstByFlagPassrole;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getPhotoLink() {
		return photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	public EnumConst getEnumConstByFlagImg() {
		return enumConstByFlagImg;
	}

	public void setEnumConstByFlagImg(EnumConst enumConstByFlagImg) {
		this.enumConstByFlagImg = enumConstByFlagImg;
	}

	public String getTeaImg() {
		return teaImg;
	}

	public void setTeaImg(String teaImg) {
		this.teaImg = teaImg;
	}

	public EnumConst getEnumConstByFlagTeaimg() {
		return enumConstByFlagTeaimg;
	}

	public void setEnumConstByFlagTeaimg(EnumConst enumConstByFlagTeaimg) {
		this.enumConstByFlagTeaimg = enumConstByFlagTeaimg;
	}

	public EnumConst getEnumConstByFlagAnnounce() {
		return enumConstByFlagAnnounce;
	}

	public void setEnumConstByFlagAnnounce(EnumConst enumConstByFlagAnnounce) {
		this.enumConstByFlagAnnounce = enumConstByFlagAnnounce;
	}

	public EnumConst getEnumConstByFlagOffline() {
		return enumConstByFlagOffline;
	}

	public void setEnumConstByFlagOffline(EnumConst enumConstByFlagOffline) {
		this.enumConstByFlagOffline = enumConstByFlagOffline;
	}

	public String getSatisfactionId() {
		return satisfactionId;
	}

	public void setSatisfactionId(String satisfactionId) {
		this.satisfactionId = satisfactionId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getTeaDesc() {
		return teaDesc;
	}

	public void setTeaDesc(String teaDesc) {
		this.teaDesc = teaDesc;
	}

	public String getOperationLogs() {
		return operationLogs;
	}

	public void setOperationLogs(String operationLogs) {
		this.operationLogs = operationLogs;
	}

	public Date getAnnounceDate() {
		return announceDate;
	}

	public void setAnnounceDate(Date announceDate) {
		this.announceDate = announceDate;
	}

	public EnumConst getEnumConstByFlagSatisfaction() {
		return enumConstByFlagSatisfaction;
	}

	public void setEnumConstByFlagSatisfaction(
			EnumConst enumConstByFlagSatisfaction) {
		this.enumConstByFlagSatisfaction = enumConstByFlagSatisfaction;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getLiveId() {
		return liveId;
	}

	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}

	public EnumConst getEnumConstByFlagLiveData() {
		return enumConstByFlagLiveData;
	}

	public void setEnumConstByFlagLiveData(EnumConst enumConstByFlagLiveData) {
		this.enumConstByFlagLiveData = enumConstByFlagLiveData;
	}

	public EnumConst getEnumConstByFlagLiveTime() {
		return enumConstByFlagLiveTime;
	}

	public void setEnumConstByFlagLiveTime(EnumConst enumConstByFlagLiveTime) {
		this.enumConstByFlagLiveTime = enumConstByFlagLiveTime;
	}

	public String getLiveStuPwd() {
		return liveStuPwd;
	}

	public void setLiveStuPwd(String liveStuPwd) {
		this.liveStuPwd = liveStuPwd;
	}
}
