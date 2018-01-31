<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
<!--

//-->
</script>
<html>
  <head>
    
    <title>调查问卷</title>
    
	<link href="/web/vote/css.css" rel="stylesheet" type="text/css">
		<script>
		function toresult(){
		var id = '<s:property value="peVotePaper.id"/>' ;
		window.location="/entity/first/firstPeVotePaper_voteResult.action?bean.id=" + id+"&togo=<s:property value='togo'/>";
		
		}
		
		function checkall(){
		   var temp=0;
		   var subResult="";
		
		   var acontents = document.getElementsByName('questionNV');
		   for(var i=0;i<acontents.length;i++){
		   if(acontents[i].value){
		   if(acontents[i].value.length > 500)
			{
				alert("内容不得多于500个字，请重新填写!");
				return false;
			}else{
		     temp++;
		     subResult=subResult+acontents[i].className+'@'+acontents[i].value+'#';
		   }
       	   }
		   }
		   if(subResult){
		   		document.getElementById('questionNoteValue').value=subResult;
		   }
			var inputArray=document.getElementsByTagName("input");  
			
			//for(var i=0;i<inputArray.length;i++){
				//if((inputArray[i].type=="checkbox"||inputArray[i].type=="radio")&& inputArray[i].checked){
				//	temp++;
					if(confirm("您确定要提交吗")){
					var questionList = $('input[name="questionRequired"]');
					for(var j=0;j<questionList.length;j++){
						if($(questionList[j]).val()=='1'){
							if($(questionList[j]).next().val()=='1'){
								var answerFlag = false;
								//判断单选按钮
								var thisTr = '';
								for(var i=0;i<15;i++){
									thisTr+='.next()';
									var answerTr = eval('$(questionList[j]).parent().parent()'+thisTr);
									if($(answerTr).attr('name')=='answer'){
										var answerTd = $(answerTr).children()[0];
										for(var t=0;t<$(answerTd).children().length;t++){
											if($($(answerTd).children()[t]).attr('type')=='radio'){
												if($($(answerTd).children()[t]).attr('checked')=='checked'){
													answerFlag = true;
												}
											}
										}
									}else{
										break;
									}
								}
								if(answerFlag==false){
									alert('请填写必填单选题');
									return;
								}
							}else if($(questionList[j]).next().val()=='2'){
								var answerFlag = false;
								//判断多选按钮
								var thisTr = '';
								for(var i=0;i<15;i++){
									thisTr+='.next()';
									var answerTr = eval('$(questionList[j]).parent().parent()'+thisTr);
									if($(answerTr).attr('name')=='answer'){
										var answerTd = $(answerTr).children()[0];
										for(var t=0;t<$(answerTd).children().length;t++){
											if($($(answerTd).children()[t]).attr('type')=='checkbox'){
												if($($(answerTd).children()[t]).attr('checked')=='checked'){
													answerFlag = true;
												}
											}
										}
									}else{
										break;
									}
								}
								if(answerFlag==false){
									alert('请填写必填多选题');
									return;
								}
							}else if($(questionList[j]).next().val()=='3'){
								var answerFlag = false;
								//判断多行文本
								var thisTr = '';
								for(var i=0;i<15;i++){
									thisTr+='.next()';
									var answerTr = eval('$(questionList[j]).parent().parent()'+thisTr);
									if($(answerTr).attr('name')=='answer'){
										var answerTd = $(answerTr).children()[0];
										for(var t=0;t<$(answerTd).children().length;t++){
											if($(answerTd).children()[t].tagName=='TEXTAREA'){
												if($($(answerTd).children()[t]).val()!=''){
													answerFlag = true;
												}
											}
										}
									}else{
										break;
									}
								}
								if(answerFlag==false){
									alert('请填写必填主观题');
									return;
								}
							}else if($(questionList[j]).next().val()=='4'){
								var answerFlag = false;
								//判断单选按钮
								var thisTr = '';
								for(var i=0;i<15;i++){
									thisTr+='.next()';
									var answerTr = eval('$(questionList[j]).parent().parent()'+thisTr);
									if($(answerTr).attr('name')=='answer'){
										var answerTd = $(answerTr).children()[0];
										for(var t=0;t<$(answerTd).children().length;t++){
											if($($(answerTd).children()[t]).attr('type')=='text'){
												if($($(answerTd).children()[t]).val()!=''){
													answerFlag = true;
												}
												var score =$($(answerTd).children()[t]).val();
												
											}
										}
									}else{
										break;
									}
								}
								if(answerFlag==false){
									alert('请填写必填打分题');
									return;
								}
							}
							
						}
					}
					
					
					document.forms.vote.submit();
					return ;	
					}else {
						return false ;
					}
				//}
			//}
			//if(temp==0){
			//	alert("请填写调查问卷");
			//	return false ;
			//}else {
			//	if(confirm("您确定要提交吗")){
			//		document.forms.vote.submit();	
			//		return;
			//	}else {
			//		return false ;
			//	}
			//}
		}
		function reSet(){
		document.forms.vote.reset();
		var acontents = document.getElementsByName('questionNV');
		   for(var i=0;i<acontents.length;i++){
		   acontents[i].value="";
		   }
		}
		</SCRIPT>
  </head>
  
  <body topmargin="0" leftmargin="0" bgcolor="#EEEEEE">
  <form name="vote" method=post action="/entity/first/firstPeVotePaper_vote.action" >
  <input type="hidden" id="questionNoteValue" name="questionNoteValue" />
  <input type="hidden" name="opencourseId" value="<s:property value="opencourseId"/>" />
  <%@ include file="/web/form-token.jsp" %>
  <table width="1000" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#8dc6f6" bordercolorlight="#FFFFFF">
  <tr>
    <td height="158" align="center" background="/entity/manager/images/votebg.gif" class="16title"><font size=6><s:property value="peVotePaper.pictitle"/></font></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        
        <tr>
          <td align="center" valign="bottom" class="14title"><s:property value="peVotePaper.title"/></td>
        </tr>
        <s:if test="togo!=1">
        <tr>
          <td align="center" valign="bottom" class="12content"><s:date name="peVotePaper.startDate" format="yyyy-MM-dd" />~~<s:date name="peVotePaper.endDate" format="yyyy-MM-dd" />
          &nbsp;&nbsp;<s:if test="pastDue==1">还未开始</s:if><s:elseif test="pastDue==2">已过期</s:elseif> </td>
        </tr>
        </s:if>
        <tr>
          <td align="center"><img src="/entity/manager/images/votebian.gif" width="417" height="4"></td>
        </tr>
        <tr>
          <td class="12texthei"><s:property value="peVotePaper.note" escape="false"/>
