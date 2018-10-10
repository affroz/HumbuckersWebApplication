package com.humbuckers.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.timeline.TimelineEvent;
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
public class ProjectViewBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	private List<ProjectWbsDTO>projectWbsMainList;
	private String engweight;
	private String procweight;
	private String constweight;
	private String testweight;
	private TreeNode root;
	
	@Inject
	private ProjectReportBean projectReportBean;
    
	
	@PostConstruct
	public void init() {
		projectWbsMainList=new ArrayList<ProjectWbsDTO>();
		projectReportBean.init();
	}

    public void fetchProjectWbs(Long projectid) {
    	projectWbsMainList=AbstractRestTemplate.fetchObjectList("/project/fetchWbsByParentByProject/"+projectid,ProjectWbsDTO.class);
    	if(projectWbsMainList!=null && projectWbsMainList.size()>0) {
    		for (ProjectWbsDTO dto : projectWbsMainList) {
    			if(projectWbsMainList.size()==4) {
    				if(dto.getActivityName()!=null && "Engineering".equalsIgnoreCase(dto.getActivityName())) {
    					dto.setWeightage("3");
    					setEngweight("3");
    				}
    				if(dto.getActivityName()!=null && "Procurement".equalsIgnoreCase(dto.getActivityName())) {
    					dto.setWeightage("8");	
    					setProcweight("8");
    				 }
    				if(dto.getActivityName()!=null && "Construction".equalsIgnoreCase(dto.getActivityName())) {
    					dto.setWeightage("85");
    					setConstweight("85");
    				}
    				if(dto.getActivityName()!=null && "Testing & Commisioning".equalsIgnoreCase(dto.getActivityName())) {
    					dto.setWeightage("4");
    					setTestweight("4");
    				}
    			}
    			if(projectWbsMainList.size()==3) {
    				if(dto.getActivityName()!=null && "Engineering".equalsIgnoreCase(dto.getActivityName())) {
    					dto.setWeightage("5");
    					setEngweight("5");
    				}
    				if(dto.getActivityName()!=null && "Procurement".equalsIgnoreCase(dto.getActivityName())) {
    					dto.setWeightage("8");	
    					setProcweight("8");
    				 }
    				if(dto.getActivityName()!=null && "Construction".equalsIgnoreCase(dto.getActivityName())) {
    					dto.setWeightage("87");
    					setConstweight("87");
    				}
    				
    				setTestweight("0");
    			}
    			
			}
    	}
    }
    
    public ProjectDTO createTreeStucture(ProjectDTO project) {
    	root = new DefaultTreeNode(project.getProjectName(), null);
	    root.setExpanded(true);
	    project.setTotalActivities(0);
	    project.setTotalWbs(0);
	 	if(projectWbsMainList!=null && projectWbsMainList.size()>0) {
			for (ProjectWbsDTO entity : projectWbsMainList) {
				
				String style = null;
				if(entity.getActivityName()!=null && "Engineering".equalsIgnoreCase(entity.getActivityName())) {
					style="engineering";
				}
				if(entity.getActivityName()!=null && "Procurement".equalsIgnoreCase(entity.getActivityName())) {
					style="procurement";
				}
				if(entity.getActivityName()!=null && "Construction".equalsIgnoreCase(entity.getActivityName())) {
					style="construction";
				}
				if(entity.getActivityName()!=null && "Testing & Commisioning".equalsIgnoreCase(entity.getActivityName())) {
					style="testing";
				}
				TreeNode node=new DefaultTreeNode(style,entity,root);
				project.setTotalWbs(project.getTotalWbs()+1);
				addToTreeNode(node, entity.getActivityId(),style,project);
				
			}
		}
		return project;
	}
	
	
    
    public void addToTreeNode(TreeNode node, Long activityId, String style, ProjectDTO project) {
    	List<ProjectWbsDTO> subList=AbstractRestTemplate.fetchObjectList("/project/fetchWbsByParent/"+activityId,ProjectWbsDTO.class);
		if(subList !=null && subList.size()>0) {
			for (ProjectWbsDTO entity : subList) {
				TreeNode childNode=new DefaultTreeNode(style,entity,node);
				entity.setStyle(style);
				if(entity.getActivityCode()!=null && entity.getActivityCode()==1) {
					projectReportBean.getActivityModel().add(new TimelineEvent(entity, entity.getActivityPlannedStartDate()));
					project.setTotalActivities(project.getTotalActivities()+1);
				}else {
					project.setTotalWbs(project.getTotalWbs()+1);
				}
				addToTreeNode(childNode, entity.getActivityId(),style,project);
			}
		}
	}

	
   
}
