package com.whaty.platform.database.oracle.standard.courseware.basic;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.CoursewareType;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.courseware.basic.CoursewareActivity;
import com.whaty.platform.courseware.basic.OnlineCoursewareBuildFlag;
import com.whaty.platform.courseware.basic.WhatyOnlineCoursewareInfo;
import com.whaty.platform.courseware.config.CoursewareConfig;
import com.whaty.platform.courseware.exception.CoursewareException;
import com.whaty.platform.courseware.util.log.CoursewareLog;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.Exception.FileManageException;
import com.whaty.util.file.FileManage;
import com.whaty.util.file.FileManageFactory;
import com.whaty.util.log.Log;

public class OracleCoursewareActivity extends CoursewareActivity {

	public void changeCoursewareType(Courseware courseware,
			String coursewareType) throws CoursewareException {
		// TODO Auto-generated method stub

	}

	public void buildOnlineCourseware(CoursewareConfig config,String coursewareId, String templateId) throws CoursewareException {
		 
			FileManage fileManage = FileManageFactory.creat();
			OracleWhatyOnlineCourseware courseware = new OracleWhatyOnlineCourseware(
					coursewareId);
			if (courseware.getId() == null || courseware.getId().length() < 1) {
				CoursewareLog
						.setDebug("CoursewareActivity.buildOnlineCourseware error!(no this courseware)");
				throw new CoursewareException(
						"CoursewareActivity.buildOnlineCourseware error!(no this courseware)");
			}
			try {
				/*
				 * if(fileManage.isExist(config.getCoursewareAbsPath()+coursewareId)) {
				 * CoursewareLog.setDebug("CoursewareActivity.buildOnlineCourseware
				 * error!(because the coursewarePath is exist) "); throw new
				 * CoursewareException("CoursewareActivity.buildOnlineCourseware
				 * error!(because the coursewarePath is exist) "); }
				 */
				fileManage.createDirectory(config.getCoursewareAbsPath(),
						coursewareId);
				fileManage.copy(config.getCoursewareTemplateAbsPath(),
						templateId, config.getCoursewareAbsPath()
								+ coursewareId);
				String iXML = fileManage.readFile(config.getCoursewareAbsPath()
						+ courseware.getId() + java.io.File.separator
						+ "menu.js", false, "UTF-8");
				String fileInfo = config.getCoursewareAbsPath()
				+ courseware.getId() + java.io.File.separator
				+ "menu.js";
				
				fileManage.writeFile(config.getCoursewareAbsPath()
						+ courseware.getId() + java.io.File.separator
						+ "menu.js", iXML + courseware.getName() + "\";",
						"UTF-8");

			} catch (FileManageException e) {
				
				throw new CoursewareException(
						"CoursewareActivity.buildOnlineCourseware error!(copy template error)");
			}
			courseware.setCoursewareType(CoursewareType.WHATYONLINE);
			courseware.setBuildFlag(OnlineCoursewareBuildFlag.NOTCOMPLETE);
			courseware.setTemplateId(templateId);
			try {
				courseware.update();
			} catch (PlatformException e) {
				throw new CoursewareException(
						"CoursewareActivity.buildOnlineCourseware error!(DB update courseware buildflag error)");
			}
		}

	 

