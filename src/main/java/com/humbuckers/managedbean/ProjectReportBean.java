package com.humbuckers.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.model.timeline.TimelineModel;
import org.springframework.context.annotation.Scope;

import com.humbuckers.dto.ProjectWbsDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@Scope("session")
public class ProjectReportBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;


	private TimelineModel activityModel;
    
	@PostConstruct
	public void init() {
		activityModel = new TimelineModel();
		
	}


	
}
