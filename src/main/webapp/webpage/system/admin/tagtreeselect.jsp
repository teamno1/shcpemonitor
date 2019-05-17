<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
	<title>数据选择</title>
	<base href="<%=basePath%>">
	<%-- jsp文件头和头部 --%>
	<%@ include file="top.jsp"%> 
	<link href="static/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
		<style type="text/css">
			/* #tagSelectSearch{background:url(weblib/assets/images/gallery/navbg.jpg) repeat-x !important;border:1px solid rgb(68, 185, 236);padding:7px 9px;} */
			#tagSelectSearch{
				background:url(weblib/assets/images/gallery/navbg.jpg) repeat-x !important;border:none!important;padding:0px 4px!important;
				margin-left:-4px!important;color:#fff!important;font-size:14px!important;
			}
		</style>

<%@ include file="footer.jsp"%>
<script src="static/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
	//获取URL地址参数
	function getQueryString(name, url) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	    if (!url || url == ""){
		    url = window.location.search;
	    }else{	
	    	url = url.substring(url.indexOf("?"));
	    }
	    r = url.substr(1).match(reg)
	    if (r != null) return unescape(r[2]); return null;
	}
	var key, lastValue = "", nodeList = [], type = getQueryString("type", "${url}");
	var tree, setting = {
		view:{
			selectedMulti:false,
			dblClickExpand:false
			},
		check:{
			enable:"${checked}",
			nocheckInherit:true
			},
		async:{
			url:"tagController.do?method=treeData"
			},
		data:{
			simpleData:{
				enable:true
				}
			},
		callback:{
			onClick:function(event, treeId, treeNode){
				tree.expandNode(treeNode);
			},onCheck: function(e, treeId, treeNode){
				var nodes = tree.getCheckedNodes(true);
				for (var i=0, l=nodes.length; i<l; i++) {
					tree.expandNode(nodes[i], true, false, false);
				}
				return false;
			},onAsyncSuccess: function(event, treeId, treeNode, msg){
				var nodes = tree.getNodesByParam("pId", treeNode.id, null);
				for (var i=0, l=nodes.length; i<l; i++) {
					try{tree.checkNode(nodes[i], treeNode.checked, true);}catch(e){}
					//tree.selectNode(nodes[i], false);
				}
				//selectCheckNode();
			},onDblClick: function(){//<c:if test="${!checked}">
				top.$.jBox.getBox().find("button[value='ok']").trigger("click");
				//$("input[type='text']", top.mainFrame.document).focus();//</c:if>
			}
		}
	};
	function expandNodes(nodes) {
		if (!nodes) return;
		for (var i=0, l=nodes.length; i<l; i++) {
			tree.expandNode(nodes[i], true, false, false);
			if (nodes[i].isParent && nodes[i].zAsync) {
				expandNodes(nodes[i].children);
			}
		}
	}
	$(document).ready(function(){
		var url = "${url}&extId=${extId}&isAll=${isAll}&module=${module}&subnode=${subnode}&pnode=${pnode}&nodename=${nodename}&sourcename=${sourcename}&t="
			+ new Date().getTime();
		$.get(url, function(zNodes){
			zNodes = eval(zNodes);
			tree = $.fn.zTree.init($("#tree"), setting, zNodes);
			// 默认展开一级节点
			var nodes = tree.getNodesByParam("level", 0);
			for(var i=0; i<nodes.length; i++) {
				tree.expandNode(nodes[i], true, false, false);
			}
			//异步加载子节点（加载用户）
			var nodesOne = tree.getNodesByParam("isParent", true);
			for(var j=0; j<nodesOne.length; j++) {
				tree.reAsyncChildNodes(nodesOne[j],"!refresh",true);
			}
			selectCheckNode();
		});
		key = $("#key");
		key.bind("focus", focusKey);//.bind("blur", blurKey);
		key.bind('keydown', function (e){
			if(e.which == 13){
				searchNode();
			}
		}); 
		setTimeout("search();", "300");
	});
	// 默认选择节点
	function selectCheckNode(){
		var ids = "${selectIds}".split(",");
		for(var i=0; i<ids.length; i++) {
			var node = tree.getNodeByParam("id", ids[i]);
			if("${checked}" == "true"){
				try{tree.checkNode(node, true, true);}catch(e){}
				tree.selectNode(node, false);
			}else{
				tree.selectNode(node, true);
			}
		}
	}
  	function focusKey(e) {
		if (key.hasClass("empty")) {
			key.removeClass("empty");
		}
	}
	function blurKey(e) {
		if (key.get(0).value === "") {
			key.addClass("empty");
		}
		searchNode(e);
	}
	//搜索节点
	function searchNode() {
		// 取得输入的关键字的值
		var value = $.trim(key.get(0).value);
		// 按名字查询
		var keyType = "name";
		// 如果和上次一次，就退出不查了。
		if (lastValue == value) {
			return;
		}
		// 保存最后一次
		lastValue = value;
		var nodes = tree.getNodes();
		// 如果要查空字串，就退出不查了。
		if (value == "") {
			showAllNode(nodes);
			return;
		}
		hideAllNode(nodes);
		nodeList = tree.getNodesByParamFuzzy(keyType, value);
		updateNodes(nodeList);
	}
	//隐藏所有节点
	function hideAllNode(nodes){			
		nodes = tree.transformToArray(nodes);
		for(var i=nodes.length-1; i>=0; i--) {
			tree.hideNode(nodes[i]);
		}
	}
	//显示所有节点
	function showAllNode(nodes){
		nodes = tree.transformToArray(nodes);
		for(var i=nodes.length-1; i>=0; i--) {
			/* if(!nodes[i].isParent){
				tree.showNode(nodes[i]);
			}else{ */
				if(nodes[i].getParentNode()!=null){
					tree.expandNode(nodes[i],false,false,false,false);
				}else{
					tree.expandNode(nodes[i],true,true,false,false);
				}
				tree.showNode(nodes[i]);
				showAllNode(nodes[i].children);
			/* } */
		}
	}
	//更新节点状态
	function updateNodes(nodeList) {
		tree.showNodes(nodeList);
		for(var i=0, l=nodeList.length; i<l; i++) {
			//展开当前节点的父节点
			tree.showNode(nodeList[i].getParentNode()); 
			//tree.expandNode(nodeList[i].getParentNode(), true, false, false);
			//显示展开符合条件节点的父节点
			while(nodeList[i].getParentNode()!=null){
				tree.expandNode(nodeList[i].getParentNode(), true, false, false);
				nodeList[i] = nodeList[i].getParentNode();
				tree.showNode(nodeList[i].getParentNode());
			}
			//显示根节点
			tree.showNode(nodeList[i].getParentNode());
			//展开根节点
			tree.expandNode(nodeList[i].getParentNode(), true, false, false);
		}
	}
	// 开始搜索
	function search() {
		$("#search").slideToggle(200);
		$("#key").focus();
	}	
//	$(document).click(function(){
//		$(".ztree *").not("span,a").css("display","block");
//	})
</script>
</head>
<body>
	<div class="input-group" style="padding:5px">
		<input  id="key" name="key" type="text" class="form-control search-query" placeholder="请输入关键字查询"/>
		<span class="input-group-btn">
			<button type="button" class="btn-mini" id="tagSelectSearch" onclick="searchNode()">
				搜索
				<i class="icon-search icon-on-right bigger-110"></i>
			</button>
		</span>
	</div>
	<div id="tree" class="ztree" style="padding:15px 20px;"></div>
</body>