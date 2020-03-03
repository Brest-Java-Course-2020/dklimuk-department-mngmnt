package com.epam.brest.courses.dao;


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
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
class DepartmentDtoDaoJdbcIT {

    @Autowired
    DepartmentDtoDao departmentDtoDao;

    @Test
    public void shouldFindAllWithAvgSalary() {
        List<DepartmentDto> departments = departmentDtoDao.findAllWithAvgSalary();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
        assertTrue(departments.get(0).getAvgSalary().intValue() > 0);
    }
}