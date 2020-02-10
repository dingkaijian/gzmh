// 对标签信息的配置过程
/*
var validate_elements = {
	name: {
		// 标签名称
		label:"",
		depends:"required,minlength,maxlength",
		// 规则中需要的参数
		parameters: {
			// 最小长度
			minlength:2,
			// 最大长度
			maxlength:10,
			// 正则表达式
			mask: {
				regexp:/\d/,
				message: "只能是以为数字"
			}
			// 用户自己的验证方法或接口
			validateWhen : function() {
					// 用户自定义验证方法，方法没有不需要任何参数，如有需要，用户可以自定义获取
			}
		}
	}	
}*/

// 验证规则
var validate_rules = {
	// 不能为空
	required : function(element) {
			// 返回信息
			var returnMessage = null;
			// 获取标签的值
			var value = element["value"] ;
			// 验证过程
			if(value == "") {
				returnMessage = element["label"] + "不能为空！";	
			}
			// 返回验证信息
			return returnMessage;
		},
	// 最小长度
	minlength: function(element) {
			// 返回信息
			var returnMessage = null;
			// 获取标签的值
			var value = element["value"];
			// 最小长度
			var minlength = element["parameters"]["minlength"];
			// 验证过程
			if(strlen(value) < minlength)
				returnMessage = element["label"] + "的长度不能少于" + minlength + "个字符！";
			// 返回验证信息
			return returnMessage;
		},
	// 最大长度
	maxlengthen: function(element) {
			// 返回信息
			var returnMessage = null;
			// 获取标签的值
			var value = element["value"];
			// 最大长度
			var maxlength = element["parameters"]["maxlength"];
			// 验证过程
			if(strlen(value) > maxlength)
				returnMessage = element["label"] + "的长度不能多于" + maxlength + "个字符！";
			// 返回验证信息
			return returnMessage;
		},
	// 最大长度
	maxlength: function(element) {
			// 返回信息
			var returnMessage = null;
			// 获取标签的值
			var value = element["value"];
			// 最大长度
			var maxlength = element["parameters"]["maxlength"];
			// 验证过程
			if(strlen(value) > maxlength)
				returnMessage = element["label"] + "的长度不能多于" + maxlength + "个字符！";
			// 返回验证信息
			return returnMessage;
		},
		
	// 正则表达式验证
	mask : function(element) {
			// 返回信息
			var returnMessage = null;
			// 获取标签的值
			var value = element["value"];
			var mask = element["parameters"]["mask"];
			if(!mask["regexp"].test(value)) {
				returnMessage = mask["message"];	
			}
			return returnMessage;
		},
	
	// 整数验证
	integer : function(element){
		  // 返回信息
		  var returnMessage=null;
		  //获取标签值
		  var value=element["value"];
		  //验证过程
		  var iValue=parseInt(value);
		   
		  if (!/^(([1-9]\d*)|0)$/.test(value) || isNaN(iValue))  
			  returnMessage=element["label"]+"应为整数！";  
		  else if(!(iValue >= -2147483648)) 
			  returnMessage=element["label"]+"数值太小！"; 
		  else if(!(iValue <= 2147483647)) 
			  returnMessage=element["label"]+"数值太大！";
		  return returnMessage;
		},
		
	// 浮点数验证 
	double : function(element){
		 // 返回信息
		  var returnMessage=null;
		 //获取标签值
		  var value=element["value"];
         //验证过程
		  var iValue = parseFloat(value);
		  		  //if (!/^([1-9]\d*\.\d*)|(0\.\d*[1-9]\d*)|([1-9]\d*)$/.test(value) ||isNaN(iValue)) 
		  
		 if (!/^(([1-9]\d*)|0)(\.\d+)?$/.test(value)||isNaN(iValue))
		  		returnMessage=element["label"]+"应为小数或整数！";
		  return returnMessage;
	    },
	
	// 日期类型验证
	 date : function(element){
		  // 返回信息
		  var returnMessage=null;
		 //获取标签值
		  var value=element["value"];
		  var reg=/^((((((0[48])|([13579][26])|([2468][048]))00)|([0-9][0-9]((0[48])|([13579][26])|([2468][048]))))-02-29)|(((000[1-9])|(00[1-9][0-9])|(0[1-9][0-9][0-9])|([1-9][0-9][0-9][0-9]))-((((0[13578])|(1[02]))-31)|(((0[1,3-9])|(1[0-2]))-(29|30))|(((0[1-9])|(1[0-2]))-((0[1-9])|(1[0-9])|(2[0-8]))))))$/;
		  if(!reg.test(value))
		   return returnMessage=element["label"]+"格式应为YYYY-MM-DD(例如:2003-08-04)";
		},
		
	// 邮箱验证
	email : function (element) {
		  // 返回信息
		  var returnMessage=null;
		 //获取标签值
		  var emailStr=element["value"];
		  var repexg= /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]+){1,3})$/;
			   if(!repexg.test(emailStr))
			   		returnMessage=element["label"]+"不是正确的邮箱地址！";
               return returnMessage;
            },
			
      //身份证验证
      idcard : function(element){
		       // 返回信息
		      var returnMessage=null;
		       //获取标签值
		      var value=element["value"];
		      var idCardNoUtil = {
		    		    provinceAndCitys: {11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"},
		    		    powers: ["7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2"],
		    			parityBit: ["1","0","X","9","8","7","6","5","4","3","2"],
		    		    genders: {male:"男",female:"女"},
		    			checkAddressCode: function(addressCode) {
		    				var check = /^[1-9]\d{5}$/.test(addressCode);
		    				if(!check) return false;
		    				if(idCardNoUtil.provinceAndCitys[parseInt(addressCode.substring(0,2))]){
		    					return true;
		    				} else {
		    					return false;
		    				}
		    			},
		    		 
		    		    checkBirthDayCode: function(birDayCode){
		    		        var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(birDayCode);
		    		        if(!check) return false;
		    		        var yyyy = parseInt(birDayCode.substring(0,4),10);
		    		        var mm = parseInt(birDayCode.substring(4,6),10);
		    		        var dd = parseInt(birDayCode.substring(6),10);
		    		        var xdata = new Date(yyyy,mm-1,dd);
		    		        if(xdata > new Date()){
		    		            return false;//生日不能大于当前日期
		    		        }else if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) ){
		    		            return true;
		    		        }else{
		    		            return false;
		    		        }
		    		    },
		    		 
		    		    getParityBit: function(idCardNo){
		    		        var id17 = idCardNo.substring(0,17);
		    		        var power = 0;
		    		        for(var i=0;i<17;i++){
		    		            power += parseInt(id17.charAt(i),10) * parseInt(idCardNoUtil.powers[i]);
		    		        }
		    		        var mod = power % 11;
		    		        return idCardNoUtil.parityBit[mod];
		    		    },
		    		 
		    		    checkParityBit: function(idCardNo){
		    		        var parityBit = idCardNo.charAt(17).toUpperCase();
		    		        if(idCardNoUtil.getParityBit(idCardNo) == parityBit){
		    		            return true;
		    		        }else{
		    		            return false;
		    		        }
		    		    },
		    		 
		    		    checkIdCardNo: function(idCardNo){
		    		        //15位和18位身份证号码的基本校验
		    		        var check = /^[0-9]{15}|([0-9]{17}([0-9]|x|X))$/.test(idCardNo);
		    		        if(!check) return false;
		    		        //判断长度为15位或18位
		    		        if(idCardNo.length==15){
		    		            return idCardNoUtil.check15IdCardNo(idCardNo);
		    		        }else if(idCardNo.length==18){
		    		            return idCardNoUtil.check18IdCardNo(idCardNo);
		    		        }else{
		    		            return false;
		    		        }
		    		    },
		    		    //校验15位的身份证号码
		    		    check15IdCardNo: function(idCardNo){
		    		        //15位身份证号码的基本校验
		    		        //在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
		    		        var check = /^[1-9][0-9]{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))[0-9]{3}$/.test(idCardNo);
		    		        if(!check) return false;
		    		        //校验地址码
		    		        var addressCode = idCardNo.substring(0,6);
		    		        check = idCardNoUtil.checkAddressCode(addressCode);
		    		        if(!check) return false;
		    		            var birDayCode = '19' + idCardNo.substring(6,12);
		    		        //校验日期码
		    		        return idCardNoUtil.checkBirthDayCode(birDayCode);
		    		    },
		    		    //校验18位的身份证号码
		    		    check18IdCardNo: function(idCardNo){
		    		        //18位身份证号码的基本格式校验
		    		        //在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
		    		        var objReg = /^[1-9][0-9]{5}[1-9][0-9]{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))[0-9]{3}([0-9]|x|X)$/i;
		    		        var check = objReg.test(idCardNo);
		    		        if(!check) return false;
		    		        //校验地址码
		    		        var addressCode = idCardNo.substring(0,6);
		    		        check = idCardNoUtil.checkAddressCode(addressCode);
		    		        if(!check) return false;
		    		        //校验日期码
		    		        var birDayCode = idCardNo.substring(6,14);
		    		        check = idCardNoUtil.checkBirthDayCode(birDayCode);
		    		        if(!check) return false;
		    		        //验证校检码
		    		        return idCardNoUtil.checkParityBit(idCardNo);
		    		    },
		    		    formateDateCN: function(day){
		    		        var yyyy =day.substring(0,4);
		    		        var mm = day.substring(4,6);
		    		        var dd = day.substring(6);
		    		        return yyyy + '-' + mm +'-' + dd;
		    		    },
		    		    //获取信息
		    		    getIdCardInfo: function(idCardNo){
		    		        var idCardInfo = {
		    		            gender:"", //性别
		    		            birthday:"" // 出生日期(yyyy-mm-dd)
		    		        };
		    		        if(idCardNo.length==15){
		    		            var aday = '19' + idCardNo.substring(6,12);
		    		            idCardInfo.birthday=idCardNoUtil.formateDateCN(aday);
		    		            if(parseInt(idCardNo.charAt(14))%2==0){
		    		                idCardInfo.gender=idCardNoUtil.genders.female;
		    		            }else{
		    		                idCardInfo.gender=idCardNoUtil.genders.male;
		    		            }
		    		        }else if(idCardNo.length==18){
		    		            var aday = idCardNo.substring(6,14);
		    		            idCardInfo.birthday=idCardNoUtil.formateDateCN(aday);
		    		            if(parseInt(idCardNo.charAt(16))%2==0){
		    		                idCardInfo.gender=idCardNoUtil.genders.female;
		    		            }else{
		    		                idCardInfo.gender=idCardNoUtil.genders.male;
		    		            }
		    		        }
		    		        return idCardInfo;
		    		    },
		    		 
		    		    getId15:function(idCardNo){
		    		        if(idCardNo.length==15){
		    		            return idCardNo;
		    		        }else if(idCardNo.length==18){
		    		            return idCardNo.substring(0,6) + idCardNo.substring(8,17);
		    		        }else{
		    		            return null;
		    		        }
		    		    },
		    		 
		    		    getId18: function(idCardNo){
		    		        if(idCardNo.length==15){
		    		            var id17 = idCardNo.substring(0,6) + '19' + idCardNo.substring(6);
		    		            var parityBit = idCardNoUtil.getParityBit(id17);
		    		            return id17 + parityBit;
		    		        }else if(idCardNo.length==18){
		    		            return idCardNo;
		    		        }else{
		    		            return null;
		    		        }
		    		    }
		    		};
		       //验证15位身份证号的正则表达式
		      var reg1=idCardNoUtil.check15IdCardNo(value);
		       //验证18位身份证号的正则表达式
		      var reg2=idCardNoUtil.check18IdCardNo(value);
		      if(!(reg1||reg2)){
				  return returnMessage=element["label"]+"不是正确的身份证号码！";
			  }
		   },
	
	 //邮编验证
     postcode : function(element){
         // 返回信息
        var returnMessage=null;
	   //获取标签值
	    var value=element["value"];
	    var reg=/^\d{6}$/;
	     if(!reg.test(value)){
		   returnMessage= element["label"]+"只能由6位数字组成！";
		  }
	    return returnMessage;
	 },

   //手机号码验证
    mobile : function(element){
		   // 返回信息
           var returnMessage=null;
	       //获取标签值
	       var value=element["value"];
		  // var reg2=/^((\+)?(\d{1,3})?)1[3|5|8|4|7]\d{9}$/;
	       var reg2=/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/;
		      if(!reg2.test(value)){
				  returnMessage=element["label"]+"不是正确的手机号码！";
			  }
		   return returnMessage;
		},
	//固定电话验证(有无区号均可)
	telephone : function(element){
		   // 返回信息
           var returnMessage=null;
	       //获取标签值
	       var value=element["value"];
		   var regexp=/^((\d{1,3})?)((0[0-9]{2,3})(\-)?)?([2-9][0-9]{6,7})+((\-)([0-9]{1,4}))?$/;
		   if(!regexp.test(value)){
				returnMessage=element["label"]+"不是正确的电话号码！";
		   }
		   return returnMessage;
		},
	//固定电话验证(必须填写区号)
	telephoneM : function(element){
		   // 返回信息
           var returnMessage=null;
	       //获取标签值
	       var value=element["value"];
		   var regexp=/^((\d{1,3})?)((0[0-9]{2,3})(\-)?)([2-9][0-9]{6,7})+((\-)([0-9]{1,4}))?$/;
		   if(!regexp.test(value)){
				returnMessage=element["label"]+"不是正确的电话号码（必须填写区号）！";
		   }
		   return returnMessage;
		},
	//传真号码验证(有无区号均可)
	fax : function(element){
		   // 返回信息
           var returnMessage=null;
	       //获取标签值
	       var value=element["value"];
		   var regexp=/^((\d{1,3})?)((0[0-9]{2,3})(\-)?)?([2-9][0-9]{6,7})+((\-)([0-9]{1,4}))?$/;
		   if(!regexp.test(value)){
				returnMessage=element["label"]+"不是正确的传真号码！";
		   }
		   return returnMessage;
		},
	//传真号码验证(必须填写区号)
	faxM : function(element){
		   // 返回信息
           var returnMessage=null;
	       //获取标签值
	       var value=element["value"];
		   var regexp=/^((\d{1,3})?)((0[0-9]{2,3})(\-)?)([2-9][0-9]{6,7})+((\-)([0-9]{1,4}))?$/;
		   if(!regexp.test(value)){
				returnMessage=element["label"]+"不是正确的传真号码（必须填写区号）！";
		   }
		   return returnMessage;
		},
	//验证是否为电话号码（包括固话和手机号码）
	isphone : function(element) {
		var returnMessage = null;
		var value = element["value"];
		var regexp = "()-+/1234567890";
		for (i=0;i<value.length;i++) {
			if (regexp.indexOf(value.charAt(i))==-1) {
				returnMessage = element["label"]+"不是正确的电话号码！";
			}
		}
		return returnMessage
	},
    //验证空格
    nospace : function(element){
        // 返回信息
        var returnMessage=null;
	    //获取标签值
	    var value=element["value"];
   
        var regexp=/^\S+$/;
	        if(!regexp.test(value))
		      returnMessage=element["label"]+"不能包含空格信息！";
		    return returnMessage;
       },
	validateWhen : function(element) {
		return 	element["parameters"]["validateWhen"]();
	},
	checkPic : function(element) {
		// 返回信息
		var returnMessage = null;
		// 获取标签的值
		var value = element["value"] ;
		// 验证过程
		if(value == "") {
			returnMessage = element["label"] + "未上传！";	
		}
		// 返回验证信息
		return returnMessage;
	},
   
}

