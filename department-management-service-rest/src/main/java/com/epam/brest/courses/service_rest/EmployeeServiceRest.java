package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Employee;
import com.epam.brest.courses.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceRest implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public EmployeeServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Employee> findAll() {

        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Employee>) responseEntity.getBody();
    }

    @Override
    public List<Employee> findByDepartmentId(Integer departmentId) {

        LOGGER.debug("findByDepartmentId()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "?departmentId=" + departmentId, List.class);
        return (List<Employee>) responseEntity.getBody();
    }

    @Override
    public Optional<Employee> findById(Integer employeeId) {

        LOGGER.debug("findById({})", employeeId);
        ResponseEntity<Employee> responseEntity =
                restTemplate.getForEntity(url + "/" + employeeId, Employee.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Integer create(Employee employee) {

        LOGGER.debug("create({})", employee);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, employee, Integer.class);
        Object result = responseEntity.getBody();
        return (Integer) result;
    }

    @Override
    public int update(Employee employee) {

        LOGGER.debug("update({})", employee);
        restTemplate.put(url, employee);
        return 1;
    }

    @Override
    public int delete(Integer employeeId) {

        LOGGER.debug("delete({})", employeeId);
        restTemplate.delete(url + "/" + employeeId);
        return 1;
    }
}
