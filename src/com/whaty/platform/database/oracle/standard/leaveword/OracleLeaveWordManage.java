package com.whaty.platform.database.oracle.standard.leaveword;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.leaveword.basic.OracleLeaveWord;
import com.whaty.platform.database.oracle.standard.leaveword.basic.OracleLeaveWordList;
import com.whaty.platform.database.oracle.standard.leaveword.basic.OracleReplyLeaveWord;
import com.whaty.platform.database.oracle.standard.leaveword.basic.OracleReplyLeaveWordList;
import com.whaty.platform.database.oracle.standard.leaveword.user.OracleLeaveWordManagerPriv;
import com.whaty.platform.leaveword.LeaveWordManage;
import com.whaty.platform.leaveword.basic.LeaveWord;
import com.whaty.platform.leaveword.basic.ReplyLeaveWord;
import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.log.Log;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleLeaveWordManage extends LeaveWordManage {
	public OracleLeaveWordManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleLeaveWordManage(LeaveWordManagerPriv priv) {
		this.setPriv(priv);
		// TODO Auto-generated constructor stub
	}

	public int addLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		String title;
		String content;
		String createId;
		String createDate;
		String ip;
		String type;
		StrManage strManage = StrManageFactory.creat();
		OracleLeaveWord leaveword = new OracleLeaveWord();
		try {
			title = strManage.fixNull(request.getParameter("title"));
			content = strManage.fixNull(request.getParameter("content"));
			String create_name = strManage.fixNull((String) request
					.getAttribute("create_name"));
			if (!create_name.equals("") && !create_name.equals("null"))
				createId = create_name;
			else
				createId = strManage.fixNull(request.getParameter("createid"));
			type = strManage.fixNull(request.getParameter("type"));
			Log.setDebug("createId=" + createId);
			// createDate=request.getParameter("createdate");
			ip = request.getRemoteHost();
			leaveword.setTitle(title);
			leaveword.setContent(content);
			leaveword.setCreaterId(createId);
			leaveword.setType(type);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			leaveword.setCreateDate(format.format(new java.util.Date()));
			leaveword.setIp(ip);
		} catch (WhatyUtilException e) {
			// TODO Auto-generated catch block
			throw new PlatformException("addLeaveWord error!(" + e.toString()
					+ ")");
		}
		int sub = leaveword.add();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$addAnnounce</whaty><whaty>STATUS$|$"
						+ sub
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return sub;
	}

	public int replyLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		String title;
		String content;
		String createId;
		String leavewordId;
		ReplyLeaveWord leaveword = new OracleReplyLeaveWord();
		StrManage strManage = StrManageFactory.creat();
		try {
			title = strManage.fixNull(request.getParameter("title"));
			content = strManage.fixNull(request.getParameter("content"));
			createId = strManage.fixNull(request.getParameter("createid"));
			leavewordId = request.getParameter("leavewordid");
			leaveword.setTitle(title);
			leaveword.setContent(content);
			leaveword.setCreaterId(createId);
			leaveword.setLeaveWordId(leavewordId);
		} catch (WhatyUtilException e) {
			// TODO Auto-generated catch block
			throw new PlatformException("addReplyLeaveWord error!("
					+ e.toString() + ")");
		}
		int sub = leaveword.add();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$replyLeaveWord</whaty><whaty>STATUS$|$"
						+ sub
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return sub;
	}

	public int updateReplyLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		String title;
		String content;
		String createId;
		String createDate;
		String leavewordId;
		ReplyLeaveWord leaveword = new OracleReplyLeaveWord();
		StrManage strManage = StrManageFactory.creat();
		try {
			title = strManage.fixNull(request.getParameter("title"));
			content = strManage.fixNull(request.getParameter("content"));
			createId = strManage.fixNull(request.getParameter("createid"));
			leavewordId = request.getParameter("id");
			leaveword.setTitle(title);
			leaveword.setContent(content);
			leaveword.setCreaterId(createId);
			leaveword.setId(leavewordId);
		} catch (WhatyUtilException e) {
			throw new PlatformException("addReplyLeaveWord error!("
					+ e.toString() + ")");
		}
		int sub = leaveword.update();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$updateReplyLeaveWord</whaty><whaty>STATUS$|$"
						+ sub
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return sub;
	}

	public int delLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		LeaveWord leaveword = new OracleLeaveWord();
		int count = 0;
		String id[] = request.getParameterValues("id");
		if(id != null)
		{
			for (int i = 0; i < id.length; i++) {
			leaveword.setId(id[i]);
			count += leaveword.delete();
			}
		}
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$delLeaveWord</whaty><whaty>STATUS$|$"
						+ count
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
     return count;

	}

	public int changeLeaveWordLimitTimes(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		OracleLeaveWord leaveword = new OracleLeaveWord();
		String[] id = request.getParameterValues("id");
		int count = 0;
		if(id != null)
		{
			for(int i=0;i<id.length;i++)
			{
				leaveword.setId(id[i]);
				leaveword.setLimitTimes(Integer.parseInt(request
						.getParameter("limittimes")));
				count = leaveword.changeLeaveWordLimitTimes();
			}
		}
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$changeLeaveWordLimitTimes</whaty><whaty>STATUS$|$"
						+ count
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
     return count;

	}
   //��վ
	public List getLeaveWordList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		try {
			if (!strManage.fixNull(request.getParameter("id")).equals(""))
				searchList.add(new SearchProperty("id", request
						.getParameter("id"), "="));
			if (!strManage.fixNull(request.getParameter("title")).equals(""))
				searchList.add(new SearchProperty("title", request
						.getParameter("title"), "like"));
			if (!strManage.fixNull(request.getParameter("content")).equals(""))
				searchList.add(new SearchProperty("content", request
						.getParameter("content"), "like"));
			if (!strManage.fixNull(request.getParameter("createrid"))
					.equals(""))
				searchList.add(new SearchProperty("createrid", request
						.getParameter("createrid"), "="));
			if (!strManage.fixNull(request.getParameter("type")).equals(""))
				searchList.add(new SearchProperty("type", request
						.getParameter("type"), "="));
			if (!strManage.fixNull(request.getParameter("startdate"))
					.equals(""))
				searchList.add(new SearchProperty(
						"to_date(createdate, 'YYYY-MM-DD HH24:MI')", request
								.getParameter("startdate"), "D>="));
			if (!strManage.fixNull(request.getParameter("enddate")).equals(""))
				searchList.add(new SearchProperty(
						"to_date(createdate, 'YYYY-MM-DD HH24:MI')", request
								.getParameter("enddate"), "D<="));
			if (!strManage.fixNull(request.getParameter("ip")).equals(""))
				searchList.add(new SearchProperty("ip", request
						.getParameter("ip"), "="));

			if (strManage.fixNull(request.getParameter("if_reply")).equals("0"))
				searchList.add(new SearchProperty("replytimes", request
						.getParameter("if_reply"), "="));
			else if (strManage.fixNull(request.getParameter("if_reply"))
					.equals("1"))
				searchList.add(new SearchProperty("replytimes", request
						.getParameter("if_reply"), ">="));

			// orderList.add(new OrderProperty("replydate",
			// OrderProperty.DESC));
			// if
			// (!strManage.fixNull(request.getParameter("createdate")).equals(
			// ""))
			orderList.add(new OrderProperty("createdate", OrderProperty.DESC));
			if (!strManage.fixNull(request.getParameter("title")).equals(""))
				orderList.add(new OrderProperty("title", OrderProperty.ASC));
			if (!strManage.fixNull(request.getParameter("content")).equals(""))
				orderList.add(new OrderProperty("content", OrderProperty.ASC));
			if (!strManage.fixNull(request.getParameter("createrid"))
					.equals(""))
				orderList
						.add(new OrderProperty("createrid", OrderProperty.ASC));
		} catch (WhatyUtilException e) {
			throw new PlatformException("getLeaveWordList error!("
					+ e.toString() + ")");
		}
		OracleLeaveWordList list = new OracleLeaveWordList();
		return list.getLeaveWordList(page, searchList, orderList);
	}
	
	public int changeLeaveWordMessStatus(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException{
		OracleLeaveWord leaveword = new OracleLeaveWord();
		String []id = request.getParameterValues("id");
		int count = 0;
		if(id != null)
		{
			for(int i=0;i<id.length;i++)
			{
				leaveword.setId(id[i]);
				leaveword.setStatus(Integer.parseInt(request
						.getParameter("status")));
				count += leaveword.changeLeaveWordMessStatus();
			}
		}
		
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$changeLeaveWordMessStatus</whaty><whaty>STATUS$|$"
						+ count
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
     return count;

	}

	public int delReplyLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		ReplyLeaveWord leaveword = new OracleReplyLeaveWord();
		leaveword.setId(request.getParameter("id"));
		leaveword.setLeaveWordId(request.getParameter("leavewordid"));
		int count = leaveword.delete();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$delReplyLeaveWord</whaty><whaty>STATUS$|$"
						+ count
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
     return count;

	}

	public int delAllLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		LeaveWord leaveword = new OracleLeaveWord();
		ReplyLeaveWord rleaveword = new OracleReplyLeaveWord();
		int count = 0;
		String[] lwid = request.getParameterValues("id");
		String[] rlwid = request.getParameterValues("rid");
		if (lwid != null) {
			for (int i = 0; i < lwid.length; i++) {
				leaveword.setId(lwid[i]);
				count += leaveword.delete();
			}
		}
		if (rlwid != null) {
			for (int i = 0; i < rlwid.length; i++) {
				rleaveword.setId(rlwid[i]);
				count += rleaveword.delete();
			}
		}
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$delAllLeaveWord</whaty><whaty>STATUS$|$"
						+ count
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
     return count;

	}

	public List getReplyLeaveWordList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		try {
			if (!strManage.fixNull(request.getParameter("rid")).equals(""))
				searchList.add(new SearchProperty("id", request
						.getParameter("rid"), "="));
			if (!strManage.fixNull(request.getParameter("rtitle")).equals(""))
				searchList.add(new SearchProperty("title", request
						.getParameter("rtitle"), "like"));
			if (!strManage.fixNull(request.getParameter("rcontent")).equals(""))
				searchList.add(new SearchProperty("content", request
						.getParameter("rcontent"), "like"));
			if (!strManage.fixNull(request.getParameter("rcreaterid")).equals(
					""))
				searchList.add(new SearchProperty("createrid", request
						.getParameter("rcreaterid"), "="));
			if (!strManage.fixNull(request.getParameter("rstartdate")).equals(
					""))
				searchList.add(new SearchProperty("createdate", request
						.getParameter("rstartdate"), "D>="));
			if (!strManage.fixNull(request.getParameter("renddate")).equals(""))
				searchList.add(new SearchProperty("createdate", request
						.getParameter("renddate"), "D<="));
			if (!strManage.fixNull(request.getParameter("leavewordid")).equals(
					""))
				searchList.add(new SearchProperty("leavewordid", request
						.getParameter("leavewordid"), "="));

			orderList.add(new OrderProperty("createdate", OrderProperty.ASC));
			if (!strManage.fixNull(request.getParameter("rcreatedate")).equals(
					""))
				orderList.add(new OrderProperty("createdate",
						OrderProperty.DESC));
			if (!strManage.fixNull(request.getParameter("rtitle")).equals(""))
				orderList.add(new OrderProperty("title", OrderProperty.ASC));
			if (!strManage.fixNull(request.getParameter("rcontent")).equals(""))
				orderList.add(new OrderProperty("content", OrderProperty.ASC));
			if (!strManage.fixNull(request.getParameter("rcreaterid")).equals(
					""))
				orderList
						.add(new OrderProperty("createrid", OrderProperty.ASC));
		} catch (WhatyUtilException e) {
			throw new PlatformException("getReplyLeaveWordList error!("
					+ e.toString() + ")");
		}
		OracleReplyLeaveWordList list = new OracleReplyLeaveWordList();
		return list.getReplyLeaveWordList(page, searchList, orderList);
	}

	public int getLeaveWordListNum(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		try {
			if (!strManage.fixNull(request.getParameter("id")).equals(""))
				searchList.add(new SearchProperty("id", request
						.getParameter("id"), "="));
			if (!strManage.fixNull(request.getParameter("title")).equals(""))
				searchList.add(new SearchProperty("title", request
						.getParameter("title"), "like"));
			if (!strManage.fixNull(request.getParameter("content")).equals(""))
				searchList.add(new SearchProperty("content", request
						.getParameter("content"), "like"));
			if (!strManage.fixNull(request.getParameter("createrid"))
					.equals(""))
				searchList.add(new SearchProperty("createrid", request
						.getParameter("createrid"), "="));
			if (!strManage.fixNull(request.getParameter("type")).equals(""))
				searchList.add(new SearchProperty("type", request
						.getParameter("type"), "="));
			if (!strManage.fixNull(request.getParameter("startdate"))
					.equals(""))
				searchList.add(new SearchProperty(
						"to_date(createdate, 'YYYY-MM-DD HH24:MI')", request
								.getParameter("startdate"), "D>="));
			if (!strManage.fixNull(request.getParameter("enddate")).equals(""))
				searchList.add(new SearchProperty(
						"to_date(createdate, 'YYYY-MM-DD HH24:MI')", request
								.getParameter("enddate"), "D<="));
			if (!strManage.fixNull(request.getParameter("ip")).equals(""))
				searchList.add(new SearchProperty("ip", request
						.getParameter("ip"), "="));

			if (!strManage.fixNull(request.getParameter("createdate")).equals(
					""))
				orderList.add(new OrderProperty("createdate",
						OrderProperty.DESC));
			if (strManage.fixNull(request.getParameter("if_reply")).equals("0"))
				searchList.add(new SearchProperty("replytimes", request
						.getParameter("if_reply"), "="));
			else if (strManage.fixNull(request.getParameter("if_reply"))
					.equals("1"))
				searchList.add(new SearchProperty("replytimes", request
						.getParameter("if_reply"), ">="));
			if (!strManage.fixNull(request.getParameter("title")).equals(""))
				orderList.add(new OrderProperty("title", OrderProperty.ASC));
			if (!strManage.fixNull(request.getParameter("content")).equals(""))
				orderList.add(new OrderProperty("content", OrderProperty.ASC));
			if (!strManage.fixNull(request.getParameter("createrid"))
					.equals(""))
				orderList
						.add(new OrderProperty("createrid", OrderProperty.ASC));
		} catch (WhatyUtilException e) {
			throw new PlatformException("getLeaveWordList error!("
					+ e.toString() + ")");
		}
		OracleLeaveWordList list = new OracleLeaveWordList();
		return list.getLeaveWordListNum(searchList, orderList);
	}

	public int getReplyLeaveWordListNum(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		try {
			if (!strManage.fixNull(request.getParameter("rid")).equals(""))
				searchList.add(new SearchProperty("id", request
						.getParameter("rid"), "="));
			if (!strManage.fixNull(request.getParameter("rtitle")).equals(""))
				searchList.add(new SearchProperty("title", request
						.getParameter("rtitle"), "like"));
			if (!strManage.fixNull(request.getParameter("rcontent")).equals(""))
				searchList.add(new SearchProperty("content", request
						.getParameter("rcontent"), "like"));
			if (!strManage.fixNull(request.getParameter("rcreaterid")).equals(
					""))
				searchList.add(new SearchProperty("createrid", request
						.getParameter("rcreaterid"), "="));
			if (!strManage.fixNull(request.getParameter("rstartdate")).equals(
					""))
				searchList.add(new SearchProperty("createdate", request
						.getParameter("rstartdate"), "D>="));
			if (!strManage.fixNull(request.getParameter("renddate")).equals(""))
				searchList.add(new SearchProperty("createdate", request
						.getParameter("renddate"), "D<="));
			if (!strManage.fixNull(request.getParameter("leavewordid")).equals(
					""))
				searchList.add(new SearchProperty("leavewordid", request
						.getParameter("leavewordid"), "="));

			if (!strManage.fixNull(request.getParameter("rcreatedate")).equals(
					""))
				orderList.add(new OrderProperty("createdate",
						OrderProperty.DESC));
			if (!strManage.fixNull(request.getParameter("rtitle")).equals(""))
				orderList.add(new OrderProperty("title", OrderProperty.ASC));
			if (!strManage.fixNull(request.getParameter("rcontent")).equals(""))
				orderList.add(new OrderProperty("content", OrderProperty.ASC));
			if (!strManage.fixNull(request.getParameter("rcreaterid")).equals(
					""))
				orderList
						.add(new OrderProperty("createrid", OrderProperty.ASC));
		} catch (WhatyUtilException e) {
			throw new PlatformException("getReplyLeaveWordList error!("
					+ e.toString() + ")");
		}
		OracleReplyLeaveWordList list = new OracleReplyLeaveWordList();
		return list.getReplyLeaveWordListNum(searchList, orderList);
	}

	public LeaveWord getLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		return new OracleLeaveWord(request.getParameter("id"));
	}

	public LeaveWord getLeaveWord(String id) throws PlatformException {
		// TODO Auto-generated method stub
		return new OracleLeaveWord(id);
	}

	public ReplyLeaveWord getReplyLeaveWord(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		return new OracleReplyLeaveWord(request.getParameter("id"));
	}

	public List getLeaveWordStat(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		try {
			if (!strManage.fixNull(request.getParameter("startDate"))
					.equals(""))
				searchList.add(new SearchProperty("b.createdate", strManage
						.fixNull(request.getParameter("startDate")), "D>="));
			if (!strManage.fixNull(request.getParameter("endDate")).equals(""))
				searchList.add(new SearchProperty("b.createdate", strManage
						.fixNull(request.getParameter("endDate")), "D<="));
			orderList.add(new OrderProperty("type"));
		} catch (WhatyUtilException e) {
			throw new PlatformException("getReplyLeaveWordList error!("
					+ e.toString() + ")");
		}
		OracleLeaveWordList list = new OracleLeaveWordList();
		return list.getLeaveWordStat(searchList, orderList);
	}

	public LeaveWordManagerPriv getLeaveWordManagerPriv() {
		OracleLeaveWordManagerPriv leaveWordManagerPriv = new OracleLeaveWordManagerPriv();
		leaveWordManagerPriv.setManagerId(this.getPriv().getManagerId());
		return leaveWordManagerPriv;
	}
	
}
