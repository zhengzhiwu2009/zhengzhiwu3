/**
 * 
 */
package com.whaty.platform.entity.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PayGatewayConfig;
import com.whaty.platform.entity.bean.AutoRefund;
import com.whaty.platform.entity.bean.OnlineOrderInfo;
import com.whaty.platform.entity.bean.OnlineRefundInfo;
import com.whaty.platform.entity.bean.OnlineRefundRequestInfo;
import com.whaty.platform.entity.bean.OnlineRequestInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.util.XMLParserUtil2Pay;
import com.whaty.util.Crypto;

/**
 * 
 * 三方支付工具类
 * 
 * @author linjie
 * 
 */
public class PaymentUtil {

	HttpClient httpClient = new HttpClient();

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
	// 单个订单查询地址
	private String singleQueryUrl;
	// 对账地址
	private String batchQueryUrl;
	// 退费查询地址
	private String refundQueryUrl;
	// 退费申请地址
	private String refundUrl;
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

	private PayGatewayConfig payGatewayConfig;

	private String merkey;

	private String bankcardtype;// 支付银行卡类型 00借贷卡混合 01 纯借记卡
	private String pdtdetailurl;// 商品详情地址 可空
	private String pdtdnm;// 商品名称 商品名称，多个商品以逗号隔开，不可为空

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

	/**
	 * 单个检查订单状态
	 * 
	 * @author linjie
	 * @param merOrderId
	 *            订单ID
	 */
	public List checkOrderState(String merOrderId) throws EntityException {
		this.initPayGatewayConfig();

		PostMethod postMethod = new PostMethod(singleQueryUrl);// 单个查询
		postMethod.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
		String params = "merchantId=" + this.merchantid + "&merOrderId=" + merOrderId + "&version=" + this.version + "&key=" + merkey;
		this.mac = Crypto.GetMessageDigest(params, "MD5");

		postMethod.addParameter("merchantId", merchantid);
		postMethod.addParameter("merOrderId", merOrderId);
		postMethod.addParameter("version", version);
		postMethod.addParameter("mac", mac);

		String bankResultValue = "";
		try {
			bankResultValue = httpResult(postMethod);
		} catch (EntityException e) {
			e.printStackTrace();
			throw e;
		}

		try {
			OnlineRequestInfo requestInfo = XMLParserUtil2Pay.getRequestInfo(bankResultValue);

			String params_rec = "merchantId=" + requestInfo.getMerchantId() + "&version=" + requestInfo.getVersion() + "&merOrderId="
					+ requestInfo.getMerOrderId() + "&key=" + merkey;
			mac_rec = Crypto.GetMessageDigest(params_rec, "MD5");
			String status = requestInfo.getStatus();
			if (mac_rec.equalsIgnoreCase(requestInfo.getMac())) {
				if ("success".equals(status)) {
					// 查询成功
					String orderNum = requestInfo.getRecordLength();
					if (Integer.parseInt(orderNum) > 0) {
						List<OnlineOrderInfo> orderList = XMLParserUtil2Pay.getOrderList(bankResultValue);
						if (orderList != null && orderList.size() > 0) {
							for (int i = 0; i < orderList.size(); i++) {
								OnlineOrderInfo order = orderList.get(i);
								if ("1".equals(order.getState())) {// 付款成功的订单

								} else {// 付款不成功
									orderList.remove(i);//
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
									Date now = new Date();
									// 更新时间 支付接口切换
									Date updateDate = sdf.parse("2015-12-18 18:00:00");
									// 如果订单创建时间早于更新时间 则为旧支付接口生成的订单
									if (now.after(updateDate)) {
										throw new EntityException("支付接口更换，请关闭订单重新选课。");
									}
								}
							}
							return orderList;
						}
					} else {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date now = new Date();
						// 更新时间 支付接口切换
						Date updateDate = sdf.parse("2015-12-18 18:00:00");
						// 如果订单创建时间早于更新时间 则为旧支付接口生成的订单
						if (now.after(updateDate)) {
							throw new EntityException("支付接口更换，请关闭订单重新选课。");
						}
					}
				} else if (status != null && status.indexOf(":") > -1) {
					// 查询失败，有返回error id,格式checkFailure,error id:xx
					String errorId = status.substring(status.indexOf(":") + 1, status.length());
					System.out.println("查询错误--" + status);
					throw new EntityException("查询错误--" + status + "|" + errorId + ",请联系管理员");
					// if("1".equals(errorId)){//1 请求数据格式校验未通过
					//
					// }else if("2".equals(errorId)){//2 商户MAC校验不匹配
					//
					// }else if("3".equals(errorId)){//3 组织商户查询结果数据失败
					//
					// }else if("4".equals(errorId)){//4 获取新的MAC验证串失败
					//
					// }else if("5".equals(errorId)){//5 将商户订查询结果据发送给商户失败
					//
					// }else if("7".equals(errorId)){//7 商户编号为空
					//
					// }else if("8".equals(errorId)){//8 商户不存在
					//
					// }else if("11".equals(errorId)){//11 发送商户查询请求错误信息失败
					//
					// }else if("13".equals(errorId)){//13 币种格式错误
					//
					// }else if("14".equals(errorId)){//14 支付行错误
					//
					// }else if("15".equals(errorId)){//15 账户编号错误
					//
					// }else if("16".equals(errorId)){//16 交易号错误
					//
					// }else if("17".equals(errorId)){//17 开始时间错误
					//
					// }else if("18".equals(errorId)){//18 结束时间错误
					//
					// }else if("19".equals(errorId)){//19 时间间隔错误
					//
					// }else{//查询失败，未知错误
					//
					// }
				} else {
					// 查询失败，未知错误
					throw new EntityException("查询错误--未知错误");
				}
				// if(nameList!=null){
				//
				// for (Iterator nameIter = nameList.iterator();
				// nameIter.hasNext();) {
				// Element eleName = (Element) nameIter.next();
				// String eleNameValue = eleName.getStringValue();
				// System.out.println(eleNameValue);
				// }
				// }
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date now = new Date();
				// 更新时间 支付接口切换
				Date updateDate = sdf.parse("2015-12-18 18:00:00");
				// 如果订单创建时间早于更新时间 则为旧支付接口生成的订单
				if (now.after(updateDate)) {
					throw new EntityException("支付接口更换，请关闭订单重新选课。");
				}
			}
		} catch (Exception e) {
			if (e.getMessage().indexOf("支付接口更换") != -1) {
				throw new EntityException(e.getMessage());
			} else {
				throw new EntityException("查询错误--支付接口异常,请联系管理员");
			}
		}
		return null;
	}

