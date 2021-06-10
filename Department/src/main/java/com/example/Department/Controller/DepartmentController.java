package com.example.Department.Controller;

import com.example.Department.Entity.Department;
import com.example.Department.Repository.DepartmentRepository;
import com.example.Department.Service.DepartmentService;
import com.example.Department.models.DepartmentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {

    @Inject
    DepartmentRepository departmentRepository;
    @Inject
    DepartmentService departmentService;

    @PostMapping("/addDepartment")
    public ResponseEntity<Department> addNewDepartment(@RequestBody DepartmentDto departmentDto){

        Department department = departmentService.saveDepartment(departmentDto);
        log.info("Inside addDepartment method of DepartmentController");
        return ResponseEntity.ok(department);

    }

    @GetMapping("/readAllDepartments")
    public  ResponseEntity<Iterable<Department>> readAllDepartment() {

        Iterable department = departmentRepository.findAll();
        log.info("Inside readAllDepartment method of DepartmentController");
        return ResponseEntity.ok().body(department);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long departmentId){

            Department department = departmentService.findDepartmentById(departmentId);
            log.info("Inside getDepartmentById method of DepartmentController");
            return ResponseEntity.ok().body(department);

    }

    @PutMapping("/updateDepartment")
    public ResponseEntity<Department> updateDepartment(@RequestBody DepartmentDto departmentDto){

            Department department = departmentService.updateDepartment(departmentDto);
            log.info("Inside updateDepartment method of DepartmentController");
            return ResponseEntity.ok(department);

    }

    @DeleteMapping("/deleteDepartment")
    public ResponseEntity<?> deleteDepartment(@RequestParam Long departmentId){

            departmentService.deleteDepartmentById(departmentId);
            log.info("Inside deleteDepartment method of DepartmentController");
            return ResponseEntity.ok("Success");

    }

    @GetMapping("/getDepartmentWithUser/{departmentId}")
    public ResponseEntity<Department> getDepartmentWithUser(@PathVariable("departmentId") Long departmentId){

        Department department = departmentService.getDepartmentWithUser(departmentId);
        log.info("Inside getDepartmentWithUser method of DepartmentController");
        return ResponseEntity.ok(department);
    }
}