// 验证表单方法
function checkFormWithValidate(elements) {
	console.log("验证表单方法");
	// 对所有的标签配置信息进行验证
	// checkElements(elements);
	// 临时保存一个配置好的JSON对象
	var element;
	// 需要进行的验证
	var depends;
	// 返回的错误信息
	var message;
	for(var element_name in elements) {
		// 获取验证表单对象
		element = elements[element_name];
		console.log(element)
		// 获取标签对象的值
		element["value"] = getVal(element_name);
		// 拆分验证信息
		depends = element["depends"].split(",");
		// 根据验证信息，逐项进行验证
		for(var i = 0; i < depends.length; i++) {
			message = null;
			depends[i] = trim(depends[i]);
			// 判断是否存在该验证规则，如果采用checkElements(elements)方法验证后，则不需要在进行判断
			if(	validate_rules[depends[i]] != null) {
				// 进行验证规则的判断
				if(depends[i] != "required" && depends[i] != "validateWhen" && depends[i] != "checkPic" && element["value"] == "")
					continue;
				message = validate_rules[depends[i]](element);
			} else {
				// 保存验证规则不存在的信息
				message = "验证规则[" + depends[i] + "]不存在！";
			}
			// 出现错误信息，则返回消息内容
			if(message!=null)
				return message;
		}
	}	
}
// 用来判断表单对象配置信息的正确性
// 验证内容：
//	1. 验证depends中的规则信息是否正确
// 	2. depends需要参数信息，验证参数是否存在
function checkElements(elements) {
	
}
//获取名称为name的标签的值
function getVal(name) {
	var value ;
	if(document.getElementsByName(name)[0]){
		value = document.getElementsByName(name)[0].value;
	}else{
		value = document.getElementById(name).value;
	}
	value = value == null? "":value;
	return value;
}
// 判断字符串长度
function strlen(str){	var i;
	var len = 0;
	for (i=0;i<str.length;i++){
		if (str.charCodeAt(i)>255) len+=2; else len++;
	}
	return len;
}
// 去除字符串两端的空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g,"");	
}


	function validatezjlx(cardType,cardNo,birthday)
	{
	 if (cardType=="1" && (cardNo.length != 15 && cardNo.length != 18))
		{
			$("#dialog-alert").html('<img src=\" ../../style/images/alert.gif\" style=\"float:left;margin-left:10px\"><div style=\"margin-top:18px;line-height:150%;\">&nbsp;&nbsp;&nbsp;身份证位数不正确!</div>').dialog('open');
		}
		else if(cardType=="1" && (birthday == null || birthday == "" || birthday == " ")) {
			return;
		}
		else if(cardType=="1" && cardNo.length == 15 && (birthday.substring(0,2)!="19" || cardNo.substring(6,8)!=birthday.substring(2,4)))
		{
			$("#dialog-alert").html('<img src=\" ../../style/images/alert.gif\" style=\"float:left;margin-left:10px\"><div style=\"margin-top:18px;line-height:150%;\">&nbsp;&nbsp;&nbsp;出生年份与身份证不匹配!</div>').dialog('open');
		}
		else if(cardType=="1" && cardNo.length == 15 &&cardNo.substring(8,10)!=birthday.substring(5,7))
		{
			$("#dialog-alert").html('<img src=\" ../../style/images/alert.gif\" style=\"float:left;margin-left:10px\"><div style=\"margin-top:18px;line-height:150%;\">&nbsp;&nbsp;&nbsp;出生月份与身份证不匹配!</div>').dialog('open');
		}
		else if(cardType=="1" && cardNo.length == 15 &&cardNo.substring(10,12)!=birthday.substring(8,10))
		{
			$("#dialog-alert").html('<img src=\" ../../style/images/alert.gif\" style=\"float:left;margin-left:10px\"><div style=\"margin-top:18px;line-height:150%;\">&nbsp;&nbsp;&nbsp;出生日期与身份证不匹配!</div>').dialog('open');
		}
		else if(cardType=="1" && cardNo.length == 18 &&cardNo.substring(6,10)!=birthday.substring(0,4))
		{
			$("#dialog-alert").html('<img src=\" ../../style/images/alert.gif\" style=\"float:left;margin-left:10px\"><div style=\"margin-top:18px;line-height:150%;\">&nbsp;&nbsp;&nbsp;出生年份与身份证不匹配!</div>').dialog('open');
		}
		else if(cardType=="1" && cardNo.length == 18 &&cardNo.substring(10,12)!=birthday.substring(5,7))
		{
			$("#dialog-alert").html('<img src=\" ../../style/images/alert.gif\" style=\"float:left;margin-left:10px\"><div style=\"margin-top:18px;line-height:150%;\">&nbsp;&nbsp;&nbsp;出生月份与身份证不匹配!</div>').dialog('open');
		}
		else if(cardType=="1" && cardNo.length == 18 &&cardNo.substring(12,14)!=birthday.substring(8,10))
		{
			$("#dialog-alert").html('<img src=\" ../../style/images/alert.gif\" style=\"float:left;margin-left:10px\"><div style=\"margin-top:18px;line-height:150%;\">&nbsp;&nbsp;&nbsp;出生日期与身份证不匹配!</div>').dialog('open');
		}
		else
		{
			return true;
		}
	
	}
	//检测电话号码