</td>
        </tr>
        
        <input type="hidden" name="peVotePaper.id" value="<s:property value="peVotePaper.id"/>" />
        <tr>
          <td><br/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="12content">
            	<%@ include file="/web/vote/vote_include.jsp" %>
              <tr> 
                <td bgcolor="#F9F9F9" height=10></td>
              </tr>
              
              <s:if test="peVotePaper.enumConstByFlagCanSuggest.code == 1">
             <tr> 
                <td bgcolor="#F9F9F9">请输入您的意见或建议！</td>
              </tr>
              <tr> 
                <td bgcolor="#F9F9F9"><textarea name="suggest" cols="65" rows="6"></textarea></td>
              </tr>              
              </s:if>
              <s:else>
              <tr> 
                <td bgcolor="#F9F9F9"></td>
              </tr>              
              </s:else>
 
            </table></td>
        </tr>
        <input type="hidden" name="togo" value="<s:property value='togo'/>">
        <tr>
          <td height="40" align="center" valign="bottom" colspan=5> 
          <s:if test="canVote == 1">
          	<s:if test="pastDue == 0">
          	<input name="Submit1" type="button" id="Submit1" value="提交结果" onclick="checkall()" >
          	<input name="Submit2" type="button" id="Submit2" value="重  填" onclick="reSet()">
          	</s:if>
          </s:if>
          <s:else> 您已经做过本调查问卷！
          </s:else>
           <s:if test="togo!=1">
            <input  type="button" id="Submit2" value="查看结果"  onclick="toresult();">
            </s:if>
          </td>
          
         </tr>          
    
              </table><br/></td>
  </tr>
</table>
     </form>
  </body>
</html>
