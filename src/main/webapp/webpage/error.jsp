<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
  <head>
  <meta charset="utf-8" />
  <base href="<%=basePath%>">
<title>应用程序异常 (500)</title> 

   <style type="text/css"> 
        body { background-color: #fff; color: #666; text-align: center; font-family: arial, sans-serif; }
        div.dialog {
            width: 80%;
            padding: 1em 4em;
            margin: 4em auto 0 auto;
            border: 1px solid #ccc;
            border-right-color: #999;
            border-bottom-color: #999;
        }
        h1 { font-size: 100%; color: #f00; line-height: 1.5em; }
    </style> 
</head> 
 
<body> 
  <div class="dialog"> 
    <h1>系统处理出错, 错误信息：${exceptionMessage}</h1> 
<%--    <p>抱歉！您访问的页面出现异常，请稍后重试或联系管理员。</p> --%>
    <p><a href="javascript:showErr();">详 情</a> 
		<a href="javascript:void(0)" onclick="history.go(-1)">返 回</a> 
    </p> 
    <div id="zhongxin" style="display:none;text-align: left;">${exceptionStackTrace}</div>
  </div>
  <script src="static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
  <script type="text/javascript">
  $(top.hangge());
  function showErr(){
 	var attr= document.getElementById("zhongxin").style.display;
 	if(attr == "none"){
 		document.getElementById("zhongxin").style.display="block";
 	}else{
 		document.getElementById("zhongxin").style.display="none";
 	}
  }
  
  function goBack(){
	  /*history.go(-1);
	 var href=$("#jerichotabiframe_1",parent.document).attr("src");
	 var tar=$("#jerichotabiframe_1",parent.document).attr("name");
	 if($("#jerichotabiframe_1",parent.document).length>0){
	 	$("#back").attr("href",href);
	 	$("#back").attr("target",tar);
	 }
	 //alert($("#back").attr("target"));
	  $.ajax({
	 	type:"post",
	 	url:href,
	 	success:function(){}
	 }) */
	  top.Dialog.close();
  }
  </script>
</body> 
</html>
