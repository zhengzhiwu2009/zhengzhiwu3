package com.whaty.platform.entity.web.util;

import java.io.Serializable;
import java.util.Map;

public class ParamVO   implements Serializable {

	private String uri;
	private Map<String,Object> params;//缓存的数据
	
	public ParamVO(String uri,Map<String,Object> params){
		this.uri = uri;
		this.params = params;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	
	
	
}
