<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>中国证券业协会</title>
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script language="javascript">
	function chkSubmit(){
		document.getElementById("sub").disabled = true;
	}
</script>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<form action="/entity/basic/teacherResource_addNote.action" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
	<input name="id" type="hidden" value="<s:property value="teacherInfo.id"/>"/>
	<input name="courseId" type="hidden" value="<s:property value="courseId"/>"/>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          	<td valign="top" class="bg3">
          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
              		<tr>
                		<td height="45" valign="top" background="../images/top_01.gif"></td>
              		</tr>
	              	<tr>
	                	<td align="center" valign="top">
	                  		<table width="765" border="0" cellspacing="0" cellpadding="0">
	                    		<tr>
	                      			<td height="65" background="../images/table_01.gif">
	                      				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                          				<tr>
	                            				<td align="center" class="text1"><img src="../images/xb3.gif" width="17" height="15"> 
	                              					<strong>教师简介</strong>
	                              				</td>
												<td width="300">&nbsp;</td>
	                          				</tr>
	                        			</table>
	                        		</td>
	                    		</tr>
	                    		<tr>
	                      			<td background="../images/table_02.gif" >
	                      				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
	                          				<tr>
	                            				<td class="text2">
	                            					<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
	                            						<tr>
	                            							<td class="neirong" width=20% class="text1" nowrap="nowrap">
																讲师姓名：
															</td>
															<td class="neirong" valign=top>
																<s:property value="teacherInfo.sg_tti_name"/>
															</td>
							  							</tr>
	                          							<tr>
	                          								<td class="neirong" valign=top width=20% class="text1">
																讲师简介：
															</td>
															<td valign=top>
																<textarea class="smallarea" cols="80" name="description" rows="12"></textarea>
																<font color="red" size ="2px" >提示：手动编辑或粘贴内容时，先点击源代码在进行操作。</font>
																<script language="JavaScript" src="../../../WhatyEditor/config.jsp"></script><br/>
																<script language="JavaScript" src="../../../WhatyEditor/edit.js"></script>
															</td>
							  							</tr>
	                            					</table>
												</td>
											</tr>
	     								</table>
	     							</td>
	    						</tr>
					        	<tr>
			 						<td>
			 						</td>
	                    		</tr>
								<tr>
									<td align=center>
										<input type=submit value="提交" id=sub>&nbsp;&nbsp;<input type=button value="返回" onclick="window.history.back()">
									</td>
								</tr>
	                  		</table>
	                  	</td>
	              	</tr>
	        	</table>
			</td>
		</tr>
	</table> 
</form>
</body>
<script type="text/javascript"> 

var oFCKeditor = new FCKeditor( 'description' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 
</script> 