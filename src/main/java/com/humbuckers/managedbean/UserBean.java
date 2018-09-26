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
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.humbuckers.dto.UsersDTO;

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
		userlist=fetchAllUsers();
	}
	
	public List<UsersDTO> fetchAllUsers(){
		 String url = "http://localhost:8090/humbuckers/users/fetchAllUsers";
		   try {
			   
			   RestTemplate restTemplate = new RestTemplate();
			   restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			   @SuppressWarnings("unchecked")
			   List<UsersDTO> user = restTemplate.getForObject(url, List.class);
			   return user;
		   }
		   catch (Exception e) {
			   e.printStackTrace();
			// TODO: handle exception
		}
		return null;
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
    
    
    public UsersDTO updateUser(UsersDTO users){
		 String url = "http://localhost:8090/humbuckers/users/updateUser";
		   try {
			   
			   RestTemplate restTemplate = new RestTemplate();
			   users = restTemplate.postForObject( url, users, UsersDTO.class);
			   return users;
		   }
		   catch (Exception e) {
			   e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
	
}
