package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.Reader;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.FrequentlyAskedQuestions;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.FrequentlyAskedQuestionsService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class FrequentlyAskedQuestionsAction extends MyBaseAction<FrequentlyAskedQuestions> {

	private FrequentlyAskedQuestionsService frequentlyAskedQuestionsService;
	private boolean error = false;
	private String id;
	private FrequentlyAskedQuestions question;
	private String solution;
	private List type_list;
	private List role_list;
	private String title;
	private String types;
	private String typesIds;
	private String keyword;
	private String roles;
	private String description;
	private String remark;
	private String rolesIds;
	private String msg;

	public FrequentlyAskedQuestions getQuestion() {
		return question;
	}

	public void setQuestion(FrequentlyAskedQuestions question) {
		this.question = question;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public List getType_list() {
		return type_list;
	}

	public void setType_list(List type_list) {
		this.type_list = type_list;
	}

	public List getRole_list() {
		return role_list;
	}

	public void setRole_list(List role_list) {
		this.role_list = role_list;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getTypesIds() {
		return typesIds;
	}

	public void setTypesIds(String typesIds) {
		this.typesIds = typesIds;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRolesIds() {
		return rolesIds;
	}

	public void setRolesIds(String rolesIds) {
		this.rolesIds = rolesIds;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = false;
		boolean canUpdate = false;
		boolean canDelete = false;
		/* 添加按钮 */
//		if (capabilitySet.contains(this.servletPath + "_add.action")) {
//			canAdd = true;
//		}
		/* 删除按钮 */
		if (capabilitySet.contains(this.servletPath + "_delete.action")) {
			canDelete = true;
		}
		/* 双击修改 */
//		if (capabilitySet.contains(this.servletPath + "_update.action")) {
//			canUpdate = true;
//		}
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);

		this.getGridConfig().setTitle("常见问题库");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("问题标题"), "title", true, true, true, "TextField");

//		ColumnConfig columnConfigQuestionType = new ColumnConfig(this.getText("问题分类"), "types", true, true, true, "TextField", false, 100, "");
//		String sql = "select a.name ,a.name from enum_const a where a.namespace='FlagFrequentlyQuestionType'";
//		columnConfigQuestionType.setComboSQL(sql);
//		this.getGridConfig().addColumn(columnConfigQuestionType);
		this.getGridConfig().addColumn(this.getText("问题分类"), "types", true, true, true, "TextField");

		this.getGridConfig().addColumn(this.getText("关键词"), "keywords", true, true, true, "TextField", false, 200, "");

//		ColumnConfig columnConfigAskerRole = new ColumnConfig(this.getText("用户类型"), "roles", true, true, true, "TextField", true, 100, "");
//		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagAskerRole'";
//		columnConfigAskerRole.setComboSQL(sql2);
//		this.getGridConfig().addColumn(columnConfigAskerRole);
		this.getGridConfig().addColumn(this.getText("用户类型"), "roles", true, true, true, "TextField");

		this.getGridConfig().addColumn(this.getText("问题描述"), "questionDescription", false, true, false, "TextArea", true, 400, "");

		this.getGridConfig().addColumn(this.getText("备注"), "remarks", false, true, false, "TextArea", true, 100);

		this.getGridConfig().addColumn(this.getText("问题解决方案"), "solution", false, true, false, "TextEditor", true, 2000);
		
		this.getGridConfig().addColumn(this.getText("问题浏览次数"), "views", false, false, true, "TextEditor", true, 100);

		this.getGridConfig().addRenderScript(this.getText("查看详情"), "{return '<a href=\"/entity/basic/frequentlyAskedQuestions_showDetail.action?id='+${value}+'\" target=_blank>查看详情</a>';}", "id");

		if (capabilitySet.contains(this.servletPath + "_impQuestion.action")) {
			this.getGridConfig().addMenuScript(this.getText("导入"), "{window.location='/entity/basic/frequentlyAskedQuestions_implQuestion.action';}");
		}
		if ("3".equals(us.getUserLoginType())) {
			if (capabilitySet.contains(this.servletPath + "_exportQuestions.action")) {
				this.getGridConfig().addMenuScript(
						this.getText("导出问题明细 "),
						"{var m = grid.getSelections();  "
								+ "if(m.length > 0){	         "
								// + " function(){ "
								+ "		var jsonData = '';       " + "		var bId = '';       " + "		for(var i = 0, len = m.length; i < len; i++){" + "			var ss =  m[i].get('id');" + "			if(i==0)	jsonData = jsonData + ss ;" + "			else	jsonData = jsonData + ',' + ss;" + "		}                     "
								+ "		document.getElementById('user-defined-content').style.display='none'; "
								+ "		document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/frequentlyAskedQuestions_resultSetToExcel.action?id=\"+jsonData+\"' method='post' name='formx1' style='display:none'></form>\";" + "		document.formx1.submit();"
								+ "	document.getElementById('user-defined-content').innerHTML=\"\";} else {                    " + " Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}}             ");
			}
		}
		if(capabilitySet.contains(this.servletPath + "_update.action")) {
//			this.getGridConfig().addRenderFunction(this.getText("修改"),
//					"<a href=\"/entity/basic/frequentlyAskedQuestions_toEdit.action?id=${value}\" >修改</a>", "id");
			this.getGridConfig().addRenderScript(this.getText("修改"), "{return '<a href=\"/entity/basic/frequentlyAskedQuestions_toEdit.action?id='+${value}+'\">修改</a>';}", "id");
		}

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = FrequentlyAskedQuestions.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/frequentlyAskedQuestions";
	}

	public void setBean(FrequentlyAskedQuestions instance) {
		super.superSetBean(instance);

	}

	public FrequentlyAskedQuestions getBean() {
		return super.superGetBean();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 课程管理列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT faq.id,faq.title,faq.types,faq.keywords,faq.roles,");
			sqlBuffer.append("description,remarks,solution,views,faq.create_date createDate ");
			sqlBuffer.append("from Frequently_Asked_Questions faq ");
//			sqlBuffer.append("join enum_const ec on faq.flag_question_type = ec.id ");
//			if ("3".equals(us.getUserLoginType())) {
//				sqlBuffer.append("left join enum_const ec1 on faq.flag_asker_role = ec1.id ");
//			} else {
//				sqlBuffer.append("join enum_const ec1 on faq.flag_asker_role = ec1.id ");
//			}

			sqlBuffer.append(" ) WHERE 1 = 1 ");
			/* 拼接查询条件 */
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
				/* 问题类型 */
				if (name.equals("types")) {
					String[] type = value.split(" ");
					String sql = " and (";
					for(int i = 0; i < type.length; i++) {
						sql += " types like'%" + type[i] + "%' or";
					}
					sql = sql.substring(0, sql.lastIndexOf("or")) + ")";
					sqlBuffer.append(sql);
				} else if (name.equals("roles")) {
					String[] role = value.split(" ");
					String sql = " and (";
					for(int i = 0; i < role.length; i++) {
						sql += " roles like'%" + role[i] + "%' or";
					}
					sql = sql.substring(0, sql.lastIndexOf("or")) + ")";
					sqlBuffer.append(sql);
				} else {
					sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
				}
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("enumConstByFlagFrequentlyQuestionType.name")) {
					temp = "FlagFrequentlyQuestionType";
				}
				/* 下线 */
				if (temp.equals("roles")) {
					temp = "roles";
				}
				if (temp.equals("id")) {
					temp = "createDate";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") desc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " desc ");
					}
				} else {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") asc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by createDate desc");
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
	 * 重写框架方法：添加数据
	 * 
	 * @author Lee
	 * @return
	 */
	public Map add() {
		Map map = new HashMap();
		try {
			String sql = "select id from frequently_asked_questions where title = '" + this.getBean().getTitle() + "'";
			List list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0) {
				map.put("success", "false");
				map.put("info", "问题名称已经存在，请检查后重新添加！");
			} else {
				this.superSetBean((FrequentlyAskedQuestions) setSubIds(this.getBean()));
				this.getBean().setCreateDate(new Date());

				String fileLinkStr = this.getBean().getSolution();
				if (fileLinkStr.length() > 0 && fileLinkStr.toLowerCase().indexOf("</a>") != -1) {
					int startIdx = fileLinkStr.toLowerCase().indexOf("<");
					int endIdx = fileLinkStr.toLowerCase().lastIndexOf(">");
					// 截取多个上传附件路径并以","分隔
					String fileLink = fileLinkStr.substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
					this.getBean().setFileLink(fileLink);
				}
				this.getBean().setViews(0);
				this.setBean(this.getFrequentlyAskedQuestionsService().add(this.getBean()));
				map.put("success", "true");
				map.put("info", "一条常见问题添加成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.checkAlternateKey(e, "添加");
		}
		return map;
	}

	public FrequentlyAskedQuestionsService getFrequentlyAskedQuestionsService() {
		return frequentlyAskedQuestionsService;
	}

	public void setFrequentlyAskedQuestionsService(FrequentlyAskedQuestionsService frequentlyAskedQuestionsService) {
		this.frequentlyAskedQuestionsService = frequentlyAskedQuestionsService;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * 加载问题信息
	 * 
	 * @author Lee
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(FrequentlyAskedQuestions.class);
		dc.createCriteria("enumConstByFlagFrequentlyQuestionType", "enumConstByFlagFrequentlyQuestionType");
		dc.createCriteria("enumConstByFlagAskerRole", "enumConstByFlagAskerRole", DetachedCriteria.LEFT_JOIN);
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
			this.setJsonString(JsonUtil.toJSONString(map));
			JsonUtil.setDateformat("yyyy-MM-dd");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}

	/**
	 * excel上传
	 * 
	 * @author linjie
	 * @return
	 */
	public String BatchUpload() {
		try {
			this.saveBatchElective(this.get_upload());
			if (error) {
				return "m_msg";
			}
		} catch (EntityException e) {
			super.setDoLog(false);// 出错信息不记录
			// TODO Auto-generated catch block
			// e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("/entity/basic/frequentlyAskedQuestions_implQuestion.action");
			return "m_msg";
		}
		return "confirmElective";
	}

	/**
	 * 导入问题
	 * 
	 * @author linjie
	 * @return
	 */
	public void saveBatchElective(File file) throws EntityException {
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
			error = true;
			msg.append("表格为空！<br/>");
		}
		if (rows > 1002) {
			error = true;
			msg.append("请保证导入1000条以内的问题，过多的问题分批次进行！<br/>");
			throw new EntityException(msg.toString());
		} else {
			String temp = "";

			EnumConst flagFrequentlyQuestionType;
			EnumConst enumConstByFlagAskerRole;

			for (int i = 1; i < rows; i++) {// 每1000行处理一次start
				try {
					temp = sheet.getCell(0, i).getContents();
					if (temp == null || "".equals(temp)) {
						error = true;
						msg.append("第" + (i + 1) + "行数据，标题不能为空！<br/>");
						continue;
					} else {
						String sql = "select id from frequently_asked_questions where title='" + temp + "'";
						List list = this.getGeneralService().getBySQL(sql);
						if (list != null && list.size() > 0) {
							error = true;
							msg.append("第" + (i + 1) + "行数据，问题名称已存在，请检查后重新填写！<br/>");
							continue;
						}
					}

					temp = sheet.getCell(1, i).getContents();
					String types = "";
					String typeIds = "";
					if (temp == null || "".equals(temp)) {
						error = true;
						msg.append("第" + (i + 1) + "行数据，问题分类不能为空！<br/>");
						continue;
					} else {
//						flagFrequentlyQuestionType = this.getEnumConstService().getByNamespaceName("FlagFrequentlyQuestionType", temp);
//						if (flagFrequentlyQuestionType == null) {
//							msg.append("第" + (i + 1) + "行数据，问题分类不存在，请根据模板提示重新填写!");
//						}
						String[] typeStr = temp.split(",");
						for(int k = 0; k < typeStr.length; k++) {
							flagFrequentlyQuestionType = this.getEnumConstService().getByNamespaceName("FlagFrequentlyQuestionType", typeStr[k]);
							if (flagFrequentlyQuestionType == null) {
								msg.append("第" + (i + 1) + "行数据，问题分类不存在，请根据模板提示重新填写!");
							}
							typeIds += flagFrequentlyQuestionType.getId() + ",";
							types += typeStr[k] + ",";
						}
						types = types.substring(0, types.length() - 1);
						typeIds = typeIds.substring(0, typeIds.length() - 1);
					}

					temp = sheet.getCell(2, i).getContents();
					if (temp == null || "".equals(temp)) {
						error = true;
						msg.append("第" + (i + 1) + "行数据，关键词不能为空！<br/>");
						continue;
					}

					temp = sheet.getCell(3, i).getContents();
					String roleStr = "";
					if(temp != null && !"".equals(temp)) {
						String[] roles = temp.split(",");
						String sql = "SELECT EC.ID FROM ENUM_CONST EC WHERE EC.NAMESPACE = 'FlagAskerRole' AND EC.NAME IN('";
						for(int j = 0; j < roles.length; j++) {
							sql += roles[j] + "','";
						}
						sql += "')";
						List role_list = this.getGeneralService().getBySQL(sql);
						if(role_list == null || role_list.size() <= 0) {
							for(int k = 0; k < role_list.size(); k++) {
								roleStr += role_list.get(k) + ",";
							}
							roleStr = roleStr.substring(0, roleStr.length() - 1);
						}
					}
					
//					if (temp == null || "".equals(temp)) {
//						error = true;
//						msg.append("第" + (i + 1) + "行数据，提问者角色不能为空！<br/>");
//						continue;
//					} else {
//						enumConstByFlagAskerRole = this.getEnumConstService().getByNamespaceName("FlagAskerRole", temp);
//						if (enumConstByFlagAskerRole == null) {
//							msg.append("第" + (i + 1) + "行数据，提问者角色不存在，请根据模板提示重新填写!");
//						}
//					}

					FrequentlyAskedQuestions question = new FrequentlyAskedQuestions();
					question.setTitle(sheet.getCell(0, i).getContents());
//					question.setEnumConstByFlagFrequentlyQuestionType(flagFrequentlyQuestionType);
					question.setTypes(types);
					question.setTypesIds(typeIds);
					question.setKeywords(sheet.getCell(2, i).getContents());
					question.setRoles(sheet.getCell(3, i).getContents());
					question.setRoleIds(roleStr);
					question.setQuestionDescription(sheet.getCell(4, i).getContents());
					question.setSolution(sheet.getCell(5, i).getContents());
					question.setRemarks(sheet.getCell(6, i).getContents());
					question.setCreateDate(new Date());
					this.getGeneralService().save(question);
				} catch (Exception e) {
					e.printStackTrace();
					error = true;
					msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
					continue;
				}

			}// 每1000行处理一次end

			if (msg.length() > 0) {
				msg.append("常见问题上传失败，请修改以上错误之后重新上传！<br/>");
				throw new EntityException(msg.toString());
			}
		}

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
			HSSFSheet sheet = wb.createSheet("常见问题列表");
			HSSFRow subRow = sheet.createRow((int) 0);
			HSSFCell subCell = subRow.createCell((short) 0);
			subCell.setCellValue("编号");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 1);
			subCell.setCellValue("问题标题");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 2);
			subCell.setCellValue("问题分类");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 3);
			subCell.setCellValue("关键词");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 4);
			subCell.setCellValue("提问者角色");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 5);
			subCell.setCellValue("问题描述");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 6);
			subCell.setCellValue("问题解决方案");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 7);
			subCell.setCellValue("备注");
			subCell.setCellStyle(style);

			if (id != null && !"".equals(id)) {
				String[] ids = id.split(",");
				Reader inStream = null;
				String sql = "select faq.title,faq.types questionType,faq.keywords,faq.roles,faq.description,faq.solution,faq.remarks from Frequently_Asked_Questions faq "
						+ "where faq.id in (";
				for (int i = 0; i < ids.length; i++) {
					sql += "'" + ids[i] + "',";
				}
				sql += "'') order by faq.create_date desc";

				List questions = new ArrayList();
				questions = this.getGeneralService().getBySQL(sql);

				for (int k = 0; k < questions.size(); k++) {
					inStream = null;
					String data = "";
					subRow = sheet.createRow((int) k + 1);
					Object[] question = (Object[]) questions.get(k);
					Clob clob = (Clob) question[5];
					if (clob != null && !"".equals(clob)) {
						inStream = clob.getCharacterStream();
						char[] c = new char[(int) clob.length()];
						inStream.read(c);
						data = new String(c);
					}
					subRow.createCell((short) 0).setCellValue(k + 1);
					subRow.createCell((short) 1).setCellValue((String) question[0]);
					subRow.createCell((short) 2).setCellValue((String) question[1]);
					subRow.createCell((short) 3).setCellValue((String) question[2]);
					subRow.createCell((short) 4).setCellValue((String) question[3]);
					subRow.createCell((short) 5).setCellValue((String) question[4]);
					subRow.createCell((short) 6).setCellValue(data);
					subRow.createCell((short) 7).setCellValue((String) question[6]);
				}
				if (inStream != null) {
					inStream.close();
				}
				autoWidth(sheet, 8);

				Calendar c = Calendar.getInstance();
				String tm = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日" + c.get(Calendar.HOUR_OF_DAY) + "时" + c.get(Calendar.MINUTE) + "分" + c.get(Calendar.SECOND) + "秒" + "";
				String excelName = "常见问题(" + tm + ").xls";
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + toUtf8String(excelName));
				ServletOutputStream out = response.getOutputStream();
				wb.write(out);
				if (out != null) {
					out.flush();
					out.close();
				}
				this.setMsg("导出成功！");
			}
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
		for (int columnNum = 0; columnNum < maxColNum; columnNum++) {
			sheet.autoSizeColumn((short) columnNum);
		}

	}

	public String implQuestion() {
		return "toImpQuestions";
	}

	/**
	 * 2014-11-09 常见问题详情
	 * 
	 * @return
	 */
	public String showDetail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "UPDATE FREQUENTLY_ASKED_QUESTIONS SET VIEWS = VIEWS + 1 WHERE ID = '" + id + "'";
		try {
			if("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {
				this.getGeneralService().executeBySQL(sql);
			}
			this.question = this.getGeneralService().getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showDetail";
	}
	/**
	 * 2015-03-03 lwq 常见问题添加页面
	 * @return
	 */
	public String addQuestion() {
		String type_sql = "select a.id ,a.name from enum_const a where a.namespace='FlagFrequentlyQuestionType'";
		String role_sql = "select a.id ,a.name from enum_const a where a.namespace='FlagAskerRole'";
		try {
			type_list = this.getGeneralService().getBySQL(type_sql);
			role_list = this.getGeneralService().getBySQL(role_sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "show_add_question";
	}
	
	/**
	 * 2015-03-05 lwq 常见问题添加
	 * @return
	 */
	public String saveQuestion() {
		String sql = "select id from frequently_asked_questions where title = '" + title + "'";
		List list;
		FrequentlyAskedQuestions freQuestion = new FrequentlyAskedQuestions();
		try {
			list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0) {
				msg = "问题已经存在,请检查后重新添加!";
			} else {
				freQuestion.setTitle(title);
//				EnumConst enumConstByFlagFrequentlyQuestionType = new EnumConst();
//				enumConstByFlagFrequentlyQuestionType.setId(type);
//				freQuestion.setEnumConstByFlagFrequentlyQuestionType(enumConstByFlagFrequentlyQuestionType);
				freQuestion.setTypes(types);
				freQuestion.setTypesIds(typesIds);
				freQuestion.setKeywords(keyword);
				freQuestion.setRoles(roles);
				freQuestion.setRoleIds(rolesIds);
				freQuestion.setQuestionDescription(description);
				freQuestion.setRemarks(remark);
				freQuestion.setSolution(solution);
				freQuestion.setCreateDate(new Date());
				freQuestion.setViews(0);
				this.getGeneralService().save(freQuestion);
				msg = "添加成功!";
			}
		} catch (EntityException e) {
			msg = "添加失败";
			e.printStackTrace();
		}
		return "do_info";
	}
	
	/**
	 * 2015-03-26 lwq 常见问题修改
	 * @return
	 */
	public String toEdit() {
		String type_sql = "select a.id ,a.name from enum_const a where a.namespace='FlagFrequentlyQuestionType'";
		String role_sql = "select a.id ,a.name from enum_const a where a.namespace='FlagAskerRole'";
		try {
			type_list = this.getGeneralService().getBySQL(type_sql);
			role_list = this.getGeneralService().getBySQL(role_sql);
			question = this.getGeneralService().getById(id);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "to_edit";
	}
	
	
	public String edit() {
		try {
			FrequentlyAskedQuestions question = this.getGeneralService().getById(id);
			question.setTitle(title);
//			EnumConst enumConstByFlagFrequentlyQuestionType = new EnumConst();
//			enumConstByFlagFrequentlyQuestionType.setId(type);
//			question.setEnumConstByFlagFrequentlyQuestionType(enumConstByFlagFrequentlyQuestionType);
			question.setKeywords(keyword);
			question.setRoleIds(rolesIds);
			question.setRoles(roles);
			question.setQuestionDescription(description);
			question.setSolution(solution);
			question.setRemarks(remark);
			question.setTypes(types);
			question.setTypesIds(typesIds);
			this.getGeneralService().update(question);
			msg = "修改成功!";
		} catch (EntityException e) {
			msg = "修改失败!";
			e.printStackTrace();
		}
		return "do_info";
	}

}
