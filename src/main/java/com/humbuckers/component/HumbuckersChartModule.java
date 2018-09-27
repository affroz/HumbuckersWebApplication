package com.humbuckers.component;

public class HumbuckersChartModule {

	private String labelDataPoint;
	private Double xDataPoint;
	private Double yDataPoint;
	private Double[] arrayXDataPoint;
	private Double[] arrayYDataPoint;
	
	public String getLabelDataPoint() {
		return labelDataPoint;
	}
	public void setLabelDataPoint(String labelDataPoint) {
		this.labelDataPoint = labelDataPoint;
	}
	public Double getxDataPoint() {
		return xDataPoint;
	}
	public void setxDataPoint(Double xDataPoint) {
		this.xDataPoint = xDataPoint;
	}
	public Double getyDataPoint() {
		return yDataPoint;
	}
	public void setyDataPoint(Double yDataPoint) {
		this.yDataPoint = yDataPoint;
	}
	public Double[] getArrayXDataPoint() {
		return arrayXDataPoint;
	}
	public void setArrayXDataPoint(Double[] arrayXDataPoint) {
		this.arrayXDataPoint = arrayXDataPoint;
	}
	public Double[] getArrayYDataPoint() {
		return arrayYDataPoint;
	}
	public void setArrayYDataPoint(Double[] arrayYDataPoint) {
		this.arrayYDataPoint = arrayYDataPoint;
	}
	public HumbuckersChartModule(String labelDataPoint,Double xDataPoint,Double yDataPoint,Double[] arrayXDataPoint,Double[] arrayYDataPoint) {
		this.labelDataPoint = labelDataPoint;
		this.xDataPoint = xDataPoint;
		this.yDataPoint = yDataPoint;
		this.arrayXDataPoint = arrayXDataPoint;
		this.arrayYDataPoint = arrayYDataPoint;
	}
	
	
}
