package com.cst438.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.CourseDTOG;

public class RegistrationServiceREST extends RegistrationService {

	
	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${registration.url}") 
	String registration_url;
	
	public RegistrationServiceREST() {
		System.out.println("REST registration service ");
	}
	
	@Override
	//@PostMapping("/course/{course_id}/finalgrades")
	public void sendFinalGrades(int course_id , CourseDTOG courseDTO) { 
		
		//TODO  complete this method in homework 4
		//CourseDTOG dtog = new CourseDTOG();
		//when the teacher does a post call to /course/{course_id}/finalgrades 
		//send all the final grades of the students to Registration using CourseDTOG
		System.out.println("Sending final grades "+ course_id+ " " +courseDTO);
		restTemplate.put(registration_url+"/course/"+course_id,courseDTO);
		System.out.println("After sending final grades");
		
		
		
	}
}
