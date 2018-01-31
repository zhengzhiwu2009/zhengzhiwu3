/*
 * OracleNote.java
 *
 * Created on 2005年5月10日, 上午10:40
 */

package com.whaty.platform.database.oracle.standard.entity.basic;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.Note;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
/**
 *
 * @author Administrator
 */
public class OracleNote extends Note{
    
    /** Creates a new instance of OracleNote */
    public OracleNote() {
    }

    public int add() {
        dbpool db = new dbpool();
        String sql = "";
        sql = "insert into lrn_notebook_info (id , title ,name, content , input_date , ip , re_id) values(s_lrn_notebook_id.nextval , '" + this.getTitle() + "','"+this.getName()+"','" + this.getContent() + "','"+this.getInput_date()+"','" + this.getIp() + "',0)";
        int i = db.executeUpdate(sql);
        UserAddLog.setDebug("OracleNote.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
        return i;
    }
    
    public int delete() {
        dbpool db = new dbpool();
        String sql = "";
        sql = "delete from lrn_notebook_info where id = '" + this.getId()+"' or re_id = '"+this.getId()+"'";
        int i = db.executeUpdate(sql);
        UserDeleteLog.setDebug("OracleNote.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
        return i;
    }

    public int update() {
        return 0;
    }

    public void delete(List notelist) {
        dbpool db = new dbpool();
        String sql = "";
        String re_sql = "";
        sql = "delete from lrn_note_book where id in (";
        for(int i =0;i<notelist.size();i++)
        {
            OracleNote note = new OracleNote();
            note = (OracleNote)notelist.get(i);
            if(i<notelist.size()-1)
            {
                sql = sql + "'"+note.getId()+"',";
            }
            else
            {
                sql = sql + "'"+note.getId()+"')";
            }
        }
        re_sql = "delete from lrn_note_book where re_id in (";
        for(int i =0;i<notelist.size();i++)
        {
            OracleNote note = new OracleNote();
            note = (OracleNote)notelist.get(i);
            if(i<notelist.size()-1)
            {
                re_sql = re_sql + "'"+note.getId()+"',";
            }
            else
            {
                re_sql = re_sql + "'"+note.getId()+"')";
            }
        }
        Hashtable sqllist = new Hashtable();
        EntityLog.setDebug("OracleNote@Method:delete()="+sql);
        EntityLog.setDebug("OracleNote@Method:delete()="+re_sql);
        sqllist.put(new Integer(1), sql);
        sqllist.put(new Integer(2),re_sql);
        int i = db.executeUpdateBatch(sqllist);
        UserDeleteLog.setDebug("OracleNote.delete(List notelist) SQL1=" + sql + " SQL2=" + re_sql + " COUNT=" + i + " DATE=" + new Date());
    }
    
	/* （非 Javadoc）
	 * @see com.whaty.platform.entity.Note#getNoteList(com.whaty.platform.util.Page)
	 */
	public List getNoteList(Page page) {
		// TODO 自动生成方法存根
		ArrayList notelist = new ArrayList();
		
		return null;
	}
	/* （非 Javadoc）
	 * @see com.whaty.platform.entity.Note#getNoteNum()
	 */
	public int getNoteNum() {
		// TODO 自动生成方法存根
		return 0;
	}
}
