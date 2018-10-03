package com.humbuckers.managedbean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.model.timeline.TimelineModel;
import org.springframework.context.annotation.Scope;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@Scope("session")
public class ProjectReportBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;


	private TimelineModel basicmodel;
	private TimelineModel rangemodel;
    private boolean selectable = true;
    private boolean zoomable = true;
    private boolean moveable = true;
    private boolean stackEvents = true;
    private String eventStyle = "box";
    private boolean axisOnTop;
    private boolean showCurrentTime = true;
    private boolean showNavigation = false;
    private Date start;  
    private Date end; 
    
    
	@PostConstruct
	public void init() {
		basicmodel = new TimelineModel();
		rangemodel=new TimelineModel();
		
	}

	
	


	
}
