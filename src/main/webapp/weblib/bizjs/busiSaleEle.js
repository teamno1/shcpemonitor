function checkType(obj1,obj2){
	if(obj1=='001003001'){
		$("#saleType").val('系统内卖断');
		$(".outer").hide();
		$(".inner").show();
		$(".rebuyDt").hide();
		$("#footer").height($("#footer").height() + 79);
	}else if(obj1=='001003002'){
		$("#saleType").val('同业间卖断');
		$(".outer").show();
		$(".inner").hide();
		$(".rebuyDt").hide();
		$("#footer").height($("#footer").height() + 39);
	}else if(obj1=='001003003'){
	 	$("#saleType").val('再贴现卖断');
	 	$(".outer").show();
		$(".inner").hide();
		$(".rebuyDt").hide();
		$("#footer").height($("#footer").height() + 39);
	}else if(obj1=='001003004'){
		$("#saleType").val('系统内回购式卖出');
		$(".outer").hide();
		$(".inner").show();
		$(".rebuyDt").show();
		$("#footer").height($("#footer").height() + 79);
	}else if(obj1=='001003005'){
		$("#saleType").val('同业间回购式卖出');
		$(".outer").show();
		$(".inner").hide();
		$(".rebuyDt").show();
		$("#footer").height($("#footer").height() + 39);
	}else if(obj1=='001003006'){
	 	$("#saleType").val('回购式再贴现');
	 	$(".outer").show();
		$(".inner").hide();
		$(".rebuyDt").show();
		$("#footer").height($("#footer").height() + 39);
	}
	if(obj2=='1'){
		$("#inAcctType").val('结算账户');
	}else if(obj2=='2'){
		$("#inAcctType").val('内部账户');
	}else if(obj2=='3'){
		$("#inAcctType").val('影子账户');
	}
}