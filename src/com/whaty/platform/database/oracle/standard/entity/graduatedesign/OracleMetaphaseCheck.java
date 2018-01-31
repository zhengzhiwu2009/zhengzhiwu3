package com.whaty.platform.database.oracle.standard.entity.graduatedesign;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 类描述：中期检查表信息
 * @author zhangliang
 *
 */
public class OracleMetaphaseCheck extends MetaphaseCheck  {
    
	public OracleMetaphaseCheck(){
		
	}
	public OracleMetaphaseCheck(String id){
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id ,student_id,link,discoursename,completetask,anaphaseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id  from (select egm.id ,egm.student_id,link,egm.discoursename,egm.completetask,egm.anaphaseplan,egm.remark,egm.teacher_note,egm.siteteacher_note,egm.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id from entity_graduate_metaphasecheck egm,entity_student_info  esi,entity_usernormal_info   eui where egm.student_id = esi.id and egm.student_id = eui.id) where id ='"+id+"'";
		try{
	    	rs = db.executeQuery(sql);
	    	while(rs!=null&&rs.next()){
	    		this.setId(rs.getString("id"));
	    		OracleStudent student = new OracleStudent(rs.getString("student_id"));
	    		this.setStudent(student);
	    		
	    		this.setLink(rs.getString("link"));
	    		
	    		this.setDiscourseName(rs.getString("discoursename"));
	    		this.setCompletetask(rs.getString("completetask"));
	    		this.setAnaphasePlan(rs.getString("anaphaseplan"));
	    		this.setRemark(rs.getString("remark"));
	    		this.setTeacher_note(rs.getString("teacher_note"));
	    		this.setSiteteacher_note(rs.getString("siteteacher_note"));
	    		this.setStatus(rs.getString("status"));
	    		
	    	}
	    }catch(SQLException es){
	    	EntityLog.setDebug("OracleMetaphaseCheck@OracleMetaphaseCheck(String id) error!!! sql="+sql);
	    }finally{
	    	db.close(rs);
	    	db = null;
	    }
	}
	
	public int add() throws PlatformException {
		 dbpool db = new dbpool();
		 String sqlSel = "select * from entity_graduate_metaphasecheck where student_id='" + this.getStudent().getId() +"'";
		 int ii = db.countselect(sqlSel);
		 String sql = "";
		 if(ii>0)
			 sql = "update entity_graduate_metaphasecheck set discoursename = '" + this.getDiscourseName() + "',link='" + this.getLink() + "'";
		 else
			 sql = "insert into entity_graduate_metaphasecheck (id ,student_id,link,discoursename,completetask,anaphaseplan,remark,status) " +
		 		"values (to_char(s_entity_graduate_mpecheck_id.nextval),to_char('" +this.getStudent().getId()
		 		+"'),to_char('" +this.getLink()
		 		+"'),to_char('" +this.getDiscourseName()
		 		+"'),to_char('" +this.getCompletetask()
		 		+"'),to_char('" +this.getAnaphasePlan()
		 		+"'),to_char('" +this.getRemark()
		 		+"'),to_char('" +this.getStatus()
		 		+"'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleMetaphaseCheck.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql =" update entity_graduate_metaphasecheck set" +
				" student_id = to_char('" 
		        + this.getStudent().getId()
				+"'), link = to_char('"
				+ this.getLink()
				+"'),discoursename=to_char('"
				+ this.getDiscourseName()
				+"'),completetask=to_char('"
				+ this.getCompletetask()
				+"'),anaphaseplan=to_char('"
				+ this.getAnaphasePlan()
				+"'),remark=to_char('"
				+ this.getRemark()
				+"'),teacher_note=to_char('"
				+ this.getTeacher_note()
				+"'),siteteacher_note=to_char('"
				+ this.getSiteteacher_note()
				+"'),status=to_char('"
				+ this.getStatus()
				+"') where id ='"
				+ this.getId()
				+"'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMetaphaseCheck.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql ="delete from entity_graduate_metaphasecheck where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleMetaphaseCheck.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	 
	public int siteTeacherConfirm() {
		dbpool db = new dbpool();
		String sql ="update entity_graduate_metaphasecheck set status ='3',siteteacher_note = '"+this.getSiteteacher_note()+"' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMetaphaseCheck.siteTeacherConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int siteTeacherReject() {
		dbpool db = new dbpool();
		String sql ="update entity_graduate_metaphasecheck set status ='2',siteteacher_note = '"+this.getSiteteacher_note()+"' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMetaphaseCheck.siteTeacherReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int teacherConfirm() {
		dbpool db = new dbpool();
		String sql ="update entity_graduate_metaphasecheck set status ='3',teacher_note = '"+this.getTeacher_note()+"' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMetaphaseCheck.teacherConfirm() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int teacherReject() {
		dbpool db = new dbpool();
		String sql ="update entity_graduate_metaphasecheck set status ='4',teacher_note = '"+this.getTeacher_note()+"' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMetaphaseCheck.teacherReject() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public MetaphaseCheck getMetaphaseCheck(String student_id){
		dbpool db = new dbpool();
		MyResultSet rs = null;
		MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck();
		String sql = "select id ,student_id,link,discoursename,completetask,anaphaseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id  from (select egm.id ,egm.student_id,link,egm.discoursename,egm.completetask,egm.anaphaseplan,egm.remark,egm.teacher_note,egm.siteteacher_note,egm.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id from entity_graduate_metaphasecheck egm,entity_student_info  esi,entity_usernormal_info   eui where egm.student_id = esi.id and egm.student_id = eui.id) where student_id ='"+student_id+"'";
		try{
	    	rs = db.executeQuery(sql);
	    	while(rs!=null&&rs.next()){
	    		metaphaseCheck.setId(rs.getString("id"));
	    		OracleStudent student = new OracleStudent(rs.getString("student_id"));
	    		metaphaseCheck.setStudent(student);
	    		
	    		metaphaseCheck.setLink(rs.getString("link"));
	    		
	    		metaphaseCheck.setDiscourseName(rs.getString("discoursename"));
	    		metaphaseCheck.setCompletetask(rs.getString("completetask"));
	    		metaphaseCheck.setAnaphasePlan(rs.getString("anaphaseplan"));
	    		metaphaseCheck.setRemark(rs.getString("remark"));
	    		metaphaseCheck.setTeacher_note(rs.getString("teacher_note"));
	    		metaphaseCheck.setSiteteacher_note(rs.getString("siteteacher_note"));
	    		metaphaseCheck.setStatus(rs.getString("status"));
	    		
	    	}
	    }catch(SQLException es){
	    	EntityLog.setDebug("OracleMetaphaseCheck@OracleMetaphaseCheck(String id) error!!! sql="+sql);
	    }finally{
	    	db.close(rs);
	    	db = null;
	    }
	    return metaphaseCheck;
	}
	public int upLoadGraduateDesignWord(String reg_no, String fileLink) {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_metaphasecheck egs set egs.link ='"
				+ fileLink
				+ "' where egs.student_id = (select id from entity_student_info esi where esi.reg_no ='"
				+ reg_no + "')";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMetaphaseCheck.upLoadGraduateDesignWord(String reg_no,String fileLink) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
