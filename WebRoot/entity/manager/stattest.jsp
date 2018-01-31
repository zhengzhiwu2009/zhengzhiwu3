<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>执行统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
	<form method="post" action="/entity/basic/statistic_run.action">
	<div>
		<button type="submit">执行</button>
		<br/><input name="date">
		<br/><input type="checkbox" name="types" value="all" id="all"/>全部
		<table width="100%" border="1">
			<tr>
				<td width="50%">
					<input type="checkbox" name="types" value="1"/>初始化statistic_enterprise表
					<br/><input type="checkbox" name="types" value="2"/>机构类型
					<br/><input type="checkbox" name="types" value="3"/>注册机构新增
					<br/><input type="checkbox" name="types" value="4"/>注册机构累计
					<br/><input type="checkbox" name="types" value="5"/>注册人数累计
					<br/><input type="checkbox" name="types" value="6"/>注册人数新增
					<br/><input type="checkbox" name="types" value="7"/>开始测验累计
					<br/><input type="checkbox" name="types" value="8"/>完成测验累计
					<br/><input type="checkbox" name="types" value="9"/>开始测验新增
					<br/><input type="checkbox" name="types" value="10"/>完成测验新增
					<br/><input type="checkbox" name="types" value="11"/>报名人数
					<br/><input type="checkbox" name="types" value="12"/>报名人次
					<br/><input type="checkbox" name="types" value="13"/>报名学时数
					<br/><input type="checkbox" name="types" value="14"/>付费金额
					<br/><input type="checkbox" name="types" value="15"/>完成学时数
					<br/><input type="checkbox" name="types" value="16"/>退费人数
					<br/><input type="checkbox" name="types" value="17"/>退费人次
					<br/><input type="checkbox" name="types" value="18"/>退费学时
					<br/><input type="checkbox" name="types" value="19"/>退费金额
					<br/><input type="checkbox" name="types" value="20"/>跨年退费人数
					<br/><input type="checkbox" name="types" value="21"/>跨年退费人次
					<br/><input type="checkbox" name="types" value="22"/>跨年退费学时
					<br/><input type="checkbox" name="types" value="23"/>跨年退费金额
					<br/><input type="checkbox" name="types" value="24"/>开始学习人次<font color="red">(慢)</font>
					<br/><input type="checkbox" name="types" value="25"/>完成学习人次<font color="red">(慢)</font>
					<br/><input type="checkbox" name="types" value="26"/>开始学习累计<font color="red">(慢)</font>
					<br/><input type="checkbox" name="types" value="27"/>完成学习累计<font color="red">(慢)</font>
					<br/><input type="checkbox" name="types" value="28"/>完成学时数累计<font color="red">(慢)</font>
					<br/><input type="checkbox" name="types" value="29"/>修改学员表完成10学时时间
					<br/><input type="checkbox" name="types" value="30"/>完成10个必修学时数<font color="red">(慢)</font>
					<br/><input type="checkbox" name="types" value="31"/>完成10个必修学时人数累计<font color="red">(慢)</font>
					<br/><input type="checkbox" name="types" value="32"/>修改学员表完成15学时人数时间
					<br/><input type="checkbox" name="types" value="33"/>完成15个必修学时人数<font color="red">(慢)</font>
					<br/><input type="checkbox" name="types" value="34"/>完成15个必修学时人数累计<font color="red">(慢)</font>
				</td>
				<td>
					<input type="checkbox" name="types" value="35"/>初始化statistic_year表
					<br/><input type="checkbox" name="types" value="36"/>年注册机构新增
					<br/><input type="checkbox" name="types" value="37"/>年注册机构累计
					<br/><input type="checkbox" name="types" value="38"/>年注册人数新增
					<br/><input type="checkbox" name="types" value="39"/>年注册人数累计
					<br/><input type="checkbox" name="types" value="40"/>年报名人数
					<br/><input type="checkbox" name="types" value="41"/>年报名人次
					<br/><input type="checkbox" name="types" value="42"/>年报名学时数
					<br/><input type="checkbox" name="types" value="43"/>年付费金额
					<br/><input type="checkbox" name="types" value="44"/>年开始学习人次
					<br/><input type="checkbox" name="types" value="45"/>年完成学习人次
					<br/><input type="checkbox" name="types" value="46"/>年开始测验人次
					<br/><input type="checkbox" name="types" value="47"/>年完成测验人次
					<br/><input type="checkbox" name="types" value="48"/>年退费人数
					<br/><input type="checkbox" name="types" value="49"/>年退费人次
					<br/><input type="checkbox" name="types" value="50"/>年退费学时数
					<br/><input type="checkbox" name="types" value="51"/>年退费金额
					<br/><input type="checkbox" name="types" value="52"/>年跨年退费人数
					<br/><input type="checkbox" name="types" value="53"/>年跨年退费人次
					<br/><input type="checkbox" name="types" value="54"/>年跨年退费学时数
					<br/><input type="checkbox" name="types" value="55"/>年跨年退费金额
					<br/><input type="checkbox" name="types" value="56"/>年完成10学时人数
					<br/><input type="checkbox" name="types" value="57"/>年完成15学时人数
					<br/><input type="checkbox" name="types" value="58"/>年报名人数同比
					<br/><input type="checkbox" name="types" value="59"/>年报名人次同比
					<br/><input type="checkbox" name="types" value="60"/>年报名学时数同比
					<br/><input type="checkbox" name="types" value="61"/>插入course表
					<br/><input type="checkbox" name="types" value="62"/>更新course表
					<br/><input type="checkbox" name="types" value="63"/>插入跨年表
					<br/><input type="checkbox" name="types" value="64"/>更新跨年表
					<br/><input type="checkbox" name="types" value="65"/>初始化集体报名概况teamentry表
					<br/><input type="checkbox" name="types" value="66"/>更新集体报名概况
				</td>
			</tr>
		</table>
		<br/><button type="submit">执行</button>
	</div>
	</form>
 </body>
</html>
