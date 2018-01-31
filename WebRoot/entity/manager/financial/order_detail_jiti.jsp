
<%@page import="java.text.SimpleDateFormat"%><%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>tableType_2</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
	</head>
	<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
	<div id="main_content"><div align="center"> 
    </div><div class="cntent_k"><div align="center"> 
   	  </div><div class="k_cc"><div align="center"> 
</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							订单详情
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div class="border" style="padding-left: 30px">
							<s:if test="electiveList != null && electiveList.size() >0">
							<table width='98%' align="center" cellpadding='1' cellspacing='0'
								class='list'>
								<tr>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">系统编号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">学员姓名</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">课程编号</span>
									</th>
									<th width='' style='white-space: nowrap;'>
										<span class="link">课程名称</span>
									</th>
								</tr>
								<s:iterator value="electiveList" id="elective">
									<tr class='oddrowbg'>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective[0]"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective[1]"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective[2]"/>
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#elective[3]"/>
									</td>
								</tr>
								</s:iterator>
							</table>
							</s:if>
						</div>
					</td>
				</tr>
                          <tr>
                          	<td colspan="10" align="center">
                          		<span style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');">
									<a style="cursor: pointer;" onclick="history.go(-1);">返回</a>
								</span>
                          </tr>
			</table>
				  </div>
    </div>
</div>
<div class="clear"></div>
	</body>
</html>