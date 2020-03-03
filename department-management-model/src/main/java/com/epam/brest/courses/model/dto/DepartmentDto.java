package com.epam.brest.courses.model.dto;

import java.math.BigDecimal;

/**
 * POJO Department for model.
 */
public class DepartmentDto {

    /**
     * Department Id.
     */
    private Integer departmentId;

    /**
     * Department Name.
     */
    private String departmentName;

    /**
     * Average salary of the Department.
     */
    private BigDecimal avgSalary;

    /**
     * Constructor without arguments.
     */
    public DepartmentDto() {
    }

    /**
     * Constructor with department name.
     *
     * @param departmentName department name
     */
    public DepartmentDto(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Returns <code>Integer</code> representation of this departmentId.
     *
     * @return departmentId Department Id.
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the department's identifier.
     *
     * @param departmentId Department Id.
     */
    public void setDepartmentId(final Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Returns <code>String</code> representation of this departmentName.
     *
     * @return departmentName Department Name.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the department's name.
     *
     * @param departmentName Department Name.
     */
    public void setDepartmentName(final String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Returns <code>BigDecimal</code> representation of average salary
     * for the Department.
     *
     * @return departmentId.
     */
    public BigDecimal getAvgSalary() {
        return avgSalary;
    }

    /**
     * Sets the department's average salary.
     *
     * @param avgSalary Average salary.
     */
    public void setAvgSalary(final BigDecimal avgSalary) {
        this.avgSalary = avgSalary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DepartmentDto{"
                + "departentId=" + departmentId
                + ", departmentName='" + departmentName + '\''
                + ", avgSalary=" + avgSalary
                + '}';
    }
}