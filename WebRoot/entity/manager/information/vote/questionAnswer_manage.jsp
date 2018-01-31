<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <title>调查问卷</title>
	<head>
		<title></title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<link href="../css/style.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/css.css" rel="stylesheet" type="text/css">
	<script>
	function cfmdel(que,odr)
	{
		var id= document.getElementById("peVotePaper").value;
		if(confirm("您确定要删除本题目吗？")) {
			window.navigate('/entity/information/prVoteQuestion_courseVote.action?bean.id='+que+'&bean.peVotePaper.id='+id+'&status=del&odr='+odr);
			}
	}
	</script>	
	</head>
  
  <body eftmargin="0" topmargin="0" class="scllbar">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td><div class="content_title" id="zlb_title_start">
											满意度调查管理  <input type="hidden" value="<s:property value="peVotePaper.id"/>" id="peVotePaper"/>
										</div id="zlb_title_end"></td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder_zlb">

					<div class="cntent_k" id="zlb_content_start">
						<table width="96%" border="0" cellpadding="0" cellspacing="0">
							<tr valign="bottom" class="postFormBox">
								<td valign="bottom">
									<span class="name">满意度调查名称：</span>
								</td>
								<td>
									<s:property value="peVotePaper.title"/>
								</td>
							</tr>						
							<tr valign="bottom" class="postFormBox" style="display:none;">
								<td valign="bottom">
									<span class="name">是否发布：</span>
								</td>
								<td>
									<s:property value="peVotePaper.enumConstByFlagIsvalid.name"/>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<!-- 列出题目 -->
				<table width="100%">
					
					<tr>
						<td>
							<table width=100%>
							  <s:iterator value="prVoteQuestionList" status="num">
								<tr width="60%">
									<td style="padding-top:0;margin-top:0;">
										<font color="blue">(<s:property value="#num.index+1"/>)</font>&nbsp;&nbsp;
										<s:property value="enumConstByFlagQuestionType.name"/> </br>
									</td>
									
								</tr>
								<tr>
									<td style="padding-top:0;margin-top:0;">
										<!-- <a href ="/entity/information/prVoteQuestion_answerDetail.action?bean.id=<s:property value="id"/>" title ="查看自定义回答"><s:property value="questionNote" escape="false"/></a>-->
										<a href ="/entity/information/prVoteQuestionAnswer.action?questionId=<s:property value="id"/>" title ="查看自定义回答"><s:property value="questionNote" escape="false"/></a> 
									</td>
									<td></td>
								</tr>
								<tr>
									<td colspan="2">
										<table>
											<tr>
										<s:if test="item1 != null">
												<td>
													<font style="color:blue;">1.</font>&nbsp;
													<s:property value="item1"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item2 != null">
												<td>
													<font style="color:blue;">2.</font>&nbsp;
													<s:property value="item2"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item3 != null">
												<td>
													<font style="color:blue;">3.</font>&nbsp;
													<s:property value="item3"/>&nbsp;
												</td>												
										</s:if>	
										<s:if test="item4 != null">
												<td>
													<font style="color:blue;">4.</font>&nbsp;
													<s:property value="item4"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item5 != null">
												<td>
													<font style="color:blue;">5.</font>&nbsp;
													<s:property value="item5"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item6 != null">
												<td>
													<font style="color:blue;">6.</font>&nbsp;
													<s:property value="item6"/>&nbsp;
												</td>												
										</s:if>		
										<s:if test="item7 != null">
												<td>
													<font style="color:blue;">7.</font>&nbsp;
													<s:property value="item7"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item8 != null">
												<td>
													<font style="color:blue;">8.</font>&nbsp;
													<s:property value="item8"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item9 != null">
												<td>
													<font style="color:blue;">9.</font>&nbsp;
													<s:property value="item9"/>&nbsp;
												</td>												
										</s:if>	
										<s:if test="item10 != null">
												<td>
													<font style="color:blue;">10.</font>&nbsp;
													<s:property value="item10"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item11 != null">
												<td>
													<font style="color:blue;">11.</font>&nbsp;
													<s:property value="item11"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item12 != null">
												<td>
													<font style="color:blue;">12.</font>&nbsp;
													<s:property value="item12"/>&nbsp;
												</td>												
										</s:if>		
										<s:if test="item13 != null">
												<td>
													<font style="color:blue;">13.</font>&nbsp;
													<s:property value="item13"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item14 != null">
												<td>
													<font style="color:blue;">14.</font>&nbsp;
													<s:property value="item14"/>&nbsp;
												</td>												
										</s:if>
										<s:if test="item15 != null">
												<td>
													<font style="color:blue;">15.</font>&nbsp;
													<s:property value="item15"/>&nbsp;
												</td>												
										</s:if>																																																																		
											</tr>
										</table>
									</td>
								</tr>
							</s:iterator>
							</table>
						</td>
					</tr>
				</table>
				<!-- ------ -->
			</tr>
			<tr>
				<td height="10" align="center" class="pageBottomBorder">
					<table width="98%" border="0" cellpadding="0"
						cellspacing="0"><tr>
							<td align="center" valign="middle">
								<span class="norm"
									style="width:150px;height:15px;padding-top:3px"
									onmouseover="className='over'" onmouseout="className='norm'"
									onmousedown="className='push'" onmouseup="className='over'"
									onclick="window.navigate('/entity/teaching/satisfactionPaperManager.action')">返回满意度调查管理</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>

		</table>  
  </body>
</html>
