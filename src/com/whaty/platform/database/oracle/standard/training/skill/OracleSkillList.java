/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.skill;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.training.skill.SkillChain;
import com.whaty.platform.training.skill.SkillList;
import com.whaty.platform.training.skill.StudentSkill;
import com.whaty.platform.training.skill.StudentSkillStatus;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.TrainingLog;

/**
 * @author chenjian
 *
 */
public class OracleSkillList implements SkillList {

	private  String SKILLSQL="select skill_id,skill_name,skill_note," +
			"skill_status,chain_id,chain_name from (" +
			"select a.id as skill_id,a.name as skill_name,a.note " +
			"as skill_note,a.status as skill_status,a.chain_id as chain_id,b.name " +
			"as chain_name from training_skill a,training_skill_chain b " +
			"where a.chain_id=b.id) ";
	
	private String SKILLCHAINSQL="select id,name,note,status from training_skill_chain ";
	
	public int getSkillChainNum(List searchproperty, List orderproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = SKILLCHAINSQL + Conditions.convertToCondition(searchproperty, orderproperty);
		
		return db.countselect(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.training.skill.SkillList#searchSkillChain(com.whaty.platform.util.Page, java.util.List, java.util.List)
	 */
	public List searchSkillChain(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = SKILLCHAINSQL + Conditions.convertToCondition(searchproperty, orderproperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList skillChains = null;
		try {
			db = new dbpool();
			skillChains = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleSkillChain skillChain = new OracleSkillChain();
				skillChain.setId(rs.getString("id"));
				skillChain.setName(rs.getString("name"));
				skillChain.setNote(rs.getString("note"));
				if(rs.getInt("status")==1) 
					skillChain.setActive(true);
				else
					skillChain.setActive(false);
				skillChains.add(skillChain);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchSkillChain");
		} finally {
			db.close(rs);
			db = null;

		}
		return skillChains;
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.training.skill.SkillList#getSkillNum(com.whaty.platform.util.Page, java.util.List, java.util.List)
	 */
	public int getSkillNum(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = SKILLSQL + Conditions.convertToCondition(searchproperty, orderproperty);
		
		return db.countselect(sql);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.skill.SkillList#searchSkill(com.whaty.platform.util.Page, java.util.List, java.util.List)
	 */
	public List searchSkill(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = SKILLSQL + Conditions.convertToCondition(searchproperty, orderproperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList skills = null;
		try {
			db = new dbpool();
			skills = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleSkill skill = new OracleSkill();
				skill.setId(rs.getString("skill_id"));
				skill.setName(rs.getString("skill_name"));
				skill.setNote(rs.getString("skill_note"));
				if(rs.getInt("skill_status")==1)
					skill.setActive(true);
				else
					skill.setActive(false);
				SkillChain chain=new OracleSkillChain();
				chain.setId(rs.getString("chain_id"));
				chain.setName(rs.getString("chain_name"));
				skill.setChain(chain);
				skills.add(skill);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchSkill with "+e.getLocalizedMessage());
		} finally {
			db.close(rs);
			db = null;
			return skills;
		}
		
	}
	
	public int getStudentSkillNum(List searchproperty,
			List orderproperty, TrainingStudent student)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql="select skill_id,skill_name,skill_note," +
		"skill_status,chain_id,chain_name,studentskill_status,get_date from (" +
		"select a.id as skill_id,a.name as skill_name,a.note " +
		"as skill_note,a.status as skill_status,a.chain_id as chain_id,b.name " +
		"as chain_name,c.studentskill_status as studentskill_status,c.get_date as get_date " +
		" from training_skill a,training_skill b,(select skill_id,status as studentskill_status," +
		"to_char(get_date,'yyyy-mm-dd') as get_date from " +
		"training_skill_student  where student_id='"+student.getId()+"') c " +
		"where a.id=c.skill_id and a.chain_id=b.id(+))";
		sql = sql + Conditions.convertToCondition(searchproperty, orderproperty);
		TrainingLog.setDebug(sql);
				
		db = new dbpool();
		
		return db.countselect(sql);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.skill.SkillList#searchStudentSkill(com.whaty.platform.util.Page, java.util.List, java.util.List, com.whaty.platform.training.user.TrainingStudent)
	 */
	public List searchStudentSkill(Page page, List searchproperty,
			List orderproperty, TrainingStudent student)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql="select skill_id,skill_name,skill_note," +
		"skill_status,chain_id,chain_name,studentskill_status,get_date from (" +
		"select a.id as skill_id,a.name as skill_name,a.note " +
		"as skill_note,a.status as skill_status,a.chain_id as chain_id,b.name " +
		"as chain_name,c.studentskill_status as studentskill_status,c.get_date as get_date " +
		" from training_skill a,training_skill b,(select skill_id,status as studentskill_status," +
		"to_char(get_date,'yyyy-mm-dd') as get_date from " +
		"training_skill_student  where student_id='"+student.getId()+"') c " +
		"where a.id=c.skill_id and a.chain_id=b.id(+))";
		sql = sql + Conditions.convertToCondition(searchproperty, orderproperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList skills = null;
		String status=null;
		try {
			db = new dbpool();
			skills = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				StudentSkill studentSkill=new StudentSkill();
				OracleSkill skill = new OracleSkill();
				skill.setId(rs.getString("skill_id"));
				skill.setName(rs.getString("skill_name"));
				skill.setNote(rs.getString("skill_note"));
				if(rs.getInt("skill_status")==1)
					skill.setActive(true);
				else
					skill.setActive(false);
				SkillChain chain=new OracleSkillChain();
				chain.setId(rs.getString("chain_id"));
				chain.setName(rs.getString("chain_name"));
				skill.setChain(chain);
				studentSkill.setSkill(skill);
				studentSkill.setStudent(student);
				status=rs.getString("studentskill_status");
				if(status==null || status.length()<1)
					status=StudentSkillStatus.UNOBTAINED;
				studentSkill.setStatus(rs.getString("studentskill_status"));
				studentSkill.setGetDate(rs.getString("get_date"));
				skills.add(studentSkill);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchSkill");
		} finally {
			db.close(rs);
			db = null;

		}
		return skills;
	}


}
