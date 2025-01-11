package com.spring_security_6_demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    private String label;
    @ManyToMany(fetch = FetchType.LAZY)
    List<User> users;
}
