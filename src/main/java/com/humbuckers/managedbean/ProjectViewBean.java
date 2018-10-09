package com.humbuckers.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

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
    
    public void createTreeStucture(Long projectKey, String projectName) {
    	root = new DefaultTreeNode(projectName, null);
	    root.setExpanded(true);
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
				addToTreeNode(node, entity.getActivityId(),style);
				
			}
		}
	}
	
	
    
    public void addToTreeNode(TreeNode node, Long activityId, String style) {
    	List<ProjectWbsDTO> subList=AbstractRestTemplate.fetchObjectList("/project/fetchWbsByParent/"+activityId,ProjectWbsDTO.class);
		if(subList !=null && subList.size()>0) {
			for (ProjectWbsDTO entity : subList) {
				TreeNode childNode=new DefaultTreeNode(style,entity,node);
				addToTreeNode(childNode, entity.getActivityId(),style);
			}
		}
	}
    /*
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void test(ProjectActivitiesDTO entity,TreeNode node) {
		List<ProjectActivitiesDTO> subList=AbstractRestTemplate.fetchObjectList("/project/fetchSubActivitiesByParent/"+entity.getActivityKey(),ProjectActivitiesDTO.class);
		for (ProjectActivitiesDTO subentity : subList) {
			ActivitiesDTO subdto= (ActivitiesDTO) AbstractRestTemplate.fetchObject("/activity/fetchactivity/"+subentity.getActivityKey(), ActivitiesDTO.class);
			ProjectActivitiesViewDTO subact=new ProjectActivitiesViewDTO();
			subact.setName(subdto.getActivityName());
			subact.setActivityActualEndDate(subentity.getActivityActualEndDate());
			subact.setActivityPlannedStartDate(subentity.getActivityPlannedStartDate());
			subact.setActivityPlannedEndDate(subentity.getActivityPlannedEndDate());
			subact.setActivityAcutalStartDate(subentity.getActivityAcutalStartDate());
			subact.setActivityActualEndDate(subentity.getActivityActualEndDate());
			
			TreeNode subNode = new DefaultTreeNode(subact,node);
			subNode.setExpanded(true);
			if(style.equals("one")) {
				setStyle("two");
			}
			else if(style.equals("two")) {
				setStyle("three");
			}
			else if(style.equals("three")) {
				setStyle("one");
			}
			projectReportBean.getRangemodel().add(new TimelineEvent(subdto.getActivityName(), subentity.getActivityPlannedStartDate(), subentity.getActivityPlannedEndDate(),
					true, subdto.getActivityName(),style));
			projectReportBean.getBasicmodel().add(new TimelineEvent(subdto.getActivityName(), subentity.getActivityPlannedStartDate()));
			if(subdto.getActivityType()!=2) {
				test(subentity, subNode);
			}
			
		}
	}*/
	
}
