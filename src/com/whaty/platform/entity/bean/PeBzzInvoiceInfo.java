package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzInvoiceInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzInvoiceInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeBzzOrderInfo peBzzOrderInfo;
	private EnumConst enumConstByFlagFpOpenState;
	private String num;
	private String addressee;
	private String title;
	private String address;
	private String zipCode;
	private String phone;
	private String province;
	private String city;
	private EnumConst enumConstByInvoiceType;// 发票类型
	private EnumConst enumConstByflagPrintStatus; // 打印状态
	private EnumConst enumConstByFlagPostType; // 邮寄方式
	private Date createDate;
	private Date openDate;
	private String fplx ; //发票类型
	private String nsrsbh;//纳税人识别号
	private String gmfnsrsbh; //购买方纳税人识别号
	private String gmfdz; //购买方地址
	private String gmfdh; //购买方电话
	private String gmfkhyh ;//购买方开户行
	private String gmfyhzh;// 购买方银行账号
	private String gmfsjhm;//购买方手机号码
	private String invoiceRemark;//发票描述
	private String email; //邮箱
	private String invoiceNum; //发票流水号
	private String count;//查询次数
	private String fpdmNum;//发票代码
	private String fpjymNum;//发票校验码
	// Constructors

	public String getFpdmNum() {
		return fpdmNum;
	}

	public void setFpdmNum(String fpdmNum) {
		this.fpdmNum = fpdmNum;
	}

	public String getFpjymNum() {
		return fpjymNum;
	}

	public void setFpjymNum(String fpjymNum) {
		this.fpjymNum = fpjymNum;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EnumConst getEnumConstByFlagPostType() {
		return enumConstByFlagPostType;
	}

	public void setEnumConstByFlagPostType(EnumConst enumConstByFlagPostType) {
		this.enumConstByFlagPostType = enumConstByFlagPostType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/** default constructor */
	public PeBzzInvoiceInfo() {
	}

	/** minimal constructor */
	public PeBzzInvoiceInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

	public PeBzzInvoiceInfo(String id, PeBzzOrderInfo peBzzOrderInfo,
			EnumConst enumConstByFlagFpOpenState, String num, String addressee,
			String title, String address, String zipCode, String phone,
			String province, String city, EnumConst enumConstByFlagPrintStatus) {
		super();
		this.id = id;
		this.peBzzOrderInfo = peBzzOrderInfo;
		this.enumConstByFlagFpOpenState = enumConstByFlagFpOpenState;
		this.num = num;
		this.addressee = addressee;
		this.title = title;
		this.address = address;
		this.zipCode = zipCode;
		this.phone = phone;
		this.province = province;
		this.city = city;
		this.enumConstByflagPrintStatus = enumConstByflagPrintStatus;
		this.enumConstByFlagPostType = enumConstByFlagPostType;
	}

	public PeBzzInvoiceInfo(String id, PeBzzOrderInfo peBzzOrderInfo,
			EnumConst enumConstByFlagFpOpenState, String num, String addressee,
			String title, String address, String zipCode, String phone,
			String province, String city) {
		this.id = id;
		this.peBzzOrderInfo = peBzzOrderInfo;
		this.enumConstByFlagFpOpenState = enumConstByFlagFpOpenState;
		this.num = num;
		this.addressee = addressee;
		this.title = title;
		this.address = address;
		this.zipCode = zipCode;
		this.phone = phone;
		this.province = province;
		this.city = city;
		this.enumConstByFlagPostType = enumConstByFlagPostType;
	}

	// /** full constructor */
	// public PeBzzInvoiceInfo(PeBzzOrderInfo peBzzOrderInfo, EnumConst
	// enumConstByFlagFpOpenState,
	// String num, String addressee, String title, String address) {
	// this.peBzzOrderInfo = peBzzOrderInfo;
	// this.enumConstByFlagFpOpenState = enumConstByFlagFpOpenState;
	// this.num = num;
	// this.addressee = addressee;
	// this.title = title;
	// this.address = address;
	// }

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return this.peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

	public EnumConst getEnumConstByFlagFpOpenState() {
		return this.enumConstByFlagFpOpenState;
	}

	public void setEnumConstByFlagFpOpenState(
			EnumConst enumConstByFlagFpOpenState) {
		this.enumConstByFlagFpOpenState = enumConstByFlagFpOpenState;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAddressee() {
		return this.addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public EnumConst getEnumConstByflagPrintStatus() {
		return enumConstByflagPrintStatus;
	}

	public void setEnumConstByflagPrintStatus(
			EnumConst enumConstByflagPrintStatus) {
		this.enumConstByflagPrintStatus = enumConstByflagPrintStatus;
	}

	public EnumConst getEnumConstByInvoiceType() {
		return enumConstByInvoiceType;
	}

	public void setEnumConstByInvoiceType(EnumConst enumConstByInvoiceType) {
		this.enumConstByInvoiceType = enumConstByInvoiceType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getFplx() {
		return fplx;
	}

	public void setFplx(String fplx) {
		this.fplx = fplx;
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getGmfnsrsbh() {
		return gmfnsrsbh;
	}

	public void setGmfnsrsbh(String gmfnsrsbh) {
		this.gmfnsrsbh = gmfnsrsbh;
	}

	public String getGmfdz() {
		return gmfdz;
	}

	public void setGmfdz(String gmfdz) {
		this.gmfdz = gmfdz;
	}

	public String getGmfdh() {
		return gmfdh;
	}

	public void setGmfdh(String gmfdh) {
		this.gmfdh = gmfdh;
	}

	public String getGmfkhyh() {
		return gmfkhyh;
	}

	public void setGmfkhyh(String gmfkhyh) {
		this.gmfkhyh = gmfkhyh;
	}

	public String getGmfyhzh() {
		return gmfyhzh;
	}

	public void setGmfyhzh(String gmfyhzh) {
		this.gmfyhzh = gmfyhzh;
	}

	public String getGmfsjhm() {
		return gmfsjhm;
	}

	public void setGmfsjhm(String gmfsjhm) {
		this.gmfsjhm = gmfsjhm;
	}

	public String getInvoiceRemark() {
		return invoiceRemark;
	}

	public void setInvoiceRemark(String invoiceRemark) {
		this.invoiceRemark = invoiceRemark;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	

}