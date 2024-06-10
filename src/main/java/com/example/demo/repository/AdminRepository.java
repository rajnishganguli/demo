package com.example.demo.repository;

import com.example.demo.bean.model.AdminDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminDetail, Long> {
    Optional<AdminDetail> findByUserName(String userName);
}
