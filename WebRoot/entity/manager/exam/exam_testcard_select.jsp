<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ page import = "com.whaty.platform.interaction.exam.*"%>
<%@ include file="../statistics/pub/priv.jsp"%>

<%@ page import = "com.whaty.platform.database.oracle.standard.interaction.exam.*"%>

<script language="javascript">
		window.open('/entity/manager/exam/exam_testcard_selectprint.jsp');
		//window.parent.location='/entity/exam/peBzzExamScore.action';
		window.history.back();
	</script>