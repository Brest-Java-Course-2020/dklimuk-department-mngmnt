package com.epam.brest.courses.web_app.validators;


import com.epam.brest.courses.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import static com.epam.brest.courses.constants.DepartmentConstants.DEPARTMENT_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DepartmentValidatorTest {

    private Department department;

    private DepartmentValidator departmentValidator = new DepartmentValidator();
    private BindingResult result;

    @BeforeEach
    void setup() {
        department = Mockito.mock(Department.class);
        result = new BeanPropertyBindingResult(department, "department");
    }

    @Test
    void shouldRejectNullDepartmentName() {
        // given
        Mockito.when(department.getDepartmentName()).thenReturn(null);

        // when
        departmentValidator.validate(department, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyDepartmentName() {
        // given
        Mockito.when(department.getDepartmentName()).thenReturn("");

        // when
        departmentValidator.validate(department, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectLargeDepartmentName() {

        // given
        String filled = StringUtils.repeat("*", DEPARTMENT_NAME_SIZE + 1);
        Mockito.when(department.getDepartmentName()).thenReturn(filled);

        // when
        departmentValidator.validate(department, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidateDepartmentName() {

        // given
        String filled = StringUtils.repeat("*", DEPARTMENT_NAME_SIZE);
        Mockito.when(department.getDepartmentName()).thenReturn(filled);

        // when
        departmentValidator.validate(department, result);

        // then
        assertFalse(result.hasErrors());
    }
}