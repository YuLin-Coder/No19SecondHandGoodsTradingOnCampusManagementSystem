<%
String __APP__ = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=__APP__ %>/web/public/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=__APP__ %>/web/public/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=__APP__ %>/web/public/easyui/themes/all.css"/>
<script type="text/javascript" src="<%=__APP__ %>/web/public/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=__APP__ %>/web/public/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=__APP__ %>/web/public/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=__APP__ %>/web/public/js/common.js"></script>
<script type="text/javascript" src="<%=__APP__ %>/web/public/js/json2.js"></script>

<script type="text/javascript">
	var __APP__ = "<%=request.getContextPath() %>";
	var __FAPP__="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>"
</script>