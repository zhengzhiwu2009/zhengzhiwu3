package com.whaty.platform.entity.web.action.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBatchDetailAction extends MyBaseAction<StudentBatch> {

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
	private String actionUrl;// 判断是从直播过来的还是从专项过来的
	private String freeze;// 直播是否封版1封版0未封版
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
		this.getGridConfig().setTitle(this.getText("学员列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("具有从业资格"), "enumConstByIsEmployee.name", false);
		// this.getGridConfig().addColumn(this.getText("资格类型"),
		// "peBzzStudent.enumConstByFlagQualificationsType.name");
		ColumnConfig c_name = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
		c_name.setAdd(true);
		c_name.setSearch(true);
		c_name.setList(true);
		c_name.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
		this.getGridConfig().addColumn(c_name);
		if ("student".equals(method)) {
			;
		} else {
			if (null == this.actionUrl || "".equals(this.actionUrl))
				this.getGridConfig().addColumn(this.getText("专项学时数"), "zhuanxiang_HR", false, false, true, null);
			else
				this.getGridConfig().addColumn(this.getText("直播学时数"), "zhuanxiang_HR", false, false, true, null);
			this.getGridConfig().addColumn(this.getText("已获学时数"), "yihuode_HR", false, false, true, null);
			this.getGridConfig().addColumn(this.getText("缴费金额"), "jiaofei_AMOUNT", false, false, true, null);
			this.getGridConfig().addColumn(this.getText("支付/报名时间"), "PAYMENT_DATE", false, false, true, null);
		}
		this.getGridConfig().addColumn(this.getText("所在机构"), "peEnterprise.name", true);
		this.getGridConfig().addColumn(this.getText("是否有效"), "ssoUser.enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("国籍"), "cardType");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo");
		this.getGridConfig().addColumn(this.getText("组别"), "groups");
		this.getGridConfig().addColumn(this.getText("已分配学时"), "ssoUser.sumAmount", false, false, false, null);
		this.getGridConfig().addColumn(this.getText("已使用学时"), "ssoUser.amount", false, false, false, null);
		this.getGridConfig().addColumn(this.getText("剩余学时"), "ssoUser.unUseAmount", false, false, false, null);
		if ("liveSearch".equalsIgnoreCase(actionUrl)) {
			this.getGridConfig().addColumn(this.getText("课程ID"), "courseid", false, false, false, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("开课ID"), "opencourseid", false, false, false, "TextField", false, 100, true);
			this.getGridConfig().addColumn(this.getText("课程CODE"), "coursecode", false, false, false, "TextField", false, 100, true);
		}
		this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a target=\"_blank\" href=\"peBatchDetail_stuviewDetail.action?id=${value}&type=1\">查看详细信息</a>", "id");
		ServletActionContext.getRequest().getSession().setAttribute("flag_x", "subEn");
		if (null == this.actionUrl || "".equals(this.actionUrl)) {
			this.getGridConfig().addRenderScript(this.getText("查看学习详情"), "{return '<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_electivedCourse.action?batchId=" + this.getBatchid() + "&flag=stu&method=mystudent&stuId='+record.data['id']+'\">查看学习详情</a>';}", "id");
		}
		if ("liveSearch".equalsIgnoreCase(actionUrl)) {
			this.getGridConfig().addRenderScript(this.getText("查看学习记录"),
					"{return '<a href=\"/entity/teaching/searchAnyStudent_courseInfo.action?courseId='+record.data['courseid']+'&opencourseId='+record.data['opencourseid']+'&sid='+record.data['id']+'&courseCode='+record.data['coursecode']+'\"  target=\"_blank\">查看学习记录</a>';}", "id");
		}
		if (capabilitySet.contains(this.servletPath + "_StuAdd.action")) {
			if ("student".equals(method) && (null == this.actionUrl || "".equals(this.actionUrl))) {
				this.getGridConfig().addMenuFunction(this.getText("添加学员"), "StuAdd_" + this.getBatchid());
			} else if ("student".equals(method) && (null != this.actionUrl && !"".equals(this.actionUrl))) {
				this.getGridConfig().addMenuFunction(this.getText("添加学员"), "StuAddLive_" + this.getBatchid());
			}
		}
		if (capabilitySet.contains(this.servletPath + "_StuDel.action")) {
			if ("mystudent".equals(method) && !"liveSearch".equalsIgnoreCase(actionUrl) && !"1".equals(freeze)) {
				this.getGridConfig().addMenuFunction(this.getText("删除学员"), "StuDel_" + this.getBatchid());
			}
		}
		if (null == this.actionUrl || "".equals(this.actionUrl))
			this.getGridConfig().addMenuScript(this.getText("返回"), "{location.href='entity/basic/peBzzBatch.action'};");
		else if ("liveSearch".equalsIgnoreCase(actionUrl))
			this.getGridConfig().addMenuScript(this.getText("返回"), "{location.href='/entity/teaching/peBzzCourseLiveSearch.action'};");
		else
			this.getGridConfig().addMenuScript(this.getText("返回"), "{location.href='/entity/teaching/peBzzCourseLiveManager.action'};");
		ActionContext.getContext().getSession().put("id", id);

	}

	public void setEntityClass() {
		this.entityClass = StudentBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peBatchDetail";
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
		String id;
		String ap[] = action.split("_");
		id = ap[1];
		Date dt = new Date();
		if (action.indexOf("StuAddLive") >= 0) {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			if (!"3".equals(us.getUserLoginType())) {// 协会管理员不用判断人数可以直接添加
				try {
					// 已报人数
					String sql1 = "SELECT COUNT(*) FROM STU_BATCH WHERE BATCH_ID = '" + id + "'";
					Object nowStudents = this.getGeneralService().getBySQL(sql1).get(0);
					// 允许人数
					sql1 = "SELECT NVL(PBTC.MAXSTUS,0) FROM PE_BZZ_TCH_COURSE PBTC,PR_BZZ_TCH_OPENCOURSE PBTO,PE_BZZ_BATCH PBB WHERE PBB.ID = PBTO.FK_BATCH_ID AND PBB.ID = '" + id + "' AND PBTO.FK_COURSE_ID = PBTC.ID";
					Object maxStudents = this.getGeneralService().getBySQL(sql1).get(0);
					BigDecimal b1 = new BigDecimal(nowStudents.toString());
					BigDecimal b2 = new BigDecimal(maxStudents.toString());
					if (b1.subtract(b2).doubleValue() + this.getIds().split(",").length > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "报名人数上限为：" + maxStudents + "，还可以添加" + b2.subtract(b1) + "名学员，请重新选择！");
						return map;
					}
				} catch (Exception e) {
					map.clear();
					map.put("success", "false");
					map.put("info", "添加失败：" + e.getMessage() + "，请重新选择学员！");
					return map;
				}
			}
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
					if (action.indexOf("StuAdd") >= 0 || action.indexOf("StuAddLive") >= 0) {
						/*
						 * 如果报名结束时候是当天，就不能再报名，原则上报名结束时间应该是当前日期的23：59：59，
						 * 但数据库查出来是0：0：0 所以 以当前日期减一天 就是将23：59：59这个时间加上
						 */
						if (action.indexOf("StuAddLive") >= 0) {
							if (peBzzBatchlist.get(0).getEndDate().before(new Date())) {
								map.put("success", "false");
								map.put("info", "报名时间已结束,不能报名！");
								return map;
							}
						} else {
							Calendar calendar = Calendar.getInstance();
							calendar.add(Calendar.DAY_OF_MONTH, -1);
							dt = calendar.getTime();
							if (peBzzBatchlist.get(0).getEndDate().before(dt)) {
								map.put("success", "false");
								map.put("info", "报名时间已结束,不能报名！");
								return map;
							}
						}
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
								 * trainingCourseStudent.setPrBzzTchOpencourse(opencourse);
								 * trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
								 * trainingCourseStudent.setPercent(0.00);
								 * trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
								 */
								DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
								dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
								dceletived.createAlias("peBzzStudent", "peBzzStudent");
								dceletived.add(Restrictions.eq("peBzzStudent", peBzzStudent));
								// Lee 注释↓查询选课记录方式：因为公共课程课程对应多个开课ID所以修改查询方式
								// dceletived.add(Restrictions.eq("prBzzTchOpencourse",
								// opencourse));
								// Lee↓新查询选课方式
								dceletived.add(Restrictions.eq("peBzzTchCourse.id", opencourse.getPeBzzTchCourse().getId()));
								DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
								dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
								dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
								dceletiveback.add(Restrictions.eq("peBzzStudent", peBzzStudent));
								// Lee 注释↓查询选课记录方式：因为公共课程课程对应多个开课ID所以修改查询方式
								// dceletiveback.add(Restrictions.eq("prBzzTchOpencourse",
								// opencourse));
								// Lee↓新查询选课方式
								dceletiveback.add(Restrictions.eq("peBzzTchCourse.id", opencourse.getPeBzzTchCourse().getId()));
								try {
									List tempList = this.getGeneralService().getList(dceletived);
									List tempBackList = this.getGeneralService().getList(dceletiveback);
									if ((tempList != null && tempList.size() > 0) || (tempBackList != null && tempBackList.size() > 0)) {
										// map.put("success", "false");
										// map.put("info", "学员：" +
										// peBzzStudent.getRegNo() + "已选课程-" +
										// opencourse.getPeBzzTchCourse().getCode());
										// return map;
									} else {
										PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
										eleBack.setPeBzzStudent(peBzzStudent);
										eleBack.setPrBzzTchOpencourse(opencourse);
										// stuEle.setTrainingCourseStudent(trainingCourseStudent);
										eleBacklist.add(eleBack);
									}
								} catch (EntityException e) {
									e.printStackTrace();
									map.put("success", "false");
									map.put("info", "选课验证失败！");
									return map;
								}
							}
						}
						this.getPeDetailService().saveStuBatchBack(eleBacklist, stuBatchList);
					}
					if (action.indexOf("StuDel") >= 0) {
						if (null != peBzzBatchlist.get(0).getEnumConstByFlagDeploy() && "1".equals(peBzzBatchlist.get(0).getEnumConstByFlagDeploy().getCode())) {
							if (!"2".equals(peBzzBatchlist.get(0).getEnumConstByFlagBatchType().getCode())) {
								map.clear();
								map.put("success", "false");
								map.put("info", "专项已发布，无法删除学生");
								return map;
							}
						}
						if (peBzzBatchlist.get(0).getEndDate().before(new Date())) {
							map.clear();
							map.put("success", "false");
							map.put("info", "报名已经结束，无法删除学生");
							return map;
						}
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
						 * if(!sb.getEnumConstByFlagBatchPayState().getCode().equals("0")) {
						 * map.clear(); map.put("success", "false");
						 * map.put("info",
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
					}
					map.clear();
					map.put("success", "true");
					map.put("info", msg + ids.length + "条记录操作成功");
				}

			} catch (Exception e) {
				e.printStackTrace();
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
	/*
	 * public Page list() { DetachedCriteria dc = null; Page page = null;
	 * StringBuffer sql_buf = new StringBuffer();
	 * 
	 * UserSession us = (UserSession) ServletActionContext
	 * .getRequest().getSession().getAttribute(
	 * SsoConstant.SSO_USER_SESSION_KEY); DetachedCriteria expcetdc = null; //
	 * expcetdc = DetachedCriteria.forClass(StudentBatch.class);
	 * expcetdc.setProjection(Projections.distinct(Property
	 * .forName("peStudent.id"))); expcetdc.createCriteria("peStudent",
	 * "peStudent"); expcetdc.createCriteria("peBzzBatch", "peBzzBatch");
	 * 
	 * if("student".equals(method)){
	 * expcetdc.add(Restrictions.eq("peBzzBatch.id", this.getBatchid())); }else{
	 * ActionContext.getContext().getSession().put("id", id);
	 * expcetdc.add(Restrictions.eq("peBzzBatch.id", id)); }
	 * 
	 * 
	 * dc = DetachedCriteria.forClass(PeBzzStudent.class);
	 * dc.createCriteria("ssoUser", "ssoUser").createAlias(
	 * "enumConstByFlagIsvalid", "enumConstByFlagIsvalid",
	 * DetachedCriteria.INNER_JOIN); dc.add(Restrictions
	 * .eq("ssoUser.enumConstByFlagIsvalid.id", "2"));
	 * dc.createCriteria("peEnterprise", "peEnterprise");
	 * dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee");
	 * dc.createCriteria("enumConstByFlagQualificationsType",
	 * "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
	 * dc.createCriteria("studentBatch.enumConstByFlagBatchPayState",
	 * "enumConstByFlagBatchPayState"); if
	 * (!"3".equalsIgnoreCase(us.getUserLoginType())) { DetachedCriteria
	 * expcetdc2 = DetachedCriteria .forClass(PeEnterpriseManager.class);
	 * expcetdc2.createCriteria("peEnterprise", "peEnterprise");
	 * expcetdc2.createCriteria("ssoUser", "ssoUser");
	 * expcetdc2.add(Restrictions.eq("ssoUser.loginId", us .getLoginId()));
	 * expcetdc2.setProjection(Projections.distinct(Property
	 * .forName("peEnterprise.id")));
	 * 
	 * dc.createCriteria("peEnterprise", "supPeEnterprise",
	 * DetachedCriteria.LEFT_JOIN);
	 * dc.add(Restrictions.or(Subqueries.propertyIn( "peEnterprise.id",
	 * expcetdc2), Subqueries .propertyIn("supPeEnterprise.id", expcetdc2))); }
	 * if ("student".equals(method)) { dc.add(Subqueries.propertyNotIn("id",
	 * expcetdc)); }
	 * 
	 * if ("mystudent".equals(method)) { dc.add(Subqueries.propertyIn("id",
	 * expcetdc)); }
	 * 
	 * dc = setDetachedCriteria(dc, this.superGetBean()); try { page =
	 * getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()),
	 * Integer.parseInt(this.getStart())); } catch (NumberFormatException e) {
	 * e.printStackTrace(); } catch (EntityException e) { e.printStackTrace(); }
	 * return page; }
	 */

	public Page list() {
		Page page = null;
		batchid = this.getBatchid();
		StringBuffer sqlBuffer = new StringBuffer();
		if ("mystudent".equals(method)) {
			sqlBuffer.append(" SELECT  ID,REGNO,TRUENAME,ISEMPLOYEE,  QUALIFICATIONSTYPE,");
			if ("mystudent".equals(method)) {
				sqlBuffer.append("ZHUANXIANG_HR,YIHUODE_HR,JIAOFEI_AMOUNT,PAYMENT_DATE,");
			}
			if ("liveSearch".equalsIgnoreCase(actionUrl)) {
				sqlBuffer.append("ENTERPRISENAME,FLAGISVALID, CARD_TYPE,CARD_NO,GROUPS,SUMAMOUNT, AMOUNT, UNUSEAMOUNT,COURSEID,OPENCOURSEID,COURSECODE");
			} else {
				sqlBuffer.append("ENTERPRISENAME,FLAGISVALID, CARD_TYPE,CARD_NO,GROUPS,SUMAMOUNT, AMOUNT, UNUSEAMOUNT");
			}
			sqlBuffer.append(" FROM (  ");
			sqlBuffer.append("SELECT      PS.ID ID ,PS.REG_NO REGNO,PS.TRUE_NAME TRUENAME, ");
			sqlBuffer.append("CASE PS.IS_EMPLOYEE WHEN '40288ccf3ac50e95013ac5148fde0003' THEN '否' WHEN '40288ccf3ac50e95013ac51504430004' THEN '是' END AS ISEMPLOYEE,  ");
			sqlBuffer.append("EC2.NAME QUALIFICATIONSTYPE,NVL(S_HOUR.S_HR,0) ZHUANXIANG_HR, ");
			sqlBuffer.append("NVL(Y_HOUR.Y_HR,0) YIHUODE_HR,TO_NUMBER(NVL(Y_AMOUNT.P_AMOUNT,0)) JIAOFEI_AMOUNT, ");
			sqlBuffer.append("DECODE(PAY_INFO.PAYMENT_DATE,NULL,'-',TO_CHAR(PAY_INFO.PAYMENT_DATE,'yyyy-MM-dd hh24:mi:ss')) PAYMENT_DATE,PE.NAME ENTERPRISENAME, ");
			sqlBuffer.append("CASE SU.FLAG_ISVALID WHEN '3' THEN '否' WHEN '2' THEN '是' WHEN '3x' THEN '待审核' END AS FLAGISVALID,   ");
			sqlBuffer.append("PS.CARD_TYPE,PS.CARD_NO,PS.GROUPS, ");
			if ("liveSearch".equalsIgnoreCase(actionUrl)) {
				sqlBuffer.append(" '' SUMAMOUNT,'' AMOUNT,'' UNUSEAMOUNT, S_HOUR.COURSEID, S_HOUR.OPENCOURSEID, S_HOUR.COURSECODE FROM PE_BZZ_BATCH PBB  ");
			} else {
				sqlBuffer.append(" '' SUMAMOUNT,'' AMOUNT,'' UNUSEAMOUNT FROM PE_BZZ_BATCH PBB  ");
			}
			// 2014-09-28 李文强 没有添加课程是无法查看学员问题修改
			sqlBuffer.append("join stu_batch sb on sb.batch_id = pbb.id ");
			sqlBuffer.append("JOIN PE_BZZ_STUDENT PS ON sb.stu_id = PS.ID ");
			// bug 430 2014-10-08 zgl BEGIN
			// sqlBuffer.append("left JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBB.ID
			// = PBTO.FK_BATCH_ID ");
			// sqlBuffer.append("left JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSEB
			// ON PBTO.ID = PBTSEB.FK_TCH_OPENCOURSE_ID ");
			// sqlBuffer.append(" left JOIN PE_BZZ_TCH_COURSE PBTC ON
			// PBTO.FK_COURSE_ID = PBTC.ID ");
			// sqlBuffer.append("JOIN PE_BZZ_STUDENT PS ON PBTSEB.FK_STU_ID =
			// PS.ID ");
			// sqlBuffer.append(" LEFT JOIN ENUM_CONST EC2 ON PS.ZIGE = EC2.ID
			// AND EC2.NAMESPACE = 'FlagQualificationsType' ");
			// sqlBuffer.append(" LEFT JOIN PE_ENTERPRISE PE ON
			// PS.FK_ENTERPRISE_ID = PE.ID ");
			sqlBuffer.append(" JOIN SSO_USER SU ON PS.REG_NO = SU.LOGIN_ID  ");
			sqlBuffer.append("  AND SU.flag_isvalid = '2'");
			sqlBuffer.append(" LEFT JOIN PE_ENTERPRISE PE ON PS.FK_ENTERPRISE_ID = PE.ID ");
			sqlBuffer.append(" LEFT JOIN ENUM_CONST EC2 ON PS.ZIGE = EC2.ID AND EC2.NAMESPACE = 'FlagQualificationsType' ");
			// END
			sqlBuffer.append("LEFT JOIN (SELECT  PBOI.ID, DECODE(PBOI.PAYMENT_DATE, NULL, PBTSE.ELECTIVE_DATE, PBOI.PAYMENT_DATE) PAYMENT_DATE,  PBTSE.FK_STU_ID ");
			sqlBuffer.append("            FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sqlBuffer.append("            INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("            ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
			sqlBuffer.append("            AND PBTO.FK_BATCH_ID = '" + batchid + "' ");
			sqlBuffer.append("            LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
			sqlBuffer.append("            ON PBOI.ID = PBTSE.FK_ORDER_ID ");
			sqlBuffer.append("             GROUP BY PBOI.ID, PBOI.PAYMENT_DATE,  PBTSE.FK_STU_ID, PBTSE.ELECTIVE_DATE ");
			sqlBuffer.append(" 			UNION ALL ");
			sqlBuffer.append("           SELECT  PBOI.ID, PBOI.PAYMENT_DATE,  PBTSE.FK_STU_ID ");
			sqlBuffer.append("             FROM PE_BZZ_ORDER_INFO PBOI ");
			sqlBuffer.append("             JOIN ELECTIVE_HISTORY PBTSE ");
			sqlBuffer.append("               ON PBOI.ID = PBTSE.FK_ORDER_ID ");
			sqlBuffer.append("               WHERE PBOI.FK_BATCH_ID = '" + batchid + "'  ");
			sqlBuffer.append("               GROUP BY PBOI.ID, PBOI.PAYMENT_DATE,  PBTSE.FK_STU_ID ");
			sqlBuffer.append(" ) PAY_INFO ");
			sqlBuffer.append("   ON PAY_INFO.FK_STU_ID = SB.STU_ID ");
			// 使用inner join ,去除不匹配的学员
			// 修改学员编号查询2014-10-09 zgl
			if ("liveSearch".equalsIgnoreCase(actionUrl)) {
				sqlBuffer.append(" INNER JOIN (SELECT SB.STU_ID AS FK_STU_ID, NVL(SUM(PBTC.TIME),0) AS S_HR, PBTC.ID COURSEID, PBTO.ID OPENCOURSEID, PBTC.CODE COURSECODE ");
			} else {
				sqlBuffer.append(" INNER JOIN (SELECT SB.STU_ID AS FK_STU_ID, NVL(SUM(PBTC.TIME),0) AS S_HR ");
			}
			sqlBuffer.append("             FROM PE_BZZ_BATCH PBB  ");
			// 增加
			sqlBuffer.append("             JOIN STU_BATCH SB ON SB.BATCH_ID = PBB.ID ");
			sqlBuffer.append("             LEFT JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBB.ID = PBTO.FK_BATCH_ID ");
			sqlBuffer.append("             LEFT JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("              WHERE PBB.ID = '" + batchid + "' ");
			if ("liveSearch".equalsIgnoreCase(actionUrl)) {
				sqlBuffer.append("             GROUP BY SB.STU_ID, PBTC.ID, PBTO.ID, PBTC.CODE) S_HOUR ");
			} else {
				sqlBuffer.append("             GROUP BY SB.STU_ID) S_HOUR ");
			}
			sqlBuffer.append("  ON S_HOUR.FK_STU_ID = SB.STU_ID ");
			sqlBuffer.append(" LEFT JOIN (SELECT PBTSE.FK_STU_ID, SUM(PBTC.TIME) Y_HR ");
			sqlBuffer.append("             FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("             JOIN PE_BZZ_BATCH PBB  ON PBB.ID = PBTO.FK_BATCH_ID ");
			sqlBuffer.append("             JOIN PE_BZZ_TCH_COURSE PBTC  ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("             JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE  ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID  ");
			sqlBuffer.append("             JOIN PE_BZZ_ORDER_INFO PBOT ON PBOT.ID =PBTSE.FK_ORDER_ID  ");
			sqlBuffer.append("             WHERE PBB.ID = '" + batchid + "' AND PBOT.Fk_Batch_Id IS NOT NULL ");
			sqlBuffer.append("               AND ((PBTC.FLAG_IS_EXAM='40288acf3ae01103013ae012940b0001' AND PBTSE.ISPASS = '1' AND PBTSE.COMPLETED_TIME IS NOT NULL) OR (PBTC.FLAG_IS_EXAM<>'40288acf3ae01103013ae012940b0001' AND PBTSE.ISPASS = '1' AND PBTSE.COMPLETED_TIME IS NOT NULL)) ");
			sqlBuffer.append("             GROUP BY PBTSE.FK_STU_ID) Y_HOUR ");
			sqlBuffer.append("  ON Y_HOUR.FK_STU_ID = SB.STU_ID ");
			sqlBuffer.append(" LEFT JOIN (SELECT PBTSE.FK_STU_ID, DECODE(PBTSE.FK_ORDER_ID,NULL,'0',SUM(PBTC.PRICE)) P_AMOUNT ");
			sqlBuffer.append("             FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("             JOIN PE_BZZ_BATCH PBB ON PBB.ID = PBTO.FK_BATCH_ID ");
			sqlBuffer.append("             JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("             JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID  ");
			sqlBuffer.append("            WHERE PBB.ID = '" + batchid + "' ");
			sqlBuffer.append("             GROUP BY PBTSE.FK_STU_ID,PBTSE.FK_ORDER_ID) Y_AMOUNT ");
			sqlBuffer.append("ON Y_AMOUNT.FK_STU_ID = SB.STU_ID WHERE PBB.ID = '" + batchid + "'  ) ");
			sqlBuffer.append("WHERE  1 = 1 ");
		} else {
			sqlBuffer.append(" SELECT  ID,REGNO,TRUENAME,ISEMPLOYEE,  QUALIFICATIONSTYPE,");
			if ("mystudent".equals(method)) {
				sqlBuffer.append("ZHUANXIANG_HR,YIHUODE_HR,PAYMENT_DATE,");
			}
			if ("liveSearch".equalsIgnoreCase(actionUrl)) {
				sqlBuffer.append("ENTERPRISENAME,FLAGISVALID, CARD_TYPE,CARD_NO,GROUPS,SUMAMOUNT, AMOUNT, UNUSEAMOUNT,COURSEID,OPENCOURSEID,COURSECODE");
			} else {
				sqlBuffer.append("ENTERPRISENAME,FLAGISVALID, CARD_TYPE,CARD_NO,GROUPS,SUMAMOUNT, AMOUNT, UNUSEAMOUNT");
			}
			sqlBuffer.append(" FROM   (select distinct ps.ID  ID ,su.login_ID regNo,                                         ");
			sqlBuffer.append("                 ps.TRUE_NAME trueName,                                    ");
			sqlBuffer.append("                 ec1.name IsEmployee,                                 ");
			sqlBuffer.append("                 ec2.name QualificationsType,                         ");
			sqlBuffer.append("                 pe.name enterpriseName,                              ");
			sqlBuffer.append("                 ec3.name FlagIsvalid,                                ");
			sqlBuffer.append("                 ps.CARD_TYPE,                                        ");
			sqlBuffer.append("                 ps.card_no,                                          ");
			sqlBuffer.append("                 ps.groups,                                           ");
			sqlBuffer.append("                 '' sumAmount,                                        ");
			sqlBuffer.append("                 '' amount,                                           ");
			sqlBuffer.append("                 '' unUseAmount                                       ");
			sqlBuffer.append("   from PE_BZZ_STUDENT ps                                             ");
			sqlBuffer.append("  inner join enum_const ec1                                           ");
			sqlBuffer.append("     on ps.is_employee = ec1.id                                       ");
			sqlBuffer.append("    and ec1.namespace = 'IsEmployee'                                  ");
			sqlBuffer.append("  inner join enum_const ec2                                           ");
			sqlBuffer.append("     on ps.ZIGE = ec2.id                                              ");
			sqlBuffer.append("    and ec2.namespace = 'FlagQualificationsType'                      ");
			sqlBuffer.append("  inner join pe_enterprise pe                                         ");
			sqlBuffer.append("     on ps.fk_enterprise_id = pe.id                                   ");
			sqlBuffer.append("  inner join sso_user su                                              ");
			sqlBuffer.append("     on ps.reg_no = su.login_id                                       ");
			sqlBuffer.append("  inner join enum_const ec3                                           ");
			sqlBuffer.append("     on su.FLAG_ISVALID = ec3.id                                      ");
			sqlBuffer.append("    and ec3.namespace = 'FlagIsvalid'                                 ");
			sqlBuffer.append("    and ps.ID not in                                                  ");
			sqlBuffer.append("        (select stu_id                                                ");
			sqlBuffer.append("           from stu_batch                                             ");
			sqlBuffer.append("          where  BATCH_ID = '" + batchid + "') where   su.flag_isvalid = '2' )");
			sqlBuffer.append("WHERE  1 = 1 ");
		}
		try {
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
				if (name.equals("cardType")) {
					name = "card_type";
				}
				if (name.equals("cardNo")) {
					name = "card_no";
				}
				if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "FlagIsvalid";
				}
				if (name.equals("enumConstByFlagQualificationsType.name")) {
					name = "QualificationsType";
				}
				if (name.equals("peEnterprise.name")) {
					name = "enterpriseName";
				}
				sqlBuffer.append(" and " + name + " like '%" + value + "%'");
			} while (true);
			sqlBuffer.append("GROUP BY  ID,REGNO,TRUENAME,ISEMPLOYEE,  QUALIFICATIONSTYPE,");
			// 添加学员没有以下几项 zgl 0923
			if ("mystudent".equals(method)) {
				sqlBuffer.append("ZHUANXIANG_HR,YIHUODE_HR,JIAOFEI_AMOUNT,PAYMENT_DATE,");
			}

			if ("liveSearch".equalsIgnoreCase(actionUrl)) {
				sqlBuffer.append("ENTERPRISENAME,FLAGISVALID, CARD_TYPE,CARD_NO,GROUPS,SUMAMOUNT, AMOUNT, UNUSEAMOUNT,COURSEID,OPENCOURSEID,COURSECODE");
			} else {
				sqlBuffer.append("ENTERPRISENAME,FLAGISVALID, CARD_TYPE,CARD_NO,GROUPS,SUMAMOUNT, AMOUNT, UNUSEAMOUNT");
			}

			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("id")) {
					temp = "regNo";
				}
				if (temp.equals("cardType")) {
					temp = "card_type";
				}
				if (temp.equals("cardNo")) {
					temp = "card_no";
				}
				if (temp.equals("peEnterprise.name")) {
					temp = "enterpriseName";
				}
				if (temp.equals("enumConstByFlagIsvalid.name")) {
					temp = "FlagIsvalid";
				}
				if (temp.equals("enumConstByFlagQualificationsType.name")) {
					temp = "QualificationsType";
				}
				if (temp.equals("enumConstByIsEmployee.name")) {
					temp = "IsEmployee";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if (temp.equals("time")) {
						sqlBuffer.append(" order by to_number(" + temp + ") desc ");
					} else {
						sqlBuffer.append(" order by " + temp + " desc ");
					}

				} else {
					if (temp.equals("time")) {
						sqlBuffer.append(" order by to_number(" + temp + ") asc ");
					} else {
						sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by regNo desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
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
			sql = "select s.id as id from pe_bzz_student s ,pe_enterprise p,pe_enterprise p1,sso_user su where su.id=s.fk_sso_user_id and p.fk_parent_id is null and p1.fk_parent_id=p.id and s.fk_enterprise_id=p1.id and p.id='" + enterprise.getId()
					+ "' and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' union " + "select s.id as id from pe_bzz_student s ,pe_enterprise p,sso_user su " + " where su.id=s.fk_sso_user_id and s.fk_enterprise_id=p.id and p.id='" + enterprise.getId() + "'"
					+ " and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006'";
		} else // 二级企业
		{
			sql = "select a.id as id from pe_bzz_student a,sso_user su where a.fk_enterprise_id='" + enterprise.getId() + "' and su.id=a.fk_sso_user_id and su.flag_isvalid='2' and a.flag_rank_state='402880f827f5b99b0127f5bdadc70006'";
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
		criteria.add(Restrictions.eq("id", id));
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

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getFreeze() {
		return freeze;
	}

	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}
}
