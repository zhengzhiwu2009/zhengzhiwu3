/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingClassManager;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingManager;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudent;
import com.whaty.platform.leaveword.LeaveWordFactory;
import com.whaty.platform.leaveword.LeaveWordNormalManage;
import com.whaty.platform.training.TrainingPubManage;
import com.whaty.platform.training.user.TrainingUser;
import com.whaty.platform.training.user.TrainingUserType;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingPubManage extends TrainingPubManage {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.TrainingPubManage#getTrainingUser(java.lang.String,
	 *      java.lang.String)
	 */

	public TrainingUser getTrainingUser(String userId, String type)
			throws PlatformException {
		TrainingUser user = null;
		if (type.equalsIgnoreCase(TrainingUserType.STUDENT)) {
			return new OracleTrainingStudent(userId);

		} else if (type.equalsIgnoreCase(TrainingUserType.MANAGER)) {
			return new OracleTrainingManager(userId);
		} else if (type.equalsIgnoreCase(TrainingUserType.CLASSMANAGER)) {
			return new OracleTrainingClassManager(userId);
		}
		return user;
	}

	public LeaveWordNormalManage getLeaveWordNormalManage()
			throws PlatformException {
		LeaveWordFactory factory = LeaveWordFactory.getInstance();
		return factory.creatLeaveWordNormalManage(null);
	}
}
