package com.example.User.Service;

import com.example.User.VO.Department;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface DepartmentProxyService {
    @RequestMapping(value = "/departments/getDepartmentWithUser/{departmentId}", method = RequestMethod.GET)
    Department getDepartmentWithUser(@PathVariable("departmentId") Long departmentId);

}
