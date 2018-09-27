package com.humbuckers.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.humbuckers.component.HumbuckersChartModule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@Scope("session")
public class DashboardBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	private List<HumbuckersChartModule> columnchart;
	private List<HumbuckersChartModule> piechart;

	@PostConstruct
	public void init() {
		columnchart=new ArrayList<HumbuckersChartModule>();
		piechart=new ArrayList<HumbuckersChartModule>();
		
		HumbuckersChartModule one=new HumbuckersChartModule("UAE",1.00,20.00,null,null);
		HumbuckersChartModule two=new HumbuckersChartModule("INDIA",2.00,30.00,null,null);
		HumbuckersChartModule three=new HumbuckersChartModule("OTHER",3.00,10.00,null,null);
		
		columnchart.add(one);
		columnchart.add(two);
		columnchart.add(three);
		
		
		
		
		
		
		HumbuckersChartModule one1=new HumbuckersChartModule("UAE",null,20.00,null,null);
		HumbuckersChartModule two2=new HumbuckersChartModule("INDIA",null,30.00,null,null);
		HumbuckersChartModule three3=new HumbuckersChartModule("OTHER",null,50.00,null,null);
		
		piechart.add(one1);
		piechart.add(two2);
		piechart.add(three3);
	}
	
	
	
}
