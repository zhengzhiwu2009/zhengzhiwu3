package com.whaty.platform.entity.web.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;

public class SendMailUtil {
	protected javax.mail.Session session = null;

	//邮件用户名
//	String mailUser = "training@sac.net.cn"; //你通过哪个账号发送邮件
//	String host = "mail.sac.net.cn"; //你通过哪个主机发送邮件
//	String pwd = "1qazxsw2!@#"; //你所要通过的账号的密码是多少，也就是mailUser的密码
	
	String mailUser = "trainingservice@sac.net.cn"; //你通过哪个账号发送邮件
	String host = "mail.sac.net.cn"; //你通过哪个主机发送邮件
	String pwd = "1qazXSW@"; //你所要通过的账号的密码是多少，也就是mailUser的密码
	String getter = ""; //谁接收邮件

	public SendMailUtil() {
		Properties props = new Properties(); //先声明一个配置文件以便存储信息
		props.put("mail.transport.protocol", "smtp"); //首先说明邮件的传输协议
		props.put("mail.smtp.host", host); //说明发送邮件的主机地址
		props.put("mail.smtp.auth", "true"); //说明发送邮件是否需要验证，表示自己的主机发送是需要验证的
		props.put("mail.smpt.port", "25"); //说明邮件发送的端口号

		//session认证 getInstance()
		session = javax.mail.Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUser, pwd);
			}
		});

		//这个是跟踪后台消息。打印在控制台
		session.setDebug(true);
	}

	/**
	 * 构造方法，自定义邮件服务器
	 * @param props
	 * @param username
	 * @param password
	 */
	public SendMailUtil(Properties props, final String username, final String password) {
		/*try {
			Properties emailProperties = PropertiesLoaderUtils.loadAllProperties("email.properties");
			System.out.println(emailProperties.getProperty("jdbc.driverClassName"));
			} catch (IOException e) {
				e.printStackTrace();
			} 
		*/
		//session认证 getInstance()
		session = javax.mail.Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		//这个是跟踪后台消息。打印在控制台
		session.setDebug(false);
	}

	public void send(String to, String content) {
		try {
			this.getter = to;
			Message msg = new MimeMessage(session);
			//设置发送者
			msg.setFrom(new InternetAddress(mailUser));
			//设置接受者
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getter));
			//设置发送时间
			msg.setSentDate(new Date());
			//设置内容的基本机制，字体等
			String htmltext = content;
			msg.setContent(htmltext, "text/html;charset=UTF-8");
			//设置发送主题
			msg.setSubject("找回密码邮件");
			//设置邮件内容
			//         msg.setText(htmltext); //如果以html的格式发送邮件那么邮件的内容需要通过setContent来设置并且不能用setText

			Transport transport = session.getTransport("smtp"); //得到发送协议
			transport.connect(host, mailUser, pwd);//与发送者的邮箱相连
			transport.send(msg); //发送消息

		} catch (Exception ee) {
			ee.printStackTrace();
		}

	}

	public boolean send(String from, String to, String subject, String content) {
		boolean flag = false;
		try {
			this.getter = to;
			InternetAddress sender = null;
			if (StringUtils.isNotBlank(from)) {
				sender = new InternetAddress(mailUser, from);
			} else {
				sender = new InternetAddress(mailUser);
			}

			if (StringUtils.isBlank(subject)) {
				subject = "找回密码邮件（系统自动邮件,请勿答复）";
			}
			Message msg = new MimeMessage(session);
			//设置发送者
			msg.setFrom(sender);
			//设置接受者
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getter));
			//设置发送时间
			msg.setSentDate(new Date());
			//设置内容的基本机制，字体等
			String htmltext = content;
			msg.setContent(htmltext, "text/html;charset=UTF-8");
			//设置发送主题
			msg.setSubject(subject);
			//设置邮件内容
			//         msg.setText(htmltext); //如果以html的格式发送邮件那么邮件的内容需要通过setContent来设置并且不能用setText
			Transport transport = session.getTransport("smtp"); //得到发送协议
			transport.connect(host, mailUser, pwd);//与发送者的邮箱相连
			transport.send(msg); //发送消息

			flag = true;

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;
	}

}
