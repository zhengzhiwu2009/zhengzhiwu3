package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.HttpClient;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PayGateway99BillConfig;
import com.whaty.platform.config.PayGatewayConfig;
import com.whaty.platform.entity.bean.AutoRefund;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.OnlineRefundInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Payment99BillUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;
import com.whaty.util.Crypto;
import com.whaty.util.Pkipair;

public class OnlinePaymentAction extends MyBaseAction {

	HttpClient httpClient = new HttpClient();

	/** 快钱相关属性* */
	// 字符集 固定值：1 1代表UTF-8
	private String inputCharset;
	// 网关页面显示诧言种类：固定值：1 1代表中文显示
	private String language;
	// 签名类型 固定值：1 1代表MD5加密签名方式
	private String signType;
	// 组合字符串
	private String signMsg;
	// 人民币账号
	// 本参数用来指定接收款项的快钱用户的人民币账号
	private String merchantAcctId;
	// 商户订单号 只允许使用字母、数字、- 、_,并以字母或数字开头,需要查询的某比订单
	private String orderId;
	// 商户订单金额：整型数字 以分为单位。比方10元，提交时金额庒为1000,商户页面显示金额可以转换成以元为单位显示
	private String orderAmount;
	// 商户订单提交时间：数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
	// 例如：20071117020101
	private String orderTime;
	// 00代表显示快钱各支付方式列表
	private String payType;
	/** 非必填参数* */
	// 接叐支付结果的页面地址
	private String pageUrl;
	// 服务器接叐支付结果的后台地址
	private String bgUrl;
	// 支付人姓名
	private String payerName;
	// 支付人联系方式类型：固定值：1或者2 1代表申子邮件方式；2代表手机联系方式
	private String payerContactType;
	// 支付人联系方式
	private String payerContact;
	// 商品名称
	private String productName;
	// 商品数量
	private String productNum;
	// 商品代码
	private String productId;
	// 商品描述
	private String productDesc;
	// 扩展字段1
	private String ext1;
	// 扩展字段2
	private String ext2;
	// 银行代码
	private String bankId;
	// 同一订单禁止重复提交标志：固定选择值： 1、0
	// 1代表同一订单号只允许提交1次；0表示同一订单号在没有支付成功的前提下可重复提交多次。 默讣为0
	// 建议实物购物车结算类商户采用0；虚拟产品类商户采用1；
	private String redoFlag;
	// 合作伙伴在快钱的用户编号：数字串 用户登彔快钱首页后可查询到。仅适用亍快钱合作伙伴中系统及平台提供商
	private String pid;
	/** ************** */

	// 商户编号(必填): 签约后由支付平台给商户分配,最大长度20
	private String merchantid;

	// 订单编号(必填): 商户的交易定单号,由商户网站生成,最大长度30
	private String merorderid;

	// 金额(必填): 缴费金额,金额单位--元,保留两位小数
	private String amountsum;

	// 商品种类(可选): 需在支付平台进行设置,规则由商户制定;如果不填,则为默认,最大长度30
	private String subject;

	// 币种(必填): 人民币,编码--01
	private String currencytype;

	// 自动调转取货页面(必填): 字符串，最大长度2位,银行付款成功后是否自动跳转到取货页面：0→不跳转；1→跳转；
	private String autojump;

	// 跳转等待时间(必填): 字符串，最大长度2位;跳转到取货页面的等待时间，以秒为单位，
	private String waittime;

	// 商户取货URL(必填):
	// 字符串，最大长度255位,需要自动跳转时必填，支付成功后，客户URL浏览器自动跳转到该URL，如果autojump为空或者0，merurl不为空，则允许用//户手动跳转到取货页面
	private String merurl;

	// 通知商户(必填): 字符串，最大长度2位,将订单的状态通知给商户的URL：0→不通知；1→通知
	private String informmer;

	// 商户通知URL(必填):字符串，最大长度255位,将订单的状态通知给商户的URL
	private String informurl;

