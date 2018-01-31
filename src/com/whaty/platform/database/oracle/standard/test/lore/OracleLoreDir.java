/**
 * 
 */
package com.whaty.platform.database.oracle.standard.test.lore;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.lore.LoreDir;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author wq
 * 
 */
public class OracleLoreDir extends LoreDir {
	
	public OracleLoreDir() {

	}

	public OracleLoreDir(String id) {
		if (!"0".equals(id)) {
			String sql = "select id, name, note, fatherdir, to_char(creatdate,'yyyy-mm-dd') as creatdate,group_id from test_lore_dir where id='"
					+ id + "'";
			dbpool db = new dbpool();
			MyResultSet rs = db.executeQuery(sql);
			try {
				if (rs != null && rs.next()) {
					this.setId(rs.getString("id"));
					this.setName(rs.getString("name"));
					this.setNote(rs.getString("note"));
					this.setFatherDir(rs.getString("fatherdir"));
					this.setCreatDate(rs.getString("creatdate"));
					this.setGroupId(rs.getString("group_id"));
				}
			} catch (SQLException e) {
				
			} finally {
				db.close(rs);
			}
		} else {
			this.setId("0");
			this.setName("根目录");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() throws PlatformException {
		String sql = "insert into test_lore_dir(id, name, note, fatherdir,creatdate,group_id) values ("
				+ "to_char(s_test_lore_dir_id.nextval), '"
				+ this.getName()
				+ "', '"
				+ this.getNote()
				+ "', '"
				+ this.getFatherDir()
				+ "', to_date('"
				+ this.getCreatDate()
				+ "', 'yyyy-mm-dd hh24:mi:ss'),'"
				+ this.getGroupId() + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleLoreDir.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() throws PlatformException {
		String sql = "update test_lore_dir set name='" + this.getName()
				+ "', note='" + this.getNote() + "', fatherdir='"
				+ this.getFatherDir() + "', creatdate=to_date('"
				+ this.getCreatDate() + "','yyyy-mm-dd'), group_id='"
				+ this.getGroupId() + "' where id='" + this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleLoreDir.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() throws PlatformException {
		
		dbpool db2 = new dbpool();
		String sqlisexist = "select t.id from test_lore_info t where t.loredir='"+this.getId()+"'";
		int ii = db2.countselect(sqlisexist);
		if(ii>0){
			throw new PlatformException("目录"+this.getName()+"下已经有知识点，删除失败！");
		}

		
		String sql = "delete from test_lore_dir where id='" + this.getId()
				+ "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleLoreDir.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getLoreList() {
		String sql = "select id,name,creatdate,content,loredir,createrid,active from test_lore_info "
				+ "where loredir='" + this.getId() + "'";
		List lores = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleLore lore = new OracleLore();
				lore.setId(rs.getString("id"));
				lore.setName(rs.getString("name"));
				lore.setCreatDate(rs.getString("creatdate"));
				lore.setContent(rs.getString("content"));
				lore.setLoreDir(rs.getString("loredir"));
				lore.setCreaterId(rs.getString("createrid"));
				lore.setActive(rs.getString("active").equals("1") ? true
						: false);

				lores.add(lore);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return lores;
	}

	public List getSubLoreDirList() {
		String sql = "select id, name, note, fatherdir, creatdate,group_id from test_lore_dir where fatherdir='"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		List loreDirs = new ArrayList();
		try {
			while (rs != null && rs.next()) {
				OracleLoreDir loreDir = new OracleLoreDir();
				loreDir.setId(rs.getString("id"));
				loreDir.setName(rs.getString("name"));
				loreDir.setNote(rs.getString("note"));
				loreDir.setFatherDir(rs.getString("fatherdir"));
				loreDir.setCreatDate(rs.getString("creatdate"));
				loreDir.setGroupId(rs.getString("group_id"));

				loreDirs.add(loreDir);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return loreDirs;
	}
	public int updateName() throws PlatformException {
		String sql = "update test_lore_dir set name='" + this.getName()+ "' where id='" + this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleLore.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
