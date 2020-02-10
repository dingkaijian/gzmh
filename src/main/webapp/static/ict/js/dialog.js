/*$(function() {
	$("#dialog-add").dialog( {
		autoOpen : false,
		modal : true,
		resizable : false,
		hide : "fade",
		bgiframe : false,
		height : "auto"
		
	});
	$("#dialog-upload").dialog( {
		autoOpen : false,
		modal : true,
		resizable : false,
		hide : "fade",
		bgiframe : false,
		height : "auto"
		
	});
	$("#dialog-alert").dialog( {
		autoOpen : false,
		modal : true,
		resizable : false,
		hide : "fade",
		bgiframe : false,
		height : "auto",
		buttons : {
			"\u786e\u5b9a" : function() {
				$(this).dialog("close");
			}
		}
	});
	$("#dialog-alert")
	.html(
			'<img src="../statics/css/images/alert.gif" style="float:left;margin-left:10px"><div id="messageShow" style="margin-top:18px;line-height:150%;">&nbsp;&nbsp;&nbsp;</div>');
	
	$("#dialog-alert1").dialog( {
		autoOpen : false,
		modal : true,
		resizable : false,
		hide : "fade",
		bgiframe : false,
		height : "auto",
		buttons : {
			"\u786e\u5b9a" : function() {
				$(this).dialog("close");
			}
		},
		position:"top"
	});
	$("#dialog-alert1")
	.html(
			'<img src="../statics/css/images/alert.gif" style="float:left;margin-left:10px"><div id="messageShow" style="margin-top:18px;line-height:150%;">&nbsp;&nbsp;&nbsp;</div>');
});

function alert_qd(a, b) {
	$("#dialog-alert").dialog({
		autoOpen : false,
		modal : true,
		resizable : false,
		hide : "fade",
		bgiframe : false,
		height : "auto",
		buttons : {
			"\u786e\u5b9a" : function() {
				b.call();
				$(this).dialog("close");
			}
		}
	});
	$("#dialog-alert")
			.html(
					'<img src="../statics/css/images/alert.gif" style="float:left;margin-left:10px"><div style="margin-top:18px;line-height:150%;">&nbsp;&nbsp;&nbsp;'
							+ a + "</div>").dialog("open");
};
*//**
** 按钮全选与反全选
**//*
function chkall(input1,input2){
    var objForm = document.forms[input1];
    var objLen = objForm.length;
    for (var iCount = 0; iCount < objLen; iCount++){
        if (input2.checked == true){
            if (objForm.elements[iCount].type == "checkbox")
            {
                objForm.elements[iCount].checked = true;
            }
        }else{
            if (objForm.elements[iCount].type == "checkbox"){
                objForm.elements[iCount].checked = false;
            }
        }
    }
}
function confirm_ky(a, b) {
	$("#dialog-confirm").dialog({
		autoOpen : false,
		modal : true,
		resizable : false,
		position : "center",
		hide : "fade",
		bgiframe : false,
		height : "auto",
		buttons : {
			"\u786e\u5b9a" : function() {
				b.call();
				$(this).dialog("close")
			},
			"\u53d6\u6d88" : function() {
				$(this).dialog("close")
			}
		}
	});
	$("#dialog-confirm")
			.html(
					'<img src="../statics/css/images/alert.gif" style="float:left;margin-left:10px"><div style="margin-top:18px;line-height:150%;">&nbsp;&nbsp;&nbsp;'
							+ a + "</div>").dialog("open")
};
function confirm_ky1(a, b) {
	$("#dialog-confirm").dialog({
		autoOpen : false,
		modal : true,
		resizable : false,
		position : "top",
		hide : "fade",
		bgiframe : false,
		height : "auto",
		buttons : {
			"\u786e\u5b9a" : function() {
				b.call();
				$(this).dialog("close")
			},
			"\u53d6\u6d88" : function() {
				$(this).dialog("close")
			}
		}
	});
	$("#dialog-confirm")
			.html(
					'<img src="../statics/css/images/alert.gif" style="float:left;margin-left:10px"><div style="margin-top:18px;line-height:150%;">&nbsp;&nbsp;&nbsp;'
							+ a + "</div>").dialog("open")
};*/

