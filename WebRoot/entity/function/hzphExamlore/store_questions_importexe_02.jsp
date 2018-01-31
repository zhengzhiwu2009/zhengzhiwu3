<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page language="java"
	import="javazoom.upload.*,java.util.*,java.io.*"%>
<%@ page
	import="jxl.*,java.text.*,java.io.*,java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page
	import="com.whaty.platform.interaction.*,com.whaty.platform.test.question.*"%>
<%@ page
	import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page
	import="com.whaty.platform.test.TestManage,com.whaty.util.string.encode.*"%>
<%@ page
	import="com.whaty.platform.test.question.CognizeType,com.whaty.platform.database.oracle.dbpool"%>
<%@ include file="../pub/priv.jsp"%>

<%!String fixNull(String value) {
		if (value != null && value.trim().length() > 0) {
		} else {
			value = "";
		}
		return value;
	}%>

<%
	String type = request.getParameter("type");
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	String xlsFile = request.getParameter("xlsFile"); // System.out.println("xlsFile->"+xlsFile);
	int totalSuc = 0;
	Workbook w;
	try {
		w = Workbook.getWorkbook(new File(xlsFile));
	} catch (Exception e) {
		out.println("对不起，您的模板有误！");
%>


<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
<%
		return;
	}

	Sheet sheet = w.getSheet(0);
	InteractionFactory interactionFactory = InteractionFactory
			.getInstance();
	InteractionManage interactionManage = interactionFactory
			.creatInteractionManage(interactionUserPriv);
	TestManage testManage = interactionManage.creatTestManage();

	String creatuser = user.getName();
	if(creatuser==null || "".equals(creatuser)){
		creatuser="教师1";
	}
	//		String creatdate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	java.util.Date sDate = new java.util.Date();
	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String creatdate = sDateFormat.format(sDate);

	String modeltype1 = "";

	try {
		modeltype1 = sheet.getCell(0, 0).getContents();

	} catch (Exception e) {
		out.println("对不起，您的模板有误！");
%>

<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
<%
	return;
	}

	if (modeltype1.indexOf("单选题") == -1
			&& modeltype1.indexOf("多选题") == -1
			&& modeltype1.indexOf("判断题") == -1
			&& modeltype1.indexOf("填空题") == -1
			&& modeltype1.indexOf("问答题") == -1) {
		out.println("对不起，您的模板有误！");
%>

<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
<%
		return;
	}

	int rowsi = sheet.getRows();
	if (rowsi == 2) {
		out.println("对不起，您模板中输入的内容为空！");
%>

<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
<%
		return;
	}

	int cols = sheet.getColumns();

	if (type.equals(QuestionType.DANXUAN)) {
		if (cols >= 9 && "单选题".equals(modeltype1)) {
		} else {
			out.println("对不起，您的模板有误，请重新下载单选题模板！");
%>

<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
<%
			return;
		}
	}
	else if (type.equals(QuestionType.DUOXUAN)) {
		if (cols >= 9 && "多选题".equals(modeltype1)) {
		} else {
			out.println("对不起，您的模板有误，请重新下载多选题模板！");
%>

<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
<%
			return;
		}
	}
	else  if (type.equals(QuestionType.PANDUAN)) {
		if (cols >= 4 && "判断题".equals(modeltype1)) {
		} else {
			out.println("对不起，您的模板有误，请重新下载判断题模板！");
%>

<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
<%
			return;
		}
	}
	else if (type.equals(QuestionType.TIANKONG)) {
		if (cols >= 4 && "填空题".equals(modeltype1)) {
		} else {
			out.println("对不起，您的模板有误，请重新下载填空题模板！");
%>

<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
<%
			return;
		}
	}
	else if (type.equals(QuestionType.WENDA)) {
		if (cols >= 4 && "问答题".equals(modeltype1)) {
		} else {
			out.println("对不起，您的模板有误,请重新下在问答题模板！");
%>

<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
<%
			return;
		}
	}
	
	dbpool db = new dbpool();
	try {
		for (int i = 2; i < sheet.getRows(); i++) {
			if(sheet.getCell(0,i).getContents()!= null && !sheet.getCell(0,i).getContents().equals("")){
			//   难度	建议题目分值	建议作答时间	认知分类	题目用途	题目名称	题目内容	题目提示	题目要求	选项A	选项B	选项C	选项D	选项E	答案
			String diff = "0.0";
			String referencescore = "1";
			String referencetime = "1800";
			String cognizetype = "了解";
			String purpose = "OnlineExam";
			String title = sheet.getCell(0, i).getContents().trim();

			if (title.equals("") || title == null) {
				out.println("第" + (i + 1) + "行的题目名称为空！");
				out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}
			//保证同一门课程下试题题目名称不能重复
			String t_courseId = openCourse.getBzzCourse().getId();
			String sql_t = "select title from test_storequestion_info where title='"
					+ title
					+ "' and lore in (select a.id from test_lore_info a,test_lore_dir b "
					+ "where a.loredir=b.id and b.group_id='"
					+ t_courseId + "')";
			if (db.countselect(sql_t) > 0) {
				out.println("第" + (i + 1) + "行的题目内容有重复，同一课程下题目名称不能重复！");
				out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}
			String questioncore = sheet.getCell(1, i).getContents()
					.trim();
			if (questioncore.equals("") || questioncore == null) {
				out.println("第" + (i + 1) + "行的题目内容为空！");
				out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}
			String studentNote = "";
			String teacherNote = "";
			String modeltype = sheet.getCell(0, 0).getContents();
			if (type.equals(QuestionType.DANXUAN)) {
				if (cols == 11 && "单选题".equals(modeltype)) {
					String xml = "<question><body>" + questioncore
							+ "</body><select>";
					List optionList = new ArrayList();
					String option_strA = sheet.getCell(2, i)
							.getContents().trim();
					String option_strB = sheet.getCell(3, i)
							.getContents().trim();
					if ((option_strA == null || "".equals(option_strA))
							|| (option_strB == null || ""
									.equals(option_strB))) {
						out.println("第" + (i + 1) + "行的选项A和选项B不能为空！");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					for (int j = 2; j <= 6; j++) {
						if (!sheet.getCell(j, i).getContents().trim()
								.equals("")) {
							optionList.add(sheet.getCell(j, i)
									.getContents().trim());
						} else {
							break;
						}
					}
					for (int k = 0; k < optionList.size(); k++) {
						int charCode = k + 65; //选项字母的ASCII码
						String index = String.valueOf((char) charCode); //将ASCII码转换成字母
						xml = xml + "<item><index>" + index
								+ "</index><content>"
								+ (String) optionList.get(k)
								+ "</content></item>";
					}

					String answer = sheet.getCell(7, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						out.println("第" + (i + 1) + "行的答案内容为空！");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					/*
					if(answer.length()!=1){
						out.println("第" + (i+1) +"行的答案应为ABCDE中的一个。");
						out.print("第" + (i+1) + "行导入失败！<br/>");
						continue;
					}*/
					if (answer.length() != 1
							|| (answer.indexOf("A") == -1
									&& answer.indexOf("B") == -1
									&& answer.indexOf("C") == -1
									&& answer.indexOf("D") == -1 && answer
									.indexOf("E") == -1)) {
						out.println("第" + (i + 1) + "行的答案应为ABCDE中的一个。");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					int countans = 0;
					if (answer.indexOf("A") != -1) {
						countans++;
					}
					if (answer.indexOf("B") != -1) {
						countans++;
					}
					if (answer.indexOf("C") != -1) {
						countans++;
					}
					if (answer.indexOf("D") != -1) {
						countans++;
					}
					if (answer.indexOf("E") != -1) {
						countans++;
					}
					if (countans > 1) {
						out.println("第" + (i + 1) + "行的答案应为ABCDE中的一个。");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					xml = xml + "</select><answer>" + answer
							+ "</answer></question>";
					String loreName = sheet.getCell(8, i).getContents()
							.trim();
					if (loreName.equals("") || loreName == null) {
						out.println("第" + (i + 1) + "行，请输入知识点名称");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					String score =sheet.getCell(9,i).getContents().trim();
					if(score !=null && !score.equals("")){
						referencescore =score;
					}
					String discription =sheet.getCell(10,i).getContents().trim();
					
					String sqllore = " select tli.id from test_lore_info tli where tli.name='"
							+ loreName
							+ "' and tli.loredir='"
							+ loreDirId + "' ";
					String lore_Id = "";
					MyResultSet rs = null;
					try{
						rs = db.executeQuery(sqllore);
	
						if (rs != null && rs.next()) {
							lore_Id = rs.getString("id");
						}
	
						//int k=	db.countselect(sqllore);
	
						else {
	
							String sqladdLore = "insert into test_lore_info(id, name, creatdate, content, loredir, createrid, active ,discription) values ("
									+ " to_char(s_test_lore_info_id.nextval), '"
									+ loreName
									+ "', '','"
									+ loreName
									+ "', '"
									+ loreDirId
									+ "', '"
									+ " 1111 ', '1' ,'"+discription+"') ";
	
							int j = db.executeUpdate(sqladdLore);
	
							String sqllore_02 = " select tli.id from test_lore_info tli where tli.name='"
									+ loreName
									+ "' and tli.loredir='"
									+ loreDirId + "' ";
							MyResultSet rs1 = null;
							try{
								rs1 = db.executeQuery(sqllore_02);
								if (rs1 != null && rs1.next()) {
									lore_Id = rs1.getString("id");
								} else {
									out.println("操作失败，请稍后重试");
									return;
								}
							}catch(Exception e){
								
							}finally{
								db.close(rs1);
							}
						}
					}catch(Exception e){
						
					}finally{
						db.close(rs);
					}

					int suc = testManage.addStoreQuestion(title,
							creatuser, creatdate, diff, "keyword", xml,
							loreDirId, loreName, lore_Id, cognizetype,
							purpose, referencescore, referencetime,
							studentNote, teacherNote, type,"");
					String sqlx = "insert into test_storequestion_info(id,title,creatuser,creatdate,diff,keyword,lore,"
						+ "cognizetype,purpose,referencescore,referencetime,teachernote,studentnote,questioncore,type) values (to_char(s_test_storequestion_info.nextval), '"
						+ title
						+ "', '"
						+ creatuser
						+ "', to_date('"
						+ creatdate
						+ "','yyyy-mm-dd'), '"
						+ diff
						+ "', '"
						+ "keyword"
						+ "', '"
						+ lore_Id
						+ "', '"
						+ cognizetype
						+ "', '"
						+ purpose
						+ "', '"
						+ referencescore
						+ "', '"
						+ referencetime
						+ "', '"
						+ teacherNote
						+ "', '"
						+ studentNote
						+ "',? "
						+ ", '"
						+ type
						+ "')";
					if (suc < 1) {
						//out.print(sqlx+"<br/>");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					out.print("请选择正确的题目模板！<br/>");
				}
			}

			if (type.equals(QuestionType.DUOXUAN)) {
				if (cols == 11 && "多选题".equals(modeltype)) {
					String xml = "<question><body>" + questioncore
							+ "</body><select>";
					List optionList = new ArrayList();

					String option_strA = sheet.getCell(2, i)
							.getContents().trim();
					String option_strB = sheet.getCell(3, i)
							.getContents().trim();
					if ((option_strA == null || "".equals(option_strA))
							|| (option_strB == null || ""
									.equals(option_strB))) {
						out.println("第" + (i + 1) + "行的选项A和选项B不能为空！");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					for (int j = 2; j <= 6; j++) {
						if (!sheet.getCell(j, i).getContents().trim()
								.equals("")) {
							optionList.add(sheet.getCell(j, i)
									.getContents().trim());
						} else {
							break;
						}
					}
					for (int k = 0; k < optionList.size(); k++) {
						int charCode = k + 65; //选项字母的ASCII码
						String index = String.valueOf((char) charCode); //将ASCII码转换成字母
						xml = xml + "<item><index>" + index
								+ "</index><content>"
								+ (String) optionList.get(k)
								+ "</content></item>";
					}
					String answer = sheet.getCell(7, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						out.println("第" + (i + 1) + "行的答案内容为空！");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					if (answer.indexOf("A") == -1
							&& answer.indexOf("B") == -1
							&& answer.indexOf("C") == -1
							&& answer.indexOf("D") == -1
							&& answer.indexOf("E") == -1) {
						out.println("第" + (i + 1)
								+ "行的答案应为ABCDE中的一个或多个,且用‘|’隔开。");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					if (answer.length() > 1
							&& answer.indexOf("|") == -1) {
						out.println("第" + (i + 1)
								+ "行的答案应为ABCDE中的一个或多个,且用‘|’隔开。");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					xml = xml + "</select><answer>" + answer
							+ "</answer></question>";
					String loreName = sheet.getCell(8, i).getContents()
							.trim();
					if (loreName.equals("") || loreName == null) {
						out.println("第" + (i + 1) + "行，请输入知识点名称");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					String score =sheet.getCell(9, i).getContents()
							.trim();
							if(score !=null && !score.equals("")){
							referencescore =score;
							}
					String discrption = sheet.getCell(10, i).getContents()
							.trim();
							
					String sqllore = " select tli.id from test_lore_info tli where tli.name='"
							+ loreName
							+ "' and tli.loredir='"
							+ loreDirId + "' ";
					String lore_Id = "";
					MyResultSet rs = null;
					try{
						rs = db.executeQuery(sqllore);
	
						if (rs != null && rs.next()) {
							lore_Id = rs.getString("id");
						}else {
	
							String sqladdLore = "insert into test_lore_info(id, name, creatdate, content, loredir, createrid, active ,discription) values ("
									+ " to_char(s_test_lore_info_id.nextval), '"
									+ loreName
									+ "', '','"
									+ loreName
									+ "', '"
									+ loreDirId
									+ "', '"
									+ " 1111 ', '1','"+discrption+"') ";
	
							int j = db.executeUpdate(sqladdLore);
	
							String sqllore_02 = " select tli.id from test_lore_info tli where tli.name='"
									+ loreName
									+ "' and tli.loredir='"
									+ loreDirId + "' ";
							MyResultSet rs1 = null;
							try{
								rs1 = db.executeQuery(sqllore_02);
								if (rs1 != null && rs1.next()) {
									lore_Id = rs1.getString("id");
								} else {
									out.println("操作失败，请稍后重试");
									return;
								}
							}catch(Exception e){
								
							}finally{
								db.close(rs1);
							}
						}
					}catch(Exception e){
						
					}finally{
						db.close(rs);
					}

					int suc = testManage.addStoreQuestion(title,
							creatuser, creatdate, diff, "keyword", xml,
							loreDirId, loreName, lore_Id, cognizetype,
							purpose, referencescore, referencetime,
							studentNote, teacherNote, type ,"");

					if (suc < 1) {
						out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					out.print("请选择正确的题目模板！<br/>");
				}
			}

			if (type.equals(QuestionType.PANDUAN)) {
				if (cols == 6 && "判断题".equals(modeltype)) {
					String answer = sheet.getCell(2, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						out.println("第" + (i + 1) + "行的答案内容为空！");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					if (answer.indexOf("正确") == -1
							&& answer.indexOf("错误") == -1) {
						out.println("第" + (i + 1)
								+ "行的答案内容只能为“正确”或“错误”！");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					int countanser = 0;
					if (answer.indexOf("正确") != -1) {
						countanser++;
					}
					if (answer.indexOf("错误") != -1) {
						countanser++;
					}
					if (countanser > 1) {
						out.println("第" + (i + 1)
								+ "行的答案内容只能为“正确”或“错误”,只能是一个。！");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					if (answer.trim().equals("正确")) {
						answer = "1";
					}
					if (answer.trim().equals("错误")) {
						answer = "0";
					}

					String xml = "<question><body>" + questioncore
							+ "</body><answer>" + answer
							+ "</answer></question>";
					String loreName = sheet.getCell(3, i).getContents()
							.trim();
					if (loreName.equals("") || loreName == null) {
						out.println("第" + (i + 1) + "行，请输入知识点名称");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					String score = sheet.getCell(4, i).getContents()
							.trim();
							if(score !=null && !score.equals("")){
							referencescore =score;
							}
					String discription =sheet.getCell(5, i).getContents()
							.trim();
					String sqllore = " select tli.id from test_lore_info tli where tli.name='"
							+ loreName
							+ "' and tli.loredir='"
							+ loreDirId + "' ";
					String lore_Id = "";
					MyResultSet rs = null;
					try{
						rs = db.executeQuery(sqllore);
	
						if (rs != null && rs.next()) {
							lore_Id = rs.getString("id");
						}
	
						//int k=	db.countselect(sqllore);
	
						else {
	
							String sqladdLore = "insert into test_lore_info(id, name, creatdate, content, loredir, createrid, active,discription) values ("
									+ " to_char(s_test_lore_info_id.nextval), '"
									+ loreName
									+ "', '','"
									+ loreName
									+ "', '"
									+ loreDirId
									+ "', '"
									+ " 1111 ', '1','"+discription+"') ";
	
							int j = db.executeUpdate(sqladdLore);
	
							String sqllore_02 = " select tli.id from test_lore_info tli where tli.name='"
									+ loreName
									+ "' and tli.loredir='"
									+ loreDirId + "' ";
							MyResultSet rs1 = null;
							try{
								rs1 = db.executeQuery(sqllore_02);
								if (rs1 != null && rs1.next()) {
									lore_Id = rs1.getString("id");
								} else {
									out.println("操作失败，请稍后重试");
									return;
								}
							}catch(Exception e){
								
							}finally{
								db.close(rs1);
							}
						}
					}catch(Exception e){
						
					}finally{
						db.close(rs);
					}

					int suc = testManage.addStoreQuestion(title,
							creatuser, creatdate, diff, "keyword", xml,
							loreDirId, loreName, lore_Id, cognizetype,
							purpose, referencescore, referencetime,
							studentNote, teacherNote, type,"");

					if (suc < 1) {
						out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					out.print("请选择正确的题目模板！<br/>");
				}
			}

			if (type.equals(QuestionType.TIANKONG)) {
				if (cols == 6 && "填空题".equals(modeltype)) {
					String answer = sheet.getCell(2, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						out.println("第" + (i + 1) + "行的答案内容为空！");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					String xml = "<question><body>" + questioncore
							+ "</body><answer>" + answer
							+ "</answer></question>";
					String loreName = sheet.getCell(3, i).getContents()
					.trim();
			if (loreName.equals("") || loreName == null) {
				out.println("第" + (i + 1) + "行，请输入知识点名称");
				out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}
			String score = sheet.getCell(4, i).getContents()
							.trim();
							if(score !=null && !score.equals("")){
							referencescore =score;
							}
					String discription =sheet.getCell(5, i).getContents()
							.trim();
			String sqllore = " select tli.id from test_lore_info tli where tli.name='"
					+ loreName
					+ "' and tli.loredir='"
					+ loreDirId + "' ";
			String lore_Id = "";
			MyResultSet rs = null;
			try{
				rs = db.executeQuery(sqllore);

				if (rs != null && rs.next()) {
					lore_Id = rs.getString("id");
				}

				//int k=	db.countselect(sqllore);

				else {

					String sqladdLore = "insert into test_lore_info(id, name, creatdate, content, loredir, createrid, active,discription) values ("
							+ " to_char(s_test_lore_info_id.nextval), '"
							+ loreName
							+ "', '','"
							+ loreName
							+ "', '"
							+ loreDirId
							+ "', '"
							+ " 1111 ', '1','"+discription+"') ";

					int j = db.executeUpdate(sqladdLore);

					String sqllore_02 = " select tli.id from test_lore_info tli where tli.name='"
							+ loreName
							+ "' and tli.loredir='"
							+ loreDirId + "' ";
					MyResultSet rs1 = null;
					try{
						rs1 = db.executeQuery(sqllore_02);
						if (rs1 != null && rs1.next()) {
							lore_Id = rs1.getString("id");
						} else {
							out.println("操作失败，请稍后重试");
							return;
						}
					}catch(Exception e){
						
					}finally{
						db.close(rs1);
					}
				}
			}catch(Exception e){
				
			}finally{
				db.close(rs);
				
			}	
			int suc = testManage.addStoreQuestion(title,
					creatuser, creatdate, diff, "keyword", xml,
					loreDirId, loreName, lore_Id, cognizetype,
					purpose, referencescore, referencetime,
					studentNote, teacherNote, type,"");
				
					if (suc < 1) {
						out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					out.print("请选择正确的题目模板！<br/>");
				}
			}

			if (type.equals(QuestionType.WENDA)) {
				if (cols == 6 && "问答题".equals(modeltype)) {
					String answer = sheet.getCell(2, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						out.println("第" + (i + 1) + "行的答案内容为空！");
						out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					String xml = "<question><body>" + questioncore
							+ "</body><answer>" + answer
							+ "</answer></question>";
					String loreName = sheet.getCell(3, i).getContents()
					.trim();
			if (loreName.equals("") || loreName == null) {
				out.println("第" + (i + 1) + "行，请输入知识点名称");
				out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}
			String score = sheet.getCell(4, i).getContents()
							.trim();
							if(score !=null && !score.equals("")){
							referencescore =score;
							}
					String discription =sheet.getCell(5, i).getContents()
							.trim();
			String sqllore = " select tli.id from test_lore_info tli where tli.name='"
					+ loreName
					+ "' and tli.loredir='"
					+ loreDirId + "' ";
			String lore_Id = "";
			MyResultSet rs = null;
			try{
				rs = db.executeQuery(sqllore);

				if (rs != null && rs.next()) {
					lore_Id = rs.getString("id");
				}

				//int k=	db.countselect(sqllore);

				else {

					String sqladdLore = "insert into test_lore_info(id, name, creatdate, content, loredir, createrid, active,discription) values ("
							+ " to_char(s_test_lore_info_id.nextval), '"
							+ loreName
							+ "', '','"
							+ loreName
							+ "', '"
							+ loreDirId
							+ "', '"
							+ " 1111 ', '1','"+discription+"') ";

					int j = db.executeUpdate(sqladdLore);

					String sqllore_02 = " select tli.id from test_lore_info tli where tli.name='"
							+ loreName
							+ "' and tli.loredir='"
							+ loreDirId + "' ";
					MyResultSet rs1 = null;
					try{
						rs1 = db.executeQuery(sqllore_02);
						if (rs1 != null && rs1.next()) {
							lore_Id = rs1.getString("id");
						} else {
							out.println("操作失败，请稍后重试");
							return;
						}
					}catch(Exception e){
						
					}finally{
						db.close(rs1);
					}
				}
			}catch(Exception e){
				
			}finally{
				db.close(rs);
				
			}	int suc = testManage.addStoreQuestion(title,
					creatuser, creatdate, diff, "keyword", xml,
					loreDirId, loreName, lore_Id, cognizetype,
					purpose, referencescore, referencetime,
					studentNote, teacherNote, type,"");
					
					if (suc < 1) {
						out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					out.print("请选择正确的题目模板！<br/>");
				}
			}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		db=null;
	}
%>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<title>插入附件</title>
	</head>
	<script language="javascript">
// opener.location.reload();
</script>
	<body topmargin="10" leftmargin="10" rightmargin="0">
		<br/>
		<p class="text3" align="center">
			成功导入<%=totalSuc%>道题目！
		</p>
		<br/>
		<p align="center">
			<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
		</p>
	</body>
</html>