	// 商户返回确认(必填): 字符串，最大长度2位,商户是否响应平台的确认信息：0→不返回；1→返回；为1时，必须返回success=true字符串
	private String confirm;

	// 支付银行编码
	private String merbank;

	// 支付类型,交易类型： 0→即时到账；1→担保交易
	private String tradetype;

	// 是否选择银行 0 不选择银行
	private String bankInput;

	// 接口版本(必填):字符串，最大长度5,精确位2.判断接口版本,新用户接口为2.00
	private String strInterface;

	// 备注(可选): 支付备注信息,最大长度50
	private String remark;

	// 支付密钥(必填): 需在支付平台进行设置,可登录商户管理系统进行维护,用于上送商户支付及下传支付结果加密
	// private String merkey;

	// 支付请求URL(必填): 不可修改,真实地址http://www.umbpay.cn需跟支付平台技术人员联系确认
	// String url = "https://www.umbpay.com/pay2_1_/paymentImplAction.do";
	private String url;
	// 拼接加密的源字符串
	private String mac_src;
	// 调用签名函数生成签名串
	private String mac;

	private String version;// 退费接口版本

	private String appuser;// 退费申请人

	private String cause;// 退费原因

	private String state;// 支付状态
	private String paybank;// 支付银行
	private String banksendtime;// 发送到银行时间
	private String merrecvtime;// 返回到商户时间
	private String mac_rec;// key加密串

	private String refundMeg; // 退款申请消息

	private String bankcardtype;// 支付银行卡类型 00借贷卡混合 01 纯借记卡
	private String pdtdetailurl;// 商品详情地址 可空
	private String pdtdnm;// 商品名称 商品名称，多个商品以逗号隔开，不可为空

