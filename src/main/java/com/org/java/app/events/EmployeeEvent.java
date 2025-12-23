package com.org.java.app.events;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Setter
@Getter
public class EmployeeEvent extends ApplicationEvent {
	
	private String empId;
	private String empName;

	public EmployeeEvent(Object source,String empId,String empName) {
		super(source);
		this.empId=empId;
		this.empName=empName;
		
	}

}
