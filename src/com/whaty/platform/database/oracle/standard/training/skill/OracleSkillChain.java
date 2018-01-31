/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.skill;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.training.skill.SkillChain;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 *
 */
public class OracleSkillChain extends SkillChain {

	public OracleSkillChain() {
	
	}
	
	public OracleSkillChain(String chainId) {
		dbpool db=new dbpool();
		MyResultSet rs=null;
		try
		{
			String sql="select id,name,note,status from training_skill_chain where id='"+chainId+"'";
			rs=db.executeQuery(sql);
			if(rs!=null && rs.next())
			{
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
				if(rs.getInt("status")==1)
					this.setActive(true);
				else
					this.setActive(false);
				
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

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.skill.SkillChain#getSkillList()
	 */
	public List getSkillList() throws PlatformException{
		SearchProperty search=new SearchProperty("chain_id",this.getId());
		List searchList=new ArrayList();
		searchList.add(search);
		OracleSkillList skillList=new OracleSkillList();
		return skillList.searchSkill(null, searchList, null);
		
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() {
		dbpool db=new dbpool();
		int status=0;
		if(this.getIsActive()) status=1;
		String sql="insert into training_skill_chain(id,name, status,note) values(" +
				"to_char(s_training_id.nextval),'"+this.getName()+"'" +
				","+status+",'"+this.getNote()+"')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSkillChain.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() {
		dbpool db=new dbpool();
		int status=0;
		if(this.getIsActive()) status=1;
		String sql="update training_skill_chain set name='"+this.getName()+"',status="+status+"," +
				"note=	'"+this.getNote()+"' where id='"+this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSkillChain.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() {
		dbpool db=new dbpool();
		String sql="delete from  training_skill_chain  where id='"+this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSkillChain.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.skill.SkillChain#addSkills(java.util.List)
	 */
	public void addSkills(List skillIdList) throws PlatformException {
		if(skillIdList.size()>0)
		{
			String skillIds="";
			for(int i=0;i<skillIdList.size();i++)
			{
				skillIds=skillIds+"'"+((String)skillIdList.get(i))+"',";
			}
			if(skillIds.length()>=3)
				skillIds=skillIds.substring(0, skillIds.length()-1);
			String sql="update  training_skill set chain_id='"+this.getId()+"' " +
					"from training_skill where id in("+skillIds+")";
			dbpool db=new dbpool();
			
			int i = db.executeUpdate(sql);
			
			UserUpdateLog.setDebug("OracleSkillChain.addSkills(List skillIdList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
		
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.skill.SkillChain#removeSkills(java.util.List)
	 */
	public void removeSkills(List skillIdList) throws PlatformException {
		if(skillIdList.size()>0)
		{
			String skillIds="";
			for(int i=0;i<skillIdList.size();i++)
			{
				skillIds=skillIds+"'"+((String)skillIdList.get(i))+"',";
			}
			if(skillIds.length()>=3)
				skillIds=skillIds.substring(0, skillIds.length()-1);
			String sql="update  training_skill set chain_id='' " +
					"from training_skill where id in("+skillIds+")";
			dbpool db=new dbpool();
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleSkillChain.removeSkills(List skillIdList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
		
	}

}
