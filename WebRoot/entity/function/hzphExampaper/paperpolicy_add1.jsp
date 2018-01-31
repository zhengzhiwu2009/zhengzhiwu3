<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加试卷</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<SCRIPT src="../js/add.js"></SCRIPT>
<script src="../js/check.js"></script>
<script>
	function doCommit() {
	if(document.getElementById("ptitle").value.replace(/[ ]/g,"")==""){
		alert("请输入标题");	
		document.getElementById("ptitle").value="";
		return false；
	}
	if(document.getElementById("note").value.replace(/[ ]/g,"")==""){
		alert("请输入备注");	
			return false;
	}
		if(document.getElementById("DANXUAN_num").value==""){
			alert("请输入单选数量");	
			return false;
		}
		else if(!isInt(document.getElementById("DANXUAN_num").value)){
			alert("数量请选择数字");	
			return false;
		}
		if(document.getElementById("DUOXUAN_num").value==""){
			alert("请输入多选数量");	
			return false;
			}
			else if(!isInt(document.getElementById("DUOXUAN_num").value)){
			alert("数量请选择数字");	
			return false;
		}
		if(document.getElementById("PANDUAN_num").value==""){
			alert("请输入判断题");	
			return false;
		}
		else if(!isInt(document.getElementById("PANDUAN_num").value)){
			alert("数量请选择数字");	
			
			return false;
		}
		if(document.getElementById("TIANKONG_num").value==""){
			alert("请输入填空题");	
			return false;
		}
		else if(!isInt(document.getElementById("TIANKONG_num").value)){
			alert("数量请选择数字");	
			
			return false;
		}
		if(document.getElementById("WENDA_num").value==""){
			alert("请输入问答题");	
			return false;
		}
		else if(!isInt(document.getElementById("WENDA_num").value)){
			alert("数量请选择数字");	
			
			return false;
		}
		var tempQuestionNum=0;
		var questiontype=['DANXUAN','DUOXUAN','PANDUAN','TIANKONG','WENDA'];
		
		for(var i=0;i<questiontype.length;i++){
	//	alert(document.getElementById(questiontype[i]+"_total").innerHTML);
	//	alert(document.getElementById(questiontype[i]+"_num").value+"=========wwwwwwww");
		tempQuestionNum+=Number(document.getElementById(questiontype[i]+"_num").value);
		//alert("tempQuestionNum="+tempQuestionNum);
			if(Number(document.getElementById(questiontype[i]+"_total").innerHTML)<Number(document.getElementById(questiontype[i]+"_num").value)){
			alert("请重新选择题目数量，数量不能大于当前题库当前类型总题数");
			return false;
			}
		}
		if(tempQuestionNum <1){
			alert("当前总题目数量为零，请修正此错误");
			return false;
		}
		/*
		if(document.select.questiontype_select.length<1)
		{
			alert("请选择题型!");
			document.forms["select"].questiontype_list.focus();
			return;
		}
		*/
		if(document.getElementsByName("lore").length<1)
		{ 
			if(confirm("您确定不选择知识点吗")){	
			}else{
				return false;
			}
			//document.forms["select"].lore_list.focus();
	
		}else{
			var lore =  document.getElementsByName("lore");
			var b = true;
			for(var i=0;i<lore.length;i++){
				if(lore[i].checked){
					b = false;
					break;
				}
			}
			if(b){
				alert("请选择知识点!");
				//document.forms["select"].lore_list.focus();
				return;
			}
		}
	    SelectTotal('lore');
	    SelectTotal('questiontype_select');
	    document.select.submit();
	}
	var store = new Array();
	function addRow(str) {
		var arr = str.split("::");
		for(i=0; i<arr.length; i++) {
			var cellArr = arr[i].split("|");
			var id = cellArr[0];
			var name = cellArr[1];
			var x=store.length;
			var st=0;
			for(f=0;f<x;f++)
			{
				if(id==store[f])
				{
					st=1;
					break;
				}
			}
			if(st==1)
				continue;
			store[x]=new Object();
			store[x] = id;
			//添加1行			
			var newTr = Tbl.insertRow(Tbl.rows.length-1);
			
			//添加2列			
			var newTd0 = newTr.insertCell();			
			var newTd1 = newTr.insertCell();
			var newTd2 = newTr.insertCell();
			
			//设置列内容和属性
			newTd0.align = 'right';
			newTd0.innerHTML = '<INPUT name="lore" type="checkbox" value='+id+' checked>';
			newTd1.innerHTML = '&nbsp;';
			newTd2.innerHTML = name;
		}
	}
</script>
</head>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;	
	}
%>
<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
			String [] questiontypes={"DANXUAN","DUOXUAN","PANDUAN","TIANKONG","WENDA"};
%>
<%	

		String danXuanNum =  testManage.getCountByType(courseId,"DANXUAN","OnlineExam");
		String duoXuanNum = testManage.getCountByType(courseId,"DUOXUAN","OnlineExam");
		String panDuanNum = testManage.getCountByType(courseId,"PANDUAN","OnlineExam");
		String tianKong = testManage.getCountByType(courseId,"TIANKONG","OnlineExam");	
		String wenDa = testManage.getCountByType(courseId,"WENDA","OnlineExam");	
		
%>
<form name='select' action='paperpolicy_addexe.jsp' method='post' class='nomargin' onsubmit="">
<%
	for(int i=0;i<questiontypes.length;i++)
	{
%>
<input type="hidden" name="questiontype" value=<%=questiontypes[i]%>>
<%
	}
