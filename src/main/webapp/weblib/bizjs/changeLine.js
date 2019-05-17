window.onload=function(){
	var inputs=document.getElementById("header").getElementsByTagName("input"); 
	 document.getElementById("header").onkeydown=function(event){ 
		 var focus=document.activeElement; 
		 if(!document.getElementById("header").contains(focus)) return; 
		 var event=window.event||event;
		 var key=event.keyCode; 
		 for(var i=0; i<inputs.length; i++){ 
			 if(inputs[i]===focus) break; 
		 } 
		 switch(key){ 
			  case 37:  /*左*/
			   if(i>0) inputs[i-1].focus(); 
			   break; 
			  case 38:  /*上*/
			   if(i-2>=0) inputs[i-2].focus(); 
			   break; 
			  case 39: /*右*/
			   if(i<inputs.length-1) inputs[i+1].focus(); 
			   break; 
			  case 40:  /*下*/
			   if(i+2 <inputs.length) inputs[i+2].focus(); 
			   break; 
		 } 
	} 
}
