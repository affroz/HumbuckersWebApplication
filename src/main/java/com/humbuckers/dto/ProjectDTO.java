package com.humbuckers.dto;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {
	
	private Long activityMainId;
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
	
}



