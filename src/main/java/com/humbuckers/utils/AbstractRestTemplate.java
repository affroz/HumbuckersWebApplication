package com.humbuckers.utils;

import java.util.List;

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
			e.printStackTrace();
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
			e.printStackTrace();
			// TODO: handle exception
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
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
	
	
	public static void main(String [] args)
	{
		
		UsersDTO user=new UsersDTO();
		user.setUserId(3L);
		user.setFullName("test");
		user.setUserName("test");
		user.setPassword("test");
		user.setUserRole(new UserRoleDTO());
		user.getUserRole().setRoleId(1L);
		
		String response =postForObject("/users/updateUser",user);
		
		System.out.println(response);

	}

}
