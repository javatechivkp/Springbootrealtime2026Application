package com.org.java.app.service;

import org.springframework.stereotype.Service;



@Service
public interface EmployeeEventService {

	String saveEventDetail(String empId, String empName);
	
	

}
