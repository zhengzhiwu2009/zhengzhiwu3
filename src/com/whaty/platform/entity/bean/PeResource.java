package com.whaty.platform.entity.bean;

import java.util.Date;

public class PeResource  extends com.whaty.platform.entity.bean.AbstractBean
implements java.io.Serializable {
	private String id;//主键
	private EnumConst enumConstByFlagIsvalid;//有效
	private Date replyDate;//发布时间
	private String filelink;//资料地址
	private EnumConst enumConstByFlagIsvalidSPxianzai;//资料是否下载
	private EnumConst enumConstByFlagIsvalidSPshouye;//资料是否首页
	//lwq-2015\12\22-增加资料标签
	private String resourceTagIds;
	private String resourceTagNames;
	
	private String content;//资料内容
	private String fabuunit;//发布单位
	private String resetype;//资料类型
	//lwq 2016-01-11 资料类型修改为下拉列表
	private EnumConst enumConstByFlagResourceType; 
	
	private String describe;//资料描述
	private String flagtop;//资料是否置顶
	private String name;//名称
	private EnumConst enumConstByFlagIsvalidSPXiaXian;//是否下线
	private String creater;//创建人
	private Date creationdate;//创建时间 
	
	private EnumConst enumConstByFlagIsAudit;//是否审核通过
	
	private EnumConst enumConstByFlagIsOpen;//是否公开
	
	private int views;
	private String showUsers;
	private EnumConst enumConstByInformationResource;
	public EnumConst getEnumConstByInformationResource() {
		return enumConstByInformationResource;
	}
	public void setEnumConstByInformationResource(
			EnumConst enumConstByInformationResource) {
		this.enumConstByInformationResource = enumConstByInformationResource;
	}
	public String getShowUsers() {
		return showUsers;
	}
	public void setShowUsers(String showUsers) {
		this.showUsers = showUsers;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public EnumConst getEnumConstByFlagResourceType() {
		return enumConstByFlagResourceType;
	}
	public void setEnumConstByFlagResourceType(EnumConst enumConstByFlagResourceType) {
		this.enumConstByFlagResourceType = enumConstByFlagResourceType;
	}
	public EnumConst getEnumConstByFlagIsvalidSPXiaXian() {
		return enumConstByFlagIsvalidSPXiaXian;
	}
	public void setEnumConstByFlagIsvalidSPXiaXian(
			EnumConst enumConstByFlagIsvalidSPXiaXian) {
		this.enumConstByFlagIsvalidSPXiaXian = enumConstByFlagIsvalidSPXiaXian;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public EnumConst getEnumConstByFlagIsvalid() {
		return enumConstByFlagIsvalid;
	}
	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}
	public String getFilelink() {
		return filelink;
	}
	public void setFilelink(String filelink) {
		this.filelink = filelink;
	}
	public EnumConst getEnumConstByFlagIsvalidSPxianzai() {
		return enumConstByFlagIsvalidSPxianzai;
	}
	public void setEnumConstByFlagIsvalidSPxianzai(
			EnumConst enumConstByFlagIsvalidSPxianzai) {
		this.enumConstByFlagIsvalidSPxianzai = enumConstByFlagIsvalidSPxianzai;
	}
	public EnumConst getEnumConstByFlagIsvalidSPshouye() {
		return enumConstByFlagIsvalidSPshouye;
	}
	public void setEnumConstByFlagIsvalidSPshouye(
			EnumConst enumConstByFlagIsvalidSPshouye) {
		this.enumConstByFlagIsvalidSPshouye = enumConstByFlagIsvalidSPshouye;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFabuunit() {
		return fabuunit;
	}
	public void setFabuunit(String fabuunit) {
		this.fabuunit = fabuunit;
	}
	public String getResetype() {
		return resetype;
	}
	public void setResetype(String resetype) {
		this.resetype = resetype;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getFlagtop() {
		return flagtop;
	}
	public void setFlagtop(String flagtop) {
		this.flagtop = flagtop;
	}
	public PeResource() {
		super();
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public PeResource(String id, EnumConst enumConstByFlagIsvalid,
			Date replyDate, String filelink,
			EnumConst enumConstByFlagIsvalidSPxianzai,
			EnumConst enumConstByFlagIsvalidSPshouye, String content,
			String fabuunit, String resetype, String describe, String flagtop,
			String name, EnumConst enumConstByFlagIsvalidSPXiaXian,
			String creater, Date creationdate) {
		super();
		this.id = id;
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.replyDate = replyDate;
		this.filelink = filelink;
		this.enumConstByFlagIsvalidSPxianzai = enumConstByFlagIsvalidSPxianzai;
		this.enumConstByFlagIsvalidSPshouye = enumConstByFlagIsvalidSPshouye;
		this.content = content;
		this.fabuunit = fabuunit;
		this.resetype = resetype;
		this.describe = describe;
		this.flagtop = flagtop;
		this.name = name;
		this.enumConstByFlagIsvalidSPXiaXian = enumConstByFlagIsvalidSPXiaXian;
		this.creater = creater;
		this.creationdate = creationdate;
	}
	public Date getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}
	public String getResourceTagIds() {
		return resourceTagIds;
	}
	public void setResourceTagIds(String resourceTagIds) {
		this.resourceTagIds = resourceTagIds;
	}
	public String getResourceTagNames() {
		return resourceTagNames;
	}
	public void setResourceTagNames(String resourceTagNames) {
		this.resourceTagNames = resourceTagNames;
	}
	public EnumConst getEnumConstByFlagIsAudit() {
		return enumConstByFlagIsAudit;
	}
	public void setEnumConstByFlagIsAudit(EnumConst enumConstByFlagIsAudit) {
		this.enumConstByFlagIsAudit = enumConstByFlagIsAudit;
	}
	public EnumConst getEnumConstByFlagIsOpen() {
		return enumConstByFlagIsOpen;
	}
	public void setEnumConstByFlagIsOpen(EnumConst enumConstByFlagIsOpen) {
		this.enumConstByFlagIsOpen = enumConstByFlagIsOpen;
	}

}