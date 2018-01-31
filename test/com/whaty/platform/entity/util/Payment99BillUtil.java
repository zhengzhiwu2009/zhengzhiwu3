package com.whaty.platform.entity.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.rpc.ServiceException;
import md5.MD5Util;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import com._99bill.sandbox.apipay.services.gatewayOrderQuery.GatewayOrderQueryServiceLocator;
import com._99bill.www.gatewayapi.services.gatewayRefundQuery.GatewayRefundQuery;
import com._99bill.www.gatewayapi.services.gatewayRefundQuery.GatewayRefundQueryServiceLocator;
import com.bill99.seashell.domain.dto.gatewayquery.GatewayOrderDetail;
import com.bill99.seashell.domain.dto.gatewayquery.GatewayOrderQueryRequest;
import com.bill99.seashell.domain.dto.gatewayquery.GatewayOrderQueryResponse;
import com.bill99.seashell.domain.dto.gatewayrefundquery.GatewayRefundQueryRequest;
import com.bill99.seashell.domain.dto.gatewayrefundquery.GatewayRefundQueryResponse;
import com.bill99.seashell.domain.dto.gatewayrefundquery.GatewayRefundQueryResultDto;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PayGateway99BillConfig;
import com.whaty.platform.entity.bean.AutoRefund;
import com.whaty.platform.entity.bean.OnlineRefundInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.XMLParserUtil99BillPay;

/**
 * 快钱支付
 * 
 * @author Lee 2015-11-26
 */
public class Payment99BillUtil {

	HttpClient httpClient = new HttpClient();
	// 快钱支付配置
	private PayGateway99BillConfig payGateway99BillConfig;
	// 人民币账号
	// 本参数用来指定接收款项的快钱用户的人民币账号
	private String merchantAcctId;
	// 客户编号所对应的密钥。。在账户邮箱中获取
	private String key;
	// 字符集 固定值：1 1代表UTF-8
	private String inputCharset;
	// 查询接口版本 固定值：v2.0注意为小写字母
	private String version;
	// 签名类型 固定值：1 1代表MD5加密签名方式
	private String signType;
	// 查询方式 固定选择值：0、1
	// 0按商户订单号单笔查询（返回该订单信息）
	// 1按交易结束时间批量查询（只返回成功订单）
	private String queryType;
	// 查询模式 固定值：1 1代表简单查询（返回基本订单信息）
	private String queryMode;
	// 交易开始时间 数字串，一共14位
	// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
	private String startTime;// "20120319150000";
	// 交易结束时间 数字串，一共14位
	// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
	private String endTime;// "20120320142624";
	// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
	// 默认为1，表示第1页。
	private String requestPage;
	// 组合字符串。。必须按照此顺序组串
	private String signMsgVal;

	/** 退款相关参数* */
	// 退款接口版本号 目前固定为此值
	private String refundVersion;
	// 操作类型 固定值001 001代表下订单请求退款
	private String commandType;
	// 加密所需的key值，线上的话发到商户快钱账户邮箱里
	private String merchantKey;
	// 退费地址
	private String bankResultValue;

