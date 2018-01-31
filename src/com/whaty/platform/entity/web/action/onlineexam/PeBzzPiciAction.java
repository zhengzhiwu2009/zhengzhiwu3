package com.whaty.platform.entity.web.action.onlineexam;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzPici;
import com.whaty.platform.entity.bean.PeBzzPiciStudent;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * @param
 * @version 创建时间：2009-6-22 下午02:15:44
 * @return
 * @throws PlatformException
 *             类说明
 */
public class PeBzzPiciAction extends MyBaseAction<PeBzzPici> {

	private static final long serialVersionUID = 1L;
	private PeBzzstudentbacthService peBzzstudentbacthService;
	private int count;

	/**
	 * 列表加载显示字段
	 * 
	 * @author linjie
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canUpdate = false;
		/* 双击修改 */
		if (capabilitySet.contains(this.servletPath + "_update.action")) {
			canUpdate = true;
		}
		this.getGridConfig().setCapability(true, false, true);
		this.getGridConfig().setTitle("考试信息管理");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("考试编号"), "code");
		this.getGridConfig().addColumn(this.getText("考试名称"), "name");
		
		ColumnConfig columnConfigExamType = new ColumnConfig(this.getText("考试类型"), "enumConstByFlagExamTypes.name", true, true, false, "TextField", true, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagExamTypes'";
		columnConfigExamType.setComboSQL(sql1);
		this.getGridConfig().addColumn(columnConfigExamType);
		this.getGridConfig().addColumn(this.getText("开始时间"), "startDatetime");
		this.getGridConfig().addColumn(this.getText("结束时间"), "endDatetime");
		//this.getGridConfig().addColumn(this.getText("成绩公布时间"), "publishDatetime");
		this.getGridConfig().addColumn(this.getText("成绩公布时间"), "publishDatetime", false, true, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("考试次数"), "examTimes", false, true, false,"");
		this.getGridConfig().addColumn(this.getText("通过分数"), "passScore", false, true, false, Const.score_for_extjs);
		ColumnConfig columnConfigType = new ColumnConfig(this.getText("考试状态"), "enumConstByFlagPiciStatus.name", true, false, true, "TextField", true, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagPiciStatus'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);
		this.getGridConfig().addColumn(this.getText("已添加考生人数"), "PARCOUNT", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("参加考试人数"), "JOINCOUNT", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("通过考试人数"), "PASSCOUNT", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("考试通过率"), "total", false, false, true, "");
		// this.getGridConfig().addMenuFunction(this.getText("添加学员"),
		// "pePcDetail.action?method=student", true, false);
		if (capabilitySet.contains(this.servletPath + "_piciBtn.action")) {// 按钮权限判断
			if (us.getUserLoginType().equals("3")) {
				this.getGridConfig().addRenderScript(this.getText("添加学员"), "{if(record.data['enumConstByFlagExamTypes.name'] =='开放式考试'){return '添加学员';} else{return '<a href=pePcDetail.action?id='+record.data['id']+'&method=student><font color=#0000ff ><u>添加学员</u></font></a>';}}",
						"");
			}
			this.getGridConfig().addRenderScript(this.getText("查看学员"), "{return '<a href=pePcDetail.action?id='+record.data['id']+'&method=mystudent><font color=#0000ff ><u>查看学员</u></font></a>';}",
					"");
			if (us.getUserLoginType().equals("3")) {
				this
						.getGridConfig()
						.addRenderScript(
								this.getText("进入组卷"),
								"{return '<a href=/sso/bzzinteraction_hzphExamManage.action?batch_id='+record.data['id']+'&teacher_id=teacher1&piciName='+encodeURI(encodeURI(record.data['name']))+' target=_blank ><font color=#0000ff ><u>进入组卷</u></font></a>';}",
								"");
				this.getGridConfig().addMenuScript(this.getText("Excel批量导入学员"), "{window.location='/entity/onlineexam/peBzzPici_toUpload.action';}");
				this.getGridConfig().addMenuFunction(this.getText("打开"), "FlagPiciStatus.true");
				this.getGridConfig().addMenuFunction(this.getText("关闭"), "FlagPiciStatus.false");
			}
		} else {
			this.getGridConfig().addRenderScript(this.getText("查看学员"), "{return '<a href=pePcDetail.action?id='+record.data['id']+'&method=mystudent><font color=#0000ff ><u>查看学员</u></font></a>';}",
					"");
			this.getGridConfig().setCapability(false, false, false);

		}
		this.getGridConfig().addColumn(this.getText("备注"), "remark", false, true, false, "TextArea", true, 500);
	}

	public void setEntityClass() {
		this.entityClass = PeBzzPici.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/onlineexam/peBzzPici";
	}

	public void setBean(PeBzzPici instance) {
		super.superSetBean(instance);
	}

	public PeBzzPici getBean() {
		return super.superGetBean();
	}

	/**
	 * 批量添加学员
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toUpload() {// 批量添加学员
		return "toUpload";
	}

	/**
	 * 执行导入数据
	 * 
	 * @return String
	 * @author linjie
	 */
	public String exeUpload() {
		try {
			this.saveUpload(this.get_upload());
			if (this.getMsg() == null) {
				this.setMsg("共有" + this.count + "条数据导入成功！");
			} else {
				this.setMsg(this.getMsg() + "共有" + this.count + "条数据导入成功！");
			}
			if (this.count == 0) {
				this.setTogo(this.getServletPath() + "_toUpload.action");
			} else {
				this.setTogo(this.getServletPath() + ".action");
			}
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
			this.setTogo(this.getServletPath() + "_toUpload.action");
		}
		return "m_msg";
	}

	/**
	 * 执行添加
	 * 
	 * @author linjie
	 */
	public void saveUpload(File file) throws EntityException {
		StringBuffer msg = new StringBuffer();
		Workbook work = null;
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		StringBuffer stuIds = new StringBuffer();// 学员编号ids
		Map<String, Set<String>> uniqueEleMap = new HashMap<String, Set<String>>();
		Set<String> courseIdSet = new HashSet<String>();
		Map electiveMap = null;
		List<Map> electiveMapList = new ArrayList();
		for (int i = 1; i < rows; i++) {

			String stuId = sheet.getCell(0, i).getContents().trim();
			if (stuId != null && !stuId.equals("") && !stuId.equals("null")) {
				electiveMap = new HashMap();
				temp = StringUtils.defaultString(sheet.getCell(2, i).getContents()).trim();
				DetachedCriteria rodc = DetachedCriteria
				.forClass(PeBzzPici.class);
				rodc.add(Restrictions.eq("code",temp ));
				List<PeBzzPici> peBzzPclist = this.getGeneralService().getList(rodc);
				if(!peBzzPclist.get(0).getEnumConstByFlagPiciStatus().getCode().equals("1")){
					throw new EntityException(peBzzPclist.get(0).getCode() + "考试处于打开状态不能导入学员");
				}
				
				stuIds.append("'" + stuId.toUpperCase() + "',");
				String[] codeList = temp.split("\\|");
				for (int j = 0; j < codeList.length; j++) {
					if (!courseIdSet.contains(codeList[j])) {
						courseIdSet.add(codeList[j]);
					}

					if (!StringUtils.defaultString(sheet.getCell(0, i).getContents()).trim().equals("")) {
						// 增加学员不能重复选择考试考试的校验
						Set<String> set = uniqueEleMap.get(StringUtils.defaultString(sheet.getCell(0, i).getContents()).trim());
						if (set == null) {
							set = new HashSet<String>();
						}
						if (set.contains(codeList[j])) {
							msg.append("第" + (i + 1) + "行数据，学员选择的考试存在重复！<br/>");
							continue;
						} else {
							set.add(codeList[j]);
							uniqueEleMap.put(StringUtils.defaultString(sheet.getCell(0, i).getContents()).trim(), set);
						}
					}
				}
				// 验证学生信息
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，系统编号不能为空！<br/>");
					continue;
				}
				if (!checkIsvalid(temp.toUpperCase())) {
					msg.append("第" + (i + 1) + "行数据，系统编号不存在或账号无效！<br/>");
					continue;
				}
				electiveMap.put("student", this.getStudentByRegNo(temp));
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，考试考试编号不能为空！<br/>");
					continue;
				}
				if (!isValidPici(temp)) {
					msg.append("第" + (i + 1) + "行数据，该考试已经开始不能添加学生！<br/>");
					continue;
				}
				if (isValidPcStu(stuId.toUpperCase(), temp)) {
					msg.append("第" + (i + 1) + "行数据，该学生已经加入该考试，不能重复添加！<br/>");
					continue;
				}
				electiveMap.put("pici", this.getPiciByCode(temp));
				electiveMapList.add(electiveMap);
				count++;
			}
		}
		
		if (msg.length() > 0) {
			msg.append("批量信息上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		this.peBzzstudentbacthService.savePiciStudent(electiveMapList);
	}

	/**
	 * 判断学生是否有效
	 * 
	 * @param regNo
	 * @author linjie
	 */
	public boolean checkIsvalid(String regNo) {
		boolean flag = false;
		String sql = "";
		sql = " select pbs.id from pe_bzz_student pbs,sso_user su where pbs.fk_sso_user_id = su.id and su.flag_isvalid  = '2' and upper(pbs.reg_no)='" + regNo + "'";
		try {
			List list = new ArrayList();
			list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0) {
				flag = true;
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 判断考试是否可以添加学员
	 * 
	 * @param code
	 * @author linjie
	 */
	public boolean isValidPici(String code) {
		boolean flag = true;
		String sql = " select id from pe_bzz_pici t where t.start_time<sysdate and t.code='" + code + "'";
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0)
				flag = false;
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 判断该学生是否已选该考试
	 * 
	 * @param stuId
	 * @param code
	 * @author linjie
	 */
	public boolean isValidPcStu(String stuId, String code) {
		boolean flag = false;
		//SELECT * 改为 SELECT 1
		String sql = " select 1 from pe_bzz_pici_student pbps,pe_bzz_student pbs,pe_bzz_pici pbp where pbps.stu_id=pbs.id and pbps.pc_id=pbp.id and pbp.code='" + code + "' and upper(pbs.reg_no)='"
				+ stuId + "'";
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0)
				flag = true;
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 根据编号获取学生
	 * 
	 * @param regNo
	 * @return PeBzzStudent
	 * @author linjie
	 */
	public PeBzzStudent getStudentByRegNo(String regNo) {
		PeBzzStudent student = null;
		String hql = " from PeBzzStudent where regNo='" + regNo + "'";
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getByHQL(hql);
			if (list != null && list.size() > 0)
				student = (PeBzzStudent) list.get(0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;
	}

	/**
	 * 根据编号获取考试
	 * 
	 * @param regNo
	 * @return PeBzzPici
	 * @author linjie
	 */
	public PeBzzPici getPiciByCode(String code) {
		PeBzzPici bzzPici = new PeBzzPici();
		String sql = "from PeBzzPici where code='" + code + "'";
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getByHQL(sql);
			if (list != null && list.size() > 0)
				bzzPici = (PeBzzPici) list.get(0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bzzPici;
	}

	/**
	 * 初始化实体化类
	 * 
	 * @return DetachedCriteria
	 * @author linjie
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {

		DetachedCriteria criteria = DetachedCriteria.forClass(PeBzzPici.class);
		criteria.createAlias("enumConstByFlagPiciStatus", "enumConstByFlagPiciStatus", DetachedCriteria.INNER_JOIN);
		return criteria;
	}

	public Page list() {
		Page page = null;
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("       SELECT * FROM(  ");
			sqlBuffer.append("       SELECT PICI.ID ID,  ");
			sqlBuffer.append("        PICI.CODE CODE,  ");
			sqlBuffer.append("       PICI.NAME NAME,  ");
			sqlBuffer.append("      EC.NAME TYPE,  ");
			sqlBuffer.append("         to_char(PICI.START_TIME ,'yyyy-mm-dd HH24:mi:ss') START_TIME,");
			sqlBuffer.append("       to_char(PICI.END_TIME ,'yyyy-mm-dd HH24:mi:ss')  END ,");
			sqlBuffer.append("        to_char( PICI.PUBLISH_DATE  ,'yyyy-mm-dd HH24:mi:ss') PUBLISH, ");
			sqlBuffer.append("          PICI.EXAM_TIMES EXAMTIMES ,");
			sqlBuffer.append("        PICI.PASSSCORE passScore,");
			sqlBuffer.append("       CONST.NAME STATUS,  ");
			sqlBuffer.append("       NVL(AA.COUN,0) PARCOUNT,  ");
			sqlBuffer.append("        NVL(BB.COUN,0) JOINCOUNT, ");
			sqlBuffer.append("          DECODE( SIGN(SYSDATE - PICI.PUBLISH_DATE) ,-1,'--',  NVL(CC.COUN,0)) PASSCOUNT , ");
			sqlBuffer.append("         DECODE( SIGN(SYSDATE - PICI.PUBLISH_DATE) ,-1,'-- ' ,DECODE (BB.COUN,NULL,0, ROUND(NVL(CC.COUN,0)*100 / BB.COUN, 2)) || '%') total ");

			sqlBuffer.append("         FROM PE_BZZ_PICI PICI ");
			sqlBuffer.append("             JOIN ENUM_CONST CONST ON PICI.STATUS =CONST.ID ");
			sqlBuffer.append("  LEFT JOIN ENUM_CONST EC ON PICI.EXAM_TYPE = EC.ID  ");
			if (!us.getUserLoginType().equals("3")) {
				sqlBuffer.append("  and pici.status ='40288acf3d62b37f013d62b9aeec000x' INNER ");
			}else{
				sqlBuffer.append(" LEFT ");
			}
			sqlBuffer.append("         JOIN (SELECT STU.PC_ID ID, COUNT(*) COUN ");
			sqlBuffer.append("        FROM PE_BZZ_PICI_STUDENT STU ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_STUDENT STUDENT ON STU.STU_ID =STUDENT.ID    ");
			if (!us.getUserLoginType().equals("3")) {
				sqlBuffer.append("          AND STUDENT.FK_ENTERPRISE_ID IN (SELECT ID FROM PE_ENTERPRISE WHERE ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '"
						+ us.getSsoUser().getId() + "') OR FK_PARENT_ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'))   ");
			}
			sqlBuffer.append("         INNER JOIN SSO_USER U ON STUDENT.FK_SSO_USER_ID = U.ID ");
			sqlBuffer.append("         AND U.FLAG_ISVALID = '2' ");
			sqlBuffer.append("        GROUP BY STU.PC_ID) AA ON PICI.ID = AA.ID ");
			sqlBuffer.append("       LEFT JOIN (SELECT STU.PC_ID ID, COUNT(*) COUN  ");
			sqlBuffer.append("       FROM PE_BZZ_PICI_STUDENT STU  ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_STUDENT STUDENT ON STU.STU_ID =STUDENT.ID    ");
			if (!us.getUserLoginType().equals("3")) {
				sqlBuffer.append("          AND STUDENT.FK_ENTERPRISE_ID IN (SELECT ID FROM PE_ENTERPRISE WHERE ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '"
						+ us.getSsoUser().getId() + "') OR FK_PARENT_ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'))   ");
			}
			sqlBuffer.append("       INNER JOIN SSO_USER U ON STUDENT.FK_SSO_USER_ID = U.ID AND STU.SCORE IS NOT NULL ");
			sqlBuffer.append("         AND U.FLAG_ISVALID = '2'   ");
			sqlBuffer.append("         GROUP BY STU.PC_ID) BB ON PICI.ID =BB.ID ");
			sqlBuffer.append("        LEFT JOIN (SELECT STU.PC_ID ID, COUNT(*) COUN ");
			sqlBuffer.append("         FROM PE_BZZ_PICI_STUDENT STU ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_STUDENT STUDENT ON STU.STU_ID =STUDENT.ID    ");
			if (!us.getUserLoginType().equals("3")) {
				sqlBuffer.append("          AND STUDENT.FK_ENTERPRISE_ID IN (SELECT ID FROM PE_ENTERPRISE WHERE ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '"
						+ us.getSsoUser().getId() + "') OR FK_PARENT_ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'))   ");
			}
			sqlBuffer.append("       INNER JOIN SSO_USER U ON STUDENT.FK_SSO_USER_ID = U.ID AND STU.ISPASS ='1' ");
			sqlBuffer.append("         AND U.FLAG_ISVALID = '2'  ");
			sqlBuffer.append("         GROUP BY STU.PC_ID) CC ON PICI.ID =CC.ID  ORDER BY PICI.START_TIME DESC  ");
			sqlBuffer.append("         )SS WHERE 1=1 ");
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
				/* 考试编号 */
				if (name.equals("code")) {
					searchSql.append(" AND UPPER(SS.CODE) LIKE '%" + value.toUpperCase() + "%' ");
				}
				/* 考试名称 */
				if (name.equals("name")) {
					searchSql.append(" AND UPPER(SS.NAME) LIKE '%" + value.toUpperCase() + "%' ");
				}
				/* 开始时间 */
				if (name.equals("startDatetime")) {
					searchSql.append(" AND to_date(SS.START_TIME,'yyyy-MM-dd hh24:mi:ss')  >= to_date('" + value.toUpperCase() + "','yyyy-MM-dd hh24:mi:ss')");
				}
				if (name.equals("endDatetime")) {
					searchSql.append(" AND   to_date(SS.END,'yyyy-MM-dd hh24:mi:ss')   <= to_date('" + value.toUpperCase() + "','yyyy-MM-dd hh24:mi:ss')");
				}
				if (name.equals("publishDatetime")) {
					searchSql.append(" AND  to_date(SS.PUBLISH ,'yyyy-MM-dd hh24:mi:ss')  <= to_date('" + value.toUpperCase() + "','yyyy-MM-dd hh24:mi:ss')");
				}
				/* 业务分类 */
				if (name.equals("enumConstByFlagPiciStatus.name")) {
					searchSql.append(" AND UPPER(SS.STATUS) LIKE '%" + value.toUpperCase() + "%' ");
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
				if(temp.equals("enumConstByFlagPiciStatus.name")){
					temp = "STATUS";
				}
				if(temp.equals("startDatetime")){
					temp = "START_TIME";
				}
				if(temp.equals("endDatetime")){
					temp = "END";
				}	
				if(temp.equals("publishDatetime")){
					temp = "PUBLISH";
				}
				if(temp.equals("total")){
					temp = " to_number(replace(total,'%',''))";
				}
			}
			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
				sqlBuffer.append(" order by " + temp + " desc ");
			}

			else {
				sqlBuffer.append(" order by " + temp + " asc ");
			}
		
			sqlBuffer.append(searchSql);
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 删除选中的考试考试
	 * 
	 * @return Map
	 * @author linjie
	 */
	public Map delete() {
		Map map = new HashMap();
		String msg = "";
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				DetachedCriteria cbdc = DetachedCriteria.forClass(PeBzzPiciStudent.class);
				cbdc.createCriteria("peBzzPici", "peBzzPici");
				cbdc.add(Restrictions.in("peBzzPici.id", ids));

				try {
					// List<StudentCourse> scList =
					// this.getGeneralService().getList(scdc);
					List<PeBzzPiciStudent> cbList = this.getGeneralService().getList(cbdc);
					if (cbList.size() > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "该考试下存有相关联的学员,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try {
					this.getGeneralService().deleteByIds(idList);

					map.put("success", "true");
					map.put("info", "共有" + idList.size() + "条考试删除成功");

				} catch (RuntimeException e) {
					return this.checkForeignKey(e);
				} catch (Exception e1) {
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzPici.class);
		dc.createCriteria("enumConstByFlagExamTypes","enumConstByFlagExamTypes");
		// dc.createCriteria("enumConstByFlagIsvalid",
		// "enumConstByFlagIsvalid");
		// dc.createCriteria("enumConstByFlagOffline",
		// "enumConstByFlagOffline");
		// dc.createCriteria("enumConstByFlagCourseType",
		// "enumConstByFlagCourseType");
		// dc.createCriteria("enumConstByFlagCourseCategory",
		// "enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagIsvisitAfterPass",
		// "enumConstByFlagIsvisitAfterPass", DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagIsFree", "enumConstByFlagIsFree",
		// DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagIsRecommend",
		// "enumConstByFlagIsRecommend", DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagCourseItemType",
		// "enumConstByFlagCourseItemType", DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagContentProperty",
		// "enumConstByFlagContentProperty", DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagCheckStatus",
		// "enumConstByFlagCheckStatus", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();

			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
				map.put("models", list);
			}
			JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}

	/**
	 * 对考试考试信息进行修改
	 * 
	 * @return Map
	 * @author linjie
	 */
	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		String msg = "";
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");

			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzPici.class);
			tempdc.createCriteria("enumConstByFlagPiciStatus");
			tempdc.add(Restrictions.in("id", ids));
			try {
				if (action.equals("FlagPiciStatus.true")) {
					EnumConst enumConstByFlagPiciStatus = this.getMyListService().getEnumConstByNamespaceCode("FlagPiciStatus", "0");
					List<PeBzzPici> sslist = new ArrayList<PeBzzPici>();
					try {
						sslist = this.getGeneralService().getList(tempdc);
					} catch (EntityException e) {
						e.printStackTrace();
					}
					Iterator<PeBzzPici> iterator = sslist.iterator();
					while (iterator.hasNext()) {
						PeBzzPici bzzPici = iterator.next();
						bzzPici.setEnumConstByFlagPiciStatus(enumConstByFlagPiciStatus);
						this.getGeneralService().save(bzzPici);
					}
				} else if (action.equals("FlagPiciStatus.false")) {
					EnumConst enumConstByFlagPiciStatus = this.getMyListService().getEnumConstByNamespaceCode("FlagPiciStatus", "1");
					List<PeBzzPici> sslist = new ArrayList<PeBzzPici>();
					try {
						sslist = this.getGeneralService().getList(tempdc);
					} catch (EntityException e) {
						e.printStackTrace();
					}
					Iterator<PeBzzPici> iterator = sslist.iterator();
					while (iterator.hasNext()) {
						PeBzzPici bzzPici = iterator.next();
						bzzPici.setEnumConstByFlagPiciStatus(enumConstByFlagPiciStatus);
						this.getGeneralService().save(bzzPici);
					}
				}
				map.put("success", "true");
				map.put("info", "操作成功");
			} catch (Exception e) {
				map.put("success", "false");
				map.put("info", "操作失败");
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
	 * 在添加考试编号之前进行检测当前考试编号是否存在
	 * 
	 * @author linjie
	 * @throws EntityException
	 */
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzPici.class);
		dc.add(Restrictions.eq("code", this.getBean().getCode()));
		List list = new ArrayList<PeBzzPici>();
		list = this.getGeneralService().getList(dc);
		if (list.size() > 0)
			throw new EntityException("考试编号已存在，请修改后再试");
		if (this.getBean().getStartDatetime().after(this.getBean().getEndDatetime())) {
			throw new EntityException("考试开始时间不能晚于考试结束时间");
		}
		if(this.getBean().getPublishDatetime() != null){
			if (this.getBean().getEndDatetime().getTime() > (this.getBean().getPublishDatetime().getTime())) {
				throw new EntityException("考试公布时间不能小于等于考试结束时间");
			}
		}
		if(!this.getBean().getEnumConstByFlagExamTypes().getName().equals("开放式考试")){
			if(this.getBean().getPublishDatetime() == null){
				throw new EntityException("常规考试，考试公布时间不能为空");
			}
		}
		EnumConst enumConstByFlagPiciStatus = this.getMyListService().getEnumConstByNamespaceCode("FlagPiciStatus", "1");
		this.getBean().setEnumConstByFlagPiciStatus(enumConstByFlagPiciStatus);
	}

	/**
	 * 在修改考试编号之前进行检测当前考试编号是否存在
	 * 
	 * @author linjie
	 * @throws EntityException
	 */
	public void checkBeforeUpdate() throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzPici.class);
		dc.add(Restrictions.eq("code", this.getBean().getCode()));
		dc.add(Restrictions.ne("id", this.getBean().getId()));
		List list = new ArrayList<PeBzzPici>();
		list = this.getGeneralService().getList(dc);
		if (list.size() > 0)
			throw new EntityException("考试编号已存在，请修改后再试");
		if (this.getBean().getStartDatetime().after(this.getBean().getEndDatetime())) {
			throw new EntityException("考试的报名开始时间不能晚于报名结束时间");
		}
		if(this.getBean().getPublishDatetime() != null){
			if (this.getBean().getEndDatetime().getTime()  > (this.getBean().getPublishDatetime().getTime())) {
				throw new EntityException("考试公布时间不能小于等于考试结束时间");
			}
		}
		
		List<PeBzzPici> pici = this.getGeneralService().getByHQL(" from PeBzzPici where id ='"+this.getBean().getId()+"'");
		if(!pici.get(0).getEnumConstByFlagPiciStatus().getCode().equals("1")){
			throw new EntityException(pici.get(0).getCode() + "考试处于打开状态不能修改");
		}
		
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public PeBzzstudentbacthService getPeBzzstudentbacthService() {
		return peBzzstudentbacthService;
	}

	public void setPeBzzstudentbacthService(PeBzzstudentbacthService peBzzstudentbacthService) {
		this.peBzzstudentbacthService = peBzzstudentbacthService;
	}
}
