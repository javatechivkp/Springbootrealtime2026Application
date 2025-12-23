package com.org.java.app.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class EmployeeDto {
	
	private int empId;
	private String empName;
	private int age;
	private double salary;
	private String designation;
	//private Date dataofJoinning;
	private String plateform;
	private String sector;
	private long mobbileNumber;
	private String email;
	private int departmentId;
	private String deptName;

}
