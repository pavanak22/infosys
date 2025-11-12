package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Complaint;
import com.example.demo.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ------------------- Admin Login -------------------
    @PostMapping("/login")
    public Admin login(@RequestParam String email, @RequestParam String password) {
        return adminService.validateLogin(email, password);
    }

    // ------------------- Get all complaints -------------------
    @GetMapping("/complaints")
    public List<Complaint> getAllComplaints() {
        return adminService.getAllComplaints();
    }

    // ------------------- Update complaint status -------------------
    @PutMapping("/complaints/{complaintId}")
    public Complaint updateComplaintStatus(@PathVariable Long complaintId, @RequestParam String status) {
        return adminService.updateComplaintStatus(complaintId, status);
    }
}
