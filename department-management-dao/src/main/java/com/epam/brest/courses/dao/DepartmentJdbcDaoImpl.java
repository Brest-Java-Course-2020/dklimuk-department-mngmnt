package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentJdbcDaoImpl implements DepartmentDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentJdbcDaoImpl.class);

    @Value("${department.select}")
    private String selectSql;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> getDepartments() {
        LOGGER.trace("Get all departments");
        return namedParameterJdbcTemplate.query(selectSql, new DepartmentRowMapper());
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        return null;
    }

    @Override
    public Department addDepartment(Department department) {
        return null;
    }

    @Override
    public void updateDepartment(Department department) {

    }

    @Override
    public void deleteDepartment(Integer departmentId) {

    }

    private class DepartmentRowMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt("DEPARTMENTID"));
            department.setDepartmentName(resultSet.getString("DEPARTMENTNAME"));
            return department;
        }
    }



}
