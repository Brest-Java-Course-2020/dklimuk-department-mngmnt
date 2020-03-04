package com.epam.brest.courses.service;

import com.epam.brest.courses.model.dto.DepartmentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
class DepartmentDtoServiceImplIT {

    @Autowired
    DepartmentDtoService departmentDtoService;

    @Test
    public void shouldFindAllWithAvgSalary() {
        List<DepartmentDto> departments = departmentDtoService.findAllWithAvgSalary();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
        assertTrue(departments.get(0).getAvgSalary().intValue() > 0);
    }
}