<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>我的关注</title>
	<meta name="decorator" content="default"/>
	<link href="/AC_MAN_PORTAL/static/layui/css/layui.css" rel="stylesheet" type="text/css"/>
	<link href="/AC_MAN_PORTAL/static/css/tztg.css" type="text/css" rel="stylesheet"/>
	<link href="/AC_MAN_PORTAL/static/css/fontawesome/css/all.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="/AC_MAN_PORTAL/static/layui/layui.js"></script>
	<script type="text/javascript" src="/AC_MAN_PORTAL/static/vue.js"></script>
	<script type="text/javascript">
	$(function(){
		layui.use('element', function(){
		  var element = layui.element;
		});
		});
	$(function(){
		  $.ajax({
		    url: "/AC_MAN_PORTAL/a/sys/man/getuseraffairs",
		    type: "POST",
		    data: {},
		    dataType: "json",
		    success: function(data){
			//console.log(data);
		      vueData(data);
		    }, 
		    error: function(error) {
		    }
		  });
		});
		function vueData(data) {
			var example1 = new Vue({
			  el: '#mainContent',
			  data: {
			    items: data.affairsLists,
			    fullInfo: data.isNull
			  }
			});
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
<style type="text/css">
	[v-cloak]{
		display: none!important;
	}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a style="color:#2fa4e7;" href="${ctx}/sys/man/useraffairs">我的关注</a></li>
		<li><a style="color:#2fa4e7;" href="${ctx}/sys/man/affairslist">服务事项</a></li>	
	</ul>	
	<div class="wdgz">
		<ul id="mainContent" v-cloak>			
			 <li v-for="item in items">
	                    <div class="zxbl-sys-link-wrap">
	                      <i class="zxbl-item-icon" :style="{backgroundImage: 'url(/AC_MAN_PORTAL'+item.affairicon1+')'}"></i>
	                      <a :class="item.isgz == 0 ? 'zxbl-sys-button-attention zxbl-sys-button-addAttention zxbl-sys-button-show' : 'zxbl-sys-button-attention zxbl-sys-button-addAttention' " href="javascript:;" v-bind:onClick="'addAttention(\''+item.affairid+'\', this);'"> 未关注&nbsp;&nbsp;&nbsp; </a>
	                      <a :class="item.isgz == 1 ? 'zxbl-sys-button-attention zxbl-sys-button-cancelAttention zxbl-sys-button-show' : 'zxbl-sys-button-attention zxbl-sys-button-cancelAttention'" href="javascript:;" v-bind:onClick="'cancelAttention(\''+item.affairid+'\', this);'"> &nbsp;&nbsp;&nbsp;已关注 </a>
	                    </div>
	                    <div class="zxbl-sys-info">
	                      <div class="zxbl-sys-name">{{ item.affairname }}</div>
	                      <div class="zxbl-sys-org">主办单位：{{ item.affairbureau }}</div>
	                      <div class="zxbl-sys-org">事项编码：{{ item.implementCode }}</div>
	                    </div>
	                    <div class="zxbl-sys-actions">
	                    	<div class="white-button" v-if="item.extraUrls!=undefined">	                	                                   
			                   	<a v-if="item.clientlist.length<4" v-for="url in item.extraUrls.split('|')" class="zxbl-sys-button zxbl-sys-button-extra" :href="item.affairUrl!=undefined && item.affairUrl==1 && fullInfo == false ? 'javascript:;' : url.split(',')[0]" :onClick="item.affairUrl!=undefined && item.affairUrl==1 && fullInfo == false ? 'displayFullInfo();' : ''" :target="url.split(',')[1]" :title="url.split(',')[2]">
			                        <i class="far fa-arrow-alt-circle-right"></i>{{ url.split(',')[2] }}
			                    </a>
	                      	</div>
							<div class="white-button">
							<div :class="{'align-center' : item.clientlist.length>4}">
							<template v-for="client in item.clientlist">
							<a v-if="client.clientName != ''" 
							    class="zxbl-sys-button zxbl-sys-button-extra" 
							    :href="   client.incomeUri.split('^')[0] == 'javascript' ? client.systemState == '未集成' ? null                                                                                                             : fullInfo == false ? null                                                                                                                                                                                                                                                      : client.issq == '0' ? null                                                                                                                                                                     : null                                                                                                                                 : client.systemState == '未集成' ? client.incomeUri : fullInfo == false ? null                : client.issq == '0' ? null                                      : client.incomeUri" 
							    :onClick="client.incomeUri.split('^')[0] == 'javascript' ? client.systemState == '未集成' ? client.incomeUri.split('^')[1]+'(\''+client.incomeUri.split('^')[2]+'\',\''+client.incomeUri.split('^')[3]+'\')' : fullInfo == false ? client.incomeUri.split('^')[1]+'(\''+client.incomeUri.split('^')[2]+'\',\''+client.incomeUri.split('^')[3]+'\',function(){displayFullInfo();}' +')' :  client.issq == '0' ? client.incomeUri.split('^')[1]+'(\''+client.incomeUri.split('^')[2]+'\',\''+client.incomeUri.split('^')[3]+'\',function(){needAuthAlert(\''+item.affairname+'\');}' +')' : client.incomeUri.split('^')[1]+'(\''+client.incomeUri.split('^')[2]+'\',\''+client.incomeUri.split('^')[3]+'\')'                     : client.systemState == '未集成' ? null             : fullInfo == false ? 'displayFullInfo();'            : client.issq == '0' ? 'needAuthAlert(\''+item.affairname+'\');' : null" 
							    :target=" client.incomeUri.split('^')[0] == 'javascript' ? client.systemState == '未集成' ? null                                                                                                             : fullInfo == false ? null                                                                                                                                                                                                                                                            : client.issq == '0' ? null                                                                                                                                                                     : null                                                                                                                                 : client.systemState == '未集成' ? '_blank'         : fullInfo == false ? null                 : client.issq == '0' ? null                                      : '_blank'"
							    :title="client.clientName.split('|').length>1? client.clientName.split('|')[1] : client.clientName">
							    <i class="far fa-arrow-alt-circle-right"></i><template v-if="client.clientName.split('|').length>1">{{ client.clientName.split('|')[0] }}</template><template v-else>{{ client.clientName }}</template>
							</a>								                   
							</template>
							</div>
							</div>
							<div class="white-button">
			                      <!--额外链接按钮 开始--> 
			                      <template v-if="item.affaircondition != undefined && item.affaircondition !=''">
			                        <template v-for="entry in item.affaircondition.split('|')">
			                          <a :class="entry.split('^')[0] == '1' ? 'zxbl-sys-button' : 'zxbl-sys-button-disable'" :href="entry.split('^')[2].substr(0,4)=='http' ? entry.split('^')[2] : entry.split('^')[0] == '0' ? null : entry.split('^')[2].split('?')[0]+entry.split('^')[2].split('?')[1]" :target="entry.split('^')[2].substr(0,4)=='http' ? '_blank' : entry.split('^')[0] == '0' ? null : entry.split('^')[3]"><i :class="entry.split('^')[4]"></i>{{entry.split('^')[1]}}</a>
			                        </template>
			                      </template>
			                      <!--额外链接按钮 结束-->
							</div>
	                    </div>
	                  </li>
		</ul>
	</div>
	<sys:message content="${message}"/>
</body>
</html>