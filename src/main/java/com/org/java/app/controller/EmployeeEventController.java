package com.org.java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.java.app.dto.EmployeeEventDto;
import com.org.java.app.service.EmployeeEventService;

@RequestMapping("/event/api")
@RestController
public class EmployeeEventController {
	
	@Autowired
	private EmployeeEventService employeeEventService;
	
	@PostMapping("/testEvent")
	public String saveEmpEvent(@RequestBody EmployeeEventDto empEventdto) {
		String event=employeeEventService.saveEventDetail(empEventdto.getEmpId(),empEventdto.getEmpName());
		return event;
		
	}

}
