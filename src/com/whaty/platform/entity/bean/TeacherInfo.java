package com.whaty.platform.entity.bean;


public class TeacherInfo extends AbstractBean implements java.io.Serializable {
	private String id;//主键
	private String sg_tfc_id;//授课领域ID
	private String sco_id;//性别
	private String ctce_id;//证件类型
	private String eco_id;//最高学历
	private String sg_tti_name;//教师姓名
	private String sg_tti_code;//教师证件号码
	private String sg_tti_organization_name;//所在单位
	private String sg_tti_department_name;//部门
	private String sg_tti_position;//职务
	private String sg_tti_mobile;//联系电话
	private String sg_tti_phone;//手机
	private String sg_tti_guoji;//国籍
	private String sg_tti_email;//邮箱
	private String sg_tti_level;//职级
	private String sg_tti_photo_path;//讲师照片
	private String tfc_name;//授课领域中文
	private String sg_tti_data_type;//师资录入源分类
	private String sg_birthday;//出生年月
	private String stec_id;//上报部门评价
	private String sg_pro;//授课专业
	private String sg_pro_name;//授课专业名称
	private String add_time;//添加时间
	private String sg_tti_description;//讲师简介
	private String sg_tti_is_complete;//是否完备
	private String sg_tti_is_hzph;//是否来源于远程培训系统
	private String sg_tti_is_cyry;//是否来源于从业人员系统
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSg_tfc_id() {
		return sg_tfc_id;
	}
	public void setSg_tfc_id(String sg_tfc_id) {
		this.sg_tfc_id = sg_tfc_id;
	}
	public String getSco_id() {
		return sco_id;
	}
	public void setSco_id(String sco_id) {
		this.sco_id = sco_id;
	}
	public String getCtce_id() {
		return ctce_id;
	}
	public void setCtce_id(String ctce_id) {
		this.ctce_id = ctce_id;
	}
	public String getEco_id() {
		return eco_id;
	}
	public void setEco_id(String eco_id) {
		this.eco_id = eco_id;
	}
	public String getSg_tti_name() {
		return sg_tti_name;
	}
	public void setSg_tti_name(String sg_tti_name) {
		this.sg_tti_name = sg_tti_name;
	}
	public String getSg_tti_code() {
		return sg_tti_code;
	}
	public void setSg_tti_code(String sg_tti_code) {
		this.sg_tti_code = sg_tti_code;
	}
	public String getSg_tti_organization_name() {
		return sg_tti_organization_name;
	}
	public void setSg_tti_organization_name(String sg_tti_organization_name) {
		this.sg_tti_organization_name = sg_tti_organization_name;
	}
	public String getSg_tti_department_name() {
		return sg_tti_department_name;
	}
	public void setSg_tti_department_name(String sg_tti_department_name) {
		this.sg_tti_department_name = sg_tti_department_name;
	}
	public String getSg_tti_position() {
		return sg_tti_position;
	}
	public void setSg_tti_position(String sg_tti_position) {
		this.sg_tti_position = sg_tti_position;
	}
	public String getSg_tti_mobile() {
		return sg_tti_mobile;
	}
	public void setSg_tti_mobile(String sg_tti_mobile) {
		this.sg_tti_mobile = sg_tti_mobile;
	}
	public String getSg_tti_phone() {
		return sg_tti_phone;
	}
	public void setSg_tti_phone(String sg_tti_phone) {
		this.sg_tti_phone = sg_tti_phone;
	}
	public String getSg_tti_guoji() {
		return sg_tti_guoji;
	}
	public void setSg_tti_guoji(String sg_tti_guoji) {
		this.sg_tti_guoji = sg_tti_guoji;
	}
	public String getSg_tti_email() {
		return sg_tti_email;
	}
	public void setSg_tti_email(String sg_tti_email) {
		this.sg_tti_email = sg_tti_email;
	}
	public String getSg_tti_level() {
		return sg_tti_level;
	}
	public void setSg_tti_level(String sg_tti_level) {
		this.sg_tti_level = sg_tti_level;
	}
	public String getSg_tti_photo_path() {
		return sg_tti_photo_path;
	}
	public void setSg_tti_photo_path(String sg_tti_photo_path) {
		this.sg_tti_photo_path = sg_tti_photo_path;
	}
	public String getTfc_name() {
		return tfc_name;
	}
	public void setTfc_name(String tfc_name) {
		this.tfc_name = tfc_name;
	}
	public String getSg_tti_data_type() {
		return sg_tti_data_type;
	}
	public void setSg_tti_data_type(String sg_tti_data_type) {
		this.sg_tti_data_type = sg_tti_data_type;
	}
	public String getSg_birthday() {
		return sg_birthday;
	}
	public void setSg_birthday(String sg_birthday) {
		this.sg_birthday = sg_birthday;
	}
	public String getStec_id() {
		return stec_id;
	}
	public void setStec_id(String stec_id) {
		this.stec_id = stec_id;
	}
	public String getSg_pro() {
		return sg_pro;
	}
	public void setSg_pro(String sg_pro) {
		this.sg_pro = sg_pro;
	}
	public String getSg_pro_name() {
		return sg_pro_name;
	}
	public void setSg_pro_name(String sg_pro_name) {
		this.sg_pro_name = sg_pro_name;
	}
	public String getSg_tti_description() {
		return sg_tti_description;
	}
	public void setSg_tti_description(String sg_tti_description) {
		this.sg_tti_description = sg_tti_description;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getSg_tti_is_complete() {
		return sg_tti_is_complete;
	}
	public void setSg_tti_is_complete(String sg_tti_is_complete) {
		this.sg_tti_is_complete = sg_tti_is_complete;
	}
	public String getSg_tti_is_hzph() {
		return sg_tti_is_hzph;
	}
	public void setSg_tti_is_hzph(String sg_tti_is_hzph) {
		this.sg_tti_is_hzph = sg_tti_is_hzph;
	}
	public String getSg_tti_is_cyry() {
		return sg_tti_is_cyry;
	}
	public void setSg_tti_is_cyry(String sg_tti_is_cyry) {
		this.sg_tti_is_cyry = sg_tti_is_cyry;
	}
}
