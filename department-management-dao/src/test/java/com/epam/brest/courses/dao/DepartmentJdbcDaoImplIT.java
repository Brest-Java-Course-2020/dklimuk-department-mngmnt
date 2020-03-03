package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.DepartmentConstants.DEPARTMENT_NAME_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.commons.lang3.RandomStringUtils;

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
    public void shouldFindDepartmentById() {

        // given
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentDao.create(department);

        // when
        Optional<Department> optionalDepartment = departmentDao.findById(id);

        // then
        Assertions.assertTrue(optionalDepartment.isPresent());
        assertEquals(optionalDepartment.get().getDepartmentId(), id);
        assertEquals(optionalDepartment.get().getDepartmentName(), department.getDepartmentName());
    }

    @Test
   public void shouldCreateDepartment() {
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentDao.create(department);
        assertNotNull(id);

    }

    @Test
    public void updateDepartment() {
    }

    @Test
    public void shouldDeleteDepartment() {
        // given
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentDao.create(department);

        List<Department> departments = departmentDao.findAll();
        assertNotNull(departments);

        // when
        int result = departmentDao.delete(id);

        // then
        assertTrue(result == 1);

        List<Department> currentDepartments = departmentDao.findAll();
        assertNotNull(currentDepartments);

        assertTrue(departments.size()-1 == currentDepartments.size());
    }

}
