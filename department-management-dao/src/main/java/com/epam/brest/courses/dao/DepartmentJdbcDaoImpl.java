package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.DepartmentConstants.*;

public class DepartmentJdbcDaoImpl implements DepartmentDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentJdbcDaoImpl.class);

    @Value("${department.select}")
    private String selectSql;

    @Value("${department.create}")
    private String createSql;

    private final DepartmentRowMapper departmentRowMapper = new DepartmentRowMapper();
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> findAll() {

        LOGGER.trace("findAll()");
        return namedParameterJdbcTemplate.query(selectSql, departmentRowMapper);
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {

        LOGGER.debug("findById(id:{})", departmentId);
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer create(Department department) {

        LOGGER.debug("create(department:{})", department);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DEPARTMENT_NAME, department.getDepartmentName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(createSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int update(Department department) {

        LOGGER.debug("update(department:{})", department);
        throw new UnsupportedOperationException();

    }

    @Override
    public int delete(Integer departmentId) {

        LOGGER.debug("delete(departmentId:{})", departmentId);
        throw new UnsupportedOperationException();

    }

    private class DepartmentRowMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt(COLUMN_DEPARTMENT_ID));
            department.setDepartmentName(resultSet.getString(COLUMN_DEPARTMENT_NAME));
            return department;
        }
    }



}