%>
<input type="hidden" name="type" value="OnlineExam">
<input type="hidden" name="teachclass_id" value=<%=teachclass_id%>>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/khcy.jpg" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                            <td width="200" valign="top" class="text3" style="padding-top:25px">添加组卷策略信息</td>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          		<tr>
                            	<td background="images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">标题：<font color=red>*</font></td>
		                                  <td valign="top" align="left"><input name="title" id="ptitle" type="text" size="30" maxlength="50"></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">备注：<font color=red>*</font></td>
		                                  <td valign="top"><textarea name="note" id="note" rows="10" cols="49"></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          <!-- 
                          	<tr>
                          	<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">选择题：</td>
		                                  <td valign="top"><textarea name="note" rows="10" cols="50"></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          	
                          	</tr>
                          	<tr>
                          	
                            	<td background="images/wt_10.gif">-->
                            	
                            	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  id="Tbl">
                                
						         <!--    <tr valign="bottom" class="postFormBox"> 
						              <td width="40%" align="right" valign="bottom" style="padding-bottom:10px"> 
						                <select name="questiontype_list" size="6"  class="selfScale" multiple>
						                  <option value="DANXUAN">单 选 题</option>
						                  <option value="DUOXUAN">多 选 题</option>
						                  <option value="PANDUAN">判 断 题</option> -->
						                  <!-- 
						                  <option value="TIANKONG">填 空 题</option>
						                  <option value="WENDA">问 答 题</option>
						                  <option value="YUEDU">阅读理解题</option>
						                   -->
						              <!--    </select>
						              </td>
						              <td align="center" valign="middle">
						              <p><a href="#" title="提交" class="tj" onclick="JavaScript:AddItem('questiontype_list','questiontype_select','')">&gt;&gt;</a></p>
						              <p><a href="#" title="提交" class="tj" onclick="JavaScript:DeleteItem('questiontype_select')">&lt;&lt;</a></p></td>
						              <td width="40%"  style="padding-bottom:10px"><select name="questiontype_select" size="6" class="selfScale" multiple>
						                </select></td>
						            </tr>
						             -->
						             <tr valign="bottom" class="postFormBox"> 
						              <td width="20%" nowrap="nowrap" align="right" valign="bottom" style="padding-right:15px"><span class="name">单选题数：<font color=red>*</font></span></td>
						              <td> &nbsp;&nbsp;&nbsp;<input id="DANXUAN_num" name="DANXUAN_num" type="text" class="selfScale"> &nbsp;总题数：<span id="DANXUAN_total" class="name"><%=danXuanNum%></span></td>
						              <input type="hidden" name="DANXUAN_lore_select" value="">
						            </tr>
						              <tr valign="bottom" class="postFormBox"> 
					              <td width="20%" nowrap="nowrap" align="right" valign="bottom" style="padding-right:15px"><span class="name">多选题数：<font color=red>*</font></span></td>
					              <td> &nbsp;&nbsp;&nbsp;<input id="DUOXUAN_num" name="DUOXUAN_num" type="text" class="selfScale"> &nbsp;总题数：<span id="DUOXUAN_total" class="name"><%=duoXuanNum%></span></td>
					               <input type="hidden" name="DUOXUAN_lore_select" value="">
					            </tr>
					             <tr valign="bottom" class="postFormBox"> 
					              <td width="20%" nowrap="nowrap" align="right" valign="bottom" style="padding-right:15px"><span class="name">判断题数：<font color=red>*</font></span></td>
					              <td> &nbsp;&nbsp;&nbsp;<input id="PANDUAN_num" name="PANDUAN_num" type="text" class="selfScale"> &nbsp;总题数：<span id="PANDUAN_total" class="name"><%=panDuanNum%></span></td>
					             <input type="hidden" name="PANDUAN_lore_select" value="">
					            </tr>
					             <tr valign="bottom" class="postFormBox"> 
					              <td width="20%" nowrap="nowrap" align="right" valign="bottom" style="padding-right:15px"><span class="name">填空题数：<font color=red>*</font></span></td>
					              <td> &nbsp;&nbsp;&nbsp;<input id="TIANKONG_num" name="TIANKONG_num" type="text" class="selfScale"> &nbsp;总题数：<span id="TIANKONG_total" class="name"><%=tianKong%></span></td>
					             <input type="hidden" name="TIANKONG_lore_select" value="">
					            </tr>
					             <tr valign="bottom" class="postFormBox"> 
					              <td width="20%" nowrap="nowrap" align="right" valign="bottom" style="padding-right:15px"><span class="name">问答题数：<font color=red>*</font></span></td>
					              <td> &nbsp;&nbsp;&nbsp;<input id="WENDA_num" name="WENDA_num" type="text" class="selfScale"> &nbsp;总题数：<span id="WENDA_total" class="name"><%=wenDa%></span></td>
					             <input type="hidden" name="WENDA_lore_select" value="">
					            </tr>
					            
						           	<tr align="center" class="postFormBox">
						                <td  align="center" colspan="3">
						                <input type="button" name="up" value="请选择知识点" onClick="window.open('lore/lore_dir_list.jsp?courseID=<%=courseId %>','','height=600, width=900, top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')">
										</td>
						            </tr>
						  		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                           
						            
	                            	</table>
									</td>
                          		</tr>
                          		
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="images/wt_06.gif" width="604" height="11"></td>
                    </tr>
                    <tr>
                      <td align="center"><a href="#" title="提交" onclick="doCommit()" class="tj">[提交]</a>
                      <a href="#" title="返回" onclick=" javascript:window.history.back()" class="tj">[返回]</a></td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br/>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</form>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>