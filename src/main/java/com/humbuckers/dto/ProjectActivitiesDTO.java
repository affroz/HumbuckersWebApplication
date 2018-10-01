package com.humbuckers.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectActivitiesDTO {
	
	private ProjectDTO project;
	private ActivitiesDTO activities;
	private Long projectActivityId;
}