	public void addOnlineCoursewareInfo(CoursewareConfig config,
			String coursewareId, String infoId, String infoTitle)
			throws CoursewareException {
		FileManage fileManage = FileManageFactory.creat();
		String coursewarePath = config.getCoursewareAbsPath() + coursewareId
				+ File.separator;
		String pageTemplate;
		try {
			pageTemplate = fileManage.readFile(config
					.getCoursewareTemplateAbsPath()
					+ "shared" + File.separator + "tpl_page_info.txt", false,
					"UTF-8");
			String codes = pageTemplate.replaceFirst("<!-- TPL_INFO_TITLE -->",
					infoTitle);
			fileManage.writeFile(coursewarePath + "tpl_info_" + infoId
					+ ".html", codes, "UTF-8");

			String iXML = fileManage.readFile(coursewarePath + "menu.js",
					false, "UTF-8");
			String temp1 = iXML
					.substring(0, iXML.indexOf("new Array(\"�γ�ѧϰ\""));
			String temp2 = iXML.substring(iXML.indexOf("new Array(\"�γ�ѧϰ\""),
					iXML.length());
			iXML = temp1 + "new Array(\"" + infoTitle + "\", \"tpl_info_"
					+ infoId + ".html\")," + temp2;
			
			
			fileManage.writeFile(coursewarePath + "menu.js", iXML, "UTF-8");
		} catch (FileManageException e) {
			throw new CoursewareException(
					"OracleCoursewareActivity.addOnlineCoursewareInfo error!(filemanage error)");
		}
	}

	public void deleteOnlineCoursewareInfo(CoursewareConfig config,
			String coursewareId, String infoUrl, String infoTitle)
			throws CoursewareException {

		String CoursewarePath = config.getCoursewareAbsPath() + coursewareId
				+ File.separator;
		FileManage fileManage = FileManageFactory.creat();
		try {
			String iXML = fileManage.readFile(CoursewarePath + "menu.js",
					false, "UTF-8");
			String temp1 = iXML.substring(0, iXML.indexOf("new Array(\""
					+ infoTitle + "\", \"" + infoUrl + "\""));
			String temp2 = iXML
					.substring(iXML.indexOf("new Array(\"" + infoTitle
							+ "\", \"" + infoUrl + "\"") + 4, iXML.length());
			temp2 = temp2.substring(temp2.indexOf("new Array"), temp2.length());
			// out.print(temp1);
			// out.print("&&"+temp2);
			iXML = temp1 + temp2;
			fileManage.writeFile(CoursewarePath + "menu.js", iXML, "UTF-8");
		} catch (FileManageException e) {
			throw new CoursewareException(
					"deleteOnlineCoursewareInfo error!(read or write file menu.js error)");
		}
	}

	public List getOnlineCoursewareInfos(CoursewareConfig config,
			String coursewareId) throws CoursewareException {
		String _CoursewarePath = config.getCoursewareAbsPath() + coursewareId;
		java.io.File yourFile = new java.io.File(_CoursewarePath + "/menu.js");
		if (!yourFile.isFile()) {
			throw new CoursewareException("no this file (" + _CoursewarePath
					+ "/menu.js)");
		}
		FileManage fileManage = FileManageFactory.creat();
		String iXML;
		try {
			iXML = fileManage.readFile(_CoursewarePath + "/menu.js", false,
					"UTF-8");
		} catch (FileManageException e) {
			throw new CoursewareException("read file error (" + _CoursewarePath
					+ ")");
		}
		int info_at = iXML.indexOf("new Array(\"");
		String temp = iXML.substring(info_at + 11, iXML.length());
		List onlineCoursewareInfoList = new ArrayList();
		while (info_at != -1) {
			String temp_info = "";
			WhatyOnlineCoursewareInfo info = new WhatyOnlineCoursewareInfo();
			temp_info = temp.substring(0, temp.indexOf("\""));
			info.setName(temp_info);
			info_at = temp.indexOf("new Array(\"");
			if (info_at != -1) {
				temp_info = temp.substring(temp.indexOf("\"") + 4, info_at - 3);
			} else {
				temp_info = temp.substring(temp.indexOf("\"") + 4, temp
						.indexOf("\"))"));
			}
			info.setUrl(temp_info);
			temp = temp.substring(info_at + 11, temp.length());
			onlineCoursewareInfoList.add(info);
		}
		return onlineCoursewareInfoList;
	}

