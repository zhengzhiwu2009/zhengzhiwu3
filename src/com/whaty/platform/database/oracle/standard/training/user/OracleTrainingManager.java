/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.user;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.training.user.TrainingManager;
import com.whaty.platform.util.log.UserAddLog;

/**
 * @author chenjian
 *
 */
public class OracleTrainingManager extends TrainingManager {

	public OracleTrainingManager() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OracleTrainingManager(String managerId) {
		super();
		dbpool db=new dbpool();
		MyResultSet rs=null;
		try
		{
			String sql="select id,name,nick_name,email,phone from training_manager where id='"+managerId+"'";
			rs=db.executeQuery(sql);
			if(rs!=null && rs.next())
			{
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNickName(rs.getString("nick_name"));
				this.setEmail(rs.getString("email"));
			}
		}
		catch(SQLException e)
		{
			
		}
		finally
		{
			db.close(rs);
			db=null;
		}
	}
	public int add() throws PlatformException {
		dbpool db=new dbpool();
		String sql="insert into training_chief(id,name,nick_name, email, note) values(" +
				"'"+this.getId()+"','"+this.getName()+"'" +
				",'"+this.getNickName()+"','"+this.getEmail()+"','"+this.getNote()+"')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTrainingManager.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int update() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}
	public int delete() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
