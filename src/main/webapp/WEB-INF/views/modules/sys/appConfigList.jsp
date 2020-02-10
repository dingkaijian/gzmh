<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><html>
<head>
<title>系统配置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/appconfig/list/">系统配置列表</a></li>
		<shiro:hasPermission name="sys:appconfig:auditor"><li><a href="${ctx}/sys/appconfig/form/">安全策略<shiro:hasPermission name="sys:appconfig:auditor">${not empty appconfig.id?'修改':'添加'}</shiro:hasPermission></a></li></shiro:hasPermission>
		<shiro:hasPermission name="sys:appconfig:sys"><li><a href="${ctx}/sys/appconfig/form2/">系统配置策略<shiro:hasPermission name="sys:appconfig:sys">${not empty appconfig.id?'修改':'添加'}</shiro:hasPermission></a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>规则名称</th><th>规则值</th><th>规则描述</th><th>策略状态</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="appConfig">
			<tr>
				<shiro:hasPermission name="sys:appconfig:sys">
					<c:if test="${appConfig.userpermission == 'sys'}">
						<td>
							<shiro:hasPermission name="sys:appconfig:sys">
								<a href="${ctx}/sys/appconfig/form2?id=${appConfig.id}">${appConfig.configname}</a>
							</shiro:hasPermission>
						</td>
						<td>${appConfig.configvalue}</td>
						<td>${appConfig.configdesc}</td>
						<td>${appConfig.configstate eq '0'?'未启用':'启用中'}</td>
						<shiro:hasPermission name="sys:appconfig:sys"><td nowrap>
							<a href="${ctx}/sys/appconfig/form2?id=${appConfig.id}">修改</a>
							<c:if test="${appConfig.configstate == '1'}">
								<a href="${ctx}/sys/appconfig/pushAndCloseBySys?id=${appConfig.id}&configstate=0&configname=${appConfig.configname}" >关闭</a>
							</c:if>	
							<c:if test="${appConfig.configstate == '0'}">
								<a href="${ctx}/sys/appconfig/pushAndCloseBySys?id=${appConfig.id}&configstate=1&configname=${appConfig.configname}" >启用</a>
							</c:if>						
							<a href="${ctx}/sys/appconfig/deleteBySys?id=${appConfig.id}" onclick="return confirmx('确定删除此策略？', this.href)">删除</a>
						</td></shiro:hasPermission>
					</c:if>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:appconfig:auditor">
					<c:if test="${appConfig.userpermission == 'auditor'}">
						<td>
							<shiro:hasPermission name="sys:appconfig:auditor">
								<a href="${ctx}/sys/appconfig/form?id=${appConfig.id}">${appConfig.configname}</a>
							</shiro:hasPermission>
						</td>
						<td>${appConfig.configvalue}</td>
						<td>${appConfig.configdesc}</td>
						<td>${appConfig.configstate eq '0'?'未启用':'启用中'}</td>
						<shiro:hasPermission name="sys:appconfig:auditor"><td nowrap>
							<a href="${ctx}/sys/appconfig/form?id=${appConfig.id}">修改</a>
							<c:if test="${appConfig.configstate == '1'}">
								<a href="${ctx}/sys/appconfig/pushAndCloseByAuditor?id=${appConfig.id}&configstate=0&configname=${appConfig.configname}" >关闭</a>
							</c:if>	
							<c:if test="${appConfig.configstate == '0'}">
								<a href="${ctx}/sys/appconfig/pushAndCloseByAuditor?id=${appConfig.id}&configstate=1&configname=${appConfig.configname}" >启用</a>
							</c:if>						
							<a href="${ctx}/sys/appconfig/deleteByAuditor?id=${appConfig.id}" onclick="return confirmx('确定删除此策略？', this.href)">删除</a>
						</td></shiro:hasPermission>		
					</c:if>
				</shiro:hasPermission>
			</tr>
		</c:forEach>	
		</tbody>		
	</table>
	<div class="pagination">${page}</div>
</body>
</html>