package com.whaty.platform.entity.user;

import java.io.Serializable;

public class StudentEduInfo implements Serializable{

	private String reg_no = null; // ѧ��

	private String class_id = null; // �����༶ID

	private String class_name = null; // �����༶���

	private String edutype_id = null; // ���ID

	private String edutype_name = null; // ������

	private String grade_id = null; // �꼶ID

	private String grade_name = null; // �꼶���

	private String site_id = null; // ��ѧվID
	
	private String old_site_id = null; // �ɽ�ѧվID

	private String site_name = null; // ��ѧվ���

	private String major_id = null; // רҵID

	private String major_name = null; // רҵ���

	private String study_form = null; // ѧϰ��ʽ

	private String study_status = null; // ѧ��״��

	private String status = "0"; // ��ƽ̨�е�״̬

	private String photo_link = null; // ��ƽ̨�е���Ƭλ��

	private String entrance_date = null; // ��ѧʱ��

	private String isgraduated = null; // �Ƿ��ҵ

	private String isdegreed = null; // �Ƿ���ѧλ

	private String graduate_no = null; // ��ҵ֤��

	private String graduate_status = null; // �Ƿ��ϱ�ҵ�����ҵ

	private String degree_status = null; // �Ƿ���ѧλ���

	private String graduate_operate_time = null; // ��ҵ����ִ��ʱ��

	private String graduate_operator = null; // ��ҵ����ִ����

	private String expend_score_status = "";// �Ƿ񲹿�״̬ 0Ϊ��1Ϊ����

	private String degree_operate_time = null; // ѧλ�������ִ��ʱ��

	private String degree_operator = null; // ѧλ�������ִ����

	private String degree_score = null; // ѧλ����

	private String degree_release = null; // ѧλ����״̬

	private String school_name = "";        //��ѧǰ��ҵѧУ���

	private String school_code = "";      //��ѧǰ��ҵѧУ����

	private String graduate_year = "";   //��ѧǰ��ҵ���

	private String graduate_cardno = "";  //��ѧǰ��ҵ֤����
	
	
	public String getGraduate_cardno() {
		return graduate_cardno;
	}

	public void setGraduate_cardno(String graduate_cardno) {
		this.graduate_cardno = graduate_cardno;
	}

	public String getGraduate_year() {
		return graduate_year;
	}

	public void setGraduate_year(String graduate_year) {
		this.graduate_year = graduate_year;
	}

	public String getSchool_code() {
		return school_code;
	}

	public void setSchool_code(String school_code) {
		this.school_code = school_code;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public String getPhoto_link() {
		return photo_link;
	}

	public void setPhoto_link(String photo_link) {
		this.photo_link = photo_link;
	}

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getEdutype_id() {
		return edutype_id;
	}

	public void setEdutype_id(String edutype_id) {
		this.edutype_id = edutype_id;
	}

	public String getEdutype_name() {
		return edutype_name;
	}

	public void setEdutype_name(String edutype_name) {
		this.edutype_name = edutype_name;
	}

	public String getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(String grade_id) {
		this.grade_id = grade_id;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getMajor_id() {
		return major_id;
	}

	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}

	public String getMajor_name() {
		return major_name;
	}

	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}

	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEntrance_date() {
		return entrance_date;
	}

	public void setEntrance_date(String entrance_date) {
		this.entrance_date = entrance_date;
	}

	public String getStudy_form() {
		return study_form;
	}

	public void setStudy_form(String study_form) {
		this.study_form = study_form;
	}

	public String getStudy_status() {
		return study_status;
	}

	public void setStudy_status(String study_status) {
		this.study_status = study_status;
	}

	public String getIsgraduated() {
		return isgraduated;
	}

	public void setIsgraduated(String isgraduated) {
		this.isgraduated = isgraduated;
	}

	public String getDegree_status() {
		return degree_status;
	}

	public void setDegree_status(String degree_status) {
		this.degree_status = degree_status;
	}

	public String getGraduate_status() {
		return graduate_status;
	}

	public void setGraduate_status(String graduate_status) {
		this.graduate_status = graduate_status;
	}

	public String getGraduate_no() {
		return graduate_no;
	}

	public void setGraduate_no(String graduate_no) {
		this.graduate_no = graduate_no;
	}

	public String getIsdegreed() {
		return isdegreed;
	}

	public void setIsdegreed(String isdegreed) {
		this.isdegreed = isdegreed;
	}

	public String getGraduate_operate_time() {
		return graduate_operate_time;
	}

	public void setGraduate_operate_time(String graduate_operate_time) {
		this.graduate_operate_time = graduate_operate_time;
	}

	public String getGraduate_operator() {
		return graduate_operator;
	}

	public void setGraduate_operator(String graduate_operator) {
		this.graduate_operator = graduate_operator;
	}

	public String getExpend_score_status() {
		return expend_score_status;
	}

	public void setExpend_score_status(String expend_score_status) {
		this.expend_score_status = expend_score_status;
	}

	public String getDegree_operate_time() {
		return degree_operate_time;
	}

	public void setDegree_operate_time(String degree_operate_time) {
		this.degree_operate_time = degree_operate_time;
	}

	public String getDegree_operator() {
		return degree_operator;
	}

	public void setDegree_operator(String degree_operator) {
		this.degree_operator = degree_operator;
	}

	public String getDegree_release() {
		return degree_release;
	}

	public void setDegree_release(String degree_release) {
		this.degree_release = degree_release;
	}

	public String getDegree_score() {
		return degree_score;
	}

	public void setDegree_score(String degree_score) {
		this.degree_score = degree_score;
	}

	public String getOld_site_id() {
		return old_site_id;
	}

	public void setOld_site_id(String old_site_id) {
		this.old_site_id = old_site_id;
	}

}
