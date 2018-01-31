package com.whaty.platform.entity.web.action.onlineexam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzPici;
import com.whaty.platform.entity.bean.PeBzzPiciStudent;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeDetailService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;

public class PePcDetailAction extends MyBaseAction<PeBzzPiciStudent> {

	private PeDetailService peDetailService;

	private String id;
	private String num;
	private PeEnterprise peEnterprise;
	private PeBzzStudent peBzzStudent;
	private String type;
	private String tempFlag;
	private String method;
	private Date endDate;

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	/**
	 * 加载列表（显示字段）
	 * 
	 * @author qiaoshijia
	 */
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		boolean flag = true;
		boolean endFlag = true;
		List<PeBzzPici> pici = new ArrayList<PeBzzPici>();
		try {
			pici = this.getGeneralService().getByHQL(" from PeBzzPici where id ='" + this.getId() + "'");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (null == pici.get(0).getPublishDatetime()) {

		} else {
			if (pici.get(0).getPublishDatetime().after(new Date())) {
				flag = false;
			}
			if (null == pici.get(0).getEndDatetime() || pici.get(0).getEndDatetime().after(new Date())) {
				endFlag = false;
			}
		}

		this.getGridConfig().setCapability(false, false, false);

		if ("student".equals(method)) {
			this.getGridConfig().setTitle(this.getText("添加学员"));
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, false, true, "textField");
			this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
			this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo");
			this.getGridConfig().addColumn(this.getText("所在机构"), "peEnterprise.name", true);
			this.getGridConfig().addColumn(this.getText("具有从业资格"), "ismep", false, false, true, "");
			ColumnConfig c_name = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
			c_name.setAdd(true);
			c_name.setSearch(true);
			c_name.setList(true);
			c_name.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
			this.getGridConfig().addColumn(c_name);
			this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "");
			this.getGridConfig().addRenderFunction(this.getText("详细信息"),
					"<a href=\"pePcDetail_stuviewDetail.action?id=${value}&type=1\">查看详细信息</a>", "id");
		} else if ("mystudent".equals(method)) {
			this.getGridConfig().setTitle(this.getText("查看学员"));
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("试卷id"), "piciid", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("试卷名称"), "piciname", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("学生ID"), "peBzzStudent.id", false);
			this.getGridConfig().addColumn(this.getText("系统编号"), "peBzzStudent.regNo", true, false, true, "textField");
			ColumnConfig c_name = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
			c_name.setAdd(true);
			c_name.setSearch(true);
			c_name.setList(true);
			c_name.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
			this.getGridConfig().addColumn(c_name);
			this.getGridConfig().addColumn(this.getText("姓名"), "peBzzStudent.trueName");
			this.getGridConfig().addColumn(this.getText("从事业务"), "peBzzStudent.work", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("职务"), "peBzzStudent.position", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("所在机构"), "organname", true);
			this.getGridConfig().addColumn(this.getText("是否有效"), "peBzzStudent.ssoUser.enumConstByFlagIsvalid.name", false);
			this.getGridConfig().addColumn(this.getText("参加考试时间"), "startDate", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("提交考试时间"), "endDate", false, false, true, "");
			if (us.getUserLoginType().equals("3")) {
				this.getGridConfig().addColumn(this.getText("客观题分数"), "subScore", false, false, endFlag, "");
				this.getGridConfig().addColumn(this.getText("主观题分数"), "zScore", false, false, endFlag, "");
				this.getGridConfig().addColumn(this.getText("考试成绩"), "score", false, false, endFlag, "");
			} else {
				this.getGridConfig().addColumn(this.getText("客观题分数"), "subScore", false, false, flag, "");
				this.getGridConfig().addColumn(this.getText("主观题分数"), "zScore", false, false, flag, "");
				this.getGridConfig().addColumn(this.getText("考试成绩"), "score", false, false, flag, "");

			}
			this.getGridConfig().addColumn(this.getText("考试结果"), "isPass", false, false, flag, "");
			ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("考试状态"), "enumConstByFlagStruts.name", true, true, true,
					"TextField", false, 100, "");
			String sql2 = "select a.id ,a.name from enum_const a where a.namespace='examStruts'";
			columnConfigCategory.setComboSQL(sql2);
			this.getGridConfig().addColumn(columnConfigCategory);

			this.getGridConfig().addColumn(this.getText("主观题打分"), "grade", false, false, false, "");
			if (us.getUserLoginType().equals("3")) { // 稍后要区分考试类型
				if (pici.get(0).getPublishDatetime() != null) {
					if (pici.get(0).getEndDatetime().before(new Date()) && pici.get(0).getPublishDatetime().after(new Date())) {
						this
								.getGridConfig()
								.addRenderScript(
										this.getText("主观题打分"),
										"{if(record.data['grade'] =='打分')"
												+ "{return '<a href=\"/sso/bzzinteraction_hzphExamManageScore.action?pici_student='+record.data['id']+'&batch_id='+record.data['piciid']+'&teacher_id=teacher1&piciName= '+encodeURI(encodeURI(record.data['name']))+'&tsId='+record.data['historyid']+'&pageInt=1&piciId="
												+ this.getId()
												+ "\">打分</a>';}else if(record.data['grade'] =='已打分'){ return '<a href=\"/sso/bzzinteraction_hzphExamManageScore.action?pici_student='+record.data['id']+'&batch_id='+record.data['piciid']+'&teacher_id=teacher1&piciName= '+encodeURI(encodeURI(record.data['name']))+'&tsId='+record.data['historyid']+'&pageInt=1&piciId="
												+ this.getId() + "\">已打分</a>'}else{return '--';}}", "id");
					} else if (pici.get(0).getEndDatetime().after(new Date())) {
						this.getGridConfig().addRenderScript(
								this.getText("主观题打分"),
								"{if(record.data['grade'] =='打分')"
										+ "{return '--';}else if(record.data['grade'] =='已打分'){ return '--'}else{return '--';}}", "id");
					} else if (pici.get(0).getPublishDatetime().before(new Date())) {
						this.getGridConfig().addRenderScript(
								this.getText("主观题打分"),
								"{if(record.data['grade'] =='打分')"
										+ "{return '--';}else if(record.data['grade'] =='已打分'){ return '已打分'}else{return '--';}}", "id");
					}
				} else {
					this.getGridConfig().addRenderScript(
							this.getText("主观题打分"),
							"{if(record.data['grade'] =='打分')"
									+ "{return '--';}else if(record.data['grade'] =='已打分'){ return '已打分'}else{return '--';}}", "id");
				}
			}
			this.getGridConfig().addColumn(this.getText("考试答案id"), "historyid", false, false, false, "");
			if (pici.get(0).getPublishDatetime() != null && pici.get(0).getPublishDatetime().before(new Date())) {
				this
						.getGridConfig()
						.addRenderScript(
								this.getText("查看"),
								"{if(record.data['grade'] =='打分' ||record.data['grade'] =='已打分')"
										+ "{return '<a href=\"/sso/bzzinteraction_hzphExamManageDetail.action?batch_id='+record.data['piciid']+'&teacher_id=teacher1&piciName= '+encodeURI(encodeURI(record.data['name']))+'&tsId='+record.data['historyid']+'&pageInt=1&piciId="
										+ this.getId() + "\" target=\"_blank\">查看详细信息</a>';}else{return '--';}}", "id");
			} else {
				this
						.getGridConfig()
						.addRenderScript(
								this.getText("查看"),
								"{if(record.data['grade'] =='打分' ||record.data['grade'] =='已打分')"
										+ "{return '<a href=\"/sso/bzzinteraction_hzphExamManageDetail.action?batch_id='+record.data['piciid']+'&teacher_id=teacher1&piciName= '+encodeURI(encodeURI(record.data['name']))+'&tsId='+record.data['historyid']+'&pageInt=1&piciId="
										+ this.getId() + "\" target=\"_blank\">查看详细信息</a>';}else{return '--';}}", "id");
			}
		}
		if (us.getUserLoginType().equals("3")) {
			if ("student".equals(method)) {
				this.getGridConfig().addMenuFunction(this.getText("加入到考试"), "StuAdd");
			}
		}
		this.getGridConfig().addMenuScript(this.getText("返回"), "{location.href='/entity/onlineexam/peBzzPici.action'}");

		ActionContext.getContext().getSession().put("id", id);
	}

	public void setEntityClass() {
		this.entityClass = PeBzzPiciStudent.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/onlineexam/pePcDetail";
	}

	/**
	 * 满足条件下执行将学员添加到考试管理下或者将学员从当前考试管理下删除
	 * 
	 * @return Map
	 * @author qiaoshijia
	 */
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		String id = ActionContext.getContext().getSession().get("id").toString().trim();
		EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("examStruts", "0");
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria rodc = DetachedCriteria.forClass(PeBzzPici.class);
				rodc.add(Restrictions.eq("id", id));
				List<PeBzzPici> peBzzPclist = this.getGeneralService().getList(rodc);

				PeBzzStudent peBzzStudent;
				if (action.equals("StuAdd")) {
					DetachedCriteria peprdc = DetachedCriteria.forClass(PeBzzStudent.class);
					peprdc.add(Restrictions.in("id", ids));
					List<PeBzzStudent> studentlist = this.getGeneralService().getList(peprdc);
					if (!peBzzPclist.get(0).getEnumConstByFlagPiciStatus().getCode().equals("1")) {
						map.clear();
						map.put("success", "false");
						map.put("info", "考试处于打开状态，无法添加学员");
						return map;
					}
					if (peBzzPclist.get(0).getStartDatetime().before(new Date())) {
						map.clear();
						map.put("success", "false");
						map.put("info", "考试已经开始或结束，无法添加学生");
						return map;
					}
					for (int n = 0; n < studentlist.size(); n++) {
						peBzzStudent = studentlist.get(n);
						PeBzzPiciStudent peBzzPiciStudent = new PeBzzPiciStudent();
						peBzzPiciStudent.setPeBzzPici(peBzzPclist.get(0));
						peBzzPiciStudent.setPeBzzStudent(peBzzStudent);
						peBzzPiciStudent.setEnumConstByFlagStruts(enumConst);
						this.getGeneralService().save(peBzzPiciStudent);
					}
					msg = "加入考试管理";
				}
				if (action.equals("StuDel")) {
					if (peBzzPclist.get(0).getStartDatetime().before(new Date())) {
						map.clear();
						map.put("success", "false");
						map.put("info", "考试已经开始，无法删除学生");
						return map;
					}
					// 获取学生专项id列表
					DetachedCriteria piciStuDc = DetachedCriteria.forClass(PeBzzPiciStudent.class);
					piciStuDc.add(Restrictions.eq("peBzzPici.id", id));
					piciStuDc.add(Restrictions.in("id", ids));
					List<PeBzzPiciStudent> sbList = this.getGeneralService().getList(piciStuDc);
					piciStuDc.setProjection(Projections.distinct(Projections.property("id")));
					List stuBatchIdList = this.getGeneralService().getList(piciStuDc);
					this.getGeneralService().deleteByIds(stuBatchIdList);
					msg = "从考试中删除";
				}
				map.clear();
				map.put("success", "true");
				map.put("info", msg + ids.length + "条记录操作成功");

			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	/**
	 * 初始化
	 * 
	 * @author qiaoshijia
	 */
	public DetachedCriteria initDetachedCriteria() {

		if ("student".equals(method)) {
			return this.addstudent();
		} else if ("mystudent".equals(method)) {
			return this.mystudent();
		}
		return null;
	}

	/**
	 * 显示学生的详细信息
	 * 
	 * @author qiaoshijia
	 */
	public String stuviewDetail() {

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria criteria = DetachedCriteria.forClass(PeBzzStudent.class);
		criteria.add(Restrictions.eq("id", id));
		try {
			this.setPeBzzStudent(this.getGeneralService().getStudentInfo(criteria));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "stuinfo";
	}

	/**
	 * 查看当前考试管理下已经存在的学员
	 * 
	 * @return pePcDetail
	 * @author qiaoshijia
	 */
	public DetachedCriteria mystudent() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = null;
		DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzPiciStudent.class);
		dc1.createCriteria("peBzzPici", "peBzzPici");
		dc1.add(Restrictions.eq("peBzzPici.id", id));
		dc = dc1.createCriteria("peBzzStudent", "peBzzStudent");
		dc.createCriteria("ssoUser", "ssoUser")
				.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.INNER_JOIN);
		;
		dc.add(Restrictions.eq("ssoUser.enumConstByFlagIsvalid.id", "2"));
		dc.createCriteria("peEnterprise", "peEnterprise");
		if ("24".indexOf(us.getUserLoginType()) != -1) {
			String sql = "\n" + "select pe.id\n" + "  from pe_enterprise pe\n"
					+ " inner join pe_enterprise_manager pem on pem.fk_enterprise_id = pe.id\n" + " where pem.login_id = '"
					+ us.getLoginId() + "'\n" + "\n" + "union\n" + "\n" + "select pe.id\n"
					+ "  from pe_enterprise pe, pe_enterprise pePar, pe_enterprise_manager pem\n" + " where pe.fk_parent_id = pePar.Id\n"
					+ "   and pePar.Id = pem.fk_enterprise_id\n" + "   and pem.login_id = '" + us.getLoginId() + "'";
			List list = null;
			try {
				list = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (null != list && list.size() > 0) {
				dc.add(Restrictions.in("peEnterprise.id", list.toArray(new String[list.size()])));
			}
		}
		dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee");
		dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
		return dc;
	}

	public Page list() {
		Page page = null;
		try {
			if ("mystudent".equals(method)) {
				UserSession us = (UserSession) ServletActionContext.getRequest().getSession()
						.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
				StringBuffer sqlBuffer = new StringBuffer();
				sqlBuffer.append("     select * from ( ");
				sqlBuffer.append("       SELECT PICI.ID ID,        ");
				sqlBuffer.append("        PE_PICI.ID PICIID,        ");
				sqlBuffer.append("        PE_PICI.NAME PICINAME,        ");
				sqlBuffer.append("            STUDENT.ID STUDENTID,           ");
				sqlBuffer.append("          STUDENT.REG_NO CARDNO,      ");
				sqlBuffer.append("            CONST.NAME TYPE,    ");
				sqlBuffer.append("             STUDENT.TRUE_NAME USERNAME,   ");
				sqlBuffer.append("             STUDENT.WORK,   ");
				sqlBuffer.append("             STUDENT.POSITION,   ");
				sqlBuffer.append("              ORGAN.NAME ORGANNAME, ");
				sqlBuffer.append("             CONST1.NAME ISNAME,  ");
				sqlBuffer.append("              HISTORY.TEST_OPEN_DATE STARTDATE,  ");
				sqlBuffer.append("            to_char(HISTORY.TEST_DATE ,'YYYY-MM-DD HH24:mi:ss')ENDDATE,  ");
				sqlBuffer.append("         NVL(PICI.SUB_SCORE,'--') SUBSCORE , ");
				sqlBuffer.append("         DECODE (HISTORY.SCORE,NULL ,'--' ,(HISTORY.SCORE- PICI.SUB_SCORE)) ZSCORE, ");
				sqlBuffer.append("               HISTORY.SCORE SCORE, ");
				sqlBuffer
						.append("              DECODE(HISTORY.SCORE,NULL,'--', (DECODE (SIGN(NVL(HISTORY.SCORE,'0') - NVL(PE_PICI.PASSSCORE,0)),-1,'未通过考试','已通过考试')))  isPass ,");
				sqlBuffer.append("           CONST2.NAME EXAM_STRUTS , ");
				sqlBuffer.append("                DECODE(HISTORY.SCORE,  NULL, '--',  DECODE(HISTORY.ISCHECK, NULL, '打分', '已打分')) GRADE ,");
				sqlBuffer.append("                HISTORY.ID HISTORYID ");
				sqlBuffer.append("              FROM PE_BZZ_PICI_STUDENT PICI  ");
				sqlBuffer.append("               INNER JOIN  PE_BZZ_PICI PE_PICI ON PICI.PC_ID =PE_PICI.ID  ");
				sqlBuffer.append("              INNER JOIN PE_BZZ_STUDENT STUDENT ON PICI.STU_ID = STUDENT.ID  ");
				if (!us.getUserLoginType().equals("3")) {// 集体用户或二级用户
					sqlBuffer
							.append(" AND STUDENT.FK_ENTERPRISE_ID IN (SELECT ID FROM PE_ENTERPRISE WHERE ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '"
									+ us.getSsoUser().getId()
									+ "') OR FK_PARENT_ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '"
									+ us.getSsoUser().getId() + "'))");
				}
				sqlBuffer.append("              INNER JOIN PE_ENTERPRISE ORGAN ON STUDENT.FK_ENTERPRISE_ID = ORGAN.ID  ");
				sqlBuffer.append("              AND PICI.PC_ID ='" + this.getId() + "'  ");
				sqlBuffer.append("             LEFT JOIN ENUM_CONST CONST ON STUDENT.ZIGE = CONST.ID  ");
				sqlBuffer.append("              LEFT JOIN SSO_USER U ON STUDENT.FK_SSO_USER_ID = U.ID AND u.FLAG_ISVALID ='2' ");
				sqlBuffer.append("            LEFT JOIN ENUM_CONST CONST1  ON U.FLAG_ISVALID =CONST1.ID   ");
				sqlBuffer.append("            LEFT JOIN ENUM_CONST CONST2  ON PICI.EXAM_STRUTS =CONST2.ID   ");
				sqlBuffer
						.append("              LEFT JOIN ( SELECT HIS.ID,HIS.T_USER_ID,  HIS.TEST_OPEN_DATE, HIS.TEST_DATE,  HIS.SCORE,   HIS.ISCHECK         ");
				sqlBuffer.append("               FROM TEST_TESTPAPER_HISTORY HIS,        ");
				sqlBuffer.append("                 (SELECT TO_CHAR(MAX(TO_NUMBER(ID))) ID      ");
				sqlBuffer.append("                    FROM TEST_TESTPAPER_HISTORY HIS   ");
				sqlBuffer.append("                    WHERE HIS.TESTPAPER_ID IN   ");
				sqlBuffer.append("                      (SELECT ID ");
				sqlBuffer.append("                      FROM TEST_TESTPAPER_INFO INFO  ");
				sqlBuffer.append("                     WHERE INFO.GROUP_ID ='" + this.getId() + "' ) ");
				sqlBuffer.append("                     GROUP BY T_USER_ID ) IDS  ");
				sqlBuffer.append("                       WHERE HIS.ID = IDS.ID) HISTORY  ");
				sqlBuffer.append("                        ON STUDENT.ID =HISTORY.T_USER_ID   )AA   ");
				sqlBuffer.append("                WHERE 1=1  ");
				StringBuffer searchSql = new StringBuffer();
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
					if (name.equalsIgnoreCase("peBzzStudent.regNo")) {
						searchSql.append(" AND UPPER(AA.CARDNO) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("enumConstByFlagStruts.name")) {
						searchSql.append(" AND UPPER(AA.EXAM_STRUTS) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("enumConstByFlagQualificationsType.name")) {
						searchSql.append(" AND UPPER(AA.TYPE) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("peBzzStudent.trueName")) {
						searchSql.append(" AND UPPER(AA.USERNAME) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("organname")) {
						searchSql.append(" AND UPPER(AA.ORGANNAME) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("startDate")) {
						searchSql.append(" AND SS.STARTDATE  = to_date('" + value.toUpperCase() + "','yyyy-MM-dd hh24:mi:ss')");
					}
					if (name.equalsIgnoreCase("endDate")) {
						searchSql.append(" AND SS.ENDDATE  = to_date('" + value.toUpperCase() + "','yyyy-MM-dd hh24:mi:ss')");
					}
					if (name.equalsIgnoreCase("score")) {
						searchSql.append(" AND UPPER(AA.SCORE) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("peBzzStudent.work")) {
						searchSql.append(" AND AA.WORK LIKE '%" + value.trim() + "%' ");
					}
					if (name.equalsIgnoreCase("peBzzStudent.position")) {
						searchSql.append(" AND AA.POSITION LIKE '%" + value.trim() + "%' ");
					}
				} while (true);
				String temp = this.getSort();
				if (temp != null && temp.indexOf(".") > 1) {
					if (temp.toLowerCase().startsWith("combobox_")) {
						temp = temp.substring(temp.indexOf(".") + 1);
					}
				}
				if (this.getSort() != null && temp != null) {
					/* 课程性质 */
					if (temp.equals("enumConstByFlagCourseType.name")) {
						temp = "COURSETYPE";
					}
					if (temp.equals("peBzzStudent.regNo")) {
						temp = "CARDNO";
					}
					if (temp.equals("enumConstByFlagQualificationsType.name")) {
						temp = "type";
					}
					if (temp.equals("peBzzStudent.trueName")) {
						temp = "USERNAME";
					}
					if (temp.equals("peBzzStudent.work")) {
						temp = "WORK";
					}
					if (temp.equals("peBzzStudent.position")) {
						temp = "POSITION";
					}

					if (temp.equals("enumConstByFlagIsvalid.name")) {
						temp = "ISNAME";
					}
					if (temp.equals("enumConstByFlagStruts.name")) {
						temp = "EXAM_STRUTS";
					}
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");
				}

				else {
					sqlBuffer.append(" order by " + temp + " asc ");
				}
				sqlBuffer.append(searchSql);

				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			}
			if ("student".equals(method)) {
				StringBuffer sqlBuffer = new StringBuffer();
				sqlBuffer.append("             SELECT *       ");
				sqlBuffer.append("               FROM (SELECT STUDENT.ID ID,     ");
				sqlBuffer.append("               STUDENT.REG_NO REGNO,      ");
				sqlBuffer.append("               STUDENT.TRUE_NAME TRUENAME,     ");
				sqlBuffer.append("               STUDENT.CARD_NO CARDNO,    ");
				sqlBuffer.append("               ORGAN.NAME ORGAN,   ");
				sqlBuffer.append("               CONST1.NAME ISEMP,");
				sqlBuffer.append("               CONST.NAME CONSTNAME, ");
				sqlBuffer.append("               STUDENT.WORK ,");
				sqlBuffer.append("               STUDENT.POSITION ");
				sqlBuffer.append("               FROM PE_BZZ_STUDENT STUDENT    ");
				sqlBuffer.append("               INNER JOIN PE_ENTERPRISE ORGAN ON STUDENT.FK_ENTERPRISE_ID = ORGAN.ID   ");
				sqlBuffer.append("               LEFT JOIN ENUM_CONST CONST ON STUDENT.ZIGE = CONST.ID   ");
				sqlBuffer.append("               LEFT JOIN ENUM_CONST CONST1 ON STUDENT.IS_EMPLOYEE =CONST1.ID");
				sqlBuffer.append("               INNER JOIN SSO_USER U ON STUDENT.FK_SSO_USER_ID = U.ID   ");
				sqlBuffer
						.append("               AND U.FLAG_ISVALID = '2'   WHERE STUDENT.ID NOT IN (SELECT STU.STU_ID FROM PE_BZZ_PICI_STUDENT STU WHERE STU.PC_ID ='"
								+ this.id + "'  )) AA  ");
				sqlBuffer.append("                 	WHERE 1=1   ");
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
					if (name.equalsIgnoreCase("regNo")) {
						sqlBuffer.append(" AND UPPER(AA.REGNO) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("trueName")) {
						sqlBuffer.append(" AND UPPER(AA.TRUENAME) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("cardNo")) {
						sqlBuffer.append(" AND UPPER(AA.CARDNO) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("enumConstByFlagQualificationsType.name")) {
						sqlBuffer.append(" AND UPPER(AA.CONSTNAME) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("peEnterprise.name")) {
						sqlBuffer.append(" AND UPPER(AA.ORGAN) LIKE '%" + value.toUpperCase() + "%' ");
					}
					if (name.equalsIgnoreCase("work")) {
						sqlBuffer.append(" AND AA.WORK LIKE '%" + value.trim() + "%' ");
					}
					if (name.equalsIgnoreCase("position")) {
						sqlBuffer.append(" AND AA.POSITION LIKE '%" + value.trim() + "%' ");
					}
				} while (true);
				String temp = this.getSort();
				if (temp != null && temp.indexOf(".") > 1) {
					if (temp.toLowerCase().startsWith("combobox_")) {
						temp = temp.substring(temp.indexOf(".") + 1);
					}
				}
				if (this.getSort() != null && temp != null) {
					/* 系统编号 */
					if (temp.equals("regNo")) {
						temp = "regNo";
					}
					/* 姓名 */
					if (temp.equals("trueName")) {
						temp = "TRUENAME";
					}
					/* 身份证号 */
					if (temp.equals("cardNo")) {
						temp = "CARDNO";
					}
					/* 所在机构 */
					if (temp.equals("peEnterprise.name")) {
						temp = "ORGAN";
					}
					/* 是否具有从业资格 */
					if (temp.equals("ismep")) {
						temp = "ISEMP";
					}
					/* 资格类型 */
					if (temp.equals("enumConstByFlagQualificationsType.name")) {
						temp = "CONSTNAME";
					}
					/* 从事业务 */
					if (temp.equals("work")) {
						temp = "work";
					}
					/* 职务 */
					if (temp.equals("position")) {
						temp = "position";
					}
					if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
						sqlBuffer.append(" order by " + temp + " desc ");
					} else {
						sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 显示需要添加到当前考试管理的学员
	 * 
	 * @author qiaoshijia
	 */
	public DetachedCriteria addstudent() {
		DetachedCriteria dc = null;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria expcetdc = null;
		ActionContext.getContext().getSession().put("id", id);
		expcetdc = DetachedCriteria.forClass(PeBzzPiciStudent.class);
		expcetdc.setProjection(Projections.distinct(Property.forName("peBzzStudent.id")));
		expcetdc.createCriteria("peBzzStudent", "peBzzStudent");
		expcetdc.createCriteria("peBzzPici", "peBzzPici");
		expcetdc.add(Restrictions.eq("peBzzPici.id", id));

		dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("ssoUser", "ssoUser")
				.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.INNER_JOIN);
		dc.add(Restrictions.eq("ssoUser.enumConstByFlagIsvalid.id", "2"));
		dc.createCriteria("peEnterprise", "peEnterprise");
		if ("14".indexOf(us.getUserLoginType()) != -1) {
			String sql = "\n" + "select pe.id\n" + "  from pe_enterprise pe\n"
					+ " inner join pe_enterprise_manager pem on pem.fk_enterprise_id = pe.id\n" + " where pem.login_id = '"
					+ us.getLoginId() + "'\n" + "\n" + "union\n" + "\n" + "select pe.id\n"
					+ "  from pe_enterprise pe, pe_enterprise pePar, pe_enterprise_manager pem\n" + " where pe.fk_parent_id = pePar.Id\n"
					+ "   and pePar.Id = pem.fk_enterprise_id\n" + "   and pem.login_id = '" + us.getLoginId() + "'";
			List list = null;
			try {
				list = this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (null != list && list.size() > 0) {
				dc.add(Restrictions.in("peEnterprise.id", list.toArray(new String[list.size()])));
			}
		}
		dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee");
		dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
		dc.add(Subqueries.propertyNotIn("id", expcetdc));
		return dc;
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

	/** 给主观题打分 */
	public String questionView() {

		return "questionView";
	}

}
