package com.humbuckers.dto;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityTypeDTO {
	
	private Long activityTypeId;
	private String activityTypeName;
    private ActivityMainDTO activityMain;
    
    private List<ActivitySubTypeDTO> activitySubTypeList;
}



