package com.epam.brest.courses.rest;

import com.epam.brest.courses.model.Employee;
import com.epam.brest.courses.rest.exception.ErrorResponse;
import com.epam.brest.courses.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Employee controller.
 */
@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    public static final String EMPLOYEE_NOT_FOUND = "employee.not_found";

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Goto employees list page.
     *
     * @return view name
     */
    @GetMapping(value = "/employees")
    public final Collection<Employee> employees(
            @RequestParam(value = "departmentId", required = false) Integer departmentId)
    {

        LOGGER.debug("employees(departmentId:{})", departmentId);
        if (departmentId != null) {
            return employeeService.findByDepartmentId(departmentId);
        }
        return employeeService.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Integer id) {

        LOGGER.debug("find employee by id({})", id);
        //return employeeService.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        Optional<Employee> optional = employeeService.findById(id);
        return optional.isPresent()
                ? new ResponseEntity<>(optional.get(), HttpStatus.OK)
                : new ResponseEntity(
                new ErrorResponse(EMPLOYEE_NOT_FOUND,
                        Arrays.asList("Employee not Found for id:" + id)),
                HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/employees", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createEmployee(@RequestBody Employee employee) {

        LOGGER.debug("createEmployee({})", employee);
        Integer id = employeeService.create(employee);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/employees", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateEmployee(@RequestBody Employee employee) {

        LOGGER.debug("updateEmployee({})", employee);
        int result = employeeService.update(employee);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/employees/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteEmployee(@PathVariable Integer id) {

        int result = employeeService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}