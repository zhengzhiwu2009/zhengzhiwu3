package com.whaty.platform.database.oracle.standard.leaveword.basic;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.leaveword.basic.LeaveWordList;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;

public class OracleLeaveWordList implements LeaveWordList {

	private String SQL_LEAVEWORD_LIST = "select id,title,content,createrid,createdate,ip,limittimes,replytimes,type,replydate,status from (select id,title,content,createrid,to_char(createdate,'YYYY-MM-DD HH24:MI') as createdate,ip,limittimes,replytimes,type,to_char(replydate,'YYYY-MM-DD HH24:MI') as replydate ,status from leaveword_info )";

	public OracleLeaveWordList() {
	}

	public List getLeaveWordList(Page page, List searchList, List orderList) {
		dbpool db = new dbpool();
		String sql = SQL_LEAVEWORD_LIST
				+ Conditions.convertToCondition(searchList, orderList);
//		Log.setDebug("getLeaveWordList=" + sql);
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
			while (rs != null && rs.next()) {
				OracleLeaveWord newsRecord = new OracleLeaveWord();
				newsRecord.setLimitTimes(rs.getInt("limittimes"));
				newsRecord.setReplyTimes(rs.getInt("replytimes"));
				newsRecord.setId(rs.getString("id"));
				newsRecord.setTitle(rs.getString("title"));
				newsRecord.setContent(rs.getString("content"));
				newsRecord.setCreaterId(rs.getString("createrid"));
				newsRecord.setCreateDate(rs.getString("createdate"));
				newsRecord.setIp(rs.getString("ip"));
				newsRecord.setType(rs.getString("type"));
				newsRecord.setReplyDate(rs.getString("replydate"));
				newsRecord.setStatus(rs.getInt("status"));
				records.add(newsRecord);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return records;
	}

	public int getLeaveWordListNum(List searchList, List orderList) {
		dbpool db = new dbpool();
		String sql = SQL_LEAVEWORD_LIST
				+ Conditions.convertToCondition(searchList, orderList);
		return db.countselect(sql);
	}

	public List getLeaveWordStat(List searchList, List orderList) {
		dbpool db = new dbpool();
		String sql = "select type,num,replynum from (select a.type,num,replynum from (select type,count(id) as num from leaveword_info b "
				+ Conditions.convertToCondition(searchList, null)
				+ " group by type) a,(select c.type,count(b.id) as replynum from leaveword_reply b,leaveword_info c "
				+ "where c.id=b.leavewordid "
				+ Conditions.convertToAndCondition(searchList, null)
				+ " group by c.type) b where a.type=b.type) "
				+ Conditions.convertToAndCondition(null, orderList);
		MyResultSet rs = null;
		List list = null;
		try {
			list = new ArrayList();
			rs = db.executeQuery(sql);
			int num = 0;
			int replynum = 0;
			while (rs != null && rs.next()) {
				List item = new ArrayList();
				item.add(rs.getString("type"));
				num += rs.getInt("num");
				item.add(rs.getString("num"));
				replynum += rs.getInt("replynum");
				item.add(rs.getString("replynum"));
				list.add(item);
			}
			List item = new ArrayList();
			item.add("合计：");
			item.add(Integer.toString(num));
			item.add(Integer.toString(replynum));
			list.add(item);
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
}
