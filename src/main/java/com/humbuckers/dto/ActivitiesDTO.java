package com.humbuckers.dto;


import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ActivitiesDTO {
	
	private Long activityId;
	private String activityName;
	private Long activityType;
	private Long activityParentId;
	private List<ActivitiesDTO> activityChildList;
	private Date activityPlannedStartDate;
	private Date activityPlannedEndDate;
	private Date activityAcutalStartDate;
	private Date activityActualEndDate;
	private String exist;
	private String checkbox;
	
}



