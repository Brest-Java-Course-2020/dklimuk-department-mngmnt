package com.epam.brest.courses.rest.exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Integer id) {
        super("Department not found for id: " + id);
    }

}