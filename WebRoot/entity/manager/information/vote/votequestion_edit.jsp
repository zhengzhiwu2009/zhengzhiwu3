<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>修改题目</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script>
			<% String quesType = request.getParameter("quesType");
				String styleType = "";
				if(quesType !=null && !quesType.equals("")){
					if(quesType.equals("402880a91e635a011e635fcd2c0001")){
					styleType="none";
					}
				}
				%>
				
				
				 
		
		
		var bSubmit=false;
		var bLoaded=false;
		function pageGuarding()
		{
			 var reg = /^(0|[1-9][0-9]{0,2}|1000)$/;
        var qOrd=document.getElementById("questionOrder");
        if(qOrd.value){
        if(!reg.test(qOrd.value)){
          alert("顺序号限制为0-1000内的正整数");
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
//       var acontent=oEditor.GetXHTML();
		var acontent = document.getElementById('body');
       	if(acontent.value == null || acontent.value==""){
       		alert("内容不能为空！");
					return;
       	}
       	
       	if(acontent.value.length > 1000)
			{
				alert("内容不得多于1000个字，请重新填写!");
				return false;
			}
			var itemNum;
			var qType=document.getElementById("question_type");
			var index=qType.selectedIndex;
			if(qType.options[index].value!='3' && qType.options[index].value!='4' && qType.options[index].value!='5'){
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
		}
	
		function questionChan(selectedValue){
		if(selectedValue=='3'|| selectedValue=='4' || selectedValue=='5'){//隐藏添加删除选项按钮
		document.getElementById("opa").style.display="none";
		}else{
		 document.getElementById("opa").style.display="block";
		}
		if(selectedValue=='3'|| selectedValue=='4'){
		  document.getElementById("score").style.display="";
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
			document.getElementById("trIsCustom").style.display="none";
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
		<s:if test="status=='edit'">
			<form name='paperinfo'
				action='/entity/information/prVoteQuestion_courseVote.action?bean.id=<s:property value="bean.id"/>&status=edit'
				method='post' class='nomargin' onsubmit="">
		</s:if>
		<s:else>
			<form name='paperinfo'
				action='/entity/information/prVoteQuestion_editQuestion.action?bean.id=<s:property value="bean.id"/>'
				method='post' class='nomargin' onsubmit="">
		</s:else>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<div class="content_title" id="zlb_title_start">
						修改调查问卷题目 
					</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder_zlb">

					<div class="cntent_k" id="zlb_content_start">
						<table width="96%" border="0" cellpadding="0" cellspacing="0">
							<input type=hidden name="paper_id"
								value="<s:property value="peVotePaper.id"/>">
							<input type=hidden name="itemNum"
								value="<s:property value="peVotePaper.itemNum"/>">

							<tr valign="bottom" class="postFormBox">
								<td valign="bottom" nowrap="nowrap">
									<span class="name">题目类型：</span>
								</td>
								<td>
								 	<select name="question" disabled="disabled" id="question_type" class="input6303"
										onchange="questionChan(this.options[this.selectedIndex].value)">
										<s:set name="typecode"
											value="bean.enumConstByFlagQuestionType.code" />
										<s:iterator value="questionTypeList">
											<s:set name="type" value="code" />
											<option value="<s:property value="code"/>"
												<s:if test="#typecode == #type"> selected="selected"</s:if>>
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
								
									<input type="radio" name="bean.isRequirde"
										id="isRequirde" value="1" <s:if test="bean.isRequirde ==1"> checked="checked"</s:if>/>
									是 &nbsp;&nbsp;
									<input type="radio"  name="bean.isRequirde" id="notIsRequirde"
										value="2" <s:if test="bean.isRequirde ==2"> checked="checked"</s:if>/>
									否
								</td>
							</tr>
							<tr valign="bottom" class="postFormBox">
								<td valign="bottom" nowrap="nowrap">
									<span class="name">顺序号：</span>
								</td>
								<td>
									<input class="textinput" type="text" id="questionOrder"
										value="<s:property value="bean.questionOrder"/>"
										name="bean.questionOrder" />
									(只能输入0-1000内的正整数)
								</td>
							<tr valign="bottom" class="postFormBox" id="trWeight" style="display:<%=styleType %>"">
								<td valign="bottom" nowrap="nowrap">
									<span class="name">权重：</span>
								</td>
								<td>
									<input class="textinput" type="text" id="weight"
										value="<s:property value="bean.weight"/>" name="bean.weight" />% &nbsp;&nbsp;(只能输入0-100内的正整数)

								</td>
							</tr>
							<tr valign="bottom" class="postFormBox">
								<td valign="top" nowrap="nowrap">
									<span class="name">题目内容：</span>
								</td>
								<td>
									<textarea class="smallarea" name="bean.questionNote" cols="70"
										rows="12" id="body">
										<s:property value="bean.questionNote" />
									</textarea>

									<!--img src="../images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="top.showHelpInfo('该新闻类型的介绍')" onmouseout="top.showHelpInfo()"-->
								</td>
							</tr>
							<tr id="score" class="postFormBox" style="display:none">
								<td>
								<span class="name">分值：</span>
								</td>
								<td> 
								<input class="textinput" type="text" id="score"  name="bean.score" value="<s:property value="bean.score" />"/>
                                     
								</td>
							</tr>

							<s:if test="bean.item1 != null">
								<tr id="tr0" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr0" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项1:</span>
							</td>
							<td>
								<input type=text id="item1" name="bean.item1" class=selfScale
									value="<s:property value="bean.item1"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore1" name="bean.itemScore1" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore1"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item2 != null">
								<tr id="tr1" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr1" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项2:</span>
							</td>
							<td>
								<input type=text id="item2" name="bean.item2"  class=selfScale
									value="<s:property value="bean.item2"/>" size="3" maxlength="50"
								>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore2" name="bean.itemScore2" onchange="flag(this.value);" 
									  value="<s:property value="bean.itemScore2"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item3 != null">
								<tr id="tr2" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr2" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项3:</span>
							</td>
							<td>
								<input type=text id="item3" name="bean.item3" class=selfScale 
									value="<s:property value="bean.item3"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore3" name="bean.itemScore3" onchange="flag(this.value);" 
									  value="<s:property value="bean.itemScore3"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item4 != null">
								<tr id="tr3" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr3" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项4:</span>
							</td>
							<td>
								<input type=text id="item4" name="bean.item4" class=selfScale
									value="<s:property value="bean.item4"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore4" name="bean.itemScore4" onchange="flag(this.value);" 
									  value="<s:property value="bean.itemScore4"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item5 != null">
								<tr id="tr4" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr4" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项5:</span>
							</td>
							<td>
								<input type=text id="item5" name="bean.item5" class=selfScale
									value="<s:property value="bean.item5"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore5" name="bean.itemScore5" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore5"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item6 != null">
								<tr id="tr5" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr5" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项6:</span>
							</td>
							<td>
								<input type=text id="item6" name="bean.item6" class=selfScale
									value="<s:property value="bean.item6"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore6" name="bean.itemScore6" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore6"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item7 != null">
								<tr id="tr6" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr6" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项7:</span>
							</td>
							<td>
								<input type=text id="item7" name="bean.item7" class=selfScale
									value="<s:property value="bean.item7"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore7" name="bean.itemScore7" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore7"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item8 != null">
								<tr id="tr7" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr7" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项8:</span>
							</td>
							<td>
								<input type=text id="item8" name="bean.item8" class=selfScale
									value="<s:property value="bean.item8"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore8" name="bean.itemScore8" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore8"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item9 != null">
								<tr id="tr8" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr8" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项9:</span>
							</td>
							<td>
								<input type=text id="item9" name="bean.item9" class=selfScale
									value="<s:property value="bean.item9"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore9" name="bean.itemScore9" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore9"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item10 != null">
								<tr id="tr9" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr9" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项10:</span>
							</td>
							<td>
								<input type=text id="item10" name="bean.item10" class=selfScale
									value="<s:property value="bean.item10"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore10" name="bean.itemScore10" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore10"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item11 != null">
								<tr id="tr10" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr10" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项11:</span>
							</td>
							<td>
								<input type=text id="item11" name="bean.item11" class=selfScale
									value="<s:property value="bean.item11"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore11" name="bean.itemScore11" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore11"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item12 != null">
								<tr id="tr11" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr11" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项12:</span>
							</td>
							<td>
								<input type=text id="item12" name="bean.item12" class=selfScale
									value="<s:property value="bean.item12"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore12" name="bean.itemScore12" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore12"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item13 != null">
								<tr id="tr12" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr12" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项13:</span>
							</td>
							<td>
								<input type=text id="item13" name="bean.item13" class=selfScale
									value="<s:property value="bean.item13"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore13" name="bean.itemScore13" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore13"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item14 != null">
								<tr id="tr13" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr13" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项14:</span>
							</td>
							<td>
								<input type=text id="item14" name="bean.item14" class=selfScale
									value="<s:property value="bean.item14"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore14" name="bean.itemScore14" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore14"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
							<s:if test="bean.item15 != null">
								<tr id="tr14" valign="bottom" class="postFormBox">
							</s:if>
							<s:else>
								<tr id="tr14" valign="bottom" class="postFormBox"
									style='display: none'>
							</s:else>
							<td valign="bottom" width="20%">
								<span class="name">选项15:</span>
							</td>
							<td>
								<input type=text id="item15" name="bean.item15" class=selfScale
									value="<s:property value="bean.item15"/>" size="50"
									maxlength="50">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td valign="bottom" width="10%">
								<span class="name">分值:</span>
							</td>
							<td>
								<input type=text id="itemScore15" name="bean.itemScore15" onchange="flag(this.value);" 
									 value="<s:property value="bean.itemScore15"/>"
									size="3" maxlength="3">
								&nbsp;&nbsp;&nbsp;
							</td>
							</tr>
						
							 <tr valign="bottom" class="postFormBox" id="trIsCustom" style="display:">
								<td valign="bottom" nowrap="nowrap">
									<span class="name">是否添加自定义回答：</span>
								</td>
								<td> 
									 <input type="radio" <s:if test="bean.isCustom ==1">checked="checked"</s:if> name="bean.isCustom" id="isCustom" value="1" />是 &nbsp;&nbsp;
									 <input type="radio" <s:if test="bean.isCustom ==2">checked="checked"</s:if> name="bean.isCustom" id="notCustom" value="2" />否
								</td>
							</tr>
						

							<tr id="opa">
								<td colspan=2 align=center>
								<s:if test="bean.enumConstByFlagQuestionType.code !=3 &&bean.enumConstByFlagQuestionType.code !=4 && bean.enumConstByFlagQuestionType.code !=5">
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
									</s:if>
								</td>
							</tr>
							<script>
								  var num = "<s:property value="bean.enumConstByFlagQuestionType.code"/>";
								   if(num=='3'){//隐藏添加删除选项按钮
		                              document.getElementById("opa").style.display="none";
		                           }
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
									<span class="norm"
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
oFCKeditor.ReplaceTextarea() ; 
--%>
//--> 
</script>
</html>
