package com.spring_security_6_demo.controller;

import com.spring_security_6_demo.entities.Role;
import com.spring_security_6_demo.entities.User;
import com.spring_security_6_demo.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private final UserService userService;
    public Controller(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/roles")
    public List<Role> roles() {
        return userService.getAllRoles();
    }
    @PostMapping("/role")
    public Role addRole(@RequestBody Role role) {
        return userService.addNewRole(role);
    }
}
