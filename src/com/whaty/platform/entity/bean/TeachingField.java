package com.whaty.platform.entity.bean;

public class TeachingField extends AbstractBean implements java.io.Serializable {
	private String id;
	private String name;
	private String order;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
}
