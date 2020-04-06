package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.dto.DepartmentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class DepartmentDtoServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoServiceRestTest.class);

    public static final String URL = "http://localhost:8088/department_dtos";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    DepartmentDtoServiceRest departmentDtoService;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        departmentDtoService = new DepartmentDtoServiceRest(URL, restTemplate);
    }

    @Test
    void shouldFindAllWithAvgSalary() throws Exception {
        LOGGER.debug("shouldFindAllDepartments()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<DepartmentDto> list = departmentDtoService.findAllWithAvgSalary();

        // then
        mockServer.verify();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        
    }

    private DepartmentDto create(int index) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(index);
        departmentDto.setDepartmentName("d" + index);
        departmentDto.setAvgSalary(BigDecimal.valueOf(100 + index));
        return departmentDto;
    }
}
