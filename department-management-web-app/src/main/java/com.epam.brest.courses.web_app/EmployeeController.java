package com.epam.brest.courses.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Employee controller.
 */
@Controller
public class EmployeeController {

    /**
     * Goto employees page.
     *
     * @return view name
     */
    @GetMapping(value = "/employees")
    public final String employees() {
        return "employees";
    }

    /**
     * Goto employee page.
     *
     * @return view name
     */
    @GetMapping(value = "/employee")
    public final String employee() {
        return "employee";
    }
}