	public void addChapterPage(CoursewareConfig config, String coursewareId,
			String remark) throws CoursewareException {

		OracleWhatyOnlineCourseware courseware = new OracleWhatyOnlineCourseware(
				coursewareId);
		String mySection[][] = new String[1000][4];
		int mySectionCount;
		StringTokenizer secStr = new StringTokenizer(remark, "|");
		StringTokenizer thisSection;
		mySectionCount = secStr.countTokens();

		int i, j;

		for (i = 1; i <= mySectionCount; i++) {
			thisSection = new StringTokenizer(secStr.nextToken(), "+");
			for (j = 0; j < 4; j++) {
				mySection[i - 1][j] = thisSection.nextToken();
			}
		}
		FileManage fileManage = FileManageFactory.creat();
		try {
			String Codes = fileManage.readFile(config
					.getCoursewareTemplateAbsPath()
					+ "shared" + File.separator + "tpl_page_course.txt", false,
					"UTF-8");
			for (i = 0; i < mySectionCount; i++) {
				String codes = Codes.replaceAll("<!-- TPL_COURSE_TITLE -->",
						mySection[i][2]);
				fileManage.writeFile(config.getCoursewareAbsPath()
						+ coursewareId + File.separator + mySection[i][3]
						+ ".html", codes, "UTF-8");
			}
			fileManage.writeFile(config.getCoursewareAbsPath() + coursewareId
					+ File.separator + "tpl_course_conf.htm", remark, "UTF-8");
			generateMarginPage(config, courseware);
		} catch (FileManageException e) {
			throw new CoursewareException(
					"addChapterPage error!(fielmanage readfile or writefile error)");
		} catch (CoursewareException e) {
			throw new CoursewareException(e.getMessage());
		}
		courseware.setBuildFlag(OnlineCoursewareBuildFlag.COMPLETED);
		try {
			courseware.update();
		} catch (PlatformException e) {
			throw new CoursewareException(
					"CoursewareActivity.addChapterPage error!(DB update courseware buildflag error)");
		}
	}

	private void generateMarginPage(CoursewareConfig config,
			Courseware courseware) throws CoursewareException {

		String mySection[][] = new String[1000][4];
		int mySectionCount;

		StringBuffer strbuffer = new StringBuffer();
		strbuffer
				.append("<html><head>  	<meta http-equiv=\"content-Type\" content=\"text/html; charset=UTF-8\"/>  	<style>		td {font-size:9pt;}		a {color:black;text-decoration:underline;}		a:hover {color:#ff1100;}	.px14 {font-size:14px;}		.arial8 {font-family:arial;font-size:8pt;}		.verdana8 {font-family:verdana;font-size:8pt;}		.copyright {font-family:verdana,arial;font-size:8pt;color:black;}		input.smartext {border:1 solid #000000;font-size:9pt;background-color:white;}  		textarea.smartext {border:1 solid #000000;font-size:9pt;background-color:white;}  		.coursename {font-size:25px;font-weight:bold;font-family:�D��;color:#35538f;}  		a.section {color:#dddddd;font-weight:bold;text-decoration:none;}  		a.section:hover {color:white;text-decoration:none;}		a.Tree {color:black;text-decoration:none;}	a.Tree:hover {color:#666666;}		a.Leaf {color:black;text-decoration:none;}		a.Leaf:hover color:#666666;}  	</style>	</head><body topmargin=0 leftmargin=0 background=\"./images/tpl_bg_margin.gif\">	<br>    <table border=0 cellpadding=0 cellspacing=1 width=95% align=center>		<tr>			<td valign=top>");

		FileManage fileManage = FileManageFactory.creat();
		try {
			String Codes = fileManage.readFile(config.getCoursewareAbsPath()
					+ courseware.getId() + File.separator
					+ "tpl_course_conf.htm", false, "UTF-8");
			StringTokenizer secStr = new StringTokenizer(Codes, "|");
			mySectionCount = secStr.countTokens();

			mySection[0][0] = "0";
			mySection[0][1] = "TPL_STATIC";
			mySection[0][2] = courseware.getName();
			mySection[0][3] = "TOP";
			int i, j;
			for (i = 1; i <= mySectionCount; i++) {
				StringTokenizer thisSection = new StringTokenizer(secStr
						.nextToken(), "+");
				for (j = 0; j < 4; j++) {
					mySection[i][j] = thisSection.nextToken();
				}
			}
			strbuffer
					.append("<div id=Trees><div class=Tree id=TREE_0_0 style='cursor:hand'>	<img class=Tree id=_IMG_0_0 src=\"./images/tpl_icon_book_closed.gif\" border=0>"
							+ courseware.getName()
							+ "</div>	<div class=Leaf id=LEAF_0_0 style='cursor:normal'>"
							+ getTreeCodes(0, 1, mySection, mySectionCount));
			strbuffer
					.append("</div>	</div><script language=\"JavaScript\">	function showTrees(){var LeafID,srcElement,LeafElement,theSameID,ImageID,ImageElement;    	srcElement=window.event.srcElement;    	if (srcElement.className==\"Tree\"){      		theSameID=srcElement.id;      		LeafID=\"LEAF\"+theSameID.substring(4,theSameID.length);      		LeafElement=document.all(LeafID);	ImageID=\"_IMG\"+theSameID.substring(4,theSameID.length);      		ImageElement=document.all(ImageID);      		      		if (LeafElement.style.display==\"none\"){        		LeafElement.style.display=\"\";			ImageElement.src=\"./images/tpl_icon_book_open.gif\";      		}     		else{        		LeafElement.style.display=\"none\";        		ImageElement.src=\"./images/tpl_icon_book_closed.gif\";      		}    	}  	}  	Trees.onclick=showTrees;</script>			</td>		</tr>	</table></body></html>");
			fileManage.writeFile(config.getCoursewareAbsPath()
					+ courseware.getId() + File.separator
					+ "tpl_study_margin.htm", strbuffer.toString(), "UTF-8");
		} catch (FileManageException e) {

			throw new CoursewareException(
					"generateMarginPage error!(read file or write file error)");
		}

	}

