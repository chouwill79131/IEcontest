$(document).ready(function() {
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