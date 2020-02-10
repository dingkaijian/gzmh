<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<!--密码加密相关js文件引入,开始  -->
	<script src="${ctxStatic}/ict/js/sm/core.js"></script> 
		<script src="${ctxStatic}/ict/js/sm/jsbn.js"></script>
		<script src="${ctxStatic}/ict/js/sm/jsbn2.js"></script> 
		<script src="${ctxStatic}/ict/js/sm/prng4.js"></script>
		<script src="${ctxStatic}/ict/js/sm/rng.js"></script> 
		<script src="${ctxStatic}/ict/js/sm/sm2.js"></script>
		<script src="${ctxStatic}/ict/js/sm/sm3.js"></script>
		<script src="${ctxStatic}/ict/js/sm/ec.js"></script>
		<script src="${ctxStatic}/ict/js/sm/ecdsa-modified-1.0.js"></script> 
		<script src="${ctxStatic}/ict/js/sm/ecparam-1.0.js"></script>
		<script src="${ctxStatic}/ict/js/sm/ec-patch.js"></script>
	<!--密码加密相关js文件引入,结束  -->
	<script src="${ctxStatic}/jquery/jquery-1.12.4.min.js?v=${jsVersion}"></script>
	<script src="${ctxStatic}/ict/js/validateRules.js?v=${jsVersion}"></script>
	<script src="${ctxStatic}/ict/js/dialog.js?v=${jsVersion}"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/static/ict/js/resetPw.js?v=${jsVersion}"></script> 
	<script type="text/javascript" src="<%=request.getContextPath() %>/static/ict/js/md5.js?v=${jsVersion}"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/static/layer/3.1.1/layer.js?v=${jsVersion}"></script>	
	<script type="text/javascript" language="JavaScript"/>
	<title>修改密码</title>
	<meta name="decorator" content="default"/>
	 <script type="text/javascript">
		$(document).ready(function() {
			/* $("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
			}); */
		});
	</script> 
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/info">个人信息</a></li>
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm"  action="${ctx}/resetpw/updatePw" method="post" class="form-horizontal">
		<%-- <form:hidden path="id"/>
		<sys:message content="${message}"/> --%>
		<!-- <div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldpw" name="oldpw" type="password" value="" maxlength="50" minlength="3" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="pw" name="authBO.pw" type="password" value="" onblur="repwonblur()" maxlength="50" minlength="3" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span id="mmts" style="color:#FF0000 ;font-size:13px;line-height: 36px;"></span>
				<div style="clear: both;"></div>	
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="pw2" name="authBO.pw2" type="password" value="" onblur="repwonblur1()" maxlength="50" minlength="3" class="required" equalTo="#newPassword"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span id="mmts1" style="color:#FF0000 ;font-size:13px;line-height: 36px;"></span>
				<div style="clear: both;"></div>
			</div>
		</div>
		<!-- <div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div> -->
		<div class="confirm_content_form_1" >
			<input class="dark"  type="button" name="button3" id="button3" onclick="QDupdatePW();"  value="确定"/>
		</div>
	</form:form>
	<script>
	$(function() {
		//显示提示信息
		showMessage();
	});
	function showMessage() {
		var message = "${message}";
		if (message != "" && message != "null") {
			layer.msg(message, {
				time : 3000,// 不自动关闭
				content : message,
				btnAlign : "c",// 居中
				yes : function(index, layero) {
					layer.close(index);
				},
				
			});
		}
	}
	</script>
	 
	
</body>
</html>