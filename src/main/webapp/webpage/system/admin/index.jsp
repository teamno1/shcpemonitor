﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ include file="/webpage/system/admin/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<title>上海票据交易所仿真系统</title>
	<%@ include file="../admin/top.jsp"%>
	<link rel="stylesheet" href="weblib/bizcss/index.css">
</head>
<body style="font-family:'微软雅黑';min-width:1250px;" id="indexBody">
	<div id="home">
		<div class="navbar navbar-default" id="navbar">
			<div id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<i class="icon-leaf"></i>
						上海票据交易所仿真系统
					</a>
				</div>
				<div class="navbar-header pull-right" role="navigation" id="navbarHeader">
					<ul class="nav ace-nav">
						<li class="white">
							<span class="pull-left" id="note">
								欢迎您,${user.brchNo} (${user.brchName})
							<i class="icon-time"></i>
								${currentDate}
							</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
	    <%--页面顶部--%>        
	    <%--主内容区--%>
	    <div class="main-container ace-save-state" id="main-container">
			<%--左侧菜单--%>
			<div class="sidebar responsive ace-save-state" id="sidebar">
				<ul class="nav nav-list" id="nav-menu" style="height: 222px;">
					<input type="hidden" id="firstMenuId" value="02">
					<li class="actived gray" onclick="changeShow(this.id)" id="menuid_02" title="模拟器" style="display: block;">
						<a class="dropdown-toggle first_child firstLevelNav" >
							<b class="arrow icon-angle-down"></b>
							<i class="icon-laptop"></i> 模拟器</a>

						<ul class="submenu" id="c02" style="display: block;">
							<li class="second_nav open" id="lm0201">
								<a style="cursor:pointer;padding:7px 0 9px 27px;" class="dropdown-toggle second_child">
									<span style="background: rgba(0, 0, 0, 0) url(weblib/assets/images/gallery/arrow_1.png) no-repeat scroll 0% 0%; display: inline-block; width: 5px; height: 9px; margin-right: 3px;"></span>
									<span title="系统管理"  class="menu-text">全功列表</span>
								</a>

								<ul class="submenu" style="background: rgb(210, 237, 255) none repeat scroll 0% 0%;">
									<li id="z020101" style="border:1px dashed #ddd;line-height:30px;" class="secondActived">
										<a class="func" title="系统状态"  name="系统状态" style="cursor: pointer; padding: 8px 0px 8px 35px; font-size: 12px; margin-left: 0px; color: rgb(88, 88, 88); font-weight: bold;" onclick="siMenu(this,'z020101','lm020101','系统状态','sys/getSysStatus')" id="jerichotab_3" href="sys/getSysStatus" target="jerichotabiframe_lm020101">系统状态
										</a>
									</li>

									<li id="z020102" style="border:1px dashed #ddd;line-height:30px;" class="">
										<a class="func" title="节假日维护" name="节假日维护" style="cursor: pointer; padding: 8px 0px 8px 35px; font-size: 12px; margin-left: 0px; color: rgb(88, 88, 88); font-weight: normal;" onclick="siMenu(this,'z020102','lm020102','节假日维护','acptApplyController.do?method=exportBill')">节假日维护</a>
									</li>
								</ul>
							</li>
							<%--<li class="second_nav" id="lm0202">
								<a style="cursor: pointer; padding: 7px 0px 9px 27px; font-weight: normal;" class="dropdown-toggle second_child">
								<span style="background: rgba(0, 0, 0, 0) url(weblib/assets/images/gallery/arrow_2.png) no-repeat scroll 0% 0%; display: inline-block; width: 5px; height: 9px; margin-right: 3px;"></span>
								<span title="核心交易" style="" class="menu-text">核心交易</span>
								</a>

								<ul class="submenu" style="background:#d2edff;display:none;"><li id="z020201" style="border:1px dashed #ddd;line-height:30px;"><a class="func" title="收到申请" datelink="discApplyController.do?method=elecBillManage" name="[电.贴现]收到申请" style="cursor:pointer;padding:8px 0 8px 35px;font-size:12px;margin-left:0px;color:rgb(88,88,88)" onclick="siMenu(this,'z020201','lm020201','[电.贴现]收到申请','discApplyController.do?method=elecBillManage')">收到申请</a>
								</li>
									<li id="z020203" style="border:1px dashed #ddd;line-height:30px;"><a class="func" title="撤销申请" datelink="discApplyController.do?method=cancelElecBatch" name="[电.贴现]撤销申请" style="cursor:pointer;padding:8px 0 8px 35px;font-size:12px;margin-left:0px;color:rgb(88,88,88)" onclick="siMenu(this,'z020203','lm020203','[电.贴现]撤销申请','discApplyController.do?method=cancelElecBatch')">撤销申请</a>
									</li>
									<li id="z020202" style="border:1px dashed #ddd;line-height:30px;"><a class="func" title="修改申请" datelink="discApplyController.do?method=elecBatchList" name="[电.贴现]修改申请" style="cursor:pointer;padding:8px 0 8px 35px;font-size:12px;margin-left:0px;color:rgb(88,88,88)" onclick="siMenu(this,'z020202','lm020202','[电.贴现]修改申请','discApplyController.do?method=elecBatchList')">修改申请</a>
									</li>
									<li id="z020206" style="border:1px dashed #ddd;line-height:30px;"><a class="func" title="贴现签收" datelink="discAccountController.do?method=elecAccount" name="[电]贴现签收" style="cursor:pointer;padding:8px 0 8px 35px;font-size:12px;margin-left:0px;color:rgb(88,88,88)" onclick="siMenu(this,'z020206','lm020206','[电]贴现签收','discAccountController.do?method=elecAccount')">贴现签收</a>
									</li>
									<li id="z020207" style="border:1px dashed #ddd;line-height:30px;"><a class="func" title="贴现记账" datelink="discAccountController.do?method=finalAccount" name="[电]贴现记账" style="cursor:pointer;padding:8px 0 8px 35px;font-size:12px;margin-left:0px;color:rgb(88,88,88)" onclick="siMenu(this,'z020207','lm020207','[电]贴现记账','discAccountController.do?method=finalAccount')">贴现记账</a>
									</li>
									<li id="z020208" style="border:1px dashed #ddd;line-height:30px;"><a class="func" title="赎回申请" datelink="rediscAccountController.do?method=rediscApply" name="[电.贴现]赎回申请" style="cursor:pointer;padding:8px 0 8px 35px;font-size:12px;margin-left:0px;color:rgb(88,88,88)" onclick="siMenu(this,'z020208','lm020208','[电.贴现]赎回申请','rediscAccountController.do?method=rediscApply')">赎回申请</a>
									</li>
								</ul>
							</li>--%>
						</ul>
					</li>
				</ul>

			</div>
			<%--/左侧菜单--%>
			<div id="sidebar-collapsed">
				<i class="icon-backward" id="imgs"></i>
			</div>
			<%--中间内容区域--%>
			<div class="main-content" id="mainContent">
				<%--头部菜单导航--%>
				<div id="parentFrame" style="background: url(weblib/assets/images/gallery/index2.jpg) no-repeat;
		background-size:cover;">
					<iframe name="mainFrame" id="mainFrame" frameborder="0" src="loginController/tab"></iframe>
				</div>
			</div>
		</div>
	</div>
