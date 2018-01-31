package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.AssignRecord;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeSignUpOrder;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class CollectiveElectiveAction extends MyBaseAction {

	private List<Map> electiveMapList = new ArrayList();
	private List<PeBzzStudent> peBzzStudentList;
	private List courseList = new ArrayList();
	private BigDecimal allCredit = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal allAmount = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
	private String isInvoice;
	private String payMethod;
	private String sso_user_amount;
	private PeBzzOrderInfo peBzzOrderInfo;
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private PrBzzTchStuElective prBzzTchStuElective;
	private List<PrBzzTchStuElective> electiveList = new ArrayList<PrBzzTchStuElective>();
	private List<PrBzzTchStuElectiveBack> electiveBackList = new ArrayList<PrBzzTchStuElectiveBack>();
	private String compareResult;// 比较结果
	private EnumConstService enumConstService;
	private String merorderid; // 网银支付由银行生成的单据号码
	// Lee start 2014年1月23日 报名单ID
	private boolean err_course;// 是否有无效课程
	private String err_msg;// 无效课程错误信息
	private String peSignupOrderId;
	private String postType;

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getPeSignupOrderId() {
		return peSignupOrderId;
	}

	public void setPeSignupOrderId(String peSignupOrderId) {
		this.peSignupOrderId = peSignupOrderId;
	}

	// Lee end

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/collectiveElective";
	}

	public String toEelectiveBatchUpload() {
		return "electiveBatchUpload";
	}

	public List<PrBzzTchStuElectiveBack> getElectiveBackList() {
		return electiveBackList;
	}

	public void setElectiveBackList(List<PrBzzTchStuElectiveBack> electiveBackList) {
		this.electiveBackList = electiveBackList;
	}

	/**
	 * 直接选课excel上传
	 * 
	 * @author linjie
	 * @return
	 */
	public String BatchUpload() {
		try {
			this.saveBatchElective(this.get_upload());
			if (err_course) {
				this.setMsg(err_msg);
				return "m_msg";
			}
		} catch (EntityException e) {
			super.setDoLog(false);// 出错信息不记录
			// TODO Auto-generated catch block
			// e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("/entity/basic/collectiveElective_toEelectiveBatchUpload.action");
			return "m_msg";
		}
		return "confirmElective";
	}

	/**
	 * 确认订单信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String toConfirmOrderInfo() {
		getInvoice();// 获得历史发票信息
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("loginId", us.getLoginId()));
			List<SsoUser> listUser = this.getGeneralService().getList(dc);
			SsoUser ssoUser = listUser.get(0);
			/*******************************************************************
			 * !!!!!!! 要计算修改后回写数据库的(尤其金额等敏感数据) 不能读session中数据 !!!!!!!
			 ******************************************************************/
			// String balance = us.getSsoUser().getBalance();
			String balance = ssoUser.getBalance();
			if (Double.parseDouble(balance) >= Double.parseDouble(ActionContext.getContext().getSession().get("allCredit").toString())) {
				this.compareResult = "1";
			} else {
				this.compareResult = "2";
			}
			this.peBzzOrderInfo = new PeBzzOrderInfo();
			this.peBzzOrderInfo.setSeq(this.getGeneralService().getOrderSeq());
			this.peBzzOrderInfo.setAmount(ActionContext.getContext().getSession().get("allAmount").toString());
			this.peBzzOrderInfo.setClassHour(ActionContext.getContext().getSession().get("allCredit").toString());
			if ("y".equalsIgnoreCase(this.isInvoice)) {
				this.peBzzInvoiceInfo.setPeBzzOrderInfo(this.peBzzOrderInfo);
				ActionContext.getContext().getSession().put("peBzzInvoiceInfo", peBzzInvoiceInfo);
			} else {
				ActionContext.getContext().getSession().put("peBzzOrderInfo", peBzzOrderInfo);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Lee start 2014年1月23日 查看报名单课程的sql
		String sql = "SELECT DISTINCT C.CODE FROM PE_SIGNUP_ORDER A INNER JOIN PR_SIGNUP_ORDER_COURSE B ON A.ID = B.FK_SIGNUP_ORDER_ID INNER JOIN PE_BZZ_TCH_COURSE C ON B.COURSE_ID = C.ID WHERE A.ID = '"
				+ this.getPeSignupOrderId() + "' AND C.FLAG_OFFLINE <> '22'";
		List course_list = new ArrayList();
		try {
			course_list = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			this.setMsg("确认操作失败！请联系管理员！");
		}
		if (course_list != null && course_list.size() > 0) {
			String course_str = "";
			for (Object o : course_list) {
				course_str += " " + o;
			}
			this.setMsg("课程：" + course_str + "已下线，请修改报名单！");
			this.setTogo("close");
			return "m_msg";
		} else {
			// Lee end
			return "confirmOrderInfo";
		}
	}

	/*
	 * public String toConfirmPaymentMethod(){ try { UserSession us =
	 * (UserSession) ServletActionContext.getRequest()
	 * .getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	 * DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
	 * dc.add(Restrictions.eq("loginId", us.getLoginId())); List<SsoUser>
	 * listUser = this.getGeneralService().getList(dc); SsoUser ssoUser =
	 * listUser.get(0); /*!!!!!!! 要计算修改后回写数据库的(尤其金额等敏感数据) 不能读session中数据 !!!!!!!/
	 * //String balance = us.getSsoUser().getBalance(); String balance =
	 * ssoUser.getBalance();
	 * if(Double.parseDouble(balance)>=Double.parseDouble(ActionContext.getContext().getSession().get("allCredit").toString())){
	 * this.compareResult = "1"; }else{ this.compareResult = "2"; }
	 * this.peBzzOrderInfo.setSeq(this.getGeneralService().getOrderSeq());
	 * this.peBzzOrderInfo.setAmount(ActionContext.getContext().getSession().get("allAmount").toString());
	 * this.peBzzOrderInfo.setClassHour(ActionContext.getContext().getSession().get("allCredit").toString());
	 * if ("y".equalsIgnoreCase(this.isInvoice)) {
	 * this.peBzzInvoiceInfo.setPeBzzOrderInfo(this.peBzzOrderInfo);
	 * ActionContext.getContext().getSession().put("peBzzInvoiceInfo",
	 * peBzzInvoiceInfo); }else{
	 * ActionContext.getContext().getSession().put("peBzzOrderInfo",
	 * peBzzOrderInfo); } } catch (EntityException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } return "confirmPaymentMethod"; }
	 */

	/**
	 * 初始化要保存的订单和选课信息
	 */
	public void initOrderAndElective() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		// 是否申请发票
		/**
		 * 如果直接从session中取peBzzInvoiceInfo或peBzzOrderInfo 会将本次传参覆盖掉，
		 */
		PeBzzInvoiceInfo _peBzzInvoiceInfo = (PeBzzInvoiceInfo) ActionContext.getContext().getSession().get("peBzzInvoiceInfo");
		PeBzzOrderInfo _peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext().getSession().get("peBzzOrderInfo");
		;
		if (_peBzzOrderInfo != null) {
			_peBzzOrderInfo.setCname(peBzzOrderInfo.getCname());
			_peBzzOrderInfo.setNum(peBzzOrderInfo.getNum());
			peBzzOrderInfo = _peBzzOrderInfo;
		}

		if ("y".equalsIgnoreCase(this.isInvoice) && !"1".equalsIgnoreCase(this.payMethod)) {// 选中开发票，并且不是预付费支付
			EnumConst enumConstByFlagFpOpenState = this.getEnumConstService().getByNamespaceCode("FlagFpOpenState", "0");// 待开发票
			EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);
			if (_peBzzInvoiceInfo != null) {
				_peBzzInvoiceInfo.setAddress(peBzzInvoiceInfo.getAddress());
				_peBzzInvoiceInfo.setAddressee(peBzzInvoiceInfo.getAddressee());
				_peBzzInvoiceInfo.setNum(peBzzInvoiceInfo.getNum());
				_peBzzInvoiceInfo.setPhone(peBzzInvoiceInfo.getPhone());
				_peBzzInvoiceInfo.setTitle(peBzzInvoiceInfo.getTitle());
				_peBzzInvoiceInfo.setZipCode(peBzzInvoiceInfo.getZipCode());
				peBzzInvoiceInfo = _peBzzInvoiceInfo;
			}
			peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);// 待开发票
			peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);//邮寄方式
			peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
		} else {
			peBzzInvoiceInfo = null;
		}

		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", this.payMethod);// 支付方式
		EnumConst enumConstByFlagPaymentType = this.getEnumConstService().getByNamespaceCode("FlagPaymentType", "1");// 集体支付
		EnumConst enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "4");// 订单类型,直接选课
		EnumConst enumConstByFlagOrderState = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "1");// 订单状态，正常
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");// 订单初始化为有效状态
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");// 未支付
		EnumConst enumConstByFlagBatchType = this.getEnumConstService().getByNamespaceCode("FlagBatchType", "1");// 专项状态，非专项，普通课程
		EnumConst enumConstByFlagCheckState = this.getEnumConstService().getByNamespaceCode("FlagCheckState", "0");// 对账状态

		if (ActionContext.getContext().getSession().get("signUpOrder2PayFlag") != null && (ActionContext.getContext().getSession().get("signUpOrder2PayFlag") instanceof PeSignUpOrder)) {
			enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "5");// 在线填写报名单
			this.peBzzOrderInfo.setPeSignUpOrder((PeSignUpOrder) ActionContext.getContext().getSession().get("signUpOrder2PayFlag"));
			ActionContext.getContext().getSession().remove("signUpOrder2PayFlag");
		} else {
			enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "4");
		}
		this.peBzzOrderInfo.setEnumConstByFlagOrderIsValid(enumConstByFlagOrderIsValid); // 订单初始化为有效状态
		this.peBzzOrderInfo.setEnumConstByFlagPaymentMethod(enumConstByFlagPaymentMethod);
		this.peBzzOrderInfo.setEnumConstByFlagPaymentType(enumConstByFlagPaymentType);
		this.peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);// 未支付
		this.peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType);
		this.peBzzOrderInfo.setEnumConstByFlagOrderState(enumConstByFlagOrderState);// 订单状态，正常
		this.peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState);// 订单对账状态
		this.peBzzOrderInfo.setSsoUser(us.getSsoUser());
		this.peBzzOrderInfo.setCreateDate(new Date());
		try {
			this.peBzzOrderInfo.setPayer(this.getGeneralService().getEnterpriseManagerByLoginId(us.getSsoUser().getLoginId()).getName());
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		this.allAmount = new BigDecimal(this.peBzzOrderInfo.getAmount()).setScale(2);
		this.electiveMapList = (ArrayList<Map>) ActionContext.getContext().getSession().get("electiveMapList");

		PeBzzStudent peBzzStudent;
		List<PrBzzTchOpencourse> openCourseList = new ArrayList<PrBzzTchOpencourse>();

		String courseStr = "";
		for (Map electiveMap : this.electiveMapList) {
			peBzzStudent = (PeBzzStudent) electiveMap.get("peBzzStudent");
			courseStr = electiveMap.get("courseStr").toString();
			DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
			dc.createAlias("peBzzTchCourse", "peBzzTchCourse");
			dc.add(Restrictions.in("peBzzTchCourse.code", courseStr.split("\\,")));
			// 必须是默认专项的开课
			dc.createAlias("peBzzBatch", "peBzzBatch").add(Restrictions.eq("peBzzBatch.enumConstByFlagBatchType", enumConstByFlagBatchType));

			try {
				openCourseList = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if (openCourseList != null && openCourseList.size() > 0)
				for (PrBzzTchOpencourse opencourse : openCourseList) {
					// TrainingCourseStudent trainingCourseStudent = new
					// TrainingCourseStudent();
					// trainingCourseStudent.setPrBzzTchOpencourse(opencourse);
					// trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
					// trainingCourseStudent.setPercent(0.00);
					// trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);

					// PrBzzTchStuElective elective = new PrBzzTchStuElective();
					// elective.setPeBzzStudent(peBzzStudent);
					// elective.setPrBzzTchOpencourse(opencourse);
					// elective.setPeBzzOrderInfo(this.peBzzOrderInfo);
					// elective.setSsoUser(us.getSsoUser());
					// elective.setElectiveDate(new Date());
					// elective.setTrainingCourseStudent(trainingCourseStudent);
					// if(("1".equalsIgnoreCase(this.payMethod))){
					// EnumConst enumConstByFlagElectivePayStatus =
					// this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus",
					// "1");//we支付状态
					// elective.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
					// }
					// this.electiveList.add(elective);
					PrBzzTchStuElectiveBack pb = new PrBzzTchStuElectiveBack();
					pb.setPeBzzStudent(peBzzStudent);
					pb.setPrBzzTchOpencourse(opencourse);
					pb.setPeBzzOrderInfo(this.peBzzOrderInfo);
					pb.setSsoUser(us.getSsoUser());
					this.electiveBackList.add(pb);
				}
		}
	}

	/**
	 * 清除session中订单信息
	 */
	public void destroyOrderSession() {
		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzInvoiceInfo") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzInvoiceInfo");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzOrderInfo") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzOrderInfo");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("electiveList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("electiveList");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("electiveMapList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("electiveMapList");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("allAmount") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("allAmount");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("allCredit") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("allCredit");
		}
	}

	/**
	 * 获取课时的价格
	 * 
	 * @return
	 */
	public BigDecimal getClassHourPrice() {
		EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
		return new BigDecimal(ec.getName()).setScale(2);
	}

	/**
	 * 确认单后去支付
	 * 
	 * @return
	 */
	public String toPayment() {
		if (this.payMethod == null || "".equalsIgnoreCase(this.payMethod) || "0,1,2,3".indexOf(this.payMethod) < 0) {
			this.setMsg("请正确选择支付方式！");
			this.setTogo("close");
			return "paymentMsg";
		}
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser user = us.getSsoUser();
		DetachedCriteria dcSsoUser = DetachedCriteria.forClass(SsoUser.class);
		dcSsoUser.add(Restrictions.eq("id", us.getSsoUser().getId()));
		// 获取托管状态的ssoUser实例
		try {
			user = (SsoUser) this.getGeneralService().getDetachList(dcSsoUser).get(0);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// BigDecimal price = this.allAmount;
		BigDecimal price = this.getClassHourPrice();
		// BigDecimal bdAllAmount = new BigDecimal(this.allAmount);

		// 初始化订单
		this.initOrderAndElective();

		if ("0".equalsIgnoreCase(this.payMethod)) {// 第三方支付
			try {
				// 实例化订单
				this.generateOrder(user);
			} catch (EntityException e) {
				e.printStackTrace();
				this.setMsg("订单生成失败，请联系管理员");
				return "paymentMsg";
			}

			ServletActionContext.getRequest().getSession().setAttribute("electiveList", this.electiveList);
			ServletActionContext.getRequest().getSession().setAttribute(this.peBzzOrderInfo.getSeq(), "colElePayment");
			// 新增快钱支付
			String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
			ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);
			return "onlinePayment";
		} else if ("1".equalsIgnoreCase(this.payMethod)) {// 预付费支付
			EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "1");
			EnumConst enumConstByFlagElectivePayStatus = this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus", "1");

			BigDecimal balance = new BigDecimal(user.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
			int result = balance.multiply(price).compareTo(allAmount);
			if (result == -1) {
				this.setMsg("当前账户余额不足");
				return "paymentMsg";
			} else {
				// 初始化订单和eleback数据，支付成功后才去生成elective数据
				try {
					// 实例化订单
					this.generateOrder(user);
				} catch (EntityException e) {
					e.printStackTrace();
					this.setMsg("订单生成失败，请联系管理员");
					return "paymentMsg";
				}
				this.sso_user_amount = user.getAmount();
				BigDecimal amount = new BigDecimal(user.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
				// 改成学时
				this.allCredit = new BigDecimal(ActionContext.getContext().getSession().get("allCredit").toString());
				user.setAmount(amount.add(this.allCredit).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

				try {
					BigDecimal zhyAmountnew = new BigDecimal(user.getSumAmount()).subtract(new BigDecimal(user.getAmount()));// 账户余额

					EnumConst enumConstByFlagOperateType = this.getEnumConstService().getByNamespaceCode("FlagOperateType", "5"); // 金额分配记录
					List saveList = new ArrayList();
					AssignRecord assignRecord = new AssignRecord();
					assignRecord.setSsoUser(user);
					assignRecord.setAssignStyle("operate5");
					assignRecord.setAssignDate(new Date());
					assignRecord.setAccountAmount(zhyAmountnew.toString());
					// 改成学时
					assignRecord.setOperateAmount(peBzzOrderInfo.getClassHour());
					assignRecord.setEnumConstByFlagOperateType(enumConstByFlagOperateType);
					assignRecord.setPeBzzOrderInfo(peBzzOrderInfo);
					saveList.add(assignRecord);
					this.getGeneralService().saveList(saveList);
				} catch (EntityException e1) {
					e1.printStackTrace();
				}
				/**
				 * 将session中的数据也改变
				 */
				us.getSsoUser().setAmount(user.getAmount());
				try {
					this.peBzzOrderInfo.setPaymentDate(new Date());
					// 确认订单到账
					this.getGeneralService().updatePeBzzOrderInfo(peBzzOrderInfo, "confirm", user);
				} catch (EntityException e) {
					user.setAmount(this.sso_user_amount);
					e.printStackTrace();
					this.setMsg("选课失败:" + e.getMessage());
					return "paymentMsg";
				}
				// user.setAmount(String.valueOf(Double.parseDouble(user.getAmount())
				// + this.allAmount/price));
				this.setMsg("成功添加选课记录");
			}
		} else {// 支票和电汇支付

			// 初始化订单和eleback数据，支付成功后才去生成elective数据
			try {
				// 实例化订单
				this.generateOrder(user);
			} catch (EntityException e) {
				e.printStackTrace();
				this.setMsg("订单生成失败，请联系管理员");
				return "paymentMsg";
			}
			this.setMsg("选课记录提交成功，请尽快付款");
		}
		this.destroyOrderSession();
		return "paymentMsg";
	}

	/**
	 * 生成订单
	 * 
	 * @author linjie
	 * @return
	 */
	public void generateOrder(SsoUser user) throws EntityException {

		this.peBzzOrderInfo.setSsoUser(user);
		try {
			this.electiveBackList = this.getGeneralService().saveElectiveBackList(this.electiveBackList, peBzzOrderInfo, peBzzInvoiceInfo, null, null, null);
		} catch (EntityException e) {
			throw e;
		}
	}

	/**
	 * 后来添加，再次支付
	 */
	public String continuePaymentOnline() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("seq", this.merorderid));
		try {
			this.peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
			ServletActionContext.getRequest().getSession().setAttribute(this.peBzzOrderInfo.getSeq(), "colElePayment");
			// 新增快钱支付
			String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
			ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			this.setMsg("支付直接选课失败,请联系协会管理员");
			this.setTogo("javascript：window.close();");
			return "paymentMsg";
		}
		return "onlinePayment";
	}

	// /**
	// * 第三方支付确认
	// * @return
	// */
	// public String confirmOnlinePayment(){
	//		
	// DetachedCriteria orderDc =
	// DetachedCriteria.forClass(PeBzzOrderInfo.class);
	// orderDc.createCriteria("ssoUser", "ssoUser");
	// orderDc.createCriteria("enumConstByFlagPaymentState",
	// "enumConstByFlagPaymentState");
	// orderDc.createAlias("peSignUpOrder", "peSignUpOrder",orderDc.LEFT_JOIN);
	// orderDc.add(Restrictions.eq("seq", this.merorderid));
	// PeBzzOrderInfo order = null;
	// try {
	// order = (PeBzzOrderInfo)this.getGeneralService().getList(orderDc).get(0);
	// } catch (EntityException e2) {
	// // TODO Auto-generated catch block
	// e2.printStackTrace();
	// }
	//		
	// try {
	// //确认订单
	// this.getGeneralService().updatePeBzzOrderInfo(order, "confirm",null);
	//			
	// } catch (EntityException e) {
	// this.setMsg("支付失败");
	// return "paymentMsg";
	// }
	//		
	// // EnumConst enumConstByFlagPaymentState =
	// this.getEnumConstService().getByNamespaceCode("FlagPaymentState",
	// "1");//已支付
	// // EnumConst enumConstByFlagElectivePayStatus =
	// this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus",
	// "1");//已支付状态
	// // order.setPaymentDate(new Date());
	// // //order.setNum(order.getSeq());
	// // order.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
	// // /**
	// // * 根据查询的订单来查询备份的选课信息
	// // */
	// // DetachedCriteria eleBackDc =
	// DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
	// // eleBackDc.createCriteria("ssoUser", "ssoUser");
	// // eleBackDc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
	// // eleBackDc.createCriteria("peBzzStudent", "peBzzStudent");
	// // eleBackDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
	// // eleBackDc.add(Restrictions.eq("peBzzOrderInfo", order));
	// // List<PrBzzTchStuElectiveBack> eleBackList = null;
	// // try {
	// // eleBackList = this.getGeneralService().getList(eleBackDc);
	// // } catch (EntityException e1) {
	// // // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// // }
	// //
	// //
	// // /**
	// // * 恢复选课信息
	// // */
	// // List eleList = new ArrayList();
	// // for(PrBzzTchStuElectiveBack back : eleBackList) {
	// // /**
	// // * 将备份表中的数据导入到选课正式表中
	// // */
	// // PrBzzTchStuElective ele = new PrBzzTchStuElective();
	// //
	// ele.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
	// // ele.setElectiveDate(new Date());
	// // ele.setPeBzzOrderInfo(order);
	// // ele.setPeBzzStudent(back.getPeBzzStudent());
	// // ele.setPrBzzTchOpencourse(back.getPrBzzTchOpencourse());
	// // ele.setSsoUser(back.getSsoUser());
	// // /**
	// // * 课程状态记录
	// // */
	// // TrainingCourseStudent trainingCourseStudent = new
	// TrainingCourseStudent();
	// //
	// trainingCourseStudent.setPrBzzTchOpencourse(back.getPrBzzTchOpencourse());
	// // trainingCourseStudent.setSsoUser(ele.getPeBzzStudent().getSsoUser());
	// // trainingCourseStudent.setPercent(0.00);
	// // trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
	// // ele.setTrainingCourseStudent(trainingCourseStudent);
	// // eleList.add(ele);
	// // }
	// // Set<String> set = new HashSet<String>();
	// // List resultList = new ArrayList();
	// // for(int i=0;i<eleList.size();i++){
	// // PrBzzTchStuElective temp = (PrBzzTchStuElective)eleList.get(i);
	// // String stuId = temp.getPeBzzStudent().getId() +
	// temp.getPrBzzTchOpencourse().getId();
	// // if(!set.contains(stuId)){
	// // set.add(stuId);
	// // resultList.add(temp);
	// // }
	// // }
	// // eleList = resultList;
	// // try {
	// // this.getGeneralService().saveElectiveList(eleList, order, null,
	// order.getSsoUser(), null);
	// // } catch (EntityException e) {
	// // this.setMsg("支付失败");
	// // return "paymentMsg";
	// // }
	// this.setMsg("订单"+StringUtils.defaultString(order.getSeq())+"支付成功！");
	// return "paymentMsg";
	// /*if(ActionContext.getContext().getSession().get("peBzzInvoiceInfo") !=
	// null){
	// this.peBzzInvoiceInfo = (PeBzzInvoiceInfo)
	// ActionContext.getContext().getSession().get("peBzzInvoiceInfo");
	// this.peBzzOrderInfo = this.peBzzInvoiceInfo.getPeBzzOrderInfo();
	// }else {
	// this.peBzzOrderInfo = (PeBzzOrderInfo)
	// ActionContext.getContext().getSession().get("peBzzOrderInfo");
	// }
	// this.peBzzOrderInfo.setNum(this.merorderid);
	// if (ActionContext.getContext().getSession().get("electiveList") != null)
	// {
	// this.electiveList = (List<PrBzzTchStuElective>)
	// ActionContext.getContext().getSession().get("electiveList");
	// }*/
	// /*EnumConst enumConstByFlagPaymentState =
	// this.getEnumConstService().getByNamespaceCode("FlagPaymentState",
	// "1");//已支付
	// EnumConst enumConstByFlagElectivePayStatus =
	// this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus",
	// "1");//已支付状态
	// this.peBzzOrderInfo.setPaymentDate(new Date());
	// this.peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
	// this.peBzzOrderInfo.setPaymentDate(new Date());
	// for(PrBzzTchStuElective p : this.electiveList) {
	// p.setPeBzzOrderInfo(peBzzOrderInfo);
	// p.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
	// }
	// this.peBzzOrderInfo.setPaymentDate(new Date());
	// try {
	// this.electiveList =
	// this.getGeneralService().saveElectiveList(this.electiveList,
	// peBzzOrderInfo, peBzzInvoiceInfo, null);
	// } catch (EntityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// this.setMsg("支付失败");
	// return "msg";
	// }
	// this.setMsg("成功支付"+electiveList.size()+"条选课");
	// this.destroyOrderSession();
	// return "msg";*/
	// }

	/**
	 * 报错直接选课信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void saveBatchElective(File file) throws EntityException {
		// System.out.println(" ===========before========" +
		// System.currentTimeMillis());
		StringBuffer msg = new StringBuffer();
		Workbook work = null;

		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		if (rows > 5002) {
			msg.append("请保证操作5000以内的选课，过多的选课分批次进行！<br/>");
			throw new EntityException(msg.toString());
		}
		// Lee start 2014年1月23日 加载Excel中所有课程编号 查询是否有无效课程
		int idx = 0;// 计数器拼接sql判断用 非公共课程、未发布、已下线
		String sql = "SELECT DISTINCT CODE FROM PE_BZZ_TCH_COURSE WHERE (FLAG_COURSEAREA != 'Coursearea1' OR FLAG_ISVALID != '2' OR FLAG_OFFLINE != '22') AND (";
		String course_params = "";
		if (rows <= 500) {
			for (int i = 0; i < rows; i++) {
				String course_code = sheet.getCell(2, i).getContents().toUpperCase();
				if (course_code != "" && course_code.length() > 0)
					course_params += "'" + course_code.trim() + "',";
			}
			sql += " CODE IN (" + course_params + "'') ";
			course_params = "";
		} else {
			for (int i = 1; i < rows; i++) {
				String course_code = sheet.getCell(2, i).getContents().toUpperCase();
				if (course_code != "" && course_code.length() > 0)
					course_params += "'" + course_code.trim() + "',";
				if (i % 500 == 0 && idx == 0) {
					idx++;
					sql += " CODE IN (" + course_params + "'') ";
					course_params = "";
				}
				if (i % 500 == 0 && idx != 0 && course_params != "" && course_params.length() > 0) {
					sql += " OR CODE IN (" + course_params + "'') ";
					course_params = "";
				}
			}
		}
		sql += ")";
		// String sql = "SELECT DISTINCT CODE FROM PE_BZZ_TCH_COURSE WHERE
		// FLAG_OFFLINE <> '22' AND CODE IN ("
		// + course_params + "'')";
		List offline_list = this.getGeneralService().getBySQL(sql);
		if (offline_list != null && offline_list.size() > 0) {
			String offline_str = "";
			for (Object o : offline_list) {
				offline_str += " " + o;
			}
			err_msg = "导入失败！课程编号为：" + offline_str + "的课程无效，请检查后重新导入！";
			err_course = true;
		} else {
			err_course = false;
			// Lee end

			String temp = "";
			String temp1 = "";

			String flagIsFree = this.getEnumConstService().getByNamespaceCode("FlagIsFree", "0").getId();
			String flagIsvalid = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1").getId();

			Map<String, Set<String>> uniqueEleMap = new HashMap<String, Set<String>>();
			int kilos = (rows % 1000 == 0) ? (rows / 1000) : ((rows / 1000) + 1);

			for (int k = 0; k < kilos; k++) {// LOOP START
				// 查询学员信息
				Map<String, List<String>> stuInfoMap = new HashMap<String, List<String>>();
				// 将所有信息均添加到map中
				Map<String, Map<String, String>> stuAllInfoMap = new HashMap<String, Map<String, String>>();
				// 将所有的课程编号放到一个set中
				Set<String> courseIdSet = new HashSet<String>();
				// 课程信息的map
				Map<String, List<String>> courseInfoMap = new HashMap<String, List<String>>();
				StringBuffer stuIds = new StringBuffer();// 学员编号ids
				String studentIds = "";
				stuIds.append("(");
				for (int i = k * 1000 + 1; i < (k + 1) * 1000 + 1 && i < rows; i++) {
					String stuId = upper(sheet.getCell(1, i).getContents());
					temp = upper(sheet.getCell(2, i).getContents());
					stuIds.append("'" + stuId.toUpperCase() + "',");
					Map<String, String> map = new HashMap<String, String>();
					map.put("stuCardNO", upper(sheet.getCell(1, i).getContents()));
					map.put("courseCode", upper(sheet.getCell(2, i).getContents()));
					String[] codeList = temp.split("\\|");
					for (int j = 0; j < codeList.length; j++) {
						if (!courseIdSet.contains(codeList[j])) {
							courseIdSet.add(codeList[j]);
						}
						if (!upper(sheet.getCell(1, i).getContents()).equals("")) {
							// 增加学员不能重复选课的校验
							Set<String> set = uniqueEleMap.get(upper(sheet.getCell(1, i).getContents()));
							if (set == null) {
								set = new HashSet<String>();
							}
							if (set.contains(codeList[j])) {
								msg.append("第" + (i + 1) + "行数据，学员选课存在重复！<br/>");
							} else {
								set.add(codeList[j]);
								uniqueEleMap.put(upper(sheet.getCell(1, i).getContents()), set);
							}
						}
					}
					stuAllInfoMap.put(i + "", map);
				}
				if (stuIds.toString().length() > 1) {
					studentIds = stuIds.toString().substring(0, stuIds.toString().length() - 1);
					studentIds += ")";
				} else {
					studentIds += "('')";
				}

				// 获取课程编号组成的字符串
				StringBuffer courseIds = new StringBuffer();
				String courseIdsStr = "";
				courseIds.append("(");
				for (String s : courseIdSet) {
					courseIds.append("'" + s + "',");
				}
				if (courseIds.toString().length() > 1) {
					courseIdsStr = courseIds.toString().substring(0, courseIds.toString().length() - 1);
					courseIdsStr += ")";
				} else {
					courseIdsStr += "('')";
				}
				// 验证学员信息的sql
				String checkStuSql = "  select ps.id as id, ps.reg_no as reg_no, ps.true_name as true_name,ps.fk_enterprise_id,card_no\n"
						+ "  from ( select id,reg_no,fk_sso_user_id,true_name,fk_enterprise_id,card_no from pe_bzz_student ps where upper(ps.reg_no) in " + studentIds + " ) ps, sso_user su "
						+ "  where ps.fk_sso_user_id = su.id\n" + "  and su.flag_isvalid = '" + flagIsvalid + "'";
				// 对学员机构属性进行判断
				UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
				List<Object[]> resultList = this.getGeneralService().getGeneralDao().getBySQL(checkStuSql);
				String stuDBIdsStr = "(";
				StringBuffer stuDBIds = new StringBuffer();
				for (int i = 0; i < resultList.size(); i++) {
					Object[] objs = resultList.get(i);
					List<String> tmpList = new ArrayList<String>();
					tmpList.add(objs[0].toString());
					tmpList.add(objs[1].toString());
					tmpList.add(objs[2].toString());
					tmpList.add(objs[3].toString());
					// stuInfoMap.put(objs[4].toString(),tmpList);
					stuInfoMap.put(objs[1].toString(), tmpList);
					stuDBIds.append("'" + objs[0].toString() + "',");
				}
				if (stuDBIds.toString().length() > 1) {
					stuDBIdsStr += stuDBIds.toString().substring(0, stuDBIds.toString().length() - 1);
					stuDBIdsStr += ")";
				} else {
					stuDBIdsStr = "('')";
				}
				// 验证课程信息的SQL
				// 必须为默认专项课程 以为公共课程可以添加到专项中 添加后含义就变成专项中的课程 报名就会出问题
				// String checkCourseSql = " select pto.id as opencourse_id,\n"
				// + " ptc.id as course_id,\n" + " ptc.name as course_name,\n" +
				// " ptc.code as course_code,\n"
				// + " ptc.time as course_time,ptc.price \n"
				// + " from pr_bzz_tch_opencourse pto where pto.fk_batch_id =
				// (select id from pe_bzz_batch a inner join enum_const b on
				// a.flag_batch_type = b.id and b.code = '1'),"
				// + " (select id,name,code,time,flag_isfree,price from
				// pe_bzz_tch_course where FLAG_COURSEAREA = 'Coursearea1' AND
				// code in " + courseIdsStr + " and flag_isfree = '"
				// + flagIsFree + "') ptc\n" + " where pto.fk_course_id =
				// ptc.id\n";
				String checkCourseSql = " SELECT PTO.ID AS OPENCOURSE_ID, PTC.ID AS COURSE_ID, PTC.NAME AS COURSE_NAME, PTC.CODE AS COURSE_CODE, PTC.TIME AS COURSE_TIME, PTC.PRICE "
						+ " FROM (SELECT ID, FK_COURSE_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = (SELECT A.ID "
						+ " FROM PE_BZZ_BATCH A INNER JOIN ENUM_CONST B ON A.FLAG_BATCH_TYPE = B.ID AND B.CODE = '1')) PTO, "
						+ " (SELECT ID, NAME, CODE, TIME, FLAG_ISFREE, PRICE FROM PE_BZZ_TCH_COURSE " + " WHERE FLAG_COURSEAREA = 'Coursearea1' AND CODE IN " + courseIdsStr + " AND FLAG_ISFREE = '"
						+ flagIsFree + "') PTC WHERE PTO.FK_COURSE_ID = PTC.ID ";
				List<Object[]> courseInfoList = this.getGeneralService().getGeneralDao().getBySQL(checkCourseSql);
				String courseDBIdsStr = "";// 课程ID的字符串
				StringBuffer courseDBIds = new StringBuffer();
				courseDBIds.append("(");
				for (int i = 0; i < courseInfoList.size(); i++) {
					Object[] objs = courseInfoList.get(i);
					List<String> tmpList = new ArrayList<String>();
					tmpList.add(objs[0].toString());
					tmpList.add(objs[1].toString());
					tmpList.add(objs[2].toString());
					tmpList.add(objs[3].toString());
					tmpList.add(objs[4].toString());
					tmpList.add(objs[5].toString());
					courseInfoMap.put(objs[3].toString(), tmpList);
					courseDBIds.append("'" + objs[1].toString() + "',");
				}
				courseDBIds.append("'')");
				courseDBIdsStr = courseDBIds.toString();
				// 构造验证课程是否已选的SQL
				String checkElectiveBackSql = " select a.fk_stu_id, p.code from ( "
						+ " select  ele.fk_stu_id,pto.fk_course_id from ( select fk_tch_opencourse_id,fk_stu_id from pr_bzz_tch_stu_elective_back where fk_stu_id in " + stuDBIdsStr
						+ "  AND FK_TCH_OPENCOURSE_ID IS NOT NULL) ele, " + " ( select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id in " + courseDBIds + ")  pto "
						+ " where ele.fk_tch_opencourse_id = pto.id )a,  pe_bzz_tch_course p " + "  where a.fk_course_id=p.id  ";
				//Lee 数据补全后 此验证可去掉
				/*
				String checkElectiveSql = " select a.fk_stu_id, p.code from ( "
						+ " select  ele.fk_stu_id,pto.fk_course_id from ( select fk_tch_opencourse_id,fk_stu_id from pr_bzz_tch_stu_elective where fk_stu_id in " + stuDBIdsStr
						+ "  AND FK_TCH_OPENCOURSE_ID IS NOT NULL) ele, " + " ( select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id in " + courseDBIds + ")  pto "
						+ " where ele.fk_tch_opencourse_id = pto.id )a,  pe_bzz_tch_course p " + "  where a.fk_course_id=p.id  ";
				List<Object[]> eleList = this.getGeneralService().getGeneralDao().getBySQL(checkElectiveSql);
				*/
				List<Object[]> eleBackList = this.getGeneralService().getGeneralDao().getBySQL(checkElectiveBackSql);
				// 构造验证课程 是否有效且是否为专项 的SQL
				String checkElectiveSql1 = " SELECT T.CODE, T.ID FROM PE_BZZ_TCH_COURSE T INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON T.ID = PBTO.FK_COURSE_ID AND T.ID IN "
						+ courseDBIds + " INNER JOIN PE_BZZ_BATCH PBB ON PBTO.FK_BATCH_ID = PBB.ID INNER JOIN ENUM_CONST EC ON PBB.FLAG_BATCH_TYPE = EC.ID WHERE (EC.CODE IN ('0', '2') OR T.FLAG_OFFLINE != '22' OR T.FLAG_ISVALID = '3') AND T.FLAG_COURSEAREA != 'Coursearea1' ";
				List<Object[]> eleListIsValid = this.getGeneralService().getGeneralDao().getBySQL(checkElectiveSql1);
				// 查询课程信息
				for (int i = k * 1000 + 1; i < (k + 1) * 1000 + 1 && i < rows; i++) {// 每1000行处理一次start
					Map electiveMap = new HashMap();// 存储信息的map
					BigDecimal amount = new BigDecimal(0.0).setScale(2);
					BigDecimal credit = new BigDecimal(0.0).setScale(2);
					try {
						// 如果系统编号和课程同时为空，结束循环
						if ("".equals(upper(sheet.getCell(1, i).getContents())) && "".equals(upper(sheet.getCell(2, i).getContents()))) {
							break;
						}
						temp = upper(sheet.getCell(1, i).getContents());
						if (temp == null || "".equals(temp)) {
							msg.append("第" + (i + 1) + "行数据，系统编号不能为空！<br/>");
							continue;
						}
						List<String> peStudentList = stuInfoMap.get(temp);
						if (peStudentList == null || peStudentList.isEmpty()) {
							peStudentList = stuInfoMap.get(temp.toLowerCase());
							if (peStudentList == null || peStudentList.isEmpty()) {
								msg.append("第" + (i + 1) + "行数据，系统编号不存在！<br/>");
								continue;
							}
						}
						if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {
							List<PeEnterprise> enterpriseList = us.getPriEnterprises();
							boolean containsFlag = false;
							String entStr = us.getSsoUser().getSubEnterprisesStr();
							String stuEntStr = "" + StringUtils.defaultString(peStudentList.get(3), "|").trim() + "";
							if (entStr != null && !"".equals(entStr) && entStr.contains(stuEntStr)) {
								//
							} else {
								msg.append("第" + (i + 1) + "行数据，该学员不属于当前集体管理员所管机构！<br/>");
								continue;
							}
						}
						PeBzzStudent peBzzStudent = new PeBzzStudent();
						if (peStudentList.size() > 0) {
							peBzzStudent.setId(peStudentList.get(0));
							peBzzStudent.setRegNo(peStudentList.get(1));
							peBzzStudent.setTrueName(peStudentList.get(2));
						}
						temp = upper(sheet.getCell(2, i).getContents());
						if (temp == null || "".equals(temp)) {
							msg.append("第" + (i + 1) + "行数据，课程编号不能为空！<br/>");
							continue;
						}
						if (eleListIsValid.size() != 0) {
							for (Object[] o : eleListIsValid) {
								if (o[0].equals(temp)) {
									msg.append("第" + (i + 1) + "行数据，课程未发布,或该课程是专项！<br/>");
								}
							}
						}

						electiveMap.put("peBzzStudent", peBzzStudent);

						String[] codeList = temp.split("\\|");

						temp1 = upper(sheet.getCell(0, i).getContents());
						if (temp1 == null || "".equals(temp1)) {
							msg.append("第" + (i + 1) + "行数据，姓名不能为空！<br/>");
						}
						StringBuffer courseStr = new StringBuffer();
						for (int j = 0; j < codeList.length; j++) {// 对课程编号进行循环start
							// 验证课程是否存在
							// 优化性能 dc改sql查询
							if (StringUtils.countMatches(temp, codeList[j]) > 1) {
								msg.append("第" + (i + 1) + "行数据，" + codeList[j] + "课程编号重复！<br/>");
								continue;
							}

							List<String> prBzzTchOpencourseList = courseInfoMap.get(codeList[j]);

							if (prBzzTchOpencourseList == null || prBzzTchOpencourseList.isEmpty()) {
								msg.append("第" + (i + 1) + "行数据，" + codeList[j] + "编号课程不存在或这门课程免费<br/>");
								continue;
							}
							// 判断课程是否已选
							boolean isBack = false;
							for (Object[] o : eleBackList) {
								if (o[0].equals(peBzzStudent.getId())) {
									if (o[1].equals(codeList[j])) {
										msg.append("第" + (i + 1) + "行数据，" + codeList[j] + "编号课程已存在选课记录！<br/>");
										isBack = true;
										continue;
									}
								}
							}
							/*
							if (!isBack) {
								for (Object[] o : eleList) {
									if (o[0].equals(peBzzStudent.getId())) {
										if (o[1].equals(codeList[j])) {
											msg.append("第" + (i + 1) + "行数据，" + codeList[j] + "编号课程已存在选课记录！<br/>");
											continue;
										}
									}
								}
							}
							*/

							// 判断姓名和身份证是否一致

							if (!(peStudentList.get(2)).equalsIgnoreCase(temp1)) {
								msg.append("第" + (i + 1) + "行数据，" + codeList[j] + "姓名和系统编号不相符！<br/>");
								continue;
							}
							PrBzzTchOpencourse tempOpenCourse = new PrBzzTchOpencourse();
							tempOpenCourse.setId(prBzzTchOpencourseList.get(0));
							PeBzzTchCourse tempCourse = new PeBzzTchCourse();
							tempCourse.setId(prBzzTchOpencourseList.get(1));
							tempCourse.setName(prBzzTchOpencourseList.get(2));
							tempCourse.setCode(prBzzTchOpencourseList.get(3));
							tempCourse.setTime(prBzzTchOpencourseList.get(4));
							tempCourse.setPrice(prBzzTchOpencourseList.get(5));
							tempOpenCourse.setPeBzzTchCourse(tempCourse);
							if (j == codeList.length - 1) {
								courseStr.append(tempCourse.getCode());
							} else {
								courseStr.append(tempCourse.getCode() + ",");
							}
							credit = credit.add(new BigDecimal(tempCourse.getTime())).setScale(1);
							amount = amount.add(new BigDecimal(tempCourse.getPrice()));

						}// 对课程编号进行循环end
						electiveMap.put("courseStr", courseStr);
						electiveMap.put("amount", amount);
						electiveMap.put("credit", credit);
						allAmount = allAmount.add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
						allCredit = allCredit.add(credit).setScale(1, BigDecimal.ROUND_HALF_UP);
						electiveMapList.add(electiveMap);
					} catch (Exception e) {
						e.printStackTrace();
						msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
						continue;
					}

				}// 每1000行处理一次end

			}// ===LOOP END

			if (msg.length() > 0) {
				msg.append("批量选课信息上传失败，请修改以上错误之后重新上传！<br/>");
				throw new EntityException(msg.toString());
			}

			ActionContext.getContext().getSession().put("electiveMapList", electiveMapList);
			ActionContext.getContext().getSession().put("allAmount", allAmount);
			ActionContext.getContext().getSession().put("allCredit", allCredit);
			// System.out.println(" ===========end========" +
			// System.currentTimeMillis());
			// Lee start 2014年1月23日
		}
		// Lee end
	}

	/**
	 * 获取当前用户账户金额
	 * 
	 * @author linjie
	 * @return
	 */
	public BigDecimal balance(SsoUser user) {
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("id", user.getId()));
		try {
			user = (SsoUser) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sumAmount = (user.getSumAmount() == null || user.getSumAmount().equals("")) ? "0" : user.getSumAmount();
		String amount = (user.getAmount() == null || user.getAmount().equals("")) ? "0" : user.getAmount();
		BigDecimal bdSumAmount = new BigDecimal(sumAmount);
		BigDecimal bdAmount = new BigDecimal(amount);
		return bdSumAmount.subtract(bdAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public String upper(String str) {
		if (str != null) {
			str = str.toUpperCase().trim();
		} else {
			str = "";
		}
		return str;
	}

	public List<Map> getElectiveMapList() {
		return electiveMapList;
	}

	public void setElectiveMapList(List<Map> electiveMapList) {
		this.electiveMapList = electiveMapList;
	}

	public List<PeBzzStudent> getPeBzzStudentList() {
		return peBzzStudentList;
	}

	public void setPeBzzStudentList(List<PeBzzStudent> peBzzStudentList) {
		this.peBzzStudentList = peBzzStudentList;
	}

	public List getCourseList() {
		return courseList;
	}

	public void setCourseList(List courseList) {
		this.courseList = courseList;
	}

	public BigDecimal getAllCredit() {
		return allCredit;
	}

	public void setAllCredit(BigDecimal allCredit) {
		this.allCredit = allCredit;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public PrBzzTchStuElective getPrBzzTchStuElective() {
		return prBzzTchStuElective;
	}

	public void setPrBzzTchStuElective(PrBzzTchStuElective prBzzTchStuElective) {
		this.prBzzTchStuElective = prBzzTchStuElective;
	}

	public List<PrBzzTchStuElective> getElectiveList() {
		return electiveList;
	}

	public void setElectiveList(List<PrBzzTchStuElective> electiveList) {
		this.electiveList = electiveList;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	public String getSso_user_amount() {
		return sso_user_amount;
	}

	public void setSso_user_amount(String sso_user_amount) {
		this.sso_user_amount = sso_user_amount;
	}

	public String getMerorderid() {
		return merorderid;
	}

	public void setMerorderid(String merorderid) {
		this.merorderid = merorderid;
	}

	public String getCompareResult() {
		return compareResult;
	}

	public void setCompareResult(String compareResult) {
		this.compareResult = compareResult;
	}
}
