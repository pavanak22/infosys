package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDto;
import com.example.demo.entity.Complaint;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "*") // Allow frontend requests
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // ---------------- Registration ----------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Department dept) {
        try {
            Department savedDept = departmentService.register(dept);
            return ResponseEntity.ok(savedDept);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", ex.getMessage()));
        }
    }

    // ---------------- Login ----------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            Department dept = departmentService.login(loginDto.getEmail(), loginDto.getPassword());
            return ResponseEntity.ok(dept);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", ex.getMessage()));
        }
    }

    // ---------------- Get complaints assigned to department ----------------
    @GetMapping("/{deptId}/complaints")
    public ResponseEntity<List<Complaint>> getDeptComplaints(@PathVariable Long deptId) {
        List<Complaint> complaints = departmentService.getDeptComplaints(deptId);
        return ResponseEntity.ok(complaints);
    }

    // ---------------- Close a complaint ----------------
    @PutMapping("/complaints/{complaintId}/close")
    public ResponseEntity<Complaint> closeComplaint(@PathVariable Long complaintId) {
        Complaint closedComplaint = departmentService.closeComplaint(complaintId);
        return ResponseEntity.ok(closedComplaint);
    }

    // ---------------- Get all departments ----------------
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
}
