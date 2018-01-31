package com.whaty.platform.database.oracle.standard.leaveword;

import com.whaty.platform.database.oracle.standard.leaveword.user.OracleLeaveWordManagerPriv;
import com.whaty.platform.leaveword.LeaveWordFactory;
import com.whaty.platform.leaveword.LeaveWordManage;
import com.whaty.platform.leaveword.LeaveWordNormalManage;
import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;

public class OracleLeaveWordFactory extends LeaveWordFactory {

	public LeaveWordManage creatLeaveWordManage(LeaveWordManagerPriv amanagerpriv) {
		// TODO Auto-generated method stub
		if (amanagerpriv != null)
			return new OracleLeaveWordManage(amanagerpriv);
		else
			return new OracleLeaveWordManage(new OracleLeaveWordManagerPriv(""));
	}

	public LeaveWordManagerPriv getLeaveWordManagerPriv(String id) {
		// TODO Auto-generated method stub
		return new OracleLeaveWordManagerPriv(id);
	}

	public LeaveWordNormalManage creatLeaveWordNormalManage(
			LeaveWordManagerPriv amanagerpriv) {
		return new OracleLeaveWordNormalManage(new OracleLeaveWordManagerPriv(
				""));
	}

	public LeaveWordNormalManage creatLeaveWordNormalManage() {
		return new OracleLeaveWordNormalManage();
	}

}
