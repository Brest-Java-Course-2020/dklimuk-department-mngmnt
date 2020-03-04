package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.dto.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Department DTO Service Interface implementation.
 */
@Component
public class DepartmentDtoDaoJdbc implements DepartmentDtoDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoDaoJdbc.class);

    @Value("${departmentDto.findAllWithAvgSalary}")
    private String findAllWithAvgSalarySql;


    public DepartmentDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<DepartmentDto> findAllWithAvgSalary() {

        LOGGER.debug("findAllWithAvgSalary()");
        List<DepartmentDto> departments = namedParameterJdbcTemplate.query(findAllWithAvgSalarySql,
                BeanPropertyRowMapper.newInstance(DepartmentDto.class));
        return departments;
    }

}