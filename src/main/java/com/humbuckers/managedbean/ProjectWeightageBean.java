package com.humbuckers.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.humbuckers.dto.ActivitiesDTO;
import com.humbuckers.dto.ActivityWeightageDTO;
import com.humbuckers.utils.AbstractRestTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@Scope("session")
public class ProjectWeightageBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;


	private List<ActivityWeightageDTO> weightageList;




	@PostConstruct
	public void init() {
		weightageList=new ArrayList<ActivityWeightageDTO>();
	}

	public void calaculateWeightage(Long projectId) {
		weightageList=new ArrayList<ActivityWeightageDTO>();
		weightageList=fetchAllActivityWeightage("");
	}

	public List<ActivityWeightageDTO> fetchAllActivityWeightage(String url){
		List<ActivityWeightageDTO> list=AbstractRestTemplate.fetchObjectList("/activity/fetchAllActivityWeigth",ActivityWeightageDTO.class);
		List<ActivityWeightageDTO> activityList=new ArrayList<ActivityWeightageDTO>();
		if(list!=null && list.size()>0) {
			for (ActivityWeightageDTO activityWeightageDTO : list) {
				ActivitiesDTO dto=(ActivitiesDTO) AbstractRestTemplate.fetchObject("/activity/fetchactivity/"+activityWeightageDTO.getActivityId(), ActivitiesDTO.class);
				activityWeightageDTO.setActivitiesDTO(dto);
				activityList.add(activityWeightageDTO);
			}
		}
		return activityList;

	}

}
