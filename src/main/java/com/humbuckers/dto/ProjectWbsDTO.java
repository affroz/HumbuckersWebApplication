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
	private String style;
	private String plannedPhysical;
	private String actualPhysical;
	private String plannedEarned;
	private String actualEarned;
	private String variance;
	private Long mainParentKey;
	private String plancal;
	private String actualcal;
	private String actualPercent;
	private String plannedPercent;
	
	@Override
	public String toString() {
		return activityName;
	}

	public String getVariance() {
		if(actualEarned!=null && plannedEarned!=null) {
			float varf= Float.valueOf(actualEarned)-Float.valueOf(plannedEarned);
			setVariance(String.format("%.02f", varf));
		}
		return variance;
	}

	
	public String getPlancal() {
		if(weightage!=null && plannedEarned!=null) {
			float plancalf= ((Float.valueOf(weightage)*(Float.valueOf(plannedEarned)))/ 100.0f);
			setPlancal(String.format("%.02f", plancalf));
		}
		return plancal;
	}

	public String getActualcal() {
		if(weightage!=null && actualEarned!=null) {
			float actualcalf= ((Float.valueOf(weightage)*(Float.valueOf(actualEarned)))/ 100.0f);
			setActualcal(String.format("%.02f", actualcalf));
		}
		return actualcal;
	}

	public String getPlannedEarned() {
		if(plannedEarned!=null) {
			float plannedEarnedf= Float.valueOf(plannedEarned);
			setPlannedEarned(String.format("%.02f", plannedEarnedf));
		}
		return plannedEarned;
	}
	

	public String getActualEarned() {
		if(actualEarned!=null) {
			float actualEarnedf= Float.valueOf(actualEarned);
			setActualEarned(String.format("%.02f", actualEarnedf));
		}
		
		return actualEarned;
	}

	

	
	
	
	
}



