<%-- 
 * 文件名称: user_edit.jsp
 * 系统名称: 票据管理平台
 * 模块名称:
 * 软件版权: 北京合融科技有限公司
 * 功能说明: 
 * 系统版本: @version2.0.0.1
 * 开发人员: superCheng
 * 开发时间: 2016-7-12 上午08:28:22
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期    修改人员    修改说明
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../admin/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="../admin/top.jsp"%>
</head>
<body style="background-color:#f4f8fb;font-family:'微软雅黑;'">
	<div class="clearfix">
		<div class="page-content" id="jump-content">
			<form action=" " name="Form" id="Form" method="post" class="form-search">
				<div id="zhongxin">
					<div class="row-fluid">
						<label for="user_id" class="text-right">用户编号</label>
						<input type="text" class="input-medium" valid="required"
								name="userId" id="userId" readonly="readonly"
								value="${user.userId}" />
					</div>
					<div class="row-fluid">
						<label for="user_name" class="text-right">用户名称</label>
						<input type="text" class="input-medium" valid="required"
								readonly="readonly" name="userName" id="userName"
								value="${user.userName}" />
					</div>
					<div class="row-fluid">
						<label for="oldPsw" class="text-right"><span class="star">*</span>原密码</label>
						<input type="password" class="input-medium" valid="required"
								name="oldPsw" id="oldPsw" placeholder="请输入原密码" />
					</div>
					<div class="row-fluid">
						<label for="newPsw" class="text-right"><span class="star">*</span>新密码</label>
						<input type="password" class="input-medium" valid="required"
								name="newPsw" id="newPsw" placeholder="请输入新密码" />
					</div>
					<div class="row-fluid">
						<label for="confirmNewPsw" class="text-right"><span class="star">*</span>确认密码</label>
						<input type="password" class="input-medium" valid="required"
								 name="confirmNewPsw"
								id="confirmNewPsw" placeholder="请再次输入新密码" />
					</div>
					<div class="row-fluid">
						<div class="center save">
							<a class="btn-mini" onclick="save();">保存</a> <a
								class="btn-mini" onclick="top.Dialog.close();">取消</a>
						</div>
					</div>
			</form>
		</div>
	</div>
</body>
<%@ include file="../admin/footer.jsp"%>
<script type="text/javascript">
	//保存
	function save() {
		$( "#Form" ).validate({
			  rules: {
				  newPsw: "required",
				  confirmNewPsw: {
			      equalTo: "#newPsw"
			    }
			  }
		})
		if ($("#Form").valid()) {

			checkExist();
		}
	}
	//服务器校验
	function severCheck() {
	var oldPsw = $("#oldPsw").val();
	var userPwd = $("#confirmNewPsw").val();
	var userId = $("#userId").val();
	var formData = new Object();
	formData['userId'] = userId;
	formData['userPwd'] = userPwd;
		
			$.ajax({
				type : "POST",
				url : 'loginController.do?method=checkuser',
				data : formData,
				dataType : 'json',
				async : false,
				cache : false,
				success : function(data) {
					if (data.success) {
						top.window.location.href = "loginController.do?method=login";
					} }
			});
		}
	//判断密码是否输入错误
	function checkExist() {
	var oldPsw = $("#oldPsw").val();
	var userPwd = $("#confirmNewPsw").val();
	var userId = $("#userId").val();
		var url = "loginController.do?method=checkUserPsw&user_password=" + oldPsw
				+ "&userId=" + userId + "&confirmNewPsw=" + userPwd;
		$.ajax({
			url : url,
			type : "GET",
			dataType : "JSON",
			success : function(data) {
				if (!data.success) {
					showTips("oldPsw", "密码输入错误");
					$("#oldPsw").focus();

				} else {
					severCheck();
				}
			}
		});
	}
</script>
</html>