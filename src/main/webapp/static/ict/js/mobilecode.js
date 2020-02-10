
function mobileonblur(){
	var a = /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/;
	if($("#mobile").val()==""){
		$("#mobilets").text("请输入您的手机号码！");
	}else if($("#mobile").val().match(a)){
		$("#mobilets").text("");
	}else{
		$("#mobilets").text("请输入正确的手机号码！");
	}
};
function codeonblur(){
	if($("#code").val()==""){
		$("#codets").text("请输入验证码！");
	}else{
		e={
			code : {
				label : "\u9A8C\u8BC1\u7801\u4E0D\u80FD\u4E3A\u7A7A",
				depends : "required,maxlength",
				parameters : {
					maxlength : 6
				}
			}
		}
		message = checkFormWithValidate(e);
		if(message != ""){
			$("#codets").text("");
			$("#codets").text(message);
		}
	}
};


function sendCode(){
	var mobile = $("#mobile").val();
	var elements = {
			mobile : {
				label : "\u624b\u673a\u53f7\u7801\u000d\u000a",
				depends : "required,mobile,maxlength",
				parameters : {
					maxlength : 11
				}
			}
		};
		var message = checkFormWithValidate(elements);
		if(message != null){
			ict_alert(message);			
		} else {
			$.ajax({
				type: "post",
		        url: "/AC_MAN_PORTAL/a/resetpw/sendMobileCode",
		        data: "mobile="+$("#mobile").val()+"&loginName="+$("#loginName").val(),
		        success: function (data) {		
		        	var da=JSON.parse(data);//使用JavaScript内置函数JSON.parse()将字符串转换为JavaScript对象		        	
		        	if(da.code=="0"){
		        		buttonChange(60);
		        		ict_alert(da.message);
		        	}else{
		        		ict_alert(da.message);
		        	}		        	
		        },
		        error: function (msg) {
		        }
			});
		}
}

function buttonChange(time){
	$("#codetip").text(time+"秒后重新获取");
	$("#Submit3").remove();
	time--;
	if(time==0){
		$("#codetip").text("");
		$("#codetip").append($('<input style="width:92px;padding:3px 3px;height:36px;" type="button" class="button" id="Submit3" name="Submit3" value="发送验证码" onclick="sendCode()"  title="点击发送验证码"/>'));
	}else{
		setTimeout("buttonChange("+time+")", 1000);
	}
}



					