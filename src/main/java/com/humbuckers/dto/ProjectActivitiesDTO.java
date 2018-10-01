package com.humbuckers.dto;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectActivitiesDTO {
	
	private ProjectDTO project;
	private ActivitiesDTO activities;
	private Long projectActivityId;
	private Date activityPlannedStartDate;
	private Date activityPlannedEndDate;
	private Date activityAcutalStartDate;
	private Date activityActualEndDate;
	private String remark;
}


