package com.epam.brest.courses.dao;
import com.epam.brest.courses.model.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@ExtendWith(MockitoExtension.class)
public class DepartmentJdbcDAOImplMockTest {

    @InjectMocks
    private DepartmentJdbcDaoImpl departmentDAO;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @AfterEach
    void after(){
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void getDepartments() {
        List<Department> departments = departmentDAO.getDepartments();
        assertNotNull(departments);
        verify(namedParameterJdbcTemplate).query(anyString(), any(RowMapper.class));
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