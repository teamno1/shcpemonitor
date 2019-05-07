<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<head>
<%@ include file="../admin/top.jsp"%> 
<link rel="stylesheet" href="weblib/bizcss/main.css" />
</head>
<body onload="loadWelcomeFun()" class="welcome_main" style="font-family:'微软雅黑">
	<div id="commanMenu">
		
	</div>
	<div id="delate">
		<ul>
			<li>删除菜单</li>
		</ul>
	</div>
  	<div id="mainDiv"> 
  	<%---------待处理事项开始----------%>
		 <div id="detailDiv1" class="welcom_dcl fl" style="padding:0px;margin:0px;">
			 <ul style="float:left;">
				 <h4 style="margin:0px 0px 5px;"><span></span>系统公共信息</h4>
				 <div id="NoticeDiv"></div>
			 </ul>
			 <ul id="auditList" style="float:right;">
				 <h4 style="margin:0px 0px 5px;"><span></span>待处理事项</h4>
				 <div id="AuditDiv">
				 </div>
			 </ul>
		 </div>
  <%---------待处理事项结束----------%> 
		  <div id="detailDiv2" class="welcom_dcl fl" >
			  <ul style="float:left;">
				  <h4 style="margin:0px 0px 5px;"><span></span>即将到期及逾期票据</h4>
				  <div id="DeductDraweeDiv">
				  </div>
			  </ul>
<%--			  <ul style="float:right;">--%>
<%--				  <h4 style="margin:0px 0px 5px;"><span></span>转贴现到期批次</h4>--%>
<%--				  <div id="RdscntDiv">--%>
<%--				  </div>--%>
<%--			  </ul>--%>
		  </div>
		   <%---------待处理事项结束----------%> 
	</div>
