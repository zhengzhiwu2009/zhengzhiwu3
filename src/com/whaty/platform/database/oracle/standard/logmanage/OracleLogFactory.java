/**
 * 
 */
package com.whaty.platform.database.oracle.standard.logmanage;

import com.whaty.platform.logmanage.LogFactory;
import com.whaty.platform.logmanage.LogManage;

/**
 * @author wq
 *
 */
public class OracleLogFactory extends LogFactory {

	/* (non-Javadoc)
	 * @see com.whaty.platform.logmanage.LogFactory#creatLogManage()
	 */
	public LogManage creatLogManage() {
		return new OracleLogManage();
	}

}
