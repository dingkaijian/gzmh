<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/appconfig/list/">系统配置列表</a></li>
		<shiro:hasPermission name="sys:appconfig:auditor"><li  class="active"><a href="${ctx}/sys/appconfig/form/">安全策略<shiro:hasPermission name="sys:appconfig:auditor">${not empty appconfig.id?'修改':'添加'}</shiro:hasPermission></a></li></shiro:hasPermission>
		<shiro:hasPermission name="sys:appconfig:sys"><li><a href="${ctx}/sys/appconfig/form2/">系统配置策略<shiro:hasPermission name="sys:appconfig:sys">${not empty appconfig.id?'修改':'添加'}</shiro:hasPermission></a></li></shiro:hasPermission>
	</ul></br>
	<form:form id="inputForm" modelAttribute="appconfig" action="${ctx}/sys/appconfig/saveByAuditor/"  method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">策略名称:</label>
			<div class="controls">
				<form:input path="configname" name="configname" id="configname"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">策略值:</label>
			<div class="controls">
				<form:input path="configvalue" name="configvalue" id="configvalue"/>
			</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">策略说明:</label>
			<div class="controls">
				<form:textarea path="configdesc" name="configdesc" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">策略类型:</label>
			<div class="controls">
			<select class="input-medium" name="userpermission">
				<option value="auditor">安全策略</option>
			</select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:appconfig:auditor"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>