	/**
	 * 批量检查订单退费状态
	 * 
	 * @author linjie
	 * @param refundId
	 *            订单ID
	 * @param start
	 *            开始时间 格式：2010-01-01
	 * @param end
	 *            截至时间 格式：2010-01-01
	 *            tips：当选择时间段查询时（需保证起止时间都存在值且格式正确），refoundId字段不起作用，
	 *            当需要查询某一笔订单的退款记录时，输入refoundId。
	 */
	public List<OnlineRefundInfo> checkRefoundState(String refoundId, String start, String end) throws EntityException {
		this.initPayGatewayConfig();// 初始化参数
		refoundId = StringUtils.defaultString(refoundId);
		start = StringUtils.defaultString(start);
		end = StringUtils.defaultString(end);
		if ((refoundId != null && !"".equals(refoundId)) || (start != null && !"".equals(start) && end != null && !"".equals(end))) {

		} else {
			throw new EntityException("参数错误，未执行查询");
		}
		PostMethod postMethod = new PostMethod(refundQueryUrl);// 退费查询
		postMethod.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
		String params = "merchantId=" + this.merchantid + "&beginDate=" + start + "&endDate=" + end + "&refundId=" + refoundId
				+ "&version=" + this.version + "&key=" + merkey;
		this.mac = Crypto.GetMessageDigest(params, "MD5");

		postMethod.addParameter("merchantId", merchantid);
		postMethod.addParameter("refundId", refoundId);
		postMethod.addParameter("beginDate", start);
		postMethod.addParameter("endDate", end);
		postMethod.addParameter("version", version);
		postMethod.addParameter("mac", mac);

		String bankResultValue = "";
		try {
			bankResultValue = httpResult(postMethod);
			if (bankResultValue == null || "".equals(bankResultValue)) {
				throw new EntityException("平台无返回");
			}
		} catch (EntityException e) {
			// 连接失败
			throw e;
		}
		try {
			OnlineRefundRequestInfo requestInfo = XMLParserUtil2Pay.getRefundRequestInfo(bankResultValue);

			String params_rec = "refundId=" + refoundId + "&beginDate=" + start + "&endDate=" + end + "&merchantId="
					+ requestInfo.getMerchantId() + "&version=" + this.version + "&key=" + merkey;
			mac_rec = Crypto.GetMessageDigest(params_rec, "MD5");
			String status = requestInfo.getStatus();
			if (mac_rec.equalsIgnoreCase(requestInfo.getMac())) {
				if ("0000".equals(status)) {
					// 查询成功
					String orderNum = requestInfo.getRecordLength();
					if (Integer.parseInt(orderNum) > 0) {
						List<OnlineRefundInfo> refundList = XMLParserUtil2Pay.getRefundList(bankResultValue);
						if (refundList != null && refundList.size() > 0) {
							// for(int i=0;i<refundList.size();i++){
							// OnlineRefundInfo refund = refundList.get(i);
							// if("3".equals(refund.getState())){//退款成功的订单
							//
							// }else if("0".equals(refund.getState())){//新申请
							//
							// }else if("1".equals(refund.getState())){//处理中
							//
							// }else if("2".equals(refund.getState())){//拒绝
							//
							// }else if("4".equals(refund.getState())){//平台拒绝
							//
							// }else if("5".equals(refund.getState())){//撤销申请
							//
							// }else{//未知状态
							//
							// }
							// }
							return refundList;
						}
					}
				} else if ("0005".equals(status)) { // 版本号不正确
					throw new EntityException("查询错误--版本号不正确");
				} else if ("0006".equals(status)) { // 传输的关键数据为空
					throw new EntityException("查询错误--传输的关键数据为空");
				} else if ("0007".equals(status)) { // 此商户不存
					throw new EntityException("查询错误--此商户不存");
				} else if ("0008".equals(status)) { // mac不匹配
					throw new EntityException("查询错误--mac不匹配");
				} else if ("0009".equals(status)) { // 两个日期不完整
					throw new EntityException("查询错误--两个日期不完整");
				} else if ("0010".equals(status)) { // 开始时间或结束时间格式不正确
					throw new EntityException("查询错误--开始时间或结束时间格式不正确");
				} else if ("0011".equals(status)) { // 日期或退款id不能为空
					throw new EntityException("查询错误--日期或退款id不能为空");
				} else if ("0012".equals(status)) { // 退款id格式不正确
					throw new EntityException("查询错误--退款id格式不正确");
				} else if ("0013".equals(status)) { // 此退款记录不存在
					throw new EntityException("查询错误--此退款记录不存在");
				} else if ("9999".equals(status)) { // 其它错误
					throw new EntityException("查询错误--其它错误");
				} else {
					// 查询失败，未知错误
					throw new EntityException("查询错误--未知错误");
				}
			} else {
				throw new EntityException("返回mac不匹配");
			}
		} catch (Exception e) {
			throw new EntityException("未知错误");
		}
		return null;

	}

