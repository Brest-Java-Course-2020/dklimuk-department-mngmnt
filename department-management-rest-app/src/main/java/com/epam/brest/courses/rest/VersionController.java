package com.epam.brest.courses.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Root controller.
 */
@RestController
@EnableSwagger2
public class VersionController {

    private final static String VERSION = "0.0.1";

    @GetMapping(value = "/version")
    public String version() {
        return VERSION;
    }
}