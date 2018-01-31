package com.whaty.platform.entity.web.action.information.vote;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.bean.PrVoteSubQuestion;
import com.whaty.platform.entity.bean.PrVoteSuggest;
import com.whaty.platform.entity.bean.PrVoteUserQuestion;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.information.PeVotePaperService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 投票管理action
 * 
 * @author 李冰
 * 
 */
public class PeVotePaperAction extends MyBaseAction {
	PeVotePaperService peVotePaperService;
	private String togo;
	List<PeVotePaper> peVotePaperList; // 调查问卷列表
	HashMap map; // 学期和所开课程 问题和该问题投票最大数量
	List<EnumConst> enumConstList; // 调查问卷类型
	PeVotePaper peVotePaper; // 添加或修改时传递参数
	String course_keywords; // 所选择的课程
	String semester_id; // 所选择的学期
	String type; // 调查问卷类型
	String active; // 是否发布
	String canSuggest; // 是否可以添加建议
	String viewSuggest; // 是否可以查看建议
	String limitIp; // 是否限制ip
	String limitSession; // 是否限制会话
	List<PrVoteQuestion> prVoteQuestionList; // 问卷包含的问题
	List courses; // 调查问卷修改时用于保存原课程
	int canVote;// 用户是否能够提交调查问卷 0不可以 1可以
	int pastDue;// 调查问卷日期是否有效 0有效 1还未开始 2已经过期
	String checkboxQuestion; // 来记录用户多选题所选的选项
	String questionNoteValue;// 用来记录主观题的
	String suggest;// 用户所填建议
	long voteNumber; // 参加投票的人数
	List<PrVoteSubQuestion> prVoteSubQuestionList;// 问卷包含的主观题
	List<PrVoteUserQuestion> prVoteUserQuestionList;// 问卷包含的主观题
	private String canupdate;

	public List<PrVoteUserQuestion> getPrVoteUserQuestionList() {
		return prVoteUserQuestionList;
	}

	public void setPrVoteUserQuestionList(List<PrVoteUserQuestion> prVoteUserQuestionList) {
		this.prVoteUserQuestionList = prVoteUserQuestionList;
	}

	public String getCanupdate() {
		return canupdate;
	}

	public void setCanupdate(String canupdate) {
		this.canupdate = canupdate;
	}

	public List<PrVoteSubQuestion> getPrVoteSubQuestionList() {
		return prVoteSubQuestionList;
	}

	public void setPrVoteSubQuestionList(List<PrVoteSubQuestion> prVoteSubQuestionList) {
		this.prVoteSubQuestionList = prVoteSubQuestionList;
	}