	private String getTreeCodes(int ID, int Depth, String[][] mySection,
			int mySectionCount) {
		String codes = "";

		String precodes = "";
		for (int i = 0; i < Depth; i++) {
			precodes += " &nbsp; ";
		}

		int TCount = 0;
		int[] Tree = new int[1000];
		for (int i = ID + 1; i <= mySectionCount; i++) {
			int depth = Integer.parseInt(mySection[i][0]);
			if (depth == Depth) {
				Tree[TCount] = i;
				TCount++;
			}
			if (depth < Depth)
				break;
		}

		for (int i = 0; i < TCount; i++) {
			String LeafCodes = getTreeCodes(Tree[i], Depth + 1, mySection,
					mySectionCount);
			if (!LeafCodes.equals("")) {
				codes += "<div>";
				codes += precodes;
				codes += "<span class=Tree id=TREE_" + Depth + "_" + i + "_"
						+ ID + " style='cursor:hand'>";
				codes += "<a href=\"#\" id=TREE_" + Depth + "_" + i + "_" + ID
						+ " class=Tree>@</a>";
				codes += "<a href=\"./" + mySection[Tree[i]][3]
						+ ".html\" id=TREE_" + Depth + "_" + i + "_" + ID
						+ " class=Tree target=main>" + mySection[Tree[i]][2]
						+ "</a></span>";
				codes += "</div>";
				codes += "<div class=Leafs id=LEAF_" + Depth + "_" + i + "_"
						+ ID + " style='display:none'>";
				codes += LeafCodes;
				codes += "</div>\n";
			} else {
				codes += precodes;
				codes += "<a href=\"#\" id=LEAF_" + Depth + "_" + i + "_" + ID
						+ " class=Leaf>@</a>";
				codes += "<a href=\"./" + mySection[Tree[i]][3]
						+ ".html\" id=LEAF_" + Depth + "_" + i + "_" + ID
						+ " class=Leaf target=main>" + mySection[Tree[i]][2]
						+ "</a>";
				codes += "<br>\n";
			}
		}

		return (codes);
	}

