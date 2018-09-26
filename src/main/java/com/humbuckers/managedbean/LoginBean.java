package com.humbuckers.managedbean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.humbuckers.dto.UsersDTO;
import com.humbuckers.utils.SessionUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Named
@Scope("view")
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	private String password;
	private String userName;

	//validate login
	public String validateUserLogin() {
		 String url = "http://localhost:8090/humbuckers/users/validateUser/"+userName+"/"+password;
	   try {
		   
		   RestTemplate restTemplate = new RestTemplate();
		   restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		   UsersDTO user = restTemplate
				   .getForObject(url, UsersDTO.class);
		   if(user!=null && user.getUserId()!=null) {
			   SessionUtils.setObjectInHTTPSesion("LOGIN_USER", user);
			   return "pages/dashboard.xhtml?faces-redirect=true";
		   }else {
			   FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				return "login"; 
		   }
		   
		   
			
			
		} catch (Exception e) {
			System.out.println(e);
			 FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Application is Down",
								"Please try later"));
				return "login";
		}
	
	}

	//logout event, invalidate session
	public String logout() throws IOException {
		SessionUtils.removeObjectFromHTTPSesion("LOGIN_USER");
		SessionUtils.sessionInvalidate();
		return "/login?faces-redirect=true"; 
	}
	
	
	
}
