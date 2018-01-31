<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>中国证券业协会远程培训系统</title>
		<meta name="keywords" content="班组长培训,中国证劵业协会培训平台">
		<META name="description" Content="班组长培训,中国证劵业协会培训平台">
		<link href="style/index.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td align="center">
					<table width="932" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin: 0px 0px 8px 0px;">
						<tr>
							<td width="119">
								<img src="images/bottom_03.jpg" width="119" height="23">
							</td>
							<td background="images/bottom_04.jpg">
								&nbsp;
							</td>
						</tr>
					</table>
					<table width="932" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<DIV id=demo
									style="OVERFLOW: hidden; WIDTH: 932px; HEIGHT: 62px"
									align=center>
									<TABLE cellSpacing=0 cellPadding=0 align=left border=0
										cellspace="0">
										<TBODY>
											<TR>
												<TD id=demo1 vAlign=top>
													<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
														<TBODY>
															<TR>
																<TD align=middle>
																	<A
																		href="http://www.sasac.gov.cn/n1180/n2381219/n2381237/index.html"
																		target=_blank><IMG src="images/1.jpg" width=121
																			height=56 hspace="3" border=0>
																	</A>
																</TD>
																<TD align=middle>
																	<A
																		href="http://www.sasac.gov.cn/n1180/n2381219/n6271021/index.html"
																		target=_blank><IMG height=56 src="images/2.jpg"
																			hspace="3" width=121 border=0>
																	</A>
																</TD>
																<TD align=middle>
																	<A href="http://kxfzg.chinamobile.com/" target=_blank><IMG
																			hspace="3" height=56 src="images\3(1).jpg" width=121
																			border=0>
																	</A>
																</TD>
																<TD align=middle>
																	<A href="http://www.airchinagroup.com/" target=_blank><IMG
																			hspace="3" height=56 src="images\4(1).jpg" width=121
																			border=0>
																	</A>
																</TD>
																<TD align=middle>
																	<A href="http://www.airchinagroup.com/" target=_blank><IMG
																			height=56 src="images/5.jpg" hspace="3" width=121
																			border=0>
																	</A>
																</TD>
																<TD align=middle>
																	<A href="http://www.cnooc.com.cn/" target=_blank><IMG
																			hspace="3" height=56 src="images\6(1).jpg" width=121
																			border=0>
																	</A>
																</TD>
																<TD align=middle>
																	<A href="http://www.cnpc.com.cn/" target=_blank><IMG
																			hspace="3" height=56 src="images\7(1).jpg" width=121
																			border=0>
																	</A>
																</TD>
																<TD align=middle>
																	<A href="http://www.airchinagroup.com/" target=_blank><IMG
																			hspace="3" height=56 src="images/5.jpg" width=121
																			border=0>
																	</A>
																</TD>
																<TD align=middle>
																	<A href="http://www.cnooc.com.cn/" target=_blank><IMG
																			hspace="3" height=56 src="images\6(1).jpg" width=121
																			border=0>
																	</A>
																</TD>
																<TD align=middle>
																	<A href="http://www.cnpc.com.cn/" target=_blank><IMG
																			hspace="3" height=56 src="images\7(1).jpg" width=121
																			border=0>
																	</A>
																</TD>
															</TR>
														</TBODY>
													</TABLE>
												</TD>
												<TD id=demo2 vAlign=top></TD>
											</TR>
										</TBODY>
									</TABLE>
								</DIV>
								<SCRIPT language=javascript>
        var speed=5
        demo2.innerHTML=demo1.innerHTML
        function Marquee(){
        if(demo2.offsetWidth-demo.scrollLeft<=0)
        demo.scrollLeft-=demo1.offsetWidth
        else{
        demo.scrollLeft++
        }
        }
        var MyMar=setInterval(Marquee,speed)
        demo.onmouseover=function() {clearInterval(MyMar)}
        demo.onmouseout=function() {MyMar=setInterval(Marquee,speed)}

</SCRIPT>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="54" align="center" valign="middle"
					background="images/bottom_08.jpg">
					<span class="down">班组长培训&nbsp;&nbsp;&nbsp;&nbsp;版权所有：中国证劵业协会
						<BR> 投诉：010-11110000 传真：010-11116666 咨询服务电话：010-62415115</span>
				</td>
			</tr>
		</table>
	</body>
</html>
