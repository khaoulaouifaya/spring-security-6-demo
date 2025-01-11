package com.spring_security_6_demo.services;

import com.spring_security_6_demo.entities.Role;
import com.spring_security_6_demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    User addNewUser(User user);
    Role addNewRole(Role role);
    User addRoleToUser(String userName, String role);
    List<User> getAllUsers();
    List<Role> getAllRoles();
}
