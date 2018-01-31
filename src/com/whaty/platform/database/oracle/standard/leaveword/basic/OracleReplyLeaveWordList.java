package com.whaty.platform.database.oracle.standard.leaveword.basic;

import java.util.ArrayList;
import java.util.List;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.leaveword.basic.ReplyLeaveWordList;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;

public class OracleReplyLeaveWordList implements ReplyLeaveWordList {

	private String SQL_REPLY_LEAVEWORD_LIST = "select id,title,content,createrid,createdate,leavewordid,creatername from (select a.id as id,title,content,createrid,to_char(createdate,'YYYY-MM-DD HH24:MI') as createdate,leavewordid,b.name as creatername from leaveword_reply a,sso_user b where a.createrid = b.id(+))";

	public List getReplyLeaveWordList(Page page, List searchList, List orderList) {
		dbpool db = new dbpool();
		String sql = SQL_REPLY_LEAVEWORD_LIST
				+ Conditions.convertToCondition(searchList, orderList);
		//System.out.print("ReplyLeaveWordList: " + sql);
		MyResultSet rs = null;
		ArrayList records = null;
		try {
			records = new ArrayList();
			if (null != page) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (null != rs && rs.next()) {
				OracleReplyLeaveWord newsRecord = new OracleReplyLeaveWord();
				newsRecord.setId(rs.getString("id"));
				newsRecord.setTitle(rs.getString("title"));
				newsRecord.setContent(rs.getString("content"));
				newsRecord.setCreaterId(rs.getString("createrid"));
				newsRecord.setCreateDate(rs.getString("createdate"));
				newsRecord.setLeaveWordId(rs.getString("leavewordid"));
				newsRecord.setCreaterName(rs.getString("creatername"));
				records.add(newsRecord);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return records;
	}

	public int getReplyLeaveWordListNum(List searchList, List orderList) {
		dbpool db = new dbpool();
		String sql = SQL_REPLY_LEAVEWORD_LIST
				+ Conditions.convertToCondition(searchList, orderList);
		return db.countselect(sql);
	}

}
