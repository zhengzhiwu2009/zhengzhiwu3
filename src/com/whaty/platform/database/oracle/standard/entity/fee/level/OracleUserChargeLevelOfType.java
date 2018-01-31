package com.whaty.platform.database.oracle.standard.entity.fee.level;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.fee.level.UserChargeLevelOfType;
import com.whaty.platform.util.log.EntityLog;

public class OracleUserChargeLevelOfType extends UserChargeLevelOfType {

	public OracleUserChargeLevelOfType() {
		super();
		
	}
	
	public OracleUserChargeLevelOfType(String userId,String type) {
		
		OracleChargeLevel oracleChargeLevel=new OracleChargeLevel();
		String field=((String)oracleChargeLevel.chargeTypeField.get(type));
		String sql="select "+field+" from entity_userfee_level a,  where user_id='"+userId+"'";
		dbpool db=new dbpool();
		MyResultSet rs=null;
		String chargeLevel="";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				chargeLevel=rs.getString(field);
			this.setStudent(new OracleStudent(userId));
			this.setValue(chargeLevel);
			}
		
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleUserChargeLevel.getChargeLevel error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		
	
	}
	

}
