package com.humbuckers.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
import org.springframework.context.annotation.Scope;

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

	private String style;
	@Inject
	private ProjectReportBean projectReportBean;
    
    
	@PostConstruct
	public void init() {
		style="one";
	}

	
	public TreeNode createTreeStucture(Long projectKey)
	{	projectReportBean.setBasicmodel(new TimelineModel());
	    projectReportBean.setRangemodel(new TimelineModel());
	 	List<ProjectActivitiesDTO> list=AbstractRestTemplate.fetchObjectList("/project/fetchMainActivitiesByProject/"+projectKey,ProjectActivitiesDTO.class);
		TreeNode root = new DefaultTreeNode("Activities", null);
		root.setExpanded(true);
	
		if(list!=null && list.size()>0) {
			for (ProjectActivitiesDTO entity : list) {
				ProjectActivitiesViewDTO act=new ProjectActivitiesViewDTO();
				ActivitiesDTO dto= (ActivitiesDTO) AbstractRestTemplate.fetchObject("/activity/fetchactivity/"+entity.getActivityKey(), ActivitiesDTO.class);
				act.setName(dto.getActivityName());
				act.setActivityActualEndDate(entity.getActivityActualEndDate());
				act.setActivityPlannedStartDate(entity.getActivityPlannedStartDate());
				act.setActivityPlannedEndDate(entity.getActivityPlannedEndDate());
				act.setActivityAcutalStartDate(entity.getActivityAcutalStartDate());
				act.setActivityActualEndDate(entity.getActivityActualEndDate());
				projectReportBean.setStart(entity.getActivityPlannedStartDate());
				projectReportBean.setEnd(entity.getActivityPlannedEndDate());
				TreeNode parentNode = new DefaultTreeNode(act,root);
				parentNode.setExpanded(true);
				test(entity, parentNode);
			}
		}
		return root;
	}
	
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
	}
	
		
}
