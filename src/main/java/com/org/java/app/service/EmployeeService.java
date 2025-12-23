package com.org.java.app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.org.java.app.dto.EmployeeDto;
import com.org.java.app.entity.Employee;


@Service
public interface EmployeeService {
	
	public Employee saveEmployeeDetails(EmployeeDto employeeDto);
	
	public Employee updateEmployeeDetails(Employee employee);

	public Employee deleteEmployeeDetails(Employee employee);
	
	public List<EmployeeDto> findAllEmployeeDetails();

	public Employee findByIdEmployeeDetails(int empId);
	
	public List<Employee> findByName(String empName);
	
	List<Employee> findByEmployedeptNameDeatails(String deptName);

	List<Employee> findByEmployeeSalaryAscDeatails();

	List<Employee> findByEmployeeSalaryDscDeatails();

	List<Employee> findByEmployeeIdEvenDeatails();

	List<Employee> findByEmployeeIdOddDeatails();

	Employee findByMaxSalaryDeatails();

	Employee findByMinSalaryDeatails();

	double findBySumSalaryDeatails();

	double findByCountSalaryDeatails();

	List<Employee> findParticularRecordsDeatails();
	
	List<Employee> findParticularRecordFileter();

	Set<Employee> printDublicateRecordsDeatails();

	Set<Employee> printWithoutDublicateRecordsDeatails();

	List<Employee> findParticularRecordsAscsDeatails();

	List<Employee> findParticularRecordsDscDeatails();

	List<String> mapNamesDeatails();
	
	List<String> mapNamesToUppercaseDeatails();

	Map<Character, Integer> findStringOccurenceDeatails();

	Map<Object, List<Employee>> groupBySalaryDeatails();

	Map<Object, List<Employee>> groupByNamesDeatails();

	Employee findByNameAndDeptNameDeatails(String empName, String deptName);

	Employee findByEmpIdAndNameAndDeptNameDeatails(int empId, String empName, String deptName);

	String firstnonRepeactedCharacterInStringDeatails();

	String firstRepeactedCharacterInStringDeatails();

	List<String> printDublicatesInStringDeatails();

	List<String> uniquerecordsInStringDeatails();

	String longestStringDeatails();

	String smallestStringDeatails();

	List<String> filterDepartmentIdsDeatails();

	String stringReverseJava8Deatails();

	Employee secondHigestSalaryDeatails();

	Employee secondListSalaryDeatails();

	List<Employee> indexRangesDeatails(int fromIndex, int toIndex);

	String joiningNamesDeatails();

	Set<Employee> listToSetCoversion();

	Map<Integer, Employee> listToMapCoversion();

	List<Employee> setToListConversion();

	Map<Integer, Employee> setToMapConversionDetails();

	List<Entry<Integer, Employee>> mapToListConversionDetails();

	Set<Entry<Integer, Employee>> mapToSetConversionDetails();

	Optional<Employee> findByEmployeeIdDeatails(int empId);

	List<Employee> findByDepartmentDetails(String deptName);

	String leftRotationStringDeatails();

	String rightRotationStringDeatails();

	List<EmployeeDto> findByEmployeeBetweenSalaryDeatails();

	Map<String, Long> findBygroupCountDeatails();


}
