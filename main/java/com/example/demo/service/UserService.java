package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.User;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    // ------------------- User Registration -------------------
    public User registerUser(User user) {
        // Optionally, check if email already exists
        Optional<User> existingUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(existingUser.isPresent()) {
            throw new RuntimeException("User already exists with this email");
        }
        return userRepository.save(user);
    }

    // ------------------- User Login -------------------
    public User loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    // ------------------- Submit Complaint -------------------
    public Complaint submitComplaint(Complaint complaint) {
        // By default, status is "Open"
        complaint.setStatus("Open");
        return complaintRepository.save(complaint);
    }

    // ------------------- Get Complaints for a User -------------------
    public List<Complaint> getUserComplaints(User user) {
        return complaintRepository.findByUser(user);
    }

    // ------------------- Get All Complaints (for Admin/Dept) -------------------
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // ------------------- Update Complaint Status -------------------
    public Complaint updateComplaintStatus(Long complaintId, String status) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(status);
        return complaintRepository.save(complaint);
    }
}
