package com.humbuckers.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.humbuckers.dto.ActivitiesDTO;
import com.humbuckers.dto.ActivityWeightageDTO;
import com.humbuckers.dto.ProjectActivitiesDTO;
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
		weightageList=fetchAllActivityWeightage("/activity/fetchAllActivityWeigth");







	}



	public List<ActivityWeightageDTO> fetchAllActivityWeightage(String url){
		List<ActivityWeightageDTO> list=AbstractRestTemplate.restServiceForList(url);
		List<ActivityWeightageDTO> activityList=fetchActivityParent(fetchAllActivity(list));
		return activityList;

	}

	private List<ActivityWeightageDTO> fetchActivityParent(List<ActivityWeightageDTO> activityList) {
		List<ActivityWeightageDTO> list=new ArrayList<ActivityWeightageDTO>();
		ObjectMapper mapper = new ObjectMapper();
		if(activityList!=null && activityList.size()>0) {
			for (ActivityWeightageDTO activityWeightageDTO : activityList) {
				try {
					ActivitiesDTO dto= mapper.readValue(AbstractRestTemplate.restServiceForObject("/activity/fetchactivity/"+activityWeightageDTO.getActivityId()),ActivitiesDTO.class);
					activityWeightageDTO.setActivitiesDTO(dto);
					list.add(activityWeightageDTO);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return list;
	}


	public List<ActivityWeightageDTO> fetchAllActivity(List<ActivityWeightageDTO> list){
		List<ActivityWeightageDTO> finalList=new ArrayList<>();
		if(list!=null && list.size()>0) {
			for (int j = 0; j < list.size(); j++) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					Gson gson = new Gson();
					String jsonString = gson.toJson(list.get(j));
					ActivityWeightageDTO entity=mapper.readValue(jsonString,ActivityWeightageDTO.class);
					finalList.add(entity);
				} catch (IOException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Something went wrong!!",
									"Something went wrong!!"));
				}
			}
		}

		return finalList;
	}


}
