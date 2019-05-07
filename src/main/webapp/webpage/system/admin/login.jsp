<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../admin/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en" style="width:100%;height:100%;overflow:hidden;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>商业汇票综合处理平台</title>
	<link rel="stylesheet" href="../../../weblib/biz/login/css/bootstrap.min.css" />
	<link rel="stylesheet" href="../../../weblib/biz/login/css/camera.css" />
	<link rel="stylesheet" href="../../../weblib/biz/login/css/bootstrap-responsive.min.css" />
	<link rel="stylesheet" href="../../../weblib/biz/login/css/matrix-login.css" />
	<link rel="stylesheet" href="../../../weblib/biz/login/css/font-awesome.css"/>
	<link rel="stylesheet" href="../../../weblib/biz/login/css/login.css"/>
	<%-- jsp文件头和头部 --%>
	<%@ include file="../admin/top.jsp"%>
</head>
<body style="font-family:'微软雅黑';width:100%;padding:0px;">
	<div id="parent-login" >
		<div id="loginbox">
			<div class="control-group normal_text">
				<h2>
					商业汇票综合处理平台
				</h2>
			</div>
			<form action="" method="post" name="loginForm" id="loginForm">
				<div class="control-group" style="margin-top:40px;">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
								<i><img height="37" src="../../../weblib/biz/login/images/user.png" /></i>
							</span>
							<input type="text" name="userId" TabIndex="1" id="userId" value="" placeholder="请输入用户名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
								<i><img height="37" src="../../../weblib/biz/login/images/suo.png" /></i>
							</span><input type="password" TabIndex="2" name="userPwd" id="userPwd" placeholder="请输入密码" value=""/>
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
						<span id="nameerr">Copyright © herongtech 2016-2017</span>
					</font>
				</div>
			</div>
		</div>
	</div>
	<div id="templatemo">
		<img src="../../../weblib/assets/images/gallery/login_bg2.jpg"/>
	</div> 
<script type="text/javascript" src="../../../weblib/assets/js/jquery-1.7.2.js"></script>
<script src="../../../weblib/assets/js/bootstrap.min.js"></script>
<%--[if lte IE 9]>
	<script type="text/javascript" src="weblib/assets/js/jquery.placeholder.js"></script>
	<script type="text/javascript" src="weblib/bizjs/ie8.js"></script>
<![endif]--%>
<script src="../../../weblib/biz/login/js/jquery.easing.1.3.js"></script>
<script src="../../../weblib/biz/login/js/jquery.mobile.customized.min.js"></script>
<script src="../../../weblib/biz/login/js/camera.min.js"></script>
<script type="text/javascript" src="../../../weblib/assets/js/jquery.tips.js"></script>
<script type="text/javascript" src="../../../weblib/assets/js/jquery.cookie.js"></script>
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
			var userId = $("#userId").val();
			var userPwd = $("#userPwd").val();
			var formData = new Object();
			formData['userId'] = userId;
   			formData['userPwd'] = userPwd;
			$.ajax({
				type: "POST",
				url: 'loginController.do?method=checkuser',
		    	data: formData,
				dataType:'json',
				async : false,
     			cache : false,
				success: function(data){				
					if(data.success){
						$("#loginForm").tips({
							side : 2,
							msg : '正在登录 , 请稍后 ...',
							bg : '#68B500',
							time : 3
						});
						saveCookie();
						window.location.href = "loginController.do?method=login";
						/*if(data.obj==1){
							modifyUserPsw(userId);
						}else if(data.obj==2){
							$("#userId").tips({
							side : 1,
							msg : data.msg, 
							bg : '#FF5080',
							time : 3
							});
							saveCookie();
							window.location.href = "loginController.do?method=login";
						}else{
							$("#loginForm").tips({
								side : 2,
								msg : '正在登录 , 请稍后 ...',
								bg : '#68B500',
								time : 3
							});
							saveCookie();
							window.location.href = "loginController.do?method=login";
						}*/
					}else{
						$("#userId").tips({
							side : 2,
							msg : data.msg, 
							bg : '#FF5080',
							time : 3 
						});
						$("#userId").focus();
						$("#userId").css({"border":"1px solid #66afe9","box-shadow":"1px 1px 8px 1px rgba(102, 175, 233, 0.6)"});
					}
				 }
			});
		}
	}
	function modifyUserPsw(userId){
		var diag = new top.Dialog();
		diag.Drag = true;
		diag.Title ="编辑";
		diag.URL = "loginController.do?method=tomodifyUserPsw&user_id="+userId;
		diag.Width = 500;
		diag.Height = 340;
		diag.CancelEvent = function(){ //关闭事件
		diag.close();
		};
		diag.show();
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
	$("#userId").focus(function(){
		$("#userId").css({"border":"1px solid #66afe9","box-shadow":"1px 1px 8px 1px rgba(102, 175, 233, 0.6)"});
		$("#userPwd").css({"border":"1px solid #66afe9","box-shadow":"none"});
	})
	$("#userPwd").focus(function(){
		$("#userPwd").css({"border":"1px solid #66afe9","box-shadow":"1px 1px 8px 1px rgba(102, 175, 233, 0.6)"});
		$("#userId").css({"border":"1px solid #66afe9","box-shadow":"none"});
	})
	//客户端校验
	function check() {
		if ($("#userId").val() == "") {
			$("#userId").tips({
				side : 2,
				msg : '请输入用户名',
				bg : '#AE81FF',
				time : 1
			});
			$("#userId").focus();
			$("#userId").css({"border":"1px solid #66afe9","box-shadow":"1px 1px 8px 1px rgba(102, 175, 233, 0.6)"});
			return false;
		} else {
			$("#userId").val(jQuery.trim($('#userId').val()));
		}
		if ($("#userPwd").val() == "") {
			$("#userPwd").tips({
				side : 2,
				msg : '请输入密码',
				bg : '#AE81FF',
				time : 1
			});
			$("#userPwd").focus();
			$("#userPwd").css({"border":"1px solid #66afe9","box-shadow":"1px 1px 8px 1px rgba(102, 175, 233, 0.6)"});
			return false;
		}
		return true;
	}
	function savePaw() {
		if (!$("#saveid").attr("checked")) {
			$.cookie('loginname', '', {
				expires : -1
			});
			$.cookie('password', '', {
				expires : -1
			});
			$("#userId").val('');
			$("#userPwd").val('');
		}
	}
	function saveCookie() {
		if ($("#saveid").attr("checked")) {
			$.cookie('loginname', $("#userId").val(), {
				expires : 7
			});
			$.cookie('password', $("#userPwd").val(), {
				expires : 7
			});
		}
	}
	function quxiao() {
		$("#userId").val('');
		$("#userPwd").val('');
	}
	jQuery(function() {
		var loginname = $.cookie('loginname');
		var password = $.cookie('password');
		if (typeof(loginname) != "undefined"
				&& typeof(password) != "undefined") {
			$("#userId").val(loginname);
			$("#userPwd").val(password);
			$("#saveid").attr("checked", true);
		}
	});
	if (window != top) {
		top.location.href = location.href;
	}
</script>
</body>
</html>