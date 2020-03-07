package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Employee;
import com.epam.brest.courses.service.DepartmentService;
import com.epam.brest.courses.service.EmployeeService;
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
 * Employee controller.
 */
@Controller
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final DepartmentService departmentService;

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    /**
     * Goto employees page.
     *
     * @return view name
     */
    @GetMapping(value = "/employees")
    public final String employees(Model model) {

        LOGGER.debug("employees()");
        model.addAttribute("employees", employeeService.findAll());
        return "employees";
    }

    /**
     * Goto edit employee page.
     *
     * @return view name
     */
    @GetMapping(value = "/employee/{id}")
    public final String gotoEditEmployeePage(@PathVariable Integer id, Model model) {

        LOGGER.debug("gotoEditEmployeePage({},{})", id, model);
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        if (optionalEmployee.isPresent()) {
            model.addAttribute("isNew", false);
            model.addAttribute("employee", optionalEmployee.get());
            model.addAttribute("departments", departmentService.findAll());
            return "employee";
        } else {
            // TODO employee not found - pass error message as parameter or handle not found error
            return "redirect:employees";
        }
    }

    /**
     * Update employee.
     *
     * @param employee  employee with filled data.
     * @param result    binding result
     * @return          view name
     */
    @PostMapping(value = "/employee/{id}")
    public String updateEmployee(@Valid Employee employee,
                                 BindingResult result) {

        LOGGER.debug("updateEmployee({}, {})", employee, result);
        // TODO implement validation
        if (result.hasErrors()) {
            return "employee";
        } else {
            this.employeeService.update(employee);
            return "redirect:/employees";
        }
    }

    /**
     * Goto add employee page.
     *
     * @return view name
     */
    @GetMapping(value = "/employee")
    public final String gotoAddEmployeePage(Model model) {

        LOGGER.debug("gotoAddEmployeePage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("employee", new Employee().setDepartmentId(1));
        model.addAttribute("departments", departmentService.findAll());
        return "employee";
    }

    /**
     * Persist new employee into persistence storage.
     *
     * @param employee new employee with filled data.
     * @param result     binding result.
     * @return view name
     */
    @PostMapping(value = "/employee")
    public String addEmployee(@Valid Employee employee,
                              BindingResult result) {

        LOGGER.debug("addEmployee({}, {})", employee, result);
        // TODO implement validation
        if (result.hasErrors()) {
            return "employee";
        } else {
            this.employeeService.create(employee);
            return "redirect:/employees";
        }
    }

    /**
     * Delete employee.
     *
     * @return view name
     */
    @GetMapping(value = "/employee/{id}/delete")
    public final String deleteEmployeeById(@PathVariable Integer id, Model model) {

        LOGGER.debug("delete({},{})", id, model);
        employeeService.delete(id);
        return "redirect:/employees";
    }
}