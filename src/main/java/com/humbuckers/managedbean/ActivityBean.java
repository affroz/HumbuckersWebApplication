package com.humbuckers.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.humbuckers.dto.ActivitiesDTO;
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


	private ActivitiesDTO selectedActivity;
	private String newactivityname;

	@PostConstruct
	public void init() {
		activityList=new ArrayList<ActivitiesDTO>();
		
	}

	public void onClickOfMenu() {
		activityList=fetchAllActivty();
		root=createTree();
		selectedNodes=null;
		setSelectedActivity(null);
		
	}


	public void addNewProject() {
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
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}


}