/**
 * 基于layer的弹出框，弹框标题待默认title，systemName为全局变量
 * 
 * @param message
 *            显示内容
 */
function ict_alert(message) {
	layerAlert("", message);
}

/**
 * 基于layer的弹出框
 * 
 * @param title
 *            自定义标题
 * @param message
 *            显示内容
 */
function ict_alert2(title, message) {
	layerAlert(title, message);
}

/**
 * 初始化弹出框
 * 
 * @param t
 *            标题
 * @param message
 *            显示内容
 */
function layerAlert(t, message) {
	message = $.trim(message);
	if (message != null && message != 'null' && message != '') {
		// 如果在框架中，则父窗口弹出，用于处理：子窗口内容太长导致弹框以内容高度居中而不在可见范围内居中
		if (self.frameElement) {
			showLayerAlertAtParent(t, message)			
		} else 
		{
			showLayerAlertAtSub(t, message);
		}

	}
}
/**
 * 在子窗口中弹出
 * 
 * @param t
 * @param message
 */
function showLayerAlertAtSub(t, message) {
	var icon = selectIcon(message);
	layer.alert(message, {
		icon : icon,
		title : t,
		skin : "layui-layer-lan-ict",// 自定义的弹出框样式
		time : 0,// 不自动关闭
		btn : "确定",
		btnAlign : "c",// 居中,
		closeBtn : 0,
		shade : 0,// 不显示遮罩层
		scrollbar : false,// 不允许滚动条滚动
		yes : function(index, layero) {
			layer.close(index);
		}
	});
}

/**
 * 在父窗口中弹出
 * 
 * @param t
 * @param message
 */
function showLayerAlertAtParent(t, message) {
	var icon = selectIcon(message);
	layer.alert(message, {
		icon : icon,
		title : t,
		skin : "layui-layer-lan-ict",// 自定义的弹出框样式
		time : 0,// 不自动关闭
		btn : "确定",
		btnAlign : "c",// 居中,
		closeBtn : 0,
		shade : 0,// 不显示遮罩层
		scrollbar : false,// 不允许滚动条滚动
		yes : function(index, layero) {
			layer.close(index);
		}
	});
}

/**
 * 根据内容设置显示的图标
 * 
 * @param message
 * @returns {Number}
 */
function selectIcon(message) {
	var icon = 0;// 叹号
	if (message.indexOf("成功") != -1) {
		icon = 1;// 正确
	}
	if (message.indexOf("失败") != -1 || message.indexOf("异常") != -1
			|| message.indexOf("无效") != -1 || message.indexOf("错误") != -1) {
		icon = 2;// 错误
	}
	return icon;
}

function ict_confirm(confirm_message,success_message,failure_message,success_function,failure_function){
	layer.confirm(
			confirm_message,
			{
				btn:['确定','取消'],
				skin : "layui-layer-lan-ict"	
			},
			function(){
				if(success_message!=null&&''!=success_message){
					ict_alert(success_message);
				}
				if(success_function!=null&&''!=success_function){
					success_function();
				}
			},
			function(){
				if(failure_message!=null&&''!=failure_message){
					ict_alert(failure_message);
				}
				if(failure_function!=null&&''!=failure_function){
					failure_function();
				}
			}
	)
}
function ict_confirm2(confirm_message,success_message,failure_message,success_function,failure_function){
	layer.confirm(
			confirm_message,
			{
				icon:1,			
				title:'科技部政务服务平台用户注册备案系统',									
				btn:['确定','取消'],
				skin : "layui-layer-lan-ict"				
			},
			function(index){
				if(success_message!=null&&''!=success_message){
					ict_alert(success_message);
				}
				if(success_function!=null&&''!=success_function){
					success_function();
				}
				layer.close(index);//点击确认按钮关闭窗口
			},
			function(){
				if(failure_message!=null&&''!=failure_message){
					ict_alert(failure_message);
				}
				if(failure_function!=null&&''!=failure_function){
					failure_function();
				}
			}
	)
}
