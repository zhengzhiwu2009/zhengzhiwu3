/*
 * User.java
 *
 * Created on 2004��9��24��, ����2:51
 */

package com.whaty.platform;

import java.io.Serializable;

import com.whaty.platform.sso.FtpAccount;



/**
 * �û��࣬
 * @author chenjian
 */
public class User implements Serializable{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7309666268694324756L;
	/**
     * ȫ��Ψһ��¼ID
     */
    private String loginId;
    /**
     * �����ʼ�
     */
    private String email;
    private FtpAccount ftpaccount;
    /**
     * ȫ��ͳһ��ʶID
     */
    private String id;
    private String name;
    private String password;
    private String loginType;
    
    public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	/** Creates a new instance of User */
    public User() {
    }
    
    /**
     * ���� email �Ļ�ȡ������
     * @return ���� email ��ֵ��
     */
    public java.lang.String getEmail() {
		if(email == null || email.length() ==0)
			return "";
        return email;
    }    
   
    /**
     * ���� email �����÷�����
     * @param email ���� email ����ֵ��
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    
    /**
     * ���� login_id �Ļ�ȡ������
     * @return ���� login_id ��ֵ��
     */
    public java.lang.String getLoginId() {
        return loginId;
    }
    
    /**
     * ���� login_id �����÷�����
     * @param login_id ���� login_id ����ֵ��
     */
    public void setLoginId(java.lang.String loginId) {
        this.loginId = loginId;
    }
    
   
    /**
     * ���� name �Ļ�ȡ������
     * @return ���� name ��ֵ��
     */
    public java.lang.String getName() {
		if(name == null || name.length() ==0)
			return "";
        return name;
    }
    
    /**
     * ���� name �����÷�����
     * @param name ���� name ����ֵ��
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    /**
     * ���� id �Ļ�ȡ������
     * @return ���� id ��ֵ��
     */
    public java.lang.String getId() {
        return id;
    }
    
    /**
     * ���� id �����÷�����
     * @param id ���� id ����ֵ��
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    /**
     * ���� password �Ļ�ȡ������
     * @return ���� password ��ֵ��
     */
    public java.lang.String getPassword() {
        return password;
    }
    
    /**
     * ���� password �����÷�����
     * @param password ���� password ����ֵ��
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }
    
    /**
     * ���� ftpaccount �Ļ�ȡ������
     * @return ���� ftpaccount ��ֵ��
     */
    public com.whaty.platform.sso.FtpAccount getFtpaccount() {
        return ftpaccount;
    }
    
    /**
     * ���� ftpaccount �����÷�����
     * @param ftpaccount ���� ftpaccount ����ֵ��
     */
    public void setFtpaccount(com.whaty.platform.sso.FtpAccount ftpaccount) {
        this.ftpaccount = ftpaccount;
    }

    
  }
