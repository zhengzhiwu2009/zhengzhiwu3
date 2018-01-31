package com.whaty.platform.database.oracle.standard.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PlatformConfig;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMailInfo;
import com.whaty.platform.entity.BasicMailInfoManage;
import com.whaty.platform.entity.basic.MailInfo;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;
import com.whaty.util.net.JavaSendEmail;

public class OracleBasciMailInfoManage extends BasicMailInfoManage {

	ManagerPriv basicManagePriv;

	public OracleBasciMailInfoManage(ManagerPriv amanagerpriv) {
		this.basicManagePriv = amanagerpriv;
	}

	public int addMailMessage(String targets, String content, String sender,
			String scope, String siteId) throws PlatformException {
		if (basicManagePriv.addMail == 1) {
			OracleMailInfo mailMessage = new OracleMailInfo();
			mailMessage.setTargets(targets);
			mailMessage.setContent(content);
			mailMessage.setSender(sender);
			mailMessage.setScope(scope);
			mailMessage.setSiteId(siteId);
			int i = mailMessage.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addMailMessage</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return i;
		} else {
			throw new PlatformException("ûʼȨ!");
		}
	}

	public int updateMailMessage(String id, String targets, String content,
			String status, String sender, String time, String scope)
			throws PlatformException {
		if (basicManagePriv.updateMail == 1) {
			OracleMailInfo mailMessage = new OracleMailInfo();
			mailMessage.setMsgId(id);
			mailMessage.setTargets(targets);
			mailMessage.setContent(content);
			mailMessage.setStatus(status);
			mailMessage.setSender(sender);
			mailMessage.setTime(time);
			mailMessage.setScope(scope);
			int i = mailMessage.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateMailMessage</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return i;
		} else {
			throw new PlatformException("ûб༭ʼȨ!");
		}
	}

	public MailInfo getMailMessage(String id) throws PlatformException {
		if (basicManagePriv.getMail == 1) {
			return new OracleMailInfo(id);
		} else {
			throw new PlatformException("ûв鿴ʼȨ!");
		}
	}

	public int rejectMailMessage(String checker, String[] msgIds, String[] notes)
			throws PlatformException {
		if (basicManagePriv.rejectMail == 1) {
			OracleMailInfo mailList = new OracleMailInfo();
			int suc = mailList.rejectMailMessages(checker, msgIds, notes);
			return suc;
		} else {
			throw new PlatformException("ûвʼȨ!");
		}
	}

