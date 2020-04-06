package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Department;
import com.epam.brest.courses.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DepartmentServiceRest implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public DepartmentServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Department> findAll() {

        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Department>) responseEntity.getBody();
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {

        LOGGER.debug("findById({})", departmentId);
        ResponseEntity<Department> responseEntity =
                restTemplate.getForEntity(url + "/" + departmentId, Department.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Integer create(Department department) {

        LOGGER.debug("create({})", department);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, department, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public int update(Department department) {

        LOGGER.debug("update({})", department);
        // restTemplate.put(url, department);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<>(department, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public int delete(Integer departmentId) {

        LOGGER.debug("delete({})", departmentId);
        //restTemplate.delete(url + "/" + departmentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + departmentId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

}
