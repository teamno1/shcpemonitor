<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../admin/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en" style="width:100%;height:100%;overflow:hidden;">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>上海票交所模拟器</title>
	<link rel="stylesheet" href="weblib/biz/login/css/bootstrap.min.css" />
	<link rel="stylesheet" href="weblib/biz/login/css/camera.css" />
	<link rel="stylesheet" href="weblib/biz/login/css/bootstrap-responsive.min.css" />
	<link rel="stylesheet" href="weblib/biz/login/css/matrix-login.css" />
	<link rel="stylesheet" href="weblib/biz/login/css/font-awesome.css"/>
	<link rel="stylesheet" href="weblib/biz/login/css/login.css"/>
	<%-- jsp文件头和头部 --%>
	<%@ include file="../admin/top.jsp"%>
</head>
<body style="font-family:'微软雅黑';width:100%;padding:0px;">
	<div id="parent-login" >
		<div id="loginbox">
			<div class="control-group normal_text">
				<h2>
						上海票交所模拟器
				</h2>
			</div>
			<form action="loginController/login" method="post" name="loginForm" id="loginForm">
				<div class="control-group" style="margin-top:40px;">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
								<i><img height="37" src="weblib/biz/login/images/user.png" /></i>
							</span>
							<input type="text" name="brchNo" TabIndex="1" id="brchNo" value="" placeholder="请输入用户名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
								<i><img height="37" src="weblib/biz/login/images/suo.png" /></i>
							</span><input type="password" TabIndex="2" name="tradeUserNo" id="tradeUserNo" placeholder="请输入密码" value=""/>
						</div>
					</div>
				</div>
				<div class="form-actions" id="form-actions">
					<div style="width:100%;">
						<span class="center">
							<a onclick="severCheck();" TabIndex="3" class="btn-mini" id="to-recover">登录</a>
						</span> 
						<span class="center">
							<a href="javascript:quxiao();" TabIndex="4" class="flip-link btn-mini">重置</a>
						</span>
					</div>
				</div>
			</form>
			<div class="controls">
				<div class="main_input_box">
					<font color="white">
						<span id="nameerr"></span>
					</font>
				</div>
			</div>
		</div>
	</div>
	<div id="templatemo">
		<img src="weblib/assets/images/gallery/login_bg2.jpg"/>
	</div> 
<script src="weblib/assets/js/bootstrap.min.js"></script>
<script src="weblib/biz/login/js/jquery.easing.1.3.js"></script>
<script src="weblib/biz/login/js/jquery.mobile.customized.min.js"></script>
<script src="weblib/biz/login/js/camera.min.js"></script>
<script type="text/javascript" src="weblib/assets/js/jquery.tips.js"></script>
<script type="text/javascript" src="weblib/assets/js/jquery.cookie.js"></script>
<script type="text/javascript">
	//服务器校验
	/* 窗口事件，背景图的大小变化 */
	$(window).resize(function(){
		//$("#templatemo img").css("height","100%");
		if($("body").width()<1360){
			$("html").css("overflow","auto");
			$("#templatemo").css("height","585px");
		}else{
			$("html").css("overflow","hiiden");
			$("#templatemo").css("height","100%");
		}
		//$("#templatemo").height('0px');
		//$("#templatemo").height($(document).height()+$(document).scrollTop());
	})
	function severCheck(){
		if(check()){

			$("#loginForm").submit();

		}
	}

	$(document).ready(function() {
		changeCode();
		$("#codeImg").bind("click", changeCode);
	});
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#to-recover").trigger("click");
		}
	});
	function genTimestamp() {
		var time = new Date();
		return time.getTime();
	}
	function changeCode() {
		$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
	}
	$("#brchNo").focus(function(){
		$("#brchNo").css({"border":"1px solid #66afe9","box-shadow":"1px 1px 8px 1px rgba(102, 175, 233, 0.6)"});
		$("#tradeUserNo").css({"border":"1px solid #66afe9","box-shadow":"none"});
	})
	$("#tradeUserNo").focus(function(){
		$("#tradeUserNo").css({"border":"1px solid #66afe9","box-shadow":"1px 1px 8px 1px rgba(102, 175, 233, 0.6)"});
		$("#brchNo").css({"border":"1px solid #66afe9","box-shadow":"none"});
	})
	//客户端校验
	function check() {
		if ($("#brchNo").val() == "") {
			$("#brchNo").tips({
				side : 2,
				msg : '请输入用户名',
				bg : '#AE81FF',
				time : 1
			});
			$("#brchNo").focus();
			$("#brchNo").css({"border":"1px solid #66afe9","box-shadow":"1px 1px 8px 1px rgba(102, 175, 233, 0.6)"});
			return false;
		} else {
			$("#brchNo").val(jQuery.trim($('#brchNo').val()));
		}
		if ($("#tradeUserNo").val() == "") {
			$("#tradeUserNo").tips({
				side : 2,
				msg : '请输入密码',
				bg : '#AE81FF',
				time : 1
			});
			$("#tradeUserNo").focus();
			$("#tradeUserNo").css({"border":"1px solid #66afe9","box-shadow":"1px 1px 8px 1px rgba(102, 175, 233, 0.6)"});
			return false;
		}
		return true;
	}

	function saveCookie() {
		if ($("#saveid").attr("checked")) {
			$.cookie('loginname', $("#brchNo").val(), {
				expires : 7
			});
			$.cookie('password', $("#tradeUserNo").val(), {
				expires : 7
			});
		}
	}
	function quxiao() {
		$("#brchNo").val('');
		$("#tradeUserNo").val('');
	}
	jQuery(function() {
		var loginname = $.cookie('loginname');
		var password = $.cookie('password');
		if (typeof(loginname) != "undefined"
				&& typeof(password) != "undefined") {
			$("#brchNo").val(loginname);
			$("#tradeUserNo").val(password);
			$("#saveid").attr("checked", true);
		}
	});
	if (window != top) {
		top.location.href = location.href;
	}
</script>
</body>
</html>