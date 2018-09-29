package com.humbuckers.dto;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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



