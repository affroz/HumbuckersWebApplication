package com.humbuckers.dto;


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
	
}



