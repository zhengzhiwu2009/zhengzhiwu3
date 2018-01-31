package com.whaty.platform.database.oracle.standard.test.paper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.OracleTestList;
import com.whaty.platform.database.oracle.standard.test.lore.OracleLore;
import com.whaty.platform.database.oracle.standard.test.lore.OracleLoreDir;
import com.whaty.platform.database.oracle.standard.test.question.OraclePaperQuestion;
import com.whaty.platform.database.oracle.standard.test.question.OracleStoreQuestion;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.interaction.InteractionFactory;
import com.whaty.platform.interaction.InteractionManage;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.test.TestManage;
import com.whaty.platform.test.lore.LoreDir;
import com.whaty.platform.test.paper.PaperActivity;
import com.whaty.platform.test.paper.PaperPolicyCore;
import com.whaty.platform.test.question.TestQuestionType;
import com.whaty.platform.util.SearchProperty;

public class OraclePaperActivity implements PaperActivity {
	private String paperPolicyId;

	private String courseId;
	private String questiontype;

	public String getPaperPolicyId() {
		return paperPolicyId;
	}

	public void setPaperPolicyId(String paperPolicyId) {
		this.paperPolicyId = paperPolicyId;
	}

	public String getCourseId() {
		if (this.paperPolicyId != null && !this.paperPolicyId.equals("")
				&& !this.paperPolicyId.equals("null")) {
			dbpool db = new dbpool();
//			String sql = "select a.course_id from entity_course_active a,entity_teach_class b,test_paperpolicy_info c "
//					+ "where b.open_course_id=a.id and b.id=c.group_id and c.id='"
//					+ this.paperPolicyId + "'";
			String sql = "select o.fk_course_id from pr_tch_opencourse o, test_paperpolicy_info t where o.id = t.group_id and t.id = '"+ this.paperPolicyId + "'";
			MyResultSet rs = db.executeQuery(sql);
			try {
				if (rs != null && rs.next()) {
					courseId = rs.getString("fk_course_id");
				}
			} catch (SQLException e) {
			}
			db.close(rs);
		}
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public HashMap getQuestionsByPaperPolicy(String xmlPolicyCore) {
		PaperPolicyCore paperPolicyCore = new PaperPolicyCore();
		paperPolicyCore.setXmlPolicyCore(xmlPolicyCore);
		List list = paperPolicyCore.getPaperPolicyCore();
		HashMap questionMap = new HashMap();
		HashMap item;
		List loreList, typeList, questionList;
		String type;
		Iterator it = list.iterator();
		if (it.hasNext())
			loreList = (List) it.next();
		if (it.hasNext())
			typeList = (List) it.next();
		while (it.hasNext()) {
			item = new HashMap();
			item = (HashMap) it.next();
			questionList = new ArrayList();
			type = (String) item.get("type");
			if (type.equals(TestQuestionType.DANXUAN)) {
				questionList = getDANXUAN(item);
			} else if (type.equals(TestQuestionType.DUOXUAN)) {
				questionList = getDUOXUAN(item);
			} else if (type.equals(TestQuestionType.PANDUAN)) {
				questionList = getPANDUAN(item);
			} else if (type.equals(TestQuestionType.TIANKONG)) {
				questionList = getTIANKONG(item);
			} else if (type.equals(TestQuestionType.WENDA)) {
				questionList = getWENDA(item);
			} else if (type.equals(TestQuestionType.YUEDU)) {
				questionList = getYUEDU(item);
			}
			questionMap.put(type, questionList);
		}
		return questionMap;
	}
/*
 * 按照组卷策略，新的选题方法
 */
	public HashMap getQuestionsByPaperPolicyNew(String xmlPolicyCore,String policyType) {

		Map<String, Integer> allQTypeMap = new HashMap<String, Integer>();//用于缓存题目类型
		Map<String, String> usedQTypeMap = new HashMap<String, String>();//用于缓存题目类型
		Map<String,Map<String,String>>  allQuestionMap = new HashMap<String,Map<String,String>>(); //所有符合条件将被添加到试卷中的题目 key: 题目类型    value: Map<String,PeQuestions> (key:题目Id,value:题目) 
		Map<String,OraclePaperQuestion>  usedQuestionsMap = new HashMap<String,OraclePaperQuestion>();		
		HashMap item = new HashMap();	
		int qtotalnum = 0;
		
		PaperPolicyCore paperPolicyCore = new PaperPolicyCore();
		paperPolicyCore.setXmlPolicyCore(xmlPolicyCore);
		List list = paperPolicyCore.getPaperPolicyCore();
		HashMap questionMap = new HashMap();
		List qListtemp = new ArrayList();
		
		List<String> loreListMust= new ArrayList<String>();
		List<String> loreListAll= new ArrayList<String>();
		List<String> typeList= new ArrayList<String>();
		List questionList = new ArrayList();		
		//String policyType = "";
		String type;
		Iterator it = list.iterator();
		if (it.hasNext())
			loreListMust = (List) it.next();
		System.out.println(loreListMust.toString());
			
		if (it.hasNext())
			typeList = (List) it.next();
		System.out.println(typeList.toString());
		
		//组装allQTypeMap
		try {
			while (it.hasNext()) {			
				item = (HashMap) it.next();			
				type = (String) item.get("type");
				allQTypeMap = getAllTypeMap(item,allQTypeMap);				
				if((allQTypeMap.get(type) instanceof Integer)){
					if(allQTypeMap.get(type)<=0){
						allQTypeMap.remove(type);
					}else{
						qtotalnum = qtotalnum + allQTypeMap.get(type);
					}
				}
				System.out.println(allQTypeMap.toString());
			}		
			
			for(int i=0;i<loreListMust.size();i++){
				if(qtotalnum>0 && !"".equals(loreListMust.get(i))){
					List<String> ls = new ArrayList();
					ls.add(loreListMust.get(i));
					int mun = 1;
					questionList = getLoreQestion(ls,usedQuestionsMap,allQTypeMap,mun,policyType);
					if(null!=questionList&& questionList.size()>0){
						OraclePaperQuestion pq = (OraclePaperQuestion)questionList.get(0);				
						
						if(null!=questionMap.get(pq.getType())){
							qListtemp = (List)questionMap.get(pq.getType());
						}else{
							qListtemp = new ArrayList();
						}
						qListtemp.add(pq);		
						questionMap.put(pq.getType(), qListtemp);
						
						//重新设置查询条件—-- 已使用题目
						usedQuestionsMap.put(pq.getId(),pq);				
						//重新设置查询条件—-- 题目类型
						if(allQTypeMap.get(pq.getType())-1 >0){
							allQTypeMap.put(pq.getType(),allQTypeMap.get(pq.getType())-1);
							qtotalnum--;
						}else{
							allQTypeMap.remove(pq.getType());
							qtotalnum--;
						}
					}
				}else{
					
				}
							
			}
		
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//所有知识点的题目都选择了一遍后，剩余的题目在全部知识点中选

		HttpSession ss=	ServletActionContext.getRequest().getSession();
		InteractionUserPriv interactionUserPriv = (InteractionUserPriv)ss.getAttribute("interactionUserPriv");		
		OpenCourse po = (OpenCourse)ss.getAttribute("openCourse");
		String coreid = (String)ss.getAttribute("courseId");
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		TestManage testManage = interactionManage.creatTestManage();
		String rootDirId;
		try {
			rootDirId = testManage.getLoreDirByGroupId(coreid).getId();			
			LoreDir loreDir = testManage.getLoreDir(rootDirId);
			// get lorelist of root dir
			List loreList = loreDir.getLoreList();
			if(null!=loreList && loreList.size()>0){
				for(int i=0;i<loreList.size();i++){
					loreListAll.add(((OracleLore)loreList.get(i)).getId());
				}
			}
			// get lorelist of the sub dir (gong liang ji mu lu)
			List subLoreDirList = loreDir.getSubLoreDirList();
			if(null!=subLoreDirList && subLoreDirList.size()>0){
				for(int i=0;i<subLoreDirList.size();i++){
					rootDirId = ((OracleLoreDir)subLoreDirList.get(i)).getId();
					loreDir = testManage.getLoreDir(rootDirId);
					loreList = loreDir.getLoreList();
					if(null!=loreList && loreList.size()>0){
						for(int j=0;j<loreList.size();j++){
							loreListAll.add(((OracleLore)loreList.get(j)).getId());
						}
					}
					
				}
			}
			
			
		} catch (NoRightException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (PlatformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
		for(String key : allQTypeMap.keySet()){
			if(qtotalnum>0){
				int num = allQTypeMap.get(key);
				Map<String, Integer> typeMap = new HashMap<String, Integer>();//用于缓存题目类型
				typeMap.put(key, num);
				questionList = getLoreQestion(loreListAll,usedQuestionsMap,typeMap,num,policyType);
				
				//将此类型的题目添加到questionMap中
				if(null!=questionList&& questionList.size()>0){		
					
					if(null!=questionMap.get(key)){
						qListtemp = (List)questionMap.get(key);
					}else{
						qListtemp = new ArrayList();
					}					
					for(int j=0;j<questionList.size();j++){
						OraclePaperQuestion pq = (OraclePaperQuestion)questionList.get(j);
						qListtemp.add(pq);
					}								
					questionMap.put(key, qListtemp);					
					qtotalnum = qtotalnum - questionList.size();					
				}
			}else{
				return questionMap;
			}
			
		}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return questionMap;
	}
	/*
	 * 题库选择 sql ，并得到题目列表
	 */
	public List getLoreQestion(List<String> loreList ,Map<String,OraclePaperQuestion> usedQuestionsMap ,Map<String,Integer> allQTypeMap,int num,String policyType){
		//组装 知识点 查询条件
		String lore = "";
		for (int i = 0; i < loreList.size(); i++)
			lore += ",'" + (String) loreList.get(i) + "'";
		if(!"".equals(lore)){			
			lore = lore.substring(1);
		}
		//组装 题目类型 查询条件
		Iterator iter = allQTypeMap.entrySet().iterator();
		String allQTypes = "";
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			allQTypes += ",'"+key+"'";
		}
		if(!"".equals(allQTypes)){ allQTypes = allQTypes.substring(1); }
		
		
		//组装 已使用题目 查询条件
		Iterator iter2 = usedQuestionsMap.entrySet().iterator();
		String usedQuestionIds = "";
		while (iter2.hasNext()) {
			Map.Entry entry = (Map.Entry) iter2.next();
			String key = entry.getKey().toString();
			usedQuestionIds += ",'"+key+"'";
		}
		if(!"".equals(usedQuestionIds)){
			usedQuestionIds = usedQuestionIds.substring(1);
		}
		
		
		List paperQuestionList = new ArrayList();
		String id = "";		
		String score = "";
		String purpose="";
		List searchproperty = new ArrayList();
		
		searchproperty.add(new SearchProperty("lore", lore, "in"));
		searchproperty.add(new SearchProperty("type", allQTypes, "in"));
		if(null!=policyType && !"".equals(policyType)){
			if(policyType.equals("test")){
				purpose="KAOSHI";
				searchproperty.add(new SearchProperty("purpose", purpose, "like"));
			}
			if(policyType.equals("OnlineExam")){
				purpose="OnlineExam";
				searchproperty.add(new SearchProperty("purpose", purpose, "like"));
			}
		}		
		
		if(null!=usedQuestionsMap){
			searchproperty.add(new SearchProperty("id", usedQuestionIds, "notIn"));
		}

		OracleTestList testList = new OracleTestList();
		int count = testList.getStoreQuestionsNum(searchproperty);
		if (count > 0) {
			id = getRandomIds(num, count);
			searchproperty.add(new SearchProperty("rownums", id, "in"));
			paperQuestionList = testList.getQuestionsByPaperPolicy(score, this
					.getCourseId(), null, searchproperty, null);

		}		
		
		return paperQuestionList;
	}
	/*
	 * 获取题目类型及其数量
	 */
	public Map<String, Integer> getAllTypeMap(HashMap item,Map<String, Integer> allQTypeMap){	
		
		Set set = item.keySet();
		Iterator it = set.iterator();		
		String type = (String) item.get("type");		
		int num = 0;		
		while (it.hasNext()) {
			String contype = (String) it.next();
			String[] content;
			if (contype.equals("A")) {
				content = (String[]) item.get(contype);
				num = Integer.parseInt(content[0]);
				allQTypeMap.put(type, num);							
			} 
			
		}
		return allQTypeMap; 
	}

	public List getDANXUAN(HashMap item) {
		// List storeQuestionList = new ArrayList();
		List paperQuestionList = new ArrayList();
		String id = "";
		Set set = item.keySet();
		Iterator it = set.iterator();
		List searchproperty = new ArrayList();
		String type = (String) item.get("type");
		searchproperty.add(new SearchProperty("type", type, "="));
		int num = 0;
		String score = "";
		while (it.hasNext()) {
			String contype = (String) it.next();
			String[] content;
			if (contype.equals("A")) {
				content = (String[]) item.get(contype);
				num = Integer.parseInt(content[0]);
			} else if (contype.equals("B")) {
				content = (String[]) item.get(contype);
				try {
					score = Float.toString(Integer.parseInt(content[0]) / num);
				} catch (Exception e) {
					score = "-1";
				}
			}
			/*
			else if (contype.equals("C")) {
				content = (String[]) item.get(contype);
				searchproperty.add(new SearchProperty("to_number(diff)",
						content[0], ">="));
				searchproperty.add(new SearchProperty("to_number(diff)",
						content[1], "<="));
			} 
			else if (contype.equals("D")) {
				content = (String[]) item.get(contype);
				try {
					float time_low = Float.parseFloat(content[0]);
					searchproperty.add(new SearchProperty(
							"to_number(referencetime)", Float
									.toString(time_low), ">="));
				} catch (Exception e) {
				}
				try {
					float time_high = Float.parseFloat(content[1]);
					searchproperty.add(new SearchProperty(
							"to_number(referencetime)", Float
									.toString(time_high), "<="));
				} catch (Exception e) {
				}
			} else if (contype.equals("E")) {
				content = (String[]) item.get(contype);
				try {
					float score_low = Float.parseFloat(content[0]);
					searchproperty.add(new SearchProperty(
							"to_number(referencescore)", Float
									.toString(score_low), ">="));
				} catch (Exception e) {
				}
				try {
					float score_high = Float.parseFloat(content[1]);
					searchproperty.add(new SearchProperty(
							"to_number(referencescore)", Float
									.toString(score_high), "<="));
				} catch (Exception e) {
				}
			} else if (contype.equals("F")) {
				content = (String[]) item.get(contype);
				String cognizetype = "";
				for (int i = 0; i < content.length; i++)
					cognizetype += ",'" + content[i] + "'";
				cognizetype = cognizetype.substring(1);
				searchproperty.add(new SearchProperty("cognizetype",cognizetype, "in"));
			}*/
			else if (contype.equals("lore")) {
				List lorelist = (List) item.get(contype);
				String lore = "";
				for (int i = 0; i < lorelist.size(); i++)
					lore += ",'" + (String) lorelist.get(i) + "'";
				lore = lore.substring(1);
				searchproperty.add(new SearchProperty("lore", lore, "in"));
			}
		}
		OracleTestList testList = new OracleTestList();
		int count = testList.getStoreQuestionsNum(searchproperty);
		if (count > 0) {
			id = getRandomIds(num, count);
			searchproperty.add(new SearchProperty("rownums", id, "in"));
			paperQuestionList = testList.getQuestionsByPaperPolicy(score, this
					.getCourseId(), null, searchproperty, null);
			// paperQuestionList =
			// StoreQuestionsToPaperQuestions(storeQuestionList, score);
		}
		return paperQuestionList;
	}

	public List getDUOXUAN(HashMap item) {
		return getDANXUAN(item);
	}

	public List getPANDUAN(HashMap item) {
		return getDANXUAN(item);
	}

	public List getTIANKONG(HashMap item) {
		return getDANXUAN(item);
	}

	public List getWENDA(HashMap item) {
		return getDANXUAN(item);
	}

	public List getYUEDU(HashMap item) {
		return getDANXUAN(item);
	}

	public String getRandomIds(int num, int count) {
		if (num > count)
			num = count;
		StringBuffer str = new StringBuffer();
		HashSet set = new HashSet();
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			Integer integer = new Integer(r.nextInt(count) + 1);// �������Ϊnum�ģ���Χ��1-count�������
			if (!set.add(integer)) // ���������������set��
				i--;
		}
		for (Iterator it = set.iterator(); it.hasNext();) {
			str.append(",'").append(it.next()).append("'");
		}
		return str.toString().substring(1);// �����м��'',���ַ�ܣ�
	}

	public OraclePaperQuestion StoreQuestionToPaperQuestion(
			OracleStoreQuestion storeQuestion, String score) {
		// TODO Auto-generated method stub
		return null;
	}

	public List StoreQuestionsToPaperQuestions(List storeQuestionList,
			String score) {
		List paperQuestionList = new ArrayList();
		Iterator it = storeQuestionList.iterator();
		while (it.hasNext()) {
			paperQuestionList.add(StoreQuestionToPaperQuestion(
					(OracleStoreQuestion) it.next(), score));
		}
		return paperQuestionList;
	}

	public String getQuestiontype() {
		return questiontype;
	}

	public void setQuestiontype(String questiontype) {
		this.questiontype = questiontype;
	}
}
