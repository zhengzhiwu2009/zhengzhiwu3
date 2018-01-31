package com.whaty.platform.database.oracle.standard.vote.user;

import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.vote.user.VoteManagerPriv;
/**
 * 投票管理 
 * @author lwx 2008-8-12
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public class OracleVoteManagerPriv extends VoteManagerPriv {
	
	ManagerPriv includePriv;
	
	public ManagerPriv getIncludePriv() {
		return includePriv;
	}

	public void setIncludePriv(ManagerPriv includePriv) {
		this.includePriv = includePriv;
	}

	public OracleVoteManagerPriv() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OracleVoteManagerPriv(String id) {
		
	}
	
	/**
	 * 根据managpriv
	 */
	public OracleVoteManagerPriv(ManagerPriv priv ){
		super();
		if(priv.addVotePaper == 1){
			this.addVotePaper = 1;
		}
		if(priv.getVotePaper == 1){
			this.getVotePaper = 1;
		}
	}
	
}
