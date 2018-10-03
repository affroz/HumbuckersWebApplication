package com.humbuckers.dto;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ActivityWeightageDTO {
	
	private Long activitiesWeightageId;
	
	private String weightagePercentage;
	
	private Long activityId;
	
	private ActivitiesDTO activitiesDTO;
	
	private String plannedPhysical;
	
	private String actualPhysical;
	
	private String plannedEarned;
	
	private String actualEarned;
	
	private String variance;
	
	private String remark;
	
}



