package com.spring_security_6_demo.services;

import com.spring_security_6_demo.dao.AppUserRepository;
import com.spring_security_6_demo.dao.RoleRepository;
import com.spring_security_6_demo.entities.Role;
import com.spring_security_6_demo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl implements UserService{
    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AppUserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User addRoleToUser(String userName, String role) {
        User user = userRepository.findByUsername(userName);
        Role role1 = roleRepository.findByRole(role);
        user.getRoles().add(role1);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
