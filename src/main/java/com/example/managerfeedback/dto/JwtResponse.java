package com.example.managerfeedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;

    private String type = "Bearer";
    private Long id;

    private String firstName;

    private String lastName;
    private String username;
    private String email;

    private String phoneNumber;

    private String address;
    private List<String> roles;
    public JwtResponse(String accessToken, Long id,String firstName, String lastName, String username, String email, String phoneNumber, String address, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roles = roles;
    }
}

