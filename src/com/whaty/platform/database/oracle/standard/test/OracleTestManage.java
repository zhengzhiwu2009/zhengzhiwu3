package com.whaty.platform.database.oracle.standard.test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.exam.OracleBasicSequence;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamActivity;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamBatch;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamCourse;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamList;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamRoom;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamScore;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamSequence;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamUser;
import com.whaty.platform.database.oracle.standard.test.history.OracleExamPaperHistory;
import com.whaty.platform.database.oracle.standard.test.history.OracleExperimentPaperHistory;
import com.whaty.platform.database.oracle.standard.test.history.OracleHomeworkPaperHistory;
import com.whaty.platform.database.oracle.standard.test.history.OracleTestPaperHistory;
import com.whaty.platform.database.oracle.standard.test.lore.OracleLore;
import com.whaty.platform.database.oracle.standard.test.lore.OracleLoreDir;
import com.whaty.platform.database.oracle.standard.test.onlinetest.OracleOnlineExamCourse;
import com.whaty.platform.database.oracle.standard.test.onlinetest.OracleOnlineTestBatch;
import com.whaty.platform.database.oracle.standard.test.onlinetest.OracleOnlineTestCourse;
import com.whaty.platform.database.oracle.standard.test.paper.OracleExamPaper;
import com.whaty.platform.database.oracle.standard.test.paper.OracleExperimentPaper;
import com.whaty.platform.database.oracle.standard.test.paper.OracleHomeworkPaper;
import com.whaty.platform.database.oracle.standard.test.paper.OraclePaper;
import com.whaty.platform.database.oracle.standard.test.paper.OraclePaperActivity;
import com.whaty.platform.database.oracle.standard.test.paper.OraclePaperPolicy;
import com.whaty.platform.database.oracle.standard.test.paper.OracleTestPaper;
import com.whaty.platform.database.oracle.standard.test.question.OraclePaperQuestion;
import com.whaty.platform.database.oracle.standard.test.question.OracleStoreQuestion;
import com.whaty.platform.database.oracle.standard.test.reexam.OracleReExamActivity;
import com.whaty.platform.database.oracle.standard.test.reexam.OracleReExamBatch;
import com.whaty.platform.database.oracle.standard.test.reexam.OracleReExamCourse;
import com.whaty.platform.database.oracle.standard.test.reexam.OracleReExamList;
import com.whaty.platform.database.oracle.standard.test.reexam.OracleReExamRoom;
import com.whaty.platform.database.oracle.standard.test.reexam.OracleReExamScore;
import com.whaty.platform.database.oracle.standard.test.reexam.OracleReExamSequence;
import com.whaty.platform.database.oracle.standard.test.reexam.OracleReExamUser;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.test.TestManage;
import com.whaty.platform.test.TestPriv;
import com.whaty.platform.test.exam.BasicSequence;
import com.whaty.platform.test.exam.ExamBatch;
import com.whaty.platform.test.exam.ExamCourse;
import com.whaty.platform.test.exam.ExamRoom;
import com.whaty.platform.test.exam.ExamSequence;
import com.whaty.platform.test.exam.ExamUser;
import com.whaty.platform.test.history.ExamPaperHistory;
import com.whaty.platform.test.history.ExperimentPaperHistory;
import com.whaty.platform.test.history.HomeworkPaperHistory;
import com.whaty.platform.test.history.TestPaperHistory;
import com.whaty.platform.test.lore.Lore;
import com.whaty.platform.test.lore.LoreDir;
import com.whaty.platform.test.onlinetest.OnlineExamCourse;
import com.whaty.platform.test.onlinetest.OnlineTestCourse;
import com.whaty.platform.test.paper.ExamPaper;
import com.whaty.platform.test.paper.ExperimentPaper;
import com.whaty.platform.test.paper.HomeworkPaper;
import com.whaty.platform.test.paper.PaperPolicy;
import com.whaty.platform.test.paper.PaperPolicyCore;
import com.whaty.platform.test.paper.TestPaper;
import com.whaty.platform.test.question.PaperQuestion;
import com.whaty.platform.test.question.StoreQuestion;
import com.whaty.platform.test.reexam.ReExamBatch;
import com.whaty.platform.test.reexam.ReExamCourse;
import com.whaty.platform.test.reexam.ReExamRoom;
import com.whaty.platform.test.reexam.ReExamSequence;
import com.whaty.platform.test.reexam.ReExamUser;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;

public class OracleTestManage extends TestManage {
	TestPriv testPriv;

	public OracleTestManage(TestPriv testPriv) {
		this.testPriv = testPriv;
	}

	public int addLore(String name, String creatDate, String content, String loreDir, String createrId, String active) throws NoRightException, PlatformException {
		if (testPriv.addLore == 1) {
			OracleLore lore = new OracleLore();
			lore.setName(name);
			// lore.setDiscription(discription);
			lore.setCreatDate(creatDate);
			lore.setContent(content);
			lore.setLoreDir(loreDir);
			lore.setCreaterId(createrId);
			// lore.setActive(active.equals("1") ? true : false);
			lore.setActive("1".equals(active) ? true : false);

			int suc = lore.add();
			return suc;
		} else {
			throw new NoRightException("您没有添加知识点的权限");
		}
	}

	public int addLore(String name, String discription, String creatDate, String content, String loreDir, String createrId, String active) throws NoRightException, PlatformException {
		if (testPriv.addLore == 1) {
			OracleLore lore = new OracleLore();
			lore.setName(name);
			lore.setDiscription(discription);
			lore.setCreatDate(creatDate);
			lore.setContent(content);
			lore.setLoreDir(loreDir);
			lore.setCreaterId(createrId);
			// lore.setActive(active.equals("1") ? true : false);
			lore.setActive("1".equals(active) ? true : false);

			int suc = lore.add();
			return suc;
		} else {
			throw new NoRightException("您没有添加知识点的权限");
		}
	}

	public int updateLore(String id, String name, String creatDate, String content, String loreDir, String createrId, String active) throws NoRightException, PlatformException {
		if (testPriv.updateLore == 1) {
			OracleLore lore = new OracleLore();
			lore.setId(id);
			lore.setName(name);
			lore.setCreatDate(creatDate);
			lore.setContent(content);
			lore.setLoreDir(loreDir);
			lore.setCreaterId(createrId);
			lore.setActive(active != null && active.equals("1") ? true : false);

			return lore.update();
		} else {
			throw new NoRightException("您没有修改知识点的权限");
		}
	}

