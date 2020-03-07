package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Department;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.courses.constants.DepartmentConstants.DEPARTMENT_NAME_SIZE;

@Component
public class DepartmentValidator implements Validator  {

    @Override
    public boolean supports(Class<?> clazz) {
        return Department.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "departmentName", "departmentName.empty");
        Department department = (Department) target;

        if (StringUtils.hasLength(department.getDepartmentName())
                && department.getDepartmentName().length() > DEPARTMENT_NAME_SIZE) {
            errors.rejectValue("departmentName", "departmentName.maxSize");
        }
    }
}
