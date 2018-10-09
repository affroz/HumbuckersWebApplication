package com.humbuckers.dto;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProjectWbsDTO {
	
	private Long activityId;
	private String activityName;
	private Long parentKey;
	private Date activityPlannedStartDate;
	private Date activityPlannedEndDate;
	private Date activityAcutalStartDate;
	private Date activityActualEndDate;
	private String weightage;
	private String noOfDays;
	private String remark;
	private Long activityCode;
	private String wbsname;
	private Long projectKey;
	@Override
	public String toString() {
		return activityName;
	}
	
	
	
}



