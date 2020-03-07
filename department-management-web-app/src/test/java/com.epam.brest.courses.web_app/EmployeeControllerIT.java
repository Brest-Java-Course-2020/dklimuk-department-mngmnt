package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Employee;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.brest.courses.constants.EmployeeConstants.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
class EmployeeControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnEmployeesPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("employees"))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("employeeId", is(1)),
                                hasProperty("firstname", is("FUSER10")),
                                hasProperty("lastname", is("LUSER10")),
                                hasProperty("email", is("email10@mail.com")),
                                hasProperty("salary", is(Double.valueOf(100)))
                        )
                )))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("employeeId", is(2)),
                                hasProperty("firstname", is("FUSER11")),
                                hasProperty("lastname", is("LUSER11")),
                                hasProperty("email", is("email11@mail.com")),
                                hasProperty("salary", is(Double.valueOf(200)))
                        )
                )))
        ;
    }

    @Test
    public void shouldOpenEditEmployeePageById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("employee"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("employee", hasProperty("employeeId", is(1))))
                .andExpect(model().attribute("employee", hasProperty("firstname", is("FUSER10"))))
                .andExpect(model().attribute("employee", hasProperty("lastname", is("LUSER10"))))
                .andExpect(model().attribute("employee", hasProperty("email", is("email10@mail.com"))))
                .andExpect(model().attribute("employee", hasProperty("salary", is(Double.valueOf(100)))))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(1)),
                                hasProperty("departmentName", is("DEV"))
                        )
                )));
    }

    @Test
    public void shouldReturnToEmployeesPageIfEmployeeNotFoundById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee/99999")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("employees"));
    }

    @Test
    public void shouldUpdateEmployeeAfterEdit() throws Exception {

        Employee employee = new Employee()
                .setEmployeeId(1)
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(Double.valueOf(100))
                .setDepartmentId(1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/employee/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("employeeId", String.valueOf(employee.getDepartmentId()))
                        .param("firstname", employee.getFirstname())
                        .param("lastname", employee.getLastname())
                        .param("email", employee.getEmail())
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("departmentId", String.valueOf(employee.getDepartmentId()))
                        .sessionAttr("employee", employee)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/employees"))
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    public void shouldOpenNewEmployeePage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("employee"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("employee", isA(Employee.class)))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(1)),
                                hasProperty("departmentName", is("DEV"))
                        )
                )));
    }

    @Test
    public void shouldAddNewEmployee() throws Exception {

        Employee employee = new Employee()
                .setFirstname(RandomStringUtils.randomAlphabetic(EMPLOYEE_FIRSTNAME_SIZE))
                .setLastname(RandomStringUtils.randomAlphabetic(EMPLOYEE_LASTNAME_SIZE))
                .setEmail(RandomStringUtils.randomAlphabetic(EMPLOYEE_EMAIL_SIZE))
                .setSalary(Double.valueOf(100))
                .setDepartmentId(1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/employee")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstname", employee.getFirstname())
                        .param("lastname", employee.getLastname())
                        .param("email", employee.getEmail())
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("departmentId", String.valueOf(employee.getDepartmentId()))
                        .sessionAttr("employee", employee)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/employees"))
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    public void shouldDeleteEmployee() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee/3/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/employees"))
                .andExpect(redirectedUrl("/employees"));
    }
}