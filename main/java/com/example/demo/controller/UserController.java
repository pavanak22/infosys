package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    // ------------------- Register User -------------------
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // ------------------- Login User -------------------
    @PostMapping("/login")
    public User loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.loginUser(email, password);
    }

    // ------------------- Submit Complaint -------------------
    @PostMapping("/complaints")
    public Complaint submitComplaint(@RequestBody Complaint complaint) {
        return userService.submitComplaint(complaint);
    }

    // ------------------- Get complaints for a user -------------------
    @GetMapping("/{userId}/complaints")
    public List<Complaint> getUserComplaints(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        return userService.getUserComplaints(user);
    }
}
