<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<link href="/web/bzz_index/style/index.css" rel="stylesheet"
	type="text/css" />
<link href="/web/bzz_index/style/xytd.css" rel="stylesheet"
	type="text/css" />
<link href="/web/bzz_index/css.css" rel="stylesheet" type="text/css" />
<link href="/web/css/css.css" rel="stylesheet" type="text/css" />

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>中国证劵业协会培训平台</title>
	<style type="text/css">
.STYLE1 {
	color: #FF9000;
	font-weight: bold;
}

.link a {
	color: #3399cc;
}
</style>
</head>

<body style="background-color: white;">
	<table width="100%" align="center" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td
				style="background-image: url(/web/bzz_index/images/top_pic1.jpg); background-position: bottom; background-repeat: repeat-x;">
				<table width="1002px" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td>
							<img width="100%" src="/web/bzz_index/images/top_pic.jpg"
								height="140">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td background="/web/news/images/news_05.jpg">
				<table width="1002" align="center" border="0" cellspacing="0"
					cellpadding="0" height=500>

					<tr>
						<td class="text_news" valign=top>
							<div style="font-size: 24px" align=center>
								<b><s:property value="bulletin.title" /> </b>
							</div>
							<br/>
							<div class="news_date" align=center>
								发布时间：
								<s:date name="bulletin.publishDate" format="yyyy-MM-dd" />
								<span onClick="window.print()" class="print">【打 印】</span>
							</div>
							<p>
								<s:property value="bulletin.note" escape="false" />
							</p>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="78" align=center>
				<table width="1002" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- 
							<td width="10" height="78">
								<img src="/web/news/images/news_15.jpg" border="0">
							</td>
							 -->
						<td background="/web/news/images/news_17.jpg" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="center" class="close">
										<div style="cursor: pointer;" onclick="abc();">
											【关 闭】
										</div>
									</td>
								</tr>
								<tr>
									<td align="center" style="padding-top: 4px;">
										<span class="down">版权所有：中国证劵业协会 <BR>
											投诉：010-11110000 传真：010-11116666 咨询服务电话：010-62415115 </span>
									</td>
								</tr>
							</table>
						</td>
						<!-- 
							<td width="10" height="78">
								<img src="/web/news/images/news_20.jpg" border="0">
							</td>
							 -->
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
<script>
		function abc() {
			if(confirm('您正在关闭此窗口，要继续吗？')) {
			window.opener = window;
       		var win = window.open("","_self");
       		win.close();
       		}
			
		}
	</script>
</html>
