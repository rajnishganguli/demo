package com.example.demo.service;

import com.example.demo.bean.model.AdminDetail;
import com.example.demo.bean.model.CustomerDetail;
import com.example.demo.bean.dto.UserDetailsDto;
import com.example.demo.constants.CommonConstants;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.util.ThreadLocalContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AdminRepository adminRepository;

    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String userType = ThreadLocalContextHolder.get();
        return loadUserByUserName(userName, userType);
    }

    public UserDetails loadUserByUserName(String userName, String userType) {
        logger.info("**************** userType ********** {} ", userType);
        if(CommonConstants.USER_TYPE_CUSTOMER.equals(userType)){
            Optional<CustomerDetail> customerDetail = customerRepository.findByUserName(userName);
            UserDetailsDto customerUserDetails = customerDetail.map(UserDetailsDto::new).orElseThrow(() -> new UsernameNotFoundException("User not found " + userName));

            Collection<? extends GrantedAuthority> existingAuthorities = customerUserDetails.getAuthorities();
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(existingAuthorities);
            updatedAuthorities.add(new SimpleGrantedAuthority(CommonConstants.ROLE_CUSTOMER));
            customerUserDetails.setAuthorities(updatedAuthorities);
            customerUserDetails.getRoles().add(CommonConstants.ROLE_CUSTOMER);

            return customerUserDetails;
        } else if (CommonConstants.USER_TYPE_ADMIN.equals(userType)){
            Optional<AdminDetail> adminDetail = adminRepository.findByUserName(userName);
            UserDetailsDto adminDetailsDto = adminDetail.map(UserDetailsDto::new).orElseThrow(() -> new UsernameNotFoundException("User not found " + userName));

            Collection<? extends GrantedAuthority> existingAuthorities = adminDetailsDto.getAuthorities();
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(existingAuthorities);
            updatedAuthorities.add(new SimpleGrantedAuthority(CommonConstants.ROLE_ADMIN));
            adminDetailsDto.setAuthorities(updatedAuthorities);
            adminDetailsDto.getRoles().add(CommonConstants.ROLE_ADMIN);

            return adminDetailsDto;
        } else {
            return null;
        }
    }

}
