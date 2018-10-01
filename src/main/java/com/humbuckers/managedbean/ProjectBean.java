package com.humbuckers.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

	private ProjectDTO project;
	
	private List<ActivitiesDTO> activityList;

	private List<ProjectDTO>projectList;

	private TreeNode root;
	
	private TreeNode[] selectedNodes;
	
	
	private ActivitiesDTO selectedActivity;
	private String newactivityname;
	private String activeIndex;

	@PostConstruct
	public void init() {
		activityList=new ArrayList<ActivitiesDTO>();
		project=new ProjectDTO();
		projectList=new ArrayList<ProjectDTO>(); 
		setActiveIndex("0");
	}

	public String onClickOfMenu() {
	    activityList=fetchAllActivty();
		fetchAllProjects();
		root=createTree();
		selectedNodes=null;
		setSelectedActivity(null);
		setActiveIndex("0");
		return "projectList.xhtml?faces-redirect=true";
	}


	public String addNewProject() {
		project=new ProjectDTO();
		root=createTree();
		selectedNodes=null;
		setSelectedActivity(null);
		setActiveIndex("0");
		return "project.xhtml?faces-redirect=true";
	}

	@SuppressWarnings("unchecked")
	public void fetchAllProjects() {
		projectList=AbstractRestTemplate.restServiceForList("/project/fetchAllProjects/");
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
	
	


	public String saveProject() {
		if(this.project!=null) {

			try {
				ObjectMapper mapper = new ObjectMapper();
				project = mapper.readValue(AbstractRestTemplate.postForObject("/project/saveProject",project),ProjectDTO.class);
				if(project.getProjectId()!=null) {
					List<ProjectActivitiesDTO>projectActivities=new ArrayList<>();
					if(selectedNodes!=null && selectedNodes.length>0) {
						
						for (TreeNode node : Arrays.asList(selectedNodes)) {
							ActivitiesDTO act=(ActivitiesDTO) node.getData();
							if(act!=null && act.getActivityType()==2) {
								ProjectActivitiesDTO dto=new ProjectActivitiesDTO();
								dto.setProject(project);
								dto.setActivities(act);
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
	  
	 
	  
	 
	  
	  public void onClickofNext() {
	      setActiveIndex("1");
	        
	  }
	  
	  public void onClickofBack() {
	      setActiveIndex("0");
	        
	  }
	
}
