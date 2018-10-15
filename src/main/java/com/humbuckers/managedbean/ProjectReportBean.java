package com.humbuckers.managedbean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.model.timeline.TimelineModel;
import org.springframework.context.annotation.Scope;

import com.humbuckers.component.HumbuckersChartModule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@Scope("session")
public class ProjectReportBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;


	private TimelineModel activityModel;
	private List<HumbuckersChartModule> columnchart;
	private List<HumbuckersChartModule> combinedChart;
	
	private List<HumbuckersChartModule> list1;
	private List<HumbuckersChartModule> list2;
	private List<HumbuckersChartModule> list3;
	private List<HumbuckersChartModule> list4;
    
	@PostConstruct
	public void init() {
		activityModel = new TimelineModel();
		columnchart=new ArrayList<HumbuckersChartModule>();
		combinedChart=new ArrayList<HumbuckersChartModule>();
		
		list1=new ArrayList<HumbuckersChartModule>();
		list2=new ArrayList<HumbuckersChartModule>();
		list3=new ArrayList<HumbuckersChartModule>();
		list4=new ArrayList<HumbuckersChartModule>();
		
		plotCombinedList1(date(1), 30000.00);
		plotCombinedList1(date(2), 40000.00);
		plotCombinedList1(date(3), 10000.00);
		plotCombinedList1(date(4), 50000.00);
		plotCombinedList1(date(5), 70000.00);
		plotCombinedList1(date(6), 20000.00);
		plotCombinedList1(date(7), 35000.00);
		plotCombinedList1(date(8), 20000.00);
		plotCombinedList1(date(9), 36000.00);
		plotCombined(list1);
		
		plotCombinedList2(date(1), 20000.00);
		plotCombinedList2(date(2), 30000.00);
		plotCombinedList2(date(3), 20000.00);
		plotCombinedList2(date(4), 40000.00);
		plotCombinedList2(date(5), 60000.00);
		plotCombinedList2(date(6), 22000.00);
		plotCombinedList2(date(7), 35000.00);
		plotCombinedList2(date(8), 10000.00);
		plotCombinedList2(date(9), 30000.00);
		plotCombined(list2);
		
		plotCombinedList3(date(1), 10000.00);
		plotCombinedList3(date(2), 20000.00);
		plotCombinedList3(date(3), 30000.00);
		plotCombinedList3(date(4), 50000.00);
		plotCombinedList3(date(5), 40000.00);
		plotCombinedList3(date(6), 10000.00);
		plotCombinedList3(date(7), 35000.00);
		plotCombinedList3(date(8), 10000.00);
		plotCombinedList3(date(9), 36000.00);
		plotCombined(list3);
		
		plotCombinedList4(date(1), 30000.00);
		plotCombinedList4(date(2), 20000.00);
		plotCombinedList4(date(3), 40000.00);
		plotCombinedList4(date(4), 20000.00);
		plotCombinedList4(date(5), 20000.00);
		plotCombinedList4(date(6), 60000.00);
		plotCombinedList4(date(7), 45000.00);
		plotCombinedList4(date(8), 10000.00);
		plotCombinedList4(date(9), 16000.00);
		plotCombined(list4);
				
	}
	
	private String date(int day) {
	    Date date=new Date(2016, 0, day);
		return new SimpleDateFormat("dd-MMM-yyyy").format(date);
		
	}
	
	public void plotBarChart(String startName, Double startPoint, Double endPoint) {
		HumbuckersChartModule chart=new HumbuckersChartModule(startName,startPoint,endPoint,null,null);
		columnchart.add(chart);
	}
	
	public void plotCombinedList1(String xDate, Double yData) {
		HumbuckersChartModule chart=new HumbuckersChartModule(xDate,yData);
		list1.add(chart);
	}

	public void plotCombinedList2(String xDate, Double yData) {
		HumbuckersChartModule chart=new HumbuckersChartModule(xDate,yData);
		list2.add(chart);
	}
	public void plotCombinedList3(String xDate, Double yData) {
		HumbuckersChartModule chart=new HumbuckersChartModule(xDate,yData);
		list3.add(chart);
	}
	public void plotCombinedList4(String xDate, Double yData) {
		HumbuckersChartModule chart=new HumbuckersChartModule(xDate,yData);
		list4.add(chart);
	}
	
	
	public void plotCombined(List<HumbuckersChartModule> list) {
		HumbuckersChartModule chart=new HumbuckersChartModule(list);
		combinedChart.add(chart);
	}
}
