package com.example.Department.Service;

import com.example.Department.Entity.Department;
import com.example.Department.Repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department){

        log.info("Inside saveDepartment method of DepartmentService");
        return departmentRepository.save(department);
    }

    public void deleteDepartmentById(Long departmentId){

        log.info("Inside deleteDepartmentById method of DepartmentService");
        departmentRepository.deleteById(departmentId);

    }

    public Department findDepartmentById(Long departmentId) {

        log.info("Inside findDepartmentById method of DepartmentService");
        return departmentRepository.findByDepartmentId(departmentId);

    }
}
