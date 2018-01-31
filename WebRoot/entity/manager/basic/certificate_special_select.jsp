<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ page import = "com.whaty.platform.interaction.*"%>
<%@ include file="../statistics/pub/priv.jsp"%>

<%@ page import = "com.whaty.platform.database.oracle.standard.interaction.*"%>

<script language="javascript">
		window.open('/entity/manager/basic/certificate_special_selectprint.jsp');
		//window.parent.location='/entity/exam/peBzzExamScore.action';
		setTimeout("window.opener=null;window.location.href='/entity/basic/peBzzCertificate.action?backParam=true'",2000);		//页面返回
	</script>