package com.whaty.platform.database.oracle.standard.test;

import com.whaty.platform.test.TestFactory;
import com.whaty.platform.test.TestManage;
import com.whaty.platform.test.TestPriv;

public class OracleTestFactory extends TestFactory {

	public TestManage creatTestManage(TestPriv testPriv) {
		return new OracleTestManage(testPriv);
	}

	public TestManage creatExpendTestManage(TestPriv testPriv) {
		return new OracleTestManage(testPriv);
	}
	
	public TestPriv getTestPriv() {
		return new OracleTestPriv();
	}

}
