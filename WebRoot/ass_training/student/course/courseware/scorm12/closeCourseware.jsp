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
				var msg = '��ȷ��Ҫ�ϴ�ѧϰ��¼���˳��μ��ۿ�?';
				var cw_window = window.parent.opener.windows['courseWin'];
			    if(cw_window){
					var temp_temp = cw_window.document.getElementById("temp");
					if(temp_temp){//IE
						if(confirm(msg)){
							temp_temp.value="2";//ͨ�����ַ�ʽȡ��beforeunload�¼�����ʾ��Ϣ 
							var comRes = window.parent.API.LMSFinish("");
							if("failed" == comRes){
								if(confirm("ѧϰ��¼�ϴ�ʧ�ܣ�ȷ���˳���")){
									window.parent.close();
								}
							}else{
								alert("����ѧϰ��¼���ϴ��ɹ�");
								window.parent.close();
							}
							window.parent.opener.location.reload();
						}
					}else{//Not IE,ͨ��beforeunload��ʾ��Ϣ
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
	<span style="border:0px solid red;margin-top:2px;font-size:12pt;line-height:17px;color:red;float:left;padding-top: 2px;">&nbsp;&nbsp;&nbsp;��ʾ�������Ҳ�"�ϴ���¼���˳�"��ť��������ѧϰ��¼��</span>
	<!-- <input type="image" id="closeBtn" src="./images/closeButton.jpg"  title="�رտμ����Ŵ���" value="Submit" onclick="closeWin();"/> -->
	<input type="button" id="closeBtn" title="�ϴ�ѧϰ��¼���˳�" value="�ϴ���¼���˳�" onclick="closeWin();"/>	
	<input type="button" id="closeBtn2" value="Submit" style="display:none;" onclick="closeWin2();"/>
</div>
</div>
</body>
</html>
