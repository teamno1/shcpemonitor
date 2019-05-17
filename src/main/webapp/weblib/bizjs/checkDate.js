/*datepicker使用*/ 
/*$.datepicker.setDefaults({  showButtonPanel: true,   beforeShow: function (input, inst) { 
	setTimeout(function() {  
	      var buttonPane = $( input )  
	        .datepicker( "widget" )  
	        .find( ".ui-datepicker-buttonpane" );  
	        
	      $( "<button>", {  
	        text: "清空",  
	        click: function() {  
	          $.datepicker._clearDate( input );    
		      $("#ui-datepicker-div").css("display","block");
	        }  
	      }).appendTo( buttonPane );
	    }, 1 ); 
} });
$('.ui-datepicker-current').live('click', function() {
	$(".clear").appendTo(".ui-datepicker-buttonpane");
});
*/
function getDateObject(obj){
	$("#"+obj).datepicker({
    	dateFormat: 'yy-mm-dd',
    	showOn: "button",
	    buttonImage: "weblib/assets/images/gallery/calender.png",
	    buttonImageOnly: true,
	    changeYear :true,
	    changeMonth :true,
	    showMonthAfterYear: true,
	    altField:"#"+obj,
	    constrainInput :false,
	    clearText:"清除",//清除日期的按钮名称  
        closeText:"关闭",//关闭选择框的按钮名称  
        currentText: '今天'
    });
}
/*日期文本框校验*/
function checkDate(obj){
	$("#"+obj).keyup(function(){
		var str=$("#"+obj).val();
		var reg=/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/;
		if(!reg.test(str) || str.length>10){
			$("#" + obj).tips({
				side : 3,
				msg : "请输入有效的日期 (YYYY-MM-DD)",
				bg : "#AE81FF",
				time : 1
			})
		}
	})
}
/*function checkValid(newObj){
	var newStr=$("#"+newObj).val();
	alert(newStr)
	var newReg=/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/;
	if(!newReg.test(newStr) || newStr.length>10){
		$("#" + newObj).tips({
			side : 3,
			msg : "请输入YYYY-MM-DD格式的日期",
			bg : "#AE81FF",
			time : 1
		})
	}
	return true;
}*/