	public List getMailMessagesList(Page page, String id, String targets,
			String content, String status, String sender, String startTime,
			String endTime, String scope, String checker, String siteId)
			throws PlatformException {
		if (basicManagePriv.getMail == 1) {
			OracleMailInfo mailList = new OracleMailInfo();
			List searchList = new ArrayList();
			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("id", id, "="));
			if (targets != null && !targets.equals("")
					&& !targets.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("targets", targets, "like"));
			if (content != null && !content.equals("")
					&& !content.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("content", content, "like"));
			if (status != null && !status.equals("")
					&& !status.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("status", status, "="));
			if (sender != null && !sender.equals("")
					&& !sender.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("sender", sender, "like"));
			if (startTime != null && !startTime.equals("")
					&& !startTime.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("time", startTime, ">="));
			if (endTime != null && !endTime.equals("")
					&& !endTime.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("time", endTime, "<="));
			if (scope != null && !scope.equals("")
					&& !scope.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("scope", scope, "like"));
			if (checker != null && !checker.equals("")
					&& !checker.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("checker", checker, "="));
			if (siteId != null && !siteId.equals("")
					&& !siteId.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("site", siteId, "="));

			List orderList = new ArrayList();
			orderList.add(new OrderProperty("to_number(id)", "1"));
			return mailList.searchMailMessages(page, searchList, orderList);
		} else {
			throw new PlatformException("ûлȡʼбȨ!");
		}
	}

	public int getMailMessagesNum(String id, String targets, String content,
			String status, String sender, String startTime, String endTime,
			String scope, String checker, String siteId)
			throws PlatformException {
		if (basicManagePriv.getMail == 1) {
			OracleMailInfo mailList = new OracleMailInfo();
			List searchList = new ArrayList();
			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("id", id, "="));
			if (targets != null && !targets.equals("")
					&& !targets.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("targets", targets, "like"));
			if (content != null && !content.equals("")
					&& !content.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("content", content, "like"));
			if (status != null && !status.equals("")
					&& !status.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("status", status, "="));
			if (sender != null && !sender.equals("")
					&& !sender.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("sender", sender, "like"));
			if (startTime != null && !startTime.equals("")
					&& !startTime.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("time", startTime, ">="));
			if (endTime != null && !endTime.equals("")
					&& !endTime.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("time", endTime, "<="));
			if (scope != null && !scope.equals("")
					&& !scope.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("scope", scope, "like"));
			if (checker != null && !checker.equals("")
					&& !checker.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("checker", checker, "="));
			if (siteId != null && !siteId.equals("")
					&& !siteId.equalsIgnoreCase("null"))
				searchList.add(new SearchProperty("site", siteId, "="));

			return mailList.searchMailMessagesNum(searchList);
		} else {
			throw new PlatformException("ûлȡʼбȨ!");
		}
	}

	public int deleteMailMessage(List smsMessages) throws PlatformException {
		if (basicManagePriv.deleteMail == 1) {
			OracleMailInfo mailList = new OracleMailInfo();
			int i = mailList.deleteMailMessages(smsMessages);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteMailMessage</whaty><whaty>STATUS$|$"
									+ i
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return i;
		} else {
			throw new PlatformException("ûɾʼȨ!");
		}
	}

	public String getEmails(String fileName) throws PlatformException {
		if (basicManagePriv.batchImportMails == 1) {
			String mobiles = "";
			try {
				Workbook w = Workbook.getWorkbook(new File(fileName));
				Sheet sheet = w.getSheet(0);
				int rows = sheet.getRows();

				for (int i = 1; i < rows; i++) {
					String name = sheet.getCell(0, i).getContents().trim();
					String mobile = sheet.getCell(1, i).getContents().trim();
					mobiles += mobile + ",";
				}
			} catch (BiffException e) {
				
			} catch (IOException e) {
				
			}
			if (mobiles.length() > 0)
				mobiles = mobiles.substring(0, mobiles.length() - 1);
			return mobiles;
		} else {
			throw new PlatformException("ûemailȨ!");
		}
	}

	public int updateMailMessageSendStatus(String id, String email,
			String sendStatus) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getMailMessageNumBySite(Page pageover, String site_id,
			String start_time, String end_time) throws PlatformException {
		if (basicManagePriv.getMailStatistic == 1) {
			OracleMailInfo mailList = new OracleMailInfo();
			List smsNmList = mailList.getMailMessageNumBySite(pageover,
					site_id, start_time, end_time);
			return smsNmList;
		} else {
			throw new PlatformException("ûв鿴ʼͳƵȨ!");
		}
	}

	public int checkMailMessage(String checker, List smsMessages,
			String dirconfig) throws PlatformException {
		if (basicManagePriv.checkMail == 1) {
			OracleMailInfo mailList = new OracleMailInfo();
			int suc = mailList.checkMailMessages(checker, smsMessages);

			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$checkMailMessage</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());

			PlatformConfig platformConfig = new PlatformConfig(dirconfig);
			platformConfig.getConfig();
			String from = platformConfig.getMailUser();
			JavaSendEmail email = new JavaSendEmail(platformConfig
					.getMailSmtp(), platformConfig.getMailUser(),
					platformConfig.getMailPassword());
			for (int i = 0; i < smsMessages.size(); i++) {
				String msgId = (String) smsMessages.get(i);
				MailInfo message = this.getMailMessage(msgId);
				String mail = message.getTargets();
				String content = message.getContent();
				String subject = message.getScope();
				try {
					email.sendMail(from, subject, content, mail);
				} catch (Exception e) {
				}

				// SmsSendThread sendThread = new SmsSendThread(mobile,
				// content);
				// sendThread.start();
			}

			return suc;
		} else {
			throw new PlatformException("ûʼȨ!");
		}
	}

	public int checkMailMessage(String checker, List smsMessages)
			throws PlatformException {
		if (basicManagePriv.checkMail == 1) {
			OracleMailInfo mailList = new OracleMailInfo();
			int suc = mailList.checkMailMessages(checker, smsMessages);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$checkMailMessage</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());

			return suc;
		} else {
			throw new PlatformException("ûʼȨ!");
		}
	}

}