	public Courseware getCourseware(String coursewareId)
			throws CoursewareException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
//		String sql = "select ID,NAME,AUTHOR,PUBLISHER,NOTE,FOUNDER_ID,FOUND_DATE,COURSEWARE_TYPE,"
//				+ "LINK,ACTIVE from PE_TCH_COURSEWARE where id = '"
//				+ coursewareId + "'";
		
		String sql = "select ID,NAME,AUTHOR,PUBLISHER,NOTE,FOUNDER_ID,FOUND_DATE,COURSEWARE_TYPE,"
			+ "LINK,ACTIVE,OPENCOURSEDEPID from PE_TCH_COURSEWARE where id = '"
			+ coursewareId + "'";
		String coursewareType = null;
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				coursewareType = rs.getString("courseware_type");
			}
		} catch (java.sql.SQLException e) {
			Log
					.setError("OracleWhatyOnlineCourseware(String templateId) error "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		if (coursewareType == null || coursewareType.length() < 1
				|| coursewareType.equalsIgnoreCase("null")) {
			return new OracleCourseware(coursewareId);
		} else if (coursewareType.equals(CoursewareType.WHATYONLINE)) {
			return new OracleWhatyOnlineCourseware(coursewareId);
		} else if (coursewareType.equals(CoursewareType.NORMALHTTP)) {
			return new OracleNormalHttpCourseware(coursewareId);
		} else if (coursewareType.equals(CoursewareType.UPLOADHTTP)) {
			return new OracleWhatyUploadCourseware(coursewareId);
		} else
			return null;
	}

	public String getCoursewareEnterUrl(CoursewareConfig config,
			String coursewareId) throws CoursewareException {
		OracleCourseware courseware = new OracleCourseware(coursewareId);
		String enterUrl = null;
		if (courseware.getCoursewareType() == null
				|| courseware.getCoursewareType().length() < 1
				|| courseware.getCoursewareType().trim().equalsIgnoreCase(
						"null")) {
			throw new CoursewareException("courseware no type!");
		} else {
			if (courseware.getCoursewareType().equals(
					CoursewareType.WHATYONLINE)) {
				enterUrl = config.getCoursewareURI() + coursewareId + "/"
						+ "tpl_index.htm";

			} else if (courseware.getCoursewareType().equals(
					CoursewareType.NORMALHTTP)) {
				OracleNormalHttpCourseware cw = new OracleNormalHttpCourseware(
						coursewareId);
				enterUrl = cw.getHttpLink();
			} else if (courseware.getCoursewareType().equals(
					CoursewareType.UPLOADHTTP)) {
				OracleWhatyUploadCourseware cw = new OracleWhatyUploadCourseware(
						coursewareId);
				enterUrl = config.getCoursewareURI() + coursewareId + "/"
						+ cw.getEnterFileName();
			}
		}
		return enterUrl;
	}

	public void activeCoursewares(List coursewareIdList, boolean flag)
			throws CoursewareException {
		String coursewareIdStr = "";
		if (coursewareIdList == null && coursewareIdList.size() < 1)
			return;
		else {
			for (int i = 0; i < coursewareIdList.size(); i++) {
				coursewareIdStr = coursewareIdStr + "'"
						+ (String) coursewareIdList.get(i) + "',";
			}
			if (coursewareIdStr.length() > 3)
				coursewareIdStr = coursewareIdStr.substring(0, coursewareIdStr
						.length() - 1);
			dbpool db = new dbpool();
			int active = 0;
			if (flag)
				active = 1;
			String sql = "update PE_TCH_COURSEWARE set active=" + active
					+ " where id in(" + coursewareIdStr + ")";
			UserUpdateLog.setDebug("OracleCoursewareActivity.activeCoursewares(List coursewareIdList,boolean flag) SQL=" + sql + " DATE=" + new Date());
			db.executeUpdate(sql);
		}

	}

}
