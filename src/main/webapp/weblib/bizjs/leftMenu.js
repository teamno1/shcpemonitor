/*//左侧菜单快速打开
		//根据ID获取DOM
		function getId(id) {
			return document.getElementById(id);
		}
		var
			oSearchContent = getId('search_content'),
			oSearchButton  = getId('search_btn'),
			oSearchNotice  = getId('search_notice');
		oSearchNotice.style.display="none";
		var	iIndex = -1;
		var reg=/^[\u4e00-\u9fa5]+$/;
		oSearchContent.oninput = oSearchContent.onpropertychange = function () {
			var inputValue = oSearchContent.value;
			var str='';
			if(reg.test(inputValue)){
				 $.ajax({
					url:"menuController.do?selectmenu",
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
								str+='<li onclick="'+'gotoMenuTab(\''+menuCode+'\',\''+liHtml+'\',\''+menuUrl+'\');">'+liHtml+'</li>';
							}
						}else{
							str='<li>没有相关菜单目录</li>';
						}
						oSearchNotice.style.display="block";
						oSearchNotice.innerHTML=str;
						if($("#search_notice li").length>6){
							oSearchNotice.style.height='158px';
							oSearchNotice.style.overflow='auto';
						}else{
							oSearchNotice.style.height='';
						}
					}
				}) ;
			}else if(inputValue==''){
				oSearchNotice.style.display="none";
			}else{
				str='<li>没有相关菜单目录</li>';
				oSearchNotice.style.height='';
				oSearchNotice.style.display="block";
				oSearchNotice.innerHTML=str;
			}
			$('body').click(function(){
				$('#search_notice').css("display","none");
			});
			$("#mainFrame").contents().find('body').click(function(){
				$('#search_notice').css("display","none");
			});
			$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('body').click(function(){
				$('#search_notice').css("display","none");
			});
		};
		function gotoMenuTab(menuCode,name,ourl){
			var tabText=$("#mainFrame").contents().find(".tabs .tab_text");
			var flag=false;
			if(tabText.length==0){
				oSearchNotice.style.display="none";
				var url="<%=basePath%>"+ourl;
				top.mainFrame.jericho.buildTree(menuCode,name,url);
			}else{
				for(var k=0;k<tabText.length;k++){
					var tabName=tabText.eq(k).attr("title");
					if(name == tabName){
						flag=true;
						break;
					}else{
						flag=false;
					}
				}
				if(flag==true){
					oSearchNotice.style.display="none";
					$("#mainFrame").contents().find(".tabs li").removeClass('tab_selected').addClass('tab_unselect').css('color','rgb(88, 88, 88)');
					$("#mainFrame").contents().find(".tabs li").eq(k).next().removeClass('tab_unselect').addClass('tab_selected').css('color','#fff');
					$("#mainFrame").contents().find("#jerichotab_contentholder div").removeClass('curholder').addClass('holder');
					$("#mainFrame").contents().find("#jerichotab_contentholder div").css('height','0px');
					$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(k).next().next().removeClass('holder').addClass('curholder');
					$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(k).next().next().css('height','inherit');
				}else{
					oSearchNotice.style.display="none";
					var url="<%=basePath%>"+ourl;
					top.mainFrame.jericho.buildTree(menuCode,name,url);
				}
			}
		}
		 键盘上下箭头事件 
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
		};
		//回车事件，敲击回车，跳转到相应的tab页
		$("#search_content").keydown(function(ev){
			var ev=ev||window.event;
			var aLid = $('#search_notice li');
			if(ev.keyCode == 13){
				if($("#search_content").val()!=''){
					aLid.eq(iIndex).trigger("click");
				}else{
					return false;
				}
			}
		});
	显示默认左侧菜单
	var firstMenuId = document.getElementById("firstMenuId").value;
	if(firstMenuId!='undefinded'){
		getSubMenu(firstMenuId);
	}
	//tab导航操作
	var tabHtml;
	window.onload=function(){
		tabHtml=document.getElementById("mainFrame").contentDocument;
	};
	//一级菜单之间收缩与展开
	var activeLi=$("#nav-menu li.gray");
	var icon=true;
	var liLength=activeLi.length;
	for(var i=0;i<liLength;i++){
		activeLi[i].addEventListener('click',function(){
			$(this).css("display","block");
			$(this).siblings().children("ul").css("display","none");
			$(this).find('.first_child b').removeClass('icon-angle-down').addClass('icon-angle-up');
			if($(this).find('ul:first').css('display')=='block'){
				$(this).find('.first_child b').removeClass('icon-angle-up').addClass('icon-angle-down');
			}
			$(this).siblings().find('.first_child b').removeClass('icon-angle-up').addClass('icon-angle-down');				
		},false)
	}
	//获取左侧菜单
	function getSubMenu(menuCode,id){
		var locat = (window.location+'').split('/');
		locat =  locat[0]+'//'+locat[2]+'/'+locat[3];
		var url = locat+"/menuController.do?getSubMenu&menuCode=" + menuCode + "&tm="+new Date().getTime();
		$.ajax({
			url:url,
			type:"GET",
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
		for(var j=0;j<liLen;j++){
			secondLi[j].addEventListener('click',function(){
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
			},false)
		} 
	} 
	var subMenusd,tabs,tabClose,closeLen,liValue,datalink,aWidth;
	function loadTab(){
		subMenusd=document.getElementById("nav-menu");
		//左侧菜单点击事件
		var func=subMenusd.getElementsByClassName('func');
		var once,jerichotabiframe,start,end,id;
		var funLen=func.length;
		var div=document.getElementById('box');
		document.onclick=function(){
			div.style.display='none';
		};
		$("#mainFrame").contents().find('body').click(function(){
			$("#box").css("display","none");
		});
		$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('body').click(function(){
			$("#box").css("display","none");
		});
		for(var i=0;i<funLen;i++){
			//右键三级菜单添加到工作台
			func[i].oncontextmenu=function(evt){
				liValue=this.innerHTML;
				datalink=this.getAttribute('datelink');
				menuCode=this.parentNode.getAttribute('id');
				aWidth=$(this).width();
				var e=evt||event;
				f(e);
				div.style.display='block';
				var disx=e.clientX;
				var disy=e.clientY;
				div.style.left=disx+'px';
				div.style.top=disy-42+'px';
			};
			func[i].addEventListener('click',function(event){
				var menuFlag=false;
				$("#box").css("display","none");
				event.stopPropagation();
				var link=$(this).attr('dateLink');
				var textTab=tabHtml.getElementsByClassName("tab_text");
				tabs=tabHtml.getElementsByClassName("tabs")[0];
				tabs.style.width='96.5%';
				$(this).parent().parent().parent().find('.second_child span:first').css('background','url(weblib/assets/images/gallery/arrow_1.png) no-repeat');
				$(this).parent().removeClass("active").addClass("secondActived");
				jerichotabiframe=localStorage.getItem('key');
				if(jerichotabiframe.length==18){
					start=jerichotabiframe.substring(0,10);
					end=jerichotabiframe.substring(16,18);
				}else if(jerichotabiframe.length==19){
					start=jerichotabiframe.substring(0,10);
					end=jerichotabiframe.substring(16,19);
				}else if(jerichotabiframe.length==20){
					start=jerichotabiframe.substring(0,10);
					end=jerichotabiframe.substring(16,20);
				}
				//再次点击左侧菜单时，页面刷新
				if($(this).attr('target')){
					//alert(1)
				}else{
					id=start+end;
					this.setAttribute("id",id);
					this.setAttribute("href",link+'&tabPageId='+jerichotabiframe);
					this.setAttribute("target",jerichotabiframe);
					//$.cookie($(this).attr('id'),$(this).attr("href"));
				}
			});
		}
	}		
	 菜单添加到工作台事件 
	var imgArr=['menu1','menu2','menu3','menu4','menu5','menu6','menu7','menu8','menu9','menu10','menu11','menu12'];
	$("#box li").click(function(){
		var newliValue=liValue;
		addMenu(newliValue,datalink,aWidth);
	});
	function addMenu(liValue,datalink,aWidth){
		var dlLength=0;
		var dlFlag=false;
		$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_0").contents().find('#commanMenu').css('display','block');
		$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_0").contents().find('#mainDiv').css('border','1px solid rgb(194, 226, 251)');
		var strDl='<dl class="dlMenu">'+
					'<dt><img src="weblib/assets/images/gallery/'+imgArr[0]+'.jpg"/></dt>'+
					'<dd datalink="'+ datalink +'">'+ liValue +'</dd>'+
				   '</dl>';
		$.each(imgArr,function(index,item){  
            if(item==imgArr[0]){
                imgArr.splice(index,1);
                return imgArr;
             }
        });
		if(imgArr.length==0){
			imgArr=['menu1','menu2','menu3','menu4','menu5','menu6','menu7','menu8','menu9','menu10','menu11','menu12'];
		}
		var commanMenu=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_0").contents().find('#commanMenu');
		var dlMenu=$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('.dlMenu');
		var dlMenuA=$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('.dlMenu dd');
		var w1=0;
		dlLength=dlMenu.length;
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
				var src="weblib/assets/images/gallery/"+imgArr[0]+".jpg";
				addContextMenu(menuCode,datalink,src);
				$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('#commanMenu').append(strDl);
				dlLength++;
				
			}
			$("#box").css("display","none");
			//右键出现删除菜单事件
			var commanMenu=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_0").contents().find('#commanMenu');
			var dlMenu=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_0").contents().find('.dlMenu');
			var divDelate=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_0").contents().find('#delate');
			var divDelateA=$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_0").contents().find('#delate li');
			var menuIndex;
			for(var i=0;i<dlMenu.length;i++){
				dlMenu[i].index=i;
				dlMenu[i].oncontextmenu=function(evt){
					menuIndex = 0;
					var e=evt||event;
					f(e);
					divDelate.css('display','block');
					var disx=e.clientX;
					var disy=e.clientY;
					divDelate.css('left',disx);
					divDelate.css('top',disy);
					menuIndex = this.index;
				};
				dlMenu[i].onclick=function(){
					var dlTabText=$("#mainFrame").contents().find(".tabs .tab_text");
					var dlTabFlag=false;
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
						$("#mainFrame").contents().find(".tabs li").eq(i).next().removeClass('tab_unselect').addClass('tab_selected').css('color','#fff');
						$("#mainFrame").contents().find("#jerichotab_contentholder div").removeClass('curholder').addClass('holder');
						$("#mainFrame").contents().find("#jerichotab_contentholder div").css('height','0px');
						$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(i).next().next().removeClass('holder').addClass('curholder');
						$("#mainFrame").contents().find("#jerichotab_contentholder div").eq(i).next().next().css('height','inherit');
					}else{
						var taburl="<%=basePath%>" + dlDataLink;
						top.mainFrame.jericho.buildTree(dlInner,dlInner,taburl);
					}
				};
			}
			//点击删除菜单，菜单隐藏事件
			$(divDelateA).click(function(){
				delate(menuIndex);
				deleteContextMenu(menuCode);
			});
			function delate(menuindex){
				dlMenu.eq(menuindex).remove();
				if($("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_0").contents().find('.dlMenu').length==0){
					commanMenu.css('display','none');	
					$("#mainFrame").contents().find("#jerichotab_contentholder #jerichotabiframe_0").contents().find('#mainDiv').css('border','none');	
				} 
			}
			document.onclick=function(){
				divDelate.css('display','none');
			};
			$("#mainFrame").contents().find('body').click(function(){
				divDelate.css("display","none");
			});
			$("#mainFrame").contents().find("#jerichotab_contentholder iframe").contents().find('body').click(function(){
				divDelate.css("display","none");
			});
		}
	}
	function f(e){
		if(e.preventDefault){
			e.preventDefault();
		}else{
			e.returnValue=false;
		}
	}
	 var alertInfo=$("#alertInfo").val();
		if(alertInfo!=""){
		 $("#note").tips({
				side : 3,
				msg : '您的密码即将到期，请及时修改',
				bg : '#68B500',
				time : 30
			}); 
		}
	showTips("note",alertInfo, 60, "#68B500", 1); 
	调用后台方法添加快捷菜单
	function addContextMenu(menuCode,datalink,src){
		$.ajax({
			type: "POST",
			url:"UserContextMenuController.do?save&menuCode="+menuCode+"&menuUrl="+datalink+"&contextMenuSrc="+src,
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
	
	调用后台方法删除快捷菜单
	function deleteContextMenu(menuCode){
		$.ajax({
			type: "POST",
			url:'UserContextMenuController.do?del',
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
	}*/