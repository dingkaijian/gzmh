<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>对话列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a>对话列表</a></li>
		<c:if test="${!xtjhInfo.self}">
			<shiro:hasPermission name="xtjh:xtjhInfo:edit">
				<li><a href="${ctx}/xtjh/xtjhInfo/form?type=d">发送消息</a></li>
			</shiro:hasPermission>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="xtjhInfo"
		action="${ctx}/xtjh/xtjhInfo/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>对方：</label> <form:input path="createBy.name"
					htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>对方</th>
				<th>最近消息</th>
				<th>发送日期</th>
				<%-- <shiro:hasPermission name="xtjh:xtjhInfo:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="xtjhInfo">
				<tr>
					<td><a
						href="${ctx}/xtjh/xtjhInfo/messageList?toId=${xtjhInfo.to.id}">${xtjhInfo.to.name}</a></td>
					<td>${xtjhInfo.content}</td>
					<td><fmt:formatDate value="${xtjhInfo.createDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<%-- <shiro:hasPermission name="xtjh:xtjhInfo:edit"><td>
						<a href="${ctx}/xtjh/xtjhInfo/delete?id=${xtjhInfo.id}" onclick="return confirmx('确认要删除该协同交互吗？', this.href)">删除</a>
					</td></shiro:hasPermission> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>