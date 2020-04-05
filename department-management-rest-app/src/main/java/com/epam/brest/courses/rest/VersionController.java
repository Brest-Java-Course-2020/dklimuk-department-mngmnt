package com.epam.brest.courses.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Root controller.
 */
@RestController
public class VersionController {

    private final static String VERSION = "0.0.1";

    /**
     * Get current version.
     *
     * @return version
     */
    @GetMapping(value = "/version")
    public String version() {
        return VERSION;
    }
}