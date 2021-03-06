package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzStudent generated by MyEclipse - Hibernate Tools
 */

public class PeBzzFaxInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeEnterprise peEnterprise; // 所在企业
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
	
	private PeBzzBatch peBzzBatch; // 报名学期
	
	private PeEnterprise peSite;	//管理员所在的企业

	private Date remittanceDate;	//汇款时间
	// Constructors

	/** default constructor */
	public PeBzzFaxInfo() {
	}

	/** full constructor */
	public PeBzzFaxInfo(PeEnterprise peEnterprise, String seq,
			String fullName, String amount, String num, EnumConst enumConstByFlagFpOpenState,
			EnumConst enumConstByFlagFpMailState, 
			String bz, Date luruDate,String luruPeople,PeBzzBatch peBzzBatch) {
		this.peEnterprise = peEnterprise;
		this.seq = seq;
		this.fullName = fullName;
		this.amount = amount;
		this.num = num;
		this.enumConstByFlagFpOpenState = enumConstByFlagFpOpenState;
		this.enumConstByFlagFpMailState = enumConstByFlagFpMailState;
		this.bz = bz;
		this.luruDate = luruDate;
		this.luruPeople = luruPeople;
		this.peBzzBatch=peBzzBatch;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public EnumConst getEnumConstByFlagFpMailState() {
		return enumConstByFlagFpMailState;
	}

	public void setEnumConstByFlagFpMailState(EnumConst enumConstByFlagFpMailState) {
		this.enumConstByFlagFpMailState = enumConstByFlagFpMailState;
	}

	public EnumConst getEnumConstByFlagFpOpenState() {
		return enumConstByFlagFpOpenState;
	}

	public void setEnumConstByFlagFpOpenState(EnumConst enumConstByFlagFpOpenState) {
		this.enumConstByFlagFpOpenState = enumConstByFlagFpOpenState;
	}

	public String getFpInfo() {
		return fpInfo;
	}

	public void setFpInfo(String fpInfo) {
		this.fpInfo = fpInfo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public PeEnterprise getPeEnterprise() {
		return peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public PeBzzBatch getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public PeEnterprise getPeSite() {
		return peSite;
	}

	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}

	public Date getRemittanceDate() {
		return remittanceDate;
	}

	public void setRemittanceDate(Date remittanceDate) {
		this.remittanceDate = remittanceDate;
	}
}