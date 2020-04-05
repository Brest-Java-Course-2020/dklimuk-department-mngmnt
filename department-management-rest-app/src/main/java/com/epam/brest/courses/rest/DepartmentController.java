package com.epam.brest.courses.rest;


import com.epam.brest.courses.model.Department;
import com.epam.brest.courses.model.dto.DepartmentDto;
import com.epam.brest.courses.rest.exception.DepartmentNotFoundException;

import com.epam.brest.courses.service.DepartmentDtoService;
import com.epam.brest.courses.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Goto departments list page.
     *
     * @return view name
     */
    @GetMapping(value = "/departments")
    public final Collection<Department> departments() {

        LOGGER.debug("departments()");
        return departmentService.findAll();
    }

    @GetMapping("/departments/{id}")
    public Department findById(@PathVariable Integer id) {

        LOGGER.debug("find department by id({})", id);
        return departmentService.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    @PostMapping(path = "/departments", consumes = "application/json", produces = "application/json")
    public Integer add(@RequestBody String departmentName) {
        LOGGER.debug("add department with name({})", departmentName);
        return departmentService.create(new Department(departmentName));
    }
}
