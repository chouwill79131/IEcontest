$(document).ready(function() {
	var s = "";
	var theDate = new Date();  
	s  +=  theDate.getYear()+1900 + ".";                //  年  
  	s  +=  (theDate.getMonth() + 1) + ".";				// 月
  	s  +=  theDate.getDate(); 							// 日
	view = document.getElementById("date").innerHTML;
	document.getElementById("date").innerHTML = view+s;
	
	
	var hr=15; 
	span_hr = document.getElementById("hr").innerHTML;
	document.getElementById("hr").innerHTML = span_hr+hr;

   	$(".measure").click(function() {
 	 window.location.href = "chart.html";
	});
	view = document.getElementById("mid").innerHTML;
	
 	$(".share").click(function fb() {
 	sleep(1);
 	
 	 document.getElementById("mid").innerHTML = view+'<img src="button/complete.png" style="width:150%; margin-top:-60%; margin-left:-25% ;" />';
	});
	
	document.getElementById("mid").ontouchstart = function() {
	sleep(0.3);
    	document.getElementById("mid").innerHTML = view;
}
	
	
});

function sleep( seconds ) {
	var timer = new Date();
	var time = timer.getTime();
	do
		timer = new Date();
	while( (timer.getTime() - time) < (seconds * 1000) );
}