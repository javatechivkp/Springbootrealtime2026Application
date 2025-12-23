package com.org.java.app.serviceimpl;

import com.org.java.app.dto.EmployeeEventDto;
import com.org.java.app.events.EmployeeEvent;
import com.org.java.app.service.EmployeeEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventServiceImpl implements EmployeeEventService{
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public String saveEventDetail(String empId, String empName) {
		applicationEventPublisher.publishEvent(new EmployeeEvent(this, empId, empName));
		 return "Employee " + empId + " with ID " + empName + " create successfully!";
	}

	


    
    }
