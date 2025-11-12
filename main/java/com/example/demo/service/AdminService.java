package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Complaint;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    // Admin login
    public Admin validateLogin(String email, String password) {
        // Make sure AdminRepository has: Optional<Admin> findByEmailAndPassword(String email, String password);
        return adminRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    // Get all complaints (for admin dashboard)
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // Close or update complaint status
    public Complaint updateComplaintStatus(Long complaintId, String status) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus(status);
        return complaintRepository.save(complaint);
    }
}
