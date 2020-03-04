package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.DepartmentDtoDao;
import com.epam.brest.courses.model.dto.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentDtoServiceImpl implements DepartmentDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoServiceImpl.class);

    private final DepartmentDtoDao departmentDtoDao;

    public DepartmentDtoServiceImpl(DepartmentDtoDao departmentDtoDao) {
        this.departmentDtoDao = departmentDtoDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> findAllWithAvgSalary() {

        LOGGER.debug("findAllWithAvgSalary()");
        return departmentDtoDao.findAllWithAvgSalary();
    }
}