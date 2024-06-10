package com.example.demo.service;

import com.example.demo.bean.model.AdminDetail;
import com.example.demo.bean.request.AdminRequest;
import com.example.demo.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService {

    @Autowired
    AdminRepository adminRepository;

    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    Logger logger = LoggerFactory.getLogger(AdminDetailsService.class);

    public AdminDetail retrieveAdmin(Long id){
        return adminRepository.findById(id).orElse(null);
    }

    public AdminDetail retrieveAdminByUserName(String userName){
        return adminRepository.findByUserName(userName).orElse(null);
    }

    public AdminDetail UpdateAdmin(AdminDetail admin){
        admin.setPassword(encoder.encode(admin.getPassword()));
        return adminRepository.saveAndFlush(admin);
    }

    public AdminDetail createAdmin(AdminRequest request){
        try {
            AdminDetail admin = new AdminDetail(request.getName(), request.getUserName(), encoder.encode(request.getPassword()));
            return adminRepository.saveAndFlush(admin);
        } finally {
            logger.info("AdminAdded successfully");
        }

    }

    public void deleteAdmin(AdminDetail admin){
        adminRepository.delete(admin);
    }
}
