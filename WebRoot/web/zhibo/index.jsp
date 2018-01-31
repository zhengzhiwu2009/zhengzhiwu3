<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="com.whaty.util.*"%>
<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String serverId = fixnull(request.getParameter("serverId"));
	//System.out.println("serverId:"+serverId);
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>中国证券业协会远程培训系统</title>
		<link href="/web/zhibo/css/css.css" rel="stylesheet" type="text/css" />
		<script src="/web/zhibo/js/inpng.js"></script>
		<style type="text/css">
<!--
-->
</style>
		<script>
function changeItem(option)
{
	window.location.href="/web/zhibo/index.jsp?serverId="+option;
}
</script>
	</head>

	<body>
		<table width="912" border="0" align="center" cellpadding="0"
			cellspacing="0" class="main_bor">
			<tr>
				<td>
					<img src="/web/zhibo/images/sp1_02.jpg" width="912" height="139" />
				</td>
			</tr>
			<tr>
				<td>
					<img src="/web/zhibo/images/sp1_04.jpg" width="912" height="128" />
				</td>
			</tr>
			<tr>
				<td valign="top" class="main_bg">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="31%" align="center" valign="top">
								<table width="270" height="26" border="0" cellpadding="0"
									cellspacing="0" background="/web/zhibo/images/sp_03.png">
									<tr>
										<td class="title">
											日程安排(10月19日)
										</td>
									</tr>
								</table>
								<table width="269" border="0" cellspacing="0" cellpadding="0">
									<tr class="trbor">
										<td width="14" align="center" valign="middle">
											<img src="/web/zhibo/images/sp_13.png" width="5" height="5" />
										</td>
										<td width="50" align="left" valign="middle">
											1、
										</td>
										<td width="200" align="left">
											主持人宣布典礼开始、介绍出席领导及来宾
										</td>
									</tr>
									<tr class="trbor">
										<td width="14" align="center" valign="middle">
											<img src="/web/zhibo/images/sp_13.png" width="5" height="5" />
										</td>
										<td width="50" align="left" valign="middle">
											2、
										</td>
										<td align="left">
											清华大学继续教育学院常务副院长李家强教授致辞
										</td>
									</tr>
									<tr class="trbor">
										<td width="14" align="center" valign="middle">
											<img src="/web/zhibo/images/sp_13.png" width="5" height="5" />
										</td>
										<td width="50" align="left" valign="middle">
											3、
										</td>
										<td align="left">
											管理员代表-中国南方电网有限责任公司刘宾发言
										</td>
									</tr>
									<tr class="trbor">
										<td width="14" align="center" valign="middle">
											<img src="/web/zhibo/images/sp_13.png" width="5" height="5" />
										</td>
										<td width="50" align="left" valign="middle">
											4、
										</td>
										<td align="left">
											老学员代表-中粮集团刘伟发言
										</td>
									</tr>
									<tr class="trbor">
										<td width="14" align="center" valign="middle">
											<img src="/web/zhibo/images/sp_13.png" width="5" height="5" />
										</td>
										<td width="50" align="left" valign="middle">
											5、
										</td>
										<td align="left">
											老学员代表-中国航天科技林宏发言
										</td>
									</tr>
									<tr class="trbor">
										<td width="14" align="center" valign="middle">
											<img src="/web/zhibo/images/sp_13.png" width="5" height="5" />
										</td>
										<td width="50" align="left" valign="middle">
											6、
										</td>
										<td align="left">
											三期新学员代表-中国联通麻力波发言
										</td>
									</tr>
									<tr class="trbor">
										<td width="14" align="center" valign="middle">
											<img src="/web/zhibo/images/sp_13.png" width="5" height="5" />
										</td>
										<td width="50" align="left" valign="middle">
											7、
										</td>
										<td align="left">
											国资委群工局徐宇栋处长总结致辞
										</td>
									</tr>
									<tr class="trbor">
										<td width="14" align="center" valign="middle">
											<img src="/web/zhibo/images/sp_13.png" width="5" height="5" />
										</td>
										<td width="50" align="left" valign="middle">
											8、
										</td>
										<td align="left">
											典礼结束，人员退场
										</td>
									</tr>
								</table>
							</td>
							<td width="49%" align="center">
								<table width="416" border="0" cellspacing="0" cellpadding="0"
									style="margin-top: 5px">
									<tr>
										<td width="27">
											<img src="/web/zhibo/images/sp_06.png" width="27"
												height="325" />
										</td>
										<td valign="top" class="spbg">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="spbox">
												<tr>
													<td>
														<object id=nstv
															classid='CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6'
															width=355 height=288
															codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701standby=Loading
															Microsoft? Windows Media? Player components...
															type=application/x-oleobject>
															<param name='URL'
																value='/courseserver/search_server2.jsp?filepath=20110713&serverId=<%=serverId%>'>
															<PARAM NAME='UIMode' value='full'>
															<PARAM NAME='AutoStart' value='true'>
															<PARAM NAME='Enabled' value='true'>
															<PARAM NAME='enableContextMenu' value='true'>
															<param name='WindowlessVideo' value='true'>
														</object>
													</td>
												</tr>
											</table>
										</td>
										<td width="34">
											<img src="/web/zhibo/images/sp_09.png" width="34"
												height="325" />
										</td>
									</tr>
								</table>
							</td>
							<td width="20%" valign="top">
								<table width="100%" height="100%" border="0" cellpadding="0"
									cellspacing="0" style="margin-top: 50px">
									<tr>
										<td>
											<table width="133" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td height="28" align="center"
														background="/web/zhibo/images/sp_21.png" class="title2">
														选择服务器
														<br/>
													</td>
												</tr>
												<tr>
													<!-- <td height="60" background="/web/zhibo/images/sp_23.png"><table width="100%" height="40" border="0" cellpadding="0" cellspacing="5" class="jishu"> -->
												<tr>
													<td align="center">
														<br/>
														<select name="domain111"
															onchange="javascript:changeItem(this.value)">
															<option value="">
																自动选择
															</option>
															<!--   <option value="1.tel.webtrn.cn"  <%if(serverId.equals("1.tel.webtrn.cn")) out.print("selected");%>>江苏电信(推荐)</option>   -->
															<option value="2.tel.webtrn.cn"
																<%if(serverId.equals("2.tel.webtrn.cn")) out.print("selected");%>>
																武汉电信
															</option>
															<option value="3.tel.webtrn.cn"
																<%if(serverId.equals("3.tel.webtrn.cn")) out.print("selected");%>>
																广西电信
															</option>
															<option value="4.tel.webtrn.cn"
																<%if(serverId.equals("4.tel.webtrn.cn")) out.print("selected");%>>
																莆田电信(推荐)
															</option>
															<option value="5.tel.webtrn.cn"
																<%if(serverId.equals("5.tel.webtrn.cn")) out.print("selected");%>>
																福州电信
															</option>
															<option value="6.tel.webtrn.cn"
																<%if(serverId.equals("6.tel.webtrn.cn")) out.print("selected");%>>
																湖南电信(推荐)
															</option>
															<!--    <option value="1.cnc.webtrn.cn" <%if(serverId.equals("1.cnc.webtrn.cn")) out.print("selected");%> >山东联通(推荐)</option>   -->
															<option value="2.cnc.webtrn.cn"
																<%if(serverId.equals("2.cnc.webtrn.cn")) out.print("selected");%>>
																郑州联通
															</option>
															<option value="3.cnc.webtrn.cn"
																<%if(serverId.equals("3.cnc.webtrn.cn")) out.print("selected");%>>
																西安联通
															</option>
															<option value="4.cnc.webtrn.cn"
																<%if(serverId.equals("4.cnc.webtrn.cn")) out.print("selected");%>>
																黑龙江联通(推荐)
															</option>
															<option value="1.edu.webtrn.cn"
																<%if(serverId.equals("1.edu.webtrn.cn")) out.print("selected");%>>
																教育网
															</option>
														</select>

													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr class="trbor">
										<td align="center">
											<br/>
											说明：如果您无法播放直播视频，请检查Window Media
											Player软件是否已经安装，如果没有安装，请点击下载相应软件安装。
											<a href="/web/bzz_index/files/MP10Setup_CN.exe">点击下载</a>
											<br/>
										</td>
									</tr>
								</table>
								<br/>
								<br/>
								<br/>
								<br/>
								<br />
								<br />
							</td>
						</tr>
						<!--  <tr>
            <td><table width="131" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><a href="#"><img src="/web/zhibo/images/sp_26.png" width="132" height="28" border="0" /></a></td>
                </tr>
                <tr>
                  <td><img src="/web/zhibo/images/sp_28.png" width="132" height="28" /></td>
                </tr>
            </table></td>
          </tr>  -->
					</table>
				</td>
			</tr>
		</table>
		<br />
		</td>
		</tr>
		<tr>
			<td valign="top">
				<table width="912" height="100" border="0" align="center"
					cellpadding="0" cellspacing="0"
					background="/web/zhibo/images/lingbg.jpg">
					<tr>
						<td align="center">
							<a href="javascript:window.close()"><img
									src="/web/zhibo/images/sp_33.png" width="124" height="34"
									vspace="10" border="0">
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<table width="912" height="55" border="0" align="center"
					cellpadding="0" cellspacing="0"
					background="/web/zhibo/images/sp1_09.jpg">
					<tr>
						<td class="footer">
							<p>
								版权所有：中国证劵业协会
								<br/>
								投诉：010-11110000 传真：010-11116666 咨询服务电话：010-62415115
							</p>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</table>

	</body>
</html>
