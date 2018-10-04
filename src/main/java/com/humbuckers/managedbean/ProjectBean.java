package com.humbuckers.managedbean;

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

import com.humbuckers.dto.ActivitiesDTO;
import com.humbuckers.dto.ProjectActivitiesDTO;
import com.humbuckers.dto.ProjectDTO;
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
	
	@Inject
	private ProjectActivityBean projectActivityBean;
	
	@Inject
	private ProjectWeightageBean projectWeightageBean;
	
	private ProjectDTO project;
	
	private List<ProjectDTO>projectList;
	
	private String activeIndex;
	
	private Long projectId;

	private  List<ProjectActivitiesDTO> projectActivitiesList;
	@PostConstruct
	public void init() {
		project=new ProjectDTO();
		projectList=new ArrayList<ProjectDTO>(); 
		projectActivitiesList=new ArrayList<>();
		setActiveIndex("0");
	}

	public String onClickOfMenu() {
		fetchAllProjects();
		activityBean.onClickOfMenu();
		setActiveIndex("0");
		return "projectListMain.xhtml?faces-redirect=true";
	}

	public String addNewProject() {
		project=new ProjectDTO();
		activityBean.addNewProject();
		setActiveIndex("0");
		return "projectAddOrEditMain.xhtml?faces-redirect=true";
	}
	
	public String editProject() {
		project =(ProjectDTO) AbstractRestTemplate.fetchObject("/project/fetchProjectById/"+projectId, ProjectDTO.class); 
		setActiveIndex("0");
		activityBean.setProjectActivities(activityBean.fetchProjectActivitiesByProject(projectId));
		fetchActivityByProjectActivities(activityBean.getProjectActivities());
		return "projectAddOrEditMain.xhtml?faces-redirect=true";
	}
	
	public String viewProject() {
		project =(ProjectDTO) AbstractRestTemplate.fetchObject("/project/fetchProjectById/"+projectId, ProjectDTO.class); 
		setActiveIndex("0");
		projectActivityBean.setRoot(projectActivityBean.createTreeStucture(projectId)); 
		projectWeightageBean.calaculateWeightage(projectId);
		return "projectViewMain.xhtml?faces-redirect=true";
	}

	public void onClickofNext() {
		setActiveIndex("1");

	}

	public void onClickofBack() {
		setActiveIndex("0");

	}

	public void fetchAllProjects() {
		projectList=AbstractRestTemplate.fetchObjectList("/project/fetchAllProjects",ProjectDTO.class);
	}

	public String saveProject() {
		if(this.project!=null) {
			projectActivitiesList=new ArrayList<>();

				project =(ProjectDTO) AbstractRestTemplate.postObject("/project/saveProject",project,ProjectDTO.class);
				if(project.getProjectId()!=null) {
					AbstractRestTemplate.fetchObject("/project/deleteProjectActivityByProject/"+project.getProjectId(), ProjectDTO.class);
					if(activityBean.getSelectedNodes()!=null && activityBean.getSelectedNodes().length>0) {
						for (TreeNode node : Arrays.asList(activityBean.getSelectedNodes())) {
							ActivitiesDTO act=(ActivitiesDTO) node.getData();
							if(act!=null && act.getActivityType()==2) {
								ProjectActivitiesDTO dto=new ProjectActivitiesDTO();
								dto.setProjectKey(project.getProjectId());
								dto.setActivityKey(act.getActivityId());
								dto.setActivityPlannedStartDate(act.getActivityPlannedStartDate());
								dto.setActivityPlannedEndDate(act.getActivityPlannedEndDate());
								if(act.getActivityPlannedStartDate()!=null && act.getActivityPlannedEndDate()!=null) {
									Long days =((act.getActivityPlannedEndDate().getTime() - act.getActivityPlannedStartDate().getTime()) / (1000 * 60 * 60 * 24));
									dto.setNoOfDays(days.toString());
								}
								dto.setActivityAcutalStartDate(act.getActivityAcutalStartDate());
								dto.setActivityActualEndDate(act.getActivityActualEndDate());
								dto.setActivityTypeCode(act.getActivityType());
								dto.setParentActvityKey(act.getActivityParentId());
								projectActivitiesList.add(dto);
								addParentObj(act.getActivityParentId());
							}
							
						}
						if(projectActivitiesList!=null && projectActivitiesList.size()>0)
							AbstractRestTemplate.postObject("/project/saveProjectActivitiesList",projectActivitiesList,ProjectActivitiesDTO.class);
					}
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Project saved successfully",
								"Project saved successfully"));
				
				project=new ProjectDTO();
				onClickOfMenu();
				return "projectListMain.xhtml?faces-redirect=true";
			
		}
		return null;
	}
	
	private void addParentObj(Long key) {
	    ActivitiesDTO act =(ActivitiesDTO) AbstractRestTemplate.fetchObject("/activity/fetchactivity/"+key, ActivitiesDTO.class);
		ProjectActivitiesDTO dto=new ProjectActivitiesDTO();
		dto.setActivityKey(act.getActivityId());
		dto.setActivityTypeCode(act.getActivityType());
		dto.setParentActvityKey(act.getActivityParentId());
		dto.setProjectKey(project.getProjectId());
		projectActivitiesList.add(dto);
		if(dto.getActivityTypeCode()!=0) {
			addParentObj(dto.getParentActvityKey());
		}
	}
	
	private void fetchActivityByProjectActivities(List<ProjectActivitiesDTO> projectActivities) {
		List<ActivitiesDTO> activities=new ArrayList<>();
		for (ProjectActivitiesDTO projectActivitiesDTO : projectActivities) {
			ActivitiesDTO dto = (ActivitiesDTO) AbstractRestTemplate.fetchObject("/activity/fetchactivity/"+projectActivitiesDTO.getActivityKey(), ActivitiesDTO.class);
			dto.setActivityPlannedStartDate(projectActivitiesDTO.getActivityPlannedStartDate());
			dto.setActivityPlannedEndDate(projectActivitiesDTO.getActivityPlannedEndDate());
			dto.setActivityAcutalStartDate(projectActivitiesDTO.getActivityAcutalStartDate());
			dto.setActivityActualEndDate(projectActivitiesDTO.getActivityActualEndDate());
			activities.add(dto);
			
		}
		if(activities!=null && activities.size()>0) {
			activityBean.setRoot(activityBean.createTree(activities));
			TreeNode[] selectedNodes = new TreeNode[activityBean.getSelectedNodesList().size()];
			activityBean.setSelectedNodes(activityBean.getSelectedNodesList().toArray(selectedNodes));
		}
		
	}
	
	
	
	
	
		 
	
}
