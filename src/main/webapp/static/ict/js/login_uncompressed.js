﻿$(function() {
	$("#form").validate({
		rules : {
			loginName : {
				required : true
			},
			password : {
				required : true
			},
			validateCode: {
				required : true
			}
		},
		messages : {
			loginName : {
				required : "登录名不能为空"
			},
			password : {
				required : "密码不能为空"
			},
			validateCode : {
				required : "验证码不能为空"
			}
		},
		// 失去焦点时不执行验证
		onfocusout : false,
		// 重写showErrors，结合layer显示消息
		showErrors: function(errorMap, errorList){
			$.each(errorList, function(i, v){
				//layer.tips(v.message,v.element,{time:2000});
				layer.msg(v.message);
				return false;
			});
		},
		submitHandler : function(form) {
			var key = $("input[name='key']").val();
			$("#password_pbe").val(encode.encode64($("#password").val()));
			//var password = CryptoJS.MD5(key + "-" + CryptoJS.MD5($("#password").val()));
			var password = $("#password").val();
			//SM2对前台密码进行加密
			password=sm_password(password);
			$("#password").val(password);
			//防止表单重复提交
			 document.getElementById("login-submit").disabled='disabled';
			// 验证通过后提交表单
			form.submit();
		}
	});
});

/* 全局变量 begin  */
var encode = {
	keyStr:"ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv" + "wxyz0123456789+/" + "="
};
/* 全局变量 end  */


/**
 * 加密
 * @author cmh
 * @since 2016-01-15 22:23:23
 * @version 1.0
 */
encode.encode64 = function(input) {
	var output = "";
	var chr1, chr2, chr3 = "";
	var enc1, enc2, enc3, enc4 = "";
	var i = 0;
	do {
		chr1 = input.charCodeAt(i++);
		chr2 = input.charCodeAt(i++);
		chr3 = input.charCodeAt(i++);
		enc1 = chr1 >> 2;
		enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
		enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
		enc4 = chr3 & 63;
		if (isNaN(chr2)) {
			enc3 = enc4 = 64;
		} else if (isNaN(chr3)) {
			enc4 = 64;
		}
		output = output + encode.keyStr.charAt(enc1) + encode.keyStr.charAt(enc2)
				+ encode.keyStr.charAt(enc3) + encode.keyStr.charAt(enc4);
		chr1 = chr2 = chr3 = "";
		enc1 = enc2 = enc3 = enc4 = "";
	} while (i < input.length);
	return output;
};
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
