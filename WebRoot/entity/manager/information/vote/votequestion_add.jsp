<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>添加题目</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script>
		
		
		var bSubmit=false;
		var bLoaded=false;
		function pageGuarding()
		{
        var reg = /^(0|[1-9][0-9]{0,2}|1000)$/;
        var qOrd=document.getElementById("questionOrder");
        if(qOrd.value){
        if(!reg.test(qOrd.value)){
          alert("顺序号限制为0-1000内的正整数！");
          document.getElementById("questionOrder").value="";
          document.getElementById("questionOrder").focus();
          return false;
        }
        }else{
        alert("请输入顺序号！");
        return false;
        }
        if(document.getElementById('question').value !='5' ){
         /*如果为管理题目 设置  lzh*/
        
         var regs = /^(?:[1-9]?\d|100)$/; 
        /*正则表达式  lzh*/
         var weight=document.getElementById("weight");
        if(weight.value !=null && weight.value !=""){
        if(!regs.test(weight.value)){
          alert("权重为0-100内的正整数！");
          return false;
          }
        }
        }
        
//			var oEditor = FCKeditorAPI.GetInstance('body') ;
 //      var acontent=oEditor.GetXHTML();
 		var acontent = document.getElementById('body');
       	if(acontent.value == null || acontent.value==""){
       		alert("内容不能为空！");
					return;
       	}
       	if(acontent.value.length > 500)
			{
				alert("内容不得多于500个字，请重新填写!");
				return false;
			}
			var qType=document.getElementById("question_type");
			var index=qType.selectedIndex;
			if(qType.options[index].value!='3' && qType.options[index].value!='4' && qType.options[index].value!='5'){//非主观题加选项判断
			for(k=0;k<15;k++)
			{
				if(document.getElementById("tr"+k).style.display=="none")
				{
					itemNum=k;
					break;
				}
			}
			if(itemNum<1)
			{
				alert("没有选项!");
				return false;
			}
			
			for(k=0;k<15;k++)
			{
				if(document.getElementById("tr"+k).style.display !="none")
				{
					var t= document.getElementById("item"+(k+1)).value;
					if(t==null ||t==""){
						alert("选项"+(k+1)+"不能为空！");
						return false;
					}
					else if(t.length>50)
					{
						alert("选项"+(k+1)+"长度不能大于50！");
						return false;
					}
				}else{
					break;
				}
			}
			
			}else{
			  itemNum=0;//主观题选项个数赋为0
			}
			document.paperinfo.itemNum.value=itemNum;
			
			document.paperinfo.submit();
			document.all.sub.style.display="none";
		}
		
		function questionChan(selectedValue){
		if(selectedValue=='3'|| selectedValue=='4' || selectedValue=='5'){//隐藏添加删除选项按钮
		document.getElementById("opa").style.display="none";
		}else{
		 document.getElementById("opa").style.display="block";
		}
		if(selectedValue=='3'|| selectedValue=='4'){
		  document.getElementById("score").style.display="none";
		}else{
		  document.getElementById("score").style.display="none";
		}
			if(selectedValue =='5'){
			  document.getElementById("trWeight").style.display="none";
			  document.getElementById("trIsRequirde").style.display="none";
			  document.getElementById("trIsCustom").style.display="none";
		}else{
			document.getElementById("trWeight").style.display="";
			document.getElementById("trIsRequirde").style.display="";
			document.getElementById("trIsCustom").style.display="";
		}
		for(var k=0;k<15;k++){
		 if(selectedValue=='3'|| selectedValue=='4'|| selectedValue=='5'||k>=4){
		   document.getElementById("tr"+k).style.display="none";
		   
		 }else{
		   document.getElementById("tr"+k).style.display="block";
		 }
		}
		}
			function flag(id){
		 var regs = /^(?:[1-9]?\d|100)$/; 
		if(!regs.test(id)){
			alert("只能输入0-100内的正整数");
			}
		}
