PrimeFaces.widget.HumbuckersChart = PrimeFaces.widget.BaseWidget.extend({

	init: function (cfg) {

		var type = $('#'+cfg.id).attr("type")
		var animationEnabled = $('#'+cfg.id).attr("animationEnabled");
		var chartTitle = $('#'+cfg.id).attr("chartTitle");
		var yAxisTitle = $('#'+cfg.id).attr("yAxisTitle");
		var yAxisSuffix = $('#'+cfg.id).attr("yAxisSuffix");
		var yAxisIncludeZero = $('#'+cfg.id).attr("yAxisIncludeZero");
		var xAxisTitle = $('#'+cfg.id).attr("xAxisTitle");
		var options;
		if(type==="column"){
			var list =JSON.parse($('#'+cfg.id).attr("list"));
			options = {
					animationEnabled: animationEnabled,
					theme: "light1",
					exportEnabled: true,
					title: {
						text: chartTitle
					},
					axisY: {
						title:yAxisTitle ,
						suffix: yAxisSuffix,
						includeZero: yAxisIncludeZero
					},
					axisX: {
						title: xAxisTitle
					},
					data: [{
						type: type,
						yValueFormatString: "#,##0.0#"%"",
						dataPoints: list
					}]
			};
		}
		else if(type==="combined"){
			var list1 =JSON.parse($('#'+cfg.id).attr("list1"));
			alert(list1);
			
			var list2 =JSON.parse($('#'+cfg.id).attr("list2"));
			
			var list3 =JSON.parse($('#'+cfg.id).attr("list3"));
			
			var list4 =JSON.parse($('#'+cfg.id).attr("list4"));
			options = {
					animationEnabled: true,
					theme: "light2",
					title: {
						text: chartTitle
					},
					axisX: {
						valueFormatString: "DD MMM"
					},
					axisY: {
					},
					toolTip: {
						shared: true
					},
					legend: {
						cursor: "pointer",
						itemclick: toggleDataSeries
					},
					data: [
						{
							type: "column",
							name: "Plan Weekly",
							showInLegend: true,
							xValueFormatString: "",
							yValueFormatString: "$#,##0",
							dataPoints: list1
						}, {
							type: "column",
							name: "Actual Plan",
							showInLegend: true,
							xValueFormatString: "",
							yValueFormatString: "$#,##0",
							dataPoints:list2
						},
						{
							type: "line",
							name: "Plan Cum",
							showInLegend: true,
							yValueFormatString: "$#,##0",
							dataPoints:list3
						},
						{
							type: "line",
							name: "Actual Cum",
							showInLegend: true,
							yValueFormatString: "$#,##0",
							dataPoints: list4
						}]
			};

		}
		else if(type==="pie"){
			options = {
					title: {
						text: chartTitle
					},
					subtitles: [{
						text: "As of November, 2017"
					}],
					animationEnabled: animationEnabled,
					data: [{
						type: type,
						startAngle: 40,
						toolTipContent: "<b>{label}</b>: {y}%",
						showInLegend: "true",
						legendText: "{label}",
						indexLabelFontSize: 16,
						indexLabel: "{label} - {y}%",
						dataPoints: list
					}]
			};
		}
		else if(type==="rangeBar"){
			options = {
					animationEnabled: true,
					title: {
						text: "GDP Growth Rate - 2016"
					},
					axisY: {
						title: "Growth Rate (in %)",
						suffix: "%",
						includeZero: false
					},
					axisX: {
						title: "Countries"
					},
					data: [{
						type: "rangeBar",
						showInLegend: true,
						yValueFormatString: "$#0.#K",
						indexLabel: "{y[#index]}",
						legendText: "Department wise Min and Max Salary",
						toolTipContent: "<b>{label}</b>: {y[0]} to {y[1]}",
						dataPoints: [
							{ x: 10, y:[105, 160], label: "Data Scientist" },
							{ x: 20, y:[95, 146], label: "Product Manager" },
							{ x: 30, y:[87, 115], label: "Web Developer" },
							{ x: 40, y:[90, 130], label: "Software Engineer" },
							{ x: 50, y:[100,152], label: "Quality Assurance" }
							]
					}]
			};

		}
		

		$('#'+cfg.id).CanvasJSChart(options);
	}
});



function toggleDataSeries(e) {
	if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		e.dataSeries.visible = false;
	} else {
		e.dataSeries.visible = true;
	}
	e.chart.render();
}