	/**
	 * 转向添加调查问卷
	 * 
	 * @return
	 */
	public String toAddVotePaper() {
		HashMap map = new HashMap();
		try {
			DetachedCriteria dcPeSemester = DetachedCriteria.forClass(PeSemester.class);
			List<PeSemester> peSemesterList = this.getGeneralService().getList(dcPeSemester);
			for (PeSemester peSemester : peSemesterList) {
				DetachedCriteria dcPrTchOpencourse = DetachedCriteria.forClass(PrTchOpencourse.class);
				dcPrTchOpencourse.createCriteria("peSemester", "peSemester").add(Restrictions.eq("id", peSemester.getId()));
				List<PrTchOpencourse> list = this.getGeneralService().getList(dcPrTchOpencourse);
				map.put(peSemester, list);
			}
			this.setMap(map);
			this.getPaperType();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "addVotePaper";
	}

	/**
	 * 添加调查问卷
	 * 
	 * @return
	 */
	public String addVotePaper() {

		if (!this.validateToken(this.getFormToken())) {
			this.setTogo("/entity/information/peVotePaper_toAddVotePaper.action");
			this.setMsg("表单出错，请重试!");
			return "msg";
		}
		if (null != this.getType() && this.getType().indexOf("jg") == 0) {// 面向监管学员/管理员
			EnumConst ec = new EnumConst();
			ec.setId(this.getType());
			this.getPeVotePaper().setEnumConstByFlagQualificationsType(ec);
		} else {
			// 设置类型
			this.getPeVotePaper().setEnumConstByFlagQualificationsType(
					this.getMyListService().getEnumConstByNamespaceCode("FlagQualificationsType", this.getType()));
		}
		// 根据类型设置关键字
		if (this.getType().equals("90")) {
			this.getPeVotePaper().setKeywords(this.getSemester_id() + "," + this.getCourse_keywords());
		}

		this.getPeVotePaper().setEnumConstByFlagCanSuggest(this.getMyListService().getEnumConstByNamespaceCode("FlagCanSuggest", "0"));
		this.getPeVotePaper().setEnumConstByFlagIsvalid(
				this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", this.getActive()));
		this.getPeVotePaper().setEnumConstByFlagLimitDiffip(
				this.getMyListService().getEnumConstByNamespaceCode("FlagLimitDiffip", this.getLimitIp()));
		this.getPeVotePaper().setEnumConstByFlagLimitDiffsession(
				this.getMyListService().getEnumConstByNamespaceCode("FlagLimitDiffsession", this.getLimitSession()));
		this.getPeVotePaper().setEnumConstByFlagViewSuggest(this.getMyListService().getEnumConstByNamespaceCode("FlagViewSuggest", "0"));
		this.getPeVotePaper().setFoundDate(new Date());

		try {
			this.getGeneralService().save(this.getPeVotePaper());
			this.setMsg("一条调查问卷添加成功！");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("添加失败！");
		}

		this.setTogo("/entity/information/peVotePaper_toAddVotePaper.action");
		return "msg";
	}

	/**
	 * 取得学期开课关系
	 * 
	 * @return
	 */
	public HashMap semesterAndCourse() {
		HashMap map = new HashMap();
		try {
			DetachedCriteria dcPeSemester = DetachedCriteria.forClass(PeSemester.class);
			List<PeSemester> peSemesterList = this.getGeneralService().getList(dcPeSemester);
			if (peSemesterList.size() == 0) {
				return map;
			}
			for (PeSemester peSemester : peSemesterList) {
				DetachedCriteria dcPrTchOpencourse = DetachedCriteria.forClass(PrTchOpencourse.class);
				dcPrTchOpencourse.createCriteria("peSemester", "peSemester").add(Restrictions.eq("id", peSemester.getId()));
				List<PrTchOpencourse> list = this.getGeneralService().getList(dcPrTchOpencourse);
				map.put(peSemester, list);
			}

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 取得调查问卷类型
	 * 
	 * @return
	 */
	public List<EnumConst> getPaperType() {
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagQualificationsType"));
		// 调查问卷类型过滤掉"无"选项
		dc.add(Restrictions.ne("code", "8"));
		dc.addOrder(Order.asc("code"));
		List<EnumConst> enumConstList = new ArrayList();
		try {
			enumConstList = this.getGeneralService().getList(dc);
			this.setEnumConstList(enumConstList);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enumConstList;
	}

	/**
	 * 查看调查问卷
	 * 
	 * @return
	 */
	public String viewDetail() {
		if (null == this.getBean() || null == this.getBean().getId()) {
			return "detail";
		}
		this.toSetVotePaperQuestion();
		// this.maxResult();
		String voteCount = toGetPrVoteRecordCount();
		if (voteCount != null && !voteCount.equals("")) {
			this.setVoteNumber(Long.parseLong(voteCount.trim()));
		}

		// // 设置参加投票的人数
		// DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
		// DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper",
		// "peVotePaper");
		// dcPeVotePaper.add(Restrictions.eq("id", this.getBean().getId()));
		// try {
		// List list = this.getGeneralService().getList(dc);
		// if (list.size() > 0) {
		// this.setVoteNumber(list.size());
		// }
		// } catch (EntityException e) {
		// e.printStackTrace();
		// }
		return "detail";
	}

	/**
	 * 导出问卷调查结果
	 * 
	 * @return
	 */
	public String resultSetToExcel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		try {
			createSubjectiveSheet(wb);
			createGradeSheet(wb);
			createObjectiveSheet(wb);
			createIscustomSheet(wb);
			Calendar c = Calendar.getInstance();
			String tm = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日"
					+ c.get(Calendar.HOUR_OF_DAY) + "时" + c.get(Calendar.MINUTE) + "分" + c.get(Calendar.SECOND) + "秒" + "";
			String excelName = "调查结果(" + tm + ").xls";
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + toUtf8String(excelName));
			ServletOutputStream out = response.getOutputStream();
			wb.write(out);
			if (out != null) {
				// Lee 清空out 否则后台报错
				out.flush();
				out.close();
			}
			this.setMsg("导出成功！");
			// this.setTogo(this.getServletPath()+".action");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("导出失败！");
			// this.setTogo("close");
			this.setTogo(this.getServletPath() + ".action");
		}
		return "msg";
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					ex.printStackTrace();
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 设置sheet列宽
	 * 
	 * @param sheet
	 * @param maxColNum
	 */
	public void autoWidth(HSSFSheet sheet, int maxColNum) {
		// for (int columnNum = 0; columnNum < maxColNum; columnNum++) {
		// sheet.autoSizeColumn( columnNum);
		// // int columnWidth = sheet.getColumnWidth(Short.parseShort(columnNum
		// // + "")) / 256;
		// // for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
		// // HSSFRow currentRow;
		// // // 当前行未被使用过
		// // if (sheet.getRow(rowNum) == null) {
		// // currentRow = sheet.createRow(rowNum);
		// // } else {
		// // currentRow = sheet.getRow(rowNum);
		// // }
		// //
		// // if (currentRow.getCell(Short.parseShort(columnNum + "")) != null)
		// // {
		// // HSSFCell currentCell = currentRow.getCell(Short
		// // .parseShort(columnNum + ""));
		// // int length = currentCell.toString().getBytes().length;
		// // if (columnWidth < length) {
		// // columnWidth = length;
		// // }
		// // }
		// // }
		// // sheet.setColumnWidth(Short.parseShort(columnNum + ""), Short
		// // .parseShort(columnWidth * 256 + ""));
		// }

	}

	/**
	 * 创建主观题sheet
	 * 
	 * @param wb
	 * @return
	 * @throws PlatformException
	 */
	public HSSFSheet createSubjectiveSheet(HSSFWorkbook wb) throws PlatformException {
		HSSFSheet sheet = null;
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		// 学员端
		String sql1 = "select s.login_id,p.true_name as name,e.name as eName,q.question_note,t.item_content,t.CREATE_DATE "
				+ "from sso_user s,pe_bzz_student p,PR_VOTE_SUB_QUESTION t ,PE_ENTERPRISE e,PR_VOTE_QUESTION q,PE_VOTE_PAPER v , ENUM_CONST     C "
				+ "where p.fk_sso_user_id=s.id and t.student_id=s.id and p.fk_enterprise_id=e.id and q.fk_vote_paper_id=v.id and t.fk_vote_question_id=q.id"
				+ " and q.FLAG_QUESTION_TYPE =c.ID AND c.CODE ='3'  and t.iscustom <>'1' and  v.id='" + this.getBean().getId()
				+ "' order by s.login_id asc ";
		// 集体端
		String sql2 = "select s.login_id,p.LOGIN_ID as name,e.name as eName,q.question_note,t.item_content,t.CREATE_DATE "
				+ "from sso_user s,pe_enterprise_manager p,PR_VOTE_SUB_QUESTION t ,PE_ENTERPRISE e,PR_VOTE_QUESTION q,PE_VOTE_PAPER v  ,ENUM_CONST     C "
				+ "where p.fk_sso_user_id=s.id and t.student_id=s.id and p.fk_enterprise_id=e.id and q.fk_vote_paper_id=v.id and t.fk_vote_question_id=q.id"
				+ " and q.FLAG_QUESTION_TYPE =c.ID AND c.CODE ='3'  and v.id='" + this.getBean().getId() + "' order by s.login_id asc ";

		List subList = null;
		List subList2 = null;
		try {
			subList = this.getGeneralService().getBySQL(sql1);
			subList2 = this.getGeneralService().getBySQL(sql2);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (subList2 != null && subList2.size() > 0) {
			for (int i = 0; i < subList2.size(); i++) {
				subList.add(subList2.get(i));
			}
		}
		if (subList != null && subList.size() > 0) {
			HSSFCell c;
			int page = 0;
			int tempIndex = 0;
			for (int i = 0; i < subList.size(); i++) {
				if (i % 65535 == 0) {// 避免溢出，每页只存65535条记录
					tempIndex = 0;
					page++;
					sheet = this.createSubSheetTitle(wb, page, "主观题列表");//
				}
				tempIndex++;
				Object[] ojs = (Object[]) subList.get(i);
				HSSFRow subRow = sheet.createRow(tempIndex);
				subRow.createCell(0).setCellValue((String) ojs[0]);
				subRow.createCell(1).setCellValue((String) ojs[1]);
				subRow.createCell(2).setCellValue((String) ojs[2]);
				subRow.createCell(3).setCellValue((String) ojs[3]);
				subRow.createCell(4).setCellValue((String) ojs[4]);
				c = subRow.createCell(5);
				c.setCellValue(ojs[5].toString());
				c.setCellStyle(cellStyle);
			}
			autoWidth(sheet, 6);
		}
		return sheet;
	}

	/**
	 * 创建打分题sheet
	 */
	public HSSFSheet createGradeSheet(HSSFWorkbook wb) throws PlatformException {
		HSSFSheet sheet = null;
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		// 学员端
		String sql1 = "select s.login_id,p.true_name as name,e.name as eName,q.question_note,t.item_content,t.CREATE_DATE "
				+ "from sso_user s,pe_bzz_student p,PR_VOTE_SUB_QUESTION t ,PE_ENTERPRISE e,PR_VOTE_QUESTION q,PE_VOTE_PAPER v , ENUM_CONST     C "
				+ "where p.fk_sso_user_id=s.id and t.student_id=s.id and p.fk_enterprise_id=e.id and q.fk_vote_paper_id=v.id and t.fk_vote_question_id=q.id"
				+ " and q.FLAG_QUESTION_TYPE =c.ID AND c.CODE='4' and t.iscustom <>'1' and  v.id='" + this.getBean().getId()
				+ "' order by s.login_id asc ";
		// 集体端
		String sql2 = "select s.login_id,p.LOGIN_ID as name,e.name as eName,q.question_note,t.item_content,t.CREATE_DATE "
				+ "from sso_user s,pe_enterprise_manager p,PR_VOTE_SUB_QUESTION t ,PE_ENTERPRISE e,PR_VOTE_QUESTION q,PE_VOTE_PAPER v  ,ENUM_CONST     C "
				+ "where p.fk_sso_user_id=s.id and t.student_id=s.id and p.fk_enterprise_id=e.id and q.fk_vote_paper_id=v.id and t.fk_vote_question_id=q.id"
				+ " and q.FLAG_QUESTION_TYPE =c.ID AND c.CODE='4' and v.id='" + this.getBean().getId() + "' order by s.login_id asc ";

		List subList = null;
		List subList2 = null;
		try {
			subList = this.getGeneralService().getBySQL(sql1);
			subList2 = this.getGeneralService().getBySQL(sql2);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (subList2 != null && subList2.size() > 0) {
			for (int i = 0; i < subList2.size(); i++) {
				subList.add(subList2.get(i));
			}
		}
		if (subList != null && subList.size() > 0) {
			HSSFCell c;
			int page = 0;
			int tempIndex = 0;
			for (int i = 0; i < subList.size(); i++) {
				if (i % 65535 == 0) {// 避免溢出，每页只存65535条记录
					tempIndex = 0;
					page++;
					sheet = this.createSubSheetTitle(wb, page, "打分题列表");//
				}
				tempIndex++;
				Object[] ojs = (Object[]) subList.get(i);
				HSSFRow subRow = sheet.createRow(tempIndex);
				subRow.createCell(0).setCellValue((String) ojs[0]);
				subRow.createCell(1).setCellValue((String) ojs[1]);
				subRow.createCell(2).setCellValue((String) ojs[2]);
				subRow.createCell(3).setCellValue((String) ojs[3]);
				subRow.createCell(4).setCellValue((String) ojs[4]);
				c = subRow.createCell(5);
				c.setCellValue(ojs[5].toString());
				c.setCellStyle(cellStyle);
			}
			autoWidth(sheet, 6);
		}
		return sheet;
	}

	public HSSFSheet createIscustomSheet(HSSFWorkbook wb) throws PlatformException {
		HSSFSheet sheet = null;
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		// 学员端
		String sql1 = "select s.login_id,p.true_name as name,e.name as eName,q.question_note,t.item_content,t.CREATE_DATE "
				+ "from sso_user s,pe_bzz_student p,PR_VOTE_SUB_QUESTION t ,PE_ENTERPRISE e,PR_VOTE_QUESTION q,PE_VOTE_PAPER v , ENUM_CONST     C "
				+ "where p.fk_sso_user_id=s.id and t.student_id=s.id and p.fk_enterprise_id=e.id and q.fk_vote_paper_id=v.id and t.fk_vote_question_id=q.id"
				+ " AND Q.FLAG_QUESTION_TYPE = C.ID and t.iscustom ='1' and  v.id='" + this.getBean().getId()
				+ "' order by s.login_id asc ";
		// 集体端
		String sql2 = "select s.login_id,p.LOGIN_ID as name,e.name as eName,q.question_note,t.item_content,t.CREATE_DATE "
				+ "from sso_user s,pe_enterprise_manager p,PR_VOTE_SUB_QUESTION t ,PE_ENTERPRISE e,PR_VOTE_QUESTION q,PE_VOTE_PAPER v  ,ENUM_CONST     C "
				+ "where p.fk_sso_user_id=s.id and t.student_id=s.id and p.fk_enterprise_id=e.id and q.fk_vote_paper_id=v.id and t.fk_vote_question_id=q.id"
				+ " AND Q.FLAG_QUESTION_TYPE = C.ID and t.iscustom ='1' and v.id='" + this.getBean().getId() + "' order by s.login_id asc ";

		List subList = null;
		List subList2 = null;
		try {
			subList = this.getGeneralService().getBySQL(sql1);
			subList2 = this.getGeneralService().getBySQL(sql2);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (subList2 != null && subList2.size() > 0) {
			for (int i = 0; i < subList2.size(); i++) {
				subList.add(subList2.get(i));
			}
		}
		if (subList != null && subList.size() > 0) {
			HSSFCell c;
			int page = 0;
			int tempIndex = 0;
			for (int i = 0; i < subList.size(); i++) {
				if (i % 65535 == 0) {// 避免溢出，每页只存65535条记录
					tempIndex = 0;
					page++;
					sheet = this.createSubSheetTitle(wb, page, "自定义回答列表");//
				}
				tempIndex++;
				Object[] ojs = (Object[]) subList.get(i);
				HSSFRow subRow = sheet.createRow(tempIndex);
				subRow.createCell(0).setCellValue((String) ojs[0]);
				subRow.createCell(1).setCellValue((String) ojs[1]);
				subRow.createCell(2).setCellValue((String) ojs[2]);
				subRow.createCell(3).setCellValue((String) ojs[3]);
				subRow.createCell(4).setCellValue((String) ojs[4]);
				c = subRow.createCell(5);
				c.setCellValue(ojs[5].toString());
				c.setCellStyle(cellStyle);
			}
			autoWidth(sheet, 6);
		}
		return sheet;
	}

	private HSSFSheet createSubSheetTitle(HSSFWorkbook wb, int page, String type) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFSheet sheet = wb.createSheet(type + page);
		HSSFRow subRow = sheet.createRow((int) 0);
		HSSFCell subCell = subRow.createCell(0);
		subCell.setCellValue("编号");
		subCell.setCellStyle(style);
		subCell = subRow.createCell(1);
		subCell.setCellValue("姓名");
		subCell.setCellStyle(style);
		subCell = subRow.createCell(2);
		subCell.setCellValue("所在机构");
		subCell.setCellStyle(style);
		subCell = subRow.createCell(3);
		subCell.setCellValue("所投问题");
		subCell.setCellStyle(style);
		subCell = subRow.createCell(4);
		subCell.setCellValue("主观内容");
		subCell.setCellStyle(style);
		subCell.setCellStyle(style);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		subCell = subRow.createCell(5);
		subCell.setCellValue("投票时间");
		subCell.setCellStyle(style);
		return sheet;
	}

	/**
	 * 创建客观题sheet
	 * 
	 * @param wb
	 * @return
	 * @throws PlatformException
	 */
	public HSSFSheet createObjectiveSheet(HSSFWorkbook wb) {
		NumberFormat nt = NumberFormat.getPercentInstance();
		nt.setMinimumFractionDigits(2);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFSheet sheet = wb.createSheet("客观题列表");
		HSSFRow row = sheet.createRow((int) 0);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("题目");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("选项");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("票数");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("百分比");
		cell.setCellStyle(style);
		List<PrVoteQuestion> prVoteQuestionList = new ArrayList<PrVoteQuestion>();
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteQuestion.class);
		DetachedCriteria dcPrVoteQuestion = dc.createCriteria("peVotePaper", "peVotePaper");
		dcPrVoteQuestion.add(Restrictions.eq("id", this.getBean().getId()));
		dc.addOrder(Order.asc("createDate"));
		// 取得问卷包含的问题

		try {
			prVoteQuestionList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int num = 0;
		for (int i = 0; i < prVoteQuestionList.size(); i++) { // sheet.autoSizeColumn(num);
			PrVoteQuestion pq = (PrVoteQuestion) prVoteQuestionList.get(i);
			if (!pq.getEnumConstByFlagQuestionType().getCode().equals("3") && !pq.getEnumConstByFlagQuestionType().getCode().equals("4")) {

				row = sheet.createRow(++num);
				row.createCell(0).setCellValue(pq.getQuestionNote());

				if (pq.getItem1() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem1());
					if (pq.getItemResult1() == null)
						pq.setItemResult1(0L);
					row.createCell(2).setCellValue(pq.getItemResult1());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult1() * 1.0 / pq.getTotalItemResult() * 1.0));
					}
				}
				if (pq.getItem2() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem2());
					if (pq.getItemResult2() == null)
						pq.setItemResult2(0L);
					row.createCell(2).setCellValue(pq.getItemResult2());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult2() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem3() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem3());
					if (pq.getItemResult3() == null)
						pq.setItemResult3(0L);
					row.createCell(2).setCellValue(pq.getItemResult3());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult3() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem4() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem4());
					if (pq.getItemResult4() == null)
						pq.setItemResult4(0L);
					row.createCell(2).setCellValue(pq.getItemResult4());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult4() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem5() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem5());
					if (pq.getItemResult5() == null)
						pq.setItemResult5(0L);
					row.createCell(2).setCellValue(pq.getItemResult5());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult5() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem6() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem6());
					if (pq.getItemResult6() == null)
						pq.setItemResult6(0L);
					row.createCell(2).setCellValue(pq.getItemResult6());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult6() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem7() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem7());
					if (pq.getItemResult7() == null)
						pq.setItemResult7(0L);
					row.createCell(2).setCellValue(pq.getItemResult7());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult7() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem8() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem8());
					if (pq.getItemResult8() == null)
						pq.setItemResult8(0L);
					row.createCell(2).setCellValue(pq.getItemResult8());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult8() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem9() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem9());
					if (pq.getItemResult9() == null)
						pq.setItemResult9(0L);
					row.createCell(2).setCellValue(pq.getItemResult9());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult9() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem10() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem10());
					if (pq.getItemResult10() == null)
						pq.setItemResult10(0L);
					row.createCell(2).setCellValue(pq.getItemResult10());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult10() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem11() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem11());
					if (pq.getItemResult11() == null)
						pq.setItemResult11(0L);
					row.createCell(2).setCellValue(pq.getItemResult11());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult11() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem12() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem12());
					if (pq.getItemResult12() == null)
						pq.setItemResult12(0L);
					row.createCell(2).setCellValue(pq.getItemResult12());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult12() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem13() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem13());
					if (pq.getItemResult13() == null)
						pq.setItemResult13(0L);
					row.createCell(2).setCellValue(pq.getItemResult13());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult13() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem14() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem14());
					if (pq.getItemResult14() == null)
						pq.setItemResult14(0L);
					row.createCell(2).setCellValue(pq.getItemResult14());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult14() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
				if (pq.getItem15() != null) {
					row = sheet.createRow(++num);
					row.createCell(1).setCellValue(pq.getItem15());
					if (pq.getItemResult15() == null)
						pq.setItemResult15(0L);
					row.createCell(2).setCellValue(pq.getItemResult15());
					if (pq.getTotalItemResult() == 0) {
						row.createCell(3).setCellValue(nt.format(0));
					} else {
						row.createCell(3).setCellValue(nt.format(pq.getItemResult15() * 1.0 / pq.getTotalItemResult() * 1.0));
					}

				}
			}
		}
		autoWidth(sheet, 4);

		return sheet;
	}

	/**
	 * 转向问题管理页面
	 * 
	 * @return
	 */
	public String toVoteQuestion() {
		this.toSetVotePaperQuestion();
		return "voteQuestion";
	}

	/**
	 * 检查用户是否已经参加过投票，设置 int canVote;//用户是否能够提交调查问卷 0不可以 1可以
	 */
	public void checkCanVote() {
		/**
		 * 如果调查问卷限制IP，则检查用户的IP是否已经投票
		 */
		if (this.getPeVotePaper().getEnumConstByFlagLimitDiffip().getCode().equals("1")) {
			// 得到用户的IP
			HttpServletRequest request = ServletActionContext.getRequest();
			String ip = request.getRemoteAddr();

			DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
			DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
			dcPeVotePaper.add(Restrictions.eq("id", this.getBean().getId()));
			dc.add(Restrictions.eq("ip", ip));
			try {
				List list = this.getGeneralService().getList(dc);
				// 如果IP已经存在则设置为不能投票
				if (list.size() > 0) {
					this.setCanVote(0);
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}

		if (this.getCanVote() == 1) {
			/**
			 * 如果调查问卷限制session，则检查用户session是否已经投票
			 */
			if (this.getPeVotePaper().getEnumConstByFlagLimitDiffsession().getCode().equals("1")) {
				// 得到用户session
				HttpSession session = ServletActionContext.getRequest().getSession();
				String sessionId = session.getId();

				DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
				DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
				dcPeVotePaper.add(Restrictions.eq("id", this.getBean().getId()));
				dc.add(Restrictions.eq("userSession", sessionId));

				try {
					List list = this.getGeneralService().getList(dc);
					// 如果session已经存在则设置为不能投票
					if (list.size() > 0) {
						this.setCanVote(0);
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 拼接单选题的选项
	 * 
	 * @return
	 */
	protected String readioQuestion() {
		HttpServletRequest request = ServletActionContext.getRequest();
		ActionContext context = ActionContext.getContext();
		Map params = context.getParameters();
		Iterator iterator = params.entrySet().iterator();
		String readioQuestion = "";
		do {
			if (!iterator.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			String name = entry.getKey().toString();
			if (name.indexOf("radio") >= 0) {
				readioQuestion += request.getParameter(name) + ",";
			}
		} while (true);
		return readioQuestion;
	}

	/**
	 * 每个问题的投票最多的数量
	 */
	protected void maxResult() {
		for (PrVoteQuestion prVoteQuestion : this.getPrVoteQuestionList()) {
			long result = 0L;
			if (prVoteQuestion.getItemResult1() != null && prVoteQuestion.getItemResult1() > result) {
				result = prVoteQuestion.getItemResult1();
			}
			if (prVoteQuestion.getItemResult2() != null && prVoteQuestion.getItemResult2() > result) {
				result = prVoteQuestion.getItemResult2();
			}
			if (prVoteQuestion.getItemResult3() != null && prVoteQuestion.getItemResult3() > result) {
				result = prVoteQuestion.getItemResult3();
			}
			if (prVoteQuestion.getItemResult4() != null && prVoteQuestion.getItemResult4() > result) {
				result = prVoteQuestion.getItemResult4();
			}
			if (prVoteQuestion.getItemResult5() != null && prVoteQuestion.getItemResult5() > result) {
				result = prVoteQuestion.getItemResult5();
			}
			if (prVoteQuestion.getItemResult6() != null && prVoteQuestion.getItemResult6() > result) {
				result = prVoteQuestion.getItemResult6();
			}
			if (prVoteQuestion.getItemResult7() != null && prVoteQuestion.getItemResult7() > result) {
				result = prVoteQuestion.getItemResult7();
			}
			if (prVoteQuestion.getItemResult8() != null && prVoteQuestion.getItemResult8() > result) {
				result = prVoteQuestion.getItemResult8();
			}
			if (prVoteQuestion.getItemResult9() != null && prVoteQuestion.getItemResult9() > result) {
				result = prVoteQuestion.getItemResult9();
			}
			if (prVoteQuestion.getItemResult10() != null && prVoteQuestion.getItemResult10() > result) {
				result = prVoteQuestion.getItemResult10();
			}
			if (prVoteQuestion.getItemResult11() != null && prVoteQuestion.getItemResult11() > result) {
				result = prVoteQuestion.getItemResult11();
			}
			if (prVoteQuestion.getItemResult12() != null && prVoteQuestion.getItemResult12() > result) {
				result = prVoteQuestion.getItemResult12();
			}
			if (prVoteQuestion.getItemResult13() != null && prVoteQuestion.getItemResult13() > result) {
				result = prVoteQuestion.getItemResult13();
			}
			if (prVoteQuestion.getItemResult14() != null && prVoteQuestion.getItemResult14() > result) {
				result = prVoteQuestion.getItemResult14();
			}
			if (prVoteQuestion.getItemResult15() != null && prVoteQuestion.getItemResult15() > result) {
				result = prVoteQuestion.getItemResult15();
			}
			prVoteQuestion.setItemNum(result);
		}
	}

	/**
	 * 转向修改页面
	 */
	public String toEditVotePaper() {
		// Lee start 添加修改前的判断 判断是否已经发布 发布的不能修改
		String sql = "SELECT ID FROM PE_VOTE_PAPER WHERE FLAG_ISVALID = '2' AND ID = '" + this.getBean().getId() + "'";
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			if (list != null) {
				this.setMsg("该调查问卷已经发布！禁止修改！");
				return "msg";
			}
		}
		if (list != null && list.size() > 0) {
			this.setMsg("该调查问卷已经发布！禁止修改！");
			return "msg";
		}
		// Lee end
		this.setEnumConstList(this.getPaperType());
		this.setMap(this.semesterAndCourse());
		try {
			this.setPeVotePaper((PeVotePaper) this.getGeneralService().getById(this.getBean().getId()));

			// 如果是课程问卷，需要取得学期和课程
			if (this.getPeVotePaper().getEnumConstByFlagQualificationsType().getCode().equals("90")) {
				this.keywords(this.getPeVotePaper());
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "editVotePaper";
	}

	/**
	 * 调查问卷修改
	 * 
	 * @return
	 */
	public String editVotePaper() {

		if (!this.validateToken(this.getFormToken())) {
			this.setTogo("/entity/information/peVotePaper_toAddVotePaper.action");
			this.setMsg("表单出错，请重试!");
			return "msg";
		}
		// 设置类型
		this.getPeVotePaper().setEnumConstByFlagQualificationsType(
				this.getMyListService().getEnumConstByNamespaceCode("FlagQualificationsType", this.getType()));
		// 根据类型设置关键字
		if (this.getType().equals("90")) {
			this.getPeVotePaper().setKeywords(this.getSemester_id() + "," + this.getCourse_keywords());
		}

		this.getPeVotePaper().setEnumConstByFlagCanSuggest(this.getMyListService().getEnumConstByNamespaceCode("FlagCanSuggest", "0"));
		this.getPeVotePaper().setEnumConstByFlagIsvalid(
				this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", this.getActive()));
		this.getPeVotePaper().setEnumConstByFlagLimitDiffip(
				this.getMyListService().getEnumConstByNamespaceCode("FlagLimitDiffip", this.getLimitIp()));
		this.getPeVotePaper().setEnumConstByFlagLimitDiffsession(
				this.getMyListService().getEnumConstByNamespaceCode("FlagLimitDiffsession", this.getLimitSession()));
		this.getPeVotePaper().setEnumConstByFlagViewSuggest(this.getMyListService().getEnumConstByNamespaceCode("FlagViewSuggest", "0"));
		try {
			PeVotePaper vote = (PeVotePaper) this.getGeneralService().getById(this.getPeVotePaper().getId());
			this.superSetBean((PeVotePaper) setSubIds(vote, this.getPeVotePaper()));
			this.getGeneralService().save(vote);
			setTogo("/entity/information/peVotePaper.action?backParam=true");
			this.setMsg("一条调查问卷修改成功!");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setTogo("back");
			this.setMsg("修改失败！");
		}
		return "msg";
	}

	/**
	 * 根据关键字设置学期和课程
	 */
	private void keywords(PeVotePaper peVotePaper) {
		String[] strs = peVotePaper.getKeywords().split(",");
		this.setSemester_id(strs[0].trim());
		List list = new ArrayList();
		for (int i = 1; i < strs.length; i++) {
			list.add(strs[i].trim());
		}
		this.setCourses(list);
	}

	/**
	 * 根据ID设置peVotePaper 和prVoteQuestionList
	 */
	protected void toSetVotePaperQuestion() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		try {
			String flag_valid = "";
			this.setCanupdate("1");
			// 取得调查问卷
			this.setPeVotePaper((PeVotePaper) this.getGeneralService().getById(PeVotePaper.class, this.getBean().getId()));

			if (this.getPeVotePaper() != null) {

				flag_valid = this.getPeVotePaper().getEnumConstByFlagIsvalid().getName();
			}

			List<PrVoteQuestion> prVoteQuestionList = new ArrayList<PrVoteQuestion>();
			DetachedCriteria dc = DetachedCriteria.forClass(PrVoteQuestion.class);
			DetachedCriteria dcPrVoteQuestion = dc.createCriteria("peVotePaper", "peVotePaper");
			dcPrVoteQuestion.add(Restrictions.eq("id", this.getBean().getId()));
			// DetachedCriteria
			// enumConstByFlagQuestionType=dc.createCriteria("enumConstByFlagQuestionType","enumConstByFlagQuestionType");
			// enumConstByFlagQuestionType.add(Restrictions.ne("code", "3"));
			dc.addOrder(Order.asc("questionOrder"));
			// 取得问卷包含的问题
			prVoteQuestionList = this.getGeneralService().getList(dc);

			// 查询用户主观题投票记录
			// List<PrVoteSubQuestion> prVoteSubQuestionList = new
			// ArrayList<PrVoteSubQuestion>();
			// DetachedCriteria d =
			// DetachedCriteria.forClass(PrVoteSubQuestion.class);
			// DetachedCriteria dPrVoteQuestion =
			// d.createCriteria("prVoteQuestion", "prVoteQuestion");
			// dPrVoteQuestion.add(Restrictions.eq("prVoteQuestion.peVotePaper.id",
			// this.getBean().getId()));
			// d.add(Restrictions.ne("isCustom", "1"));
			// // DetachedCriteria
			// //
			// et=d.createCriteria("enumConstByFlagQuestionType","enumConstByFlagQuestionType");
			// // et.add(Restrictions.eq("code", "3"));
			// DetachedCriteria su = d.createCriteria("ssoUser", "ssoUser");
			// su.add(Restrictions.eq("ssoUser.id", ssoUser.getId()));
			// d.addOrder(Order.asc("createDate"));
			// prVoteSubQuestionList = this.getGeneralService().getList(d);
			// this.setPrVoteSubQuestionList(prVoteSubQuestionList);
			// 获取用户选择题投票记录
			// List<PrVoteUserQuestion> prVoteUserQuestionList = new
			// ArrayList<PrVoteUserQuestion>();
			// DetachedCriteria userQuestions =
			// DetachedCriteria.forClass(PrVoteUserQuestion.class);
			// DetachedCriteria logUser =
			// userQuestions.createCriteria("ssoUser", "ssoUser");
			// logUser.add(Restrictions.eq("ssoUser.id", ssoUser.getId()));
			// DetachedCriteria userCriteia =
			// userQuestions.createCriteria("prVoteQuestion", "prVoteQuestion");
			// userCriteia.add(Restrictions.eq("prVoteQuestion.peVotePaper.id",
			// this.getBean().getId()));
			// prVoteUserQuestionList =
			// this.getGeneralService().getList(userQuestions);
			// this.setPrVoteUserQuestionList(prVoteUserQuestionList);
			// 遍历所有问题，标注辅助字段答案便于页面展示!!!!性能太差 取消!!!!
			// for (int i = 0; i < prVoteQuestionList.size(); i++) {
			// PrVoteQuestion pq = prVoteQuestionList.get(i);
			// for (int j = 0; j < prVoteSubQuestionList.size(); j++) {
			// if
			// (pq.getId().trim().equals(prVoteSubQuestionList.get(j).getPrVoteQuestion().getId().trim()))
			// {
			// pq.setResult(prVoteSubQuestionList.get(j).getItemContent());
			// }
			// }
			// for (int j = 0; j < prVoteUserQuestionList.size(); j++) {
			// if
			// (pq.getId().trim().equals(prVoteUserQuestionList.get(j).getPrVoteQuestion().getId().trim()))
			// {
			// pq.setResult(prVoteUserQuestionList.get(j).getVoteResult());
			// }
			// }
			// }

			this.setPrVoteQuestionList(prVoteQuestionList);
			// userQuestions.addOrder(Order.asc("createDate"));
			// 检查此调查问卷是否已经发布及存在记录
			String sql_record = "select count(1) from pr_vote_record  pr ,pe_vote_paper vp ,enum_const ec "
					+ "	  where  pr.fk_vote_paper_id=vp.id and ec.id=vp.flag_isvalid  " + " 	  and pr.fk_vote_paper_id='"
					+ this.getBean().getId() + "'";
			List list_record = this.getGeneralService().getBySQL(sql_record);
			if (list_record != null && list_record.size() > 0) {
				int voteCount = Integer.parseInt(list_record.get(0).toString());
				if (voteCount > 0 || (flag_valid != null && flag_valid.equals("是"))) {
					this.setCanupdate("0");
				}
			}

		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 20130110添加，用于获取投票的
	 */
	protected String toGetPrVoteRecordCount() {
		String sql = "select count(distinct pvr.ssoid) from PR_VOTE_RECORD pvr\n" + "              where pvr.fk_vote_paper_id = '"
				+ this.getBean().getId() + "'";
		List list = null;
		String voteCount = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null) {
			if (list.size() > 0) {
				// System.out.println("*******************************" +
				// list.get(0));
				voteCount = list.get(0).toString();
			}
		}
		return voteCount;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();

		this.getGridConfig().setTitle(this.getText("调查问卷列表"));
		this.getGridConfig().setCapability(false, true, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "title");
		ColumnConfig columnQualificationsType = new ColumnConfig(this.getText("类型"), "enumConstByFlagQualificationsType.name", true, true, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagQualificationsType' UNION SELECT 'jgstu', '面向监管类学员' FROM DUAL UNION SELECT 'jgman', '面向监管类集体管理员' FROM DUAL";
		columnQualificationsType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnQualificationsType);
		
		this.getGridConfig().addColumn(this.getText("创建日期"), "foundDate", false);
		ColumnConfig columnConfigIsvalid = new ColumnConfig(this.getText("是否发布"), "enumConstByFlagIsvalid.name", true, false, true,
				"TextField", false, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid' and a.id<>'3x' ";
		columnConfigIsvalid.setComboSQL(sql3);
		this.getGridConfig().addColumn(columnConfigIsvalid);
		// this.getGridConfig().addColumn(this.getText("是否发布"),
		// "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("创建日期起始"), "foundStartDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("创建日期结束"), "foundEndDate", true, false, false, "");
		this.getGridConfig().addRenderFunction(this.getText("查看详情"),
				"<a href=\"peVotePaper_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>", "id");

		// this.getGridConfig().addRenderFunction(this.getText("查看建议"),
		// "<a href=\"prVoteSuggest.action?bean.peVotePaper.id=${value}\"
		// >查看建议</a>",
		// "id");
		// this.getGridConfig().addRenderFunction(this.getText("导出建议"),
		// "<a
		// href=\"/entity/manager/information/vote/votesuggest_excel.jsp?id=${value}\"
		// target=\"_blank\">导出建议</a>",
		// "id");
		if (capabilitySet.contains(this.servletPath + "_voteBtn.action")) {// 按钮权限判断
			this.getGridConfig().addRenderFunction(this.getText("管理题目"),
					"<a href=\"peVotePaper_toVoteQuestion.action?bean.id=${value}\" >管理题目</a>", "id");
			this.getGridConfig().addRenderFunction(this.getText("修改"),
					"<a href=\"peVotePaper_toEditVotePaper.action?bean.id=${value}\" >修改</a>", "id");
			this.getGridConfig().addRenderFunction(this.getText("导出调查结果"),
					"<a href=\"peVotePaper_resultSetToExcel.action?bean.id=${value}&bean.title=${value}\" >导出调查结果</a>", "id");
			this.getGridConfig().addMenuFunction(this.getText("发布"), "FlagIsvalid.true");
			this.getGridConfig().addMenuFunction(this.getText("取消发布"), "FlagIsvalid.false");
		} else {
			this.getGridConfig().setCapability(false, false, false);
		}

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeVotePaper.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peVotePaper";

	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
	 * @return
	 */
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		dc.add(Restrictions.isNull("type"));
		dc.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType");
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					if (params.get("search__foundStartDate") != null) {
						String[] startDate = (String[]) params.get("search__foundStartDate");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__foundStartDate");
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date sDate = format.parse(tempDate + " 00:00:00 ");
							// Date sDate = format.parse(tempDate);
							dc.add(Restrictions.ge("foundDate", sDate));
						}
					}
					if (params.get("search__foundEndDate") != null) {
						String[] startDate = (String[]) params.get("search__foundEndDate");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__foundEndDate");
							context.setParameters(params);
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date sDate = format.parse(tempDate + " 23:59:59 ");
							// Date sDate = format.parse(tempDate);
							dc.add(Restrictions.le("foundDate", sDate));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}

	public void setBean(PeVotePaper instance) {
		super.superSetBean(instance);

	}

	public PeVotePaper getBean() {
		return (PeVotePaper) super.superGetBean();
	}

	/**
	 * 框架方法：更新列前的验证
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		int count = 0;
		String sql = "";
		try {
			for (int i = 0; i < idList.size(); i++) {
				sql = "select p.id from pe_vote_paper p  where p.id ='" + idList.get(i) + "' and p.flag_isvalid='2'";
				List list = this.getGeneralService().getBySQL(sql);
				if (list.size() > 0) {
					count++;
				}
			}
			if (count > 0) {
				throw new EntityException("所选问卷中有问卷已经发布，请重新选择后再发布！");
			}
		} catch (EntityException e) {
			throw e;
		}
	}

	/**
	 * 框架方法：更新列
	 * 
	 * @author linjie
	 * @return
	 */
	public Map updateColumn() {
		String msg = "";
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {

			String[] ids = getIds().split(",");
			List idList = new ArrayList();

			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			String action = this.getColumn();
			if ("FlagIsvalid.true".equals(action)) {
				try {
					checkBeforeUpdateColumn(idList);
					isTile(idList);
				} catch (EntityException e) {
					map.clear();
					map.put("success", "false");
					map.put("info", e.getMessage());
					return map;
				}
			}
			try {
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeVotePaper.class);
				if ("FlagIsvalid.true".equals(action)) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<PeVotePaper> courselist = new ArrayList<PeVotePaper>();
					courselist = this.getGeneralService().getList(tempdc);
					Iterator<PeVotePaper> iterator = courselist.iterator();
					while (iterator.hasNext()) {
						PeVotePaper peVotePaper = iterator.next();
						peVotePaper.setEnumConstByFlagIsvalid(enumConst);
						this.getGeneralService().save(peVotePaper);
					}
					msg = "调查问卷发布";
				}
				if (action.equals("FlagIsvalid.false")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
					tempdc.add(Restrictions.in("id", ids));
					List<PeVotePaper> courselist = new ArrayList<PeVotePaper>();
					courselist = this.getGeneralService().getList(tempdc);
					Iterator<PeVotePaper> iterator = courselist.iterator();
					while (iterator.hasNext()) {
						PeVotePaper peVotePaper = iterator.next();
						peVotePaper.setEnumConstByFlagIsvalid(enumConst);
						this.getGeneralService().save(peVotePaper);
					}
					msg = "调查问卷取消发布";
				}
			} catch (EntityException e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", msg + "共有" + ids.length + "条记录操作成功");

		}
		return map;
	}

	/**
	 * 框架方法：删除列
	 * 
	 * @author linjie
	 * @return
	 */
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				DetachedCriteria criteria = DetachedCriteria.forClass(PrVoteQuestion.class);
				criteria.createCriteria("peVotePaper", "peVotePaper");
				criteria.add(Restrictions.in("peVotePaper.id", ids));

				DetachedCriteria criteria1 = DetachedCriteria.forClass(PrVoteSuggest.class);
				criteria1.createCriteria("peVotePaper", "peVotePaper");
				criteria1.add(Restrictions.in("peVotePaper.id", ids));

				DetachedCriteria criteria2 = DetachedCriteria.forClass(PrVoteRecord.class);
				criteria2.createCriteria("peVotePaper", "peVotePaper");
				criteria2.add(Restrictions.in("peVotePaper.id", ids));

				try {
					List<PrVoteQuestion> plist = this.getGeneralService().getList(criteria);
					List<PrVoteSuggest> vslist = this.getGeneralService().getList(criteria1);
					List<PrVoteRecord> vrlist = this.getGeneralService().getList(criteria1);
					if (plist.size() > 0 || vslist.size() > 0 || vrlist.size() > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "该问卷下已经添加题目或者已经存在建议或调查结果,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try {
					// this.getGeneralService().deleteByIds(idList);
					map.put("success", "true");
					map.put("info", ids.length + "条调查问卷记录删除成功");
					UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(
							SsoConstant.SSO_USER_SESSION_KEY);
					List<PeEnterprise> priList = us.getPriEnterprises();

					List siteList = new ArrayList();

					for (int i = 0; i < priList.size(); i++) {
						siteList.add(priList.get(i).getId());
					}

					int i = this.getGeneralService().deleteByIds(this.getEntityClass(), siteList, idList);
					if (0 == i) {
						map.clear();
						map.put("success", "false");
						map.put("info", "对不起，您没有删除的权限");
					}
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

	/**
	 * 发布前如果没有试题不能发布 qiaoshijia
	 * 
	 * @param idList
	 * @throws EntityException
	 */
	public void isTile(List idList) throws EntityException {

		try {
			DetachedCriteria dc = DetachedCriteria.forClass(PrVoteQuestion.class);
			dc.add(Restrictions.in("peVotePaper.id", idList));
			dc.setProjection(Projections.projectionList().add(Projections.groupProperty("peVotePaper.id")));
			List list = this.getGeneralService().getDetachList(dc);

			if (list.size() != idList.size()) {
				throw new EntityException("所选问卷中有问卷没有题目，请先添加题目在发布！");
			}
		} catch (EntityException e) {
			throw e;
		}
	}

	/**
	 * 计算每项投票的百分比
	 * 
	 * @return
	 */
	public String votePercent(long item, long total) {
		double d = (double) item / total * 100;

		return new DecimalFormat("#.00").format(d);
	}

	public HashMap getMap() {
		return map;
	}

	public void setMap(HashMap map) {
		this.map = map;
	}

	public List<EnumConst> getEnumConstList() {
		return enumConstList;
	}

	public void setEnumConstList(List<EnumConst> enumConstList) {
		this.enumConstList = enumConstList;
	}

	public PeVotePaper getPeVotePaper() {
		return peVotePaper;
	}

	public void setPeVotePaper(PeVotePaper peVotePaper) {
		this.peVotePaper = peVotePaper;
	}

	public String getCourse_keywords() {
		return course_keywords;
	}

	public void setCourse_keywords(String course_keywords) {
		this.course_keywords = course_keywords;
	}

	public String getSemester_id() {
		return semester_id;
	}

	public void setSemester_id(String semester_id) {
		this.semester_id = semester_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCanSuggest() {
		return canSuggest;
	}

	public void setCanSuggest(String canSuggest) {
		this.canSuggest = canSuggest;
	}

	public String getViewSuggest() {
		return viewSuggest;
	}

	public void setViewSuggest(String viewSuggest) {
		this.viewSuggest = viewSuggest;
	}

	public String getLimitIp() {
		return limitIp;
	}

	public void setLimitIp(String limitIp) {
		this.limitIp = limitIp;
	}

	public String getLimitSession() {
		return limitSession;
	}

	public void setLimitSession(String limitSession) {
		this.limitSession = limitSession;
	}

	public List<PrVoteQuestion> getPrVoteQuestionList() {
		return prVoteQuestionList;
	}

	public void setPrVoteQuestionList(List<PrVoteQuestion> prVoteQuestionList) {
		this.prVoteQuestionList = prVoteQuestionList;
	}

	public int getPastDue() {
		return pastDue;
	}

	public void setPastDue(int pastDue) {
		this.pastDue = pastDue;
	}

	public List getCourses() {
		return courses;
	}

	public void setCourses(List courses) {
		this.courses = courses;
	}

	public String getCheckboxQuestion() {
		return checkboxQuestion;
	}

	public void setCheckboxQuestion(String checkboxQuestion) {
		this.checkboxQuestion = checkboxQuestion;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public int getCanVote() {
		return canVote;
	}

	public void setCanVote(int canVote) {
		this.canVote = canVote;
	}

	public PeVotePaperService getPeVotePaperService() {
		return peVotePaperService;
	}

	public void setPeVotePaperService(PeVotePaperService peVotePaperService) {
		this.peVotePaperService = peVotePaperService;
	}

	public long getVoteNumber() {
		return voteNumber;
	}

	public void setVoteNumber(long voteNumber) {
		this.voteNumber = voteNumber;
	}

	public List<PeVotePaper> getPeVotePaperList() {
		return peVotePaperList;
	}

	public void setPeVotePaperList(List<PeVotePaper> peVotePaperList) {
		this.peVotePaperList = peVotePaperList;
	}

	public String getTogo() {
		return togo;
	}

	public void setTogo(String togo) {
		this.togo = togo;
	}

	public String getQuestionNoteValue() {
		return questionNoteValue;
	}

	public void setQuestionNoteValue(String questionNoteValue) {
		this.questionNoteValue = questionNoteValue;
	}
	/*
	 * public String abstractList() { initGrid();
	 * 
	 * Page page = list(); List jsonObjects =
	 * JsonUtil.ArrayToJsonObjects(page.getItems(), this
	 * .getGridConfig().getListColumnConfig()); Map map = new HashMap(); if
	 * (page != null) { map.put("totalCount", page.getTotalCount());
	 * map.put("models", jsonObjects); }
	 * this.setJsonString(JsonUtil.toJSONString(map));
	 * JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss"); return json(); }
	 */

}
