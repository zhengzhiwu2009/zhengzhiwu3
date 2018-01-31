<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../pub/priv.jsp"%>

<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		String loreDirId = request.getParameter("loreDirId");
//		String courseId = openCourse.getCourse().getId();
		String rootDirId = testManage.getLoreDirByGroupId(courseId).getId();
		//out.print("rootDirId:" + rootDirId + "<br/>");
//System.out.println("zxc"+courseId);
		  
//		     java.util.Date sDate1 = new java.util.Date();
//	    	 SimpleDateFormat sDateFormat2=new SimpleDateFormat("yyyy-MM-dd");
//	    	  String creatdate2=sDateFormat2.format(sDate1);

					  
		if(rootDirId == null) {		//如果目录不存在则自动创建一个LoreDir
	
			String courseName = openCourse.getBzzCourse().getName(); 
			//System.out.println(rootDirId);
			String note = "课程" + courseName + "的知识点目录";
///			String creatdate = DateFormat.getDateTimeInstance().format(new Date());
			
		     java.util.Date sDate = new java.util.Date();
	    	 SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	 String creatdate=sDateFormat.format(sDate);
			// out.println(creatdate);
	
//			  Date dt = new Date();
//                String[] parts = str.split("-"); 
		
			try{
			testManage.addLoreDir(courseName,note,"0",creatdate,courseId);
			}catch (Exception e2){	
			  e2.printStackTrace();
			}
			rootDirId = testManage.getLoreDirByGroupId(courseId).getId();
		}
		if(loreDirId == null) {
			//loreDirId = "0";			
			loreDirId = rootDirId;
			
		}

		//System.out.println("rootDirId"+rootDirId);
		//System.out.println(loreDirId);
		LoreDir loreDir = testManage.getLoreDir(loreDirId);
		//--2013.06.19 修改在线考试如果管理端修改名字则去更新数据库
		if(rootDirId.equals(loreDir.getId())&&!loreDir.getName().equals(openCourse.getBzzCourse().getName())){
			int i=testManage.updateLoreDirName(openCourse.getBzzCourse().getName(),loreDirId);
			if(i==1){
				loreDir.setName(openCourse.getBzzCourse().getName());
			}
		}
					%>



<%	
		List subLoreDirList = loreDir.getSubLoreDirList();
		List loreList = loreDir.getLoreList();
		String loreDirNameDetail = loreDir.getName();
		String loreDirName = loreDir.getName();
		if(loreDirName==null||("").equals(loreDirName)||loreDirName.equals("null")){
		loreDirName="默认目录";
		}else if(loreDirName.length()>16){
			loreDirName=loreDirName.substring(0,14)+"...";
		}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点目录</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
             <!-- <tr>
                <td height="86" valign="top" background="images/top_01.gif"> <img src="images/zsd.gif" width="217" height="86"></td>
              </tr>-->
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                            <td width="100" valign="top" class="text3" style="padding-top:25px">考点目录</td>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  <tr>
                            
                      <td><img src="images/wt_04.gif" width="604" height="13"></td>
                          </tr>
                          <tr>
                            <td background="images/wt_05.gif"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="38" valign="bottom"><img src="images/wt_07.gif" width="38" height="25"></td>
                                  <td valign="bottom" class="text1" title="<%=loreDirNameDetail %>">当前目录：<%=loreDirName%>  <br/>   </td>
                        
                        
                            <td>
                             <a href="lore_store_question_batch_addexe.jsp?loreDirId=<%=loreDirId%>" class="tj">[批量上传题目]</a> </td>
                            </tr>
                            <tr>
                            <td></td>
                            <td></td>
                            <td >
                             <a href="lore_store_question_lore.jsp?loreDirId=<%=loreDirId %>" class="tj">[进入当前目录题库]</a></td>
                          
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="images/wt_08.gif" width="572" height="11"></td>
                          </tr>
                          <tr>
                            <td background="images/wt_10.gif"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <%
                            	for(int i=0; i<subLoreDirList.size(); i++) {
                            		LoreDir dir = (LoreDir)subLoreDirList.get(i);
                            		String dirId = dir.getId();
                            		String dirName = dir.getName();
                            %>
                                <tr>
                                  <td width="10%" align="center"><img src="images/wtxb1.gif" width="25" height="35"></td>
                                  <td>
                                  	<a href="lore_dir_list.jsp?loreDirId=<%=dirId%>"><%=dirName%></a>&nbsp;&nbsp;&nbsp;
                                  	<a href="lore_dir_edit.jsp?id=<%=dirId%>&loreDirId=<%=loreDirId%>" class="tj">[编辑]</a>
                                  	<a href="javascript:if(confirm('确定删除知识点目录吗？')) location.href='lore_dir_delete.jsp?id=<%=dirId%>&loreDirId=<%=loreDirId%>'" class="tj">[删除]</a>
                                  </td>
                                </tr>
                            <%
                            	}
                            	
                            	for(int i=0; i<loreList.size(); i++) {
                            		Lore lore = (Lore)loreList.get(i);
                            		String loreId = lore.getId();
                            		String loreName = lore.getName();
                            %>
                                <tr>
                                  <td align="center"><img src="images/wtxb2.gif" width="25" height="35"></td>
                                  <td>
                                  <!-- <a href="#" onclick="javascript:window.open('lore_info.jsp?id=<%=loreId%>','newwindow', 'height=300, width=600, toolbar=no , menubar=no, scrollbars=yes, resizable=no, location=no, status=no')">
                                  	</a>  -->	
                                  	<span style="font-size:12px;"><%=loreName%></span>
                                  	&nbsp;&nbsp;&nbsp;
                               <!--   	<a href="lore_edit.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>" class="tj">[编辑]</a>-->  
                                	<a href="javascript:if(confirm('确定删除知识点吗？')) location.href='lore_delete.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>'" class="tj">[删除]</a>
                                  	<a href="lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>" class="tj">[进入题库]</a>
                                  </td>
                                </tr>
                            <%
                            	}
                            %>
                       
                            
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table></td>
                          </tr>
                          <tr>
                            
                      <td><img src="images/wt_06.gif" width="604" height="11"></td>
                          </tr>
                    <tr>
                      <td align="center">
                      <%
                      	if(!loreDirId.equals(rootDirId)) {
                      %>
                      <a href="lore_dir_list.jsp?loreDirId=<%=loreDir.getFatherDir()%>" class="tj">[返回上层目录]</a>
                      <%
                      	}
                      %>
                      <a href="lore_add.jsp?loreDirId=<%=loreDirId%>" class="tj">[添加新知识点]</a>
                      <a href="lore_dir_add.jsp?fatherDir=<%=loreDirId%>" class="tj">[添加新目录]</a> 
                     
                      
                      </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br/>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</body>
<%
	} catch (Exception e) {
		e.printStackTrace();
		out.print(e.toString());
	}
%>
</html>
