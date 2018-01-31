package com.whaty.platform.entity.web.action.basic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.UserIssue;
import com.whaty.platform.entity.bean.UserReply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class ProblemsManagerAction extends MyBaseAction<UserIssue> {

	private UserIssue userissue;

	private UserReply userreply;

	private List issueType;// 问题及建议分类
	private String content;//lwq 问题及建议附件
	private List fileList;
	private String fileName;
	private String qaid;
	private List<File> file;
	private List<String> fileFileName;
	private List<String> fileContentType;
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		String filePath = "/UserFiles/File";
		InputStream is = ServletActionContext.getServletContext().getResourceAsStream(filePath + "/" + fileName);
		System.out.println(is);

		if(is == null) {
			is = new ByteArrayInputStream("文件不存在".getBytes());
		}
		return is;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public String getQaid() {
		return qaid;
	}

	public void setQaid(String qaid) {
		this.qaid = qaid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List getFileList() {
		return fileList;
	}

	public void setFileList(List fileList) {
		this.fileList = fileList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setBean(UserIssue instance) {
		super.superSetBean(instance);

	}

	public UserIssue getBean() {
		return super.superGetBean();
	}

	@Override
	public void initGrid() {

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		boolean xiehui = true;// 判断协会端登录或者集体端登录
		boolean jiting = true;
		if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4") || "5".equals(us.getUserLoginType())
				|| "6".equals(us.getUserLoginType())) {// 集体管理员
			// 获取登陆集体账号的机构ID
			xiehui = false;
			jiting = true;
		}
		if (us.getUserLoginType().equals("3")) {// 协会
			// 获取登陆集体账号的机构ID
			xiehui = true;
			jiting = false;
		}
		Set capabilitySet = us.getUserPriority().keySet();
		/* 添加按钮 */
		if ("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType()) || "5".equals(us.getUserLoginType())
				|| "6".equals(us.getUserLoginType()))
			this.getGridConfig().addMenuScript(this.getText("我要提问/建议"),
					"{window.location='/entity/basic/problemsManager_addQuestionAdvice.action';}");
		this.getGridConfig().setCapability(false, jiting, false);
		this.getGridConfig().setTitle("问题及建议");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("问题标题"), "topic", true, true, true, "TextField", false, 200, "");

		ColumnConfig Isissuesort = new ColumnConfig(this.getText("问题分类"), "enumConstByFlagUItype.name", true, true, true, "TextField",
				false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagFrequentlyQuestionType'";
		Isissuesort.setComboSQL(sql);
		this.getGridConfig().addColumn(Isissuesort);

		if (xiehui) {
			this.getGridConfig().addColumn(this.getText("提问角色"), "juese", false, false, true, "");
		} else {
			this.getGridConfig().addColumn(this.getText("提问角色"), "juese", false, false, false, "");
		}
		if ("3".equals(us.getUserLoginType()))
			this.getGridConfig().addColumn(this.getText("提问帐号"), "ZHANGHAO", true, false, true, "");
		else
			this.getGridConfig().addColumn(this.getText("提问帐号"), "ZHANGHAO", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("提问时间起始"), "whodateStartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("提问时间"), "whodatedatetime", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("提问时间结束"), "whodateEndDatetime", true, false, false, "");

		this.getGridConfig().addColumn(this.getText("最后一次回复时间起始"), "relydateStartDatetime", xiehui, false, false, "");
		this.getGridConfig().addColumn(this.getText("最后一次回复时间"), "relydatedatetime", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("最后一次回复时间结束"), "relydateEndDatetime", xiehui, false, false, "");
		// ColumnConfig Isdispose = new ColumnConfig(this.getText("问题处理情况"),
		// "enumConstByFlagUidispose.name", true, false, true, "TextField",
		// false, 100, "");
		// String sql3 = "select a.id ,a.name from enum_const a where
		// a.namespace='FlagUidispose'";
		// Isdispose.setComboSQL(sql3);
		// this.getGridConfig().addColumn(Isdispose);

		this.getGridConfig().addColumn(this.getText("问题描述"), "issuedescribe", false, true, false, "TextArea", true, 500);
		if (xiehui) {
			this.getGridConfig().addRenderScript(this.getText("操作"),
					"{return '<a href=\"/entity/basic/problemsManager_courseInfo.action?id='+${value}+'\">查看/回复</a>';}", "id");
		} else {
			this.getGridConfig().addRenderScript(this.getText("操作"),
					"{return '<a href=\"/entity/basic/problemsManager_courseInfo.action?id='+${value}+'\">查看</a>';}", "id");
		}
		this.getGridConfig().addColumn(this.getText("联系方式"), "phone", false, true, false, "");
	}

	public String saveQuestionAdvice() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String topic = request.getParameter("topic");
		String type = request.getParameter("type");
		String desc = request.getParameter("desc");
		String realPath = ServletActionContext.getServletContext().getRealPath("/UserFiles/File");
		String filePath = "";
		InputStream is = null;
		OutputStream os = null;
//		if (content.length() > 0 && content.toLowerCase().indexOf("</a>") != -1) {
//			int startIdx = content.toLowerCase().indexOf("<");
//			int endIdx = content.toLowerCase().lastIndexOf(">");
//			// 截取多个上传附件路径并以","分隔
//			fileLink = content.substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
//		}
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			//文件上传
			if(file != null && !"".equals(file)) {
				for(int i = 0; i < file.size(); i++) {
					long ts = new Date().getTime();
					is = new FileInputStream(file.get(i));
					os = new FileOutputStream(new File(realPath, ts + "_" + fileFileName.get(i)));
					byte[] buffer = new byte[500];
					
					int length = 0;
					
					while(-1 != (length = is.read(buffer, 0, buffer.length))) {
						os.write(buffer);
					}
					filePath += ts + "_" + fileFileName.get(i) + ",";
				}
				filePath = filePath.substring(0, filePath.length() - 1);
			}
			
			String sql = "INSERT INTO USER_ISSUE (ID,TOPIC,UITYPE, FILELINK,FK_SSO_USER_ID,PHONE,WHODATE,ISSUEDESCRIBE) SELECT SYS_GUID(),'" + topic
			+ "','" + type + "','" + filePath + "', PBS.FK_SSO_USER_ID,PBS.MOBILE_PHONE,SYSDATE,'" + desc
			+ "' FROM PE_ENTERPRISE_MANAGER PBS WHERE PBS.FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
			int res = this.getGeneralService().executeBySQL(sql);
			if (res > 0) {
				this.setMsg("提问/建议保存成功");
				this.setTogo("/entity/basic/problemsManager.action");
			} else {
				this.setMsg("提问/建议保存失败");
				this.setTogo("back");
			}
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("提问/建议保存失败");
			this.setTogo("back");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.setMsg("附件不存在");
			this.setTogo("back");
		} catch (IOException e) {
			e.printStackTrace();
			this.setMsg("附件上传失败");
			this.setTogo("back");
		} finally {
			try {
				if(os != null) 
					os.close();
				if(is != null) 
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "msg";
	}
	
	public String downloadFile() {
		return SUCCESS;
	}

	public String addQuestionAdvice() {
		try {
			DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
			dcType.add(Restrictions.eq("namespace", "FlagFrequentlyQuestionType"));
			List<EnumConst> questionType = this.getGeneralService().getList(dcType);
			this.issueType = questionType;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addQuestionAdvice";
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = UserIssue.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/problemsManager";
	}

	public Page list() {
		Page page = null;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT UI.ID, ");
		sqlBuffer.append("        UI.TOPIC, ");
		sqlBuffer.append("        EC.NAME FENLEI, ");
		sqlBuffer.append("        PPR.NAME JUESE, ");
		sqlBuffer.append("        SU.LOGIN_ID ZHANGHAO, ");
		sqlBuffer.append("        '' WHODATESTARTDATETIME, ");
		sqlBuffer.append("        TO_CHAR(UI.WHODATE,'yyyy-MM-dd hh24:mi:ss') WHODATEDATETIME, ");
		sqlBuffer.append("        '' WHODATEENDDATETIME, ");
		sqlBuffer.append("        '' RELYDATESTARTDATETIME, ");
		sqlBuffer.append("        TO_CHAR(UI.REPLYDATE,'yyyy-MM-dd hh24:mi:ss') RELYDATEDATETIME, ");
		sqlBuffer.append("        '' RELYDATEENDDATETIME, ");
		sqlBuffer.append("        UI.ISSUEDESCRIBE ");
		sqlBuffer.append("   FROM USER_ISSUE  UI, ");
		sqlBuffer.append("        SSO_USER    SU, ");
		sqlBuffer.append("        PE_PRI_ROLE PPR, ");
		sqlBuffer.append("        ENUM_CONST  EC ");
		sqlBuffer.append("  WHERE UI.FK_SSO_USER_ID = SU.ID ");
		sqlBuffer.append("    AND SU.FK_ROLE_ID = PPR.ID ");
		sqlBuffer.append("    AND UI.UITYPE = EC.ID ");
		if (!"3".equals(us.getUserLoginType()))
			sqlBuffer.append("    AND UI.FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "' ");
		try {
			ActionContext context = ActionContext.getContext();
			Map params = this.getParamsMap();
			if (params != null) {
				// 处理时间的查询参数
				if (params.get("search__whodateStartDatetime") != null) {// 提问时间
					// 起始
					String[] startDate = (String[]) params.get("search__whodateStartDatetime");
					String tempDate = startDate[0];
					if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
						sqlBuffer.append("           and UI.WHODATE >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						params.remove("search__whodateStartDatetime");
					}
				}
				if (params.get("search__whodateEndDatetime") != null) {// 提问时间
					// 结束
					String[] startDate = (String[]) params.get("search__whodateEndDatetime");
					String tempDate = startDate[0];
					if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
						sqlBuffer.append("           and UI.WHODATE <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						params.remove("search__whodateEndDatetime");
					}
				}
				if (params.get("search__relydateStartDatetime") != null) {// 最后一次
					String[] startDate = (String[]) params.get("search__relydateStartDatetime");
					String tempDate = startDate[0];
					if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
						sqlBuffer.append("           and UI.REPLYDATE >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						params.remove("search__relydateStartDatetime");
					}
				}
				if (params.get("search__relydateEndDatetime") != null) {// 最后一次
					String[] startDate = (String[]) params.get("search__relydateEndDatetime");
					String tempDate = startDate[0];
					if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
						sqlBuffer.append("           and UI.REPLYDATE <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						params.remove("search__relydateEndDatetime");
					}
				}
				context.setParameters(params);
			}

			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				/* 问题分类 */
				if (name.equals("enumConstByFlagUItype.name")) {
					name = "EC.NAME";
				}
				/* 问题处理情况 */
				if (name.equals("enumConstByFlagUidispose.name")) {
					name = "EC2.NAME";
				}
				sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("id")) {
					temp = "ui.id";
				}
				/* 问题分类 */
				if (temp.equals("enumConstByFlagUItype.name")) {
					temp = "EC.NAME";
				}
				/* 问题处理情况 */
				if (temp.equals("enumConstByFlagUidispose.name")) {
					temp = "EC2.NAME";
				}
				if (temp.equals("whodatedatetime")) {
					temp = "UI.WHODATE";
				}
				if (temp.equals("relydatedatetime")) {
					temp = "UI.REPLYDATE";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");
				} else {
					sqlBuffer.append(" order by " + temp + " asc ");
				}
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 添加数据
	 * 
	 * @author Lzh
	 * @return
	 */

	public Map add() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.superSetBean((UserIssue) setSubIds(this.getBean()));
		Map map = new HashMap();
		try {
			this.getBean().setSsoUser(us.getSsoUser());
			this.getBean().setWhodate(new Date());
			// 处理结果-未处理
			EnumConst ec = this.getEnumConstService().getByNamespaceCode("FlagUidispose", "0");
			this.getBean().setEnumConstByFlagUidispose(ec);
			this.getGeneralService().save(this.getBean());
			map.put("success", "true");
			map.put("info", "添加成功");
			return map;
		} catch (Exception e) {
			map.put("success", "false");
			map.put("info", "添加失败");
			return map;
		}

	}

	public String courseInfo() {
		String id = ServletActionContext.getRequest().getParameter("id");
		StringBuffer sbDetail = new StringBuffer();
		StringBuffer sbReply = new StringBuffer();

		sbDetail.append(" SELECT UI.TOPIC, ");
		sbDetail.append("        EC.NAME NAME1, ");
		sbDetail.append("        TO_CHAR(UI.WHODATE, 'yyyy-MM-dd hh24:mi:ss'), ");
		sbDetail.append("        SU.LOGIN_ID || '/' || NVL(PBS.TRUE_NAME,PEM.NAME) NAME2, ");
		sbDetail.append("        PPR.NAME NAME3, ");
		sbDetail.append("        UI.PHONE, ");
		sbDetail.append("        UI.ISSUEDESCRIBE, ");
		sbDetail.append("        UI.ID, UI.FILELINK ");
		sbDetail.append("   FROM USER_ISSUE UI ");
		sbDetail.append("  INNER JOIN ENUM_CONST EC ");
		sbDetail.append("     ON UI.UITYPE = EC.ID ");
		sbDetail.append("  INNER JOIN SSO_USER SU ");
		sbDetail.append("     ON UI.FK_SSO_USER_ID = SU.ID ");
		sbDetail.append("   LEFT JOIN PE_BZZ_STUDENT PBS ");
		sbDetail.append("     ON SU.ID = PBS.FK_SSO_USER_ID ");
		sbDetail.append("   LEFT JOIN PE_ENTERPRISE_MANAGER PEM ");
		sbDetail.append("     ON SU.ID = PEM.FK_SSO_USER_ID ");
		sbDetail.append("  INNER JOIN PE_PRI_ROLE PPR ");
		sbDetail.append("     ON SU.FK_ROLE_ID = PPR.ID ");
		sbDetail.append("  WHERE UI.ID = '" + id + "' ");

		// Lee 只能协会管理员回复？
		// sbReply.append(" SELECT UR.REPLY, PBS.TRUE_NAME,
		// TO_CHAR(UR.REPLYDATE,'yyyy-MM-dd hh24:mi:ss') REPLYDATE ");
		// sbReply.append(" FROM USER_REPLY UR ");
		// sbReply.append(" INNER JOIN PE_BZZ_STUDENT PBS ");
		// sbReply.append(" ON UR.FK_SSO_USER_ID = PBS.FK_SSO_USER_ID ");
		// sbReply.append(" AND UR.FK_ISSUE_ID = '" + id + "' ");
		// sbReply.append(" UNION ");
		// sbReply.append(" SELECT UR.REPLY, PBS.NAME,
		// TO_CHAR(UR.REPLYDATE,'yyyy-MM-dd hh24:mi:ss') REPLYDATE ");
		// sbReply.append(" FROM USER_REPLY UR ");
		// sbReply.append(" INNER JOIN PE_ENTERPRISE_MANAGER PBS ");
		// sbReply.append(" ON UR.FK_SSO_USER_ID = PBS.FK_SSO_USER_ID ");
		// sbReply.append(" AND UR.FK_ISSUE_ID = '" + id + "' ");
		// sbReply.append(" UNION ");
		sbReply.append(" SELECT UR.REPLY, PBS.TRUE_NAME, TO_CHAR(UR.REPLYDATE,'yyyy-MM-dd hh24:mi:ss') REPLYDATE ");
		sbReply.append("   FROM USER_REPLY UR ");
		sbReply.append("  INNER JOIN PE_MANAGER PBS ");
		sbReply.append("     ON UR.FK_SSO_USER_ID = PBS.FK_SSO_USER_ID ");
		sbReply.append("    AND UR.FK_ISSUE_ID = '" + id + "' ");
		sbReply.append("    ORDER BY REPLYDATE DESC ");
		try {
			List issueDetail = this.getGeneralService().getBySQL(sbDetail.toString());
			List replyList = this.getGeneralService().getBySQL(sbReply.toString());
			ServletActionContext.getRequest().setAttribute("issueDetail", issueDetail);
			ServletActionContext.getRequest().setAttribute("replyList", replyList);
			
			//附件
			fileList = new ArrayList();
			Object[] fklint = (Object[]) issueDetail.get(0);
			if(fklint[8] != null && !"".equals(fklint[8])) {
				String[] file = ((String) fklint[8]).split(",");
				for (int i = 0; i < file.length; i++) {
					List fileInfoList = new ArrayList();
					String link = file[i];
					String fileName = file[i].substring(link.lastIndexOf("/") + 1);
					fileInfoList.add(link);
					fileInfoList.add(fileName);
					fileList.add(fileInfoList);
				}
			}
			
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "courseInfo";
	}
	
	public String deleFile() {
		String sql = "SELECT UI.FILELINK FROM USER_ISSUE UI WHERE UI.ID = '" + qaid + "'";
		try {
			String fileLink = (String)(this.getGeneralService().getBySQL(sql)).get(0);
			if(fileLink.indexOf(fileName + ",") >= 0) {
				fileLink = fileLink.replaceAll(fileName + ",", "");
			} else {
				fileLink = fileLink.replace(fileName, "");
			}
			String updatesql = "UPDATE USER_ISSUE SET FILELINK = '" + fileLink + "' WHERE ID = '" + qaid + "'";
			this.getGeneralService().executeBySQL(updatesql);
			String path = ServletActionContext.getServletContext().getRealPath("");
			File file = new File(path + fileName);
			if(file.exists()) {
				file.delete();
			}
			this.setTogo("/entity/basic/problemsManager_courseInfo.action?id=" + qaid);
			this.setMsg("删除成功");
		} catch (EntityException e) {
			this.setTogo("back");
			this.setMsg("删除失败");
			e.printStackTrace();
		}
		return "lee";
	}

	public String reply() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("qaid");
		String reply = request.getParameter("reply");
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO USER_REPLY ");
			sb.append("   (ID, FK_ISSUE_ID, FK_SSO_USER_ID, REPLYDATE, REPLY) ");
			sb.append("   SELECT SYS_GUID(), '" + id + "', '" + us.getSsoUser().getId() + "', SYSDATE, '" + reply + "' FROM DUAL ");
			StringBuffer issueBuffer = new StringBuffer();
			issueBuffer.append("UPDATE USER_ISSUE SET REPLYDATE = SYSDATE WHERE ID = '" + id + "'");
			List<String> sqlList = new ArrayList<String>();
			sqlList.add(sb.toString());
			sqlList.add(issueBuffer.toString());
			this.getGeneralService().executeBySQL(sb.toString());
			this.getGeneralService().executeBySQL(issueBuffer.toString());
			this.setTogo("/entity/basic/problemsManager.action");
			this.setMsg("回复成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("回复失败");
		}
		return "lee";
	}

	public UserIssue getUserissue() {
		return userissue;
	}

	public void setUserissue(UserIssue userissue) {
		this.userissue = userissue;
	}

	public UserReply getUserreply() {
		return userreply;
	}

	public void setUserreply(UserReply userreply) {
		this.userreply = userreply;
	}

	public List getIssueType() {
		return issueType;
	}

	public void setIssueType(List issueType) {
		this.issueType = issueType;
	}

}
