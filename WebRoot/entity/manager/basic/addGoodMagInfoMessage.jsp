<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String newGoodStu_id=(String)session.getAttribute("newGoodStu_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<s:if test="stats=='success'">
<script language="javascript">
		Ext.onReady(function(){
				Ext.MessageBox.show({   
					title:'信息提示',
					msg:'修改成功！！',
					buttons:{ok:'关闭'},
					fn:function(ok){
						if(ok=='ok'){
							window.location='/entity/basic/peBzzGoodMag_viewDetail.action?bean.id=<%=newGoodStu_id%>';
						}
					}
				});
			});
</script>
</s:if>
<s:if test="stats=='failuer'">
<script language="javascript">
		Ext.onReady(function(){
				Ext.MessageBox.show({   
					title:'信息提示',
					msg:'修改失败！！',
					buttons:{ok:'关闭'},
					fn:function(ok){
						if(ok=='ok'){
							window.history.back();
						}
					}
				});
			});
</script>
</s:if>
<s:if test="stats=='nostats'">
<script language="javascript">
Ext.onReady(function(){
				Ext.MessageBox.show({   
					title:'信息提示',
					msg:'权限不够！！',
					buttons:{ok:'关闭'},
					fn:function(ok){
						if(ok=='ok'){
							window.history.back();
						}
					}
				});
			});
</script>	
</s:if>
<s:if test="stats=='formInvildate'">
<script language="javascript">
Ext.onReady(function(){
				Ext.MessageBox.show({   
					title:'信息提示',
					msg:'表单不可用！！',
					buttons:{ok:'关闭'},
					fn:function(ok){
						if(ok=='ok'){
							window.history.back();
						}
					}
				});
			});
</script>	
</s:if>			
</head>
<body>
</body>
</html>