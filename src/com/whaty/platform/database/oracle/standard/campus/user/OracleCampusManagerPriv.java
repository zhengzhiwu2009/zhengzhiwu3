package com.whaty.platform.database.oracle.standard.campus.user;

import com.whaty.platform.campus.user.CampusManagerPriv;

public class OracleCampusManagerPriv extends CampusManagerPriv {
	public OracleCampusManagerPriv(){
		
	}
    
	public OracleCampusManagerPriv(String managerId) {
		super();
		if (managerId == null || managerId.equals("") || managerId.equals("null")) {
			this.applyClass = 0;
			this.deleteClass = 0;
			this.checkClass = 0;
			this.getClass = 1;
			this.updateClass = 0;
			this.applyClassMember = 0;
			this.deleteClassMember = 0;
			this.checkClassMember = 0;
			this.getClassMember = 1;
			this.updateClassMember = 0;
			this.applyAssociation = 0;
			this.deleteAssociation = 0;
			this.checkAssociation = 0;
			this.getAssociation = 1;
			this.updateAssociation = 0;
			this.applyAssociationMember = 0;
			this.deleteAssociationMember = 0;
			this.checkAssociationMember = 0;
			this.getAssociationMember = 1;
			this.updateAssociationMember = 0;
			this.assignAssociationMemberToManager=0;
			this.assignClassMemberToManager=0;
			this.setManagerId("ANONYMOUS");
		}
		else
		{
			
		}
		
	}

}
