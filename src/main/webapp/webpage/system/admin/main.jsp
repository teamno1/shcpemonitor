<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<head>
<%@ include file="../admin/top.jsp"%> 
<link rel="stylesheet" href="/weblib/bizcss/main.css" />
</head>
<body  class="welcome_main" style="font-family:'微软雅黑">
  	<div id="mainDiv">
  	<%---------待处理事项开始----------%>
		 <div id="detailDiv1" class="welcom_dcl fl" >
			 <ul style="float:left;">
				 <h4 style="margin:0px 0px 5px;"><span></span>系统公共信息</h4>
				 <div id="NoticeDiv">
					<%-- <li><span class="green"> test  </span><a href="#" onclick="">公告</a></li>--%>
				 </div>
			 </ul>
		 </div>

	</div>
<%@ include file="../admin/modalDialog.jsp"%> 
<script type="text/javascript" src="weblib/commonjs/common.js"></script>
<script type="text/javascript">


	function f(e){
		if(e.preventDefault){
			e.preventDefault();
		}else{
			e.returnValue=false;
		}
	} 


</script>
</body>
</html> 