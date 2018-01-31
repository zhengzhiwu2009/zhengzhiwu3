/*
 * OracleSite.java
 *
 * Created on 200557, 10:54
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

/**
 * 
 * @author Administrator
 */
public class OracleSite extends Site {

	/** Creates a new instance of OracleSite */
	public OracleSite() {
	}

	public OracleSite(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,linkman,phone,address,email,fax,to_char(found_date,'yyyy-mm-dd') as found_date,manager,note,recruit_status,status,zip_code,URL,index_show,DIQU_ID,site_company,site_type,corporation,to_char(first_student_date,'yyyy-mm-dd') as first_student_date from entity_site_info where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setAddress(rs.getString("address"));
				this.setEmail(rs.getString("email"));
				this.setFax(rs.getString("fax"));
				this.setFound_date(rs.getString("found_date"));
				this.setLinkman(rs.getString("linkman"));
				this.setManager(rs.getString("manager"));
				this.setNote(rs.getString("note"));
				this.setPhone(rs.getString("phone"));
				this.setRecruit_status(rs.getString("recruit_status"));
				this.setStatus(rs.getString("status"));
				this.setZip_code(rs.getString("zip_code"));
				this.setURL(rs.getString("URL"));
				this.setIndex_show(rs.getString("index_show"));
				this.setDiqu_id(rs.getString("DIQU_ID"));
				this.setSite_company(rs.getString("site_company")) ;
				this.setSite_type(rs.getString("site_type")) ;
				this.setCorporation(rs.getString("corporation")) ;
				this.setFirst_student_date(rs.getString("first_student_date")) ;
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleSite(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public Site getSiteInfo(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		OracleSite site = new OracleSite();
		String sql = "";
		sql = "select id,name,linkman,phone,address,email,fax,to_char(found_date,'yyyy-mm-dd') as found_date,manager,note,recruit_status,status,zip_code,URL,index_show,DIQU_ID from entity_site_info where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {

				site.setId(rs.getString("id"));
				site.setName(rs.getString("name"));
				site.setAddress(rs.getString("address"));
				site.setEmail(rs.getString("email"));
				site.setFax(rs.getString("fax"));
				site.setFound_date(rs.getString("found_date"));
				site.setLinkman(rs.getString("linkman"));
				site.setManager(rs.getString("manager"));
				site.setNote(rs.getString("note"));
				site.setPhone(rs.getString("phone"));
				site.setRecruit_status(rs.getString("recruit_status"));
				site.setStatus(rs.getString("status"));
				site.setZip_code(rs.getString("zip_code"));
				site.setURL(rs.getString("URL"));
				site.setIndex_show(rs.getString("index_show"));
				site.setDiqu_id(rs.getString("DIQU_ID"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleSite(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return site;
	}

	public int add() throws PlatformException {
		if (isIdExist() != 0)
			throw new PlatformException("վIDѾ");
		dbpool db = new dbpool();
		String sql = "";
		// sql = "insert into
		// lrn_site_info(id,name,address,email,manager,linkman,phone,fax,found_date,note,zip_code)
		// values('" + this.getId() + "','" + this.getName() + "','" +
		// this.getAddress() + "','" + this.getEmail() + "','" +
		// this.getManager() + "','" + this.getLinkman() + "','" +
		// this.getPhone() + "','" + this.getFax() + "',to_date('" +
		// this.getFound_date() + "','yyyy-mm-dd'),?,'" + this.getZip_code() +
		// "')";
		sql = "insert into entity_site_info(id,name,address,email,manager,linkman,phone,fax,found_date,note,zip_code,URL,DIQU_ID,site_company,site_type,corporation,first_student_date) values('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getAddress()
				+ "','"
				+ this.getEmail()
				+ "','"
				+ this.getManager()
				+ "','"
				+ this.getLinkman()
				+ "','"
				+ this.getPhone()
				+ "','"
				+ this.getFax()
				+ "',to_date('"
				+ this.getFound_date()
				+ "','yyyy-mm-dd'),'"
				+ this.getNote()
				+ "','"
				+ this.getZip_code()
				+ "','"
				+ this.getURL()
				+ "','"
				+ this.getDiqu_id() 
				+ "','"
				+ this.getSite_company() 
				+ "','"
				+ this.getSite_type() 
				+ "','"
				+ this.getCorporation() 
				+ "',to_date('"
				+ this.getFirst_student_date() 
				+ "','yyyy-mm-dd'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSite.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		
		if(i > 0) {
			String selectSql = "select id,tag from info_news_type where id in (select id from info_news_type where tag = 'վ֪ͨ') or parent_id in (select id from info_news_type where tag = 'վ')"; 
			MyResultSet rs = db.executeQuery(selectSql);
			try {
				if(rs!=null) {
					ArrayList sqlList = new ArrayList();
					while(rs.next()) {
						String tag = rs.getString("tag").trim();
						String p_id = rs.getString("id");
						String insertSql = "insert into info_news_type values(to_char(S_NEWS_TYPE_ID.nextval),'"
							+ this.getName().trim() + tag +"','"
							+ this.getName().trim() + tag +"','"
							+ p_id +"','"
							+ "1','" 
							+ tag + ":" +this.getId()
							+ "')";
						UserAddLog.setDebug("OracleSite.add() SQL=" + sql + " DATE=" + new Date());
						sqlList.add(insertSql);
					}
					db.executeUpdateBatch(sqlList);
					//JOptionPane.showConfirmDialog(null, (String)sqlList.get(0));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
		}
		return i;
	}

	public int delete() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_site_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSite.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_site_info set name='" + this.getName()
				+ "',phone='" + this.getPhone() + "',email='" + this.getEmail()
				+ "',found_date=to_date('" + this.getFound_date()
				+ "','yyyy-mm-dd'),fax='" + this.getFax() + "',address='"
				+ this.getAddress() + "',linkman='" + this.getLinkman()
				+ "',zip_code='" + this.getZip_code() + "',manager='"
				+ this.getManager() + "',note =?,URL='" + this.getURL()
				+ "',DIQU_ID = '" + this.getDiqu_id() + "',site_company='" + this.getSite_company() + "',site_type='"
				+ this.getSite_type() + "',corporation='" + this.getCorporation() + "',first_student_date=to_date('" + this.getFirst_student_date()
				+ "','yyyy-mm-dd') where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql, this.getNote());
		UserUpdateLog.setDebug("OracleSite.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isIdExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_site_info where id = '" + this.getId()
				+ "'";
		int i = db.countselect(sql);
		return i;
	}

	public int studentNum() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_site_info where site = '" + this.getId()
				+ "'";
		int i = db.countselect(sql);
		return i;
	}

	public int changeShow() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_site_info set index_show = decode(index_show,'0','1','0') where id = '"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSite.changeShow() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
