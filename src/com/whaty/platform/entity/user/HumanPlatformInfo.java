package com.whaty.platform.entity.user;

import java.io.Serializable;

/**
 * @author �½�
 * �����������û���ƽ̨�ϵ������Ϣ
 *
 */
public class HumanPlatformInfo implements Serializable{
	private String lastlogin_time=null; //�ϴε�¼ʱ��
	private String lastlogin_ip=null;   //�ϴε�¼IP
	public String getLastlogin_ip() {
		return lastlogin_ip;
	}
	public void setLastlogin_ip(String lastlogin_ip) {
		this.lastlogin_ip = lastlogin_ip;
	}
	public String getLastlogin_time() {
		return lastlogin_time;
	}
	public void setLastlogin_time(String lastlogin_time) {
		this.lastlogin_time = lastlogin_time;
	}

}
