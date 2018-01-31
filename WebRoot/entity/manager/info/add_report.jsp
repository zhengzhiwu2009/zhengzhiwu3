<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加公告</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<script>
	var bSubmit=false
	var bLoaded=false;
	function doCommit() {		
		var content;
		if(FCKeditor_IsCompatibleBrowser()){
			var oEditor = FCKeditorAPI.GetInstance("reportInfo.note"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
		
		if(isNull(document.getElementById("reportInfo.title").value)){ 
		}else{
			alert("请填写报告题目!!");
			document.getElementById("reportInfo.title").focus();

			return false;
		}
		
		if(content.length <1){
			alert("请填写报告内容!");
			return false;
		}
		
		window.document.info_news_add.action='/entity/information/addYearReport_saveReportInfo.action';
		document.forms["info_news_add"].submit();		
	}
	function typeChoice(type) {
		if(type=='1') {
			document.getElementById('half').style.display='block';
			document.getElementById('quarter').style.display='none';
			document.getElementById('month').style.display='none';
			document.getElementById('year').style.display='none';
		} else if(type=='2') {
			document.getElementById('half').style.display='none';
			document.getElementById('quarter').style.display='block';
			document.getElementById('month').style.display='none';
			document.getElementById('year').style.display='none';
		} else if(type=='3') {
			document.getElementById('half').style.display='none';
			document.getElementById('quarter').style.display='none';
			document.getElementById('month').style.display='block';
			document.getElementById('year').style.display='none';
		} else if(type=='0') {
			document.getElementById('half').style.display='none';
			document.getElementById('quarter').style.display='none';
			document.getElementById('month').style.display='none';
			document.getElementById('year').style.display='block';
		}
	}
	/*加载年份*/
   function years(obj, Cyear) {
    var len = 6; // select长度,即option数量
    var selObj = document.getElementById(obj);
    var selIndex = len - 1;
    var newOpt; // OPTION对象

    // 循环添加OPION元素到年份select对象中
    for (i = 1; i <= len; i++) {
        if (selObj.options.length <= len) { // 如果目标对象下拉框升度不等于设定的长度则执行以下代码
            newOpt = window.document.createElement("OPTION"); // 新建一个OPTION对象
            newOpt.text = Cyear - i+1; // 设置OPTION对象的内容
            newOpt.value = Cyear - i+1; // 设置OPTION对象的值
            selObj.options.add(newOpt, i); // 添加到目标对象中
        }

    }
}

</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar"  >
  <form name='info_news_add' method='post' class='nomargin'>
  <%@ include file="/web/form-token.jsp" %>
  <input type="hidden" id="reportId" name="reportId"/>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">统计分析>报表下载>添加报告</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">报告题目*</span></td>
              <td width="626"> <input name="reportInfo.title"   value="<s:property value='reportInfo.title'/>" type="text" class="selfScale" id="reportInfo.title" size="50" maxlength="50"> 
                
           </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">报告类型*</span></td>
              <td> 
              年报<input type="radio" id="yearReport" name="reportType" value="0" checked onclick="typeChoice('0');">
              半年报<input type="radio" id="halfReport" name="reportType" value="1" onclick="typeChoice('1');">
              季报<input type="radio" id="quarterReport" name="reportType" value="2" onclick="typeChoice('2');">
              月报<input type="radio" id="monthReport" name="reportType" value="3" onclick="typeChoice('3');">
            </tr>
			<tr valign="bottom" class="postFormBox" > 
              <td valign="top"><span class="name">时间范围*</span></td>
              <td>
             
              <span id="year" style="display:block;">
              	 选择年份：
			  	<select name="reportInfo.year" id="y" onfocus="years('y',new Date().getFullYear())">
			  		<option value="<s:property value="#request.date"/>"><s:property value="#request.date"/></option>
			 	 </select>
              </span>
			  <span style="display:none;" id="half">
              	 选择年份：
			  	<select name="reportInfo.year" id="yh" onfocus="years('yh',new Date().getFullYear())">
			  		<option value="<s:property value="#request.date"/>"><s:property value="#request.date"/></option>
			 	 </select>
			 	 &nbsp;&nbsp;&nbsp;&nbsp;
			  	<select name="reportInfo.half">
			  		<option value="0">上半年</option>
			  		<option value="1">下半年</option>
			 	 </select>
              </span>
              <span style="display:none;" id="quarter">
               	选择年份：
			  	<select name="reportInfo.year" id="yq" onfocus="years('yq',new Date().getFullYear())">
			  		<option value="<s:property value="#request.date"/>"><s:property value="#request.date"/></option>
			 	 </select>
			 	 &nbsp;&nbsp;&nbsp;&nbsp;
              	 选择季度：
			  	<select name="reportInfo.quarter">
			  		<option value="1">第一季度</option>
			  		<option value="2">第二季度</option>
			  		<option value="3">第三季度</option>
			  		<option value="4">第四季度</option>
			 	 </select>
              </span>
              <span style="display:none;" id="month">
              	 选择年份：
			  	<select name="reportInfo.year" id="ym" onfocus="years('ym',new Date().getFullYear())">
			  		<option value="<s:property value="#request.date"/>"><s:property value="#request.date"/></option>
			 	 </select>
			 	 &nbsp;&nbsp;&nbsp;&nbsp;
              	 选择月份：
			  	<select name="reportInfo.month">
			  		<option value="01">一月</option>
			  		<option value="02">二月</option>
			  		<option value="03">三月</option>
			  		<option value="04">四月</option>
			  		<option value="05">五月</option>
			  		<option value="06">六月</option>
			  		<option value="07">七月</option>
			  		<option value="08">八月</option>
			  		<option value="09">九月</option>
			  		<option value="10">十月</option>
			  		<option value="11">十一月</option>
			  		<option value="12">十二月</option>
			 	 </select>
              </span>
             
			  </td>
            </tr>

             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">报告内容*</span></td>
              <td> 
              <textarea class="smallarea"  name="reportInfo.note" cols="70" rows="12" id="reportInfo.note"><s:property value='reportInfo.note'/></textarea>

			  </td>
            </tr>
          </table> 
      </div>
   </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
       <td  align="center" valign="middle">
       	<a href="#" title="提交" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">提交</span>       			
       		</span>
       	</a>
       	</td>
       <td  align="center" id="back" valign="middle">
       	</td>            
       	</tr>
      </table></td>
  </tr>
</table>
</form>
<script type="text/javascript"> 


var oFCKeditor = new FCKeditor( 'reportInfo.note' ) ; 
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
//--> 
</script> 
<script>bLoaded=true;</script>

</body>

</html>



