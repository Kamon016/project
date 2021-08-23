package com.example.User.Controller;

import com.example.User.Entity.Users;
import com.example.User.Repository.UserRepository;
import com.example.User.Service.UserService;
import com.example.User.VO.ResponseTemplateVO;
import com.example.User.models.UserDto;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestParam("login") String login, @RequestParam("password") String password){
        Users user = userService.login(login, password);
        log.info("Inside login method of UserController");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/addUser")
    public ResponseEntity<Users> addNewUsers(@RequestBody UserDto userDto){

        Users user = userService.saveUser(userDto);
        log.info("Inside addNewUsers method of UserController");
        return ResponseEntity.ok(user);

    }

    @GetMapping("/readAllUsers")
    public  ResponseEntity<Iterable> readAllUsers() {

        Iterable user = userRepository.findAll();
        log.info("Inside readAllUsers method of UserController");
        return ResponseEntity.ok(user);

    }

    @GetMapping("/findUser")
    public ResponseEntity<Users> findUser(@RequestParam Long userId){

        Users user = userService.findUserById(userId);
        log.info("Inside findUser method of UserController");
        return ResponseEntity.ok(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplateVO> getUserWithDepartment(@PathVariable("id") Long userId){

            ResponseTemplateVO responseTemplateVO = userService.getUserWithDepartment(userId);
            log.info("Inside getUserWithDepartment method of UserController");
            return ResponseEntity.ok(responseTemplateVO);

    }


    @PutMapping("/updateUser")
    public ResponseEntity<Users> updateUser(@RequestBody UserDto userDto){

            Users user = userService.updateUser(userDto);
            log.info("Inside updateUser method of UserController");
            return ResponseEntity.ok(user);

    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId){

            userService.deleteUserById(userId);
            log.info("Inside deleteUser method of UserController");
            return ResponseEntity.ok("Success");

    }
}
