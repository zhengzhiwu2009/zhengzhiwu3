<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>学员信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="/entity/manager/images/css.css" rel="stylesheet"
	type="text/css">
</head>
<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">
	<div id="main_content">
		<div align="center"></div>
		<div class="content">
			<table width="500" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="26" align="center" colspan="10">
						<h4>学员信息</h4>
					</td>
				</tr>
				<tr>
					<th>系统编号：</th>
					<td><s:if test="peBzzStudent.regNo == null ">--</s:if> <s:property
							value="peBzzStudent.regNo" />
					</td>
				</tr>
				<tr>
					<th>姓名：</th>
					<td><s:if test="peBzzStudent.trueName == null ">--</s:if> <s:property
							value="peBzzStudent.trueName" />
					</td>
				</tr>
				<tr>
					<th>性别：</th>
					<td><s:if test="peBzzStudent.gender == null ">--</s:if> <s:property
							value="peBzzStudent.gender" />
					</td>

				</tr>
				<!--  
						<tr>
							<th>民族：</th>
							<td>
								<s:if test="peBzzStudent.folk == null ">--</s:if>
								<s:property value="peBzzStudent.folk" />
							</td>

						</tr>
						<tr>
							<th>出生年月：</th>
							<td>
								<s:if test="peBzzStudent.birthdayDate == null ">--</s:if>
								<s:date name="peBzzStudent.birthdayDate" format="yyyy-MM-dd"/>
							</td>

						</tr>
						-->
				<s:if
					test='peBzzStudent.peEnterprise.enumConstByFlagEnterpriseType.code != "V" || peBzzStudent.peEnterprise.peEnterprise.enumConstByFlagEnterpriseType.code != "V"'>
					<tr>
						<th>国籍：</th>
						<td><s:if test="peBzzStudent.cardType ==null ">--</s:if> <s:property
								value="peBzzStudent.cardType" />
						</td>

					</tr>
					<tr>
						<th>证件号码：</th>
						<td><s:if test="peBzzStudent.cardNo == null ">--</s:if> <s:property
								value="peBzzStudent.cardNo" />
						</td>

					</tr>
					<tr>
						<th>学历：</th>
						<td><s:if test="peBzzStudent.education == null ">--</s:if> <s:property
								value="peBzzStudent.education" />
						</td>
					</tr>
				</s:if>
				<tr>
					<th>工作部门：</th>
					<td><s:if test="peBzzStudent.department == null ">--</s:if> <s:property
							value="peBzzStudent.department" />
					</td>
				</tr>
				<tr>
					<th>所在机构：</th>
					<td><s:if test="peBzzStudent.peEnterprise == null ">--</s:if>
						<s:property value="peBzzStudent.peEnterprise.name" />
					</td>
				</tr>
				<tr>
					<th>自填单位名称：</th>
					<td><s:if test="peBzzStudent.enterpriseName == null ">--</s:if>
						<s:property value="peBzzStudent.enterpriseName" />
					</td>
				</tr>
				<s:if
					test='peBzzStudent.peEnterprise.enumConstByFlagEnterpriseType.code != "V" || peBzzStudent.peEnterprise.peEnterprise.enumConstByFlagEnterpriseType.code != "V"'>
					<tr>
						<th>所在机构代码：</th>
						<td><s:if test="peBzzStudent.peEnterprise == null ">--</s:if>
							<s:property value="peBzzStudent.peEnterprise.code" />
						</td>
					</tr>
					<tr>
						<th>工作所在地：</th>
						<td><s:if test="peBzzStudent.address == null ">--</s:if> <s:property
								value="peBzzStudent.address" />
						</td>
					</tr>
				</s:if>
				<tr>
					<th>工作区域：</th>
					<td><s:if test="peBzzStudent.workAddress == null ">--</s:if> <s:property
							value="peBzzStudent.workAddress" />
					</td>
				</tr>
				<tr>
					<th>从事业务:</th>
					<td><s:if test="peBzzStudent.work == null ">--</s:if> <s:property
							value="peBzzStudent.work" />
					</td>
				</tr>
				<tr>
					<th>职务:</th>
					<td><s:if test="peBzzStudent.position == null ">--</s:if> <s:property
							value="peBzzStudent.position" />
					</td>
				</tr>
				<s:if
					test='peBzzStudent.peEnterprise.enumConstByFlagEnterpriseType.code != "V" || peBzzStudent.peEnterprise.peEnterprise.enumConstByFlagEnterpriseType.code != "V"'>
					<tr>
						<th>资格类型：</th>
						<td><s:if
								test="peBzzStudent.enumConstByFlagQualificationsType.name==null ">--</s:if>
							<s:property
								value="peBzzStudent.enumConstByFlagQualificationsType.name"
								default="--" />
						</td>
					</tr>
				</s:if>
				<tr>
					<th>电子邮件：</th>
					<td><s:if test="peBzzStudent.email == null ">--</s:if> <s:property
							value="peBzzStudent.email" />
					</td>
				</tr>

				<s:if
					test='peBzzStudent.peEnterprise.enumConstByFlagEnterpriseType.code == "V" || peBzzStudent.peEnterprise.peEnterprise.enumConstByFlagEnterpriseType.code == "V"'>
					<tr>
						<th>办公电话：</th>
						<td><s:if test="peBzzStudent.phone == null ">--</s:if> <s:property
								value="peBzzStudent.phone" />
						</td>
					</tr>
				</s:if>
				<tr>
					<th>手机：</th>
					<td><s:if test="peBzzStudent.mobilePhone == null ">--</s:if> <s:property
							value="peBzzStudent.mobilePhone" />
					</td>
				</tr>
				<tr>
					<td colspan="20" align="center">
						<!-- <input type="button" value="&nbsp;&nbsp;关 &nbsp;&nbsp;闭&nbsp;&nbsp;" onclick="javascript:window.close();">  -->
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="clear"></div>
</body>
</html>
