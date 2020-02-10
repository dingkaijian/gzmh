<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务事项</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function viewComment(href){
			top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				}
			});
			return false;
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function updateAffair(affairid,type){
			$.ajax({
				type : "post",
				url : "/AC_MAN_PORTAL/a/sys/man/updateuseraffairs",
				data: {"affairid":affairid,"type":type},
				success : function(data) {
					if(data=="success"){
						console.log("success");
					}
					else if(data=="error"){
						console.log("error");
					}
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a style="color:#2fa4e7;" href="${ctx}/sys/man/useraffairs">我的关注</a></li>
		<li class="active"><a style="color:#2fa4e7;" href="${ctx}/sys/man/affairslist">服务事项</a></li>		
	</ul>	
	<ul>
		<li style="border-bottom: 1px dashed #7cc7ef;color: #444;list-style: none;">
		</li>
		<c:forEach items="${affairs}" var="affair">
			<li style="border-bottom: 1px dashed #7cc7ef;color: #444;list-style: none;line-height:3;">
				<span>事项名称： ${affair.affairname}</span>
				<span>事项编码：${affair.implementCode}</span>
				<span><a onclick="updateAffair('${affair.affairid}','0');">关注</a></span>
				<span><a onclick="updateAffair('${affair.affairid}','1');">取消关注</a></span>
			</li>
		</c:forEach>
	</ul>
	<sys:message content="${message}"/>
</body>
</html>