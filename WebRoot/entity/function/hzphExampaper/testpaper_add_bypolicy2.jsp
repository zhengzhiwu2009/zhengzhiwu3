<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%!
	/**
	 * 方法名称：getSortedHashtable 参数：Hashtable h 引入被处理的散列表
	 * 描述：将引入的hashtable.entrySet进行排序，并返回
	 */
	public Map.Entry[] getSortedHashtableByValue(Hashtable h) {
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set
				.size()]);

		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				return ((Comparable) ((Map.Entry) arg0).getValue().toString())
						.compareTo(((Map.Entry) arg1).getValue().toString());
			}
		});

		return entries;
	}

	public Hashtable parseScore(Hashtable t) {
		Map.Entry[] set = getSortedHashtableByValue(t);
		float total = 0;
		int total1 = 0;
		for (int i = 0; i < set.length; i++) {
			total += Float.parseFloat(set[i].getValue().toString());
		}
		for (int i = 0; i < set.length; i++) {
			total1 += Float.parseFloat(set[i].getValue().toString())
					* 100 / total;
		}
		int leave = 100 - total1;
		int per = 0;
		Hashtable h = new Hashtable();
		for (int i = 0; i < set.length; i++) {
			if (leave != 0) {
				per = 1;
				leave--;
			} else
				per = 0;
			h.put(set[i].getKey().toString(), Integer.toString((int) (Float
					.parseFloat(set[i].getValue().toString())
					* 100 / total) + per));
		}
		return h;
	}

%>
<%
	session.removeAttribute("paperQuestions");
	String policyId = request.getParameter("policyId");
	//String paperId = request.getParameter("paperId");
	String paperIds = request.getParameter("paperIds");	
	String[] ls = paperIds.split(",");
	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	if(paperQuestions!=null)
	{
%>
<script>
	alert("正在使用组卷策略进行组卷，请稍候在完成现有组卷操作后再重试！");
	window.history.back(-1);
</script>
<%
	}
	int flag = 0;
	String score = "";
	String field = "";
	try	{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		if(policyId.equals("0"))
		{
			session.setAttribute("paperQuestions",null);
		}
		else
		{
			for(int n=0;n<ls.length;n++){
				String paperId = ls[n].trim();
				paperQuestions = testManage.getQuestionsByPaperPolicy(policyId);
				//System.out.println("qu.size--"+paperQuestions.size());
				session.setAttribute("paperQuestions",paperQuestions);
				//每次抽题，生成一份试卷
				if(paperQuestions == null)
					paperQuestions = new HashMap();
				Set set = paperQuestions.keySet();
				List questionList = null;	
				int count = 1;
				
				Hashtable scores = new Hashtable();
				List id_idx = new ArrayList();
				int idx = 1;
				
				for(Iterator it = set.iterator();it.hasNext();)
				{
			  		field = (String)it.next();
					questionList = (List)paperQuestions.get(field);
					for(int i=0;i<questionList.size();i++,idx++)
				    {
						PaperQuestion question = (PaperQuestion)questionList.get(i);
						//score = request.getParameter(question.getId()+"score");
						id_idx.add(question.getId());
						scores.put(Integer.toString(idx),question.getReferenceScore());
					}
				}
				Hashtable scoreHash = parseScore(scores);
				
				idx = 1;
			  	for(Iterator it = set.iterator();it.hasNext();)
			  	{
			  		field = (String)it.next();
					questionList = (List)paperQuestions.get(field);
					for(int i=0;i<questionList.size();i++,idx++)
				    {
						PaperQuestion question = (PaperQuestion)questionList.get(i);
						//score = request.getParameter(question.getId()+"score");
						question.setTestPaperId(paperId);
						question.setScore((String) scoreHash.get(Integer.toString(idx)));
						question.setIndex(Integer.toString(count++));
						testManage.addPaperQuestion(question);
						questionList.set(i,question);
					}
				}				
			
			}
			
		}
		flag = 1;
	} catch(Exception e) { 
		out.print(e.getMessage());
		return;
	}

	if (flag == 0) {
%>
<script>
	alert("抽取试题失败！");
	window.history.back(-1);
</script>
<%
	} else {
%>
<script>
	alert("组卷成功！");
    location.href="testpaper_list.jsp";	
</script>
<%
	}
%>