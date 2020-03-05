package com.epam.brest.courses.web_app;

import com.epam.brest.courses.service.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Department controller.
 */
@Controller
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
    public final String departments(Model model) {

        LOGGER.debug("departments()");
        model.addAttribute("departments", departmentDtoService.findAllWithAvgSalary());
        return "departments";
    }

    /**
     * Goto department page.
     *
     * @return view name
     */
    @GetMapping(value = "/department")
    public final String gotoAddDepartmentPage(Model model) {

        return "department";
    }
}