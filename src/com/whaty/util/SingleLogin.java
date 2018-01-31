package com.whaty.util;

import javax.servlet.http.*;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

public class SingleLogin implements HttpSessionListener {

	// 保存sessionID和username的映射
	private static HashMap hUserName = new HashMap();
	
	private GeneralService generalService;


	public static HashMap getHUserName() {
		return hUserName;
	}

	public static void setHUserName(HashMap userName) {
		hUserName = userName;
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	/** 以下是实现HttpSessionListener中的方法* */
	public void sessionCreated(HttpSessionEvent se) {
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		hUserName.remove(se.getSession());
	}

	/**
	 * isAlreadyEnter-用于判断用户是否已经登录以及相应的处理方法
	 * 
	 * @param loginId
	 *            String-登录的用户名称
	 * @return boolean-该用户是否已经登录过的标志
	 */
	public static boolean isAlreadyEnter(HttpSession session, String loginId, String ip) {
		boolean flag = false;
		// 如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在hUserName中)
		if (hUserName.containsValue(loginId)) {
			flag = true;
			// 遍历原来的hUserName，删除原用户名对应的sessionID(即删除原来的sessionID和username)
			Iterator iter = hUserName.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Map<String, HttpSession> key = (Map<String, HttpSession>) entry.getKey();
				Object val = entry.getValue();
				if (((String) val).equals(loginId)) {
					// 判断IP是否为本机
					Iterator iter2 = key.entrySet().iterator();
					while (iter2.hasNext()) {
						Map.Entry ent = (Map.Entry) iter2.next();
						Object ipAddr = ent.getKey();
						Object sess = ent.getValue();
						// IP不是本机
						//if (!ipAddr.equals(ip)) {
							hUserName.remove(key);
							// 注销原session
							HttpSession se = (HttpSession) sess;
							se.invalidate();
							System.out.println("单点登录：newIp[" + ip + "]  oldIp[" + ipAddr + "]  loginId[" + loginId + "]");
						//} else {
							System.out.println("单点登录：ip[" + ip + "]  loginId[" + loginId + "]");
						//}
							break;
					}
				}
				break;
			}
			saveNewUserSession(ip, session, loginId);
		} else {// 如果该用户没登录过，直接添加现在的sessionID和username
			flag = false;
			saveNewUserSession(ip, session, loginId);
		}
		return flag;
	}
	/**
	 * 根据登陆id 查询状态值
	 * 比对当前状态值和登陆是是否匹配
	 * 不匹配则把当前session置成无效
	 * */
	
	public static void isAlreadyEnter(String loginId){
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		String mark =(String)request.getSession().getAttribute("mark");
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql =" select mark from login_record where upper(login_id) =upper('"+ loginId +"')";
		try {
			rs =db.execuQuery(sql);
			while (rs != null && rs.next()) {
				String value = rs.getString("mark").toString();
				if(!mark.equals(value)){
					System.out.println("登陆状态位被改变-----------------------------------------登陆无效");
					session.invalidate();
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db = null;
		}
	} 

	/**
	 * isOnline-用于判断用户是否在线
	 * 
	 * @param session
	 *            HttpSession-登录的用户名称
	 * @return boolean-该用户是否在线的标志
	 */
	public static boolean isOnline(HttpSession session) {
		boolean flag = true;
		if (hUserName.containsKey(session)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	private static void saveNewUserSession(String ip, HttpSession session, String loginId) {
		// 添加现在的session和username
		Map<String, HttpSession> se = new HashMap<String, HttpSession>();
		se.put(ip, session);
		hUserName.put(se, loginId);
		// System.out.println("hUserName = " + hUserName);
	}
	/**获取IP地址
	 * yangcl
	 * 2016-03-03
	 * */
	public String getIpAddr() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
			// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	/**获取服务器MAC地址
	 * yangcl
	 * 2016-03-03
	 * */
	private String  getMac() throws SocketException, UnknownHostException {
		InetAddress ia = InetAddress.getLocalHost();
		// TODO Auto-generated method stub
		//获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		System.out.println("mac数组长度："+mac.length);
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString();
		//System.out.println("本机MAC地址:"+sb.toString().toUpperCase());
	}
	/**
	 * 获取用户端的mac地址
	 * */
	public String getLocalMac() {
		String mac = null;
		try {
			Process pro = Runtime.getRuntime().exec("cmd.exe /c ipconfig/all");

			InputStream is = pro.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String message = br.readLine();

			int index = -1;
			while (message != null) {
				if ((index = message.indexOf("Physical Address")) > 0) {
					mac = message.substring(index + 36).trim();
					break;
				}
				message = br.readLine();
			}
			System.out.println(mac);
			br.close();
			pro.destroy();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Can't get mac address!");
			return null;
		}
		return mac;
	 
	}
	/**
	 * 存储登陆信息
	 * mac地址
	 * 登陆账号
	 * ip地址
	 * 状态标识位
	 * 登陆账号类型
	 * */
	public void saveLoginSess(String loginId ,String roleId) throws SocketException, UnknownHostException, Exception{ 
		//this.updateMark(loginId);
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = null;
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		int value = this.getRanndom();
		String mark ="";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql =" select id  from login_record where upper(login_id) =upper('"+ loginId +"')";
		int count =0;
		try {
			count = db.countselect(sql);
			
			if(count >0){
				String updateSql =" update login_record  set mark ='"+ value +"' ,mac ='unknown' ,ip ='"+ this.getIpAddr() +"' where upper(login_id)  =upper('"+loginId+"') ";
				db.executeUpdate(updateSql);
			}else{
				String insertSql =" insert into login_record (id ,mac ,ip ,mark ,login_id ,login_date ,role_id ) values(sys_guid() ,'unknown','"+ this.getIpAddr() +"','"+ value +"',upper('"+ loginId +"'), sysdate ,'"+ roleId +"' ) ";
				int i = db.executeUpdate(insertSql);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db = null;
		}
		request.getSession().setAttribute("mark",String.valueOf(value) );
		
		
	}
	/**
	 * 一台终端同一时间仅能登陆一个学员账号
	 * 根据相同的MAC地址来判断是不是在同一个终端上
	 * */
	public void updateMark(String loginId ){
		dbpool db = new dbpool();
		MyResultSet rs =null ;
		String sql =" SELECT fk_role_id FROM SSO_USER  s  where s.login_id ='" +loginId +"' ";
		try {
			rs =db.execuQuery(sql);
			if(rs != null && rs.next()) {
				String value = rs.getString("fk_role_id").toString();
				if (value !=null && value.equals("0")){
					int i = db.executeUpdate(" update login_record set mark ='"+ this.getRanndom()+"' where mac ='"+ this.getLocalMac()+"' and role_id ='0' ");
				}
				
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	public int getRanndom(){
		int i =0;
		i =(int)(Math.random()* 100000000);
		return i ;
	}
}
