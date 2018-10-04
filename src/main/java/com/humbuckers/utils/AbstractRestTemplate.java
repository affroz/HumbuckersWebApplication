package com.humbuckers.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class AbstractRestTemplate {

	private static String finalUrl="http://localhost:8090/humbuckers";
	
	
	public static <T> List<T> fetchObjectList(String url,Object A) {
		List<T> finalList=new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			@SuppressWarnings("unchecked")
			List<T> list = (ArrayList<T> ) restTemplate.getForObject(finalUrl+url,ArrayList.class);
		     if(list!=null && list.size()>0) {
				for (int j = 0; j < list.size(); j++) {
					try {
						Gson gson = new Gson();
						String jsonString = gson.toJson(list.get(j));
						@SuppressWarnings("unchecked")
						T entity=mapper.readValue(jsonString,(Class<T>) A);
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

		}
		catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Something went wrong!!",
							"Something went wrong!!"));
			// TODO: handle exception
		}
		return finalList;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Object fetchObject(String url,Object A) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			String response = restTemplate.getForObject(finalUrl+url,String.class);
			return mapper.readValue(response,(Class<T>) A);

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
	
	@SuppressWarnings("unchecked")
	public static <T> Object postObject(String url,Object obj,Object A) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			String response = restTemplate.postForObject( finalUrl+url, obj, String.class);
			return mapper.readValue(response,(Class<T>) A);

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
		
	/*public static void main(String [] args)
	{
		ProjectDTO project=(ProjectDTO) AbstractRestTemplate.fetchObject("/project/fetchProjectById/450", ProjectDTO.class);
		
		System.out.println(project);
		
        List<ProjectDTO> project1=AbstractRestTemplate.fetchObjectList("/project/fetchAllProjects",ProjectDTO.class);
		
		System.out.println(project1);
	}*/
	
}
