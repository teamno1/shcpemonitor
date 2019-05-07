/**
 * 20160706-01 yanjl 创建 系统页面公共js函数
 * 
 */
/*
 * id 显示的页面组件的id showMsg 错误信息 time 显示时间 background 背景显示颜色 side 显示的位置:1,2,3,4
 * 分别代表 上右下左
 * 
 */
$(document).ready(function(){
	//下拉框
	$('.select2').select2();
	$("#header").parent().parent().parent().css("font-family","微软雅黑");
	$("#center").parent().parent().parent().css("font-family","微软雅黑");
	 /*dialog弹出页面body的style*/
	 $("#jump-content").parent(".clearfix").parent("body").css({"font-family":"微软雅黑","background":"#f4f8fb"});
	 /*表格超宽超高出现边框*/
		if($("#footer table").width()>$("#footer").width() && $("#footer table").height()>$("#footer").height()){
			$("#footer").css("border","1px solid #ddd");
			$("#footer").css("border","none");
		}else if($("#footer table").width()>$("#footer").width()){
			$("#footer").css({"border-left":"1px solid #ddd","border-right":"1px solid #ddd"});
		}else if($("#footer table").height()>$("#footer").height()){
			$("#footer").css({"border-bottom":"1px solid #ddd","border-top":"1px solid #ddd"});
		}else{
			$("#footer").css("border","none");
		}
})
function showTips(id, showMsg, time, background, side) {
	if ($("#" + id).length <= 0) { // 组件不存在，弹出提示框
		alert(showMsg);
		return;
	}
	// 显示默认值
	var showTime = 1;
	var showBackground = '#AE81FF';
	var showSide = 3;

	// 如果上送时间
	if (time) {
		showTime = time;
	}

	// 如果上送背景
	if (background) {
		showBackground = background;
	}

	// 如果上送位置
	if (side) {
		showSide = side;
	}

	$("#" + id).tips({
		side : showSide,
		msg : showMsg,
		bg : showBackground,
		time : showTime
	});
}

// 取选中的记录数
function getCheckNum(elementName) {
	var checklist = document.getElementsByName(elementName);
	var checkNum = 0;
	if (!checklist) { // 控件不存在
		return checkNum;
	}

	for ( var i = 0; i < checklist.length; i++) {
		if (checklist[i].checked) {
			checkNum++;
		}
	}

	return checkNum;
}

// 拼接返回选中记录值, 以,分隔
function getCheckStr(elementName) {
	var checklist = document.getElementsByName(elementName);
	var checkStr = "";
	if (getCheckNum(elementName) <= 0) { // 未选择或者控件不存在
		return checkStr;
	}

	for ( var i = 0; i < checklist.length; i++) {
		if (checklist[i].checked) {
			checkStr += checklist[i].value + ",";
		}
	}

	checkStr = checkStr.substring(0, checkStr.length - 1);
	return checkStr;
}

// 根据某个控件实现列表的全选和全不选
function selectAll(checkId, checkBoxName) {
	var checklist = document.getElementsByName(checkBoxName);
	if (document.getElementById(checkId).checked) {
		for ( var i = 0; i < checklist.length; i++) {
			checklist[i].checked = true;
		}
	} else {
		for ( var j = 0; j < checklist.length; j++) {
			checklist[j].checked = false;
		}
	}
	getall(checkBoxName);
}

//点击按钮弹出遮罩层
function modal(ele1){
	$(ele1).show();
}
window.onresize=function(){
	footer();
}
// 获取footer（tab页表格部分的高度及滚动条事件）
footer();
var footerHeight;
function footer(){
	/*alert($("#page-content").parent().parent().innerHeight());
	alert($("#header").innerHeight());
	alert($("#center").innerHeight());
	alert($("#page").innerHeight());*/
	footerHeight=$("#page-content").innerHeight()-$("#header").innerHeight()-$("#center").innerHeight()-$("#page").height()-$("#select-Info").height()-32;
	$("#footer").height(footerHeight);
}

//收起与展开
spread();
var onOff=true;
function spread(){
	$("#spread").click(function(){
		if(onOff==true){
			$("#header .form-group:not(:first)").css("display","none");
			$(this).html('展开');
			footer();
			onOff=false;
		}else{
			$("#header .form-group:not(:first)").css("display","block");
			$(this).html('收起');
			footer();
			onOff=true;
		}
	})
}	



/**
 * 为表单动态添加隐藏域
 * 
 * @param formName
 * @param elementName
 * @param elementVu
 */
function dynamicHiddenElement(formName, elementName, elementVu) {
	if (elementVu == 'undefine') {
		elementVu = '';
	}

	var pNode = document.all(formName);
	var isExists = false;
	var ele = null;
	for ( var i = 0; i < pNode.elements.length; i++) {
		if (pNode[i].name == elementName) {
			isExists = true;
			ele = pNode[i];
			break;
		}
	}

	if (isExists) {
		ele.value = elementVu;
	} else {
		var objNewNode = document.createElement("INPUT");
		objNewNode.type = "hidden";
		objNewNode.name = elementName;
		objNewNode.value = elementVu;
		pNode.appendChild(objNewNode);
	}

}

