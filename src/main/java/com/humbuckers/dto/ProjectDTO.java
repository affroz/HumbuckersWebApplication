package com.humbuckers.dto;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {
	
	private Long projectId;
	private String projectName;
	private String projectClient;
	private String projectMainContractor;
	private String projectSubContractor;
	private String projectMepSubContractor;
	private String projectInteriorDesigner;
	private Date projectActualEndDate;
	private Date projectPlannedStartDate;
	private Date projectPlannedEndDate;
	private Date projectAcutalStartDate;
	private String projectDuration;
	private int totalActivities;
	private int totalWbs;
	
	
	public String getProjectDuration() {
		if(projectPlannedEndDate!=null && projectPlannedStartDate!=null) {
			Long days=(projectPlannedEndDate.getTime() - projectPlannedStartDate.getTime()) / (1000 * 60 * 60 * 24);
			setProjectDuration(days.toString());
		}
		return projectDuration;
	}
	
	
	
	
}



