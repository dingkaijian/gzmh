<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>发送信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
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
		<li><a href="${ctx}/xtjh/xtjhInfo/${xtjhInfo.self?'myNotice':'allNotice'}">通告列表</a></li>
		<li class="active"><a
			href="${ctx}/xtjh/xtjhInfo/form?id=${xtjhInfo.id}"><shiro:hasPermission
					name="xtjh:xtjhInfo:edit">${not empty xtjhInfo.id?'查看':'发布'}通告</shiro:hasPermission>
				<shiro:lacksPermission name="xtjh:xtjhInfo:edit">查看通告</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="xtjhInfo"
		action="${ctx}/xtjh/xtjhInfo/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<c:choose>
			<c:when test="${not empty xtjhInfo.id }">
				<div class="control-group">
					<label class="control-label">类型：</label>
					<div class="controls">
						<form:radiobutton path="type" label="群发通知" value="q"
							htmlEscape="false" class="" disabled="true"/>
						<form:radiobutton path="type" label="单发消息" value="d"
							htmlEscape="false" class="" disabled="true"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">接受人：</label>
					<div class="controls">
						<sys:treeselect id="xtjhInfoRecord" name="xtjhInfoRecordIds"
							value="${xtjhInfo.xtjhInfoRecordIds}"
							labelName="xtjhInfoRecordNames"
							labelValue="${xtjhInfo.xtjhInfoRecordNames}" title="用户"
							url="/sys/office/treeData?type=3"
							cssClass="input-xxlarge required" notAllowSelectParent="true"
							checked="true" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">通告标题：</label>
					<div class="controls">
					<span>${xtjhInfo.title}</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">通告内容：</label>
					<div class="controls">
					<span>${xtjhInfo.content}</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">附件：</label>
					<div class="controls">
						<form:hidden id="files" path="files" htmlEscape="false"
							maxlength="255" class="input-xlarge" />
						<sys:ckfinder input="files" type="files" uploadPath="/xtjh/xtjh"
							selectMultiple="true" readonly="true"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">发送短信：</label>
					<div class="controls">
						<form:radiobuttons path="bySms"
							items="${fns:getDictList('yes_no')}" itemLabel="label"
							itemValue="value" htmlEscape="false" class="" disabled="true"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">发送邮件：</label>
					<div class="controls">
						<form:radiobuttons path="byEmail"
							items="${fns:getDictList('yes_no')}" itemLabel="label"
							itemValue="value" htmlEscape="false" class=""  disabled="true"/>
					</div>
				</div>
				<div class="form-actions">
					<input id="btnCancel" class="btn" type="button" value="返 回"
						onclick="history.go(-1)" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">类型：</label>
					<div class="controls">
						<form:radiobutton path="type" label="群发通知" value="q"
							htmlEscape="false" class="" />
						<form:radiobutton path="type" label="单发消息" value="d"
							htmlEscape="false" class="" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">接受人：</label>
					<div class="controls">
						<sys:treeselect id="xtjhInfoRecord" name="xtjhInfoRecordIds"
							value="${xtjhInfo.xtjhInfoRecordIds}"
							labelName="xtjhInfoRecordNames"
							labelValue="${xtjhInfo.xtjhInfoRecordNames}" title="用户"
							url="/sys/office/treeData?type=3"
							cssClass="input-xxlarge required" notAllowSelectParent="true"
							checked="true" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">通告标题：</label>
					<div class="controls">
						<form:input path="title" htmlEscape="false" maxlength="255"
							class="input-xlarge required" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">通告内容：</label>
					<div class="controls">
						<form:input path="content" htmlEscape="false" maxlength="255"
							class="input-xlarge required" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">附件：</label>
					<div class="controls">
						<form:hidden id="files" path="files" htmlEscape="false"
							maxlength="255" class="input-xlarge" />
						<sys:ckfinder input="files" type="files" uploadPath="/xtjh/xtjh"
							selectMultiple="true" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">发送短信：</label>
					<div class="controls">
						<form:radiobuttons path="bySms"
							items="${fns:getDictList('yes_no')}" itemLabel="label"
							itemValue="value" htmlEscape="false" class="" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">发送邮件：</label>
					<div class="controls">
						<form:radiobuttons path="byEmail"
							items="${fns:getDictList('yes_no')}" itemLabel="label"
							itemValue="value" htmlEscape="false" class="" />
					</div>
				</div>
				<div class="form-actions">
					<shiro:hasPermission name="xtjh:xtjhInfo:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit"
							value="保 存" />&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回"
						onclick="history.go(-1)" />
				</div>
			</c:otherwise>
		</c:choose>
	</form:form>
</body>
</html>