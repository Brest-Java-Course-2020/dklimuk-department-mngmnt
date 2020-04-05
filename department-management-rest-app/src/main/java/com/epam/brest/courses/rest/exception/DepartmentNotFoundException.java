package com.epam.brest.courses.rest.exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Integer id) {
        super("Department id not found : " + id);
    }

}