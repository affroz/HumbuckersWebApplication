package com.humbuckers.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitySubTypeDTO {
	private Long activityTypeId;
	private String activityTypeName;
    private ActivityTypeDTO activityType;
}



