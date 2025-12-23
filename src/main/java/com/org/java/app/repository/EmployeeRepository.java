package com.org.java.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.java.app.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	Optional<Employee> findByEmpId(int empId);
	
	List<Employee> findByEmpName(String empName);
	
	List<Employee> findByDeptName(String deptName);

	@Query("SELECT e FROM Employee e WHERE e.empName=:empName and e.deptName=:deptName")
	Employee findByEmpNameAndDeptName(@Param("empName") String empName,@Param("deptName") String deptName);
	
	@Query("SELECT e FROM Employee e WHERE e.empId=:empId and e.empName=:empName and e.deptName=:deptName")
	Employee findByEmpIdAndEmpNameAndDeptName(@Param("empId") int empId,@Param("empName") String empName, @Param("deptName") String deptName);

	


}
