package com.humbuckers.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitySubTypeDTO {
	private Long activityTypeId;
	private String activityTypeName;
    private ActivityTypeDTO activityType;
}



