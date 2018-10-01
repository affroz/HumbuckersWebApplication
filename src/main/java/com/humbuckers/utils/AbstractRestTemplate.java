package com.humbuckers.utils;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.humbuckers.dto.UserRoleDTO;
import com.humbuckers.dto.UsersDTO;

public class AbstractRestTemplate {

	private static String finalUrl="http://localhost:8090/humbuckers";
	
	public static String restServiceForObject(String url) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			String response = restTemplate.getForObject(finalUrl+url,String.class);
			return response;

		}
		catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Something went wrong!!",
							"Something went wrong!!"));
			// TODO: handle exception
		}
		return "";
	}

	@SuppressWarnings({ "rawtypes" })
	public static List restServiceForList(String url){
		try {

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			return restTemplate.getForObject(finalUrl+url, List.class);
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

	public static String postForObject(String url,Object object){
		try {

			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.postForObject( finalUrl+url, object, String.class);
			return response;
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


}
