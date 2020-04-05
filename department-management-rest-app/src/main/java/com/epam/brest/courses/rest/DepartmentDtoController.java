package com.epam.brest.courses.rest;

import com.epam.brest.courses.model.dto.DepartmentDto;
import com.epam.brest.courses.service.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Department DTO REST controller.
 */
@RestController
public class DepartmentDtoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoController.class);

    private final DepartmentDtoService departmentDtoService;

    public DepartmentDtoController(DepartmentDtoService departmentDtoService) {
        this.departmentDtoService = departmentDtoService;
    }

    /**
     * Get department Dtos.
     *
     * @return Department Dtos collection.
     */
    @GetMapping(value = "/department_dtos")
    public final Collection<DepartmentDto> departmentDtos() {

        LOGGER.debug("departmentDtos()");
        return departmentDtoService.findAllWithAvgSalary();
    }
}