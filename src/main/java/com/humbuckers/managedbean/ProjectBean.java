package com.humbuckers.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.springframework.context.annotation.Scope;

import com.humbuckers.dto.ProjectDTO;
import com.humbuckers.dto.ProjectWbsDTO;
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
	private ProjectWbsBean projectWbsBean;
	
	@Inject
	private ProjectViewBean projectViewBean;
	
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

	public void onPageLoad() {
		fetchAllProjects();
	}

	public String addNewProject() {
		project=new ProjectDTO();
		setActiveIndex("0");
		projectWbsBean.fetchNodes();
		projectWbsBean.setProjectWbsDTO(new ProjectWbsDTO());
		return "projectAddMain.xhtml?faces-redirect=true";
	}
	
	
	public String viewProject() {
		projectViewBean.init();
		project =(ProjectDTO) AbstractRestTemplate.fetchObject("/project/fetchProjectById/"+projectId, ProjectDTO.class); 
		projectViewBean.fetchProjectWbs(project.getProjectId());
		project=projectViewBean.createTreeStucture(project);
		return "projectViewMain.xhtml?faces-redirect=true";
	}
	
	
	public String editProject() {
		project =(ProjectDTO) AbstractRestTemplate.fetchObject("/project/fetchProjectById/"+projectId, ProjectDTO.class); 
		setActiveIndex("0");
		projectWbsBean.setRoot(new DefaultTreeNode(project.getProjectName(), null));
		List<ProjectWbsDTO> list=AbstractRestTemplate.fetchObjectList("/project/fetchWbsByParentByProject/"+projectId,ProjectWbsDTO.class);
		projectWbsBean.fetchTreeWbsNode(list, projectWbsBean.getRoot());
		return "projectAddMain.xhtml?faces-redirect=true";
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
				project =(ProjectDTO) AbstractRestTemplate.postObject("/project/saveProject",project,ProjectDTO.class);
				if(project.getProjectId()!=null) {
					//AbstractRestTemplate.fetchObject("/project/deleteWbsByProject/"+project.getProjectId(),ProjectWbsDTO.class);
					projectWbsBean.submit(project.getProjectId());
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Project saved successfully",
								"Project saved successfully"));
				
				project=new ProjectDTO();
				return "projectListMain.xhtml?faces-redirect=true";
			
		}
		return null;
	}
	
}
