package com.spring_security_6_demo.dao;

import com.spring_security_6_demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
}
