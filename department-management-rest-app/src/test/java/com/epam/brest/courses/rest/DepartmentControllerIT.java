package com.epam.brest.courses.rest;

import com.epam.brest.courses.model.Department;
import com.epam.brest.courses.rest.exception.CustomExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.DepartmentConstants.DEPARTMENT_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class DepartmentControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentControllerIT.class);

    public static final String DEPARTMENTS_ENDPOINT = "/departments";

    @Autowired
    private DepartmentController departmentController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcDepartmentService departmentService = new MockMvcDepartmentService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllDepartments() throws Exception {

        List<Department> departments = departmentService.findAll();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    @Test
    public void shouldCreateDepartment() throws Exception {
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentService.create(department);
        assertNotNull(id);
    }

    @Test
    public void shouldFindDepartmentById() throws Exception {

        // given
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentService.create(department);

        assertNotNull(id);


        // when
        Optional<Department> optionalDepartment = departmentService.findById(id);

        // then
        assertTrue(optionalDepartment.isPresent());
        assertEquals(optionalDepartment.get().getDepartmentId(), id);
        assertEquals(optionalDepartment.get().getDepartmentName(), department.getDepartmentName());
    }


    @Test
    public void shouldUpdateDepartment() throws Exception {

        // given
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentService.create(department);
        assertNotNull(id);

        Optional<Department> departmentOptional = departmentService.findById(id);
        assertTrue(departmentOptional.isPresent());

        departmentOptional.get().
                setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));

        // when
        int result = departmentService.update(departmentOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Department> updatedDepartmentOptional = departmentService.findById(id);
        assertTrue(updatedDepartmentOptional.isPresent());
        assertEquals(updatedDepartmentOptional.get().getDepartmentId(), id);
        assertEquals(updatedDepartmentOptional.get().getDepartmentName(),departmentOptional.get().getDepartmentName());

    }

    @Test
    public void shouldDeleteDepartment() throws Exception {
        // given
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentService.create(department);

        List<Department> departments = departmentService.findAll();
        assertNotNull(departments);

        // when
        int result = departmentService.delete(id);

        // then
        assertTrue(1 == result);

        List<Department> currentDepartments = departmentService.findAll();
        assertNotNull(currentDepartments);

        assertTrue(departments.size()-1 == currentDepartments.size());
    }

    class MockMvcDepartmentService {

        public List<Department> findAll() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(DEPARTMENTS_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Department>>() {});
        }

        public Optional<Department> findById(Integer departmentId) throws Exception {

            LOGGER.debug("findById({})", departmentId);
            MockHttpServletResponse response = mockMvc.perform(get(DEPARTMENTS_ENDPOINT + "/" + departmentId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Department.class));
        }

        public Integer create(Department department) throws Exception {

            LOGGER.debug("create({})", department);
            String json = objectMapper.writeValueAsString(department);
            MockHttpServletResponse response =
                    mockMvc.perform(post(DEPARTMENTS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public int update(Department department) {
            return 0;
        }

        public int delete(Integer departmentId) {
            return 0;
        }
    }
}