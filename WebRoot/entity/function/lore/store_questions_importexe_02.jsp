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
	StringBuffer msg_sb = new StringBuffer();
	String type = request.getParameter("type");
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	String xlsFile = request.getParameter("xlsFile"); // System.out.println("xlsFile->"+xlsFile);
	int totalSuc = 0;
	Workbook w;
	try {
		w = Workbook.getWorkbook(new File(xlsFile));
	} catch (Exception e) {
		msg_sb.append("对不起，您的模板有误！</br>");
		//out.println("对不起，您的模板有误！");
%>

<p class="text3" align="center">
	<%=msg_sb %>
</p>
<p align="center">
	<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
</p>
<%
		return;
	}

	Sheet sheet = w.getSheet(0);
	InteractionFactory interactionFactory = InteractionFactory
			.getInstance();
	InteractionManage interactionManage = interactionFactory
			.creatInteractionManage(interactionUserPriv);
	TestManage testManage = interactionManage.creatTestManage();

	String creatuser = new String(user.getName().getBytes("iso-8859-1"),"UTF-8");
	
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
		msg_sb.append("对不起，您的模板有误！</br>");
		//out.println("对不起，您的模板有误！");
%>
<p class="text3" align="center">
	<%=msg_sb %>
</p>
<p align="center">
	<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
