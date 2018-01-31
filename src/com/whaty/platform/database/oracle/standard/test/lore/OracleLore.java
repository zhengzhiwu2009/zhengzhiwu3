/**
 * 
 */
package com.whaty.platform.database.oracle.standard.test.lore;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.lore.Lore;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author wq
 * 
 */
public class OracleLore extends Lore {
	public OracleLore() {

	}
	
	public OracleLore(String id) {
		String sql = "select id, name, discription, to_char(creatdate,'yyyy-mm-dd') as creatdate, content, loredir, createrid, active from test_lore_info "
				+ "where id='" + id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setDiscription(rs.getString("discription"));
				this.setCreatDate(rs.getString("creatdate"));
				this.setContent(rs.getString("content"));
				this.setLoreDir(rs.getString("loredir"));
				this.setCreaterId(rs.getString("createrid"));
				this.setActive(rs.getString("active").equals("1") ? true
						: false);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() throws PlatformException {
		String active = this.isActive() ? "1" : "0";
		String sql = "insert into test_lore_info(id, name, discription, creatdate, content, loredir, createrid, active) values ("
				+ "to_char(s_test_lore_info_id.nextval), '"
				+ this.getName()
				+ "', '"
				+ this.getDiscription()
				+ "', to_date('"
				+ this.getCreatDate()
				+ "', 'yyyy-mm-dd'), '"
				+ this.getContent()
				+ "', '"
				+ this.getLoreDir()
				+ "', '"
				+ this.getCreaterId() + "', '" + active + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleLore.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() throws PlatformException {
		String active = this.isActive() ? "1" : "0";
		String sql = "update test_lore_info set name='" + this.getName()
				+ "', creatdate=to_date('" + this.getCreatDate() + "','yyyy-mm-dd'), content='"
				+ this.getContent() + "', loredir='" + this.getLoreDir()
				+ "', createrid='" + this.getCreaterId() + "', active='"
				+ active + "' where id='" + this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleLore.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() throws PlatformException {
		
		dbpool db2 = new dbpool();
		String sqlisexist = "select t.id from test_storequestion_info t where t.lore='"+this.getId()+"'";
		int ii = db2.countselect(sqlisexist);
		if(ii>0){
			throw new PlatformException("不能删除知识点"+this.getName()+"，该知识点下已经存在题目。");
		}
		
		
		String sql = "delete from test_lore_info where id='"
				+ this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleLore.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
}
