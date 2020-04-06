package com.epam.brest.courses.rest;

import com.epam.brest.courses.model.Employee;
import com.epam.brest.courses.rest.exception.CustomExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.EmployeeConstants.EMPLOYEE_EMAIL_SIZE;
import static com.epam.brest.courses.constants.EmployeeConstants.EMPLOYEE_FIRSTNAME_SIZE;
import static com.epam.brest.courses.constants.EmployeeConstants.EMPLOYEE_LASTNAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class EmployeeControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeControllerIT.class);

    public static final String EMPLOYEES_ENDPOINT = "/employees";

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcEmployeeService employeeService =  new MockMvcEmployeeService();


    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllEmployees() throws Exception {

        List<Employee> employees = employeeService.findAll();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void shouldFindEmployeesByDepartmentId() throws Exception {

        List<Employee> employees = employeeService.findByDepartmentId(1);
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
        for (Employee employee: employees) {
            assertEquals(Integer.valueOf(1), employee.getDepartmentId());
        }
    }

    @Test
    public void shouldFindEmployeeById() throws Exception {

        // given
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(100d)
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
    public void shouldCreateEmployee() throws Exception {
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(100d)
                .setDepartmentId(1);
        Integer id = employeeService.create(employee);
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {

        // given
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(100d)
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
    public void shouldDeleteEmployee() throws Exception {
        // given
        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(100d)
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

    ///////////////////////////////////////////////////////////////////

    private class MockMvcEmployeeService {

        public List<Employee> findAll() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(EMPLOYEES_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Employee>>() {});
        }

        private Optional<Employee> findById(Integer employeeId) throws Exception {

            LOGGER.debug("findById({})", employeeId);
            MockHttpServletResponse response = mockMvc.perform(get(EMPLOYEES_ENDPOINT + "/" + employeeId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Employee.class));
        }

        private Integer create(Employee employee) throws Exception {

            LOGGER.debug("create({})", employee);
            MockHttpServletResponse response =
                    mockMvc.perform(post(EMPLOYEES_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(employee))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Employee employee) throws Exception {

            LOGGER.debug("create({})", employee);
            MockHttpServletResponse response =
                    mockMvc.perform(put(EMPLOYEES_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(employee))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer employeeId) throws Exception {

            LOGGER.debug("delete(id:{})", employeeId);
            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(new StringBuilder(EMPLOYEES_ENDPOINT).append("/")
                            .append(employeeId).toString())
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public List<Employee> findByDepartmentId(int id) throws Exception {

            LOGGER.debug("findByDepartmentId({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(EMPLOYEES_ENDPOINT )
                    .param("departmentId", String.valueOf(id))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Employee>>() {});
        }
    }
}