	private PayGatewayConfig payGatewayConfig;// 宝易互通
	private PayGateway99BillConfig payGateway99BillConfig;// 快钱

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
		this.servletPath = "/entity/basic/onlinePayment";
	}

	private void initPayGatewayConfig() {
		String prefix = ServletActionContext.getServletContext().getRealPath("/");
		String configdir = prefix + "WEB-INF" + File.separator + "config" + File.separator;

		this.payGatewayConfig = new PayGatewayConfig(configdir);
		try {
			payGatewayConfig.getConfig();
		} catch (PlatformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initPayGateway99BillConfig() {
		String prefix = ServletActionContext.getServletContext().getRealPath("/");
		String configdir = prefix + "WEB-INF" + File.separator + "config" + File.separator;
		this.payGateway99BillConfig = new PayGateway99BillConfig(configdir);
		try {
			payGateway99BillConfig.getConfig();
		} catch (PlatformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String electiveBank() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		// 判断订单状态，防止重复支付。
		PeBzzOrderInfo peBzzOrderInfo = (PeBzzOrderInfo) ServletActionContext.getRequest().getSession().getAttribute("peBzzOrderInfo");
		// Lee 支付方式：null=宝易互通 99bill=快钱支付
		Object paymentType = ServletActionContext.getRequest().getSession().getAttribute("paymentType");
		this.merorderid = peBzzOrderInfo.getSeq();
		if (peBzzOrderInfo != null) {
			try {
				// Lee 快钱支付
				if (null != paymentType && "99bill".equalsIgnoreCase(paymentType.toString())) {
					try {// 如果订单为宝易互通的订单 则调用宝易互通的查询 对订单状态进行查询
						// 订单规则说明：根据更新时间 对比订单的创建时间进行判断
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date pboiCreateDate = peBzzOrderInfo.getCreateDate();
						// 更新时间
						Date updateDate = sdf.parse("2015-12-18 18:00:00");
						// 支付接口切换 如果订单创建时间早于更新时间 则为旧支付接口生成的订单
						if (pboiCreateDate.after(updateDate)) {
							this.check99BillOrder(peBzzOrderInfo.getSeq());
						} else {
							this.checkOrder(peBzzOrderInfo.getSeq());
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					this.checkOrder(peBzzOrderInfo.getSeq());
				}

				DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
				orderDc.add(Restrictions.eq("seq", merorderid));
				List<PeBzzOrderInfo> oList = this.getGeneralService().getList(orderDc);
				if (oList != null && oList.size() > 0) {
					peBzzOrderInfo = oList.get(0);
					if (peBzzOrderInfo.getEnumConstByFlagPaymentState() != null
							&& "1".equals(peBzzOrderInfo.getEnumConstByFlagPaymentState().getCode())) {
						this.setMsg("订单已支付到账，请不要重复提交！");
						this.setTogo("close");
						return "msg";
					} else {// 点击支付后将自动三方认证次数清零，供定时任务调用
						peBzzOrderInfo.setAutoCheckTimes(0);
						this.getGeneralService().save(peBzzOrderInfo);
					}
				}
			} catch (EntityException e) {
				if (e.getMessage().indexOf("支付接口更换") != -1) {
					this.setTogo("close");
					this.setMsg(e.getMessage());
				} else {
					this.setMsg("订单验证失败，请关闭本页面并刷新订单列表后重新尝试！");
				}
				e.printStackTrace();
				return "msg";
			}
			// 跳转到快钱支付
			if (null != paymentType && "99bill".equalsIgnoreCase(paymentType.toString())) {
				this.initPayGateway99BillConfig();
				/** 必填参数* */
				// 字符集 固定值：1 1代表UTF-8
				this.inputCharset = payGateway99BillConfig.getInputCharset();
				// 查询接口版本 固定值：v2.0注意为小写字母
				this.version = payGateway99BillConfig.getVersion();
				// 网关页面显示诧言种类：固定值：1 1代表中文显示
				this.language = "1";
				// 签名类型 固定值：1 1代表MD5加密签名方式
				this.signType = payGateway99BillConfig.getSignType();
				// 人民币账号
				// 本参数用来指定接收款项的快钱用户的人民币账号
				this.merchantAcctId = payGateway99BillConfig.getMerchantAcctId();
				// 商户订单号 只允许使用字母、数字、- 、_,并以字母或数字开头,需要查询的某比订单
				this.orderId = this.merorderid;
				// 商户订单金额：整型数字 以分为单位。比方10元，提交时金额庒为1000,商户页面显示金额可以转换成以元为单位显示
				this.orderAmount = this.fromYuanToFen(peBzzOrderInfo.getAmount());
				// 商户订单提交时间：数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
				// 例如：20071117020101
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String payDate = sdf.format(new Date());
				this.orderTime = payDate;
				/** 非必填参数* */
				// 接收支付结果的页面地址
				this.pageUrl = "";// payGateway99BillConfig.getPageUrl();
				// 服务器接收支付结果的后台地址
				this.bgUrl = payGateway99BillConfig.getBgUrl();
				// 支付人姓名
				try {
					this.payerName = (peBzzOrderInfo.getPayer() == null || "".equals(peBzzOrderInfo.getPayer())) ? "" : new String(
							peBzzOrderInfo.getPayer().getBytes("UTF-8"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 快钱要求中文需要转成Unicode
				// 支付人联系方式类型：固定值：1或者2 1代表申子邮件方式；2代表手机联系方式
				this.payerContactType = "";
				// 支付人联系方式
				this.payerContact = "";
				// 商品名称
				try {
					this.productName = new String(peBzzOrderInfo.getEnumConstByFlagOrderType().getName().getBytes("UTF-8"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 快钱要求中文需要转成Unicode
				// 商品数量
				this.productNum = "";
				// 商品代码
				this.productId = "";
				// 商品描述
				this.productDesc = "";
				// 扩展字段1：存储回调后跳转页面的参数
				this.ext1 = ServletActionContext.getContext().getSession().get(peBzzOrderInfo.getSeq()) + "";
				// 扩展字段2
				this.ext2 = "";
				// 00代表显示快钱各支付方式列表
				this.payType = "00";
				// 银行代码
				this.bankId = "";
				// 同一订单禁止重复提交标志：固定选择值： 1、0
				// 1代表同一订单号只允许提交1次；0表示同一订单号在没有支付成功的前提下可重复提交多次。 默讣为0
				// 建议实物购物车结算类商户采用0；虚拟产品类商户采用1；
				this.redoFlag = "0";
				// 合作伙伴在快钱的用户编号：数字串 用户登彔快钱首页后可查询到。仅适用亍快钱合作伙伴中系统及平台提供商
				this.pid = "";
				String signMsgVal = "";
				signMsgVal = appendParam(signMsgVal, "inputCharset", this.inputCharset);
				signMsgVal = appendParam(signMsgVal, "pageUrl", this.pageUrl);
				signMsgVal = appendParam(signMsgVal, "bgUrl", this.bgUrl);
				signMsgVal = appendParam(signMsgVal, "version", this.version);
				signMsgVal = appendParam(signMsgVal, "language", this.language);
				signMsgVal = appendParam(signMsgVal, "signType", this.signType);
				signMsgVal = appendParam(signMsgVal, "merchantAcctId", this.merchantAcctId);
				signMsgVal = appendParam(signMsgVal, "payerName", this.payerName);
				signMsgVal = appendParam(signMsgVal, "payerContactType", this.payerContactType);
				signMsgVal = appendParam(signMsgVal, "payerContact", this.payerContact);
				signMsgVal = appendParam(signMsgVal, "orderId", this.orderId);
				signMsgVal = appendParam(signMsgVal, "orderAmount", this.orderAmount);
				signMsgVal = appendParam(signMsgVal, "orderTime", this.orderTime);
				signMsgVal = appendParam(signMsgVal, "productName", this.productName);
				signMsgVal = appendParam(signMsgVal, "productNum", this.productNum);
				signMsgVal = appendParam(signMsgVal, "productId", this.productId);
				signMsgVal = appendParam(signMsgVal, "productDesc", this.productDesc);
				signMsgVal = appendParam(signMsgVal, "ext1", this.ext1);
				signMsgVal = appendParam(signMsgVal, "ext2", this.ext2);
				signMsgVal = appendParam(signMsgVal, "payType", this.payType);
				signMsgVal = appendParam(signMsgVal, "bankId", this.bankId);
				signMsgVal = appendParam(signMsgVal, "redoFlag", this.redoFlag);
				signMsgVal = appendParam(signMsgVal, "pid", this.pid);
				Pkipair pki = new Pkipair();
				this.signMsg = pki.signMsg(signMsgVal);
				return "to99Bill";
			} else {
				if ("0".equalsIgnoreCase(us.getRoleId())) {// 学员支付
					return "stuElectiveBank";
				} else {
					return "electiveBank";
				}
			}
		}
		this.setMsg("订单验证失败，请重试");
		return "msg";
	}

	public String doPayment() {

		this.initPayGatewayConfig();

		this.merchantid = payGatewayConfig.getMerchantid();
		this.currencytype = payGatewayConfig.getCurrencytype();
		this.autojump = payGatewayConfig.getAutojump();
		this.merurl = payGatewayConfig.getMerurl();
		this.tradetype = payGatewayConfig.getTradetype();
		this.bankInput = payGatewayConfig.getBankInput();
		this.confirm = payGatewayConfig.getConfirm();
		this.informmer = payGatewayConfig.getInformmer();
		this.informurl = payGatewayConfig.getInformurl();

		this.strInterface = payGatewayConfig.getStrInterface();
		this.subject = payGatewayConfig.getSubject();
		this.waittime = payGatewayConfig.getWaittime();

		this.bankcardtype = payGatewayConfig.getBankcardtype();

		String merkey = payGatewayConfig.getMerkey();
		this.url = payGatewayConfig.getUrl();

		this.remark = "";
		// 拼接加密的源字符串
		String mac_src = "merchantid=" + merchantid + "&merorderid=" + merorderid + "&amountsum=" + amountsum + "&subject=" + subject
				+ "&currencytype=" + currencytype + "&autojump=" + autojump + "&waittime=" + waittime + "&merurl=" + merurl + "&informmer="
				+ informmer + "&informurl=" + informurl + "&confirm=" + confirm + "&merbank=" + merbank + "&tradetype=" + tradetype
				+ "&bankInput=" + bankInput + "&interface=" + strInterface + "&bankcardtype=" + bankcardtype + "&pdtdetailurl="
				+ "&merkey=" + merkey;

		this.mac = Crypto.GetMessageDigest(mac_src);
		return "toPayment";
	}

	public String paymentReceive() {
		this.initPayGatewayConfig();
		String merkey = payGatewayConfig.getMerkey();

		this.strInterface = ServletActionContext.getRequest().getParameter("interface");
		this.mac_src = "merchantid=" + merchantid + "&merorderid=" + merorderid + "&amountsum=" + amountsum + "&currencytype="
				+ currencytype + "&subject=" + subject + "&state=" + state + "&paybank=" + paybank + "&banksendtime=" + banksendtime
				+ "&merrecvtime=" + merrecvtime + "&interface=" + strInterface + "&merkey=" + merkey;
		this.mac_rec = this.mac;
		this.mac = Crypto.GetMessageDigest(mac_src);
		System.out.println(mac);
		System.out.println(mac_rec);
		if (mac.toLowerCase().equals(mac_rec.toLowerCase()) && state.equals("1")) {
			try {
				EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");
				DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
				orderDc.add(Restrictions.eq("seq", merorderid));
				// 只执行未到账的订单，避免定时任务与网页触发同时进行而出现错误。
				orderDc.add(Restrictions.eq("enumConstByFlagPaymentState", enumConstByFlagPaymentState));
				PeBzzOrderInfo pbo = null;
				List orderList = this.getGeneralService().getList(orderDc);
				if (orderList != null && orderList.size() > 0) {
					pbo = (PeBzzOrderInfo) orderList.get(0);
					if (pbo != null && Double.parseDouble(pbo.getAmount()) == Double.parseDouble(this.amountsum)) {
						confirmOrder(this.getMerorderid());
					} else {
						System.out.println("订单:" + this.merorderid + "金额不符--" + pbo.getAmount() + "-" + this.amountsum + "--"
								+ (new Date().toString()));
						this.setMsg("订单金额不符，请联系管理员！");
					}
				}
			} catch (EntityException e) {
				this.setMsg("确认订单失败，请联系管理员！");
				e.printStackTrace();
			}
			this.setMsg("支付成功");
			// return "msg";
		} else {
			this.setMsg("支付失败");
			// return "msg";
		}
		String back = (String) ServletActionContext.getContext().getSession().get(this.getMerorderid());
		if ("stu".equalsIgnoreCase(back)) {
			return "stu_msg";
		} else if ("colElePayment".equalsIgnoreCase(back)) {// 直接选课页面不能刷新，要返回选课导入页面
			this.setTogo("close");
			return "msg";
		} else {
			return "msg";
		}
		// // 根据订单号，从session中获取返回路径
		// String back = (String) ServletActionContext.getContext().getSession()
		// .get(this.getMerorderid());
		// if (back == null || "".equalsIgnoreCase(back)) {
		// this.setMsg("会话失效，请联系管理员！");
		// return "msg";
		// } else if ("elePeriodPayment".equalsIgnoreCase(back)) {
		// return "elePeriodPayment";
		// } else if ("buyHourPayment".equalsIgnoreCase(back)) {
		// return "buyHourPayment";
		// } else if ("colElePayment".equalsIgnoreCase(back)) {
		// return "colElePayment";
		// } else if ("stuElePayment".equalsIgnoreCase(back)) {
		// return "stuElePayment";
		// } else if ("batchPayment".equalsIgnoreCase(back)) {
		// return "batchPayment";
		// } else if("paymentBatchByGroup".equalsIgnoreCase(back)){
		// return "paymentBatchByGroup";
		// } else if("continuePayment".equalsIgnoreCase(back)) {
		// return "continuePayment";
		// } else {
		// this.setMsg("会话失效，请联系管理员");
		// return "msg";
		// }
	}

	public String doRefund() {
		Map map = new HashMap();

		try {

			PeBzzOrderInfo peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getOrderBySeq(merorderid);
			if (peBzzOrderInfo != null && peBzzOrderInfo.getRefundId() != null && !"".equals(peBzzOrderInfo.getRefundId())) {// 已经申请过退费

				String re = this.getGeneralService().confirmRefund(merorderid, peBzzOrderInfo.getRefundId());
				map.put("success", "true");
				if (re != null) {
					map.put("info", re);
				} else {
					map.put("info", "退费成功");
				}
				this.setJsonString(JsonUtil.toJSONString(map));
				return this.json();
			} else {
				// 申请三方退款
				AutoRefund autoRefund = this.getGeneralService().refundOnlineApply(merorderid, appuser, cause, amountsum);
				String status = autoRefund.getStatus();// 返回状态
				String refundId_rec = autoRefund.getRefundId();// 退款流水号，用于查询退款状态
				if (refundId_rec != null || !"".equals(refundId_rec)) {
					if ("0000".equalsIgnoreCase(status) || "0008".equalsIgnoreCase(status)) {// 申请成功||存在申请记录
						// 执行判断并操作平台内退款
						this.getGeneralService().confirmRefund(merorderid, refundId_rec);
						map.put("success", "true");
						map.put("info", "退费成功");
						this.setJsonString(JsonUtil.toJSONString(map));
						return this.json();
					} else {
						map.put("success", "false");
						map.put("info", "请等待平台确认。");
						this.setJsonString(JsonUtil.toJSONString(map));
						return this.json();
					}
				} else {
					map.put("success", "false");
					map.put("info", "平台无返回，请等待平台确认。");
					this.setJsonString(JsonUtil.toJSONString(map));
					return this.json();
				}
			}
		} catch (EntityException e) {
			// e.printStackTrace();

			map.put("success", "false");
			map.put("info", e.getMessage());
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}

	}

	public String do99BillRefund() {
		Map map = new HashMap();
		try {
			PeBzzOrderInfo peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getOrderBySeq(merorderid);
			Payment99BillUtil pu = new Payment99BillUtil();// 支付工具类
			// 再次检查是否申请过退费
			List<OnlineRefundInfo> refundList = pu.checkRefoundState(null, "", "");
			for (OnlineRefundInfo ori : refundList) {
				// 退费列表中订单号匹配的订单
				if (peBzzOrderInfo.getSeq().equals(ori.getMerOrderId())) {
					// 将"丢失"的refundId补全后重新调用退费方法
					peBzzOrderInfo.setRefundId(ori.getRefundId());
					this.getGeneralService().update(peBzzOrderInfo);
					peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getOrderBySeq(merorderid);
				}
			}
			// 已经申请过退费
			if (peBzzOrderInfo != null && peBzzOrderInfo.getRefundId() != null && !"".equals(peBzzOrderInfo.getRefundId())) {
				String re = this.getGeneralService().confirm99BillRefund(merorderid, peBzzOrderInfo.getRefundId());
				map.put("success", "true");
				if (re != null) {
					map.put("info", re);
				} else {
					map.put("info", "退费成功");
				}
				this.setJsonString(JsonUtil.toJSONString(map));
				return this.json();
			} else {
				AutoRefund autoRefund = this.getGeneralService().refundOnline99BillApply(merorderid, appuser, cause, amountsum);
				String status = autoRefund.getStatus();// 返回状态
				String refundId_rec = autoRefund.getRefundId();// 退款流水号，用于查询退款状态
				if (refundId_rec != null || !"".equals(refundId_rec)) {
					if ("y".equalsIgnoreCase(status)) {// 申请成功||存在申请记录
						this.getGeneralService().confirm99BillRefund(merorderid, refundId_rec);
						map.put("success", "true");
						map.put("info", "退费成功");
						this.setJsonString(JsonUtil.toJSONString(map));
						return this.json();
					} else {
						map.put("success", "false");
						map.put("info", "请等待平台确认。");
						this.setJsonString(JsonUtil.toJSONString(map));
						return this.json();
					}
				} else {
					map.put("success", "false");
					map.put("info", "平台无返回，请等待平台确认。");
					this.setJsonString(JsonUtil.toJSONString(map));
					return this.json();
				}
			}
		} catch (EntityException e) {
			map.put("success", "false");
			map.put("info", e.getMessage());
			this.setJsonString(JsonUtil.toJSONString(map));
			return this.json();
		}
	}

	/**
	 * 订单状态，三方确认订单状态
	 * 
	 * @author linjie
	 */
	private void checkOrder(String merorderid) throws EntityException {
		this.getGeneralService().checkOnlineOrder(merorderid);
	}

	/**
	 * 快钱订单状态
	 * 
	 * @param merorderid
	 *            订单号
	 * @throws EntityException
	 */
	private void check99BillOrder(String merorderid) throws EntityException {
		this.getGeneralService().check99BillOrder(merorderid);
	}

	/**
	 * 确认订单到账
	 * 
	 * @throws EntityException
	 */
	private void confirmOrder(String merorderid) throws EntityException {
		this.getGeneralService().confirmOnlineOrder(merorderid);
	}

	public String testOrder() {
		// HttpServletResponse response = ServletActionContext.getResponse();
		// response.setHeader("Content-type","text/html;charset=UTF-8");
		// response.setCharacterEncoding("UTF-8");

		// PrintWriter out = null;
		// try {
		// out = response.getWriter();
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }

		try {
			checkOrder(this.merchantid);
			// out.println("处理成功！");
		} catch (EntityException e) {
			e.printStackTrace();
			// if(out!=null){
			// out.println(e.toString());
			// }
		}
		this.setMsg("完成");
		return "msg";
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getMerorderid() {
		return merorderid;
	}

	public void setMerorderid(String merorderid) {
		this.merorderid = merorderid;
	}

	public String getAmountsum() {
		return amountsum;
	}

	public void setAmountsum(String amountsum) {
		this.amountsum = amountsum;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCurrencytype() {
		return currencytype;
	}

	public void setCurrencytype(String currencytype) {
		this.currencytype = currencytype;
	}

	public String getAutojump() {
		return autojump;
	}

	public void setAutojump(String autojump) {
		this.autojump = autojump;
	}

	public String getWaittime() {
		return waittime;
	}

	public void setWaittime(String waittime) {
		this.waittime = waittime;
	}

	public String getMerurl() {
		return merurl;
	}

	public void setMerurl(String merurl) {
		this.merurl = merurl;
	}

	public String getInformmer() {
		return informmer;
	}

	public void setInformmer(String informmer) {
		this.informmer = informmer;
	}

	public String getInformurl() {
		return informurl;
	}

	public void setInformurl(String informurl) {
		this.informurl = informurl;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getMerbank() {
		return merbank;
	}

	public void setMerbank(String merbank) {
		this.merbank = merbank;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getBankInput() {
		return bankInput;
	}

	public void setBankInput(String bankInput) {
		this.bankInput = bankInput;
	}

	public String getStrInterface() {
		return strInterface;
	}

	public void setStrInterface(String strInterface) {
		this.strInterface = strInterface;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMac_src() {
		return mac_src;
	}

	public void setMac_src(String mac_src) {
		this.mac_src = mac_src;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPaybank() {
		return paybank;
	}

	public void setPaybank(String paybank) {
		this.paybank = paybank;
	}

	public String getBanksendtime() {
		return banksendtime;
	}

	public void setBanksendtime(String banksendtime) {
		this.banksendtime = banksendtime;
	}

	public String getMerrecvtime() {
		return merrecvtime;
	}

	public void setMerrecvtime(String merrecvtime) {
		this.merrecvtime = merrecvtime;
	}

	public String getMac_rec() {
		return mac_rec;
	}

	public void setMac_rec(String mac_rec) {
		this.mac_rec = mac_rec;
	}

	public PayGatewayConfig getPayGatewayConfig() {
		return payGatewayConfig;
	}

	public void setPayGatewayConfig(PayGatewayConfig payGatewayConfig) {
		this.payGatewayConfig = payGatewayConfig;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAppuser() {
		return appuser;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getRefundMeg() {
		return refundMeg;
	}

	public void setRefundMeg(String refundMeg) {
		this.refundMeg = refundMeg;
	}

	public String getBankcardtype() {
		return bankcardtype;
	}

	public void setBankcardtype(String bankcardtype) {
		this.bankcardtype = bankcardtype;
	}

	public String getPdtdetailurl() {
		return pdtdetailurl;
	}

	public void setPdtdetailurl(String pdtdetailurl) {
		this.pdtdetailurl = pdtdetailurl;
	}

	public String getPdtdnm() {
		return pdtdnm;
	}

	public void setPdtdnm(String pdtdnm) {
		this.pdtdnm = pdtdnm;
	}

	public PayGateway99BillConfig getPayGateway99BillConfig() {
		return payGateway99BillConfig;
	}

	public void setPayGateway99BillConfig(PayGateway99BillConfig payGateway99BillConfig) {
		this.payGateway99BillConfig = payGateway99BillConfig;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getBgUrl() {
		return bgUrl;
	}

	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerContactType() {
		return payerContactType;
	}

	public void setPayerContactType(String payerContactType) {
		this.payerContactType = payerContactType;
	}

	public String getPayerContact() {
		return payerContact;
	}

	public void setPayerContact(String payerContact) {
		this.payerContact = payerContact;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getRedoFlag() {
		return redoFlag;
	}

	public void setRedoFlag(String redoFlag) {
		this.redoFlag = redoFlag;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	private String appendParam(String returns, String paramId, String paramValue) {
		if (returns != "") {
			if (paramValue != "") {
				returns += "&" + paramId + "=" + paramValue;
			}
		} else {
			if (paramValue != "") {
				returns = paramId + "=" + paramValue;
			}
		}
		return returns;
	}

	/**
	 * 分转换为元.
	 * 
	 * @param fen
	 *            分
	 * @return 元
	 */
	private static String fromFenToYuan(final String fen) {
		String yuan = "";
		final int MULTIPLIER = 100;
		Pattern pattern = Pattern.compile("^[1-9][0-9]*{1}");
		Matcher matcher = pattern.matcher(fen);
		if (matcher.matches()) {
			yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
		} else {
			System.out.println("参数格式不正确!");
		}
		return yuan;
	}

	/**
	 * 元转换为分.
	 * 
	 * @param yuan
	 *            元
	 * @return 分
	 */
	private static String fromYuanToFen(final String yuan) {
		String fen = "";
		Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?{1}");
		Matcher matcher = pattern.matcher(yuan);
		if (matcher.matches()) {
			try {
				NumberFormat format = NumberFormat.getInstance();
				Number number = format.parse(yuan);
				double temp = number.doubleValue() * 100.0;
				// 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012
				format.setGroupingUsed(false);
				// 设置返回数的小数部分所允许的最大位数
				format.setMaximumFractionDigits(0);
				fen = format.format(temp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("参数格式不正确!");
		}
		return fen;
	}
}
