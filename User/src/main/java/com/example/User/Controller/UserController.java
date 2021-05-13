package com.example.User.Controller;

import com.example.User.Entity.Users;
import com.example.User.Repository.UserRepository;
import com.example.User.Service.UserService;
import com.example.User.VO.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController  {

    @Inject
    private UserService userService;
    @Inject
    private UserRepository userRepository;

    @PostMapping("/addUser")
    public ResponseEntity<Users> addNewUsers(@RequestParam String name, @RequestParam String email, @RequestParam Long departmentId){
        Users users = new Users();
        users.setName(name);
        users.setEmail(email);
        users.setDepartmentId(departmentId);
        userService.saveUser(users);
        log.info("Inside addNewUsers method of UserController");
        return ResponseEntity.ok().header("201").body(users);
    }

    @GetMapping("/readAllUsers")
    public  ResponseEntity<Iterable<Users>> readAllUsers() {
        Iterable user = userRepository.findAll();
        log.info("Inside readAllUsers method of UserController");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplateVO> getUserWithDepartment(@PathVariable("id") Long userId){
        log.info("Inside getUserWithDepartment method of UserController");
        return ResponseEntity.ok().body(userService.getUserWithDepartment(userId));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestParam Long userId,
                                             @RequestParam String name,
                                             @RequestParam String email){
        try {
            Users user = userService.findUserById(userId);
            user.setUserId(userId);
            user.setName(name);
            user.setEmail(email);
            userService.saveUser(user);
            log.info("Inside updateUser method of UserController");
            return ResponseEntity.ok().body("201, Success");
        }catch(NullPointerException e){
            return ResponseEntity.badRequest().body("User not found");
        }catch(Error e ){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId){
        try{
            userService.deleteUserById(userId);
            log.info("Inside deleteUser method of UserController");
            return ResponseEntity.ok().body("200, Success");
        }catch(EmptyResultDataAccessException e){
            return ResponseEntity.badRequest().body("User not found");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
