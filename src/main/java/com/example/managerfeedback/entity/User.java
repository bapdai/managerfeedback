package com.example.managerfeedback.entity;

import com.example.managerfeedback.entity.Role;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;
    private String username;

    private String email;

    private String phoneNumber;

    private String address;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password){
        this.username=username;
        this.email=email;
        this.password=password;
    }

    public User(String username, String email, String password, String firstName, String lastName, String phoneNumber, String address){
        this.username=username;
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }
}