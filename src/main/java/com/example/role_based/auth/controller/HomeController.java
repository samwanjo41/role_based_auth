package com.example.role_based.auth.controller;

import com.example.role_based.auth.entity.User;
import com.example.role_based.auth.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home() {
        return ("<h1>Welcome</h1>");
    }

    @GetMapping("/user")
    public String user() {
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("/admin")
    public String admin() {
        return ("<h1>Welcome Admin</h1>");
    }

    @ApiOperation(value = "Get list of Students in the System ", response = Iterable.class,
            tags = "getStudents")
    @GetMapping("/all/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Get all users with role admin
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/all/admin")
    public List<User> getAllUsersAdmin() {
        List<User> allUsers = userRepository.findAll();
        List<User> adminUsers = new ArrayList<>();

        for(User user : allUsers){
            if(user.getRoles().equals("ADMIN")){
                adminUsers.add(user);
            }
        }
        return adminUsers;
    }


    @GetMapping("/user/{username}")
    public User getSingleUser(@PathVariable("username") String username){
        User specificUser = new User();
        Optional<User> myUser = userRepository.findByUserName(username);
        if(myUser.isPresent()){
            specificUser = myUser.get();
        }
        return specificUser;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user) {
        String statement = "";
        Optional<User> checkUser = userRepository.findByUserName(user.getUserName());
        if (!checkUser.isPresent()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles("USER");
            user.setActive(true);
            userRepository.save(user);
            statement = "User " + user.getUserName() + " created successfully";
        } else {
            statement = "User with username " + user.getUserName() + " already exists!!!";
        }

        return statement;
    }


}
