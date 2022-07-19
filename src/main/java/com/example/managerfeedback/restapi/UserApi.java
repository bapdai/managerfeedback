package com.example.managerfeedback.restapi;

import com.example.managerfeedback.entity.User;
import com.example.managerfeedback.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UserApi {


    @Autowired
    UserDetailsServiceImpl userDetailsServiceimpl;

    @GetMapping("/views/list")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> getList(){
        return ResponseEntity.ok(userDetailsServiceimpl.findAll());
    }

    @GetMapping("/views/list/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        Optional<User> optionalUser = userDetailsServiceimpl.findById(id);
        if (!optionalUser.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalUser.get());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> create(@RequestBody User user){
        return ResponseEntity.ok(userDetailsServiceimpl.save(user));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        Optional<User> optionalUser = userDetailsServiceimpl.findById(id);
        if ((!optionalUser.isPresent())){
            ResponseEntity.badRequest().build();
        }
        User existUser = optionalUser.get();

        existUser.setUsername(user.getUsername());
        existUser.setEmail(user.getEmail());
        existUser.setPassword(user.getPassword());
        existUser.setFirstName(user.getFirstName());
        existUser.setLastName(user.getLastName());
        existUser.setAddress(user.getAddress());
        existUser.setPhoneNumber(user.getPhoneNumber());
        existUser.setRoles(user.getRoles());
        return ResponseEntity.ok(userDetailsServiceimpl.save(existUser));
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if ((!userDetailsServiceimpl.findById(id).isPresent())){
            ResponseEntity.badRequest().build();
        }
        userDetailsServiceimpl.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
