/* ie8的placeholder兼容文件*/
 $(function () {
 	$('input, textarea').placeholder();
 	/*表格隔行变色*/
 	$("tr:even").css("background-color","#f2f1f1");//双行颜色
 	$("tr:odd").css("background-color","#fdfdfd");//单行颜色
 });
