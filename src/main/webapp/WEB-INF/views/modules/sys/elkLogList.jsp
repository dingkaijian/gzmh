<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>我的关注</title>
	<meta name="decorator" content="default"/>
	<link href="/AC_MAN_PORTAL/static/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/AC_MAN_PORTAL/static/css/tztg.css" type="text/css" rel="stylesheet"/>
	<link href="/AC_MAN_PORTAL/static/css/fontawesome/css/all.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="/AC_MAN_PORTAL/static/layui/layui.js"></script>
	<script type="text/javascript" src="/AC_MAN_PORTAL/static/vue.js"></script>
<style type="text/css">
	[v-cloak]{
		display: none!important;
	}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a style="color:#2fa4e7;" href="${ctx}/sys/elkLog/list">日志列表</a></li>	
	</ul>	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<td>clientId</td>
			<td>clientAddr</td>
			<td>clientName</td>
			<td>tokenId</td>
			<td>type</td>
			<td>result</td>
			<td>loginName</td>
			<td>userName</td>
			<td>params</td>
		</thead>
		<tbody>
		<c:forEach items="${elkList}" var="log">
			<tr>
				<td>${log.clientId}</td>
				<td>${log.clientAddr}</td>
				<td>${log.clientName}</td>
				<td>${log.tokenId}</td>
				<td>${log.type}</td>
				<td>${log.result}</td>
				<td>${log.loginName}</td>
				<td>${log.userName}</td>
				<td>${log.params}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<sys:message content="${message}"/>
</body>
</html>