<%@ include file="../admin/modalDialog.jsp"%> 
<%--引入属于此页面的js --%>
<script type="text/javascript" src="weblib/assets/js/jquery.cookie.js"></script>
<script type="text/javascript" src="weblib/bizjs/head.js"></script>
<script type="text/javascript" src="weblib/bizjs/index.js"></script>
<!--[if lte IE 9]><script type="text/javascript" src="weblib/assets/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="weblib/bizjs/ie8.js"></script><![endif]-->
<script>
	$(document).ready(function(){
		/* wSizeWidth();
		function wSizeWidth(){
			var leftWidth = $("#sidebar").width();
			$("#mainContent").width($("#main-container").width()- leftWidth - $("#sidebar-collapsed").width() -11);
		} */
		$("html").css("overflow","hidden");
	});
	$(window).resize(function(){
		if($(window).width()<1250){
			$("#home").parent().parent().css("overflow-x","auto");
			//$("#home").css("width","1360px");
		}else{
			$("#home").parent().parent().css("overflow-x","hidden");
			$("#home").width("100%");
		}
	})
	//左侧菜单快速打开
		//根据ID获取DOM
		function getId(id) {
			return document.getElementById(id);
		}
		var
			oSearchContent = getId('search_content'),
			oSearchNotice  = getId('search_notice');
		oSearchNotice.style.display="none";
		var	iIndex = -1;
		var reg=/^[\u4e00-\u9fa5]+$/;
		oSearchContent.oninput = oSearchContent.onpropertychange = function () {
			var inputValue = oSearchContent.value;
			//alert(inputValue);
			var str="";
			if(navigator.appVersion.search(/MSIE 7/i) !=-1){

			}  
			if(reg.test(inputValue)){
				 $.ajax({
					url:"menuController.do?method=selectmenu",
					type:"POST",
					data:{"likestr":inputValue},
					dateType:"JSON",
					success:function(data){
						var data = eval('('+data+')');
						if(data.obj){
							var objLen=data.obj.length;
							for(var i=0;i<objLen;i++){
								var liHtml=data.obj[i].menuName;
								var menuUrl=data.obj[i].menuUrl;
								var menuCode =data.obj[i].menuCode;
								str+='<li onclick="'+'gotoMenuTab(\'z'+menuCode+'\',\'lm'+menuCode+'\',\''+liHtml+'\',\''+menuUrl+'\');">'+liHtml+'</li>';
							}
						}else{
							str='<li>没有相关菜单目录</li>';
						}
						oSearchNotice.style.display="block";
						oSearchNotice.innerHTML=str;
						if($("#search_notice li").length>6){
							oSearchNotice.style.height="158px";
							oSearchNotice.style.overflow='auto'
						}else{
							oSearchNotice.style.height='';
						}
					}
				}) 
			}else if(inputValue==""){
				oSearchNotice.style.display="none";
			}else{
				str='<li>没有相关菜单目录</li>';
				oSearchNotice.style.height='';
				oSearchNotice.style.display="block";
				oSearchNotice.innerHTML=str;
			}
			$('body').click(function(){
				$('#search_notice').css("display","none");
			})
			$("#mainFrame").contents().find('body').click(function(){
				$('#search_notice').css("display","none");
			})
			$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('body').click(function(){
				$('#search_notice').css("display","none");
			})
		};
		function gotoMenuTab(menuCodeId,menuCode,name,ourl){
			var tabText=$("#mainFrame").contents().find(".tabs .jericho_tabs");
			var flag=false;
			if(tabText.length==0){
				oSearchNotice.style.display="none";
				var url=ourl;
				top.mainFrame.jericho.buildTree(menuCodeId,menuCode,name,url);
			}else{
				for(var k=0;k<tabText.length;k++){
					var tabName=tabText.eq(k).attr("datalink");
					if(ourl == tabName){
						flag=true;
						break;
					}else{
						flag=false;
					}
				}
				if(flag==true){
					oSearchNotice.style.display="none";
					$("#mainFrame").contents().find(".tabs li").removeClass('tab_selected').addClass('tab_unselect').css('color','rgb(88, 88, 88)');
					$("#mainFrame").contents().find(".tabs li").eq(k).removeClass('tab_unselect').addClass('tab_selected').css('color','#fff')
					$("#mainFrame").contents().find("#jerichotab_contentholder div").removeClass('curholder').addClass('holder');
					$("#mainFrame").contents().find("#jerichotab_contentholder div").css('display','none');
					$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(k).next().removeClass('holder').addClass('curholder');
					$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(k).next().css('display','block');
				}else{
					oSearchNotice.style.display="none";
					var url=ourl;
					top.mainFrame.jericho.buildTree(menuCodeId,menuCode,name,url);
				}
			}
		}
		/* 键盘上下箭头事件 */
		document.onkeydown = function (ev) {
			var ev=ev||window.event;
			 var scroll_height = 26;
			if(ev.keyCode == 38 || ev.keyCode == 40 || ev.keyCode == 13) {
				var aLi = $('#search_notice li');
				if(ev.keyCode == 38) {
					iIndex--;
					if(iIndex<aLi.length-6){
						oSearchNotice.scrollTop = oSearchNotice.scrollTop - scroll_height;
					}
				} else if(ev.keyCode == 40) {
					iIndex++;
					if(iIndex>5){
						oSearchNotice.scrollTop = oSearchNotice.scrollTop + scroll_height;
					}
				} else if(ev.keyCode == 13){
					if($("#search_content").val()==''){
						return false;
					}
				}
				if(iIndex < 0) {
					iIndex = aLi.length - 1;
					oSearchNotice.scrollTop=oSearchNotice.scrollHeight;
				} else if(iIndex >= aLi.length) {
					iIndex = 0;
					oSearchNotice.scrollTop=0;
				}
				for(var i=0; i< aLi.length; i++) {
					aLi[i].className = '';
				}
				aLi[iIndex].className = 'active';
				oSearchContent.value=aLi[iIndex].innerHTML;
				return false;
			}
		}

	//一级菜单之间收缩与展开
	var activeLi=$("#nav-menu li.gray");
	var icon=true;
	var liLength=activeLi.length;
		activeLi.bind('click',function(){
			$(this).css("display","block");
			$(this).siblings().children("ul").css("display","none");
			$(this).find('.first_child b').removeClass('icon-angle-down').addClass('icon-angle-up');
			if($(this).find('ul:first').css('display')=='block'){
				$(this).find('.first_child b').removeClass('icon-angle-up').addClass('icon-angle-down');
			}
			$(this).siblings().find('.first_child b').removeClass('icon-angle-up').addClass('icon-angle-down');				
		
	})
	//获取左侧菜单
	function getSubMenu(menuCode,id){
		var locat = (window.location+'').split('/');
		locat =  locat[0]+'//'+locat[2]+'/'+locat[3];
		var url = locat+"/loginController/getSubMenu&menuCode=" + menuCode + "&tm="+new Date().getTime();
		$.ajax({
			url:url,
			type:"POST",
			dataType:"JSON",
			success: function(data){
				var htmlResult = "";
				if (data.success){
					htmlResult = data.obj;
					$("#"+id).find("a").first().siblings().remove();
					$("#"+id).append(htmlResult);
					loadTab();
					secondMenu();
				}else{
					htmlResult = '<li class="active" id="fhindex" style="margin-left: 40px;"><i class="icon-dashboard" style="margin-right: 3px;color:#999 !important;"></i><span>菜单加载失败</span></li>';
					$("#"+id).find("a").first().siblings().remove();
					$("#"+id).append(htmlResult);
				}
			}
		});
	}
	//二级菜单之间收缩与展开
	 function secondMenu(){
		var secondLi=$(".second_child");
		var liLen=secondLi.length;
			secondLi.bind('click',function(){
				$(this).children('span:first').css('background','url(weblib/assets/images/gallery/arrow_1.png) no-repeat');
				if($(this).siblings('ul').css('display')=='block'){
					$(this).siblings('ul').children('li').children('a').css('font-weight','normal');
					$(this).children('span:first').css('background','url(weblib/assets/images/gallery/arrow_2.png) no-repeat');
					document.getElementById('box').style.display="none";
				}
				if($(this).parent().siblings('li').children('ul').css('display')=='block'){
					$(this).parent().siblings('li').children('ul').children('li').children('a').css('font-weight','normal');
				}
				$(this).siblings('ul').children('a').css('font-weight','normal');
				$(this).parent().siblings().find('span:first').css('background','url(weblib/assets/images/gallery/arrow_2.png) no-repeat');
				document.getElementById('box').style.display="none";
			})
		
	} 
	var subMenusd,tabs,tabClose,closeLen,liValue,datalink,aWidth;
	function loadTab(){
		subMenusd=document.getElementById("nav-menu");
		//左侧菜单点击事件
		var func=$('.func');
		var once,jerichotabiframe,start,end,id;
		var funLen=func.length;
		var div=document.getElementById('box');
		document.onclick=function(){
			div.style.display='none';
		}
		$("#mainFrame").contents().find('body').click(function(){
			$("#box").css("display","none");
		})
		$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('body').click(function(){
			$("#box").css("display","none");
		})
		for(var i=0;i<funLen;i++){
			//右键三级菜单添加到工作台
			func[i].oncontextmenu=function(evt){
				liValue=this.getAttribute('name');
				datalink=this.getAttribute('datelink');
				menuCode=this.parentNode.getAttribute('id');
				aWidth=$(this).width();
				var e=evt||event
				f(e)
				div.style.display='block'
				var disx=e.clientX
				var disy=e.clientY
				div.style.left=disx+'px'
				div.style.top=disy+'px'
			}
		}
		func.bind('click',function(event){
			var menuFlag=false;
			$("#box").css("display","none");
			event.stopPropagation();
			var link=$(this).attr('dateLink');
			var textTab=$("#mainFrame").contents().find(".tab_text");
			tabs=$("#mainFrame").contents().find("tabs").eq(0);
			tabs.css('width','96.5%');
			$(this).parent().parent().parent().find('.second_child span:first').css('background','url(weblib/assets/images/gallery/arrow_1.png) no-repeat');
			$(this).parent().removeClass("active").addClass("secondActived");
		})
	}		
	/* 菜单添加到工作台事件 */
	var imgArr=['menu1','menu2','menu3','menu4','menu5','menu6','menu7','menu8','menu9','menu10','menu11','menu12']
	$("#box li").click(function(){
		var newliValue=liValue;
		addMenu(newliValue,datalink,aWidth);
	})
	function addMenu(liValue,datalink,aWidth){
		var dlLength=0;
		var dlFlag=false;
		var src="weblib/assets/images/gallery/"+imgArr[0]+".jpg";
		$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_").contents().find('#commanMenu').css('display','block');
		$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_").contents().find('#mainDiv').css('border','1px solid rgb(194, 226, 251)');
		var strDl='<dl class="dlMenu" id="'+menuCode+'">'+
					'<dt><img src="'+src+'"/></dt>'+
					'<dd datalink="'+ datalink +'">'+ liValue +'</dd>'+
				   '</dl>'
		$.each(imgArr,function(index,item){  
            if(item==imgArr[0]){
                imgArr.splice(index,1);
                return imgArr;
             }
        })
		if(imgArr.length==0){
			imgArr=['menu1','menu2','menu3','menu4','menu5','menu6','menu7','menu8','menu9','menu10','menu11','menu12']
		}
		var commanMenu=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_").contents().find('#commanMenu');
		var dlMenu=$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('.dlMenu');
		var dlMenuA=$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('.dlMenu dd');
		var iframe0=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabholder_0");
		var w1=0;
		dlLength=dlMenu.length;
		if(iframe0.css("display")=="none"){
			iframe0.css({"display":"block","height":"0px"});
		}
		dlMenu.each(function(){
			w1 += $(this).innerWidth();
		});
		if((w1+44+aWidth) >= commanMenu.width()){
			$("#box").css("display","none");
			bootbox.alert("新添菜单数量超出上限，请先右键删除其他已添加的菜单");
		}else{
			for(var i=0;i<dlMenuA.length;i++){
				if(liValue == dlMenuA[i].innerHTML){
					dlFlag=true;
					break;	
				}else{
					dlFlag=false;
				}
			}
			if(dlFlag == true){
				bootbox.alert("该菜单已经添加成功，不能重复添加!");
			}else{
				
				addContextMenu(menuCode,liValue,src);
				$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('#commanMenu').append(strDl);
				dlLength++;
				
			}
			$("#box").css("display","none");
			//右键出现删除菜单事件
			var commanMenu=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_").contents().find('#commanMenu');
			var dlMenu=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_").contents().find('.dlMenu');
			var divDelate=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_").contents().find('#delate');
			var divDelateA=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_").contents().find('#delate li');
			var menuIndex;
			menuIndex = -1;
			for(var i=0;i<dlMenu.length;i++){
				dlMenu[i].index=i;
				dlMenu[i].oncontextmenu=function(evt){
					var e=evt||event
					f(e)
					divDelate.css('display','block');
					var disx=e.clientX;
					var disy=e.clientY;
					divDelate.css('left',disx);
					divDelate.css('top',disy);
					menuIndex = this.index;
					menuCode = this.getAttribute('id');
				}
				dlMenu[i].onclick=function(){
					var dlTabText=$("#mainFrame").contents().find(".tabs .tab_text");
					var dlTabFlag=false;
					var dlCodeId=$(this).attr("id");
					var dlCode='lm'+dlCodeId.substring(1,dlCodeId.length);
					var dlInner=$(this).children('dd').html();
					var dlDataLink=$(this).children('dd').attr('datalink');
					for(var i=0;i<dlTabText.length;i++){
						var tabName=dlTabText.eq(i).attr("title");
						if(dlInner == tabName){
							dlTabFlag=true;
							break;
						}else{
							dlTabFlag=false;
						}
					}
					if(dlTabFlag==true){
						$("#mainFrame").contents().find(".tabs li").removeClass('tab_selected').addClass('tab_unselect').css('color','rgb(88, 88, 88)');
						$("#mainFrame").contents().find(".tabs li").eq(i).next().removeClass('tab_unselect').addClass('tab_selected').css('color','#fff')
						$("#mainFrame").contents().find("#jerichotab_contentholder div").removeClass('curholder').addClass('holder');
						$("#mainFrame").contents().find("#jerichotab_contentholder div").css('display','none');
						$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(i).next().removeClass('holder').addClass('curholder');
						$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(i).next().css('display','block');
					}else{
						var taburl= dlDataLink;
						//modal("#layer_loading,#image");
						top.mainFrame.jericho.buildTree(dlCodeId,dlCode,dlInner,taburl);
					}
				}
			}
			//点击删除菜单，菜单隐藏事件
			$(divDelateA).click(function(){
				if(menuIndex=='-1'){
					return;
				}else{
					delate(menuIndex);
					deleteContextMenu(menuCode);
				}
				//event.stopPropagation();
			})
			function delate(menuindex){
				dlMenu.eq(menuindex).remove();
				if($("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_").contents().find('.dlMenu').length==0){
					commanMenu.css('display','none');	
					$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_").contents().find('#mainDiv').css('border','none');	
				} 
			}
			document.onclick=function(){
				divDelate.css('display','none');
			}
			$("#mainFrame").contents().find('body').click(function(){
				divDelate.css("display","none");
			})
			$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('body').click(function(){
				divDelate.css("display","none");
			})
		}
	}
	function f(e){
		if(e.preventDefault){
			e.preventDefault()
		}else{
			e.returnValue=false
		}
	}
	/*调用后台方法添加快捷菜单*/
	function addContextMenu(menuCode,liValue,src){
		$.ajax({
			type: "POST",
			url: "<%=basePath%>UserContextMenuController.do?method=save&menuCode="+menuCode+"&menuName="+encodeURI(encodeURI(liValue))+"&contextMenuSrc="+src,
			dataType:'json',
			cache: false,
			success: function(data){	
				if (data.success){  //处理成功
				
				} else {
					top.hangge();
					bootbox.alert('添加失败!');
				}
			}
		});
	}
	
	/*调用后台方法删除快捷菜单*/
	function deleteContextMenu(menuCode){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>UserContextMenuController.do?method=del',
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
	};
	//修改密码
	function modifyPwd(userId){
	var diag = new top.Dialog();
		diag.Drag = true;
		diag.Title ="修改密码";
		diag.URL = "<%=basePath%>loginController.do?method=tomodifyUserPsw&user_id="+userId;
		diag.Width = 420;
		diag.Height = 300;
		diag.CancelEvent = function(){ //关闭事件
		diag.close();
		};
		diag.show();
	};
</script>
</body>
</html>
