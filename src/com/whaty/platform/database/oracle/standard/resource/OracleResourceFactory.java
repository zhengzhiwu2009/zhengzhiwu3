package com.whaty.platform.database.oracle.standard.resource;

import com.whaty.platform.resource.BasicResourceManage;
import com.whaty.platform.resource.ResourceFactory;

public class OracleResourceFactory extends ResourceFactory {

	public BasicResourceManage creatBasicResourceManage() {
		return new OracleBasicResourceManage();
	}

}