</p>
<%
	return;
	}

	if (modeltype1.indexOf("单选题") == -1
			&& modeltype1.indexOf("多选题") == -1
			&& modeltype1.indexOf("判断题") == -1
			&& modeltype1.indexOf("填空题") == -1
			&& modeltype1.indexOf("问答题") == -1) {
		msg_sb.append("对不起，您的模板有误！</br>");
		//out.println("对不起，您的模板有误！");
%>
<p class="text3" align="center">
	<%=msg_sb %>
</p>
<p align="center">
	<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
</p>
<%
		return;
	}

	int rowsi = sheet.getRows();
	if (rowsi == 2) {
		msg_sb.append("对不起，您模板中输入的内容为空！</br>");
		//out.println("对不起，您模板中输入的内容为空！");
%>
<p class="text3" align="center">
	<%=msg_sb %>
</p>
<p align="center">
	<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
</p>
<%
		return;
	}

	int cols = sheet.getColumns();
	if (type.equals(QuestionType.DANXUAN)) {
		if (cols >= 11 && "单选题".equals(modeltype1)) {
		} else {
			msg_sb.append("对不起，您的模板有误，请重新下载单选题模板！</br>");
			//out.println("对不起，您的模板有误，请重新下载单选题模板！");
%>
<p class="text3" align="center">
	<%=msg_sb %>
</p>
<p align="center">
	<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
</p>
<%
			return;
		}
	}
	else if (type.equals(QuestionType.DUOXUAN)) {
		if (cols >= 11 && "多选题".equals(modeltype1)) {
		} else {
			msg_sb.append("对不起，您的模板有误，请重新下载多选题模板！</br>");
			//out.println("对不起，您的模板有误，请重新下载多选题模板！");
%>
<p class="text3" align="center">
	<%=msg_sb %>
</p>
<p align="center">
	<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
</p>
<%
			return;
		}
	}
	else  if (type.equals(QuestionType.PANDUAN)) {
		if (cols >= 6 && "判断题".equals(modeltype1)) {
		} else {
			msg_sb.append("对不起，您的模板有误，请重新下载判断题模板！</br>");
			//out.println("对不起，您的模板有误，请重新下载判断题模板！");
%>
<p class="text3" align="center">
	<%=msg_sb %>
</p>
<p align="center">
	<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
</p>
<%
			return;
		}
	}
	else if (type.equals(QuestionType.TIANKONG)) {
		if (cols == 10 && "填空题".equals(modeltype1)) {
		} else {
			msg_sb.append("对不起，您的模板有误，请重新下载填空题模板！</br>");
			//out.println("对不起，您的模板有误，请重新下载填空题模板！");
%>
<p class="text3" align="center">
	<%=msg_sb %>
</p>
<p align="center">
	<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
</p>
<%
			return;
		}
	}
	else if (type.equals(QuestionType.WENDA)) {
		if (cols == 10 && "问答题".equals(modeltype1)) {
		} else {
			msg_sb.append("对不起，您的模板有误，请重新下载问答题模板！</br>");
			//out.println("对不起，您的模板有误,请重新下在问答题模板！");
%>
<p class="text3" align="center">
	<%=msg_sb %>
</p>
<p align="center">
	<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
</p>
<%
			return;
		}
	}
	dbpool db = new dbpool();
	try {
		for (int i = 2; i < sheet.getRows(); i++) {
			//   难度	建议题目分值	建议作答时间	认知分类	题目用途	题目名称	题目内容	题目提示	题目要求	选项A	选项B	选项C	选项D	选项E	答案
			String diff = "0.0";
			String referencetime = "1800";
			String cognizetype = "了解";
			
			String referencescore = fixNull(sheet.getCell(2, i).getContents().trim());
			if(referencescore.equals("")|| referencescore== null){
						msg_sb.append("第" + (i+1) +"行的建议题目分值为空！</br>");
						//out.println("第" + (i+1) +"行的建议题目分值为空！");
						msg_sb.append("第" + (i+1) + "行导入失败！<br/>");
						//out.print("第" + (i+1) + "行导入失败！<br/>");
						continue;
					}
					if(!referencescore.equals("")){
						try{
							//Double.parseDouble(referencescore);
							Integer.parseInt(referencescore);
						}catch(Exception e){
							msg_sb.append("建议题目分值应该为0到100之间整数，"+ "第" + (i+1) + "行导入失败！</br>");
							//out.println("建议题目分值应该为0到100之间整数，"+ "第" + (i+1) + "行导入失败！<br/>");
							continue;
						}
						if(Integer.parseInt(referencescore)>100||Integer.parseInt(referencescore)<1){
						msg_sb.append("建议题目分值应该为0到100之间整数，"+ "第" + (i+1) + "行导入失败！</br>");
						//out.println("建议题目分值应该为0到100之间整数，"+ "第" + (i+1) + "行导入失败！<br/>");
						continue;
					}
						
					}

			/**
			//String diff = fixNull(sheet.getCell(0, i).getContents().trim());   	  
			//if(diff.equals("")|| diff== null){
			//		out.println("第" + (i+1) +"行的难度值为空！<br/>");
			//		out.print("第" + (i+1) + "行导入失败！<br/>");
			//		continue;
			//}
			
			//if(!diff.equals("")){
			//		try{
						//Integer.parseInt(diff);
			//			Double.parseDouble(diff);
			//		}catch(Exception e){
			//			out.println("难度值应该在0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0中的一个"+ "第" + (i+1) + "行导入失败！<br/>");
			//			continue;
			//		}
					
			//		if(Double.parseDouble(diff)>1||Double.parseDouble(diff)<0||diff.length()!=3){
			//			out.println("难度值应该在0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0中的一个"+ "第" + (i+1) + "行导入失败！<br/>");
			//			continue;
			//		}

			//}
			
				
			
			/**
			//String referencetime = fixNull( sheet.getCell(0, i).getContents().trim());
				
			//if(referencetime.equals("")|| referencetime== null){
			//			out.println("第" + (i+1) +"行的建议作答时间为空！");
			//			out.print("第" + (i+1) + "行导入失败！<br/>");
			//			continue;
			///		}
			//if(!referencetime.equals("")){
			//			try{
			//				//Double.parseDouble(referencescore);
			//				Integer.parseInt(referencetime);
			//			}catch(Exception e){
			//				out.println("建议作答时间应该为大于0的整数，"+ "第" + (i+1) + "行导入失败！<br/>");
			//				continue;
			//			}
			//			if(Integer.parseInt(referencetime)<1){
			//			out.println("建议作答时间应该为大于0的整数，"+ "第" + (i+1) + "行导入失败！<br/>");
			//			continue;
			//		}
						
			//		}
			
			
			//String cognizetype = fixNull(sheet.getCell(1, i).getContents().trim());
			//System.out.println(cognizetype+"=========================================");
			
			//if(cognizetype.equals("")|| cognizetype== null){
			//			out.println("第" + (i+1) +"行的认知分类为空！");
			//			out.print("第" + (i+1) + "行导入失败！<br/>");
			//			continue;
			//		}
			//if(cognizetype.length()!=2)	{
			//	out.println("第" + (i+1) +"行的认知分类应为 了解|理解|应用|分析|综合|评鉴的一个，且只能是一个！");
			//	out.print("第" + (i+1) + "行导入失败！<br/>");
			//	continue;
			//}	
			//if(cognizetype!=null&&cognizetype.trim().length()==2){
			//	int countcon =0;
			//	if(cognizetype.indexOf("了解")==-1&&cognizetype.indexOf("理解")==-1&&cognizetype.indexOf("应用")==-1&&cognizetype.indexOf("分析")==-1&&cognizetype.indexOf("综合")==-1&&cognizetype.indexOf("评鉴")==-1){
			//		out.println("第" + (i+1) +"行的认知分类应为 了解|理解|应用|分析|综合|评鉴的一个，且只能是一个！");
			//				out.print("第" + (i+1) + "行导入失败！<br/>");
			//				continue;
			//	}
			
			//	if(cognizetype.indexOf("了解")!=-1){
			//		countcon ++;
			//	}
			//	if(cognizetype.indexOf("理解")!=-1){
			//		countcon ++;
			//	}
			//	if(cognizetype.indexOf("应用")!=-1){
			//		countcon ++;
			//	}
			//	if(cognizetype.indexOf("分析")!=-1){
			//		countcon ++;
			//	}
			//	if(cognizetype.indexOf("综合")!=-1){
			//		countcon ++;
			//	}
			//	if(cognizetype.indexOf("评鉴")!=-1){
			//		countcon ++;
			//	}
			//	if(countcon>1){
			//		out.println("第" + (i+1) +"行的认知分类应为 了解|理解|应用|分析|综合|评鉴的一个，且只能是一个！");
			//				out.print("第" + (i+1) + "行导入失败！<br/>");
			//				continue;
			//	}
			//	   if(cognizetype.equalsIgnoreCase("了解")){
			//	 	    cognizetype= cognizetype.replace("了解","LIAOJIE");
			//	   }else if(cognizetype.equalsIgnoreCase("理解")){
			//	   		cognizetype= cognizetype.replace("理解","LIJIE");
			//	   }else if(cognizetype.equalsIgnoreCase("应用")){
			//	   		cognizetype= cognizetype.replace("应用","YINGYONG");
			//	   }else if(cognizetype.equalsIgnoreCase("分析")){
			//	   		cognizetype= cognizetype.replace("分析","FENXI");
			//	   }else if(cognizetype.equalsIgnoreCase("综合")){
			///	   		cognizetype= cognizetype.replace("综合","ZONGHE");
			//	   }else if(cognizetype.equalsIgnoreCase("评鉴")){
			//	   		cognizetype= cognizetype.replace("评鉴","PINGJIAN");
			//	   }
			//	}
			 		
			// **/
			//String purpose = "KAOSHI|EXAM";
			String purpose = sheet.getCell(0, i).getContents().trim();
			if (purpose.equals("") || purpose == null) {
				msg_sb.append("第" + (i + 1) + "行的题目用途为空！</br>");
				//out.println("第" + (i + 1) + "行的题目用途为空！");
				msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
				//out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}
			if (purpose != null && purpose.trim().length() > 0) {
				if (purpose.indexOf("随堂练习") == -1
						&& purpose.indexOf("课后测验") == -1) {
					msg_sb.append("第" + (i + 1) + "行的题目用途应该是 随堂练习|课后测验|中的一个或多个，多个用“|”隔开。</br>");
					//out.println("第" + (i + 1) + "行的题目用途应该是 随堂练习|课后测验|中的一个或多个，多个用“|”隔开。");
					msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
					//out.print("第" + (i + 1) + "行导入失败！<br/>");
					continue;
				}
				if (purpose.length() > 4 && purpose.indexOf("|") == -1) {
					msg_sb.append("第" + (i + 1) + "行的题目用途应该是 随堂练习|课后测验中的一个或多个，多个用“|”隔开。</br>");
					//out.println("第" + (i + 1) + "行的题目用途应该是 随堂练习|课后测验中的一个或多个，多个用“|”隔开。");
					msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
					//out.print("第" + (i + 1) + "行导入失败！<br/>");
					continue;
				}
     
				/**
				 int countpur=0;;	
				 if(purpose.indexOf("在线自测")!=-1){
				 	countpur++;
				 }
				 if(purpose.indexOf("在线作业")!=-1){
				 	countpur++;
				 }
				 if(purpose.indexOf("实验")!=-1){
					countpur++;
				 }
				 if(purpose.indexOf("在线考试")!=-1){
				 	countpur++;
				 }			
				 if(countpur>1){
					 if(purpose.indexOf("在线自测")==-1&&purpose.indexOf("在线作业")==-1&&purpose.indexOf("实验")==-1&&purpose.indexOf("在线考试")==-1){
					 	msg_sb.append("第" + (i+1) +"行的题目用途应该是 在线自测|在线作业|实验|在线考试|中的一个。</br>");
						 //out.println("第" + (i+1) +"行的题目用途应该是 在线自测|在线作业|实验|在线考试|中的一个。");
						 msg_sb.append("第" + (i+1) + "行导入失败！</br>");
						 //out.print("第" + (i+1) + "行导入失败！<br/>");
						 continue;
					 }
				
				 */
				if (purpose.indexOf("课后测验") != -1) {
					purpose = purpose.replace("课后测验", "KAOSHI");
				}
				if (purpose.indexOf("随堂练习") != -1) {
					purpose = purpose.replace("随堂练习", "ZUOYE");
				}

			}
			
			String title = sheet.getCell(1, i).getContents().trim();

			if (title.equals("") || title == null) {
				msg_sb.append("第" + (i + 1) + "行的题目名称为空！</br>");
				//out.println("第" + (i + 1) + "行的题目名称为空！");
				msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
				//out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}
			//保证同一门课程下试题题目名称不能重复
			String t_courseId = openCourse.getBzzCourse().getId();
			String sql_t = "select title from test_storequestion_info where title='"
					+ title
					+ "' and lore in (select a.id from test_lore_info a,test_lore_dir b "
					+ "where a.loredir=b.id and b.group_id='"
					+ t_courseId + "')";
			//dbpool db = new dbpool();
			if (db.countselect(sql_t) > 0) {
				msg_sb.append("第" + (i + 1) + "行的题目内容有重复，同一课程下题目名称不能重复！</br>");
				//out.println("第" + (i + 1) + "行的题目内容有重复，同一课程下题目名称不能重复！");
				msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
				//out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}

			String questioncore = sheet.getCell(3, i).getContents()
					.trim();

			if (questioncore.equals("") || questioncore == null) {
				msg_sb.append("第" + (i + 1) + "行的题目内容为空！</br>");
				//out.println("第" + (i + 1) + "行的题目内容为空！");
				msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
				//out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}

			//	String studentNote =  sheet.getCell(3, i).getContents().trim();
			//	String teacherNote = sheet.getCell(6, i).getContents().trim();
			String studentNote = "";
			String teacherNote = "";
			String modeltype = sheet.getCell(0, 0).getContents();

			if (type.equals(QuestionType.DANXUAN)) {
				if (cols == 12 && "单选题".equals(modeltype)) {
					String xml = "<question><body>" + questioncore
							+ "</body><select>";
					List optionList = new ArrayList();
					String option_strA = sheet.getCell(4, i)
							.getContents().trim();
					String option_strB = sheet.getCell(5, i)
							.getContents().trim();
					if ((option_strA == null || "".equals(option_strA))
							|| (option_strB == null || ""
									.equals(option_strB))) {
						msg_sb.append("第" + (i + 1) + "行的选项A和选项B不能为空！</br>");
						//out.println("第" + (i + 1) + "行的选项A和选项B不能为空！");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					for (int j = 4; j <= 8; j++) {
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

					String answer = sheet.getCell(9, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						msg_sb.append("第" + (i + 1) + "行的答案内容为空！</br>");
						//out.println("第" + (i + 1) + "行的答案内容为空！");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					/*
					if(answer.length()!=1){
						msg_sb.append("第" + (i+1) +"行的答案应为ABCDE中的一个。</br>");
						//out.println("第" + (i+1) +"行的答案应为ABCDE中的一个。");
						msg_sb.append("第" + (i+1) + "行导入失败！</br>");
						//out.print("第" + (i+1) + "行导入失败！<br/>");
						continue;
					}*/
					if (answer.length() != 1
							|| (answer.indexOf("A") == -1
									&& answer.indexOf("B") == -1
									&& answer.indexOf("C") == -1
									&& answer.indexOf("D") == -1 && answer
									.indexOf("E") == -1)) {
						msg_sb.append("第" + (i + 1) + "行的答案应为ABCDE中的一个。</br>");
						//out.println("第" + (i + 1) + "行的答案应为ABCDE中的一个。");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
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
						msg_sb.append("第" + (i + 1) + "行的答案应为ABCDE中的一个。</br>");
						//out.println("第" + (i + 1) + "行的答案应为ABCDE中的一个。");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					xml = xml + "</select><answer>" + answer
							+ "</answer></question>";
					String loreName = sheet.getCell(10, i).getContents()
							.trim();
					if (loreName.equals("") || loreName == null) {
						msg_sb.append("第" + (i + 1) + "行，请输入知识点名称！</br>");
						//out.println("第" + (i + 1) + "行，请输入知识点名称");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					
					String loreDiscription = sheet.getCell(11, i).getContents().trim();
					//if (loreDiscription.equals("") || loreDiscription == null) {
						//msg_sb.append("第" + (i + 1) + "行，请输入知识点描述！</br>");
						//out.println("第" + (i + 1) + "行，请输入知识点描述");
						//msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						//continue;
					//}
					String sqllore = " select tli.id,tli.discription from test_lore_info tli where tli.name='"
							+ loreName
							+ "' and tli.loredir='"
							+ loreDirId + "' ";
					String lore_Id = "";
					MyResultSet rs = null;
					try{
						rs = db.executeQuery(sqllore);
	
						if (rs != null && rs.next()) {
							lore_Id = rs.getString("id");
							//if (!loreDiscription.equals(rs.getString("discription"))) {
								//msg_sb.append("第" + (i + 1) + "行，请输入正确的知识点描述！</br>");
								//out.println("第" + (i + 1) + "行，请输入正确的知识点描述");
								//msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
								//out.print("第" + (i + 1) + "行导入失败！<br/>");
								//continue;
							//}
						}else {
							String sqladdLore = "insert into test_lore_info(id, name, creatdate, content, loredir, createrid, active, discription) values ("
									+ " to_char(s_test_lore_info_id.nextval), '"
									+ loreName
									+ "', '','"
									+ loreName
									+ "', '"
									+ loreDirId
									+ "', '"
									+ " 1111 ', '1', '" + loreDiscription + "') ";
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
									msg_sb.append("操作失败，请稍后重试！</br>");
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
							studentNote, teacherNote, type, loreDiscription);
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
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					msg_sb.append("请选择正确的题目模板！</br>");
					//out.print("请选择正确的题目模板！<br/>");
				}
			}

			if (type.equals(QuestionType.DUOXUAN)) {
				if (cols == 12 && "多选题".equals(modeltype)) {
					String xml = "<question><body>" + questioncore
							+ "</body><select>";
					List optionList = new ArrayList();

					String option_strA = sheet.getCell(4, i)
							.getContents().trim();
					String option_strB = sheet.getCell(5, i)
							.getContents().trim();
					if ((option_strA == null || "".equals(option_strA))
							|| (option_strB == null || ""
									.equals(option_strB))) {
						msg_sb.append("第" + (i + 1) + "行的选项A和选项B不能为空！</br>");
						//out.println("第" + (i + 1) + "行的选项A和选项B不能为空！");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					for (int j = 4; j <= 8; j++) {
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
					String answer = sheet.getCell(9, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						msg_sb.append("第" + (i + 1) + "行的答案内容为空！</br>");
						//out.println("第" + (i + 1) + "行的答案内容为空！");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					if (answer.indexOf("A") == -1
							&& answer.indexOf("B") == -1
							&& answer.indexOf("C") == -1
							&& answer.indexOf("D") == -1
							&& answer.indexOf("E") == -1) {
						msg_sb.append("第" + (i + 1) + "行的答案应为ABCDE中的一个或多个,且用‘|’隔开。</br>");
						//out.println("第" + (i + 1) + "行的答案应为ABCDE中的一个或多个,且用‘|’隔开。");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					if (answer.length() > 1
							&& answer.indexOf("|") == -1) {
						msg_sb.append("第" + (i + 1) + "行的答案应为ABCDE中的一个或多个,且用‘|’隔开！</br>");
						//out.println("第" + (i + 1) + "行的答案应为ABCDE中的一个或多个,且用‘|’隔开。");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					xml = xml + "</select><answer>" + answer
							+ "</answer></question>";
					String loreName = sheet.getCell(10, i).getContents()
							.trim();
					if (loreName.equals("") || loreName == null) {
						msg_sb.append("第" + (i + 1) + "行，请输入知识点名称！</br>");
						//out.println("第" + (i + 1) + "行，请输入知识点名称");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					String loreDiscription = sheet.getCell(11, i).getContents()
							.trim();
					//if (loreDiscription.equals("") || loreDiscription == null) {
						//msg_sb.append("第" + (i + 1) + "行，请输入知识点描述！</br>");
						//out.println("第" + (i + 1) + "行，请输入知识点描述");
						//msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						//continue;
					//}
					String sqllore = " select tli.id,tli.discription from test_lore_info tli where tli.name='"
							+ loreName
							+ "' and tli.loredir='"
							+ loreDirId + "' ";
					String lore_Id = "";
					MyResultSet rs = null;
					try{
						rs = db.executeQuery(sqllore);
	
						if (rs != null && rs.next()) {
							lore_Id = rs.getString("id");
							//if (!loreDiscription.equals(rs.getString("discription"))) {
								//msg_sb.append("第" + (i + 1) + "行，请输入正确的知识点描述！</br>");
								//out.println("第" + (i + 1) + "行，请输入正确的知识点描述");
								//msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
								//out.print("第" + (i + 1) + "行导入失败！<br/>");
								//continue;
							//}
						}else {
	
							String sqladdLore = "insert into test_lore_info(id, name, creatdate, content, loredir, createrid, active, discription) values ("
									+ " to_char(s_test_lore_info_id.nextval), '"
									+ loreName
									+ "', '','"
									+ loreName
									+ "', '"
									+ loreDirId
									+ "', '"
									+ " 1111 ', '1','" + loreDiscription + "') ";
	
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
									msg_sb.append("操作失败，请稍后重试！</br>");
									//out.println("操作失败，请稍后重试");
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
							studentNote, teacherNote, type, loreDiscription);

					if (suc < 1) {
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					msg_sb.append("请选择正确的题目模板！</br>");
					//out.print("请选择正确的题目模板！<br/>");
				}
			}

			if (type.equals(QuestionType.PANDUAN)) {
				if (cols == 7 && "判断题".equals(modeltype)) {
					String answer = sheet.getCell(4, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						msg_sb.append("第" + (i + 1) + "行的答案内容为空！</br>");
						//out.println("第" + (i + 1) + "行的答案内容为空！");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					if (answer.indexOf("正确") == -1
							&& answer.indexOf("错误") == -1) {
						msg_sb.append("第" + (i + 1) + "行的答案内容只能为“正确”或“错误”！</br>");
						//out.println("第" + (i + 1) + "行的答案内容只能为“正确”或“错误”！");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
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
						msg_sb.append("第" + (i + 1) + "行的答案内容只能为“正确”或“错误”,只能是一个！</br>");
						//out.println("第" + (i + 1) + "行的答案内容只能为“正确”或“错误”,只能是一个。！");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
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
					String loreName = sheet.getCell(5, i).getContents()
							.trim();
					if (loreName.equals("") || loreName == null) {
						msg_sb.append("第" + (i + 1) + "行，请输入知识点名称！</br>");
						//out.println("第" + (i + 1) + "行，请输入知识点名称");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}
					String loreDiscription = sheet.getCell(6, i).getContents()
							.trim();
					//if (loreDiscription.equals("") || loreDiscription == null) {
						//msg_sb.append("第" + (i + 1) + "行，请输入知识点描述！</br>");
						//out.println("第" + (i + 1) + "行，请输入知识点描述");
						//msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						//continue;
					//}
					String sqllore = " select tli.id,tli.discription from test_lore_info tli where tli.name='"
							+ loreName
							+ "' and tli.loredir='"
							+ loreDirId + "' ";
					String lore_Id = "";
					MyResultSet rs = null;
					try{
						rs = db.executeQuery(sqllore);
	
						if (rs != null && rs.next()) {
							lore_Id = rs.getString("id");
							//if (!loreDiscription.equals(rs.getString("discription"))) {
								//msg_sb.append("第" + (i + 1) + "行，请输入正确的知识点描述！</br>");
								//out.println("第" + (i + 1) + "行，请输入正确的知识点描述");
								//msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
								//out.print("第" + (i + 1) + "行导入失败！<br/>");
								//continue;
							//}
						} else {
							String sqladdLore = "insert into test_lore_info(id, name, creatdate, content, loredir, createrid, active, discription) values ("
									+ " to_char(s_test_lore_info_id.nextval), '"
									+ loreName
									+ "', '','"
									+ loreName
									+ "', '"
									+ loreDirId
									+ "', '"
									+ " 1111 ', '1','" + loreDiscription + "') ";
	
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
									msg_sb.append("操作失败，请稍后重试！</br>");
									//out.println("操作失败，请稍后重试");
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
							studentNote, teacherNote, type, loreDiscription);

					if (suc < 1) {
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					msg_sb.append("请选择正确的题目模板！</br>");
					//out.print("请选择正确的题目模板！<br/>");
				}
			}

			if (type.equals(QuestionType.TIANKONG)) {
				if (cols == 7 && "填空题".equals(modeltype)) {
					String answer = sheet.getCell(5, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						msg_sb.append("第" + (i + 1) + "行的答案内容为空！</br>");
						//out.println("第" + (i + 1) + "行的答案内容为空！");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					String xml = "<question><body>" + questioncore
							+ "</body><answer>" + answer
							+ "</answer></question>";
					String loreName = sheet.getCell(6, i).getContents()
					.trim();
			if (loreName.equals("") || loreName == null) {
				msg_sb.append("第" + (i + 1) + "行，请输入知识点名称！</br>");
				//out.println("第" + (i + 1) + "行，请输入知识点名称");
				msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
				//out.print("第" + (i + 1) + "行导入失败！<br/>");
				continue;
			}
			String loreDiscription = sheet.getCell(6, i).getContents()
							.trim();
					//if (loreDiscription.equals("") || loreDiscription == null) {
						//msg_sb.append("第" + (i + 1) + "行，请输入知识点描述！</br>");
						//out.println("第" + (i + 1) + "行，请输入知识点描述");
						//msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						//continue;
					//}
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
				} else {
					String sqladdLore = "insert into test_lore_info(id, name, creatdate, content, loredir, createrid, active) values ("
							+ " to_char(s_test_lore_info_id.nextval), '"
							+ loreName
							+ "', '','"
							+ loreName
							+ "', '"
							+ loreDirId
							+ "', '"
							+ " 1111 ', '1') ";

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
							msg_sb.append("操作失败，请稍后重试！</br>");
							//out.println("操作失败，请稍后重试");
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
					studentNote, teacherNote, type, loreDiscription);
				
					if (suc < 1) {
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					msg_sb.append("请选择正确的题目模板！</br>");
					//out.print("请选择正确的题目模板！<br/>");
				}
			}

			if (type.equals(QuestionType.WENDA)) {
				if (cols == 10 && "问答题".equals(modeltype)) {
					String answer = sheet.getCell(9, i).getContents()
							.trim();

					if (answer.equals("") || answer == null) {
						msg_sb.append("第" + (i + 1) + "行的答案内容为空！</br>");
						//out.println("第" + (i + 1) + "行的答案内容为空！");
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
						continue;
					}

					String xml = "<question><body>" + questioncore
							+ "</body><answer>" + answer
							+ "</answer></question>";
					int suc = testManage.addStoreQuestion(title,
							creatuser, creatdate, diff, "keyword", xml,
							loreId, cognizetype, purpose,
							referencescore, referencetime, studentNote,
							teacherNote, type,"");
					if (suc < 1) {
						msg_sb.append("第" + (i + 1) + "行导入失败！</br>");
						//out.print("第" + (i + 1) + "行导入失败！<br/>");
					} else {
						totalSuc++;
					}
				} else {
					msg_sb.append("请选择正确的题目模板！</br>");
					//out.print("请选择正确的题目模板！<br/>");
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
			<%=msg_sb %>
		</p>
		<p class="text3" align="center">
			成功导入<%=totalSuc%>道题目！
		</p>
		<br/>
		<p align="center">
			<a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a>
		</p>
	</body>
</html>
