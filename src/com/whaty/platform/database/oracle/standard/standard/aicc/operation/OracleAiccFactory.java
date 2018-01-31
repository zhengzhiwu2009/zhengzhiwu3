package com.whaty.platform.database.oracle.standard.standard.aicc.operation;

import com.whaty.platform.standard.aicc.operation.AiccCourseManage;
import com.whaty.platform.standard.aicc.operation.AiccDataManage;
import com.whaty.platform.standard.aicc.operation.AiccFactory;
import com.whaty.platform.standard.aicc.operation.AiccFileManage;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManage;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManagePriv;

public class OracleAiccFactory extends AiccFactory {

	public AiccDataManage creatAiccDataManage() {
		return new OracleAiccDataManage();
	}

	public AiccFileManage creatAiccFileManage() {
		return new OracleAiccFileManage();
	}

	public AiccCourseManage creatAiccCourseManage() {
		return new OracleAiccCourseManage();
	}

	public AiccUserDataManage creatAiccUserDataManage() {
		return new OracleAiccUserDataManage();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.aicc.operation.AiccFactory#creatAiccUserDataManagePriv()
	 */
	public AiccUserDataManagePriv creatAiccUserDataManagePriv() {
		// TODO Auto-generated method stub
		return new AiccUserDataManagePriv();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.aicc.operation.AiccFactory#creatAiccUserDataManage(com.whaty.platform.standard.aicc.operation.AiccUserDataManagePriv)
	 */
	public AiccUserDataManage creatAiccUserDataManage(AiccUserDataManagePriv userPriv) {
		return new OracleAiccUserDataManage(userPriv);
	}

}
