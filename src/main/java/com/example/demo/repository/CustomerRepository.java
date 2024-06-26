package com.example.demo.repository;

import com.example.demo.bean.model.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDetail, Long> {
    Optional<CustomerDetail> findByUserName(String userName);
}
