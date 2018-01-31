package com.whaty.platform.database.oracle.standard.vote;

import com.whaty.platform.database.oracle.standard.vote.user.OracleVoteManagerPriv;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.vote.VoteFactory;
import com.whaty.platform.vote.VoteManage;
import com.whaty.platform.vote.VoteNormalManage;
import com.whaty.platform.vote.user.VoteManagerPriv;

public class OracleVoteFactory extends VoteFactory {

	public VoteManage creatVoteManage(VoteManagerPriv amanagerpriv) {
		return new OracleVoteManage(amanagerpriv);
	}

	public VoteNormalManage creatVoteNormalManage() {
		return new OracleVoteNormalManage();
	}

	public VoteManagerPriv getVoteManagerPriv(String id) {
		return new OracleVoteManagerPriv(id);
	}
	
	public VoteManagerPriv getVoteManagerPriv(ManagerPriv priv){
		return new OracleVoteManagerPriv(priv);
	}

}
