package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Employee;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.EmployeeConstants.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
class EmployeeServiceImplIT {


    private final EmployeeService employeeService;

    @Autowired
    EmployeeServiceImplIT(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Test
    public void shouldFindAllEmployees() {

        List<Employee> employees = employeeService.findAll();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void shouldFindEmployeesByDepartmentId() {

        List<Employee> employees = employeeService.findByDepartmentId(1);
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void shouldFindEmployeeById() {

        // given
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(Double.valueOf(100))
                .setDepartmentId(1);
        Integer id = employeeService.create(employee);

        // when
        Optional<Employee> optionalEmployee = employeeService.findById(id);

        // then
        Assertions.assertTrue(optionalEmployee.isPresent());
        assertEquals(optionalEmployee.get().getEmployeeId(), id);
        assertEquals(optionalEmployee.get().getFirstname(), employee.getFirstname());
        assertEquals(optionalEmployee.get().getLastname(), employee.getLastname());
        assertEquals(optionalEmployee.get().getEmail(), employee.getEmail());
        assertEquals(optionalEmployee.get().getSalary(), employee.getSalary());
    }

    @Test
    public void shouldCreateEmployee() {
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(Double.valueOf(100))
                .setDepartmentId(1);
        Integer id = employeeService.create(employee);
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateEmployee() {

        // given
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(Double.valueOf(100))
                .setDepartmentId(1);
        Integer id = employeeService.create(employee);
        assertNotNull(id);

        Optional<Employee> employeeOptional = employeeService.findById(id);
        Assertions.assertTrue(employeeOptional.isPresent());

        employeeOptional.get().
                setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE));

        // when
        int result = employeeService.update(employeeOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Employee> updatedEmployeeOptional = employeeService.findById(id);
        Assertions.assertTrue(updatedEmployeeOptional.isPresent());
        assertEquals(updatedEmployeeOptional.get().getEmployeeId(), id);
        assertEquals(updatedEmployeeOptional.get().getFirstname(), employeeOptional.get().getFirstname());

    }

    @Test
    public void shouldDeleteEmployee() {
        // given
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(Double.valueOf(100))
                .setDepartmentId(1);
        Integer id = employeeService.create(employee);

        List<Employee> employees = employeeService.findAll();
        assertNotNull(employees);

        // when
        int result = employeeService.delete(id);

        // then
        assertTrue(1 == result);

        List<Employee> currentEmployees = employeeService.findAll();
        assertNotNull(currentEmployees);

        assertTrue(employees.size()-1 == currentEmployees.size());
    }

}