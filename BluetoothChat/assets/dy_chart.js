var chart;
var step = 0.01;	//step0.008每秒125點	//0.005極限
var xstart = step;
//a = Math.random();
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

$(".measure").click(function() {
  //alert("aa");
  window.location.href = "chart.html";
});

$(function () {
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
            	max:5000,
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
