$(document).ready(function(){
	//页面加载时计算页面的高度
	var off=true;
	cmainFrame();
	function cmainFrame(){
		$("#main-container,#sidebar,#nav-menu,#sidebar-collapsed,#mainContent,#page-content,#parentFrame,#mainFrame").height('');
		var headHeight=$("#navbar").height();
		var mainheight=document.documentElement.clientHeight-headHeight-2;
		$("#nav-menu").height(mainheight-$("#search_form").height()-22);
		$("#sidebar-collapsed").css('line-height',mainheight+'px');
		$("#sidebar").height(mainheight-10);
		$("#main-container,#sidebar-collapsed,#mainContent,#page-content,#parentFrame").height(mainheight);
		var mainFrameHeight=mainheight-5;
		$("#mainFrame").height(mainFrameHeight);
	}
	//左侧箭头点击事件
	$("#sidebar-collapsed").click(function(){
		var iframeTable = $("#mainFrame").contents().find("#jerichotab_contentholder iframe:not(:first)").contents().find(".fixed-table-body table");
		var iframeLength = iframeTable.length;
		if(off==true){
			$("#sidebar").css("display","none");
			$("#imgs").removeClass("icon-backward").addClass("icon-forward");
			$("#mainContent").css("margin-left","6px");
			$.each(iframeTable,function(i){
				var oldTableWid = $(this).width();
				$(this).parent().prev().children().children().width(oldTableWid);
			});
			off=false;
		}else{
			$("#sidebar").css("display","block");
			$("#imgs").removeClass("icon-forward").addClass("icon-backward");
			$("#mainContent").css("margin-left","206px");
			$.each(iframeTable,function(i){
				var oldTableWid = $(this).width();
				$(this).parent().prev().children().children().width(oldTableWid);
			});
			off=true;
		}
	})
	//窗口事件
	window.onresize=function(){
		cmainFrame();
	}
})
		
//更改头部导航的样式
function changeShow(id){
	$("#" + id).parent("ul").children("li").removeClass("open");
    $("#" + id).addClass("actived");
    $("#" + id).parent("ul").children("li").not("#" + id).removeClass("actived");
};




	


		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		