/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee.deal;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.fee.deal.UserFeeReturnApply;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author wangqiang
 * 
 */
public class OracleUserFeeReturnApply extends UserFeeReturnApply {
	public OracleUserFeeReturnApply() {
		super();
	}

	public OracleUserFeeReturnApply(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,user_id,amount,to_char(apply_time,'yyyy-mm-dd hh24:mi:ss') as apply_time,"
				+ "ischecked,to_char(check_time,'yyyy-mm-dd hh24:mi:ss') as check_time,isreturned,"
				+ "to_char(return_time,'yyyy-mm-dd hh24:mi:ss') as return_time,note "
				+ "from entity_userfee_returnapply where id='" + id + "'";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setUserId(rs.getString("user_id"));
				this.setAmount(rs.getDouble("amount"));
				this.setApplyTime(rs.getString("apply_time"));
				this.setIsChecked(rs.getString("ischecked"));
				this.setCheckTime(rs.getString("check_time"));
				this.setIsReturned(rs.getString("isreturned"));
				this.setReturnTime(rs.getString("return_time"));
				this.setNote(rs.getString("note"));
			}
		} catch (SQLException e) {
			
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "insert into entity_userfee_returnapply(id,user_id,amount,note) values "
				+ "(to_char(s_userfee_returnapply_id.nextval),'"
				+ this.getUserId()
				+ "','"
				+ this.getAmount()
				+ "','"
				+ this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleUserFeeReturnApply.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() {
		dbpool db = new dbpool();
		String sql = "delete from entity_userfee_returnapply where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleUserFeeReturnApply.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "update entity_userfee_returnapply set user_id='"
				+ this.getUserId() + "', amount='" + this.getAmount()
				+ "', apply_time=to_date('" + this.getApplyTime()
				+ "','yyyy-mm-dd hh24:mi:ss'), ischecked='"
				+ this.getIsChecked() + "',check_time=to_date('"
				+ this.getCheckTime()
				+ "','yyyy-mm-dd hh24:mi:ss'),isreturned='"
				+ this.getIsReturned() + "',return_time=to_date('"
				+ this.getReturnTime() + "','yyyy-mm-dd hh24:mi:ss'),note='"
				+ this.getNote() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleUserFeeReturnApply.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
