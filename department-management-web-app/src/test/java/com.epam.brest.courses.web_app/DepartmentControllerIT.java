package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Department;
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

import java.math.BigDecimal;

import static com.epam.brest.courses.constants.DepartmentConstants.DEPARTMENT_NAME_SIZE;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
class DepartmentControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnDepartmentsPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/departments")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("departments"))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(1)),
                                hasProperty("departmentName", is("DEV")),
                                hasProperty("avgSalary", is(BigDecimal.valueOf(150)))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(2)),
                                hasProperty("departmentName", is("ACCOUNTING")),
                                hasProperty("avgSalary", is(BigDecimal.valueOf(400)))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(3)),
                                hasProperty("departmentName", is("MANAGEMENT")),
                                hasProperty("avgSalary", is(nullValue()))
                        )
                )));
    }
    @Test
    public void shouldOpenEditDepartmentPageById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("department"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("department", hasProperty("departmentId", is(1))))
                .andExpect(model().attribute("department", hasProperty("departmentName", is("DEV"))));
    }

    @Test
    public void shouldReturnToDepartmentsPageIfDepartmentNotFoundById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/99999")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(redirectedUrl("departments"));
    }

    @Test
    public void shouldUpdateDepartmentAfterEdit() throws Exception {

        Department department = new Department()
                .setDepartmentId(1)
                .setDepartmentName("test");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/department/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("departmentId", "1")
                        .param("departmentName", "test")
                        .sessionAttr("department", department)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    public void shouldRejectUpdateDepartmentOnLargeDepartmentName() throws Exception {

        Department department = new Department()
                .setDepartmentId(1)
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE + 1));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/department/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("departmentId", "1")
                        .param("departmentName", department.getDepartmentName())
                        .sessionAttr("department", department)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("department"));
    }

    @Test
    public void shouldOpenNewDepartmentPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/department")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("department"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("department", isA(Department.class)));
    }
    @Test
    public void shouldAddNewDepartment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/department")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("departmentName", "test")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));
    }
    @Test
    public void shouldRejectAddDepartmentOnLargeDepartmentName() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/department")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("departmentName", RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE + 1))
        ).andExpect(status().isOk())
                .andExpect(view().name("department"));
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/3/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));
    }
}