package com.whaty.platform.sso.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.RegStudent;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class SsoRegistAction extends MyBaseAction {

	private String operateresult;
	private String operateresult1;// 用来区分个人和集体
	private String re;// 用来记录用户名
	private PeBzzStudent peBzzStudent;
	private String password;
	private String cardno;
	private String textResult;
	private String pename;
	private List peEnterpriseList;// 企业List
	private List<EnumConst> studentTypeList;// 学生类型
	private EnumConstService enumConstService;
	private String userType;
	private String resultMessage;
	private String year;
	private String month;
	private String date;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	/**
	 * 待审核的注册用户列表加载
	 * 
	 * @author linjie
	 */
	@Override
	public void initGrid() {
		boolean canUpdate = true;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (!"131AF5EC87836928E0530100007F9F54".equals(us.getSsoUser().getPePriRole().getId())) {
			this.getGridConfig().setTitle("非从业注册审核");
		} else {
			this.getGridConfig().setTitle("学员注册审核");
		}
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, false, true, "TextField");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName", true, canUpdate, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("性别"), "gender", false, canUpdate, true, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("国籍"), "cardType", true, canUpdate, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo", true, canUpdate, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, canUpdate, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("职务"), "position", true, canUpdate, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", false, canUpdate, true, "TextField", true, 40);
		this.getGridConfig().addColumn(this.getText("学历"), "education", false, canUpdate, true, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("出生日期"), "birthdayDate", false, canUpdate, true, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("机构名称"), "peEnterpriseName", true, canUpdate, true, "TextField", true, 10);
		this.getGridConfig().addMenuFunction(this.getText("审核通过"), "CheckState.true");
		this.getGridConfig().addMenuFunction(this.getText("拒绝"), "CheckState.false");
	}

	/**
	 * 待审核的注册用户列表加载
	 * 
	 * @author linjie
	 */
	@Override
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		// Lee start 2014年1月7日 添加SELECT * FROM 原SQL无法添加查询
		sqlBuffer
				.append(" SELECT * FROM (SELECT T.ID        AS ID, T.REG_NO    AS REGNO, T.TRUE_NAME AS TRUENAME, T.GENDER    AS GENDER, T.CARD_TYPE AS CARDTYPE, T.CARD_NO   AS CARDNO, T.WORK, T.POSITION, T.FOLK      AS FOLK, T.EDUCATION AS EDUCATION, T.BIRTHDAY  AS BIRTHDAYDATE, P.NAME      AS PEENTERPRISENAME FROM PE_BZZ_STUDENT T INNER JOIN ENUM_CONST E ON T.STUDENT_TYPE = E.ID AND E.NAMESPACE = 'FlagStudentType' AND E.CODE = '0' INNER JOIN ENUM_CONST N ON T.CHECK_STATE = N.ID AND N.NAMESPACE = 'CheckState' AND N.CODE = '0' INNER JOIN PE_ENTERPRISE P ON T.ENTERPRISE_ID = P.ID INNER JOIN PE_ENTERPRISE_MANAGER PEM ON P.ID = PEM.FK_ENTERPRISE_ID AND PEM.LOGIN_ID = '"
						+ us.getSsoUser().getLoginId() + "')  WHERE 1 = 1 ");
		// Lee end 添加) WHERE 1 = 1 原SQL无法添加查询
		try {
			// Lee start 2014年1月7日 原方法查询条件无作用
			this.setSqlCondition(sqlBuffer);
			// Lee end
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 审核处理方法
	 * 
	 * @author linjie
	 */
	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzStudent.class);
				if (action.equals("CheckState.true")) {
					tempdc.add(Restrictions.in("id", ids));
					List<PeBzzStudent> sslist = new ArrayList<PeBzzStudent>();
					sslist = this.getGeneralService().getList(tempdc);
					Iterator<PeBzzStudent> iterator = sslist.iterator();
					while (iterator.hasNext()) {
						PeBzzStudent peBzzStudent1 = iterator.next();
						peBzzStudent1.setEnumConstByCheckState(this.getEnumConstService().getByNamespaceCode("CheckState", "1"));
						String sql = "SELECT 1 FROM ENUM_CONST WHERE CODE = 'V' AND ID = (SELECT ENTYPE FROM PE_ENTERPRISE WHERE ID = (SELECT FK_ENTERPRISE_ID FROM PE_BZZ_STUDENT WHERE ID = '"
								+ peBzzStudent1.getId() + "'))";
						List list = this.getGeneralService().getBySQL(sql);
						if (null != list && list.size() > 0) {// 监管的学员审核
																// 修改学员的资格类型
							EnumConst ec = this.getEnumConstService().getByNamespaceCode("FlagQualificationsType", "100");
							peBzzStudent1.setEnumConstByFlagQualificationsType(ec);
						}
						PeEnterprise peEnterprise = new PeEnterprise();
						peEnterprise.setId(peBzzStudent1.getEnterpriseId());
						peBzzStudent1.setPeEnterprise(peEnterprise);
						this.getGeneralService().update(peBzzStudent1);
						String peTypeSql = "SELECT EC.CODE FROM PE_ENTERPRISE PE JOIN ENUM_CONST EC ON PE.ENTYPE = EC.ID WHERE PE.ID = '" + peBzzStudent1.getEnterpriseId() + "'";
						List peTypes = this.getGeneralService().getBySQL(peTypeSql);
						String peType = "";
						if (peTypes != null && peTypes.size() > 0) {
							peType = (String) peTypes.get(0);
						}
						if ("V".equals(peType)) {
							SsoUser su = peBzzStudent1.getSsoUser();
							su.setEnumConstByFlagIsvalid(this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1"));
							this.getGeneralService().update(su);
						}
					}
					msg = "审核通过";

				} else if (action.equals("CheckState.false")) {
					tempdc.add(Restrictions.in("id", ids));
					List<PeBzzStudent> sslist = new ArrayList<PeBzzStudent>();
					sslist = this.getGeneralService().getList(tempdc);
					Iterator<PeBzzStudent> iterator = sslist.iterator();
					while (iterator.hasNext()) {
						PeBzzStudent peBzzStudent1 = iterator.next();
						peBzzStudent1.setEnumConstByCheckState(this.getEnumConstService().getByNamespaceCode("CheckState", "2"));
						this.getGeneralService().update(peBzzStudent1);
					}
					msg = "审核未通过";
				}
				map.put("success", "true");
				map.put("info", msg + "共有" + ids.length + "条记录操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", msg + "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少有一条记录被选择");
			return map;
		}
		return map;
	}

	/**
	 * 跳转到注册页
	 * 
	 * @return
	 * @author linjie
	 */
	public String registUser() {
		initStudentTypeList();
		initPeEnterpriseList();
		return "registUser";
	}

	public String registuser() {
		return this.registUser();
	}

	/**
	 * 判断身份证号是否可用
	 * 
	 * @return
	 * @author linjie
	 */
	public String isUsed() {
		String textResult = "";
		String sql = "select id from pe_bzz_student where card_no='" + cardno + "'";
		List list;
		try {
			list = this.getGeneralService().getBySQL(sql);
			if (list.size() > 0) {
				textResult = "1";
			} else {
				textResult = "2";
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.print(textResult);
		writer.flush();
		writer.close();
		return "registUser";
	}

	/**
	 * 注册提交
	 * 
	 * @return
	 * @author linjie
	 */
	public String registSucess() {
		Map map = new HashMap();
		try {
			String cardType = this.peBzzStudent.getCardType().trim();
			if ("中国".equals(cardType) && (this.peBzzStudent.getCardNo().trim() != null && !"".equals(this.peBzzStudent.getCardNo().trim()))
					&& !"-".equals(this.peBzzStudent.getCardNo().trim())) {
				String vStr = Const.IDCardValidate(this.peBzzStudent.getCardNo().trim());// 检查证件号格式
				if (vStr != null && !"".equals(vStr)) {
					this.setMsg(vStr);
					return "regFailed";
				}
			}
			String sql = "";
			List list = null;
			if (!"-".equals(this.peBzzStudent.getCardNo().trim())) {
				sql = "select * from pe_bzz_student s where " + "trim(upper(s.card_no))=trim(upper('" + this.peBzzStudent.getCardNo().trim() + "'))"
						+ " or trim(upper(s.copy_carid))=trim(upper('" + this.peBzzStudent.getCardNo().trim() + "'))";
				list = this.getGeneralService().getBySQL(sql);
			}
			if (list != null && list.size() > 0) {
				this.setMsg("该身份号已注册，请使用首页“忘记账号？”功能找回！");
				return "regFailed";
			} else {
				this.peBzzStudent.setCardNo(this.peBzzStudent.getCardNo().toUpperCase().trim());// 身份证全大写
				if ("其它".equals(peBzzStudent.getEnterpriseId())) {
					this.peBzzStudent.setEnumConstByFlagStudentType(this.getEnumConstService().getByNamespaceCode("FlagStudentType", "1"));
					if (this.pename != null && !"".equals(this.pename)) {
						peBzzStudent.setEnterpriseName(this.pename);
					}
					operateresult1 = "1";
				} else {
					operateresult1 = "2";
					this.peBzzStudent.setEnumConstByFlagStudentType(this.getEnumConstService().getByNamespaceCode("FlagStudentType", "0"));
				}
				// String enterprise_sql = "select name from pe_enterprise where
				// id = '" + peBzzStudent.getEnterpriseId() + "'";
				// List name_list =
				// this.getGeneralService().getBySQL(enterprise_sql);
				// String enterprise_name = "";
				// if(name_list != null && name_list.size() > 0) {
				// enterprise_name = (String) name_list.get(0);
				// }
				this.re = this.codenum();
				// if (!"".equals(this.year) && this.year != null &&
				// !"${param.year}".equals(this.year)) {// 如果年不为空则插入生日
				// if (!"${param.month}".equals(this.month) &&
				// Integer.parseInt(this.month) < 10)
				// this.month = '0' + this.month;
				// if (Integer.parseInt(this.date) < 10)
				// this.date = '0' + this.date;
				// String birth = this.getYear() + '-' + this.getMonth() + '-' +
				// this.getDate();
				// java.text.SimpleDateFormat sdf = new
				// java.text.SimpleDateFormat("yyyy-MM-dd");
				// peBzzStudent.setBirthdayDate(sdf.parse(birth));
				// }
				peBzzStudent.setRegNo(re);
				peBzzStudent.setName(re + "/" + this.peBzzStudent.getTrueName());
				peBzzStudent.setEnumConstByFlagPhotoConfirm(this.getEnumConstService().getDefaultByNamespace("FlagPhotoConfirm"));
				peBzzStudent.setEnumConstByFlagIsGoodStu(this.getEnumConstService().getDefaultByNamespace("FlagIsGoodStu"));
				peBzzStudent.setEnumConstByCheckState(this.getEnumConstService().getDefaultByNamespace("CheckState"));
				peBzzStudent.setEnumConstByIsEmployee(this.getEnumConstService().getDefaultByNamespace("IsEmployee"));
				peBzzStudent.setEnumConstByFlagQualificationsType(this.getEnumConstService().getDefaultByNamespace("FlagQualificationsType"));
				SsoUser ssoUser = new SsoUser();
				ssoUser.setLoginId(peBzzStudent.getRegNo());
				// ssoUser.setPassword(MyUtil.md5(this.getPassword()));
				// ssoUser.setPassword(this.getPassword());
				ssoUser.setPasswordMd5(MyUtil.md5(this.getPassword()));
				ssoUser.setPasswordBk(MyUtil.md5(this.getPassword()));
				ssoUser.setLoginNum((long) 0);
				ssoUser.setAmount("0");
				ssoUser.setSumAmount("0");
				PePriRole pePriRole = new PePriRole();
				// if("J000".equals(peBzzStudent.getEnterpriseId())) {
				// pePriRole.setId("ff808081493288bd0149335225b90036");
				// pePriRole.setName("监管机构内训学习");
				// } else {
				pePriRole.setId("0");
				pePriRole.setName("学生");
				// }
				ssoUser.setPePriRole(pePriRole);
				EnumConst enumConstByFlagIsvalid = null;
				String peTypeSql = "SELECT EC.CODE FROM PE_ENTERPRISE PE JOIN ENUM_CONST EC ON PE.ENTYPE = EC.ID WHERE PE.ID = '" + peBzzStudent.getEnterpriseId() + "'";
				List peTypes = this.getGeneralService().getBySQL(peTypeSql);
				String peType = "";
				if (peTypes != null && peTypes.size() > 0) {
					peType = (String) peTypes.get(0);
				}
				if ("V".equals(peType.toUpperCase())) {
					enumConstByFlagIsvalid = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "0");
				} else {
					enumConstByFlagIsvalid = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1");
				}
				ssoUser.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);
				peBzzStudent.setSsoUser(ssoUser);
				PeEnterprise peEnterprise = new PeEnterprise();
				// if("J000".equals(peBzzStudent.getEnterpriseId())) {
				// peEnterprise.setId("J000");
				// } else {
				peEnterprise.setId("0000");
				// }
				peBzzStudent.setPeEnterprise(peEnterprise);
				PeBzzStudent regStu = (PeBzzStudent) this.getGeneralService().save(peBzzStudent);
				// if("402880a91da9032f011da905da070003".equals(this.peBzzStudent.getEnumConstByFlagStudentType().getId())){
				// operateresult1 = "1";
				// }else{
				// operateresult1 = "2";
				// }
				if (regStu != null) {
					operateresult = "1";
				} else {
					operateresult = "2";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			operateresult = "2";
			return "registSuccess";
		}
		return "registSuccess";
	}

	/**
	 * 自动生成系统编号
	 * 
	 * @return
	 * @author linjie
	 */
	private String codenum() {

		String tempcode = "";
		List temList = new ArrayList();
		try {
			// if(name.indexOf("监管") != -1) {
			// temList = this.getGeneralService().getBySQL("SELECT
			// JG_SEQ.NEXTVAL FROM DUAL");
			// } else {
			temList = this.getGeneralService().getBySQL("SELECT S_PE_BZZ_STUDENT.NEXTVAL FROM DUAL");
			// }
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DecimalFormat df = new DecimalFormat("0000000");
		// if(name.indexOf("监管") != -1) {
		// tempcode = "jg" + df.format(temList.get(0)).toString();
		// } else {
		tempcode = "sace" + df.format(temList.get(0)).toString();
		// }
		return tempcode;

		// String tempcode = null;
		// DetachedCriteria codedc
		// =DetachedCriteria.forClass(PeBzzStudent.class);
		// codedc.setProjection(Projections.max("regNo"));
		// codedc.add(Restrictions.ilike("regNo", "sace", MatchMode.START));
		// List list;
		// try {
		// list = this.getGeneralService().getList(codedc);
		// if(list.size()==0||list.get(0)==null){
		// tempcode = "sace"+"000000"+1;
		// }else{
		// String temp = "1"+ list.get(0).toString().substring(4,11);
		// temp = Integer.parseInt(temp)+1+"";
		// tempcode = "sace"+temp.trim().substring(1, temp.length());
		// }
		// } catch (EntityException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * 注册页机构列表List
	 * 
	 * @author linjie
	 */
	public void initPeEnterpriseList() {
		String sql = "SELECT PE.ID, PE.NAME, EC.CODE FROM PE_ENTERPRISE PE JOIN ENUM_CONST EC ON EC.ID = PE.ENTYPE WHERE PE.FK_PARENT_ID IS NULL";
		if ("reg".equals(type)) {
			sql += " AND EC.CODE = 'V'";
		} else if ("pub".equals(type)) {
			sql += " AND EC.CODE != 'V'";
		}
		sql += " ORDER BY NAME";
		try {
			peEnterpriseList = new ArrayList();
			List list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					String[] str = new String[2];
					str[0] = obj[0] + "-" + obj[2];
					str[1] = obj[1] + "";
					// peEnterpriseList.add(obj[0] + "-" + obj[2]);
					// peEnterpriseList.add(obj[1]);
					peEnterpriseList.add(str);
				}
			}
			// this.peEnterpriseList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 学员状态List
	 * 
	 * @author linjie
	 */
	public void initStudentTypeList() {
		DetachedCriteria dcTemp = DetachedCriteria.forClass(EnumConst.class);
		dcTemp.add(Restrictions.eq("namespace", "FlagStudentType"));
		try {
			this.studentTypeList = this.getGeneralService().getList(dcTemp);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = RegStudent.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/regist";

	}

	public List getPeEnterpriseList() {
		return peEnterpriseList;
	}

	public void setPeEnterpriseList(List peEnterpriseList) {
		this.peEnterpriseList = peEnterpriseList;
	}

	public List<EnumConst> getStudentTypeList() {
		return studentTypeList;
	}

	public void setStudentTypeList(List<EnumConst> studentTypeList) {
		this.studentTypeList = studentTypeList;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public String getOperateresult1() {
		return operateresult1;
	}

	public void setOperateresult1(String operateresult1) {
		this.operateresult1 = operateresult1;
	}

	public String getRe() {
		return re;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getTextResult() {
		return textResult;
	}

	public void setTextResult(String textResult) {
		this.textResult = textResult;
	}

	public String getPename() {
		return pename;
	}

	public void setPename(String pename) {
		this.pename = pename;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String findUserAccount() {
		return "findUserAccount";
	}

	public String findGroupUserAccount() {
		return "findGroupUserAccount";
	}

	/**
	 * 学员找回用户账号
	 * 
	 * @return
	 * @author linjie
	 */
	public String findBackUserAccount() {
		try {
			String sql = "";
			List<Object[]> resultList = new ArrayList();
			sql = "select t.reg_no,ec.name\n" + "  from pe_bzz_student t, enum_const ec\n" + " where t.is_employee = ec.id(+)";
			if (!StringUtils.defaultString(this.getPename()).equals("")) {
				// sql += " and substr( t.name, instr(t.name, '/') + 1 ) = '" +
				// this.getPename() + "' ";
				sql += " and t.true_name  = '" + this.getPename().trim() + "' ";
			} else {
				operateresult = "1";
				this.setResultMessage("对不起,请输入正确的姓名!");
				return "findBackUserAccount";
			}
			if (!StringUtils.defaultString(this.getCardno()).equals("")) {
				sql += " and t.card_no  = '" + this.getCardno().trim() + "' ";
			} else {
				operateresult = "1";
				this.setResultMessage("对不起,请输入正确的证件号!");
				return "findBackUserAccount";
			}
			resultList = this.getGeneralService().getBySQL(sql);
			if (resultList.size() == 0) {
				operateresult = "1";
				this.setResultMessage("对不起,没有符合您查询条件的账号!");
				return "findBackUserAccount";
			} else if (resultList.size() == 1) {
				operateresult = "1";
				this.setResultMessage("您好,您的账号为:" + (resultList.get(0)[0].toString()) + "&nbsp;&nbsp;&nbsp;从业资格：" + resultList.get(0)[1].toString());
				return "findBackUserAccount";
			} else {
				operateresult = "1";
				String re = "您好,您的账号为:<br>";
				for (int i = 0; i < resultList.size(); i++) {
					re += (i + 1) + "、登陆ID：" + (resultList.get(i)[0].toString()) + "&nbsp;&nbsp;&nbsp;从业资格：" + resultList.get(i)[1].toString() + "<br>";
				}
				this.setResultMessage(re);
				return "findBackUserAccount";
			}
		} catch (Exception e) {
			// e.printStackTrace();
			operateresult = "2";
			this.setResultMessage("对不起,查找出错!");
			return "findBackUserAccount";
		}
	}

	/**
	 * 集体用户找回用户账号
	 * 
	 * @return
	 * @author linjie
	 */
	public String findBackGroupUserAccount() {
		try {
			String sql = "";
			List resultList = new ArrayList();
			sql = " select t.login_id from pe_enterprise_manager t where 1=1 ";
			if (!StringUtils.defaultString(this.getPename()).equals("")) {
				sql += " and t.name  = '" + this.getPename() + "' ";
			}
			if (!StringUtils.defaultString(this.getCardno()).equals("")) {
				sql += " and t.email  = '" + this.getCardno() + "' ";
			}
			resultList = this.getGeneralService().getBySQL(sql);
			if (resultList.size() == 0) {
				operateresult = "1";
				this.setResultMessage("对不起,没有符合您查询条件的账号!");
				return "findGroupUserAccount";
			} else if (resultList.size() == 1) {
				operateresult = "1";
				this.setResultMessage("您好,您的账号为:" + (resultList.get(0).toString()));
				return "findGroupUserAccount";
			} else {
				operateresult = "1";
				this.setResultMessage("查找到满足条件的多个账号,请进一步精确查询条件!");
				return "findGroupUserAccount";
			}
		} catch (Exception e) {
			e.printStackTrace();
			operateresult = "2";
			this.setResultMessage("对不起,查找出错!");
			return "findGroupUserAccount";
		}
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getPeType(String id) {
		String sql = "SELECT EC.ID FROM PE_ENTERPRISE PE JOIN ENUM_CONST EC ON PE.ENTYPE = EC.ID WHERE PE.ID = '" + id + "'";
		System.out.println("===============" + sql);
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0) {
				if ("12B99EA2EF64506AE0530100007F3844".equals(list.get(0))) {
					return "true";
				} else {
					return "false";
				}
			} else {
				return "false";
			}
		} catch (EntityException e) {
			e.printStackTrace();
			return "false";
		}
	}

}
