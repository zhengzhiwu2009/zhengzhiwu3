<%@ page language="java" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/ext-3.0.0/resources/css/ext-all.css" />

<script type="text/javascript" src="<%=request.getContextPath() %>/js/ext-3.0.0/adapter/ext/ext-base.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ext-3.0.0/ext-all.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ext-3.0.0/ext-basex.js"></script> 

<script type="text/javascript">
	Ext.QuickTips.init();
	
	Ext.Msg.minWidth = 200;
	Ext.layout.BorderLayout.Region.prototype.floatable = false;
	Ext.layout.BorderLayout.Region.prototype.animFloat = true;
</script>

<script type="text/javascript">
	var CONTEXT_PATH = '<%=request.getContextPath()%>';//get CONTEXT_PATH
</script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/ext-3.0.0/ext-whaty-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ext-3.0.0/ext-lang-zh_CN.js"></script>
<link id="personal" href="./" type="text/css" rel="stylesheet"/>

<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = CONTEXT_PATH + '/js/image/default/s.gif';
</script>
