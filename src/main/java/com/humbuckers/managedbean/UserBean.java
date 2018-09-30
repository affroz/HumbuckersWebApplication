package com.humbuckers.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.humbuckers.dto.UsersDTO;
import com.humbuckers.utils.AbstractRestTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@Scope("session")
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	private List<UsersDTO>userlist;
	
	@PostConstruct
	public void init() {
		userlist=new ArrayList<UsersDTO>();
		
	}
	
	public String onClickOfMenu() {
		userlist=fetchAllUsers();
		return "userslist.xhtml?faces-redirect=true";
	}
	
	@SuppressWarnings("unchecked")
	public List<UsersDTO> fetchAllUsers(){
	     List<UsersDTO> user = AbstractRestTemplate.restServiceForList("/users/fetchAllUsers");
	     return user;
	}
	
	
	public String onRowEdit(RowEditEvent event) throws JsonParseException, JsonMappingException, IOException {
		Object selectedUserObj=event.getObject();
		ObjectMapper mapper = new ObjectMapper();
		Gson gson = new Gson();
		String jsonInString = gson.toJson(selectedUserObj);
		UsersDTO selectedUser = mapper.readValue(jsonInString, UsersDTO.class);
		
		if(selectedUser!=null && selectedUser.getUserId()!=null) {
			selectedUser=updateUser(selectedUser);
			new FacesMessage(FacesMessage.SEVERITY_INFO,
					"User Details updated Successfully",
					"User Details updated Successfully");
		}
		return "";
		
    }
     
    public void onRowCancel(RowEditEvent event) {
      
    }
    
    
    public UsersDTO updateUser(UsersDTO user){
		   try {
			   ObjectMapper mapper = new ObjectMapper();
			   user = mapper.readValue(AbstractRestTemplate.postForObject("/users/updateUser",user),UsersDTO.class);
			   return user;
		   }
		   catch (Exception e) {
			   e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
	
}