	/**
	 * 单个检查订单状态
	 * 
	 * @param merOrderId
	 *            订单号
	 */
	public List checkOrderState(String merOrderId) throws EntityException {
		// 初始化参数
		this.initPayGateway99BillConfig();

		String signMsgParam = "";
		// 字符集 固定值：1 1代表UTF-8
		signMsgParam = AppendParam(signMsgParam, "inputCharset", inputCharset);

		// 查询接口版本 固定值：v2.0注意为小写字母
		signMsgParam = AppendParam(signMsgParam, "version", version);

		// 签名类型 固定值：1 1代表MD5加密签名方式
		signMsgParam = AppendParam(signMsgParam, "signType", "1");// 配置文件中为4：PKI加密,下边用的MD5加密因此值为1

		// 人民币账号
		// 本参数用来指定接收款项的快钱用户的人民币账号
		signMsgParam = AppendParam(signMsgParam, "merchantAcctId", merchantAcctId);

		// 查询方式 固定选择值：0、1
		// 0按商户订单号单笔查询（返回该订单信息）
		// 1按交易结束时间批量查询（只返回成功订单）
		signMsgParam = AppendParam(signMsgParam, "queryType", queryType);

		// 查询模式 固定值：1 1代表简单查询（返回基本订单信息）
		signMsgParam = AppendParam(signMsgParam, "queryMode", queryMode);

		// 交易开始时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		signMsgParam = AppendParam(signMsgParam, "startTime", startTime);

		// 交易结束时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		signMsgParam = AppendParam(signMsgParam, "endTime", endTime);

		// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
		// 默认为1，表示第1页。
		signMsgParam = AppendParam(signMsgParam, "requestPage", requestPage);

		// 商户订单号 只允许使用字母、数字、- 、_,并以字母或数字开头,需要查询的某比订单
		signMsgParam = AppendParam(signMsgParam, "orderId", merOrderId);

		// 客户编号所对应的密钥。。在账户邮箱中获取
		signMsgParam = AppendParam(signMsgParam, "key", key);

		// 签名字符串
		String signMsg = "";
		try {
			signMsg = MD5Util.md5Hex(signMsgParam.getBytes("utf-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		GatewayOrderQueryRequest queryRequest = new GatewayOrderQueryRequest();
		// 字符集 固定值：1 1代表UTF-8
		queryRequest.setInputCharset(inputCharset);
		// 查询接口版本 固定值：v2.0注意为小写字母
		queryRequest.setVersion(version);
		// 签名类型 固定值：1 1代表MD5加密签名方式
		queryRequest.setSignType(Integer.parseInt("1"));// 配置文件中为4：PKI加密,下边用的MD5加密因此值为1
		// 人民币账号
		// 本参数用来指定接收款项的快钱用户的人民币账号
		queryRequest.setMerchantAcctId(merchantAcctId);
		// 查询方式 固定选择值：0、1
		// 0按商户订单号单笔查询（返回该订单信息）
		// 1按交易结束时间批量查询（只返回成功订单）
		queryRequest.setQueryType(Integer.parseInt(queryType));
		// 查询模式 固定值：1 1代表简单查询（返回基本订单信息
		queryRequest.setQueryMode(Integer.parseInt(queryMode));
		// 商户订单号 只允许使用字母、数字、- 、_,并以字母或数字开头,需要查询的某比订单
		queryRequest.setOrderId(merOrderId);
		// 交易开始时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		queryRequest.setStartTime(startTime);
		// 交易结束时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		queryRequest.setEndTime(endTime);
		queryRequest.setRequestPage(requestPage);
		// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
		// 默认为1，表示第1页。
		queryRequest.setSignMsg(signMsg);

		GatewayOrderQueryServiceLocator locator = new GatewayOrderQueryServiceLocator();
		GatewayOrderQueryResponse queryResponse = null;
		// String signMsgVal = "";

		// 自己签名字符串
		// String mysignMsg = "";
		List<GatewayOrderDetail> orderList = new ArrayList<GatewayOrderDetail>();
		try {
			queryResponse = locator.getgatewayOrderQuery().gatewayOrderQuery(queryRequest);
			if (null != queryResponse) {
				// 错误编码
				if (queryResponse.getErrCode() != null && !"".equals(queryResponse.getErrCode())) {
					String error = queryResponse.getErrCode();
					String orders = "";
					if (null != queryResponse.getOrders()) {
						for (GatewayOrderDetail odr : queryResponse.getOrders()) {
							orders += odr.getOrderId() + ",";
						}
					}
					if ("10001".equals(error)) {
						System.out.println("COSError：" + merOrderId + "网关版本号不正确或不存在");
					} else if ("10002".equals(error)) {
						System.out.println("COSError：" + merOrderId + "签名类型不正确或不存在");
					} else if ("10003".equals(error)) {
						System.out.println("COSError：" + merOrderId + "人民币账号格式不正确");
					} else if ("10004".equals(error)) {
						System.out.println("COSError：" + merOrderId + "查询方式不正确或不存在");
					} else if ("10005".equals(error)) {
						System.out.println("COSError：" + merOrderId + "查询模式不正确或不存在");
					} else if ("10006".equals(error)) {
						System.out.println("COSError：" + merOrderId + "查询开始时间不正确");
					} else if ("10007".equals(error)) {
						System.out.println("COSError：" + merOrderId + "查询结束时间不正确");
					} else if ("10008".equals(error)) {
						System.out.println("COSError：" + merOrderId + "商户订单号格式不正确");
					} else if ("10010".equals(error)) {
						System.out.println("COSError：" + merOrderId + "字符集输入不正确");
					} else if ("11001".equals(error)) {
						System.out.println("COSError：" + merOrderId + "开始时间不能在结束时间之后");
					} else if ("11002".equals(error)) {
						System.out.println("COSError：" + merOrderId + "允许查询的时间段最长为30天");
					} else if ("11003".equals(error)) {
						System.out.println("COSError：" + merOrderId + "签名字符串不匹配");
					} else if ("11004".equals(error)) {
						System.out.println("COSError：" + merOrderId + "查询结束时间晚于当前时间");
					} else if ("20001".equals(error)) {
						System.out.println("COSError：" + merOrderId + "该账号不存在或已注销");
					} else if ("20002".equals(error)) {
						System.out.println("COSError：" + merOrderId + "签名字符串不匹配，您无权查询");
					} else if ("30001".equals(error)) {
						System.out.println("COSError：" + merOrderId + "系统繁忙，请稍后再查询");
					} else if ("30002".equals(error)) {
						System.out.println("COSError：" + merOrderId + "查询过程异常，请稍后再试");
					} else if ("31001".equals(error)) {
						System.out.println("COSError：" + merOrderId + "本时间段内无交易记录");
					} else if ("31002".equals(error)) {
						System.out.println("COSError：" + merOrderId + "本时间段内无成功交易记录");
					} else if ("31003".equals(error)) {
						// System.out.println("COSError：" + merOrderId +
						// "商户订单号不存在");
					} else if ("31004".equals(error)) {
						System.out.println("COSError：" + merOrderId + "查询结果超出能允许的文件范围");
					} else if ("31005".equals(error)) {
						System.out.println("COSError：" + merOrderId + "订单号对应的交易支付未成功");
					} else {
						// 00000：未知错误
						System.out.println("COSError：" + orders + "未知错误：" + error);
					}
				} else {
					GatewayOrderDetail[] orders = queryResponse.getOrders();
					for (int i = 0; i < orders.length; i++) {
						GatewayOrderDetail detail = orders[i];
						if (merOrderId.equals(detail.getOrderId()) && "10".equals(detail.getPayResult())) {
							// 支付成功的订单
							orderList.add(detail);
						} else {
							// 支付异常的订单
							System.out.println("COSError：支付异常订单[交易号]：" + detail.getDealId());
						}
					}
					return orderList;
				}
			} else {
				throw new EntityException("COSError：订单验证异常");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 多订单对账
	 */
	public List<GatewayOrderDetail> checkOrderList(String startTime, String endTime, String payBank) throws EntityException {
		// 初始化参数
		this.initPayGateway99BillConfig();

		String signMsgParam = "";
		// 字符集 固定值：1 1代表UTF-8
		signMsgParam = AppendParam(signMsgParam, "inputCharset", inputCharset);

		// 查询接口版本 固定值：v2.0注意为小写字母
		signMsgParam = AppendParam(signMsgParam, "version", version);

		// 签名类型 固定值：1 1代表MD5加密签名方式
		signMsgParam = AppendParam(signMsgParam, "signType", "1");// 配置文件中为4：PKI加密,下边用的MD5加密因此值为1

		// 人民币账号
		// 本参数用来指定接收款项的快钱用户的人民币账号
		signMsgParam = AppendParam(signMsgParam, "merchantAcctId", merchantAcctId);

		// 查询方式 固定选择值：0、1
		// 0按商户订单号单笔查询（返回该订单信息）
		// 1按交易结束时间批量查询（只返回成功订单）
		signMsgParam = AppendParam(signMsgParam, "queryType", queryType);

		// 查询模式 固定值：1 1代表简单查询（返回基本订单信息）
		signMsgParam = AppendParam(signMsgParam, "queryMode", queryMode);

		// 交易开始时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		signMsgParam = AppendParam(signMsgParam, "startTime", startTime);

		// 交易结束时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		signMsgParam = AppendParam(signMsgParam, "endTime", endTime);

		// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
		// 默认为1，表示第1页。
		signMsgParam = AppendParam(signMsgParam, "requestPage", requestPage);

		// 客户编号所对应的密钥。。在账户邮箱中获取
		signMsgParam = AppendParam(signMsgParam, "key", key);

		// 签名字符串
		String signMsg = "";
		try {
			signMsg = MD5Util.md5Hex(signMsgParam.getBytes("utf-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		GatewayOrderQueryRequest queryRequest = new GatewayOrderQueryRequest();
		// 字符集 固定值：1 1代表UTF-8
		queryRequest.setInputCharset(inputCharset);
		// 查询接口版本 固定值：v2.0注意为小写字母
		queryRequest.setVersion(version);
		// 签名类型 固定值：1 1代表MD5加密签名方式
		queryRequest.setSignType(Integer.parseInt("1"));// 配置文件中为4：PKI加密,下边用的MD5加密因此值为1
		// 人民币账号
		// 本参数用来指定接收款项的快钱用户的人民币账号
		queryRequest.setMerchantAcctId(merchantAcctId);
		// 查询方式 固定选择值：0、1
		// 0按商户订单号单笔查询（返回该订单信息）
		// 1按交易结束时间批量查询（只返回成功订单）
		queryRequest.setQueryType(Integer.parseInt(queryType));
		// 查询模式 固定值：1 1代表简单查询（返回基本订单信息
		queryRequest.setQueryMode(Integer.parseInt(queryMode));
		// 交易开始时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		queryRequest.setStartTime(startTime);
		// 交易结束时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		queryRequest.setEndTime(endTime);
		queryRequest.setRequestPage(requestPage);
		// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
		// 默认为1，表示第1页。
		queryRequest.setSignMsg(signMsg);

		GatewayOrderQueryServiceLocator locator = new GatewayOrderQueryServiceLocator();
		GatewayOrderQueryResponse queryResponse = null;
		// String signMsgVal = "";

		// 自己签名字符串
		// String mysignMsg = "";
		List<GatewayOrderDetail> orderList = new ArrayList<GatewayOrderDetail>();
		try {
			queryResponse = locator.getgatewayOrderQuery().gatewayOrderQuery(queryRequest);
			if (null != queryResponse) {
				// 错误编码
				if (queryResponse.getErrCode() != null && !"".equals(queryResponse.getErrCode())) {
					String error = queryResponse.getErrCode();
					String orders = "";
					for (GatewayOrderDetail odr : queryResponse.getOrders()) {
						orders += odr.getOrderId() + ",";
					}
					if ("10001".equals(error)) {
						System.out.println("COLError：" + orders + "网关版本号不正确或不存在");
					} else if ("10002".equals(error)) {
						System.out.println("COLError：" + orders + "签名类型不正确或不存在");
					} else if ("10003".equals(error)) {
						System.out.println("COLError：" + orders + "人民币账号格式不正确");
					} else if ("10004".equals(error)) {
						System.out.println("COLError：" + orders + "查询方式不正确或不存在");
					} else if ("10005".equals(error)) {
						System.out.println("COLError：" + orders + "查询模式不正确或不存在");
					} else if ("10006".equals(error)) {
						System.out.println("COLError：" + orders + "查询开始时间不正确");
					} else if ("10007".equals(error)) {
						System.out.println("COLError：" + orders + "查询结束时间不正确");
					} else if ("10008".equals(error)) {
						System.out.println("COLError：" + orders + "商户订单号格式不正确");
					} else if ("10010".equals(error)) {
						System.out.println("COLError：" + orders + "字符集输入不正确");
					} else if ("11001".equals(error)) {
						System.out.println("COLError：" + orders + "开始时间不能在结束时间之后");
					} else if ("11002".equals(error)) {
						System.out.println("COLError：" + orders + "允许查询的时间段最长为30天");
					} else if ("11003".equals(error)) {
						System.out.println("COLError：" + orders + "签名字符串不匹配");
					} else if ("11004".equals(error)) {
						System.out.println("COLError：" + orders + "查询结束时间晚于当前时间");
					} else if ("20001".equals(error)) {
						System.out.println("COLError：" + orders + "该账号不存在或已注销");
					} else if ("20002".equals(error)) {
						System.out.println("COLError：" + orders + "签名字符串不匹配，您无权查询");
					} else if ("30001".equals(error)) {
						System.out.println("COLError：" + orders + "系统繁忙，请稍后再查询");
					} else if ("30002".equals(error)) {
						System.out.println("COLError：" + orders + "查询过程异常，请稍后再试");
					} else if ("31001".equals(error)) {
						System.out.println("COLError：" + orders + "本时间段内无交易记录");
					} else if ("31002".equals(error)) {
						System.out.println("COLError：" + orders + "本时间段内无成功交易记录");
					} else if ("31003".equals(error)) {
						// System.out.println("COLError：" + orders +
						// "商户订单号不存在");
					} else if ("31004".equals(error)) {
						System.out.println("COLError：" + orders + "查询结果超出能允许的文件范围");
					} else if ("31005".equals(error)) {
						System.out.println("COLError：" + orders + "订单号对应的交易支付未成功");
					} else {
						System.out.println("COLError：" + orders + "未知错误：" + error);
					}
				} else {
					GatewayOrderDetail[] orders = queryResponse.getOrders();
					for (int i = 0; i < orders.length; i++) {
						GatewayOrderDetail detail = orders[i];
						orderList.add(detail);
					}
					return orderList;
				}
			} else {
				throw new EntityException("COLError：订单验证异常");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 批量检查订单退费状态
	 * 
	 */
	public List<OnlineRefundInfo> checkRefoundState(String refoundId, String start, String end) throws EntityException {
		// 原来没这个参数，因此放空
		String orderId = "";
		// 不用这个参数，根据时间段查询一系列订单，而不是单独查询某一个订单，因此放空""
		refoundId = "";

		// 初始化参数
		this.initPayGateway99BillConfig();

		refoundId = StringUtils.defaultString(refoundId);
		start = StringUtils.defaultString(start);
		end = StringUtils.defaultString(end);

		GatewayRefundQueryResponse result = null;

		String message = "";
		// 查询接口版本号 固定值：v2.0 注意为小写字母
		if (version != "") {
			message = message + "version=" + version;
		}
		// 签名类型：1:MD5 4：PKI
		message = message + "&signType=1";
		// 人民币账号
		if (merchantAcctId != "") {
			message = message + "&merchantAcctId=" + merchantAcctId;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		String startDateTime = format.format(new Date(now.getTime() - 2 * 24 * 60 * 60 * 1000));
		String endDateTime = format.format(now);
		// 退款生成时间起点 数字串，一共8位 格式为YYYYMMDD 例如20100811 startDate不endDate间隔不能超过3天。
		if (startDateTime != "") {
			message = message + "&startDate=" + startDateTime;
		}
		// 退款生成时间终点 数字串，一共8位 格式为YYYYMMDD 例如20100811 startDate不endDate间隔不能超过3天。
		if (endDateTime != "") {
			message = message + "&endDate=" + endDateTime;
		}
		// 客户方的批次号
		String customerBatchId = "";
		if (customerBatchId != "") {
			message = message + "&customerBatchId=" + customerBatchId;
		}
		// 退款订单号，原来没有这个参数，放空""
		if (orderId != "") {
			message = message + "&orderId=" + orderId;
		}
		// 请求记彔集 页码
		if (requestPage != "") {
			message = message + "&requestPage=" + requestPage;
		}
		// 原商家订单号
		if (refoundId != "") {
			message = message + "&rOrderId=" + refoundId;
		}
		// 退款交易号
		String seqId = "";
		if (seqId != "") {
			message = message + "&seqId=" + seqId;
		}
		// 交易状态 数字串： 0代表进行中 1代表成功 2代表失败
		String status = "";
		if (status != "") {
			message = message + "&status=" + status;
		}
		// 网关接口密钥
		if (key != "") {
			message = message + "&key=" + key;
		}
		String hash = MD5Util.md5Hex(message).toUpperCase();
		String signMsg = hash.toUpperCase();

		GatewayRefundQueryServiceLocator gl = new GatewayRefundQueryServiceLocator();
		GatewayRefundQuery grQuery = null;
		try {
			grQuery = gl.getgatewayRefundQuery();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		GatewayRefundQueryRequest dto = new GatewayRefundQueryRequest();
		dto.setVersion(version);
		dto.setMerchantAcctId(merchantAcctId);
		dto.setSignType("1");
		dto.setRequestPage(requestPage);
		dto.setStartDate(startDateTime);
		dto.setEndDate(endDateTime);
		dto.setROrderId(refoundId);
		dto.setCustomerBatchId(customerBatchId);
		dto.setOrderId(orderId);
		dto.setSeqId(seqId);
		dto.setStatus(status);
		dto.setSignMsg(signMsg);
		try {
			result = grQuery.query(dto);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		List<OnlineRefundInfo> list = new ArrayList<OnlineRefundInfo>();
		if (null != result) {
			if (null != result.getErrCode()) {
				if ("10002".equals(result.getErrCode())) {
					System.out.println("CRSError：不支持的的的返回类型");
				} else if ("10003".equals(result.getErrCode())) {
					System.out.println("CRSError：不合法的页面返回地址");
				} else if ("10004".equals(result.getErrCode())) {
					System.out.println("CRSError：不合法的后台返回地址");
				} else if ("10005".equals(result.getErrCode())) {
					System.out.println("CRSError：不支持的网关接口版本");
				} else if ("10006".equals(result.getErrCode())) {
					System.out.println("CRSError：商家mechantAcctId非法");
				} else if ("10007".equals(result.getErrCode())) {
					System.out.println("CRSError：输入的查询时间段违法");
				} else if ("10008".equals(result.getErrCode())) {
					System.out.println("CRSError：不支持的签名类型");
				} else if ("10009".equals(result.getErrCode())) {
					System.out.println("CRSError：解密验签失败");
				} else if ("10010".equals(result.getErrCode())) {
					System.out.println("CRSError：版本号不能为空");
				} else if ("10011".equals(result.getErrCode())) {
					System.out.println("CRSError：不支持的日期类型");
				} else if ("10012".equals(result.getErrCode())) {
					System.out.println("CRSError：没有数据");
				} else if ("10013".equals(result.getErrCode())) {
					System.out.println("CRSError：查询出错");
				} else if ("10014".equals(result.getErrCode())) {
					System.out.println("CRSError：帐户号为空");
				} else if ("10015".equals(result.getErrCode())) {
					System.out.println("CRSError：验签字段不能为空");
				} else if ("10016".equals(result.getErrCode())) {
					System.out.println("CRSError：签名类型不能为空");
				} else if ("10017".equals(result.getErrCode())) {
					System.out.println("CRSError：退款查询时间不能为空");
				} else if ("10018".equals(result.getErrCode())) {
					System.out.println("CRSError：额外输出参数不正确或不存在");
				} else {
					System.out.println("CRSError：未知错误" + result.getErrCode());
				}
			} else {
				GatewayRefundQueryResultDto[] results = result.getResults();
				for (GatewayRefundQueryResultDto reDto : results) {
					OnlineRefundInfo ori = new OnlineRefundInfo();
					// 退款流水号
					ori.setRefundId(reDto.getSequenceId());
					// 商户ID
					ori.setMerchantId(merchantAcctId);// 退款商户ID没有01因此截取
					// 快钱订单
					ori.setPayOrderId(reDto.getOrderId());
					// 商户订单
					ori.setMerOrderId(reDto.getROrderId());
					// 金额
					// ori.setAmount(reDto.getOwnerFee());//
					// reDto.getOwnerFee()快钱费用，是否放在此处不确定，因此注释
					// 退款金额
					ori.setRefundAmount(reDto.getOrderAmout());
					// 快钱：0代表进行中 1代表成功 2代表失败
					String reState = reDto.getStatus();
					// 退款状态 0新申请 1处理中 2拒绝 3成功 4平台拒绝 5撤销申请
					if (null != reState && "0".equals(reState)) {
						ori.setState("1");// 处理中
					} else if (null != reState && "1".equals(reState)) {
						ori.setState("3");// 成功
					} else if (null != reState && "2".equals(reState)) {
						ori.setState("2");// 拒绝
					}
					UserSession us = null;
					try {
						us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
					} catch (Exception e) {
						us = null;
					}
					if (null != us && !"".equals(us)) {
						// 开始状态 0：商户 1：平台
						ori.setStartFlag("0");
					} else {
						ori.setStartFlag("1");
					}
					// 退款结束时间
					ori.setApplyDate(reDto.getLastUpdateTime());
					list.add(ori);
				}
			}
		}
		return list;
	}

	/**
	 * 退费申请
	 */
	public AutoRefund doRefundApply(String merOrderId, String appUser, String cause, String amountSum) throws EntityException {
		this.initPayGateway99BillConfig();

		String macVal = "";
		// 商户编号，线上的话改成你们自己的商户编号的，发到商户的注册快钱账户邮箱的
		String maid = (null == merchantAcctId || "".equals(merchantAcctId)) ? "" : merchantAcctId.substring(0, 11);
		macVal = RefundAppendParam(macVal, "merchant_id", maid);
		// 退款接口版本号 目前固定为此值
		macVal = RefundAppendParam(macVal, "version", refundVersion);
		// 操作类型 固定值001 001代表下订单请求退款commandType
		macVal = RefundAppendParam(macVal, "command_type", commandType);
		// 原商户订单号
		macVal = RefundAppendParam(macVal, "orderid", merOrderId);
		// 退款金额，整数或小数，小数位为2位 以人民币元为单位
		Double d = Double.parseDouble(amountSum);
		DecimalFormat df = new DecimalFormat("######0.00");
		String tkAmount = df.format(d);
		macVal = RefundAppendParam(macVal, "amount", tkAmount);
		// 退款提交时间 数字串，一共14位 格式为：年[4 位]月[2 位]日[2 位]时[2 位]分[2 位]秒[2位]
		String postdate = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		macVal = RefundAppendParam(macVal, "postdate", postdate);
		// 退款流水号 字符串
		String txOrder = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		macVal = RefundAppendParam(macVal, "txOrder", txOrder);
		// 加密所需的key值，线上的话发到商户快钱账户邮箱里
		macVal = RefundAppendParam(macVal, "merchant_key", merchantKey);
		// 生成加密签名串
		String mac = "";
		try {
			mac = MD5Util.md5Hex(macVal.getBytes("utf-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			PostMethod postMethod = new PostMethod(bankResultValue);// 退费申请
			postMethod.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
			// 商户编号，线上的话改成你们自己的商户编号的，发到商户的注册快钱账户邮箱的
			postMethod.addParameter("merchant_id", maid);
			// 退款接口版本号 目前固定为此值
			postMethod.addParameter("version", refundVersion);
			// 操作类型 固定值001 001代表下订单请求退款commandType
			postMethod.addParameter("command_type", commandType);
			// 原商户订单号
			postMethod.addParameter("orderid", merOrderId);
			// 退款金额，整数或小数，小数位为2位 以人民币元为单位
			postMethod.addParameter("amount", tkAmount);
			// 退款提交时间 数字串，一共14位 格式为：年[4 位]月[2 位]日[2 位]时[2 位]分[2 位]秒[2位]
			postMethod.addParameter("postdate", postdate);
			// 退款流水号 字符串
			postMethod.addParameter("txOrder", txOrder);
			// 加密所需的key值，线上的话发到商户快钱账户邮箱里
			// postMethod.addParameter("merchant_key", merchantKey);
			// 加密签名串
			postMethod.addParameter("mac", mac);
			String bankResultValue = "";
			try {
				bankResultValue = httpResult(postMethod);
			} catch (EntityException e) {
				// 连接失败
				throw e;
			}
			AutoRefund autoRefund = XMLParserUtil99BillPay.getAutoRefund(bankResultValue);

			if (null != autoRefund) {
				if (null != autoRefund.getErrorCode() && !"".equals(autoRefund.getErrorCode())
						&& !"y".equalsIgnoreCase(autoRefund.getStatus())) {
					throw new EntityException(autoRefund.getErrorCode());
				} else {
					return autoRefund;
				}
			} else {
				throw new EntityException("申请失败，平台无返回数据。");
			}
		} catch (Exception e) {
			throw new EntityException(e.getMessage());
		}

	}

	/**
	 * 建立http连接返回结果
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
	 */
	public void parseXMLText(String xml) {
		/*
		 * int order1 = xml.indexOf("<merOrderId>"); int order2 = xml.indexOf("</merOrderId>");
		 * this.merorderid = xml.substring(order1 + 12, order2); int x1 =
		 * xml.indexOf("<status>"); int x2 = xml.indexOf("</status>"); String
		 * status = xml.substring(x1 + 8, x2); this.state = status;
		 * 
		 * System.out.println("***************************" + status);
		 */
	}

	/**
	 * 初始化参数
	 */
	public void initPayGateway99BillConfig() {
		String prefix = Thread.currentThread().getContextClassLoader().getResource("").getPath();// 获得路径
		String configdir = prefix.substring(0, prefix.length() - 8).replace("/", File.separator).replace("\\", File.separator) + "config"
				+ File.separator;
		this.payGateway99BillConfig = new PayGateway99BillConfig(configdir);
		try {
			payGateway99BillConfig.getConfig();
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		// 字符集 固定值：1 1代表UTF-8
		this.inputCharset = payGateway99BillConfig.getInputCharset();

		// 查询接口版本 固定值：v2.0注意为小写字母
		this.version = payGateway99BillConfig.getVersion();

		// 签名类型 固定值：1 1代表MD5加密签名方式
		this.signType = payGateway99BillConfig.getSignType();

		// 人民币账号
		// 本参数用来指定接收款项的快钱用户的人民币账号
		this.merchantAcctId = payGateway99BillConfig.getMerchantAcctId();

		// 查询方式 固定选择值：0、1
		// 0按商户订单号单笔查询（返回该订单信息）
		// 1按交易结束时间批量查询（只返回成功订单）
		this.queryType = payGateway99BillConfig.getQueryType();

		// 查询模式 固定值：1 1代表简单查询（返回基本订单信息）
		this.queryMode = payGateway99BillConfig.getQueryMode();

		// 交易开始时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		this.startTime = payGateway99BillConfig.getStartTime();

		// 交易结束时间 数字串，一共14位
		// 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]，例如：20071117020101
		this.endTime = payGateway99BillConfig.getEndTime();

		// 请求记录集页码 在查询结果数据总量很大时，快钱会将支付结果分多次返回。本参数表示商户需要得到的记录集页码。
		// 默认为1，表示第1页。
		this.requestPage = payGateway99BillConfig.getRequestPage();

		// 客户编号所对应的密钥。。在账户邮箱中获取
		this.key = payGateway99BillConfig.getKey();

		/** 退款参数* */
		// 退款接口版本号 目前固定为此值
		this.refundVersion = payGateway99BillConfig.getRefundVersion();
		// 操作类型 固定值001 001代表下订单请求退款
		this.commandType = payGateway99BillConfig.getCommandType();
		// 加密所需的key值，线上的话发到商户快钱账户邮箱里
		this.merchantKey = payGateway99BillConfig.getMerchantKey();
		// 退费地址
		this.bankResultValue = payGateway99BillConfig.getBankResultValue();
	}

	public PayGateway99BillConfig getPayGateway99BillConfig() {
		return payGateway99BillConfig;
	}

	public void setPayGateway99BillConfig(PayGateway99BillConfig payGateway99BillConfig) {
		this.payGateway99BillConfig = payGateway99BillConfig;
	}

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRequestPage() {
		return requestPage;
	}

	public void setRequestPage(String requestPage) {
		this.requestPage = requestPage;
	}

	public String getSignMsgVal() {
		return signMsgVal;
	}

	public void setSignMsgVal(String signMsgVal) {
		this.signMsgVal = signMsgVal;
	}

	public String getRefundVersion() {
		return refundVersion;
	}

	public void setRefundVersion(String refundVersion) {
		this.refundVersion = refundVersion;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	private String AppendParam(String ReturnStr, String ParamId, String ParamValue) {
		if (!ReturnStr.equals("")) {
			if (!ParamValue.equals("")) {
				ReturnStr = ReturnStr + "&" + ParamId + "=" + ParamValue;
			}
		} else {
			if (!ParamValue.equals("")) {
				ReturnStr = ParamId + "=" + ParamValue;
			}
		}
		return ReturnStr;
	}

	public String RefundAppendParam(String returns, String paramId, String paramValue) {
		if (returns != "") {
			if (paramValue != "") {
				returns += paramId + "=" + paramValue;
			}

		} else {
			if (paramValue != "") {
				returns = paramId + "=" + paramValue;
			}
		}
		return returns;
	}

	public String getBankResultValue() {
		return bankResultValue;
	}

	public void setBankResultValue(String bankResultValue) {
		this.bankResultValue = bankResultValue;
	}
}
