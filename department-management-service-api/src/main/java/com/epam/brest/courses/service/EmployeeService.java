package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    /**
     * Get all employees.
     *
     * @return list of all employees
     */
    List<Employee> findAll();

    /**
     * Get all employees with specified department id.
     *
     * @param departmentId department id
     * @return list of employees by department id
     */
    List<Employee> findByDepartmentId(Integer departmentId);

    /**
     * Get employee with specified id.
     *
     * @param employeeId employee id
     * @return employee by id
     */
    Optional<Employee> findById(Integer employeeId);

    /**
     * Persist new employee.
     *
     * @param employee employee
     * @return persisted employee id.
     */
    Integer create(Employee employee);

    /**
     * Update employee.
     *
     * @param employee employee
     * @return number of updated records in the database.
     */
    int update(Employee employee);

    /**
     * Delete employee with specified id.
     *
     * @param employeeId department id
     * @return number of updated records in the database.
     */
    int delete(Integer employeeId);

}