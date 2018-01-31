package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzExamFaxInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzExamFaxInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private EnumConst enumConstByFlagExamFaxStatus; // 状态
	private String seq; // 序号
	private String fullName; // 汇款人或汇款企业全称
	private String amount; // 金额
	private String num; // 人数
	private String fpInfo; // 发票信息
	private EnumConst enumConstByFlagFpOpenState; // 发票开具状态
	private EnumConst enumConstByFlagFpMailState; // 发票邮寄状态
	private String bz; // 备注
	private Date luruDate;  //录入时间
	private String luruPeople; // 录入人
	
	private PeEnterprise peSite;	//管理员所在的企业
	
//	private PeBzzBatch peBzzBatch; // 报名学期
	private PeBzzExamBatch peBzzExamBatch;	//考试批次
	// Constructors

	public PeBzzExamFaxInfo(String id, EnumConst enumConstByFlagExamFaxStatus,
			String seq, String fullName, String amount, String num,
			String fpInfo, EnumConst enumConstByFlagFpOpenState,
			EnumConst enumConstByFlagFpMailState, String bz, Date luruDate,
			String luruPeople, PeBzzExamBatch peBzzExamBatch) {
		super();
		this.id = id;
		this.enumConstByFlagExamFaxStatus = enumConstByFlagExamFaxStatus;
		this.seq = seq;
		this.fullName = fullName;
		this.amount = amount;
		this.num = num;
		this.fpInfo = fpInfo;
		this.enumConstByFlagFpOpenState = enumConstByFlagFpOpenState;
		this.enumConstByFlagFpMailState = enumConstByFlagFpMailState;
		this.bz = bz;
		this.luruDate = luruDate;
		this.luruPeople = luruPeople;
		this.peBzzExamBatch = peBzzExamBatch;
	}

	/** default constructor */
	public PeBzzExamFaxInfo() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagExamFaxStatus() {
		return enumConstByFlagExamFaxStatus;
	}

	public void setEnumConstByFlagExamFaxStatus(
			EnumConst enumConstByFlagExamFaxStatus) {
		this.enumConstByFlagExamFaxStatus = enumConstByFlagExamFaxStatus;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getFpInfo() {
		return fpInfo;
	}

	public void setFpInfo(String fpInfo) {
		this.fpInfo = fpInfo;
	}

	public EnumConst getEnumConstByFlagFpOpenState() {
		return enumConstByFlagFpOpenState;
	}

	public void setEnumConstByFlagFpOpenState(EnumConst enumConstByFlagFpOpenState) {
		this.enumConstByFlagFpOpenState = enumConstByFlagFpOpenState;
	}

	public EnumConst getEnumConstByFlagFpMailState() {
		return enumConstByFlagFpMailState;
	}

	public void setEnumConstByFlagFpMailState(EnumConst enumConstByFlagFpMailState) {
		this.enumConstByFlagFpMailState = enumConstByFlagFpMailState;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getLuruDate() {
		return luruDate;
	}

	public void setLuruDate(Date luruDate) {
		this.luruDate = luruDate;
	}

	public String getLuruPeople() {
		return luruPeople;
	}

	public void setLuruPeople(String luruPeople) {
		this.luruPeople = luruPeople;
	}

	public PeBzzExamBatch getPeBzzExamBatch() {
		return peBzzExamBatch;
	}

	public void setPeBzzExamBatch(PeBzzExamBatch peBzzExamBatch) {
		this.peBzzExamBatch = peBzzExamBatch;
	}

	public PeEnterprise getPeSite() {
		return peSite;
	}

	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}
}