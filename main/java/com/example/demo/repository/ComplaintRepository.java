package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.Department;
import com.example.demo.entity.User;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    // Find complaints by user
    List<Complaint> findByUser(User user);

    // Find complaints by department
    List<Complaint> findByDepartment(Department department);
}
