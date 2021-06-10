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
    @Autowired
    private DepartmentProxyService departmentProxyService;

    public Users saveUser(UserDto userDto) {

        validate(userDto);
        Users users = new Users();
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setDepartmentId(userDto.getDepartmentId());

        log.info("Inside saveUser method of UserService");
        return userRepository.save(users);

    }

    public Users updateUser(UserDto userDto){

        if (userDto.getUserId()==null){
            throw new RuntimeException("userID is null");
        }
        validate(userDto);
        Optional<Users> byId = userRepository.findById(userDto.getUserId());
        if (byId.isEmpty()) {
            throw new RuntimeException("user not found!");
        }
        Users users = userRepository.findByUserId(userDto.getUserId());
        users.setDepartmentId(userDto.getDepartmentId());
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());

        log.info("Inside updateUser method of UserService");
        return userRepository.save(users);
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

    private void validate(UserDto userDto) {
        if (StringUtils.isEmpty(userDto.getName())) {
            throw new RuntimeException("name is empty!");
        }
        if (StringUtils.isEmpty(userDto.getEmail())) {
            throw new RuntimeException("email is empty!");
        }
        if (StringUtils.isEmpty(userDto.getDepartmentId())) {
            throw new RuntimeException("department is empty!");
        }
        Department department = departmentProxyService.getDepartmentWithUser(userDto.getDepartmentId());
        Department CheckDepartment = new Department();
        if(department.equals(CheckDepartment)){
            throw new RuntimeException("department not found!");
        }
    }


    public Users findUserById(Long userId) {
        if(userId == null){
            throw new RuntimeException("userId is null");
        }
        Optional<Users> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new RuntimeException("user not found!");
        }
        log.info("Inside findUserById method of UserService");
        return userRepository.findByUserId(userId);

    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        if(userId == null){
            throw new RuntimeException("userId is null");
        }
        Optional<Users> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new RuntimeException("user not found!");
        }
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Users users = userRepository.findByUserId(userId);
        Long departmentId = users.getDepartmentId();
        Department department = departmentProxyService.getDepartmentWithUser(departmentId);
        Department CheckDepartment = new Department();
        if(department.equals(CheckDepartment)){
            throw new RuntimeException("department not found!");
        }
        vo.setUsers(users);
        vo.setDepartment(department);
        log.info("Inside getUserWithDepartment method of UserService");
        return vo;
    }

    /*
    Пока что в сомнениях как правильно сделать

    public ResponseTemplateVO getUserWIthDepartment() {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        log.info("Inside getUserWithDepartment method of UserService");
        return vo;
    }
     */
}