	/**
	 * 退费申请
	 * 
	 * @author linjie
	 * @param merOrderId
	 *            订单ID
	 */
	public AutoRefund doRefundApply(String merOrderId, String appUser, String cause, String amountSum) throws EntityException {
		this.initPayGatewayConfig();
		PostMethod postMethod = new PostMethod(refundUrl);// 退费申请
		postMethod.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
		try {
			cause = StringUtils.defaultString(cause).getBytes("utf-8").toString();
			appUser = appUser.getBytes("utf-8").toString();
			postMethod.addParameter("merchantId", this.merchantid);
			postMethod.addParameter("merOrderId", merOrderId);
			postMethod.addParameter("amount", amountSum);
			postMethod.addParameter("cause", cause);
			postMethod.addParameter("appuser", appUser);
			postMethod.addParameter("version", this.version);
			String params = "merchantId=" + this.merchantid + "&merOrderId=" + merOrderId + "&amount=" + amountSum + "&cause=" + cause
					+ "&appuser=" + appUser + "&version=" + this.version + "&key=" + merkey;
			System.out.println("********退费申请:params" + params);
			this.mac = Crypto.GetMessageDigest(params, "MD5");

			postMethod.addParameter("mac", this.mac);
			String bankResultValue = "";
			try {
				bankResultValue = httpResult(postMethod);
			} catch (EntityException e) {
				// 连接失败
				throw e;
			}
			AutoRefund autoRefund = XMLParserUtil2Pay.getAutoRefund(bankResultValue);
			String receive = "merchantId=" + this.merchantid + "&merOrderId=" + merOrderId + "&amount=" + amountSum + "&refundId="
					+ autoRefund.getRefundId() + "&status=" + autoRefund.getStatus() + "&version=" + this.version + "&key=" + merkey;
			this.mac_rec = Crypto.GetMessageDigest(receive, "MD5");
			if (!this.mac_rec.equals(autoRefund.getMac())) {
				throw new EntityException("验证失败！");
			}
			if (autoRefund != null) {
				String status = autoRefund.getStatus();
				if (status.equals("0000")) {
					return autoRefund;
				} else if (status.equals("0003")) {
					throw new EntityException("版本号不正确");
				} else if (status.equals("0004")) {
					throw new EntityException("传输的关键数据为空");
				} else if (status.equals("0005")) {
					throw new EntityException("自动退款处理,申请人字符过长");
				} else if (status.equals("0006")) {
					throw new EntityException("mac校验不通过");
				} else if (status.equals("0007")) {
					throw new EntityException("未找到要退款的记录，可能已结算");
				} else if (status.equals("0008")) {
					return autoRefund;
					// throw new EntityException("自动退款处理错误,存在退款中的记录");
				} else if (status.equals("0009")) {
					throw new EntityException("退款金额大于可退款金额");
				} else if (status.equals("0010")) {
					throw new EntityException("自动退款处理,输入金额小于或者等于0,不能退款");
				} else if (status.equals("0011")) {
					throw new EntityException("该笔订单未汇总，请在系统汇总后再发起退款。（5点到23点之间，系统在支付完成后30分钟内完成汇总");
				} else if (status.equals("9999")) {
					// throw new EntityException("请等待30分钟之后再审核");
					throw new EntityException("审核成功，请关注平台处理结果。");
				} else {
					throw new EntityException("未知问题");
				}
			} else {
				throw new EntityException("申请失败，平台无返回数据。");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw new EntityException(e.getMessage());
		}

		// return null;
	}

	/**
	 * 多订单对账
	 * 
	 * @author linjie
	 * @param start
	 *            开始时间 格式：yyyy-MM-dd
	 * @param end
	 *            结束时间 格式：yyyy-MM-dd
	 */
	public List<OnlineOrderInfo> checkOrderList(String startTime, String endTime, String payBank) throws EntityException {
		this.initPayGatewayConfig();
		PostMethod postMethod = new PostMethod(this.batchQueryUrl);// 批量查询接口
		postMethod.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
		try {
			String params = "merchantId=" + this.merchantid + "&payBank=" + payBank + "&startTime=" + startTime + "&endTime=" + endTime
					+ "&version=" + version + "&type=0" + // 0:非会员交易
					// 5：会员即时到账交易
					// 6:会员担保交易支付；
					"&key=" + merkey;
			System.out.println("********对订单对账:params" + params);
			this.mac = Crypto.GetMessageDigest(params, "MD5");

			postMethod.addParameter("merchantId", this.merchantid);
			postMethod.addParameter("payBank", payBank);
			postMethod.addParameter("startTime", startTime);
			postMethod.addParameter("endTime", endTime);
			postMethod.addParameter("version", version);
			postMethod.addParameter("type", "0");// 0:非会员交易 5：会员即时到账交易
			// 6:会员担保交易支付；
			postMethod.addParameter("mac", this.mac);

			String bankResultValue = "";
			try {
				bankResultValue = httpResult(postMethod);
			} catch (EntityException e) {
				// 连接失败
				throw e;
			}
			OnlineRequestInfo requestInfo = XMLParserUtil2Pay.getRequestInfo(bankResultValue);
			String receive = "merchantId=" + this.merchantid + "&startTime=" + startTime + "&endTime=" + endTime + "&version="
					+ this.version + "&payBank=" + payBank + "&type=0" + "&key=" + merkey;
			this.mac_rec = Crypto.GetMessageDigest(receive, "MD5");
			if (!this.mac_rec.equals(requestInfo.getMac())) {
				throw new EntityException("验证失败！");
			}
			List<OnlineOrderInfo> orderList = XMLParserUtil2Pay.getOrderList(bankResultValue);
			if (orderList != null && orderList.size() > 0) {
				return orderList;
			} else {
				return null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw new EntityException(e.getMessage());
		}

		// return null;

	}

	/**
	 * 建立http连接返回结果
	 * 
	 * @param postMethod
	 * @return bankResultValue
	 * @throws EntityException
	 */
	private String httpResult(PostMethod postMethod) throws EntityException {
		// 获取执行结果
		int status = 0;
		String bankResultValue = "";
		try {
			status = httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			e.printStackTrace();
			throw new EntityException("HTTP链接异常!");
		} catch (IOException e) {
			e.printStackTrace();
			throw new EntityException("HTTP I/O异常!");
		}
		if (status != 200) {
			throw new EntityException("Pay connection error！errorcode:" + status);
		} else {
			// 连接成功，获取response中的xml数据
			try {
				bankResultValue = new String(postMethod.getResponseBody(), "UTF-8");
				System.out.println("Received data :--" + bankResultValue);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new EntityException("编码异常！");
			} catch (IOException e) {
				e.printStackTrace();
				throw new EntityException("IO异常！");
			}
		}
		return bankResultValue;
	}

	/**
	 * 用于解析退费申请后传递回的xml数据
	 * 
	 * @param xml
	 */
	public void parseXMLText(String xml) {
		int order1 = xml.indexOf("<merOrderId>");
		int order2 = xml.indexOf("</merOrderId>");
		this.merorderid = xml.substring(order1 + 12, order2);
		int x1 = xml.indexOf("<status>");
		int x2 = xml.indexOf("</status>");
		String status = xml.substring(x1 + 8, x2);
		this.state = status;

		System.out.println("***************************" + status);

	}

	/**
	 * 初始化umpay参数
	 */
	public void initPayGatewayConfig() {
		String prefix = Thread.currentThread().getContextClassLoader().getResource("").getPath();// 获得路径
		String configdir = prefix.substring(0, prefix.length() - 8).replace("/", File.separator).replace("\\", File.separator) + "config"
				+ File.separator;

		this.payGatewayConfig = new PayGatewayConfig(configdir);
		try {
			payGatewayConfig.getConfig();
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		this.merchantid = payGatewayConfig.getMerchantid();// 商户ID
		this.merkey = payGatewayConfig.getMerkey();// 加密密钥
		this.currencytype = payGatewayConfig.getCurrencytype();// 币种
		this.autojump = payGatewayConfig.getAutojump();// 自动跳转
		this.tradetype = payGatewayConfig.getTradetype();// 支付类型（即时到帐）
		this.bankInput = payGatewayConfig.getBankInput();// 扩展字段
		this.confirm = payGatewayConfig.getConfirm();// 商户返回确认0、1
		this.informmer = payGatewayConfig.getInformmer();// 是否通知商户0、1
		this.informurl = payGatewayConfig.getInformurl();// 商户取货URL
		this.version = payGatewayConfig.getVersion();// 退费接口版本
		this.strInterface = payGatewayConfig.getStrInterface();// 接口版本
		this.subject = payGatewayConfig.getSubject();// 商品种类，默认empty
		this.waittime = payGatewayConfig.getWaittime();// 等待时间，默认

		this.url = payGatewayConfig.getUrl();// 支付地址
		this.refundUrl = payGatewayConfig.getRefundUrl();// 退费申请地址
		this.singleQueryUrl = payGatewayConfig.getSingleQueryUrl();// 单个订单查询地址
		this.batchQueryUrl = payGatewayConfig.getBatchQueryUrl();// 批量查询地址（对账地址）
		this.refundQueryUrl = payGatewayConfig.getRefundQueryUrl();// 退费查询地址
		this.merurl = payGatewayConfig.getMerurl();// 取货页面
	}

	public String getMerkey() {
		return merkey;
	}

	public void setMerkey(String merkey) {
		this.merkey = merkey;
	}
}