// 提交分页请求Form,在下一页，上一页，转到时可以使用
function submitForm(commandName, formObj) {
	var btName = commandName.toLowerCase();
	if (btName == "previous") {
		// 上一页
	} else if (btName == "next") {
	} else if (btName == "first") {
	} else if (btName == "last") {
	} else if (btName == "directpage") {
//		formObj['page.directPage'].value = formObj['directPage'].value;
	} else {
		alert("未定义按钮类型");
		return false;
	}
	formObj['pageCommand'].value = btName;
	var elements = formObj.elements;
	for ( var i = 0; i < elements.length; i++) {
		var tp = elements[i].type.toLowerCase();
		if (tp == "submit" || tp == "button" || tp == "select")
			elements[i].disabled = true;
	}

	formObj.submit();
}
/*禁止复选框出现多选现象*/
function radioStyle(obj,name){
	if($(obj).attr("checked")!=undefined){	
		$("input[name='"+name+"']").each(function () {
			$(this).attr("checked",false);
		});
		$(obj).attr("checked",true);
	}
}
/**
 * 获取当前页面所有选中的复选框,并且统计
 */
function getall(checkName){	
	//选中条数
	var selected=0;
	//复选框信息
	var checkboxs = document.getElementsByName(checkName);	
	//总金额
	var totalMoney=0;
	var s=0;
	var orderids=[];
	for(var i=0;i<checkboxs.length;i++){
		if(checkboxs[i].checked){
			orderids[s++]=checkboxs[i].value;
			selected++;
			//totalmoney=totalmoney+parseFloat(checkboxs[i].getAttribute("price"));
			totalMoney=Math_TwoFloatNumber_1(totalMoney,parseFloat(checkboxs[i].getAttribute("price")),"+");
		}
	}
	var rs= fmoney(totalMoney,2);
	document.getElementById("selectInfo").innerHTML="<center>已经选择<font style='color:red'>"+selected+"</font>条,总金额<font style='color:red'>"+rs+"</font>元</center>";		
}
//转换当前值为金钱格式
function fmoney(s, n)   
{   
   n = n > 0 && n <= 20 ? n : 2;   
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse(),   
   r = s.split(".")[1];   
   t = "";   
   for(i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("") + "." + r;   
}
//此法解决 两个浮点数相加精度不准的问题
function Math_TwoFloatNumber_1(float1,float2,operate) {  
var TotalNum;      
float1 = float1 + '';      
var sp_f1 = float1.split(".") ;      
      
float2 = float2 + '' ;      
var sp_f2 = float2.split(".") ; 
if(operate=='+'){ 
    if ((sp_f1.length==2) && (sp_f2.length==2)){      
        //---两个数字都有小数---      
        TotalNum = TotalNum + 0 ;      
        TotalNum = parseFloat(sp_f1[0])+ parseFloat(sp_f2[0]) ;  //整数部分      
        var length1 = sp_f1[1].length; //小数点后的长度     
        var length2 = sp_f2[1].length; //小数点后的长度     
        var length;      
        if(length1>=length2){      
            length = length1;      
            sp_f2[1] = sp_f2[1]*Math.pow(10,length1 - length2); //返回第一个参数的第二个参数次幂的值。 转换成位数相同 eg(0.123,0.23后面123 与23  123 与 230)
        }else if(length1<length2){      
            length = length2;      
            sp_f1[1] = sp_f1[1]*Math.pow(10,length2 - length1); //同上    
        }      
        var temp_second_part = Number(sp_f1[1]) + Number(sp_f2[1]);      
        temp_second_part = temp_second_part/Math.pow(10,length);  //还原成小数0.***形式    
        TotalNum = parseFloat(TotalNum) + parseFloat(temp_second_part);      
    }else {      
        TotalNum = parseFloat(float1) + parseFloat(float2) ;      
    } 
}else if(operate=='-'){
  if((sp_f1.length==1) && (sp_f2.length==1)){//不带小数
	  	TotalNum= parseFloat(float1)- parseFloat(float2); 
	  }else if ((sp_f1.length==2) && (sp_f2.length==2)){//都带小数
	  	 	TotalNum = parseFloat(sp_f1[0])-parseFloat(sp_f2[0]);//整数部分
	  	  	var length1 = sp_f1[1].length; //小数点后的长度     
        var length2 = sp_f2[1].length; //小数点后的长度     
        var length;
        var flow;      
        if(length1>=length2){      
            length = length1;      
            sp_f2[1] = sp_f2[1]*Math.pow(10,length1 - length2); 
        }else if(length1<length2){      
            length = length2;      
            sp_f1[1] = sp_f1[1]*Math.pow(10,length2 - length1); 
        }
        if(sp_f1[1]>sp_f2[1]){
        	flow='+';
        }else{
        	flow='-';
        }
        var temp_second_part = Math.abs(Number(sp_f1[1])-Number(sp_f2[1]));
        temp_second_part = temp_second_part/Math.pow(10,length); 
        if(flow=='+'){
        	 TotalNum =parseFloat(TotalNum) + parseFloat(temp_second_part);  
        }else{
        	TotalNum =Number(TotalNum) - Number(1); 
        	TotalNum = parseFloat(TotalNum) + parseFloat(temp_second_part); 
        }
	  }else{//某一个带小数
	  	TotalNum= parseFloat(float1)- parseFloat(float2); 
}  
}   
//toFixed(2)  四舍五入保留小数点后边两位小数。
return TotalNum.toFixed(2);      
}
