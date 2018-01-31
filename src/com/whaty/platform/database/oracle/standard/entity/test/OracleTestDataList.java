/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.test;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.test.TestDataList;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 *
 */
public class OracleTestDataList implements TestDataList {

	public OracleTestDataList() {
		
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.test.TestDataList#searchTestBatch(com.whaty.platform.util.Page, java.util.List, java.util.List)
	 */
	public List searchTestBatch(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		List testBatchList=new ArrayList();
		return testBatchList;

	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.test.TestDataList#searchTestSequence(com.whaty.platform.util.Page, java.util.List, java.util.List)
	 */
	public List searchTestSequence(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		List testSequenceList=new ArrayList();
		return testSequenceList;

	}

}
