package com.whaty.platform.config;

public class ForeignKey {
	
	private String keyName;
	
	private String parentTable;
	
	private String childTable;

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getParentTable() {
		return parentTable;
	}

	public void setParentTable(String parentTable) {
		this.parentTable = parentTable;
	}

	public String getChildTable() {
		return childTable;
	}

	public void setChildTable(String childTable) {
		this.childTable = childTable;
	}
	
	

}
