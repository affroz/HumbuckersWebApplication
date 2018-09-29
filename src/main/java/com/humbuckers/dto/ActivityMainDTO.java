package com.humbuckers.dto;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ActivityMainDTO {
	
	private Long activityMainId;
	private String activityMainName;
	
    private List<ActivityTypeDTO> activityTypeList;
}



