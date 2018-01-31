/**
 * 
 */
package com.whaty.platform.database.oracle.standard.standard.scorm.operation;

import com.whaty.platform.standard.scorm.LMSManifestHandler;
import com.whaty.platform.standard.scorm.operation.ScormFactory;
import com.whaty.platform.standard.scorm.operation.ScormManage;
import com.whaty.platform.standard.scorm.operation.ScormStudentManage;
import com.whaty.platform.standard.scorm.operation.ScormStudentPriv;

/**
 * @author Administrator
 *
 */
public class OracleScormFactory extends ScormFactory {

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormFactory#creatScormManage()
	 */
	public ScormManage creatScormManage() {
		return new OracleScormManage();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormFactory#creatLMSManifestHandler()
	 */
	public LMSManifestHandler creatLMSManifestHandler() {
		return new OracleLMSManifestHandler();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormFactory#creatScormStudentManage(com.whaty.platform.standard.scorm.operation.ScormStudentPriv)
	 */
	
	public ScormStudentManage creatScormStudentManage(ScormStudentPriv studentPriv) {
		return new OracleScormStudentManage(studentPriv);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormFactory#creatScormStudentPriv()
	 */
	
	public ScormStudentPriv creatScormStudentPriv() {
		return new OracleScormStudentPriv();
	}

}
