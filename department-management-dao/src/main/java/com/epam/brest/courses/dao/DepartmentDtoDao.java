package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.dto.DepartmentDto;

import java.util.List;

/**
 * DepartmentDto DAO Interface.
 */
public interface DepartmentDtoDao {

    /**
     * Get all departments with avg salary by department.
     *
     * @return departments list.
     */
    List<DepartmentDto> findAllWithAvgSalary();

}