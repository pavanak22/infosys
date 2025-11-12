package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.Department;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ---------------- Login ----------------
    public Department login(String email, String password) {
        Optional<Department> optionalDept = departmentRepository.findByEmail(email);
        if (optionalDept.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }
        Department dept = optionalDept.get();
        if (!encoder.matches(password, dept.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return dept;
    }

    // ---------------- Get complaints assigned to a department ----------------
    public List<Complaint> getDeptComplaints(Long deptId) {
        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return complaintRepository.findByDepartment(dept);
    }

    // ---------------- Close a complaint ----------------
    public Complaint closeComplaint(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus("Closed");
        return complaintRepository.save(complaint);
    }

    // ---------------- Get all departments ----------------
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // ---------------- Registration ----------------
    public Department register(Department dept) {
        if (dept.getEmail() == null || dept.getEmail().isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (departmentRepository.existsByEmail(dept.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        dept.setPassword(encoder.encode(dept.getPassword()));
        return departmentRepository.save(dept);
    }
}
