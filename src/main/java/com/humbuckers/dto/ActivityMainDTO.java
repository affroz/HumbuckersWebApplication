package com.humbuckers.dto;


import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ActivityMainDTO {
	
	private Long activityMainId;
	private String activityMainName;
	
    private List<ActivityTypeDTO> activityTypeList;
}



