package com.docker.dockerexp.controller;

import com.docker.dockerexp.entity.User;
import com.docker.dockerexp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserRepository userRepository;

    // Sample GET API
    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") Long id) {
        User user=new User();
        user.setName("Test"+id);
        userRepository.save(user);
        return "User with ID: " + id;
    }

    // Sample POST API
    @PostMapping
    public String createUser(@RequestBody String name) {
        return "Created user: " + name;
    }

    // Sample PUT API
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody String name) {
        return "Updated user ID " + id + " with name " + name;
    }

    // Sample DELETE API
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return "Deleted user with ID: " + id;
    }
}
