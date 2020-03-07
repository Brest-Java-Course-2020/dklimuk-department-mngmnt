package com.epam.brest.courses.model;

public class Employee {
    private Integer employeeId;
    private String firstname;
    private String lastname;
    private String email;
    private Double salary;
    private Integer departmentId;


    public Integer getEmployeeId() {
        return employeeId;
    }

    public Employee setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public Employee setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Employee setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    public Double getSalary() {
        return salary;
    }

    public Employee setSalary(Double salary) {
        this.salary = salary;
        return this;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public Employee setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                '}';
    }
}
