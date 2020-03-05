package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Department;
import com.epam.brest.courses.service.DepartmentDtoService;
import com.epam.brest.courses.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * Department controller.
 */
@Controller
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentDtoService departmentDtoService;

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentDtoService departmentDtoService, DepartmentService departmentService) {
        this.departmentDtoService = departmentDtoService;
        this.departmentService = departmentService;
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
     * Goto edit department page.
     *
     * @return view name
     */
    @GetMapping(value = "/department/{id}")
    public final String gotoEditDepartmentPage(@PathVariable Integer id, Model model) {

        LOGGER.debug("gotoEditDepartmentPage({},{})", id, model);
        Optional<Department> optionalDepartment = departmentService.findById(id);
        if (optionalDepartment.isPresent()) {
            model.addAttribute("department", optionalDepartment.get());
            return "department";
        } else {
            // TODO department not found - pass error message as parameter or handle not found error
            return "redirect:departments";
        }
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