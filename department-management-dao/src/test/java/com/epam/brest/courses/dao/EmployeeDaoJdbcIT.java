package com.epam.brest.courses.dao;

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
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class EmployeeDaoJdbcIT {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeDaoJdbcIT(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Test
    public void shouldFindAllEmployees() {

        List<Employee> employees = employeeDao.findAll();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void shouldFindEmployeesByDepartmentId() {

        List<Employee> employees = employeeDao.findByDepartmentId(1);
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
                .setSalary(100d)
                .setDepartmentId(1);
        Integer id = employeeDao.create(employee);

        // when
        Optional<Employee> optionalEmployee = employeeDao.findById(id);

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
                .setSalary(100d)
                .setDepartmentId(1);
        Integer id = employeeDao.create(employee);
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateEmployee() {

        // given
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(100d)
                .setDepartmentId(1);
        Integer id = employeeDao.create(employee);
        assertNotNull(id);

        Optional<Employee> employeeOptional = employeeDao.findById(id);
        Assertions.assertTrue(employeeOptional.isPresent());

        employeeOptional.get().
                setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE));

        // when
        int result = employeeDao.update(employeeOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Employee> updatedEmployeeOptional = employeeDao.findById(id);
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
                .setSalary(100d)
                .setDepartmentId(1);
        Integer id = employeeDao.create(employee);

        List<Employee> employees = employeeDao.findAll();
        assertNotNull(employees);

        // when
        int result = employeeDao.delete(id);

        // then
        assertTrue(1 == result);

        List<Employee> currentEmployees = employeeDao.findAll();
        assertNotNull(currentEmployees);

        assertTrue(employees.size()-1 == currentEmployees.size());
    }

}
