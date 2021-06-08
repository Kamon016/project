package com.example.Department.Service;

import com.example.Department.Entity.Department;
import com.example.Department.Repository.DepartmentRepository;
import com.example.Department.models.DepartmentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public Department saveDepartment(DepartmentDto departmentDto){
        validate(departmentDto);
        Department department = new Department();
        department.setDescription(departmentDto.getDescription());
        department.setName(departmentDto.getName());
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

    public Department updateDepartment(DepartmentDto departmentDto){

        if (departmentDto.getDepartmentId()==null){
            throw new RuntimeException("departmentId is null");
        }
        validate(departmentDto);
        Optional<Department> byId = departmentRepository.findById(departmentDto.getDepartmentId());
        if (byId.isEmpty()) {
            throw new RuntimeException("department not found!");
        }
        Department department = departmentRepository.findByDepartmentId(departmentDto.getDepartmentId());
        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());

        log.info("Inside updateUser method of UserService");
        return departmentRepository.save(department);

    }

    private void validate(DepartmentDto departmentDto) {
        if (StringUtils.isEmpty(departmentDto.getName())) {
            throw new RuntimeException("name is empty!");
        }
        if (StringUtils.isEmpty(departmentDto.getDescription())) {
            throw new RuntimeException("description is empty!");
        }
    }

}
