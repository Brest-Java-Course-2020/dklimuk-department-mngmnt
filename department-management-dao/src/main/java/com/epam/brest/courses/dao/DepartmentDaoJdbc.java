package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.brest.courses.constants.DepartmentConstants.*;

public class DepartmentDaoJdbc implements DepartmentDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoJdbc.class);

    @Value("${department.select}")
    private String selectSql;

    @Value("${department.create}")
    private String createSql;

    @Value("${department.update}")
    private String updateSql;

    @Value("${department.findById}")
    private String findByIdSql;

    @Value("${department.delete}")
    private String deleteSql;

    private static final String CHECK_COUNT_NAME = "select count(department_id) from department where lower(department_name) = lower(:departmentName)";


    private final DepartmentRowMapper departmentRowMapper = new DepartmentRowMapper();
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
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
        SqlParameterSource namedParameters = new MapSqlParameterSource(DEPARTMENT_ID, departmentId);
        // Note: don't use queryForObject to reduce exception handling
        // there is possible solution with BeanPropertyRowMapper.newInstance(Department.class)
        List<Department> results = namedParameterJdbcTemplate.query(findByIdSql, namedParameters, departmentRowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    @Override
    public Integer create(Department department) {

        LOGGER.debug("create(department:{})", department);

        if (!isNameUnique(department)) {
            throw new IllegalArgumentException("Department with the same name already exsists in DB.");
        }
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DEPARTMENT_NAME, department.getDepartmentName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(createSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    private boolean isNameUnique(Department department) {
        return namedParameterJdbcTemplate.queryForObject(CHECK_COUNT_NAME,
                new MapSqlParameterSource(DEPARTMENT_NAME, department.getDepartmentName()),
                Integer.class) == 0;
    }

    @Override
    public int update(Department department) {

        LOGGER.debug("update(department:{})", department);
        Map<String, Object> params = new HashMap<>();
        params.put(DEPARTMENT_ID, department.getDepartmentId());
        params.put(DEPARTMENT_NAME, department.getDepartmentName());
        return namedParameterJdbcTemplate.update(updateSql, params);

    }

    @Override
    public int delete(Integer departmentId) {

        LOGGER.debug("delete(departmentId:{})", departmentId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(DEPARTMENT_ID, departmentId);
        return namedParameterJdbcTemplate.update(deleteSql, mapSqlParameterSource);

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
