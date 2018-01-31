<%@ page language="java" pageEncoding="gb2312"%>
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0);  
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<script src="/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
			function closeWin(){
				var msg = '您确定要上传学习记录并退出课件观看?';
				var cw_window = window.parent.opener.windows['courseWin'];
			    if(cw_window){
					var temp_temp = cw_window.document.getElementById("temp");
					if(temp_temp){//IE
						if(confirm(msg)){
							temp_temp.value="2";//通过这种方式取消beforeunload事件的提示信息 
							var comRes = window.parent.API.LMSFinish("");
							if("failed" == comRes){
								if(confirm("学习记录上传失败，确定退出吗？")){
									window.parent.close();
								}
							}else{
								alert("您的学习记录已上传成功");
								window.parent.close();
							}
							window.parent.opener.location.reload();
						}
					}else{//Not IE,通过beforeunload提示信息
						if(confirm(msg)){
							window.parent.close();	
						}
					}
					return;
			    }else{
					window.parent.opener.location.reload();
					window.parent.close();
					window.parent.API.LMSFinish("");
			    }
			}
			
			function closeWin2(){
				window.parent.API.LMSFinish("");
			    window.parent.opener.location.reload();
			}
			
	</script>
	<style>
	*{ margin:0; padding:0}
	</style>
</head>
<body>
<div style="text-align:center;">
<div style="height:29px;margin:0 auto;text-align:right;padding-top:5px;">
	<span style="border:0px solid red;margin-top:2px;font-size:12pt;line-height:17px;color:red;float:left;padding-top: 2px;">&nbsp;&nbsp;&nbsp;提示：请点击右侧"上传记录并退出"按钮保存您的学习记录。</span>
	<!-- <input type="image" id="closeBtn" src="./images/closeButton.jpg"  title="关闭课件播放窗口" value="Submit" onclick="closeWin();"/> -->
	<input type="button" id="closeBtn" title="上传学习记录并退出" value="上传记录并退出" onclick="closeWin();"/>	
	<input type="button" id="closeBtn2" value="Submit" style="display:none;" onclick="closeWin2();"/>
</div>
</div>
</body>
</html>
