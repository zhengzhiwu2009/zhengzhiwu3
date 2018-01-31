package com.whaty.platform.entity.bean;

import java.util.Date;
/**
 * 推荐系列表
 * @author zhang
 *
 */
public class RecommendSeries extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {

	private static final long serialVersionUID = 1237695689328884829L;
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	private String id;
	private String name; //推荐系列名称
	private String descs; //推荐系列描述
	private EnumConst enumConstByisShowIndex;//是否首页显示
	private EnumConst enumConstByisShowList;//是否首页在列表
	private String createUser; //创建用户
	private Date createDate; //创建时间
	private String photoLink;
	private String isTop;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public EnumConst getEnumConstByisShowIndex() {
		return enumConstByisShowIndex;
	}
	public void setEnumConstByisShowIndex(EnumConst enumConstByisShowIndex) {
		this.enumConstByisShowIndex = enumConstByisShowIndex;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhotoLink() {
		return photoLink;
	}
	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}
	public EnumConst getEnumConstByisShowList() {
		return enumConstByisShowList;
	}
	public void setEnumConstByisShowList(EnumConst enumConstByisShowList) {
		this.enumConstByisShowList = enumConstByisShowList;
	}
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}	
}
