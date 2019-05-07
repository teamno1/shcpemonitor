var locat = (window.location+'').split('/'); 
$(function(){if('main'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});


//菜单状态切换
window.onload=function(){
	
};	
var tabHtml;
window.onload=function(){
	tabHtml=document.getElementById("mainFrame").contentDocument;
};
var fmid = "fhindex";
var mid = "fhindex";
function siMenu(obj,id,fid,MENU_NAME,MENU_URL){
	var menuFlag=false;
	var link=obj.getAttribute('dateLink');
	var textTab=$("#mainFrame").contents().find(".jericho_tabs");
	//再次点击左侧菜单时，页面刷新
	for(var j=0;j<textTab.length;j++){
		var tabTitle=textTab[j].getAttribute('datalink');
		if(obj.getAttribute('datelink')==tabTitle){
			menuFlag=true;
			var tabLi=textTab[j].getAttribute("id");
			var tabLi1,tabLi2;
			if(tabLi.length==12){
				tabLi1 = tabLi.substring(0,10);
				tabLi2 = tabLi.substring(10,12);
			}else if(tabLi.length==13){
				tabLi1 = tabLi.substring(0,10);
				tabLi2 = tabLi.substring(10,13);
			}else if(tabLi.length==14){
				tabLi1 = tabLi.substring(0,10);
				tabLi2 = tabLi.substring(10,14);
			}
			//var tabIframe=tabLi1+'iframe'+tabLi2;
			var tabIframe=tabLi1+'iframe_'+fid;
			$(obj).parent().siblings('li').children('a').css('fontWeight','normal');
			$(obj).parent().parent().parent().siblings('li').children('a').css('fontWeight','normal');
			obj.style.fontWeight="bold";
			obj.setAttribute("id",tabLi);
			obj.setAttribute("href",link+'&tabPageId='+tabIframe);
			obj.setAttribute("target",tabIframe);
			$("#mainFrame").contents().find(".tabs li").removeClass('tab_selected').addClass('tab_unselect').css('color','rgb(88, 88, 88)');
			$("#mainFrame").contents().find(".tabs li").eq(j).removeClass('tab_unselect').addClass('tab_selected').css('color','#fff')
			$("#mainFrame").contents().find("#jerichotab_contentholder div").removeClass('curholder').addClass('holder');
			$("#mainFrame").contents().find("#jerichotab_contentholder div").css('display','none');
			$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(j).next().removeClass('holder').addClass('curholder');
			$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(j).next().css('display','block');
			break;
		}else{
			menuFlag=false;
		}
	}
	if(menuFlag==true){
		if(obj.getAttribute('target')){
			//alert(1)
		}
	}else{
		if(id != mid){
			$("#"+mid).removeClass();
			mid = id;
		}
		if(fid != fmid){
			$("#"+fmid).removeClass();
			fmid = fid;
		}
		$(obj).parent().parent().parent().siblings('li').children('a').css('font-weight','normal');
		$(obj).parent().siblings('li').children('a').css('font-weight','normal');
		obj.style.fontWeight="bold";
		//modal("#layer_loading,#image");
		top.mainFrame.jericho.buildTree(id,fid,MENU_NAME,MENU_URL);
	}	
	$(obj).parent().parent().parent().siblings('li').children('a').css('font-weight','normal');
	$("#"+fid).attr("class","active open");
	$("#"+id).attr("class","active");
	if(MENU_URL != "druid/index.html"){
		jzts();
	}
}


/*$(function(){
	//换肤
	$("#skin-colorpicker").ace_colorpicker().on("change",function(){
		var b=$(this).find("option:selected").data("class");
		hf(b);
		var url = locat+'/head/setSKIN.do?SKIN='+b+'&tm='+new Date().getTime();
		$.get(url,function(data){});
	
	});
});*/

var USER_ID;

var user = "FH";	//用于即时通讯（ 当前登录用户）



//在线管理
var websocket;
function online(){
	if (window.WebSocket) {
		websocket = new WebSocket(encodeURI('ws://'+oladress));
		
		websocket.onopen = function() {
			//连接成功
			websocket.send('[join]'+user);
		};
		websocket.onerror = function() {
			//连接失败
		};
		websocket.onclose = function() {
			//连接断开
		};
		//消息接收
		websocket.onmessage = function(message) {
			var message = JSON.parse(message.data);
			if (message.type == 'count') {
				userCount = message.msg;
			}else if(message.type == 'goOut'){
				$("body").html("");
				goOut("此用户在其它终端已经早于您登录,您暂时无法登录");
			}else if(message.type == 'thegoout'){
				$("body").html("");
				goOut("您被系统管理员强制下线");
			}else if(message.type == 'changeMenu'){
				window.location.href=locat+'/main/yes';
			}else if(message.type == 'userlist'){
				userlist = message.list;
			}
		};
	}
}

//在线总数
var userCount = 0;
function getUserCount(){
	websocket.send('[count]'+user);
	return userCount;
}
//用户列表
var userlist = "";
function getUserlist(){
	websocket.send('[getUserlist]'+user);
	return userlist;
}
//强制下线
function goOut(msg){
	alert(msg);
	window.location.href=locat+"/logout";
}
//强制某用户下线
function goOutUser(theuser){
	websocket.send('[goOut]'+theuser);
}


//换肤
function hf(b){
	
	var a=$(document.body);
	a.attr("class",a.hasClass("navbar-fixed")?"navbar-fixed":"");
	if(b!="default"){
		a.addClass(b);
	}if(b=="skin-1"){
		$(".ace-nav > li.grey").addClass("dark");
	}else{
		$(".ace-nav > li.grey").removeClass("dark");
	}
	if(b=="skin-2"){
			$(".ace-nav > li").addClass("no-border margin-1");
			$(".ace-nav > li:not(:last-child)").addClass("white-pink")
			.find('> a > [class*="icon-"]').addClass("pink").end()
			.eq(0).find(".badge").addClass("badge-warning");
	}else{
			$(".ace-nav > li").removeClass("no-border").removeClass("margin-1");
			$(".ace-nav > li:not(:last-child)").removeClass("white-pink")
			.find('> a > [class*="icon-"]').removeClass("pink").end()
			.eq(0).find(".badge").removeClass("badge-warning");
	}
	if(b=="skin-3"){
		$(".ace-nav > li.grey").addClass("red").find(".badge").addClass("badge-yellow");
	}else{
		$(".ace-nav > li.grey").removeClass("red").find(".badge").removeClass("badge-yellow");
	}
}

//修改个人资料
function editUserH(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="个人资料";
	 diag.URL = locat+'/user/goEditU.do?USER_ID='+USER_ID+'&fx=head';
	 diag.Width = 225;
	 diag.Height = 389;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//系统设置
function editSys(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="系统设置";
	 diag.URL = locat+'/head/goSystem.do';
	 diag.Width = 600;
	 diag.Height = 596;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//代码生成
function productCode(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="代码生成器";
	 diag.URL = locat+'/head/goProductCode.do';
	 diag.Width = 800;
	 diag.Height = 450;
	 diag.CancelEvent = function(){ //关闭事件
		hangge();
		diag.close();
	 };
	 diag.show();
}

//数据字典
function zidian(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="数据字典";
	 diag.URL = locat+'/dictionaries.do?PARENT_ID=0';
	 diag.Width = 799;
	 diag.Height = 460;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
	 
}

//菜单
function menu(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="菜单编辑";
	 diag.URL = locat+'/menu.do';
	 diag.Width = 720;
	 diag.Height = 390;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
	 
}

//切换菜单
function changeMenu(){
	websocket.send('[changeMenu]'+user);
}

//清除加载进度
function hangge(){
	$("#jzts").hide();
}

//显示加载进度
function jzts(){
	$("#jzts").show();
}

