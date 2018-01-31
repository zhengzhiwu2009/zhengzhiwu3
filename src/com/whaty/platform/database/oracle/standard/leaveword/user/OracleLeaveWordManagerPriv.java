package com.whaty.platform.database.oracle.standard.leaveword.user;

import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;

public class OracleLeaveWordManagerPriv extends LeaveWordManagerPriv {
	public OracleLeaveWordManagerPriv(){
		
	}
    
	public OracleLeaveWordManagerPriv(String id) {
		super();
		if (id == null || id.equals("") || id.equals("null")) {
			this.addLeaveWord = 1;
			this.replyLeaveWord = 1;
			this.deleteAllLeaveWord = 0;
			this.deleteLeaveWord = 0;
			this.deleteReply = 0;
			this.getLeaveWord = 1;
			this.getReply = 1;
		}
		this.setManagerId(id);
	}

}
