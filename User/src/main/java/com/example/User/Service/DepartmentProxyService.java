package com.example.User.Service;

import com.example.User.VO.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("DEPARTMENT-SERVICE")
public interface DepartmentProxyService {

    @RequestMapping("/departments/getDepartment")
    Department getDepartment(Long departmentId);

}
