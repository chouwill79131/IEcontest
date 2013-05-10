var chart;
var step = 0.01;	//step0.008每秒125點	//0.005極限
var xstart = step;
var hr = "--";
var score = "--";
var index;
$(document).ready(function ready() {
	
    var s = "";
    var theDate = new Date();  
    s += theDate.getYear()+1900 + ".";                //  年  
    s += (theDate.getMonth() + 1) + ".";				// 月
    s += theDate.getDate(); 							// 日
    document.getElementById("date").innerHTML = s;
    index = document.getElementById("main").innerHTML; //保留首頁
    
    $(".measure").live("click", function() {
      measure();
    }); //click .measure
    
    
}); //(document).ready

function shared(){
	view1 = document.getElementById("rhythm").innerHTML;
	document.getElementById("rhythm").innerHTML = view1+'<img class="stop" src="button/complete.png" style="width:100%; margin-top: -50% ;z-index:5;position: relative;" />';
	$("#main").live("click", function() {
      document.getElementById("rhythm").innerHTML = view1;
    });
}

function complete(hr1,score1){
  hr = hr1;
  score = score1;
  view = document.getElementById("dy_chart").innerHTML;
  document.getElementById("dy_chart").innerHTML = view+'<img class="stop" src="button/Completed.png" style="width:50%; margin: -20% 0% 0% 25%; z-index:2;position: relative;" />';
  $(".stop").html('<img src="button/Continue.png" />');
}

function addSomething(a) {
  for (var i in a){
    chart.series[0].addPoint([xstart,a[i]],false,true);
    xstart += step;
  }
  chart.redraw();
}

function history(history){
	view2 = document.getElementById("main").innerHTML;
    $("#main").html(
    '<div class="main1">'+
	'<div class="contain">'+
	'<div id="dy_chart" style="z-index:1;position: relative;"></div>'+
	'</div>'+
	'<div id="foot">'+
	'<button class="stop">'+
	'<img src="button/Back.png" />'+
	'</button>'+
	'</div>'+
    '</div>'
    );
      
    Highcharts.setOptions({
      global: {
	useUTC: true 
      }
    });
    
    hischart = new Highcharts.Chart({
      chart: {
		renderTo: 'dy_chart',
		backgroundColor: 'transparent',
		type: 'line',
      },
      title: {
		style:{ color: '#FFFFFF'},
		text: 'Measure history',
      },
      credits: {			// highchart.com
		enabled:false
      },
      legend: {  			// 線的標示
        enabled: false
      },
      xAxis: {
      	type: 'datetime',
		dateTimeLabelFormats: { // don't display the dummy year
	  		month: '%e. %b',
	  		year: '%b'
		},
		lineColor: '#FFFFFF',
		labels: {
	  		style: {
				color: '#FFFFFF',
			}
		}
	  },
	  yAxis: {
	    title: {text: 'Feeling',
	    			style: {
						color: '#FFFFFF',
					}
				},
	  	gridLineColor: '#FFFFFF',
	  	labels: {
	  		style: {
				color: '#FFFFFF',
			}
		}
	  },
	  plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true,
                        color: '#FFFFFF',
                    },
                    enableMouseTracking: true,
                }
      },
	series: [{
	  name: 'measure history',
	  color: '#FFFFFF',
	  data: history 
	}],
    });
    
    $(".stop").live("click", function(){
 //     hr = "--";
      $(".main1").html(view2);
 //     document.getElementById("hr").innerHTML = hr;
    });
};// .record click
    
function measure(){
	xstart = 0.01;
	$("#main").html('<div class="main1">'+
		'<div class="contain">'+
		'<div id="dy_chart" style="z-index:1;position: relative;"></div>'+
		'</div>'+
		'<div id="foot">'+
		'<button class="stop" onclick="window.MyHandler.disconnect()">'+
		'<img src="button/Stop.png" />'+
		'</button>'+
		'</div>'+
      	'</div>');
    Highcharts.setOptions({
      global: {useUTC: true }
    });
    chart = new Highcharts.Chart({
      chart: {
		renderTo: 'dy_chart',
		backgroundColor: 'transparent',
		type: 'spline',
		marginRight: 10,
      },
      credits: {enabled:false},
      title: {
		style:{ color: '#FFFFFF'},
		text: 'Electrondiagram',
      },
      xAxis: {
		type: 'x Axis',
		style:{ color: '#FFFFFF'},
		tickPixelInterval: 100,
		lineColor: '#FFFFFF',
		labels: {style: {color: '#FFFFFF'}},
      },
      yAxis: {
      	title: {
      		text: 'Score',
      		style: {color: '#FFFFFF'}},
	  		gridLineColor: '#FFFFFF',
	  		labels: {style: {color: '#FFFFFF'}},
			//max:1500,
			//min:0,
			title: {
	  			style:{ color: '#FFFFFF'},
	  			text: 'PPG'
	  		},
			plotLines: [{value: 0,width: 1}]
      	},
      	legend: {enabled: false},
      	exporting: {enabled: false},
      	plotOptions: {
			spline: {
	  			lineWidth: 1,
	  			states: {
	    			hover: {
	      				enabled: false,
	      				lineWidth: 2
	    			}
	  			},
	  		marker: {
	    		enabled: false,
	    		states: {
	      			hover: {
						enabled: false,
	      			}
	    		}
	  		},
		}
      }, // yAxis
      series: [{
		name: 'Data',
		color: '#FFFFFF',
		data: (function() {
	  		var data = [];
	  		var step = 0.01;	//step0.008每秒125點	//0.005極限
			var xstart = step;
	  		for (var i = -4; i <= xstart; i+=step){data.push({x: i,y: 0 });}
	  		return data;
		})()
		}]
    }); //Highcharts
    
    hr = '--';
    score = '--';
    $(".stop").live("click",function() {
      $(".main1").html(index);
      document.getElementById("hr").innerHTML = hr;
      document.getElementById("rhythm").innerHTML = '<div id="score">'+score+'</div><img class="circle" src="button/circle.png" />';
    }); //click .stop
}
    


