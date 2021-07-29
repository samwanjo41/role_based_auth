package com.example.role_based.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String userName;
    private String password;
    private boolean active;
    private String roles;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
