package com.humbuckers.dto;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectActivitiesViewDTO {
	
	private Date activityPlannedStartDate;
	private Date activityPlannedEndDate;
	private Date activityAcutalStartDate;
	private Date activityActualEndDate;
	private String remark;
	private String name;
}



