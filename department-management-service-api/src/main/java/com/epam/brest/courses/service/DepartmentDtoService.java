package com.epam.brest.courses.service;

import com.epam.brest.courses.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoService {

    /**
     * Get all departments with avg salary by department.
     *
     * @return departments list.
     */
    List<DepartmentDto> findAllWithAvgSalary();

}