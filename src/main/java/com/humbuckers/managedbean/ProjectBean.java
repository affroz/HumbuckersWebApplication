package com.humbuckers.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humbuckers.dto.ActivitiesDTO;
import com.humbuckers.dto.ProjectActivitiesDTO;
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

	@Inject
	private ActivityBean activityBean;
	
	private ProjectDTO project;
	
	private List<ProjectDTO>projectList;
	
	private String activeIndex;
	
	private Long projectId;

	@PostConstruct
	public void init() {
		project=new ProjectDTO();
		projectList=new ArrayList<ProjectDTO>(); 
		setActiveIndex("0");
	}

	public String onClickOfMenu() {
		fetchAllProjects();
		activityBean.onClickOfMenu();
		setActiveIndex("0");
		return "projectList.xhtml?faces-redirect=true";
	}

	public String addNewProject() {
		project=new ProjectDTO();
		activityBean.addNewProject();
		setActiveIndex("0");
		return "project.xhtml?faces-redirect=true";
	}
	
	public String editProject() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			project = mapper.readValue(AbstractRestTemplate.restServiceForObject("/project/fetchProjectById/"+projectId),ProjectDTO.class);
			setActiveIndex("0");
			activityBean.setProjectActivities(activityBean.fetchProjectActivitiesByProject(projectId));
			fetchActivityByProjectActivities(activityBean.getProjectActivities());
			return "project.xhtml?faces-redirect=true";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	
	}

	public void onClickofNext() {
		setActiveIndex("1");

	}

	public void onClickofBack() {
		setActiveIndex("0");

	}

	@SuppressWarnings("unchecked")
	public void fetchAllProjects() {
		projectList=AbstractRestTemplate.restServiceForList("/project/fetchAllProjects");
	}

	public String saveProject() {
		if(this.project!=null) {

			try {
				ObjectMapper mapper = new ObjectMapper();
				project = mapper.readValue(AbstractRestTemplate.postForObject("/project/saveProject",project),ProjectDTO.class);
				if(project.getProjectId()!=null) {
					List<ProjectActivitiesDTO>projectActivities=new ArrayList<>();
					if(activityBean.getSelectedNodes()!=null && activityBean.getSelectedNodes().length>0) {
						
						for (TreeNode node : Arrays.asList(activityBean.getSelectedNodes())) {
							ActivitiesDTO act=(ActivitiesDTO) node.getData();
							if(act!=null && act.getActivityType()==2) {
								ProjectActivitiesDTO dto=new ProjectActivitiesDTO();
								dto.setProjectKey(project.getProjectId());
								dto.setActivityKey(act.getActivityId());
								dto.setActivityPlannedStartDate(act.getActivityPlannedStartDate());
								dto.setActivityPlannedEndDate(act.getActivityPlannedEndDate());
								dto.setActivityAcutalStartDate(act.getActivityAcutalStartDate());
								dto.setActivityActualEndDate(act.getActivityActualEndDate());
								projectActivities.add(dto);
							}
							
						}
						if(projectActivities!=null && projectActivities.size()>0)
					    AbstractRestTemplate.postForObject("/project/saveProjectActivitiesList",projectActivities);
						
					}
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Project saved successfully",
								"Project saved successfully"));
				
				project=new ProjectDTO();
				fetchAllProjects();
				return "projectList.xhtml?faces-redirect=true";
			}
			catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		}
		return null;
	}
	
	private void fetchActivityByProjectActivities(List<ProjectActivitiesDTO> projectActivities) {
		int i=0;
		TreeNode[] selectedNodes = null;
		for (ProjectActivitiesDTO projectActivitiesDTO : projectActivities) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				ActivitiesDTO activity = mapper.readValue(AbstractRestTemplate.restServiceForObject("/activity/fetchactivity/"+projectActivitiesDTO.getActivityKey()),ActivitiesDTO.class);
				TreeNode act=(TreeNode) activity;
				selectedNodes[i]=act;
				i++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		activityBean.setSelectedNodes(selectedNodes);
		
	}
	 
	
}
