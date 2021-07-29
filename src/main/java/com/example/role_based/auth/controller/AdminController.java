package com.example.role_based.auth.controller;

import com.example.role_based.auth.entity.User;
import com.example.role_based.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PutMapping(path = "/update/{username}")
    public String updateStudent(@PathVariable("username") String username, @RequestBody User user) {
        String statement = "";
        Optional<User> myUser = userRepository.findByUserName(username);

        if (myUser.isPresent()) {
            User updateUser = myUser.get();
            updateUser.setUserName(user.getUserName());
            updateUser.setRoles("ADMIN");
            userRepository.save(updateUser);
            statement = "User " + user.getUserName() + " Role updated successfully";
        } else {
            statement = "Failed!!! User " + username + " does not exist";
        }
        return statement;

    }

    @DeleteMapping(path = "/delete/{username}")
    public String deleteStudent(@PathVariable("username") String username) {
        String statement = "";
        Optional<User> myUser = userRepository.findByUserName(username);


        if (myUser.isPresent()) {
            User updateUser = myUser.get();
            userRepository.delete(updateUser);
            statement = "User " + updateUser.getUserName() + " Deleted successfully";
        } else {
            statement = "Failed!!! User " + username + " does not exist";
        }
        return statement;
    }
}
