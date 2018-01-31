package com.whaty.platform.entity.web.action.basic;

import java.util.Iterator;
import java.util.Map;

import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.service.basic.PeDetailService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 按订单查询 查看学员 2014-07-21
 * 
 * @author hdg
 * 
 */
public class PeBzzTchOrderStuDetailAction extends MyBaseAction<StudentBatch> {

	private static final long serialVersionUID = -5412497315127922196L;

	private PeDetailService peDetailService;

	private String id;
	private String num;
	private PeEnterprise peEnterprise;
	private PeBzzStudent peBzzStudent;
	private String type;
	private String tempFlag;
	private String method;
	private String sumTimes;// 获取学时总数

	private String batchid;
	private PeBzzstudentbacthService peBzzstudentbacthService;
	private int count;

	/**
	 * 初始化列表
	 * 
	 * @author hdg
	 * @return
	 */
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("查看学员"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("系统编号"), "LOGIN_ID", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("姓名"), "TRUE_NAME", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("从事业务"), "WORK", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("职务"), "POSITION", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("证件号码"), "CARD_NO", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("所在机构"), "NAME", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("报名课程数"), "BMS", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("报名学时数"), "ZXS", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("已完成学习课程数"), "FSCN", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("未获得学时数"), "NSN", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("已获得学时数"), "YSN", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("获得学时开始时间"), "operateTimeSTDatetime", true, false, false, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("获得学时结束时间"), "operateTimeEDDatetime", true, false, false, "TextField", false, 200, "");
		this.getGridConfig().addRenderScript(this.getText("学习详情"),
				"{return '<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_electivedCourse.action?flag=stu&stuId='+record.data['id']+'\">学习详情</a>';}", "id");
		// this.getGridConfig().addMenuScript(this.getText("返回"),
		// "{history.back()}");
	}

	/**
	 * @author hdg 替代public DetachedCriteria initDetachedCriteria()方法
	 *         原方法体移到else中
	 */
	public Page list() {
		StringBuffer lsqlBuffer = new StringBuffer();
		StringBuffer olsqlBuffer = new StringBuffer();
		Map params = this.getParamsMap();
		Iterator iterator = params.entrySet().iterator();
		do {
			if (!iterator.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			String name = entry.getKey().toString();
			String value = ((String[]) entry.getValue())[0].toString();
			if (!name.startsWith("search__"))
				continue;
			if ("".equals(value))
				continue;
			if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
				name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
			} else {
				name = name.substring(8);
			}
			/* 操作时间开始时间 to_date('2014-07-25 08:00:00','yyyy-MM-dd hh24:mi:ss') */
			if (name.equals("operateTimeSTDatetime")) {
				lsqlBuffer.append(" AND COMPLETED_TIME >= to_date('" + value + "','yyyy-MM-dd hh24:mi:ss')");
			}
			/* 操作时间结束时间 */
			if (name.equals("operateTimeEDDatetime")) {
				lsqlBuffer.append(" AND COMPLETED_TIME <= to_date('" + value + "','yyyy-MM-dd hh24:mi:ss')");
			}
			/* 系统编号 */
			if (name.equals("LOGIN_ID")) {
				olsqlBuffer.append(" AND LOGIN_ID LIKE '%" + value + "%'");
			}
			/* 系统编号 */
			if (name.equals("WORK")) {
				olsqlBuffer.append(" AND WORK LIKE '%" + value + "%'");
			}
			/* 系统编号 */
			if (name.equals("POSITION")) {
				olsqlBuffer.append(" AND POSITION LIKE '%" + value + "%'");
			}
			/* 姓名 */
			if (name.equals("TRUE_NAME")) {
				olsqlBuffer.append(" AND TRUE_NAME LIKE '%" + value + "%'");
			}
			/* 证件号码 */
			if (name.equals("CARD_NO")) {
				olsqlBuffer.append(" AND CARD_NO LIKE '%" + value + "%'");
			}

		} while (true);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("   SELECT  * ");// 系统编号
		sqlBuffer.append("   FROM( SELECT  a.ID AS ID,");// 系统编号
		sqlBuffer.append("   A.LOGIN_ID,");// 系统编号
		sqlBuffer.append("   A.TRUE_NAME,");// 姓名
		sqlBuffer.append("   A.WORK,");// 从事业务
		sqlBuffer.append("   A.POSITION,");// 职务
		sqlBuffer.append("   A.CARD_NO,");// 证件号
		sqlBuffer.append("   A.NAME,");// 所在机构
		sqlBuffer.append("   BMS,ZXS,");// 报名学时数
		sqlBuffer.append("   NVL(WC, 0)  FSCN, ");// 已完成学习课程数
		sqlBuffer.append("    NVL((ZXS -  NVL(HD, 0)), 0) NSN,");// 未获得学时数 加非空判断
																	// lzh
		sqlBuffer.append("    NVL(HD, 0) YSN,'' OPERATETIMESTDATETIME,'' OPERATETIMEEDDATETIME ");// 已获得学时数
		sqlBuffer.append("   FROM (SELECT A.LOGIN_ID,");
		sqlBuffer.append("   B.id as id, ");
		sqlBuffer.append("   B.TRUE_NAME, ");
		sqlBuffer.append("   B.WORK, ");
		sqlBuffer.append("   B.POSITION, ");
		sqlBuffer.append("   B.CARD_NO,");
		sqlBuffer.append("    C.NAME, ");
		sqlBuffer.append("  COUNT(DISTINCT F.ID) BMS, ");
		sqlBuffer.append("    SUM(F.TIME) ZXS ");
		sqlBuffer.append("   FROM SSO_USER                A,");
		sqlBuffer.append("   PE_BZZ_STUDENT          B,");
		sqlBuffer.append("   PE_ENTERPRISE           C, ");
		sqlBuffer.append("   PR_BZZ_TCH_STU_ELECTIVE D, ");
		sqlBuffer.append("   PR_BZZ_TCH_OPENCOURSE   E,");
		sqlBuffer.append("   PE_BZZ_TCH_COURSE       F,");
		sqlBuffer.append("   PE_BZZ_ORDER_INFO       G ");
		sqlBuffer.append("    WHERE A.ID = B.FK_SSO_USER_ID");
		sqlBuffer.append("    AND B.FK_ENTERPRISE_ID = C.ID");
		sqlBuffer.append("    AND B.ID = D.FK_STU_ID ");
		sqlBuffer.append("   AND D.FK_TCH_OPENCOURSE_ID = E.ID ");
		sqlBuffer.append("    AND E.FK_COURSE_ID = F.ID ");
		sqlBuffer.append("    AND D.FK_ORDER_ID = G.ID ");
		sqlBuffer.append("  AND G.ID = '" + this.getId() + "' ");
		if (!"".equals(lsqlBuffer.toString())) {
			sqlBuffer.append(" " + lsqlBuffer);
		}
		sqlBuffer.append("    GROUP BY A.LOGIN_ID, B.ID , B.TRUE_NAME, B.CARD_NO, C.NAME, B.WORK,B.POSITION) A");
		sqlBuffer.append("    LEFT JOIN (SELECT A.LOGIN_ID, ");
		sqlBuffer.append("    COUNT(DISTINCT F.ID) WC,");
		sqlBuffer.append("   SUM(F.TIME) HD ");
		sqlBuffer.append("    FROM SSO_USER                A, ");
		sqlBuffer.append("    PE_BZZ_STUDENT          B, ");
		sqlBuffer.append("  PR_BZZ_TCH_STU_ELECTIVE D, ");
		sqlBuffer.append("    PR_BZZ_TCH_OPENCOURSE   E,  ");
		sqlBuffer.append("     PE_BZZ_TCH_COURSE       F, ");
		sqlBuffer.append("  PE_BZZ_ORDER_INFO       G,");
		sqlBuffer.append("     TRAINING_COURSE_STUDENT H, ");
		sqlBuffer.append("   ENUM_CONST              I ");
		sqlBuffer.append("    WHERE A.ID = B.FK_SSO_USER_ID ");
		sqlBuffer.append("  AND B.ID = D.FK_STU_ID  ");
		sqlBuffer.append("   AND D.FK_TCH_OPENCOURSE_ID = E.ID  ");
		sqlBuffer.append("   AND E.FK_COURSE_ID = F.ID ");
		sqlBuffer.append("   AND D.FK_ORDER_ID = G.ID ");
		sqlBuffer.append("    AND F.FLAG_IS_EXAM = I.ID  ");
		sqlBuffer.append("   AND G.ID = '" + this.getId() + "'");
		sqlBuffer.append("   AND D.FK_TRAINING_ID = H.ID  ");
		sqlBuffer.append("  AND H.COMPLETE_DATE IS NOT NULL  ");
		sqlBuffer.append("   AND (D.ISPASS = '1' OR  ");
		sqlBuffer.append("   (I.CODE = '0' AND D.ISPASS = '0'))  ");
		if (!"".equals(lsqlBuffer.toString())) {
			sqlBuffer.append(" " + lsqlBuffer);
		}
		sqlBuffer.append("     GROUP BY A.LOGIN_ID) B ON  A.LOGIN_ID = B.LOGIN_ID  ) where 1=1 ");
		if (!"".equals(olsqlBuffer.toString())) {
			sqlBuffer.append(" " + olsqlBuffer);
		}
		try {
			// this.setSqlCondition(sqlBuffer);
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public String getSumTimes() {
		return sumTimes;
	}

	public void setSumTimes(String sumTimes) {
		this.sumTimes = sumTimes;
	}

	private String jfrs;

	public String getJfrs() {
		return jfrs;
	}

	public void setJfrs(String jfrs) {
		this.jfrs = jfrs;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public PeEnterprise getPeEnterprise() {
		return peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEntityClass() {
		this.entityClass = StudentBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peBzzTchOrderStuDetail";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public PeDetailService getPeDetailService() {
		return peDetailService;
	}

	public void setPeDetailService(PeDetailService peDetailService) {
		this.peDetailService = peDetailService;
	}

	public PeBzzstudentbacthService getPeBzzstudentbacthService() {
		return peBzzstudentbacthService;
	}

	public void setPeBzzstudentbacthService(PeBzzstudentbacthService peBzzstudentbacthService) {
		this.peBzzstudentbacthService = peBzzstudentbacthService;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

}
