package com.restful.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restful.model.Employee;
import com.restful.service.EmployeeService;

/**
 * 
 * @author Gautam Kumar
 *
 */

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	final static Logger logger = Logger.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService empService;
	

	/** REST service for AddEmployee  **/

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST,
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addEmployee(@RequestParam("firstname")String firstname,
			@RequestParam("lastname")String lastname,
			@RequestParam("salary")Integer salary,
	        @RequestParam("designation")String designation)
			
	{
		Employee emp=new Employee();
		emp.setDesignation(designation);
		emp.setSalary(salary);
		emp.setLastname(lastname);
		emp.setFirstname(firstname);
		empService.save(emp);
		logger.debug("Added:: " + emp);
		return new ResponseEntity<String>("added successfully", HttpStatus.CREATED);
	}

	
	/** REST service for UpdateEmployee **/

	@RequestMapping(value = "/updateEmployee", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateEmployee(@RequestParam ("firstname")Employee employee) {
		Employee existingEmp = empService.getById(employee.getId());
		if (existingEmp == null) {
			logger.debug("Employee with id " + employee.getId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			empService.save(employee);
			logger.info("Record Updated Successfully");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	/**  REST service for GetEmployee  **/

	@RequestMapping(value = "/getEmployee", method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmployee(@RequestParam("id") Long id) {
		Employee employee = empService.getById(id);
		if (employee == null) {
			logger.debug("Employee with id " + id + " does not exists");
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Employee:: " + employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	/** REST service for GetAllEmployees  **/
	
	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = empService.getAll();
		if (employees.isEmpty()) {
			logger.debug("Employees does not exists");
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + employees.size() + " Employees");
		logger.debug(Arrays.toString(employees.toArray()));
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

	/** REST service for DeleteEmployee **/
	 
	@RequestMapping(value = "/deleteEmployee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
		Employee employee = empService.getById(id);
		if (employee == null) {
			logger.debug("Employee with id " + id + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			empService.delete(id);
			logger.debug("Employee with id " + id + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

}
