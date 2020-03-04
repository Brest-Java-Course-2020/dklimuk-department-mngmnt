package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Department;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.DepartmentConstants.DEPARTMENT_NAME_SIZE;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
class DepartmentServiceImplIT {

    @Autowired
    DepartmentService departmentService;

    @Test
    public void shouldFindAllDepartments() {

        List<Department> departments = departmentService.findAll();
        assertNotNull(departments);
        Assert.assertTrue(departments.size() > 0);
    }

    @Test
    public void shouldFindDepartmentById() {

        // given
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentService.create(department);

        // when
        Optional<Department> optionalDepartment = departmentService.findById(id);

        // then
        Assertions.assertTrue(optionalDepartment.isPresent());
        assertEquals(optionalDepartment.get().getDepartmentId(), id);
        assertEquals(optionalDepartment.get().getDepartmentName(), department.getDepartmentName());
    }

    @Test
    public void shouldCreateDepartment() {
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentService.create(department);
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateDepartment() {

        // given
        Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));
        Integer id = departmentService.create(department);
        assertNotNull(id);

        Optional<Department> departmentOptional = departmentService.findById(id);
        Assertions.assertTrue(departmentOptional.isPresent());

        departmentOptional.get().
                setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));

        // when
        int result = departmentService.update(departmentOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Department> updatedDepartmentOptional = departmentService.findById(id);
        Assertions.assertTrue(updatedDepartmentOptional.isPresent());
        assertEquals(updatedDepartmentOptional.get().getDepartmentId(), id);
        assertEquals(updatedDepartmentOptional.get().getDepartmentName(),departmentOptional.get().getDepartmentName());

    }

    @Test
    public void shouldDeleteDepartment() {
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

}