function isphone(str)
{
	var number_chars = "()-+/1234567890";
	var i;
	for (i=0;i<str.length;i++)
	{
	if (number_chars.indexOf(str.charAt(i))==-1) return false;
	}
	return true;
}

function jynumber(bm,name){
  if(isphone(trim(bm))==false){
    $("#dialog-alert").html('<img src=\" ../../style/images/alert.gif\" style=\"float:left;margin-left:10px\"><div style=\"margin-top:18px;line-height:150%;\">&nbsp;&nbsp;&nbsp;'+name+'只可包含-、+、/、()和数字！</div>').dialog('open');
	return false;	
  }else{
	 return true;	
 }

}	

/**
** 操作之前判断选择的合法性
**/
function validate(bm,name){
	var i,j,prj;	
	prj = document.getElementsByName(bm);
		
	for(i = 0 ,j = 0; i < prj.length ;i++){
			if(prj[i].checked == true){
					j++;					
			}
	}
		
	if(j==0){
			$("#dialog-alert").html('<img src=\" ../../style/images/alert.gif\" style=\"float:left;margin-left:10px\"><div style=\"margin-top:18px;line-height:150%;\">&nbsp;&nbsp;&nbsp;'+name+'不能为空！</div>').dialog('open');
			return false;	
	}
	return true;
}

	function qql(str) {
		if(str>=10){
		str=str
		}else{
		 str=str.substring(1);
		}
    	return str;	
}
function checkPass2(s){   
	if(s.length < 8){   
	      return 0;   
	}   
	 var ls = 0;   
    if(s.match(/([a-z])+/)){   
	 ls++;   
	} 
	if(s.match(/([0-9])+/)){   
	   ls++;     
	}   
	if(s.match(/([A-Z])+/)){   
	  ls++;   
	}   
	
     return ls   
}
//控制正则表达式是否含有大写字母、小写字母、数字、特殊字符
function checkPassWords(s){     
	var ls = 0;   
    if(s.match(/([a-z])+/)){   
    	ls++;   
	} 
	if(s.match(/([0-9])+/)){   
		ls++;     
	}   
	if(s.match(/([A-Z])+/)){   
		ls++;   
	}   
	if(s.match(/(\[\]\{\}\(\)\*\+\.\-\?\\\^!~`@#$%=<>,)+/)){   
		ls++;   
	} 	
    return ls   
}
//密码弱中强校验-----开始
/*
    定义一个函数，对给定的数分为四类(判断密码类型)，返回十进制1，2，4，8
    数字 0001 -->1  48~57
    小写字母 0010 -->2  97~122
    大写字母 0100 -->4  65~90
    特殊 1000 --> 8 其它
*/
function charType(num){
    if(num >= 48 && num <= 57){
        return 1;
    }
    if (num >= 97 && num <= 122) {
        return 2;
    }
    if (num >= 65 && num <= 90) {
        return 4;
    }
    return 8;
}
//密码弱中强校验-----结束