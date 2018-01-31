/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training;

import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingClassManagerPriv;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingManagerPriv;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv;
import com.whaty.platform.training.TrainingClassManage;
import com.whaty.platform.training.TrainingFactory;
import com.whaty.platform.training.TrainingManage;
import com.whaty.platform.training.TrainingPubManage;
import com.whaty.platform.training.TrainingStudentOperationManage;
import com.whaty.platform.training.user.TrainingClassManagerPriv;
import com.whaty.platform.training.user.TrainingManagerPriv;
import com.whaty.platform.training.user.TrainingStudentPriv;

/**
 * @author chenjian
 *
 */
public class OracleTrainingFactory extends TrainingFactory {

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.TrainingFactory#creatTrainingPubManage()
	 */
	public TrainingPubManage creatTrainingPubManage() {
		return new OracleTrainingPubManage();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.TrainingFactory#creatTrainingManage(com.whaty.platform.training.user.TrainingManagerPriv)
	 */
	public TrainingManage creatTrainingManage(TrainingManagerPriv managerpriv) {
		OracleTrainingManage trainingManage=new OracleTrainingManage();
		trainingManage.setManagePriv(managerpriv);
		return trainingManage;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.TrainingFactory#creatTrainingClassManage(com.whaty.platform.training.user.TrainingClassManagerPriv)
	 */
	public TrainingClassManage creatTrainingClassManage(
			TrainingClassManagerPriv classmanagerpriv) {
		OracleTrainingClassManage trainingClassManage=new OracleTrainingClassManage();
		trainingClassManage.setTrainingClassManagerPriv(classmanagerpriv);
		return trainingClassManage;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.TrainingFactory#creatTrainingUserOperationManage(com.whaty.platform.training.user.TrainingStudentPriv)
	 */
	public TrainingStudentOperationManage creatTrainingUserOperationManage(
			TrainingStudentPriv trainingStudentpriv) {
		TrainingStudentOperationManage studentManage=new OracleTrainingStudentOperationManage();
		studentManage.setTrainingStudentPriv(trainingStudentpriv);
		return studentManage;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.TrainingFactory#creatTrainingManagerPriv(java.lang.String)
	 */
	public TrainingManagerPriv creatTrainingManagerPriv(String id) {
		return new OracleTrainingManagerPriv(id);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.TrainingFactory#creatTrainingStudentPriv(java.lang.String)
	 */
	public TrainingStudentPriv creatTrainingStudentPriv(String id) {
		return new OracleTrainingStudentPriv(id);
	}

	public TrainingClassManagerPriv creatTrainingClassManagerPriv(String id) {
		return new OracleTrainingClassManagerPriv(id);
	}

}