</script>
	</head>

	<body leftmargin="0" topmargin="0" class="scllbar">
		<s:if test="status=='add'">
			<form name='paperinfo'
				action='/entity/information/prVoteQuestion_courseVote.action?bean.peVotePaper.id=<s:property value="peVotePaper.id"/>&status=add'
				method='post' class='nomargin' onsubmit="">
		</s:if>
		<s:else>
			<form name='paperinfo'
				action='/entity/information/prVoteQuestion_addQuestion.action?bean.peVotePaper.id=<s:property value="peVotePaper.id"/>'
				method='post' class='nomargin' onsubmit="">
		</s:else>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<div class="content_title" id="zlb_title_start">
						添加调查问卷题目
						<input type="hidden" name="peVotePaper_id"
							value="<s:property value="peVotePaper_id"/>" />
					</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder_zlb">

					<div class="cntent_k" id="zlb_content_start">
						<table width="96%" border="0" cellpadding="0" cellspacing="0">
							<input type=hidden name="paper_id"
								value="<s:property value="peVotePaper.id"/>">
							<input type=hidden name="itemNum" value="4">
							<tr valign="bottom" class="postFormBox">
								<td valign="bottom" nowrap="nowrap">
									<span class="name">题目类型：</span>
								</td>
								<td>
									<select name="question" id="question_type" class="input6303"
										onchange="questionChan(this.options[this.selectedIndex].value)">

										<s:iterator value="questionTypeList">
											<option value="<s:property value="code"/>">
												<s:property value="name" />
											</option>
										</s:iterator>
									</select>
									&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							  <tr valign="bottom" class="postFormBox" id="trIsRequirde">
								<td valign="bottom" nowrap="nowrap">
									<span class="name">是否必填：</span>
								</td>
								<td> 
									 <input type="radio"  name="bean.isRequirde" id="isRequirde" value="1" />是 &nbsp;&nbsp;
									 <input type="radio"  checked="checked"  name="bean.isRequirde" id="notIsRequirde" value="2" />否
								</td>
							</tr>
                      <tr valign="bottom" class="postFormBox" id="trOrder">
								<td valign="bottom" nowrap="nowrap">
									<span class="name">顺序号：</span>
								</td>
								<td> 
								<input class="textinput" type="text" id="questionOrder"  name="bean.questionOrder" />
                                       (只能输入0-1000内的正整数)
								</td>
							</tr>
							<tr valign="bottom" class="postFormBox" id="trWeight">
								<td valign="bottom" nowrap="nowrap">
									<span class="name">权重：</span>
								</td>
								<td> 
								<input class="textinput" type="text" id="weight"  name="bean.weight" /> % &nbsp;&nbsp;(只能输入0-100内的正整数)
                                     
								</td>
							</tr>
							

							<tr valign="bottom" class="postFormBox">
								<td valign="top" nowrap="nowrap">
									<span class="name">题目内容：</span>
								</td>
								<td>
									<textarea class="smallarea" name="bean.questionNote" cols="70"
										rows="12" id="body"></textarea>

									<!--img src="../images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="top.showHelpInfo('该新闻类型的介绍')" onmouseout="top.showHelpInfo()"-->
								</td>
							</tr>
							<tr id="score" class="postFormBox" style="display:none">
								<td>
								<span class="name">分值：</span>
								</td>
								<td> 
								<input class="textinput" type="text" id="score"  name="bean.score" /> (只能输入0-100内的正整数)
                                     
								</td>
							</tr>

							<%
								for (int i = 0; i < 15; i++) {
							%>
							<tr id="tr<%=i%>" valign="bottom" class="postFormBox"
								<%if(i>=4) out.print("style='display:none'"); %>>
								<td valign="bottom" nowrap="nowrap">
									<span class="name">选项<%=i + 1%>:</span>
								</td>
								<td>
									<input type=text id="item<%=i + 1%>" name="bean.item<%=i + 1%>"
										class=selfScale >
									&nbsp;&nbsp;&nbsp;
								</td>
								<td valign="bottom" nowrap="nowrap">
									<span class="name">分值</span>
								</td>
								<td>
									<input type=text onchange="flag(this.value);" id="itemScore<%=i + 1%>" name="bean.itemScore<%=i + 1%>"
										class=selfScales size="6" maxlength="3">
									&nbsp;&nbsp;&nbsp;
								</td>
							</tr>

							<%
								}
							%>
							 <tr style="display:" valign="bottom" class="postFormBox" id="trIsCustom" style="display:">
								<td valign="bottom" nowrap="nowrap">
									<span class="name">是否添加自定义回答：</span>
								</td>
								<td> 
									 <input type="radio"  name="bean.isCustom" id="isCustom" value="1" />是 &nbsp;&nbsp;
									 <input type="radio" checked="checked" name="bean.isCustom" id="notCustom" value="2" />否
								</td>
							</tr>
							<tr id="opa">
								<td colspan=2 align=center>
									<table>
										<tr>
											<td>
												<span class="norm"
													style="width: 100px; height: 15px; padding-top: 3px"
													onmouseover="className='over'"
													onmouseout="className='norm'"
													onmousedown="className='push'" onmouseup="className='over'"
													onclick="javascript:deleteNewSelect();"><span
													class="text">[删除选项]</span> </span>
											</td>
											<td align=center>
												<span class="norm"
													style="width: 100px; height: 15px; padding-top: 3px"
													onmouseover="className='over'"
													onmouseout="className='norm'"
													onmousedown="className='push'" onmouseup="className='over'"
													onclick="javascript:addNewSelect();"><span
													class="text">[添加新选项]</span> </span>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<script>
									function addNewSelect()
									{
										for(k=0;k<15;k++)
										{
											if(document.getElementById("tr"+k).style.display=="none")
											{
												document.getElementById("tr"+k).style.display="block";
												break;
											}
										}
										
									}
									function deleteNewSelect()
									{
										if(confirm("你确认要删除最后一个选项吗?"))
										{
											
											for(k=14;k>=0;k--)
											{	
												if(document.getElementById("tr"+k).style.display!="none" && k>1)
												{
													document.getElementById("tr"+k).style.display="none";
													document.getElementById("item"+(k+1)).value="";
													document.getElementById("itemScore"+(k+1)).value="";
													break;
												} else if (document.getElementById("tr"+k).style.display!="none" && k<=1) {
													alert("选项最少有两项，不能再删除！");	
													break;	
												}
											}
										}
										
									}
								</script>
						</table>
					</div>

				</td>
			</tr>
			<tr>
				<td align="center" id="pageBottomBorder_zlb">
					<div class="content_bottom">
						<table width="98%" height="100%" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td align="center" valign="middle">
									<span class="norm" id="sub"
										style="width: 46px; height: 15px; padding-top: 3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:pageGuarding();"><span class="text">提交</span>
									</span>
								</td>
								<td align="center" valign="middle">
									<span class="norm"
										style="width: 46px; height: 15px; padding-top: 3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:history.back();"><span class="text">返回</span>
									</span>
								</td>
							</tr>
						</table>
				</td>
			</tr>

		</table>

		</form>
		<script>bLoaded=true;</script>
	</body>
	<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 
<%--
var oFCKeditor = new FCKeditor( 'body' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor.ReplaceTextarea() ; --%>
//--> 
</script>
</html>
