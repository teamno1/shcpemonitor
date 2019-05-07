<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- 分页 --%>
<div id="pageContent" class="pageFormContent" style="margin-right:30px;line-height:35px;">
	<a class="btn btn-minier btn-white" href="javascript:submitForm('first',pageForm)" id="first" style="width: 50px;line-height: 22px;margin-top:-4px;padding:0px;border-radius:3px;" type="button">首页</a> 
	<a class="btn btn-minier btn-white" href="javascript:submitForm('previous',pageForm)" id="previous" style="line-height: 22px; margin: 0px 5px;padding:0px; width: 60px;margin-top:-4px;border-radius:3px;" type="button">上一页</a> 
	<a class="btn btn-minier btn-white" href="javascript:submitForm('next',pageForm)" id="next" style="line-height: 22px; width: 60px;margin-top:-4px;padding:0px;border-radius:3px;" type="button">下一页</a>  
	<a class="btn btn-minier btn-white" href="javascript:submitForm('last',pageForm)" id="last" style="line-height: 22px;  margin: 0px 35px 0px 5px;padding:0px;width: 50px;margin-top:-4px;border-radius:3px;" type="button">末页</a>
	<span  class="bottomPage" style="position:absolute;top:3px;left:235px;margin:0px 15px 0 25px;line-height:25px;">页数</span> 
	 <select name="directPage" id="directPage" onchange="submitForm('directPage',pageForm)" style="width:70px;height:25px;color:#393939;padding:0px;margin:-6px 0 0 0;">
	     ${page.selectOptionHtml}
	 </select>
     <input type="hidden" name="totalResult" value="${page.totalResult}" id="totalResult"/>
     <input type="hidden" name="currentPage" value="${page.currentPage}" id="currentPage"/>
     <input type="hidden" name="totalPage" value="${page.totalPage}" id="totalPage"/>
     <input type="hidden" name="currentResult" value="${page.currentResult}" id="currentResult"/>
     <input type="hidden" name="pageCommand"/>
</div>
<script>
	$(document).ready(function(){
		var totalPage=$("#totalPage").val()
		if($("#totalResult").val()==0){
			$(".pageFormContent a").attr("disabled","true");
			$(".pageFormContent a").css("background","rgb(230, 224, 224)");
			document.getElementById("directPage").options.add(new Option('0/0','0'));
		}
		if($("#totalPage").val()==1){
			$(".pageFormContent a").attr("disabled","true");
			$(".pageFormContent a").css("background","rgb(230, 224, 224)");
		}
		if($("#currentResult").val()==1){
			$("#previous,#first").css("background","rgb(230, 224, 224)");
			$("#previous").attr("disabled","true");
			$("#first").attr("disabled","true");
		}else if($("#currentPage").val()==totalPage){
			$("#next,#last").css("background","rgb(230, 224, 224)");
			$("#next").attr("disabled","true");
			$("#last").attr("disabled","true");
		}
	})
</script>

