package com.whaty.platform.entity.web.action.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.service.basic.PeDetailService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;

public class PeDetailAction extends MyBaseAction<StudentBatch> {

	private PeDetailService peDetailService;

	private String id;
	private String num;
	private PeEnterprise peEnterprise;
	private PeBzzStudent peBzzStudent;
	private String type;
	private String tempFlag;
	private String method;
	// Lee start 2014年1月22日
	private String sumTimes;// 获取学时总数

	private String sid;

	private String batchid;

	public String getSumTimes() {
		return sumTimes;
	}

	public void setSumTimes(String sumTimes) {
		this.sumTimes = sumTimes;
	}

	// Lee end

	// Lee start 2014年1月6日
	// 缴费人数
	private String jfrs;

	public String getJfrs() {
		return jfrs;
	}

	public void setJfrs(String jfrs) {
		this.jfrs = jfrs;
	}

	// Lee end
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
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false);
		Set capabilitySet = us.getUserPriority().keySet();
		// Lee start 2014年1月6日
		if ("y".equals(jfrs)) {
			this.getGridConfig().setTitle(this.getText("缴费学员列表"));
			this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", false);
			this.getGridConfig().addColumn(this.getText("姓名"), "trueName", true, false, true, "textField");
			this.getGridConfig().addColumn(this.getText("性别"), "gender", false);
			this.getGridConfig().addColumn(this.getText("学历"), "education", false);
			this.getGridConfig().addColumn(this.getText("民族"), "folk", false);
			this.getGridConfig().addColumn(this.getText("出生日期"), "birthdayDate", false);
			this.getGridConfig().addColumn(this.getText("移动电话"), "mobilePhone", true, false, true, "textField");
			this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", true, false, true, "textField");
			this.getGridConfig().addColumn(this.getText("所在机构"), "peEnterprise.name", false);
			this.getGridConfig().addColumn(this.getText("国籍"), "cardType", false);
			this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo", true, false, true, "textField");
			this.getGridConfig().addColumn(this.getText("组别"), "groups", false);
			String url = "{history.back()}";
			this.getGridConfig().addMenuScript(this.getText("返回"), url);
		} else {
			if ("sum".equals(sumTimes)) {// 有获取学时总数字段
				this.getGridConfig().setTitle(this.getText("学员列表"));
				this.getGridConfig().addColumn(this.getText("ID"), "id", false);
				this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, false, true, "textField");
				this.getGridConfig().addColumn(this.getText("具有从业资格"), "cyName", false);
				ColumnConfig columnConfigQualificationsType = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name", true, false, true, "TextField",
						false, 100, "");
				String sql22 = "select a.id ,a.name from enum_const a where a.namespace='FlagQualificationsType'";
				columnConfigQualificationsType.setComboSQL(sql22);
				this.getGridConfig().addColumn(columnConfigQualificationsType);
				this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
				this.getGridConfig().addColumn(this.getText("所在机构"), "peEnterprise.name", true);
				ColumnConfig columnConfigActive = new ColumnConfig(this.getText("是否有效"), "enumConstByFlagActive.name", true, false, true, "TextField", false, 100, "");
				String sql33 = "select a.id ,a.name from enum_const a where a.namespace='FlagActive'";
				columnConfigActive.setComboSQL(sql33);
				this.getGridConfig().addColumn(columnConfigActive);
				this.getGridConfig().addColumn(this.getText("国籍"), "cardType");
				this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo");
				this.getGridConfig().addColumn(this.getText("组别"), "groups");
				this.getGridConfig().addColumn(this.getText("已获学时"), "sumTimes", false);
				this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a target=\"_blank\" href=\"peDetail_stuviewDetail.action?id=${value}&type=1\">查看详细信息</a>", "id");
				ServletActionContext.getRequest().getSession().setAttribute("flag_x", "peDetail");
				this.getGridConfig()
						.addRenderScript(
								this.getText("查看学习详情"),
								"{return '<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_electivedCourse.action?flag=stu&method=mystudent&stuId='+record.data['id']+'\">查看学习详情</a>';}",
								"id");
				String url = "{history.back()}";
				if (capabilitySet.contains(this.servletPath + "_StuAdd.action")) {
					if ("student".equals(method)) {
						this.getGridConfig().addMenuFunction(this.getText("加入培训名单"), "StuAdd_" + this.getBatchid());
					}
				}
				if (capabilitySet.contains(this.servletPath + "_StuDel.action")) {
					if ("mystudent".equals(method)) {
						this.getGridConfig().addMenuFunction(this.getText("从培训名单删除"), "StuDel_" + this.getBatchid());
					}
				}
				this.getGridConfig().addMenuScript(this.getText("返回"), url);
				ActionContext.getContext().getSession().put("id", id);
			} else {
				// Lee end
				this.getGridConfig().setTitle(this.getText("学员列表"));
				this.getGridConfig().addColumn(this.getText("ID"), "id", false);
				this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, false, true, "textField");
				this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
				this.getGridConfig().addColumn(this.getText("所在机构"), "peEnterprise.name", true);
				this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, false, true, "textField");
				this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "textField");
				if ("56".indexOf(us.getUserLoginType()) < 0) {// 非监管
					this.getGridConfig().addColumn(this.getText("具有从业资格"), "enumConstByIsEmployee.name", false);
					ColumnConfig c_name = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
					c_name.setAdd(true);
					c_name.setSearch(true);
					c_name.setList(true);
					c_name.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
					this.getGridConfig().addColumn(c_name);
				}
				if (!"131AF5EC87836928E0530100007F9F54".equals(us.getRoleId())) {
					this.getGridConfig().addColumn(this.getText("是否有效"), "ssoUser.enumConstByFlagIsvalid.name");
					this.getGridConfig().addColumn(this.getText("国籍"), "cardType");
				}
				if ("131AF5EC87836928E0530100007F9F54".equals(us.getRoleId())) {
					this.getGridConfig().addColumn(this.getText("工作部门"), "cardType");
				}
				this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo");
				this.getGridConfig().addColumn(this.getText("组别"), "groups");
				this.getGridConfig().addColumn(this.getText("已分配学时"), "ssoUser.sumAmount", false, false, false, null);
				this.getGridConfig().addColumn(this.getText("已使用学时"), "ssoUser.amount", false, false, false, null);
				this.getGridConfig().addColumn(this.getText("剩余学时"), "ssoUser.unUseAmount", false, false, false, null);
				this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a target=\"_blank\" href=\"peDetail_stuviewDetail.action?id=${value}&type=1\">查看详细信息</a>", "id");
				ServletActionContext.getRequest().getSession().setAttribute("flag_x", "peDetail");
				this.getGridConfig()
						.addRenderScript(
								this.getText("查看学习详情"),
								"{return '<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_electivedCourse.action?flag=stu&method=mystudent&stuId='+record.data['id']+'\">查看学习详情</a>';}",
								"id");
				String url = "";
				url = "{history.back()}";
				if (capabilitySet.contains(this.servletPath + "_StuAdd.action")) {
					if ("student".equals(method)) {
						this.getGridConfig().addMenuFunction(this.getText("加入培训名单"), "StuAdd_" + this.getBatchid());
					}
				}
				if (capabilitySet.contains(this.servletPath + "_StuDel.action")) {
					if ("mystudent".equals(method)) {
						this.getGridConfig().addMenuFunction(this.getText("从培训名单删除"), "StuDel_" + this.getBatchid());
					}
				}
				this.getGridConfig().addMenuScript(this.getText("返回"), url);
				ActionContext.getContext().getSession().put("id", id);
			}
		}
	}

	public void setEntityClass() {
		this.entityClass = StudentBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peDetail";
	}

	/**
	 * 重写框架方法--列更新
	 * 
	 * @author linjie
	 * @return
	 */
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		/*
		 * String id = ActionContext.getContext().getSession().get("id")
		 * .toString().trim();
		 */
		String id;
		String ap[] = action.split("_");
		if (action.indexOf("StuAdd") >= 0) {
			id = ap[1];
		} else {
			id = ActionContext.getContext().getSession().get("id").toString().trim();
		}
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria rodc = DetachedCriteria.forClass(PeBzzBatch.class);
				rodc.add(Restrictions.eq("id", id));
				List<PeBzzBatch> peBzzBatchlist = this.getGeneralService().getList(rodc);

				DetachedCriteria peprdc = DetachedCriteria.forClass(PeBzzStudent.class);
				peprdc.add(Restrictions.in("id", ids));
				List<PeBzzStudent> studentlist = this.getGeneralService().getList(peprdc);
				PeBzzStudent peBzzStudent;
				if (studentlist.size() > 0) {
					EnumConst enumConstByFlagBatchPayState = this.getMyListService().getEnumConstByNamespaceCode("FlagBatchPayState", "0");
					DetachedCriteria opdc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
					opdc.createCriteria("enumConstByFlagChoose", "enumConstByFlagChoose", DetachedCriteria.LEFT_JOIN);

					opdc.add(Restrictions.eq("peBzzBatch.id", id));
					opdc.add(Restrictions.eq("enumConstByFlagChoose.code", "1"));
					List<PrBzzTchOpencourse> oplist = this.getGeneralService().getList(opdc);
					List eleBacklist = new ArrayList();
					List<StudentBatch> stuBatchList = new ArrayList<StudentBatch>();
					if (action.indexOf("StuAdd") >= 0) {
						/*
						 * if (peBzzBatchlist.get(0).getStartDate().before( new
						 * Date())) { map.clear(); map.put("success", "false");
						 * map.put("info", "专项已经开始，无法添加学生"); return map; }
						 */
						for (int n = 0; n < studentlist.size(); n++) {
							peBzzStudent = studentlist.get(n);
							StudentBatch studentBatch = new StudentBatch();
							studentBatch.setPeStudent(peBzzStudent);
							studentBatch.setPeBzzBatch(peBzzBatchlist.get(0));
							studentBatch.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
							// this.getGeneralService().save(studentBatch);
							stuBatchList.add(studentBatch);
							for (PrBzzTchOpencourse opencourse : oplist) {
								/*
								 * TrainingCourseStudent trainingCourseStudent =
								 * new TrainingCourseStudent();
								 * trainingCourseStudent
								 * .setPrBzzTchOpencourse(opencourse);
								 * trainingCourseStudent
								 * .setSsoUser(peBzzStudent.getSsoUser());
								 * trainingCourseStudent.setPercent(0.00);
								 * trainingCourseStudent
								 * .setLearnStatus(StudyProgress.UNCOMPLETE);
								 */
								DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
								dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
								dceletived.createAlias("peBzzStudent", "peBzzStudent");
								dceletived.add(Restrictions.eq("peBzzStudent", peBzzStudent));
								dceletived.add(Restrictions.eq("prBzzTchOpencourse", opencourse));

								DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
								dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
								dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
								dceletiveback.add(Restrictions.eq("peBzzStudent", peBzzStudent));
								dceletiveback.add(Restrictions.eq("prBzzTchOpencourse", opencourse));
								try {
									List tempList = this.getGeneralService().getList(dceletived);
									List tempBackList = this.getGeneralService().getList(dceletiveback);
									if ((tempList != null && tempList.size() > 0) || (tempBackList != null && tempBackList.size() > 0)) {
										map.put("success", "false");
										map.put("info", "学员：" + peBzzStudent.getRegNo() + "已选课程-" + opencourse.getPeBzzTchCourse().getCode());
										return map;
									}
								} catch (EntityException e) {
									e.printStackTrace();
									map.put("success", "false");
									map.put("info", "选课验证失败！");
									return map;
								}
								PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
								eleBack.setPeBzzStudent(peBzzStudent);
								eleBack.setPrBzzTchOpencourse(opencourse);
								// stuEle.setTrainingCourseStudent(trainingCourseStudent);
								eleBacklist.add(eleBack);
							}
						}
						// this.getPeDetailService().saveStuBatch(list,
						// stuBatchList);
						// 改为所有未支付数据放入back表
						this.getPeDetailService().saveStuBatchBack(eleBacklist, stuBatchList);
						msg = "加入培训名单";
					}
					if (action.indexOf("StuDel") >= 0) {
						if (peBzzBatchlist.get(0).getStartDate().before(new Date())) {
							map.clear();
							map.put("success", "false");
							map.put("info", "报名已经开始，无法删除学生");
							return map;
						}
						// 获取学生专项id列表
						DetachedCriteria dcStuBatch = DetachedCriteria.forClass(StudentBatch.class);
						dcStuBatch.add(Restrictions.eq("peBzzBatch.id", id));
						dcStuBatch.add(Restrictions.in("peStudent.id", ids));
						dcStuBatch.createCriteria("enumConstByFlagBatchPayState");
						List<StudentBatch> sbList = this.getGeneralService().getList(dcStuBatch);
						dcStuBatch.setProjection(Projections.distinct(Projections.property("id")));
						List stuBatchIdList = this.getGeneralService().getList(dcStuBatch);
						/*
						 * 之前逻辑： 学员已经购买此专项，不能删除 ； 现在逻辑： 专项开始就不能删除 ； for(int i=0;
						 * i<sbList.size(); i++) { StudentBatch sb =
						 * (StudentBatch)sbList.get(i);
						 * if(!sb.getEnumConstByFlagBatchPayState
						 * ().getCode().equals("0")) { map.clear();
						 * map.put("success", "false"); map.put("info",
						 * sb.getPeStudent().getName()+"学员已经购买此专项，不能删除"); return
						 * map; } }
						 */// 获取学生在该专项中选课id列表
						DetachedCriteria dcEletive = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
						dcEletive.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzBatch", "peBzzBatch");
						dcEletive.add(Restrictions.eq("peBzzBatch.id", id));
						dcEletive.add(Restrictions.in("peBzzStudent.id", ids));
						dcEletive.setProjection(Projections.distinct(Projections.property("id")));
						List electiveIdList = this.getGeneralService().getList(dcEletive);

						// 执行删除
						// this.getPeDetailService().deleteStuBatch(stuBatchIdList,
						// electiveIdList);
						// 改为删除back数据
						this.getPeDetailService().deleteStuBatchBack(stuBatchIdList, electiveIdList);
						msg = "从培训名单中删除";
					}
					map.clear();
					map.put("success", "true");
					map.put("info", msg + ids.length + "条记录操作成功");
				}

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
	 * @author Lee 2014年1月6日 替代上边的public DetachedCriteria
	 *         initDetachedCriteria()方法 原方法体移到else中
	 */
	public Page list() {
		DetachedCriteria dc = null;
		Page page = null;
		StringBuffer sql_buf = new StringBuffer();
		if ("y".equals(jfrs)) {
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer
					.append(" SELECT * FROM (SELECT REG_NO AS REGNO,TRUE_NAME AS TRUENAME,GENDER,EDUCATION,FOLK,BIRTHDAY,MOBILE_PHONE AS MOBILEPHONE,EMAIL,E.NAME,CARD_TYPE AS CARDTYPE,CARD_NO AS CARDNO,GROUPS "
							+ " FROM PE_BZZ_STUDENT A INNER JOIN STU_BATCH B ON A.ID = B.STU_ID INNER JOIN PE_BZZ_BATCH C ON B.BATCH_ID = C.ID INNER JOIN ENUM_CONST D ON B.FLAG_BATCHPAYSTATE = D.ID "
							+ " LEFT JOIN PE_ENTERPRISE E ON A.ENTERPRISE_ID = E.ID WHERE D.NAMESPACE = 'FlagBatchPayState' AND D.CODE = '1' AND C.ID = '" + id + "') WHERE 1 = 1 ");
			try {
				this.setSqlCondition(sqlBuffer);
				page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else {
			// Lee start 2014年1月22日
			if ("enterprise".equalsIgnoreCase(type)) {
				if ("sum".equals(sumTimes)) {
					try {
						ActionContext context = ActionContext.getContext();
						String zglx = "";
						String szjg = "";
						String sfyx = "";
						if (context.getParameters() != null) {
							Map params = this.getParamsMap();
							if (params != null) {
								if (params.get("search__enumConstByFlagQualificationsType.name") != null) {
									String[] completeDate = (String[]) params.get("search__enumConstByFlagQualificationsType.name");
									if (completeDate.length == 1 && !StringUtils.defaultString(completeDate[0]).equals("")) {
										zglx = completeDate[0];
										params.remove("search__enumConstByFlagQualificationsType.name");
									}
								}
								if (params.get("search__peEnterprise.name") != null) {
									String[] completeDate = (String[]) params.get("search__peEnterprise.name");
									if (completeDate.length == 1 && !StringUtils.defaultString(completeDate[0]).equals("")) {
										szjg = completeDate[0];
										params.remove("search__peEnterprise.name");
									}
								}
								if (params.get("search__enumConstByFlagActive.name") != null) {
									String[] completeDate = (String[]) params.get("search__enumConstByFlagActive.name");
									if (completeDate.length == 1 && !StringUtils.defaultString(completeDate[0]).equals("")) {
										sfyx = completeDate[0];
										params.remove("search__enumConstByFlagActive.name");
									}
								}
								context.setParameters(params);
							}
						}
						UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
						String pm_sql = "SELECT * FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getId() + "'";
						List pm_list = new ArrayList();

						pm_list = this.getGeneralService().getBySQL(pm_sql);
						String app_str = "SELECT * FROM (SELECT AA.*, NVL(BB.SUMTIMES,0) SUMTIMES FROM ( SELECT A.ID, A.REG_NO AS REGNO, ";
						if (!"131AF5EC87836928E0530100007F9F54".equals(us.getRoleId())) {
							app_str += "E.NAME AS CYNAME, F.NAME AS ZGNAME,";
						}
						app_str += " A.TRUE_NAME AS TRUENAME, D.NAME AS PEMNAME, ";
						if (!"131AF5EC87836928E0530100007F9F54".equals(us.getRoleId())) {
							app_str += "C.NAME AS YXNAME, A.CARD_TYPE AS CARDTYPE, ";
						}
						if ("131AF5EC87836928E0530100007F9F54".equals(us.getRoleId())) {
							app_str += " A.DEPARTMENT, ";
						}
						app_str += " A.CARD_NO AS CARDNO, A.GROUPS FROM PE_BZZ_STUDENT A INNER JOIN SSO_USER B ON A.FK_SSO_USER_ID = B.ID LEFT OUTER JOIN ENUM_CONST C ON B.FLAG_ISVALID = C.ID INNER JOIN PE_ENTERPRISE D ON A.FK_ENTERPRISE_ID = D.ID ";
						app_str += "INNER JOIN ENUM_CONST E ON A.IS_EMPLOYEE = E.ID INNER JOIN ENUM_CONST F ON A.ZIGE = F.ID ";
						app_str += " WHERE ";
						// Lee start 2014年2月14日
						if (pm_list == null || pm_list.size() == 0) {// 协会管理员查询所有机构
							app_str += " 1 = 1 ";
						} else {// 集体用户 查询本机构
							// 原版 ↓
							app_str += " A.FK_ENTERPRISE_ID IN (SELECT A.FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A INNER JOIN SSO_USER G ON A.FK_SSO_USER_ID = G.ID INNER JOIN PE_ENTERPRISE H ON A.FK_ENTERPRISE_ID = H.ID WHERE A.FK_SSO_USER_ID = '"
									+ us.getId() + "') ";
						}
						// Lee end
						app_str += " AND A.ID IN (SELECT I.ID FROM STU_BATCH A INNER JOIN PE_BZZ_STUDENT I ON A.STU_ID = I.ID INNER JOIN PE_BZZ_BATCH J ON A.BATCH_ID = J.ID WHERE J.ID = '"
								+ this.id
								+ "')) AA LEFT JOIN (SELECT A.ID, SUM (F.TIME) AS SUMTIMES FROM PE_BZZ_STUDENT A INNER JOIN STU_BATCH B ON A.ID = B.STU_ID INNER JOIN (SELECT ID FROM PE_BZZ_BATCH WHERE ID = '"
								+ this.id
								+ "') C ON B.BATCH_ID = C.ID INNER JOIN (SELECT DISTINCT FK_STU_ID,FK_TCH_OPENCOURSE_ID FROM PR_BZZ_TCH_STU_ELECTIVE WHERE COMPLETED_TIME IS NOT NULL) D ON A.ID = D.FK_STU_ID INNER JOIN PR_BZZ_TCH_OPENCOURSE E ON D.FK_TCH_OPENCOURSE_ID = E.ID AND E.FK_BATCH_ID = C.ID INNER JOIN PE_BZZ_TCH_COURSE F ON E.FK_COURSE_ID = F.ID GROUP BY A.ID ) BB ON AA.ID = BB.ID WHERE 1 = 1 ";
						sql_buf.append(app_str);
						if (!"".equals(zglx)) {
							sql_buf.append(" AND ZGNAME LIKE '%" + zglx + "%' ");
						}
						if (!"".equals(szjg)) {
							sql_buf.append(" AND PEMNAME LIKE '%" + szjg + "%' ");
						}
						if (!"".equals(sfyx)) {
							sql_buf.append(" AND YXNAME LIKE '%" + sfyx + "%' ");
						}
						sql_buf.append(" ) WHERE 1 = 1 ");
						try {
							this.setSqlCondition(sql_buf);
							page = this.getGeneralService().getByPageSQL(sql_buf.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (EntityException e) {
							e.printStackTrace();
						}
						return page;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					// Lee end
					if ("mystudent".equals(method)) {// 专项培训
						UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
						dc = DetachedCriteria.forClass(PeBzzStudent.class);
						dc.createCriteria("peEnterprise", "peEnterprise");
						dc.createCriteria("ssoUser", "ssoUser");
						dc.createCriteria("ssoUser.enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
						DetachedCriteria manDc = DetachedCriteria.forClass(PeEnterpriseManager.class);
						manDc.createCriteria("ssoUser", "ssoUser");
						manDc.createCriteria("peEnterprise", "peEnterprise");
						manDc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
						manDc.setProjection(Property.forName("peEnterprise"));
						dc.add(Property.forName("peEnterprise").in(manDc));// 学员所属机构
						DetachedCriteria sbDc = DetachedCriteria.forClass(StudentBatch.class);
						sbDc.createCriteria("peBzzBatch", "peBzzBatch");
						sbDc.createCriteria("peStudent", "peStudent");
						sbDc.setProjection(Property.forName("peStudent.id"));
						sbDc.add(Restrictions.eq("peBzzBatch.id", this.id));
						dc.add(Property.forName("id").in(sbDc)); // 专项中存在的学员
						dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee");
						dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType");
					} else {
						dc = DetachedCriteria.forClass(PeBzzStudent.class);
						dc.createCriteria("ssoUser", "ssoUser");
						dc.createCriteria("ssoUser.enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
						dc.createAlias("peEnterprise", "peEnterprise");
						dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee", DetachedCriteria.LEFT_JOIN);
						dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
						dc.add(Restrictions.eq("peEnterprise.id", this.getId()));
					}
				}
			} else {
				UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
				DetachedCriteria expcetdc = null;
				expcetdc = DetachedCriteria.forClass(StudentBatch.class);
				expcetdc.setProjection(Projections.distinct(Property.forName("peStudent.id")));
				expcetdc.createCriteria("peStudent", "peStudent");
				expcetdc.createCriteria("peBzzBatch", "peBzzBatch");
				if ("student".equals(method)) {
					expcetdc.add(Restrictions.eq("peBzzBatch.id", this.getBatchid()));
				} else {
					ActionContext.getContext().getSession().put("id", id);
					expcetdc.add(Restrictions.eq("peBzzBatch.id", id));
				}
				dc = DetachedCriteria.forClass(PeBzzStudent.class);
				dc.createCriteria("ssoUser", "ssoUser").createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.INNER_JOIN);
				dc.add(Restrictions.eq("ssoUser.enumConstByFlagIsvalid.id", "2"));
				dc.createCriteria("peEnterprise", "peEnterprise");
				dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee");
				dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
				dc.createCriteria("studentBatch.enumConstByFlagBatchPayState", "enumConstByFlagBatchPayState");
				if (!"3".equalsIgnoreCase(us.getUserLoginType())) {
					DetachedCriteria expcetdc2 = DetachedCriteria.forClass(PeEnterpriseManager.class);
					expcetdc2.createCriteria("peEnterprise", "peEnterprise");
					expcetdc2.createCriteria("ssoUser", "ssoUser");
					expcetdc2.add(Restrictions.eq("ssoUser.loginId", us.getLoginId()));
					expcetdc2.setProjection(Projections.distinct(Property.forName("peEnterprise.id")));
					dc.createCriteria("peEnterprise", "supPeEnterprise", DetachedCriteria.LEFT_JOIN);
					dc.add(Restrictions.or(Subqueries.propertyIn("peEnterprise.id", expcetdc2), Subqueries.propertyIn("supPeEnterprise.id", expcetdc2)));
				}
				if ("student".equals(method)) {
					dc.add(Subqueries.propertyNotIn("id", expcetdc));
				}
				if ("mystudent".equals(method)) {
					dc.add(Subqueries.propertyIn("id", expcetdc));
				}
			}
			dc = setDetachedCriteria(dc, this.superGetBean());
			try {
				page = getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return page;
	}

	/**
	 * 查看机构信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewDetail() {
		DetachedCriteria viwedc = DetachedCriteria.forClass(PeEnterprise.class);
		viwedc.add(Restrictions.eq("id", id));
		PeEnterprise enterprise = null;
		try {
			enterprise = this.getGeneralService().getSubEnterprise(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setPeEnterprise(enterprise);
		return "viewDetail";
	}

	/**
	 * 查看机构管理员信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewPeDetail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.add(Restrictions.eq("loginId", us.getLoginId()));
		List peList = new ArrayList();
		try {
			peList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}

		PeEnterpriseManager pe = (PeEnterpriseManager) peList.get(0);

		PeEnterprise enterprise = null;
		try {
			enterprise = this.getGeneralService().getSubEnterprise(pe.getPeEnterprise().getId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setPeEnterprise(enterprise);

		String sql = "";
		if (us.getUserLoginType().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006")) // 一级企业
		{
			sql = "select s.id as id from pe_bzz_student s ,pe_enterprise p,pe_enterprise p1,sso_user su where su.id=s.fk_sso_user_id and p.fk_parent_id is null and p1.fk_parent_id=p.id and s.fk_enterprise_id=p1.id and p.id='"
					+ enterprise.getId()
					+ "' and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' union "
					+ "select s.id as id from pe_bzz_student s ,pe_enterprise p,sso_user su "
					+ " where su.id=s.fk_sso_user_id and s.fk_enterprise_id=p.id and p.id='"
					+ enterprise.getId() + "'" + " and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006'";
		} else // 二级企业
		{
			sql = "select a.id as id from pe_bzz_student a,sso_user su where a.fk_enterprise_id='" + enterprise.getId()
					+ "' and su.id=a.fk_sso_user_id and su.flag_isvalid='2' and a.flag_rank_state='402880f827f5b99b0127f5bdadc70006'";
		}
		List stuList = null;
		try {
			stuList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setNum(stuList.size() + "");

		return "viewPeDetail";
	}

	/**
	 * 查看学生信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String stuviewDetail() {

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us.getUserLoginType().equals("3")) {
			tempFlag = "1";
		} else {
			tempFlag = "0";
		}
		this.setType(type);
		DetachedCriteria criteria = DetachedCriteria.forClass(PeBzzStudent.class);
		// criteria.createCriteria("peBzzBatch", "peBzzBatch");
		// criteria.createCriteria("peEnterprise", "peEnterprise");

		if (null != this.getId() && !"".equals(this.getId()) && this.getId().indexOf("-") > 0) {
			String[] strAyy = this.getId().split("-");
			this.setSid(strAyy[0]);

		}
		if ("".equals(this.getSid()) || this.getSid() == null)// 查看学生信息功能
		{
			criteria.add(Restrictions.eq("id", id));
		} else {
			criteria.add(Restrictions.eq("id", this.getSid()));
		}
		try {
			this.setPeBzzStudent(this.getGeneralService().getStudentInfo(criteria));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "stuinfo";
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

	private PeBzzstudentbacthService peBzzstudentbacthService;
	private int count;

	/**
	 * 更新学生信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String fixStudent() {
		String cardNo = this.getIds();
		if (cardNo == null || "".equals(cardNo)) {
			cardNo = "1";
		}
		String m = this.peBzzstudentbacthService.updatestu(cardNo, "2", this.getCount());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		out.print(m);
		this.setMsg(m);
		return "msg";
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

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
}
