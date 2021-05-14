package com.example.Department.Controller;

import com.example.Department.Entity.Department;
import com.example.Department.Repository.DepartmentRepository;
import com.example.Department.Service.DepartmentService;
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
    public ResponseEntity<Department> addNewDepartment(@RequestParam String name, @RequestParam String description){
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        departmentService.saveDepartment(department);
        log.info("Inside addDepartment method of DepartmentController");
        return ResponseEntity.ok().header("201").body(department);
    }

    @GetMapping("/readAllDepartments")
    public  ResponseEntity<Iterable<Department>> readAllDepartment() {
        Iterable department = departmentRepository.findAll();
        log.info("Inside readAllDepartment method of DepartmentController");
        return ResponseEntity.ok().body(department);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long departmentId){
        try {
            Department department = departmentService.findDepartmentById(departmentId);
            log.info("Inside readDepartment method of DepartmentController");
            return ResponseEntity.ok().body(department);
        }catch(NullPointerException e){
            Department department = new Department();
            return ResponseEntity.badRequest().body(department);
        }
    }

    @PutMapping("/updateDepartment")
    public ResponseEntity<String> updateDepartment(@RequestParam Long departmentId,
                                                   @RequestParam String name,
                                                   @RequestParam String description){
        try {
            Department department = departmentService.findDepartmentById(departmentId);
            department.setDepartmentId(departmentId);
            department.setName(name);
            department.setDescription(description);
            departmentService.saveDepartment(department);
            log.info("Inside updateDepartment method of DepartmentController");
            return ResponseEntity.ok().body("201, Success");
        }catch(NullPointerException e){
            return ResponseEntity.badRequest().body("User not found");
        }catch(Error e ){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/deleteDepartment")
    public ResponseEntity<?> deleteDepartment(@RequestParam Long id){
        try{
            departmentService.deleteDepartmentById(id);
            log.info("Inside deleteDepartment method of DepartmentController");
            return ResponseEntity.ok().body("200, Success");
        }catch(EmptyResultDataAccessException e){
            return ResponseEntity.badRequest().body("User not found");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
