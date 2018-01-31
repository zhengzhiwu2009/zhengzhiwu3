<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>二级机构信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">


  </head>
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content"><div align="center"> 
    </div><div class="cntent_k"><div align="center"> 
   	  </div><div class="k_cc"><div align="center"> 
</div><table width="780" border="0" align="center" cellpadding="0" cellspacing="0">                          
         <tr>
           <td height="26" valign="middle" align="center" colspan="10"><h4>机构信息</h4>                                               </td>
         </tr>
         <tr>
           <td height="8" colspan="10"> </td>
         </tr>
         <s:if test="manager!=null">
         
    
		 <tr valign="middle">
			 <td width="18%" height="30" align="left" class="postFormBox"><span class="name">用&nbsp;&nbsp;户&nbsp;&nbsp;名：</span><br/></td>
		     <td width="50%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="manager.loginId"/></td>
		 </tr>
 		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name">二级机构名称：</span><br/></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="manager.peEnterprise.name"/></td>
		 </tr>
 		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name">管理员姓名：</span><br/></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="manager.name"/></td>
		 </tr>
  		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name">所在部门：</span><br/></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="manager.peEnterprise.fzrDepart"/></td>
		 </tr>
  		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name">联系电话：</span><br/></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="manager.phone"/></td>
		 </tr>
  		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name">手机号码：</span><br/></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="manager.mobilePhone"/></td>
		 </tr>
  		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name">电子邮箱：</span><br/></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="manager.email"/></td>
		 </tr>
		 <tr>
             <td  height="10"> <br/></td>
         </tr>
              </s:if>
              <s:else>
               <tr>
             <td  height="10" align="center"><h4>暂无详细信息 </h4></td>
       		  </tr>
              </s:else>
         <tr>
            <td colspan="20" align="center">
								<span
									style="width: 40px; height: 22px; line-height: 22px; background-image: url('/entity/manager/images/button_2.jpg');">
									<a href="###" onclick="history.back();">返回</a>
								</span>

							</td>
         </tr>
   	</table>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
