//package com.whaty.platform.entity.web.action.basic;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.struts2.ServletActionContext;
//import org.hibernate.criterion.DetachedCriteria;
//import org.hibernate.criterion.Restrictions;
//
//import com.opensymphony.xwork2.ActionContext;
//import com.whaty.platform.entity.bean.EnumConst;
//import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
//import com.whaty.platform.entity.bean.PeBzzOrderInfo;
//import com.whaty.platform.entity.bean.PeElectiveCoursePeriod;
//import com.whaty.platform.entity.bean.PrBzzTchStuElective;
//import com.whaty.platform.entity.bean.SsoUser;
//import com.whaty.platform.entity.exception.EntityException;
//import com.whaty.platform.entity.service.EnumConstService;
//import com.whaty.platform.entity.service.basic.PeElectiveCoursePeriodService;
//import com.whaty.platform.entity.web.action.MyBaseAction;
//import com.whaty.platform.sso.web.action.SsoConstant;
//import com.whaty.platform.sso.web.servlet.UserSession;
//import com.whaty.platform.util.Const;
//import com.whaty.platform.util.JsonUtil;
//
///**
// * 选课期管理
// * 
// * @author 
// * 
// */
//public class PeElectiveCoursePeriodAction_old extends MyBaseAction<PeElectiveCoursePeriod> {
//	
//	private List<PrBzzTchStuElective> electiveList;
//	private String id;
//	private BigDecimal allTime = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
//	private BigDecimal allAmount = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
//	private int num = 0;
//	private String isInvoice;
//	private String payMethod;
//	private String sso_user_amount;
//	private PeBzzOrderInfo peBzzOrderInfo;
//	private PeBzzInvoiceInfo peBzzInvoiceInfo;
//	private EnumConstService enumConstService;
//	private String merorderid;      //由银行生成的单据号码
//	private String periodId;        //由系统生成的订单号
//	private PeElectiveCoursePeriodService peElectiveCoursePeriodService;
//	
//	private String regNo;
//	private String stuName;
//	private String courseCode;
//	private String courseName;
//
//	public String getRegNo() {
//		return regNo;
//	}
//
//
//	public void setRegNo(String regNo) {
//		this.regNo = regNo;
//	}
//
//
//	public String getStuName() {
//		return stuName;
//	}
//
//
//	public void setStuName(String stuName) {
//		this.stuName = stuName;
//	}
//
//
//	public String getCourseCode() {
//		return courseCode;
//	}
//
//
//	public void setCourseCode(String courseCode) {
//		this.courseCode = courseCode;
//	}
//
//
//	public String getCourseName() {
//		return courseName;
//	}
//
//
//	public void setCourseName(String courseName) {
//		this.courseName = courseName;
//	}
//
//
//	public PeElectiveCoursePeriodService getPeElectiveCoursePeriodService() {
//		return peElectiveCoursePeriodService;
//	}
//
//
//	public void setPeElectiveCoursePeriodService(
//			PeElectiveCoursePeriodService peElectiveCoursePeriodService) {
//		this.peElectiveCoursePeriodService = peElectiveCoursePeriodService;
//	}
//
//
//	public String getPeriodId() {
//		return periodId;
//	}
//
//
//	public void setPeriodId(String periodId) {
//		this.periodId = periodId;
//	}
//
//
//	public String getMerorderid() {
//		return merorderid;
//	}
//
//
//	public void setMerorderid(String merorderid) {
//		this.merorderid = merorderid;
//	}
//
//
//	@Override
//	public void initGrid() {
//		Date date=new Date();
//		String sysda=date.toString();
//		this.getGridConfig().setTitle(this.getText("选课期信息列表"));
//		this.getGridConfig().setCapability(true, true, true);
//		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("名称"), "name");
//		this.getGridConfig().addColumn(this.getText("开始时间"), "beginDate");
//		this.getGridConfig().addColumn(this.getText("结束时间"), "endDate");
//		this.getGridConfig().addColumn(this.getText("学时数"), "stuTime",true, true, true,Const.fee_for_extjs);
//		this.getGridConfig().addMenuFunction(this.getText("结束选课期"), "peElectiveCoursePeriod_endPeriod.action", false, true);
//		this.getGridConfig().addRenderScript(this.getText("选课期状态"),
//				"{ var date =new Date(); var dateString=(date.getYear()*10000)+(date.getMonth()+1)*100+(date.getDate());" +
//				" if(record.data['endDate'].substring(0,10).split('-').join('') <= dateString) return '<font color=red>已结束</font>';else return '<font color=black>未结束</font>'}", "id");
//		this.getGridConfig().addRenderFunction(this.getText("查看学员"), "<a href=\"/entity/basic/addStudentToPeriod.action?id=${value}&flag=stuDel\">查看学员</a>","id");
//	//	this.getGridConfig().addRenderFunction(this.getText("添加学员"), "<a href=\"/entity/basic/addStudentToPeriod.action?id=${value}&flag=stuAdd\">添加学员</a>","id");
//		
//		this.getGridConfig().addRenderScript(this.getText("添加学员"),
//				"{ var date =new Date(); var dateString=(date.getYear()*10000)+(date.getMonth()+1)*100+(date.getDate());" +
//				" if(record.data['endDate'].substring(0,10).split('-').join('') <= dateString) return '<font color=red>不在操作日期</font>';else return '<a href=\"/entity/basic/addStudentToPeriod.action?id='+record.data['id']+'&flag=stuAdd\">添加学员</a>'}", "id");
//		this.getGridConfig().addColumn(this.getText("是否已支付"), "enumConstByFlagElePeriodPayStatus.code",true,false,false,"TextField");
//		
//		
//		//this.getGridConfig().addColumn(this.getText("支付状态"), "enumConstByFlagElePeriodPayStatus.name",true,false,false,"TextField");
//		this.getGridConfig().addColumn(this.getText("支付状态"), "enumConstByFlagElePeriodPayStatus.code",false, false, false, "");
//		this.getGridConfig().addRenderScript(this.getText("支付状态"), "{if(record.data['enumConstByFlagElePeriodPayStatus.code'] != 1) return '<a href=\"/entity/basic/peElectiveCoursePeriod_toConfirmOrder.action?bean.id='+record.data['id']+'\", target=\"_blank\">去支付</a>';else return '已支付'}","id");
//		this.getGridConfig().addRenderFunction(this.getText("查看详情"), "<a href=\"/entity/basic/peElectiveCoursePeriod_viewDetail.action?bean.id=${value}\">查看选课详情</a>","id");
//	}
//	
//	
//	public String endPeriod(){
//		Map map = new HashMap();
//		if (this.getIds() != null && this.getIds().length() > 0) {
//			String[] ids = getIds().split(",");
//			try {
//				DetachedCriteria tempdc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);		
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				tempdc.add(Restrictions.in("id", ids));
//				List<PeElectiveCoursePeriod> sslist = new ArrayList<PeElectiveCoursePeriod>();
//				sslist =this.getGeneralService().getList(tempdc);
//				Iterator<PeElectiveCoursePeriod> iterator = sslist.iterator();
//				while(iterator.hasNext()){
//					PeElectiveCoursePeriod peElectiveCoursePeriod = iterator.next();
//					peElectiveCoursePeriod.setEndDate(df.parse(df.format(new Date())));
//					this.getGeneralService().save(peElectiveCoursePeriod);
//				}
//			} catch (Exception e) {
//				map.clear();
//				map.put("success", "false");
//				map.put("info", "操作失败");
//				this.setJsonString(JsonUtil.toJSONString(map));
//				JsonUtil.setDateformat("yyyy-MM-dd");
//				return json();
//			}
//			map.put("success", "true");
//			map.put("info", " 结束选课期操作成功！");
//		}else{
//			map.clear();
//			map.put("success", "false");
//			map.put("info", "至少一条数据被选择");
//			this.setJsonString(JsonUtil.toJSONString(map));
//			JsonUtil.setDateformat("yyyy-MM-dd");
//			return json();
//		}
//		this.setJsonString(JsonUtil.toJSONString(map));
//		JsonUtil.setDateformat("yyyy-MM-dd");
//		return json();
//	}
//	
//	
//	@Override
//	public DetachedCriteria initDetachedCriteria() {
//		// TODO Auto-generated method stub
//		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		DetachedCriteria dc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
//		dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
//		dc.createCriteria("enumConstByFlagElePeriodPayStatus", "enumConstByFlagElePeriodPayStatus",DetachedCriteria.LEFT_JOIN);
//		try{
//			//处理时间的查询参数
//			ActionContext context = ActionContext.getContext();
//			if(context.getParameters()!=null){
//				Map params = this.getParamsMap();
//				if(params != null){
//					if(params.get("search__beginDate") != null ){
//						String[] beginDate = (String[])params.get("search__beginDate");
//						if(beginDate.length == 1 && !StringUtils.defaultString(beginDate[0]).equals("")){
//							beginDate[0] = ">=" + beginDate[0];
//							params.put("search__beginDate", beginDate);
//						}
//					}
//					if(params.get("search__endDate") != null){
//						String[] endDate = (String[])params.get("search__endDate");
//						if(endDate.length == 1 && !StringUtils.defaultString(endDate[0]).equals("")){
//							endDate[0] = "<= " + endDate[0];
//							params.put("search__endDate", endDate);
//						}
//					}
//					context.setParameters(params);
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return dc;
//	}
//	
//	
//	public void checkBeforeAdd() throws EntityException {
//		if (this.getBean().getBeginDate().after(this.getBean().getEndDate()) || this.getBean().getBeginDate().equals(this.getBean().getEndDate())) {
//			throw new EntityException("结束时间应晚于开始时间");
//		}
//		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		this.getBean().setSsoUser(us.getSsoUser());
//		String sql=" select ec.id from enum_const ec where ec.namespace='FlagElePeriodPayStatus' and ec.code='0'";
//		List list=		this.getGeneralService().getBySQL(sql);
//			
//		
//		if(list!=null&&list.size()>0){
//			EnumConst ec =new EnumConst();
//			ec.setId((String)list.get(0));
//				this.getBean().setEnumConstByFlagElePeriodPayStatus(ec);
//			}
//		//this.getBean().setEnumConstByFlagElePeriodPayStatus(enumConstByFlagElePeriodPayStatus);
//	
//	}
//	
//	public void checkBeforeUpdate() throws EntityException {
//		if (this.getBean().getBeginDate().after(this.getBean().getEndDate()) || this.getBean().getBeginDate().equals(this.getBean().getEndDate())) {
//			throw new EntityException("结束时间应晚于开始时间");
//		}
//	}
//	
//	public String toConfirmOrder(){
//
//		StringBuffer  sqlBuffer = new StringBuffer();
//		sqlBuffer.append("SELECT to_number(ec.name)*nvl(Sum(pbtc.time),0) AS allAmount, ");
//		sqlBuffer.append("       nvl(Sum(pbtc.time),0)  AS allTime, ");
//		sqlBuffer.append("		 ec.name ");
//		sqlBuffer.append("FROM   pr_bzz_tch_stu_elective pbtse ");
//		sqlBuffer.append("       INNER JOIN pr_bzz_tch_opencourse pbto ");
//		sqlBuffer.append("         ON pbtse.fk_tch_opencourse_id = pbto.id ");
//		sqlBuffer.append("       INNER JOIN pe_bzz_tch_course pbtc ");
//		sqlBuffer.append("         ON pbto.fk_course_id = pbtc.id ");
//		sqlBuffer.append("       INNER JOIN pe_elective_course_period pecp ");
//		sqlBuffer.append("         ON pbtse.fk_ele_course_period_id = pecp.id ");
//		sqlBuffer.append("       LEFT JOIN enum_const ec  ");
//		sqlBuffer.append("         ON ec.namespace='ClassHourRate' and ec.code='0' ");
//		sqlBuffer.append("WHERE  pecp.id = '"+this.getBean().getId()+"' ");
//		sqlBuffer.append("group by ec.name");
//		
//		try {
//			List<Object[]> tempList = this.getGeneralService().getBySQL(sqlBuffer.toString());
//			this.setBean(this.getGeneralService().getById(this.getBean().getId()));
//			if (this.getBean().getEndDate().after(new Date())) {
//				this.setMsg("选课期尚未结束，无法支付");
//				this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//				return "paymentMsg";
//			}
//			if(tempList.size()==0) {
//				this.setMsg("该选课期内没有选课信息，无法支付");
//				this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//				return "paymentMsg";
//			}
//			if (tempList != null && tempList.size() > 0) {
//				this.allAmount = new BigDecimal((tempList.get(0)[0]).toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
//				this.allTime = new BigDecimal((tempList.get(0)[1]).toString()).setScale(1, BigDecimal.ROUND_HALF_UP);
//				if(this.allAmount.compareTo(BigDecimal.ZERO)==0 && this.allTime.compareTo(BigDecimal.ZERO) == 0){
//					this.setMsg("该选课期内没有选课信息，无法支付");
//					this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//					return "paymentMsg";
//				}
//				ServletActionContext.getRequest().getSession().setAttribute("allAmount", this.allAmount);
//				ServletActionContext.getRequest().getSession().setAttribute("allTime", this.allTime);
//				ServletActionContext.getRequest().getSession().setAttribute("eleCouPeriodId", this.getBean().getId());
//			}
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//		return "confirmOrderInfo";
//	}
//	
//	public String toConfirmPaymentMethod(){
//		try {
//			this.peBzzOrderInfo.setSeq(this.getGeneralService().getOrderSeq());
//			this.peBzzOrderInfo.setAmount(ActionContext.getContext().getSession().get("allAmount").toString());
//			this.peBzzOrderInfo.setClassHour(ActionContext.getContext().getSession().get("allTime").toString());
//			if ("y".equalsIgnoreCase(this.isInvoice)) {
//				this.peBzzInvoiceInfo.setPeBzzOrderInfo(this.peBzzOrderInfo);
//				ActionContext.getContext().getSession().put("peBzzInvoiceInfo", peBzzInvoiceInfo);
//			}else{
//				ActionContext.getContext().getSession().put("peBzzOrderInfo", peBzzOrderInfo);
//			}
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//		return "confirmPaymentMethod";
//	}
//	
//	public String toPayment(){
//		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		this.initOrderAndElective();
//		DetachedCriteria dcSsoUser = DetachedCriteria.forClass(SsoUser.class);
//		dcSsoUser.add(Restrictions.eq("id", us.getSsoUser().getId()));
//		SsoUser user = new SsoUser();
//		//获取托管状态的ssoUser实例
//		try {
//			user = (SsoUser) this.getGeneralService().getDetachList(dcSsoUser).get(0);
//		} catch (EntityException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String sumAmount = (user.getSumAmount()==null || user.getSumAmount().equals("")) ? "0" : user.getSumAmount();
//		String amount = (user.getAmount()==null || user.getSumAmount().equals("")) ? "0" : user.getAmount();
//		if("0".equalsIgnoreCase(this.payMethod)){//第三方支付
//			ServletActionContext.getRequest().getSession().setAttribute("electiveList", this.electiveList);
//			ServletActionContext.getRequest().getSession().setAttribute(this.peBzzOrderInfo.getSeq(), "elePeriodPayment");
//			this.peBzzOrderInfo.setSsoUser(user);
////			this.generateOrder();    //用于处理第三方支付
//			try {
//				/**
//				 * 选课期为支付状态为未支付
//				 */
//				EnumConst enumConstByFlagElePeriodPayStatus = this.enumConstService.getByNamespaceCode("FlagElePeriodPayStatus", "0");
//				/**
//				 * 将选课期的支付状态改为未支付
//				 */
//				this.getBean().setEnumConstByFlagElePeriodPayStatus(enumConstByFlagElePeriodPayStatus);
//				this.peElectiveCoursePeriodService.updateElectiveList(electiveList, peBzzOrderInfo, peBzzInvoiceInfo, this.getBean(), null);
//			} catch (EntityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return "onlinePayment";
//		}else {
//			this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//			if("1".equalsIgnoreCase(this.payMethod)){//预付费支付
//			EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
//			
//			BigDecimal balance = new BigDecimal(sumAmount).subtract(new BigDecimal(amount)).multiply(new BigDecimal(ec.getName())).setScale(2, BigDecimal.ROUND_HALF_UP);
//			
//			//if(Double.parseDouble(user.getAmount())*Double.valueOf(ec.getName()) < this.allAmount){
//			if(balance.compareTo(this.allAmount)==-1){
//				this.setMsg("当前账户余额不足");
//				this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//				return "paymentMsg";
//			}else{
//				this.sso_user_amount = amount;
//				user.setAmount(new BigDecimal(amount).add(this.allAmount.divide(new BigDecimal(ec.getName()))).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
////				us.getSsoUser().setAmount(user.getAmount());
//				this.peBzzOrderInfo.setPaymentDate(new Date());
//				this.peBzzOrderInfo.setSsoUser(user);
//				try {
//					this.electiveList = this.peElectiveCoursePeriodService.updateElectiveList(this.electiveList, peBzzOrderInfo, peBzzInvoiceInfo ,this.getBean(), user);
//				} catch (EntityException e) {
//					// TODO Auto-generated catch block
//					user.setAmount(this.sso_user_amount);
//					e.printStackTrace();
//					this.setMsg("支付失败");
//					//this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//					return "paymentMsg";
//				}
//				this.setMsg("成功支付"+electiveList.size()+"条选课");
//			}
//		}else{//支票和电汇支付
//			try {
//				this.peBzzOrderInfo.setSsoUser(us.getSsoUser());
//				this.electiveList = this.peElectiveCoursePeriodService.updateElectiveList(this.electiveList, peBzzOrderInfo, peBzzInvoiceInfo ,this.getBean(), null);
//			} catch (EntityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				this.setMsg("支付订单提交失败");
//				//this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//				return "paymentMsg";
//			}
//			this.setMsg(electiveList.size()+"条选课支付提交成功，请尽快付款");
//		}
//		}
//		this.destroyOrderSession();
//		//this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//		return "paymentMsg";
//	}
//	/**
//	 * 第三方支付时首先生成订单，选课信息保存在选课临时表中
//	 */
//	public void generateOrder() {
//		if(ActionContext.getContext().getSession().get("peBzzInvoiceInfo") != null){
//			this.peBzzInvoiceInfo =  (PeBzzInvoiceInfo) ActionContext.getContext().getSession().get("peBzzInvoiceInfo");
//			this.peBzzOrderInfo = this.peBzzInvoiceInfo.getPeBzzOrderInfo();
//		}else {
//			this.peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext().getSession().get("peBzzOrderInfo");
//		}
//		if (ActionContext.getContext().getSession().get("electiveList") != null) {
//			this.electiveList = (List<PrBzzTchStuElective>) ActionContext.getContext().getSession().get("electiveList");
//		}
//		
//		try {
//			
//			this.electiveList = this.getGeneralService().saveElectiveListBack(this.electiveList, peBzzOrderInfo, peBzzInvoiceInfo, null ,this.getBean());
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 第三方支付确认
//	 * @return
//	 */
//	public String confirmOnlinePayment(){
//		
//		/**
//		 * 订单状态,已支付
//		 */
//		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "1");//已支付
//		
//		/**
//		 * 根据银行支付成功后返回的订单号来查找订单
//		 */
//		DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
//		orderDc.createCriteria("ssoUser", "ssoUser");
//		orderDc.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagPaymentState");
//		orderDc.add(Restrictions.eq("seq", this.merorderid));
//		PeBzzOrderInfo order = null;
//		try {
//			order = (PeBzzOrderInfo)this.getGeneralService().getList(orderDc).get(0);
//		} catch (EntityException e2) {
//			e2.printStackTrace();
//		}
//		order.setPaymentDate(new Date());
//		order.setNum(order.getSeq());
//		order.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
//		/**
//		 * 根据订单来查找选课信息
//		 */
//		DetachedCriteria electiveDc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
//		electiveDc.createCriteria("peElectiveCoursePeriod", "peElectiveCoursePeriod");
//		electiveDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
//		electiveDc.createCriteria("peElectiveCoursePeriod.enumConstByFlagElePeriodPayStatus", "enumConstByFlagElePeriodPayStatus");
//		electiveDc.add(Restrictions.eq("peBzzOrderInfo", order));
//		try {
//			this.electiveList = this.getGeneralService().getList(electiveDc);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		/**
//		 * 获取选课期信息
//		 */
//		PeElectiveCoursePeriod peElectiveCoursePeriod = this.electiveList.get(0).getPeElectiveCoursePeriod();
//		/**
//		 * 选课期状态，改为已支付
//		 */
//		EnumConst enumConstByFlagElePeriodPayStatus = this.enumConstService.getByNamespaceCode("FlagElePeriodPayStatus", "1");
//		peElectiveCoursePeriod.setEnumConstByFlagElePeriodPayStatus(enumConstByFlagElePeriodPayStatus);
//		/**
//		 * 选课信息更新，已支付
//		 */
//		EnumConst enumConstByFlagElectivePayStatus = this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus", "1");//已支付状态
//		/**
//		 * 更新选课表状态
//		 */
//		for(PrBzzTchStuElective ele : this.electiveList) {
//			ele.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
//		}
//		
//		/**
//		 * 调用service方法，保存更新信息
//		 */
//		try {
//			this.electiveList = this.peElectiveCoursePeriodService.updateElectiveList(this.electiveList, peBzzOrderInfo, peBzzInvoiceInfo, peElectiveCoursePeriod, null);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		/**
//		 * 根据查询的订单来查询备份的选课信息
//		 */
//		/*DetachedCriteria eleBackDc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
//		eleBackDc.createCriteria("ssoUser", "ssoUser");
//		eleBackDc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
//		eleBackDc.createCriteria("peBzzStudent", "peBzzStudent");
//		eleBackDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
//		eleBackDc.add(Restrictions.eq("peBzzOrderInfo", order));
//		List<PrBzzTchStuElectiveBack> eleBackList = null;
//		try {
//			eleBackList = this.getGeneralService().getList(eleBackDc);
//		} catch (EntityException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}*/
//		
//		
//		/**
//		 * 恢复选课信息
//		 */
//		/*List eleList = new ArrayList();
//		for(PrBzzTchStuElectiveBack back : eleBackList) {
//			*//**
//			 * 将备份表中的数据导入到选课正式表中
//			 *//*
//			PrBzzTchStuElective ele = new PrBzzTchStuElective();
//			ele.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
//			ele.setElectiveDate(new Date());
//			ele.setPeBzzOrderInfo(order);
//			ele.setPeBzzStudent(back.getPeBzzStudent());
//			ele.setPrBzzTchOpencourse(back.getPrBzzTchOpencourse());
//			ele.setSsoUser(back.getSsoUser());
//			*//**
//			 * 课程状态记录
//			 *//*
//			TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
//			trainingCourseStudent.setPrBzzTchOpencourse(back.getPrBzzTchOpencourse());
//			trainingCourseStudent.setSsoUser(ele.getPeBzzStudent().getSsoUser());
//			trainingCourseStudent.setPercent(0.00);
//			trainingCourseStudent.setLearnStatus(StudyProgress.INCOMPLETE);
//			ele.setTrainingCourseStudent(trainingCourseStudent);
//			eleList.add(ele);
//		}
//		try {
//			this.getGeneralService().saveElectiveList(eleList, order, null, order.getSsoUser(), this.getBean());
//			//this.studentWorkspaceService.saveOrderInvoiceEletive(order, null, eleList, stuBatchList);
//		} catch (EntityException e) {
//			e.printStackTrace();
//			this.setMsg("支付失败");
//			return "msg";
//		}*/
//		//this.destoryElectiveOrder();
//		/*this.setMsg("支付成功");
//		return "success";*/
//		/*for(PrBzzTchStuElective p : this.electiveList) {
//			p.setPeBzzOrderInfo(peBzzOrderInfo);
//			p.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
//		}*/
//			/*try {
//				this.electiveList = this.getGeneralService().saveElectiveList(this.electiveList, peBzzOrderInfo, peBzzInvoiceInfo, null ,this.getBean());
//			} catch (EntityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				this.setMsg("支付失败");
//				return "msg";
//			}*/
//		//this.getGeneralService().saveElectiveList(eleList, order, null, order.getSsoUser(), this.getBean());
//			this.setMsg("成功支付"+this.electiveList.size()+"条选课");
//			//this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//			this.destroyOrderSession();
//			return "paymentMsg";
//		
//	}
//	/**
//	 * 初始化要保存的订单和选课信息
//	 */
//	public void initOrderAndElective() {
//		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		//是否申请发票
//		if( ActionContext.getContext().getSession().get("peBzzInvoiceInfo") != null){
//			this.peBzzInvoiceInfo =  (PeBzzInvoiceInfo) ActionContext.getContext().getSession().get("peBzzInvoiceInfo");
//			this.peBzzOrderInfo = this.peBzzInvoiceInfo.getPeBzzOrderInfo();
//		}else {
//			this.peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext().getSession().get("peBzzOrderInfo");
//		}
//		//支付方式
//		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", this.payMethod);
//		EnumConst enumConstByFlagPaymentType = this.getEnumConstService().getByNamespaceCode("FlagPaymentType", "1");//集体支付
//		EnumConst enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "0");//订单类型，选课订单
//		EnumConst enumConstByFlagOrderState = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "1");//订单状态，正常
//		EnumConst enumConstByFlagElePeriodPayStatus = this.getEnumConstService().getByNamespaceCode("FlagElePeriodPayStatus", "1");//已支付状态
//		
//		/**
//		 * 订单有效状态，初始为有效
//		 */
//		EnumConst enumConstByFlagOrderIsValid = this.enumConstService.getByNamespaceCode("FlagOrderIsValid", "1");   //订单为有效状态
//		this.peBzzOrderInfo.setEnumConstByFlagOrderIsValid(enumConstByFlagOrderIsValid);  //设为有效，初始化订单
//		EnumConst enumConstByFlagPaymentState;
//		EnumConst enumConstByFlagElectivePayStatus;
//		//如果预付费支付，支付状态为“已支付”
//		if(("1".equalsIgnoreCase(this.payMethod))){
//			enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "1");//已支付
//			enumConstByFlagElectivePayStatus = this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus", "1");//已支付状态
//		}else {
//			enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");//未支付
//			enumConstByFlagElectivePayStatus = this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus", "0");//已支付状态
//		}
//		this.peBzzOrderInfo.setEnumConstByFlagPaymentMethod(enumConstByFlagPaymentMethod);
//		this.peBzzOrderInfo.setEnumConstByFlagPaymentType(enumConstByFlagPaymentType);
//		this.peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
//		this.peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType);
//		this.peBzzOrderInfo.setEnumConstByFlagOrderState(enumConstByFlagOrderState);
//		this.peBzzOrderInfo.setCreateDate(new Date());
//		try {
//			this.peBzzOrderInfo.setPayer(this.getGeneralService().getEnterpriseManagerByLoginId(us.getSsoUser().getLoginId()).getName());
//		} catch (EntityException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
////		this.allAmount = Double.parseDouble(this.peBzzOrderInfo.getAmount());
//		this.allAmount = new BigDecimal(this.peBzzOrderInfo.getAmount()).setScale(2);
//		String eleCouPeriodId = (String) ServletActionContext.getRequest().getSession().getAttribute("eleCouPeriodId");
//		DetachedCriteria dcElective = DetachedCriteria.forClass(PrBzzTchStuElective.class);
//		dcElective.createCriteria("trainingCourseStudent");
//		dcElective.createCriteria("prBzzTchOpencourse");
//		dcElective.createCriteria("peBzzStudent");
//		dcElective.add(Restrictions.eq("peElectiveCoursePeriod.id", eleCouPeriodId));
//		
//		DetachedCriteria dcElectivePeriod = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
//		dcElectivePeriod.add(Restrictions.eq("id", eleCouPeriodId));
//		try {
//			PeElectiveCoursePeriod peElectiveCoursePeriod = (PeElectiveCoursePeriod)this.getGeneralService().getDetachList(dcElectivePeriod).get(0);
//			peElectiveCoursePeriod.setEnumConstByFlagElePeriodPayStatus(enumConstByFlagElePeriodPayStatus);
//			this.setBean(peElectiveCoursePeriod);
//			this.electiveList = this.getGeneralService().getDetachList(dcElective);
//			for (int i = 0; i < electiveList.size(); i++) {
//				electiveList.get(i).setPeBzzOrderInfo(this.peBzzOrderInfo);
//				electiveList.get(i).setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
//			}
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 清除session中订单信息
//	 */
//	public void destroyOrderSession(){
//		if(ServletActionContext.getRequest().getSession().getAttribute("peBzzInvoiceInfo")!=null){
//			ServletActionContext.getRequest().getSession().removeAttribute("peBzzInvoiceInfo");
//		}
//		if(ServletActionContext.getRequest().getSession().getAttribute("peBzzOrderInfo")!=null){
//			ServletActionContext.getRequest().getSession().removeAttribute("peBzzOrderInfo");
//		}
//		if(ServletActionContext.getRequest().getSession().getAttribute("electiveList")!=null){
//			ServletActionContext.getRequest().getSession().removeAttribute("electiveList");
//		}
//	}
//	public void checkBeforeDelete(List idList) throws EntityException {
//		
//		DetachedCriteria dc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
//		dc.add(Restrictions.in("id", idList.toArray()));
//		List list = this.getGeneralService().getList(dc);
//		Iterator it = list.iterator();
//		while(it.hasNext()) {
//			PeElectiveCoursePeriod pc = (PeElectiveCoursePeriod)it.next();
//			if(pc.getPeriodStudent().size()!=0) {
//				throw new EntityException("您要删除的选课期内存在学员，不能进行删除");
//			}
//		}
//
//	}
//	/**
//	 * 查看选课期选课详情
//	 * @return
//	 */
//	public String viewDetail(){
//		DetachedCriteria dcElective = DetachedCriteria.forClass(PrBzzTchStuElective.class);
//		dcElective.createCriteria("prBzzTchOpencourse","prBzzTchOpencourse").createCriteria("peBzzTchCourse", "peBzzTchCourse", DetachedCriteria.LEFT_JOIN);
//		dcElective.createCriteria("peBzzStudent", "peBzzStudent");
//		dcElective.createCriteria("peElectiveCoursePeriod","peElectiveCoursePeriod").createAlias("peElectiveCoursePeriod.enumConstByFlagElePeriodPayStatus", "enumConstByFlagElePeriodPayStatus",DetachedCriteria.LEFT_JOIN);
//		dcElective.add(Restrictions.eq("peElectiveCoursePeriod.id", this.getBean().getId()));
//		if(this.getRegNo()!=null&&!"".equals(this.getRegNo())){
//			dcElective.add(Restrictions.eq("peBzzStudent.regNo", StringUtils.trim(this.getRegNo())));
//		}
//		if(this.getStuName()!=null&&!"".equals(this.getStuName())){
//			dcElective.add(Restrictions.eq("peBzzStudent.trueName", StringUtils.trim(this.getStuName())));
//		}
//		if(this.getCourseCode()!=null&&!"".equals(this.getCourseCode())){
//			dcElective.add(Restrictions.eq("peBzzTchCourse.code", StringUtils.trim(this.getCourseCode())));
//		}
//		if(this.getCourseName()!=null&&!"".equals(this.getCourseName())){
//			dcElective.add(Restrictions.eq("peBzzTchCourse.name", StringUtils.trim(this.getCourseName())));
//		}
//		try {
//			this.electiveList = this.getGeneralService().getList(dcElective);
//			if(this.electiveList != null){
//				List tempList = new ArrayList();
//				for (int i = 0; i < this.electiveList.size(); i++) {
//					if(!tempList.contains(electiveList.get(i).getPeBzzStudent().getId())){
//						tempList.add(electiveList.get(i).getPeBzzStudent().getId());
//					}
//					EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
//					this.allAmount = this.allAmount.add(new BigDecimal(electiveList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()).multiply(new BigDecimal(ec.getName())));
////					this.allAmount += Double.valueOf(electiveList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime())*Double.valueOf(ec.getName());
//					this.allTime = this.allTime.add(new BigDecimal(electiveList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()));
////					this.allTime += Double.valueOf(electiveList.get(i).getPrBzzTchOpencourse().getPeBzzTchCourse().getTime());
//				}
//				this.num = tempList.size();
//			}
//			
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.setMsg("++++++++++++++++++++asdfasdfasdfasdf++++++++++++++++++++++");
//		this.allAmount = this.allAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//		this.allTime = this.allTime.setScale(1, BigDecimal.ROUND_HALF_UP);
//		return "electiveCoursePeriodDetail";
//	}
//	
//	
//	
//	public String delElective(){
//		String trainingId = "";
//		String eleSql = "select ele.FK_TRAINING_ID from pr_bzz_tch_stu_elective ele where ele.id = '"+this.id+"'";
//		List eleList = null;
//		try {
//			eleList = this.getGeneralService().getBySQL(eleSql);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(eleList.size()>0) {
//			trainingId = eleList.get(0).toString();
//			String eleDel = "delete from pr_bzz_tch_stu_elective ele where ele.id = '"+this.id+"'";
//			try {
//				this.getGeneralService().executeBySQL(eleDel);
//			} catch (EntityException e) {
//				e.printStackTrace();
//				// TODO Auto-generated catch block
//				this.setMsg("删除失败");
//				this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//				return "msg";
//			}
//			
//			String trainingDel = "delete from training_course_student tcs where tcs.id = '"+trainingId+"'";
//			try {
//				this.getGeneralService().executeBySQL(trainingDel);
//			} catch (EntityException e) {
//				// TODO Auto-generated catch block
//				this.setMsg("删除失败");
//				this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
//				return "msg";
//			}
//		}
////		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
////		dc.createAlias("trainingCourseStudent", "trainingCourseStudent");
////		dc.add(Restrictions.eq("id", this.getId()));
////		try {
////			List<PrBzzTchStuElective> eleList = this.getGeneralService().getList(dc);
////			if (eleList != null && eleList.size() > 0) {
////				 this.getGeneralService().deleteElective(eleList.get(0));
////			}
////		} catch (EntityException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////			this.setMsg("删除失败");
////			this.setTogo("/entity/basic/peElectiveCoursePeriod.action");
////			return "paymentMsg";
////		}
//		return this.viewDetail();
//	}
//	
//	@Override
//	public void setEntityClass() {
//		this.entityClass = PeElectiveCoursePeriod.class;
//	}
//
//	@Override
//	public void setServletPath() {
//		this.servletPath = "/entity/basic/peElectiveCoursePeriod";
//	}
//
//	public void setBean(PeElectiveCoursePeriod instance) {
//		super.superSetBean(instance);
//		
//	}
//	
//	public PeElectiveCoursePeriod getBean(){
//		return (PeElectiveCoursePeriod) super.superGetBean();
//	}
//
//	public List<PrBzzTchStuElective> getElectiveList() {
//		return electiveList;
//	}
//
//	public void setElectiveList(List<PrBzzTchStuElective> electiveList) {
//		this.electiveList = electiveList;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public int getNum() {
//		return num;
//	}
//
//	public void setNum(int num) {
//		this.num = num;
//	}
//
//	public BigDecimal getAllTime() {
//		return allTime;
//	}
//
//
//	public void setAllTime(BigDecimal allTime) {
//		this.allTime = allTime;
//	}
//
//
//	public BigDecimal getAllAmount() {
//		return allAmount;
//	}
//
//
//	public void setAllAmount(BigDecimal allAmount) {
//		this.allAmount = allAmount;
//	}
//
//
//	public PeBzzOrderInfo getPeBzzOrderInfo() {
//		return peBzzOrderInfo;
//	}
//
//	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
//		this.peBzzOrderInfo = peBzzOrderInfo;
//	}
//
//	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
//		return peBzzInvoiceInfo;
//	}
//
//	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
//		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
//	}
//
//	public String getIsInvoice() {
//		return isInvoice;
//	}
//
//	public void setIsInvoice(String isInvoice) {
//		this.isInvoice = isInvoice;
//	}
//
//	public String getPayMethod() {
//		return payMethod;
//	}
//
//	public void setPayMethod(String payMethod) {
//		this.payMethod = payMethod;
//	}
//
//	public String getSso_user_amount() {
//		return sso_user_amount;
//	}
//
//	public void setSso_user_amount(String sso_user_amount) {
//		this.sso_user_amount = sso_user_amount;
//	}
//
//	public EnumConstService getEnumConstService() {
//		return enumConstService;
//	}
//
//	public void setEnumConstService(EnumConstService enumConstService) {
//		this.enumConstService = enumConstService;
//	}
//
//	public BigDecimal balance(SsoUser user) {
//		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
//		dc.add(Restrictions.eq("id", user.getId()));
//		try {
//			user = (SsoUser)this.getGeneralService().getList(dc).get(0);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		String sumAmount = (user.getSumAmount()==null || user.getSumAmount().equals("")) ? "0" : user.getSumAmount();
//		String amount = (user.getAmount()==null || user.getAmount().equals("")) ? "0" : user.getAmount();
//		BigDecimal bdSumAmount = new BigDecimal(sumAmount);
//		BigDecimal bdAmount = new BigDecimal(amount);
//		return bdSumAmount.subtract(bdAmount).setScale(1, BigDecimal.ROUND_HALF_UP);
//	}
//	
//	public boolean isEnough(SsoUser user, BigDecimal allAmount) {
//		EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
//		BigDecimal price = new BigDecimal(ec.getName());
//		String sumAmount = (user.getSumAmount()==null || user.getSumAmount().equals("")) ? "0" : user.getSumAmount();
//		String amount = (user.getAmount()==null || user.getAmount().equals("")) ? "0" : user.getAmount();
//		BigDecimal bdSumAmount = new BigDecimal(sumAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
//		BigDecimal bdAmount = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
//		BigDecimal subAmount = bdSumAmount.subtract(bdAmount).multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP);
//		int result = subAmount.compareTo(allAmount);
//		if(result>=0) {
//			return false;
//		} else {
//			return true;
//		}
//		
//	}
//}
