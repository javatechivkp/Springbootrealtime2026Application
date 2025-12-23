package com.org.java.app.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.java.app.dto.EmployeeDto;
import com.org.java.app.entity.Employee;
import com.org.java.app.service.EmployeeService;
import com.org.java.app.util.PdfGenerator;
import com.org.java.app.mapper.EmployeeMapper;
import com.org.java.app.util.ExcelGenerator;
import com.org.java.app.util.EmailSender;

import java.util.stream.Collectors;
import jakarta.mail.MessagingException;

@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("/empApi")
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmailSender emailSender;

	@GetMapping("/welcome")
	public String welcomeTest() {
		logger.trace("it is trace method welcome calling");
		logger.info("wel come method is working");
		return "WELCOME TO SPRINGBOOT TOMCAT INTEGRATION TEST 3.0 LATEST";
	}

	@PostMapping("/save")
	public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDto employeeDto) {
		logger.info("getting started for saveing....");
		Employee emp = employeeService.saveEmployeeDetails(employeeDto);
		logger.info("suscessfullly employee got saved" + emp);
		return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		Employee emp = employeeService.updateEmployeeDetails(employee);
		return new ResponseEntity("Sucessfully updated fields in  DB", HttpStatus.CREATED);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Employee> deletEmployee(@RequestBody Employee employee) {
		Employee emp = employeeService.deleteEmployeeDetails(employee);
		logger.info("sucessfully employeee record got deleted" + emp);
		return new ResponseEntity("Sucessfully deleted from DB", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findByIdData(@PathVariable("id") int empId,
			@RequestHeader(name = "x-Request-Source") String sourceSystem) {

		return switch (sourceSystem.toLowerCase()) {

		case "udemy" -> (empId == 2) ? fetchEmployees(empId)
				: ResponseEntity.badRequest()
						.body("Udemy does not have a course with ID " + empId + ". Please provide a valid ID.");

		case "coursera" -> (empId == 1) ? fetchEmployees(empId)
				: ResponseEntity.badRequest()
						.body("coursera does not have a course with ID " + empId + ". Please provide a valid ID.");

		case "unacademy" -> fetchEmployees(empId);

		default -> ResponseEntity.internalServerError().body("Unknown source system: " + sourceSystem);
		};
	}

	private ResponseEntity<Employee> fetchEmployees(int empId) {
		return ResponseEntity.ok(employeeService.findByIdEmployeeDetails(empId));
	}

	@GetMapping("/getEmployeeByName/{name}")
	public ResponseEntity<Employee> getEmployeeByName(@PathVariable("name") String empName) {
		List<Employee> emp = employeeService.findByName(empName);
		return new ResponseEntity(emp, HttpStatus.OK);
	}

	@GetMapping("/findAll")
	public ResponseEntity<?> findAllEmployee(@RequestHeader(name = "X-request-Source") String SouceSystem) {
		// logger.info("getting start the records for fetching");
		// List<EmployeeDto> list = employeeService.findAllEmployeeDetails();
		// logger.info("got all the records"+list);
		return switch (SouceSystem.toLowerCase()) {
		case "test" -> findAllEmployee();
		default -> ResponseEntity.internalServerError().body("Unknown source system: " + SouceSystem);
		};
	}

	private ResponseEntity<List<EmployeeDto>> findAllEmployee() {
		return ResponseEntity.ok(employeeService.findAllEmployeeDetails());
	}

	@GetMapping("/findByDepatmentName/{deptName}")
	public ResponseEntity<Employee> findByDeptDetails(@PathVariable("deptName") String deptName) {
		List<Employee> deptDetails = employeeService.findByDepartmentDetails(deptName);
		return new ResponseEntity(deptDetails, HttpStatus.OK);

	}

	@GetMapping("/findByDeptName/{deptName}")
	public ResponseEntity<Employee> findByEmployedeptName(@PathVariable("deptName") String deptName) {
		List<Employee> emplist = employeeService.findByEmployedeptNameDeatails(deptName);
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findByNameAndDeptName/{empName}/{deptName}")
	public ResponseEntity<Employee> findByNameAndDeptName(@PathVariable("empName") String empName,
			@PathVariable("deptName") String deptName) {
		Employee emplist = employeeService.findByNameAndDeptNameDeatails(empName, deptName);
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findByEmpIdAndNameAndDeptName/{empId}/{empName}/{deptName}")
	public ResponseEntity<Employee> findByEmpIdAndNameAndDeptName(@PathVariable("empId") int empId,
			@PathVariable("empName") String empName, @PathVariable("deptName") String deptName) {
		Employee emplist = employeeService.findByEmpIdAndNameAndDeptNameDeatails(empId, empName, deptName);
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findBySalaryAsc")
	public ResponseEntity<Employee> findByEmployeeAscSalary() {
		List<Employee> emplist = employeeService.findByEmployeeSalaryAscDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findBySalaryDsc")
	public ResponseEntity<Employee> findByEmployeeDscSalary() {
		List<Employee> emplist = employeeService.findByEmployeeSalaryDscDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findByBetwenSalary")
	public ResponseEntity<Employee> findByEmployeeBetweenSalary() {
		List<EmployeeDto> emplist = employeeService.findByEmployeeBetweenSalaryDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/groupCount")
	public ResponseEntity<Employee> findBygroupCount() {
		Map<String, Long> emplist = employeeService.findBygroupCountDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findByEvenNumber")
	public ResponseEntity<Employee> findByEmployeeIdEven() {
		List<Employee> emplist = employeeService.findByEmployeeIdEvenDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findByOddNumber")
	public ResponseEntity<Employee> findByEmployeeIdOdd() {
		List<Employee> emplist = employeeService.findByEmployeeIdOddDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/MaxSalary")
	public ResponseEntity<Employee> findByMaxSalary() {
		Employee emplist = employeeService.findByMaxSalaryDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/SecondHigestSalary")
	public ResponseEntity<Employee> SecondHigestSalary() {
		Employee secondHigestSalary = employeeService.secondHigestSalaryDeatails();
		return new ResponseEntity(secondHigestSalary, HttpStatus.OK);
	}

	@GetMapping("/SecondListSalary")
	public ResponseEntity<Employee> SecondListSalary() {
		Employee secondListSalary = employeeService.secondListSalaryDeatails();
		return new ResponseEntity(secondListSalary, HttpStatus.OK);
	}

	@GetMapping("/MinSalary")
	public ResponseEntity<Employee> findByMinSalary() {
		Employee emplist = employeeService.findByMinSalaryDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/SumSalary")
	public ResponseEntity<Employee> findBySumSalary() {
		double emplist = employeeService.findBySumSalaryDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/CountSalary")
	public ResponseEntity<Employee> findByCountSalary() {
		double emplist = employeeService.findByCountSalaryDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findParticularRecords")
	public ResponseEntity<Employee> findParticularRecords() {
		List<Employee> emplist = employeeService.findParticularRecordsDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findParticularFilters")
	public ResponseEntity<Employee> findParticularfilters() {
		List<Employee> empFilterlist = employeeService.findParticularRecordFileter();
		return new ResponseEntity(empFilterlist, HttpStatus.OK);
	}

	@GetMapping("/findParticularRecordsAsc")
	public ResponseEntity<Employee> findParticularRecordsAsc() {
		List<Employee> emplist = employeeService.findParticularRecordsAscsDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findParticularRecordsDsc")
	public ResponseEntity<Employee> findParticularRecordsDsc() {
		List<Employee> emplist = employeeService.findParticularRecordsDscDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/printDublicateRecordsInList")
	public ResponseEntity<Employee> printDublicateRecords() {
		Set<Employee> emplist = employeeService.printDublicateRecordsDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/printDublicatesInString")
	public ResponseEntity<Employee> printDublicatesInString() {
		List<String> dublicates = employeeService.printDublicatesInStringDeatails();
		return new ResponseEntity(dublicates, HttpStatus.OK);
	}

	@GetMapping("/printwithoutDublicateRecordsInList")
	public ResponseEntity<Employee> printWithoutDublicateRecords() {
		Set<Employee> emplist = employeeService.printWithoutDublicateRecordsDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/uniquerecordsInString")
	public ResponseEntity<Employee> uniquerecordsInString() {
		List<String> dublicates = employeeService.uniquerecordsInStringDeatails();
		return new ResponseEntity(dublicates, HttpStatus.OK);
	}

	@GetMapping("/mapNames")
	public ResponseEntity<Employee> mapNames() {
		List<String> emplist = employeeService.mapNamesDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/mapNamesToUpperCase")
	public ResponseEntity<Employee> mapNamesToUpperCase() {
		List<String> emplist = employeeService.mapNamesToUppercaseDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/findStringOccurence")
	public ResponseEntity<Employee> findStringOccurence() {
		Map<Character, Integer> emplist = employeeService.findStringOccurenceDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/groupBySalary")
	public ResponseEntity<Employee> groupBySalary() {
		Map<Object, List<Employee>> emplist = employeeService.groupBySalaryDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/groupByNames")
	public ResponseEntity<Employee> groupByNames() {
		Map<Object, List<Employee>> emplist = employeeService.groupByNamesDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/indexRanges/{fromIndex}/{toIndex}")
	public ResponseEntity<Employee> indexRanges(@PathVariable("fromIndex") int fromIndex,
			@PathVariable("toIndex") int toIndex) {
		List<Employee> emplist = employeeService.indexRangesDeatails(fromIndex, toIndex);
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/firstnonRepeactedCharacterInString")
	public ResponseEntity<Employee> firstnonRepeactedCharacterInString() {
		String emplist = employeeService.firstnonRepeactedCharacterInStringDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/firstRepeactedCharacterInString")
	public ResponseEntity<Employee> firstRepeactedCharacterInString() {
		String emplist = employeeService.firstRepeactedCharacterInStringDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/longestString")
	public ResponseEntity<Employee> longestString() {
		String emplist = employeeService.longestStringDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/leftRotationString")
	public ResponseEntity<Employee> leftRotationString() {
		String emplist = employeeService.leftRotationStringDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/rightRotationString")
	public ResponseEntity<Employee> rightRotationString() {
		String emplist = employeeService.rightRotationStringDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/smallestString")
	public ResponseEntity<Employee> smallestString() {
		String emplist = employeeService.smallestStringDeatails();
		return new ResponseEntity(emplist, HttpStatus.OK);
	}

	@GetMapping("/filterDepartmentIds")
	public ResponseEntity<Employee> filterDepartmentIds() {
		List<String> deptIds = employeeService.filterDepartmentIdsDeatails();
		return new ResponseEntity(deptIds, HttpStatus.OK);
	}

	@GetMapping("/stringReverseJava8")
	public ResponseEntity<Employee> stringReverseJava8() {
		String reverse = employeeService.stringReverseJava8Deatails();
		return new ResponseEntity(reverse, HttpStatus.OK);
	}

	@GetMapping("/joiningNames")
	public ResponseEntity<Employee> joiningNames() {
		String names = employeeService.joiningNamesDeatails();
		return new ResponseEntity(names, HttpStatus.OK);
	}

	@GetMapping("/report/pdf")
	public ResponseEntity<byte[]> downloadEmployeesPdf() {
		// Reuse existing service to fetch all employees as DTOs, then map to entity shape used by PdfGenerator
		var dtos = employeeService.findAllEmployeeDetails();
		var employees = dtos.stream().map(dto -> EmployeeMapper.INSTANCE.employeeDtoToEmployee(dto)).collect(Collectors.toList());
		byte[] pdf = PdfGenerator.generateEmployeesPdf(employees);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(pdf);
	}

	@GetMapping("/report/excel")
	public ResponseEntity<byte[]> downloadEmployeesExcel() {
		var dtos = employeeService.findAllEmployeeDetails();
		var employees = dtos.stream().map(dto -> EmployeeMapper.INSTANCE.employeeDtoToEmployee(dto)).collect(Collectors.toList());
		byte[] xlsx = ExcelGenerator.generateEmployeesExcel(employees);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx")
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(xlsx);
	}

	@PostMapping("/report/email")
	public ResponseEntity<String> emailEmployeesPdf( String to) {
		try {
			var dtos = employeeService.findAllEmployeeDetails();
			var employees = dtos.stream().map(dto -> EmployeeMapper.INSTANCE.employeeDtoToEmployee(dto)).collect(Collectors.toList());
			byte[] pdf = PdfGenerator.generateEmployeesPdf(employees);
			emailSender.sendPdf(to, "Employees Report", "<p>Please find attached the latest employees report.</p>", pdf, "employees.pdf");
			return ResponseEntity.ok("Email sent to " + to);
		} catch (MessagingException ex) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Email sending failed: " + ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
		}
	}

	@GetMapping("/listToSetConversion")
	public ResponseEntity<Employee> listToSetConversion() {
		Set<Employee> listToSetConversion = employeeService.listToSetCoversion();
		return new ResponseEntity(listToSetConversion, HttpStatus.OK);
	}

	@GetMapping("/listToMapConversion")
	public ResponseEntity<Employee> listToMapConversion() {
		Map<Integer, Employee> listToMapConversion = employeeService.listToMapCoversion();
		return new ResponseEntity(listToMapConversion, HttpStatus.OK);
	}

	@GetMapping("/setToListConversion")
	public ResponseEntity<Employee> setToListConversion() {
		List<Employee> setToListConversion = employeeService.setToListConversion();
		return new ResponseEntity(setToListConversion, HttpStatus.OK);
	}

	@GetMapping("/setToMapConversion")
	public ResponseEntity<Employee> setToMapConversion() {
		Map<Integer, Employee> setToMapConversion = employeeService.setToMapConversionDetails();
		return new ResponseEntity(setToMapConversion, HttpStatus.OK);
	}

	@GetMapping("/mapToListConversion")
	public ResponseEntity<Employee> mapToListConversion() {
		List<Entry<Integer, Employee>> mapToListConversion = employeeService.mapToListConversionDetails();
		return new ResponseEntity(mapToListConversion, HttpStatus.OK);
	}

	@GetMapping("/mapToSetConversion")
	public ResponseEntity<Employee> mapToSetConversion() {
		Set<Entry<Integer, Employee>> mapToSetConversion = employeeService.mapToSetConversionDetails();
		return new ResponseEntity(mapToSetConversion, HttpStatus.OK);
	}




	


}
