<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- 模态窗口 --%>
<div style="position: absolute;left:0;top:0;width: 100%; height: 100%;display: none; z-index: 20000;background:#000;opacity:.5;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50);"
	class="loading" id="layer_loading">
</div>
<div id="image" style="display:none;position:absolute;left:50%;top:50%;width:30px;height:30px;z-index: 20001;">
	<img src="plugins/zTree/2.6/img/loading.gif" style="width:30px;height:30px;"/>
</div>
<script>
	var wid = $("#jump-content").innerWidth();
	var hei = $("#jump-content").innerHeight();
	var bodyHei = $("#jump-content").parent().parent().height();
	if(hei > bodyHei){
		$("#layer_loading").css({"width":wid+6,"height":hei+16});
	}else{
		$("#layer_loading").css({"width":"100%","height":"100%"});
	}
</script>