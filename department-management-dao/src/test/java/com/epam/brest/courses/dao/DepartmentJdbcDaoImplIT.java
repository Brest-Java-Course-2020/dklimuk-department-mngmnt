package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class DepartmentJdbcDaoImplIT {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void shouldFindAllDepartments() {

        List<Department> departments = departmentDao.findAll();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    @Test
    public void getDepartmentById() {
    }

    @Test
    public void addDepartment() {
    }

    @Test
    public void updateDepartment() {
    }

    @Test
    public void deleteDepartment() {
    }

}