<%@ include file="../admin/modalDialog.jsp"%> 
<script type="text/javascript" src="weblib/commonjs/common.js"></script>
<script type="text/javascript">
/* $(document).ready(function(){
	var e = e || window.event;
	 if(e.preventDefault()){
         e.preventDefault();
     }else {
         e.returnValue=false;
     } 
	//getContextMenu();
}) */
	/*调用后台方法查询登录用户的快捷菜单*/
	function getContextMenu(){
		$.ajax({
			type: "POST",
			url:'UserContextMenuController.do?method=list',
			dataType:'json',
			cache: false,
			success: function(data){	
				if (data.obj){  //处理成功
					var objLen=data.obj.length;
					if(objLen){
						var strDl='';
						for(var i=0;i<objLen;i++){
							strDl +='<dl class="dlMenu" id="z'+ data.obj[i].menuCode +'">'+
									'<dt><img src="'+ data.obj[i].contextMenuSrc +'"/></dt>'+
									'<dd datalink="'+ data.obj[i].menuUrl +'">'+ data.obj[i].menuName +'</dd>'+
					  			'</dl>'
						}
						$('#commanMenu').css('display','block');
						$('#mainDiv').css('border','1px solid rgb(194, 226, 251)');
					  	$('#commanMenu').append(strDl);
					  	delate();
					}else{
					}
					//alert(strDl);
				} else {
					top.hangge();
					//bootbox.alert('无记录');
				}
			}
		});
	}
	
 //右键出现删除菜单事件
 function delate(){
	var commanMenu=document.getElementById("commanMenu");
	var dlMenu=$(".dlMenu");
	var div=document.getElementById("delate");
	var menuIndex;
	var menuCode;
	menuCode='-1';
	for(var i=0;i<dlMenu.length;i++){
		dlMenu[i].index=i;
		dlMenu[i].oncontextmenu=function(evt){//右键出现删除菜单事件
			//menuIndex =0;
			var e=evt||event
			f(e)
			div.style.display='block';
			var disx=e.clientX;
			var disy=e.clientY;
			div.style.left=disx+'px';
			div.style.top=disy+'px';
			//menuIndex = this.index;
			menuCode = this.getAttribute('id');
		}
		dlMenu[i].onclick=function(){  //左键跳转tab页，若是已存在则高亮，不存在新打开tab页
			var dlTabText=$(".tabs .jericho_tabs",parent.document);
			var dlTabFlag=false;
			var dlCodeId=$(this).attr("id");
			var dlCode='lm'+dlCodeId.substring(1,dlCodeId.length);
			var dlInner=$(this).children('dd').html();
			var dlDataLink=$(this).children('dd').attr('datalink');
			for(var i=0;i<dlTabText.length;i++){
				var tabName=dlTabText.eq(i).attr("datalinnk");
				if(dlDataLink == tabName){
					dlTabFlag=true;
					break;
				}else{
					dlTabFlag=false;
				}
			}
			if(dlTabFlag==true){
				$("#jerichotabiframe_",parent.document).find(".tabs li").removeClass('tab_selected').addClass('tab_unselect').css('color','rgb(88, 88, 88)');
				$("#jerichotabiframe_",parent.document).find(".tabs li").eq(i).removeClass('tab_unselect').addClass('tab_selected').css('color','#fff');
				$("#jerichotabiframe_",parent.document).find("#jerichotab_contentholder div").removeClass('curholder').addClass('holder');
				$("#jerichotabiframe_",parent.document).find("#jerichotab_contentholder div").css('height','0px');
				$("#jerichotabiframe_",parent.document).find("#jerichotab_contentholder div").eq(i).next().removeClass('holder').addClass('curholder');
				$("#jerichotabiframe_",parent.document).find("#jerichotab_contentholder div").eq(i).next().css('height','inherit');
			}else{
				var taburl= dlDataLink;
				//modal("#layer_loading,#image");
				top.mainFrame.jericho.buildTree(dlCodeId,dlCode,dlInner,taburl);
			}
		};
	}
	//点击删除菜单，菜单隐藏事件
	$("#delate li").click(function(){
		if(menuCode=='-1'){
			return;
		}else{
			delate(menuCode);
			deleteContextMenu(menuCode);
			//event.stopPropagation();
		}
	});
	function delate(menuCode){
		//$("#commanMenu dl").eq(index).remove();
		$("#commanMenu").find("dl[id='"+menuCode+"']").remove();
		if($("#commanMenu dl").length == 0){
			$('#commanMenu').css('display','none');
			$('#mainDiv').css('border','none');
		}
	}
	document.onclick=function(){
		div.style.display='none';
	};
	$("body",parent.document).click(function(){
		$("#delate").css("display","none");
	});
	$("body",top.parent.document,parent.document).click(function(){
		$("#delate").css("display","none");
	});
 }
 /*调用后台方法删除快捷菜单*/
	function deleteContextMenu(menuCode){
		$.ajax({
			type: "POST",
			url:'UserContextMenuController.do?method=del',
		   	data: {'menuCode': menuCode},
			dataType:'json',
			cache: false,
			success: function(data){	
				if (data.success){  //处理成功
				
				} else {
					top.hangge();
					bootbox.alert('删除失败!');
				}
			}
		});
	}
	function f(e){
		if(e.preventDefault){
			e.preventDefault();
		}else{
			e.returnValue=false;
		}
	} 
	function gotoPDeductDrawee(acptId){
		 var url="<%=basePath%>acptAccountController.do?method=dueChargeBill&pkAcptApplyId="+acptId;
		 top.mainFrame.jericho.buildTree("gotoPDeductDrawee","lm010104","纸票到期扣款提醒",url);
	}
	function gotoEDeductDrawee(acptId){
		 var url="<%=basePath%>acptAccountController.do?method=elecDueChargeBill&pkAcptApplyId="+acptId;
		 top.mainFrame.jericho.buildTree("gotoEDeductDrawee","lm020104","电票到期扣款提醒",url);
	}
	function gotoNotice(id){
		 var url="<%=basePath%>noticeController.do?method=toReadNotice&noticeNo="+id;
		 var iframeUrl = $("#jerichotabiframe_notice",parent.document).attr("src");
	   	 if(iframeUrl){
		   	 var iframe = iframeUrl.split("&");
		     iframeUrl = iframe[0]+"&"+iframe[1];
		     $(".jericho_tabs",parent.document).each(function(){
		   		 if($(this).attr("datalink") == iframeUrl){
		   			 $(this).addClass("datalink");
		   			 $(this).attr("datalink",url);
		   		 }
		   	 });
	   	 }
	   	 if(iframeUrl){
	   		$("#jerichotabiframe_notice",parent.document).attr("src",url);
	   		$("#jerichotabiframe_notice",parent.document).parent().css("display","block");
	   		$("#jerichotabiframe_notice",parent.document).parent().siblings().css("display","none");
	   		$(".datalink",parent.document).addClass("tab_selected").removeClass("tab_unselect").css("color","#fff");
	   		$(".datalink",parent.document).siblings().addClass("tab_unselect").removeClass("tab_selected").css("color","rgb(88,88,88)");
	   	 }else{
	     	top.mainFrame.jericho.buildTree("notice","notice","公告明细",url);
	   	 }
	}
	function gotoAuditTask(){
		var url="<%=basePath%>auditProcessController.do?method=searchAuditProcess";
		 top.mainFrame.jericho.buildTree("gotoAuditTask","lm030403","产品审批管理",url);
	}
	function gotoPSubColl(billNo,billClass){
		var url="<%=basePath%>subcollApplyController.do?method=advancedQuery&&billNo="+billNo+"&&billClass="+billClass;
		 top.mainFrame.jericho.buildTree("gotoPSubColl","lm010908","托收新增申请",url);
	}
	function gotoESubColl(){
		var url="<%=basePath%>discApplyController.do?method=billManage&pkDiscApplyId=2";
		 top.mainFrame.jericho.buildTree("gotoESubColl","lm020201","提示付款",url);
	}
	function gotoESaleBack(){
		var url="<%=basePath%>discApplyController.do?method=billManage&pkDiscApplyId=2";
		 top.mainFrame.jericho.buildTree("gotoESaleBack","lm020501","返售申请",url);
	}
	function gotoEBuyBack(){
		var url="<%=basePath%>discApplyController.do?method=billManage&pkDiscApplyId=2";
		 top.mainFrame.jericho.buildTree("gotoEBuyBack","lm0204601","回购申请",url);
	}
	function gotoPSaleBack(){
		var url="<%=basePath%>discApplyController.do?method=billManage&pkDiscApplyId=2";
		 top.mainFrame.jericho.buildTree("gotoPSaleBack","lm010501","返售申请",url);
	}
	function gotoPBuyBack(){
		var url="<%=basePath%>discApplyController.do?method=billManage&pkDiscApplyId=2";
		 top.mainFrame.jericho.buildTree("gotoPBuyBack","lm010601","回购申请",url);
	}
	//到期票据自动加载
	function loadDeductDrawee(){
		$.ajax({
			type: "POST",
			url:"remindController.do?method=loadDeductDrawee",
			data:"",
			dataType:'json',
			success: function(data){
					$("#DeductDraweeDiv").html(data);
			}
		});
	}
	//审核自动加载
	function loadAudit(){
		$.ajax({
			type: "POST",
			url:"remindController.do?method=loadAudit",
			data:"",
			dataType:'json',
			success: function(data){	
					$("#AuditDiv").html(data);
			}
		});
	}
	//公告自动加载
	function loadNotice(){
		$.ajax({
			type: "POST",
			url:"remindController.do?method=loadNotice",
			data:"",
			dataType:'json',
			success: function(data){	
					$("#NoticeDiv").html(data);
			}
		});
	}
	//转贴现
	function loadRdscnt(){
		$.ajax({
			type: "POST",
			url:"remindController.do?method=loadDeductRdscnt",
			data:"",
			dataType:'json',
			success: function(data){	
					$("#RdscntDiv").html(data);
			}
		});
	}
	//页面启动自动加载
	function loadWelcomeFun (){
			setTimeout(function(){
			loadNotice();
			loadDeductDrawee();
			loadAudit();
			getContextMenu();
	<%--		loadRdscnt();--%>
		},1000);
	}
</script>
</body>
</html> 