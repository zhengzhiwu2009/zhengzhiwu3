package com.whaty.platform.entity.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.PaymentUtil;
import com.whaty.util.Crypto;
import com.whaty.util.Pkipair;

public class CheckAction extends MyBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3173329912317245025L;

	protected static final Log logger = LogFactory.getLog("EntityLogger");

	private String action = "index";
	private String merchantid;
	private String merorderid;
	private String amountsum;
	private String currencytype;
	private String subject;
	private String state;
	private String paybank;
	private String banksendtime;
	private String merrecvtime;
	private String interface1;
	private String merkey;
	private String mac;
	private String s = "";
	private String e = "";
	private int rtnOK;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * 
	 * @return
	 */
	public void checking() {
		PaymentUtil pu = new PaymentUtil();
		pu.initPayGatewayConfig();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String rev = "merchantid=" + this.merchantid + "&merorderid=" + this.merorderid + "&amountsum=" + this.amountsum + "&currencytype="
				+ this.currencytype + "&subject=" + this.subject + "&state=" + this.state + "&paybank=" + this.paybank + "&banksendtime="
				+ this.banksendtime + "&merrecvtime=" + this.merrecvtime + "&interface=" + this.interface1 + "&merkey=" + pu.getMerkey();// 取得密钥加密
		String mac_rec = Crypto.GetMessageDigest(rev, "MD5");
		// System.out.println("---rev:"+rev +"--mac:" +this.getMac());
		if (!mac_rec.equals(this.getMac())) {
			// mac验证失败
			out.write("success=false");
		} else {
			try {
				this.getGeneralService().checkOnlineOrder(merorderid);
				out.write("success=true");
			} catch (Exception e) {
				// e.printStackTrace();
				out.write("success=false");
			}
		}
		out.flush();
		out.close();
		System.out.println("----订单信息接口--order" + this.merorderid + "--confirmed----");
		// return "msg";
	}

	/**
	 * 接收快钱支付反馈信息
	 * 
	 * @return
	 */
	public String checking99Bill() {
		// 数字串 不提交订单时的快钱账号保持一致
		String remerchantAcctId = ServletActionContext.getRequest().getParameter("merchantAcctId");
		// 固定值：v2.0 不提交订单时的网关版本号保持一致
		String reversion = ServletActionContext.getRequest().getParameter("version");
		// 固定选择值：1 1表示快钱支付网关网页是中文显示
		String relanguage = ServletActionContext.getRequest().getParameter("language");
		// 固定值：4 不提交订单时的签名类型保持一致
		String resignType = ServletActionContext.getRequest().getParameter("signType");
		// 固定选择值：00、10、11、12、13、14、15、17、19、21、22 不提交订单时的支付方式保持一致
		String repayType = ServletActionContext.getRequest().getParameter("payType");
		// 字符串 返回用户在实际支付时所使用的银行代码
		String rebankId = ServletActionContext.getRequest().getParameter("bankId");
		// 提交订单时的商户订单号保持一致
		String reorderId = ServletActionContext.getRequest().getParameter("orderId");
		// 数字串 不提交订单时的商户订单提交时间保持一致
		String reorderTime = ServletActionContext.getRequest().getParameter("orderTime");
		// 整型数字 以分为单位。比方10元，提交时金额庒为1000 不提交订单时的商户订单金额保持一致
		String reorderAmount = ServletActionContext.getRequest().getParameter("orderAmount");
		// 数字串 该交易在快钱系统中对庒的交易号
		String redealId = ServletActionContext.getRequest().getParameter("dealId");
		// 数字串 该交易在银行支付时对庒的交易号，如果丌是通过银行卡支付，则为空
		String rebankDealId = ServletActionContext.getRequest().getParameter("bankDealId");
		// 数字串 快钱对交易迚行处理的时间,格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
		String redealTime = ServletActionContext.getRequest().getParameter("dealTime");
		// 整型数字 返回在使用优惠券等情况后，用户实际支付的金额 以分为单位。比方10元，提交时金额庒为1000
		String repayAmount = ServletActionContext.getRequest().getParameter("payAmount");
		// 整型数字 快钱收叏商户的手续费，单位为分。
		String refee = ServletActionContext.getRequest().getParameter("fee");
		// 字符串 不提交订单时的扩展字段1保持一致
		String reext1 = ServletActionContext.getRequest().getParameter("ext1");
		// 字符串 不提交订单时的扩展字段2保持一致
		String reext2 = ServletActionContext.getRequest().getParameter("ext2");
		// 10：支付成功
		String repayResult = ServletActionContext.getRequest().getParameter("payResult");
		// 失败时返回的错诨代码，可以为空。 详细资料见下文参考资料。
		String reerrCode = ServletActionContext.getRequest().getParameter("errCode");
		String signMsg = ServletActionContext.getRequest().getParameter("signMsg");
		// 对亍所有值不为空的参数及对庒值，挄照如上顺序及如下规则组成字符串
		// DSA戒RSA方式：参数1={参数1}&参数2={参数2}&……&参数n={参数n}然后迚行快钱证书加密形成密文后迚行1024位的Base64转码。
		String merchantSignMsgVal = "";
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", remerchantAcctId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", reversion);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", relanguage);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", resignType);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", repayType);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", rebankId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", reorderId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", reorderTime);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", reorderAmount);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", redealId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", rebankDealId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", redealTime);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", repayAmount);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", refee);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", reext1);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", reext2);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", repayResult);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", reerrCode);
		Pkipair pki = new Pkipair();
		boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg);
		int rtnOKValue = 0;
		if (flag) {
			if ("10".equalsIgnoreCase(repayResult)) {
				try {
					rtnOKValue = 1;
					this.setMsg("success");
					EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");
					DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
					orderDc.add(Restrictions.eq("seq", reorderId));
					// 只执行未到账的订单，避免定时任务与网页触发同时进行而出现错误。
					orderDc.add(Restrictions.eq("enumConstByFlagPaymentState", enumConstByFlagPaymentState));
					PeBzzOrderInfo pbo = null;
					List orderList = this.getGeneralService().getList(orderDc);
					if (orderList != null && orderList.size() > 0) {
						pbo = (PeBzzOrderInfo) orderList.get(0);
						if (pbo != null && Double.parseDouble(pbo.getAmount()) == Double.parseDouble(this.fromFenToYuan(reorderAmount))) {
							// TODO YCL 2016-03-15 下面注释方法调用为原代码
							//this.getGeneralService().confirmOnlineOrder(reorderId);
							
							//TODO YCL 2016-03-15 到账方法重载：增加支付方式类型 star
							this.getGeneralService().confirmOnlineOrder(reorderId, repayType);
							// end
							System.out.println(pbo.getSeq() + "支付成功：" + reorderId);
						} else {
							System.out.println("ERROR订单：" + reorderId + "金额不符--" + pbo.getAmount() + "-"
									+ this.fromFenToYuan(reorderAmount) + "--" + (new Date().toString()));
						}
					}
				} catch (EntityException e) {
					this.setMsg("false");
					System.out.println("ERROR确认订单失败，请联系管理员：" + reorderId);
					e.printStackTrace();
				}
			} else {
				this.setMsg("false");
				System.out.println("ERROR支付失败：" + reorderId);
			}
		} else {
			this.setMsg("error");
		}
		this.setRtnOK(rtnOKValue);
		return "99BillMsg";
	}

	public String reconciliation() {
		try {
			if (s != null && e != null && s.length() > 9 && e.length() > 9) {
				s = s.substring(0, 10);
				e = e.substring(0, 10);
				String fmt = "\\d{4}-\\d{2}-\\d{2}";
				boolean ex = Pattern.matches(fmt, s);
				boolean ex1 = Pattern.matches(fmt, e);
				if (ex && ex1) {
					this.getGeneralService().checkReconciliation(s, e, "");
				} else {
					this.setMsg("日期格式错误");
					return "msg";
				}
			} else {
				this.setMsg("日期格式错误");
				return "msg";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "msg";
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/test/check";

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

	public String getCurrencytype() {
		return currencytype;
	}

	public void setCurrencytype(String currencytype) {
		this.currencytype = currencytype;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getInterface() {
		return interface1;
	}

	public void setInterface(String interface1) {
		this.interface1 = interface1;
	}

	public String getMerkey() {
		return merkey;
	}

	public void setMerkey(String merkey) {
		this.merkey = merkey;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
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

	public int getRtnOK() {
		return rtnOK;
	}

	public void setRtnOK(int rtnOK) {
		this.rtnOK = rtnOK;
	}
}
