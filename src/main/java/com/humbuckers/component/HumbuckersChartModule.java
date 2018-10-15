package com.humbuckers.component;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HumbuckersChartModule {

	private String labelDataPoint;
	private Double xDataPoint;
	private Double yDataPoint;
	private Double[] arrayXDataPoint;
	private Double[] arrayYDataPoint;
	private String xDate;
	private List<HumbuckersChartModule> list;
	
	
	public HumbuckersChartModule(String labelDataPoint,Double xDataPoint,Double yDataPoint,Double[] arrayXDataPoint,Double[] arrayYDataPoint) {
		this.labelDataPoint = labelDataPoint;
		this.xDataPoint = xDataPoint;
		this.yDataPoint = yDataPoint;
		this.arrayXDataPoint = arrayXDataPoint;
		this.arrayYDataPoint = arrayYDataPoint;
	}
	
	public HumbuckersChartModule(String xDate,Double yDataPoint) {
		this.xDate = xDate;
		this.yDataPoint = yDataPoint;
	}
	
	public HumbuckersChartModule(List<HumbuckersChartModule> list) {
		this.list = list;
	}
}
