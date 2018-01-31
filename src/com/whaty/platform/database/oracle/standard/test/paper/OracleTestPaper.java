package com.whaty.platform.database.oracle.standard.test.paper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.ehcache.Element;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.question.OraclePaperQuestion;
import com.whaty.platform.test.paper.TestPaper;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.HzphCache;

public class OracleTestPaper extends TestPaper {
	private static HzphCache hzphCache;
    private static final String cachaName="onlineExam";
	public OracleTestPaper() {

	}
	
	public OracleTestPaper(String id) {
		String sql = "select id,title,creatuser,creatdate,status,note,time,type,group_id,paper_fun"
				+ " from test_testpaper_info where id='" + id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setNote(rs.getString("note"));
				this.setCreatDate(rs.getString("creatdate"));
				this.setCreatUser(rs.getString("creatuser"));
				this.setStatus(rs.getString("status"));
				this.setTitle(rs.getString("title"));
				this.setTime(rs.getString("time"));
				this.setType(rs.getString("type"));
				this.setGroupId(rs.getString("group_id"));
				this.setPaper_fun(rs.getString("paper_fun"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String id = "";
		String sql_pre = "select to_char(s_test_paper_info.nextval) as id from dual";
		MyResultSet rs = db.executeQuery(sql_pre);
		try {
			if (rs != null && rs.next())
				id = rs.getString("id");
		} catch (SQLException e) {
			
		}
		db.close(rs);
		String sql = "insert into test_testpaper_info(id,title,creatuser,creatdate,status,note,"
				+ "type,group_id,time,paper_fun) values ('"
				+ id
				+ "', '"
				+ this.getTitle()
				+ "', '"
				+ this.getCreatUser()
				+ "', sysdate, '"
				+ this.getStatus()
				+ "', '"
				+ this.getNote()
				+ "', '"
				+ this.getType()
				+ "', '"
				+ this.getGroupId()
				+ "', '"
				+ this.getTime() + "','"+this.getPaper_fun()+"')";
			String sqltitle=" select  tsi.title from test_testpaper_info  tsi where tsi.group_id='"+this.getGroupId()+"'";
			MyResultSet rs1 = db.executeQuery(sqltitle);
			try {
				while(rs1!=null&&rs1.next()){
					String title_group=rs1.getString("title");
					if(title_group.equals(this.getTitle())){
						return 0;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				db.close(rs1);
			}
			
		//System.out.println("sql++++++++++++++=="+sql);
		int count = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTestPaper.add() SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		if (count > 0)
			count = Integer.parseInt(id);
		return count;
	}

	public int update() throws PlatformException {
		String sql = "update test_testpaper_info set title='" + this.getTitle()
				+ "', creatuser='" + this.getCreatUser() + "', status='"
				+ this.getStatus() + "', note='" + this.getNote() + "', time='"
				+ this.getTime() + "',type='" + this.getType() + "',group_id='"
				+ this.getGroupId() + "'" + " where id='" + this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTestPaper.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from test_paperquestion_info where testpaper_id='"
				+ this.getId() + "'";
		UserDeleteLog.setDebug("OracleTestPaper.delete() SQL=" + sql + " DATE=" + new Date());
		db.executeUpdate(sql);
		sql = "delete from test_testpaper_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTestPaper.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getPaperQuestion() throws PlatformException {
		String sql = 
				"select tpi.id,\n" +
				"		tpi.creatuser,\n" +
				"		tpi.creatdate,\n" +
				"		tpi.diff,\n" +
				"		tpi.questioncore,\n" +
				"		tpi.title,\n" +
				"		tpi.serial,\n" +
				"		tpi.score,\n" +
				"		tpi.lore,\n" +
				"		tpi.cognizetype,\n" +
				"		tpi.purpose,\n" +
				"		tpi.referencescore,\n" +
				"		tpi.referencetime,\n" +
				"		tpi.studentnote,\n" +
				"		tpi.teachernote,\n" +
				"		tpi.testpaper_id,\n" +
				"		tpi.type,\n" +
				"       tpi.remark,\n "+
				"		tpx.num \n" +
				" from test_paperquestion_info tpi "+
				"  left outer join (\n" +
				"     select tpi.titilelibrary_id,\n" + 
				"            count(tpi.titilelibrary_id) as num\n" + 
				"       from test_paperquestion_info tpi\n" + 
				"      group by tpi.titilelibrary_id) tpx\n" + 
				"  on tpi.titilelibrary_id=tpx.titilelibrary_id\n" +
				" where testpaper_id='" + this.getId() + "'";
		//System.out.println("sql="+sql);
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		List pqList = new ArrayList();
		try {
			while (rs != null && rs.next()) {
				OraclePaperQuestion pq = new OraclePaperQuestion();
				pq.setId(rs.getString("id"));
				pq.setCreatUser(rs.getString("creatuser"));
				pq.setCreatDate(rs.getString("creatdate"));
				pq.setDiff(rs.getString("diff"));
				pq.setQuestionCore(rs.getString("questioncore"));
				pq.setTitle(rs.getString("title"));
				pq.setIndex(rs.getString("serial"));
				pq.setScore(rs.getString("score"));
				pq.setLore(rs.getString("lore"));
				pq.setCognizeType(rs.getString("cognizetype"));
				pq.setPurpose(rs.getString("purpose"));
				pq.setReferenceScore(rs.getString("referencescore"));
				pq.setReferenceTime(rs.getString("referencetime"));
				pq.setStudentNote(rs.getString("studentnote"));
				pq.setTeacherNote(rs.getString("teachernote"));
				pq.setTestPaperId(rs.getString("testpaper_id"));
				pq.setType(rs.getString("type"));
				pq.setNum(rs.getString("num"));
				pq.setRemark(rs.getString("remark"));
				pqList.add(pq);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return pqList;
	}

	public void addPaperQuestion(List PaperQuestion) throws PlatformException {
		// TODO Auto-generated method stub

	}

	public void removePaperQuestion(List PaperQuestion)
			throws PlatformException {
		// TODO Auto-generated method stub
	}

	public int cancelActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_testpaper_info set status='0' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTestPaper.cancelActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_testpaper_info set status='1' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTestPaper.setActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int reverseActive() {
		if (this.getStatus().equals("0"))
			return this.setActive();
		else
			return this.cancelActive();
	}

	public int removePaperQuestions() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from test_paperquestion_info where testpaper_id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTestPaper.removePaperQuestions() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int removePaperQuestions(String questionIds)
			throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}
    
	@Override
	public List getCachePaperQuestion(ServletContext servletContext) throws PlatformException {
		// TODO Auto-generated method stub
		//得到缓存
		getHzphCache(servletContext);
		
		if(null!=hzphCache){
			//得到放到缓存里的考试试题
		 Element el=hzphCache.getCache().get(cachaName+this.getId());
		 if(null!=el){
			 return (List)el.getValue();
		 }
		 List list=getPaperQuestion();
		 //如果有这个试卷信息放到缓存里
		 if(null!=list&&list.size()>0){
		 hzphCache.putToCache(cachaName+this.getId(), list, 5*100000);
		 }
		 return list;
		}else{
			//缓存创建失败就不放直接查出来
			return getPaperQuestion();
		}
		
	}
	public static HzphCache getHzphCache(ServletContext servletContext) {
		if(null==hzphCache){
			ApplicationContext cxt=WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			hzphCache=(HzphCache)cxt.getBean("hzphCacheCacheManager");
		}
		return hzphCache;
	}
	
	/**
	 * 2014-11-03 李文强 批量删除试卷
	 * @return
	 * @throws PlatformException
	 */
	public int deleteSelect(String[] ids) throws PlatformException {
		dbpool db = new dbpool();
		String condition ="";
		for(int i=0 ; i<ids.length ;i++){
			condition += "'" +ids[i].trim()+ "',";
		}
		condition = condition.substring(0, condition.length()-1);
		String paperSql = "delete from test_paperquestion_info where testpaper_id in ("+condition+")";
		int count = db.executeUpdate(paperSql);
//		String sql = "delete from test_testpaper_info tti where tti.id in('";
//		for(int i = 0; i < ids.length; i++) {
//			sql += ids[i] + "','";
//		}
//		sql += "')";
		String sql ="delete from test_testpaper_info tti where tti.id in ("+condition+")";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTestPaper.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
