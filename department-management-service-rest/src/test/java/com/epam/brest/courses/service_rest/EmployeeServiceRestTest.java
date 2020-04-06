package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.EmployeeConstants.EMPLOYEE_FIRSTNAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class EmployeeServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceRestTest.class);

    public static final String URL = "http://localhost:8088/employees";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    EmployeeServiceRest employeeService;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        employeeService = new EmployeeServiceRest(URL, restTemplate);
    }

    @Test
    public void shouldFindAllEmployees() throws Exception {

        LOGGER.debug("shouldFindAllEmployees()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Employee> employees = employeeService.findAll();

        // then
        mockServer.verify();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    void shouldFindByDepartmentId() throws Exception {

        LOGGER.debug("shouldFindByDepartmentId()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "?departmentId=1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Employee> employees = employeeService.findByDepartmentId(1);

        // then
        mockServer.verify();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void shouldCreateEmployee() throws Exception {

        LOGGER.debug("shouldCreateEmployee()");
        // given
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = employeeService.create(employee);

        // then
        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    public void shouldFindEmployeeById() throws Exception {

        // given
        Integer id = 1;
        Employee employee = new Employee()
                .setEmployeeId(id)
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee))
                );

        // when
        Optional<Employee> optionalEmployee = employeeService.findById(id);

        // then
        mockServer.verify();
        assertTrue(optionalEmployee.isPresent());
        assertEquals(optionalEmployee.get().getEmployeeId(), id);
        assertEquals(optionalEmployee.get().getFirstname(), employee.getFirstname());
        assertEquals(optionalEmployee.get().getLastname(), employee.getLastname());
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {

        // given
        Integer id = 1;
        Employee employee = new Employee()
                .setEmployeeId(id)
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee))
                );

        // when
        int result = employeeService.update(employee);
        Optional<Employee> updatedEmployeeOptional = employeeService.findById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertTrue(updatedEmployeeOptional.isPresent());
        assertEquals(updatedEmployeeOptional.get().getEmployeeId(), id);
        assertEquals(updatedEmployeeOptional.get().getFirstname(), employee.getFirstname());
        assertEquals(updatedEmployeeOptional.get().getLastname(), employee.getLastname());
    }

    @Test
    public void shouldDeleteEmployee() throws Exception {

        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        int result = employeeService.delete(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }

    private Employee create(int index) {
        Employee employee = new Employee();
        employee.setEmployeeId(index);
        employee.setFirstname("f" + index);
        employee.setLastname("l" + index);
        return employee;
    }
}
