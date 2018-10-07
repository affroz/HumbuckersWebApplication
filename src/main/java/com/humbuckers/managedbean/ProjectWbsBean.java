package com.humbuckers.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
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
public class ProjectWbsBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;



	private TreeNode root;
    private List<ProjectWbsDTO>projectWbsList;
    private ProjectWbsDTO projectWbsDTO;
    private TreeNode selectedNode;
	
	@PostConstruct
	public void init() {
		root=new DefaultTreeNode("WBS", null);
		projectWbsDTO=new ProjectWbsDTO();
	}

	
	public void addWbsNode() {
		if(selectedNode==null) {
			selectedNode=root;
		}
		projectWbsDTO.setActivityName(projectWbsDTO.getWbsname());
		new DefaultTreeNode(projectWbsDTO,selectedNode);
		setProjectWbsDTO(new ProjectWbsDTO());
	}
	
	public void addActivityNode() {
		projectWbsDTO.setActivityCode(1L);
		new DefaultTreeNode(projectWbsDTO,selectedNode);
		setProjectWbsDTO(new ProjectWbsDTO());
	}
	
	public void addToList(TreeNode node, Long projectKey) {
		ProjectWbsDTO dto=(ProjectWbsDTO) node.getData();
		dto.setActivityId(dto.getActivityId()==null ?fetchMaxWbsId():dto.getActivityId());
		dto.setProjectKey(projectKey);
		projectWbsList.add(dto);
		if(node!=null && node.getChildren()!=null) {
			for (TreeNode childNode : node.getChildren()) {
				ProjectWbsDTO childdto=(ProjectWbsDTO) childNode.getData();
				childdto.setActivityId(childdto.getActivityId()==null ?fetchMaxWbsId():childdto.getActivityId());
				childdto.setParentKey(dto.getActivityId());
				childdto.setProjectKey(projectKey);
				projectWbsList.add(childdto);
	            if (childNode.getChildCount() > 0) {
	            	addToList(childNode,projectKey);
	            }
	        }
		}
	}
	
	public void submit(Long projectKey) {
		projectWbsList=new ArrayList<ProjectWbsDTO>();
		if(root!=null && root.getChildCount()>0) {
			for (TreeNode node : root.getChildren()) {
				addToList(node,projectKey);
			}
		}
		if(projectWbsList!=null && projectWbsList.size()>0) {
			AbstractRestTemplate.postObject("/project/saveProjectWbsList/"+projectKey,projectWbsList,ProjectWbsDTO.class);
		}
		
	}
	
	
	private Long fetchMaxWbsId() {
		return (Long) AbstractRestTemplate.fetchObject("/project/fetchMaxWbsId",Long.class);
	}
	
	
	public boolean deleteNode() {
	    if (root.getChildren().remove(selectedNode)) {
	        return true;
	    } else {
	        for (TreeNode childNode : root.getChildren()) {
	            if (childNode.getChildCount() > 0) {
	                return removeElemetOfTreeNode(childNode, selectedNode);
	            }

	        }
	        return false;
	    }
	}

	
	public boolean removeElemetOfTreeNode(TreeNode rootNode,
	        TreeNode nodeToDelete) {
	    if (rootNode.getChildren().remove(nodeToDelete)) {
	        return true;
	    } else {
	        for (TreeNode childNode : rootNode.getChildren()) {
	            if (childNode.getChildCount() > 0) {
	                return removeElemetOfTreeNode(childNode, nodeToDelete);
	            }

	        }
	        return false;
	    }
	}

	
	public void fetchTreeWbsNode(List<ProjectWbsDTO> list,TreeNode parentnode) {
		for (ProjectWbsDTO projectWbsDTO : list) {
			TreeNode node= new DefaultTreeNode(projectWbsDTO, parentnode);
			List<ProjectWbsDTO> childlist=AbstractRestTemplate.fetchObjectList("/project/fetchWbsByParent/"+projectWbsDTO.getActivityId(),ProjectWbsDTO.class);
			if(childlist!=null && childlist.size()>0) {
				fetchTreeWbsNode(childlist, node);
			}
		}
		
		
	}
	
}
