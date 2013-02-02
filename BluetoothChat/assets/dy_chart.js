var chart;
var step = 0.008;	//step0.008每秒125點	//0.005極限
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
                type: 'spline',
                marginRight: 10,
            },
            title: {
                text: 'Live data'
            },
            xAxis: {
                type: 'x Axis',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
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
                data: (function() {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),i;
    
                    for (i = -6.2; i <= xstart; i+=step) {
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
