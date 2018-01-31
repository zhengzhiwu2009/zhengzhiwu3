<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/ht清华大学继续教育学院charset=utf-8">
<title>清华大学继续教育学院</title>
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css" />
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="javascript" src="/entity/bzz-students/css/jquery.js"></script>
</head>
<script >
	var bLoaded=false; 
	function chkSubmit()
	{
	var title=document.getElementById("ziliaoname").value.length;
	var body=document.getElementById("content").value;
    var describes=document.getElementById("describes").value.length;
	
		if(title==0)
		{
			alert("请填写标题");
			return false;
		}
		if(body.length<2 || body== "<P>&nbsp;</P>")
		{
			alert("内容为空，还是多写几句吧？");
			return false;
		}
		if(describes>500){
		   alert("描述最多为500个字符");
			return false;
		}	
		document.getElementById("sub").disabled = true;
	}
	
	$(function() {
	            $("#add").click(function() {
	                var html = $("<input type='file' name='file'><label>&nbsp;&nbsp;&nbsp;&nbsp;</label>");
	                var button = $("<a id='add'>删除</a><br/>");
	                
	                $("#addFile").append(html).append(button);
	                
	                button.click(function()
	                {
	                    html.remove();
	                    button.remove();
	                })
	            })
       		})
</script>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="/sso/peResourceInteraction_addziLiao.action?type=KCZL" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()" enctype="multipart/form-data">
	<input type=hidden name=teachclassId value=<s:property value="#request.teachclassId" />>
	<input type=hidden name=coursename value=<s:property value="#request.coursename" />>
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
             
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>添加资料</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                      	<tr>
                            <td>
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
                            <tr>
                            <td class="neirong" width=20% nowrap="nowrap">
								<font size=2>题目：</font>
							</td>
							<td class="neirong" valign=top>
								<input type=text name=peResource.name class=input1 id="ziliaoname" size=60">
							</td>
						</tr>
						<tr>
                            <td class="neirong" width=20% nowrap="nowrap">
								<font size=2>资料分类：</font>
							</td>
							<td class="neirong" valign=top>
								<!--  
								<input type=text name=peResource.resetype class=input1 size=60">
								-->
								<select id="resrtype" name="resetype">
									<s:iterator value="resourceTypeList" id="type">
										<option value="<s:property value="#type.id"/>"><s:property value="#type.name"/></option>
									</s:iterator>
								</select>
							</td>
						 </tr>
						 <tr>
                            <td class="neirong" width=20% nowrap="nowrap">
							<font size=2> 颁布/发布时间：</font>
							</td>
							<td class="neirong" valign=top>
								<input type="text" style="width: 80px" name="peResource.replyDate" id="ktimestart" value="" />
								<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_a" style="border: 1px solid #551896; cursor: pointer;" title="Date selector" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
							</td>
						  </tr> 
						    <tr>
                            <td class="neirong" width=20% nowrap="nowrap">
							<font size=2>颁布/发布单位：</font>
							</td>
							<td class="neirong" valign=top>
								<input type=text name=peResource.fabuunit class=input1  size=60">
							</td>
						  </tr>
                          <tr>
                          		<td class="neirong" valign=top width=20%>
							<font size=2>上传资料：</font>
							</td>
							<td class="neirong" valign=top>
								<input type="file" name="file"/>&nbsp;&nbsp;&nbsp;&nbsp;<a id="add">添加</a><br/>
								<div id="addFile"></div>
							</td>
						  </tr>
						  <tr>
                       		<td class="neirong" valign=top width=20%>
									<font size=2>资料描述：</font>
							</td>
							<td class="neirong" valign=top>
								<textarea class="smallarea" cols="80" name="peResource.describe" id="describes" rows="12"></textarea>
							</td>
						 </tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
        <tr>
		 <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=submit value="提交" id="sub">&nbsp;&nbsp;<input type=button value="返回" onclick="history.go(-1)"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
      
</body>

		<script type="text/javascript">
	    Calendar.setup({
	        inputField     :    "ktimestart",     // id of the input field
	        ifFormat       :    "%Y-%m-%d",       // format of the input field
	        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
	        align          :    "Tl",           // alignment (defaults to "Bl")
	        singleClick    :    true
	    });
</script>


</html>
