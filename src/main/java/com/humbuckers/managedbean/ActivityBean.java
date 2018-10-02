package com.humbuckers.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.humbuckers.dto.ActivitiesDTO;
import com.humbuckers.dto.ProjectActivitiesDTO;
import com.humbuckers.utils.AbstractRestTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@Scope("session")
public class ActivityBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;


	private List<ActivitiesDTO> activityList;

	private TreeNode root;

	private TreeNode[] selectedNodes;
	private List<TreeNode>selectedNodesList;

	private ActivitiesDTO selectedActivity;
	private String newactivityname;
	
	private List<ProjectActivitiesDTO> projectActivities;

	@PostConstruct
	public void init() {
		activityList=new ArrayList<ActivitiesDTO>();
		projectActivities=new ArrayList<ProjectActivitiesDTO>();
		
	}

	public void onClickOfMenu() {
		activityList=fetchAllActivty();
		projectActivities=new ArrayList<ProjectActivitiesDTO>();
		root=createTree();
		selectedNodes=null;
		setSelectedActivity(null);
		
	}


	public void addNewProject() {
		root=createTree();
		projectActivities=new ArrayList<ProjectActivitiesDTO>();
		selectedNodes=null;
		setSelectedActivity(null);
		
	}
	public void editProject() {
		root=createTree();
		selectedNodes=null;
		setSelectedActivity(null);
		
	}
	

	@SuppressWarnings("unchecked")
	public List<ActivitiesDTO> fetchAllActivty(){
		activityList=new ArrayList<ActivitiesDTO>();
		List<ActivitiesDTO> list = AbstractRestTemplate.restServiceForList("/activity/fetchAllActivity");
		List<ActivitiesDTO> finalList=new ArrayList<>();
		if(list!=null && list.size()>0) {
			list=fetchAllList(list);
			setActivityList(new ArrayList<>());
			activityList.addAll(finalList);
		}

		return list;
	}


	public List<ActivitiesDTO> fetchAllList(List<ActivitiesDTO> list){
		List<ActivitiesDTO> finalList=new ArrayList<>();
		if(list!=null && list.size()>0) {
			for (int j = 0; j < list.size(); j++) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					Gson gson = new Gson();
					String jsonString = gson.toJson(list.get(j));
					ActivitiesDTO entity=mapper.readValue(jsonString,ActivitiesDTO.class);
					if(entity.getActivityChildList()!=null && entity.getActivityChildList().size()>0) {
						List<ActivitiesDTO> childList=fetchAllList(entity.getActivityChildList());
						entity.setActivityChildList(childList);
					}
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


	public TreeNode createTree() {
		TreeNode root = new DefaultTreeNode("Activities", null);
		root.setExpanded(true);
		if(activityList!=null && activityList.size()>0) {
			for (ActivitiesDTO activitiesDTO : activityList) {
				TreeNode childnode = new DefaultTreeNode(activitiesDTO,root);
				childnode.setExpanded(true);
				if(activitiesDTO.getActivityChildList()!=null && activitiesDTO.getActivityChildList().size()>0) {
					for (ActivitiesDTO childentity : activitiesDTO.getActivityChildList()) {
						TreeNode treenode=test(childentity, childnode);
						treenode.setExpanded(true);
					}

				}

			}
		}


		return root;
	}

	private TreeNode test(ActivitiesDTO obj,TreeNode node) {

		TreeNode childnode = new DefaultTreeNode(obj,node);
		if(obj.getActivityChildList()!=null && obj.getActivityChildList().size()>0) {
			for (ActivitiesDTO childentity : obj.getActivityChildList()) {
				TreeNode child=new DefaultTreeNode(childentity,childnode);
				if(childentity.getActivityChildList()!=null && childentity.getActivityChildList().size()>0) {
					for (ActivitiesDTO entity : childentity.getActivityChildList()) {
						TreeNode treenode=test(entity, child);
						treenode.setExpanded(true);

					}
				}
			}

		}
		return childnode;

	}


	public void saveNewActivity() throws JsonParseException, JsonMappingException, IOException {
		if(selectedActivity!=null && selectedActivity.getActivityId()!=null && newactivityname!=null && !"".equals(newactivityname)) {

			ActivitiesDTO activitiesDTO=new ActivitiesDTO();
			activitiesDTO.setActivityName(newactivityname);
			activitiesDTO.setActivityParentId(selectedActivity.getActivityId());
			activitiesDTO.setActivityType(2L);

			updateOrSaveAct(activitiesDTO);

			if(selectedActivity.getActivityType()==2L) {
				selectedActivity.setActivityType(1L);
			}
			updateOrSaveAct(selectedActivity);
			activityList=fetchAllActivty();

			root=createTree();
			setNewactivityname(null);
		}
	}


	public ActivitiesDTO updateOrSaveAct(ActivitiesDTO act){
		try {
			ObjectMapper mapper = new ObjectMapper();
			act = mapper.readValue(AbstractRestTemplate.postForObject("/activity/updateActivity",act),ActivitiesDTO.class);
			return act;
		}
		catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Something went wrong!!",
							"Something went wrong!!"));
		}
		return null;
	}

	
	
	public List<ProjectActivitiesDTO> fetchProjectActivitiesByProject(Long projectKey){
		List<ProjectActivitiesDTO> list=AbstractRestTemplate.restServiceForList("/project/fetchProjectActivityByProject/"+projectKey);
		List<ProjectActivitiesDTO> finalList=fetchAllProjectActivities(list);
		return finalList;
		
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
	
	
	
	
	public TreeNode createTree(List<ActivitiesDTO> activities) {
		selectedNodesList=new ArrayList<>();
		TreeNode root = new DefaultTreeNode("Activities", null);
		root.setExpanded(true);
		if(activityList!=null && activityList.size()>0) {
			for (ActivitiesDTO activitiesDTO : activityList) {
				activitiesDTO=checkObjectExists(activities, activitiesDTO);
				TreeNode childnode = new DefaultTreeNode(activitiesDTO,root);
				childnode.setExpanded(true);
				if("A".equals(activitiesDTO.getExist())) {
					childnode.setSelected(true);
					selectedNodesList.add(childnode);
				}
				if(activitiesDTO.getActivityChildList()!=null && activitiesDTO.getActivityChildList().size()>0) {
					for (ActivitiesDTO childentity : activitiesDTO.getActivityChildList()) {
						childentity=checkObjectExists(activities, childentity);
						TreeNode treenode=test(childentity, childnode,activities);
						treenode.setExpanded(true);
						if("A".equals(childentity.getExist())) {
							treenode.setSelected(true);
							selectedNodesList.add(treenode);
						}
					}

				}

			}
		}


		return root;
	}

	private TreeNode test(ActivitiesDTO obj,TreeNode node,List<ActivitiesDTO> activities) {

		TreeNode childnode = new DefaultTreeNode(obj,node);
		if(obj.getActivityChildList()!=null && obj.getActivityChildList().size()>0) {
			for (ActivitiesDTO childentity : obj.getActivityChildList()) {
				childentity=checkObjectExists(activities, childentity);
				TreeNode child=new DefaultTreeNode(childentity,childnode);
				if("A".equals(childentity.getExist())) {
					child.setSelected(true);
					selectedNodesList.add(child);
				}
				if(childentity.getActivityChildList()!=null && childentity.getActivityChildList().size()>0) {
					for (ActivitiesDTO entity : childentity.getActivityChildList()) {
						entity=checkObjectExists(activities, entity);
						TreeNode treenode=test(entity, child);
						treenode.setExpanded(true);
						if("A".equals(entity.getExist())) {
							treenode.setSelected(true);
							selectedNodesList.add(treenode);
						}

					}
				}
			}

		}
		return childnode;

	}
	

	private ActivitiesDTO checkObjectExists(List<ActivitiesDTO> activities,ActivitiesDTO dto) {
		for (ActivitiesDTO entity : activities) {
			if(entity.getActivityId().equals(dto.getActivityId())) {
				dto.setExist("A");
				dto.setActivityPlannedStartDate(entity.getActivityPlannedStartDate());
				dto.setActivityPlannedEndDate(entity.getActivityPlannedEndDate());
				dto.setActivityAcutalStartDate(entity.getActivityAcutalStartDate());
				dto.setActivityActualEndDate(entity.getActivityActualEndDate());
				return dto;
			}
		}
		
		return dto;
		
	}
	
}
