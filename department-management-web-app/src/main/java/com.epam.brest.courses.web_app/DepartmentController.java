package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Department;
import com.epam.brest.courses.service.DepartmentDtoService;
import com.epam.brest.courses.service.DepartmentService;
import com.epam.brest.courses.web_app.validators.DepartmentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Department controller.
 */
@Controller
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentDtoService departmentDtoService;

    private final DepartmentService departmentService;

    @Autowired
    DepartmentValidator departmentValidator;

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
            model.addAttribute("isNew", false);
            model.addAttribute("department", optionalDepartment.get());
            return "department";
        } else {
            // TODO department not found - pass error message as parameter or handle not found error
            return "redirect:departments";
        }
    }
    /**
     * Update department.
     *
     *  @param department department with filled data.
     *  @param result     binding result
     * @return view name
     */
    @PostMapping(value = "/department/{id}")
    public String updateDepartment(@Valid Department department,
                                   BindingResult result) {

        LOGGER.debug("updateDepartment({}, {})", department, result);
        departmentValidator.validate(department, result);
        if (result.hasErrors()) {
            return "department";
        } else {
            this.departmentService.update(department);
            return "redirect:/departments";
        }
    }
    /**
     * Goto add department page.
     *
     * @return view name
     */
    @GetMapping(value = "/department")
    public final String gotoAddDepartmentPage(Model model) {

        LOGGER.debug("gotoAddDepartmentPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("department", new Department());
        return "department";
    }
    /**
     * Persist new department into persistence storage.
     *
     * @param department new department with filled data.
     * @param result     binding result.
     * @return view name
     */
    @PostMapping(value = "/department")
    public String addDepartment(@Valid Department department,
                                BindingResult result) {

        LOGGER.debug("addDepartment({}, {})", department, result);
        departmentValidator.validate(department, result);
        if (result.hasErrors()) {
            return "department";
        } else {
            this.departmentService.create(department);
            return "redirect:/departments";
        }
    }
    /**
     * Delete department.
     *
     * @return view name
     */
    @GetMapping(value = "/department/{id}/delete")
    public final String deleteDepartmentById(@PathVariable Integer id, Model model) {

        LOGGER.debug("delete({},{})", id, model);
        departmentService.delete(id);
        return "redirect:/departments";
    }
}