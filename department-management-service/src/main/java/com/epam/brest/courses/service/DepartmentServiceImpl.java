package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.DepartmentDao;
import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentDao departmentDao;

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        LOGGER.trace("findAll()");
        return departmentDao.findAll();
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        LOGGER.debug("findById(id:{})", departmentId);
        return departmentDao.findById(departmentId);
    }

    @Override
    public Integer create(Department department) {
        LOGGER.debug("create(department:{})", department);
        return departmentDao.create(department);
    }

    @Override
    public int update(Department department) {
        LOGGER.debug("update(department:{})", department);
        return departmentDao.update(department);
    }

    @Override
    public int delete(Integer departmentId) {
        LOGGER.debug("delete(departmentId:{})", departmentId);
        return departmentDao.delete(departmentId);
    }
}