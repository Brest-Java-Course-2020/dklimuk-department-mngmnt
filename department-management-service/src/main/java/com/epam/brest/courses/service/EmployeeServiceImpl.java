package com.epam.brest.courses.service;


import com.epam.brest.courses.dao.EmployeeDao;
import com.epam.brest.courses.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        LOGGER.trace("findAll()");
        return employeeDao.findAll();
    }

    @Override
    public List<Employee> findByDepartmentId(Integer departmentId) {
        LOGGER.trace("findByDepartmentId(departmentId:{})", departmentId);
        return employeeDao.findByDepartmentId(departmentId);
    }

    @Override
    public Optional<Employee> findById(Integer employeeId) {
        LOGGER.debug("findById(id:{})", employeeId);
        return employeeDao.findById(employeeId);
    }

    @Override
    public Integer create(Employee employee) {
        LOGGER.debug("create(employee:{})", employee);
        return employeeDao.create(employee);
    }

    @Override
    public int update(Employee employee) {
        LOGGER.debug("update(employee:{})", employee);
        return employeeDao.update(employee);
    }

    @Override
    public int delete(Integer employeeId) {
        LOGGER.debug("delete(employeeId:{})", employeeId);
        return employeeDao.delete(employeeId);
    }
}