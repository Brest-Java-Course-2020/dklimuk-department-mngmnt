package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.brest.courses.constants.EmployeeConstants.*;

/**
 * Employee DAO JDBC implementation.
 */
@Component
public class EmployeeDaoJdbc implements EmployeeDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoJdbc.class);

    @Value("${employee.findAll}")
    private String findAllSql;

    @Value("${employee.findById}")
    private String findByIdSql;

    @Value("${employee.findByDepartmentId}")
    private String findByDepartmentIdSql;


    @Value("${employee.create}")
    private String createSql;

    @Value("${employee.update}")
    private String updateSql;

    @Value("${employee.delete}")
    private String deleteSql;

    public EmployeeDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {

        LOGGER.trace("findAll()");
        return namedParameterJdbcTemplate.query(findAllSql, BeanPropertyRowMapper.newInstance(Employee.class));
    }

    @Override
    public List<Employee> findByDepartmentId(Integer departmentId) {

        LOGGER.trace("findByDepartmentId(departmentId:{})", departmentId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(DEPARTMENT_ID, departmentId);
        return namedParameterJdbcTemplate.query(findByDepartmentIdSql, namedParameters,
                BeanPropertyRowMapper.newInstance(Employee.class));
    }

    @Override
    public Optional<Employee> findById(Integer employeeId) {

        LOGGER.debug("findById(employeeId:{})", employeeId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(EMPLOYEE_ID, employeeId);
        List<Employee> results = namedParameterJdbcTemplate.query(findByIdSql, namedParameters,
                BeanPropertyRowMapper.newInstance(Employee.class));
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    @Override
    public Integer create(Employee employee) {

        LOGGER.debug("create(employee:{})", employee);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(FIRSTNAME, employee.getFirstname());
        parameters.addValue(LASTNAME, employee.getLastname());
        parameters.addValue(EMAIL, employee.getEmail());
        parameters.addValue(SALARY, employee.getSalary());
        parameters.addValue(DEPARTMENT_ID, employee.getDepartmentId());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(createSql, parameters, generatedKeyHolder);
        return Objects.requireNonNull(generatedKeyHolder.getKey()).intValue();
    }

    @Override
    public int update(Employee employee) {

        LOGGER.debug("update(employee:{})", employee);
        return namedParameterJdbcTemplate.update(updateSql, new BeanPropertySqlParameterSource(employee));
    }

    @Override
    public int delete(Integer employeeId) {

        LOGGER.debug("delete(employeeId:{})", employeeId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(EMPLOYEE_ID, employeeId);
        return namedParameterJdbcTemplate.update(deleteSql, mapSqlParameterSource);
    }

}