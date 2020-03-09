package com.epam.brest.courses.rest_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Root controller.
 */
@RestController
public class VersionController {

    private final static String VERSION = "0.0.1";

    @GetMapping(value = "/version")
    public String version() {
        return VERSION;
    }
}