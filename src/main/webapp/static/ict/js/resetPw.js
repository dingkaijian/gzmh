
//输入框失去焦点 校验
function repwonblur(){
	var a = document.getElementById("pw");
	var b = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,30}$/;
	var a2 = document.getElementById("pw2");
	if(a.value == ""){
		$("#mmts").text("请输入密码！");
	}else if((a.value).match(b)){
		$("#mmts").text("");
	}else{
		$("#mmts").text("密码至少包含大写字母、小写字母、数字、特殊符号中的三种，且长度不小于8位！");
	}
	if(a.value != "" && a2.value!="" && a2.value != a.value){
		$("#mmts").text("两次密码不一致，请重新输入！");
	}else if (a2.value!="" && a.value!="" && a2.value == a.value){
		$("#mmts1").text("");
	}
};
function repwonblur1(){
	var a = document.getElementById("pw");
	var b = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,30}$/;
	var a2 = document.getElementById("pw2");
	if(a2.value == ""){
		$("#mmts1").text("请输入密码！");
	}else if((a2.value).match(b)){
		$("#mmts1").text("");
	}else{
		$("#mmts1").text("密码至少包含大写字母、小写字母、数字、特殊符号中的三种，且长度不小于8位！");
	}
	if(a2.value != "" && a.value!="" && a2.value != a.value){
		$("#mmts1").text("两次密码不一致，请重新输入！");
	}else if (a2.value!="" && a.value!="" && a2.value == a.value){
		$("#mmts").text("");
	}
};

	
	//修改密码校验
	function QDupdatePW(){
		var elements = {
				oldpw : {
					label : "旧密码",
					depends : "required,nospace,maxlength",
					parameters : {
						maxlength : 120
					}
				},	
				pw : {
					label : "新密码",
					depends : "required,nospace,maxlength",
					parameters : {
						maxlength : 120,
						mask : {
							regexp : /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,30}$/,
							message : "密码至少包含以下4中类别的3种：大写字母、小写字母、数字、特殊符号，且长度不小于8位！"
						}
					}
				},				
				pw2 : {
					label : "确认新密码",
					depends : "validateWhen",
					parameters : {
						validateWhen : function() {
							if (document.forms[0].pw2.value != document.forms[0].pw.value) {
								return "\u4e24\u6b21\u8f93\u5165\u7684\u5bc6\u7801\u4e0d\u76f8\u540c,\u8bf7\u91cd\u65b0\u8f93\u5165\uff01"
							}
						}
					}
				}/*,				
				validateCode : {
					label : "验证码",
					depends : "required,nospace,maxlength",
					parameters : {
						maxlength : 4
					}
				}*/
			}
		QDFunchk(elements);
	}
	
	

	//前端验证
	function QDFunchk(elements){
				if (document.forms[0].pw2.value != document.forms[0].pw.value) {
					ict_alert("\u4e24\u6b21\u8f93\u5165\u7684\u5bc6\u7801\u4e0d\u76f8\u540c,\u8bf7\u91cd\u65b0\u8f93\u5165\uff01");
				}else if (document.getElementById("pw").value.length < 8) {
					ict_alert("\u5BC6\u7801\u957F\u5EA6\u4E0D\u80FD\u5C0F\u4E8E8\u4F4D\uFF0C\u8BF7\u68C0\u67E5\u540E\u91CD\u65B0\u8F93\u5165");
		
				} else {
					if (checkPassWords(document.getElementById("pw2").value) < 3) {
						ict_alert("密码至少包含以下4中类别的3种：大写字母、小写字母、数字、特殊符号");
						//document.forms[0].password.focus()
					} else {				
							var passwordObj = document.getElementById("pw");
							var password = sm_password(passwordObj.value);
							passwordObj.value=password;
							
							var passwordObj2 = document.getElementById("pw2");
							password = sm_password(passwordObj2.value);
							passwordObj2.value=password;
							var passwordObj3 = document.getElementById("oldpw");
							if (passwordObj3!=null) {
								var password1 = sm_password(passwordObj3.value);
								passwordObj3.value=password1;
							}
							function regConfir(){
								document.getElementById("inputForm").submit();
							}				
							ict_confirm("信息将保存，保存成功后将完成密码修改，请牢记登录名及密码！确定保存信息？",'','',regConfir,'');					
						}
					
			}	
	}
	
	
	
	
	//SM2密码加密
	function sm_password(password){
		//加密key值
		var pubkeyHex= '049c3e914350086279c0a09ae2e6056d87734bf6d03d33a5d70aad9cb0412abed463ebc5e8510318b5e8cc0a70f14f079e7aa8fb36d561425fe85ed2970168c9ae';
		var cipherMode = 'SM2';//加密方式
		var cipher = new SM2Cipher(cipherMode);
		var msgData = CryptoJS.enc.Utf8.parse(password);
	    var userKey = cipher.CreatePoint(pubkeyHex);
	    msgData = cipher.str2Bytes(msgData.toString());
	    var encryptData = cipher.Encrypt(userKey, msgData);
		return encryptData;
	}