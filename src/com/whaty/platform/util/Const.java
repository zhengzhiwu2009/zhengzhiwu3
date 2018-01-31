package com.whaty.platform.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Const {

	// //靳希坤增加///
	public static int mustscore = 60;// 获得学分的成绩最低标准
	public static int mustmainscore = 60;// 主干课程通过分数
	public static int paperscore = 60;// 毕业的论文成绩最低标准
	public static int mustEnglishDegreeScore = 60;// 学位英语标准

	// TODO 靳希坤：课程分组name改为id
	public static String majorrequired_id = "_2";// "专业必修课";
	public static String majoralternation_id = "_3";// "专业选修课";
	public static String publicrequired_id = "_1";// "公共必修课";
	public static String publicalternation_id = "_4";// "公共选修课";
	// /////////////
	public static String credit = "^\\d{1,2}(\\.\\d)?$";
	public static String creditMessage = "输入格式：1至2到两位整数 0至1位小数";
	public static String credit_for_extjs = "regex:new RegExp(/" + credit + "/),regexText:'" + creditMessage + "',";

	public static String score = "^(([1-9]?\\d)(\\.\\d)?)$|^100$";
	public static String scoreMessage = "输入格式：0至100的整数，0至1位小数";
	public static String score_for_extjs = "regex:new RegExp(/" + score + "/),regexText:'" + scoreMessage + "',";

	public static String email = "^\\w+([-+.]\\w+)*@\\w+([-]\\w+)*(\\.\\w+([-]\\w+)*){1,3}$";
	public static String emailMessage = "邮箱格式输入不正确";
	public static String email_for_extjs = "regex:new RegExp(/" + email + "/),regexText:'" + emailMessage + "',";

	public static String phone = "^(\\d{11})$";
	public static String phoneMessage = "输入格式：11位数字";
	public static String phone_for_extjs = "regex:new RegExp(/" + phone + "/),regexText:'" + phoneMessage + "',";

	public static String fee = "^\\d{1,8}(\\.\\d{1,2})?$";
	public static String feeMessage = "金额、学时输入格式：1到8位整数 0到2位小数";
	public static String fee_for_extjs = "regex:new RegExp(/" + fee + "/),regexText:'" + feeMessage + "',";
	public static String _fee = "^(-)?\\d{1,8}(\\.\\d{1,2})?$"; // 金额输入格式：1到8位整数
	// 0到2位小数 可以是负数

	public static String AccountingInvoiceID = "^[A-Za-z]{2}\\d{8}$";
	public static String AccountingInvoiceMessage = "发票号格式：2位字母加8位数字";
	public static String AccountingInvoice_for_extjs = "regex:new RegExp(/" + AccountingInvoiceID + "/),regexText:'" + AccountingInvoiceMessage + "',";

	public static String scoreLine = "^((0|[1-9]\\d{0,2}))$";
	public static String scoreLineMessage = "输入格式：正整数(最多3位)或0";
	public static String scoreLine_for_extjs = "regex:new RegExp(/" + scoreLine + "/),regexText:'" + scoreLineMessage + "',";

	public static String sex = "^[\u7537|\u5973]{1,}$";
	public static String sexMessage = "输入格式：性别只能为男,女";
	public static String sex_for_extjs = "regex:new RegExp(/" + sex + "/),regexText:'" + sexMessage + "',";

	public static String edu = "^[\u521D\u4E2D|\u9AD8\u4E2D|\u804C\u9AD8|\u4E2D\u4E13|\u6280\u6821|\u5927\u4E13|\u5927\u672C|\u7855\u58EB|\u535A\u58EB]{1,}$";
	public static String eduMessage = "输入格式：学历只能填写：初中、高中、职高、中专、技校、大专、本科、硕士、博士";
	public static String edu_for_extjs = "regex:new RegExp(/" + edu + "/),regexText:'" + eduMessage + "',";

	public static String chinese = "^[\u0391-\uFFE5]+$";
	public static String chineseMessage = "输入格式：中文";
	public static String chinese_for_extjs = "regex:new RegExp(/" + chinese + "/),regexText:'" + chineseMessage + "',";

	public static String phone_number = "^(((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?)|(((\\(\\d{2,3}\\))|(\\d{3}\\-))?1[35]\\d{9})$";
	public static String phone_numberMessage = "请输入正确的办公电话(区号-电话号码-分机号)";
	public static String phone_number_for_extjs = "regex:new RegExp(/" + phone_number + "/),regexText:'" + phone_numberMessage + "',";

	public static String mobile = "^(\\+86)?0?1[3|5|8]\\d{9}$";
	public static String mobileMessage = "请输入正确的移动电话";
	public static String mobile_for_extjs = "regex:new RegExp(/" + mobile + "/),regexText:'" + mobileMessage + "',";

	public static String checkdate = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[1-9])|(1[0-2]))\\:([0-5][0-9])((\\s)|(\\:([0-5][0-9])\\s))([AM|PM|am|pm]{2,2})))?$";

	// 李冰增加

	// public static String telephone =
	// "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[3]\\d{9}$)";
	// // 固定电话
	// 电话号码，匹配格式： 11位手机号码 3-4位区号，7-8位直播号码，1－4位分机号
	// 如：12345678901、1234-12345678-1234
	public static String telephone = "^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
	// public static String telephone = "^((d{3,4})|d{3,4}-)?d{7,8}$";
	public static String telephoneMessage = "输入格式：3至4位区号-7至8位直播号码-1至4位分机号";
	public static String telephone_for_extjs = "regex:new RegExp(/" + telephone + "/),regexText:'" + telephoneMessage + "',";

	public static String zip = "^\\d{6}$"; // 邮编
	public static String zipMessage = "输入格式：6位数字";
	public static String zip_for_extjs = "regex:new RegExp(/" + zip + "/),regexText:'" + zipMessage + "',";

	// public static String coursecode = "^[A-Za-z]{2}\\d{4}$"; //
	public static String coursecode = "^[a-zA-Z0-9]{4,8}$";
	public static String coursecodeMessage = "输入格式：长度为4-8位字母数字组合";
	public static String coursecode_for_extjs = "regex:new RegExp(/" + coursecode + "/),regexText:'" + coursecodeMessage + "',";

	public static String code = "^\\d{3}$"; // 邮编
	public static String codeMessage = "输入格式：企业编号长度为3位";
	public static String code_for_extjs = "regex:new RegExp(/" + code + "/),regexText:'" + codeMessage + "',";

	public static String number = "^\\d+$"; // 数字
	public static String numberMessage = "输入格式：数字";
	public static String number_for_extjs = "regex:new RegExp(/" + number + "/),regexText:'" + numberMessage + "',";

	public static String matriculateNum = "^.*\\d{4}$"; // 以4位数字结尾
	public static String matriculateNumMessage = "输入格式：以4位数字结尾";
	public static String matriculateNum_for_extjs = "regex:new RegExp(/" + matriculateNum + "/),regexText:'" + matriculateNumMessage + "',";

	public static String oneNum = "^\\d$"; // 输入1位数字
	public static String oneNumMessage = "输入格式：1位数字";
	public static String oneNum_for_extjs = "regex:new RegExp(/" + oneNum + "/),regexText:'" + oneNumMessage + "',";

	public static String fiftyNum = "^((0|[1-9]\\d{0,49}))$"; // 输入最多50位数字
	public static String fiftyNumMessage = "输入格式：最多50位数字";
	public static String fiftyNum_for_extjs = "regex:new RegExp(/" + fiftyNum + "/),regexText:'" + fiftyNumMessage + "',";

	public static String twoNum = "^\\d{2}$"; // 输入1位数字
	public static String twoNumMessage = "输入格式：2位数字";
	public static String twoNum_for_extjs = "regex:new RegExp(/" + twoNum + "/),regexText:'" + twoNumMessage + "',";

	public static String oneTwoNum = "^\\d{1,2}$"; // 输入1-2位数字
	public static String oneTwoNumMessage = "输入格式：1~2位数字";
	public static String oneTwoNum_for_extjs = "regex:new RegExp(/" + oneTwoNum + "/),regexText:'" + oneTwoNumMessage + "',";

	// Lee 2014年6月18日 添加1-3位数字验证
	public static String oneThreeNum = "^\\d{1,3}$"; // 输入1-3位数字
	public static String oneThreeNumMessage = "输入格式：1~3位数字";
	public static String oneThreeNum_for_extjs = "regex:new RegExp(/" + oneThreeNum + "/),regexText:'" + oneThreeNumMessage + "',";

	// Lee 2014年11月9日 添加1-5位数字验证
	public static String oneFiveNum = "^\\d{1,5}$"; // 输入1-5位数字
	public static String oneFiveNumMessage = "输入格式：1~5位数字";
	public static String oneFiveNum_for_extjs = "regex:new RegExp(/" + oneFiveNum + "/),regexText:'" + oneFiveNumMessage + "',";

	public static String timeScale = "^([1-9]?\\d)$|^100$"; // 学时比例
	public static String timeScaleMessage = "输入格式：0至100的整数";
	public static String timeScale_for_extjs = "regex:new RegExp(/" + timeScale + "/),regexText:'" + timeScaleMessage + "',";

	public static String scale = "^(0\\.[1-9])$|^[01]$"; // 成绩比例
	public static String scaleMessage = "输入格式：0至1之间的1位小数或0或1";
	public static String scale_for_extjs = "regex:new RegExp(/" + scale + "/),regexText:'" + scaleMessage + "',";

	// 体验账号学员领用人：中英数字
	public static String pickUser = "^[\u0391-\uFFE5-a-zA-Z0-9]{0,25}$";
	public static String pickUserMessage = "输入格式：中英文或数字";
	public static String pickUser_for_extjs = "regex:new RegExp(/" + pickUser + "/),regexText:'" + pickUserMessage + "',";

	// 反馈标题
	public static String feedbackTitle = "^[\u0391-\uFFE5-a-zA-Z0-9]{0,25}$";
	public static String feedbackTitleMessage = "输入格式：中英文或数字";
	public static String feedbackTitle_for_extjs = "regex:new RegExp(/" + feedbackTitle + "/),regexText:'" + feedbackTitleMessage + "',";

	// 学员工号
	public static String jobNumber = "^[\u0391-\uFFE5-a-zA-Z0-9]{0,25}$";
	public static String jobNumberMessage = "输入格式：中英文或数字";
	public static String jobNumber_for_extjs = "regex:new RegExp(/" + jobNumber + "/),regexText:'" + jobNumberMessage + "',";

	public static String defaultPwd = "sac123";// 默认密码

	/**
	 * 比较日期的年月日 忽略时间 当 date2年月日 >= date1年月日 返回true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(Date date1, Date date2) {

		return date2.getTime() > date1.getTime() - 86400000;
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 产生0至max-1之间的随机整数
	 * 
	 * @param max
	 * @return
	 */
	public static int getRandInt(int max) {
		Random rd = new Random();
		return Math.abs(rd.nextInt() % max);
	}

	/**
	 * ********************************* 身份证验证开始
	 * ***************************************
	 */
	/**
	 * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
	 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
	 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
	 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
	 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
	 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
	 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
	 * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
	 * X 9 8 7 6 5 4 3 2
	 */

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public static String IDCardValidate(String IDStr) throws ParseException {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return errorInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
			errorInfo = "身份证生日不在有效范围。";
			return errorInfo;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return errorInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return errorInfo;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return errorInfo;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			// if (Ai.equals(IDStr) == false) { //Lee 2014年4月21日
			// 注释原比较方法,原方法当身份证最后一位为X时 Ai为小写IDStr为大写 验证会失败 因此替换
			if (Ai.equalsIgnoreCase(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return errorInfo;
			}
		} else {
			return "";
		}
		// =====================(end)=====================
		return "";
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	@SuppressWarnings("unchecked")
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ********************************* 身份证验证结束
	 * ***************************************
	 */

}
