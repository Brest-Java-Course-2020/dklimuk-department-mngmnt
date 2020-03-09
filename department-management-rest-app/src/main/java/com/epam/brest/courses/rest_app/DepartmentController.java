package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.dto.DepartmentDto;
import com.epam.brest.courses.service.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Department controller.
 */
@RestController(value = "/api")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentDtoService departmentDtoService;

    public DepartmentController(DepartmentDtoService departmentDtoService) {
        this.departmentDtoService = departmentDtoService;
    }

    /**
     * Goto departments list page.
     *
     * @return view name
     */
    @GetMapping(value = "/departments")
    public final Collection<DepartmentDto> departments() {

        LOGGER.debug("departments()");
        return departmentDtoService.findAllWithAvgSalary();
    }

}