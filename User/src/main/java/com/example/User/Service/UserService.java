package com.example.User.Service;

import com.example.User.Entity.Users;
import com.example.User.Repository.UserRepository;
import com.example.User.VO.Department;
import com.example.User.VO.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Users saveUser(Users users) {

        log.info("Inside saveUser method of UserService");
        return userRepository.save(users);

    }

    public void deleteUserById(Long userId) {

        log.info("Inside deleteUserById method of UserService");
        userRepository.deleteById(userId);

    }

    public Users findUserById(Long userId) {

        log.info("Inside findUserById method of UserService");
        return userRepository.findByUserId(userId);

    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Users users = userRepository.findByUserId(userId);
        Department department =
                restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + users.getDepartmentId(), Department.class);
        vo.setUsers(users);
        vo.setDepartment(department);
        log.info("Inside getUserWithDepartment method of UserService");
        return vo;
    }

    public ResponseTemplateVO getUserWIthDepartment(){
        ResponseTemplateVO vo = new ResponseTemplateVO();
        log.info("Inside getUserWithDepartment method of UserService");
        return vo;
    }
}
