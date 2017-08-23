package com.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restful.model.Employee;
/**
 * 
 * @author Gautam Kumar
 *
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
