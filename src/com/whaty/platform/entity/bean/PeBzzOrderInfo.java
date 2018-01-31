package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzOrderInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzOrderInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagPaymentState;
	private EnumConst enumConstByFlagPaymentType;
	private EnumConst enumConstByFlagOrderState;
	private EnumConst enumConstByFlagPaymentMethod;
	private EnumConst enumConstByFlagOrderType;
	private EnumConst enumConstByFlagRefundState;
	private EnumConst enumConstByFlagCheckState;
	private EnumConst enumConstByFlagZhiFuState;
	private EnumConst enumConstByFlagFaPiaoState;
	private String checkNote;
	private Date checkDate;
	private String seq;
	private String cname;
	private String amount;
	private String num;
	private String operater;
	private String operateType;
	private Date operateDate;
	private Date paymentDate;
	private Date createDate;
	private String payer;
	private String classHour;
	private String tel;
	private PeBzzBatch peBzzBatch;//专项
	private PeElectiveCoursePeriod peElectiveCoursePeriod;//选课期
	private PeSignUpOrder peSignUpOrder;//报名单
	private PeEnterprise peEnterprise;//机构ID
	private Set peBzzInvoiceInfos = new HashSet(0);
	private Set peBzzRefundInfos = new HashSet(0);
	private Set prBzzTchStuElectives = new HashSet(0);
	private Set studentBatchs = new HashSet(0);
	private Set prBzzTchStuElectiveBack = new HashSet(0);
	private Set prepaidAmountRecord = new HashSet(0); //预付费账户查询记录
	private SsoUser ssoUser;
	private EnumConst enumConstByFlagOrderIsValid;     //新添加字段，用于判断订单是否有效
	private EnumConst enumConstByFlagOrderIsIncomplete;   //20130102添加，用于标识此订单是否为购买未通过课程订单
	private String paymentMethod ;  //20160315 添加，标识个人网银、或企业网银支付
	private Set recordAssign = new HashSet(0);
	
	private int autoCheckTimes; //自动三方确认次数
	// Constructors
	private Date refundDate;//退费确认时间
	private String refundId;//三方返回的退款流水号
	private String mergeSeq; //合并订单号，供合并开发票使用
	public String getMergeSeq() {
		return mergeSeq;
	}

	public void setMergeSeq(String mergeSeq) {
		this.mergeSeq = mergeSeq;
	}

	public PeBzzOrderInfo(String id, EnumConst enumConstByFlagPaymentState,
			EnumConst enumConstByFlagPaymentType,
			EnumConst enumConstByFlagOrderState,
			EnumConst enumConstByFlagPaymentMethod,
			EnumConst enumConstByFlagOrderType,
			EnumConst enumConstByFlagRefundState,
			EnumConst enumConstByFlagCheckState,
			EnumConst enumConstByFlagZhiFuState,
			EnumConst enumConstByFlagFaPiaoState, String checkNote,
			Date checkDate, String seq, String cname, String amount,
			String num, String operater, String operateType, Date operateDate,
			Date paymentDate, Date createDate, String payer, String classHour,
			String tel, PeBzzBatch peBzzBatch,
			PeElectiveCoursePeriod peElectiveCoursePeriod,
			PeSignUpOrder peSignUpOrder, PeEnterprise peEnterprise,
			Set peBzzInvoiceInfos, Set peBzzRefundInfos,
			Set prBzzTchStuElectives, Set studentBatchs,
			Set prBzzTchStuElectiveBack, Set prepaidAmountRecord,
			SsoUser ssoUser, EnumConst enumConstByFlagOrderIsValid,
			EnumConst enumConstByFlagOrderIsIncomplete, int autoCheckTimes,
			Date refundDate, String refundId) {
		super();
		this.id = id;
		this.enumConstByFlagPaymentState = enumConstByFlagPaymentState;
		this.enumConstByFlagPaymentType = enumConstByFlagPaymentType;
		this.enumConstByFlagOrderState = enumConstByFlagOrderState;
		this.enumConstByFlagPaymentMethod = enumConstByFlagPaymentMethod;
		this.enumConstByFlagOrderType = enumConstByFlagOrderType;
		this.enumConstByFlagRefundState = enumConstByFlagRefundState;
		this.enumConstByFlagCheckState = enumConstByFlagCheckState;
		this.enumConstByFlagZhiFuState = enumConstByFlagZhiFuState;
		this.enumConstByFlagFaPiaoState = enumConstByFlagFaPiaoState;
		this.checkNote = checkNote;
		this.checkDate = checkDate;
		this.seq = seq;
		this.cname = cname;
		this.amount = amount;
		this.num = num;
		this.operater = operater;
		this.operateType = operateType;
		this.operateDate = operateDate;
		this.paymentDate = paymentDate;
		this.createDate = createDate;
		this.payer = payer;
		this.classHour = classHour;
		this.tel = tel;
		this.peBzzBatch = peBzzBatch;
		this.peElectiveCoursePeriod = peElectiveCoursePeriod;
		this.peSignUpOrder = peSignUpOrder;
		this.peEnterprise = peEnterprise;
		this.peBzzInvoiceInfos = peBzzInvoiceInfos;
		this.peBzzRefundInfos = peBzzRefundInfos;
		this.prBzzTchStuElectives = prBzzTchStuElectives;
		this.studentBatchs = studentBatchs;
		this.prBzzTchStuElectiveBack = prBzzTchStuElectiveBack;
		this.prepaidAmountRecord = prepaidAmountRecord;
		this.ssoUser = ssoUser;
		this.enumConstByFlagOrderIsValid = enumConstByFlagOrderIsValid;
		this.enumConstByFlagOrderIsIncomplete = enumConstByFlagOrderIsIncomplete;
		this.autoCheckTimes = autoCheckTimes;
		this.refundDate = refundDate;
		this.refundId = refundId;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public EnumConst getEnumConstByFlagOrderIsValid() {
		return enumConstByFlagOrderIsValid;
	}

	public void setEnumConstByFlagOrderIsValid(EnumConst enumConstByFlagOrderIsValid) {
		this.enumConstByFlagOrderIsValid = enumConstByFlagOrderIsValid;
	}

	public EnumConst getEnumConstByFlagRefundState() {
		return enumConstByFlagRefundState;
	}

	public void setEnumConstByFlagRefundState(EnumConst enumConstByFlagRefundState) {
		this.enumConstByFlagRefundState = enumConstByFlagRefundState;
	}

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	/** default constructor */
	public PeBzzOrderInfo() {
	}

	/** full constructor */
	
	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagPaymentState() {
		return this.enumConstByFlagPaymentState;
	}

	public void setEnumConstByFlagPaymentState(
			EnumConst enumConstByFlagPaymentState) {
		this.enumConstByFlagPaymentState = enumConstByFlagPaymentState;
	}

	public EnumConst getEnumConstByFlagPaymentType() {
		return this.enumConstByFlagPaymentType;
	}

	public void setEnumConstByFlagPaymentType(
			EnumConst enumConstByFlagPaymentType) {
		this.enumConstByFlagPaymentType = enumConstByFlagPaymentType;
	}

	public EnumConst getEnumConstByFlagOrderState() {
		return this.enumConstByFlagOrderState;
	}

	public void setEnumConstByFlagOrderState(EnumConst enumConstByFlagOrderState) {
		this.enumConstByFlagOrderState = enumConstByFlagOrderState;
	}

	public EnumConst getEnumConstByFlagPaymentMethod() {
		return this.enumConstByFlagPaymentMethod;
	}

	public void setEnumConstByFlagPaymentMethod(
			EnumConst enumConstByFlagPaymentMethod) {
		this.enumConstByFlagPaymentMethod = enumConstByFlagPaymentMethod;
	}

	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}



	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPayer() {
		return this.payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public Set getPeBzzInvoiceInfos() {
		return this.peBzzInvoiceInfos;
	}

	public void setPeBzzInvoiceInfos(Set peBzzInvoiceInfos) {
		this.peBzzInvoiceInfos = peBzzInvoiceInfos;
	}

	public Set getPeBzzRefundInfos() {
		return this.peBzzRefundInfos;
	}

	public void setPeBzzRefundInfos(Set peBzzRefundInfos) {
		this.peBzzRefundInfos = peBzzRefundInfos;
	}

	public EnumConst getEnumConstByFlagOrderType() {
		return enumConstByFlagOrderType;
	}

	public void setEnumConstByFlagOrderType(EnumConst enumConstByFlagOrderType) {
		this.enumConstByFlagOrderType = enumConstByFlagOrderType;
	}

	public String getClassHour() {
		return classHour;
	}

	public void setClassHour(String classHour) {
		this.classHour = classHour;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Set getPrBzzTchStuElectives() {
		return prBzzTchStuElectives;
	}

	public void setPrBzzTchStuElectives(Set prBzzTchStuElectives) {
		this.prBzzTchStuElectives = prBzzTchStuElectives;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Set getStudentBatchs() {
		return studentBatchs;
	}

	public void setStudentBatchs(Set studentBatchs) {
		this.studentBatchs = studentBatchs;
	}

	public Set getPrBzzTchStuElectiveBack() {
		return prBzzTchStuElectiveBack;
	}

	public void setPrBzzTchStuElectiveBack(Set prBzzTchStuElectiveBack) {
		this.prBzzTchStuElectiveBack = prBzzTchStuElectiveBack;
	}

	public PeBzzBatch getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public PeElectiveCoursePeriod getPeElectiveCoursePeriod() {
		return peElectiveCoursePeriod;
	}

	public void setPeElectiveCoursePeriod(
			PeElectiveCoursePeriod peElectiveCoursePeriod) {
		this.peElectiveCoursePeriod = peElectiveCoursePeriod;
	}

	public PeSignUpOrder getPeSignUpOrder() {
		return peSignUpOrder;
	}

	public void setPeSignUpOrder(PeSignUpOrder peSignUpOrder) {
		this.peSignUpOrder = peSignUpOrder;
	}

	public EnumConst getEnumConstByFlagOrderIsIncomplete() {
		return enumConstByFlagOrderIsIncomplete;
	}

	public void setEnumConstByFlagOrderIsIncomplete(
			EnumConst enumConstByFlagOrderIsIncomplete) {
		this.enumConstByFlagOrderIsIncomplete = enumConstByFlagOrderIsIncomplete;
	}

	public PeEnterprise getPeEnterprise() {
		return peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public int getAutoCheckTimes() {
		return autoCheckTimes;
	}

	public void setAutoCheckTimes(int autoCheckTimes) {
		this.autoCheckTimes = autoCheckTimes;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public EnumConst getEnumConstByFlagCheckState() {
		return enumConstByFlagCheckState;
	}

	public void setEnumConstByFlagCheckState(EnumConst enumConstByFlagCheckState) {
		this.enumConstByFlagCheckState = enumConstByFlagCheckState;
	}

	public String getCheckNote() {
		return checkNote;
	}

	public void setCheckNote(String checkNote) {
		this.checkNote = checkNote;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public EnumConst getEnumConstByFlagZhiFuState() {
		return enumConstByFlagZhiFuState;
	}

	public void setEnumConstByFlagZhiFuState(EnumConst enumConstByFlagZhiFuState) {
		this.enumConstByFlagZhiFuState = enumConstByFlagZhiFuState;
	}

	public EnumConst getEnumConstByFlagFaPiaoState() {
		return enumConstByFlagFaPiaoState;
	}

	public void setEnumConstByFlagFaPiaoState(EnumConst enumConstByFlagFaPiaoState) {
		this.enumConstByFlagFaPiaoState = enumConstByFlagFaPiaoState;
	}



	public void setPrepaidAmountRecord(Set prepaidAmountRecord) {
		this.prepaidAmountRecord = prepaidAmountRecord;
	}

	public Set getPrepaidAmountRecord() {
		return prepaidAmountRecord;
	}

	public Set getRecordAssign() {
		return recordAssign;
	}

	public void setRecordAssign(Set recordAssign) {
		this.recordAssign = recordAssign;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}