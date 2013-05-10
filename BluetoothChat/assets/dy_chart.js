var chart;
var step = 0.01;	//step0.008每秒125點	//0.005極限
var xstart = step;
//a = Math.random();
var hr = 15;
var score = 80;
function complete(hr1,score1){
	hr = hr1;
	score = score1;
	view = document.getElementById("dy_chart").innerHTML;
	document.getElementById("dy_chart").innerHTML = view+'<img class="stop" src="button/60.png" style="width:50%; margin: -20% 0% 0% 25%; z-index:2;position: relative;" /><br />'+hr+',<br />'+score;
}

function addSomething(a) {
//	setInterval(function() {        			 // for testing feq
	for (var i in a){
		chart.series[0].addPoint([xstart,a[i]],false,true);
		xstart += step;
	}
	
	/**
	chart.series[0].addPoint([xstart,a],false,true);
	xstart += step;
	**/
	
	chart.redraw();
//	}, 1000);									// for testing feq
}



$(function () {
	$(".stop").click(function() {
	$(".main1").html('  <div id="main" >'+
    	'<div class="left">'+
			'<div class="text">'+
				'<div class="word">Electrocardiagram</div><br />'+
				'<div class="subword">Measuring</div><br />'+
				'<div class="hr">HR <span id="hr"></span></div>'+
				'<div id="date"></div>'+
			'</div>'+
		'</div>'+
		'<div class="mid" id="mid">'+
			'<div class="score">84</div>'+
			'<img class="circle" src="button/circle.png" />'+
		'</div>'+	
		'<div class="right">'+
			'<button class="measure"><img src="button/Measure.png" /></button><br />'+
			'<button class="share" ><img src="button/Share.png" /></button>'+
			'<button class="record"><img src="button/Record.png" /></button>'+
		'</div>'+
	'</div>');
 // 		window.location.href = "complete.html";
	});
    $(document).ready(function() {
        Highcharts.setOptions({
            global: {
                useUTC: false 
            }
        });
    
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'dy_chart',
                backgroundColor: 'transparent',
                type: 'spline',
                marginRight: 10,
            },
            credits: {
            enabled:false
            }
            ,
            title: {
            	style:{ color: '#FFFFFF'},
                text: 'Electrondiagram',
            },
            xAxis: {
                type: 'x Axis',
                style:{ color: '#FFFFFF'},
                tickPixelInterval: 150
            },
            yAxis: {
            	max:1500,
            	min:0,
                title: {
                	style:{ color: '#FFFFFF'},
                    text: 'Value'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                }]
            },
            
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
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
            },
            series: [{
                name: 'Data',
                color: '#FFFFFF',
                data: (function() {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),i;
    
                    for (i = -4; i <= xstart; i+=step) {
                        data.push({
                            x: i,
                            y: 0 
                        });
                    }
                    return data;
                })()
            }]
        });
    });
});
