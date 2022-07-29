package com.example.managerfeedback.entity;

import com.example.managerfeedback.entity.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull(message = "firstName không được bỏ trống")
    @Size(min = 1, message = "firstName không được bỏ trống")
    private String firstName;

    @NotNull(message = " lastName không được bỏ trống ")
    @Size(min = 1, message = "lastName không được bỏ trống")
    private String lastName;

    @NotNull(message = " username không được bỏ trống ")
    private String username;

    @NotNull(message = "email không được bỏ trống")
    @Email(message = "Không đúng định dạng email!, Vui lòng nhập lại")
    private String email;

//    @NotNull(message = "phoneNumber không được bỏ trống")
//    @Pattern(regexp = "^{10}", message = "Vui lòng nhập đúng định dạng số điện thoại")
    private String phoneNumber;

//    @NotNull(message = "address không được bỏ trống")
    private String address;

    @NotNull(message = "password không được bỏ trống")
    @Size(min = 6, message = "password phải lớn hơn hoặc bằng 6")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

//    public User(String username, String email, String password){
//        this.username=username;
//        this.email=email;
//        this.password=password;
    public User(String firstName, String lastName, String username, String email,String phoneNumber, String address, String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.username=username;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.password=password;
    }
}