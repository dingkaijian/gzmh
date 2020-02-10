<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh">
    <head>
    	<%@ include file="/WEB-INF/views/include/head.jsp"%>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="ie=edge" />
        <title>Document</title>
        <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
        <!--[if lt IE 9]>
            <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
            <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
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
		<script src="${ctxStatic}/ict/js/mobilecode.js"></script>
		<script src="${ctxStatic}/ict/js/validateRules.js"></script>
		<script src="${ctxStatic}/ict/js/md5.js"></script>
		<script src="${ctxStatic}/ict/js/login.js"></script>
		<script src="${ctxStatic}/ict/js/dialog.js"></script>
		<script src="${ctxStatic}/layer/3.1.1/layer.js?v=${jsVersion}"></script>
        <link rel="stylesheet" href="${ctxStatic}/work/lib/layui/css/layui.css" />
        <link rel="stylesheet" href="${ctxStatic}/work/lib/fontawesome/css/all.css" />
        <link rel="stylesheet" href="${ctxStatic}/work/css/common.css" />
        <link rel="stylesheet" href="${ctxStatic}/work/css/login.css" />
    </head>
    <body>
        <form class="layui-form layui-bg-cyan" id="form" name="form" method="post" action="${ctx}/gzmh/login">
            <div class="login-box" style="height:420px" id="boxId">
                <div class="login-title">
                    科技政务服务平台工作门户
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                        <input
                            type="text"
                            required
                            lay-verify="required"
                            class="layui-input"
                            id="loginName" name="loginName"
                        />
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                        <input
                            type="password"
                            required
                            lay-verify="required"
                            class="layui-input"
                            id="password" name="password"
                       		onkeydown="if(window.event.keyCode==13){return login();}" />
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">验证码</label>
                    <div class="layui-input-block" style="font-size: 0;">
                        <input
                        	id="validateCode" name="validateCode"
                            type="text"
                            required
                            lay-verify="required"
                            class="layui-input code"
                            style="width: 290px; display: inline-block; font-size: 14px; vertical-align: middle;"
                        />
                        
                          <img id="code2"
							src="/AC_MAN_PORTAL/servlet/validateCodeServlet?width=88&height=38&v=${randomNum}" width="70"
							height="38" border="0" align="absmiddle" onchange="time();"
							onclick="$('#code2').attr('src','/AC_MAN_PORTAL/servlet/validateCodeServlet?width=88&height=38&'+new Date().getTime());">
                        
                    </div>
                </div>
                <div class="layui-form-item" id="mobilediv">
                    <label class="layui-form-label">手机号</label>
                    <div class="layui-input-block" style="font-size: 0;">
                        <input
                        	id="mobile" name="authBO.mobile"
                        	onblur="mobileonblur()"
                            type="text"
                            required
                            lay-verify="required"
                            class="layui-input code"
                            style="width: 290px; display: inline-block; font-size: 14px; vertical-align: middle;"
                        />
                    </div>
                </div>
                <div class="layui-form-item" id="mobilecodediv">
                    <label class="layui-form-label">手机验证码</label>
                    <div class="layui-input-block" style="font-size: 0;">
                        <input
                        	id="code" name="mobileCode"
                        	onblur="codeonblur()"
                            type="text"
                            required
                            lay-verify="required"
                            class="layui-input code"
                            style="width: 290px; display: inline-block; font-size: 14px; vertical-align: middle;"
                        />
                        <input style="width:92px;padding:3px 3px;height:36px;" type="button" class="button" id="Submit3" name="Submit3" value="获取验证码" onclick="sendCode()"  title="点击发送验证码"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div
                        class="layui-input-block"
                        style="text-align: right; vertical-align: middle;"
                    >
                        <button class="layui-btn" lay-submit>
                            登录
                        </button>
                    </div>
                </div>
            </div>
        </form>
        <script src="${ctxStatic}/work/lib/layui/layui.all.js"></script>
        <script type="text/javascript">
        $(function() {
    		//显示提示信息
    		showMessage();
    		var mobileCodeSwitch = "${mobileCodeSwitch}"
        		console.log(mobileCodeSwitch);
        		if (mobileCodeSwitch == "0"){
        			document.getElementById("boxId").style.height="350px";
        			var mobilediv =document.getElementById("mobilediv");
            		var mobilecodediv =document.getElementById("mobilecodediv");
            		if(mobilediv){
            			mobilediv.parentNode.removeChild(mobilediv);
            		}
            		if(mobilecodediv){
            			mobilecodediv.parentNode.removeChild(mobilecodediv);
            		}
        		}
    	});
        function login() {
    		$("#form").submit();
    		return false;
    	}

    	function showMessage() {
    		var message = "${message}";
    		if (message != "" && message != "null") {
    			layer.msg(message, {
    				time : 3000,// 不自动关闭
    				content : message,
    				//btn : message=="密码过期，请修改密码"?["确定","修改密码"]:"确定",
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
