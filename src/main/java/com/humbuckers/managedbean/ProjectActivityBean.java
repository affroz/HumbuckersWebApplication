package com.humbuckers.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.humbuckers.dto.ActivitiesDTO;
import com.humbuckers.dto.ProjectActivitiesDTO;
import com.humbuckers.dto.ProjectActivitiesViewDTO;
import com.humbuckers.utils.AbstractRestTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@Scope("session")
public class ProjectActivityBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;


	private TreeNode root;
	private TimelineModel basicmodel;
	private TimelineModel rangemodel;
    private boolean selectable = true;
    private boolean zoomable = true;
    private boolean moveable = true;
    private boolean stackEvents = true;
    private String eventStyle = "box";
    private boolean axisOnTop;
    private boolean showCurrentTime = true;
    private boolean showNavigation = false;
    private Date start;  
    private Date end; 
    
    
	@PostConstruct
	public void init() {
		basicmodel = new TimelineModel();
		rangemodel=new TimelineModel();
		
	}

	
	public TreeNode createTreeStucture(Long projectKey)
	{	basicmodel = new TimelineModel();
	    rangemodel=new TimelineModel();
		List<ProjectActivitiesDTO> list=fetchProjectActivitiesMainByProject("/project/fetchMainActivitiesByProject/"+projectKey);
		TreeNode root = new DefaultTreeNode("Activities", null);
		root.setExpanded(true);
		ObjectMapper mapper = new ObjectMapper();
		
		if(list!=null && list.size()>0) {
			for (ProjectActivitiesDTO entity : list) {
				try {
					ProjectActivitiesViewDTO act=new ProjectActivitiesViewDTO();
					ActivitiesDTO dto= mapper.readValue(AbstractRestTemplate.restServiceForObject("/activity/fetchactivity/"+entity.getActivityKey()),ActivitiesDTO.class);
				    act.setName(dto.getActivityName());
				    act.setActivityActualEndDate(entity.getActivityActualEndDate());
				    act.setActivityPlannedStartDate(entity.getActivityPlannedStartDate());
				    act.setActivityPlannedEndDate(entity.getActivityPlannedEndDate());
				    act.setActivityAcutalStartDate(entity.getActivityAcutalStartDate());
				    act.setActivityActualEndDate(entity.getActivityActualEndDate());
				    setStart(entity.getActivityPlannedStartDate());
				    setEnd(entity.getActivityPlannedEndDate());
				    TreeNode parentNode = new DefaultTreeNode(act,root);
				    parentNode.setExpanded(true);
				    test(entity, parentNode);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return root;
	}
	
	public void test(ProjectActivitiesDTO entity,TreeNode node) {
		ObjectMapper mapper = new ObjectMapper();
		List<ProjectActivitiesDTO> subList=fetchProjectActivitiesMainByProject("/project/fetchSubActivitiesByParent/"+entity.getActivityKey());
		for (ProjectActivitiesDTO subentity : subList) {
			ActivitiesDTO subdto;
			try {
				subdto = mapper.readValue(AbstractRestTemplate.restServiceForObject("/activity/fetchactivity/"+subentity.getActivityKey()),ActivitiesDTO.class);
				ProjectActivitiesViewDTO subact=new ProjectActivitiesViewDTO();
				subact.setName(subdto.getActivityName());
				subact.setActivityActualEndDate(subentity.getActivityActualEndDate());
				subact.setActivityPlannedStartDate(subentity.getActivityPlannedStartDate());
				subact.setActivityPlannedEndDate(subentity.getActivityPlannedEndDate());
				subact.setActivityAcutalStartDate(subentity.getActivityAcutalStartDate());
				subact.setActivityActualEndDate(subentity.getActivityActualEndDate());
				
				TreeNode subNode = new DefaultTreeNode(subact,node);
				subNode.setExpanded(true);
				rangemodel.add(new TimelineEvent(subdto.getActivityName(), subentity.getActivityPlannedStartDate(), subentity.getActivityPlannedEndDate(),
						true, subdto.getActivityName(), subdto.getActivityName()));
				basicmodel.add(new TimelineEvent(subdto.getActivityName(), subentity.getActivityPlannedStartDate()));
				if(subdto.getActivityType()!=2) {
					test(subentity, subNode);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public List<ProjectActivitiesDTO> fetchProjectActivitiesMainByProject(String url){
		List<ProjectActivitiesDTO> list=AbstractRestTemplate.restServiceForList(url);
		List<ProjectActivitiesDTO> activityList=fetchAllProjectActivities(list);
		return activityList;
		
	}
	
	public List<ProjectActivitiesDTO> fetchAllProjectActivities(List<ProjectActivitiesDTO> list){
		List<ProjectActivitiesDTO> finalList=new ArrayList<>();
		if(list!=null && list.size()>0) {
			for (int j = 0; j < list.size(); j++) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					Gson gson = new Gson();
					String jsonString = gson.toJson(list.get(j));
					ProjectActivitiesDTO entity=mapper.readValue(jsonString,ProjectActivitiesDTO.class);
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
