package com.humbuckers.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.humbuckers.dto.ActivityMainDTO;
import com.humbuckers.dto.ActivitySubTypeDTO;
import com.humbuckers.dto.ActivityTypeDTO;
import com.humbuckers.dto.ProjectDTO;
import com.humbuckers.dto.UsersDTO;
import com.humbuckers.utils.AbstractRestTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@Scope("session")
public class ProjectBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;

	private ProjectDTO project;
	private List<ActivitySubTypeDTO>activitysubtypeList;

	private List<ActivityTypeDTO>activitytypeList;

	private List<ActivityMainDTO>activitymainList;

	private List<ActivityTypeDTO> selectedActivity;
	
	private List<ProjectDTO>projectList;

	@PostConstruct
	public void init() {
		activitysubtypeList=new ArrayList<ActivitySubTypeDTO>();
		activitysubtypeList=fetchAllActivtySubtype();

		activitytypeList=new ArrayList<ActivityTypeDTO>();
		activitytypeList=fetchAllActivtytype();

		activitymainList=new ArrayList<ActivityMainDTO>();
		activitymainList=fetchAllActivtyMain();

		fetchTypeByMainActivity();
		
		project=new ProjectDTO();
		projectList=new ArrayList<ProjectDTO>(); 
		fetchAllProjects();
	}

	@SuppressWarnings("unchecked")
	public List<ActivitySubTypeDTO> fetchAllActivtySubtype(){
		List<ActivitySubTypeDTO> list = AbstractRestTemplate.restServiceForList("/activity/fetchAllActivitySubType");
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ActivityTypeDTO> fetchAllActivtytype(){
		List<ActivityTypeDTO> list = AbstractRestTemplate.restServiceForList("/activity/fetchAllActivityType");
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ActivityMainDTO> fetchAllActivtyMain(){
		List<ActivityMainDTO> list = AbstractRestTemplate.restServiceForList("/activity/fetchAllMainActivity");
		return list;
	}


	public void fetchTypeByMainActivity(){
		List<ActivityMainDTO>list=new ArrayList<>();
		if(activitymainList!=null && activitymainList.size()>0) {
			for (int j = 0; j < activitymainList.size(); j++) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					Gson gson = new Gson();
					String jsonString = gson.toJson(activitymainList.get(j));
					ActivityMainDTO activityMainDTO=mapper.readValue(jsonString,ActivityMainDTO.class);
					activityMainDTO.setActivityTypeList(AbstractRestTemplate.restServiceForList("/activity/fetchTypeByMainActivity/"+activityMainDTO.getActivityMainId()));
					list.add(activityMainDTO);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			setActivitymainList(new ArrayList<>());
			activitymainList.addAll(list);
			
		}
	}
	
	
	
	public void saveProject() {
		if(this.project!=null) {
			
			try {
				   ObjectMapper mapper = new ObjectMapper();
				   project = mapper.readValue(AbstractRestTemplate.postForObject("/project/saveProject",project),ProjectDTO.class);
				   FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Project saved successfully",
									"Project saved successfully"));
				   project=new ProjectDTO();
			   }
			   catch (Exception e) {
				   e.printStackTrace();
				// TODO: handle exception
			}
			
		}
	}
	
	
	
	public void fetchAllProjects() {
		projectList=AbstractRestTemplate.restServiceForList("/project/fetchAllProjects/");
	}

}
