package com.example.User.Service;

import com.example.User.Entity.Users;
import com.example.User.Repository.UserRepository;
import com.example.User.VO.Department;
import com.example.User.VO.ResponseTemplateVO;
import com.example.User.models.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Users saveUser(UserDto userDto) {

        validate(userDto);
        Users users = new Users();
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setDepartmentId(userDto.getDepartmentId());

        log.info("Inside saveUser method of UserService");
        return userRepository.save(users);

    }

    private void validate(UserDto userDto) {
        if (StringUtils.isEmpty(userDto.getName())) {
            throw new RuntimeException("name is empty!");
        }


    }

    public void deleteUserById(Long userId) {

        if (userId == null) {
            throw new RuntimeException("userId is null!");
        }
        Optional<Users> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new RuntimeException("user not found!");
        }

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

    public ResponseTemplateVO getUserWIthDepartment() {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        log.info("Inside getUserWithDepartment method of UserService");
        return vo;
    }
}
