package com.org.java.app.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.org.java.app.dto.EmployeeDto;
import com.org.java.app.entity.Employee;
import com.org.java.app.exception.NoDataAvailableException;
import com.org.java.app.repository.EmployeeRepository;
import com.org.java.app.serviceimpl.EmployeeImplService;

@ExtendWith(MockitoExtension.class)
class EmployeeImplServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeImplService employeeImplService;

	// Basic CRUD Tests
	
	  @Test void saveEmployeeDetails_returnsSavedEmployee() { EmployeeDto dto = new
	  EmployeeDto(1, "John", 30, 50000.0, "Engineer", "Java", "Tech", 1234567890L,
	  "john@test.com", 10, "IT"); Employee employee = new Employee(1, "John", 30,
	  50000.0, "Engineer", "Java", "Tech", 1234567890L, "john@test.com", 10, "IT");
	  
	  given(employeeRepository.save(any(Employee.class))).willReturn(employee);
	  
	  Employee result = employeeImplService.saveEmployeeDetails(dto);
	  
	  assertThat(result.getEmpName()).isEqualTo("John");
	  verify(employeeRepository).save(any(Employee.class)); }
	 

	@Test
	void updateEmployeeDetails_returnsUpdatedEmployee() {
		Employee employee = new Employee(1, "John", 30, 50000.0, "Engineer", "Java", "Tech", 1234567890L, "john@test.com", 10, "IT");
		given(employeeRepository.save(any(Employee.class))).willReturn(employee);
		
		Employee result = employeeImplService.updateEmployeeDetails(employee);
		
		assertThat(result.getEmpName()).isEqualTo("John");
		verify(employeeRepository).save(employee);
	}

	@Test
	void deleteEmployeeDetails_whenEmployeeExists_returnsDeletedEmployee() {
		Employee employee = new Employee(1, "John", 30, 50000.0, "Engineer", "Java", "Tech", 1234567890L, "john@test.com", 10, "IT");
		List<Employee> employees = Arrays.asList(employee);
		given(employeeRepository.findAll()).willReturn(employees);
		
		Employee result = employeeImplService.deleteEmployeeDetails(employee);
		
		assertThat(result.getEmpName()).isEqualTo("John");
		verify(employeeRepository).delete(employee);
	}

	@Test
	void deleteEmployeeDetails_whenEmployeeNotExists_throwsException() {
		Employee employee = new Employee(999, "John", 30, 50000.0, "Engineer", "Java", "Tech", 1234567890L, "john@test.com", 10, "IT");
		List<Employee> employees = Arrays.asList(new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"));
		given(employeeRepository.findAll()).willReturn(employees);
		
		assertThatThrownBy(() -> employeeImplService.deleteEmployeeDetails(employee))
			.isInstanceOf(NoDataAvailableException.class);
	}

	@Test
	void findByIdEmployeeDetails_whenEmployeeExists_returnsEmployee() {
		Employee employee = new Employee(1, "John", 30, 50000.0, "Engineer", "Java", "Tech", 1234567890L, "john@test.com", 10, "IT");
		given(employeeRepository.findByEmpId(1)).willReturn(Optional.of(employee));
		
		Employee result = employeeImplService.findByIdEmployeeDetails(1);
		
		assertThat(result.getEmpName()).isEqualTo("John");
	}

	@Test
	void findByIdEmployeeDetails_whenEmployeeNotFound_throwsException() {
		given(employeeRepository.findByEmpId(999)).willReturn(Optional.empty());
		
		assertThatThrownBy(() -> employeeImplService.findByIdEmployeeDetails(999))
			.isInstanceOf(NoDataAvailableException.class)
			.hasMessageContaining("No data prasent given id::999");
	}

	
	  @Test void findAllEmployeeDetails_returnsAllEmployees() { List<Employee>
	  employees = Arrays.asList( new Employee(1, "John", 30, 50000.0, "Engineer",
	  "Java", "Tech", 1234567890L, "john@test.com", 10, "IT"), new Employee(2,
	  "Jane", 25, 45000.0, "Developer", "Python", "Tech", 1234567891L,
	  "jane@test.com", 20, "HR") );
	  given(employeeRepository.findAll()).willReturn(employees);
	  
	  List<EmployeeDto> result = employeeImplService.findAllEmployeeDetails();
	  
	  assertThat(result).hasSize(2);
	  assertThat(result.get(0).getEmpName()).isEqualTo("John"); }
	 

	// Salary Calculation Tests
	@Test
	void findBySumSalaryDeatails_returnsSumOfSalaries() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.5, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "C", 27, 300.25, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		double sum = employeeImplService.findBySumSalaryDeatails();

		assertThat(sum).isEqualTo(600.75);
	}

	@Test
	void findByCountSalaryDeatails_returnsCountOfEmployees() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.5, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findAll()).willReturn(employees);

		double count = employeeImplService.findByCountSalaryDeatails();

		assertThat(count).isEqualTo(2.0);
	}

	@Test
	void findByMaxSalaryDeatails_returnsEmployeeWithMaxSalary() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.5, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findAll()).willReturn(employees);

		Employee result = employeeImplService.findByMaxSalaryDeatails();

		assertThat(result.getSalary()).isEqualTo(200.5);
	}

	@Test
	void findByMinSalaryDeatails_returnsEmployeeWithMinSalary() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.5, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findAll()).willReturn(employees);

		Employee result = employeeImplService.findByMinSalaryDeatails();

		assertThat(result.getSalary()).isEqualTo(100.0);
	}

	// Sorting Tests
	@Test
	void findByEmployeeSalaryAscDeatails_whenEmployeesExist_returnsSortedList() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 200.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 100.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<Employee> result = employeeImplService.findByEmployeeSalaryAscDeatails();

		assertThat(result.get(0).getSalary()).isEqualTo(100.0);
		assertThat(result.get(1).getSalary()).isEqualTo(200.0);
	}

	@Test
	void findByEmployeeSalaryAscDeatails_whenNoEmployees_throwsException() {
		given(employeeRepository.findAll()).willReturn(Collections.emptyList());

		assertThatThrownBy(() -> employeeImplService.findByEmployeeSalaryAscDeatails())
			.isInstanceOf(NoDataAvailableException.class);
	}

	@Test
	void findByEmployeeSalaryDscDeatails_whenEmployeesExist_returnsSortedList() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<Employee> result = employeeImplService.findByEmployeeSalaryDscDeatails();

		assertThat(result.get(0).getSalary()).isEqualTo(200.0);
		assertThat(result.get(1).getSalary()).isEqualTo(100.0);
	}

	@Test
	void findByEmployeeSalaryDscDeatails_whenNoEmployees_throwsException() {
		given(employeeRepository.findAll()).willReturn(Collections.emptyList());

		assertThatThrownBy(() -> employeeImplService.findByEmployeeSalaryDscDeatails())
			.isInstanceOf(NoDataAvailableException.class);
	}

	// ID Filtering Tests
	@Test
	void findByEmployeeIdEvenDeatails_returnsEvenIdEmployees() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "C", 27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<Employee> result = employeeImplService.findByEmployeeIdEvenDeatails();

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getEmpId()).isEqualTo(2);
	}

	@Test
	void findByEmployeeIdEvenDeatails_whenNoEvenIds_throwsException() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(3, "C", 27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));
		given(employeeRepository.findAll()).willReturn(employees);

		assertThatThrownBy(() -> employeeImplService.findByEmployeeIdEvenDeatails())
			.isInstanceOf(NoDataAvailableException.class);
	}

	@Test
	void findByEmployeeIdOddDeatails_returnsOddIdEmployees() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "C", 27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<Employee> result = employeeImplService.findByEmployeeIdOddDeatails();

		assertThat(result).hasSize(2);
		assertThat(result.get(0).getEmpId()).isEqualTo(1);
		assertThat(result.get(1).getEmpId()).isEqualTo(3);
	}

	@Test
	void findByEmployeeIdOddDeatails_whenNoOddIds_throwsException() {
		List<Employee> employees = Arrays.asList(
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(4, "D", 28, 400.0, "Dev", "Java", "Tech", 4L, "d@x.com", 40, "IT"));
		given(employeeRepository.findAll()).willReturn(employees);

		assertThatThrownBy(() -> employeeImplService.findByEmployeeIdOddDeatails())
			.isInstanceOf(NoDataAvailableException.class);
	}

	// Department Tests
	@Test
	void findByEmployedeptNameDeatails_whenDepartmentExists_returnsEmployees() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "IT"));
		given(employeeRepository.findByDeptName("IT")).willReturn(employees);

		List<Employee> result = employeeImplService.findByEmployedeptNameDeatails("IT");

		assertThat(result).hasSize(2);
		assertThat(result.get(0).getDeptName()).isEqualTo("IT");
	}

	@Test
	void findByEmployedeptNameDeatails_whenDepartmentNotFound_throwsException() {
		given(employeeRepository.findByDeptName("NonExistent")).willReturn(Collections.emptyList());

		assertThatThrownBy(() -> employeeImplService.findByEmployedeptNameDeatails("NonExistent"))
			.isInstanceOf(NoDataAvailableException.class);
	}

	// Pagination Tests
	@Test
	void findParticularRecordsDeatails_returnsSkippedAndLimitedRecords() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "C", 27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"),
			new Employee(4, "D", 28, 400.0, "Dev", "Java", "Tech", 4L, "d@x.com", 40, "IT"),
			new Employee(5, "E", 29, 500.0, "Dev", "Java", "Tech", 5L, "e@x.com", 50, "HR"),
			new Employee(6, "F", 30, 600.0, "Dev", "Java", "Tech", 6L, "f@x.com", 60, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<Employee> result = employeeImplService.findParticularRecordsDeatails();

		assertThat(result).hasSize(4); // skip 2, limit 5
		assertThat(result.get(0).getEmpId()).isEqualTo(3); // first after skip
	}

	@Test
	void findParticularRecordsDeatails_whenNoEmployees_throwsException() {
		given(employeeRepository.findAll()).willReturn(Collections.emptyList());

		assertThatThrownBy(() -> employeeImplService.findParticularRecordsDeatails())
			.isInstanceOf(NoDataAvailableException.class);
	}

	// String Processing Tests
	@Test
	void mapNamesDeatails_returnsNamesInReverseOrder() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "Alice", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "Bob", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<String> result = employeeImplService.mapNamesDeatails();

		assertThat(result).containsExactly("Bob", "Alice");
	}

	@Test
	void groupBySalaryDeatails_returnsGroupedBySalary() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "C", 27, 100.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		Map<Object, List<Employee>> result = employeeImplService.groupBySalaryDeatails();

		assertThat(result.get(100.0)).hasSize(2);
		assertThat(result.get(200.0)).hasSize(1);
	}

	@Test
	void groupByNamesDeatails_returnsGroupedByName() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "John", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "Jane", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "John", 27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		Map<Object, List<Employee>> result = employeeImplService.groupByNamesDeatails();

		assertThat(result.get("John")).hasSize(2);
		assertThat(result.get("Jane")).hasSize(1);
	}

	// Collection Conversion Tests
	@Test
	void listToSetCoversion_returnsSetOfEmployees() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT")); // duplicate

		given(employeeRepository.findAll()).willReturn(employees);

		Set<Employee> result = employeeImplService.listToSetCoversion();

		assertThat(result).hasSize(1); // duplicates removed
	}

	@Test
	void listToMapCoversion_returnsMapWithEmpIdAsKey() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findAll()).willReturn(employees);

		Map<Integer, Employee> result = employeeImplService.listToMapCoversion();

		assertThat(result).hasSize(2);
		assertThat(result.get(1).getEmpName()).isEqualTo("A");
		assertThat(result.get(2).getEmpName()).isEqualTo("B");
	}

	// String Processing Tests
	@Test
	void stringReverseJava8Deatails_returnsReversedString() {
		String result = employeeImplService.stringReverseJava8Deatails();

		// "SREENIVASARAO" reversed should be "OARASANIVEERS"
		assertThat(result).isEqualTo("OARASAVINEERS");
	}

	@Test
	void leftRotationStringDeatails_returnsLeftRotatedString() {
		String result = employeeImplService.leftRotationStringDeatails();

		assertThat(result).isEqualTo("nivasaraosree");
	}

	@Test
	void rightRotationStringDeatails_returnsRightRotatedString() {
		String result = employeeImplService.rightRotationStringDeatails();

		assertThat(result).isEqualTo("raosreenivasa");
	}

	// Advanced Query Tests
	@Test
	void secondHigestSalaryDeatails_returnsSecondHighestSalaryEmployee() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 300.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "C", 27, 200.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		Employee result = employeeImplService.secondHigestSalaryDeatails();

		assertThat(result.getSalary()).isEqualTo(200.0); // second highest
	}

	@Test
	void secondListSalaryDeatails_returnsSecondLowestSalaryEmployee() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 300.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "C", 27, 200.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		Employee result = employeeImplService.secondListSalaryDeatails();

		assertThat(result.getSalary()).isEqualTo(200.0); // second lowest
	}

	// Filtering Tests
	
	  @Test void findByEmployeeBetweenSalaryDeatails_returnsFilteredEmployees() {
	  List<Employee> employees = Arrays.asList( new Employee(1, "A", 25, 50000.0,
	  "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"), new Employee(2, "B", 26,
	  70000.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"), new Employee(3,
	  "C", 27, 100000.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));
	  
	  given(employeeRepository.findAll()).willReturn(employees);
	  
	  List<EmployeeDto> result =
	  employeeImplService.findByEmployeeBetweenSalaryDeatails();
	  
	  assertThat(result).hasSize(1);
	  assertThat(result.get(0).getEmpName()).isEqualTo("B"); }
	 

	@Test
	void mapNamesToUppercaseDeatails_returnsUppercaseNames() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "alice", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "bob", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<String> result = employeeImplService.mapNamesToUppercaseDeatails();

		assertThat(result).containsExactly("ALICE", "BOB");
	}

	// Optional Tests
	@Test
	void findByEmployeeIdDeatails_whenEmployeeExists_returnsOptionalEmployee() {
		Employee employee = new Employee(1, "John", 30, 50000.0, "Engineer", "Java", "Tech", 1234567890L, "john@test.com", 10, "IT");
		given(employeeRepository.findByEmpId(1)).willReturn(Optional.of(employee));

		Optional<Employee> result = employeeImplService.findByEmployeeIdDeatails(1);

		assertThat(result).isPresent();
		assertThat(result.get().getEmpName()).isEqualTo("John");
	}

	@Test
	void findByEmployeeIdDeatails_whenEmployeeNotFound_returnsEmptyOptional() {
		given(employeeRepository.findByEmpId(999)).willReturn(Optional.empty());

		Optional<Employee> result = employeeImplService.findByEmployeeIdDeatails(999);

		assertThat(result).isEmpty();
	}

	// Department Count Tests
	
	  @Test void findBygroupCountDeatails_returnsDepartmentCountMap() {
	  List<Employee> employees = Arrays.asList( new Employee(1, "A", 25, 100.0,
	  "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"), new Employee(2, "B", 26,
	  200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"), new Employee(3, "C",
	  27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "IT"));
	  
	  given(employeeRepository.findAll()).willReturn(employees);
	  
	  Map<String, Long> result = employeeImplService.findBygroupCountDeatails();
	  
	  assertThat(result.get("IT")).isEqualTo(2L);
	  assertThat(result.get("HR")).isEqualTo(1L); }
	 

	// Name Search Tests
	@Test
	void findByName_whenEmployeeExists_returnsEmployees() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "John", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "John", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findByEmpName("John")).willReturn(employees);

		List<Employee> result = employeeImplService.findByName("John");

		assertThat(result).hasSize(2);
		assertThat(result.get(0).getEmpName()).isEqualTo("John");
	}

	@Test
	void findByName_whenEmployeeNotFound_throwsException() {
		given(employeeRepository.findByEmpName("NonExistent")).willReturn(Collections.emptyList());

		assertThatThrownBy(() -> employeeImplService.findByName("NonExistent"))
			.isInstanceOf(NoDataAvailableException.class);
	}

	// String Analysis Tests (with proper null handling)
	@Test
	void firstnonRepeactedCharacterInStringDeatails_returnsFirstNonRepeatedCharacter() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "suresh", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"));

		given(employeeRepository.findAll()).willReturn(employees);

		String result = employeeImplService.firstnonRepeactedCharacterInStringDeatails();

		// In "suresh": s(2), u(1), r(1), e(1), h(1) - first non-repeated is 'u'
		assertThat(result).isEqualTo("u");
	}

	@Test
	void firstRepeactedCharacterInStringDeatails_returnsFirstRepeatedCharacter() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "suresh", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"));

		given(employeeRepository.findAll()).willReturn(employees);

		String result = employeeImplService.firstRepeactedCharacterInStringDeatails();

		assertThat(result).isEqualTo("s"); // first repeated character in "suresh"
	}

	@Test
	void printDublicatesInStringDeatails_returnsDuplicateCharacters() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "suresh", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<String> result = employeeImplService.printDublicatesInStringDeatails();

		assertThat(result).contains("s"); // 's' is duplicated in "suresh"
	}

	@Test
	void uniquerecordsInStringDeatails_returnsUniqueCharacters() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "suresh", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<String> result = employeeImplService.uniquerecordsInStringDeatails();

		assertThat(result).contains("u", "r", "e", "h"); // unique characters in "suresh"
	}

	@Test
	void longestStringDeatails_returnsLongestName() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "John", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "Alice", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "Bob", 27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		String result = employeeImplService.longestStringDeatails();

		assertThat(result).isEqualTo("Alice"); // longest name
	}

	@Test
	void smallestStringDeatails_returnsShortestName() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "John", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "Alice", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "Bob", 27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"));

		given(employeeRepository.findAll()).willReturn(employees);

		String result = employeeImplService.smallestStringDeatails();

		assertThat(result).isEqualTo("Bob"); // shortest name
	}

	// Additional Utility Tests
	@Test
	void joiningNamesDeatails_returnsCommaSeparatedNames() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "Alice", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "Bob", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"));

		given(employeeRepository.findAll()).willReturn(employees);

		String result = employeeImplService.joiningNamesDeatails();

		assertThat(result).isEqualTo("Alice,Bob");
	}

	@Test
	void indexRangesDeatails_returnsSubListInRange() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "C", 27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"),
			new Employee(4, "D", 28, 400.0, "Dev", "Java", "Tech", 4L, "d@x.com", 40, "IT"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<Employee> result = employeeImplService.indexRangesDeatails(1, 3);

		assertThat(result).hasSize(2);
		assertThat(result.get(0).getEmpId()).isEqualTo(2);
		assertThat(result.get(1).getEmpId()).isEqualTo(3);
	}

	// Filter Department IDs Test
	@Test
	void filterDepartmentIdsDeatails_returnsFilteredDepartmentIds() {
		List<Employee> employees = Arrays.asList(
			new Employee(1, "A", 25, 100.0, "Dev", "Java", "Tech", 1L, "a@x.com", 10, "IT"),
			new Employee(2, "B", 26, 200.0, "Dev", "Java", "Tech", 2L, "b@x.com", 20, "HR"),
			new Employee(3, "C", 27, 300.0, "Dev", "Java", "Tech", 3L, "c@x.com", 30, "FIN"),
			new Employee(4, "D", 28, 400.0, "Dev", "Java", "Tech", 4L, "d@x.com", 40, "IT"));

		given(employeeRepository.findAll()).willReturn(employees);

		List<String> result = employeeImplService.filterDepartmentIdsDeatails();

		assertThat(result).containsExactly("20"); // department IDs starting with "2"
		assertThat(result).doesNotContain("10", "30", "40"); // should not contain IDs not starting with "2"
	}
}