	public int deleteLore(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteLore == 1) {
			OracleLore lore = new OracleLore(id);

			return lore.delete();
		} else {
			throw new NoRightException("您没有删除知识点的权限");
		}
	}

	public Lore getLore(String id) throws NoRightException, PlatformException {
		if (testPriv.getLore == 1) {
			OracleLore lore = new OracleLore(id);

			return lore;
		} else {
			throw new NoRightException("您没有查看知识点的权限");
		}
	}

	public int getLoresNum(String id, String name, String creatDate, String content, String loreDir, String createrId, String active) throws NoRightException, PlatformException {
		if (testPriv.getLores == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (creatDate != null)
				searchList.add(new SearchProperty("creatdate", creatDate, "="));
			if (content != null)
				searchList.add(new SearchProperty("content", content, "like"));
			if (loreDir != null)
				searchList.add(new SearchProperty("loredir", loreDir, "="));
			if (createrId != null)
				searchList.add(new SearchProperty("createrid", createrId, "="));
			if (active != null)
				searchList.add(new SearchProperty("active", active, "="));

			return testList.getLoresNum(searchList);
		} else {
			throw new NoRightException("您没有查看知识点的权限");
		}
	}

	public List getLores(Page page, String id, String name, String creatDate, String content, String loreDir, String createrId, String active) throws NoRightException, PlatformException {
		if (testPriv.getLores == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (creatDate != null)
				searchList.add(new SearchProperty("creatdate", creatDate, "="));
			if (content != null)
				searchList.add(new SearchProperty("content", content, "like"));
			if (loreDir != null)
				searchList.add(new SearchProperty("loredir", loreDir, "="));
			if (createrId != null)
				searchList.add(new SearchProperty("createrid", createrId, "="));
			if (active != null)
				searchList.add(new SearchProperty("active", active, "="));

			return testList.getLores(page, searchList, null);
		} else {
			throw new NoRightException("您没有批量查看知识点的权限");
		}
	}

	public int addLoreDir(String name, String note, String fatherdir, String creatdate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.addLoreDir == 1) {
			OracleLoreDir loreDir = new OracleLoreDir();
			loreDir.setName(name);
			loreDir.setNote(note);
			loreDir.setFatherDir(fatherdir);
			loreDir.setCreatDate(creatdate);
			loreDir.setGroupId(groupId);
			return loreDir.add();
		} else {
			throw new NoRightException("您没有添加知识点目录的权限");
		}
	}

	public int getLoresNum(List idList) throws NoRightException, PlatformException {
		if (testPriv.getLores == 1) {
			OracleTestList testList = new OracleTestList();
			String ids = "";
			List searchList = new ArrayList();
			for (Iterator it = idList.iterator(); it.hasNext();)
				ids += ",'" + it.next() + "'";
			ids.substring(1);
			searchList.add(new SearchProperty("id", ids, "in"));
			return testList.getLoresNum(searchList);
		} else {
			throw new NoRightException("您没有查看知识点的权限");
		}
	}

	public List getLores(Page page, List idList) throws NoRightException, PlatformException {
		if (testPriv.getLores == 1) {
			OracleTestList testList = new OracleTestList();
			String ids = "";
			List searchList = new ArrayList();
			if (idList != null && idList.size() > 0) {
				for (Iterator it = idList.iterator(); it.hasNext();)
					ids += ",'" + (String) it.next() + "'";
				ids = ids.substring(1);
			}
			searchList.add(new SearchProperty("id", ids, "in"));
			return testList.getLores(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看知识点的权限");
		}
	}

	public List getLores(Page page, List idList1, List idList2) throws NoRightException, PlatformException {
		if (testPriv.getLores == 1) {
			OracleTestList testList = new OracleTestList();
			String ids = "";
			List searchList = new ArrayList();
			if (idList1 != null) {
				for (Iterator it = idList1.iterator(); it.hasNext();)
					ids += ",'" + (String) it.next() + "'";
				searchList.add(new SearchProperty("id", ids.substring(1), "in"));
			}
			if (idList2 != null) {
				ids = "";
				for (Iterator it = idList2.iterator(); it.hasNext();)
					ids += ",'" + ((Lore) it.next()).getId() + "'";
				searchList.add(new SearchProperty("id", ids.substring(1), "in"));
			}
			return testList.getLores(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看知识点的权限");
		}
	}

	public int updateLoreDir(String id, String name, String note, String fatherdir, String creatdate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.updateLoreDir == 1) {
			OracleLoreDir loreDir = new OracleLoreDir(id);
			loreDir.setName(name);
			loreDir.setNote(note);
			loreDir.setFatherDir(fatherdir);
			loreDir.setCreatDate(creatdate);
			return loreDir.update();
		} else {
			throw new NoRightException("您没有修改知识点目录的权限");
		}
	}

	public int deleteLoreDir(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteLoreDir == 1) {
			// OracleLoreDir loreDir = new OracleLoreDir();
			// loreDir.setId(id);
			OracleLoreDir loreDir = new OracleLoreDir(id);

			return loreDir.delete();
		} else {
			throw new NoRightException("您没有删除知识点目录的权限");
		}
	}

	public LoreDir getLoreDir(String id) throws NoRightException, PlatformException {
		if (testPriv.getLoreDir == 1) {
			OracleLoreDir loreDir = new OracleLoreDir(id);

			return loreDir;
		} else {
			throw new NoRightException("您没有查看知识点目录的权限");
		}
	}

	public LoreDir getLoreDirByGroupId(String groupId) throws NoRightException, PlatformException {
		if (testPriv.getLoreDir == 1) {
			OracleTestList testList = new OracleTestList();

			return testList.getLoreDirIdByGroupId(groupId);
		} else {
			throw new NoRightException("您没有查看知识点目录的权限");
		}
	}

	public int getLoreDirsNum(String id, String name, String note, String fatherdir, String creatdate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getLoreDirs == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (fatherdir != null)
				searchList.add(new SearchProperty("fatherdir", fatherdir, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getLoreDirsNum(searchList);
		} else {
			throw new NoRightException("您没有批量查看知识点目录的权限");
		}
	}

	public List getLoreDirs(Page page, String id, String name, String note, String fatherdir, String creatdate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getLoreDirs == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (fatherdir != null)
				searchList.add(new SearchProperty("fatherdir", fatherdir, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getLores(page, searchList, null);
		} else {
			throw new NoRightException("您没有批量查看知识点目录的权限");
		}
	}

	public int getStoreQuestionsNum(String id, String title, String creatuser, String creatdate, String diff, String keyword, String questioncore, String lore, String cognizetype, String purpose, String referencescore, String referencetime, String studentnote, String teachernote, String type)
			throws NoRightException, PlatformException {
		if (testPriv.getStoreQuestions == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null && !"".equals(title) && !"null".equalsIgnoreCase(title))
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null && !"".equals(creatuser) && !"null".equalsIgnoreCase(creatuser))
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null && !"".equals(creatdate) && !"null".equalsIgnoreCase(creatdate))
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (diff != null && !"".equals(diff) && !"null".equalsIgnoreCase(diff))
				searchList.add(new SearchProperty("diff", diff, "="));
			if (keyword != null && !"".equals(keyword) && !"null".equalsIgnoreCase(keyword))
				searchList.add(new SearchProperty("keyword", keyword, "like"));
			if (questioncore != null && !"".equals(questioncore) && !"null".equalsIgnoreCase(questioncore))
				searchList.add(new SearchProperty("questioncore", questioncore, "like"));
			if (lore != null && !"".equals(lore) && !"null".equalsIgnoreCase(lore))
				searchList.add(new SearchProperty("lore", lore, "="));
			if (cognizetype != null && !"".equals(cognizetype) && !"null".equalsIgnoreCase(cognizetype))
				searchList.add(new SearchProperty("cognizetype", cognizetype, "="));
			if (purpose != null && !"".equals(purpose) && !"null".equalsIgnoreCase(purpose))
				searchList.add(new SearchProperty("purpose", purpose, "="));
			if (referencescore != null && !"".equals(referencescore) && !"null".equalsIgnoreCase(referencescore))
				searchList.add(new SearchProperty("referencescore", referencescore, "="));
			if (referencetime != null && !"".equals(referencetime) && !"null".equalsIgnoreCase(referencetime))
				searchList.add(new SearchProperty("referencetime", referencetime, "="));
			if (studentnote != null && !"".equals(studentnote) && !"null".equalsIgnoreCase(studentnote))
				searchList.add(new SearchProperty("studentnote", studentnote, "like"));
			if (teachernote != null && !"".equals(teachernote) && !"null".equalsIgnoreCase(teachernote))
				searchList.add(new SearchProperty("teachernote", teachernote, "like"));
			if (type != null && !"".equals(type) && !"null".equalsIgnoreCase(type))
				searchList.add(new SearchProperty("type", type, "="));

			return testList.getStoreQuestionsNum(searchList);
		} else {
			throw new NoRightException("您没有查看题库列表的权限");
		}
	}

	// by wuhao
	public int getStoreQuestionsNum(String loreDir) throws NoRightException, PlatformException {
		if (testPriv.getStoreQuestions == 1) {
			OracleTestList testList = new OracleTestList();

			return testList.getStoreQuestionsNum(loreDir);
		} else {
			throw new NoRightException("您没有查看题库列表的权限");
		}
	}

	public List getStoreQuestions(Page page, String id, String title, String creatuser, String creatdate, String diff, String keyword, String questioncore, String lore, String cognizetype, String purpose, String referencescore, String referencetime, String studentnote, String teachernote,
			String type) throws NoRightException, PlatformException {
		if (testPriv.getStoreQuestions == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null && !"".equals(title) && !"null".equalsIgnoreCase(title))
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null && !"".equals(creatuser) && !"null".equalsIgnoreCase(creatuser))
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null && !"".equals(creatdate) && !"null".equalsIgnoreCase(creatdate))
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (diff != null && !"".equals(diff) && !"null".equalsIgnoreCase(diff))
				searchList.add(new SearchProperty("diff", diff, "="));
			if (keyword != null && !"".equals(keyword) && !"null".equalsIgnoreCase(keyword))
				searchList.add(new SearchProperty("keyword", keyword, "like"));
			if (questioncore != null && !"".equals(questioncore) && !"null".equalsIgnoreCase(questioncore))
				searchList.add(new SearchProperty("questioncore", questioncore, "like"));
			if (lore != null && !"".equals(lore) && !"null".equalsIgnoreCase(lore))
				searchList.add(new SearchProperty("lore", lore, "="));
			if (cognizetype != null && !"".equals(cognizetype) && !"null".equalsIgnoreCase(cognizetype))
				searchList.add(new SearchProperty("cognizetype", cognizetype, "="));
			if (purpose != null && !"".equals(purpose) && !"null".equalsIgnoreCase(purpose))
				searchList.add(new SearchProperty("purpose", purpose, "="));
			if (referencescore != null && !"".equals(referencescore) && !"null".equalsIgnoreCase(referencescore))
				searchList.add(new SearchProperty("referencescore", referencescore, "="));
			if (referencetime != null && !"".equals(referencetime) && !"null".equalsIgnoreCase(referencetime))
				searchList.add(new SearchProperty("referencetime", referencetime, "="));
			if (studentnote != null && !"".equals(studentnote) && !"null".equalsIgnoreCase(studentnote))
				searchList.add(new SearchProperty("studentnote", studentnote, "like"));
			if (teachernote != null && !"".equals(teachernote) && !"null".equalsIgnoreCase(teachernote))
				searchList.add(new SearchProperty("teachernote", teachernote, "like"));
			if (type != null && !"".equals(type) && !"null".equalsIgnoreCase(type))
				searchList.add(new SearchProperty("type", type, "="));

			List orderProperties = new ArrayList();
			// orderProperties.add(new OrderProperty("creatdate", "DESC"));
			// orderProperties.add(new OrderProperty("title",
			// OrderProperty.ASC));
			orderProperties.add(new OrderProperty("type", OrderProperty.ASC));
			orderProperties.add(new OrderProperty("creatdate", "DESC"));
			orderProperties.add(new OrderProperty("title", OrderProperty.ASC));
			return testList.getStoreQuestions(page, searchList, orderProperties);
		} else {
			throw new NoRightException("您没有查看题库列表的权限");
		}
	}

	public List getTitleInfo(Page page, String id, String title, String creatuser, String creatdate, String diff, String keyword, String questioncore, String lore, String cognizetype, String purpose, String referencescore, String referencetime, String studentnote, String teachernote, String type)
			throws NoRightException {
		if (testPriv.getStoreQuestions == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null && !"".equals(title) && !"null".equalsIgnoreCase(title))
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null && !"".equals(creatuser) && !"null".equalsIgnoreCase(creatuser))
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null && !"".equals(creatdate) && !"null".equalsIgnoreCase(creatdate))
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (diff != null && !"".equals(diff) && !"null".equalsIgnoreCase(diff))
				searchList.add(new SearchProperty("diff", diff, "="));
			if (keyword != null && !"".equals(keyword) && !"null".equalsIgnoreCase(keyword))
				searchList.add(new SearchProperty("keyword", keyword, "like"));
			if (questioncore != null && !"".equals(questioncore) && !"null".equalsIgnoreCase(questioncore))
				searchList.add(new SearchProperty("questioncore", questioncore, "like"));
			if (lore != null && !"".equals(lore) && !"null".equalsIgnoreCase(lore))
				searchList.add(new SearchProperty("lore", lore, "="));
			if (cognizetype != null && !"".equals(cognizetype) && !"null".equalsIgnoreCase(cognizetype))
				searchList.add(new SearchProperty("cognizetype", cognizetype, "="));
			if (purpose != null && !"".equals(purpose) && !"null".equalsIgnoreCase(purpose))
				searchList.add(new SearchProperty("purpose", purpose, "="));
			if (referencescore != null && !"".equals(referencescore) && !"null".equalsIgnoreCase(referencescore))
				searchList.add(new SearchProperty("referencescore", referencescore, "="));
			if (referencetime != null && !"".equals(referencetime) && !"null".equalsIgnoreCase(referencetime))
				searchList.add(new SearchProperty("referencetime", referencetime, "="));
			if (studentnote != null && !"".equals(studentnote) && !"null".equalsIgnoreCase(studentnote))
				searchList.add(new SearchProperty("studentnote", studentnote, "like"));
			if (teachernote != null && !"".equals(teachernote) && !"null".equalsIgnoreCase(teachernote))
				searchList.add(new SearchProperty("teachernote", teachernote, "like"));
			if (type != null && !"".equals(type) && !"null".equalsIgnoreCase(type))
				searchList.add(new SearchProperty("type", type, "="));

			List orderProperties = new ArrayList();

			orderProperties.add(new OrderProperty("type", OrderProperty.ASC));
			orderProperties.add(new OrderProperty("creatdate", "DESC"));
			orderProperties.add(new OrderProperty("title", OrderProperty.ASC));
			return testList.getTitleInfo(page, searchList, orderProperties);
		} else {
			throw new NoRightException("您没有查看题库列表的权限");
		}
	}

	/**
	 * by wuhao
	 * 
	 */
	public List getTitleInfo(Page page, String id, String title, String creatuser, String creatdate, String diff, String keyword, String questioncore, String lore, String cognizetype, String purpose, String referencescore, String referencetime, String studentnote, String teachernote, String type,
			String loreDirId) throws NoRightException {
		if (testPriv.getStoreQuestions == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null && !"".equals(title) && !"null".equalsIgnoreCase(title))
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null && !"".equals(creatuser) && !"null".equalsIgnoreCase(creatuser))
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null && !"".equals(creatdate) && !"null".equalsIgnoreCase(creatdate))
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (diff != null && !"".equals(diff) && !"null".equalsIgnoreCase(diff))
				searchList.add(new SearchProperty("diff", diff, "="));
			if (keyword != null && !"".equals(keyword) && !"null".equalsIgnoreCase(keyword))
				searchList.add(new SearchProperty("keyword", keyword, "like"));
			if (questioncore != null && !"".equals(questioncore) && !"null".equalsIgnoreCase(questioncore))
				searchList.add(new SearchProperty("questioncore", questioncore, "like"));
			if (lore != null && !"".equals(lore) && !"null".equalsIgnoreCase(lore))
				searchList.add(new SearchProperty("lore", lore, "="));
			if (cognizetype != null && !"".equals(cognizetype) && !"null".equalsIgnoreCase(cognizetype))
				searchList.add(new SearchProperty("cognizetype", cognizetype, "="));
			if (purpose != null && !"".equals(purpose) && !"null".equalsIgnoreCase(purpose))
				searchList.add(new SearchProperty("purpose", purpose, "="));
			if (referencescore != null && !"".equals(referencescore) && !"null".equalsIgnoreCase(referencescore))
				searchList.add(new SearchProperty("referencescore", referencescore, "="));
			if (referencetime != null && !"".equals(referencetime) && !"null".equalsIgnoreCase(referencetime))
				searchList.add(new SearchProperty("referencetime", referencetime, "="));
			if (studentnote != null && !"".equals(studentnote) && !"null".equalsIgnoreCase(studentnote))
				searchList.add(new SearchProperty("studentnote", studentnote, "like"));
			if (teachernote != null && !"".equals(teachernote) && !"null".equalsIgnoreCase(teachernote))
				searchList.add(new SearchProperty("teachernote", teachernote, "like"));
			if (type != null && !"".equals(type) && !"null".equalsIgnoreCase(type))
				searchList.add(new SearchProperty("type", type, "="));

			List orderProperties = new ArrayList();

			orderProperties.add(new OrderProperty("type", OrderProperty.ASC));
			orderProperties.add(new OrderProperty("creatdate", "DESC"));
			orderProperties.add(new OrderProperty("title", OrderProperty.ASC));
			return testList.getTitleInfo(page, searchList, orderProperties, loreDirId);
		} else {
			throw new NoRightException("您没有查看题库列表的权限");
		}
	}

	public int addStoreQuestion(String title, String creatuser, String creatdate, String diff, String keyword, String questioncore, String lore, String cognizetype, String purpose, String referencescore, String referencetime, String studentnote, String teachernote, String type,String remark)
			throws NoRightException, PlatformException {
		if (testPriv.addStoreQuestion == 1) {
			OracleStoreQuestion question = new OracleStoreQuestion();
			question.setTitle(title);
			question.setCreatUser(creatuser);
			question.setCreatDate(creatdate);
			question.setDiff(diff);
			question.setKeyWord(keyword);
			question.setQuestionCore(questioncore);
			question.setLore(lore);
			question.setCognizeType(cognizetype);
			question.setPurpose(purpose);
			question.setReferenceScore(referencescore);
			question.setReferenceTime(referencetime);
			question.setStudentNote(studentnote);
			question.setTeacherNote(teachernote);
			question.setType(type);
			question.setRemark(remark);
			return question.add();
		} else {
			throw new NoRightException("您没有添加题库的权限");
		}
	}

	public int updateStoreQuestion(String id, String title, String creatuser, String creatdate, String diff, String keyword, String questioncore, String lore, String cognizetype, String purpose, String referencescore, String referencetime, String studentnote, String teachernote, String type ,String remark)
			throws NoRightException, PlatformException {
		if (testPriv.updateStoreQuestion == 1) {
			OracleStoreQuestion question = new OracleStoreQuestion();
			question.setId(id);
			question.setTitle(title);
			question.setCreatUser(creatuser);
			question.setCreatDate(creatdate);
			question.setDiff(diff);
			question.setKeyWord(keyword);
			question.setQuestionCore(questioncore);
			question.setLore(lore);
			question.setCognizeType(cognizetype);
			question.setPurpose(purpose);
			question.setReferenceScore(referencescore);
			question.setReferenceTime(referencetime);
			question.setStudentNote(studentnote);
			question.setTeacherNote(teachernote);
			question.setType(type);
			question.setRemark(remark);
			return question.update();
		} else {
			throw new NoRightException("您没有修改题库的权限");
		}
	}

	public int deleteStoreQuestion(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteStoreQuestion == 1) {
			OracleStoreQuestion question = new OracleStoreQuestion();
			question.setId(id);

			return question.delete();
		} else {
			throw new NoRightException("您没有删除题库的权限");
		}
	}

	public int deleteAllStoreQuestion(String loreDirId) throws NoRightException, PlatformException {
		if (testPriv.deleteStoreQuestion == 1) {
			OracleStoreQuestion question = new OracleStoreQuestion();
			return question.deleteAll(loreDirId);
		} else {
			throw new NoRightException("您没有删除题库的权限");
		}
	}

	public int downloadStoreQuestion(String[] ids) throws NoRightException, PlatformException {
		if (testPriv.deleteStoreQuestion == 1) {
			OracleStoreQuestion question = new OracleStoreQuestion();
			return question.batchDownload(ids);
		} else {
			throw new NoRightException("您没有查看题库的权限");
		}
	}

	public int downloadAllStoreQuestion(String loreDirId) throws NoRightException, PlatformException {
		if (testPriv.deleteStoreQuestion == 1) {
			OracleStoreQuestion question = new OracleStoreQuestion();
			return question.downloadAll(loreDirId);
		} else {
			throw new NoRightException("您没有查看题库的权限");
		}
	}

	public StoreQuestion getStoreQuestion(String id) throws NoRightException, PlatformException {
		if (testPriv.getStoreQuestion == 1) {
			OracleStoreQuestion question = new OracleStoreQuestion(id);

			return question;
		} else {
			throw new NoRightException("您没有查看题库的权限");
		}
	}

	public List getTestPapers(Page page, String id, String title, String creatuser, String creatdate, String status, String note, String time, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getTestPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (time != null)
				searchList.add(new SearchProperty("time", time, "="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getTestPapers(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看试卷列表的权限");
		}
	}

	public int getTestPapersNum(String id, String title, String creatuser, String creatdate, String status, String note, String time, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getTestPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (time != null)
				searchList.add(new SearchProperty("time", time, "="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getTestPapersNum(searchList);
		} else {
			throw new NoRightException("您没有查看试卷列表的权限");
		}
	}

	public int addTestPaper(String title, String creatuser, String status, String note, String time, String groupId) throws NoRightException, PlatformException {
		if (testPriv.addTestPaper == 1) {
			OracleTestPaper testPaper = new OracleTestPaper();
			testPaper.setTitle(title);
			testPaper.setCreatUser(creatuser);
			testPaper.setStatus(status);
			testPaper.setNote(note);
			testPaper.setTime(time);
			testPaper.setType("test");
			testPaper.setGroupId(groupId);

			return testPaper.add();
		} else {
			throw new NoRightException("您没有添加自测试卷的权限");
		}
	}

	public List<String> addTestPaper(String title, String creatuser, String status, String note, String time, String groupId, String paper_num) throws NoRightException, PlatformException {
		List<String> ls = new ArrayList<String>();
		int num = Integer.parseInt(paper_num);
		if (testPriv.addTestPaper == 1) {
			for (int i = 0; i < num; i++) {
				OracleTestPaper testPaper = new OracleTestPaper();
				if (num == 1) {
					testPaper.setTitle(title);
				} else {
					testPaper.setTitle(title + "_" + (i + 1));
				}
				testPaper.setCreatUser(creatuser);
				testPaper.setStatus(status);
				testPaper.setNote(note);
				testPaper.setTime(time);
				testPaper.setType("test");
				testPaper.setGroupId(groupId);
				String paperid = String.valueOf(testPaper.add());
				if (paperid != null && !paperid.equals("0")) {
					ls.add(paperid);
				}
			}
			return ls;
		} else {
			throw new NoRightException("您没有添加自测试卷的权限");
		}
	}

	public TestPaper getTestPaper(String id) throws NoRightException, PlatformException {
		if (testPriv.getTestPaper == 1) {
			OracleTestPaper testPaper = new OracleTestPaper(id);
			return testPaper;
		} else {
			throw new NoRightException("您没有查看自测试卷信息的权限");
		}
	}

	public int updateTestPaper(String id, String title, String creatuser, String status, String note, String time, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.updateTestPaper == 1) {
			OracleTestPaper testPaper = new OracleTestPaper(id);
			testPaper.setTitle(title);
			testPaper.setCreatUser(creatuser);
			testPaper.setStatus(status);
			testPaper.setNote(note);
			testPaper.setTime(time);
			testPaper.setType(type);
			testPaper.setGroupId(groupId);

			return testPaper.update();
		} else {
			throw new NoRightException("您没有添加自测试卷的权限");
		}
	}

	public int deleteTestPaper(String id) throws NoRightException, PlatformException {
		if (testPriv.updateTestPaper == 1) {
			OracleTestPaper testPaper = new OracleTestPaper(id);
			return testPaper.delete();
		} else {
			throw new NoRightException("您没有添加自测试卷的权限");
		}
	}

	public HashMap getQuestionsByPaperPolicy(String paperPolicyId) throws NoRightException {
		if (testPriv.getStoreQuestions == 1) {
			OraclePaperPolicy paperPolicy = new OraclePaperPolicy(paperPolicyId);
			OraclePaperActivity paperActivity = new OraclePaperActivity();
			paperActivity.setPaperPolicyId(paperPolicyId);
			// paperActivity.setQuestiontype(paperPolicy.getType());//如果要加上题目用途范围限制的话就加上这个。
			// return
			// paperActivity.getQuestionsByPaperPolicy(paperPolicy.getPolicyCore());
			return paperActivity.getQuestionsByPaperPolicyNew(paperPolicy.getPolicyCore(), paperPolicy.getType());
		} else {
			throw new NoRightException("您没有使用组卷策略组织自测试卷的权限");
		}
	}

	public List getOtherQuestionsByPaperPolicy(Page page, String courseId, String questionType, List questionList, String purpose) throws NoRightException {
		if (testPriv.getStoreQuestions == 1) {
			OracleTestList testList = new OracleTestList();
			List searchproperty = new ArrayList();
			searchproperty.add(new SearchProperty("type", questionType, "="));
			searchproperty.add(new SearchProperty("purpose", purpose, "like"));
			// searchproperty.add(new SearchProperty("purpose", purpose, "="));
			String id = "";
			if (questionList != null) {
				for (Iterator it = questionList.iterator(); it.hasNext();) {
					id += ",'" + ((PaperQuestion) it.next()).getTitle() + "'";
				}
				if (id.length() > 0)
					searchproperty.add(new SearchProperty("title", id.substring(1), "notIn"));
			}
			return testList.getQuestionsByPaperPolicy("-1", courseId, page, searchproperty, null);
		} else {
			throw new NoRightException("您没有使用组卷策略组织自测试卷的权限");
		}
	}

	public int getOtherQuestionsByPaperPolicyNum(String courseId, String questionType, List questionList, String purpose) throws NoRightException {
		if (testPriv.getStoreQuestions == 1) {
			OracleTestList testList = new OracleTestList();
			List searchproperty = new ArrayList();
			searchproperty.add(new SearchProperty("type", questionType, "="));
			searchproperty.add(new SearchProperty("purpose", purpose, "like"));
			String id = "";
			if (questionList != null) {
				for (Iterator it = questionList.iterator(); it.hasNext();) {
					id += ",'" + ((PaperQuestion) it.next()).getTitle() + "'";
				}
				if (id.length() > 0)
					searchproperty.add(new SearchProperty("title", id.substring(1), "notIn"));
			}
			return testList.getStoreQuestions1Num(searchproperty, courseId);
		} else {
			throw new NoRightException("您没有使用组卷策略组织自测试卷的权限");
		}
	}

	public List getQuestionsByPaperPolicy(Page page, String questionType, List questionList) throws NoRightException {
		if (testPriv.getStoreQuestions == 1) {
			OracleTestList testList = new OracleTestList();
			List searchproperty = new ArrayList();
			searchproperty.add(new SearchProperty("type", questionType, "="));
			String id = "";
			if (questionList != null) {
				for (Iterator it = questionList.iterator(); it.hasNext();) {
					id += ",'" + (String) it.next() + "'";
				}
				if (id.length() > 0)
					searchproperty.add(new SearchProperty("id", id.substring(1), "in"));
			}
			return testList.getQuestionsByPaperPolicy("-1", null, page, searchproperty, null);
		} else {
			throw new NoRightException("您没有使用组卷策略组织自测试卷的权限");
		}
	}

	public int getQuestionsByPaperPolicyNum(String questionType, List questionList) throws NoRightException {
		if (testPriv.getStoreQuestions == 1) {
			OracleTestList testList = new OracleTestList();
			List searchproperty = new ArrayList();
			searchproperty.add(new SearchProperty("type", questionType, "="));
			String id = "";
			if (questionList != null) {
				for (Iterator it = questionList.iterator(); it.hasNext();) {
					id += ",'" + (String) it.next() + "'";
				}
				if (id.length() > 0)
					searchproperty.add(new SearchProperty("id", id.substring(1), "in"));
			}
			return testList.getStoreQuestionsNum(searchproperty);
		} else {
			throw new NoRightException("您没有使用组卷策略组织自测试卷的权限");
		}
	}

	public int addPaperPolicy(HttpServletRequest request, HttpSession session) throws NoRightException, PlatformException {
		if (testPriv.addPaperPolicy == 1) {
			OraclePaperPolicy paperPolicy = new OraclePaperPolicy();
			PaperPolicyCore paperPolicyCore = new PaperPolicyCore();
			paperPolicy.setTitle((String) session.getAttribute("paper_id"));

			paperPolicy.setTitle(request.getParameter("title"));
			paperPolicy.setNote(request.getParameter("note"));
			EntityUser user = (EntityUser) session.getAttribute("eduplatform_user");
			paperPolicy.setCreatUser("(" + user.getId() + ")" + user.getName());
			paperPolicyCore.getXmlPolicyCore(request);
			paperPolicy.setPolicyCore(paperPolicyCore.getXmlPolicyCore());
			paperPolicy.setGroupId(request.getParameter("teachclass_id"));
			paperPolicy.setType(request.getParameter("type"));
			return paperPolicy.add();
		} else {
			throw new NoRightException("您没有添加组卷策略的权限");
		}
	}

	public int updatePaperPolicy(HttpServletRequest request, HttpSession session) throws NoRightException, PlatformException {
		if (testPriv.updatePaperPolicy == 1) {
			OraclePaperPolicy paperPolicy = new OraclePaperPolicy(request.getParameter("id"));
			PaperPolicyCore paperPolicyCore = new PaperPolicyCore();
			/*
			 * EntityUser user = (EntityUser) session
			 * .getAttribute("eduplatform_user");
			 * paperPolicy.setCreatUser(user.getId());
			 */
			paperPolicyCore.getXmlPolicyCore(request);
			paperPolicy.setPolicyCore(paperPolicyCore.getXmlPolicyCore());
			paperPolicy.setStatus("1");
			return paperPolicy.update();
		} else {
			throw new NoRightException("您没有修改组卷策略的权限");
		}
	}

	public int updateBasicPaperPolicy(HttpServletRequest request, HttpSession session) throws NoRightException, PlatformException {
		if (testPriv.updatePaperPolicy == 1) {
			OraclePaperPolicy paperPolicy = new OraclePaperPolicy(request.getParameter("id"));
			PaperPolicyCore paperPolicyCore = new PaperPolicyCore();
			paperPolicy.setTitle(request.getParameter("title"));
			paperPolicy.setNote(request.getParameter("note"));
			paperPolicyCore.setXmlPolicyCore(paperPolicy.getPolicyCore());
			paperPolicyCore.updateBasicXmlPolicyCore(request);
			paperPolicy.setPolicyCore(paperPolicyCore.getXmlPolicyCore());
			return paperPolicy.update();
		} else {
			throw new NoRightException("您没有修改组卷策略的权限");
		}
	}

	public int modifyPaperPolicy(HttpServletRequest request, HttpSession session) throws NoRightException, PlatformException {
		if (testPriv.updatePaperPolicy == 1) {
			OraclePaperPolicy paperPolicy = new OraclePaperPolicy(request.getParameter("id"));
			PaperPolicyCore paperPolicyCore = new PaperPolicyCore();
			paperPolicy.setTitle(request.getParameter("title"));
			paperPolicy.setNote(request.getParameter("note"));
			paperPolicyCore.getXmlPolicyCore(request);
			paperPolicy.setPolicyCore(paperPolicyCore.getXmlPolicyCore());
			paperPolicy.setStatus("0");
			return paperPolicy.update();
		} else {
			throw new NoRightException("您没有修改组卷策略的权限");
		}
	}

	public List getPaperPolicys(Page page, String id, String title, String creatuser, String creatdate, String status, String note, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getPaperPolicys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null && !"".equals(title) && !"null".equalsIgnoreCase(title))
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null && !"".equals(creatuser) && !"null".equalsIgnoreCase(creatuser))
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null && !"".equals(creatdate) && !"null".equalsIgnoreCase(creatdate))
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null && !"".equals(status) && !"null".equalsIgnoreCase(status))
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null && !"".equals(note) && !"null".equalsIgnoreCase(note))
				searchList.add(new SearchProperty("note", note, "like"));
			if (type != null && !"".equals(type) && !"null".equalsIgnoreCase(type))
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null && !"".equals(groupId) && !"null".equalsIgnoreCase(groupId))
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getPaperPolicys(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看组卷策略的权限");
		}
	}

	public int getPaperPolicysNum(String id, String title, String creatuser, String creatdate, String status, String note, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getPaperPolicys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null && !"".equals(title) && !"null".equalsIgnoreCase(title))
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null && !"".equals(creatuser) && !"null".equalsIgnoreCase(creatuser))
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null && !"".equals(creatdate) && !"null".equalsIgnoreCase(creatdate))
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null && !"".equals(status) && !"null".equalsIgnoreCase(status))
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null && !"".equals(note) && !"null".equalsIgnoreCase(note))
				searchList.add(new SearchProperty("note", note, "like"));
			if (type != null && !"".equals(type) && !"null".equalsIgnoreCase(type))
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null && !"".equals(groupId) && !"null".equalsIgnoreCase(groupId))
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getPaperPolicysNum(searchList);
		} else {
			throw new NoRightException("您没有查看组卷策略的权限");
		}
	}

	public int deletePaperPolicy(String id) throws NoRightException, PlatformException {
		if (testPriv.deletePaperPolicy == 1) {
			OraclePaperPolicy paperPolicy = new OraclePaperPolicy();
			paperPolicy.setId(id);
			return paperPolicy.delete();
		} else {
			throw new NoRightException("您没有删除组卷策略的权限");
		}
	}

	public PaperPolicy getPaperPolicy(String id) throws NoRightException, PlatformException {
		if (testPriv.getPaperPolicy == 1) {
			return new OraclePaperPolicy(id);
		} else {
			throw new NoRightException("您没有查看组卷策略的权限");
		}
	}

	public PaperPolicyCore getPaperPolicyCore(String xmlPolicyCore) throws NoRightException {
		if (testPriv.getPaperPolicy == 1) {
			return new PaperPolicyCore(xmlPolicyCore);
		} else {
			throw new NoRightException("您没有查看组卷策略的权限");
		}
	}

	public PaperQuestion getPaperQuestion(String id) throws NoRightException {
		if (testPriv.getPaperQuestion == 1) {
			OraclePaperQuestion opq = new OraclePaperQuestion(id);

			return opq;
		} else {
			throw new NoRightException("您没有查看PaperQuestion的权限");
		}
	}

	public int addPaperQuestion(PaperQuestion question) throws NoRightException, PlatformException {
		if (testPriv.addLoreDir == 1) {
			OraclePaperQuestion paperQuestion = new OraclePaperQuestion(question);
			return paperQuestion.add();
		} else {
			throw new NoRightException("您没有添加PaperQuestion的权限");
		}
	}

	public int updatePaperQuestionScore(PaperQuestion question) throws NoRightException, PlatformException {
		if (testPriv.addLoreDir == 1) {
			OraclePaperQuestion paperQuestion = new OraclePaperQuestion();
			paperQuestion.setId(question.getId());
			paperQuestion.setScore(question.getScore());
			return paperQuestion.updateScore();
		} else {
			throw new NoRightException("您没有添加PaperQuestion的权限");
		}
	}

	public int addTestPaperHistory(String userId, String testPaperId, HashMap answer) throws NoRightException, PlatformException {
		if (testPriv.addTestPaperHistory == 1) {
			OracleTestPaperHistory history = new OracleTestPaperHistory();
			history.setUserId(userId);
			history.setTestPaperId(testPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			return history.add();
		} else {
			throw new NoRightException("您没有添加答卷结果的权限");
		}
	}

	/**
	 * 新添加，分数保存在elective
	 */
	public int addTestPaperHistory(String userId, String testPaperId, String score, HashMap answer) throws NoRightException, PlatformException {
		if (testPriv.addTestPaperHistory == 1) {
			OracleTestPaperHistory history = new OracleTestPaperHistory();
			history.setUserId(userId);
			history.setTestPaperId(testPaperId);
			history.setTestResult(answer);
			history.setScore(score);
			history.setScore((String) answer.get("totalScore"));
			return history.add();
		} else {
			throw new NoRightException("您没有添加答卷结果的权限");
		}
	}

	public List getHomeworkPapers(Page page, String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getHomeworkPapers(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看作业列表的权限");
		}
	}

	public int getHomeworkPapersNum(String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getHomeworkPapersNum(searchList);
		} else {
			throw new NoRightException("您没有查看作业列表的权限");
		}
	}

	public int addHomeworkPaper(String title, String creatuser, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.addTestPaper == 1) {
			OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper();
			homeworkPaper.setTitle(title);
			homeworkPaper.setCreatUser(creatuser);
			homeworkPaper.setStatus(status);
			homeworkPaper.setNote(note);
			homeworkPaper.setComments(comment);
			homeworkPaper.setStartDate(upDate);
			homeworkPaper.setEndDate(endDate);
			homeworkPaper.setType(type);
			homeworkPaper.setGroupId(groupId);
			return homeworkPaper.add();
		} else {
			throw new NoRightException("您没有添加作业的权限");
		}
	}

	public int addHomeworkPaper(String title, String creatuser, String status, String note, String comment, String upDate, String endDate, String type, String groupId, String batch_id) throws NoRightException, PlatformException {
		if (testPriv.addTestPaper == 1) {
			OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper();
			homeworkPaper.setTitle(title);
			homeworkPaper.setCreatUser(creatuser);
			homeworkPaper.setStatus(status);
			homeworkPaper.setNote(note);
			homeworkPaper.setComments(comment);
			homeworkPaper.setStartDate(upDate);
			homeworkPaper.setEndDate(endDate);
			homeworkPaper.setType(type);
			homeworkPaper.setGroupId(groupId);
			homeworkPaper.setBatch_id(batch_id);
			return homeworkPaper.add(true);
		} else {
			throw new NoRightException("您没有添加作业的权限");
		}
	}

	public HomeworkPaper getHomeworkPaper(String id) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaper == 1) {
			OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper(id);
			return homeworkPaper;
		} else {
			throw new NoRightException("您没有查看作业信息的权限");
		}
	}

	public int updateHomeworkPaper(String id, String title, String creatuser, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.updateHomeworkPaper == 1) {
			OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper(id);
			homeworkPaper.setTitle(title);
			homeworkPaper.setCreatUser(creatuser);
			homeworkPaper.setStatus(status);
			homeworkPaper.setNote(note);
			homeworkPaper.setComments(comment);
			homeworkPaper.setStartDate(upDate);
			homeworkPaper.setEndDate(endDate);
			homeworkPaper.setType(type);
			homeworkPaper.setGroupId(groupId);
			return homeworkPaper.update();
		} else {
			throw new NoRightException("您没有添加作业的权限");
		}
	}

	public int updateHomeworkPaper(String id, String title, String creatuser, String status, String note, String comment, String upDate, String endDate, String type, String groupId, String batch_id) throws NoRightException, PlatformException {
		if (testPriv.updateHomeworkPaper == 1) {
			OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper(id);
			homeworkPaper.setTitle(title);
			homeworkPaper.setCreatUser(creatuser);
			homeworkPaper.setStatus(status);
			homeworkPaper.setNote(note);
			homeworkPaper.setComments(comment);
			homeworkPaper.setStartDate(upDate);
			homeworkPaper.setEndDate(endDate);
			homeworkPaper.setType(type);
			homeworkPaper.setGroupId(groupId);
			homeworkPaper.setBatch_id(batch_id);
			return homeworkPaper.update(true);
		} else {
			throw new NoRightException("您没有添加作业的权限");
		}
	}

	public int deleteHomeworkPaper(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteHomeworkPaper == 1) {
			OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper();
			homeworkPaper.setId(id);
			return homeworkPaper.delete();
		} else {
			throw new NoRightException("您没有删除作业的权限");
		}
	}

	public List getTestPaperHistorys(Page page, String userId, String testPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (testPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", testPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getTestPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public List getTestPaperHistorys(Page page, String regNo, String userName, String testPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "student";

			if (userName != null) {
				searchList.add(new SearchProperty("user_id", "(%)%" + userName, "like"));
			}

			if (testPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", testPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			List orderproperty = new ArrayList();
			orderproperty.add(new OrderProperty("ISCHECK", OrderProperty.DESC));
			orderproperty.add(new OrderProperty("test_date", OrderProperty.DESC));
			return testList.getTestPaperHistorys(page, searchList, orderproperty, type, regNo);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public TestPaperHistory getTestPaperHistory(String id) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistory == 1) {
			return new OracleTestPaperHistory(id);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public int getTestPaperHistorysNum(String userId, String testPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (testPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", testPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getTestPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public int getTestPaperHistorysNum(String regNo, String userName, String testPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";

			if (userName != null) {
				searchList.add(new SearchProperty("user_id", "(%)%" + userName, "like"));
			}
			if (testPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", testPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getTestPaperHistorysNum(searchList, type, regNo);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public String getRegNoByUserId(String userId) {
		OracleTestList testList = new OracleTestList();
		return testList.getRegNoByUserId(userId);
	}

	public int deleteTestPaperHistory(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteTestPaperHistory == 1) {
			OracleTestPaperHistory history = new OracleTestPaperHistory();
			history.setId(id);
			return history.delete();
		} else {
			throw new NoRightException("您没有删除考试结果的权限");
		}
	}

	public int updateTestPaperHistory(String id, String userId, String testPaperId, HashMap answer ,String piciStudent) throws NoRightException, PlatformException {
		if (testPriv.updateTestPaperHistory == 1) {
			OracleTestPaperHistory history = new OracleTestPaperHistory();
			history.setId(id);
			history.setUserId(userId);
			history.setTestPaperId(testPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			history.setStatus((String) answer.get("status"));
			history.setPiciStudent(piciStudent);
			return history.update();
		} else {
			throw new NoRightException("您没有修改考试结果的权限");
		}
	}

	public int addHomeworkPaperHistory(String userId, String HomeworkPaperId, HashMap answer, String ischeck) throws NoRightException, PlatformException {
		if (testPriv.addHomeworkPaperHistory == 1) {
			OracleHomeworkPaperHistory history = new OracleHomeworkPaperHistory();
			history.setUserId(userId);
			history.setTestPaperId(HomeworkPaperId);
			history.setTestResult(answer);
			history.setIscheck(ischeck);
			history.setScore((String) answer.get("totalScore"));
			return history.add();
		} else {
			throw new NoRightException("您没有添加作业结果的权限");
		}
	}

	public int addHomeworkPaperHistory(String userId, String HomeworkPaperId, HashMap answer, String ischeck, String total, String percent, String type, String tuserId) throws NoRightException, PlatformException {
		if (testPriv.addHomeworkPaperHistory == 1) {
			OracleHomeworkPaperHistory history = new OracleHomeworkPaperHistory();
			history.setUserId(userId);
			history.setTestPaperId(HomeworkPaperId);
			history.setTestResult(answer);
			history.setIscheck(ischeck);
			history.setScore((String) answer.get("totalScore"));
			history.setTotal(total);
			history.setPercent(percent);
			history.setType(type);
			history.setTuserId(tuserId);
			return history.add();
		} else {
			throw new NoRightException("您没有添加作业结果的权限");
		}
	}

	public int deleteHomeworkPaperHistory(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteHomeworkPaperHistory == 1) {
			OracleHomeworkPaperHistory history = new OracleHomeworkPaperHistory();
			history.setId(id);
			return history.delete();
		} else {
			throw new NoRightException("您没有删除作业结果的权限");
		}
	}

	public int updateHomeworkPaperHistory(String id, String userId, String HomeworkPaperId, HashMap answer) throws NoRightException, PlatformException {
		if (testPriv.updateHomeworkPaperHistory == 1) {
			OracleHomeworkPaperHistory history = new OracleHomeworkPaperHistory();
			history.setId(id);
			history.setUserId(userId);
			history.setTestPaperId(HomeworkPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			history.setStatus("1");
			return history.update();
		} else {
			throw new NoRightException("您没有修改作业结果的权限");
		}
	}

	public int updateHomeworkPaperHistory(String id, String userId, String HomeworkPaperId, HashMap answer, String status) throws NoRightException, PlatformException {
		if (testPriv.updateHomeworkPaperHistory == 1) {
			OracleHomeworkPaperHistory history = new OracleHomeworkPaperHistory();
			history.setId(id);
			history.setUserId(userId);
			history.setTestPaperId(HomeworkPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			history.setStatus(status);
			return history.update();
		} else {
			throw new NoRightException("您没有修改作业结果的权限");
		}
	}

	public int updateHomeworkPaperHistory(String id, String userId, String HomeworkPaperId, HashMap answer, String status, String total, String percent, String type) throws NoRightException, PlatformException {
		if (testPriv.updateHomeworkPaperHistory == 1) {
			OracleHomeworkPaperHistory history = new OracleHomeworkPaperHistory();
			history.setId(id);
			history.setUserId(userId);
			history.setTestPaperId(HomeworkPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			history.setStatus(status);
			history.setTotal(total);
			history.setPercent(percent);
			history.setType(type);
			return history.update();
		} else {
			throw new NoRightException("您没有修改作业结果的权限");
		}
	}

	public List getHomeworkPaperHistorys(Page page, String userId, String HomeworkPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (type.equals("teacher")) {
				OracleHomeworkPaper paper = new OracleHomeworkPaper(HomeworkPaperId);
				Date today = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date paperEndDate = new Date();
				try {
					paperEndDate = format.parse(paper.getEndDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (today.getTime() <= paperEndDate.getTime()) {
					searchList.add(new SearchProperty("ischeck=0", "ischeck=1", "or"));
				}

			}
			return testList.getHomeworkPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看上交的作业的权限");
		}
	}

	public List getHomeworkPaperHistorysNew(Page page, String userId, String HomeworkPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (type.equals("teacher")) {
				OracleHomeworkPaper paper = new OracleHomeworkPaper(HomeworkPaperId);
				Date today = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date paperEndDate = new Date();
				try {
					paperEndDate = format.parse(paper.getEndDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (today.getTime() <= paperEndDate.getTime()) {
					searchList.add(new SearchProperty("ischeck=0", "ischeck=1", "or"));
				}

			}
			return testList.getHomeworkPaperHistorysNew(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看上交的作业的权限");
		}
	}

	public List getHomeworkPaperHistorys(Page page, String HomeworkPaperId, String siteId, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";

			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));

			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			if (siteId != null)
				searchList.add(new SearchProperty("site_id", siteId, "="));
			return testList.getHomeworkPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看上交的作业的权限");
		}
	}

	public HomeworkPaperHistory getHomeworkPaperHistory(String id) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaperHistory == 1) {
			return new OracleHomeworkPaperHistory(id);
		} else {
			throw new NoRightException("您没有查看作业结果的权限");
		}
	}

	public int getHomeworkPaperHistorysNum(String userId, String HomeworkPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getHomeworkPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看作业结果的权限");
		}
	}

	public int getHomeworkPaperHistorysNumNew(String userId, String HomeworkPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getHomeworkPaperHistorysNumNew(searchList, type);
		} else {
			throw new NoRightException("您没有查看作业结果的权限");
		}
	}

	public int getHomeworkPaperHistorysNum(String HomeworkPaperId, String site_id, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";

			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));

			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			if (site_id != null)
				searchList.add(new SearchProperty("site_id", site_id, "="));
			return testList.getHomeworkPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看作业结果的权限");
		}
	}

	public int changeTestPaperStatus(String id, String status) throws NoRightException, PlatformException {
		if (testPriv.changeTestPaperStatus == 1) {
			OracleTestPaper testPaper = new OracleTestPaper();
			testPaper.setId(id);
			testPaper.setStatus(status);
			return testPaper.reverseActive();
		} else {
			throw new NoRightException("您没有修改自测试卷状态的权限");
		}
	}

	public int addOnlineTestCourse(String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser) throws NoRightException, PlatformException {
		if (testPriv.addOnlineTestCourse == 1) {
			OracleOnlineTestCourse course = new OracleOnlineTestCourse();
			course.setTitle(title);
			course.setNote(note);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
			course.setStatus(status);
			course.setIsAutoCheck(isAutoCheck);
			course.setIsHiddenAnswer(isHiddenAnswer);
			OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
			batch.setId(batchId);
			course.setBatch(batch);
			course.setGroupId(groupId);
			course.setCreatUser(creatUser);
			return course.add();
		} else {
			throw new NoRightException("您没有添加考试课程的权限");
		}
	}

	public int addOnlineTestCourse(String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser, String batch_id) throws NoRightException, PlatformException {
		if (testPriv.addOnlineTestCourse == 1) {
			OracleOnlineTestCourse course = new OracleOnlineTestCourse();
			course.setTitle(title);
			course.setNote(note);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
			course.setStatus(status);
			course.setIsAutoCheck(isAutoCheck);
			course.setIsHiddenAnswer(isHiddenAnswer);
			course.setBatch_id(batch_id);
			OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
			batch.setId(batchId);
			course.setBatch(batch);
			course.setGroupId(groupId);
			course.setCreatUser(creatUser);
			return course.add(true);
		} else {
			throw new NoRightException("您没有添加考试课程的权限");
		}
	}

	public int deleteOnlineTestCourse(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteOnlineTestCourse == 1) {
			OracleOnlineTestCourse course = new OracleOnlineTestCourse();
			course.setId(id);
			return course.delete();
		} else {
			throw new NoRightException("您没有删除考试课程的权限");
		}
	}

	public int updateOnlineTestCourse(String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser) throws NoRightException, PlatformException {
		if (testPriv.updateOnlineTestCourse == 1) {
			OracleOnlineTestCourse course = new OracleOnlineTestCourse(id);
			course.setTitle(title);
			course.setNote(note);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
			course.setStatus(status);
			course.setIsAutoCheck(isAutoCheck);
			course.setIsHiddenAnswer(isHiddenAnswer);
			OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
			batch.setId(batchId);
			course.setBatch(batch);
			course.setGroupId(groupId);
			return course.update();
		} else {
			throw new NoRightException("您没有修改考试课程的权限");
		}
	}

	public int updateOnlineTestCourse(String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser, String batch_id) throws NoRightException, PlatformException {
		if (testPriv.updateOnlineTestCourse == 1) {
			OracleOnlineTestCourse course = new OracleOnlineTestCourse(id);
			course.setTitle(title);
			course.setNote(note);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
			course.setStatus(status);
			course.setIsAutoCheck(isAutoCheck);
			course.setIsHiddenAnswer(isHiddenAnswer);
			course.setBatch_id(batch_id);
			OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
			batch.setId(batchId);
			course.setBatch(batch);
			course.setGroupId(groupId);
			return course.update(true);
		} else {
			throw new NoRightException("您没有修改考试课程的权限");
		}
	}

	public List getOnlineTestCourses(Page page, String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (note != null) {
				searchList.add(new SearchProperty("note", note, "like"));
			}
			if (startDate != null) {
				searchList.add(new SearchProperty("start_date", startDate, "D>="));
			}
			if (endDate != null)
				searchList.add(new SearchProperty("end_date", endDate, "D<="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (isAutoCheck != null)
				searchList.add(new SearchProperty("isautocheck", isAutoCheck, "="));
			if (isHiddenAnswer != null)
				searchList.add(new SearchProperty("ishiddenanswer", isHiddenAnswer, "="));
			if (batchId != null)
				searchList.add(new SearchProperty("batch_id", batchId, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (creatUser != null)
				searchList.add(new SearchProperty("creatuser", creatUser, "="));
			return testList.getOnlineTestCourses(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public List getOnlineTestCourses(Page page, String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser, String batch_id) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (note != null) {
				searchList.add(new SearchProperty("note", note, "like"));
			}
			if (startDate != null) {
				searchList.add(new SearchProperty("start_date", startDate, "D>="));
			}
			if (endDate != null)
				searchList.add(new SearchProperty("end_date", endDate, "D<="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (isAutoCheck != null)
				searchList.add(new SearchProperty("isautocheck", isAutoCheck, "="));
			if (isHiddenAnswer != null)
				searchList.add(new SearchProperty("ishiddenanswer", isHiddenAnswer, "="));
			if (batchId != null)
				searchList.add(new SearchProperty("batch_id", batchId, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (creatUser != null)
				searchList.add(new SearchProperty("creatuser", creatUser, "="));
			return testList.getOnlineTestCourses(page, searchList, null, batch_id);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public Map getOnlineTestScore(String openCourseId, String studentId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			// return testList.getOnlineTestCourses(page, searchList,
			// null,batch_id);
			return testList.getOnlineTestScore(openCourseId, studentId);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public OnlineTestCourse getOnlineTestCourse(String id) throws NoRightException, PlatformException {
		if (testPriv.getOnlineTestCourse == 1) {
			return new OracleOnlineTestCourse(id);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public int getOnlineTestCoursesNum(String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (note != null) {
				searchList.add(new SearchProperty("note", note, "like"));
			}
			if (startDate != null) {
				searchList.add(new SearchProperty("start_date", startDate, "D>="));
			}
			if (endDate != null)
				searchList.add(new SearchProperty("end_date", endDate, "D<="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (isAutoCheck != null)
				searchList.add(new SearchProperty("isautocheck", isAutoCheck, "="));
			if (isHiddenAnswer != null)
				searchList.add(new SearchProperty("ishiddenanswer", isHiddenAnswer, "="));
			if (batchId != null)
				searchList.add(new SearchProperty("batch_id", batchId, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (creatUser != null)
				searchList.add(new SearchProperty("creatuser", creatUser, "="));
			return testList.getOnlineTestCoursesNum(searchList);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public int getOnlineTestCoursesNum(String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser, String user) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			/*
			 * if("student".equals(user)){ System.out.println("student");
			 * SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd
			 * HH:mm:ss"); String s = sdf1.format(new Date()); String sdf =
			 * s.toString(); searchList.add(new SearchProperty("end_date",sdf,
			 * "D>=")); searchList.add(new SearchProperty("start_date",sdf,"D<=")); }
			 */
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (note != null) {
				searchList.add(new SearchProperty("note", note, "like"));
			}
			if (startDate != null) {
				searchList.add(new SearchProperty("start_date", startDate, "D>="));
			}
			if (endDate != null)
				searchList.add(new SearchProperty("end_date", endDate, "D<="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (isAutoCheck != null)
				searchList.add(new SearchProperty("isautocheck", isAutoCheck, "="));
			if (isHiddenAnswer != null)
				searchList.add(new SearchProperty("ishiddenanswer", isHiddenAnswer, "="));
			if (batchId != null)
				searchList.add(new SearchProperty("batch_id", batchId, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (creatUser != null)
				searchList.add(new SearchProperty("creatuser", creatUser, "="));
			return testList.getOnlineTestCoursesNum2(searchList);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public int getOnlineTestCoursesNum(String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser, String user, String batch_id) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			/*
			 * if("student".equals(user)){ System.out.println("student");
			 * SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd
			 * HH:mm:ss"); String s = sdf1.format(new Date()); String sdf =
			 * s.toString(); searchList.add(new SearchProperty("end_date",sdf,
			 * "D>=")); searchList.add(new SearchProperty("start_date",sdf,"D<=")); }
			 */
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (note != null) {
				searchList.add(new SearchProperty("note", note, "like"));
			}
			if (startDate != null) {
				searchList.add(new SearchProperty("start_date", startDate, "D>="));
			}
			if (endDate != null)
				searchList.add(new SearchProperty("end_date", endDate, "D<="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (isAutoCheck != null)
				searchList.add(new SearchProperty("isautocheck", isAutoCheck, "="));
			if (isHiddenAnswer != null)
				searchList.add(new SearchProperty("ishiddenanswer", isHiddenAnswer, "="));
			if (batchId != null)
				searchList.add(new SearchProperty("batch_id", batchId, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (creatUser != null)
				searchList.add(new SearchProperty("creatuser", creatUser, "="));
			return testList.getOnlineTestCoursesNum2(searchList, batch_id);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public int changeOnlineTestCourseStatus(String id, String status) throws NoRightException, PlatformException {
		if (testPriv.changeOnlineTestCourseStatus == 1) {
			OracleOnlineTestCourse testCourse = new OracleOnlineTestCourse();
			testCourse.setId(id);
			testCourse.setStatus(status);
			return testCourse.reverseActive();
		} else {
			throw new NoRightException("您没有修改考试课程状态的权限");
		}
	}

	public List getTestPapersByOnlineTestCourse(Page page, String testCourseId) throws NoRightException, PlatformException {
		if (testPriv.getTestPapersByOnlineTestCourse == 1) {
			OracleOnlineTestCourse testCourse = new OracleOnlineTestCourse();
			testCourse.setId(testCourseId);
			return testCourse.getTestPapers(page);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public int getTestPapersNumByOnlineTestCourse(String testCourseId) throws NoRightException, PlatformException {
		if (testPriv.getTestPapersByOnlineTestCourse == 1) {
			OracleOnlineTestCourse testCourse = new OracleOnlineTestCourse();
			testCourse.setId(testCourseId);
			return testCourse.getTestPapersNum();
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public int addTestPaperByOnlineTestCourse(String paperId, String testCourseId) throws NoRightException, PlatformException {
		if (testPriv.addTestPaperByOnlineTestCourse == 1) {
			OracleOnlineTestCourse testCourse = new OracleOnlineTestCourse();
			testCourse.setId(testCourseId);
			return testCourse.addTestPaper(paperId);
		} else {
			throw new NoRightException("您没有查添加考试试卷的权限");
		}
	}

	/*
	 * (non-Javadoc) 组卷时用到的
	 * 
	 * @see com.whaty.platform.test.TestManage#addTestPaperByOnlineTestCourse(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> addTestPaperByOnlineTestCourse(List<String> paperls, String testCourseId) throws NoRightException, PlatformException {
		List<String> tcls = new ArrayList<String>();
		if (testPriv.addTestPaperByOnlineTestCourse == 1) {
			for (int i = 0; i < paperls.size(); i++) {
				OracleOnlineTestCourse testCourse = new OracleOnlineTestCourse();
				testCourse.setId(testCourseId);
				int tc = testCourse.addTestPaper(paperls.get(i));
				tcls.add(String.valueOf(tc));
			}
			return tcls;
		} else {
			throw new NoRightException("您没有查添加考试试卷的权限");
		}
	}

	public int deleteTestPaperByOnlineTestCourse(String paperId, String testCourseId) throws NoRightException, PlatformException {
		if (testPriv.addTestPaperByOnlineTestCourse == 1) {
			OracleOnlineTestCourse testCourse = new OracleOnlineTestCourse();
			testCourse.setId(testCourseId);
			return testCourse.deleteTestPaper(paperId);
		} else {
			throw new NoRightException("您没有查删除考试试卷的权限");
		}
	}

	/**
	 * @author shu
	 * @return 通过批次ID得到批次，相关信息。
	 */
	public ExamBatch getExamBatch(String id) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			return new OracleExamBatch(id);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	/**
	 * @author shubeibei
	 * @return 得到批次的列表。
	 */
	public List getExamBatchList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			if (request != null) {
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String minstartDate = request.getParameter("minstartDate");
				String maxstartDate = request.getParameter("maxstartDate");
				String minendDate = request.getParameter("minendDate");
				String maxendDate = request.getParameter("maxendDate");
				String status = request.getParameter("status");

				String search = request.getParameter("Search");

				if (id != null)
					searchList.add(new SearchProperty("id", id, "="));
				if (name != null)
					searchList.add(new SearchProperty("name", name, "like"));
				if (minstartDate != null)
					searchList.add(new SearchProperty("s_date", minstartDate, ">="));
				if (maxstartDate != null)
					searchList.add(new SearchProperty("s_date", maxstartDate, "<="));
				if (minendDate != null)
					searchList.add(new SearchProperty("e_date", minendDate, ">="));
				if (maxendDate != null)
					searchList.add(new SearchProperty("e_date", maxstartDate, "<="));
				if (status != null)
					searchList.add(new SearchProperty("status", status, "="));

				if (search != null) {
					searchList.add(new SearchProperty("name", search, "like"));
				}

			}
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id", "1"));
			return testList.getBatches(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	/**
	 * @author shubeibei
	 * @return 得到批次的列表。
	 */
	public List getExamActiveBatchList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			if (request != null) {
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String minstartDate = request.getParameter("minstartDate");
				String maxstartDate = request.getParameter("maxstartDate");
				String minendDate = request.getParameter("minendDate");
				String maxendDate = request.getParameter("maxendDate");
				String status = request.getParameter("status");

				String search = request.getParameter("Search");

				if (id != null)
					searchList.add(new SearchProperty("id", id, "="));
				if (name != null)
					searchList.add(new SearchProperty("name", name, "like"));
				if (minstartDate != null)
					searchList.add(new SearchProperty("s_date", minstartDate, ">="));
				if (maxstartDate != null)
					searchList.add(new SearchProperty("s_date", maxstartDate, "<="));
				if (minendDate != null)
					searchList.add(new SearchProperty("e_date", minendDate, ">="));
				if (maxendDate != null)
					searchList.add(new SearchProperty("e_date", maxstartDate, "<="));
				if (status != null)
					searchList.add(new SearchProperty("status", status, "="));

				if (search != null) {
					searchList.add(new SearchProperty("name", search, "like"));
				}

			}
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id", "1"));
			// return testList.getBatches(page, searchList, orderList);
			return testList.getActiveBatches(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public List getTableMessage6(Page page, String batch_id, String grade_id, String major_id, String edutype_id, String kaoqu_id, String shenfen_id, String semester_id) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			List searchProperties = new ArrayList();

			if (grade_id != null && !grade_id.equals("") && !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
			}
			if (major_id != null && !major_id.equals("") && !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id, "="));
			}
			if (edutype_id != null && !edutype_id.equals("") && !edutype_id.equals("null")) {
				searchProperties.add(new SearchProperty("edutype_id", edutype_id, "="));
			}
			if (kaoqu_id != null && !kaoqu_id.equals("") && !kaoqu_id.equals("null")) {
				searchProperties.add(new SearchProperty("kaoqu_id", kaoqu_id, "="));
			}
			if (shenfen_id != null && !shenfen_id.equals("") && !shenfen_id.equals("null")) {
				searchProperties.add(new SearchProperty("standing", shenfen_id, "="));
			}
			if (semester_id != null && !semester_id.equals("") && !semester_id.equals("null")) {
				searchProperties.add(new SearchProperty("semester_id", semester_id, "="));
			}
			/*
			 * if (shenfen_id != null && !shenfen_id.equals("") &&
			 * !shenfen_id.equals("null")) { searchProperties.add(new
			 * SearchProperty("bathc_id", batch_id, "=")); }
			 */
			List order = new ArrayList();// to_number(room_no),
			order.add(new OrderProperty("to_number(room_no)", OrderProperty.ASC));
			order.add(new OrderProperty("kaoqu_id", OrderProperty.ASC));
			order.add(new OrderProperty("grade_id", OrderProperty.ASC));
			order.add(new OrderProperty("edutype_id", OrderProperty.ASC));
			order.add(new OrderProperty("major_id", OrderProperty.ASC));
			// OracleBasicUserList oracleBasicUserList = new
			// OracleBasicUserList();
			OracleExamList testList = new OracleExamList();
			return testList.getTableMsg6(page, searchProperties, order, batch_id);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public List getTableMessage7(Page page, String batch_id, String grade_id, String major_id, String edutype_id, String kaoqu_id, String shenfen_id, String semester_id) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			List searchProperties = new ArrayList();

			if (grade_id != null && !grade_id.equals("") && !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
			}
			if (major_id != null && !major_id.equals("") && !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id, "="));
			}
			if (edutype_id != null && !edutype_id.equals("") && !edutype_id.equals("null")) {
				searchProperties.add(new SearchProperty("edutype_id", edutype_id, "="));
			}
			if (kaoqu_id != null && !kaoqu_id.equals("") && !kaoqu_id.equals("null")) {
				searchProperties.add(new SearchProperty("kaoqu_id", kaoqu_id, "="));
			}
			if (shenfen_id != null && !shenfen_id.equals("") && !shenfen_id.equals("null")) {
				searchProperties.add(new SearchProperty("standing", shenfen_id, "="));
			}
			if (semester_id != null && !semester_id.equals("") && !semester_id.equals("null")) {
				searchProperties.add(new SearchProperty("semester_id", semester_id, "="));
			}
			/*
			 * if (shenfen_id != null && !shenfen_id.equals("") &&
			 * !shenfen_id.equals("null")) { searchProperties.add(new
			 * SearchProperty("bathc_id", batch_id, "=")); }
			 */
			List order = new ArrayList();
			order.add(new OrderProperty("kaoqu_id", OrderProperty.ASC));
			order.add(new OrderProperty("grade_id", OrderProperty.ASC));
			order.add(new OrderProperty("edutype_id", OrderProperty.ASC));
			order.add(new OrderProperty("major_id", OrderProperty.ASC));
			// OracleBasicUserList oracleBasicUserList = new
			// OracleBasicUserList();
			OracleReExamList testList = new OracleReExamList();
			return testList.getTableMsg6(page, searchProperties, order, batch_id);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	/**
	 * @author shubeibei
	 * @return 得到活动批次的列表。
	 */
	public List getReExamActiveBatchList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			if (request != null) {
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String minstartDate = request.getParameter("minstartDate");
				String maxstartDate = request.getParameter("maxstartDate");
				String minendDate = request.getParameter("minendDate");
				String maxendDate = request.getParameter("maxendDate");
				String status = request.getParameter("status");

				String search = request.getParameter("Search");

				if (id != null)
					searchList.add(new SearchProperty("id", id, "="));
				if (name != null)
					searchList.add(new SearchProperty("name", name, "like"));
				if (minstartDate != null)
					searchList.add(new SearchProperty("s_date", minstartDate, ">="));
				if (maxstartDate != null)
					searchList.add(new SearchProperty("s_date", maxstartDate, "<="));
				if (minendDate != null)
					searchList.add(new SearchProperty("e_date", minendDate, ">="));
				if (maxendDate != null)
					searchList.add(new SearchProperty("e_date", maxstartDate, "<="));
				if (status != null)
					searchList.add(new SearchProperty("status", status, "="));

				if (search != null) {
					searchList.add(new SearchProperty("name", search, "like"));
				}

			}
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id", "1"));
			// return testList.getBatches(page, searchList, orderList);
			// return testList.getBatches(page, searchList, orderList);
			return testList.getActiveBatches(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int addExamBatch(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamBatch == 1) {
			OracleExamBatch oracleExamBatch = new OracleExamBatch();
			oracleExamBatch.setId(request.getParameter("id"));
			oracleExamBatch.setName(request.getParameter("name"));
			oracleExamBatch.setStartDate(request.getParameter("startDate"));
			oracleExamBatch.setEndDate(request.getParameter("endDate"));
			oracleExamBatch.setExamRoomStartDate(request.getParameter("astartDate"));
			oracleExamBatch.setExamRoomEndDate(request.getParameter("aendDate"));
			if (request.getParameter("status") == null) {
				oracleExamBatch.setStatus("0");
			} else {
				oracleExamBatch.setStatus(request.getParameter("status"));
			}
			return oracleExamBatch.add();

		} else {
			throw new PlatformException("您没有添加考试批次的权限");
		}
	}

	public int updateExamBatch(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamBatch == 1) {
			OracleExamBatch oracleExamBatch = new OracleExamBatch(request.getParameter("id"));
			if (request.getParameter("name") != null) {
				oracleExamBatch.setName(request.getParameter("name"));
			}
			if (request.getParameter("startDate") != null) {
				oracleExamBatch.setStartDate(request.getParameter("startDate"));
			}
			if (request.getParameter("endDate") != null) {
				oracleExamBatch.setEndDate(request.getParameter("endDate"));
			}
			if (request.getParameter("examRoomStartDate") != null) {
				oracleExamBatch.setExamRoomStartDate(request.getParameter("examRoomStartDate"));
			}
			if (request.getParameter("examRoomEndDate") != null) {
				oracleExamBatch.setExamRoomEndDate(request.getParameter("examRoomEndDate"));
			}
			if (request.getParameter("status") != null) {
				oracleExamBatch.setStatus(request.getParameter("status"));
			}
			return oracleExamBatch.update();

		} else {
			throw new PlatformException("您没有更新考试批次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 通过批次id来删除批次。
	 */
	public int deleteExamBatch(String id) throws PlatformException {
		if (testPriv.deleteExamBatch == 1) {
			OracleExamBatch oracleExamBatch = new OracleExamBatch(id);
			return oracleExamBatch.delete();
		} else {
			throw new PlatformException("您没有删除考试批次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 得到批次的个数。
	 */
	public int getExamBatchNum(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleExamList testList = new OracleExamList();
			if (request.getParameter("Search") == null) {
				return testList.getBatchNum(null);
			} else {
				List searchList = new ArrayList();
				searchList.add(new SearchProperty("name", request.getParameter("Search"), "like"));
				return testList.getBatchNum(searchList);
			}
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 得到处于解锁状态的批次的个数。
	 */
	public int getExamBatchNumStatus(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleExamList testList = new OracleExamList();
			/*
			 * if (request.getParameter("Search") == null) { return
			 * testList.getBatchNum(null); } else { List searchList = new
			 * ArrayList(); searchList.add(new SearchProperty("name", request
			 * .getParameter("Search"), "like")); return
			 * testList.getBatchNumStatus(null); }
			 */
			return testList.getBatchNumStatus(null);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int updateExamBatchStatus(String id, String status) throws PlatformException {
		if (testPriv.updateExamBatch == 1) {
			OracleExamBatch oracleExamBatch = new OracleExamBatch(id);
			oracleExamBatch.setStatus(status);
			return oracleExamBatch.update();
		} else {
			throw new PlatformException("您没有更新考试批次的权限");
		}
	}

	public ExamCourse getExamCourse(String id) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			return new OracleExamCourse(id);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getExamCourseList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String minstartDate = request.getParameter("minstartDate");
			String maxstartDate = request.getParameter("maxstartDate");
			String minendDate = request.getParameter("minendDate");
			String maxendDate = request.getParameter("maxendDate");
			String batch_id = request.getParameter("batch_id");
			String course_id = request.getParameter("course_id");
			String examsequence_id = request.getParameter("examsequence_id");
			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");

			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (minstartDate != null)
				searchList.add(new SearchProperty("s_date", minstartDate, ">="));
			if (maxstartDate != null)
				searchList.add(new SearchProperty("s_date", maxstartDate, "<="));
			if (minendDate != null)
				searchList.add(new SearchProperty("e_date", minendDate, ">="));
			if (maxendDate != null)
				searchList.add(new SearchProperty("e_date", maxstartDate, "<="));
			if (batch_id != null)
				searchList.add(new SearchProperty("test_batch_id", batch_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));

			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			if (examsequence_id != null && !examsequence_id.equals("")) {
				searchList.add(new SearchProperty("examsequence_id", examsequence_id, "="));
			}

			orderList.add(new OrderProperty("test_batch_id"));
			orderList.add(new OrderProperty("examsequence_id"));
			orderList.add(new OrderProperty("course_id"));
			return testList.getCourses(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getExamCourseList1(String activeBatchId, Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			/*
			 * String id = request.getParameter("id"); String name =
			 * request.getParameter("name"); String minstartDate =
			 * request.getParameter("minstartDate"); String maxstartDate =
			 * request.getParameter("maxstartDate"); String minendDate =
			 * request.getParameter("minendDate"); String maxendDate =
			 * request.getParameter("maxendDate"); String batch_id =
			 * request.getParameter("batch_id"); String course_id =
			 * request.getParameter("course_id"); String examsequence_id =
			 * request.getParameter("examsequence_id"); String examcourseName =
			 * request.getParameter("examcourseName"); String exambatchid =
			 * request.getParameter("exambatchid");
			 * 
			 * if (id != null) searchList.add(new SearchProperty("id", id,
			 * "=")); if (name != null) searchList.add(new
			 * SearchProperty("name", name, "like")); if (minstartDate != null)
			 * searchList .add(new SearchProperty("s_date", minstartDate,
			 * ">=")); if (maxstartDate != null) searchList .add(new
			 * SearchProperty("s_date", maxstartDate, "<=")); if (minendDate !=
			 * null) searchList.add(new SearchProperty("e_date", minendDate,
			 * ">=")); if (maxendDate != null) searchList .add(new
			 * SearchProperty("e_date", maxstartDate, "<=")); if (batch_id !=
			 * null) searchList.add(new SearchProperty("test_batch_id",
			 * batch_id, "=")); if (course_id != null) searchList.add(new
			 * SearchProperty("course_id", course_id, "="));
			 * 
			 * if (examcourseName != null && !examcourseName.equals("")) {
			 * searchList.add(new SearchProperty("name", examcourseName,
			 * "like")); } if (exambatchid != null && !exambatchid.equals("")) {
			 * searchList.add(new SearchProperty("test_batch_id", exambatchid,
			 * "=")); } if (examsequence_id != null &&
			 * !examsequence_id.equals("")) { searchList.add(new
			 * SearchProperty("examsequence_id", examsequence_id, "=")); }
			 */
			orderList.add(new OrderProperty("test_batch_id"));
			orderList.add(new OrderProperty("basicsequence_id"));
			orderList.add(new OrderProperty("open_course_id"));
			return testList.getCourses1(activeBatchId, page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getReExamCourseList1(String activeBatchId, Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			/*
			 * String id = request.getParameter("id"); String name =
			 * request.getParameter("name"); String minstartDate =
			 * request.getParameter("minstartDate"); String maxstartDate =
			 * request.getParameter("maxstartDate"); String minendDate =
			 * request.getParameter("minendDate"); String maxendDate =
			 * request.getParameter("maxendDate"); String batch_id =
			 * request.getParameter("batch_id"); String course_id =
			 * request.getParameter("course_id"); String examsequence_id =
			 * request.getParameter("examsequence_id"); String examcourseName =
			 * request.getParameter("examcourseName"); String exambatchid =
			 * request.getParameter("exambatchid");
			 * 
			 * if (id != null) searchList.add(new SearchProperty("id", id,
			 * "=")); if (name != null) searchList.add(new
			 * SearchProperty("name", name, "like")); if (minstartDate != null)
			 * searchList .add(new SearchProperty("s_date", minstartDate,
			 * ">=")); if (maxstartDate != null) searchList .add(new
			 * SearchProperty("s_date", maxstartDate, "<=")); if (minendDate !=
			 * null) searchList.add(new SearchProperty("e_date", minendDate,
			 * ">=")); if (maxendDate != null) searchList .add(new
			 * SearchProperty("e_date", maxstartDate, "<=")); if (batch_id !=
			 * null) searchList.add(new SearchProperty("test_batch_id",
			 * batch_id, "=")); if (course_id != null) searchList.add(new
			 * SearchProperty("course_id", course_id, "="));
			 * 
			 * if (examcourseName != null && !examcourseName.equals("")) {
			 * searchList.add(new SearchProperty("name", examcourseName,
			 * "like")); } if (exambatchid != null && !exambatchid.equals("")) {
			 * searchList.add(new SearchProperty("test_batch_id", exambatchid,
			 * "=")); } if (examsequence_id != null &&
			 * !examsequence_id.equals("")) { searchList.add(new
			 * SearchProperty("examsequence_id", examsequence_id, "=")); }
			 */
			orderList.add(new OrderProperty("test_batch_id"));
			orderList.add(new OrderProperty("basicsequence_id"));
			orderList.add(new OrderProperty("course_id"));
			return testList.getCourses1(activeBatchId, page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getSiteExamCourseList(String site_id, Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String minstartDate = request.getParameter("minstartDate");
			String maxstartDate = request.getParameter("maxstartDate");
			String minendDate = request.getParameter("minendDate");
			String maxendDate = request.getParameter("maxendDate");
			String batch_id = request.getParameter("batch_id");
			String course_id = request.getParameter("course_id");

			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");

			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (minstartDate != null)
				searchList.add(new SearchProperty("s_date", minstartDate, ">="));
			if (maxstartDate != null)
				searchList.add(new SearchProperty("s_date", maxstartDate, "<="));
			if (minendDate != null)
				searchList.add(new SearchProperty("e_date", minendDate, ">="));
			if (maxendDate != null)
				searchList.add(new SearchProperty("e_date", maxstartDate, "<="));
			if (batch_id != null)
				searchList.add(new SearchProperty("test_batch_id", batch_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));

			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			if (request.getParameter("examsequence_id") != null && !request.getParameter("examsequence_id").equals("")) {
				searchList.add(new SearchProperty("examsequence_id", request.getParameter("examsequence_id"), "="));
			}

			List orderList = new ArrayList();
			orderList.add(new OrderProperty("examsequence_id"));
			orderList.add(new OrderProperty("id"));
			return testList.getSiteCourses(site_id, page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getSiteExamCourseListNewGroup(String site_id, Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			/*
			 * String id = request.getParameter("id"); String name =
			 * request.getParameter("name"); String minstartDate =
			 * request.getParameter("minstartDate"); String maxstartDate =
			 * request.getParameter("maxstartDate"); String minendDate =
			 * request.getParameter("minendDate"); String maxendDate =
			 * request.getParameter("maxendDate"); String batch_id =
			 * request.getParameter("batch_id"); String course_id =
			 * request.getParameter("course_id");
			 * 
			 * String examcourseName = request.getParameter("examcourseName");
			 * String exambatchid = request.getParameter("exambatchid");
			 * 
			 * if (id != null) searchList.add(new SearchProperty("id", id,
			 * "=")); if (name != null) searchList.add(new
			 * SearchProperty("name", name, "like")); if (minstartDate != null)
			 * searchList .add(new SearchProperty("s_date", minstartDate,
			 * ">=")); if (maxstartDate != null) searchList .add(new
			 * SearchProperty("s_date", maxstartDate, "<=")); if (minendDate !=
			 * null) searchList.add(new SearchProperty("e_date", minendDate,
			 * ">=")); if (maxendDate != null) searchList .add(new
			 * SearchProperty("e_date", maxstartDate, "<=")); if (batch_id !=
			 * null) searchList.add(new SearchProperty("test_batch_id",
			 * batch_id, "=")); if (course_id != null) searchList.add(new
			 * SearchProperty("course_id", course_id, "="));
			 * 
			 * if (examcourseName != null && !examcourseName.equals("")) {
			 * searchList.add(new SearchProperty("name", examcourseName,
			 * "like")); } if (exambatchid != null && !exambatchid.equals("")) {
			 * searchList.add(new SearchProperty("test_batch_id", exambatchid,
			 * "=")); } if (request.getParameter("examsequence_id") != null &&
			 * !request.getParameter("examsequence_id").equals("")) {
			 * searchList.add(new SearchProperty("examsequence_id", request
			 * .getParameter("examsequence_id"), "=")); }
			 */
			List orderList = new ArrayList();
			// orderList.add(new OrderProperty("examsequence_id"));
			// orderList.add(new OrderProperty("id"));
			return testList.getSiteCoursesNewGroup(site_id, page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getSiteExamCourseListNewGroup(String activeBatchId, String site_id, Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			/*
			 * String id = request.getParameter("id"); String name =
			 * request.getParameter("name"); String minstartDate =
			 * request.getParameter("minstartDate"); String maxstartDate =
			 * request.getParameter("maxstartDate"); String minendDate =
			 * request.getParameter("minendDate"); String maxendDate =
			 * request.getParameter("maxendDate"); String batch_id =
			 * request.getParameter("batch_id"); String course_id =
			 * request.getParameter("course_id");
			 * 
			 * String examcourseName = request.getParameter("examcourseName");
			 * String exambatchid = request.getParameter("exambatchid");
			 * 
			 * if (id != null) searchList.add(new SearchProperty("id", id,
			 * "=")); if (name != null) searchList.add(new
			 * SearchProperty("name", name, "like")); if (minstartDate != null)
			 * searchList .add(new SearchProperty("s_date", minstartDate,
			 * ">=")); if (maxstartDate != null) searchList .add(new
			 * SearchProperty("s_date", maxstartDate, "<=")); if (minendDate !=
			 * null) searchList.add(new SearchProperty("e_date", minendDate,
			 * ">=")); if (maxendDate != null) searchList .add(new
			 * SearchProperty("e_date", maxstartDate, "<=")); if (batch_id !=
			 * null) searchList.add(new SearchProperty("test_batch_id",
			 * batch_id, "=")); if (course_id != null) searchList.add(new
			 * SearchProperty("course_id", course_id, "="));
			 * 
			 * if (examcourseName != null && !examcourseName.equals("")) {
			 * searchList.add(new SearchProperty("name", examcourseName,
			 * "like")); } if (exambatchid != null && !exambatchid.equals("")) {
			 * searchList.add(new SearchProperty("test_batch_id", exambatchid,
			 * "=")); } if (request.getParameter("examsequence_id") != null &&
			 * !request.getParameter("examsequence_id").equals("")) {
			 * searchList.add(new SearchProperty("examsequence_id", request
			 * .getParameter("examsequence_id"), "=")); }
			 */
			List orderList = new ArrayList();
			// orderList.add(new OrderProperty("examsequence_id"));
			// orderList.add(new OrderProperty("id"));
			return testList.getSiteCoursesNewGroup(activeBatchId, site_id, page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getSiteReExamCourseListNewGroup(String activeBatchId, String site_id, Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			/*
			 * String id = request.getParameter("id"); String name =
			 * request.getParameter("name"); String minstartDate =
			 * request.getParameter("minstartDate"); String maxstartDate =
			 * request.getParameter("maxstartDate"); String minendDate =
			 * request.getParameter("minendDate"); String maxendDate =
			 * request.getParameter("maxendDate"); String batch_id =
			 * request.getParameter("batch_id"); String course_id =
			 * request.getParameter("course_id");
			 * 
			 * String examcourseName = request.getParameter("examcourseName");
			 * String exambatchid = request.getParameter("exambatchid");
			 * 
			 * if (id != null) searchList.add(new SearchProperty("id", id,
			 * "=")); if (name != null) searchList.add(new
			 * SearchProperty("name", name, "like")); if (minstartDate != null)
			 * searchList .add(new SearchProperty("s_date", minstartDate,
			 * ">=")); if (maxstartDate != null) searchList .add(new
			 * SearchProperty("s_date", maxstartDate, "<=")); if (minendDate !=
			 * null) searchList.add(new SearchProperty("e_date", minendDate,
			 * ">=")); if (maxendDate != null) searchList .add(new
			 * SearchProperty("e_date", maxstartDate, "<=")); if (batch_id !=
			 * null) searchList.add(new SearchProperty("test_batch_id",
			 * batch_id, "=")); if (course_id != null) searchList.add(new
			 * SearchProperty("course_id", course_id, "="));
			 * 
			 * if (examcourseName != null && !examcourseName.equals("")) {
			 * searchList.add(new SearchProperty("name", examcourseName,
			 * "like")); } if (exambatchid != null && !exambatchid.equals("")) {
			 * searchList.add(new SearchProperty("test_batch_id", exambatchid,
			 * "=")); } if (request.getParameter("examsequence_id") != null &&
			 * !request.getParameter("examsequence_id").equals("")) {
			 * searchList.add(new SearchProperty("examsequence_id", request
			 * .getParameter("examsequence_id"), "=")); }
			 */
			List orderList = new ArrayList();
			// orderList.add(new OrderProperty("examsequence_id"));
			// orderList.add(new OrderProperty("id"));
			return testList.getSiteCoursesNewGroup(activeBatchId, site_id, page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	/**
	 * @author shu
	 * @return 通过批次，场次，课程号来查询该批次，该场次的考试的课程的相关信息。
	 * 
	 */
	public List getExamCoursesList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			if (request.getParameter("examcourseName") != null && !request.getParameter("examcourseName").equals("")) {
				searchList.add(new SearchProperty("course_name", request.getParameter("examcourseName"), "like"));
			}
			if (request.getParameter("exambatchid") != null && !request.getParameter("exambatchid").equals("")) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("exambatchid"), "="));
			}
			if (request.getParameter("course_id") != null) {
				searchList.add(new SearchProperty("course_id", request.getParameter("course_id"), "="));
			}
			if (request.getParameter("test_batch_id") != null) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("test_batch_id"), "="));
			}
			if (request.getParameter("examsequence_id") != null) {
				searchList.add(new SearchProperty("examsequence_id", request.getParameter("examsequence_id"), "="));
			}
			if (page == null)
				orderList.add(new OrderProperty("course_id"));
			else {
				orderList.add(new OrderProperty("test_batch_id"));
				orderList.add(new OrderProperty("examsequence_id"));
				orderList.add(new OrderProperty("course_id"));
			}
			return testList.getExamCourse(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getSiteExamCoursesList(String site_id, Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");
			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("course_name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			return testList.getSiteExamCourse(site_id, page, searchList, null);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public int addExamCourse(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamCourse == 1) {
			OracleExamCourse oracleExamCourse = new OracleExamCourse();
			oracleExamCourse.setName(request.getParameter("name"));
			oracleExamCourse.setStartDate(request.getParameter("startDate"));
			oracleExamCourse.setEndDate(request.getParameter("endDate"));
			oracleExamCourse.setExamBatch(new OracleExamBatch(request.getParameter("test_batch_id")));
			oracleExamCourse.setCourse_id(request.getParameter("course_id"));
			return oracleExamCourse.add();

		} else {
			throw new PlatformException("您没有添加考试课程的权限");
		}
	}

	public int updateExamCourse(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamCourse == 1) {
			OracleExamCourse oracleExamCourse = new OracleExamCourse(request.getParameter("id"));
			oracleExamCourse.setName(request.getParameter("name"));
			oracleExamCourse.setStartDate(request.getParameter("startDate"));
			oracleExamCourse.setEndDate(request.getParameter("endDate"));
			oracleExamCourse.setExamBatch(new OracleExamBatch(request.getParameter("test_batch_id")));
			oracleExamCourse.setCourse_id(request.getParameter("course_id"));
			return oracleExamCourse.update();

		} else {
			throw new PlatformException("您没有更新考试课程的权限");
		}
	}

	public int deleteExamCourse(String id) throws PlatformException {
		if (testPriv.deleteExamCourse == 1) {
			OracleExamCourse oracleExamCourse = new OracleExamCourse(id);
			return oracleExamCourse.delete();
		} else {
			throw new PlatformException("您没有删除考试课程的权限");
		}
	}

	public int getExamCourseNum(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			List searchList = new ArrayList();
			OracleExamList testList = new OracleExamList();

			if (request.getParameter("examcourseName") != null && !request.getParameter("examcourseName").equals("")) {
				searchList.add(new SearchProperty("name", request.getParameter("examcourseName"), "like"));
			}
			if (request.getParameter("exambatchid") != null && !request.getParameter("exambatchid").equals("")) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("exambatchid"), "="));
			}
			if (request.getParameter("course_id") != null) {
				searchList.add(new SearchProperty("course_id", request.getParameter("course_id"), "="));
			}
			if (request.getParameter("test_batch_id") != null) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("test_batch_id"), "="));
			}
			if (request.getParameter("examsequence_id") != null) {
				searchList.add(new SearchProperty("title", request.getParameter("examsequence_id"), "="));
			}
			return testList.getExamCourseNum(searchList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int getSiteExamCourseNum(String site_id, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			List searchList = new ArrayList();
			OracleExamList testList = new OracleExamList();

			if (request.getParameter("examcourseName") != null && !request.getParameter("examcourseName").equals("")) {
				searchList.add(new SearchProperty("name", request.getParameter("examcourseName"), "like"));
			}
			if (request.getParameter("exambatchid") != null && !request.getParameter("exambatchid").equals("")) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("exambatchid"), "="));
			}
			if (request.getParameter("course_id") != null) {
				searchList.add(new SearchProperty("course_id", request.getParameter("course_id"), "="));
			}
			if (request.getParameter("test_batch_id") != null) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("test_batch_id"), "="));
			}

			return testList.getSiteExamCourseNum(site_id, searchList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int getExamCoursesNum(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			List searchList = new ArrayList();
			OracleExamList testList = new OracleExamList();

			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");
			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("course_name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			if (request.getParameter("examsequence_id") != null) {
				searchList.add(new SearchProperty("examsequence_id", request.getParameter("examsequence_id"), "="));
			}
			return testList.getExamCourseNum(searchList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int getSiteExamCoursesNum(String site_id, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			List searchList = new ArrayList();
			OracleExamList testList = new OracleExamList();

			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");
			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("course_name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			if (request.getParameter("examsequence_id") != null) {
				searchList.add(new SearchProperty("examsequence_id", request.getParameter("examsequence_id"), "="));
			}
			return testList.getSiteExamCourseNum(site_id, searchList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 通过批次ID得到批次，相关信息。
	 */
	public ReExamBatch getReExamBatch(String id) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			return new OracleReExamBatch(id);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	/**
	 * @author shubeibei
	 * @return 得到批次的列表。
	 */
	public List getReExamBatchList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			if (request != null) {
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String minstartDate = request.getParameter("minstartDate");
				String maxstartDate = request.getParameter("maxstartDate");
				String minendDate = request.getParameter("minendDate");
				String maxendDate = request.getParameter("maxendDate");
				String status = request.getParameter("status");

				String search = request.getParameter("Search");

				if (id != null)
					searchList.add(new SearchProperty("id", id, "="));
				if (name != null)
					searchList.add(new SearchProperty("name", name, "like"));
				if (minstartDate != null)
					searchList.add(new SearchProperty("s_date", minstartDate, ">="));
				if (maxstartDate != null)
					searchList.add(new SearchProperty("s_date", maxstartDate, "<="));
				if (minendDate != null)
					searchList.add(new SearchProperty("e_date", minendDate, ">="));
				if (maxendDate != null)
					searchList.add(new SearchProperty("e_date", maxstartDate, "<="));
				if (status != null)
					searchList.add(new SearchProperty("status", status, "="));

				if (search != null) {
					searchList.add(new SearchProperty("name", search, "like"));
				}

			}
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id", "1"));
			return testList.getBatches(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int addReExamBatch(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamBatch == 1) {
			OracleReExamBatch oracleReExamBatch = new OracleReExamBatch();
			oracleReExamBatch.setId(request.getParameter("id"));
			oracleReExamBatch.setName(request.getParameter("name"));
			oracleReExamBatch.setStartDate(request.getParameter("startDate"));
			oracleReExamBatch.setEndDate(request.getParameter("endDate"));
			oracleReExamBatch.setExamRoomStartDate(request.getParameter("astartDate"));
			oracleReExamBatch.setExamRoomEndDate(request.getParameter("aendDate"));
			if (request.getParameter("status") == null) {
				oracleReExamBatch.setStatus("0");
			} else {
				oracleReExamBatch.setStatus(request.getParameter("status"));
			}
			return oracleReExamBatch.add();

		} else {
			throw new PlatformException("您没有添加考试批次的权限");
		}
	}

	public int updateReExamBatch(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamBatch == 1) {
			OracleReExamBatch oracleReExamBatch = new OracleReExamBatch(request.getParameter("id"));
			if (request.getParameter("name") != null) {
				oracleReExamBatch.setName(request.getParameter("name"));
			}
			if (request.getParameter("startDate") != null) {
				oracleReExamBatch.setStartDate(request.getParameter("startDate"));
			}
			if (request.getParameter("endDate") != null) {
				oracleReExamBatch.setEndDate(request.getParameter("endDate"));
			}
			if (request.getParameter("examRoomStartDate") != null) {
				oracleReExamBatch.setExamRoomStartDate(request.getParameter("examRoomStartDate"));
			}
			if (request.getParameter("examRoomEndDate") != null) {
				oracleReExamBatch.setExamRoomEndDate(request.getParameter("examRoomEndDate"));
			}
			if (request.getParameter("status") != null) {
				oracleReExamBatch.setStatus(request.getParameter("status"));
			}
			return oracleReExamBatch.update();

		} else {
			throw new PlatformException("您没有更新考试批次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 通过批次id来删除批次。
	 */
	public int deleteReExamBatch(String id) throws PlatformException {
		if (testPriv.deleteExamBatch == 1) {
			OracleReExamBatch oracleReExamBatch = new OracleReExamBatch(id);
			return oracleReExamBatch.delete();
		} else {
			throw new PlatformException("您没有删除考试批次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 得到批次的个数。
	 */
	public int getReExamBatchNum(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleReExamList testList = new OracleReExamList();
			if (request.getParameter("Search") == null) {
				return testList.getBatchNum(null);
			} else {
				List searchList = new ArrayList();
				searchList.add(new SearchProperty("name", request.getParameter("Search"), "like"));
				return testList.getBatchNum(searchList);
			}
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 得到批次中处于激活状态的个数。
	 */
	public int getReExamBatchNumStatus(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleReExamList testList = new OracleReExamList();
			/*
			 * if (request.getParameter("Search") == null) { return
			 * testList.getBatchNum(null); } else { List searchList = new
			 * ArrayList(); searchList.add(new SearchProperty("name", request
			 * .getParameter("Search"), "like")); return
			 * testList.getBatchNum(searchList); return
			 * testList.getBatchNumStatus(searchList); }
			 */// return testList.getBatchNum(searchList);
			return testList.getBatchNumStatus(null);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int updateReExamBatchStatus(String id, String status) throws PlatformException {
		if (testPriv.updateExamBatch == 1) {
			OracleReExamBatch oracleReExamBatch = new OracleReExamBatch(id);
			oracleReExamBatch.setStatus(status);
			return oracleReExamBatch.update();
		} else {
			throw new PlatformException("您没有更新考试批次的权限");
		}
	}

	public ReExamCourse getREExamCourse(String id) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			return new OracleReExamCourse(id);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getReExamCourseList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String minstartDate = request.getParameter("minstartDate");
			String maxstartDate = request.getParameter("maxstartDate");
			String minendDate = request.getParameter("minendDate");
			String maxendDate = request.getParameter("maxendDate");
			String batch_id = request.getParameter("batch_id");
			String course_id = request.getParameter("course_id");
			String examsequence_id = request.getParameter("examsequence_id");
			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");

			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (minstartDate != null)
				searchList.add(new SearchProperty("s_date", minstartDate, ">="));
			if (maxstartDate != null)
				searchList.add(new SearchProperty("s_date", maxstartDate, "<="));
			if (minendDate != null)
				searchList.add(new SearchProperty("e_date", minendDate, ">="));
			if (maxendDate != null)
				searchList.add(new SearchProperty("e_date", maxstartDate, "<="));
			if (batch_id != null)
				searchList.add(new SearchProperty("test_batch_id", batch_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));

			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			if (examsequence_id != null && !examsequence_id.equals("")) {
				searchList.add(new SearchProperty("examsequence_id", examsequence_id, "="));
			}
			orderList.add(new OrderProperty("test_batch_id"));
			orderList.add(new OrderProperty("examsequence_id"));
			orderList.add(new OrderProperty("course_id"));
			return testList.getCourses(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getSiteReExamCourseList(String site_id, Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String minstartDate = request.getParameter("minstartDate");
			String maxstartDate = request.getParameter("maxstartDate");
			String minendDate = request.getParameter("minendDate");
			String maxendDate = request.getParameter("maxendDate");
			String batch_id = request.getParameter("batch_id");
			String course_id = request.getParameter("course_id");

			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");

			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (minstartDate != null)
				searchList.add(new SearchProperty("s_date", minstartDate, ">="));
			if (maxstartDate != null)
				searchList.add(new SearchProperty("s_date", maxstartDate, "<="));
			if (minendDate != null)
				searchList.add(new SearchProperty("e_date", minendDate, ">="));
			if (maxendDate != null)
				searchList.add(new SearchProperty("e_date", maxstartDate, "<="));
			if (batch_id != null)
				searchList.add(new SearchProperty("test_batch_id", batch_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));

			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			if (request.getParameter("examsequence_id") != null && !request.getParameter("examsequence_id").equals("")) {
				searchList.add(new SearchProperty("examsequence_id", request.getParameter("examsequence_id"), "="));
			}

			List orderList = new ArrayList();
			orderList.add(new OrderProperty("examsequence_id"));
			orderList.add(new OrderProperty("id"));
			return testList.getSiteCourses(site_id, page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	/**
	 * @author shu
	 * @return 通过批次，场次，课程号来查询该批次，该场次的考试的课程的相关信息。
	 * 
	 */
	public List getReExamCoursesList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			if (request.getParameter("examcourseName") != null && !request.getParameter("examcourseName").equals("")) {
				searchList.add(new SearchProperty("course_name", request.getParameter("examcourseName"), "like"));
			}
			if (request.getParameter("exambatchid") != null && !request.getParameter("exambatchid").equals("")) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("exambatchid"), "="));
			}
			if (request.getParameter("course_id") != null) {
				searchList.add(new SearchProperty("course_id", request.getParameter("course_id"), "="));
			}
			if (request.getParameter("test_batch_id") != null) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("test_batch_id"), "="));
			}
			if (request.getParameter("examsequence_id") != null) {
				searchList.add(new SearchProperty("examsequence_id", request.getParameter("examsequence_id"), "="));
			}
			if (page == null)
				orderList.add(new OrderProperty("course_id"));
			else {
				orderList.add(new OrderProperty("test_batch_id"));
				orderList.add(new OrderProperty("examsequence_id"));
				orderList.add(new OrderProperty("course_id"));
			}
			return testList.getExamCourse(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public List getSiteReExamCoursesList(String site_id, Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");
			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("course_name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			return testList.getSiteExamCourse(site_id, page, searchList, null);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public int addReExamCourse(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamCourse == 1) {
			OracleReExamCourse oracleExamCourse = new OracleReExamCourse();
			oracleExamCourse.setName(request.getParameter("name"));
			oracleExamCourse.setStartDate(request.getParameter("startDate"));
			oracleExamCourse.setEndDate(request.getParameter("endDate"));
			oracleExamCourse.setExamBatch(new OracleReExamBatch(request.getParameter("test_batch_id")));
			oracleExamCourse.setCourse_id(request.getParameter("course_id"));
			return oracleExamCourse.add();

		} else {
			throw new PlatformException("您没有添加考试课程的权限");
		}
	}

	public int updateReExamCourse(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamCourse == 1) {
			OracleReExamCourse oracleExamCourse = new OracleReExamCourse(request.getParameter("id"));
			oracleExamCourse.setName(request.getParameter("name"));
			oracleExamCourse.setStartDate(request.getParameter("startDate"));
			oracleExamCourse.setEndDate(request.getParameter("endDate"));
			oracleExamCourse.setExamBatch(new OracleReExamBatch(request.getParameter("test_batch_id")));
			oracleExamCourse.setCourse_id(request.getParameter("course_id"));
			return oracleExamCourse.update();

		} else {
			throw new PlatformException("您没有更新考试课程的权限");
		}
	}

	public int deleteReExamCourse(String id) throws PlatformException {
		if (testPriv.deleteExamCourse == 1) {
			OracleReExamCourse oracleExamCourse = new OracleReExamCourse(id);
			return oracleExamCourse.delete();
		} else {
			throw new PlatformException("您没有删除考试课程的权限");
		}
	}

	public int getReExamCourseNum(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			List searchList = new ArrayList();
			OracleReExamList testList = new OracleReExamList();

			if (request.getParameter("examcourseName") != null && !request.getParameter("examcourseName").equals("")) {
				searchList.add(new SearchProperty("name", request.getParameter("examcourseName"), "like"));
			}
			if (request.getParameter("exambatchid") != null && !request.getParameter("exambatchid").equals("")) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("exambatchid"), "="));
			}
			if (request.getParameter("course_id") != null) {
				searchList.add(new SearchProperty("course_id", request.getParameter("course_id"), "="));
			}
			if (request.getParameter("test_batch_id") != null) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("test_batch_id"), "="));
			}
			if (request.getParameter("examsequence_id") != null) {
				searchList.add(new SearchProperty("title", request.getParameter("examsequence_id"), "="));
			}
			return testList.getExamCourseNum(searchList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int getSiteReExamCourseNum(String site_id, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			List searchList = new ArrayList();
			OracleReExamList testList = new OracleReExamList();

			if (request.getParameter("examcourseName") != null && !request.getParameter("examcourseName").equals("")) {
				searchList.add(new SearchProperty("name", request.getParameter("examcourseName"), "like"));
			}
			if (request.getParameter("exambatchid") != null && !request.getParameter("exambatchid").equals("")) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("exambatchid"), "="));
			}
			if (request.getParameter("course_id") != null) {
				searchList.add(new SearchProperty("course_id", request.getParameter("course_id"), "="));
			}
			if (request.getParameter("test_batch_id") != null) {
				searchList.add(new SearchProperty("test_batch_id", request.getParameter("test_batch_id"), "="));
			}

			return testList.getSiteExamCourseNum(site_id, searchList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int getReExamCoursesNum(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			List searchList = new ArrayList();
			OracleReExamList testList = new OracleReExamList();

			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");
			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("course_name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			if (request.getParameter("examsequence_id") != null) {
				searchList.add(new SearchProperty("examsequence_id", request.getParameter("examsequence_id"), "="));
			}
			return testList.getExamCourseNum(searchList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int getSiteReExamCoursesNum(String site_id, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			List searchList = new ArrayList();
			OracleReExamList testList = new OracleReExamList();

			String examcourseName = request.getParameter("examcourseName");
			String exambatchid = request.getParameter("exambatchid");
			if (examcourseName != null && !examcourseName.equals("")) {
				searchList.add(new SearchProperty("course_name", examcourseName, "like"));
			}
			if (exambatchid != null && !exambatchid.equals("")) {
				searchList.add(new SearchProperty("test_batch_id", exambatchid, "="));
			}
			if (request.getParameter("examsequence_id") != null) {
				searchList.add(new SearchProperty("examsequence_id", request.getParameter("examsequence_id"), "="));
			}
			return testList.getSiteExamCourseNum(site_id, searchList);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public ReExamUser getReExamUser(String id) throws PlatformException {
		if (testPriv.getExamUser == 1) {
			return new OracleReExamUser(id);
		} else {
			throw new PlatformException("您没有查看考生的权限");
		}
	}

	public int addReExamRoom(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamRoom == 1) {
			OracleReExamRoom oracleExamRoom = new OracleReExamRoom();
			oracleExamRoom.setName(request.getParameter("name"));
			oracleExamRoom.setAddress(request.getParameter("address"));
			oracleExamRoom.setRoomNo(request.getParameter("room_no"));
			oracleExamRoom.setExamCourse(new OracleReExamCourse(request.getParameter("course_id")));
			return oracleExamRoom.add();

		} else {
			throw new PlatformException("您没有添加考试教室的权限");
		}
	}

	public int deleteReExamSequence(String id) throws PlatformException {
		if (testPriv.deleteExamSequence == 1) {
			OracleReExamSequence oracleExamSeq = new OracleReExamSequence(id);
			return oracleExamSeq.delete();
		} else {
			throw new PlatformException("您没有删除考试场次的权限");
		}
	}

	public int updaterReExamRoom(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamRoom == 1) {
			OracleReExamRoom oracleExamRoom = new OracleReExamRoom(request.getParameter("id"));
			oracleExamRoom.setName(request.getParameter("name"));
			oracleExamRoom.setAddress(request.getParameter("address"));
			oracleExamRoom.setRoomNo(request.getParameter("room_no"));
			oracleExamRoom.setExamCourse(new OracleReExamCourse(request.getParameter("course_id")));
			return oracleExamRoom.update();

		} else {
			throw new PlatformException("您没有更新考试教室的权限");
		}
	}

	public List getReExamRoomList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamRoom == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String room_no = request.getParameter("room_no");
			String course_id = request.getParameter("course_id");

			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (address != null)
				searchList.add(new SearchProperty("address", address, "like"));
			if (room_no != null)
				searchList.add(new SearchProperty("room_no", room_no, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));

			return testList.getRooms(page, searchList, null);
		} else {
			throw new PlatformException("您没有查看考试教室的权限");
		}
	}

	public List getReExamUserList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamUser == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			String id = request.getParameter("id");
			String batch_id = request.getParameter("batch_id");
			String user_id = request.getParameter("user_id");
			String examcode = request.getParameter("examcode");
			String note = request.getParameter("note");
			String status = request.getParameter("status");

			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (batch_id != null)
				searchList.add(new SearchProperty("batch_id", batch_id, "="));
			if (user_id != null)
				searchList.add(new SearchProperty("user_id", user_id, "="));
			if (examcode != null)
				searchList.add(new SearchProperty("examcode", examcode, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));

			return testList.getExamUsers(page, searchList, null);
		} else {
			throw new PlatformException("您没有查看考生的权限");
		}
	}

	/**
	 * @author shu
	 * @return 添加考试场次（时间，批次，场次）。
	 */
	public int addReExamSequence(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamSequence == 1) {
			OracleReExamSequence oracleExamSeq = new OracleReExamSequence();
			String title = request.getParameter("basicsequence_id");
			String basicSequenceId = title.substring(title.indexOf("~~~~") + 4);
			title = title.substring(0, title.indexOf("~~~~"));
			oracleExamSeq.setTitle(title);
			oracleExamSeq.setStartDate(request.getParameter("startDate"));
			oracleExamSeq.setEndDate(request.getParameter("endDate"));
			oracleExamSeq.setNote(request.getParameter("note"));
			oracleExamSeq.setBatchId(request.getParameter("batchId"));
			oracleExamSeq.setBasicSequenceId(basicSequenceId);
			// return oracleExamSeq.add();
			return oracleExamSeq.addWithBatch();

		} else {
			throw new PlatformException("您没有添加考试场次的权限");
		}
	}

	public int deleteReExamUser(String id) throws PlatformException {
		if (testPriv.deleteExamUser == 1) {
			OracleReExamUser oracleExamUser = new OracleReExamUser(id);
			return oracleExamUser.delete();
		} else {
			throw new PlatformException("您没有删除考生的权限");
		}
	}

	public int getReExamSequenceNum(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamSequence == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			if (request != null && !request.equals("")) {
				String search = request.getParameter("Search");
				if (search != null && !search.equals("")) {
					searchList.add(new SearchProperty("title", search, "like"));
				}
				String testbatch_id = request.getParameter("exambatchid");
				if (testbatch_id != null && !testbatch_id.equals("")) {
					searchList.add(new SearchProperty("testbatch_id", testbatch_id, "="));
				}
			}
			return testList.getExamSequencesNum(searchList);
		} else {
			throw new PlatformException("您没有查看考试场次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 修改考试场次批次时间。
	 */
	public int updateReExamSequence(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamSequence == 1) {
			OracleReExamSequence oracleExamSeq = new OracleReExamSequence(request.getParameter("id"));
			String title = request.getParameter("basicsequence_id");
			String basicSequenceId = title.substring(title.indexOf("~~~~") + 4);
			title = title.substring(0, title.indexOf("~~~~"));
			if (title != null) {
				oracleExamSeq.setTitle(title);
			}
			if (request.getParameter("startDate") != null) {
				oracleExamSeq.setStartDate(request.getParameter("startDate"));
			}
			if (request.getParameter("endDate") != null) {
				oracleExamSeq.setEndDate(request.getParameter("endDate"));
			}
			if (request.getParameter("note") != null) {
				oracleExamSeq.setNote(request.getParameter("note"));
			}
			if (request.getParameter("batchId") != null) {
				oracleExamSeq.setBatchId(request.getParameter("batchId"));
			}
			if (title != null) {
				oracleExamSeq.setBasicSequenceId(basicSequenceId);
			}
			return oracleExamSeq.update();

		} else {
			throw new PlatformException("您没有更新考试场次的权限");
		}
	}

	public int updateReExamUser(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamUser == 1) {
			OracleReExamUser oracleExamUser = new OracleReExamUser(request.getParameter("id"));
			oracleExamUser.setExamBatch(new OracleReExamBatch(request.getParameter("batch_id")));
			oracleExamUser.setTestUser(new OracleTestUser(request.getParameter("user_id")));
			oracleExamUser.setExamcode(request.getParameter("examcode"));
			oracleExamUser.setNote(request.getParameter("note"));
			oracleExamUser.setStatus(request.getParameter("status"));
			return oracleExamUser.update();

		} else {
			throw new PlatformException("您没有更新考生的权限");
		}
	}

	public List getReExamSequenceList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamSequence == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			if (request != null && !request.equals("")) {
				String search = request.getParameter("Search");
				String testbatch_id = request.getParameter("exambatchid");
				if (search != null && !search.equals("")) {
					searchList.add(new SearchProperty("title", search, "like"));
				}
				if (testbatch_id != null && !testbatch_id.equals("")) {
					searchList.add(new SearchProperty("testbatch_id", testbatch_id, "="));
				}
			}
			orderList.add(new OrderProperty("basicsequence_id", "0"));
			return testList.getExamSequences(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试场次的权限");
		}
	}

	public List getReExamBatchList() throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleReExamList testList = new OracleReExamList();
			return testList.getBatches(null, null, null);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public ReExamRoom getReExamRoom(String id) throws PlatformException {
		if (testPriv.getExamRoom == 1) {
			return new OracleReExamRoom(id);
		} else {
			throw new PlatformException("您没有查看考试教室的权限");
		}
	}

	public int deleteReExamRoom(String id) throws PlatformException {
		if (testPriv.deleteExamRoom == 1) {
			OracleReExamRoom oracleExamRoom = new OracleReExamRoom(id);
			return oracleExamRoom.delete();
		} else {
			throw new PlatformException("您没有删除考试教室的权限");
		}
	}

	public int updateReExamScore(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamScore == 1) {
			OracleReExamScore oracleExamScore = new OracleReExamScore(new OracleReExamUser(request.getParameter("user_id")), new OracleReExamCourse(request.getParameter("course_id")));
			oracleExamScore.setScore(request.getParameter("score"));
			return oracleExamScore.update();
		} else {
			throw new PlatformException("您没有更新考生成绩的权限");
		}
	}

	public int updateReExamRoom(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamRoom == 1) {
			OracleReExamRoom oracleExamRoom = new OracleReExamRoom(request.getParameter("id"));
			oracleExamRoom.setName(request.getParameter("name"));
			oracleExamRoom.setAddress(request.getParameter("address"));
			oracleExamRoom.setRoomNo(request.getParameter("room_no"));
			oracleExamRoom.setExamCourse(new OracleReExamCourse(request.getParameter("course_id")));
			return oracleExamRoom.update();

		} else {
			throw new PlatformException("您没有更新考试教室的权限");
		}
	}

	public int updateExamRoomPlace(String[] ids, String[] places) throws PlatformException {
		if (testPriv.updateExamRoom == 1) {
			OracleExamRoom oracleExamRoom = new OracleExamRoom();
			return oracleExamRoom.updatePlace(ids, places);

		} else {
			throw new PlatformException("您没有更新考试教室的权限");
		}
	}

	public List getExamDesks(String room_id) throws PlatformException, PlatformException {
		return new OracleExamActivity().getExamroomDisplyInfo(room_id);
	}

	/**
	 * @author shu
	 * @return 根据room_no来得到考场人员信息。
	 */
	public List getExamDesks3(String room_no) throws PlatformException, PlatformException {
		return new OracleExamActivity().getExamroomDisplyInfo3(room_no);
	}

	public List getExamDesks2(String room_id) throws PlatformException, PlatformException {
		return new OracleExamActivity().getExamroomDisplyInfo2(room_id);
	}

	public List getExamDesks4(String room_no) throws PlatformException, PlatformException {
		return new OracleExamActivity().getExamroomDisplyInfo4(room_no);
	}

	public List getReExamDesks(String room_id) throws PlatformException, PlatformException {
		return new OracleReExamActivity().getExamroomDisplyInfo(room_id);
	}

	public List getReExamDesks3(String room_no) throws PlatformException, PlatformException {
		return new OracleReExamActivity().getExamroomDisplyInfo3(room_no);
	}

	public List getReExamDesks2(String room_id) throws PlatformException, PlatformException {
		return new OracleReExamActivity().getExamroomDisplyInfo2(room_id);
	}

	public List getReExamDesks4(String room_no) throws PlatformException, PlatformException {
		return new OracleReExamActivity().getExamroomDisplyInfo4(room_no);
	}

	public int updateReExamRoomPlace(String[] ids, String[] places) throws PlatformException {
		if (testPriv.updateExamRoom == 1) {
			OracleReExamRoom oracleExamRoom = new OracleReExamRoom();
			return oracleExamRoom.updatePlace(ids, places);

		} else {
			throw new PlatformException("您没有更新考试教室的权限");
		}
	}

	public ReExamCourse getReExamCourse(String id) throws PlatformException {
		if (testPriv.getExamCourse == 1) {
			return new OracleReExamCourse(id);
		} else {
			throw new PlatformException("您没有查看考试课程的权限");
		}
	}

	public ReExamSequence getReExamSequence(String id) throws PlatformException {
		if (testPriv.getExamSequence == 1) {
			return new OracleReExamSequence(id);
		} else {
			throw new PlatformException("您没有查看考试场次的权限");
		}
	}

	public int addReExamUser(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamUser == 1) {
			OracleReExamUser oracleExamUser = new OracleReExamUser();
			oracleExamUser.setExamBatch(new OracleReExamBatch(request.getParameter("batch_id")));
			oracleExamUser.setTestUser(new OracleTestUser(request.getParameter("user_id")));
			oracleExamUser.setExamcode(request.getParameter("examcode"));
			oracleExamUser.setNote(request.getParameter("note"));
			oracleExamUser.setStatus(request.getParameter("status"));
			return oracleExamUser.add();

		} else {
			throw new PlatformException("您没有添加考生的权限");
		}
	}

	public ExamRoom getExamRoom(String id) throws PlatformException {
		if (testPriv.getExamRoom == 1) {
			return new OracleExamRoom(id);
		} else {
			throw new PlatformException("您没有查看考试教室的权限");
		}
	}

	public List getExamRoomList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamRoom == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String room_no = request.getParameter("room_no");
			String course_id = request.getParameter("course_id");

			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (name != null)
				searchList.add(new SearchProperty("name", name, "like"));
			if (address != null)
				searchList.add(new SearchProperty("address", address, "like"));
			if (room_no != null)
				searchList.add(new SearchProperty("room_no", room_no, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));

			return testList.getRooms(page, searchList, null);
		} else {
			throw new PlatformException("您没有查看考试教室的权限");
		}
	}

	public int addExamRoom(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamRoom == 1) {
			OracleExamRoom oracleExamRoom = new OracleExamRoom();
			oracleExamRoom.setName(request.getParameter("name"));
			oracleExamRoom.setAddress(request.getParameter("address"));
			oracleExamRoom.setRoomNo(request.getParameter("room_no"));
			oracleExamRoom.setExamCourse(new OracleExamCourse(request.getParameter("course_id")));
			return oracleExamRoom.add();

		} else {
			throw new PlatformException("您没有添加考试教室的权限");
		}
	}

	public int updateExamRoom(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamRoom == 1) {
			OracleExamRoom oracleExamRoom = new OracleExamRoom(request.getParameter("id"));
			oracleExamRoom.setName(request.getParameter("name"));
			oracleExamRoom.setAddress(request.getParameter("address"));
			oracleExamRoom.setRoomNo(request.getParameter("room_no"));
			oracleExamRoom.setExamCourse(new OracleExamCourse(request.getParameter("course_id")));
			return oracleExamRoom.update();

		} else {
			throw new PlatformException("您没有更新考试教室的权限");
		}
	}

	public int connectExamRoom(String[] ids) throws PlatformException {
		if (testPriv.updateExamRoom == 1) {
			int suc = new OracleExamActivity().connectExamRoom(ids);
			return suc;

		} else {
			throw new PlatformException("您没有更新考试教室的权限");
		}

	}

	public int connectReExamRoom(String[] ids) throws PlatformException {
		if (testPriv.updateExamRoom == 1) {
			int suc = new OracleReExamActivity().connectExamRoom(ids);
			return suc;

		} else {
			throw new PlatformException("您没有更新考试教室的权限");
		}

	}

	public int deleteExamRoom(String id) throws PlatformException {
		if (testPriv.deleteExamRoom == 1) {
			OracleExamRoom oracleExamRoom = new OracleExamRoom(id);
			return oracleExamRoom.delete();
		} else {
			throw new PlatformException("您没有删除考试教室的权限");
		}
	}

	public ExamUser getExamUser(String id) throws PlatformException {
		if (testPriv.getExamUser == 1) {
			return new OracleExamUser(id);
		} else {
			throw new PlatformException("您没有查看考生的权限");
		}
	}

	public List getExamUserList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamUser == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			String id = request.getParameter("id");
			String batch_id = request.getParameter("batch_id");
			String user_id = request.getParameter("user_id");
			String examcode = request.getParameter("examcode");
			String note = request.getParameter("note");
			String status = request.getParameter("status");

			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (batch_id != null)
				searchList.add(new SearchProperty("batch_id", batch_id, "="));
			if (user_id != null)
				searchList.add(new SearchProperty("user_id", user_id, "="));
			if (examcode != null)
				searchList.add(new SearchProperty("examcode", examcode, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));

			return testList.getExamUsers(page, searchList, null);
		} else {
			throw new PlatformException("您没有查看考生的权限");
		}
	}

	public List getExamUserListNewGroup(String course_id, String kaoqu_id, String edu_type_id, String major_id, String grade_id, String shenfen_id) throws PlatformException {
		if (testPriv.getExamUser == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("kaoqu_id", kaoqu_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("edu_type_id", edu_type_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("major_id", major_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("grade_id", grade_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("shenfen_id", shenfen_id, "="));

			// String id = request.getParameter("id");
			// String batch_id = request.getParameter("batch_id");
			// String user_id = request.getParameter("user_id");
			// String examcode = request.getParameter("examcode");
			// String note = request.getParameter("note");
			// String status = request.getParameter("status");
			/*
			 * if (id != null) searchList.add(new SearchProperty("id", id,
			 * "=")); if (batch_id != null) searchList.add(new
			 * SearchProperty("batch_id", batch_id, "=")); if (user_id != null)
			 * searchList.add(new SearchProperty("user_id", user_id, "=")); if
			 * (examcode != null) searchList.add(new SearchProperty("examcode",
			 * examcode, "=")); if (note != null) searchList.add(new
			 * SearchProperty("note", note, "=")); if (status != null)
			 * searchList.add(new SearchProperty("status", status, "="));
			 */
			return testList.getExamUsersNewGroup(course_id, kaoqu_id, edu_type_id, major_id, grade_id, shenfen_id, searchList, null);
		} else {
			throw new PlatformException("您没有查看考生的权限");
		}
	}

	/**
	 * @author shu
	 * @param activeBatchId
	 * @param course_id
	 * @param kaoqu_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @param shenfen_id
	 * @return 得到该条件下的考试的人员列表。
	 * @throws PlatformException
	 */
	public List getExamUserListNewGroup(String activeBatchId, String course_id, String kaoqu_id, String edu_type_id, String major_id, String grade_id, String shenfen_id) throws PlatformException {
		if (testPriv.getExamUser == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("kaoqu_id", kaoqu_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("edu_type_id", edu_type_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("major_id", major_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("grade_id", grade_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("shenfen_id", shenfen_id, "="));

			// String id = request.getParameter("id");
			// String batch_id = request.getParameter("batch_id");
			// String user_id = request.getParameter("user_id");
			// String examcode = request.getParameter("examcode");
			// String note = request.getParameter("note");
			// String status = request.getParameter("status");
			/*
			 * if (id != null) searchList.add(new SearchProperty("id", id,
			 * "=")); if (batch_id != null) searchList.add(new
			 * SearchProperty("batch_id", batch_id, "=")); if (user_id != null)
			 * searchList.add(new SearchProperty("user_id", user_id, "=")); if
			 * (examcode != null) searchList.add(new SearchProperty("examcode",
			 * examcode, "=")); if (note != null) searchList.add(new
			 * SearchProperty("note", note, "=")); if (status != null)
			 * searchList.add(new SearchProperty("status", status, "="));
			 */
			// return
			// testList.getExamUsersNewGroup(course_id,kaoqu_id,edu_type_id,major_id,
			// grade_id,shenfen_id, searchList, null);
			return testList.getExamUsersNewGroup(activeBatchId, course_id, kaoqu_id, edu_type_id, major_id, grade_id, shenfen_id, searchList, null);
		} else {
			throw new PlatformException("您没有查看考生的权限");
		}
	}

	/**
	 * @author shu
	 * @param activeBatchId
	 * @param course_id
	 * @param kaoqu_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @param shenfen_id
	 * @return 得到该条件下的考试的人员列表。
	 * @throws PlatformException
	 */
	public List getReExamUserListNewGroup(String activeBatchId, String course_id, String kaoqu_id, String edu_type_id, String major_id, String grade_id, String shenfen_id) throws PlatformException {
		if (testPriv.getExamUser == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("kaoqu_id", kaoqu_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("edu_type_id", edu_type_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("major_id", major_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("grade_id", grade_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("shenfen_id", shenfen_id, "="));

			// String id = request.getParameter("id");
			// String batch_id = request.getParameter("batch_id");
			// String user_id = request.getParameter("user_id");
			// String examcode = request.getParameter("examcode");
			// String note = request.getParameter("note");
			// String status = request.getParameter("status");
			/*
			 * if (id != null) searchList.add(new SearchProperty("id", id,
			 * "=")); if (batch_id != null) searchList.add(new
			 * SearchProperty("batch_id", batch_id, "=")); if (user_id != null)
			 * searchList.add(new SearchProperty("user_id", user_id, "=")); if
			 * (examcode != null) searchList.add(new SearchProperty("examcode",
			 * examcode, "=")); if (note != null) searchList.add(new
			 * SearchProperty("note", note, "=")); if (status != null)
			 * searchList.add(new SearchProperty("status", status, "="));
			 */
			// return
			// testList.getExamUsersNewGroup(course_id,kaoqu_id,edu_type_id,major_id,
			// grade_id,shenfen_id, searchList, null);
			return testList.getExamUsersNewGroup(activeBatchId, course_id, kaoqu_id, edu_type_id, major_id, grade_id, shenfen_id, searchList, null);
		} else {
			throw new PlatformException("您没有查看考生的权限");
		}
	}

	/**
	 * @author shu
	 * @param activeBatchId
	 * @param course_id
	 * @param kaoqu_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @param shenfen_id
	 * @return 得到该条件下的考试的人员列表。
	 * @throws PlatformException
	 */
	public List getExamUserListNewGroup1(String activeBatchId, String course_id, String kaoqu_id, String edu_type_id, String major_id, String grade_id, String shenfen_id) throws PlatformException {
		if (testPriv.getExamUser == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			if (course_id != null)
				searchList.add(new SearchProperty("course_id", course_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("kaoqu_id", kaoqu_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("edu_type_id", edu_type_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("major_id", major_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("grade_id", grade_id, "="));
			if (course_id != null)
				searchList.add(new SearchProperty("shenfen_id", shenfen_id, "="));

			// String id = request.getParameter("id");
			// String batch_id = request.getParameter("batch_id");
			// String user_id = request.getParameter("user_id");
			// String examcode = request.getParameter("examcode");
			// String note = request.getParameter("note");
			// String status = request.getParameter("status");
			/*
			 * if (id != null) searchList.add(new SearchProperty("id", id,
			 * "=")); if (batch_id != null) searchList.add(new
			 * SearchProperty("batch_id", batch_id, "=")); if (user_id != null)
			 * searchList.add(new SearchProperty("user_id", user_id, "=")); if
			 * (examcode != null) searchList.add(new SearchProperty("examcode",
			 * examcode, "=")); if (note != null) searchList.add(new
			 * SearchProperty("note", note, "=")); if (status != null)
			 * searchList.add(new SearchProperty("status", status, "="));
			 */
			// return
			// testList.getExamUsersNewGroup(course_id,kaoqu_id,edu_type_id,major_id,
			// grade_id,shenfen_id, searchList, null);
			return testList.getExamUsersNewGroup(activeBatchId, course_id, kaoqu_id, edu_type_id, major_id, grade_id, shenfen_id, searchList, null);
		} else {
			throw new PlatformException("您没有查看考生的权限");
		}
	}

	public int addExamUser(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamUser == 1) {
			OracleExamUser oracleExamUser = new OracleExamUser();
			oracleExamUser.setExamBatch(new OracleExamBatch(request.getParameter("batch_id")));
			oracleExamUser.setTestUser(new OracleTestUser(request.getParameter("user_id")));
			oracleExamUser.setExamcode(request.getParameter("examcode"));
			oracleExamUser.setNote(request.getParameter("note"));
			oracleExamUser.setStatus(request.getParameter("status"));
			return oracleExamUser.add();

		} else {
			throw new PlatformException("您没有添加考生的权限");
		}
	}

	public int updateExamUser(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamUser == 1) {
			OracleExamUser oracleExamUser = new OracleExamUser(request.getParameter("id"));
			oracleExamUser.setExamBatch(new OracleExamBatch(request.getParameter("batch_id")));
			oracleExamUser.setTestUser(new OracleTestUser(request.getParameter("user_id")));
			oracleExamUser.setExamcode(request.getParameter("examcode"));
			oracleExamUser.setNote(request.getParameter("note"));
			oracleExamUser.setStatus(request.getParameter("status"));
			return oracleExamUser.update();

		} else {
			throw new PlatformException("您没有更新考生的权限");
		}
	}

	public int deleteExamUser(String id) throws PlatformException {
		if (testPriv.deleteExamUser == 1) {
			OracleExamUser oracleExamUser = new OracleExamUser(id);
			return oracleExamUser.delete();
		} else {
			throw new PlatformException("您没有删除考生的权限");
		}
	}

	public int updateExamScore(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamScore == 1) {
			OracleExamScore oracleExamScore = new OracleExamScore(new OracleExamUser(request.getParameter("user_id")), new OracleExamCourse(request.getParameter("course_id")));
			oracleExamScore.setScore(request.getParameter("score"));
			return oracleExamScore.update();
		} else {
			throw new PlatformException("您没有更新考生成绩的权限");
		}
	}

	public void courseAddUser(List examCourseList, List examUserList) throws PlatformException {
		OracleExamActivity oracleExamActivity = new OracleExamActivity();
		oracleExamActivity.courseAddUser(examCourseList, examUserList);
	}

	public void courseRemoveUser(List examCourseList, List examUserList) throws PlatformException {
		OracleExamActivity oracleExamActivity = new OracleExamActivity();
		oracleExamActivity.courseRemoveUser(examCourseList, examUserList);
	}

	/*
	 * public List getExamUserListForExamCourse(HttpServletRequest request, Page
	 * page) throws PlatformException { if(testPriv.getExamUser==1){
	 * OracleExamList testList = new OracleExamList(); List searchList = new
	 * ArrayList(); String site_id=request.getParameter("site_id"); String
	 * major_id=request.getParameter("major_id"); String
	 * edu_type_id=request.getParameter("edu_type_id"); String
	 * grade_id=request.getParameter("grade_id"); String
	 * cardno=request.getParameter("cardno"); String
	 * name=request.getParameter("name"); String
	 * batch_id=request.getParameter("batch_id");
	 * 
	 * if (site_id != null) searchList.add(new SearchProperty("site_id",
	 * site_id, "=")); if (major_id != null) searchList.add(new
	 * SearchProperty("major_id", major_id, "=")); if (edu_type_id != null)
	 * searchList.add(new SearchProperty("edutype_id", edu_type_id, "=")); if
	 * (grade_id != null) searchList.add(new SearchProperty("grade_id",
	 * grade_id, "=")); if (cardno != null) searchList.add(new
	 * SearchProperty("reg_no", cardno, "like")); if (name != null)
	 * searchList.add(new SearchProperty("name", name, "like")); if (batch_id !=
	 * null) searchList.add(new SearchProperty("batch_id", batch_id, "="));
	 * return testList.getExamUsers(page, searchList, null); } else { throw new
	 * PlatformException("您没有查看考生的权限"); } }
	 */

	public int changeHomeworkPaperStatus(String id, String status) throws NoRightException, PlatformException {
		if (testPriv.changeHomeworkPaperStatus == 1) {
			OracleHomeworkPaper homeworkPaper = new OracleHomeworkPaper();
			homeworkPaper.setId(id);
			homeworkPaper.setStatus(status);
			return homeworkPaper.reverseActive();
		} else {
			throw new NoRightException("您没有修改作业状态的权限");
		}
	}

	public List getActiveHomeworkPapers(Page page, String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", "1", "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getActiveHomeworkPapers(page, searchList, null);

		} else {
			throw new NoRightException("您没有查看作业列表的权限");
		}
	}

	public List getActiveHomeworkPapers(Page page, String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId, String batch_id) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", "1", "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getActiveHomeworkPapers(page, searchList, null, batch_id);

		} else {
			throw new NoRightException("您没有查看作业列表的权限");
		}
	}

	public boolean getHomeworkPaperExpired(String paperId) throws NoRightException, PlatformException {
		OracleTestList testList = new OracleTestList();
		return testList.getHomeworkPaperExpired(paperId);
	}

	public int getActiveHomeworkPapersNum(String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", "1", "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getActiveHomeworkPapersNum(searchList);
		} else {
			throw new NoRightException("您没有查看作业列表的权限");
		}
	}

	public int getActiveHomeworkPapersNum(String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId, String batch_id) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", "1", "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getActiveHomeworkPapersNum(searchList, batch_id);
		} else {
			throw new NoRightException("您没有查看作业列表的权限");
		}
	}

	public List getExamBatchList() throws PlatformException {
		if (testPriv.getExamBatch == 1) {
			OracleExamList testList = new OracleExamList();
			return testList.getBatches(null, null, null);
		} else {
			throw new PlatformException("您没有查看考试批次的权限");
		}
	}

	public int deleteStuPaperQuestions(String paperId, String userId) throws NoRightException, PlatformException {
		if (testPriv.deletePaperQuestions == 1) {
			OraclePaperQuestion question = new OraclePaperQuestion();
			question.setTestPaperId(paperId);
			question.setUserId(userId);
			return question.removeQuestion();
		} else {
			throw new PlatformException("您没有删除题目的权限");
		}
	}

	public int deletePaperQuestions(String paperId) throws NoRightException, PlatformException {
		if (testPriv.deletePaperQuestions == 1) {
			OraclePaper paper = new OraclePaper();
			paper.setId(paperId);
			return paper.removePaperQuestions();
		} else {
			throw new PlatformException("您没有删除试题的权限");
		}
	}

	public int deletePaperQuestions(String paperId, String questionIds) throws NoRightException, PlatformException {
		if (testPriv.deletePaperQuestions == 1) {
			OraclePaper paper = new OraclePaper();
			paper.setId(paperId);
			return paper.removePaperQuestions(questionIds);
		} else {
			throw new PlatformException("您没有删除试题的权限");
		}
	}

	public List getTestPaperHistorys(Page page, String siteId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			return testList.getTestPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public List getTestPaperHistorys(Page page, String paperId, String teachclassId, String siteId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			if (teachclassId != null && !"".equals(teachclassId) && !"null".equals(teachclassId))
				searchList.add(new SearchProperty("group_id", teachclassId, "="));

			if (paperId != null && !"".equals(paperId) && !"null".equals(paperId))
				searchList.add(new SearchProperty("testpaper_id", paperId, "="));

			return testList.getTestPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public int getTestPaperHistorysNum(String siteId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			return testList.getTestPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public int getTestPaperHistorysNum(String paperId, String teachclassId, String siteId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			if (teachclassId != null && !"".equals(teachclassId) && !"null".equals(teachclassId))
				searchList.add(new SearchProperty("group_id", teachclassId, "="));

			if (paperId != null && !"".equals(paperId) && !"null".equals(paperId))
				searchList.add(new SearchProperty("testpaper_id", paperId, "="));

			return testList.getTestPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public List getHomeworkPaperHistorys(Page page, String siteId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			return testList.getTestPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看作业结果的权限");
		}
	}

	public int getHomeworkPaperHistorysNum(String siteId) throws NoRightException, PlatformException {
		if (testPriv.getHomeworkPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			return testList.getHomeworkPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看作业结果的权限");
		}
	}

	public List getExamSequenceList(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamSequence == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			if (request != null && !request.equals("")) {
				String search = request.getParameter("Search");
				String testbatch_id = request.getParameter("exambatchid");
				if (search != null && !search.equals("")) {
					searchList.add(new SearchProperty("title", search, "like"));
				}
				if (testbatch_id != null && !testbatch_id.equals("")) {
					searchList.add(new SearchProperty("testbatch_id", testbatch_id, "="));
				}
			}
			orderList.add(new OrderProperty("basicsequence_id", "0"));
			return testList.getExamSequences(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看考试场次的权限");
		}
	}

	public int getExamSequenceNum(HttpServletRequest request) throws PlatformException {
		if (testPriv.getExamSequence == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			if (request != null && !request.equals("")) {
				String search = request.getParameter("Search");
				if (search != null && !search.equals("")) {
					searchList.add(new SearchProperty("title", search, "like"));
				}
				String testbatch_id = request.getParameter("exambatchid");
				if (testbatch_id != null && !testbatch_id.equals("")) {
					searchList.add(new SearchProperty("testbatch_id", testbatch_id, "="));
				}
			}
			return testList.getExamSequencesNum(searchList);
		} else {
			throw new PlatformException("您没有查看考试场次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 添加考试场次（时间，批次，场次）。
	 */
	public int addExamSequence(HttpServletRequest request) throws PlatformException {
		if (testPriv.addExamSequence == 1) {
			OracleExamSequence oracleExamSeq = new OracleExamSequence();
			String title = request.getParameter("basicsequence_id");
			String basicSequenceId = title.substring(title.indexOf("~~~~") + 4);
			title = title.substring(0, title.indexOf("~~~~"));
			oracleExamSeq.setTitle(title);
			oracleExamSeq.setStartDate(request.getParameter("startDate"));
			oracleExamSeq.setEndDate(request.getParameter("endDate"));
			oracleExamSeq.setNote(request.getParameter("note"));
			oracleExamSeq.setBatchId(request.getParameter("batchId"));
			oracleExamSeq.setBasicSequenceId(basicSequenceId);
			// return oracleExamSeq.add();
			return oracleExamSeq.addWithBatch();

		} else {
			throw new PlatformException("您没有添加考试场次的权限");
		}
	}

	public ExamSequence getExamSequence(String id) throws PlatformException {
		if (testPriv.getExamSequence == 1) {
			return new OracleExamSequence(id);
		} else {
			throw new PlatformException("您没有查看考试场次的权限");
		}
	}

	/**
	 * @author shu
	 * @return 修改考试场次批次时间。
	 */
	public int updateExamSequence(HttpServletRequest request) throws PlatformException {
		if (testPriv.updateExamSequence == 1) {
			OracleExamSequence oracleExamSeq = new OracleExamSequence(request.getParameter("id"));
			String title = request.getParameter("basicsequence_id");
			String basicSequenceId = title.substring(title.indexOf("~~~~") + 4);
			title = title.substring(0, title.indexOf("~~~~"));
			if (title != null) {
				oracleExamSeq.setTitle(title);
			}
			if (request.getParameter("startDate") != null) {
				oracleExamSeq.setStartDate(request.getParameter("startDate"));
			}
			if (request.getParameter("endDate") != null) {
				oracleExamSeq.setEndDate(request.getParameter("endDate"));
			}
			if (request.getParameter("note") != null) {
				oracleExamSeq.setNote(request.getParameter("note"));
			}
			if (request.getParameter("batchId") != null) {
				oracleExamSeq.setBatchId(request.getParameter("batchId"));
			}
			if (title != null) {
				oracleExamSeq.setBasicSequenceId(basicSequenceId);
			}
			return oracleExamSeq.update();

		} else {
			throw new PlatformException("您没有更新考试场次的权限");
		}
	}

	public int deleteExamSequence(String id) throws PlatformException {
		if (testPriv.deleteExamSequence == 1) {
			OracleExamSequence oracleExamSeq = new OracleExamSequence(id);
			return oracleExamSeq.delete();
		} else {
			throw new PlatformException("您没有删除考试场次的权限");
		}
	}

	public int addBasicSequence(HttpServletRequest request) throws PlatformException {
		if (testPriv.addBasicSequence == 1) {
			OracleBasicSequence oracleBasicSeq = new OracleBasicSequence();
			oracleBasicSeq.setId(request.getParameter("id"));
			oracleBasicSeq.setTitle(request.getParameter("title"));
			oracleBasicSeq.setNote(request.getParameter("note"));
			return oracleBasicSeq.add();

		} else {
			throw new PlatformException("您没有添加场次的权限");
		}
	}

	public int deleteBasicSequence(HttpServletRequest request) throws PlatformException {
		if (testPriv.deleteBasicSequence == 1) {
			OracleBasicSequence oracleBasicSeq = new OracleBasicSequence(request.getParameter("id"));
			return oracleBasicSeq.delete();
		} else {
			throw new PlatformException("您没有删除场次的权限");
		}
	}

	public BasicSequence getBasicSequence(HttpServletRequest request) throws PlatformException {
		if (testPriv.getBasicSequence == 1) {
			return new OracleBasicSequence(request.getParameter("id"));
		} else {
			throw new PlatformException("您没有查看场次的权限");
		}
	}

	public List getBasicSequences(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getBasicSequence == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id"));
			return testList.getBasicSequences(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看场次的权限");
		}
	}

	public List getBasicSequences(Page page, HttpServletRequest request, String batch_id) throws PlatformException {
		if (testPriv.getBasicSequence == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id"));
			return testList.getBasicSequences(page, searchList, orderList, batch_id);
		} else {
			throw new PlatformException("您没有查看场次的权限");
		}
	}

	public List getBasicSequences2(Page page, HttpServletRequest request, String batch_id) throws PlatformException {
		if (testPriv.getBasicSequence == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id"));
			return testList.getBasicSequences2(page, searchList, orderList, batch_id);
		} else {
			throw new PlatformException("您没有查看场次的权限");
		}
	}

	/**
	 * @author shu
	 * @param page
	 * @param request
	 * @return 得到没有安排考场时间的场次
	 * @throws PlatformException
	 */
	public List getBasicSequencesNoTime(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getBasicSequence == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id"));
			return testList.getBasicSequencesNoTime(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看场次的权限");
		}
	}

	public List getBasicSequencesNoTime(Page page, HttpServletRequest request, String batchId) throws PlatformException {
		if (testPriv.getBasicSequence == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id"));
			// return testList.getBasicSequencesNoTime(page, searchList,
			// orderList);
			return testList.getBasicSequencesNoTime(page, searchList, orderList, batchId);
		} else {
			throw new PlatformException("您没有查看场次的权限");
		}
	}

	/**
	 * @author shu
	 * @param page
	 * @param request
	 * @return 得到没有安排考场时间的场次
	 * @throws PlatformException
	 */
	public List getBasicSequencesNoTimeRe(Page page, HttpServletRequest request) throws PlatformException {
		if (testPriv.getBasicSequence == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id"));
			return testList.getBasicSequencesNoTime(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有查看场次的权限");
		}
	}

	public List getBasicSequencesNoTimeRe(Page page, HttpServletRequest request, String batchId) throws PlatformException {
		if (testPriv.getBasicSequence == 1) {
			OracleReExamList testList = new OracleReExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id"));
			// return testList.getBasicSequencesNoTime(page, searchList,
			// orderList);
			return testList.getBasicSequencesNoTime(page, searchList, orderList, batchId);
		} else {
			throw new PlatformException("您没有查看场次的权限");
		}
	}

	public int getBasicSequencesNum(HttpServletRequest request) throws PlatformException {
		if (testPriv.getBasicSequence == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			return testList.getBasicSequencesNum(searchList);
		} else {
			throw new PlatformException("您没有查看场次的权限");
		}
	}

	public int updateBasicSequence(HttpServletRequest request) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int changeCourseType(String id, String courseType) throws PlatformException {
		OracleExamCourse course = new OracleExamCourse();
		course.setId(id);
		course.setCourseType(courseType);
		return course.changeCourseType();
	}

	public int addExamPaper(String title, String creatuser, String status, String note, String time, String groupId) throws NoRightException, PlatformException {
		if (testPriv.addExamPaper == 1) {
			OracleExamPaper paper = new OracleExamPaper();
			paper.setTitle(title);
			paper.setCreatUser(creatuser);
			paper.setStatus(status);
			paper.setNote(note);
			paper.setTime(time);
			paper.setType("exam");
			paper.setGroupId(groupId);
			return paper.add();
		} else {
			throw new NoRightException("您没有添加考试试卷的权限");
		}
	}

	public int addExamPaperByOnlineExamCourse(String paperId, String testCourseId) throws NoRightException, PlatformException {
		if (testPriv.addExamPaperByOnlineExamCourse == 1) {
			OracleOnlineExamCourse course = new OracleOnlineExamCourse();
			course.setId(testCourseId);
			return course.addExamPaper(paperId);
		} else {
			throw new NoRightException("您没有查添加考试试卷的权限");
		}
	}

	public int addExamPaperHistory(String userId, String testPaperId, HashMap answer) throws NoRightException, PlatformException {
		if (testPriv.addExamPaperHistory == 1) {
			OracleExamPaperHistory history = new OracleExamPaperHistory();
			history.setUserId(userId);
			history.setTestPaperId(testPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			return history.add();
		} else {
			throw new NoRightException("您没有添加考试答卷结果的权限");
		}
	}

	public int addExperimentPaper(String title, String creatuser, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.addExperimentPaper == 1) {
			OracleExperimentPaper paper = new OracleExperimentPaper();
			paper.setTitle(title);
			paper.setCreatUser(creatuser);
			paper.setStatus(status);
			paper.setNote(note);
			paper.setComments(comment);
			paper.setStartDate(upDate);
			paper.setEndDate(endDate);
			paper.setType(type);
			paper.setGroupId(groupId);
			return paper.add();
		} else {
			throw new NoRightException("您没有添加实验的权限");
		}
	}

	public int addExperimentPaperHistory(String userId, String HomeworkPaperId, HashMap answer) throws NoRightException, PlatformException {
		if (testPriv.addExperimentPaperHistory == 1) {
			OracleExperimentPaperHistory history = new OracleExperimentPaperHistory();
			history.setUserId(userId);
			history.setTestPaperId(HomeworkPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			return history.add();
		} else {
			throw new NoRightException("您没有添加实验结果的权限");
		}
	}

	public int addOnlineExamCourse(String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser) throws NoRightException, PlatformException {
		if (testPriv.addOnlineExamCourse == 1) {
			OracleOnlineExamCourse course = new OracleOnlineExamCourse();
			course.setTitle(title);
			course.setNote(note);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
			course.setStatus(status);
			course.setIsAutoCheck(isAutoCheck);
			course.setIsHiddenAnswer(isHiddenAnswer);
			OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
			batch.setId(batchId);
			course.setBatch(batch);
			course.setGroupId(groupId);
			course.setCreatUser(creatUser);
			return course.add();
		} else {
			throw new NoRightException("您没有添加考试课程的权限");
		}
	}

	public int changeExamPaperStatus(String id, String status) throws NoRightException, PlatformException {
		if (testPriv.changeExamPaperStatus == 1) {
			OracleExamPaper paper = new OracleExamPaper();
			paper.setId(id);
			paper.setStatus(status);
			return paper.reverseActive();
		} else {
			throw new NoRightException("您没有修改考试试卷状态的权限");
		}
	}

	public int changeExperimentPaperStatus(String id, String status) throws NoRightException, PlatformException {
		if (testPriv.changeExperimentPaperStatus == 1) {
			OracleExperimentPaper paper = new OracleExperimentPaper();
			paper.setId(id);
			paper.setStatus(status);
			return paper.reverseActive();
		} else {
			throw new NoRightException("您没有修改实验状态的权限");
		}
	}

	public int changeOnlineExamCourseStatus(String id, String status) throws NoRightException, PlatformException {
		if (testPriv.changeOnlineExamCourseStatus == 1) {
			OracleOnlineExamCourse course = new OracleOnlineExamCourse();
			course.setId(id);
			course.setStatus(status);
			return course.reverseActive();
		} else {
			throw new NoRightException("您没有修改考试课程状态的权限");
		}
	}

	public int deleteExamPaper(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteExamPaper == 1) {
			OracleExamPaper paper = new OracleExamPaper(id);
			return paper.delete();
		} else {
			throw new NoRightException("您没有删除考试试卷的权限");
		}
	}

	public int deleteExamPaperByOnlineExamCourse(String paperId, String testCourseId) throws NoRightException, PlatformException {
		if (testPriv.deleteExamPaperByOnlineExamCourse == 1) {
			OracleOnlineExamCourse course = new OracleOnlineExamCourse();
			course.setId(testCourseId);
			return course.deleteExamPaper(paperId);
		} else {
			throw new NoRightException("您没有查删除考试试卷的权限");
		}
	}

	public int deleteExamPaperHistory(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteExamPaperHistory == 1) {
			OracleExamPaperHistory history = new OracleExamPaperHistory();
			history.setId(id);
			return history.delete();
		} else {
			throw new NoRightException("您没有删除考试结果的权限");
		}
	}

	public int deleteExperimentPaper(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteExperimentPaper == 1) {
			OracleExperimentPaper paper = new OracleExperimentPaper();
			paper.setId(id);
			return paper.delete();
		} else {
			throw new NoRightException("您没有删除实验的权限");
		}
	}

	public int deleteExperimentPaperHistory(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteExperimentPaperHistory == 1) {
			OracleExperimentPaperHistory history = new OracleExperimentPaperHistory();
			history.setId(id);
			return history.delete();
		} else {
			throw new NoRightException("您没有删除实验结果的权限");
		}
	}

	public int deleteOnlineExamCourse(String id) throws NoRightException, PlatformException {
		if (testPriv.deleteOnlineExamCourse == 1) {
			OracleOnlineExamCourse course = new OracleOnlineExamCourse();
			course.setId(id);
			return course.delete();
		} else {
			throw new NoRightException("您没有删除考试课程的权限");
		}
	}

	public List getActiveExperimentPapers(Page page, String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", "1", "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getActiveExperimentPapers(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看实验列表的权限");
		}
	}

	public int getActiveExperimentPapersNum(String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", "1", "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getActiveExperimentPapersNum(searchList);
		} else {
			throw new NoRightException("您没有查看实验列表的权限");
		}
	}

	public ExamPaper getExamPaper(String id) throws NoRightException, PlatformException {
		if (testPriv.getExamPaper == 1) {
			return new OracleExamPaper(id);
		} else {
			throw new NoRightException("您没有查看考试试卷信息的权限");
		}
	}

	public ExamPaperHistory getExamPaperHistory(String id) throws NoRightException, PlatformException {
		if (testPriv.getExamPaperHistory == 1) {
			return new OracleExamPaperHistory(id);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public List getExamPaperHistorys(Page page, String userId, String testPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExamPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (testPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", testPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getExamPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public List getExamPaperHistorys(Page page, String siteId) throws NoRightException, PlatformException {
		if (testPriv.getExamPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			return testList.getExamPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public List getExamPaperHistorys(Page page, String paperId, String teachclassId, String siteId) throws NoRightException, PlatformException {
		if (testPriv.getExamPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			if (teachclassId != null && !"".equals(teachclassId) && !"null".equals(teachclassId))
				searchList.add(new SearchProperty("group_id", teachclassId, "="));

			if (paperId != null && !"".equals(paperId) && !"null".equals(paperId))
				searchList.add(new SearchProperty("testpaper_id", paperId, "="));

			return testList.getExamPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public int getExamPaperHistorysNum(String userId, String testPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExamPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (testPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", testPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getExamPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public int getExamPaperHistorysNum(String siteId) throws NoRightException, PlatformException {
		if (testPriv.getExamPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			return testList.getExamPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public int getExamPaperHistorysNum(String paperId, String teachclassId, String siteId) throws NoRightException, PlatformException {
		if (testPriv.getExamPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			if (teachclassId != null && !"".equals(teachclassId) && !"null".equals(teachclassId))
				searchList.add(new SearchProperty("group_id", teachclassId, "="));

			if (paperId != null && !"".equals(paperId) && !"null".equals(paperId))
				searchList.add(new SearchProperty("testpaper_id", paperId, "="));

			return testList.getExamPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public List getExamPapers(Page page, String id, String title, String creatuser, String creatdate, String status, String note, String time, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExamPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (time != null)
				searchList.add(new SearchProperty("time", time, "="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getExamPapers(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看试卷列表的权限");
		}
	}

	public List getExamPapersByOnlineExamCourse(Page page, String testCourseId) throws NoRightException, PlatformException {
		if (testPriv.getExamPapersByOnlineExamCourse == 1) {
			OracleOnlineExamCourse course = new OracleOnlineExamCourse();
			course.setId(testCourseId);
			return course.getExamPapers(page);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public int getExamPapersNum(String id, String title, String creatuser, String creatdate, String status, String note, String time, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExamPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (time != null)
				searchList.add(new SearchProperty("time", time, "="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getExamPapersNum(searchList);
		} else {
			throw new NoRightException("您没有查看试卷列表的权限");
		}
	}

	public int getExamPapersNumByOnlineExamCourse(String testCourseId) throws NoRightException, PlatformException {
		if (testPriv.getExamPapersByOnlineExamCourse == 1) {
			OracleOnlineExamCourse course = new OracleOnlineExamCourse();
			course.setId(testCourseId);
			return course.getExamPapersNum();
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public ExperimentPaper getExperimentPaper(String id) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPaper == 1) {
			return new OracleExperimentPaper(id);
		} else {
			throw new NoRightException("您没有查看实验信息的权限");
		}
	}

	public boolean getExperimentPaperExpired(String paperId) throws NoRightException, PlatformException {
		OracleTestList testList = new OracleTestList();
		return testList.getExperimentPaperExpired(paperId);
	}

	public ExperimentPaperHistory getExperimentPaperHistory(String id) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPaperHistory == 1) {
			return new OracleExperimentPaperHistory(id);
		} else {
			throw new NoRightException("您没有查看实验结果的权限");
		}
	}

	public List getExperimentPaperHistorys(Page page, String userId, String HomeworkPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getExperimentPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看上交的实验报告的权限");
		}
	}

	public List getExperimentPaperHistorys(Page page, String HomeworkPaperId, String siteId, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";

			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));

			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			if (siteId != null)
				searchList.add(new SearchProperty("site_id", siteId, "="));
			return testList.getExperimentPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看上交的实验报告的权限");
		}
	}

	public List getExperimentPaperHistorys(Page page, String siteId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			return testList.getExperimentPaperHistorys(page, searchList, null, type);
		} else {
			throw new NoRightException("您没有查看上交的实验报告的权限");
		}
	}

	public int getExperimentPaperHistorysNum(String userId, String HomeworkPaperId, String beginDate, String endDate, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";
			if (userId != null) {
				searchList.add(new SearchProperty("user_id", userId, "="));
				type = "student";
			}
			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));
			if (beginDate != null) {
				searchList.add(new SearchProperty("test_date", beginDate, "D<="));
			}
			if (endDate != null) {
				searchList.add(new SearchProperty("test_date", endDate, "D<="));
			}
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getExperimentPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看上交的实验报告的权限");
		}
	}

	public int getExperimentPaperHistorysNum(String HomeworkPaperId, String site_id, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			String type = "teacher";

			if (HomeworkPaperId != null)
				searchList.add(new SearchProperty("testpaper_id", HomeworkPaperId, "="));

			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			if (site_id != null)
				searchList.add(new SearchProperty("site_id", site_id, "="));
			return testList.getExperimentPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看上交的实验报告的权限");
		}
	}

	public int getExperimentPaperHistorysNum(String siteId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			String type = "teacher";
			List searchList = new ArrayList();
			if (siteId != null && !"".equals(siteId) && !"null".equals(siteId))
				searchList.add(new SearchProperty("site_id", siteId, "="));

			return testList.getExperimentPaperHistorysNum(searchList, type);
		} else {
			throw new NoRightException("您没有查看上交的实验报告的权限");
		}
	}

	public List getExperimentPapers(Page page, String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getExperimentPapers(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看实验列表的权限");
		}
	}

	public int getExperimentPapersNum(String id, String title, String creatuser, String creatdate, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.getExperimentPapers == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (creatuser != null)
				searchList.add(new SearchProperty("creatuser", creatuser, "="));
			if (creatdate != null)
				searchList.add(new SearchProperty("creatdate", creatdate, "="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (note != null)
				searchList.add(new SearchProperty("note", note, "like"));
			if (upDate != null)
				searchList.add(new SearchProperty("startdate", upDate, "D>="));
			if (endDate != null)
				searchList.add(new SearchProperty("enddate", endDate, "D<="));
			if (type != null)
				searchList.add(new SearchProperty("type", type, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));

			return testList.getExperimentPapersNum(searchList);
		} else {
			throw new NoRightException("您没有查看实验列表的权限");
		}
	}

	public OnlineExamCourse getOnlineExamCourse(String id) throws NoRightException, PlatformException {
		if (testPriv.getOnlineExamCourse == 1) {
			return new OracleOnlineExamCourse(id);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public List getOnlineExamCourses(Page page, String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser) throws NoRightException, PlatformException {
		if (testPriv.getOnlineExamCourses == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (note != null) {
				searchList.add(new SearchProperty("note", note, "like"));
			}
			if (startDate != null) {
				searchList.add(new SearchProperty("start_date", startDate, "D>="));
			}
			if (endDate != null)
				searchList.add(new SearchProperty("end_date", endDate, "D<="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (isAutoCheck != null)
				searchList.add(new SearchProperty("isautocheck", isAutoCheck, "="));
			if (isHiddenAnswer != null)
				searchList.add(new SearchProperty("ishiddenanswer", isHiddenAnswer, "="));
			if (batchId != null)
				searchList.add(new SearchProperty("batch_id", batchId, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (creatUser != null)
				searchList.add(new SearchProperty("creatuser", creatUser, "="));
			return testList.getOnlineExamCourses(page, searchList, null);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public int getOnlineExamCoursesNum(String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser) throws NoRightException, PlatformException {
		if (testPriv.getOnlineExamCourse == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (id != null)
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (note != null) {
				searchList.add(new SearchProperty("note", note, "like"));
			}
			if (startDate != null) {
				searchList.add(new SearchProperty("start_date", startDate, "D>="));
			}
			if (endDate != null)
				searchList.add(new SearchProperty("end_date", endDate, "D<="));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (isAutoCheck != null)
				searchList.add(new SearchProperty("isautocheck", isAutoCheck, "="));
			if (isHiddenAnswer != null)
				searchList.add(new SearchProperty("ishiddenanswer", isHiddenAnswer, "="));
			if (batchId != null)
				searchList.add(new SearchProperty("batch_id", batchId, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (creatUser != null)
				searchList.add(new SearchProperty("creatuser", creatUser, "="));
			return testList.getOnlineExamCoursesNum(searchList);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public int updateExamPaper(String id, String title, String creatuser, String status, String note, String time, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.updateExamPaper == 1) {
			OracleExamPaper paper = new OracleExamPaper(id);
			paper.setTitle(title);
			paper.setCreatUser(creatuser);
			paper.setStatus(status);
			paper.setNote(note);
			paper.setTime(time);
			paper.setType(type);
			paper.setGroupId(groupId);

			return paper.update();
		} else {
			throw new NoRightException("您没有添加考试试卷的权限");
		}
	}

	public int updateExamPaperHistory(String id, String userId, String testPaperId, HashMap answer) throws NoRightException, PlatformException {
		if (testPriv.updateExamPaperHistory == 1) {
			OracleExamPaperHistory history = new OracleExamPaperHistory();
			history.setId(id);
			history.setUserId(userId);
			history.setTestPaperId(testPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			return history.update();
		} else {
			throw new NoRightException("您没有修改考试结果的权限");
		}
	}

	public int updateExperimentPaper(String id, String title, String creatuser, String status, String note, String comment, String upDate, String endDate, String type, String groupId) throws NoRightException, PlatformException {
		if (testPriv.updateExperimentPaper == 1) {
			OracleExperimentPaper paper = new OracleExperimentPaper(id);
			paper.setTitle(title);
			paper.setCreatUser(creatuser);
			paper.setStatus(status);
			paper.setNote(note);
			paper.setComments(comment);
			paper.setStartDate(upDate);
			paper.setEndDate(endDate);
			paper.setType(type);
			paper.setGroupId(groupId);
			return paper.update();
		} else {
			throw new NoRightException("您没有添加实验的权限");
		}
	}

	public int updateExperimentPaperHistory(String id, String userId, String HomeworkPaperId, HashMap answer) throws NoRightException, PlatformException {
		if (testPriv.updateExperimentPaperHistory == 1) {
			OracleExperimentPaperHistory history = new OracleExperimentPaperHistory();
			history.setId(id);
			history.setUserId(userId);
			history.setTestPaperId(HomeworkPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			history.setStatus("1");
			return history.update();
		} else {
			throw new NoRightException("您没有修改实验结果的权限");
		}
	}

	public int updateExperimentPaperHistory(String id, String userId, String HomeworkPaperId, HashMap answer, String status) throws NoRightException, PlatformException {
		if (testPriv.updateExperimentPaperHistory == 1) {
			OracleExperimentPaperHistory history = new OracleExperimentPaperHistory();
			history.setId(id);
			history.setUserId(userId);
			history.setTestPaperId(HomeworkPaperId);
			history.setTestResult(answer);
			history.setScore((String) answer.get("totalScore"));
			history.setStatus("0");
			return history.update();
		} else {
			throw new NoRightException("您没有修改实验结果的权限");
		}
	}

	public int updateOnlineExamCourse(String id, String title, String note, String startDate, String endDate, String status, String isAutoCheck, String isHiddenAnswer, String batchId, String groupId, String creatUser) throws NoRightException, PlatformException {
		if (testPriv.updateOnlineExamCourse == 1) {
			OracleOnlineExamCourse course = new OracleOnlineExamCourse(id);
			course.setTitle(title);
			course.setNote(note);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
			course.setStatus(status);
			course.setIsAutoCheck(isAutoCheck);
			course.setIsHiddenAnswer(isHiddenAnswer);
			OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
			batch.setId(batchId);
			course.setBatch(batch);
			course.setGroupId(groupId);
			return course.update();
		} else {
			throw new NoRightException("您没有修改考试课程的权限");
		}
	}

	@Override
	public String getPolicyIdByPaperId(String homeworkId) throws Exception {
		OraclePaperPolicy paperPolicy = new OraclePaperPolicy();
		return paperPolicy.getIdByTitle(homeworkId);
	}

	@Override
	public int addStoreQuestion(String title, String creatuser, String creatdate, String diff, String keyword, String questioncore, String loreDir, String loreName, String lore_Id, String cognizetype, String purpose, String referencescore, String referencetime, String studentnote,
			String teachernote, String type, String discription) throws NoRightException, PlatformException {
		// TODO Auto-generated method stub
		if (testPriv.addStoreQuestion == 1) {
			OracleStoreQuestion question = new OracleStoreQuestion();
			question.setTitle(title);
			question.setCreatUser(creatuser);
			question.setCreatDate(creatdate);
			question.setDiff(diff);
			question.setKeyWord(keyword);
			question.setQuestionCore(questioncore);
			question.setLore(lore_Id);
			question.setCognizeType(cognizetype);
			question.setPurpose(purpose);
			question.setReferenceScore(referencescore);
			question.setReferenceTime(referencetime);
			question.setStudentNote(studentnote);
			question.setTeacherNote(teachernote);
			question.setType(type);
			//question.setDiscription(discription);
			question.setRemark(discription);

			return question.add();
		} else {
			throw new NoRightException("您没有添加题库的权限");
		}
	}

	public String getCountByType(String groupId, String type) throws NoRightException, PlatformException {
		String count = "0";
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = "select count(tsi.id) as count from test_storequestion_info tsi, test_lore_info tli, test_lore_dir tld where tli.id=tsi.lore " + "and tld.id=tli.loredir  and tld.group_id='" + groupId + "' and tsi.purpose like'%KAOSHI%'  and tsi.type='" + type + "'";

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				count = rs.getString("count");
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return count;

	}

	@Override
	public List getQuestions(String loreId) {
		// TODO Auto-generated method stub
		if (testPriv.getBasicSequence == 1) {
			OracleTestList testList = new OracleTestList();

			return testList.getQuestions(loreId);
		} else {
			return null;
		}

	}

	/**
	 * by 魏慧宁 删除试卷前判断 说明：如果学员已经考试，不能删除
	 * 
	 * @param loreId
	 */
	public Boolean deleteJudge(String loreId) {
		Boolean flag = true;
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = "select * from test_testpaper_history where testpaper_id = '" + loreId + "'";

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return flag;
	}

	/**
	 * by 蔡磊 设为无效前判断 说明：如果学员已经考试，不能设为无效
	 * 
	 * @param testCourseId
	 */
	public Boolean invalideJudge(String testCourseId) {

		Boolean flag = true;
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = "select * from onlinetest_course_info oci\n" + "inner join test_testpaper_info tti on oci.group_id = tti.group_id\n" + "inner join test_testpaper_history tth on tth.testpaper_id = tti.id\n" + "where oci.id = '" + testCourseId + "'";

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return flag;
	}

	@Override
	public int updateLoreDirName(String name, String id) throws PlatformException, NoRightException {
		// TODO Auto-generated method stub
		if (testPriv.updateLore == 1) {
			OracleLoreDir loreDir = new OracleLoreDir();
			loreDir.setId(id);
			loreDir.setName(name);
			return loreDir.updateName();

		} else {
			throw new NoRightException("您没有修改知识点的权限");
		}
	}

	@Override
	public Map getOnlineExamScore(String stuid, String piciId) throws NoRightException {
		// TODO Auto-generated method stub
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			// return testList.getOnlineTestCourses(page, searchList,
			// null,batch_id);
			return testList.getOnlineExamScore(stuid, piciId);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	@Override
	public int addExamTestPaperHistory(String userId, String testPaperId, String score, HashMap answer, String openDate ,String isSub) throws NoRightException, PlatformException {
		// TODO Auto-generated method stub
		if (testPriv.addTestPaperHistory == 1) {
			OracleTestPaperHistory history = new OracleTestPaperHistory();
			history.setUserId(userId);
			history.setTestPaperId(testPaperId);
			history.setTestResult(answer);
			history.setScore(score);
			history.setTestOpenDate(openDate);
			history.setIsSub(isSub);
			history.setScore((String) answer.get("totalScore"));
			return history.addOnlineExam();
		} else {
			throw new NoRightException("您没有添加答卷结果的权限");
		}
	}

	public String getCountByType(String groupId, String type, String purpose) throws NoRightException, PlatformException {
		String count = "0";
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = "select count(tsi.id) as count from test_storequestion_info tsi, test_lore_info tli, test_lore_dir tld where tli.id=tsi.lore " + "and tld.id=tli.loredir  and tld.group_id='" + groupId + "' and tsi.purpose='" + purpose + "'  and tsi.type='" + type + "'";

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				count = rs.getString("count");
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return count;

	}

	@Override
	public boolean getisExam(String stuid, String piciId, String type) {
		// TODO Auto-generated method stub
		OracleTestList testList = new OracleTestList();

		return testList.getisExam(stuid, piciId, type);
	}

	/**
	 * 课后测验列表
	 * 
	 * @author Lee
	 * @param page
	 * @param title
	 * @param status
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public List getOnlineTestCourses(Page page, String title, String status, String groupId) throws NoRightException, PlatformException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (testPriv.getTestPaperHistorys == 1 && null != us && null != us.getSsoUser().getId()) {
			String uid = us.getSsoUser().getId();
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			searchList.add(new SearchProperty("fk_sso_user_id", uid, "="));
			return testList.getOnlineTestCourses(page, searchList);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	/**
	 * 课后测验数量
	 */
	public int getOnlineTestCoursesNum(String title, String status, String groupId, String user) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestList testList = new OracleTestList();
			List searchList = new ArrayList();
			if (title != null)
				searchList.add(new SearchProperty("title", title, "like"));
			if (status != null)
				searchList.add(new SearchProperty("status", status, "="));
			if (groupId != null)
				searchList.add(new SearchProperty("group_id", groupId, "="));
			return testList.getOnlineTestCoursesNum3(searchList);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	/**
	 * 试卷删除
	 */
	public int deleteSelectPaper(String[] ids) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistorys == 1) {
			OracleTestPaper testPaper = new OracleTestPaper();
			return testPaper.deleteSelect(ids);
		} else {
			throw new NoRightException("您没有查看考试课程的权限");
		}
	}

	public List<String> addTestPaper(String title, String creatuser, String status, String note, String time, String groupId, String paper_num, String paper_fun) throws NoRightException, PlatformException {
		List<String> ls = new ArrayList<String>();
		int num = Integer.parseInt(paper_num);
		if (testPriv.addTestPaper == 1) {
			for (int i = 0; i < num; i++) {
				OracleTestPaper testPaper = new OracleTestPaper();
				if (num == 1) {
					testPaper.setTitle(title);
				} else {
					testPaper.setTitle(title + "_" + (i + 1));
				}
				testPaper.setCreatUser(creatuser);
				testPaper.setStatus(status);
				testPaper.setNote(note);
				testPaper.setTime(time);
				testPaper.setType("test");
				testPaper.setGroupId(groupId);
				testPaper.setPaper_fun(paper_fun);
				String paperid = String.valueOf(testPaper.add());
				if (paperid != null && !paperid.equals("0")) {
					ls.add(paperid);
				}
			}
			return ls;
		} else {
			throw new NoRightException("您没有添加自测试卷的权限");
		}
	}

	/**
	 * 根据试卷ID,用户ID查询试卷结果
	 * 
	 * @param testPaperId
	 *            试卷ID
	 * @param userId用户ID
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 * @author Lee
	 */
	public TestPaperHistory getTestPaperHistoryByTestPaperId(String testPaperId, String userId) throws NoRightException, PlatformException {
		if (testPriv.getTestPaperHistory == 1) {
			TestPaperHistory tph = new OracleTestPaperHistory();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ID, ");
			sb.append("        USER_ID, ");
			sb.append("        TESTPAPER_ID, ");
			sb.append("        TO_CHAR(TEST_DATE, 'yyyy-mm-dd') AS TEST_DATE, ");
			sb.append("        TEST_RESULT, ");
			sb.append("        SCORE ");
			sb.append("   FROM TEST_TESTPAPER_HISTORY ");
			sb.append("  WHERE ID = ");
			sb.append("        (SELECT MAX(ID) ");
			sb.append("           FROM TEST_TESTPAPER_HISTORY ");
			sb.append("          WHERE TESTPAPER_ID = '" + testPaperId + "' ");
			sb.append("            AND T_USER_ID = '" + userId + "')");
			dbpool db = new dbpool();
			MyResultSet rs = db.executeQuery(sb.toString());
			try {
				if (rs != null && rs.next()) {
					tph.setId(rs.getString("id"));
					tph.setUserId(rs.getString("user_id"));
					tph.setTestPaperId(rs.getString("testpaper_id"));
					tph.setTestDate(rs.getString("test_date"));
					tph.setTestResult(rs.getString("test_result"));
					tph.setScore(rs.getString("score"));
				}
			} catch (SQLException e) {

			} finally {
				db.close(rs);
			}
			return tph;
		} else {
			throw new NoRightException("您没有查看考试结果的权限");
		}
	}

	public void saveLoginfo(String executeDetail, String userId, String modeType, String loginPost, String writeValue, String ipAdress) {
		String sql = " insert into log_info(id,execute_Detail,user_code,mode_type ,login_post ,write_value,ip_adress ,execute_date) values(" + "sys_guid()," + "'" + executeDetail + "'," + "'" + userId + "'," + "'" + modeType + "'," + "'" + loginPost + "'," + "'" + writeValue + "'," + "'" + ipAdress
				+ "'," + "sysdate )";
		dbpool db = new dbpool();
		db.executeUpdate(sql);
		db = null;

	}

	public String getExamTimes(String testCourseId) {
		String examTimes = "";
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = " SELECT  A.TIME examTimes FROM TEST_TESTPAPER_INFO A, ONLINETEST_COURSE_PAPER B  WHERE A.ID = B.PAPER_ID AND B.TESTCOURSE_ID = '" + testCourseId + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				examTimes = rs.getString("examTimes");
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return examTimes;
	}

	@Override
	public String getOnlineTestCourses(String groupId) throws NoRightException,
			PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String id = "";
		String sql = " select id from onlinetest_course_info t where rownum <= 1 and t.group_id = '" + groupId + "' order by t.creatdate desc ";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				id = rs.getString("id");
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return id;
	}

	@Override
	public int addPreTestRecord(String userid, String courseId,
			String score, String passrole) throws NoRightException,
			PlatformException {
		// TODO Auto-generated method stub
		int count = 0;
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString().replace("-", "").toLowerCase();
		int ispass = 0;
		if(score != null && !score.equals("")){
			float sc = Float.parseFloat(score);
			if(passrole != null && !passrole.equals("")){
				float pass = Float.parseFloat(passrole);
				if(sc >= pass){
					ispass = 1;
				}
			}
		}
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id from pretest_record where  user_id= '" + userid + "' and course_id = '" + courseId + "'" ;
		String insert = "insert into pretest_record(id,user_id,course_id,test_date,score,ispass)" +
				" values('" + id + "','" + userid + "','" + courseId + "', SYSDATE," + score + " ," + ispass + " )";
		
		String update = " update pretest_record set score = '" + score + "', test_date = sysdate , ispass = " + ispass + " where user_id= '" + userid + "' and course_id = '" + courseId + "'" ;
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				count = db.executeUpdate(update);
			}else{
				count = db.executeUpdate(insert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db = null;
		}		
		return count;
	}
}
