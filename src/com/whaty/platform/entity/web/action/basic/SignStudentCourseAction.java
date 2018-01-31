package com.whaty.platform.entity.web.action.basic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSignUpOrder;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SignStudentCourseAction extends MyBaseAction {

	private String signUpId;

	private int totalCount; // 总条数
	private int startIndex = 0; // 开始数
	private int pageSize = 10; // 页面显示数
	private Page page;

	private String typeflag;
	private List stucoulist;

	private String regno;
	private String stuname;
	private String classno;
	private String classname;

	private String totalfee;
	private String totalbm;
	private String totalhour;

	public String getTotalfee() {
		return totalfee;
	}

	public void setTotalfee(String totalfee) {
		this.totalfee = totalfee;
	}

	public String getTotalbm() {
		return totalbm;
	}

	public void setTotalbm(String totalbm) {
		this.totalbm = totalbm;
	}

	public String getTotalhour() {
		return totalhour;
	}

	public void setTotalhour(String totalhour) {
		this.totalhour = totalhour;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getClassno() {
		return classno;
	}

	public void setClassno(String classno) {
		this.classno = classno;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Override
	public void initGrid() {
		if ("addstu".equals(this.typeflag)) {
			this.getGridConfig().setCapability(false, false, false, true, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("系统编号"), "REG_NO", true, false, true, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("姓名"), "TRUE_NAME", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("证件号码"), "CARD_NO", true, false, true, "TextField", false, 100);
			ColumnConfig xkstatus = new ColumnConfig(this.getText("资格类型"), "Combobox_ZIGE_NAME", true, false, true, "TextField", false, 100, "");
			String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest'";
			xkstatus.setComboSQL(sql7);
			this.getGridConfig().addColumn(xkstatus);
			this.getGridConfig().addColumn(this.getText("从事业务"), "WORK", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("职务"), "POSITION", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("所在部门"), "DEPARTMENT", true, false, true, "TextField", false, 100);
			this.getGridConfig()
					.addMenuScript(
							this.getText("添加学员"),
							"{var m = grid.getSelections();  "
									+ "if(m.length > 0){	"
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		var ss =  m[i].get('id');"
									+ "		jsonData = jsonData + ss+',' ;"
									+ "	}                       "
									+ "	document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/signUpOnline_saveSelectStudentInfo.action' method='post' name='formx1' style='display:none'><input type=hidden name=stuids value='\"+jsonData+\"' ><input type=hidden name=id value='"
									+ this.getSignUpId() + "' ><input type=hidden name=typeflag value='" + typeflag + "' ></form>\";" + "	document.formx1.submit();"
									+ "	document.getElementById('user-defined-content').innerHTML=\"\";" + "} else {                    "
									+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
		} else if ("addcou".equals(this.typeflag)) {
			this.getGridConfig().setCapability(false, false, false, true, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "CODE", true, false, true, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("课程名称"), "NAME", true, false, true, "TextField", false, 100);

			ColumnConfig xzstatus = new ColumnConfig(this.getText("课程性质"), "Combobox_COURSETYPENAME", true, false, true, "TextField", false, 100, "");
			String sqlxz = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
			xzstatus.setComboSQL(sqlxz);
			this.getGridConfig().addColumn(xzstatus);

			this.getGridConfig().addColumn(this.getText("课程金额"), "PRICE", false, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("主讲人"), "TEACHER", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("课程学时"), "TIME", false, false, true, "TextField", false, 100);

			ColumnConfig status2 = new ColumnConfig(this.getText("大纲分类"), "Combobox_COURSE_ITEM_NAME", true, false, false, "TextField", false, 100, "");
			String sqlx2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
			status2.setComboSQL(sqlx2);
			this.getGridConfig().addColumn(status2);

			ColumnConfig status3 = new ColumnConfig(this.getText("内容属性"), "Combobox_CONTENT_PROPERTY_NAME", true, false, false, "TextField", false, 100, "");
			String sqlx3 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
			status3.setComboSQL(sqlx3);
			this.getGridConfig().addColumn(status3);

			ColumnConfig status5 = new ColumnConfig(this.getText("业务分类"), "Combobox_COURSECATEGORY_NAME", true, false, false, "TextField", false, 100, "");
			String sqlx5 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
			status5.setComboSQL(sqlx5);
			this.getGridConfig().addColumn(status5);

			ColumnConfig status6 = new ColumnConfig(this.getText("建议学习人群"), "Combobox_FS_NAME", true, false, false, "TextField", false, 100, "");
			String sqlx6 = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
			status6.setComboSQL(sqlx6);
			this.getGridConfig().addColumn(status6);

			this.getGridConfig()
					.addMenuScript(
							this.getText("添加课程"),
							"{var m = grid.getSelections();  "
									+ "if(m.length > 0){	"
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		var ss =  m[i].get('id');"
									+ "		jsonData = jsonData + ss+',' ;"
									+ "	}                       "
									+ "	document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/signUpOnline_saveSelectCourseInfo.action' method='post' name='formx1' style='display:none'><input type=hidden name=dcourseids value='\"+jsonData+\"' ><input type=hidden name=id value='"
									+ this.getSignUpId() + "' ><input type=hidden name=typeflag value='" + typeflag + "' ></form>\";" + "	document.formx1.submit();"
									+ "	document.getElementById('user-defined-content').innerHTML=\"\";" + "} else {                    "
									+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
		} else if ("viewstu".equals(this.typeflag)) {
			this.getGridConfig().setCapability(false, false, false, true, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("系统编号"), "REG_NO", true, false, true, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("姓名"), "TRUE_NAME", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("证件号码"), "CARD_NO", true, false, true, "TextField", false, 100);

			ColumnConfig xkstatus = new ColumnConfig(this.getText("资格类型"), "Combobox_ZIGE_NAME", true, false, true, "TextField", false, 100, "");
			String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest'";
			xkstatus.setComboSQL(sql7);

			this.getGridConfig().addColumn(xkstatus);
			this.getGridConfig().addColumn(this.getText("从事业务"), "WORK", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("职务"), "POSITION", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("所在部门"), "DEPARTMENT", true, false, true, "TextField", false, 100);
			ColumnConfig zftatus = new ColumnConfig(this.getText("支付状态"), "Combobox_P_STATUS", true, false, true, "TextField", false, 100, "");
			String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagElectivePayStatus'";
			zftatus.setComboSQL(sql);
			this.getGridConfig().addColumn(zftatus);

			this.getGridConfig()
					.addMenuScript(
							this.getText("删除学员"),
							"{var m = grid.getSelections();  "
									+ "if(m.length > 0){	"
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		var ss =  m[i].get('id');"
									+ "		jsonData = jsonData + ss+',' ;"
									+ "	}                       "
									+ "	document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/signUpOnline_deleteSelectStudentInfo.action' method='post' name='formx1' style='display:none'><input type=hidden name=stuids value='\"+jsonData+\"' ><input type=hidden name=id value='"
									+ this.getSignUpId() + "' ><input type=hidden name=typeflag value='" + typeflag + "' ></form>\";" + "	document.formx1.submit();"
									+ "	document.getElementById('user-defined-content').innerHTML=\"\";" + "} else {                    "
									+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
		} else if ("viewcour".equals(this.typeflag)) {
			this.getGridConfig().setCapability(false, false, false, true, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "CODE", true, false, true, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("课程名称"), "NAME", true, false, true, "TextField", false, 100);

			ColumnConfig xzstatus = new ColumnConfig(this.getText("课程性质"), "Combobox_COURSETYPENAME", true, false, true, "TextField", false, 100, "");
			String sqlxz = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
			xzstatus.setComboSQL(sqlxz);
			this.getGridConfig().addColumn(xzstatus);

			this.getGridConfig().addColumn(this.getText("课程金额"), "PRICE", false, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("主讲人"), "TEACHER", true, false, true, "TextField", false, 100);
			this.getGridConfig().addColumn(this.getText("课程学时"), "TIME", false, false, true, "TextField", false, 100);

			ColumnConfig status2 = new ColumnConfig(this.getText("大纲分类"), "Combobox_COURSE_ITEM_NAME", true, false, false, "TextField", false, 100, "");
			String sqlx2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
			status2.setComboSQL(sqlx2);
			this.getGridConfig().addColumn(status2);

			ColumnConfig status3 = new ColumnConfig(this.getText("内容属性"), "Combobox_CONTENT_PROPERTY_NAME", true, false, false, "TextField", false, 100, "");
			String sqlx3 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
			status3.setComboSQL(sqlx3);
			this.getGridConfig().addColumn(status3);

			ColumnConfig status5 = new ColumnConfig(this.getText("业务分类"), "Combobox_COURSECATEGORY_NAME", true, false, false, "TextField", false, 100, "");
			String sqlx5 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
			status5.setComboSQL(sqlx5);
			this.getGridConfig().addColumn(status5);

			ColumnConfig status6 = new ColumnConfig(this.getText("建议学习人群"), "Combobox_FS_NAME", true, false, false, "TextField", false, 100, "");
			String sqlx6 = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest'";
			status6.setComboSQL(sqlx6);
			this.getGridConfig().addColumn(status6);

			this.getGridConfig()
					.addMenuScript(
							this.getText("删除课程"),
							"{var m = grid.getSelections();  "
									+ "if(m.length > 0){	"
									+ "	var jsonData = '';       "
									+ "	var bId = '';       "
									+ "	for(var i = 0, len = m.length; i < len; i++){"
									+ "		var ss =  m[i].get('id');"
									+ "		jsonData = jsonData + ss+',' ;"
									+ "	}                       "
									+ "	document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/signUpOnline_deleteSelectCourseInfo.action' method='post' name='formx1' style='display:none'><input type=hidden name=dcourseids value='\"+jsonData+\"' ><input type=hidden name=id value='"
									+ this.getSignUpId() + "' ><input type=hidden name=typeflag value='" + typeflag + "' ></form>\";" + "	document.formx1.submit();"
									+ "	document.getElementById('user-defined-content').innerHTML=\"\";" + "} else {                    "
									+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
		} else if ("topay".equals(this.typeflag)) {

		}

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeSignUpOrder.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/signStudentCourse";
	}

	public String toConfirm() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			String sql = "SELECT PBS.True_Name,PBS.REG_NO,PBS.CARD_NO,PBTC.NAME,PBTC.CODE FROM PE_SIGNUP_ORDER PSO "
					+ " JOIN PR_SIGNUP_ORDER_STUDENT PSOS ON PSOS.FK_SIGNUP_ORDER_ID=PSO.ID " + " JOIN PR_SIGNUP_ORDER_COURSE PSOC ON PSOC.FK_SIGNUP_ORDER_ID=PSO.ID  "
					+ " JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.ID=PSOC.COURSE_ID " + " JOIN PE_BZZ_STUDENT PBS ON PBS.ID=PSOS.STUDENT_ID " + " WHERE PSO.ID='" + this.getSignUpId()
					+ "' ";
			// 获取总费用
			String feesql = "SELECT SUM(PBTC.PRICE) AS TOTALPRICE FROM PE_SIGNUP_ORDER PSO " + " JOIN PR_SIGNUP_ORDER_STUDENT PSOS ON PSOS.FK_SIGNUP_ORDER_ID=PSO.ID "
					+ " JOIN PR_SIGNUP_ORDER_COURSE PSOC ON PSOC.FK_SIGNUP_ORDER_ID=PSO.ID  " + " JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.ID=PSOC.COURSE_ID "
					+ " JOIN PE_BZZ_STUDENT PBS ON PBS.ID=PSOS.STUDENT_ID " + " WHERE PSO.ID='" + this.getSignUpId() + "' ";

			// 获取总报名人数
			String stusql = "SELECT COUNT(DISTINCT PSOS.STUDENT_ID) AS TOTALSTU FROM PE_SIGNUP_ORDER PSO "
					+ " JOIN PR_SIGNUP_ORDER_STUDENT PSOS ON PSOS.FK_SIGNUP_ORDER_ID=PSO.ID " + " JOIN PR_SIGNUP_ORDER_COURSE PSOC ON PSOC.FK_SIGNUP_ORDER_ID=PSO.ID  "
					+ " JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.ID=PSOC.COURSE_ID " + " JOIN PE_BZZ_STUDENT PBS ON PBS.ID=PSOS.STUDENT_ID " + " WHERE PSO.ID='" + this.getSignUpId()
					+ "' ";
			// 获取总学时数
			String timesql = "SELECT SELECT SUM(PBTC.TIME) FROM PE_SIGNUP_ORDER PSO " + " JOIN PR_SIGNUP_ORDER_STUDENT PSOS ON PSOS.FK_SIGNUP_ORDER_ID=PSO.ID "
					+ " JOIN PR_SIGNUP_ORDER_COURSE PSOC ON PSOC.FK_SIGNUP_ORDER_ID=PSO.ID  " + " JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.ID=PSOC.COURSE_ID "
					+ " JOIN PE_BZZ_STUDENT PBS ON PBS.ID=PSOS.STUDENT_ID " + " WHERE PSO.ID='" + this.getSignUpId() + "' ";

			// 系统编号
			if (this.regno != null && !"".equalsIgnoreCase(this.regno)) {
				sql += " AND PBS.REG_NO ='" + this.regno + "'";
				feesql += " AND PBS.REG_NO ='" + this.regno + "'";
				stusql += " AND PBS.REG_NO ='" + this.regno + "'";
				timesql += " AND PBS.REG_NO ='" + this.regno + "'";
			}
			// 学员姓名
			if (this.stuname != null && !"".equalsIgnoreCase(this.stuname)) {
				sql += " AND PBS.TRUE_NAME ='" + this.stuname + "'";
				feesql += " AND PBS.TRUE_NAME ='" + this.stuname + "'";
				stusql += " AND PBS.TRUE_NAME ='" + this.stuname + "'";
				timesql += " AND PBS.TRUE_NAME ='" + this.stuname + "'";
			}
			// 课程编号
			if (this.classno != null && !"".equalsIgnoreCase(this.classno)) {
				sql += " AND PBTC.CODE ='" + this.classno + "'";
				feesql += " AND PBTC.CODE ='" + this.classno + "'";
				stusql += " AND PBTC.CODE ='" + this.classno + "'";
				timesql += " AND PBTC.CODE ='" + this.classno + "'";
			}
			// 课程名称
			if (this.classname != null && !"".equalsIgnoreCase(this.classname)) {
				sql += " AND PBTC.NAME ='" + this.classname + "'";
				feesql += " AND PBTC.NAME ='" + this.classname + "'";
				stusql += " AND PBTC.NAME ='" + this.classname + "'";
				timesql += " AND PBTC.NAME ='" + this.classname + "'";
			}

			sql += " AND PEM.CODE = '" + us.getLoginId() + "' AND PSO.SIGN_ID='" + this.getSignUpId() + "' ORDER BY PBS.REG_NO";
			feesql += " AND PEM.CODE = '" + us.getLoginId() + "' AND PSO.SIGN_ID='" + this.getSignUpId() + "' ORDER BY PBS.REG_NO";
			stusql += " AND PEM.CODE = '" + us.getLoginId() + "' AND PSO.SIGN_ID='" + this.getSignUpId() + "' ORDER BY PBS.REG_NO";
			timesql += " AND PEM.CODE = '" + us.getLoginId() + "' AND PSO.SIGN_ID='" + this.getSignUpId() + "' ORDER BY PBS.REG_NO";
			stucoulist = this.getGeneralService().getBySQL(sql);

			List feelist = this.getGeneralService().getBySQL(feesql);
			if (null != feelist && feelist.size() > 0) {
				totalfee = feelist.get(0).toString();
			}
			List stulist = this.getGeneralService().getBySQL(stusql);
			if (null != stulist && stulist.size() > 0) {
				totalbm = stulist.get(0).toString();
			}
			List timelist = this.getGeneralService().getBySQL(timesql);
			if (null != timelist && timelist.size() > 0) {
				totalhour = timelist.get(0).toString();
			}
			BigDecimal price = new BigDecimal(totalfee).setScale(2, BigDecimal.ROUND_HALF_UP);
			ServletActionContext.getRequest().setAttribute("totalfee", price);
			ServletActionContext.getRequest().setAttribute("totalbm", totalbm);
			ServletActionContext.getRequest().setAttribute("totalhour", totalhour);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toConfirmPay";
	}

	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "";
		// 获取登陆集体账号的机构ID
		String enterpriseIdString = us.getPriEnterprises().get(0).getId();
		// 查询本机构及本机构下属机构ID集合
		String sql_enterprises = "SELECT ID FROM PE_ENTERPRISE WHERE ID = '" + enterpriseIdString + "' OR FK_PARENT_ID = '" + enterpriseIdString + "'";
		if ("addstu".equals(this.typeflag)) {
			sql = "SELECT * FROM (SELECT PBS_ID.ID,PBS_ID.REG_NO,PBS_ID.TRUE_NAME,PBS_ID.CARD_NO,EC1.NAME AS Combobox_ZIGE_NAME, PBS_ID.WORK, PBS_ID.POSITION, PBS_ID.DEPARTMENT "
					+ " FROM PE_BZZ_STUDENT PBS_ID "
					+ " JOIN PE_ENTERPRISE PEM ON PEM.ID = PBS_ID.FK_ENTERPRISE_ID "
					+ " JOIN SSO_USER SU ON PBS_ID.FK_SSO_USER_ID = SU.ID "// 关联用户表
					+ " JOIN ENUM_CONST EC ON SU.FLAG_ISVALID = EC.ID AND EC.CODE = '1'" // 用户为有效状态
					// 修改表重复别名 李文强 2014-09-10
					+ " LEFT JOIN ENUM_CONST EC1 ON EC1.ID = PBS_ID.ZIGE " + " WHERE PEM.ID IN (" + sql_enterprises + ")" + " AND PBS_ID.ID NOT IN ( "
					+ " SELECT  PSSC.STUDENT_ID PBS_ID FROM PE_SIGNUP_ORDER PSO "
					+ " JOIN PR_SIGNUP_ORDER_STUDENT PSSC ON PSSC.FK_SIGNUP_ORDER_ID = PSO.ID WHERE PSSC.STUDENT_ID IS NOT NULL AND PSO.ID = '" + this.getSignUpId() + "'"
					+ " )) WHERE 1=1";
		} else if ("addcou".equals(this.typeflag)) {
			sql = "SELECT * FROM (SELECT  PBTC.ID,PBTC.CODE,PBTC.NAME,EC_O.NAME AS Combobox_COURSETYPENAME,TO_NUMBER(NVL(PBTC.PRICE,0)) PRICE,PBTC.TEACHER ,TO_NUMBER(NVL(PBTC.TIME,0)) TIME,PBTC.FLAG_COURSETYPE "
					+ " ,EC2.NAME AS Combobox_COURSECATEGORY_NAME,EC3.NAME AS Combobox_COURSE_ITEM_NAME,EC4.NAME AS Combobox_CONTENT_PROPERTY_NAME,TO_CHAR(WM_CONCAT(EC10.NAME)) Combobox_FS_NAME FROM  PE_BZZ_TCH_COURSE PBTC "
					+ " JOIN ENUM_CONST EC_O ON EC_O.ID = PBTC.FLAG_COURSETYPE " + " 					INNER JOIN ENUM_CONST EC2 ON PBTC.FLAG_COURSECATEGORY = EC2.ID "
					+ " INNER JOIN ENUM_CONST EC3 ON PBTC.FLAG_COURSE_ITEM_TYPE = EC3.ID " + " INNER JOIN ENUM_CONST EC4 ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID "
					+ " INNER JOIN ENUM_CONST EC5 ON PBTC.FLAG_ISFREE = EC5.ID AND EC5.NAMESPACE = 'FlagIsFree' AND EC5.CODE = '0'"
					+ " LEFT JOIN PE_BZZ_TCH_COURSE_SUGGEST B ON PBTC.ID = B.FK_COURSE_ID AND TABLE_NAME = 'PE_BZZ_TCH_COURSE'"
					+ " LEFT JOIN ENUM_CONST EC10 ON B.FK_ENUM_CONST_ID = EC10.ID "
					+ " WHERE FLAG_COURSEAREA='Coursearea1'  AND PBTC.FLAG_ISVALID = '2' AND PBTC.FLAG_OFFLINE = '22' AND PBTC.ID NOT IN ( "
					+ "     SELECT  PSSC.COURSE_ID FROM PE_SIGNUP_ORDER PSO "
					+ "    JOIN PR_SIGNUP_ORDER_COURSE PSSC ON PSSC.FK_SIGNUP_ORDER_ID = PSO.ID WHERE PSSC.COURSE_ID IS NOT NULL AND PSO.ID = '" + this.getSignUpId() + "'  "
					+ "    ) GROUP BY PBTC.ID,PBTC.CODE,PBTC.NAME,EC_O.NAME,PBTC.PRICE,PBTC.TEACHER,PBTC.TIME,PBTC.FLAG_COURSETYPE,EC2.NAME,EC3.NAME,EC4.NAME ) WHERE 1=1";
		} else if ("viewstu".equals(this.typeflag)) {
			sql = "SELECT * FROM (SELECT PBS_ID.ID,PBS_ID.REG_NO,PBS_ID.TRUE_NAME,PBS_ID.CARD_NO,EC.NAME AS ZIGE_NAME, PBS_ID.WORK, PBS_ID.POSITION, PBS_ID.DEPARTMENT,ETC.NAME AS Combobox_P_STATUS "
					+ " FROM PE_BZZ_STUDENT PBS_ID " + " JOIN PE_ENTERPRISE PEM ON PEM.ID = PBS_ID.FK_ENTERPRISE_ID "
					+ " JOIN PR_SIGNUP_ORDER_STUDENT PSSC_ ON PSSC_.STUDENT_ID = PBS_ID.ID " + " JOIN PE_SIGNUP_ORDER PSO ON PSO.ID = PSSC_.FK_SIGNUP_ORDER_ID "
					+ " LEFT JOIN ENUM_CONST EC ON EC.ID = PBS_ID.ZIGE " + " LEFT JOIN ENUM_CONST ETC ON ETC.ID = PSO.FLAG_ISPAIED " + " WHERE PSSC_.FK_SIGNUP_ORDER_ID= '"
					+ this.getSignUpId() + "') WHERE 1=1";
		} else if ("viewcour".equals(this.typeflag)) {
			//字段错误 COURSETYPENAME 改成 Combobox_COURSETYPENAME zgl,后台异常nonexistent LOB value
			sql = "SELECT * FROM (SELECT  PBTC.ID,PBTC.CODE,PBTC.NAME,EC_O.NAME AS Combobox_COURSETYPENAME,TO_NUMBER(NVL(PBTC.PRICE,0)) PRICE,PBTC.TEACHER ,TO_NUMBER(NVL(PBTC.TIME,0)) TIME,PBTC.FLAG_COURSETYPE,EC2.NAME AS Combobox_COURSECATEGORY_NAME,EC3.NAME AS Combobox_COURSE_ITEM_NAME,EC4.NAME AS Combobox_CONTENT_PROPERTY_NAME," 
				    + " C.CNAME COMBOBOX_FS_NAME FROM  PE_BZZ_TCH_COURSE PBTC "
					+ " JOIN PR_SIGNUP_ORDER_COURSE PSOC ON PSOC.COURSE_ID=PBTC.ID "
					+ " JOIN ENUM_CONST EC_O ON EC_O.ID = PBTC.FLAG_COURSETYPE "
					+ " INNER JOIN ENUM_CONST EC2 ON PBTC.FLAG_COURSECATEGORY = EC2.ID "
					+ " INNER JOIN ENUM_CONST EC3 ON PBTC.FLAG_COURSE_ITEM_TYPE = EC3.ID "
					+ " INNER JOIN ENUM_CONST EC4 ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID "
					+ " LEFT JOIN (SELECT B.FK_COURSE_ID, TO_CHAR(WM_CONCAT(EC10.NAME)) CNAME "
					+ " FROM PE_BZZ_TCH_COURSE_SUGGEST B INNER JOIN ENUM_CONST EC10 ON B.FK_ENUM_CONST_ID = EC10.ID " 
					+ " WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE' GROUP BY B.FK_COURSE_ID) C "
					+ " ON PBTC.ID = C.FK_COURSE_ID "
					+ " WHERE  PSOC.FK_SIGNUP_ORDER_ID='"
					+ this.getSignUpId()
					+ "'  GROUP BY PBTC.ID,PBTC.CODE,PBTC.NAME,EC_O.NAME,PBTC.PRICE,PBTC.TEACHER,PBTC.TIME,PBTC.FLAG_COURSETYPE,EC2.NAME,EC3.NAME,EC4.NAME,C.CNAME ) WHERE 1=1";
		}
		try {
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					context.setParameters(params);
				}
			}
			StringBuffer sqlBuffer = new StringBuffer(sql);
			this.setSqlCondition(sqlBuffer);
			this.page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	public String getSignUpId() {
		return signUpId;
	}

	public void setSignUpId(String signUpId) {
		this.signUpId = signUpId;
	}

	public String getTypeflag() {
		return typeflag;
	}

	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}

	public List getStucoulist() {
		return stucoulist;
	}

	public void setStucoulist(List stucoulist) {
		this.stucoulist = stucoulist;
	}

}
